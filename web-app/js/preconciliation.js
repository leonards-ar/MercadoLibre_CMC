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
		if ($('#sales_table tbody tr:.yellow').length > 1 && $('#receipt_table tbody tr:.yellow').length > 1) {
	          var $dialog = getDialog(preconciliationBadRelationError);
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
                if (strData.length > 0) {
                    strData += "&";
                }
                strData += $(this).attr('id') + "=" + $(this).val();
            });
		    

		$.ajax({
		    type : 'POST',
			url : groupLink,
			data : strData,
			success : function(data) {
				$('#conciliado').fadeOut('fast',function() {
				    $(this).html(data).fadeIn('slow');
				});

				deleteRows('#receipt_table');
				deleteRows('#sales_table');
				
				createCombos('#receipt_table');
				createCombos('#sales_table');
				
				
				var balanced = 0;
				$('#balance').html(String(balanced));
			},
			error : function(XMLHttpRequest,textStatus, errorThrown) {
				showError(XMLHttpRequest,textStatus,errorThrown);
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
	
	$('#filterType').live('change', function(){
		
		var strdata = "filterType=" + $(this).val(); 
		strdata += "&" + $('#country').attr('id') + "=" + $('#country').val();
		strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
		strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();

		
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
			url : listReceiptsLink,
			data : strdata,
			beforeSend: function() {
			    $processing.dialog('open');
			},
			complete: function(){
			    $processing.dialog('close');
		    },
			success : function(data) {
		    	$('#receipts').fadeOut('fast',function() {
		    		$(this).html(data).fadeIn('slow');
		    		}); 		    	
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showError(XMLHttpRequest, textStatus,errorThrown);
			}
		});
		
		$.ajax({
			type : 'POST',
			url : listSalesLink,
			data : strdata,
			beforeSend: function() {
			    $processing.dialog('open');
			},
			complete: function(){
			    $processing.dialog('close');
		    },
			success : function(data) {
		    	$('#sales').fadeOut('fast',function() {
		    		$(this).html(data).fadeIn('slow');
		    		}); 		    	
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				showError(XMLHttpRequest, textStatus,errorThrown);
			}
		});
		
    	createTable('#receipt_table');
    	createTable('#sales_table');
		
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

		showHideColumn('#receipt_table', $(this).attr('name'), this.checked);
		
	});	
	
	$('#receiptColAll').live('click',function(){
		var checked = this.checked;
		$('.receiptCol').each(function() {
				this.checked = checked;
				var column = $(this).attr('name');
				showHideColumn('#receipt_table', column, checked);
		});
		
	});	

	$('.salesSiteCol').live('click',function(){

		showHideColumn('#sales_table', $(this).attr('name'), this.checked);
		
	});
	
	$('#salesColAll').live('click',function(){
		var checked = this.checked;
		$('.salesSiteCol').each(function() {
				this.checked = checked;
				showHideColumn('#sales_table', $(this).attr('name'), checked);
		});
		
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
			    	
			    	createTable('#receipt_table');
			    	createTable('#sales_table');
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


