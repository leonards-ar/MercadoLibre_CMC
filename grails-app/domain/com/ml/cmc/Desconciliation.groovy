package com.ml.cmc

class Desconciliation implements Serializable{

 	SalesSite sale
	Receipt receipt
	Long lot
	Conciliated conciliated
	String username
	Medio medio
	Long period
	
    static constraints = {
		
    }
	
	static mapping = {
		table ' F_ROLLBACK_DESCONCILIAR'
		version false
		
		id composite:['conciliated']
        conciliated column: 'CD_CONCILIACION'
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot column: 'LOTE_CONCILIACION'
		medio column: 'CD_MEDIO'
		period column: 'PERIODO'
		username column:'USUARIO'
	}
	
}
