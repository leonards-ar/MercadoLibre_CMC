package com.ml.cmc

class State {
    Long id
    String value
     
    static constraints = {
        
    }
    
    static mapping = {
        table 'LKP_ESTADOS'
        id generator:'assigned', name:'id'
        id column:'CD_ESTADO'
        value column:'TX_ESTADO'
        
    }
}
