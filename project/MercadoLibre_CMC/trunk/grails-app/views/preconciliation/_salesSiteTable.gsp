  <table id="sales_table">
  <thead>
  <tr>
   <th><input type="button" class="ui-icon ui-icon-arrowthick-2-n-s" id="salesSiteFilter" alt="Filtrar Columnas"/></th>
   <g:sortableColumn action="listSalesSite" property="medio" title="${message(code: 'salesSite.medio', default: 'Medio')}" />   
   <g:sortableColumn action="listSalesSite" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" />
   <g:sortableColumn action="listSalesSite" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
   <g:sortableColumn action="listSalesSite" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
   <g:sortableColumn action="listSalesSite" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
   <g:sortableColumn action="listSalesSite" property="shareAmount" title="${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}" />
   <g:sortableColumn action="listSalesSite" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
   <g:sortableColumn action="listSalesSite" property="shareNumber" title="${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}" />
   <g:sortableColumn action="listSalesSite" property="shareQty" title="${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}" />
   <g:sortableColumn action="listSalesSite" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
   <g:sortableColumn action="listSalesSite" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
   <g:sortableColumn action="listSalesSite" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
   <g:sortableColumn action="listSalesSite" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
   <g:sortableColumn action="listSalesSite" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
      </tr>
      </thead>
      <tbody>
        <g:each in="${salesSiteInstanceList}" status="i" var="salesSiteInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:checkBox class="salesSite_check" name="saleSiteCheckbox_${salesSiteInstance.id}" value="${false}" /></td>
            <td>
              <g:hiddenField name="sales_${salesSiteInstance?.id}" value="${salesSiteInstance?.id}"/>
              ${salesSiteInstance?.medio?.id}
            </td>            
            <td>${salesSiteInstance?.registerType}</td>
            <td>${salesSiteInstance?.cardNumber}</td>
            <td>${salesSiteInstance?.transactionDate}</td>
            <td>${salesSiteInstance?.amount}</td>
            <td>${salesSiteInstance?.shareAmount}</td>
            <td>${salesSiteInstance?.authorization}</td>
            <td>${salesSiteInstance?.shareNumber}</td>
            <td>${salesSiteInstance?.shareQty}</td>
            <td>${salesSiteInstance?.customerId}</td>
            <td>${salesSiteInstance?.documentId}</td>
            <td>${salesSiteInstance?.tid}</td>
            <td>${salesSiteInstance?.nsu}</td>
            <td>${salesSiteInstance?.documentNumber}</td>
        </g:each>
      </tbody>
  </table>

