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
        
        def lock = new Lock()
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
                 List<Long> longIds = (preconciliationCmd.receiptIds instanceof String)?[preconciliationCmd.receiptIds.toLong()]:preconciliationCmd.receiptIds.collect{it.toLong()}
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
                 List<Long> longIds = (preconciliationCmd.salesSiteIds instanceof String)?[preconciliationCmd.salesSiteIds.toLong()]:preconciliationCmd.salesSiteIds.collect{it.toLong()}
                 not{inList('id', longIds)}
             }
        }
        
        render(template:"salesSiteTable", model:[salesSiteInstanceList: salesSiteInstanceList])
        
    }
    
    def group = { PreconciliationCmd preconciliationCmd ->
        
        def salesSiteInstance = SalesSite.findById(params.salesSiteId)
        def receiptInstance = Receipt.findById(params.receiptId)
        
        List preconciliation = preconciliationCmd.insertPair(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
        
        render(template:"preconciliateTable", model:[preconciliationInstancelist: preconciliation])
        
    }
    
    def save = { PreconciliationCmd preconciliationCmd ->
        
        def lot = lotGeneratorService.getLotId()
        
        List salesSiteReceiptList = preconciliationCmd.createList()
        
        salesSiteReceiptList.each{ item ->
            
            def preconciliation = new Preconciliation(sale:item.salesSite, receipt:item.receipt, 
                lot:lot, medio:item.receipt?.medio, period: item.salesSite?.period)
            
            preconciliation.save()
        }
        
        /* call datastage */
        def job = "cmd.exe /C echo El proceso se ejecuto satisfactoriamente.".execute()
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

class PreconciliationCmd {
    
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