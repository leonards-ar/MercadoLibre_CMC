package com.ml.cmc
import grails.converters.JSON

import org.apache.commons.logging.LogFactory

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class ConciliationController extends SessionInfoController{
	private static final log = LogFactory.getLog(this)
	
	def securityLockService
	def lotGeneratorService
	def sessionFactory

	def colNames = ["transactionDate","amount","authorization","cardNumber","customerId","documentNumber",
		"documentId","id","ro","tid","nsu","shareNumber","shareQty","paymentDate","payment"]

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
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0
		
		def colIdx = params.iSortCol? Integer.parseInt(params.iSortCol_0):0
		def colName = colNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		def state3 = State.findById(3)

        def criteria = Receipt.createCriteria()
		def receiptInstanceList = criteria.list(max:max, offset:offset){
			 order(colName, sortDir)
			 if(medio != null) eq('medio', medio)
			 eq('state',state3)
             eq('period', AccountantPeriod.findById(params.period))
			 if(params.selectedList.length() > 0) {
				 def ids = params.selectedList.split(",")
				 not{inList('id', ids)}
			  }
			 if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
				 def fromTransDate = new Date().parse("dd/MM/yyyy", params.fromReceiptTransDate)
				 def toTransDate = new Date().parse("dd/MM/yyyy", params.toReceiptTransDate)
				 between('transactionDate', fromTransDate, toTransDate)
			 }
			 if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
				 def fromPaymtDate = new Date().parse("dd/MM/yyyy", params.fromReceiptPaymtDate)
				 def toPaymtDate = new Date().parse("dd/MM/yyyy", params.toReceiptPaymtDate)
				 between('transactionDate', fromPaymtDate, toPaymtDate)
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
        
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0
		def colIdx = params.iSortCol? Integer.parseInt(params.iSortCol_0):0
		def colName = colNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

		def medios = Medio.find("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		def state = State.findById(3)
        def criteria = SalesSite.createCriteria()
		def salesSiteInstanceList = criteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			
			 if(medios != null) inList('medio', medios)
			 eq('state',state)
             eq('period', AccountantPeriod.findById(params.period))
  			 if(params.selectedList.length() > 0) {
				def ids = params.selectedList.split(",")
                not{inList('id', ids)}
             }
			 if(params.fromSalesTransDate != null && params.toSalesTransDate != null){
			  def fromTransDate = new Date().parse("dd/MM/yyyy", params.fromSalesTransDate)
			  def toTransDate = new Date().parse("dd/MM/yyyy", params.toSalesTransDate)
			  between('transactionDate', fromTransDate, toTransDate)
			 }
		}
        
        responseMap.aaData = serializeReceiptData(salesSiteInstanceList)
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
				
				conciliation.save()
			}
		}
		
		sessionFactory.getCurrentSession().clear();
		
		
		/* call datastage */
		def username = getUsername()
		def strLot = formatNumber(number:lot, format:"000")
		int result = exceuteCommand("/datastage/ConcManual.sh ${username} ${strLot}")
		
		
		render message(code:"conciliation.calledProcess", default:"Se ha invocado el proceso", args:[username])
		 
	}
    

	
}
