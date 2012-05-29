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
		
	def exit = {
		securityLockService.unLockFunctionality(getSessionId())
		redirect(controller:'home', action:'index')
	}
    
}
