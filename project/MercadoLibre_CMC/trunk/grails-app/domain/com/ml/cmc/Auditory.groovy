package com.ml.cmc

class Auditory {

	Date auditoryDate
	String time
	String user
	AuditoryType auditoryType
	Medio medio
	String description
	String rollback
	Long rollbackLot
	AccountantPeriod period
	
    static constraints = {
		
		auditoryDate (nullable:true)
		time(nullable:true)
		user(nullable:true)
		auditoryType(nullable:true)
		medio(nullable:true)
		description(nullable:true)
		rollback(nullable:true)
		rollbackLot(nullable:true)
		period(nullable:true)
		
		
		
    }
	
	static mapping = {
		table 'F_AUDITORIA'
		version false
		
		id column:'LOTE', generator:'assigned'
		auditoryDate column:'FC_FECHA'
		time column:'HORA'
		user column:'TX_USUARIO'
		auditoryType column:'TX_TIPO_AUDITORIA'
		medio column:'CD_MEDIO'
		description column:'TX_DESC_AUDITORIA'
		rollback column:'FL_ROLLBACK'
	    rollbackLot column:'LOTE_ROLLBACK'
		period column:'CD_PERIODO'		
	}
	
	
}
