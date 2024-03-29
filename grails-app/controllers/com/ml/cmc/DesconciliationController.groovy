package com.ml.cmc

import grails.converters.JSON
import grails.util.GrailsUtil

import org.apache.commons.logging.LogFactory

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class DesconciliationController extends SessionInfoController {
	private static final log = LogFactory.getLog(this)
	
    def securityLockService
    def lotGeneratorService
    def sessionFactory

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def colNames = ["transactionDate","amount","authorization","cardNumber","customerId","documentNumber","documentId","id","ro","tid","nsu","shareNumber","shareQty","paymentDate","payment",
                "transactionDate","amount","authorization","cardNumber","customerId","documentNumber","documentId","id","ro","tid","nsu","shareNumber","shareQty","paymentDate","payment"]
	
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
	
	def lots = {
		if(params._value == '---' || params.country == null || params.card == null || params.site == null ) return
		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
		def lotList = Conciliated.createCriteria().list {
			projections {
				distinct("lot")
    		}
			eq('medio',medio)
		} 
		
		def lotKey = []
		lotList.each { item ->
			lotKey.add([item, item])
			
		}
		render lotKey as JSON
		
	}


    def lock = {
        
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
        
        if(medio == null) {
            response.setStatus(500)
            render message(code:"conciliation.nomedio.found.error", default:"No se encontr� ningun medio", args:[params.country, params.card, params.site])
            return
        }

        try{
            securityLockService.lockFunctionality(getUsername(), Constant.FUNC_DESCONCILIATE, getSessionId(), medio)
            render(template: "desconciliationBody")

        }catch (SecLockException e) {
            def error = message(code:"conciliation.security.error" ,default:"Error",args:[e.invalidObject?.username, medio])
            response.setStatus(500)
            render error
            return
         } catch (Exception e){
             response.setStatus(500)
             render e.message
             securityLockService.unLockFunctionality(getSessionId())
             return
         }

    }
    
    def list = {
        def responseMap = [:]
        
        def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site])
        def state4 = State.findById(4)
        
        def conciliatedCriteria = Conciliated.createCriteria()
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colNames[colIdx]

        def conciliationInstanceList = conciliatedCriteria.list(max:max, offset:offset) {
            
			eq('lot',params.lot.toLong())
			if(params.processedList?.length() > 0) {
				def processedIds = params.processedList.split(",") 
				not{inList('id', processedIds.collect{it.toLong()})}
				
			}
            receipt{
                if(medio != null) eq('medio', medio)
                eq('state',state4)
				if(params.fromReceiptTransDate != null && params.toReceiptTransDate != null){
					def fromTransDate = new Date().parse(dateFormat, params.fromReceiptTransDate)
					def toTransDate = new Date().parse(dateFormat, params.toReceiptTransDate)
					between('transactionDate', fromTransDate, toTransDate)
				}
				if(params.fromReceiptPaymtDate != null && params.toReceiptPaymtDate != null){
					def fromPaymtDate = new Date().parse(dateFormat, params.fromReceiptPaymtDate)
					def toPaymtDate = new Date().parse(dateFormat, params.toReceiptPaymtDate)
					between('transactionDate', fromPaymtDate, toPaymtDate)
				}
                if(colIdx < 15)
                order(colName, params.sSortDir_0)
            }
            
			sale {
				if(params.fromSalesTransDate != null && params.toSalesTransDate != null){
					def fromTransDate = new Date().parse(dateFormat, params.fromSalesTransDate)
					def toTransDate = new Date().parse(dateFormat, params.toSalesTransDate)
					between('transactionDate', fromTransDate, toTransDate)
				}
				if(colIdx >= 15) {
                    order(colName, params.sSortDir_0)
                }
            }
            
        }
        
        responseMap.aaData = serializeData(conciliationInstanceList)
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = conciliationInstanceList.totalCount
        responseMap.iTotalDisplayRecords = conciliationInstanceList.totalCount
        
        render responseMap as JSON
        
    }
    
    def save = {
		
		def lot = lotGeneratorService.getLotId()

        def conciliatedIds = params.ids.split(",")

		def medio = Medio.find("from Medio m where m.country= :country and m.card= :card and m.site= :site", [country:params.country, card:params.card, site: params.site]);
		
        Desconciliation.withTransaction {
            
            conciliatedIds.each {id ->
                
                def conciliated = Conciliated.findById(id)
                
                def desconciliation = new Desconciliation(sale:conciliated?.sale,
                    receipt:conciliated?.receipt,
                    lot:lot,
                    conciliated: conciliated,
                    username:getUsername(),
                    medio: medio,
                    period: conciliated.period
                )
                
                if(!desconciliation.save()){
					desconciliation.errors.each {
						println it
					}	
				}
            }
            
        }
        
        sessionFactory.getCurrentSession().clear();
		
		def username = getUsername()
		def strLot = formatNumber(number:lot, format:"000")
		def command = GrailsUtil.getEnvironment().equals('mercadolibre') || GrailsUtil.getEnvironment().equals('mercadolibreUat') ?"/datastage/DesConcManual_PROD.sh":"/datastage/DesConcManual.sh"
		Thread.start{
			executeCommand(" ${command} ${username} ${strLot}")
		}

        render message(code:"desconciliation.calledProcess", default:"Se ha invocado el proceso", args:[username])
        
    }
    
    private serializeData(conciliationInstanceList) {
        
        def data = []
        
        conciliationInstanceList.each(){
            data << ["DT_RowId":it.id.toString(),
                     "0":formatDate(date:it.receipt?.transactionDate, format:"dd-MM-yyyy"),
                     "1":formatNumber(number:it?.receipt?.amount,format:"###,###.00"),
                     "2":it.receipt?.authorization.toString(),
                     "3":it.receipt?.cardNumber.toString(),
                     "4":it.receipt?.customerId.toString(),
                     "5":it.receipt?.documentNumber.toString(),
                     "6":it.receipt?.documentId.toString(),
                     "7":it.receipt?.id.toString(),
                     "8":it.receipt?.ro.toString(),
                     "9":it.receipt?.tid.toString(),
                     "10":it.receipt?.nsu.toString(),
                     "11":it.receipt?.shareNumber.toString(),
                     "12":it.receipt?.shareQty.toString(),
                     "13":formatDate(date:it.receipt?.paymentDate, format:"dd-MM-yyyy"),
                     "14":it.receipt?.payment.toString(),
                     "15":formatDate(date:it.sale?.transactionDate, format:"dd-MM-yyyy"),
                     "16":formatNumber(number:it.sale?.amount,format:"###,###.00"),
                     "17":it.sale?.authorization.toString(),
                     "18":it.sale?.cardNumber.toString(),
                     "19":it.sale?.customerId.toString(),
                     "20":it.sale?.documentNumber.toString(),
                     "21":it.sale?.documentId.toString(),
                     "22":it.sale?.id.toString(),
                     "23":it.sale?.ro.toString(),
                     "24":it.sale?.tid.toString(),
                     "25":it.sale?.nsu.toString(),
                     "26":it.sale?.shareNumber.toString(),
                     "27":it.sale?.shareQty.toString(),
                     "28":formatDate(date:it.sale?.paymentDate, format:"dd-MM-yyyy"),
                     "29":it.sale?.payment.toString()]         
        }
        
        return data
    }
}
