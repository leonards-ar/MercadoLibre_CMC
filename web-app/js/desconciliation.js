$(function() {
    var aSelected = [];
    
    $('#country').chainSelect('#card', cardLink, {
        nonSelectedValue : '---'
    });

    $('#card').chainSelect('#site', siteLink, {
        nonSelectedValue : '---'
    });
    
    $('#datepicker').datepicker({
        dateFormat: "dd/mm/yy",
        changeMonth: true,
        changeYear: true,
        showAnim: 'fadeIn'
    });
    
    $('#lock').click(function(){
        
        if($(this).attr('value') == 'Lock'){
            var strdata = $('#country').attr('id') + "=" + $('#country').val();
            strdata += "&" + $('#card').attr('id') + "=" + $('#card').val();
            strdata += "&" + $('#site').attr('id') + "=" + $('#site').val();
            strdata += "&" + $('#datepicker').attr('id') + "=" + $('#datepicker').val();
            
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
                    $('#myBody').html(data);
                    
                    $('#conciliate_table').dataTable({
                        "bPaginate": true,
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listLink,
                        
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "datepicker", "value": $('#datepicker').val() } );
                        },
                        //"fnInitComplete": function(oSettings, json) {
                        //    createCombos('this');
                        //},
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
                            
                        },
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
        
        $('#desconciliateButton').live('click', function() {
            
            var strdata = "";
            
            if(aSelected.length == 0) {
                var $dialog = getDialog("No hay elementos para desconciliar");
                $dialog.dialog('open');
                
                return;
            }
            
            $.each(aSelected, function(index, value){
                if(strdata.length > 0) strdata +=",";
                
                strdata+= value;
            });
            
            var $processing = getProcessingDialog();
            
            $.ajax({
                type : 'POST',
                url : saveLink,
                data : "ids=" + strdata,
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