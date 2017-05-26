$(function(){
	var conten = null;
	conten = document.getElementById("content");
	if(conten != null){
	    var editor = CodeMirror.fromTextArea(conten, {
	        mode: "text/html",
	        lineNumbers: true,
	        lineWrapping: true, //是否自动换行
	        theme: "erlang-dark",
	        extraKeys: {
	            "F11": function (cm) {
	                cm.setOption("fullScreen", !cm.getOption("fullScreen"));
	            },
	            "Esc": function (cm) {
	                if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
	            }
	        }
	    });
	   
	    
	    editor.on('change',function(){
	    		conten.value=editor.getValue();
	    });
	    window.editor = editor;
	}
	
});