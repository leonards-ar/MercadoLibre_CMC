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
});

function showLoading() {
	$loading.dialog("open");
}

function closeLoading() {
	$loading.dialog("close");
}

function lockCombo() {

	$('#country').attr("disabled", true);
	$('#card').attr("disabled", true);
	$('#site').attr("disabled", true);
	
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
}
