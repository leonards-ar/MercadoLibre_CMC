<html>
  <head>
     <title><g:message code='cmc.title' default="Consolidador de Medios de Cobro"/></title>
    <meta name="layout" content="main" />
    
    <g:javascript library="jquery" />
    <g:javascript library="jquery.chainedSelects"/>
  <g:javascript>
  
   $(function(){
	 $('#country').chainSelect('#card','${createLink(action:"cards")}',
   { 
    before:function (target) //before request hide the target combobox and display the loading message
    { 
      $(target).attr("disabled",true);
    },
    after:function (target) //after request show the target combobox and hide the loading message
    { 

      $(target).attr("disabled",false);
    }
  });
  
  $('#card').chainSelect('#site','${createLink(action:"sites")}',
  { 
    before:function (target) //before request hide the target combobox and display the loading message
    { 
      $(target).attr("disabled",true);
    },
    after:function (target) //after request show the target combobox and hide the loading message
    { 
      $(target).attr("disabled",false);
    }
  });

  $('.filtered').find(".paginateButtons a, th.sortable a").live('click', function(event) {
        event.preventDefault();
        var url = $(this).attr('href');
        alert(url);
        var closestDiv = $(this).closest('div');
        
        alert($(closestDiv).html());
 
        //var sales_grid = $(this).parents("#sales_table");
        $(closestDiv).html($("#spinner").html());
 
        $.ajax({
            type: 'POST',
            url: url,
            success: function(data) {
                $(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
            }
        })
    });
    
});



  </g:javascript>    
  </head>
  
  <body>
		<div class="nav">
		  <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}"><g:message code="home" default="Home"/></a></span>
		</div>
		
		<h1><g:message code="preconciliation.manual" default="Preconciliacion Manual"/></h1>  
		
    <div id="lockBox">
      <g:form method="post" name="lockForm" id="lockForm" >
        <table>
          <tr>
          <td>
            <div><g:message code="preconciliation.country" default="País"/></div>
            <g:select name="country" id="country" from="${['Argentina', 'Bolivia', 'Brasil','Chile','Colombia','Ecuador','Mexico','Peru','Uruguay','Venezuela']}"
             noSelection="['':'']" />
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
              <g:submitToRemote update="myBody" name="lock" class="save" value="Lock" id="lock" action="lock"/>
            </span>
          </td>

          </tr>
        </table>

      </g:form>
    <br/>
    <div id="myBody" />
  </body>
</html>