  <table id="sales_table" class="display">
  <thead>
  <tr>
    <th></th> 
    <th>${message(code: 'salesSite.saleMl', default: 'Venta ML')}</th>
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
    <th>${message(code: 'salesSite.documentId', default: 'Doc.')}</th>
    <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
    <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
    <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
    <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
    <th>${message(code: 'salesSite.store', default: 'P. de Venta')}</th>
    <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
    <th>${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}</th>   
    <th>${message(code: 'salesSite.documentNumber', default: 'DNI')}</th>
    <th>${message(code: 'salesSite.payment', default: 'Pago')}</th>
    <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
    <th>${message(code: 'salesSite.origin', default: 'Origen')}</th>
   </tr>
 
  <tr>
    <th></th>
		<th>${message(code: 'salesSite.saleMl', default: 'Venta ML')}</th>
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
		<th>${message(code: 'salesSite.documentId', default: 'Doc.')}</th>
		<th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
		<th>${message(code: 'salesSite.tid', default: 'TID')}</th>
		<th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
		<th>${message(code: 'salesSite.ro', default: 'RO')}</th>
		<th>${message(code: 'salesSite.store', default: 'P. de Venta')}</th>
		<th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
		<th>${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}</th>   
		<th>${message(code: 'salesSite.documentNumber', default: 'DNI')}</th>
		<th>${message(code: 'salesSite.payment', default: 'Pago')}</th>
		<th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
		<th>${message(code: 'salesSite.origin', default: 'Origen')}</th>
   </tr>
  </thead>
  <tbody>
    <g:each in="${salesSiteInstanceList}" status="i" var="salesSiteInstance">
      <tr>
        <td><g:hiddenField name="sales_${salesSiteInstance?.id}" value="${salesSiteInstance?.id}"/></td>
        <td>
          ${salesSiteInstance?.saleMl}
        </td>            
        <td>${salesSiteInstance?.medio?.id}</td>
        <td>${salesSiteInstance?.state}</td>
        <td>${salesSiteInstance?.registerType}</td>
        <td>${salesSiteInstance?.lot}</td>
        <td>${salesSiteInstance?.cardNumber}</td>
        <td><g:formatDate date="${salesSiteInstance?.transactionDate}" formatName="default.date.format"/></td>
        <td><g:formatDate date="${salesSiteInstance?.paymentDate}" formatName="default.date.format"/></td>
        <td>${salesSiteInstance?.amount}</td>
        <td>${salesSiteInstance?.shareAmount}</td>
        <td>${salesSiteInstance?.authorization}</td>
        <td>${salesSiteInstance?.shareNumber}</td>
        <td>${salesSiteInstance?.shareQty}</td>
        <td>${salesSiteInstance?.liq}</td>
        <td>${salesSiteInstance?.customerId}</td>
        <td>${salesSiteInstance?.documentId}</td>
        <td>${salesSiteInstance?.receiptNumber}</td>
        <td>${salesSiteInstance?.tid}</td>
        <td>${salesSiteInstance?.nsu}</td>
        <td>${salesSiteInstance?.ro}</td>
        <td>${salesSiteInstance?.store}</td>
        <td>${salesSiteInstance?.cardLot}</td>
        <td>${salesSiteInstance?.uniqueRo}</td>
        <td>${salesSiteInstance?.documentNumber}</td>
        <td>${salesSiteInstance?.payment}</td>
        <td>${salesSiteInstance?.period}</td>
        <td>${salesSiteInstance?.origin}</td>
       </tr>
      </g:each>
     </tbody>
  </table>

