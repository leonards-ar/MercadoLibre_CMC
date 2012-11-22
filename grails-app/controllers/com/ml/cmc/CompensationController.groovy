package com.ml.cmc

import grails.converters.JSON

import org.apache.commons.logging.LogFactory

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class CompensationController extends SessionInfoController {
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
		if(params.country == null || params.card == null || params.site == null){
			response.setStatus(500)
			render message(code:"preconciliation.nocomboselected.error")
			return
		}
		
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
		def lock = new Lock()
		if(medio == null) {
			response.setStatus(500)
			render message(code:"preconciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
			return
		}

		try{
			securityLockService.lockFunctionality(getUsername(), Constant.FUNC_COMPENSATE, getSessionId(), medio)
            render(template: "compensationBody")
		}catch (SecLockException e) {
		   def error = message(code:"preconciliation.security.error" ,default:"Error",args:[e.invalidObject?.username, medio])
		   response.setStatus(500)
		   render error
		   return
		} catch (Exception e){
			response.setStatus(500)
			render e.message
			return
		}


	}
	
	def listReceipts = {
        def responseMap = [:]
        
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])

        def receiptCriteria = Receipt.createCriteria()
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = params.iSortCol? Integer.parseInt(params.iSortCol_0):0
        def colName = colNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
        
		def accountDate = new Date().parse("yyyy-MM-dd",params.period)
		
		def receiptInstanceList = receiptCriteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			eq('medio', medio)
			eq('state',state1)
			if(params.compReceiptList.length() > 0) {
				def ids = params.compReceiptList.split(",")
                not{inList('id', ids)}
            }
			if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
				def fromTransDate = new Date().parse("yyyy-MM-dd", params.fromReceiptTransDate)
				def toTransDate = new Date().parse("yyyy-MM-dd", params.toReceiptTransDate)
				between('transactionDate', fromTransDate, toTransDate)
			} else {
				le('transactionDate', accountDate)
			}
			if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
				def fromPaymtDate = new Date().parse("yyyy-MM-dd", params.fromReceiptPaymtDate)
				def toPaymtDate = new Date().parse("yyyy-MM-dd", params.toReceiptPaymtDate)
				between('transactionDate', fromPaymtDate, toPaymtDate)
			} else {
				le('paymentDate', accountDate)
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
        
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
        def salesCriteria = SalesSite.createCriteria()

        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = params.iSortCol? Integer.parseInt(params.iSortCol_0):0
        def colName = colNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
		
		def accountDate = new Date().parse("yyyy-MM-dd",params.period)
		
		def salesSiteInstanceList = salesCriteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			 eq('medio', medio)
			 eq('state',state1)
             
             if(params.compSalesList.length() > 0){
				 def ids = params.compSalesList.split(",")
                 not{inList('id', ids)}
             }
			 if(params.fromSalesTransDate != null  && params.toSalesTransDate != null){
				 def fromTransDate = new Date().parse("yyyy-MM-dd", params.fromSalesTransDate)
				 def toTransDate = new Date().parse("yyyy-MM-dd", params.toSalesTransDate)
				 between('transactionDate', fromTransDate, toTransDate)
		 	 } else {
			  	le('transactionDate', accountDate)
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
        
        def groups = params.ids.split(";")
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
        Compensation.withTransaction{        
            groups.each{group ->
                def groupId = lotGeneratorService.getGroupId() 
                def items = group.split(",")
                items.each{
                    def item = params.element == 'F_RECIBOS'?Receipt.findById(it):SalesSite.findById(it)                    
                    def compensation = new Compensation(source:params.element, registerId:item?.id,medio: medio, group:groupId, period:item?.period, serial:lot)

                    compensation.save()
					
                }
            }
        }
        
        sessionFactory.getCurrentSession().clear();
    
        
        /* call datastage */
        def username = getUsername()
		def jobName = params.element == "F_RECIBOS"?"/datastage/CompManual_Recibos.sh":"/datastage/CompManual_Ventas.sh"
		def strLot = formatNumber(number:lot, format:"000")
		def accountDate = formatDate(date:new Date().parse('yyyy-MM-dd',params.period),format:'yyyy-MM_dd')
		Thread.start{
			executeCommand("${jobName} ${username} ${strLot} ${accountDate}")
        }
        
        render message(code:"compensation.calledProcess", default:"Se ha invocado el proceso", args:[username])
    
    }
    
}
