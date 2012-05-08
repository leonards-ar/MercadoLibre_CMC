    
  <table id="receipt_table">
  <thead>
  <tr>
   <th></th>
     <g:sortableColumn action="listReceipts" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" onclick("sortReceipt()")/>
     <g:sortableColumn action="listReceipts" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
     <g:sortableColumn action="listReceipts" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
     <g:sortableColumn action="listReceipts" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
     <g:sortableColumn action="listReceipts" property="secQuotesAmount" title="${message(code: 'salesSite.secQuotesAmount', default: 'Cuotas Sec.')}" />
     <g:sortableColumn action="listReceipts" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
     <g:sortableColumn action="listReceipts" property="quotaNumber" title="${message(code: 'salesSite.quotaNumber', default: 'Nro Cuota')}" />
     <g:sortableColumn action="listReceipts" property="quotaQty" title="${message(code: 'salesSite.quotaQty', default: 'Cant. Cuotas')}" />
     <g:sortableColumn action="listReceipts" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
     <g:sortableColumn action="listReceipts" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
     <g:sortableColumn action="listReceipts" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
     <g:sortableColumn action="listReceipts" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
     <g:sortableColumn action="listReceipts" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
      </tr>
      </thead>
      <tbody>
        <g:each in="${receiptInstanceList}" status="i" var="receiptInstance">
          <tr id="${receiptInstance.id}">
            <td><g:checkBox class="receipt_check" id="receiptCheckbox_${receiptInstance.id}" name="receiptCheckbox_${receiptInstance.id}" value="${false}" /></td>            
            <td>
            	<g:hiddenField name="receipt_${receiptInstance?.id}" value="${formatNumber(number:receiptInstance?.id, format:'###.##')}"/>
            	${receiptInstance?.registerType}
            </td>
            <td>${receiptInstance?.cardNumber}</td>
            <td>${receiptInstance?.transactionDate}</td>
            <td>${receiptInstance?.amount}</td>
            <td>${receiptInstance?.secQuotesAmount}</td>
            <td>${receiptInstance?.authorization}</td>
            <td>${receiptInstance?.quotaNumber}</td>
            <td>${receiptInstance?.quotaQty}</td>
            <td>${receiptInstance?.customerId}</td>
            <td>${receiptInstance?.documentId}</td>
            <td>${receiptInstance?.tid}</td>
            <td>${receiptInstance?.nsu}</td>
            <td>${receiptInstance?.documentNumber}</td>
        </g:each>
      </tbody>
  </table>

