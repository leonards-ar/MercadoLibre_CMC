<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
    <g:javascript library="jquery-1.6.2.min" />
    <g:javascript library="jquery-ui-1.8.16.custom.min" />
    <g:javascript library="jquery.chainedSelects"/>
    <g:javascript library="preconciliation"/>
    <g:javascript>
      var cardLink = '${createLink(action:"cards")}';
      var siteLink = '${createLink(action:"sites")}';
      var groupLink = '${createLink(action:"group")}';
      var saveLink = '${createLink(action:"save")}';
      var exitLink = '${createLink(action:"exit")}';
      var preconciliationNoselectionError = "${message(code:'preconciliation.noselection.error', default:'Seleccione un Recibo y una Venta')}";
      var preconcliationOnlyoneError = "${message(code:'preconcliation.onlyone.error', default:'Seleccione solo uno') }";
    </g:javascript>
  
  </head>
  
  <body>

		<div class="nav">
		  <span class="menuButton"><a class="home" href="${createLink(action: 'exit')}"><g:message code="home" default="Home"/></a></span>
		</div>
		
		<h1><g:message code="preconciliation.manual" default="Preconciliacion Manual"/></h1>  
		
    <div id="lockBox">
      <g:form method="post" name="lockForm" id="lockForm" >
        <table>
          <tr>
          <td>
            <div><g:message code="preconciliation.country" default="País"/></div>
            <g:select name="country" id="country" from="${countryList}" noSelection="['':'']" />
          </td>
          <td>
            <div><g:message code="preconciliation.card" default="Tarjeta"/> </div>
          <g:select name="card" id="card" noSelection="['':'']" disabled="true"/>

          </td>
          <td>
            <div><g:message code="preconciliation.site" default="Site"/> </div>
          <g:select name="site" id="site" noSelection="['':'']" disabled="true"/>

          </td>
          <td>
            <span class="button">
              <g:submitToRemote update="myBody" name="lock" class="save" value="Lock" id="lock" action="lock" onLoading="showLoading()" onFailure="showError(XMLHttpRequest,textStatus,errorThrown);" onSuccess="lockCombo()"/>
            </span>
          </td>

          </tr>
        </table>

      </g:form>
    <br/>
    <div id="myBody" />
  </body>
</html>