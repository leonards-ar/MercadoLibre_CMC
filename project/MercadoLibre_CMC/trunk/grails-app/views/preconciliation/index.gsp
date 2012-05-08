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
    },
    nonSelectedValue:'---'
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
    },
    nonSelectedValue:'---'
  });
  
  
  $('#agrupar').live('click',function(){

    var salesSiteRow = $('#sales_table').find('tr:.yellow').clone();
    var receiptRow = $('#receipt_table').find('tr:.yellow').clone();
    
    var receiptList = new Array();
    var salesSiteList = new Array();
    $('#preconciliate_table input:hidden').each(function(){
      if($(this).attr('id') == 'receiptIds') {
        receiptList.push($(this).val());
      } else {
        salesSiteList.push($(this).val());
      }
    });

    console.log(receiptList);
    console.log(salesSiteList);    
    
    
      if(salesSiteRow.length == 0 || receiptRow == 0) {
        alert("${message(code:'preconciliation.noselection.error', default:'Seleccione un Recibo y una Venta')}");
        return;
      }     
    
    var receiptId = receiptRow.find('td:eq(1)').find('input:hidden').val();
    var salesSiteId = salesSiteRow.find('td:eq(1)').find('input:hidden').val();
    
    $.ajax({
        type: 'POST',
        url: '${createLink(action:"group")}',
        data: {salesId: salesSiteId, receiptId: receiptId, receiptIds:receiptList, salesSiteIds:salesSiteList},
        success: function(data) {
            $('#conciliado').fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
            $('#sales_table').find('tr:.yellow').remove();
            $('#receipt_table').find('tr:.yellow').remove();
            var balanced = 0;
            $('#balance').html(String(balanced));            
        }
      })
  });

  $('.receipt_check').live('click', function(){
    if(this.checked) {
      var misselected = $('#receipt_table').find('tr:.yellow').get();
      if(misselected.length > 0){
        alert("${message(code:'preconcliation.onlyone.error', default:'Seleccione solo uno') }");
        this.checked = false;
        return;
      }
      $(this).parent().parent().toggleClass('yellow');
      var monto = $(this).parent().parent().find('td:eq(4)').text();
      var balanced = parseFloat($('#balance').text());
      balanced = isNaN(balanced)? 0 : balanced;
      balanced += parseFloat(monto);
      $('#balance').html(String(balanced));
    } else {
      $(this).parent().parent().removeClass('yellow');
      var monto = $(this).parent().parent().find('td:eq(4)').text();
      var balanced = parseFloat($('#balance').text());
      if(!(isNaN(balanced))){
         balanced -= parseFloat(monto);
         $('#balance').html(String(balanced));
        }
    }
  });
  
  $('.salesSite_check').live('click',function(){
    if(this.checked) {
      var misselected = $('#sales_table').find('tr:.yellow').get();
      if(misselected.length > 0){
        alert("Select only one");
        this.checked = false;
        return;
      }    
      $(this).parent().parent().toggleClass('yellow');
      var monto = $(this).parent().parent().find('td:eq(5)').text();
      var balanced = parseFloat($('#balance').text());
      balanced = isNaN(balanced)? 0 : balanced;
      balanced -= parseFloat(monto);
      $('#balance').html(String(balanced));
    } else {
      $(this).parent().parent().removeClass('yellow');
      var monto = $(this).parent().parent().find('td:eq(5)').text();
      var balanced = parseFloat($('#balance').text());
      if(!(isNaN(balanced))){
        balanced += parseFloat(monto);
        $('#balance').html(String(balanced));       
      }
    }
  });
    
  $('.filtered').find(".paginateButtons a, th.sortable a").live('click', function(event) {
        event.preventDefault();
        var url = $(this).attr('href');

        var closestDiv = $(this).closest('div');
        
   var strdata = $('#country').attr('id') + "=" + $('#country').val();
   strdata+= "&" + $('#card').attr('id') + "=" + $('#card').val();
   strdata+= "&" + $('#site').attr('id') + "=" + $('#site').val();
   
    $('#preconciliate_table input:hidden').each(function(){
      if(strdata.length > 0) {
        strdata+= "&";
      }
       strdata+= $(this).attr('id') + "=" + $(this).val(); 
    });

    $(closestDiv).html($("#spinner").html());
 
    $.ajax({
    type: 'POST',
    url: url,
    data:strdata,
            success: function(data) {
                $(closestDiv).fadeOut('fast', function() {$(this).html(data).fadeIn('slow');});
            }
        })
    });
});

function showLoading() {
	$('#myBody').html($('#spinner').html())
}

function lockCombo() {

   $('#country').attr("disabled",true);
   $('#card').attr("disabled",true);
   $('#site').attr("disabled",true);
}

function showError(XMLHttpRequest,textStatus,errorThrown) {
//$('#errorDialog').html("<p>Ha ocurrido un error en la aplicacion. En breve lo vamos a ver!!!</p>");
//	$('#errorDialog').dialog('open');
    $('#myBody').html("");
  alert(XMLHttpRequest.responseText);

}

  </g:javascript>    
  </head>
  
  <body>
    <div id="errorDialog"/>

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
              <g:submitToRemote update="myBody" name="lock" class="save" value="Lock" id="lock" action="lock" onLoading="showLoading()" onFailure="showError(XMLHttpRequest,textStatus,errorThrown)" onSuccess="lockCombo()"/>
            </span>
          </td>

          </tr>
        </table>

      </g:form>
    <br/>
    <div id="myBody" />
  </body>
</html>