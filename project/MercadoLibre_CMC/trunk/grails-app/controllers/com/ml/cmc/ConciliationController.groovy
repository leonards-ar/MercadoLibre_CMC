package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService
import com.ml.cmc.service.LotGeneratorService
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class ConciliationController extends SessionInfoController{

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
			render message(code:"conciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
			return
		}

		try{
			securityLockService.lockFunctionality(getUsername(), Constant.FUNC_CONCILIATE, getSessionId(), medio)
		}catch (SecLockException e) {
		   def error = message(code:"conciliation.security.error" ,default:"Error",args:[e.invalidObject?.username, medio])
		   response.setStatus(500)
		   render error
		   return
		} catch (Exception e){
			response.setStatus(500)
			render e.message
			return
		}
		
		render(template: "conciliationBody")

	}

	def listReceipts = {
        def responseMap = [:]
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0
        
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		def state3 = State.findById(3)

        def criteria = Receipt.createCriteria()
		def receiptInstanceList = criteria.list(max:max, offset:offset){

			 if(medio != null) eq('medio', medio)
			 eq('state',state3)
             eq('period', AccountantPeriod.findById(params.period))

		}
        
        responseMap.aaData = serializeReceiptData(receiptInstanceList)
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = receiptInstanceList.totalCount
        responseMap.iTotalDisplayRecords = receiptInstanceList.totalCount
		
		render responseMap as JSON
		
	}
	
	def listSalesSite = {
        def responseMap = [:]
        
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0
        
		def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		def state = State.findById(3)
        def criteria = SalesSite.createCriteria()
		def salesSiteInstanceList = criteria.list(max:max, offset:offset) {
			
			 if(medios != null) inList('medio', medios)
			 eq('state',state)
             eq('period', AccountantPeriod.findById(params.period))
			 //if(params.aSalesList && params.aSalesList.size() > 0) {
			//	 List<Long> longIds = (params.aSalesList instanceof String)?[params.aSalesList.toLong()]:params.aSalesList.collect{it.toLong()}
			//	 not{inList('id', longIds)}
			// }
		}
        
        responseMap.aaData = serializeReceiptData(salesSiteInstanceList)
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = salesSiteInstanceList.totalCount
        responseMap.iTotalDisplayRecords = salesSiteInstanceList.totalCount
        
        render responseMap as JSON
		
		
	}
	
	def group = { ConciliationCmd conciliationCmd ->
		
        if(!params.salesSiteId instanceof String && params.salesSiteId.size() > 1 && !params.receiptId instanceof String && params.receiptId.size() > 1){
            response.setStatus(500)
            render message(code:"preconcliation.relationship.error", default:"La relacion entre los Recibos y Ventas no es correcta")
            return
        }

        LinkedList conciliation = (LinkedList)conciliationCmd.createList() 
                
        if(!params.salesSiteId instanceof String && params.salesSiteId.size() > 1){
            def receiptInstance = Receipt.findById(params.receiptId)
            params.salesSiteId.each{ saleId ->
                def salesSiteInstance = SalesSite.findById(saleId)
                conciliation.addFirst(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
            }
        } else {
            def salesSiteInstance = SalesSite.findById(params.salesSiteId)
			if(params.receiptId instanceof String){
				def receiptInstance = Receipt.findById(params.receiptId)
				conciliation.addFirst(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
			} else { 
	            params.receiptId.each{ receipt ->
	                def receiptInstance = Receipt.findById(receipt)
	                conciliation.addFirst(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
	            } 
			}
        }
        

		
		render(template:"conciliateTable", model:[conciliationInstancelist: conciliation])
		
	}
	
	def save = { ConciliationCmd conciliationCmd ->
		
		def lot = lotGeneratorService.getLotId()
		
		List salesSiteReceiptList = conciliationCmd.createList()
		
		Conciliation.withTransaction{
			salesSiteReceiptList.each{ item ->
				
				def conciliation = new Conciliation(sale:item.salesSite, receipt:item.receipt,
					lot:lot, medio:item.receipt?.medio, period:item.receipt?.period, registerType:item.receipt?.registerType)
				
				conciliation.save()
			}
		}
		
		sessionFactory.getCurrentSession().clear();
		
		/* call datastage */
		def username = getUsername()
		def job = ["/datastage/ConcManual.sh", username, lot].execute()
		job.waitFor()
		if(job.exitValue()){
			response.setStatus(500)
			render job.err.text
		}
		render job.text
		 
	}
    
    private serializeReceiptData(instanceList) {
        
        def data = []
        
        instanceList.each(){
            data << ["DT_RowId":it.id.toString(),
                     "0":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
                     "1":it?.amount.toString(),
                     "2":it?.authorization.toString(),
                     "3":it?.cardNumber.toString(),
                     "4":it?.customerId.toString(),
                     "5":it?.documentNumber.toString(),
                     "6":it?.documentId.toString(),
                     "7":it?.id.toString(),
                     "8":it?.ro.toString(),
                     "9":it?.tid.toString(),
                     "10":it?.nsu.toString(),
                     "11":it?.shareNumber.toString(),
                     "12":it?.shareQty.toString(),
                     "13":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
                     "14":it?.payment
                     ]
        }
        
        return data
    }
	
}

class ConciliationCmd {
	
	List salesSiteIds = []
	List receiptIds = []

	List createList() {
		List list = []
		salesSiteIds.eachWithIndex {salesSiteId, i ->
			def salesSite = SalesSite.findById(salesSiteId)
			def receipt = Receipt.findById(receiptIds[i])
			def salesSiteReceipt = new SalesSiteReceiptCmd(salesSite: salesSite, receipt: receipt)
			
			list.add(salesSiteReceipt)
		}
		return list
		
	}
	List insertPair(SalesSiteReceiptCmd salesSiteReceipt) {
		List list = []
		list.add(salesSiteReceipt)
		salesSiteIds.eachWithIndex {salesSiteId, i ->
			def salesSiteInstance = SalesSite.findById(salesSiteId)
			def receiptInstance = Receipt.findById(reciptids[i])
			def salesSiteRecepit = new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt: receiptInstance)
			
			list.add(salesSiteReceipt)
		}
		return list
	}
	
}

class SalesSiteReceiptCmd {
	SalesSite salesSite
	Receipt receipt
}