package com.ml.cmc

class Compensated implements Serializable{

	String source
	String registerId
	String lot
	Medio medio
	Long group
	Long period
	String type
	
    static constraints = {
    }
	
	static mapping = {
		table 'F_COMPENSACIONES'
		version false
		
		id columnd:'CD_COMPENSACION', generator:'assigned' 
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
