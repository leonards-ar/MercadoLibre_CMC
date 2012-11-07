package com.ml.cmc
import java.security.Principal
import org.springframework.security.core.session.SessionInformation
import grails.converters.JSON

class SessionInfoController {

    def sessionRegistry
    def springSecurityService
	def securityLockService

    protected String getSessionId() {
        
        return org.springframework.web.context.request.RequestContextHolder.getRequestAttributes()?.getSessionId()
        
    }
    
    protected String getUsername() {
        
        def principal = springSecurityService.principal
        return principal.username
        
    }
    
    protected Principal getPrincipal() {
        
        return springSecurityService.principal
        
    }
    
    protected Lock getLocker() {
        
        def locker = new Lock()
        locker.sessionId = getSessionId()
        locker.username = getUsername()
        locker.function = 'pre_conciliate'
    
        return locker;
        
    }

	def cards = {
		
		def cardsToSelect = Medio.withCriteria{
			projections{
				distinct "card"
			}
			eq("country", params._value)
			order("card")
		}
		
		render cardsToSelect as JSON
	}
	
	def sites = {
		
		 def sitesToSelect = Medio.withCriteria{
			projections{
				distinct "site"
			}
			eq("card", params._value)
			order("site")
		}
		render sitesToSelect as JSON
	}
	
	def periods = {
		if(params._value == '---' || params.country == null || params.card == null) return
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		def periodList = AccountantPeriod.findAll("from AccountantPeriod a where a.medio= :medio and a.status= :status ", [medio: medio, status:'ACTIVO'])
		def periodKey = []
		periodList.each { item ->
			periodKey.add([item.id, item.toString()])
			
		}
		render periodKey as JSON
		
	}
		
	def exit = {
		securityLockService.unLockFunctionality(getSessionId())
		redirect(controller:'home', action:'index')
	}
    
}
