package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService
import com.ml.cmc.service.LotGeneratorService
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class PreconciliationController extends SessionInfoController{

    def securityLockService
    def lotGeneratorService
	def sessionFactory
    
    def index = {
        securityLockService.unLockFunctionality(getSessionId())
		def countryList = Medio.withCriteria{
			projections{
				distinct "country"
			}
			order("country")
		}
        render(view:'index', model:[countryList: countryList]) 
        }
    
    def lock = {
		
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
        
        if(medio == null) {
            response.setStatus(500)
            render message(code:"preconciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
            return
        }

        try{
            securityLockService.lockFunctionality(getUsername(), Constant.FUNC_PRECONCILIATE, getSessionId(), medio)
        }catch (SecLockException e) {
           def error = message(code:"preconciliation.security.error" ,default:"Error",args:[e.invalidObject?.username, medio])           
           response.setStatus(500)
           render error
           return
        } catch (Exception e){
            response.setStatus(500)
            render e.message
            return
        }
        
        def receiptInstanceList = getReceiptsForSales(medio, null, null)

		def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		
		if(medios instanceof Medio){
			medios = [medios]
		} 

		def salesSiteInstanceList = getSalesForSales(medios, null, null)
        
        render(template: "preconciliationBody", model:[receiptInstanceList:receiptInstanceList, salesSiteInstanceList:salesSiteInstanceList, filterType:"salesFilter"])

    }

    def listReceipts = {
       
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        def state1 = State.findById(1)
        def state2 = State.findById(2)
        def receiptInstanceList = Receipt.withCriteria {
			if(medio != null) eq('medio', medio)
             inList('state',[state1,state2])
           
        }
        
        render(template:"receiptTable", model:[receiptInstanceList: receiptInstanceList])
        
    }
    
    def listSalesSite = {PreconciliationCmd preconciliationCmd ->
		
        def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		if(medios instanceof Medio){
			medios = [medios]
		}

		def salesSiteInstanceList = []
		
		if(params.filterType == 'salesFilter') {
			salesSiteInstanceList = getSalesForSales(medios, params.sort, params.order)
		} else {
			salesSiteInstanceList = getSalesForDisable(medios, params.sort, params.order)
		}
		

/*        def state = State.findById(1)
        def salesSiteInstanceList = SalesSite.withCriteria {
            order(params.sort, params.order)
             if(medios != null) inList('medio', medios)
             eq('state',state)
             if(preconciliationCmd.salesSiteIds.size() >0) {
                 List<String> longIds = (preconciliationCmd.salesSiteIds instanceof String)?[preconciliationCmd.salesSiteIds]:preconciliationCmd.salesSiteIds.collect{it}
                 not{inList('id', longIds)}
             }
        }
*/
        
        render(template:"salesSiteTable", model:[salesSiteInstanceList: salesSiteInstanceList])
        
    }
    
    def group = { PreconciliationCmd preconciliationCmd ->
        if(params.salesSiteId.size() > 1 && params.receiptId.size() > 1){
            response.setStatus(500)
            render message(code:"preconcliation.relationship.error", default:"La relacion entre los Recibos y Ventas no es correcta")
            return
        }

        LinkedList preconciliation = (LinkedList)preconciliationCmd.createList() 
                
        if(params.salesSiteId.size() > 1){
            def receiptInstance = Receipt.findById(params.receiptId)
            params.salesSiteId.each{ saleId ->
                def salesSiteInstance = SalesSite.findById(saleId)
                preconciliation.addFirst(new PreSalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
            }
        } else {
            def salesSiteInstance = SalesSite.findById(params.salesSiteId)
            params.receiptId.each{ receipt ->
                def receiptInstance = Receipt.findById(receipt)
                preconciliation.addFirst(new PreSalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
            } 
        }
        
        render(template:"preconciliateTable", model:[preconciliationInstancelist: preconciliation])
        
    }
    
    def save = { PreconciliationCmd preconciliationCmd ->
        
        def lot = lotGeneratorService.getLotId()
        
        List salesSiteReceiptList = preconciliationCmd.createList()
        
		Preconciliation.withTransaction{
	        salesSiteReceiptList.each{ item ->
	            
	            def conciliation = new Conciliation(sale:item.salesSite, receipt:item.receipt, 
	                lot:lot, medio:item.receipt?.medio, period: item.salesSite?.period)
	            
	            conciliation.save()
	        }
        }
        
        /* call datastage */
        def job = "echo El proceso se ejecuto satisfactoriamente.".execute()
        job.waitFor()
        if(job.exitValue()){    
            response.setStatus(500)
            render job.err.text
        }
        render job.text
         
    }
	
	private getReceiptsForSales(Medio medio, String sortType, String orderType) {
		
		def state1 = State.findById(1)
		
		def registerType1 = RegisterType.findById(1);
		def registerType2 = RegisterType.findById(2);
		def registerType5 = RegisterType.findById(5);

		def receiptInstanceList = Receipt.withCriteria {
			order(sortType != null? sortType:'receiptNumber', orderType != null?orderType:'asc')
			if(medio != null) eq('medio', medio)
			eq('state',state1)
			inList('registerType',[registerType1, registerType2, registerType5])
		}
		
		return receiptInstanceList
		
	}
	
	private getSalesForSales(List medios,String sortType, String orderType ) {

		def state1 = State.findById(1)
		def state2 = State.findById(2)
		def registerType1 = RegisterType.findById(1);
		def registerType5 = RegisterType.findById(5);

		
		def salesSiteInstanceList = SalesSite.withCriteria {
			order(sortType != null? sortType:'receiptNumber', orderType != null?orderType:'asc')
			 if(medios != null) inList('medio', medios)
			 inList('state',[state1, state2])
			 inList('registerType',[registerType1, registerType5])
		}

		
	}
	
	private getReceiptsForDisable(Medio medio, String sortType, String orderType) {
		
		def state1 = State.findById(1)
		
		def registerType3 = RegisterType.findById(3);


		def receiptInstanceList = Receipt.withCriteria {
			order(sortType != null? sortType:'receiptNumber', orderType != null?orderType:'asc')
			if(medio != null) eq('medio', medio)
			eq('state',state1)
			eq('registerType',registerType3)
		}
		
		return receiptInstanceList
		
	}
	
	private getSalesForDisable(List medios,String sortType, String orderType ) {

		def state1 = State.findById(1)
		def state2 = State.findById(2)
		
		def registerType3 = RegisterType.findById(3);
		
		def salesSiteInstanceList = SalesSite.withCriteria {
			order(sortType != null? sortType:'receiptNumber', orderType != null?orderType:'asc')
			 if(medios != null) inList('medio', medios)
			 inList('state',[state1, state2])
			 eq('registerType',registerType3)
		}

	}

}

class PreconciliationCmd {
    
    List<String> salesSiteIds = []
    List<String> receiptIds = []

    List createList() {
        List list = new LinkedList();
        salesSiteIds.eachWithIndex {salesSiteId, i ->
            def salesSite = SalesSite.findById(salesSiteIds[i])
            def receipt = Receipt.findById(receiptIds[i])
            def salesSiteReceipt = new PreSalesSiteReceiptCmd(salesSite: salesSite, receipt: receipt)
            
            list.add(salesSiteReceipt)
        }
        return list
        
    }
}


class PreSalesSiteReceiptCmd {
    SalesSite salesSite
    Receipt receipt
}
