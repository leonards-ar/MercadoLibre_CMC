<div id="preconciliacion">
 <table>
 <tr>
   <td>
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
     <div id="sales" style="width:550;height:200;overflow:auto;border:1px solid #ccc;position:relative">
       <g:render template="salesSiteTable" model="[salesSiteInstanceList:salesSiteInstanceList]"/>
     </div>
   </td>      
 </tr>
 </table>
 <br/>
     <center><div id="conciliado" style="width:800;height:200;overflow:auto;">
       <g:render template="preconciliateTable"/>  
    </div></center>

    <div class="buttons">
        <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'save', 'default': 'save')}" /></span>
    </div>    
 
</div>
