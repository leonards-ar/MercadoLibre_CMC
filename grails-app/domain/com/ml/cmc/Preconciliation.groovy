package com.ml.cmc

class Preconciliation implements Serializable{

    SalesSite sale
    Receipt receipt
    Long lot
    Medio medio
    RegisterType registerType
    
    
    static constraints = {
    }
    
    static mappings = {
        table 'TMP_PRECON'
        version false
        
        id composite: ['lot','sale','receipt']
        sale column:'CD_VENTA_CUOTA'
        receipt column:'CD_RECIBO'
        lot column:'LOTE_JAVA'
        medio column:'CD_MEDIO'
        state column:'CD_CAMBIO_ESTADO'
        register_type column:'CD_TIPO_REGISTRO'
        
    }
}
