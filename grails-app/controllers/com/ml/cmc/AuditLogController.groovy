package com.ml.cmc

class AuditLogController {

    def index = {redirect(action: "list", params: params)}
	
	def list = {
		def auditLogInstanceList = AuditLog.withCriteria{
			order('date', 'desc')
			order('time', 'desc')
			maxResults(15)
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
