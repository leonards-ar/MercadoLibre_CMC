package com.ml.cmc

class Conciliation {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	AccountantPeriod period
	
    static constraints = {
		
		period(nullable:true)
		lot(nullable:true)
		
    }
	
	static mapping = {
		table 'A_CONCILIACIONES_TMP'
		version false
		
		id column:'CD_COMPENSACION'
		sale column: 'CD_SITE'
		receipt column: 'CD_RECIBO'
		lot: 'LOTE'
		medio: 'CD_MEDIO'
		period: 'CD_PERIODO'
	}
}
