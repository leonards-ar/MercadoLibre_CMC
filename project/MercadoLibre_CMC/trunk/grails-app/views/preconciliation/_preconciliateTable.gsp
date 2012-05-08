  <table id="preconciliate_table" width="100%" align="center">
  <thead>
  <tr>
    <th>R<g:message code="salesSite.registerType" default="Tipo de Registro"/></th>
    <th>R<g:message code="salesSite.cardNumber" default="Nro Tarjeta"/></th>
    <th>R<g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></th>
    <th>R<g:message code="salesSite.amount" default="Monto"/></th>
    <th>R<g:message code="salesSite.secQuotesAmount" default="Cuotas Sec."/></th>
    <th>R<g:message code="salesSite.authorization" default="Autorizacion"/></th>
    <th>R<g:message code="salesSite.quotaNumber" default="Nro Cuota"/></th>
    <th>R<g:message code="salesSite.quotaQty" default="Cant. Cuotas"/></th>
    <th>R<g:message code="salesSite.customerId" default="Cliente"/></th>
    <th>R<g:message code="salesSite.documentId" default="Doc."/></th>
    <th>R<g:message code="salesSite.tid" default="TID"/></th>
    <th>R<g:message code="salesSite.nsu" default="NSU"/></th>
    <th>R<g:message code="salesSite.documentNumber" default="DNI"/></th>
    <th>S<g:message code="salesSite.medio" default="Medio"/></th>
    <th>S<g:message code="salesSite.registerType" default="Tipo de Registro"/></th>
    <th>S<g:message code="salesSite.cardNumber" default="Nro Tarjeta"/></th>
    <th>S<g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></th>
    <th>S<g:message code="salesSite.amount" default="Monto"/></th>
    <th>S<g:message code="salesSite.secQuotesAmount" default="Cuotas Sec."/></th>
    <th>S<g:message code="salesSite.authorization" default="Autorizacion"/></th>
    <th>S<g:message code="salesSite.quotaNumber" default="Nro Cuota"/></th>
    <th>S<g:message code="salesSite.quotaQty" default="Cant. Cuotas"/></th>
    <th>S<g:message code="salesSite.customerId" default="Cliente"/></th>
    <th>S<g:message code="salesSite.documentId" default="Doc."/></th>
    <th>S<g:message code="salesSite.tid" default="TID"/></th>
    <th>S<g:message code="salesSite.nsu" default="NSU"/></th>
    <th>S<g:message code="salesSite.documentNumber" default="DNI"/></th>
  </tr>
  </thead>
	  <tbody>
		  <g:each in="${preconciliationInstancelist}" status="i" var="salesSiteReceiptInstance">
		     <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	 	        <!-- td><g:checkBox name="receiptCheckbox_${salesSiteReceiptInstance?.receipt?.id}" value="${false}" /></td-->            
	            <td>
	   	        	<g:hiddenField name="receiptIds[${i}]" value="${formatNumber(number:salesSiteReceiptInstance?.receipt?.id, format:'###.##')}"/>
	   	        	<g:hiddenField name="salesSiteIds[${i}]" value="${formatNumber(number:salesSiteReceiptInstance?.salesSite?.id, format:'###.##')}"/>
            	${salesSiteReceiptInstance?.receipt?.registerType}
            </td>
            <td>${salesSiteReceiptInstance?.receipt?.cardNumber}</td>
            <td>${salesSiteReceiptInstance?.receipt?.transactionDate}</td>
            <td>${salesSiteReceiptInstance?.receipt?.amount}</td>
            <td>${salesSiteReceiptInstance?.receipt?.secQuotesAmount}</td>
            <td>${salesSiteReceiptInstance?.receipt?.authorization}</td>
            <td>${salesSiteReceiptInstance?.receipt?.quotaNumber}</td>
            <td>${salesSiteReceiptInstance?.receipt?.quotaQty}</td>
            <td>${salesSiteReceiptInstance?.receipt?.customerId}</td>
            <td>${salesSiteReceiptInstance?.receipt?.documentId}</td>
            <td>${salesSiteReceiptInstance?.receipt?.tid}</td>
            <td>${salesSiteReceiptInstance?.receipt?.nsu}</td>
            <td>${salesSiteReceiptInstance?.receipt?.documentNumber}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.medio?.id}</td>            
            <td>${salesSiteReceiptInstance?.salesSite?.registerType}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.cardNumber}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.transactionDate}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.amount}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.secQuotesAmount}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.authorization}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.quotaNumber}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.quotaQty}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.customerId}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.documentId}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.tid}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.nsu}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.documentNumber}</td>            
        </g:each>
      </tbody>
  </table>

