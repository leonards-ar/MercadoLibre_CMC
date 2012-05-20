<div id="preconciliacion">
 <table>
 <tr>
   <td>
  <div id="filterReceiptColumns" class="receiptfilterColumns">
   <h3>${message(code: 'preconciliation.receipts', default: 'Recibos')}</h3>
  <table>
    <tr>
      <td><g:checkBox class='receiptCol' name="2" value="${true}" /></td>
      <td>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</td>
      <td><g:checkBox class='receiptCol' name="3" value="${true}" /></td>
      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
      <td><g:checkBox class='receiptCol' name="4" value="${true}" /></td>
      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
      <td><g:checkBox class='receiptCol' name="5" value="${true}" /></td>
      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
      <td><g:checkBox class='receiptCol' name="6" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
    </tr>
    <tr>
      <td><g:checkBox class='receiptCol' name="7" value="${true}" /></td>
      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
      <td><g:checkBox class='receiptCol' name="8" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
      <td><g:checkBox class='receiptCol' name="9" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
      <td><g:checkBox class='receiptCol' name="10" value="${true}" /></td>
      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
      <td><g:checkBox class='receiptCol' name="11" value="${true}" /></td>
      <td>${message(code: 'salesSite.documentId', default: 'Doc.')}</td>
    </tr>
    <tr>
      <td><g:checkBox class='receiptCol' name="12" value="${true}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
      <td><g:checkBox class='receiptCol' name="13" value="${true}" /></td>    
      <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
      <td><g:checkBox class='receiptCol' name="14" value="true"/></td>
      <td>${message(code: 'salesSite.documentNumber', default: 'DNI')}</td>
      <td colspan="4">&nbsp;</td>
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
  <div id="filterSalesColumns" class="salesSitefilterColumns">
  <h3>${message(code: 'preconciliation.salesSite', default: 'Ventas del Sitio')}</h3>
  <table>
    <tr>
      <td><g:checkBox class='salesSiteCol' name="2" value="${true}" /></td>
      <td>${message(code: 'salesSite.medio', default: 'Medio')}</td>
      <td><g:checkBox class='salesSiteCol' name="3" value="${true}" /></td>
      <td>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</td>
      <td><g:checkBox class='salesSiteCol' name="4" value="${true}" /></td>
      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
      <td><g:checkBox class='salesSiteCol' name="5" value="${true}" /></td>
      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
      <td><g:checkBox class='salesSiteCol' name="6" value="${true}" /></td>
      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
    </tr>
    <tr>
      <td><g:checkBox class='salesSiteCol' name="7" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
      <td><g:checkBox class='salesSiteCol' name="8" value="${true}" /></td>
      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
      <td><g:checkBox class='salesSiteCol' name="9" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
      <td><g:checkBox class='salesSiteCol' name="10" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
      <td><g:checkBox class='salesSiteCol' name="11" value="${true}" /></td>
      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
    </tr>
    <tr>
      <td><g:checkBox class='salesSiteCol' name="12" value="${true}" /></td>
      <td>${message(code: 'salesSite.documentId', default: 'Doc.')}</td>
      <td><g:checkBox class='salesSiteCol' name="13" value="${true}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
      <td><g:checkBox class='salesSiteCol' name="14" value="${true}" /></td>    
      <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
      <td><g:checkBox class='salesSiteCol' name="15" value="true"/></td>
      <td>${message(code: 'salesSite.documentNumber', default: 'DNI')}</td>
      <td colspan="2">&nbsp;</td>
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
       <g:render template="preconciliateTable"/>  
    </div></center>

    <div class="buttons">
        <span class="button"><input type="button" class="save" id="preconciliateButton" value="${message(code:'save', default:'Grabar')}"/></span>
    </div>    
 
</div>
