  <table id="preconciliate_table">
  <thead>
  <tr>
    <th>R<g:message code="salesSite.registerType", default: "Tipo de Registro"/></th>
    <th>R<g:message code="salesSite.cardNumber", default: "Nro Tarjeta"/></th>
    <th>R<g:message code="salesSite.transDate", default: "Fecha de la Transaccion"/></th>
    <th>R<g:message code="salesSite.amount", default: "Monto"/></th>
    <th>R<g:message code="salesSite.secQuotesAmount", default: "Cuotas Sec."/></th>
    <th>R<g:message code="salesSite.authorization", default: "Autorizacion"/></th>
    <th>R<g:message code="salesSite.quotaNumber", default: "Nro Cuota"/></th>
    <th>R<g:message code="salesSite.quotaQty", default: "Cant. Cuotas"/></th>
    <th>R<g:message code="salesSite.customerId", default: "Cliente"/></th>
    <th>R<g:message code="salesSite.documentId", default: "Doc."/></th>
    <th>R<g:message code="salesSite.tid", default: "TID"/></th>
    <th>R<g:message code="salesSite.nsu", default: "NSU"/></th>
    <th>R<g:message code="salesSite.documentNumber", default: "DNI"/></th>
    <th>S<g:message code="salesSite.medio", default: "Medio"/></th>
    <th>S<g:message code="salesSite.registerType", default: "Tipo de Registro"/></th>
    <th>S<g:message code="salesSite.cardNumber", default: "Nro Tarjeta"/></th>
    <th>S<g:message code="salesSite.transDate", default: "Fecha de la Transaccion"/></th>
    <th>S<g:message code="salesSite.amount", default: "Monto"/></th>
    <th>S<g:message code="salesSite.secQuotesAmount", default: "Cuotas Sec."/></th>
    <th>S<g:message code="salesSite.authorization", default: "Autorizacion"/></th>
    <th>S<g:message code="salesSite.quotaNumber", default: "Nro Cuota"/></th>
    <th>S<g:message code="salesSite.quotaQty", default: "Cant. Cuotas"/></th>
    <th>S<g:message code="salesSite.customerId", default: "Cliente"/></th>
    <th>S<g:message code="salesSite.documentId", default: "Doc."/></th>
    <th>S<g:message code="salesSite.tid", default: "TID"/></th>
    <th>S<g:message code="salesSite.nsu", default: "NSU"/></th>
    <th>S<g:message code="salesSite.documentNumber", default: "DNI"/></th>
  </tr>
  </thead>
  <tbody>
	  <g:each in="${receiptInstanceList}" status="i" var="receiptInstance">
	     <tr class="${(i % 2) == 0 ? 'odd' : 'even'} id="${receiptInstance.id}>
 	        <!-- td><g:checkBox name="receiptCheckbox_${receiptInstance.id}" value="${false}" /></td-->            
            <td>
   	        	<g:hiddenField name="receipt_${receiptIds[i]}" value="${formatNumber(number:receiptIds[${i}], format:'###.##')}"/>
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

