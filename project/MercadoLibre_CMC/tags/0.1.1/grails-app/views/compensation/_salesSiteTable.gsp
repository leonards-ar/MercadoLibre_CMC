<div style="width:100%;height:140;border:1px solid #ccc;position:relative">
  <div class="prefilterFields">
    <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></div>
    <g:message code="salesSite.from" default="Desde"/>: <g:textField name="fromSalesTransDate" id="fromSalesTransDate" value="${fromSalesTransDate}" size="10" />
    <g:message code="salesSite.to" default="Hasta"/>: <g:textField name="toSalesTransDate" id="toSalesTransDate" value="${toSalesTransDate}" size="10" />
    
    <div><g:message code="compensation.amount1" default="Monto"/></div>
    <g:message code="salesSite.minAmount" default="Monto Minimo"/>: <g:textField name="minSalesAmount" id="minSalesAmount" value="${minSalesAmount}" size="10" />
    <g:message code="salesSite.maxAmount" default="Monto Maximo"/>: <g:textField name="maxSalesAmount" id="maxSalesAmount" value="${maxSalesAmount}" size="10" />

    <div><g:message code="compensation.amount2" default="Monto"/></div>
    <g:message code="salesSite.minAmount" default="Monto Minimo"/>: <g:textField name="minSalesAmount2" id="minSalesAmount2" value="${minSalesAmount2}" size="10" />
    <g:message code="salesSite.maxAmount" default="Monto Maximo"/>: <g:textField name="maxSalesAmount2" id="maxSalesAmount2" value="${maxSalesAmount2}" size="10" />
    
  </div>
       <div class="filterButton">
    <span class="button">
      <input type="button" class="save" value="Filtrar" id="applySalesFilter"/>
    </span>        
  </div>
</div>
<div style="position:relative">
  <span class="menuButton"><input type="button" class="filter" id="salesSiteFilter" value="${message(code:'preconciliation.filtercolumns', default:'Filtrar Columnas')}"></span>
</div>
<br/>

 <div id="filterSalesColumns" class="salesSitefilterColumns">
 <h3>${message(code: 'preconciliation.salesSite', default: 'Ventas del Sitio')}</h3>
  <table>
    <tr>
     <td><g:checkBox name="salesColAll" id='salesColAll' value="${true}" /></td>
     <td>${message(code: 'salesSite.unselect', default: 'Seleccionar Todo')}</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
    </tr>
      <tr>
        <td><g:checkBox class='salesSiteCol' name="1" value="${true}" /></td>
        <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
        <td><g:checkBox class='salesSiteCol' name="2" value="${true}" /></td>
        <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='salesSiteCol' name="3" value="${true}" /></td>
        <td>${message(code: 'salesSite.absamount', default: 'Monto Abs.')}</td>
        <td><g:checkBox class='salesSiteCol' name="4" value="${true}" /></td>
        <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>        
      </tr>
      <tr>
        <td><g:checkBox class='salesSiteCol' name="5" value="${true}" /></td>
        <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
        <td><g:checkBox class='salesSiteCol' name="6" value="${true}" /></td>
        <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
        <td><g:checkBox class='salesSiteCol' name="7" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</td>
        <td><g:checkBox class='salesSiteCol' name="8" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentId', default: 'DNI')}</td>
       </tr>
       <tr>
        <td><g:checkBox class='salesSiteCol' name="9" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='salesSiteCol' name="10" value="${true}" /></td>
        <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
        <td><g:checkBox class='salesSiteCol' name="11" value="${true}" /></td>
        <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
        <td><g:checkBox class='salesSiteCol' name="12" value="${true}" /></td>
        <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
      </tr>
      <tr>
        <td><g:checkBox class='salesSiteCol' name="13" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
        <td><g:checkBox class='salesSiteCol' name="14" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='salesSiteCol' name="15" value="true"/></td>
        <td>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='salesSiteCol' name="16" value="true"/></td>
        <td>${message(code: 'salesSite.payment', default: 'Pagado')}</td>
      </tr>                    
  </table>
  </div>       

<div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">
	<table id="sales_table" class="display">
	  <thead>
		  <tr>
	      <th>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
	      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
	      <th>${message(code: 'salesSite.absamount', default: 'Monto Abs')}</th>
	      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
	      <th>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
	      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
	      <th>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
	      <th>${message(code: 'salesSite.documentId', default: 'DNI')}</th>
	      <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th> 
	      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
	      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
	      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
	      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>          
	      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
	      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
	      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
			</tr>
		</thead>
		<tbody>
	    <tr>
	      <td colspan="11" class="dataTables_empty">Loading data from server</td>
	    </tr>
	  </tbody>
	</table>
</div>
<br/><br/>
<div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">
  <table id="compensated_sales_table" class="display">
    <thead>
      <tr>
	      <th>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
	      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
	      <th>${message(code: 'salesSite.absamount', default: 'Monto Abs')}</th>
	      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
	      <th>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
	      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
	      <th>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
	      <th>${message(code: 'salesSite.documentId', default: 'DNI')}</th>
	      <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th> 
	      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
	      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
	      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
	      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>          
	      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
	      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
	      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
      </tr>
    </thead>
    <tbody>

    </tbody>
  </table>  

</div>
<div class="buttons">
    <span class="button"><input type="button" class="save" id="compensateSalesButton" value="${message(code:'save', default:'Grabar')}"/></span>
</div>    
