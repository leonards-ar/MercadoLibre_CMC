package com.ml.cmc

class Preconciliation implements Serializable{

    SalesSite sale
    Receipt receipt
    Long lot
    Medio medio
	String saleStatus
    Long period
    RegisterType registerType
	String origin
	
    static constraints = {
    }
    
    static mapping = {
        table 'F_TMP_PRECONCILIACIONES'
        version false
        
        id composite: ['lot','sale','receipt']
        sale column:'CD_VENTA_CUOTA'
        receipt column:'CD_RECIBO'
        lot column:'SERIAL_JAVA'
        medio column:'CD_MEDIO'
		period column:'CD_PERIODO'
		saleStatus column:'ESTADO_VENTA'
		registerType column:'TIPO_REGISTRO_RECIBOS'
		origin column:'FL_ORIGEN'
		
        
        
    }
}
