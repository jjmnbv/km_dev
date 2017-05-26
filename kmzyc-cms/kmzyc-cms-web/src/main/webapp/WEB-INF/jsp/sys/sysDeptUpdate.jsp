<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改部门信息</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
</head>
<body onkeydown="changeKey();">

<form name="updateForm" action="doUpdateSysDept.action" method="POST" target="main"   namespace='/sys' onsubmit="return Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="deptId" id="deptId" value="<s:property value='model.deptId'/>">
<INPUT TYPE="hidden" name="upDeptid" id="upDeptid" value="<s:property value='model.upDeptid'/>">
<INPUT TYPE="hidden" name="deptLv" id="deptLv" value="<s:property value='model.deptLv'/>">
<INPUT TYPE="hidden" name="createDate" id="createDate" value="<s:property value='model.createDate'/>">
<INPUT TYPE="hidden" name="createUser" id="createUser" value="<s:property value='model.createUser'/>">
<INPUT TYPE="hidden" name="updateDate" id="updateDate" value="<s:property value='model.updateDate'/>">
<INPUT TYPE="hidden" name="updateUser" id="updateUser" value="<s:property value='model.updateUser'/>">
<INPUT TYPE="hidden" name="isEnable" value="<s:property value='model.isEnable'/>">

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="modeltitle">部门信息:<s:property value='model.deptName'/></th>
	</tr>
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th width="20%" align="right"><font color="red">*</font>部门名称：</th>
		<td width="80%"> 
			<input name="deptName" type="text" require="true" dataType="LimitB" max="40" min="1" msg="部门名称必输，且不超过20个汉字" value="<s:property value='model.deptName'/>"/>  (如：人事部)
		</td>
	</tr>

	<tr> 
		<th align="right"><font color="red">*</font>部门编号：</th>
		<td> 
			<input name="deptCd" type="text" require="true" dataType="LimitB" max="20" min="1" msg="部门编号必输，且不超过20个字符" value="<s:property value='model.deptCd'/>"/>
		</td>
	</tr>
	
	<tr> 
		<th align="right">上级部门：</th>
		<td> 
			<input type="text" name="upDept" id="upDept" value="<s:property value='upDeptName'/>" readonly >
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectDept()">
		</td>
	</tr>
	
	<tr>
		<th align="right">备注：</th>
		<td> 
			<textarea name="deptRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.deptRemark'/></textarea> 
		</td>
	</tr>
	<tr> 
		<td colspan="2" align="center">
			<input type="submit" class="btngreen"  value="  保存  "/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btngreen"  value=" 删除" onclick="deleteThisClass();"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="btngreen"  value=" 新增下级部门" onclick="addSonClass();"/>
		</td>
	</tr>
</table>


</form>

<SCRIPT LANGUAGE="JavaScript">
<!--
function deleteThisClass(){
    var isHaveSon = "<s:property value='isHaveSon'/>";
    if(isHaveSon!=null&&isHaveSon=="1"){
		alert("该部门下面有子部门，请不要删除！！！");
		return;
    }
    if(confirm("您确定要删除该部门吗？")){
    	window.document.updateForm.action = "deleteSysDept.action";
    	window.document.updateForm.target = "main";
		window.document.updateForm.submit();
	}
}
function addSonClass(){
	var deptId = window.document.getElementById("deptId").value;
	//var deptLv = window.document.getElementById("deptLv").value;
	//if(deptLv>=0){
	//	deptLv = Number(deptLv)+1;
	//}
	dialog("新增子部门","iframe:gotoSysDeptAdd.action?upDeptid="+deptId,"500px","310px","iframe");
}

function closePopDiv(){
	closeThis();
}

function closePopDiv2(){
	closeThis();
	alert("保存完毕！");
	window.document.updateForm.action = "gotoSysDeptList.action";
	window.document.updateForm.target = "main";
	window.document.updateForm.submit();
}

//弹出层 选择上级部门
function popSelectDept() {
    dialog("选择上级部门","iframe:/sys/gotoPopSelectUpDept.action?deptId=<s:property value='model.deptId'/>" ,"500px","310px","iframe");
}

function closeOpenDept(deptId,deptName,deptLv){
    closeThis();
    window.document.getElementById("upDeptid").value = deptId;
    window.document.getElementById("upDept").value = deptName;
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

//-->
</SCRIPT>

</BODY>
</HTML>