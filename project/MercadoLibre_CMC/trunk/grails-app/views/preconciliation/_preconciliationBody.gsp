    <div id="preconciliacion">
    <table>
    <tr>
      <td>
        <div id="receipts" style="width:550;height:200;overflow:auto;border:1px solid #ccc;position:relative">
          <g:render template="receiptTable"/>
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
        <div id="sales" style="width:550;height:200;overflow:auto;border:1px solid #ccc;position:relative">
          <g:render template="salesSiteTable"/>
        </div>
      </td>      
    </tr>
    </table>
    </div>
      <br/>
    <div id="conciliado">
      <table>
      <tr>
      <td> 
        <div  style="width:100%;height:200;overflow:auto;"/>
 
       </td>
        <td>
        <div id="consolidatedCards" style="width:800;height:200;overflow:auto;">
          <table id="consolidatedCards_table" size="100%">
            <tr>
             <th></th>
             <th class="sortable sorted desc" ><a href="#">Usuario</a></th>
             <th class="sortable" ><a href="#">Pais</a></th>
             <th class="sortable" ><a href="#">Division</a></th>
             <th class="sortable" ><a href="#">Tarjeta</a></th>
             <th class="sortable" ><a href="#">Nro.Op.</a></th>
             <th class="sortable" ><a href="#">Monto</a></th>
             <th class="sortable" ><a href="#">Tarjeta</th>
             <th class="sortable" ><a href="#">Monto</th>             
            </tr>            
          </table>
          
       </div>
      </td>
      <td> 
        <div style="width:100;height:200;overflow:auto;"/>
 
       </td>
    </tr>
    </table>
    </div>