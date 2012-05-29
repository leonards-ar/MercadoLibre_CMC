<div id="preconciliacion">
 <table>
 <tr>
   <td>
  <div id="filterReceiptColumns" class="receiptfilterColumns">
   <h3>${message(code: 'preconciliation.receipts', default: 'Recibos')}</h3>
  <table>
    <tr>
      <td><g:checkBox class='receiptCol' name="2" value="${true}" /></td>
      <td>${message(code: 'salesSite.medio', default: 'Medio')}</td>
      <td><g:checkBox class='receiptCol' name="3" value="${true}" /></td>
      <td>${message(code: 'salesSite.state', default: 'Estado')}</td>
      <td><g:checkBox class='receiptCol' name="4" value="${true}" /></td>
      <td>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</td>
      <td><g:checkBox class='receiptCol' name="5" value="${true}" /></td>
      <td>${message(code: 'salesSite.lot', default: 'Lote')}</td>
      <td><g:checkBox class='receiptCol' name="6" value="${true}" /></td>
      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='receiptCol' name="7" value="${true}" /></td>
      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
      <td><g:checkBox class='receiptCol' name="8" value="${true}" /></td>
      <td>${message(code: 'salesSite.paymentDate', default: 'Fecha Pago')}</td>
      <td><g:checkBox class='receiptCol' name="9" value="${true}" /></td>
      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
      <td><g:checkBox class='receiptCol' name="10" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
      <td><g:checkBox class='receiptCol' name="11" value="${true}" /></td>
      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='receiptCol' name="12" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
      <td><g:checkBox class='receiptCol' name="13" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
      <td><g:checkBox class='receiptCol' name="14" value="${true}" /></td>
      <td>${message(code: 'salesSite.liq', default: 'Liquidacion')}</td>
      <td><g:checkBox class='receiptCol' name="15" value="true"/></td>
      <td>${message(code: 'salesSite.documentId', default: 'DNI')}</td>
      <td><g:checkBox class='receiptCol' name="16" value="${true}" /></td>
      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='receiptCol' name="17" value="true"/></td>
      <td>${message(code: 'salesSite.receiptNumber', default: 'DNI')}</td>
      <td><g:checkBox class='receiptCol' name="18" value="${true}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
      <td><g:checkBox class='receiptCol' name="19" value="${true}" /></td>    
      <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
      <td><g:checkBox class='receiptCol' name="20" value="${true}" /></td>    
      <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
      <td><g:checkBox class='receiptCol' name="21" value="${true}" /></td>    
      <td>${message(code: 'salesSite.store', default: 'P. de Venta')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='receiptCol' name="22" value="${true}" /></td>    
      <td>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</td>
      <td><g:checkBox class='receiptCol' name="23" value="${true}" /></td>    
      <td>${message(code: 'salesSite.uniqueRo', default: 'Lote Tarjeta')}</td>
      <td><g:checkBox class='receiptCol' name="24" value="${true}" /></td>
      <td>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</td>
      <td><g:checkBox class='receiptCol' name="25" value="${true}" /></td>    
      <td>${message(code: 'salesSite.period', default: 'Periodo')}</td>
      <td colspan="2">&nbsp;</td>
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
      <td>${message(code: 'salesSite.saleMl', default: 'Venta ML')}</td>
      <td><g:checkBox class='salesSiteCol' name="3" value="${true}" /></td>
      <td>${message(code: 'salesSite.medio', default: 'Medio')}</td>
      <td><g:checkBox class='salesSiteCol' name="4" value="${true}" /></td>
      <td>${message(code: 'salesSite.state', default: 'Estado')}</td>
      <td><g:checkBox class='salesSiteCol' name="5" value="${true}" /></td>
      <td>${message(code: 'salesSite.registerType', default: 'Tipo de Registro')}</td>
      <td><g:checkBox class='salesSiteCol' name="6" value="${true}" /></td>
      <td>${message(code: 'salesSite.lot', default: 'Lote')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='salesSiteCol' name="7" value="${true}" /></td>
      <td>${message(code: 'salesSite.cardNumber', default: 'Nro Tarjeta')}</td>
      <td><g:checkBox class='salesSiteCol' name="8" value="${true}" /></td>
      <td>${message(code: 'salesSite.transDate', default: 'Fecha de la Transaccion')}</td>
      <td><g:checkBox class='salesSiteCol' name="9" value="${true}" /></td>
      <td>${message(code: 'salesSite.paymentDate', default: 'Fecha Pago')}</td>
      <td><g:checkBox class='salesSiteCol' name="10" value="${true}" /></td>
      <td>${message(code: 'salesSite.amount', default: 'Monto')}</td>
      <td><g:checkBox class='salesSiteCol' name="11" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareAmount', default: 'Monto Cuota')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='salesSiteCol' name="12" value="${true}" /></td>
      <td>${message(code: 'salesSite.authorization', default: 'Autorizacion')}</td>
      <td><g:checkBox class='salesSiteCol' name="13" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareNumber', default: 'Nro Cuota')}</td>
      <td><g:checkBox class='salesSiteCol' name="14" value="${true}" /></td>
      <td>${message(code: 'salesSite.shareQty', default: 'Cant. Cuotas')}</td>
      <td><g:checkBox class='salesSiteCol' name="15" value="${true}" /></td>
      <td>${message(code: 'salesSite.liq', default: 'Liquidacion')}</td>
      <td><g:checkBox class='salesSiteCol' name="16" value="${true}" /></td>
      <td>${message(code: 'salesSite.customerId', default: 'Cliente')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='salesSiteCol' name="17" value="true"/></td>
      <td>${message(code: 'salesSite.documentId', default: 'DNI')}</td>
      <td><g:checkBox class='salesSiteCol' name="18" value="true"/></td>
      <td>${message(code: 'salesSite.receiptNumber', default: 'DNI')}</td>
      <td><g:checkBox class='salesSiteCol' name="19" value="${true}" /></td>
      <td>${message(code: 'salesSite.tid', default: 'TID')}</td>
      <td><g:checkBox class='salesSiteCol' name="20" value="${true}" /></td>    
      <td>${message(code: 'salesSite.nsu', default: 'NSU')}</td>
      <td><g:checkBox class='salesSiteCol' name="21" value="${true}" /></td>    
      <td>${message(code: 'salesSite.ro', default: 'RO')}</td>
     </tr>
     <tr>      
      <td><g:checkBox class='salesSiteCol' name="22" value="${true}" /></td>    
      <td>${message(code: 'salesSite.store', default: 'P. de Venta')}</td>
      <td><g:checkBox class='salesSiteCol' name="23" value="${true}" /></td>    
      <td>${message(code: 'salesSite.cardLot', default: 'Lote Tarjeta')}</td>
      <td><g:checkBox class='salesSiteCol' name="24" value="${true}" /></td>    
      <td>${message(code: 'salesSite.uniqueRo', default: 'Lote Tarjeta')}</td>
      <td><g:checkBox class='salesSiteCol' name="25" value="${true}" /></td>
      <td>${message(code: 'salesSite.documentNumber', default: 'Nro. Doc.')}</td>
      <td><g:checkBox class='salesSiteCol' name="26" value="${true}" /></td>
      <td>${message(code: 'salesSite.payment', default: 'Pagoo')}</td>
    </tr>
    <tr>      
      <td><g:checkBox class='salesSiteCol' name="27" value="${true}" /></td>    
      <td>${message(code: 'salesSite.period', default: 'Periodo')}</td>
      <td colspan="8">&nbsp;</td>
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
