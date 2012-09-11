package com.ml.cmc

import com.ml.cmc.constants.Constant 
import com.ml.cmc.exception.SecLockException
import grails.converters.JSON

class CompensationController extends SessionInfoController {
    
    def securityLockService
    def lotGeneratorService
    def sessionFactory

    def colNames = ["registerType","cardNumber","transactionDate","amount","shareAmount","authorization",
                    "shareNumber","shareQty","customerId","documentId","tid","nsu","documentNumber"]
    
    def index = {
		securityLockService.unLockFunctionality(getSessionId())
		def countryList = Medio.withCriteria{
			projections{
				distinct "country"
			}
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
        
		def state1 = State.findById(1);
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])

        def receiptCriteria = Receipt.createCriteria()
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = params.iSortCol? Integer.parseInt(params.iSortCol_0):0
        def colName = colNames[colIdx]
        def sortDir = params.sSortDir_0? params.sSortDir_0:'asc'
        
		def receiptInstanceList = receiptCriteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			eq('medio', medio)
			eq('state',state1)
			if(params.compReceiptList.length() > 0) {
				def ids = params.compReceiptList.split(",")
                not{inList('id', ids)}
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
		def salesSiteInstanceList = salesCriteria.list(max:max, offset:offset) {
			order(colName, sortDir)
			 eq('medio', medio)
			 eq('state',state1)
             def ids = params.compSalesList.split(",")
             if(ids.length > 0){
                 not{inList('id', ids)}
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
        
        def groups = params.ids.split(";")

        Compensation.withTransaction{        
            groups.each{group ->
                def groupId = lotGeneratorService.getGroupId() 
                def items = group.split(",")
                items.each{
                    def item = params.element == 'F_RECIBOS'?Receipt.findById(it):SalesSite.findById(it)                    
                    def compensation = new Compensation(source:params.element, registerId:item?.id,medio: item?.medio, group:groupId, period:item?.period, serial:lot)

                    compensation.save()
					
                }
            }
        }
        
        sessionFactory.getCurrentSession().clear();
    
        
        /* call datastage */
        def username = getUsername()
		def jobName = params.element == "F_RECIBOS"?"/datastage/CompManual_Recibos.sh":"/datastage/CompManual_Ventas.sh"
        def job = [jobName, username, lot].execute()
        job.waitFor()
        if(job.exitValue()){
            response.setStatus(500)
            render job.err.text
        }
        
        render job.text
    
    }
    
    private serializeReceiptData(instanceList) {
        
        def data = []
        
        instanceList.each(){
            data << ["DT_RowId":it.id.toString(),
                     "0":it?.registerType.toString(),
                     "1":it?.cardNumber.toString(),
                     "2":formatDate(date:it?.transactionDate, format:"dd-MM-yyyy"),
                     "3":it?.amount.toString(),
                     "4":it?.shareAmount.toString(),
                     "5":it?.authorization.toString(),
                     "6":it?.shareNumber.toString(),
                     "7":it?.shareQty.toString(),
                     "8":it?.tid.toString(),
                     "9":it?.nsu.toString(),
                     "10":it?.documentNumber.toString()]
        }
        
        return data
    }
    
    private serializeSalesData(instanceList) {
        
        def data = []
        
        instanceList.each(){
            data << ["DT_RowId":it.id.toString(),
                     "0":it?.registerType.toString(),
                     "1":it?.cardNumber.toString(),
                     "2":formatDate(date:it?.transactionDate,format:"dd-MM-yyyy"),
                     "3":it?.amount.toString(),
                     "4":it?.shareAmount.toString(),
                     "5":it?.authorization.toString(),
                     "6":it?.shareNumber.toString(),
                     "7":it?.shareQty.toString(),
                     "8":it?.tid.toString(),
                     "9":it?.nsu.toString(),
                     "10":it?.documentNumber.toString()]
        }
        
        return data
    }

		 
}
