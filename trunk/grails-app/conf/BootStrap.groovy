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
import com.ml.cmc.Conciliated

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

        def receipt3 = new Receipt(id:3,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123132',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt4 = new Receipt(id:4,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123133',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123193,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213214',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt5 = new Receipt(id:5,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123134',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123194,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213215',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt6 = new Receipt(id:6,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123135',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123195,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213216',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt7 = new Receipt(id:7,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123136',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123196,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213217',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt8 = new Receipt(id:8,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123137',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123197,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213218',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt9 = new Receipt(id:9,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123138',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123198,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213219',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt10 = new Receipt(id:10,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123139',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123199,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213220',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt11 = new Receipt(id:11,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123140',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123200,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213221',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt12 = new Receipt(id:12,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123141',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:1231201,shareNumber:7,shareQty:7,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213222',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt13 = new Receipt(id:13,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123142',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123100,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213223',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt14 = new Receipt(id:14,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123143',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123102,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213224',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt15 = new Receipt(id:15,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123144',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123103,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213225',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt16 = new Receipt(id:16,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123145',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123104,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213226',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt17 = new Receipt(id:17,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123146',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123105,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213227',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt18 = new Receipt(id:18,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123147',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123106,shareNumber:7,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213228',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt19 = new Receipt(id:19,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123148',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123107,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213229',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt20 = new Receipt(id:20,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123149',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123108,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213230',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt21 = new Receipt(id:21,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123150',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123109,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213231',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt22 = new Receipt(id:22,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123151',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123110,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213232',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt23 = new Receipt(id:23,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123152',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123111,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213233',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt24 = new Receipt(id:24,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123153',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123112,shareNumber:7,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213234',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt25 = new Receipt(id:25,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123154',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123113,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213235',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt26 = new Receipt(id:26,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123155',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123114,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213236',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt27 = new Receipt(id:27,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123156',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123115,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213237',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        
        def receipt28 = new Receipt(id:3,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt29 = new Receipt(id:4,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123133',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123193,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213214',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt30 = new Receipt(id:5,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123134',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123194,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213215',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt31 = new Receipt(id:6,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123135',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123195,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213216',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt32 = new Receipt(id:7,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123136',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123196,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213217',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt33 = new Receipt(id:8,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123137',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123197,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213218',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt34 = new Receipt(id:9,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123138',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123198,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213219',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt35 = new Receipt(id:10,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123139',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123199,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213220',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt36 = new Receipt(id:11,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123140',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123200,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213221',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt37 = new Receipt(id:12,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123141',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:1231201,shareNumber:7,shareQty:7,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213222',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt38 = new Receipt(id:13,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123142',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123100,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213223',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt39 = new Receipt(id:14,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123143',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123102,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213224',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt40 = new Receipt(id:15,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123144',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123103,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213225',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt41 = new Receipt(id:16,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123145',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123104,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213226',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt42 = new Receipt(id:17,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123146',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123105,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213227',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt43 = new Receipt(id:18,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123147',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123106,shareNumber:7,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213228',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt44 = new Receipt(id:19,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123148',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123107,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213229',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt45 = new Receipt(id:20,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123149',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123108,shareNumber:3,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213230',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt46 = new Receipt(id:21,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123150',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123109,shareNumber:4,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213231',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt47 = new Receipt(id:22,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123151',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123110,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213232',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt48 = new Receipt(id:23,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123152',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123111,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213233',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt49 = new Receipt(id:24,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123153',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123112,shareNumber:7,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213234',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt50 = new Receipt(id:25,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123154',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123113,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213235',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt51 = new Receipt(id:26,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123155',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123114,shareNumber:5,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213236',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def receipt52 = new Receipt(id:27,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123156',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123115,shareNumber:6,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213237',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        
        def salesSite = new SalesSite(saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
            transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
            shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
            tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

		def salesSite2 = new SalesSite(saleMl:2L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',
			transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,
			shareNumber:1,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',
			tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

        def salesSite3 = new SalesSite(id:3, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123132',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite4 = new SalesSite(id:4, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123133',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite5 = new SalesSite(id:5, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123134',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite6 = new SalesSite(id:6, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123135',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite7 = new SalesSite(id:7, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123136',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite8 = new SalesSite(id:8, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123137',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite9 = new SalesSite(id:9, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123138',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite10 = new SalesSite(id:10, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123139',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite11 = new SalesSite(id:11, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123140',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite12 = new SalesSite(id:12, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123141',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite13 = new SalesSite(id:13, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123142',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite14 = new SalesSite(id:14, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123143',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite15 = new SalesSite(id:15, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123144',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite16 = new SalesSite(id:16, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123145',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite17 = new SalesSite(id:17, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123146',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite18 = new SalesSite(id:18, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123147',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite19 = new SalesSite(id:19, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123148',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite20 = new SalesSite(id:20, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123149',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite21 = new SalesSite(id:21, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123150',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite22 = new SalesSite(id:22, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123151',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite23 = new SalesSite(id:23, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123152',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite24 = new SalesSite(id:24, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123153',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite25 = new SalesSite(id:25, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123154',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite26 = new SalesSite(id:26, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123155',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite27 = new SalesSite(id:27, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123156',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite28 = new SalesSite(id:28, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123157',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite29 = new SalesSite(id:29, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123158',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite30 = new SalesSite(id:30, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123159',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite31 = new SalesSite(id:31, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123160',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite32 = new SalesSite(id:32, saleMl:1L,medio: Medio.findById(1), state:State.findById(4), registerType:RegisterType.findById(1),cardNumber:'123123161',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)

        def salesSite33 = new SalesSite(id:3, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123132',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite34 = new SalesSite(id:4, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123133',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite35 = new SalesSite(id:5, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123134',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite36 = new SalesSite(id:6, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123135',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite37 = new SalesSite(id:7, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123136',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite38 = new SalesSite(id:8, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123137',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite39 = new SalesSite(id:9, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123138',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite40 = new SalesSite(id:10, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123139',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite41 = new SalesSite(id:11, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123140',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite42 = new SalesSite(id:12, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123141',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite43 = new SalesSite(id:13, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123142',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite44 = new SalesSite(id:14, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123143',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite45 = new SalesSite(id:15, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123144',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite46 = new SalesSite(id:16, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123145',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite47 = new SalesSite(id:17, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123146',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite48 = new SalesSite(id:18, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123147',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite49 = new SalesSite(id:19, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123148',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite50 = new SalesSite(id:20, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123149',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite51 = new SalesSite(id:21, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123150',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite52 = new SalesSite(id:22, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123151',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite53 = new SalesSite(id:23, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123152',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite54 = new SalesSite(id:24, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123153',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite55 = new SalesSite(id:25, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123154',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite56 = new SalesSite(id:26, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123155',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite57 = new SalesSite(id:27, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123156',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite58 = new SalesSite(id:28, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123157',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite59 = new SalesSite(id:29, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123158',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite60 = new SalesSite(id:30, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123159',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite61 = new SalesSite(id:31, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123160',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        def salesSite62 = new SalesSite(id:32, saleMl:1L,medio: Medio.findById(1), state:State.findById(1), registerType:RegisterType.findById(1),cardNumber:'123123161',transactionDate: new Date(),paymentDate: new Date(),amount:500.00, shareAmount:0.0, authorization:123192,shareNumber:2,shareQty:6,liq:'111',customerId:'1111111',documentId:'123121', receiptNumber:'12313213213',tid:1l, nsu:1l, ro:1l, store:1l,cardLot:'20034',uniqueRo:'11111',documentNumber:'20223410313',lot:1L, period:period)
        
        receipt.id=1
        receipt2.id=2
        receipt3.id=3
        receipt4.id=4
        receipt5.id=5
        receipt6.id=6
        receipt7.id=7
        receipt8.id=8
        receipt9.id=9
        receipt10.id=10
        receipt11.id=11
        receipt12.id=12
        receipt13.id=13
        receipt14.id=14
        receipt15.id=15
        receipt16.id=16
        receipt17.id=17
        receipt18.id=18
        receipt19.id=19
        receipt20.id=20
        receipt21.id=21
        receipt22.id=22
        receipt23.id=23
        receipt24.id=24
        receipt25.id=25
        receipt26.id=26
        receipt27.id=27
        
        receipt28.id=28
        receipt29.id=29
        receipt30.id=30
        receipt31.id=31
        receipt32.id=32
        receipt33.id=33
        receipt34.id=34
        receipt35.id=35
        receipt36.id=36
        receipt37.id=37
        receipt38.id=38
        receipt39.id=39
        receipt40.id=40
        receipt41.id=41
        receipt42.id=42
        receipt43.id=43
        receipt44.id=44
        receipt45.id=45
        receipt46.id=46
        receipt47.id=47
        receipt48.id=48
        receipt49.id=49
        receipt50.id=50
        receipt51.id=51
        receipt52.id=52
        
        salesSite.id=1
		salesSite2.id=2
        salesSite3.id=3
        salesSite4.id=4
        salesSite5.id=5
        salesSite6.id=6
        salesSite7.id=7
        salesSite8.id=8
        salesSite9.id=9
        salesSite10.id=10
        salesSite11.id=11
        salesSite12.id=12
        salesSite13.id=13
        salesSite14.id=14
        salesSite15.id=15
        salesSite16.id=16
        salesSite17.id=17
        salesSite18.id=18
        salesSite19.id=19
        salesSite20.id=20
        salesSite21.id=21
        salesSite22.id=22
        salesSite23.id=23
        salesSite24.id=24
        salesSite25.id=25
        salesSite26.id=26
        salesSite27.id=27
        salesSite28.id=28
        salesSite29.id=29
        salesSite30.id=30
        salesSite31.id=31
        salesSite32.id=32

        salesSite33.id=33
        salesSite34.id=34
        salesSite35.id=35
        salesSite36.id=36
        salesSite37.id=37
        salesSite38.id=38
        salesSite39.id=39
        salesSite40.id=40
        salesSite41.id=41
        salesSite42.id=42
        salesSite43.id=43
        salesSite44.id=44
        salesSite45.id=45
        salesSite46.id=46
        salesSite47.id=47
        salesSite48.id=48
        salesSite49.id=49
        salesSite50.id=50
        salesSite51.id=51
        salesSite52.id=52
        salesSite53.id=53
        salesSite54.id=54
        salesSite55.id=55
        salesSite56.id=56
        salesSite57.id=57
        salesSite58.id=58
        salesSite59.id=59
        salesSite60.id=60
        salesSite61.id=61
        salesSite62.id=62

        receipt.save(flush:true)
        receipt2.save(flush:true)
        receipt3.save(flush:true)
        receipt4.save(flush:true)
        receipt5.save(flush:true)
        receipt6.save(flush:true)
        receipt7.save(flush:true)
        receipt8.save(flush:true)
        receipt9.save(flush:true)
        receipt10.save(flush:true)
        receipt11.save(flush:true)
        receipt12.save(flush:true)
        receipt13.save(flush:true)
        receipt14.save(flush:true)
        receipt15.save(flush:true)
        receipt16.save(flush:true)
        receipt17.save(flush:true)
        receipt18.save(flush:true)
        receipt19.save(flush:true)
        receipt20.save(flush:true)
        receipt21.save(flush:true)
        receipt22.save(flush:true)
        receipt23.save(flush:true)
        receipt24.save(flush:true)
        receipt25.save(flush:true)
        receipt26.save(flush:true)
        receipt27.save(flush:true)
        receipt28.save(flush:true)
        receipt29.save(flush:true)
        receipt30.save(flush:true)
        receipt31.save(flush:true)
        receipt32.save(flush:true)
        receipt33.save(flush:true)
        receipt34.save(flush:true)
        receipt35.save(flush:true)
        receipt36.save(flush:true)
        receipt37.save(flush:true)
        receipt38.save(flush:true)
        receipt39.save(flush:true)
        receipt40.save(flush:true)
        receipt41.save(flush:true)
        receipt42.save(flush:true)
        receipt43.save(flush:true)
        receipt44.save(flush:true)
        receipt45.save(flush:true)
        receipt46.save(flush:true)
        receipt47.save(flush:true)
        receipt48.save(flush:true)
        receipt49.save(flush:true)
        receipt50.save(flush:true)
        receipt51.save(flush:true)
        receipt52.save(flush:true)

        salesSite.save(flush:true)
        salesSite2.save(flush:true)
        salesSite3.save(flush:true)
        salesSite4.save(flush:true)
        salesSite5.save(flush:true)
        salesSite6.save(flush:true)
        salesSite7.save(flush:true)
        salesSite8.save(flush:true)
        salesSite9.save(flush:true)
        salesSite10.save(flush:true)
        salesSite11.save(flush:true)
        salesSite12.save(flush:true)
        salesSite13.save(flush:true)
        salesSite14.save(flush:true)
        salesSite15.save(flush:true)
        salesSite16.save(flush:true)
        salesSite17.save(flush:true)
        salesSite18.save(flush:true)
        salesSite19.save(flush:true)
        salesSite20.save(flush:true)
        salesSite21.save(flush:true)
        salesSite22.save(flush:true)
        salesSite23.save(flush:true)
        salesSite24.save(flush:true)
        salesSite25.save(flush:true)
        salesSite26.save(flush:true)
        salesSite27.save(flush:true)
        salesSite28.save(flush:true)
        salesSite29.save(flush:true)
        salesSite30.save(flush:true)
        salesSite31.save(flush:true)
        salesSite32.save(flush:true)
        salesSite33.save(flush:true)
        salesSite34.save(flush:true)
        salesSite35.save(flush:true)
        salesSite36.save(flush:true)
        salesSite37.save(flush:true)
        salesSite38.save(flush:true)
        salesSite39.save(flush:true)
        salesSite40.save(flush:true)
        salesSite41.save(flush:true)
        salesSite42.save(flush:true)
        salesSite43.save(flush:true)
        salesSite44.save(flush:true)
        salesSite45.save(flush:true)
        salesSite46.save(flush:true)
        salesSite47.save(flush:true)
        salesSite48.save(flush:true)
        salesSite49.save(flush:true)
        salesSite50.save(flush:true)
        salesSite51.save(flush:true)
        salesSite52.save(flush:true)
        salesSite53.save(flush:true)
        salesSite54.save(flush:true)
        salesSite55.save(flush:true)
        salesSite56.save(flush:true)
        salesSite57.save(flush:true)
        salesSite58.save(flush:true)
        salesSite59.save(flush:true)
        salesSite60.save(flush:true)
        salesSite61.save(flush:true)
        salesSite62.save(flush:true)

        def conciliated1 = new Conciliated(receipt:Receipt.findById(3), sale:SalesSite.findById(3), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated2 = new Conciliated(receipt:Receipt.findById(4), sale:SalesSite.findById(4), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated3 = new Conciliated(receipt:Receipt.findById(5), sale:SalesSite.findById(5), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated4 = new Conciliated(receipt:Receipt.findById(6), sale:SalesSite.findById(6), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated5 = new Conciliated(receipt:Receipt.findById(7), sale:SalesSite.findById(7), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated6 = new Conciliated(receipt:Receipt.findById(8), sale:SalesSite.findById(8), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated7 = new Conciliated(receipt:Receipt.findById(9), sale:SalesSite.findById(9), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))   
        def conciliated8 = new Conciliated(receipt:Receipt.findById(10), sale:SalesSite.findById(10), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1)) 
        def conciliated9 = new Conciliated(receipt:Receipt.findById(11), sale:SalesSite.findById(11), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1)) 
        def conciliated10 = new Conciliated(receipt:Receipt.findById(12), sale:SalesSite.findById(12), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated11 = new Conciliated(receipt:Receipt.findById(13), sale:SalesSite.findById(13), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated12 = new Conciliated(receipt:Receipt.findById(14), sale:SalesSite.findById(14), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated13 = new Conciliated(receipt:Receipt.findById(15), sale:SalesSite.findById(15), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated14 = new Conciliated(receipt:Receipt.findById(16), sale:SalesSite.findById(16), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated15 = new Conciliated(receipt:Receipt.findById(17), sale:SalesSite.findById(17), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated16 = new Conciliated(receipt:Receipt.findById(18), sale:SalesSite.findById(18), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated17 = new Conciliated(receipt:Receipt.findById(19), sale:SalesSite.findById(19), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated18 = new Conciliated(receipt:Receipt.findById(20), sale:SalesSite.findById(20), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated19 = new Conciliated(receipt:Receipt.findById(21), sale:SalesSite.findById(21), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated20 = new Conciliated(receipt:Receipt.findById(22), sale:SalesSite.findById(22), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        def conciliated21 = new Conciliated(receipt:Receipt.findById(23), sale:SalesSite.findById(23), lot:1L, medio:Medio.findById(1), period:period, registerType:RegisterType.findById(1))
        
        conciliated1.id=1
        conciliated2.id=2
        conciliated3.id=3
        conciliated4.id=4
        conciliated5.id=5
        conciliated6.id=6
        conciliated7.id=7
        conciliated8.id=8
        conciliated9.id=9
        conciliated10.id=10
        conciliated11.id=11
        conciliated12.id=12
        conciliated13.id=13
        conciliated14.id=14
        conciliated15.id=15
        conciliated16.id=16
        conciliated17.id=17
        conciliated18.id=18
        conciliated19.id=19
        conciliated20.id=20
        conciliated21.id=21
        
        conciliated1.save(flush:true)
        conciliated2.save(flush:true)
        conciliated3.save(flush:true)
        conciliated4.save(flush:true)
        conciliated5.save(flush:true)
        conciliated6.save(flush:true)
        conciliated7.save(flush:true)
        conciliated8.save(flush:true)
        conciliated9.save(flush:true)
        conciliated10.save(flush:true)
        conciliated11.save(flush:true)
        conciliated12.save(flush:true)
        conciliated13.save(flush:true)
        conciliated14.save(flush:true)
        conciliated15.save(flush:true)
        conciliated16.save(flush:true)
        conciliated17.save(flush:true)
        conciliated18.save(flush:true)
        conciliated19.save(flush:true)
        conciliated20.save(flush:true)
        conciliated21.save(flush:true)
        
        println conciliated1.errors
        assert conciliated1.hasErrors() == false
        
		//def auditLog = new AuditLog(date: new Date(), time:'11:00:00', user:'jorge', auditLogType:'Conciliacion Manual',
		//	medio:Medio.findById(1), description:'no hay seguridad');
		
		
		//auditLog.id = 1
		//auditLog.save(flush:true)
		
        assert receipt.hasErrors() == false
        assert salesSite.hasErrors() == false
        assert User.count() == 2
        assert Role.count() == 1
        assert UserRole.count() == 2
        assert State.count() == 5
        assert RegisterType.count() == 4
        //assert Receipt.count() == 2
        
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



