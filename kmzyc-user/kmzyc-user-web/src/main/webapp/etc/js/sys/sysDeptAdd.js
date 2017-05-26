function saveDept(){
	if(Validator.Validate(window.document.popForm,3)){
		//异步提交调用
	    $.ajax({ 
	    	  type:"post",
	    	  data:$("#saveSysDept").serialize(),
	          url: '/sys/saveSysDept.action',   //提交的action 
	          success: function(data) {             //获取成功  
	        	  parent.closePopDiv2();
	          }
	    }); 
	}
}

function closeDiv(){
    parent.closePopDiv();
}

//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
    }
}