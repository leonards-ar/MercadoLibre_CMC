<div style="width:100%;height:75;border:1px solid #ccc;position:relative">
  <div class="prefilterFields">
    <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion'"/></div>
    <g:message code="salesSite.from" default="Desde'"/>: <g:textField name="fromSalesTransDate" id="fromSalesTransDate" value="${fromSalesTransDate}" size="10" />
    <g:message code="salesSite.to" default="Hasta'"/>: <g:textField name="toSalesTransDate" id="toSalesTransDate" value="${toSalesTransDate}" size="10" />
  </div>
       <div class="filterButton">
    <span class="button">
      <input type="button" class="save" value="Filtrar" id="applySalesFilter"/>
    </span>        
  </div>
</div>
<br/>
<br/>
<div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">
	<table id="sales_table" class="display">
	  <thead>
		  <tr>
	      <th>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
	      <th>${message(code: 'salesSite.amount', default: 'Monto')}</th>
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
