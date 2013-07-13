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
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
     <td>&nbsp;</td>
    </tr>
      <tr>
        <td><g:checkBox class='salesSiteCol' name="1" value="${true}" /></td>
        <td>${message(code: 'salesSite.saleCode', default: 'Cod. Venta')}</td>
        <td><g:checkBox class='salesSiteCol' name="2" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='salesSiteCol' name="3" value="${true}" /></td>
        <td>${message(code: 'salesSite.medio', default: 'Medio')}</td>
        <td><g:checkBox class='salesSiteCol' name="4" value="${true}" /></td>
        <td>${message(code: 'salesSite.state', default: 'Estado')}</td>
        <td><g:checkBox class='salesSiteCol' name="5" value="${true}" /></td>
        <td>${message(code: 'salesSite.registerType', default: 'Tipo Registro')}</td>
        <td><g:checkBox class='salesSiteCol' name="6" value="${true}" /></td>
        <td>${message(code: 'salesSite.lot', default: 'Lote')}</td>
        <td><g:checkBox class='salesSiteCol' name="7" value="${true}" /></td>
        <td>${message(code: 'salesSite.cardNumber', default: 'Nro. Tarjeta')}</td>
        <td><g:checkBox class='salesSiteCol' name="8" value="${true}" /></td>
        <td>${message(code: 'salesSite.transDate', default: 'Fecha de Operacion')}</td>
      </tr>
      <tr>        
        <td><g:checkBox class='salesSiteCol' name="9" value="${true}" /></td>
        <td>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='salesSiteCol' name="10" value="${true}" /></td>
        <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='salesSiteCol' name="11" value="${true}" /></td>
        <td>${message(code: 'salesSite.absamount', default: 'Monto Absoluto')}</td>
        <td><g:checkBox class='salesSiteCol' name="12" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
        <td><g:checkBox class='salesSiteCol' name="13" value="${true}" /></td>
        <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
        <td><g:checkBox class='salesSiteCol' name="14" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareNumber', default: 'Nro. Cuota')}</td>
        <td><g:checkBox class='salesSiteCol' name="15" value="true"/></td>
        <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='salesSiteCol' name="16" value="true"/></td>
        <td>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</td>
      </tr>
      <tr>        
        <td><g:checkBox class='salesSiteCol' name="17" value="true"/></td>
        <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
        <td><g:checkBox class='salesSiteCol' name="18" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentIdTypeType', default: 'Tipo de Doc.')}</td>
        <td><g:checkBox class='salesSiteCol' name="19" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</td>
        <td><g:checkBox class='salesSiteCol' name="20" value="${true}" /></td>
        <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
        <td><g:checkBox class='salesSiteCol' name="21" value="${true}" /></td>
        <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
        <td><g:checkBox class='salesSiteCol' name="22" value="${true}" /></td>
        <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
        <td><g:checkBox class='salesSiteCol' name="23" value="true"/></td>
        <td>${message(code: 'salesSite.store', default: 'Num. comercio')}</td>
        <td><g:checkBox class='salesSiteCol' name="24" value="true"/></td>
        <td>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</td>
      </tr>
      <tr>        
        <td><g:checkBox class='salesSiteCol' name="25" value="true"/></td>
        <td>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</td>
        <td><g:checkBox class='salesSiteCol' name="26" value="${true}" /></td>
        <td>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</td>
        <td><g:checkBox class='salesSiteCol' name="27" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</td>
        <td><g:checkBox class='salesSiteCol' name="28" value="${true}" /></td>
        <td>${message(code: 'salesSite.period', default: 'Periodo')}</td>
        <td><g:checkBox class='salesSiteCol' name="29" value="${true}" /></td>
        <td>${message(code: 'salesSite.origin', default: 'Flag Origen')}</td>
        <td><g:checkBox class='salesSiteCol' name="30" value="${true}" /></td>
        <td>${message(code: 'salesSite.operationId', default: 'Operation ID')}</td>
        <td><g:checkBox class='salesSiteCol' name="31" value="true"/></td>
        <td>${message(code: 'salesSite.sapId', default: 'Sap ID')}</td>
        <td><g:checkBox class='salesSiteCol' name="32" value="true"/></td>
        <td>${message(code: 'salesSite.paymentReference', default: 'Pay Reference')}</td>
        <td><g:checkBox class='salesSiteCol' name="33" value="true"/></td>
        <td>${message(code: 'salesSite.pricing', default: 'Pricing')}</td>
        <td><g:checkBox class='salesSiteCol' name="34" value="true"/></td>
        <td>${message(code: 'salesSite.concPayId', default: 'Conc Pay ID')}</td>
      </tr>                                      
    </table>
  </div>       

<div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">
	<table id="sales_table" class="display">
	  <thead>
		  <tr>
		      <th>${message(code: 'salesSite.saleCode', default: 'Cod. Venta')}</th>
		      <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
		      <th>${message(code: 'salesSite.medio', default: 'Medio')}</th>
		      <th>${message(code: 'salesSite.state', default: 'Estado')}</th>
		      <th>${message(code: 'salesSite.registerType', default: 'Tipo Registro')}</th>
		      <th>${message(code: 'salesSite.lot', default: 'Lote')}</th>
		      <th>${message(code: 'salesSite.cardNumber', default: 'Nro. Tarjeta')}</th>
		      <th>${message(code: 'salesSite.transDate', default: 'Fecha de Operacion')}</th> 
		      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
		      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
		      <th>${message(code: 'salesSite.absamount', default: 'Monto Absoluto')}</th>
		      <th>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</th>
		      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>          
		      <th>${message(code: 'salesSite.shareNumber', default: 'Nro. Cuota')}</th>
		      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
		      <th>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</th>
		      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
		      <th>${message(code: 'salesSite.documentIdTypeType', default: 'Tipo de Doc.')}</th>
		      <th>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</th>
		      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
		      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
		      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
		      <th>${message(code: 'salesSite.store', default: 'Num. comercio')}</th>
		      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
		      <th>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</th>
		      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
		      <th>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</th>
		      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
		      <th>${message(code: 'salesSite.origin', default: 'Flag Origen')}</th>
		      <th>${message(code: 'salesSite.operationId', default: 'Operation ID')}</th>
		      <th>${message(code: 'salesSite.sapId', default: 'Sap ID')}</th>
		      <th>${message(code: 'salesSite.paymentReference', default: 'Pay Reference')}</th>
		      <th>${message(code: 'salesSite.pricing', default: 'Pricing')}</th>
		      <th>${message(code: 'salesSite.concPayId', default: 'Conc Pay ID')}</th>
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
          <th>${message(code: 'salesSite.saleCode', default: 'Cod. Venta')}</th>
          <th>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
          <th>${message(code: 'salesSite.medio', default: 'Medio')}</th>
	      <th>${message(code: 'salesSite.state', default: 'Estado')}</th>
	      <th>${message(code: 'salesSite.registerType', default: 'Tipo Registro')}</th>
	      <th>${message(code: 'salesSite.lot', default: 'Lote')}</th>
	      <th>${message(code: 'salesSite.cardNumber', default: 'Nro. Tarjeta')}</th>
	      <th>${message(code: 'salesSite.transDate', default: 'Fecha de Operacion')}</th> 
	      <th>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
	      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
	      <th>${message(code: 'salesSite.absamount', default: 'Monto Absoluto')}</th>
	      <th>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</th>
	      <th>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>          
	      <th>${message(code: 'salesSite.shareNumber', default: 'Nro. Cuota')}</th>
	      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
	      <th>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</th>
	      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
	      <th>${message(code: 'salesSite.documentIdTypeType', default: 'Tipo de Doc.')}</th>
	      <th>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</th>
	      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
	      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
	      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
	      <th>${message(code: 'salesSite.store', default: 'Num. comercio')}</th>
	      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
	      <th>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</th>
	      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
	      <th>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</th>
	      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
	      <th>${message(code: 'salesSite.origin', default: 'Flag Origen')}</th>
	      <th>${message(code: 'salesSite.operationId', default: 'Operation ID')}</th>
	      <th>${message(code: 'salesSite.sapId', default: 'Sap ID')}</th>
	      <th>${message(code: 'salesSite.paymentReference', default: 'Pay Reference')}</th>
	      <th>${message(code: 'salesSite.pricing', default: 'Pricing')}</th>
	      <th>${message(code: 'salesSite.concPayId', default: 'Conc Pay ID')}</th>
      </tr>
    </thead>
    <tbody>

    </tbody>
  </table>  

</div>
<div class="buttons">
    <span class="button"><input type="button" class="save" id="compensateSalesButton" value="${message(code:'save', default:'Grabar')}"/></span>
</div>    
