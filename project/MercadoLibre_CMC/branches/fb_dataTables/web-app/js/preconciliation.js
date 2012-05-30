$(function() {
	$('#country').chainSelect('#card', cardLink, {
		nonSelectedValue : '---'
	});

	$('#card').chainSelect('#site', siteLink, {
		nonSelectedValue : '---'
	});

	$('#agrupar').live({
	click: function() {
	    
		if ($('#sales_table tbody tr:.yellow').length == 0 || $('#receipt_table tbody tr:.yellow').length == 0) {
			var $dialog = getDialog(preconciliationNoselectionError);
			$dialog.dialog("open");
			return;
		}

		var strData = "";


	    $('#receipt_table tbody tr:.yellow').each(function(){
	        if(strData.length > 0){
	            strData += "&";
	        }
	        strData += "receiptId=" + $(this).find('td:eq(0)').find('input:hidden').val();
	    });

        $('#sales_table tbody tr:.yellow').each(function(){
            if(strData.length > 0){
                strData += "&";
            }
            strData += "salesSiteId=" + $(this).find('td:eq(0)').find('input:hidden').val();
        });		    
		    
        $('#preconciliate_table input:hidden').each(
            function() {
                if (strdata.length > 0) {
                    strdata += "&";
                }
                strdata += $(this).attr('id') + "=" + $(this).val();
            });
		    

		$.ajax({
		    type : 'POST',
			url : groupLink,
			data : strData,
			success : function(data) {
				$('#conciliado').fadeOut('fast',function() {
				    $(this).html(data).fadeIn('slow');
				});
				
				$('#sales_table').find('tr:.yellow').remove();
				$('#receipt_table').find('tr:.yellow').remove();
				
				var balanced = 0;
				$('#balance').html(String(balanced));
			},
			error : function(XMLHttpRequest,textStatus, errorThrown) {
				showError(XMLHttpRequest, textStatus,errorThrown);
			}
		})
	},
	mouseover: function() {
		$(this).addClass("ui-state-hover");
		$(this).css("cursor","pointer");
	},
	  mouseout: function() {
		$(this).removeClass("ui-state-hover");
	}
	
	});

	$('#receipt_table tbody tr').live('click',function() {

	    $(this).toggleClass('yellow');
    	if ($(this).hasClass('yellow')) {
    		var monto = parseFloat($(this).find('td:eq(8)').text());
    		var balanced = parseFloat($('#balance').text());
    		balanced = isNaN(balanced) ? 0 : balanced;
    		balanced += isNaN(monto)? 0 : monto;
    		$('#balance').html(String(balanced));
    	} else {
    		var monto = parseFloat($(this).find('td:eq(8)').text());
    		var balanced = parseFloat($('#balance').text());
    		if (!(isNaN(balanced))) {
    			balanced -= isNaN(monto)? 0 : monto;
    			$('#balance').html(String(balanced));
    		}
    	}
    });

	$('#sales_table tbody tr').live('click', function() {
		$(this).toggleClass('yellow');
		if ($(this).hasClass('yellow')) {
			var monto = parseFloat($(this).parent().parent().find('td:eq(9)').text());
			var balanced = parseFloat($('#balance').text());
			balanced = isNaN(balanced) ? 0 : balanced;
			balanced -= isNaN(monto) ? 0 : monto;
			$('#balance').html(String(balanced));
		} else {
			var monto = parseFloat($(this).parent().parent().find('td:eq(9)').text());
			var balanced = parseFloat($('#balance').text());
			if (!(isNaN(balanced))) {
				balanced += isNaN(monto) ? 0 : monto;
				$('#balance').html(String(balanced));
			}
		}
	});

	$('.filtered').find(".paginateButtons a, th.sortable a").live('click',function(event) {
		event.preventDefault();
		var url = $(this).attr('href');

		var closestDiv = $(this).closest('div');

		var strdata = $('#country').attr('id') + "=" + $('#country').val();
		strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
		strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();

		$('#preconciliate_table input:hidden').each(function() {
    		if (strdata.length > 0) {
    			strdata += "&";
    		}
    		strdata += $(this).attr('id') + "=" + $(this).val();
		});

		$(closestDiv).html($("#spinner").html());

		$.ajax({
			type : 'POST',
			url : url,
			data : strdata,
			success : function(data) {
				$(closestDiv).fadeOut('fast', function() {
					$(this).html(data).fadeIn('slow');
					updateTable(closestDiv);
				});
			},
			error : function(XMLHttpRequest, textStatus,
					errorThrown) {
				showError(XMLHttpRequest, textStatus,
						errorThrown);
				$(closestDiv).html("");
			}
		})
	});
	$('.button').find('#preconciliateButton').live('click', function() {

		var strdata = "";

		if($('#preconciliate_table input:hidden').length == 0) {
			var $dialog = getDialog("No hay elementos para preconciliar");
			$dialog.dialog('open');
			
			return;
		}
		
		$('#preconciliate_table input:hidden').each(
				function() {
					if (strdata.length > 0) {
						strdata += "&";
					}
					strdata += $(this).attr('id') + "=" + $(this).val();
				});

        var $processing = getProcessingDialog();
        
		$.ajax({
			type : 'POST',
			url : saveLink,
			data : strdata,
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
				        $(location).attr('href',exitLink);
				    } 
				});
				$dialog.dialog('open');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showError(XMLHttpRequest, textStatus,errorThrown);
			}
		});
	});	
	
	$('#receiptFilter').live({
	click: function(){
		$('#filterReceiptColumns').toggle('blind',500);
		$('#filterReceiptColumns').draggable();
	},
	mouseover: function() {
		$(this).addClass("ui-state-hover");
		$(this).css("cursor","pointer");
	},
	  mouseout: function() {
		$(this).removeClass("ui-state-hover");
	}
	});
	
	
	
	
	$('#salesSiteFilter').live({
	click: function(){
		$('#filterSalesColumns').toggle('blind',500);
		$('#filterSalesColumns').draggable();
	},	
    mouseover: function() {
		$(this).addClass("ui-state-hover");
		$(this).css("cursor","pointer");
		
	},
	  mouseout: function() {
		$(this).removeClass("ui-state-hover");
	}
	});
	
	$('.receiptCol').live('click',function(){

		var column = $(this).attr('name');

		if(this.checked) {
			$('#receipt_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').show('slide',500);
		} else {
			$('#receipt_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').hide('slide',500);
		}
		
	});	

	$('.salesSiteCol').live('click',function(){

		var column = $(this).attr('name');

		if(this.checked) {
			$('#sales_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').show('slide',500);
		} else {
			$('#sales_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').hide('slide',500);
		}
		
	});
	
	$('#lock').click(function(){
		
		if($(this).attr('value') == 'Lock'){
			var strdata = $('#country').attr('id') + "=" + $('#country').val();
			strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
			strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();
			
	        var $processing = getProcessingDialog();
	        
			$.ajax({
				type : 'POST',
				url : lockLink,
				data : strdata,
				beforeSend: function() {
				    $processing.dialog('open');
				},
				complete: function(){
				    $processing.dialog('close');
			    },
				success : function(data) {
			    	$('#country').attr("disabled", true);
			    	$('#card').attr("disabled", true);
			    	$('#site').attr("disabled", true);
			    	$('#lock').attr("value","Unlock");
			    	$('#myBody').html(data);
			    	
			    	var oReceiptTable = $('#receipt_table').dataTable({
		                "bPaginate":false,
		                "bInfo":false,
		                "sDom": 'rt',
                        "aoColumnDefs": [
                                         { "bSortable": false, "aTargets": [ 0 ] }
                                       ]                                		                
		               
		             });
			    	$('#receipt_table').find('td:nth-child(1),th:nth-child(1)').hide();
		            $('#receipt_table').find('thead tr:nth-child(1) th').each(function(i){
		                 
		                 this.innerHTML = fnCreateSelect( oReceiptTable.fnGetColumnData(i) );
		                 $('select', this).change( function () {
		                     oReceiptTable.fnFilter( $(this).val(), i );
		                 });		                 
		             });
		                     
			    	var oSalesTable = $('#sales_table').dataTable({
    	                        "bPaginate":false,
    	                        "bInfo":false,
    	                        "sDom": 'rt',
    	                        "aoColumnDefs": [
    	                                         { "bSortable": false, "aTargets": [ 0 ] }
    	                                       ]     	                        
    			    	});
			    	$('#sales_table').find('td:nth-child(1),th:nth-child(1)').hide();
			    	
                    $('#sales_table').find('thead tr:nth-child(1) th').each(function(i){
                        
                        this.innerHTML = fnCreateSelect( oSalesTable.fnGetColumnData(i) );
                        $('select', this).change( function () {
                            oSalesTable.fnFilter( $(this).val(), i);
                        });                         
                    });

				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					showError(XMLHttpRequest, textStatus,errorThrown);
				}
			});		
		} else {
			$(location).attr('href',index);
		}
	});
	
	
});

function updateTable(target){

	if($(target).attr('id') == 'receipts') {
		$('.receiptCol').each(function(){
			var column = $(this).attr('name');
			if(!this.checked){
				$('#receipt_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').hide();	
			}
		});
	} else {
		$('.salesSiteCol').each(function(){
			var column = $(this).attr('name');
			if(!this.checked){
				$('#sales_table').find('td:nth-child(' + column + '),th:nth-child(' + column + ')').hide();	
			}
		});		
		
	}

}


