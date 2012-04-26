package com.ml.cmc.service

import com.ml.cmc.Lock
import com.ml.cmc.Medio
import com.ml.cmc.constants.Constant
import com.ml.cmc.exception.SecLockException

class SecurityLockService {

    static transactional = true
    
    private static Map constraints = [
        (Constant.FUNC_PRECONCILIATE):[(Constant.FUNC_COMPENSATE)], 
        (Constant.FUNC_CONCILIATE):[(Constant.FUNC_DESPRECONCILIATE)],
        (Constant.FUNC_COMPENSATE):[(Constant.FUNC_PRECONCILIATE)],
        (Constant.FUNC_DESPRECONCILIATE):[(Constant.FUNC_CONCILIATE)]
        ]

    Lock lockFunctionality(String username, String functionality, String sessionId, Medio medio) {
        
        Lock lockFound = Lock.findByFunction(functionality)
        if(lockFound == null){
            Lock lock = new Lock(username:username, function:functionality, sessionId: sessionId, medio: medio)
            lock.save(flush: true)
            
            return lock
        }
        
        def funcConstraint = constraints.get(functionality) != null ? constraints.get(functionality) : []  
        if(funcConstraint.contains(lockFound.function)) throw new SecLockException("Resource locked by user: ${lockFound?.username}", lockFound);
        
        Lock lock = new Lock(username:username, function:functionality, sessionId: sessionId, medio: medio)
        lock.save(flush: true)
        
        return lock
        
    }
    
    void unLockFunctionality(String sessionId) {
        
        Lock locker = Lock.findBySessionId(sessionId)
        if(locker != null){
            locker.delete(flush:true)
        }
    }
    
}
