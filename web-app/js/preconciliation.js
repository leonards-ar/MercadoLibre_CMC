$(function() {
	$('#country').chainSelect('#card', cardLink, {
		before : function(target) // before request hide the target combobox
									// and display the loading message
		{
			$(target).attr("disabled", true);
		},
		after : function(target) // after request show the target combobox
									// and hide the loading message
		{
			$(target).attr("disabled", false);
		},
		nonSelectedValue : '---'
	});

	$('#card').chainSelect('#site', siteLink, {
		before : function(target) // before request hide the target combobox
									// and display the loading message
		{
			$(target).attr("disabled", true);
		},
		after : function(target) // after request show the target combobox
									// and hide the loading message
		{
			$(target).attr("disabled", false);
		},
		nonSelectedValue : '---'
	});

	$('#agrupar').live('click',function() {

		var salesSiteRow = $('#sales_table').find('tr:.yellow').clone();
		var receiptRow = $('#receipt_table').find('tr:.yellow').clone();

		if (salesSiteRow.length == 0 || receiptRow == 0) {
			var $dialog = getDialog(preconciliationNoselectionError);
			$dialog.dialog("open");
			return;
		}

		var strData = ""

        strData +="receiptId=" + receiptRow.find('td:eq(1)').find('input:hidden').val();
		strData +="&salesSiteId=" + salesSiteRow.find('td:eq(1)').find('input:hidden').val();
		    
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
	});

	$('.receipt_check').live('click',function() {
    	if (this.checked) {
    		var misselected = $('#receipt_table').find('tr:.yellow').get();
    		if (misselected.length > 0) {
    			var $dialog = getDialog(preconcliationOnlyoneError);
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
				var $dialog = getDialog(preconcliationOnlyoneError);
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
		
		$('#preconciliate_table input:hidden').each(
				function() {
					if (strdata.length > 0) {
						strdata += "&";
					}
					strdata += $(this).attr('id') + "=" + $(this).val();
				});

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
		

});

function showLoading() {
	$('#myBody').html($('#spinner').html())
}

function lockCombo() {

	$('#country').attr("disabled", true);
	$('#card').attr("disabled", true);
	$('#site').attr("disabled", true);
}

function showError(XMLHttpRequest, textStatus, errorThrown) {

	var $dialog = getDialog(XMLHttpRequest.responseText);

	$dialog.dialog('open');
	// prevent the default action, e.g., following a link
}

function getDialog(message) {

	var $dialog = $('<div></div>').html('<p>' + message + '</p>').dialog({
		autoOpen : false,
		title : 'Error',
		modal : true,
		buttons : {
			Ok : function() {
				$(this).dialog("close");
			}
		}
	});
	return $dialog;
}