package com.ml.cmc

class Preconciliation {

    SalesSite sale
    Receipt receipt
    Long lot
    Medio medio
    Long preconciliationType
    String state
    
    
    static constraints = {
    }
    
    static mappings = {
        table 'preconciliacion'
        version false
        
        id column:'CD_PRECONCILIACION', generator:'auto'
        sale column:'CD_VENTA_CUOTA'
        receipt column:'CD_RECIBO'
        lot column:'LOTE'
        medio column:'CD_MEDIO'
        preconciliationType column:'CD_TIPO_PRECONCILIACION'
        state column:'CD_CAMBIO_ESTADO'
        
        
    }
}
