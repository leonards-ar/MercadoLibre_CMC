package com.ml.cmc

class Lock {

    String sessionId
    String username
    Medio medio
    String function
    
    static constraints = {
        
    }
    
    static mapping = {
        table 'LOCK_FUNCTIONALITY'
        id generator: 'assigned', name:'sessionId', type:'string'
        sessionId column:'session_id'
    }
    
    String toString() {
        return sessionId + '-' + username + '-' + function
    }
    
    Lock(String username, String sessionId, String function, Medio medio) {
        
        this.username = username 
        this.sessionId = sessionId
        this.function = function
        this.medio = medio
        
    }
    
}
