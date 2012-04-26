package com.ml.cmc
import grails.converters.JSON
import com.ml.cmc.service.SecurityLockService

class PreconciliationController extends SessionInfoController{

    def securityLockService
    
    def index = {
        render(view:'index') 
        }
    
    def cards = {
        def cardsToSelect = [[],['VISA'], ['MASTERCARD'], ['AMEX'], ['NARANJA'],['CABAL']]
        render cardsToSelect as JSON
    }
    
    def sites = {
         def sitesToSelect = [[],['MP'],['ML']]   
        render sitesToSelect as JSON
    }
    
    def lockMedio = {
        
        sercurityLockSerivce.lock()
        render true as JSON
    }
}
