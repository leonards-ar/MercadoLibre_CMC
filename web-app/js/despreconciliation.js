$(function() {
    var aSelected = [];
    
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
        
        if($(this).attr('value') == 'Lock'){
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
                    
                    $('#conciliate_table').dataTable({
				        "sDom": 'lrtip',
				        "sPaginationType": "full_numbers",
				        "bProcessing": true,
				        "bServerSide": true,        
				        "sAjaxSource": listLink,
				        "sServerMethod": "POST",
				        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "period", "value": $('#period').val() } );
				            if($('#fromReceiptTransDate').val() !='') aoData.push( { "name":"fromReceiptTransDate", "value":$('#fromReceiptTransDate').val()} );
				            if($('#toReceiptTransDate').val() !='') aoData.push( { "name":"toReceiptTransDate", "value":$('#toReceiptTransDate').val()} );
				            if($('#fromSalesTransDate').val() !='') aoData.push( { "name":"fromSalesTransDate", "value":$('#fromSalesTransDate').val()} );
				            if($('#toSalesTransDate').val() !='') aoData.push( { "name":"toSalesTransDate", "value":$('#toSalesTransDate').val()} );
                        },
                        "fnRowCallback": function( nRow, aData, iDisplayIndex ) {
                            if ( jQuery.inArray(aData.DT_RowId, aSelected) !== -1 ) {
                                $(nRow).addClass('row_selected');
                            }
                        },
                        "fnDrawCallback": function (oSettings){
                            //show hide selected columns
                            $('.desconciliationCol').each(function() {
                                showHideColumn('#conciliate_table', $(this).attr('name'), this.checked);
                            });                            
                        }
                        
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
        
        $('.desconciliationCol').live('click',function(){

            showHideColumn('#conciliate_table', $(this).attr('name'), this.checked);
            
            if(!this.checked){
                $('#desconciliationColAll').attr('checked', false);
            }
            
        }); 
        
        $('#desconciliationColAll').live('click',function(){
            var checked = this.checked;
            $('.desconciliationCol').each(function() {
                    this.checked = checked;
                    var column = $(this).attr('name');
                    showHideColumn('#conciliate_table', column, checked);
            });
            
        });
        
        $('#conciliate_table tbody tr').live('click',function() {
            var id = this.id;
            var index = jQuery.inArray(id, aSelected);
            
            if ( index === -1 ) {
                aSelected.push( id );
            } else {
                aSelected.splice( index, 1 );
            }
            
            $(this).toggleClass('row_selected');
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
        
    	$('#applyReceiptFilter').live({
    		click: function(){
    			aSelected = [];
    			oTable = $('#conciliate_table').dataTable();
    			
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

        $('#desconciliateButton').live('click', function() {
            
            var strdata = "";
            
            if(aSelected.length == 0) {
                var $dialog = getDialog("No hay elementos para desconciliar");
                $dialog.dialog('open');
                
                return;
            }
            
            strdata +="ids=" + aSelected.join(",")
        	strdata +="&site=" + $('#site').val();
        	strdata +="&country=" + $('#country').val();
        	strdata +="&card=" + $('#card').val(); 

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
        
    
});