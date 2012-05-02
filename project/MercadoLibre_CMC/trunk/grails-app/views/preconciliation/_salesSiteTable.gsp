  <table id="sales_table">
  <thead>
  <tr>
   <th></th>
     <g:sortableColumn action="listSalesSite"  params="${params}" property="medio" title="${message(code: 'salesSite.medio', default: 'Medio')}" />   
     <g:sortableColumn action="listSalesSite"  params="${params}" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" />
     <g:sortableColumn action="listSalesSite" params="${params}" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
     <g:sortableColumn action="listSalesSite" params="${params}" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="secQuotesAmount" title="${message(code: 'salesSite.secQuotesAmount', default: 'Cuotas Sec.')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="quotaNumber" title="${message(code: 'salesSite.quotaNumber', default: 'Nro Cuota')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="quotaQty" title="${message(code: 'salesSite.quotaQty', default: 'Cant. Cuotas')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
	 <g:sortableColumn action="listSalesSite" params="${params}" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
      </tr>
      </thead>
      <tbody>
        <g:each in="${salesSiteInstanceList}" status="i" var="salesSiteInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'} id="${salesSiteInstance.id}>
            <td><g:checkBox name="saleSiteCheckbox_${salesSiteInstance.id}" value="${false}" /></td>
            <td>
            	<g:hiddenField name="sales_${salesSiteInstance?.id}" value="${formatNumber(number:salesSiteInstance?.id, format:'###.##')}"/>
            	${salesSiteInstance?.medio?.id}
            </td>            
            <td>${salesSiteInstance?.registerType}</td>
            <td>${salesSiteInstance?.cardNumber}</td>
            <td>${salesSiteInstance?.transactionDate}</td>
            <td>${salesSiteInstance?.amount}</td>
            <td>${salesSiteInstance?.secQuotesAmount}</td>
            <td>${salesSiteInstance?.authorization}</td>
            <td>${salesSiteInstance?.quotaNumber}</td>
            <td>${salesSiteInstance?.quotaQty}</td>
            <td>${salesSiteInstance?.customerId}</td>
            <td>${salesSiteInstance?.documentId}</td>
            <td>${salesSiteInstance?.tid}</td>
            <td>${salesSiteInstance?.nsu}</td>
            <td>${salesSiteInstance?.documentNumber}</td>
        </g:each>
      </tbody>
  </table>

