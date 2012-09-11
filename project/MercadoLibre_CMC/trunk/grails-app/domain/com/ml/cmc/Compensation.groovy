package com.ml.cmc

class Compensation implements Serializable{

	String id
	String source
    String regsterId
    String lot
    Medio medio
    String group
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
		
		id composite: ['group','regsterId']
		source column: 'ORIGEN'
		regsterId column: 'ID_REGISTRO'
		lot: 'LOTE'
		medio: 'CD_MEDIO'
        group: 'GRUPO'
		period: 'CD_PERIODO'
        type: 'TX_TIPO'
        serial: 'SERIAL_JAVA'
	}
}
