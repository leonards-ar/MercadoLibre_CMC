<div id="conciliacion">
 <table>
 <tr>
   <td>
    <div style="position:relative">
    <span class="menuButton"><input type="button" class="filter" id="receiptFilter" value="${message(code:'preconciliation.filtercolumns', default:'Filtrar Columnas')}"/></span>
    </div>
    <br/>
    <div style="width:550;height:75;border:1px solid #ccc;position:relative">
      <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion'"/></div>
      <g:message code="salesSite.from" default="Desde'"/>: <g:textField name="fromReceiptTransDate" id="fromReceiptTransDate" value="${fromReceiptTransDate}" size="10" />
      <g:message code="salesSite.to" default="Hasta'"/>: <g:textField name="toReceiptTransDate" id="toReceiptTransDate" value="${toReceiptTransDate}" size="10" />
      
      <div><g:message code="salesSite.paymtDate" default="Fecha de Pago'"/></div>
      <g:message code="salesSite.from" default="Desde'"/>: <g:textField name="fromReceiptPaymtDate" id="fromReceiptPaymtDate" value="${fromReceiptPaymtDate}" size="10" />
      <g:message code="salesSite.to" default="Hasta'"/>: <g:textField name="toReceiptPaymtDate" id="toReceiptPaymtDate" value="${toReceiptPaymtDate}" size="10" />
    </div>
    <br/> 
	  <div id="filterReceiptColumns" class="receiptfilterColumns">
	   <h3>${message(code: 'preconciliation.receipts', default: 'Recibos')}</h3>
	  <table>
	    <tr>
	     <td><g:checkBox id='receiptColAll' value="${true}" /></td>
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
	    </tr>	  
	    <tr>
	      <td><g:checkBox class='receiptCol' name="1" value="${true}" /></td>
	      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
	      <td><g:checkBox class='receiptCol' name="2" value="${true}" /></td>
	      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
	      <td><g:checkBox class='receiptCol' name="3" value="${true}" /></td>
	      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
	      <td><g:checkBox class='receiptCol' name="4" value="${true}" /></td>
	      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
	      <td><g:checkBox class='receiptCol' name="5" value="${true}" /></td>
	      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
	    </tr>
	    <tr>
	      <td><g:checkBox class='receiptCol' name="6" value="${true}" /></td>
	      <td>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</td>
	      <td><g:checkBox class='receiptCol' name="7" value="${true}" /></td>
	      <td>${message(code: 'salesSite.documentId', default: 'DNI')}</td>
	      <td><g:checkBox class='receiptCol' name="8" value="${true}" /></td>
	      <td>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
	      <td><g:checkBox class='receiptCol' name="9" value="${true}" /></td>
	      <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
	      <td><g:checkBox class='receiptCol' name="10" value="${true}" /></td>
	      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
       </tr>
       <tr>      
	      <td><g:checkBox class='receiptCol' name="11" value="${true}" /></td>
	      <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
	      <td><g:checkBox class='receiptCol' name="12" value="${true}" /></td>
	      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
	      <td><g:checkBox class='receiptCol' name="13" value="${true}" /></td>
	      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='receiptCol' name="14" value="true"/></td>
        <td>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='receiptCol' name="15" value="true"/></td>
        <td>${message(code: 'salesSite.payed', default: 'Pagado')}</td>
	    </tr>                    
	  </table>
	  </div>       
	    <div id="receipts" style="width:550;height:200;overflow:auto;border:1px solid #ccc;position:relative">
         <g:render template="receiptTable" model="[receiptInstanceList:receiptInstanceList]"/>
	    </div>
   
   </td>
   <td>
      <table style="border:0px">
      <tr>
        <td align="center">
	<span class="button"><input type="button" class="save" value="agrupar" id="agrupar"/></span>           
        </td>
      </tr>
      <tr>
        <td align="center">
        	<p id='balance'>0</p></span>
        </td>
      </tr>
      </table>
     
     
   </td>
   <td>
    <div style="position:relative">
      <span class="menuButton"><input type="button" class="filter" id="salesSiteFilter" value="${message(code:'preconciliation.filtercolumns', default:'Filtrar Columnas')}"></span>
    </div>
    <br/>
    <div style="width:550;height:75;border:1px solid #ccc;position:relative">
      <div><g:message code="salesSite.transDate" default="Fecha de la Transaccion'"/></div>
      <g:message code="salesSite.from" default="Desde'"/>: <g:textField name="fromSalesTransDate" id="fromSalesTransDate" value="${fromSalesTransDate}" size="10" />
      <g:message code="salesSite.to" default="Hasta'"/>: <g:textField name="toSalesTransDate" id="toSalesTransDate" value="${toSalesTransDate}" size="10" />
    </div>
    <br/>     
    <div id="filterSalesColumns" class="salesSitefilterColumns">
    <h3>${message(code: 'preconciliation.salesSite', default: 'Ventas del Sitio')}</h3>
  <table>
    <tr>
     <td><g:checkBox id='salesColAll' value="${true}" /></td>
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
        <td><g:checkBox class='salesSiteCol' name="1" value="${true}" /></td>
        <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
        <td><g:checkBox class='salesSiteCol' name="2" value="${true}" /></td>
        <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
        <td><g:checkBox class='salesSiteCol' name="3" value="${true}" /></td>
        <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
        <td><g:checkBox class='salesSiteCol' name="4" value="${true}" /></td>
        <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
        <td><g:checkBox class='salesSiteCol' name="5" value="${true}" /></td>
        <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
      </tr>
      <tr>
        <td><g:checkBox class='salesSiteCol' name="6" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentNumber', default: 'Doc. Nro.')}</td>
        <td><g:checkBox class='salesSiteCol' name="7" value="${true}" /></td>
        <td>${message(code: 'salesSite.documentId', default: 'DNI')}</td>
        <td><g:checkBox class='salesSiteCol' name="8" value="${true}" /></td>
        <td>${message(code: 'salesSite.receiptNumber', default: 'Nro. Recibo')}</td>
        <td><g:checkBox class='salesSiteCol' name="9" value="${true}" /></td>
        <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
        <td><g:checkBox class='salesSiteCol' name="10" value="${true}" /></td>
        <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
       </tr>
       <tr>      
        <td><g:checkBox class='salesSiteCol' name="11" value="${true}" /></td>
        <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
        <td><g:checkBox class='salesSiteCol' name="12" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
        <td><g:checkBox class='salesSiteCol' name="13" value="${true}" /></td>
        <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
        <td><g:checkBox class='salesSiteCol' name="14" value="true"/></td>
        <td>${message(code: 'salesSite.paymentDate', default: 'Fecha de Pago')}</td>
        <td><g:checkBox class='salesSiteCol' name="15" value="true"/></td>
        <td>${message(code: 'salesSite.payed', default: 'Pagado')}</td>
      </tr>                    
  </table>
  </div>       
 
     <div id="sales" style="width:550;height:200;overflow:auto;border:1px solid #ccc;position:relative">
       <g:render template="salesSiteTable" model="[salesSiteInstanceList:salesSiteInstanceList]"/>
     </div>
   </td>      
 </tr>
 </table>
 <br/>
     <center><div id="conciliado" style="width:1000;height:200;overflow:auto;">
       <g:render template="conciliateTable"/>  
    </div></center>

    <div class="buttons">
        <span class="button"><input type="button" class="save" id="conciliateButton" value="${message(code:'save', default:'Grabar')}"/></span>
    </div>    
 
</div>
