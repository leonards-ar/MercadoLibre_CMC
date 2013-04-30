<html>
  <head>
    <title><g:layoutTitle default="Conciliacion de Medios de Cobro" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
    <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'cmc.css')}" type="text/css" media="screen" charset="utf-8">
    <g:javascript library="application" />
</head>
<body>
  <div id="spinner" class="spinner" style="display:none;">
    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
  </div>
  <div class="logo"><img src="${resource(dir:'images',file:'logo-mercadolibre.png')}" alt="${message(code:'cmc.title', default:'Consolidador de Medios de Cobro')}" />

      <p>  </p>
      <div class="welcomeBox">
        <br/><br/>
        <sec:ifLoggedIn>
          <g:link controller="logout"><g:message code="logout" default="Abandonar" /></g:link>
          <br/><br/>
          <g:message code="login.bar.welcomeback" args="${[sec.loggedInUserInfo(field:'displayName')]}"/>
        </sec:ifLoggedIn>
        <br/><br/>
  </div>
<g:layoutBody/>
</body>
</html>