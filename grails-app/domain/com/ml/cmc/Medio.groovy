package com.ml.cmc

class Medio {

    String country
    String bank
    String card
    Long store
    String site
    
    static constraints = {
    }
    
    static mappings = {
        
        table 'MEDIO_COBRO'
        version false
        id column:'CD_MEDIO', generator:'assigned', name:'medioId'
        country column:'TX_PAIS'
        bank column:'TX_BANCO_CTA'
        card column:'TX_TARJETA'
        store column:'CD_COMERCIO'
        site column:'TX_SITE'
        
    }
    
    
}
