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
		println params.id
		println params.name
		println params.value
		println params.country
		
        def cardsToSelect = Medio.withCriteria{
			projections{
				distinct "card"
			}
			eq("country", params.value)
		}
		
        render cardsToSelect as JSON
    }
    
    def sites = {
         def sitesToSelect = Medio.withCriteria{
			projections{
				distinct "card"
			}
			eq("country", params.card)
			eq("card", params.card)
		}   
        render sitesToSelect as JSON
    }
    
    def lock = {
        
        //sercurityLockSerivce.lock()
        
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
        medio = new Medio(country: params.country, card: params.card, site:params.site, medioId:1, bank:"Santander", store:"1111111")
        medio.save(flush:true)
        
        def lock = new Lock()
        if(medio == null) {
            //throw new Exception("No Medio found for ${params.country} - ${params.card} - ${params.site} ")
            def error = [error: "No Medio found for ${params.country} - ${params.card} - ${params.site}"]
            render error  as JSON
            return
        }

        try{
            securityLockService.lockFunctionality(getUsername(), Constant.FUNC_PRECONCILIATE, getSessionId(), medio)
        }catch (SecLockException e) {
           def error = [error:message(code:"preconciliation.security.error" ,default:"Error",args:[e.invalidObject?.username,e.invalidObject?.function])]
           render error as JSON
           return
        } catch (Exception e){
            render error.message as JSON
            return
        }
        
        render(template: "preconciliationBody")

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
        
        render(template:"receiptTable", receiptInstanceList: receiptInstanceList)
        
    }
    
    def listSalesSite = {
        def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
        def state = State.findById(1)
        def salesSiteInstanceList = SalesSite.withCriteria {
            order(params.sort, params.order)
             if(medios != null) inList('medio', medios)
             eq('state',state)
        }
        
        render(template:"salesSiteTable", salesSiteInstanceList: salesSiteInstanceList)
        
    }
    

}
