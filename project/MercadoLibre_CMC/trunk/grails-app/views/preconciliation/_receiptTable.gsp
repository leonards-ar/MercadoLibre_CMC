<div id="receipt_filtered" style="width:450;height:200;overflow:auto;">
    
  <table id="receipt_table">
  <thead>
  <tr>
   <th></th>
     <g:sortableColumn action="listReceipts" params="${params}" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" onclick("sortReceipt()")/>
     <g:sortableColumn action="listReceipts" params="${params}" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="secQuotesAmount" title="${message(code: 'salesSite.secQuotesAmount', default: 'Cuotas Sec.')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="quotaNumber" title="${message(code: 'salesSite.quotaNumber', default: 'Nro Cuota')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="quotaQty" title="${message(code: 'salesSite.quotaQty', default: 'Cant. Cuotas')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
     <g:sortableColumn action="listReceipts" params="${params}" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
      </tr>
      </thead>
      <tbody>
        <g:each in="${receiptInstanceList}" status="i" var="receiptInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'} id="${receiptInstance.id}>
            <td><g:checkBox name="receiptCheckbox_${receiptInstance.id}" value="${false}" /></td>            
            <td>${receiptInstance?.registerType}</td>
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
</div>
