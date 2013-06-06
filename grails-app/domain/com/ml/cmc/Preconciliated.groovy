package com.ml.cmc

import java.util.Date;

class Preconciliated {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	Long period
	String registerType
	String origin

	static constraints = {
	}
	
	static mapping = {
		table 'F_PRECONCILIACIONES'
		version false
		
		id column:'CD_PRECONCILIACION', generator:'assigned'
		sale column:'CD_VENTA_CUOTA'
		receipt column:'CD_RECIBO'
		lot column:'LOTE'
		medio column:'CD_MEDIO'
		period column:'CD_PERIODO'
		saleStatus column:'CD_CAMBIO_ESTADO'
		registerType column:'CD_TIPO_PRECONC'
		origin column:'FL_ORIGEN'
	}

}
