package com.ml.cmc

import java.util.Date;

class Receipt {

    Medio medio
    State state
    RegisterType registerType
    Long lot
    String cardNumber
    Date transactionDate
    Date paymentDate
    Double amount
    Double secQuotesAmount
    String authorization
    Integer quotaNumber
    Integer quotaQty
    String liq
    String customerId
    String documentId
    String receiptNumber
    Long tid
    Long nsu
    Long ro
    Long store
    String cardLot
    String uniqueRo
    String documentNumber
    Payment payment

    
    static constraints = {
        payment(nullable:true)
    }
    
    static mapping = {
        
        table 'RECIBOS'
        version false
        id column:'CD_RECIBO', generator:'assigned'
        medio column:'CD_MEDIO'
        state column:'CD_ESTADO'
        registerType column:'CD_TIPO_REGISTRO'
        lot column:'LOTE'
        cardNumber column:'NU_TARJETA'
        transactionDate column:'FC_OPERACION'
        paymentDate column:'FC_PAGO'
        amount column:'VL_IMPORTE'
        secQuotesAmount column:'VL_SEG_CUOTA'
        authorization column:'NU_AUTORIZACION'
        quotaNumber column:'NU_CUOTA'
        quotaQty column:'NU_CANT_CUOTAS'
        liq column:'NU_LIQUIDACION'
        customerId column:'CUST_ID'
        documentId column:'DOC_ID'
        receiptNumber column:'NU_RECIBO'
        tid column:'TID'
        nsu column:'NSU'
        ro column:'RO'
        store column:'NRO_COMERCIO'
        cardLot column:'LOTE_TARJETA'
        uniqueRo column:'NU_UNICO_RO'
        documentNumber column:'DOC_NUMBER'
        payment column:'PAYMENT_ID'
    }

}
