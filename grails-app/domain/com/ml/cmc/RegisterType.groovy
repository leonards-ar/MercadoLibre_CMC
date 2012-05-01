package com.ml.cmc

class RegisterType {

    String value
     
    static constraints = {
        
    }
    
    static mapping = {
        table 'LKP_TIPO_REGISTRO'
        version false
        id column:'CD_TIPO_REGISTRO', generator:'assigned'
        value column:'TX_TIPO_REGISTRO'
        
    }
    
    String toString() {
        
        return value
        
    }
}
