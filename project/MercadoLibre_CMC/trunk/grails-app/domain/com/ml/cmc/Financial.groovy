package com.ml.cmc

class Financial {

    Medio medio
    State state
    Long lot
    Double amount
    Double liq
    Long tid
    Long nsu
    Long ro
    Number cardLot
    Date paymentDate

    static constraints = {
    }
    
    static mappings = {
        table 'FINANCIERO'
        version false
        id column:'CD_RECIB_FINAN', generator:'assigned'
        //financialId column:''
        medio column:'CD_MEDIO'
        state column:'CD_ESTADO'
        lot column:'LOTE'
        amount column:'VL_IMPORTE'
        liq column:'VL_LIQUIDO'
        tid column:'TID'
        nsu column:'NSU'
        ro column:'RO'
        cardLot column:'LOTE_TARJETA'
        paymentDate column:'FC_PAGO'

    }
}
