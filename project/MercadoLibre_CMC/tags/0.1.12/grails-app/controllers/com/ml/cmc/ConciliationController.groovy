package com.ml.cmc
import grails.converters.JSON
import grails.util.GrailsUtil

import org.apache.commons.logging.LogFactory
import org.apache.jasper.compiler.Node.ParamsAction;

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class ConciliationController extends SessionInfoController{
	private static final log = LogFactory.getLog(this)
	
	def securityLockService
	def lotGeneratorService
	def sessionFactory

	def colNames = ["transactionDate","amount","authorization","cardNumber","customerId","documentNumber",
		"documentId","id","ro","tid","nsu","shareNumber","shareQty","paymentDate","payment"]

	def salesColNames = ["saleMl","id","medio","state","registerType","lot","cardNumber","transactionDate",
		"paymentDate","amount","shareAmount","authorization","shareNumber","shareQty","liq",
		"customerId","documentId","receiptNumber","tid","nsu","ro","store","cardLot","uniqueRo",
		"payment","documentNumber","period","origin","operation","sap","paymentReference","pricing",
		"concPay"]
	
	def receiptColNames = ["id","medio","state","registerType","lot","cardNumber","transactionDate",
		"paymentDate","amount","shareAmount","authorization","shareNumber","shareQty","liq",
		"customerId","documentId","receiptNumber","tid","nsu","ro","store","cardLot","uniqueRo","payment",
		"documentNumber","period","payed"]
	def index = {
		securityLockService.unLockFunctionality(getSessionId())
		def countryList = Medio.withCriteria{
			projections{
				distinct "country"
			}
			order("country")
		}
       
		render(view:'index', model:[countryList: countryList])
	}
	
	def periods = {
		if(params._value == '---' || params.country == null || params.card == null) return
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		def period = AccountantPeriod.find("from AccountantPeriod a where a.medio= :medio and a.status= :status ", [medio: medio, status:'ACTIVO'])
		
		def data = [minDate:period?.startDate, maxDate:period?.endDate] 
		render data as JSON
		
	}
	
	def lock = {
		
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
		if(medio == null) {
			response.setStatus(500)
			render message(code:"conciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
			return
		}

		try{
			securityLockService.lockFunctionality(getUsername(), Constant.FUNC_CONCILIATE, getSessionId(), medio)
		}catch (SecLockException e) {
		   def error = message(code:"conciliation.security.error" ,default:"Error",args:[e.invalidObject?.username, medio])
		   response.setStatus(500)
		   render error
		   return
		} catch (Exception e){
			response.setStatus(500)
			render e.message
			return
		}
		
		render(template: "conciliationBody")

	}

	def listReceipts = {
        def responseMap = [:]
        def max = params.iDisplayLength?params.iDisplayLength:maxRecords
        def offset = params.iDisplayStart?params.iDisplayStart:0
		
		def colIdx = Integer.parseInt(params.iSortCol_0)
		def colName = receiptColNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		def state3 = State.findById(3)

		def accountDate = new Date().parse(dateFormat,params.period)
		
        def criteria = Receipt.createCriteria()
		def receiptInstanceList = criteria.list(max:max, offset:offset){
			 order(colName, sortDir)
			 if(medio != null) eq('medio', medio)
			 eq('state',state3)
			 eq('payed','OK')
			 
			 if(params.selectedList.length() > 0) {
				 def ids = params.selectedList.split(",")
				 not{inList('id', ids)}
			  }
			 if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
				 def fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
				 def toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
				 between('transactionDate', fromTransDate, toTransDate)
			 } //else {
			 	//le('transactionDate', accountDate)
			 //}
			 if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
				 def fromPaymtDate = new Date().parse(dateFormat, params.fromReceiptPaymtDate)
				 def toPaymtDate = new Date().parse(dateFormat, params.toReceiptPaymtDate)
				 between('paymentDate', fromPaymtDate, toPaymtDate)
			 } else {
			 	le('paymentDate', accountDate)
			 }
			 if(params.minReceiptAmount != null){
				 ge('amount', params.minReceiptAmount?.toDouble())
			 }
			 if(params.maxReceiptAmount != null){
				 le('amount', params.maxReceiptAmount?.toDouble())
			 }
			 

		}
        
        responseMap.aaData = serializeReceiptData(receiptInstanceList)
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = receiptInstanceList.totalCount
        responseMap.iTotalDisplayRecords = receiptInstanceList.totalCount
		
		render responseMap as JSON
		
	}
	
	def listSalesSite = {
        def responseMap = [:]
        
        def max = params.iDisplayLength?params.iDisplayLength:maxRecords
        def offset = params.iDisplayStart?params.iDisplayStart:0
		def colIdx = Integer.parseInt(params.iSortCol_0)
		def colName = salesColNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		def state = State.findById(3)
		
		def accountDate = new Date().parse(dateFormat,params.period)
		
        def criteria = SalesSite.createCriteria()
		def salesSiteInstanceList = criteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			
			 if(medio != null) eq('medio', medio)
			 eq('state',state)
			 eq('origin','I')
			 
			 if(params.salesSelectedBox == "checked") {
				 if(params.selectedRows.length() > 0) {
					 def ids = params.selectedRows.split(",")
					inList('id', ids)
				  }
			 } else {
				 if(params.selectedList.length() > 0) {
					 def ids = params.selectedList.split(",")
					  not{inList('id', ids)}
				}
             }
			  
			 if(params.fromSalesTransDate != null && params.toSalesTransDate != null){
			  def fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
			  def toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
			  between('transactionDate', fromTransDate, toTransDate)
			 } else {
			 	le('transactionDate', accountDate)
			 }
			 if(params.minSalesAmount != null){
				 ge('amount', params.minSalesAmount?.toDouble())
			 }
			 if(params.maxSalesAmount != null){
				 le('amount', params.maxSalesAmount?.toDouble())
			 }
		}
        
        responseMap.aaData = serializeSalesData(salesSiteInstanceList)
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = salesSiteInstanceList.totalCount
        responseMap.iTotalDisplayRecords = salesSiteInstanceList.totalCount
        
        render responseMap as JSON
		
		
	}
	
	def save = { 
		def lot = lotGeneratorService.getLotId()
		
		List salesSiteReceiptList = params.ids.split(";")
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		
		Conciliation.withTransaction{
			
			salesSiteReceiptList.each{ item ->
				def conciliateIds = item.split(",")
				def receipt = Receipt.findById(conciliateIds[0])
				def salesSite = SalesSite.findById(conciliateIds[1])
				def conciliation = new Conciliation(sale:salesSite, receipt:receipt,
					lot:lot, medio:medio, period:receipt?.period, registerType:receipt?.registerType)
				
				if(!conciliation.save()) {
					conciliation.errors.each {
						println it
					}
				}
			}
		}
		
		sessionFactory.getCurrentSession().clear();
		
		
		/* call datastage */
		def username = getUsername()
		def strLot = formatNumber(number:lot, format:"000")
		def accountDate = formatDate(date:new Date().parse('yyyy-MM-dd',params.period),format:'yyyy-MM_dd')

		def command = GrailsUtil.getEnvironment().equals('mercadolibre') || GrailsUtil.getEnvironment().equals('mercadolibreUat') ? "/datastage/ConcManual_PROD.sh":"/datastage/ConcManual.sh"  
		Thread.start{
				executeCommand("${command} ${username} ${strLot} ${accountDate}")
		}
		
		
		render message(code:"conciliation.calledProcess", default:"Se ha invocado el proceso", args:[username])
		 
	}
    

	
}
