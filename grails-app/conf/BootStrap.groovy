import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import com.ml.cmc.Role
import com.ml.cmc.State
import com.ml.cmc.RegisterType
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
        
        def pendienteState = new State(id:1,value:'Pendiente')
        pendienteState.id=1
        pendienteState.save(flush: true)
        def rechazadoState = new State(value:'Rechazado')
        rechazadoState.id=2
        rechazadoState.save(flush:true)
        def preConciliadoState = new State(id:3, value:'Pre-conciliado')
        preConciliadoState.id=3
        preConciliadoState.save(flush:true)
        def concliadoState = new State(id:4, value:'Concliado')
        concliadoState.id=4
        concliadoState.save(flush:true)
        def compensadoState = new State(id:5, value:'Compensado')
        compensadoState.id=5
        compensadoState.save(flush:true)
        
        def ventaType = new RegisterType(id:1, value:'Venta')
        ventaType.id=1
        ventaType.save(flush:true)
        def anticipoType = new RegisterType(id:2, value:'Anticipo')
        anticipoType.id=2
        anticipoType.save(flush:true)
        def anulacionType = new RegisterType(id:3, value: 'Anulacion')
        anulacionType.id=3
        anulacionType.save(flush:true)
        def cbksTypeType = new RegisterType(id:4, value: 'CBKS')
        cbksTypeType.id=4
        cbksTypeType.save(flush:true)
        
        assert User.count() == 1
        assert Role.count() == 1
        assert UserRole.count() == 1
        assert State.count() == 5
        assert RegisterType.count() == 4
        
        //def SalesSite = new SalesSite(id:1, )
    }
    
    def destroy = {
    }
}


