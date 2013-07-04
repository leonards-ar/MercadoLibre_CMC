package com.ml.cmc

class Conciliated {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	Long period
	String registerType
	Date accountDate
	
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
		accountDate column:'FECHA_CONTABLE'
	}
}
