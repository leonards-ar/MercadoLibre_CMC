$(function() {
    var aReceiptSelected = [];
    var aSalesSelected = [];
    var compReceipts = [];
    var compReceiptList = [];
    var compSales = [];
    var compSalesList = [];
    var receiptCount = 0;
    var salesCount = 0;
    
	$('#country').chainSelect('#card', cardLink, {
		nonSelectedValue : '---'
	});

	$('#card').chainSelect('#site', siteLink, {
		nonSelectedValue : '---'
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
			var strData = "";

			
		    $('#receipt_table tbody tr:.yellow').each(function(){
		        if(strData.length > 0){
		            strData += "&";
		        }
		        strData += "receiptId=" + $(this).find('td:eq(0)').find('input:hidden').val();
		    });

	        $('#sales_table tbody tr:.yellow').each(function(){
	            if(strData.length > 0){
	                strData += "&";
	            }
	            strData += "salesSiteId=" + $(this).find('td:eq(0)').find('input:hidden').val();
	        });		
	    
	        $('#preconciliate_table input:hidden').each(
	            function() {
	                if (strData.length > 0) {
	                    strData += "&";
	                }
	                strData += $(this).attr('id') + "=" + $(this).val();
	            });
			    

			$.ajax({
			    type : 'POST',
				url : groupLink,
				data : strData,
				success : function(data) {
					$('#conciliado').fadeOut('fast',function() {
					    $(this).html(data).fadeIn('slow');
					});

					deleteRows('#receipt_table');
					deleteRows('#sales_table');
					
					createCombos('#receipt_table');
					createCombos('#sales_table');
					
					
					var balanced = 0;
					$('#balance').html(String(balanced));
				},
				error : function(XMLHttpRequest,textStatus, errorThrown) {
					showError(XMLHttpRequest,textStatus,errorThrown);
				}
			})
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
	    var monto = parseFloat($(this).find('td:eq(8)').text());
	    
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

        var monto = parseFloat($(this).find('td:eq(9)').text());
        var balanced = parseFloat($('#balance').text());
        balanced = isNaN(balanced) ? 0 : balanced;
        
		$(this).toggleClass('yellow');
		if ($(this).hasClass('yellow')) {
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
			    	
			    	createTableServer('#receipt_table', listReceiptLink, compReceiptList, aReceiptSelected);
			    	createTableServer('#sales_table', listSalesLink, compSalesList, aSalesSelected);
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
	
	function createTableServer(target, link, compList, selectedList){
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
	            aoData.push( { "name": "compReceiptList", "value":compList.join(",") } );
	        },
	        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
	            var index = jQuery.inArray(aData.DT_RowId, compList); 
	            if ( index !== -1 ) {
	                $(nRow).hide();
	            } else if ( jQuery.inArray(aData.DT_RowId, selectdList) !== -1 ) {
	                $(nRow).addClass('yellow');
	            }
	        },    
	    });
	};
});
