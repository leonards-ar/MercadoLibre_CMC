$(function() {
	
    var aReceiptSelected = [];
    var aSalesSelected = [];
    var receiptList = [];
    var salesList = [];
    var conciliateList = [];
    var filterType = 'salesFilter';
    
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
		
		var receiptTable = $('#receipt_table').dataTable();
	    var receiptRows = receiptTable.$('tr.yellow');
	    
	    var salesTable = $('#sales_table').dataTable();
	    var salesRows = salesTable.$('tr.yellow');
		
	    group(receiptRows, salesRows, $('#preconciliate_table'))
	    
	    aReceiptSelected = [];
	    aSalesSelected = [];
	    
	    $('#balance').html("0.00");
	    
	    receiptTable.fnDraw(false);
	    salesTable.fnDraw(false);
		
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
	    var id = this.id
	    var index = jQuery.inArray(id, aReceiptSelected);
	    
	    $(this).toggleClass('yellow');
	    
	    var balanced = parseFloat($('#balance').text());
	    balanced = isNaN(balanced) ? 0 : balanced;
	    var monto = parseFloat($(this).find('td:eq(1)').text());
	    
    	if (index == -1) {
    		balanced += isNaN(monto)? 0 : monto;
    		aReceiptSelected.push(id)
    		
    	} else {
    	    
    		var balanced = parseFloat($('#balance').text());
    		if (balanced != 0) {
    			balanced -= isNaN(monto)? 0 : monto;
    		}
    		aReceiptSelected.splice( index, 1 );
    	}
    	
    	$('#balance').html(String(balanced.toFixed(2)));
    });

	$('#sales_table tbody tr').live('click', function() {
        var id = this.id
        var index = jQuery.inArray(id, aSalesSelected);

        var monto = parseFloat($(this).find('td:eq(1)').text());
        var balanced = parseFloat($('#balance').text());
        balanced = isNaN(balanced) ? 0 : balanced;
        
		$(this).toggleClass('yellow');

		if (index == -1) {
			balanced -= isNaN(monto) ? 0 : monto;
	         aSalesSelected.push(id)
		} else {
		    aSalesSelected.splice( index, 1 );
			if (balanced != 0) {
				balanced += isNaN(monto) ? 0 : monto;
			}
		}
	    $('#balance').html(String(balanced.toFixed(2)));
	});

	$('#preconciliate_table tbody tr').live('click',function() {
		
	    $(this).toggleClass('yellow');
	    
    });
	
	$('#desagrupar').live({
		click: function() {

			var conciliateTable = $('#preconciliate_table').dataTable();
		    var conciliatedRows = conciliateTable.$('tr.yellow');
		    
		    for(var i=0; i < conciliatedRows.length;i++){
		    	var row = conciliatedRows[i];
		    	var salesId = $(row).find('td:eq(22)').text();
		    	var receiptId = $(row).find('td:eq(7)').text();
		    	
		    	var salesIndex = jQuery.inArray(salesId, salesList);
		    	var receiptIndex = jQuery.inArray(receiptId, receiptList);
		    	
		    	receiptList.splice( receiptIndex, 1 );
		    	salesList.splice(salesIndex, 1);
		    	
		    	var conciliateIndex = -1;
		    	for(var j=0; j < conciliateList.length; j++){
		    		var pair = conciliateList[j];
		    		if(pair[0] == receiptId && pair[1] == salesId){
		    			conciliateIndex = j;
		    			break;
		    		}
		    		
		    	}
		    	
		    	if(conciliateIndex >= 0) conciliateList.splice(conciliateIndex,1);
		    	
		    	conciliateTable.fnDeleteRow(row);
		    	
		    }
		    
			var receiptTable = $('#receipt_table').dataTable();
		    var salesTable = $('#sales_table').dataTable();
		    receiptTable.fnDraw(false);
		    salesTable.fnDraw(false);
		    
		},
		mouseover: function() {
			$(this).addClass("ui-state-hover");
			$(this).css("cursor","pointer");
		},
		  mouseout: function() {
			$(this).removeClass("ui-state-hover");
		}
		
	});
	
	$('#filterType').live('change', function(){
		
		filterType = $(this).val();
		
		 $('#receipt_table').dataTable().fnDraw();
		 $('#sales_table').dataTable().fnDraw();
		
		
	});

	$('.button').find('#preconciliateButton').live('click', function() {

		var strdata = "";

		if(conciliateList.length == 0) {
			var $dialog = getDialog("No hay elementos para preconciliar");
			$dialog.dialog('open');
			
			return;
		}
		
		strdata = "ids=" + conciliateList.join(";")
		strdata +="&site=" + $('#site').val();
		strdata +="&country=" + $('#country').val();
		strdata +="&card=" + $('#card').val();
		strdata += "&period=" + $('#period').val();

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
	
    $('#minSalesAmount').live({
    	keydown: function(event) {
    		var text = $(this).val();
    		if(!validNumber(event)) {
    			event.preventDefault();
    			$(this).val(text);
    		}
    		
    	}
    });
    		
    $('#maxSalesAmount').live({
    	keydown: function(event) {
    		var text = $(this).val();
    		if(!validNumber(event)) {
    			event.preventDefault();
    			$(this).val(text);
    		}
    		
    	}
    });

    $('#minReceiptAmount').live({
    	keydown: function(event) {
    		var text = $(this).val();
    		if(!validNumber(event)) {
    			event.preventDefault();
    			$(this).val(text);
    		}
    		
    	}
    });        		

    $('#maxReceiptAmount').live({
    	keydown: function(event) {
    		var text = $(this).val();
    		if(!validNumber(event)) {
    			event.preventDefault();
    			$(this).val(text);
    		}
    		
    	}
    });        		
	
	$('.receiptCol').live('click',function(){

		showHideColumn('#receipt_table', $(this).attr('name'), this.checked);
		showHideColumn('#preconciliate_table', $(this).attr('name'), this.checked);
        if(!this.checked){
            $('#receiptColAll').attr('checked', false);
        }		
		
	});	
	
	$('#receiptColAll').live('click',function(){
		var checked = this.checked;
		$('.receiptCol').each(function() {
				this.checked = checked;
				var column = $(this).attr('name');
				showHideColumn('#receipt_table', column, checked);
				showHideColumn('#preconciliate_table',column, checked);
		});
		
	});	

	$('.salesSiteCol').live('click',function(){

		showHideColumn('#sales_table', $(this).attr('name'), this.checked);
		showHideColumn('#preconciliate_table', 15+parseInt($(this).attr('name')), this.checked);
        if(!this.checked){
            $('#salesColAll').attr('checked', false);
        }			
		
	});
	
	$('#salesColAll').live('click',function(){
		var checked = this.checked;
		$('.salesSiteCol').each(function() {
				this.checked = checked;
				showHideColumn('#sales_table', $(this).attr('name'), checked);
				showHideColumn('#preconciliate_table', 15+parseInt($(this).attr('name')), checked);
		});
		
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

    $('#fromReceiptTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "yy-mm-dd",
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
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
            onClose: function( selectedDate ) {
            	$( "#fromReceiptTransDate" ).datepicker( "option", "maxDate", selectedDate );
            }                	
        }).datepicker('show');
    });
    
    $('#fromSalesTransDate').live("focus", function(){
        $(this).datepicker({
            dateFormat: "yy-mm-dd",
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
            dateFormat: "yy-mm-dd",
            changeMonth: true,
            changeYear: true,
            showAnim: 'fadeIn',
            onClose: function( selectedDate ) {
            	$( "#fromSalesTransDate" ).datepicker( "option", "maxDate", selectedDate );
            }                
        }).datepicker('show');
    });
	
	$('#lock').click(function(){
		
		if($(this).attr('value') == 'Lock'){
			if($('#country').val()== ''  || $('#country').val()== '---' ||
		        $('#card').val()== '' || $('#card').val()== '---' ||
		        $('#site').val()== '' || $('#site').val()== '---' ||
		        $('#period').val()== '' || $('#period').val()== '---'){
		        var $dialog = getDialog(completeFilters);
                $dialog.dialog("open");
                return;
		    }
		    
			var strdata = $('#country').attr('id') + "=" + $('#country').val();
			strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
			strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();
			strdata += "&" + $('#period').attr('id') + "=" + $('#period').val();
			strdata += "&filterType = 'salesFilter'";
			
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
		            $('#period').attr("disabled", true);			    	
			    	$('#lock').attr("value","Unlock");
			    	$('#myBody').html(data);
			    	
				    $('#receipt_table').dataTable({
				        "sDom": 'lrtip',
				        "iDisplayLength": 20,
				        "aLengthMenu":[20,50,75,100,500,1000],				        
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
				            aoData.push( { "name": "filterType","value": filterType});
				            aoData.push( { "name": "selectedList", "value":receiptList.join(",") } );
				            if($('#fromReceiptTransDate').val() !='') aoData.push( { "name":"fromReceiptTransDate", "value":$('#fromReceiptTransDate').val()} );
				            if($('#toReceiptTransDate').val() !='') aoData.push( { "name":"toReceiptTransDate", "value":$('#toReceiptTransDate').val()} );
				            if($('#minReceiptAmount').val() !='') aoData.push( { "name":"minReceiptAmount", "value":$('#minReceiptAmount').val().replace(",",".")});
				            if($('#maxReceiptAmount').val() !='') aoData.push( { "name":"maxReceiptAmount", "value":$('#maxReceiptAmount').val().replace(",",".")});
				        },
				        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {

				            var index = jQuery.inArray(aData.DT_RowId, receiptList); 
				            if ( index !== -1 ) {
				                $(nRow).hide();
				            } else if ( jQuery.inArray(aData.DT_RowId, aReceiptSelected) !== -1 ) {
				                $(nRow).addClass('yellow');
				            }
				        },
                        "fnDrawCallback": function (oSettings){
                            //show hide selected columns
                            $('.receiptCol').each(function() {
                                showHideColumn('#receipt_table', $(this).attr('name'), this.checked);
                                showHideColumn('#preconciliate_table', $(this).attr('name'), this.checked);
                            });                            
                        }    
				    });
			    	
				    $('#sales_table').dataTable({
				        "sDom": 'lrtip',
				        "iDisplayLength": 20,
				        "aLengthMenu":[20,50,75,100,500,1000],				        
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
				            aoData.push( { "name": "selectedList", "value":salesList.join(",") } );
				            aoData.push( { "name": "filterType","value": filterType});
				            if($('#fromSalesTransDate').val() !='') aoData.push( { "name":"fromSalesTransDate", "value":$('#fromSalesTransDate').val()} );
				            if($('#toSalesTransDate').val() !='') aoData.push( { "name":"toSalesTransDate", "value":$('#toSalesTransDate').val()} );
				            if($('#minSalesAmount').val() !='') aoData.push( { "name":"minSalesAmount", "value":$('#minSalesAmount').val().replace(",",".")});
				            if($('#maxSalesAmount').val() !='') aoData.push( { "name":"maxSalesAmount", "value":$('#maxSalesAmount').val().replace(",",".")});
				        },
				        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {

				            var index = jQuery.inArray(aData.DT_RowId, salesList); 
				            if ( index !== -1 ) {
				                $(nRow).hide();
				            } else if ( jQuery.inArray(aData.DT_RowId, aSalesSelected) !== -1 ) {
				                $(nRow).addClass('yellow');
				            }
				        },
                        "fnDrawCallback": function (oSettings){
                            //show hide selected columns
                            $('.salesSiteCol').each(function() {
                                showHideColumn('#sales_table', $(this).attr('name'), this.checked);
                                showHideColumn('#preconciliate_table', $(this).attr('name'), this.checked);
                            });
                        }
				    });
			    	
			    	$('#preconciliate_table').dataTable({
			            "bPaginate":false,
			            "bInfo":false,
			            "bDestroy":true,
			            "sDom": 'rt',
			            "bAutoWidth":false
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
	
	function group(receiptRows, salesRows, conciliateTable){
	    var oTable = conciliateTable.dataTable({	    		
	    		"bPaginate":false,
	            "bInfo":false,
	            "bDestroy":true,
	            "sDom": 'rt',
	            "bAutoWidth":false});
	    if(receiptRows.length > 1) {
	        //iterate over receipt rows
	        for(var i=0; i < receiptRows.length; i++) {
	            var row = receiptRows[i];
	            var columnVals = [];
	            var tmpIds = [];
	            $(row).find('td').each(function(){
	                columnVals.push($(this).html());
	            });
	            var saleRow = salesRows[0]; 
	            $(saleRow).find('td').each(function(){
	                columnVals.push($(this).html());
	            });
	            
	            oTable.fnAddData(columnVals);
	            receiptList.push(row.id);
	            salesList.push(saleRow.id);
	            tmpIds.push(row.id);
	            tmpIds.push(saleRow.id);
	            conciliateList.push(tmpIds);
	        }
	    } else {
	        //iterate over sales rows
	        for(var i=0; i < salesRows.length; i++){

	            var row = salesRows[i];
	            var columnVals = [];
	            var tmpIds = [];
	            var receiptRow = receiptRows[0];
	            $(receiptRow).find('td').each(function(){
                    columnVals.push($(this).html());
                });
                
                $(row).find('td').each(function(){
                    columnVals.push($(this).html());
                });
                
                oTable.fnAddData(columnVals);
                receiptList.push(receiptRow.id);
                salesList.push(row.id);
                tmpIds.push(receiptRow.id);
                tmpIds.push(row.id);
                conciliateList.push(tmpIds);
	            
	        }
	    }
	};
	
});


