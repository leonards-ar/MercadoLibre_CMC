<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
    <g:javascript library="jquery-1.6.2.min" />
    <g:javascript library="jquery-ui-1.8.16.custom.min" />
    <g:javascript library="jquery.chainedSelects"/>
    <g:javascript library="commons"/>
    <g:javascript library="conciliation"/>
    <g:javascript>
      var cardLink = '${createLink(action:"cards")}';
      var siteLink = '${createLink(action:"sites")}';
      var groupLink = '${createLink(action:"group")}';
      var saveLink = '${createLink(action:"save")}';
      var exitLink = '${createLink(action:"exit")}';
      var lockLink = '${createLink(action:"lock")}';
      var index = '${createLink(action:"index")}';
      
      var conciliationNoselectionError = "${message(code:'conciliation.noselection.error', default:'Seleccione un Recibo y una Venta')}";
      var concliationOnlyoneError = "${message(code:'concliation.onlyone.error', default:'Seleccione solo uno') }";
    </g:javascript>
  
  </head>
  
  <body>

		<div class="nav">
		  <span class="menuButton"><a class="home" href="${createLink(action: 'exit')}"><g:message code="home" default="Home"/></a></span>
		</div>
		
		<h1><g:message code="conciliation.manual" default="Preconciliacion Manual"/></h1>  
		
    <div id="lockBox">
        <table>
          <tr>
          <td>
            <div><g:message code="conciliation.country" default="País"/></div>
            <g:select name="country" id="country" from="${countryList}" noSelection="['':'']" />
          </td>
          <td>
            <div><g:message code="conciliation.card" default="Tarjeta"/> </div>
          <g:select name="card" id="card" noSelection="['':'']"/>

          </td>
          <td>
            <div><g:message code="conciliation.site" default="Site"/> </div>
          <g:select name="site" id="site" noSelection="['':'']" />

          </td>
          <td>
            <span class="button">
              <input type="button" class="save" value="Lock" id="lock"/>
            </span>
          </td>

          </tr>
        </table>

    <br/>
    <div id="myBody" />
  </body>
</html>