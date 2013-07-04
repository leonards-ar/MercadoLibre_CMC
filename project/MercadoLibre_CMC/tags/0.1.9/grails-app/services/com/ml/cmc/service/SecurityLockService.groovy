package com.ml.cmc.service

import com.ml.cmc.AccountantPeriod
import com.ml.cmc.Lock
import com.ml.cmc.Medio
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class SecurityLockService {

    static transactional = true
    
    private static Map constraints = [
        (Constant.FUNC_PRECONCILIATE):[(Constant.FUNC_COMPENSATE)], 
        (Constant.FUNC_CONCILIATE):[(Constant.FUNC_DESPRECONCILIATE)],
        (Constant.FUNC_COMPENSATE):[(Constant.FUNC_CONCILIATE)],
        (Constant.FUNC_DESPRECONCILIATE):[(Constant.FUNC_CONCILIATE)]
        ]

    Lock lockFunctionality(String username, String functionality, String sessionId, Medio medio) throws SecLockException {
        
        def lockFound = Lock.findByMedio(medio)
        if(lockFound != null){

	        def funcConstraint = constraints.get(functionality) != null ? constraints.get(functionality) : []  
	        if(funcConstraint.contains(lockFound.function) || lockFound.function == functionality ) throw new SecLockException("Resource locked by user: ${lockFound?.username}", lockFound);

        }
        
        
        def lock = new Lock(username:username, function:functionality, sessionId: sessionId, medio: medio)
        lock.save(flush: true)
        
        return lock
        
    }
	
	Lock lockFunctionalityByCountry(String username, String functionality, String sessionId, String country) throws SecLockException {
		
		def medios = Medio.findAllByCountry(country);
		
		def lockFound = Lock.findAllByMedioInList(medios instanceof Medio ? [medios]:medios)
		def locks = []
		if(lockFound.size() > 0){
			def funcConstraint = constraints.get(functionality) != null ? constraints.get(functionality) : []
			lockFound.each{lock ->
				if(funcConstraint.contains(lock.function) || lock.function == functionality ) throw new SecLockException("Resource locked by user: ${lockFound?.username}", lock);
			}
	
		}

		//lock all medios that has the selected country	
			medios.each{medio ->
				def lock = new Lock(username:username,function:functionality,sessionId:sessionId,medio:medio)
				lock.save()
				locks.add(lock)
			}
		
		return locks[0]
		
	}
    
    void unLockFunctionality(String sessionId) {
        
		Lock.executeUpdate("delete Lock l where l.sessionId = :sessionId", [sessionId:sessionId])
 
    }
    
}




