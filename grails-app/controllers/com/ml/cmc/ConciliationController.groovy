package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService
import com.ml.cmc.service.LotGeneratorService
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class ConciliationController extends SessionInfoController{

	def securityLockService
	def lotGeneratorService
	
	def index = {
		securityLockService.unLockFunctionality(getSessionId())
		def countryList = Medio.withCriteria{
			projections{
				distinct "country"
			}
		}
		render(view:'index', model:[countryList: countryList])
		}
	
	def cards = {

		def cardsToSelect = Medio.withCriteria{
			projections{
				distinct "card"
			}
			eq("country", params._value)
		}
		
		render cardsToSelect as JSON
	}
	
	def sites = {
		
		 def sitesToSelect = Medio.withCriteria{
			projections{
				distinct "site"
			}
			eq("card", params._value)
		}
		render sitesToSelect as JSON
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
		
		def state3 = State.findById(3)
		def receiptInstanceList = Receipt.withCriteria {
			order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
			if(medio != null) eq('medio', medio)
			eq('state',state3)
		}

		def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		def salesSiteInstanceList = SalesSite.withCriteria {
			order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
			 if(medios != null) inList('medio', medios)
			 eq('state',state3)
		}
		
		render(template: "conciliationBody", model:[receiptInstanceList:receiptInstanceList, salesSiteInstanceList:salesSiteInstanceList])

	}

	def listReceipts = {ConciliationCmd conciliationCmd ->
	   
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		def state3 = State.findById(3)

		def receiptInstanceList = Receipt.withCriteria {
			order(params.sort, params.order)
			 if(medio != null) eq('medio', medio)
			 eq('state',state3)
			 if(conciliationCmd.receiptIds.size() >0) {
				 List<Long> longIds = (conciliationCmd.receiptIds instanceof String)?[conciliationCmd.receiptIds.toLong()]:conciliationCmd.receiptIds.collect{it.toLong()}
				 not{inList('id', longIds)}
			 }
		}
		
		render(template:"receiptTable", model:[receiptInstanceList: receiptInstanceList])
		
	}
	
	def listSalesSite = {ConciliationCmd conciliationCmd ->
		def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		def state = State.findById(3)
		def salesSiteInstanceList = SalesSite.withCriteria {
			order(params.sort, params.order)
			 if(medios != null) inList('medio', medios)
			 eq('state',state)
			 if(conciliationCmd.salesSiteIds.size() >0) {
				 List<Long> longIds = (conciliationCmd.salesSiteIds instanceof String)?[conciliationCmd.salesSiteIds.toLong()]:conciliationCmd.salesSiteIds.collect{it.toLong()}
				 not{inList('id', longIds)}
			 }
		}
		
		render(template:"salesSiteTable", model:[salesSiteInstanceList: salesSiteInstanceList])
		
	}
	
	def group = { ConciliationCmd conciliationCmd ->
		
        if(params.salesSiteId.size() > 1 && params.receiptId.size() > 1){
            response.setStatus(500)
            render message(code:"preconcliation.relationship.error", default:"La relacion entre los Recibos y Ventas no es correcta")
            return
        }

        LinkedList conciliation = (LinkedList)conciliationCmd.createList() 
                
        if(params.salesSiteId.size() > 1){
            def receiptInstance = Receipt.findById(params.receiptId)
            params.salesSiteId.each{ saleId ->
                def salesSiteInstance = SalesSite.findById(saleId)
                conciliation.addFirst(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
            }
        } else {
            def salesSiteInstance = SalesSite.findById(params.salesSiteId)
            params.receiptId.each{ receipt ->
                def receiptInstance = Receipt.findById(receipt)
                conciliation.addFirst(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
            } 
        }
        

		
		render(template:"conciliateTable", model:[conciliationInstancelist: conciliation])
		
	}
	
	def save = { ConciliationCmd conciliationCmd ->
		
		def lot = lotGeneratorService.getLotId()
		
		List salesSiteReceiptList = conciliationCmd.createList()
		
		salesSiteReceiptList.each{ item ->
			
			def conciliation = new Conciliation(sale:item.salesSite, receipt:item.receipt,
				lot:lot, medio:item.receipt?.medio, period:item.receipt?.period)
			
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
	
	def exit = {
		securityLockService.unLockFunctionality(getSessionId())
		redirect(controller:'home', action:'index')
	}
	

}

class ConciliationCmd {
	
	List salesSiteIds = []
	List receiptIds = []

	List createList() {
		List list = []
		salesSiteIds.eachWithIndex {salesSiteId, i ->
			def salesSite = SalesSite.findById(salesSiteIds[i])
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