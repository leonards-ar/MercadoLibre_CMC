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

	def salesColSQLNames = ["CD_VENTA_CUOTA","CD_VENTA_ML","CD_MEDIO","CD_ESTADO","CD_TIPO_REGISTRO","LOTE","NU_TARJETA",
		"FC_OPERACION","FC_PAGO","VL_IMPORTE","absAmount","VL_CUOTA","absCuota","NU_AUTORIZACION","NU_CUOTA","NU_CANT_CUOTAS","NU_LIQUIDACION","CUST_ID",
		"DOC_ID","NU_RECIBO","TID","NSU","RO","NU_COMERCIO","LOTE_TARJETA","NU_UNICO_RO","DOC_NUMBER","PAYMENT_ID","CD_PERIODO",
		"FL_ORIGEN","OPERATION_ID","SAP_ID","PAY_REFERENCE","PRICING","CONC_PAY_ID"]
	
	def receiptColSqlNames = ["CD_RECIBO","CD_MEDIO","CD_ESTADO","CD_TIPO_REGISTRO","LOTE","NU_TARJETA","FC_OPERACION",
		"FC_PAGO","VL_IMPORTE","absAmount","VL_CUOTA","absCuota","NU_AUTORIZACION","NU_CUOTA","NU_CANT_CUOTAS","NU_LIQUIDACION",
		"CUST_ID","DOC_ID","NU_RECIBO","TID","NSU","RO","NU_COMERCIO","LOTE_TARJETA","NU_UNICO_RO","PAYMENT_ID",
		"DOC_NUMBER","CD_PERIODO","FL_PAGADO"]
	
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
        
		def state1 = State.findById(1)
		def state3 = State.findById(3)
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])

        
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = receiptColSqlNames[colIdx]
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
		} else if(colName == "absCuota"){
			query += " order by abs(VL_CUOTA) ${sortDir}"
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
			sqlQuery.setParameterList("ids",queryMap.ids.split(","))
			sqlCountQuery.setParameterList("ids",queryMap.ids.split(","))
		}
		if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
		 sqlQuery.setDate("fromTransDate",queryMap.fromTransDate)
		 sqlCountQuery.setDate("fromTransDate",queryMap.fromTransDate)
		 sqlQuery.setDate("toTransDate",queryMap.toTransDate)
		 sqlCountQuery.setDate("toTransDate",queryMap.toTransDate)
		}
		if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
		 sqlQuery.setDate("fromPaymentDate",queryMap.fromPaymentDate)
		 sqlQuery.setDate("toPaymentDate",queryMap.toPaymentDate)
		 
		 sqlCountQuery.setDate("fromPaymentDate",queryMap.fromPaymentDate)
		 sqlCountQuery.setDate("toPaymentDate",queryMap.toPaymentDate)
		} else {
		  sqlQuery.setDate("accountPaymentDate", queryMap.accountPaymentDate)
		  sqlCountQuery.setDate("accountPaymentDate", queryMap.accountPaymentDate)
		}
		
		if(params.minReceiptAmount != null && params.minReceiptAmount2 != null){
			sqlQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			sqlQuery.setDouble("minReceiptAmount2",queryMap.minReceiptAmount2)
			sqlQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
			
			sqlCountQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlCountQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			sqlCountQuery.setDouble("minReceiptAmount2",queryMap.minReceiptAmount2)
			sqlCountQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
		} else {
			if(params.minReceiptAmount != null){
			sqlQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
			
			sqlCountQuery.setDouble("minReceiptAmount",queryMap.minReceiptAmount)
			sqlCountQuery.setDouble("maxReceiptAmount",queryMap.maxReceiptAmount)
		
			} else {
				if(params.minReceiptAmount2 != null) {
				  sqlQuery.setDouble("minReceiptAmount2",queryMap.minReceiptAmount2)
				  sqlQuery.setDouble("maxReceiptAmount2",queryMap.maxReceiptAmount2)
				  
				  sqlCountQuery.setDouble("minReceiptAmount2",queryMap.minReceiptAmount2)
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
        def colName = salesColSQLNames[colIdx]
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
		 } else if(colName == "absCuota"){
		 	query += " order by abs(VL_IMPORTE) ${sortDir}"
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
			 sqlQuery.setParameterList("ids",queryMap.ids.split(","))
			 sqlCountQuery.setParameterList("ids",queryMap.ids.split(","))
		}
		if(params.fromSalesTransDate != null  && params.toSalesTransDate != null){
			sqlQuery.setDate("fromTransDate",queryMap.fromTransDate)
			sqlQuery.setDate("toTransDate",queryMap.toTransDate)
			
			sqlCountQuery.setDate("fromTransDate",queryMap.fromTransDate)
			sqlCountQuery.setDate("toTransDate",queryMap.toTransDate)

		} else {
			sqlQuery.setDate("accountDate", queryMap.accountDate)
			sqlCountQuery.setDate("accountDate", queryMap.accountDate)
		}
		if(params.minSalesAmount != null && params.minSalesAmount2 != null){
			sqlQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
			sqlQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
			sqlQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
			sqlQuery.setDouble("maxSalesAmount2",queryMap.maxSalesAmount2)
			
			sqlCountQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
			sqlCountQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
			sqlCountQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
			sqlCountQuery.setDouble("maxSalesAmount2",queryMap.maxSalesAmount2)
		} else {
			if(params.minSalesAmount != null){
				sqlQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
				sqlQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)
				
				sqlCountQuery.setDouble("minSalesAmount",queryMap.minSalesAmount )
				sqlCountQuery.setDouble("maxSalesAmount",queryMap.maxSalesAmount)

			} else {
				if(params.minSalesAmount2 != null) {
					sqlQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
					sqlQuery.setDouble("maxSalesAmount2",queryMap.maxSalesAmount2)
					
					sqlCountQuery.setDouble("minSalesAmount2",queryMap.minSalesAmount2)
					sqlCountQuery.setDouble("maxSalesAmount2",queryMap.maxSalesAmount2)

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
		

        responseMap.aaData = serializeSalesData(salesSiteInstanceList)
        
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

                    if(!compensation.save()){
						compensation.errors.each{
							println it
						}
					}
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
			int i=0
			data << ["DT_RowId":it.id.toString(),
					 "${i++}":it?.id.toString(),
					 "${i++}":it?.medio.id,
					 "${i++}":it?.state.id,
					 "${i++}":it?.registerType,
					 "${i++}":it?.lot,
					 "${i++}":it?.cardNumber,
					 "${i++}":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),//documentId.toString(),
					 "${i++}":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "${i++}":formatNumber(number:it?.amount,format:"###,###.00"),
					 "${i++}":formatNumber(number:it?.amount?.abs(),format:"###,###.00"),
					 "${i++}":formatNumber(number:it?.shareAmount,format:"###,###.00"),
				     "${i++}":formatNumber(number:it?.shareAmount?.abs(),format:"###,###.00"),
					 "${i++}":it?.authorization?.toString(),
					 "${i++}":it?.shareNumber?.toString(),
					 "${i++}":it?.shareQty?.toString(),
					 "${i++}":it?.liq?.toString(),
					 "${i++}":it?.customerId?.toString(),
					 "${i++}":it?.documentId?.toString(),
					 "${i++}":it?.receiptNumber?.toString(),
					 "${i++}":it?.tid?.toString(),
					 "${i++}":it?.nsu?.toString(),
					 "${i++}":it?.ro?.toString(),
					 "${i++}":it?.store?.toString(),
					 "${i++}":it?.cardLot?.toString(),
					 "${i++}":it?.uniqueRo?.toString(),
					 "${i++}":it?.payment?.toString(),
					 "${i++}":it?.documentNumber?.toString(),
					 "${i++}":it?.period?.toString(),
					 "${i++}":it?.payed?.toString()
					 ]
		}
		
		return data
	}

	protected serializeSalesData(instanceList) {
		
		def data = []
		
		instanceList.each(){
			int i=0
			data << ["DT_RowId":it.id.toString(),
					 "${i++}":it?.saleMl,
					 "${i++}":it?.id,
					 "${i++}":it?.medio.id.toString(),
					 "${i++}":it?.state.id.toString(),
					 "${i++}":it?.registerType?.toString(),
					 "${i++}":it?.lot.toString(),
					 "${i++}":it?.cardNumber?.toString(),
					 "${i++}":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
					 "${i++}":formatDate(date:it?.paymentDate, format:"dd-MM-yyyy"),
					 "${i++}":formatNumber(number:it?.amount,format:"###,###.00"),
					 "${i++}":formatNumber(number:it?.amount?.abs(),format:"###,###.00"),
					 "${i++}":formatNumber(number:it?.shareAmount,format:"###,###.00"),
					 "${i++}":formatNumber(number:it?.shareAmount?.abs(),format:"###,###.00"),
					 "${i++}":it?.authorization?.toString(),
					 "${i++}":it?.shareNumber?.toString(),
					 "${i++}":it?.shareQty?.toString(),
					 "${i++}":it?.liq?.toString(),
					 "${i++}":it?.customerId?.toString(),
					 "${i++}":it?.documentId?.toString(),
					 "${i++}":it?.receiptNumber?.toString(),
					 "${i++}":it?.tid?.toString(),
					 "${i++}":it?.nsu?.toString(),
					 "${i++}":it?.ro?.toString(),
					 "${i++}":it?.store?.toString(),
					 "${i++}":it?.cardLot?.toString(),
					 "${i++}":it?.uniqueRo?.toString(),
					 "${i++}":it?.payment?.toString(),
					 "${i++}":it?.documentNumber?.toString(),
					 "${i++}":it?.period?.toString(),
					 "${i++}":it?.origin?.toString(),
					 "${i++}":it?.operation?.toString(),
					 "${i++}":it?.sap?.toString(),
					 "${i++}":it?.paymentReference?.toString(),
					 "${i++}":it?.pricing?.toString(),
					 "${i++}":it?.concPay?.toString()
					 ]
		}
		
		return data
	}

    
}
