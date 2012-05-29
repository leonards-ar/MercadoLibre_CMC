  <table id="receipt_table" class="display">
  <thead>
  <tr>
     <th><input type="button" class="ui-icon ui-icon-arrowthick-2-n-s" id="receiptFilter" /></th>
     <g:sortableColumn action="listReceipts" property="medio" title="${message(code: 'salesSite.medio', default: 'Medio')}"/>
     <g:sortableColumn action="listReceipts" property="state" title="${message(code: 'salesSite.state', default: 'Estado')}" onclick("sortReceipt()")/>  
     <g:sortableColumn action="listReceipts" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}"/>
     <g:sortableColumn action="listReceipts" property="lot" title="${message(code: 'salesSite.lot', default: 'Lote')}"/>
     <g:sortableColumn action="listReceipts" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
     <g:sortableColumn action="listReceipts" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
     <g:sortableColumn action="listReceipts" property="paymentDate" title="${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}" />
     <g:sortableColumn action="listReceipts" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
     <g:sortableColumn action="listReceipts" property="shareAmount" title="${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}" />
     <g:sortableColumn action="listReceipts" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
     <g:sortableColumn action="listReceipts" property="shareNumber" title="${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}" />
     <g:sortableColumn action="listReceipts" property="shareQty" title="${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}" />
     <g:sortableColumn action="listReceipts" property="liq" title="${message(code: 'salesSite.liq', default: 'Liquidacion')}" />
     <g:sortableColumn action="listReceipts" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
     <g:sortableColumn action="listReceipts" property="documentId" title="${message(code: 'salesSite.documentId', default: 'DNI')}" />     
     <g:sortableColumn action="listReceipts" property="receiptNumber" title="${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}" />
     <g:sortableColumn action="listReceipts" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
     <g:sortableColumn action="listReceipts" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />          
     <g:sortableColumn action="listReceipts" property="ro" title="${message(code: 'salesSite.ro', default: 'RO')}" />
     <g:sortableColumn action="listReceipts" property="store" title="${message(code: 'salesSite.store', default: 'P. de Venta')}" />
     <g:sortableColumn action="listReceipts" property="cardLot" title="${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}" />
     <g:sortableColumn action="listReceipts" property="uniqueRo" title="${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}" />
     <g:sortableColumn action="listReceipts" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}" />
     <g:sortableColumn action="listReceipts" property="period" title="${message(code: 'salesSite.period', default: 'Periodo')}" />
	  </tr>
	  <!-- tr>
     <th></th>
     <th></th>
     <th></th>  
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>     
     <th></th>
     <th></th>
     <th></th>          
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
     <th></th>
    </tr-->
	  
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

