package com.ml.cmc.service

import com.ml.cmc.Lock
import com.ml.cmc.exception.SecLockException

class SecurityLockService {

    static transactional = true

    Lock lockFunctionality(String username, String functionality, String sessionId) {
        
        Lock lockFound = Lock.findByFunction(functionality)
        if(lockFound != null){
            
          throw new SecLockException("Resource locked by user: ${lockFound?.username}", lockFound);
        }
        
        def locker = new Lock()
        locker.sessionId = sessionId
        locker.username = username
        locker.function = functionality
        
        locker.save(flush: true)
        
        return locker
        
    }
    
    void unLockFunctionality(String sessionId) {
        
        Lock locker = Lock.findBySessionId(sessionId)
        if(locker != null){
            locker.delete(flush:true)
        }
    }

}
