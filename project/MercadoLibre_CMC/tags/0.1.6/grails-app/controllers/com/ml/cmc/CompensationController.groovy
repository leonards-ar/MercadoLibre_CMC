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
	def colSQLNames = ["FC_OPERACION","VL_IMPORTE","absAmount","NU_AUTORIZACION","NU_TARJETA","CUST_ID","DOC_NUMBER",
		"DOC_ID","CD_VENTA_CUOTA","RO","TID","NSU","NU_CUOTA","NU_CANT_CUOTAS","FC_PAGO","PAYMENT_ID"]
	
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
			render message(code:"preconciliation.nomedio.found.error", default:"No se encontr� ningun medio", args:[params.country, params.card, params.site])
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
        
		def state1 = State.findById(1)
		def state3 = State.findById(3)
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])

        
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colSQLNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
        
		def accountDate = new Date().parse(dateFormat,params.period)
		
		def query = " from F_RECIBOS s where s.CD_MEDIO=:medio and s.CD_ESTADO=:state3 and fl_pagado = 'OK' "
		def queryCount = "select count(*) from F_RECIBOS s where s.CD_MEDIO=:medio and s.CD_ESTADO=:state3 and fl_pagado = 'OK' "
		def queryMap = [medio:medio, state3:state3]

		if(params.compReceiptList.length() > 0) {
			 query += " and s.CD_RECIBO not in (:ids) "
			 queryMap.ids = params.compReceiptList
        }
		if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
		 query += " and s.FC_OPERACION between :fromTransDate and :toTransDate "
		 queryMap.fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
		 queryMap.toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
		}
		
		if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
		 query += " and s.FC_PAGO between :fromPaymentDate and :toPaymentDate "
		 queryMap.fromPaymentDate = new Date().parse(dateFormat, params.fromReceiptPaymtDate)
		 queryMap.toPaymentDate = new Date().parse(dateFormat, params.toReceiptPaymtDate)
		} else {
	  	  query += " and s.FC_PAGO <= :accountPaymentDate "
		  queryMap.accountPaymentDate = accountDate
		}
		
		if(params.minReceiptAmount != null && params.minReceiptAmount2 != null){
			query += " and ((s.VL_IMPORTE between :minReceiptAmount and :maxReceiptAmount) "
			query += "	or (s.VL_IMPORTE between :minReceiptAmount2 and :maxReceiptAmount2))"
			queryMap.minReceiptAmount = params.minReceiptAmount?.toDouble()
			queryMap.maxReceiptAmount = params.maxReceiptAmount?.toDouble()
			queryMap.minReceiptAmount2 = params.minReceiptAmount2?.toDouble()
			queryMap.maxReceiptAmount2 = params.maxReceiptAmount2?.toDouble()
		} else {
			if(params.minReceiptAmount != null){
				query += " and (s.VL_IMPORTE between :minReceiptAmount and :maxReceiptAmount) "
				queryMap.minReceiptAmount = params.minReceiptAmount?.toDouble()
				queryMap.maxReceiptAmount = params.maxReceiptAmount?.toDouble()
			} else {
				if(params.minReceiptAmount2 != null) {
					query += "	and (s.VL_IMPORTE between :minReceiptAmount2 and :maxReceiptAmount2)"
					queryMap.minReceiptAmount2 = params.minReceiptAmount2?.toDouble()
					queryMap.maxReceiptAmount2 = params.maxReceiptAmount2?.toDouble()
				}
			}
			
		}

		if(colName == "absAmount") {
			query += " order by abs(VL_IMPORTE) ${sortDir}"
		} else {
			query += " order by ${colName} ${sortDir}"
		}
		
		def pagedQuery
		if(offset == "0") {
			pagedQuery = " select * from (select /* INDEX(F_RECIBOS,IDX_F_RECIBOS_CDM_CDE_FCO_FCP) */ * ${query}) where rownum <= :rownum"
		 } else {
			 pagedQuery = " select * from (select a.*, rownum rnum from (select /* INDEX(F_RECIBOS,IDX_F_RECIBOS_CDM_CDE_FCO_FCP) */ * ${query}) a where rownum <=:maxRowNum) where rnum >:minRowNum"
		 }
		 
		def session = sessionFactory.currentSession
		
		def sqlQuery = session.createSQLQuery(pagedQuery)
		def sqlCountQuery = session.createSQLQuery("select count(*) ${query}")
		
		sqlQuery.addEntity(com.ml.cmc.Receipt.class)
		sqlQuery.setLong("medio", medio.id)
		//sqlQuery.setLong("state1",state1.id)
		sqlQuery.setLong("state3",state3.id)

		
		sqlCountQuery.setLong("medio", medio.id)
		//sqlCountQuery.setLong("state1",state1.id)
		sqlCountQuery.setLong("state3",state3.id)

		if(params.compReceiptList.length() > 0){
			sqlQuery.setString("ids",queryMap.ids)
			sqlCountQuery.setString("ids",queryMap.ids)
		}
		if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
		 sqlQuery.setDate("fromTransDate",queryMap.fromTransDate)
		 sqlCountQuery.setDate("fromTransDate",queryMap.fromTransDate)
		 sqlQuery.setDate("toTransDate",queryMap.toTransDate)
		 sqlCountQuery.setDate("toTransDate",queryMap.toTransDate)
		}
		if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
		 sqlQuery.setDate("frompaymentDate",queryMap.fromPaymentDate)
		 sqlQuery.setDate("toPaymentDate",queryMap.toPaymentDate)
		 
		 sqlCountQuery.setDate("frompaymentDate",queryMap.fromPaymentDate)
		 sqlCountQuery.setdate("toPaymentDate",queryMap.toPaymentDate)
		} else {
		  sqlQuery.setDate("accountPaymentDate", queryMap.accountPaymentDate)
		  sqlCountQuery.setDate("accountPaymentDate", queryMap.accountPaymentDate)
		}
		
		if(params.minReceiptAmount != null && params.minReceiptAmount2 != null){
			sqlQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			sqlQuery.setDouble("minReceiptAmount2",queryMap.minRecepitAmount2)
			sqlQuery.setDouble("maxReceiptAmount2",querymap.maxReceiptAmount2)
			
			sqlCountQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlCountQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			sqlCountQuery.setDouble("minReceiptAmount2",queryMap.minRecepitAmount2)
			sqlCountQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
		} else {
			if(params.minReceiptAmount != null){
			sqlQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			
			sqlCountQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlCountQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
		
			} else {
				if(params.minReceiptAmount2 != null) {
				  sqlQuery.setDouble("minReceiptAmount2",queryMap.minRecepitAmount2)
				  sqlQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
				  
				  sqlCountQuery.setDouble("minReceiptAmount2",queryMap.minRecepitAmount2)
				  sqlCountQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
				}
			}
			
		}
		
		if(offset == "0") {
			sqlQuery.setLong("rownum",max.toLong())
		 } else {
			 Long maxRowNum = max.toLong() + offset.toLong()
			 sqlQuery.setLong("minRowNum",offset.toLong())
			 sqlQuery.setLong("maxRowNum", maxRowNum)
		 }

		def receiptInstanceList = sqlQuery.list()
		
		def receiptCount = sqlCountQuery.list()[0]

        responseMap.aaData = serializeReceiptData(receiptInstanceList)
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = receiptCount
        responseMap.iTotalDisplayRecords = receiptCount
        
        render responseMap as JSON
	}
	
	def listSalesSite = {
        def responseMap = [:]
        
		//def state1 = State.findById(1)
		def state3 = State.findById(3)
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colSQLNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
		
		def accountDate = new Date().parse(dateFormat,params.period)

		def query = "select /*+ INDEX(F_VENTAS_SITE,IDX_F_V_SITE_CDM_CDE_FCO) */ * from F_VENTAS_SITE s where s.CD_MEDIO=:medio and s.CD_ESTADO=:state3 and FL_ORIGEN='I' "
		def queryCount = "select count(*) from F_VENTAS_SITE s where s.CD_MEDIO=:medio and s.CD_ESTADO=:state3 and  FL_ORIGEN='I' "
		def queryMap = [medio:medio, state3:state3]

	
         if(params.compSalesList.length() > 0){
			 query += " and s.CD_VENTA_CUOTA not in (:ids) "
			 queryCount += " and s.CD_VENTA_CUOTA not in (:ids) "
			 queryMap.ids = params.compSalesList
         }
		 
		 if(params.fromSalesTransDate != null  && params.toSalesTransDate != null){
			 
			 query += " and s.FC_OPERACION between :fromTransDate and :toTransDate "
			 queryCount += " and s.FC_OPERACION between :fromTransDate and :toTransDate "
			 queryMap.fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
			 queryMap.toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
			 
	 	 } else {
		  	  query += " and s.FC_OPERACION <= :accountDate "
			  queryCount += " and s.FC_OPERACION <= :accountDate "
			  queryMap.accountDate = accountDate
	 	 }
		 if(params.minSalesAmount != null && params.minSalesAmount2 != null){
			 query += " and ((s.VL_IMPORTE between :minSalesAmount and :maxSalesAmount) " 
			 query += "	or (s.VL_IMPORTE between :minSalesAmount2 and :maxSalesAmount2))"
			 queryCount += " and ((s.VL_IMPORTE between :minSalesAmount and :maxSalesAmount) "
			 queryCount += " or (s.VL_IMPORTE between :minSalesAmount2 and :maxSalesAmount2))"

			 queryMap.minSalesAmount = params.minSalesAmount?.toDouble()
			 queryMap.maxSalesAmount = params.maxSalesAmount?.toDouble()
			 queryMap.minSalesAmount2 = params.minSalesAmount2?.toDouble()
			 queryMap.maxSalesAmount2 = params.maxSalesAmount2?.toDouble()
		 } else {
		 	if(params.minSalesAmount != null){
				 query += " and (s.VL_IMPORTE between :minSalesAmount and :maxSalesAmount) "
				 queryCount += " and (s.VL_IMPORTE between :minSalesAmount and :maxSalesAmount) "
				 queryMap.minSalesAmount = params.minSalesAmount?.toDouble()
				 queryMap.maxSalesAmount = params.maxSalesAmount?.toDouble()
			 } else {
			 	if(params.minSalesAmount2 != null) {
					 query += "	and (s.VL_IMPORTE between :minSalesAmount2 and :maxSalesAmount2)"
					 queryCount += "	and (s.VL_IMPORTE between :minSalesAmount2 and :maxSalesAmount2)"
					 queryMap.minSalesAmount2 = params.minSalesAmount2?.toDouble()
					 queryMap.maxSalesAmount2 = params.maxSalesAmount2?.toDouble()
				 }
			 }
			 
		 }
		 if(colName == "absAmount") {
			 query += " order by abs(s.VL_IMPORTE) ${sortDir}"
		 } else {
		 	query += " order by ${colName} ${sortDir}"
		 }

		 if(offset == "0") {
			 query = " select * from (${query}) where rownum <= :rownum"
		  } else {
			  query = " select * from (select a.*, rownum rnum from (${query}) a where rownum <=:maxRowNum) where rnum >:minRowNum"
		  }
 
		def session = sessionFactory.currentSession
		
		def sqlQuery = session.createSQLQuery(query)
		def sqlCountQuery = session.createSQLQuery(queryCount)
		
		sqlQuery.addEntity(com.ml.cmc.SalesSite.class)
		sqlQuery.setLong("medio", medio.id)
		sqlQuery.setLong("state3",state3.id)

		//sqlCountQuery.addScalar(com.ml.cmc.SalesSite.class)
		sqlCountQuery.setLong("medio", medio.id)
		sqlCountQuery.setLong("state3",state3.id)

		if(params.compSalesList.length() > 0){
			 sqlQuery.setString("ids",queryMap.ids)
			 sqlCountQuery.setString("ids",queryMap.ids)
		}
		if(params.fromSalesTransDate != null  && params.toSalesTransDate != null){
			sqlQuery.setDate("fromTransDate",queryMaps.fromTransDate)
			sqlQuery.setDate("toTransDate",queryMap.toTransDate)
			
			sqlCountQuery.setDate("fromTransDate",queryMaps.fromTransDate)
			sqlCountQuery.setDate("toTransDate",queryMap.toTransDate)

		} else {
			sqlQuery.setDate("accountDate", queryMap.accountDate)
			sqlCountQuery.setDate("accountDate", queryMap.accountDate)
		}
		if(params.minSalesAmount != null && params.minSalesAmount2 != null){
			sqlQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
			sqlQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
			sqlQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
			sqlQuery.setDouble("maxSalesAmounts",queryMap.maxSalesAmount2)
			
			sqlCountQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
			sqlCountQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
			sqlCountQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
			sqlCountQuery.setDouble("maxSalesAmounts",queryMap.maxSalesAmount2)
		} else {
			if(params.minSalesAmount != null){
				sqlQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
				sqlQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
				
				sqlCountQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
				sqlCountQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)

			} else {
				if(params.minSalesAmount2 != null) {
					sqlQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
					sqlQuery.setDouble("maxSalesAmounts",queryMap.maxSalesAmount2)
					
					sqlCountQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
					sqlCountQuery.setDouble("maxSalesAmounts",queryMap.maxSalesAmount2)

				}
			}
		}
		
		if(offset == "0") {
			sqlQuery.setLong("rownum",max.toLong())
		 } else {
			 Long maxRowNum = max.toLong() + offset.toLong()
			 sqlQuery.setLong("minRowNum",offset.toLong())
			 sqlQuery.setLong("maxRowNum", maxRowNum)
		 }

		//def salesSiteInstanceList = SalesSite.executeQuery(query,queryMap,[max:max, offset:offset])
		def salesSiteInstanceList = sqlQuery.list()
		def salesSiteCount = sqlCountQuery.list()[0]
		

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
		def jobName;
		if(GrailsUtil.getEnvironment().equals('mercadolibre') || GrailsUtil.getEnvironment().equals('mercadolibreUat') ) {
			jobName = params.element == "F_RECIBOS"?"/datastage/CompManual_Recibos_PROD.sh":"/datastage/CompManual_Ventas_PROD.sh"
		} else {
			jobName = params.element == "F_RECIBOS"?"/datastage/CompManual_Recibos.sh":"/datastage/CompManual_Ventas.sh"
		}
		def strLot = formatNumber(number:lot, format:"000")
		def accountDate = formatDate(date:new Date().parse('yyyy-MM-dd',params.period),format:'yyyy-MM_dd')
		def command = "${jobName}"
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
