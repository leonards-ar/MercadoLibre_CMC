<div id="tabs">      
	<ul>
	  <li><a href="#tabsSalesSite" title="salesSites">${message(code:'compensation.salesSite', default:'Ventas del Sitio')}</a></li>
    <li><a href="#tabsReceipt" title="receipts">${message(code:'compensation.receipts', default:'Recibos') }</a></li>	  
	</ul>

  <div id="tabsSalesSite">
    <g:render template="salesSiteTable"/>
  </div>
  <div id="tabsReceipt">
    <g:render template="receiptTable"/>
  </div>
  

</div>