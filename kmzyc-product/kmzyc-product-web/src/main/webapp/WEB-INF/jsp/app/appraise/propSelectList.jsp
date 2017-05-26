<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>



<s:form action="/app/prodAppraisePropSelectList.action" method="POST"  namespace='/app' id="frm" name='frm'>
<input type="hidden" value="<s:property value="categoryIds" />" name="categoryIds" id="categoryIds" />
<!-- 查询条件区域 -->
<table  width="98%" class="content_table"  height="50" align="center" cellpadding="0" cellspacing="0" style="border-collapse: collapse;font-size:12px">
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    <td valign="middle" align="center" colspan="3">
	    	<INPUT TYPE="button" onclick="saveSelected();" class="btngray" value=" 保存 ">
		</td>
		<td align="right">
			属性名称：
		</td>
		<td align="left">
			<input name="prop.propName" style="height:20px;" type="text" value="<s:property value='prop.propName'/>">
		</td>
		<td>
			<INPUT TYPE="button" onclick="doSearch()" class="btngray" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="margin-left:10px;border-collapse: collapse;font-size:12px">
	<tr>
	    <th align="center" bgcolor="#99ccff" width="5%">
	    	&nbsp;
		</th>
		<th bgcolor="#99ccff" align="center" width="15%">属性名</th>
		<th bgcolor="#99ccff" align="center" width="80%">属性值</th>
	</tr>
	
	<s:iterator value="#request.selectList" >
		<tr>
		    <td bgcolor="#FFFFCC" align="center" width="5%">
			    <input type="checkbox" checked="checked" name="appPropIds"  value='<s:property value="appraisePropId"/>'>
			</td>
			<td bgcolor="#FFFFCC" align="center"><s:property value="propName"/></td>
			<td bgcolor="#FFFFCC" align="center">
				<s:iterator value="valList" >
					<s:property value="point" />：<s:property value="propVal" />&nbsp;
				</s:iterator>
			</td>
		</tr>
	</s:iterator>
	
	<s:iterator id="custiterator" value="page.dataList">
		<tr>
		    <td bgcolor="#FFFFCC" align="center" width="5%">
			    <input type="checkbox" name="appPropIds"  value='<s:property value="appraisePropId"/>'>
			</td>
			<td bgcolor="#FFFFCC" align="center"><s:property value="propName"/></td>
			<td bgcolor="#FFFFCC" align="center">
				<s:iterator value="valList" >
					<s:property value="point" />：<s:property value="propVal" />&nbsp;
				</s:iterator>
			</td>
		</tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
//返回我的桌面界面

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}
function saveSelected(){
	
	var _checks = $("input[type='checkbox'][name='appPropIds']:checked");
	
	if(_checks.length==0){
		alert("请选择要绑定的属性！");
		return;
	}
	if(_checks.length>2){
		alert("最多只能绑定两个属性！");
		return;
	}
	
	var appPropIds = "";
	
	$.each(_checks,function(){
		appPropIds += this.value+",";
	});
	appPropIds = appPropIds.substring(0,appPropIds.length-1);
	$.post(
		"/app/categoryAndPropSave.action",
		$("#frm").serializeArray(),
		function(data){
			if(data=="success"){
				alert("保存成功！");
				parent.closeDialog();
			}else{
				alert("保存失败！");
			}
		}
	);
}

$(function(){
	$("input[type='checkbox'][name='appPropIds']").click(function(){
		if($("input[type='checkbox'][name='appPropIds']:checked").length==3){
			alert("只能选择两个属性！");
			this.checked = "";
			return;
		}
	});
});


</SCRIPT>

</BODY>
</HTML>