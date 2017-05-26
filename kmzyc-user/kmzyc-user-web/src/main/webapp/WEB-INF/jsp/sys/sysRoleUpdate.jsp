<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改角色</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script  src="/etc/js/sys/sysRoleUpdate.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'角色管理'" scope="request" />
<s:set name="son_name" value="'编辑角色'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="doUpdateSysRole.action" method="POST"  namespace='/sys' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="roleId" value="<s:property value='model.roleId'/>">

<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <!-- error message -->
    
	<tr> 
		<th colspan="2" align="left" class="modeltitle">角色信息</th>
	</tr>

	<tr> 
		<td width="20%" align="right"><font color="red">*</font>角色名称：</th>
		<td> 
			<input name="roleName" type="text" require="true" dataType="LimitB" max="40" min="1" msg="角色名称必输，且不超过10个汉字" value="<s:property value='model.roleName'/>"/>
		</td>
	</tr>

	<tr> 
		<td align="right">角色职责：</th>
		<td> 
			<textarea name="roleExplain" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.roleExplain'/></textarea> 
		</td>
	</tr>

	<tr> 
		<td align="right">备注：</th>
		<td> 
			<textarea name="roleRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="100" msg="备注不要超过50个汉字"><s:property value='model.roleRemark'/></textarea> 
		</td>
	</tr>
	</table>


<!-- 底部 按钮条 -->
<table width="60%" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="center">
			<INPUT class="saveBtn" TYPE="submit" value=" ">
            &nbsp;&nbsp;
            <input class="backBtn" onclick="gotoList()" type="button"   
							value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>


</s:form>

</BODY>
</HTML>