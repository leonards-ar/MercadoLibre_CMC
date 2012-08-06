package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService
import com.ml.cmc.service.LotGeneratorService
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class PreconciliationController extends SessionInfoController{

    def securityLockService
    def lotGeneratorService
    
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
		
		if(params.country == null || params.card == null || params.site == null){
			response.setStatus(500)
			render message(code:"preconciliation.nocomboselected.error")
			return
		}
        
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
        
        def state1 = State.findById(1)
        def state2 = State.findById(2)
        def receiptInstanceList = Receipt.withCriteria {
            order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
            if(medio != null) eq('medio', medio)
            inList('state',[state1,state2])
        }

        def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
        def salesSiteInstanceList = SalesSite.withCriteria {
            order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
             if(medios != null) inList('medio', medios)
             eq('state',state1)
        }
        
        render(template: "preconciliationBody", model:[receiptInstanceList:receiptInstanceList, salesSiteInstanceList:salesSiteInstanceList])

    }

    def listReceipts = {PreconciliationCmd preconciliationCmd ->
       
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        def state1 = State.findById(1)
        def state2 = State.findById(2)
        def receiptInstanceList = Receipt.withCriteria {
            order(params.sort, params.order)
             if(medio != null) eq('medio', medio)
             inList('state',[state1,state2])
             if(preconciliationCmd.receiptIds.size() >0) {
                 List<String> longIds = (preconciliationCmd.receiptIds instanceof String)?[preconciliationCmd.receiptIds]:preconciliationCmd.receiptIds.collect{it}
                 not{inList('id', longIds)}
             }
        }
        
        render(template:"receiptTable", model:[receiptInstanceList: receiptInstanceList])
        
    }
    
    def listSalesSite = {PreconciliationCmd preconciliationCmd ->
        def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
        def state = State.findById(1)
        def salesSiteInstanceList = SalesSite.withCriteria {
            order(params.sort, params.order)
             if(medios != null) inList('medio', medios)
             eq('state',state)
             if(preconciliationCmd.salesSiteIds.size() >0) {
                 List<String> longIds = (preconciliationCmd.salesSiteIds instanceof String)?[preconciliationCmd.salesSiteIds]:preconciliationCmd.salesSiteIds.collect{it}
                 not{inList('id', longIds)}
             }
        }
        
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
        
        salesSiteReceiptList.each{ item ->
            
            def conciliation = new Conciliation(sale:item.salesSite, receipt:item.receipt, 
                lot:lot, medio:item.receipt?.medio, period: item.salesSite?.period)
            
            conciliation.save()
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
