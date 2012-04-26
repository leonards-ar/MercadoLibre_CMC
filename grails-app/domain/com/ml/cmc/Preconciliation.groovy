package com.ml.cmc

class Preconciliation {

    Long preconciliationId
    SiteSales sale
    Receipt receipt
    Long lot
    Medio medio
    Long preconciliationType
    String state
    
    
    static constraints = {
    }
    
    static mappings = {
        
        id generator:'auto', name:'preconciliationId'
        preconciliationId column:'CD_PRECONCILIACION'
        sale column:'CD_VENTA_ML'
        receipt column:'CD_RECIBO'
        lot column:'LOTE'
        medio column:'CD_MEDIO'
        preconciliationType column:'CD_TIPO_PRECONCILIACION'
        state column:'CD_CAMBIO_ESTADO'
        
        
    }
}
