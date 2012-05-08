package com.ml.cmc

class State {
    String value
     
    static constraints = {
        
    }
    
    static mapping = {
        table 'LKP_ESTADOS'
        version false
        id column:'CD_ESTADO', generator:'assigned'
        value column:'TX_ESTADO'
        
    }
    
    String toString() {
        return value
    }
}
