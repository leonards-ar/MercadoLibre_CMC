package com.ml.cmc
import java.security.Principal
import org.springframework.security.core.session.SessionInformation

class SessionInfoController {

    def sessionRegistry
    def springSecurityService

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
    
    
}
