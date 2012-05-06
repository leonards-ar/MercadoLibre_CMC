package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class PreconciliationController extends SessionInfoController{

    def securityLockService
    
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
            //throw new Exception("No Medio found for ${params.country} - ${params.card} - ${params.site} ")
            response.setStatus(500)
            render "No Medio found for ${params.country} - ${params.card} - ${params.site}"
            return
        }

        try{
            securityLockService.lockFunctionality(getUsername(), Constant.FUNC_PRECONCILIATE, getSessionId(), medio)
        }catch (SecLockException e) {
           def error = message(code:"preconciliation.security.error" ,default:"Error",args:[e.invalidObject?.username,e.invalidObject?.function])           
           response.setStatus(500)
           render error
           return
        } catch (Exception e){
            response.setStatus(500)
            render e.toString()
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

    def listReceipts = {
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        def state1 = State.findById(1)
        def state2 = State.findById(2)
        def receiptInstanceList = Receipt.withCriteria {
            order(params.sort, params.order)
             if(medio != null) eq('medio', medio)
             inList('state',[state1,state2])
        }
        
        render(template:"receiptTable", model:[receiptInstanceList: receiptInstanceList])
        
    }
    
    def listSalesSite = {
        def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
        def state = State.findById(1)
        def salesSiteInstanceList = SalesSite.withCriteria {
            order(params.sort, params.order)
             if(medios != null) inList('medio', medios)
             eq('state',state)
        }
        
        render(template:"salesSiteTable", model:[salesSiteInstanceList: salesSiteInstanceList])
        
    }
    
    def group = { PreconciliationCmd preconciliationCmd ->
        
        
        def salesSiteInstance = SalesSite.findById(params.salesId)
        def receiptInstance = Receipt.findById(params.receiptId)
        
        List preconciliation = preconciliationCmd.insertPair(new SalesSiteReceiptCmd(salesSite: salesSiteInstance, receipt:receiptInstance ))
        
        render(template:"preconciliateTable", model:[preconciliationInstancelist: preconciliation])
        
    }
    

}

class PreconciliationCmd {
    
    List salesSiteIds = []
    List receiptIds = []

    List createList() {
        List list = []
        salesSiteIds.eachWithIndex {salesSiteId, i ->
            def salesSite = SalesSite.findById(salesSiteId)
            def receipt = Receipt.findById(reciptids[i])
            def salesSiteRecepit = new SalesSiteReceiptCmd(salesSite: salesSite, receipt: receipt)
            
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