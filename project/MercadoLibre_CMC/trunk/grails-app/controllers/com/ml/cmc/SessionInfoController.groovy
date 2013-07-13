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
					 "0":it?.id.toString(),
					 "1":it?.medio.id,
					 "2":it?.state.id,
					 "3":it?.registerType,
					 "4":it?.lot,
					 "5":it?.cardNumber,
					 "6":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),//documentId.toString(),
					 "7":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "8":formatNumber(number:it?.amount,format:"###,###.00"),
					 "9":formatNumber(number:it?.shareAmount,format:"###,###.00"),
					 "10":it?.authorization?.toString(),
					 "11":it?.shareNumber?.toString(),
					 "12":it?.shareQty?.toString(),
					 "13":it?.liq?.toString(),
					 "14":it?.customerId?.toString(),
					 "15":it?.documentId?.toString(),
					 "16":it?.receiptNumber?.toString(),
					 "17":it?.tid?.toString(),
					 "18":it?.nsu?.toString(),
					 "19":it?.ro?.toString(),
					 "20":it?.store?.toString(),
					 "21":it?.cardLot?.toString(),
					 "22":it?.uniqueRo?.toString(),
					 "23":it?.payment?.toString(),
					 "24":it?.documentNumber?.toString(),
					 "25":it?.period?.toString(),
					 "26":it?.payed?.toString()
					 ]
		}
		
		return data
	}
	
	protected serializeSalesData(instanceList) {
		
		def data = []
		
		instanceList.each(){
			data << ["DT_RowId":it.id.toString(),
					 "0":it?.saleMl,
					 "1":it?.id,
					 "2":it?.medio.id.toString(),
					 "3":it?.state.id.toString(),
					 "4":it?.registerType.toString(),
					 "5":it?.lot?.toString(),
					 "6":it?.cardNumber.toString(),
					 "7":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
					 "8":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "9":formatNumber(number:it?.amount,format:"###,###.00"),
					 "10":formatNumber(number:it?.shareAmount,format:"###,###.00"),
					 "11":it?.authorization?.toString(),
					 "12":it?.shareNumber?.toString(),
					 "13":it?.shareQty?.toString(),
					 "14":it?.liq?.toString(),
					 "15":it?.customerId?.toString(),
					 "16":it?.documentId?.toString(),
					 "17":it?.receiptNumber?.toString(),
					 "18":it?.tid?.toString(),
					 "19":it?.nsu?.toString(),
					 "20":it?.ro?.toString(),
					 "21":it?.store?.toString(),
					 "22":it?.cardLot?.toString(),
					 "23":it?.uniqueRo?.toString(),
					 "24":it?.payment?.toString(),
					 "25":it?.documentNumber?.toString(),
					 "26":it?.period?.toString(),
					 "27":it?.origin?.toString(),
					 "28":it?.operation?.toString(),
					 "29":it?.sap?.toString(),
					 "30":it?.paymentReference?.toString(),
					 "31":it?.pricing?.toString(),
					 "32":it?.concPay?.toString()
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
