
<%@ page import="com.ml.cmc.AuditLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'auditLog.label', default: 'AuditLog')}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
        <g:javascript library="jquery-1.6.2.min" />
        <g:javascript library="jquery-ui-1.8.16.custom.min" />
        <g:javascript library="commons" />
        <g:javascript library="auditLog" />
        <title><g:message code="auditLog.label"/></title>
        
        <g:javascript>
          var rollbackUrl = '${createLink(action:"rollback")}';
          var confirmMessage = '${message(code:"auditLog.confirm", default:"Esta seguro?")}'
         </g:javascript>        
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(controller:'home', action:'index')}"><g:message code="home" default="Home"/></a></span>
        </div>
        <div class="body">
            <h1><g:message code="auditLog.label" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="date" title="${message(code: 'auditLog.date', default: 'Date')}" />
                        
                            <g:sortableColumn property="time" title="${message(code: 'auditLog.time', default: 'Time')}" />
                        
                            <g:sortableColumn property="user" title="${message(code: 'auditLog.user', default: 'User')}" />
                            
                            <g:sortableColumn property="lot" title="${message(code: 'auditLog.lot', default: 'Lote')}" />
                        
                            <g:sortableColumn property="auditoryType" title="${message(code: 'auditLog.auditLogType', default: 'Auditory Type')}" />
                        
                            <th class="sortable"><g:message code="auditLog.medio" default="Medio" /></th>
                            
                            <th class="sortable"><g:message code="auditLog.description" default="Descripcion" /></th>
                            
                            <th class="sortable"><g:message code="auditLog.rollbackLot" default="Rollback" /></th>
                            
                            <th class="sortable"><g:message code="auditLog.rollback" default="Rollback" /></th>
                            
                            <th class="sortable"><g:message code="auditLog.period" default="Periodo Contable" /></th>
                            
                            <th class="sortable"><g:message code="auditLog.rollbackJob" default="Deshacer" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${auditLogInstanceList}" status="i" var="auditLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:formatDate date="${auditLogInstance.date}" /></td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "time")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "user")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "id")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "auditLogType")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "medio")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "description")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "rollbackLot")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "rollback")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "period")}</td>
                            
                            <td><input type="button" id="rollbackButton" class="ui-icon ui-icon-circle-triangle-w"/></td>                                                        
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${auditLogInstanceTotal}" />
            </div>
            
        </div>
    </body>
</html>
