<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>图片上传</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>

		<style type="text/css">
			.list_table tr input
			{
				width:220px;
			}
		</style>
	</head>
	<body>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="dateForm" id="dateForm" action="/cms/cmsImageAction_uploadImage.action" method="post" onsubmit="return checkValue()" enctype="multipart/form-data">
		<s:token></s:token>
		<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
			<!-- 数据列表区域 -->
			<table width="95%" class="list_table" cellpadding="3" align="center">	
				<tr><td style="font-size:20px;text-align:left;">共添加了<span id="num">0</span>行</td></tr>
			</table>
			<table width="95%" class="list_table" cellpadding="3" align="center" id="list_table">	
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<INPUT id="addBtn" class="addBtn" TYPE="button" value="" onclick="addRowsFun()">
						<INPUT class="saveBtn" TYPE="submit" value="">
					</td>
				</tr>
			</table>
		</s:form>
		
		
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
		var num=0;
		function delTr(obj)
		{
			var tab=document.getElementById("list_table");
			var tabLength=$("#list_table tr").length;
			var tr = obj.parentNode.parentNode;
			var rowNum=tr.childNodes[0].innerHTML;
	  		$(obj).parent().parent().remove();
			for(i=rowNum;i<tabLength;i++)
			{
				//修改编号
				tab.rows[Number(i)-1].cells[0].innerHTML=tab.rows[Number(i)-1].cells[0].innerHTML-1;
				
				//修改图片下标
				var picurl=document.getElementById("picurl"+(Number(i)+1));
				picurl.id="picurl"+i;
			}
			num=Number(tabLength)-1;
			$("#num").html(num);
		}
		
		//图片大小检查
		function fileChange(target) { 
     var fileSize = 0;         
     if (!target.files) {     
       var filePath = target.value;     
       var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
       var file = fileSystem.GetFile (filePath);     
       fileSize = file.Size;    
     } else {    
      fileSize = target.files[0].size;     
      }   
      var size = fileSize / 1024;  
      
      if(size>500){  
    	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过500K！</span>";
	      dialog("消息提示","text:"+msg ,"300px","text");
       	  target.value="";
       return false;
      }
    }
		
		
// 添加一行
var _len=0;
function addRowsFun() {
	 _len = $("#list_table tr").length;
	$("#list_table").append(
					"<tr>"
					+"<td>"
					+(Number(_len)+1)
					+"</td>"
					+"<td>图片:<input type='file' class='picurls' id=picurl"
					+(Number(_len)+1)
					+" name='files'"
					+" onchange='fileChange(this);'"
					+"/></td>"
					+"<td><img style='cursor:pointer;' src='/etc/images/icon_delete.png' onclick='delTr(this)'/></td>"
					+"</tr>"
					);
			var addBtn=document.getElementById("addBtn");
			addBtn.focus();
			$("#num").html(Number(_len)+1);
}


function checkValue(){
	var urls = $(".picurls");
	if(urls.length != 0){
		for(var i=0;i< urls.length; i++){
		if(urls[i].value == null || urls[i].value == ""){
			  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择 ";
		      dialog("消息提示","text:"+msg ,"300px","127px","text");
			  return false;
		  }
		}
	}else{
		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择 ";
        dialog("消息提示","text:"+msg ,"300px","127px","text"); 
		return false;
	}
	
}
</script>
</html>