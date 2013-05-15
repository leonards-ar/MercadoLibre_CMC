package com.ml.cmc

class Compensation implements Serializable{

	String source
    String registerId
    String lot
    Medio medio
    Long group
    AccountantPeriod period
    String type="MANUAL"
	Long serial

	
    static constraints = {
		
		period(nullable:true)
		lot(nullable:true)
		
    }
	
	static mapping = {
		table 'F_TMP_COMPENSACIONES'
		version false
		
		id composite: ['group','registerId']
		source column: 'ORIGEN'
		registerId column: 'ID_REGISTRO'
		lot column: 'LOTE'
		medio column: 'CD_MEDIO'
        group column: 'GRUPO'
		period column: 'CD_PERIODO'
        type column: 'TX_TIPO'
        serial column: 'SERIAL_JAVA'
	}
}
