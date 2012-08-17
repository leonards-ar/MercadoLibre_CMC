package com.ml.cmc

import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException
import grails.converters.JSON

class DesconciliationController extends SessionInfoController {

    def securityLockService
    def lotGeneratorService
    def sessionFactory

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
        def conciliationInstanceList = conciliatedCriteria.list(max:max, offset:offset) {
            order(params.sort != null? params.sort:'receipt', params.order != null?params.order:'asc')
            receipt{
                if(medio != null) eq('medio', medio)
                //eq('transactionDate', new Date().parse('dd/MM/yyyy', params.datepicker))
                eq('state',state4)
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
        
        conciliatedIds        
        render "la pucha"
        
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
                     "16":it.sale?.amount.toString(),
                     "17":it.sale?.shareAmount.toString(),
                     "18":it.sale?.authorization.toString(),
                     "19":it.sale?.shareNumber.toString(),
                     "20":it.sale?.shareQty.toString(),
                     "21":it.sale?.customerId.toString(),
                     "22":it.sale?.documentId.toString(),
                     "23":it.sale?.tid.toString(),
                     "24":it.sale?.nsu.toString(),
                     "25":it.sale?.documentNumber.toString(),
                     "26":it.sale?.transactionDate.toString()]
        }
        
        return data
    }
}
