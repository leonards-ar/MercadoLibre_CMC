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
        
        responseMap.aaData = []
        conciliationInstanceList.each(){
            responseMap.aaData << ["0":it.receipt?.registerType.toString(),"1":it.receipt?.cardNumber.toString(),"2":it.receipt?.transactionDate.toString(),
                       "3":it.receipt?.amount.toString()]
        }
        
        responseMap.sEcho = params.sEcho
        responseMap.iTotalRecords = conciliationInstanceList.totalCount
        responseMap.iTotalDisplayRecords = conciliationInstanceList.size()
        
        render responseMap as JSON
        
    }
}
