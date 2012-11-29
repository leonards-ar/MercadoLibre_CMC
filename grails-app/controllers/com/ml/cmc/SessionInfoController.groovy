package com.ml.cmc
import grails.converters.JSON

import java.security.Principal

import org.apache.commons.logging.LogFactory

class SessionInfoController {
	//private static final myLog = LogFactory.getLog(this)
	
    def sessionRegistry
    def springSecurityService
	def securityLockService
	
	def dateFormat = 'yyyy-MM-dd'
	def maxRecords = 50
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
	
	protected serializeReceiptData(instanceList) {
		
		def data = []
		
		instanceList.each(){
			data << ["DT_RowId":it.id.toString(),
					 "0":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
					 "1":formatNumber(number:it?.amount,format:"###,###.00"),
					 "2":it?.authorization.toString(),
					 "3":it?.cardNumber.toString(),
					 "4":it?.customerId.toString(),
					 "5":it?.documentNumber.toString(),
					 "6":it?.documentId.toString(),
					 "7":it?.id.toString(),
					 "8":it?.ro.toString(),
					 "9":it?.tid.toString(),
					 "10":it?.nsu.toString(),
					 "11":it?.shareNumber.toString(),
					 "12":it?.shareQty.toString(),
					 "13":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "14":it?.payment
					 ]
		}
		
		return data
	}
	
	protected Integer executeCommand(String command) {
		
		StringWriter stringWriterOutput = new StringWriter()
		StringWriter stringWriterError = new StringWriter()

		def batchProcess = "${command}"
		log.info("Callling batch process ${batchProcess}")

		Process job = command.execute()
		
		stringWriterOutput << job.in
		stringWriterError << job.err
		
		job.waitFor()
		
		log.info("Process \"${batchProcess}\" returned: ${job.exitValue()}")
		log.info("Process \"${batchProcess}\" stdout: ${stringWriterOutput.toString()}")
		log.info("Process \"${batchProcess}\" stderr: ${stringWriterError.toString()}")
		
		return job.exitValue()
		
	}
    
}
