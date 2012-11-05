package com.ml.cmc
import java.text.*

class AccountantPeriod {

	Date startDate
	Date endDate
	String status
    DateFormat formatter = new SimpleDateFormat('dd/MM/yyyy')
	
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
		formatter.format(startDate) + " - " + formatter.format(endDate)
	}
}
