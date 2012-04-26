package com.ml.cmc

class Medio {

    Long medioId
    String country
    String bank_account
    String card
    Long store
    String site
    
    static constraints = {
    }
    
    static mappings = {
        
        table 'MEDIO_COBRO'
        
        id generator:'assigned', name:'medioId'
        medioId column:'CD_MEDIO'
        country column:'TX_PAIS'
        bank_account column:'TX_BANCO_CTA'
        card column:'TX_TARJETA'
        store column:'CD_COMERCIO'
        site column:'TX_SITE'
        
    }
    
    
}
