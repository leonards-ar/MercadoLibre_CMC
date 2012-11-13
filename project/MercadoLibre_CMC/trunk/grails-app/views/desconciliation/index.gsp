<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" title="custom" href="${createLinkTo(dir:'DataTables/media/css',file:'demo_table.css')}" type="text/css" charset="utf-8">
    
    <g:javascript library="jquery-1.6.2.min" />
    <g:javascript library="jquery-ui-1.8.16.custom.min" />
    <g:javascript library="jquery.chainedSelects"/>
    <g:javascript library="commons"/>
    <g:javascript library="desconciliation"/>
    <script type="text/javascript" src="${resource(dir:'DataTables/media/js',file:'jquery.dataTables.js')}"></script>
    <g:javascript library="jquery.dataTables.columnFilter"/>    
    <g:javascript>
      var cardLink = '${createLink(action:"cards")}';
      var siteLink = '${createLink(action:"sites")}';
      var periodLink = '${createLink(action:"periods")}';
      var saveLink = '${createLink(action:"save")}';
      var exitLink = '${createLink(action:"exit")}';
      var lockLink = '${createLink(action:"lock")}';
      var index = '${createLink(action:"index")}';
      var listLink = '${createLink(action:"list")}';
      var conciliationNoselectionError = "${message(code:'desconciliation.noselection.error', default:'Seleccione un Recibo y una Venta')}";
      var concliationOnlyoneError = "${message(code:'desconcliation.onlyone.error', default:'Seleccione solo uno') }";

    </g:javascript>
  
  </head>
  
  <body>

		<div class="nav">
		  <span class="menuButton"><a class="home" href="${createLink(action: 'exit')}"><g:message code="home" default="Home"/></a></span>
		</div>
		
		<h1><g:message code="desconciliation.manual" default="Preconciliacion Manual"/></h1>  
		
    <div id="lockBox">
        <table>
          <tr>
          <td>
            <div><g:message code="desconciliation.country" default="País"/></div>
            <g:select name="country" id="country" from="${countryList}" noSelection="['':'']" />
          </td>
          <td>
            <div><g:message code="desconciliation.card" default="Tarjeta"/> </div>
          <g:select name="card" id="card" noSelection="['':'']"/>
          </td>
          <td>
            <div><g:message code="desconciliation.site" default="Site"/> </div>
          <g:select name="site" id="site" noSelection="['':'']" />
          </td>
          <td>
            <div><g:message code="conciliation.period" default="Site"/> </div>
            <g:select name="period" id="period" optionKey="id" noSelection="['':'']" />
          </td>
          <td>
            <span class="button">
              <input type="button" class="save" value="Lock" id="lock"/>
            </span>
          </td>

          </tr>
        </table>
      </div>
      <br/>
    
    <div id="myBody"></div>
  </body>
</html>