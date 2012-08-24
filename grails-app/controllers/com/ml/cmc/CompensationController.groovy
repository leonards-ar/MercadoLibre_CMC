package com.ml.cmc

import com.ml.cmc.constants.Constant 
import com.ml.cmc.exception.SecLockException
import grails.converters.JSON

class CompensationController extends SessionInfoController {

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
			render message(code:"preconciliation.nomedio.found.error", default:"No se encontró ningun medio", args:[params.country, params.card, params.site])
			return
		}

		try{
			securityLockService.lockFunctionality(getUsername(), Constant.FUNC_COMPENSATE, getSessionId(), medio)
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
		
		render(template: "compensationBody")

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
		}
		
        responseMap.aaData = serializeSalesData(salesSiteInstanceList)
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = salesSiteInstanceList.totalCount
        responseMap.iTotalDisplayRecords = salesSiteInstanceList.totalCount
        
        render responseMap as JSON
        
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
