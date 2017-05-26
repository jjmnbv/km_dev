<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改操作员</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/sys/sysUserUpdate.js"></script>
</head>
<body onkeydown="changeKey();">
<!-- 导航栏 -->
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'操作员管理'" scope="request" />
<s:set name="son_name" value="'编辑操作员'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="doUpdateSysUser.action" method="POST"  namespace='/sys' onsubmit="return Validator.Validate(this,3)">
<!-- hidden properties -->
<INPUT TYPE="hidden" name="userId" value="<s:property value='model.userId'/>">
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	
	<tr> 
		<th colspan="2" align="left" class="modeltitle">基本信息</th>
	</tr>

	<tr> 
		<td width="30%" align="right"><font color="red">*</font>用户名：</th>
		<td> 
			<input name="userName" type="text" require="true" dataType="LimitB" max="25" min="1" msg="用户名必输，且不超过25个字符" value="<s:property value='model.userName'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right">密码：</th>
		<td> 
			<input name="newPwd1" type="text" require="false" dataType="LimitB" max="25" min="1" msg="密码必输，且不超过25个字符" value="<s:property value='model.userPwd'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right">真实姓名：</th>
		<td> 
			<input name="userReal" type="text" require="false" dataType="LimitB" max="25" min="0" msg="真实姓名不超过25个汉字" value="<s:property value='model.userReal'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">昵称：</th>
		<td width="80%"> 
			<input name="userAlias" type="text" require="false" dataType="LimitB" max="20" min="0" msg="昵称不超过10个汉字" value="<s:property value='model.userAlias'/>"/>&nbsp;&nbsp;(填写管理员发布新闻时的笔名。)
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right">性别：</th>
		<td> 
			<input name="sysuser.userSex" type="radio" value="1"  <s:if test="sysuser.userSex==1">checked</s:if>/>
			<img src="/etc/images/boy.gif">男 &nbsp;&nbsp;
			<input name="sysuser.userSex" type="radio" value="0"  <s:if test="sysuser.userSex==0">checked</s:if>/>
			<img src="/etc/images/girl.gif">女 
			
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right">身份证号码：</th>
		<td> 
			<input name="userCardno" type="text" require="false" dataType="LimitB" max="20" min="0" msg="身份证号码不超过20个字符" value="<s:property value='model.userCardno'/>"/>
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right">联系电话：</th>
		<td> 
			<input name="userPhone" type="text" require="true" dataType="LimitB" max="20" min="0" msg="联系电话不超过20个字符" value="<s:property value='model.userPhone'/>"/>
		</td>
	</tr>

	<tr> 
		<td width="30%" align="right">生日：</th>
		<td> 
			<input name="userBirthday" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value='<s:date   name="model.userBirthday"  format="yyyy-MM-dd"/>'/>
		</td>
	</tr>

	<tr> 
		<td width="30%" align="right">备注：</th>
		<td> 
			<textarea name="userRemark" cols="50" rows="4" wrap="PHYSICAL" require="false" dataType="LimitB" max="200" msg="备注不要超过100个汉字"><s:property value='model.userRemark'/></textarea> 
		</td>
	</tr>

	<tr> 
		<th colspan="2" align="left" class="modeltitle">权限信息</th>
	</tr>
	<tr> 
		<td width="30%" align="right"><font color="red">*</font>所属部门：</th>
		<td> 
		    <input name="deptId" type="hidden" value="<s:property value='model.deptId'/>">
			<input name="deptName" type="text"  class="inpt_gray" readonly="true" dataType="Require" msg="所属部门必选" value="<s:property value='sysdept.deptName'/>"/>
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectDept()">
		</td>
	</tr>
	<tr> 
		<td width="30%" align="right"><font color="red">*</font>岗位角色：</th>
		<td> 
			<INPUT TYPE="hidden" name="roleListStr" value="<s:property value='model.roleIdsStr'/>">
			<INPUT TYPE="text" class="inpt_gray" readonly="true" name="roleName" value="<s:property value='model.roleName'/>"  dataType="Require" msg="岗位角色必选">
			<INPUT TYPE="button" class="button-2s" value="选择" onclick="popSelectRole()">
		</td>
	</tr>

<!-- 底部 按钮条 -->
<!-- 底部 按钮条 -->
<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
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