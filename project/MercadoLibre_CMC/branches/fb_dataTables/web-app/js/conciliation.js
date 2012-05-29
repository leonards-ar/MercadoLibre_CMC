$(function() {
	$('#country').chainSelect('#card', cardLink, {
		nonSelectedValue : '---'
	});

	$('#card').chainSelect('#site', siteLink, {
		nonSelectedValue : '---'
	});

	$('#agrupar').live('click',function() {

		var salesSiteRow = $('#sales_table').find('tr:.yellow').clone();
		var receiptRow = $('#receipt_table').find('tr:.yellow').clone();

		if (salesSiteRow.length == 0 || receiptRow == 0) {
			var $dialog = getDialog(conciliationNoselectionError);
			$dialog.dialog("open");
			return;
		}

		var strData = ""

        strData +="receiptId=" + receiptRow.find('td:eq(1)').find('input:hidden').val();
		strData +="&salesSiteId=" + salesSiteRow.find('td:eq(1)').find('input:hidden').val();
		    
        $('#conciliate_table input:hidden').each(
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
	});

	$('.receipt_check').live('click',function() {
    	if (this.checked) {
    		var misselected = $('#receipt_table').find('tr:.yellow').get();
    		if (misselected.length > 0) {
    			var $dialog = getDialog(conciliationOnlyoneError);
    			$dialog.dialog("open");								
    			this.checked = false;
    			return;
    		}
    		$(this).parent().parent().toggleClass('yellow');
    		
    		var monto = $(this).parent().parent().find('td:eq(4)').text();
    		var balanced = parseFloat($('#balance').text());
    		balanced = isNaN(balanced) ? 0 : balanced;
    		balanced += parseFloat(monto);
    		$('#balance').html(String(balanced));
    	} else {
    		$(this).parent().parent().removeClass('yellow');
    		var monto = $(this).parent().parent().find('td:eq(4)').text();
    		var balanced = parseFloat($('#balance').text());
    		if (!(isNaN(balanced))) {
    			balanced -= parseFloat(monto);
    			$('#balance').html(String(balanced));
    		}
    	}
    });

	$('.salesSite_check').live('click', function() {
		if (this.checked) {
			var misselected = $('#sales_table').find('tr:.yellow').get();
			if (misselected.length > 0) {
				var $dialog = getDialog(conciliationOnlyoneError);
				$dialog.dialog('open');
				this.checked = false;
				return;
			}
			$(this).parent().parent().toggleClass('yellow');
			var monto = $(this).parent().parent().find('td:eq(5)').text();
			var balanced = parseFloat($('#balance').text());
			balanced = isNaN(balanced) ? 0 : balanced;
			balanced -= parseFloat(monto);
			$('#balance').html(String(balanced));
		} else {
			$(this).parent().parent().removeClass('yellow');
			var monto = $(this).parent().parent().find('td:eq(5)').text();
			var balanced = parseFloat($('#balance').text());
			if (!(isNaN(balanced))) {
				balanced += parseFloat(monto);
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

		$('#conciliate_table input:hidden').each(function() {
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
	$('.button').find('#conciliateButton').live('click', function() {

		var strdata = "";

		if($('#conciliate_table input:hidden').length == 0) {
			var $dialog = getDialog("No hay elementos para preconciliar");
			$dialog.dialog('open');
			
			return;
		}
		
		$('#conciliate_table input:hidden').each(
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
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					showError(XMLHttpRequest, textStatus,errorThrown);
				}
			});		
		} else {
			//Redirect to index action
			$(location).attr('href',index);
		}
	});
	

});
