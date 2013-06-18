import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import com.ml.cmc.Lock

class BootStrap {

    def authenticationManager
    def concurrentSessionController
    def securityContextPersistenceFilter
    def authenticationProcessingFilter
    def concurrentSessionControlStrategy

    def init = { servletContext ->
        
        SpringSecurityUtils.clientRegisterFilter('concurrencyFilter', SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
        authenticationProcessingFilter.sessionAuthenticationStrategy = concurrentSessionControlStrategy

		//delete locks.
		if(Lock.count() > 0){
			
			def locks = Lock.findAll()
			locks.each{lock ->
				lock.delete(flush:true)
			}
			
		}
		
    }
    
    def destroy = {
		
		def locks = Lock.findAll()
		locks.each{lock ->
			lock.delete(flush:true)
		}
    }
}



