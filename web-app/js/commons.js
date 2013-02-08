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

function createCombos(target){
    var oTable = $(target).dataTable();
    $(target).find('thead tr:nth-child(1) th').each(function(i){
        
        this.innerHTML = fnCreateSelect( oTable.fnGetColumnData(i) );
        $('select', this).change( function () {
            oTable.fnFilter( $(this).val(), i);
        });                         
    });
    
}

function deleteRows(target){
    var oTable = $(target).dataTable();
    selectedRows = oTable.$('tr.yellow');
    for(var i=0; i < selectedRows.length; i++) {
        oTable.fnDeleteRow(selectedRows[i]);
    }
    
}

function createTable(target){
    $(target).dataTable({
        "bPaginate":false,
        "bInfo":false,
        "bDestroy":true,
        "sDom": 'rt',
        "bAutoWidth":false,
        "aoColumnDefs": [
                         { "bSortable": false, "aTargets": [ 0 ] }
                       ]                                                        
       
     });
    $(target).find('td:nth-child(1),th:nth-child(1)').hide();
    createCombos(target);
    
}



function refreshTable(target){
    $(target).dataTable();
    $(target).find('td:nth-child(1),th:nth-child(1)').hide();
    createCombos(target);
}


function showHideColumn(target, column, show) {
	if(show) {
		$(target).find('td:nth-child(' + column + '),th:nth-child(' + column + ')').show();
	} else {
		$(target).find('td:nth-child(' + column + '),th:nth-child(' + column + ')').hide();
	}
	
	
}

function validNumber(event) {

	return (event.keyCode == 8                                // backspace
            || event.keyCode == 9                               // tab
            || event.keyCode == 17                              // ctrl
            || event.keyCode == 46                              // delete
            || event.keyCode == 190								// .
            || event.keyCode == 188								// ,
            || event.keyCode == 173								// -
            || (event.keyCode >= 35 && event.keyCode <= 40)     // arrow keys/home/end
            || (event.keyCode >= 48 && event.keyCode <= 57)     // numbers on keyboard
            || (event.keyCode >= 96 && event.keyCode <= 105))
            
}

function clearTable(table, list) {
	
	oTable = table.dataTable();
    oTable.fnClearTable();
    list = [];
	
}