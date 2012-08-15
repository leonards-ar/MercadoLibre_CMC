$(function() {
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
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": listLink,
                        "sServerMethod": "POST",
                        "fnServerParams": function ( aoData ) {
                            aoData.push( { "name": "country", "value": $('#country').val() } );
                            aoData.push( { "name": "card", "value": $('#card').val() } );
                            aoData.push( { "name": "site", "value": $('#site').val() } );
                            aoData.push( { "name": "datepicker", "value": $('#datepicker').val() } );
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
            
        }); 
        
        $('#desconciliationColAll').live('click',function(){
            var checked = this.checked;
            $('.desconciliationCol').each(function() {
                    this.checked = checked;
                    var column = $(this).attr('name');
                    showHideColumn('#conciliate_table', column, checked);
            });
            
        });
    
    
});