package com.ml.cmc

class Lock implements Serializable{

    String sessionId
    String username
    Medio medio
    String function
    
    static constraints = {
        
    }
    
    static mapping = {
        table 'A_LOCK_FUNCTIONALITY'
		version false
		
        id composite:['sessionId', 'medio']
        sessionId column:'session_id'
        medio column:'cd_medio'
        function column:'funcionalidad'
    }
    
    String toString() {
        return sessionId + '-' + username + '-' + function
    }
    
}
