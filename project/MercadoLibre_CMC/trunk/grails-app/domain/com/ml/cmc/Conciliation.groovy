package com.ml.cmc

class Conciliation {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	String preconciliationType
	AccountantPeriod period
	
    static constraints = {
		
		period(nullable:true)
		lot(nullable:true)
		preconciliationType(nullable:true)
		
    }
	
	static mapping = {
		table 'TMP_COMPENSACIONES'
		version false
		
		id column:'CD_COMPENSACION'
		sale column: 'CD_SITE'
		receipt column: 'CD_RECIBO'
		lot: 'LOTE'
		medio: 'CD_MEDIO'
		preconciliationType: 'CD_TIPO_PRECONC'
		period: 'CD_PERIODO'
	}
}
