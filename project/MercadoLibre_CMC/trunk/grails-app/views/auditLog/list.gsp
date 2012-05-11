
<%@ page import="com.ml.cmc.AuditLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'auditLog.label', default: 'AuditLog')}" />
        <link rel="stylesheet" href="${createLinkTo(dir:'css/smoothness',file:'jquery-ui-1.8.20.custom.css')}" type="text/css" media="screen" charset="utf-8">
        <g:javascript library="jquery-1.6.2.min" />
        <g:javascript library="jquery-ui-1.8.16.custom.min" />
        <title><g:message code="auditLog.label"/></title>
        
        <g:javascript>
          $(function() {
            $('.ui-icon ui-icon-circle-triangle-w').click(function() {
              
              var $confirmDialog = $('<div></div>').html('<p>' + message + '</p>').dialog({
						    autoOpen : false,
						    title : 'Error',
						    modal : true,
						    buttons : {
						      Ok : function() {
						        $(this).dialog("close");
						        doRollback();
						        
						      }
						      Cancel: function() {
						        $(this).dialog("close");
						      }
						    }
					    }
					    
					    $confirmDialog.dialog('open');
					    
            });
            
          });
          function doRollback() {
          
			       var $processing = $('<div></div>').html('<p> Procesando...' + '</p>' + $("#spinner").html()).dialog({
			            autoOpen : false,
			            modal : true,
			            closeOnEscape: false,
			            open: function(event, ui) { 
			              //hide close button.
			              $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
			            }
			        });
			        
						    $.ajax({
						      type : 'POST',
						      url : ${createLink(action:'rollback') },
						      data : {id: 1},
						      beforeSend: function() {
						          $processing.dialog('open');
						      },
						      complete: function(){
						          $processing.dialog('close');
						        },
						      success : function(data) {
						        var $dialog = getDialog(data);
						        $dialog.dialog('option','title','');
						        $dialog.dialog( "option", "buttons", { 
						            "Ok": function() { 
						                $(this).dialog("close");
						            } 
						        });
						        $dialog.dialog('open');
						      },
						      error : function(XMLHttpRequest, textStatus, errorThrown) {
						        showError(XMLHttpRequest, textStatus,errorThrown);
						      }
						    });
          }
        </g:javascript>        
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(action:'home')}"><g:message code="home" default="Home"/></a></span>
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
                        
                            <th><g:message code="auditLog.medio" default="Medio" /></th>
                            
                            <th><g:message code="auditLog.description" default="Descripcion" /></th>
                            
                            <th><g:message code="auditLog.rollbackLot" default="Rollback" /></th>
                            
                            <th><g:message code="auditLog.rollback" default="Rollback" /></th>
                            
                            <th><g:message code="auditLog.period" default="Periodo Contable" /></th>
                            
                            <th><g:message code="auditLog.rollbackJob" default="Deshacer" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${auditLogInstanceList}" status="i" var="auditLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${auditLogInstance.id}">${fieldValue(bean: auditLogInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${auditLogInstance.date}" /></td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "time")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "user")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "id")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "auditLogType")}</td>
                        
                            <td>${fieldValue(bean: auditLogInstance, field: "medio")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "description")}</td>
                            
                            <td>${fieldValue(bean: auditLogInstance, field: "rollbackLot")}</td>
                            
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
