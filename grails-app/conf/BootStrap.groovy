import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.util.GrailsUtil
import com.ml.cmc.AccountantPeriod
import com.ml.cmc.AuditLog
import com.ml.cmc.Lock
import com.ml.cmc.Medio
import com.ml.cmc.Receipt
import com.ml.cmc.Role
import com.ml.cmc.SalesSite
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

		//delete locks.
		if(Lock.count() > 0){
			
			def locks = Lock.findAll()
			locks.each{lock ->
				lock.delete(flush:true)
			}
			
		}
		if(!GrailsUtil.getEnvironment().equals('mercadolibre') ){
        // Temporary Users
        def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

        def testUser = new User(username:'elvis',enabled: true,password: '1234')
        def testUser2 = new User(username:'jorge',enabled:true,password: '1234')

        testUser.save(flush:true)
        testUser2.save(flush: true)

        UserRole.create testUser2, userRole, true
        UserRole.create testUser, userRole, true

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
		
		def medio = new Medio(country:'ARG',bank:'SANTANDER' ,card:'VISA'     , store:'1113255' , site:'MP')
		medio.id=1
		medio.save(flush:true)
		medio = new Medio(country:'ARG',bank:'HSBC'      ,card:'AMEX'     , store:'1113266' , site:'MP')
		medio.id=2
		medio.save(flush:true)
		medio =new Medio(country:'ARG',bank:'ITAU'      ,card:'NARANJA'  , store:'1113221' , site:'MP')
		medio.id=3
		medio.save(flush:true)
		medio =new Medio(country:'BRA',bank:'SANTANDER' ,card:'VISA'     , store:'1113243' , site:'MP')
		medio.id=4
		medio.save(flush:true)
		medio =new Medio(country:'BRA',bank:'ITAU'      ,card:'REDECARD' , store:'1113265' , site:'MP')
		medio.id=5
		medio.save(flush:true)
		medio =new Medio(country:'BRA',bank:'BRADESCO'  ,card:'HIPERCARD', store:'1113287' , site:'ML')
		medio.id=6
		medio.save(flush:true)
		medio =new Medio(country:'CHI',bank:'SANTANDER' ,card:'VISA'     , store:'1113299' , site:'MP')
		medio.id=7
		medio.save(flush:true)
		medio =new Medio(country:'CHI',bank:'FRANCES'   ,card:'AMEX'     , store:'1113211' , site:'MP')
		medio.id=8
		medio.save(flush:true)
		medio =new Medio(country:'MEX',bank:'SANTANDER' ,card:'VISA'     , store:'1113223' , site:'ML')
		medio.id=9
		medio.save(flush:true)
		medio =new Medio(country:'MEX',bank:'BITAL'     ,card:'AMEX'     , store:'1113254' , site:'MP')
		medio.id=10
		medio.save(flush:true)
		medio =new Medio(country:'VEN',bank:'SANTANDER' ,card:'VISA'     , store:'1113278' , site:'ML')
		medio.id=11
		medio.save(flush:true)
		
		def period = new AccountantPeriod(startDate:new Date(), endDate: new Date(), status:'ACTIVO')
		period.id = 1L
		period.save(flush:true)
		
		
		
        def receipt = new Receipt(medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
            transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
            shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
            tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

		def receipt2 = new Receipt(medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
			transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
			shareNumber:1,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
			tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

        def salesSite = new SalesSite(saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
            transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
            shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
            tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

		def salesSite2 = new SalesSite(saleMl:2L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
			transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
			shareNumber:1,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
			tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)


        receipt.id="1";
        salesSite.id="1";

		receipt2.id="2";
		salesSite2.id="2";

		
        receipt.save(flush:true)
        salesSite.save(flush:true)

		receipt2.save(flush:true)
		salesSite2.save(flush:true)

		
		def auditLog = new AuditLog(date: new Date(), time:'11:00:00', user:'jorge', auditLogType:'Conciliacion Manual',
			medio:Medio.findById(1), description:'no hay seguridad');
		
		
		auditLog.id = 1
		auditLog.save(flush:true)
		
        assert receipt.hasErrors() == false
        assert salesSite.hasErrors() == false
        assert User.count() == 2
        assert Role.count() == 1
        assert UserRole.count() == 2
        assert State.count() == 5
        assert RegisterType.count() == 4
        assert Receipt.count() == 2
        
      } else {

		  // Temporary Users
		  if(Role.count() == 0) {	  
		  
			  def userRole = new Role(authority: 'ROLE_USER').save(flush: true)
		  
		  }
		  
		  if(User.count() == 0) {
	
			  def testUser = new User(username:'elvis',enabled: true,password: '1234')
			  def testUser2 = new User(username:'jorge',enabled:true,password: '1234')
		
			  testUser.save(flush:true)
			  testUser2.save(flush: true)
		  }
		  
		  if(UserRole.count() == 0) {
			  def userRole = Role.findByAuthority('ROLE_USER')
			  def testUser2 = User.findByUsername('elvis');
			  def testUser = User.findByUsername('jorge');
			  
			  UserRole.create testUser2, userRole, true
			  UserRole.create testUser, userRole, true
			  
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


