package com.ml.cmc

class Conciliated {

    String id
	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	AccountantPeriod period
	RegisterType registerType
	
    static constraints = {
		
    }
	
	static mapping = {
		table 'F_CONCILIACIONES'
		version false
		
		id column:'CD_CONCILIACION', generator:'assigned'
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot column: 'LOTE'
		medio column: 'CD_MEDIO'
		period column: 'CD_PERIODO'
		registerType column:'CD_TIPO_CONC'
	}
}
