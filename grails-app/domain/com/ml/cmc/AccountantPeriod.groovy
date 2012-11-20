package com.ml.cmc
import java.text.*

class AccountantPeriod {

	Date startDate
	Date endDate
	String status
	Medio medio
    private DateFormat formatter = new SimpleDateFormat('dd/MM/yyyy')
	private DateFormat dateformatter = new SimpleDateFormat('yyyy-MM_dd')
	
	static transients = ['formatter', 'dateformatter','startDateStr','endDateStr']
	
    static constraints = {
    }
	
	static mapping = {
		table 'F_PERIODOS_CONTABLES'
		version false
		
		id column:'CD_PERIODO', generator:'assigned'
		startDate column:'FC_DESDE'
		endDate column:'FC_HASTA'
		status column:'TX_ESTADO'
		medio column:"CD_MEDIO"
	}
	
	String toString() {
		formatter.format(startDate) + " - " + formatter.format(endDate)
	}
	
	public String getStartDateStr(){
		return dateformatter.format(startDate)
	}
	
	public String getEndDateStr(){
		return dateformatter.format(endDate)
	}
}
