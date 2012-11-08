$(function() {
    var aReceiptSelected = [];
    var aSalesSelected = [];
    var receiptList = [];
    var salesList = [];
    var conciliateList = [];
    
    var receiptCount = 0;
    var salesCount = 0;
    
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
		$('#sales_table tbody tr:.yellow').length
			if ($('#sales_table tbody tr:.yellow').length == 0 || $('#receipt_table tbody tr:.yellow').length == 0) {
				var $dialog = getDialog(conciliationNoselectionError);
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
			
		    group(receiptRows, salesRows, $('#conciliate_table'))
		    
		    aReceiptSelected = [];
		    aSalesSelected = [];
		    
		    receiptTable.fnDraw();
		    salesTable.fnDraw();
		    
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

	$('#conciliateButton').live('click', function() {
		
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
		
        $('#salesSiteFilterTable').live({
            click: function(){
                $('#filterSalesTable').toggle('blind',500);
                $('#filterSalesTable').draggable();
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
                showAnim: 'fadeIn'
            }).datepicker('show');
        });

        $('#toReceiptTransDate').live("focus", function(){
            $(this).datepicker({
                dateFormat: "dd/mm/yy",
                changeMonth: true,
                changeYear: true,
                showAnim: 'fadeIn'
            }).datepicker('show');
        });
        
        $('#fromReceiptPaymtDate').live("focus", function(){
            $(this).datepicker({
                dateFormat: "dd/mm/yy",
                changeMonth: true,
                changeYear: true,
                showAnim: 'fadeIn'
            }).datepicker('show');
        });

        $('#toReceiptPaymtDate').live("focus", function(){
            $(this).datepicker({
                dateFormat: "dd/mm/yy",
                changeMonth: true,
                changeYear: true,
                showAnim: 'fadeIn'
            }).datepicker('show');
        });
        
        $('#fromSalesTransDate').live("focus", function(){
            $(this).datepicker({
                dateFormat: "dd/mm/yy",
                changeMonth: true,
                changeYear: true,
                showAnim: 'fadeIn'
            }).datepicker('show');
        });

        $('#toSalesTransDate').live("focus", function(){
            $(this).datepicker({
                dateFormat: "dd/mm/yy",
                changeMonth: true,
                changeYear: true,
                showAnim: 'fadeIn'
            }).datepicker('show');
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
		    if($('#country').val()== ''  || $('#country').val()== '---' ||
		        $('#card').val()== '' || $('#card').val()== '---' ||
		        $('#site').val()== '' || $('#site').val()== '---' ||
		        $('#period').val()== ''){
		        var $dialog = getDialog(completeFilters);
                $dialog.dialog("open");
                return;
		    }
		    
			var strdata = $('#country').attr('id') + "=" + $('#country').val();
			strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
			strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();
			strdata += "&" + $('#period').attr('id') + "=" + $('#period').val();
			
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
			    	
			    	var extReceiptParams = {1:{"name":"fromReceiptTransDate", "value":$('#fromReceiptTransDate').val()},
			    	                        2:{"name":"toReceiptTransDate", "value":$('#toReceiptTransDate').val()},
			    	                        3:{"name":"fromReceiptPaymtDate", "value":$('#fromReceiptPaymtDate').val()},
			    							4:{"name":"toReceiptPaymtDate", "value":$('#toReceiptPaymtDate').val()}};
			    	
			    	var extSalesParams = {1:{"name":"fromSalesTransDate", "value":$('#fromSalesTransDate').val()},
			    	                      2:{"name":"toSalesTransDate", "value":$('#toSalesTransDate').val()}};
			    	
			    	createTableServer('#receipt_table', listReceiptLink, receiptList, aReceiptSelected, extReceiptParams);
			    	createTableServer('#sales_table', listSalesLink, salesList, aSalesSelected, extSalesParams);
			    	
			    	$('conciliate_table').dataTable({
			    		"sDom":"rt",
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
			//Redirect to index action
			$(location).attr('href',index);
		}
	});
	
	function createTableServer(target, link, compList, selectedList, extraParams){
	    $(target).dataTable({
	        "sDom": 'lrtip',
	        "sPaginationType": "full_numbers",
	        "bProcessing": true,
	        "bServerSide": true,        
	        "sAjaxSource": link,
	        "sServerMethod": "POST",
	        "fnServerParams": function ( aoData ) {
	            aoData.push( { "name": "country", "value": $('#country').val() } );
	            aoData.push( { "name": "card", "value": $('#card').val() } );
	            aoData.push( { "name": "site", "value": $('#site').val() } );
	            aoData.push( { "name": "period", "value": $('#period').val() } );
	            aoData.push( { "name": "selectedList", "value":compList.join(",") } );
	            for(var i=0; i<extraParams; i++){
	            	aoData.push({ "name":extraParams[i].name, "value":extraParams[i].value})
	            }
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {

	            var index = jQuery.inArray(aData.DT_RowId, compList); 
	            if ( index !== -1 ) {
	                $(nRow).hide();
	            } else if ( jQuery.inArray(aData.DT_RowId, selectedList) !== -1 ) {
	                $(nRow).addClass('yellow');
	            }
	        },    
	    });
	};
	
	function group(receiptRows, salesRows, conciliateTable){
	    var oTable = conciliateTable.dataTable();
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
