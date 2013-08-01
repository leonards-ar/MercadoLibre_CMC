<div id="conciliacion">
 <table>
 <tr>
   <td>
    <div style="position:relative"><span class="menuButton"><input type="button" class="filter" id="receiptFilter" value="${message(code:'preconciliation.filtercolumns', default:'Filtrar Columnas')}"/></span></div>
    <div id="filterReceiptColumns" class="desconciliationfilterColumns">
     <h3>${message(code: 'desconciliation.conciliated', default: 'Conciliaciones')}</h3>
    <table>
      <tr>
       <td><g:checkBox id='desconciliationColAll' value="${true}" /></td>
       <td>${message(code: 'salesSite.unselect', default: 'Seleccionar Todo')}</td>
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
      <tr>
        <td><g:checkBox class='desconciliationCol' name="1" value="${true}" /></td>
        <td>R${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
        <td><g:checkBox class='desconciliationCol' name="2" value="${true}" /></td>
        <td>R${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='desconciliationCol' name="3" value="${true}" /></td>
        <td>R${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
        <td><g:checkBox class='desconciliationCol' name="4" value="${true}" /></td>
        <td>R${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
        <td><g:checkBox class='desconciliationCol' name="5" value="${true}" /></td>
        <td>R${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
      </tr>
      <tr>
        <td><g:checkBox class='desconciliationCol' name="6" value="${true}" /></td>
        <td>R${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</td>
        <td><g:checkBox class='desconciliationCol' name="7" value="${true}" /></td>
        <td>R${message(code: 'salesSite.documentIdType', default: 'DNI')}</td>
        <td><g:checkBox class='desconciliationCol' name="8" value="${true}" /></td>
        <td>R${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='desconciliationCol' name="9" value="${true}" /></td>
        <td>R${message(code: 'salesSite.ro', default: 'RO')}</td>
        <td><g:checkBox class='desconciliationCol' name="10" value="${true}" /></td>
        <td>R${message(code: 'salesSite.tid', default: 'TID')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='desconciliationCol' name="11" value="${true}" /></td>
        <td>R${message(code: 'salesSite.nsu', default: 'NSU')}</td>
        <td><g:checkBox class='desconciliationCol' name="12" value="${true}" /></td>
        <td>R${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
        <td><g:checkBox class='desconciliationCol' name="13" value="${true}" /></td>
        <td>R${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='desconciliationCol' name="14" value="true"/></td>
        <td>R${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='desconciliationCol' name="15" value="true"/></td>
        <td>R${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</td>
      </tr>                    
      <tr>
        <td><g:checkBox class='desconciliationCol' name="16" value="${true}" /></td>
        <td>S${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
        <td><g:checkBox class='desconciliationCol' name="17" value="${true}" /></td>
        <td>S${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='desconciliationCol' name="18" value="${true}" /></td>
        <td>S${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
        <td><g:checkBox class='desconciliationCol' name="19" value="${true}" /></td>
        <td>S${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
        <td><g:checkBox class='desconciliationCol' name="20" value="${true}" /></td>
        <td>S${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
      </tr>
      <tr>
        <td><g:checkBox class='desconciliationCol' name="21" value="${true}" /></td>
        <td>S${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</td>
        <td><g:checkBox class='desconciliationCol' name="22" value="${true}" /></td>
        <td>S${message(code: 'salesSite.documentIdType', default: 'DNI')}</td>
        <td><g:checkBox class='desconciliationCol' name="23" value="${true}" /></td>
        <td>S${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='desconciliationCol' name="24" value="${true}" /></td>
        <td>S${message(code: 'salesSite.ro', default: 'RO')}</td>
        <td><g:checkBox class='desconciliationCol' name="25" value="${true}" /></td>
        <td>S${message(code: 'salesSite.tid', default: 'TID')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='desconciliationCol' name="26" value="${true}" /></td>
        <td>S${message(code: 'salesSite.nsu', default: 'NSU')}</td>
        <td><g:checkBox class='desconciliationCol' name="27" value="${true}" /></td>
        <td>S${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
        <td><g:checkBox class='desconciliationCol' name="28" value="${true}" /></td>
        <td>S${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='desconciliationCol' name="29" value="true"/></td>
        <td>S${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='desconciliationCol' name="30" value="true"/></td>
        <td>S${message(code: 'salesSite.payment', default: 'Ref. de Pago')}</td>
      </tr>                    
    </table>
    </div>
  </td>
 </tr>
 <tr>
  <td>
   <div class="desconciliationBox">
     <div class="title_box receiptFilter">
      <div id="title"><g:message code="desconciliation.receipt" default="Recibos"/></div>
      <div id="salesContent"> 
        <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></div>
        <g:message code="salesSite.from" default="Desde"/>: <g:textField name="fromReceiptTransDate" id="fromReceiptTransDate" value="${fromReceiptTransDate}" size="10" />
        <g:message code="salesSite.to" default="Hasta"/>: <g:textField name="toReceiptTransDate" id="toReceiptTransDate" value="${toReceiptTransDate}" size="10" />
        
      </div>
     </div>
     <div class="title_box salesFilter" >
      <div id="title"><g:message code="desconciliation.sales" default="Ventas"/></div>
      <div id="salesContent"> 
        <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></div>
        <g:message code="salesSite.from" default="Desde"/>: <g:textField name="fromSalesTransDate" id="fromSalesTransDate" value="${fromSalesTransDate}" size="10" />
        <g:message code="salesSite.to" default="Hasta"/>: <g:textField name="toSalesTransDate" id="toSalesTransDate" value="${toSalesTransDate}" size="10" />
       </div>
     </div>
     <div class="filterButton">
      <span class="button">
        <input type="button" class="save" value="Filtrar" id="applyReceiptFilter"/>
      </span>        
     </div>     
   </div> 
   <div style="width:100%;width:1200px;overflow:auto;border:1px solid #ccc;position:relative">
    <table id="conciliate_table" class="display">
    <thead>
    <tr>
    <th>R${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
    <th>R${message(code: 'salesSite.amount', default: 'Monto')}</th>
    <th>R${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
    <th>R${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
    <th>R${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
    <th>R${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
    <th>R${message(code: 'salesSite.documentIdType', default: 'DNI')}</th>
    <th>R${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
    <th>R${message(code: 'salesSite.ro', default: 'RO')}</th>
    <th>R${message(code: 'salesSite.tid', default: 'TID')}</th>
    <th>R${message(code: 'salesSite.nsu', default: 'NSU')}</th>
    <th>R${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>
    <th>R${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
    <th>R${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
    <th>R${message(code: 'salesSite.payment', default: 'Ref de Pago')}</th>
    <th>S${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</th>
    <th>S${message(code: 'salesSite.amount', default: 'Monto')}</th>
    <th>S${message(code: 'salesSite.authorization', default: 'Autorizacion')}</th>
    <th>S${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</th>
    <th>S${message(code: 'salesSite.customerId', default: 'Cliente')}</th>
    <th>S${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</th>
    <th>S${message(code: 'salesSite.documentIdType', default: 'DNI')}</th>
    <th>S${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</th>
    <th>S${message(code: 'salesSite.ro', default: 'RO')}</th>
    <th>S${message(code: 'salesSite.tid', default: 'TID')}</th>
    <th>S${message(code: 'salesSite.nsu', default: 'NSU')}</th>
    <th>S${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</th>
    <th>S${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</th>
    <th>S${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</th>
    <th>S${message(code: 'salesSite.payment', default: 'Ref de Pago')}</th>    </tr>
    </thead>
    <tbody>
    </tbody>
  </table>
 </div>    
</td>
</tr>       
</table>
<div class="buttons">
  <span class="button"><input type="button" class="save" id="desconciliateButton" value="${message(code:'save', default:'Grabar')}"/></span>
</div>    
 
</div>
