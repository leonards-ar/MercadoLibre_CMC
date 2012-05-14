function getProcessingDialog() {
    var $processing = $('<div></div>').html('<cener><p> Procesando...' + '</p>' + $("#spinner").html() + '</cener>').dialog({
        autoOpen : false,
        modal : true,
        closeOnEscape: false,
        open: function(event, ui) { 
          //hide close button.
          $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        }
    });
    
    return $processing;
	
}

function showError(XMLHttpRequest, textStatus, errorThrown) {
    
    var $dialog = getDialog(XMLHttpRequest.responseText);
  
    $dialog.dialog('open');
}

function getDialog(message) {
	
	  var $dialog = $('<div></div>').html('<p>' + message + '</p>').dialog({
	    autoOpen : false,
	    title : 'Error',
	    modal : true,
	    buttons : {
	      Ok : function() {
	        $(this).dialog("close");
	      }
	    }
	  });
	  return $dialog;				            
}