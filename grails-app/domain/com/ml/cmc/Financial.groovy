package com.ml.cmc

class Financial {

    Long financialId
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

        id generator:'assigned', name:'financialId'
        financialId column:'CD_RECIB_FINAN'
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
