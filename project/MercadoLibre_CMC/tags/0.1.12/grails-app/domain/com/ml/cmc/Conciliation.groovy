package com.ml.cmc

class Conciliation implements Serializable {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	Long period
	Long registerType
	
    static constraints = {
		
    }
	
	static mapping = {
		table 'F_TMP_CONCILIACIONES'
		version false
		
		id composite: ['lot','sale','receipt']
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot column: 'SERIAL_JAVA'
		medio column: 'CD_MEDIO'
		period column: 'CD_PERIODO'
		registerType column:'CD_TIPO_CONC'
	}
}
