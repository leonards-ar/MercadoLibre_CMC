package com.ml.cmc
import grails.converters.JSON
import grails.util.GrailsUtil

import org.apache.commons.logging.LogFactory

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class PreconciliationController extends SessionInfoController{
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
            render message(code:"preconciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
            return
        }

        try{
            securityLockService.lockFunctionalityByCountry(getUsername(), Constant.FUNC_PRECONCILIATE, getSessionId(), params.country)
        }catch (SecLockException e) {
           def error = message(code:"preconciliation.security.country.error" ,default:"Error",args:[e.invalidObject?.username, medio.country])           
           response.setStatus(500)
           render error
           return
        } catch (Exception e){
            response.setStatus(500)
            render e.message
            return
        }
        
        render(template: "preconciliationBody", model:[filterType:'salesFilter'])

    }

    def listReceipts = {
		
		def responseMap = [:]
		def max = params.iDisplayLength?params.iDisplayLength:10
		def offset = params.iDisplayStart?params.iDisplayStart:0
		
		def colIdx = Integer.parseInt(params.iSortCol_0)
		def colName = colNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        def receiptInstanceList = []
		
        def selectedList = null
		if(params.selectedList.length() > 0) selectedList = params.selectedList.split(",")
        
        if(params.filterType == 'salesFilter') {
            receiptInstanceList = getReceiptsForSales(medio, colName, sortDir, max, offset, selectedList)
        } else {
            receiptInstanceList = getReceiptsForDisable(medio, colName, sortDir, max, offset, selectedList)
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
		
		def colIdx = Integer.parseInt(params.iSortCol_0)
		def colName = colNames[colIdx]
		def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'

        def medios = Medio.findAll("from Medio m where m.country= :country and m.site= :site", [country:params.country, site: params.site])
		if(medios instanceof Medio){
			medios = [medios]
		}
		
        def selectedList = null
		if(params.selectedList.length() > 0) selectedList = params.selectedList.split(",")

		def salesSiteInstanceList = []
		
		if(params.filterType == 'salesFilter') {
			salesSiteInstanceList = getSalesForSales(medios, colName, sortDir, max, offset, selectedList )
		} else {
			salesSiteInstanceList = getSalesForDisable(medios, colName, sortDir, max, offset, selectedList)
		}
		
        responseMap.aaData = serializeReceiptData(salesSiteInstanceList)
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = salesSiteInstanceList.totalCount
        responseMap.iTotalDisplayRecords = salesSiteInstanceList.totalCount
        
		render responseMap as JSON
        
    }
    
    def save = { 
        
        def lot = lotGeneratorService.getLotId()
        def period = AccountantPeriod.findById(params.period)
		
		List salesSiteReceiptList = params.ids.split(";")
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        
		Preconciliation.withTransaction{
	        salesSiteReceiptList.each{ item ->
				def preconciliateIds = item.split(",")
				def receipt = Receipt.findById(preconciliateIds[0])
				def salesSite = SalesSite.findById(preconciliateIds[1])
	            def preConciliation = new Preconciliation(sale:salesSite, receipt:receipt, 
	                lot:lot, medio:medio, period: receipt?.period, registerType:receipt?.registerType,
					saleStatus: salesSite.state, origin:salesSite?.origin)
	            
	            preConciliation.save()
	        }
        }
        
        sessionFactory.getCurrentSession().clear();
        
        /* call datastage */

       	def username = getUsername()
		def strLot = formatNumber(number:lot, format:"000")
		def command = GrailsUtil.getEnvironment().equals('mercadolibre') || GrailsUtil.getEnvironment().equals('mercadolibreUat') ?"/datastage/PreConcManual_PROD.sh":"/datastage/PreConcManual.sh"
		Thread.start{
			executeCommand("${command} ${username} ${strLot}" )
       	}
		
        render message(code:"preconciliation.calledProcess", default:"Se ha invocado el proceso", args:[username])
         
    }
	
	private getReceiptsForSales(Medio medio,String sortType, String orderType, String max, String offset, String[] ids) {
		
		def state1 = State.findById(1)
		
		def registerTypes = RegisterType.findAllByIdInList([1L,2L,5L])

		def criteria = Receipt.createCriteria()
		def receiptInstanceList = criteria.list(max:max, offset:offset) {
			order(sortType, orderType)
			if(medio != null) eq('medio', medio)
			eq('state',state1)
			eq('period', params.period)
			inList('registerType',registerTypes)
			if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
				def fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
				def toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
				between('transactionDate', fromTransDate, toTransDate)
			}
			if(params.minReceiptAmount != null){
				ge('amount', params.minReceiptAmount?.toDouble())
			}
			if(params.maxReceiptAmount != null){
				le('amount', params.maxReceiptAmount?.toDouble())
			}
            if(ids != null && ids.length > 0) {
                not{inList('id', ids)}
            }
		}
		
		return receiptInstanceList
		
	}
	
	private getSalesForSales(List medios,String sortType, String orderType, String max, String offset, String[] ids ) {

		def states = State.findAllByIdInList([1L,2L])

		def registerTypes = RegisterType.findAllByIdInList([1L,2L,5L])

		def criteria = SalesSite.createCriteria()
		
		def salesSiteInstanceList = criteria.list(max:max, offset:offset) {
			order(sortType, orderType)
			 if(medios != null) inList('medio', medios)
			 inList('state',states)
			 inList('registerType',registerTypes)
			 eq('period', params.period)
			 if(params.fromSalesTransDate != null && params.toSalesTransDate != null){
				 def fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
				 def toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
				 between('transactionDate', fromTransDate, toTransDate)
			 }
			 if(params.minSalesAmount != null){
				 ge('amount', params.minSalesAmount?.toDouble())
			 }
			 if(params.maxSalesAmount != null){
				 le('amount', params.maxSalesAmount?.toDouble())
			 }
             if(ids != null && ids.size() > 0) {
                 not{inList('id', ids)}
             }
		}
		
		return salesSiteInstanceList

		
	}
	
	private getReceiptsForDisable(Medio medio,String sortType, String orderType, String max, String offset, String[] ids) {
		
		def state1 = State.findById(1)
		
		def registerType3 = RegisterType.findById(3);

		def criteria = Receipt.createCriteria()
		def receiptInstanceList = criteria.list(max:max, offset:offset) {
			order(sortType, orderType)
			if(medio != null) eq('medio', medio)
			eq('state',state1)
			eq('registerType',registerType3)
			eq('period', params.period)
			if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
				def fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
				def toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
				between('transactionDate', fromTransDate, toTransDate)
			}
			if(params.minReceiptAmount != null){
				ge('amount', params.minReceiptAmount?.toDouble())
			}
			if(params.maxReceiptAmount != null){
				le('amount', params.maxReceiptAmount?.toDouble())
			}

            if(ids != null && ids.size() > 0) {
                not{inList('id', ids)}
            }
		}
		
		return receiptInstanceList
		
	}
	
	private getSalesForDisable(List medios,String sortType, String orderType, String max, String offset, String[] ids ) {

		def states = State.findAllByIdInList([1L,2L])

		
		def registerType3 = RegisterType.findById(3);
		
		def criteria = SalesSite.createCriteria()
		def salesSiteInstanceList = criteria.list(max:max, offset:offset) {
			order(sortType != null? sortType:'receiptNumber', orderType != null?orderType:'asc')
			 if(medios != null) inList('medio', medios)
			 inList('state',states)
			 eq('registerType',registerType3)
			 eq('period', params.period)
			 if(params.fromSalesTransDate != null && params.toSalesTransDate != null){
				 def fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
				 def toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
				 between('transactionDate', fromTransDate, toTransDate)
			 }
			 if(params.minSalesAmount != null){
				 ge('amount', params.minSalesAmount?.toDouble())
			 }
			 if(params.maxSalesAmount != null){
				 le('amount', params.maxSalesAmount?.toDouble())
			 }
             if(ids != null && ids.size() > 0) {
                 not{inList('id', ids)}
             }
		}

	}
	
}
