package com.ml.cmc

import java.util.Date;

class Preconciliated {

	SalesSite sale
	Receipt receipt
	Long lot
	Medio medio
	AccountantPeriod period
	String registerType
	String origin

	static constraints = {
	}
	
	static mapping = {
		table 'F_TMP_PRECONCILIACIONES'
		version false
		
		id column:'CD_PRECONCILIACION', generator:'assigned'
		sale column:'CD_VENTA_CUOTA'
		receipt column:'CD_RECIBO'
		lot column:'SERIAL_JAVA'
		medio column:'CD_MEDIO'
		period column:'CD_PERIODO'
		saleStatus column:'ESTADO_VENTA'
		registerType column:'TIPO_REGISTRO_RECIBOS'
		origin column:'FL_ORIGEN'
	}

}
