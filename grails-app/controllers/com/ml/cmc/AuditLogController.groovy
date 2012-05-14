package com.ml.cmc

class AuditLogController {

    def index = {redirect(action: "list", params: params)}
	
	def list = {
		def auditCriteria = AuditLog.createCriteria()
		def auditLogInstanceList = auditCriteria.list(max:15, offset:params.offset? params.offset : 0){
			order('date', 'desc')
			order('time', 'desc')
		}
		
		[auditLogInstanceList: auditLogInstanceList, auditLogInstanceTotal:auditLogInstanceList.totalCount]
		 
	}
	
	def rollback = {
		def job ="cmd.exe /C echo rollback succesfully".execute()
		
		job.waitFor()
		if(job.exitValue()){
			response.setStatus(500)
			render job.err.text
		}
		render job.text
	}
	
}
