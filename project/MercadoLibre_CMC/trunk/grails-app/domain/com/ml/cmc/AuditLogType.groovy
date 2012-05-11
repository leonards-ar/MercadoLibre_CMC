package com.ml.cmc

class AuditLogType {

	String value
    static constraints = {
    }
	
	static mapping = {
		table 'F_LKP_TIPO_AUDITORIA'
		version false
		
		id column:'CD_TIPO_AUDITORIA', generator:'assigned'
		value column:'TX_TIPO_AUDITORIA'
	}
}
