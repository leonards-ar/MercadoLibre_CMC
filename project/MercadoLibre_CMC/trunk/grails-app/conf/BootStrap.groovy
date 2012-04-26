import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import com.ml.cmc.Role
import com.ml.cmc.User
import com.ml.cmc.UserRole

class BootStrap {

    def authenticationManager
    def concurrentSessionController
    def securityContextPersistenceFilter
    def authenticationProcessingFilter
    def concurrentSessionControlStrategy

    def init = { servletContext ->
        
        SpringSecurityUtils.clientRegisterFilter('concurrencyFilter', SecurityFilterPosition.CONCURRENT_SESSION_FILTER)
        authenticationProcessingFilter.sessionAuthenticationStrategy = concurrentSessionControlStrategy

        // Temporary Users
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def testUser2 = new User(username: 'jorge', enabled: true, password: '1234')

        testUser2.save(flush: true)

        UserRole.create testUser2, userRole, true

        assert User.count() == 1
        assert Role.count() == 1
        assert UserRole.count() == 1
    }
    
    def destroy = {
    }
}
