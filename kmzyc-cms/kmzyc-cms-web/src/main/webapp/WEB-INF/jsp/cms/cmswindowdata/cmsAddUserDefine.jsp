<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
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
		<s:form name="dateForm" id="dateForm" action="/cms/cmsWindowDataAction_saveDate.action" method="post" enctype="multipart/form-data" onsubmit="return check();">
		<s:token></s:token>
		<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>

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
						<input class="backBtn"  onclick="javascript:window.history.back(-1)" type="button" value="">
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


				//修改类型下标
				var datatype=document.getElementById("datatype"+(Number(i)+1));
				//datatype.setAttribute("id", "datatype"+i);
				datatype.id="datatype"+i;
				//datatype.setAttribute("name", "cmsWindowDatas["+i+"].user_defined_type");
				datatype.name="cmsWindowDatas["+i+"].user_defined_type";

				//修改名称下标
				var datatext=document.getElementById("datatext"+(Number(i)+1));
				datatext.id="datatext"+i;
				datatext.name="cmsWindowDatas["+i+"].user_defined_name";
				//修改URL下标
				var dataurl=document.getElementById("dataurl"+(Number(i)+1));
				dataurl.id="dataurl"+i;
				dataurl.name="cmsWindowDatas["+i+"].user_defined_url";

				//修改自定义数据（remark）下标
				var dataurl=document.getElementById("dataremark"+(Number(i)+1));
				dataurl.id="dataremark"+i;
				dataurl.name="cmsWindowDatas["+i+"].remark";

				//修改图片下标
				var picurl=document.getElementById("picurl"+(Number(i)+1));
				picurl.id="picurl"+i;
			}
			num=Number(tabLength)-1;
			$("#num").html(num);
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
					+"<td>数据类型："
					+"	<select id=datatype"
					+(Number(_len)+1)
					+" onchange='disPic(this)'"
					+" name='cmsWindowDatas["
					+(Number(_len)+1)
					+"].user_defined_type'>"
					+"		<option value='0'>文本</option>"
					+"		<option value='1'>图片</option>"
					+"	</select>"
					+"</td>"
					+"<td>数据名称:<input type='text' id=datatext"
					+(Number(_len)+1)
					+" name='cmsWindowDatas["
					+(Number(_len)+1)
					+"].user_defined_name'/>"
					+"</td>"
					+"<td>URL:<input type='text' id=dataurl"
					+(Number(_len)+1)
					+" name='cmsWindowDatas["
					+(Number(_len)+1)
					+"].user_defined_url'/></td>"

					+"<td>自定义数据:<input type='text' id=dataremark"
					+(Number(_len)+1)
					+" name='cmsWindowDatas["
					+(Number(_len)+1)
					+"].remark'/>"
					+"</td>"

					+"<td>图片地址:<input type='file' disabled='disabled' id=picurl"
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
function disPic(obj)
{
	var tr = obj.parentNode.parentNode;
	var rowNum=tr.childNodes[0].innerHTML;
	if(obj.value==0)
	{
		document.getElementById("picurl"+rowNum).setAttribute("disabled","disabled");
	}
	else if(obj.value==1)
	{
		document.getElementById("picurl"+rowNum).removeAttribute("disabled");
	}
}
function check()
{
	var tab=document.getElementById("list_table");
	var tabLength=$("#list_table tr").length;
	for(i=1;i<=tabLength;i++)
	{
		var objname = document.getElementById("datatext"+i);
		if(objname.value==null||objname.value=='')
		{
			objname.focus();
			alert("数据名称不能为空.");
			return false;
		}
		var objtype = document.getElementById("datatype"+i);
		if(objtype.value==1)
		{
			var objurl = document.getElementById("picurl"+i);
			if(objurl.value==null||objurl.value=='')
			{
				objurl.focus();
				alert("请选择图片.");
				return false;
			}
		}
	}
	return true;
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
</script>
</html>

