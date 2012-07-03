  <table id="conciliate_table" width="100%" align="center">
  <thead>
  <tr>
    <th>R<g:message code="salesSite.registerType" default="Tipo de Registro"/></th>
    <th>R<g:message code="salesSite.cardNumber" default="Nro Tarjeta"/></th>
    <th>R<g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></th>
    <th>R<g:message code="salesSite.amount" default="Monto"/></th>
    <th>R<g:message code="salesSite.shareAmount" default="Monto Cuota"/></th>
    <th>R<g:message code="salesSite.authorization" default="Autorizacion"/></th>
    <th>R<g:message code="salesSite.shareNumber" default="Nro Cuota"/></th>
    <th>R<g:message code="salesSite.shareQty" default="Cant. Cuotas"/></th>
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
    <th>S<g:message code="salesSite.shareAmount" default="Monto Cuota"/></th>
    <th>S<g:message code="salesSite.authorization" default="Autorizacion"/></th>
    <th>S<g:message code="salesSite.shareNumber" default="Nro Cuota"/></th>
    <th>S<g:message code="salesSite.shareQty" default="Cant. Cuotas"/></th>
    <th>S<g:message code="salesSite.customerId" default="Cliente"/></th>
    <th>S<g:message code="salesSite.documentId" default="Doc."/></th>
    <th>S<g:message code="salesSite.tid" default="TID"/></th>
    <th>S<g:message code="salesSite.nsu" default="NSU"/></th>
    <th>S<g:message code="salesSite.documentNumber" default="DNI"/></th>
  </tr>
  </thead>
	  <tbody>
		  <g:each in="${conciliationInstancelist}" status="i" var="salesSiteReceiptInstance">
		     <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	 	        <!-- td><g:checkBox name="receiptCheckbox_${salesSiteReceiptInstance?.receipt?.id}" value="${false}" /></td-->            
	            <td>
	   	        	<g:hiddenField name="receiptIds[${i}]" value="${salesSiteReceiptInstance?.receipt?.id}"/>
	   	        	<g:hiddenField name="salesSiteIds[${i}]" value="${salesSiteReceiptInstance?.salesSite?.id}"/>
            	${salesSiteReceiptInstance?.receipt?.registerType}
            </td>
            <td>${salesSiteReceiptInstance?.receipt?.cardNumber}</td>
            <td>${salesSiteReceiptInstance?.receipt?.transactionDate}</td>
            <td>${salesSiteReceiptInstance?.receipt?.amount}</td>
            <td>${salesSiteReceiptInstance?.receipt?.shareAmount}</td>
            <td>${salesSiteReceiptInstance?.receipt?.authorization}</td>
            <td>${salesSiteReceiptInstance?.receipt?.shareNumber}</td>
            <td>${salesSiteReceiptInstance?.receipt?.shareQty}</td>
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
            <td>${salesSiteReceiptInstance?.salesSite?.shareAmount}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.authorization}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.shareNumber}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.shareQty}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.customerId}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.documentId}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.tid}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.nsu}</td>
            <td>${salesSiteReceiptInstance?.salesSite?.documentNumber}</td>            
        </g:each>
      </tbody>
  </table>

