package com.ml.cmc

import java.io.Serializable;

class DesPreconciliation implements Serializable{

	SalesSite sale
	Receipt receipt
	Long lot
	Conciliated conciliated
	String username
	Medio medio
	AccountantPeriod period
	String status
	
	static constraints = {
		
	}
	
	static mapping = {
		table ' F_ROLLBACK_DESPRECONCILIAR'
		version false
		
		id composite:['preconciliated']
		preconciliated column: 'CD_PRECONCILIACION'
		sale column: 'CD_VENTA_CUOTA'
		receipt column: 'CD_RECIBO'
		lot column: 'LOTE_CONCILIACION'
		medio column: 'CD_MEDIO'
		period column: 'PERIODO'
		username column:'USUARIO'
		status column:'CD_CAMBIO_ESTADO'
	}

}
