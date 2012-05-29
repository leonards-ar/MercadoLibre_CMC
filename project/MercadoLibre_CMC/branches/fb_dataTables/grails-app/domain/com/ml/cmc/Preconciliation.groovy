package com.ml.cmc

class Preconciliation implements Serializable{

    SalesSite sale
    Receipt receipt
    Long lot
    Medio medio
    AccountantPeriod period
    
    static constraints = {
    }
    
    static mapping = {
        table 'A_PRECONCILIACIONES_TMP'
        version false
        
        id composite: ['lot','sale','receipt']
        sale column:'CD_VENTA_CUOTA'
        receipt column:'CD_RECIBO'
        lot column:'LOTE_JAVA'
        medio column:'CD_MEDIO'
		period column:'CD_PERIODO'
        
        
    }
}
