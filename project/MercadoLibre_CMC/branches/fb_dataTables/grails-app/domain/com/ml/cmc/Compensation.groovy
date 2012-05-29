package com.ml.cmc

class Compensation implements Serializable{

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
		table 'A_COMPENSACIONES_TMP'
		version false
		
		id composite: ['lot','sale','receipt']
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot: 'LOTE'
		medio: 'CD_MEDIO'
		period: 'CD_PERIODO'
	}
}
