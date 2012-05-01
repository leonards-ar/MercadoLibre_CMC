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
        medio column:'cd_medio'
        function column:'function'
    }
    
    String toString() {
        return sessionId + '-' + username + '-' + function
    }
    
}
