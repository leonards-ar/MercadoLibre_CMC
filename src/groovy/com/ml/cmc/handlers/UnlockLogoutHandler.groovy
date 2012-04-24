package com.ml.cmc.handlers


import javax.servlet.http.*
import org.springframework.context.ApplicationListener
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import com.ml.cmc.Lock
import com.ml.cmc.service.SecurityLockService

class UnlockLogoutHandler implements LogoutHandler, HttpSessionListener{

    SecurityLockService lockService = new SecurityLockService()
    
    void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        authentication.with {
            lockService.unLockFunctionality(details.sessionId)
        }
    }
    
    public void sessionCreated(HttpSessionEvent sessionEvent) {
    
    }
    
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {

        HttpSession mySession = sessionEvent.getSession()
        
        lockService.unLockFunctionality(mySession.getId())
        
        System.out.println("sessionDestroyed - ${mySession.id}");
      }
    
    private removeLocker(String sessionId) {

        Lock locker = Lock.findBySessionId(sessionId)
        if(locker != null){
            locker.delete(flush:true)
        }
        
    }
}
