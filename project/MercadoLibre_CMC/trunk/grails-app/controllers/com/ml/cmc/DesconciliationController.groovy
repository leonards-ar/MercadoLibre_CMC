package com.ml.cmc

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException
import grails.converters.JSON

class DesconciliationController extends SessionInfoController {

    def securityLockService
    def lotGeneratorService
    def sessionFactory

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def colNames = ["registerType","cardNumber","transactionDate","amount","shareAmount","authorization","shareNumber","shareQty","customerId","documentId","tid","nsu","documentNumber",
                "registerType","cardNumber","amount","shareAmount","authorization","shareNumber","shareQty","customerId","documentId","tid","nsu","documentNumber","transactionDate"]
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
        //def conciliationInstanceList = Conciliated.list();
        def conciliatedCriteria = Conciliated.createCriteria()
        def max = params.iDisplayLength?params.iDisplayLength:10
        def offset = params.iDisplayStart?params.iDisplayStart:0

        def colIdx = Integer.parseInt(params.iSortCol_0)
        def colName = colNames[colIdx]

        def conciliationInstanceList = conciliatedCriteria.list(max:max, offset:offset) {
            
            receipt{
                if(medio != null) eq('medio', medio)
                //eq('transactionDate', new Date().parse('dd/MM/yyyy', params.datepicker))
                eq('state',state4)
                if(colIdx < 13)
                order(colName, params.sSortDir_0)
            }
            
            if(colIdx > 12) {
                
                sale {
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

        def conciliatedIds = params.ids.split(",")
        
        def desconciliations = []
        
        Desconciliation.withTransaction {
            
            conciliatedIds.each {id ->
                
                def conciliated = Conciliated.findById(id)
                
                def desconciliation = new Desconciliation(sale:conciliated?.sale,
                    receipt:conciliated?.receipt,
                    lot:conciliated?.lot,
                    conciliated: conciliated,
                    username:getUsername(),
                    medio: conciliated.medio,
                    period: conciliated.period
                )
                
                desconciliation.save();
                
            }
            
        }
        
        sessionFactory.getCurrentSession().clear();
        
//        def job = ["/datastage/ConcManual.sh", username, lot].execute()
//        job.waitFor()
//        if(job.exitValue()){
//            response.setStatus(500)
//            render job.err.text
//        }
//        render job.text

        render "job needs to be defined"
        
    }
    
    private serializeData(conciliationInstanceList) {
        
        def data = []
        
        conciliationInstanceList.each(){
            data << ["DT_RowId":it.id.toString(),
                     "0":it.receipt?.registerType.toString(),
                     "1":it.receipt?.cardNumber.toString(),
                     "2":it.receipt?.transactionDate.toString(),
                     "3":it.receipt?.amount.toString(),
                     "4":it.receipt?.shareAmount.toString(),
                     "5":it.receipt?.authorization.toString(),
                     "6":it.receipt?.shareNumber.toString(),
                     "7":it.receipt?.shareQty.toString(),
                     "8":it.receipt?.customerId.toString(),
                     "9":it.receipt?.documentId.toString(),
                     "10":it.receipt?.tid.toString(),
                     "11":it.receipt?.nsu.toString(),
                     "12":it.receipt?.documentNumber.toString(),
                     "13":it.sale?.medio?.id.toString(),
                     "14":it.sale?.registerType.toString(),
                     "15":it.sale?.cardNumber.toString(),
                     "16":it.sale?.transactionDate.toString(),
                     "17":it.sale?.amount.toString(),
                     "18":it.sale?.shareAmount.toString(),
                     "19":it.sale?.authorization.toString(),
                     "20":it.sale?.shareNumber.toString(),
                     "21":it.sale?.shareQty.toString(),
                     "22":it.sale?.customerId.toString(),
                     "23":it.sale?.documentId.toString(),
                     "24":it.sale?.tid.toString(),
                     "25":it.sale?.nsu.toString(),
                     "26":it.sale?.documentNumber.toString()]
         
        }
        
        return data
    }
}
