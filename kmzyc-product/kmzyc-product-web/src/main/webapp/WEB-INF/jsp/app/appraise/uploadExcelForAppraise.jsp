<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>

</head>
<body>
	<h3>请选择后缀名为<span style="color:red">xls</span>的文件或者是<span style="color:red">03</span>版本的Excel</h3>
	<form style="height: 555px;width: 1100px;" action="/app/testFileUpload.action" id="excelFrm" enctype="multipart/form-data" method="post">
		<input type="file" name="excelFile" id="excelFile" >
		
		<input type="button" value="提交" onclick="beginUpload();">
		
	</form>
	
	<script type="text/javascript">
	function beginUpload(){
		
		if($("#excelFile").val() == ""){
			alert("请选择要导入的文件！");
			return;
		}
		
		var type = $("#excelFile").val().substring($("#excelFile").val().lastIndexOf(".") + 1);
		if(!(type == "xls")){
			alert("请选择正确的文件类型，此版本只支持xls格式的文件或者是03版本的Excel！");
			return;
		}
		
		$(document).ajaxStart(function() {
			$.blockUI({ 
				message: '<img src="/etc/images/wait.gif" style="vertical-align:middle;height:42px;" /><b style="font-size:30px;vertical-align:middle;">正在导入，请稍后...</b>', 
				css: {width:'40%'}
			});
        });
		$(document).ajaxComplete(function() {
			$.unblockUI();
        });
		
		$.ajaxFileUpload({  
	        url:'/app/uploadExcelFileForAppraise.action',            // 需要链接到服务器地址
	        secureuri:false,  
	        fileElementId:["excelFile"],                        // 文件选择框的id属性
	        dataType: 'json',                                     // 服务器返回的格式，可以是json
	        success: function (data, status)            // 相当于java中try语句块的用法
	        {      
	      	  alert(data.message);
	        },  
	        error: function (data, status, e)            // 相当于java中catch语句块的用法
	        {  
	           alert("系统发生错误，请稍后再试或联系管理员！");
	        }
		});
	}
	</script>
	
</body>
</html>