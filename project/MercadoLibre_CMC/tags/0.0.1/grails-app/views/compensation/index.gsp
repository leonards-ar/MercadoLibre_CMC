<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
    <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
    <g:javascript library="jquery-1.6.2.min" />
    <g:javascript library="jquery-ui-1.8.16.custom.min" />
    <g:javascript library="jquery.chainedSelects"/>
    <g:javascript library="commons"/>
    <g:javascript library="compensation"/>
    <g:javascript>
      var cardLink = '${createLink(action:"cards")}';
      var siteLink = '${createLink(action:"sites")}';
      var groupLink = '${createLink(action:"group")}';
      var saveLink = '${createLink(action:"save")}';
      var exitLink = '${createLink(action:"exit")}';
      var lockLink = '${createLink(action:"lock")}';
      var index = '${createLink(action:"index")}';
      var preconciliationNoselectionError = "${message(code:'preconciliation.noselection.error', default:'Seleccione un Recibo y una Venta')}";
      var preconcliationOnlyoneError = "${message(code:'preconcliation.onlyone.error', default:'Seleccione solo uno') }";
      var $loading = getProcessingDialog();
    </g:javascript>
  
  </head>
  
  <body>

    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLink(action: 'exit')}"><g:message code="home" default="Home"/></a></span>
    </div>
    
    <h1><g:message code="compensation.label" default="Compensacion Manual"/></h1>  
    
    <div id="lockBox">
        <table>
          <tr>
          <td>
            <div><g:message code="compensation.country" default="País"/></div>
            <g:select name="country" id="country" from="${countryList}" noSelection="['':'']" />
          </td>
          <td>
            <div><g:message code="compensation.card" default="Tarjeta"/> </div>
          <g:select name="card" id="card" noSelection="['':'']"/>

          </td>
          <td>
            <div><g:message code="compensation.site" default="Site"/> </div>
          <g:select name="site" id="site" noSelection="['':'']"/>

          </td>
          <td>
            <span class="button">
              <input type="button" class="save" value="Lock" id="lock"/>
            </span>
          </td>

          </tr>
        </table>

    <br/>
    <div id="tabs"/>
  </body>
</html>