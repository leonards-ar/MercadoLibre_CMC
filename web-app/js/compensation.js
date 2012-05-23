$(function() {
	$('#country').chainSelect('#card', cardLink, {
		nonSelectedValue : '---'
	});

	$('#card').chainSelect('#site', siteLink, {
		nonSelectedValue : '---'
	});
	
	$('.filtered').find(".paginateButtons a, th.sortable a").live('click',function(event) {
		event.preventDefault();
		var url = $(this).attr('href');

		var divTab = $(this).closest('div');

		var strdata = $('#country').attr('id') + "=" + $('#country').val();
		strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
		strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();

		
		$.ajax({
			type : 'POST',
			url : url,
			data : strdata,
			success : function(data) {
				$(divTab).fadeOut('fast', function() {
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
			    	$('#tabs').html(data);
			    	$('#tabs').tabs({
			    		ajaxOptions: {
			    		error : function(XMLHttpRequest, textStatus, errorThrown) {
			    			showError(XMLHttpRequest, textStatus, errorThrown);
			    			},
			    			data: $('#country').attr('id') + "=" + $('#country').val() +
			    			      "&" + $('#card').attr('id') + "=" + $('#card').val() +
			    			      "&" + $('#site').attr('id') + "=" + $('#site').val()
			    		}		
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
	
	$('.receipt_check').live('click',function() {
		alert('si man estoy aca');
		$(this).parent().parent().toggleClass('yellow');
    });
	
	$('.salesSite_check').live('click', function() {
		$(this).parent().parent().toggleClass('yellow');			
		
	});	
	
});

	
