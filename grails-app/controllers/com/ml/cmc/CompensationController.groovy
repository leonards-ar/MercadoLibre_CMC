package com.ml.cmc

import com.ml.cmc.constants.Constant 
import com.ml.cmc.exception.SecLockException 

class CompensationController extends SessionInfoController {

    def index = {
		securityLockService.unLockFunctionality(getSessionId())
		def countryList = Medio.withCriteria{
			projections{
				distinct "country"
			}
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
		
		def lock = new Lock()
		if(medio == null) {
			response.setStatus(500)
			render message(code:"preconciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
			return
		}

		try{
			securityLockService.lockFunctionality(getUsername(), Constant.FUNC_COMPENSATE, getSessionId(), medio)
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
		

		
		
		
		render(template: "compensationBody")

	}
	
	def listReceipts = {
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])

				def receiptInstanceList = Receipt.withCriteria {
			order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
			eq('medio', medio)
			eq('state',state1)
		}
		
		render(template:"receiptTable", model:[receiptInstanceList: receiptInstanceList])

	}
	
	def listSalesSite = {
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
		def salesSiteInstanceList = SalesSite.withCriteria {
			order(params.sort != null? params.sort:'receiptNumber', params.order != null?params.order:'asc')
			 eq('medio', medio)
			 eq('state',state1)
		}
		
		render(template:"salesSiteTable", model:[salesSiteInstanceList: salesSiteInstanceList])

	}
		 
}
