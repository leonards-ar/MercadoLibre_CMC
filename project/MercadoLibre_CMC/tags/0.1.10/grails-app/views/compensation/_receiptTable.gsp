<div style="width:1200px;height:160;border:1px solid #ccc;position:relative">
  <div class="filterFields">
    <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></div>
    <g:message code="salesSite.from" default="Desde"/>: <g:textField name="fromReceiptTransDate" id="fromReceiptTransDate" value="${fromReceiptTransDate}" size="10" />
    <g:message code="salesSite.to" default="Hasta"/>: <g:textField name="toReceiptTransDate" id="toReceiptTransDate" value="${toReceiptTransDate}" size="10" />
    
    <div><g:message code="salesSite.paymtDate" default="Fecha de Pago"/></div>
    <g:message code="salesSite.from" default="Desde"/>: <g:textField name="fromReceiptPaymtDate" id="fromReceiptPaymtDate" value="${fromReceiptPaymtDate}" size="10" />
    <g:message code="salesSite.to" default="Hasta"/>: <g:textField name="toReceiptPaymtDate" id="toReceiptPaymtDate" value="${toReceiptPaymtDate}" size="10" />
    
    <div><g:message code="compensation.amount1" default="Monto"/></div>
    <g:message code="salesSite.minAmount" default="Monto Minimo"/>: <g:textField name="minReceiptAmount" id="minReceiptAmount" value="${minReceiptAmount}" size="10" />
    <g:message code="salesSite.maxAmount" default="Monto Maximo"/>: <g:textField name="maxReceiptAmount" id="maxReceiptAmount" value="${maxReceiptAmount}" size="10" />

    <div><g:message code="compensation.amount2" default="Monto"/></div>
    <g:message code="salesSite.minAmount" default="Monto Minimo"/>: <g:textField name="minReceiptAmount2" id="minReceiptAmount2" value="${minReceiptAmount2}" size="10" />
    <g:message code="salesSite.maxAmount" default="Monto Maximo"/>: <g:textField name="maxReceiptAmount2" id="maxReceiptAmount2" value="${maxReceiptAmount2}" size="10" />
      
  </div>
  <div class="filterButton">
    <span class="button">
      <input type="button" class="save" value="Filtrar" id="applyReceiptFilter"/>
    </span>        
  </div>
</div>
<div style="position:relative">
  <span class="menuButton"><input type="button" class="filter" id="receiptFilter" value="${message(code:'preconciliation.filtercolumns', default:'Filtrar Columnas')}"></span>
</div>
<br/>

 <div id="filterReceiptColumns" class="receiptfilterColumns">
 <h3>${message(code: 'preconciliation.receipts', default: 'Ventas del Sitio')}</h3>
  <table>
      <tr>
       <td><g:checkBox name="receiptColAll" id='receiptColAll' value="${true}" /></td>
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
      </tr>   
      <tr>
        <td><g:checkBox class='receiptCol' name="1" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='receiptCol' name="2" value="${true}" /></td>
        <td>${message(code: 'salesSite.medio', default: 'Medio')}</td>
        <td><g:checkBox class='receiptCol' name="3" value="${true}" /></td>
        <td>${message(code: 'salesSite.state', default: 'Estado')}</td>
        <td><g:checkBox class='receiptCol' name="4" value="${true}" /></td>
        <td>${message(code: 'salesSite.registerType', default: 'Tipo Registro')}</td>
        <td><g:checkBox class='receiptCol' name="5" value="${true}" /></td>
        <td>${message(code: 'salesSite.lot', default: 'Lote')}</td>
        <td><g:checkBox class='receiptCol' name="6" value="${true}" /></td>
        <td>${message(code: 'salesSite.cardNumber', default: 'Nro. Tarjeta')}</td>
        <td><g:checkBox class='receiptCol' name="7" value="${true}" /></td>
        <td>${message(code: 'salesSite.transDate', default: 'Fecha de Operacion')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='receiptCol' name="8" value="${true}" /></td>
        <td>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='receiptCol' name="9" value="${true}" /></td>
        <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='receiptCol' name="10" value="${true}" /></td>
        <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='receiptCol' name="11" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
        <td><g:checkBox class='receiptCol' name="12" value="${true}" /></td>
        <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
        <td><g:checkBox class='receiptCol' name="13" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
        <td><g:checkBox class='receiptCol' name="14" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='receiptCol' name="15" value="true"/></td>
        <td>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</td>
        <td><g:checkBox class='receiptCol' name="16" value="true"/></td>
        <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
        <td><g:checkBox class='receiptCol' name="17" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentIdType', default: 'Tipo de Doc.')}</td>
        <td><g:checkBox class='receiptCol' name="18" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</td>
        <td><g:checkBox class='receiptCol' name="19" value="${true}" /></td>
        <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
        <td><g:checkBox class='receiptCol' name="20" value="true"/></td>
        <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
        <td><g:checkBox class='receiptCol' name="21" value="true"/></td>
        <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='receiptCol' name="22" value="${true}" /></td>
        <td>${message(code: 'salesSite.store', default: 'Num. Comercio')}</td>
        <td><g:checkBox class='receiptCol' name="23" value="${true}" /></td>
        <td>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</td>
        <td><g:checkBox class='receiptCol' name="24" value="${true}" /></td>
        <td>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</td>
        <td><g:checkBox class='receiptCol' name="25" value="true"/></td>
        <td>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</td>
        <td><g:checkBox class='receiptCol' name="26" value="true"/></td>
        <td>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</td>
        <td><g:checkBox class='receiptCol' name="27" value="true"/></td>
        <td>${message(code: 'salesSite.period', default: 'Periodo')}</td>
        <td><g:checkBox class='receiptCol' name="28" value="true"/></td>
        <td>${message(code: 'salesSite.origin', default: 'Flag Origen')}</td>
      </tr>                    
  </table>
  </div>       

<div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">

  <table id="receipt_table" class="display">
	  <thead>
		  <tr>
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
		      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>          
		      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
		      <th>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</th>
		      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
		      <th>${message(code: 'salesSite.documentIdType', default: 'Tipo de Doc.')}</th>
		      <th>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</th>
		      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
		      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
		      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
		      <th>${message(code: 'salesSite.store', default: 'Num. Comercio')}</th>
		      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
		      <th>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</th>
		      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
		      <th>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</th>
		      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
		      <th>${message(code: 'salesSite.origin', default: 'Flag Origen')}</th>
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
  <table id="compensated_receipt_table" class="display">
    <thead>
      <tr>
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
	      <th>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>          
	      <th>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
	      <th>${message(code: 'salesSite.liq', default: 'Num. Liquidacion')}</th>
	      <th>${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
	      <th>${message(code: 'salesSite.documentIdType', default: 'Tipo de Doc.')}</th>
	      <th>${message(code: 'salesSite.receiptNumber', default: 'Num. Recibo')}</th>
	      <th>${message(code: 'salesSite.tid', default: 'TID')}</th>
	      <th>${message(code: 'salesSite.nsu', default: 'NSU')}</th>
	      <th>${message(code: 'salesSite.ro', default: 'RO')}</th>
	      <th>${message(code: 'salesSite.store', default: 'Num. Comercio')}</th>
	      <th>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</th>
	      <th>${message(code: 'salesSite.uniqueRo', default: 'Unico RO')}</th>
	      <th>${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</th>
	      <th>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</th>
	      <th>${message(code: 'salesSite.period', default: 'Periodo')}</th>
	      <th>${message(code: 'salesSite.origin', default: 'Flag Origen')}</th>
      </tr>
    </thead>
    <tbody>

    </tbody>
  </table>  

</div>

<div class="buttons">
    <span class="button"><input type="button" class="save" id="compensateReceiptButton" value="${message(code:'save', default:'Grabar')}"/></span>
</div>    
