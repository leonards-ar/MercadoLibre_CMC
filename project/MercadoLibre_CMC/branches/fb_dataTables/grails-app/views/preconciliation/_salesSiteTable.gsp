  <table id="sales_table">
  <thead>
  <tr>
   <th><input type="button" class="ui-icon ui-icon-arrowthick-2-n-s" id="salesSiteFilter" alt="Filtrar Columnas"/></th>
   <g:sortableColumn action="listSalesSite" property="saleMl" title="${message(code: 'salesSite.saleMl', default: 'Venta ML')}" />
   <g:sortableColumn action="listSalesSite" property="medio" title="${message(code: 'salesSite.medio', default: 'Medio')}" />   
   <g:sortableColumn action="listSalesSite" property="state" title="${message(code: 'salesSite.state', default: 'Estado')}" />   
   <g:sortableColumn action="listSalesSite" property="registerType" title="${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}" />
   <g:sortableColumn action="listSalesSite" property="lot" title="${message(code: 'salesSite.lot', default: 'Lote')}" />   
   <g:sortableColumn action="listSalesSite" property="cardNumber" title="${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}" />
   <g:sortableColumn action="listSalesSite" property="transactionDate" title="${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}" />
   <g:sortableColumn action="listSalesSite" property="paymentDate" title="${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}" />
   <g:sortableColumn action="listSalesSite" property="amount" title="${message(code: 'salesSite.amount', default: 'Monto')}" />
   <g:sortableColumn action="listSalesSite" property="shareAmount" title="${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}" />
   <g:sortableColumn action="listSalesSite" property="authorization" title="${message(code: 'salesSite.authorization', default: 'Autorizacion')}" />
   <g:sortableColumn action="listSalesSite" property="shareNumber" title="${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}" />
   <g:sortableColumn action="listSalesSite" property="shareQty" title="${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}" />
   <g:sortableColumn action="listSalesSite" property="liq" title="${message(code: 'salesSite.liq', default: 'Liquidacion')}" />
   <g:sortableColumn action="listSalesSite" property="customerId" title="${message(code: 'salesSite.customerId', default: 'Cliente')}" />
   <g:sortableColumn action="listSalesSite" property="documentId" title="${message(code: 'salesSite.documentId', default: 'Doc.')}" />
   <g:sortableColumn action="listSalesSite" property="receiptNumber" title="${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}" />
   <g:sortableColumn action="listSalesSite" property="tid" title="${message(code: 'salesSite.tid', default: 'TID')}" />
   <g:sortableColumn action="listSalesSite" property="nsu" title="${message(code: 'salesSite.nsu', default: 'NSU')}" />
   <g:sortableColumn action="listSalesSite" property="ro" title="${message(code: 'salesSite.ro', default: 'RO')}" />
   <g:sortableColumn action="listSalesSite" property="store" title="${message(code: 'salesSite.store', default: 'P. de Venta')}" />
   <g:sortableColumn action="listSalesSite" property="cardLot" title="${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}" />
   <g:sortableColumn action="listSalesSite" property="uniqueRo" title="${message(code: 'salesSite.uniqueRo', default: 'RO Unico')}" />   
   <g:sortableColumn action="listSalesSite" property="documentNumber" title="${message(code: 'salesSite.documentNumber', default: 'DNI')}" />
   <g:sortableColumn action="listSalesSite" property="payment" title="${message(code: 'salesSite.payment', default: 'Pago')}" />
   <g:sortableColumn action="listSalesSite" property="period" title="${message(code: 'salesSite.period', default: 'Periodo')}" />
      </tr>
    <tr>
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
     <th></th>
     <th></th>     
    </tr>
      
      </thead>
      <tbody>
        <g:each in="${salesSiteInstanceList}" status="i" var="salesSiteInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:hiddenField name="sales_${salesSiteInstance?.id}" value="${salesSiteInstance?.id}"/></td>
            <td>${salesSiteInstance?.saleMl}</td>            
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
           </tr>
        </g:each>
      </tbody>
  </table>

