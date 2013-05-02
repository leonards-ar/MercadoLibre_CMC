package com.ml.cmc

import grails.converters.JSON
import grails.util.GrailsUtil

import org.apache.commons.logging.LogFactory

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class CompensationController extends SessionInfoController {
	private static final log = LogFactory.getLog(this)
	
    def securityLockService
    def lotGeneratorService
    def sessionFactory

	def colNames = ["transactionDate","amount","absAmount","authorization","cardNumber","customerId","documentNumber",
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

        
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
        
		def accountDate = new Date().parse(dateFormat,params.period)
		
		def query = "from Receipt s where s.medio=:medio and s.state=:state "
		def queryMap = [medio:medio, state:state1]

		if(params.compReceiptList.length() > 0) {
			 query += " and s.id not in (:ids) "
			 queryMap.ids = params.compReceiptList.split(",")
        }
		if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
		 query += " and s.transactionDate between :fromTransDate and :toTransDate "
		 queryMap.fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
		 queryMap.toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
		} else {
	  	  query += " and s.transactionDate <= :accountDate "
		  queryMap.accountDate = accountDate
		}
		if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
		 query += " and s.paymentDate between :fromPaymentDate and :toPaymentDate "
		 queryMap.fromPaymentDate = new Date().parse(dateFormat, params.fromReceiptPaymtDate)
		 queryMap.toPaymentDate = new Date().parse(dateFormat, params.toReceiptPaymtDate)
		} else {
	  	  query += " and s.paymentDate <= :accountDate "
		  queryMap.accountDate = accountDate
		}
		if(params.minReceiptAmount != null){
		  query += " and s.amount >= :minReceiptAmount "
		  queryMap.minReceiptAmount = params.minReceiptAmount?.toDouble()
		}
			
		if(params.maxReceiptAmount != null){
		   query += " and s.amount <= :maxReceiptAmount "
		   queryMap.maxReceiptAmount = params.maxReceiptAmount?.toDouble()
		}
			
		if(colName == "absAmount") {
			query += " order by abs(amount) ${sortDir}"
		} else {
			query += " order by ${colName} ${sortDir}"
		}
		
		def receiptInstanceList = Receipt.executeQuery(query,queryMap,[max:max, offset:offset])
		
		def receiptCount = Receipt.executeQuery("select count(*) " + query, queryMap);

        responseMap.aaData = serializeReceiptData(receiptInstanceList)
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = receiptCount
        responseMap.iTotalDisplayRecords = receiptCount
        
        render responseMap as JSON
	}
	
	def listSalesSite = {
        def responseMap = [:]
        
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
		
		def accountDate = new Date().parse(dateFormat,params.period)

		def query = "from SalesSite s where s.medio=:medio and s.state=:state "
		def queryMap = [medio:medio, state:state1]

	
         if(params.compSalesList.length() > 0){
			 query += " and s.id not in (:ids) "
			 queryMap.ids = params.compSalesList.split(",")
         }
		 
		 if(params.fromSalesTransDate != null  && params.toSalesTransDate != null){
			 
			 query += " and s.transactionDate between :fromTransDate and :toTransDate "
			 queryMap.fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
			 queryMap.toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
			 
	 	 } else {
		  	  query += " and s.transactionDate <= :accountDate "
			  queryMap.accountDate = accountDate
	 	 }
		 if(params.minSalesAmount != null){
			 query += " and s.amount >= :minSalesAmount "
			 queryMap.minSalesAmount = params.minSalesAmount?.toDouble()
		 }
		 if(params.maxSalesAmount != null){
			 query += " and s.amount <= :minSalesAmount "
			 queryMap.maxSalesAmount = params.maxSalesAmount?.toDouble()
		 }
		 
		 if(colName == "absAmount") {
			 query += " order by abs(amount) ${sortDir}"
		 } else {
		 	query += " order by ${colName} ${sortDir}"
		 }
		
		def salesSiteInstanceList = SalesSite.executeQuery(query,queryMap,[max:max, offset:offset])

		def salesSiteCount = SalesSite.executeQuery("select count(*) " + query, queryMap);		
		
        responseMap.aaData = serializeReceiptData(salesSiteInstanceList)
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = salesSiteCount;
        responseMap.iTotalDisplayRecords = salesSiteCount;
        
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
		def command = "${jobName}" + (GrailsUtil.getEnvironment().equals('mercadolibre') ? "_PROD":"")
		Thread.start{
			executeCommand("${command} ${username} ${strLot} ${accountDate}")
        }
        
        render message(code:"compensation.calledProcess", default:"Se ha invocado el proceso", args:[username])
    
    }
	
	protected serializeReceiptData(instanceList) {
		
		def data = []
		
		instanceList.each(){
			data << ["DT_RowId":it.id.toString(),
					 "0":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
					 "1":formatNumber(number:it?.amount,format:"###,###.00"),
					 "2":formatNumber(number:it?.amount.abs(),format:"###,###.00"),
					 "3":it?.authorization.toString(),
					 "4":it?.cardNumber.toString(),
					 "5":it?.customerId.toString(),
					 "6":it?.documentNumber.toString(),
					 "7":it?.documentId.toString(),
					 "8":it?.id.toString(),
					 "9":it?.ro.toString(),
					 "10":it?.tid.toString(),
					 "11":it?.nsu.toString(),
					 "12":it?.shareNumber.toString(),
					 "13":it?.shareQty.toString(),
					 "14":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "15":it?.payment
					 ]
		}
		
		return data
	}
    
}
