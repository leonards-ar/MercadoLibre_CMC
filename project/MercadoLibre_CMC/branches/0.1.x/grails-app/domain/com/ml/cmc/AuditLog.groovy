package com.ml.cmc

class AuditLog {

	Date date
	String time
	String user
	String auditLogType
	Medio medio
	String description
	String rollback
	Long rollbackLot
	Long period
	
    static constraints = {
		
		date (nullable:true)
		time(nullable:true)
		user(nullable:true)
		auditLogType(nullable:true)
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
		date column:'FC_FECHA'
		time column:'HORA'
		user column:'TX_USUARIO'
		auditLogType column:'TX_TIPO_AUDITORIA'
		medio column:'CD_MEDIO'
		description column:'TX_DESC_AUDITORIA'
		rollback column:'FL_ROLLBACK'
	    rollbackLot column:'LOTE_ROLLBACK'
		period column:'CD_PERIODO'		
	}
	
	
}
