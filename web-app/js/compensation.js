$(function() {
    var aReceiptSelected = [];
    var aSalesSelected = [];
    var compReceipts = [];
    var compReceiptList = [];
    var compSales = [];
    var compSalesList = [];
    var receiptCount = 0;
    var salesCount = 0;
    
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
                        "sDom":'l<"receiptBalance">rtip<"receiptGroupButton">',
                        "bPaginate": true,
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listReceiptLink,
                        
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "compReceiptList", "value":compReceiptList.join(",") } );                                                                                                       

                        },
                        //"fnInitComplete": function(oSettings, json) {
                        //    createCombos('this');
                        //},
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            
                            var index = jQuery.inArray(aData.DT_RowId, compReceiptList); 
                            if ( index !== -1 ) {
                              $(nRow).hide();
                            } else if ( jQuery.inArray(aData.DT_RowId, aReceiptSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },
                     });
                    
                    $("div.receiptBalance").html('<p id="receiptBalance" align="right"><b>Balance:' + receiptBalance + '</b></p>');
                    $("div.receiptGroupButton").html('<p align="left"><span class="button"><input id="receiptGroup" type="button" class="save" value="agrupar" id="agrupar"/></span></p>');

                    
                    $('#sales_table').dataTable({
                        "sDom":'l<"salesBalance">rtip<"salesGroupButton">',
                        "bPaginate": true,
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listSalesLink,
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "compSalesList", "value":compSalesList.join(",") } );                            
                        },
                        //"fnInitComplete": function(oSettings, json) {
                        //    createCombos('this');
                        //},
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            var index = jQuery.inArray(aData.DT_RowId, compReceiptList); 
                            if ( index !== -1 ) {
                              $(nRow).hide();
                            } else if ( jQuery.inArray(aData.DT_RowId, aReceiptSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },
                     });
                        
                    $("div.salesBalance").html('<p id="salesBalance" align="right"><b>Balance:' + salesBalance + '</b></p>');
                    $("div.salesGroupButton").html('<p align="left"><span class="button"><input id="salesGroup" type="button" class="save" value="agrupar" id="agrupar"/></span></p>');
                    
                    $('#compensated_receipt_table').dataTable({
                        "bPaginate": true,
                        "bProcessing": true,
                        "bSort":false
                     });
                    
                    $('#compensated_sales_table').dataTable({
                        "bPaginate": true,
                        "bProcessing": true,
                        "bSort":false
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
        
        var monto = parseFloat($(this).find('td:eq(3)').text());
        
        if ( index == -1 ) {
            aReceiptSelected.push( id );
            receiptBalance+= isNaN(monto)? 0 : monto;
        } else {
            aReceiptSelected.splice( index, 1 );
            receiptBalance-= isNaN(monto)? 0 : monto;
        }
        
        $("#receiptBalance").html('<b>Balance:' + String(receiptBalance.toFixed(2)) + '</b>');        
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

        $('#salesBalance').html("<b>Balance: " + String(salesBalance.toFixed(2)) + "</b>");        
        $(this).toggleClass('row_selected');
    });
    
    $('#receiptGroup').live({
            'click':function(){
                if ($('#receipt_table tbody tr:.row_selected').length <= 1) {
                    var $dialog = getDialog(compensationNoselectionError);
                    $dialog.dialog("open");
                    return;
                }
                receiptBalance = 0;
                $("#receiptBalance").html('<b>Balance:' + receiptBalance + '</b>');
                receiptCount++;
                compReceipts.push(aReceiptSelected);
                var oTable = $("#receipt_table").dataTable();
                var selectedRows = oTable.$('tr.row_selected');
                var compTable = $("#compensated_receipt_table").dataTable();
                for(var i=0; i < selectedRows.length; i++){
                    row = selectedRows[i];
                    var columnVals = []
                    $(row).find('td').each(function(){
                        columnVals.push($(this).html());
                    });
                    compTable.fnAddData(columnVals);
                    var nodes = compTable.fnGetNodes();
                    var lastTr = nodes[nodes.length - 1];
                    $(lastTr).addClass((receiptCount % 2) == 0? "groupeven":"groupodd");
                    compReceiptList.push(selectedRows[i].id);
                }
                
                oTable.fnDraw();
            },
            mouseover: function() {
                $(this).addClass("ui-state-hover");
                $(this).css("cursor","pointer");
            },
              mouseout: function() {
                $(this).removeClass("ui-state-hover");
            }

    });
    
    $('#salesGroup').live({
        'click':function(){
            if ($('#sales_table tbody tr:.row_selected').length <= 1) {
                var $dialog = getDialog(compensationNoselectionError);
                $dialog.dialog("open");
                return;
            }
            salesCount++;
            
            var rows = [];
            var oTable = $("#sales_table").dataTable();
            var selectedRows = oTable.$('tr.row_selected');
            var tmpIds = [];
            
            for(var i=0; i < selectedRows.length; i++){
                row = selectedRows[i];
                var columnVals = []
                $(row).find('td').each(function(){
                    columnVals.push($(this).html());
                });
                rows.push(columnVals);
                compSalesList.push(selectedRows[i].id);
                tmpIds.push(selectedRows[i].id);
            }
            compReceipts.push(tmpIds);
            $("#compensated_sales_table").dataTable().fnAddData(rows);
            oTable.fnDraw();
        },
        mouseover: function() {
            $(this).addClass("ui-state-hover");
            $(this).css("cursor","pointer");
        },
          mouseout: function() {
            $(this).removeClass("ui-state-hover");
        }

    });
    
	
});

	
