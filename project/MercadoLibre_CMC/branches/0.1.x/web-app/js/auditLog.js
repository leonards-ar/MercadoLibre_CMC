 $(function() {
    $('#rollbackButton').click(function() {


      var $confirmDialog = $('<div></div>').html('<p>'+confirmMessage+'</p>').dialog({
        autoOpen : false,
        title : 'Confirm',
        modal : true,
        buttons : {
          Ok : function() {
            $(this).dialog('close');
            doRollback();
          },
          Cancel: function() {
            $(this).dialog('close');
          }
        }
      });
      
      $confirmDialog.dialog('open');
      
    });              
      
  });
  
  
  function doRollback() {
  
	       var $processing = getProcessingDialog();
	        
		    $.ajax({
		      type : 'POST',
		      url : rollbackUrl,
		      data : "id=1",
		      beforeSend: function() {
		          $processing.dialog('open');
		      },
		      complete: function(){
		          $processing.dialog('close');
		      },
		      success : function(resp) {
			      var $dialog = getDialog(resp);
			      $dialog.dialog('option','title','');
			      $dialog.dialog( "option", "buttons", { 
			          "Ok": function() { 
			              $(this).dialog("close");
			          } 
			      });
			      $dialog.dialog('open');
		      },
		      error : function(XMLHttpRequest, textStatus, errorThrown) {
		        showError(XMLHttpRequest, textStatus,errorThrown);
		      }
		    });
  }
  

			