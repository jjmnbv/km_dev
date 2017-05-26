<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'基础数据'" scope="request"/>
<s:set name="name" value="'品类管理'" scope="request"/>
<s:set name="son_name" value="'品类查看'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/basedata/saveProdBrand.action" method="POST"  namespace='/basedata' onsubmit="return Validator.Validate(this,2)">



<!-- 数据编辑区域 -->
<table width="65%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">基本信息</th>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle" width="20%">编码：</th>
		<td> 
			<s:property value='drugCate.code'/>
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">名称：</th>
		<td> 
			<s:property value='drugCate.name'/>
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">状态：</th>
		<td> 
			<s:property value="#request.isValidMap[drugCate.status]" />
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">审核状态：</th>
		<td> 
			<s:property value="#request.auditStatusMap[drugCate.auditStatus]" />
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">创建人：</th>
		<td> 
			<s:property value='drugCate.createUserName'/>
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">创建时间：</th>
		<td> 
			<s:date name="drugCate.createDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">最后修改人：</th>
		<td> 
			<s:property value='drugCate.modifyUserName'/>
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">修改时间：</th>
		<td> 
			<s:date name="drugCate.modifyDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">审核人：</th>
		<td> 
			<s:property value='drugCate.auditUserName'/>
		</td>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle">审核时间：</th>
		<td> 
			<s:date name="drugCate.auditDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">备注：</th>
		<td> 
			<s:property value='drugCate.remark'/>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="65%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<input type="button" class="backBtn" onclick="gotoList('<s:property value="type" />')" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">

function gotoList(arg){
	if(arg == "viewList"){
		location.href="/basedata/drugCategoryShow.action";
	}else{
		location.href="/basedata/drugCategoryShow.action?type=audit";
	}
	
    
}


//光标移动
function changeKey()
{
	//var tr=event.srcElement.getAttribute("type");
	//if("textarea"!=tr && "button" != tr)
	//{
	//		if(13 == event.keyCode)
	//		{
	//			event.keyCode=9;
	//		}
  //}
}

function showPath(){
	
	alert(document.getElementById("logoPath").value);
	
}


</SCRIPT>


</BODY>
</HTML>


