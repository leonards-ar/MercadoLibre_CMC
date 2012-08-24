$(function() {
    var aReceiptSelected = [];
    var aSalesSelected = [];
    
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
			    	$('#tabs').tabs();
			    	
                    $('#receipt_table').dataTable({
                        "bPaginate": true,
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listReceiptLink,
                        
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                        },
                        //"fnInitComplete": function(oSettings, json) {
                        //    createCombos('this');
                        //},
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            if ( jQuery.inArray(aData.DT_RowId, aReceiptSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },
                     });
                    
                    $('#sales_table').dataTable({
                        "bPaginate": true,
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listSalesLink,
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                        },
                        //"fnInitComplete": function(oSettings, json) {
                        //    createCombos('this');
                        //},
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            if ( jQuery.inArray(aData.DT_RowId, aSalesSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },

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
	
    $('#receipt_table tbody tr').live('click',function() {
        var id = this.id;
        var index = jQuery.inArray(id, aReceiptSelected);
        
        if ( index === -1 ) {
            aReceiptSelected.push( id );
        } else {
            aReceiptSelected.splice( index, 1 );
        }
        
        $(this).toggleClass('row_selected');
    });
	
    $('#sales_table tbody tr').live('click',function() {
        var id = this.id;
        var index = jQuery.inArray(id, aSalesSelected);
        
        if ( index === -1 ) {
            aSalesSelected.push( id );
        } else {
            aSalesSelected.splice( index, 1 );
        }
        
        $(this).toggleClass('row_selected');
    });
	
});

	
