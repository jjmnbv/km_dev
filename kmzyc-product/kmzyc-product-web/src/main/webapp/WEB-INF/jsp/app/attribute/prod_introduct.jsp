<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script   src="/etc/js/qtip/jquery.min.1.8.3.js"></script> 
<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 



<script type="text/javascript">   
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="product.introduce"]', {
		cssPath : '/kindeditor/plugins/code/prettify.css',
		uploadJson : '/kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
		allowFileManager : true
	});
});
function back() {
	history.back();
}
function checkSubmit() {
	editor.sync();
	$("form").submit();
}
function saveInfor()
{
	//var intorduct = $(".ke-content").html();
	 var intorduct=$("#editor_id").html();
	// alert(intorduct);
	//var x = document.getElementsByName()
	//location.href="/app/saveproduct.action?intorduct="+intorduct;
	}
 
 </script>
 

</head>


<body>
<form action="saveproduct.action" method="post"  >
<textarea id="editor_id"  name="product.introduce"   
 style="height:500px;width: 1000px;margin-left: 140px;margin-top: 54px;"> 
	 <s:property    value="product.introduce"     />
 </textarea>
   
  <div > <s:textfield name="product.id"   id="productid" 
  style=" display: none; " /></div></td>
  <div>
 <input name="保存" style="width:58px; height: 30px; margin-left: 500px; margin-top: 20px;"
  value="保存"  type="submit"  onClick="saveInfor()"  >
</div>

</form>
</body>

 
</html>