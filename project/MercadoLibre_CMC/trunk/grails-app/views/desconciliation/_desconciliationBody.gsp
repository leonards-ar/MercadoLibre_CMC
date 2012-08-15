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
        <td><g:checkBox class='desconciliationCol' name="1" value="${true}" /></td>
        <td>R<g:message code="salesSite.registerType" default="Tipo de Registro"/></td>
	      <td><g:checkBox class='desconciliationCol' name="2" value="${true}" /></td>
	      <td>R<g:message code="salesSite.cardNumber" default="Nro Tarjeta"/></td>
	      <td><g:checkBox class='desconciliationCol' name="3" value="${true}" /></td>
	      <td>R<g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></td>
	      <td><g:checkBox class='desconciliationCol' name="4" value="${true}" /></td>
	      <td>R<g:message code="salesSite.amount" default="Monto"/></td>
	      <td><g:checkBox class='desconciliationCol' name="5" value="${true}" /></td>
	      <td>R<g:message code="salesSite.shareAmount" default="Monto Cuota"/></td>
	     </tr>
	     <tr>
        <td><g:checkBox class='desconciliationCol' name="6" value="${true}" /></td>
        <td>R<g:message code="salesSite.authorization" default="Autorizacion"/></td>
	      <td><g:checkBox class='desconciliationCol' name="7" value="${true}" /></td>
	      <td>R<g:message code="salesSite.shareNumber" default="Nro Cuota"/></td>
	      <td><g:checkBox class='desconciliationCol' name="8" value="${true}" /></td>
	      <td>R<g:message code="salesSite.shareQty" default="Cant. Cuotas"/></td>
	      <td><g:checkBox class='desconciliationCol' name="9" value="${true}" /></td>
	      <td>R<g:message code="salesSite.customerId" default="Cliente"/></td>
	      <td><g:checkBox class='desconciliationCol' name="10" value="${true}" /></td>
	      <td>R<g:message code="salesSite.documentId" default="Doc."/></td>
	     </tr>
	     <tr>
        <td><g:checkBox class='desconciliationCol' name="11" value="${true}" /></td>
        <td>R<g:message code="salesSite.tid" default="TID"/></td>
	      <td><g:checkBox class='desconciliationCol' name="12" value="${true}" /></td>
	      <td>R<g:message code="salesSite.nsu" default="NSU"/></td>
	      <td><g:checkBox class='desconciliationCol' name="13" value="${true}" /></td>
	      <td>R<g:message code="salesSite.documentNumber" default="DNI"/></td>
	      <td><g:checkBox class='desconciliationCol' name="14" value="${true}" /></td>
	      <td>S<g:message code="salesSite.medio" default="Medio"/></td>
        <td><g:checkBox class='desconciliationCol' name="15" value="true"/></td>
        <td>S<g:message code="salesSite.registerType" default="Tipo de Registro"/></td>
     </tr>
     <tr>
        <td><g:checkBox class='desconciliationCol' name="16" value="${true}" /></td>
        <td>S<g:message code="salesSite.cardNumber" default="Nro Tarjeta"/></td>
	      <td><g:checkBox class='desconciliationCol' name="17" value="${true}" /></td>
	      <td>S<g:message code="salesSite.transDate" default="Fecha de la Transaccion"/></td>
	      <td><g:checkBox class='desconciliationCol' name="18" value="${true}" /></td>    
	      <td>S<g:message code="salesSite.amount" default="Monto"/></td>
	      <td><g:checkBox class='desconciliationCol' name="19" value="${true}" /></td>    
	      <td>S<g:message code="salesSite.shareAmount" default="Monto Cuota"/></td>
	      <td><g:checkBox class='desconciliationCol' name="20" value="${true}" /></td>    
	      <td>S<g:message code="salesSite.authorization" default="Autorizacion"/></td>
       </tr>
       <tr>
        <td><g:checkBox class='desconciliationCol' name="21" value="${true}" /></td>    
        <td>S<g:message code="salesSite.shareNumber" default="Nro Cuota"/></td>
	      <td><g:checkBox class='desconciliationCol' name="22" value="${true}" /></td>
	      <td>S<g:message code="salesSite.shareQty" default="Cant. Cuotas"/></td>
	      <td><g:checkBox class='desconciliationCol' name="23" value="${true}" /></td>    
	      <td>S<g:message code="salesSite.customerId" default="Cliente"/></td>
        <td><g:checkBox class='desconciliationCol' name="24" value="${true}" /></td>    
        <td>S<g:message code="salesSite.documentId" default="Doc."/></td>
        <td><g:checkBox class='desconciliationCol' name="25" value="${true}" /></td>
        <td>S<g:message code="salesSite.tid" default="TID"/></td>
        
	    </tr>
	    <tr>
        <td><g:checkBox class='desconciliationCol' name="26" value="${true}" /></td>    
        <td>S<g:message code="salesSite.nsu" default="NSU"/></td>
        <td><g:checkBox class='desconciliationCol' name="27" value="${true}" /></td>    
        <td>S<g:message code="salesSite.documentNumber" default="DNI"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
	    </tr>                    
	  </table>
	  </div>
  </td>
 </tr>
 <tr>
  <td>
  <table id="conciliate_table" class="display">
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
    <tr>
      <td colspan="27" class="dataTables_empty">Loading data from server</td>
    </tr>
      </tbody>
  </table>    
  </td>
 </tr>       
</table>
<div class="buttons">
  <span class="button"><input type="button" class="save" id="conciliateButton" value="${message(code:'save', default:'Grabar')}"/></span>
</div>    
 
</div>
