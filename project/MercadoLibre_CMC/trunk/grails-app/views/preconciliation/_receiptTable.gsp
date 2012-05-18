  <div id="filterReceiptColumns" style="display:none;">
  <table>
    <tr>
      <td><g:checkBox name="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
    </tr>
    <tr>
      <td><g:checkBox name="${message(code: 'salesSite.amount', default: 'Monto')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
    </tr>
    <tr>
      <td><g:checkBox name="${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.customerId', default: 'Cliente')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
    </tr>
    <tr>
      <td><g:checkBox name="${message(code: 'salesSite.documentId', default: 'Doc.')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.documentId', default: 'Doc.')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.tid', default: 'TID')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
      <td><g:checkBox name="${message(code: 'salesSite.nsu', default: 'TID')}" value="${false}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'NSU')}</td>
    </tr>                    
  </table>
  </div>    
  <br/><br/>
  <table id="receipt_table">
  <thead>
  <tr>
   <th><input type="button" class="ui-icon ui-icon-arrowthick-2-n-s" id="receiptFilter" /></th>
     <g:sortableColumn action="listReceipts" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" onclick("sortReceipt()")/>
     <g:sortableColumn action="listReceipts" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
     <g:sortableColumn action="listReceipts" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
     <g:sortableColumn action="listReceipts" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
     <g:sortableColumn action="listReceipts" property="sharesAmount" title="${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}" />
     <g:sortableColumn action="listReceipts" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
     <g:sortableColumn action="listReceipts" property="shareNumber" title="${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}" />
     <g:sortableColumn action="listReceipts" property="shareQty" title="${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}" />
     <g:sortableColumn action="listReceipts" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
     <g:sortableColumn action="listReceipts" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
     <g:sortableColumn action="listReceipts" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
     <g:sortableColumn action="listReceipts" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
     <g:sortableColumn action="listReceipts" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
      </tr>
      </thead>
      <tbody>
        <g:each in="${receiptInstanceList}" status="i" var="receiptInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:checkBox class="receipt_check" id="receiptCheckbox_${receiptInstance.id}" name="receiptCheckbox_${receiptInstance.id}" value="${false}" /></td>            
            <td>
            	<g:hiddenField name="receipt_${receiptInstance?.id}" value="${receiptInstance?.id}"/>
            	${receiptInstance?.registerType}
            </td>
            <td>${receiptInstance?.cardNumber}</td>
            <td>${receiptInstance?.transactionDate}</td>
            <td>${receiptInstance?.amount}</td>
            <td>${receiptInstance?.shareAmount}</td>
            <td>${receiptInstance?.authorization}</td>
            <td>${receiptInstance?.shareNumber}</td>
            <td>${receiptInstance?.shareQty}</td>
            <td>${receiptInstance?.customerId}</td>
            <td>${receiptInstance?.documentId}</td>
            <td>${receiptInstance?.tid}</td>
            <td>${receiptInstance?.nsu}</td>
            <td>${receiptInstance?.documentNumber}</td>
        </g:each>
      </tbody>
  </table>

