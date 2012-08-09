package com.ml.cmc

class Desconciliation {

 	SalesSite sale
	Receipt receipt
	Long lot
	Conciliated conciliated
	String username
	Medio medio
	AccountantPeriod period
	
    static constraints = {
		
    }
	
	static mapping = {
		table 'F_CONCILIACIONES'
		version false
		
		id column:'CD_CONCILIACION', generator:'assigned'
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot column: 'LOTE_CONCILIACION'
		medio column: 'CD_MEDIO'
		period column: 'CD_PERIODO'
		registerType column:'CD_TIPO_CONC'
	}
	
}
