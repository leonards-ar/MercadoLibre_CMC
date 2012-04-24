package com.ml.cmc

class Lock {

    String sessionId
    String username
    String function 
    
    static constraints = {
        
    }
    
    static mapping = {
        table 'lock_functionality'
        id generator: 'assigned', name:'sessionId', type:'string'
        sessionId column:'session_id'
    }
    
    String toString() {
        return sessionId + '-' + username + '-' + function
    }
    
}
