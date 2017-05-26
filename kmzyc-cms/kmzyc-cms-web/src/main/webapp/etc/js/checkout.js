function checkOut()
{
	this.content;
	/**
	 * 检验输入框的大小是否超过固定的长度
	 */
	this.checkText=function(id)
	{
		 var s="";
		 var json=JSON.stringify(this.content);
		 json=json.substring(1,json.length-1);
		 var arr=json.split(",")
		 for(var i=0;i<arr.length;i++)
		 {
			 s=arr[i];
			 s=s.split(":")
			 if(check(s[0],s[1])==false)
			 {
				 return false;
			 }
		 }
		 return true;
	}
}
function check(id,length)
{
	id=id.substring(1,id.length-1);
	length=length.substring(1,length.length-1);
	var obj=document.getElementById(id);
	if(obj.value.length>length)
	{
		 var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle;'/>"+obj.name+"超出了规定的长度!";
         messageDialog("消息提示","text:"+msg,"300px","100px","text");
         var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
        obj.focus();
		return false;
	}
	return true;
}