  <table id="receipt_table" class="display">
  <thead>
  <tr>
     <th></th>
      <th>${message(code: 'salesSite.medio', default: 'Medio')}</th>
      <th>${message(code: 'salesSite.state', default: 'Estado')}</th>  
      <th>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</th>
      <th>${message(code: 'salesSite.lot', default: 'Lote')}</th>
      <th>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
      <th>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
      <th>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</th>
      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>
      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
      <th>${message(code: 'salesSite.liq', default: 'Liquidacion')}</th>
      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
      <th>${message(code: 'salesSite.documentId', default: 'DNI')}</th>     
      <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>          
      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
      <th>${message(code: 'salesSite.store', default: 'P. de Venta')}</th>
      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
      <th>${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}</th>
      <th>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
    </tr>
    <tr>
     <th></th>
      <th>${message(code: 'salesSite.medio', default: 'Medio')}</th>
      <th>${message(code: 'salesSite.state', default: 'Estado')}</th>  
      <th>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</th>
      <th>${message(code: 'salesSite.lot', default: 'Lote')}</th>
      <th>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
      <th>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
      <th>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</th>
      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>
      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
      <th>${message(code: 'salesSite.liq', default: 'Liquidacion')}</th>
      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
      <th>${message(code: 'salesSite.documentId', default: 'DNI')}</th>     
      <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>          
      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
      <th>${message(code: 'salesSite.store', default: 'P. de Venta')}</th>
      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
      <th>${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}</th>
      <th>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
    </tr>
    </thead>
    <tbody>
      <g:each in="${receiptInstanceList}" status="i" var="receiptInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <td><g:hiddenField name="receipt_${receiptInstance?.id}" value="${receiptInstance?.id}"/></td>
          <td>${receiptInstance?.medio.id}</td>
          <td>${receiptInstance?.state}</td>
          <td>${receiptInstance?.registerType}</td>
          <td>${receiptInstance?.lot}</td>
          <td>${receiptInstance?.cardNumber}</td>
          <td><g:formatDate date="${receiptInstance?.transactionDate}" formatName="default.date.format"/></td>
          <td><g:formatDate date="${receiptInstance?.paymentDate}" formatName="default.date.format"/></td>
          <td>${receiptInstance?.amount}</td>
          <td>${receiptInstance?.shareAmount}</td>          
          <td>${receiptInstance?.authorization}</td>
          <td>${receiptInstance?.shareNumber}</td>
          <td>${receiptInstance?.shareQty}</td>
          <td>${receiptInstance?.liq}</td>
          <td>${receiptInstance?.customerId}</td>
          <td>${receiptInstance?.documentId}</td>
          <td>${receiptInstance?.receiptNumber}</td>          
          <td>${receiptInstance?.tid}</td>
          <td>${receiptInstance?.nsu}</td>
          <td>${receiptInstance?.ro}</td>
          <td>${receiptInstance?.store}</td>
          <td>${receiptInstance?.cardLot}</td>
          <td>${receiptInstance?.uniqueRo}</td>
          <td>${receiptInstance?.documentNumber}</td>
          <td>${receiptInstance?.period}</td>           
        </tr>            
      </g:each>
  </tbody>
  </table>

