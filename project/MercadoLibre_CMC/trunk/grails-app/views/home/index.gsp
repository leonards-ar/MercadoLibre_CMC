<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
  </head>
  
  <body>
    <div class="nav">
      <!-- sec:ifAnyGranted roles="ROLE_USER,ROLE_ADMIN"-->
      <span class="menuButton">
        <g:link controller="preconciliation" action="index"><g:message code="cmc.bar.preconciliation"/></g:link>
      </span>
      <span class="menuButton">
        <g:link controller="conciliation" action="index"><g:message code="cmc.bar.conciliation"/></g:link>
      </span>
      <span class="menuButton">
        <g:link controller="#" action="#"><g:message code="cmc.bar.compensation"/></g:link>
      </span>
      <span class="menuButton">
        <g:link controller="#" action="#"><g:message code="cmc.bar.despreconciliation"/></g:link>
      </span>
      <span class="menuButton">
       <g:link controller="#" action="#"><g:message code="cmc.bar.desconciliation"/></g:link>
      </span>
      <span class="menuButton">
        <g:link controller="auditLog" action="index"><g:message code="cmc.bar.auditory"/></g:link>
      </span>
      <!-- /sec:ifAnyGranted -->
    </div>
  </body>
</html>