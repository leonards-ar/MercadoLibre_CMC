package com.ml.cmc

class Payment {
    String source
    String statusConoc
    String error
    Date timestamp
    Long operation
    String operationType
    String site
    String status
    String statusDetail
    String paymentMethod
    String paymentMethodType
    Date createdDate
    Date createdApproved
    Date lastModified
    Long installment
    String currency
    Long collector
    String collectorNickname
    Long payer
    String payerNickname
    String payerIdentificationNumber
    String payerIdentificationType
    Double transactionAmount
    Double totalPaidAmount
    Double financeCharge
    Double mercadoPagoFee
    Double netReceivedAmount
    Double buyerFee
    String marketplace
    Long payment
    Date authorizationDate
    String authorizationCode
    String numberTc
    String sapStatus
    Long sapId
    Long moveId
    Date moveDate
    Date sapDate
    Long nsuNumber
    Long tidNumber
    Long lote
    String transMlMp
    String paymentReference
    String opeMlDescription
    Double pricing
    String walletFlag
    String customerTest
    String bankPayment
    String paymentMethodModeDescription
    String opeMlFlag
    String customerStatus
    String documentStatus
    String documentNumber
    Long docId
    Date docDate
    Long transactonIdTc
    String transactionStatus
    
    static constraints = {
    }
    
    static mapping = {
        table 'PAYMENTS'
        version false
        
        id column:'CONC_PAY_ID' 
        source column:'SOURCE_ID'
        statusConoc column:'STATUS_CONC'
        error column:'ERROR_DETAILS'
        timestamp column:'TIMESTAMP'
        operation column:'OPERATION_ID'
        operationType column:'OPERATION_TYPE'
        site column:'SITE_ID'
        status column:'STATUS'
        statusDetail column:'STATUS_DETAIL'
        paymentMethod column:'PAYMENT_METHOD_ID'
        paymentMethodType column:'PAYMENT_METHOD_TYPE '
        createdDate column:'DATE_CREATED'
        createdApproved column:'DATE_APPROVED'
        lastModified column:'LAST_MODIFIED'
        installment column:'INSTALLMENT'
        currency column:'CURRENCY_ID'
        collector column:'COLLECTOR_ID'
        collectorNickname column:'COLLECTOR_NICKNAME'
        payer column:'PAYER_ID'
        payerNickname column:'PAYER_NICKNAME'
        payerIdentificationNumber column:'PAYER_IDENTIFICATION_NUMBER'
        payerIdentificationType column:'PAYER_IDENTIFICATION_TYPE'
        transactionAmount column:'TRANSACTION_AMOUNT'
        totalPaidAmount column:'TOTAL_PAID_AMOUNT'
        financeCharge column:'FINANCE_CHARGE'
        mercadoPagoFee column:'MERCADOPAGO_FEE'
        netReceivedAmount column:'NET_RECEIVED_AMOUNT'
        buyerFee column:'BUYER_FEE'
        marketplace column:'MARKETPLACE'
        payment column:'PAYMENT_ID'
        authorizationDate column:'AUTHORIZATION_DATE'
        authorizationCode column:'AUTHORIZATION_CODE'
        numberTc column:'NUMBER_TC'
        sapStatus column:'SAP_STATUS'
        sapId column:'SAP_ID'
        moveId column:'MOVE_ID'
        moveDate column:'MOVE_DATE'
        sapDate column:'SAP_DATE'
        nsuNumber column:'NSU_NUMBER'
        tidNumber column:'TID_NUMBER'
        lote column:'NRO_LOTE'
        transMlMp column:'TRANS_ML_MP'
        paymentReference column:'PAY_REFERENCE'
        opeMlDescription column:'OPE_MLIBRE_DESCRIPTION'
        pricing column:'PRICING'
        walletFlag column:'WALLET_FLAG'
        customerTest column:'CUST_TEST'
        bankPayment column:'PAYMENT_BANK_ID'
        paymentMethodModeDescription column:'PAY_METHOD_MODE_DESC'
        opeMlFlag column:'OPE_MLIBRE_FLAG'
        customerStatus column:'CUST_STATUS'
        documentStatus column:'DOC_STATUS'
        documentNumber column:'DOC_NUMBER'
        docId column:'DOC_ID'
        docDate column:'DOC_DT'
        transactonIdTc column:'TRANSACTION_ID_TC'
        transactionStatus column:'TRANSACTION_STATUS'

    }
    
}
