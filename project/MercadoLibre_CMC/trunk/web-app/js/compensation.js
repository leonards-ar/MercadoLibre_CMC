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
	
	$('#site').change(function(){
		var site = $(this).val();
		if(site == '---') return;
		var strdata = "site=" + $(this).val();
		strdata+="&country=" + $('#country').val();
		strdata +="&card=" + $('#card').val(); 
		$.ajax({
			type : 'POST',
			url : periodLink,
			data : strdata,
			success : function(data) {
				var index = 0;
				$('#period').html("");//clear old options
				data = eval(data);//get json array
				$('#period').get(0).add(new Option('---', '---'), document.all ? 0 : null);
				index = 1;
				for (i = 0; i < data.length; i++)//iterate over all options
				{
					var item = data[i];
					
					$('#period').get(0).add(new Option(item[1],item[0]), document.all ? index++ : null);
				}

		    } 
		});
					
	});
	
	
	
	$('#lock').click(function(){
		
	    if($('#country').val()== ''  || $('#country').val()== '---' ||
		        $('#card').val()== '' || $('#card').val()== '---' ||
		        $('#site').val()== '' || $('#site').val()== '---' ||
		        $('#period').val()== '' ||  $('#period').val()== '---'){
		        var $dialog = getDialog(completeFilters);
                $dialog.dialog("open");
                return;
		    }

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
                        "sPaginationType": "full_numbers",
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listReceiptLink,
                        
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "period", "value": $('#period').val() } );
                            aoData.push( { "name": "compReceiptList", "value":compReceiptList.join(",") } );
				            if($('#fromReceiptTransDate').val() !='') aoData.push( { "name":"fromReceiptTransDate", "value":$('#fromReceiptTransDate').val()} );
				            if($('#toReceiptTransDate').val() !='') aoData.push( { "name":"toReceiptTransDate", "value":$('#toReceiptTransDate').val()} );
				            if($('#fromReceiptPaymtDate').val() !='') aoData.push( { "name":"fromReceiptPaymtDate", "value":$('#fromReceiptPaymtDate').val()});
				            if($('#toReceiptPaymtDate').val() !='') aoData.push( { "name":"toReceiptPaymtDate", "value":$('#toReceiptPaymtDate').val()}); 


                        },
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
                        "sPaginationType": "full_numbers",
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listSalesLink,
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "period", "value": $('#period').val() } );
                            aoData.push( { "name": "compSalesList", "value":compSalesList.join(",") } );       
				            if($('#fromSalesTransDate').val() !='' && jQuery.type($('#fromSalesTransDate').val()) !='undefined') aoData.push( { "name":"fromSalesTransDate", "value":$('#fromSalesTransDate').val()} );
				            if($('#toSalesTransDate').val() !='' && jQuery.type($('#toSalesTransDate').val()) !='undefined') aoData.push( { "name":"toSalesTransDate", "value":$('#toSalesTransDate').val()} );                            
                        },
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            var index = jQuery.inArray(aData.DT_RowId, compSalesList); 
                            if ( index !== -1 ) {
                              $(nRow).hide();
                            } else if ( jQuery.inArray(aData.DT_RowId, aSalesSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },
                     });
                        
                    $("div.salesBalance").html('<p id="salesBalance" align="right"><b>Balance:' + salesBalance + '</b></p>');
                    $("div.salesGroupButton").html('<p align="left"><span class="button"><input id="salesGroup" type="button" class="save" value="agrupar" id="agrupar"/></span></p>');
                    
                    $('#compensated_receipt_table').dataTable({
                        "bPaginate": true,
                        "sPaginationType": "full_numbers",
                        "bProcessing": true,
                        "bSort":false
                     });
                    
                    $('#compensated_sales_table').dataTable({
                        "bPaginate": true,
                        "sPaginationType": "full_numbers",
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
        
        var monto = parseFloat($(this).find('td:eq(1)').text());
        
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
        
        var monto = parseFloat($(this).find('td:eq(1)').text());
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
                group("#receipt_table","#compensated_receipt_table",receiptCount,compReceiptList,compReceipts);
                
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
            
            salesBalance = 0;
            $("#salesBalance").html('<b>Balance:' + salesBalance + '</b>');
            salesCount++;
            group("#sales_table","#compensated_sales_table",salesCount,compSalesList,compSales);
            
        },
        mouseover: function() {
            $(this).addClass("ui-state-hover");
            $(this).css("cursor","pointer");
        },
          mouseout: function() {
            $(this).removeClass("ui-state-hover");
        }

    });
    
    $('#compensateReceiptButton').live({
        'click': function(){
            save("#compensated_receipt_table",compReceipts,receiptCount,"F_RECIBOS",saveLink);
            receiptCount = 0;
        },
        mouseover: function() {
            $(this).addClass("ui-state-hover");
            $(this).css("cursor","pointer");
        },
          mouseout: function() {
            $(this).removeClass("ui-state-hover");
        }
        
    })
    
    $('#compensateSalesButton').live({
        'click': function(){
            save("#compensated_sales_table",compSales,salesCount,"F_VENTAS_SITE",saveLink);
            salesCount = 0;
        },
        mouseover: function() {
            $(this).addClass("ui-state-hover");
            $(this).css("cursor","pointer");
        },
          mouseout: function() {
            $(this).removeClass("ui-state-hover");
        }
        
    });
    
    $('#fromReceiptTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
        	onClose: function( selectedDate ) {
                $( "#toReceiptTransDate" ).datepicker( "option", "minDate", selectedDate );
            }                
        }).datepicker('show');
    });

    $('#toReceiptTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
            onClose: function( selectedDate ) {
            	$( "#fromReceiptTransDate" ).datepicker( "option", "maxDate", selectedDate );
            }                	
        }).datepicker('show');
    });
    
    $('#fromReceiptPaymtDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
        	onClose: function( selectedDate ) {
                $( "#toReceiptPaymtDate" ).datepicker( "option", "minDate", selectedDate );
            }                
            
        }).datepicker('show');
    });

    $('#toReceiptPaymtDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
            onClose: function( selectedDate ) {
            	$( "#fromReceiptPaymtDate" ).datepicker( "option", "maxDate", selectedDate );
            }                	
        }).datepicker('show');
    });
    
    $('#fromSalesTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
        	onClose: function( selectedDate ) {
                $( "#toSalesTransDate" ).datepicker( "option", "minDate", selectedDate );
            }                
            
        }).datepicker('show');
    });

    $('#toSalesTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "dd/mm/yy",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
            onClose: function( selectedDate ) {
            	$( "#fromSalesTransDate" ).datepicker( "option", "maxDate", selectedDate );
            }                
        }).datepicker('show');
    });
    
	$('#applyReceiptFilter').live({
		click: function(){
			aReceiptSelected = [];
			oTable = $('#receipt_table').dataTable();
			
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
	
	$('#applySalesFilter').live({
		
		click: function(){
			aSalesSelected = [];
			oTable = $('#sales_table').dataTable();
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

function group(table, compensateTable, count, list, map){
    var oTable = $(table).dataTable();
    var selectedRows = oTable.$('tr.row_selected');
    var oCompTable = $(compensateTable).dataTable();
    var tmpIds = [];
    for(var i=0; i < selectedRows.length; i++){
        row = selectedRows[i];
        var columnVals = []
        $(row).find('td').each(function(){
            columnVals.push($(this).html());
        });
        oCompTable.fnAddData(columnVals);
        var nodes = oCompTable.fnGetNodes();
        var lastTr = nodes[nodes.length - 1];
        $(lastTr).addClass((count % 2) == 0? "groupeven":"groupodd");
        list.push(selectedRows[i].id);
        tmpIds.push(selectedRows[i].id);
    }
    map.push(tmpIds);
    oTable.fnDraw();
}

function save(compensateTable, map, count, element, link) {
    var oTable = $(compensateTable).dataTable();
    //si todo sale bien... primero hay que serializar el mapa
    var strdata = "element=" + element;
    strdata += "&ids=" + map.join(";");
	strdata +="&site=" + $('#site').val();
	strdata +="&country=" + $('#country').val();
	strdata +="&card=" + $('#card').val(); 
    
    $.ajax({
        type : 'POST',
        url : saveLink,
        data : strdata,
        success : function(data) {
            var $dialog = getDialog(data);
            $dialog.dialog('option','title','');
            $dialog.dialog( "option", "buttons", { 
                "Ok": function() { 
                    $(this).dialog("close");
                    
                    oTable.fnClearTable();
                    map = [];
                    count = 0;
                } 
            });
            $dialog.dialog('open');
            
        },
        error : function(XMLHttpRequest, textStatus,
                errorThrown) {
            showError(XMLHttpRequest, textStatus,
                    errorThrown);
        }
    })

}
	
