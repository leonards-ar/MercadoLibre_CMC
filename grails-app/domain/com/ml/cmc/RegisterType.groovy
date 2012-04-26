package com.ml.cmc

class RegisterType {

    Long id
    String value
     
    static constraints = {
        
    }
    
    static mapping = {
        table 'LKP_TIPO_REGISTRO'
        id generator:'assigned', name:'id'
        id column:'CD_TIPO_REGISTRO'
        value column:'TX_TIPO_REGISTRO'
        
    }
}
