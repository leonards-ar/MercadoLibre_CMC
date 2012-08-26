$(function() {
    var aReceiptSelected = [];
    var aSalesSelected = [];
    
    var receiptBalance;
    var salesBalance;
    
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
		
	    receiptBalance = 0;
	    salesBalance = 0;
	    
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
                        "sDom":'l<"receiptBalance">rtip',
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
                    
                    $("div.receiptBalance").html('<p id="receiptBalance" align="right"><b>Balance:' + receiptBalance + '</b></p>');
                    
                    $('#sales_table').dataTable({
                        "sDom":'l<"salesBalance">rtip',
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
                    $("div.salesBalance").html('<p id="salesBalance" align="right"><b>Balance:' + salesBalance + '</b></p>');
			    	
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
        
        var monto = parseFloat($(this).find('td:eq(3)').text());
        
        if ( index == -1 ) {
            aReceiptSelected.push( id );
            receiptBalance+= isNaN(monto)? 0 : monto;
        } else {
            aReceiptSelected.splice( index, 1 );
            receiptBalance-= isNaN(monto)? 0 : monto;
        }
        
        $("#receiptBalance").html('<b>Balance:' + receiptBalance + '</b>');        
        $(this).toggleClass('row_selected');
    });
	
    $('#sales_table tbody tr').live('click',function() {
        var id = this.id;
        var index = jQuery.inArray(id, aSalesSelected);
        
        var monto = parseFloat($(this).find('td:eq(3)').text());
        if ( index == -1 ) {
            aSalesSelected.push( id );
            salesBalance+= isNaN(monto)? 0 : monto;
            
        } else {
            aSalesSelected.splice( index, 1 );
            salesBalance-= isNaN(monto)? 0 : monto;
        }

        $('#salesBalance').html("<b>Balance: " + String(salesBalance.toFixed(2))) + "</b>";        
        $(this).toggleClass('row_selected');
    });
	
});

	
