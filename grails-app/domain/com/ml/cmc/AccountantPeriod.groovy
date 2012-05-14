package com.ml.cmc

class AccountantPeriod {

	Date startDate
	Date endDate
	String status
	
    static constraints = {
    }
	
	static mapping = {
		table 'F_PERIODOS_CONTABLES'
		version false
		
		id column:'CD_PERIODO', generator:'assigned'
		startDate column:'FC_DESDE'
		endDate column:'FC_HASTA'
		status column:'TX_ESTADO'
	}
	
	String toString() {
		startDate - endDate
	}
}
