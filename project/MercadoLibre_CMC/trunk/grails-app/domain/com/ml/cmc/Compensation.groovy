package com.ml.cmc

class Compensation implements Serializable{

	String id
	String origin
    String item
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
		
		id column:'CD_COMPENSACIONES', generator:'assigned'
		origin column: 'ORIGEN'
		item column: 'ID_REGISTRO'
		lot: 'LOTE'
		medio: 'CD_MEDIO'
        group: 'GRUPO'
		period: 'CD_PERIODO'
        type: 'TX_TIPO'
        serial: 'SERIAL_JAVA'
	}
}
