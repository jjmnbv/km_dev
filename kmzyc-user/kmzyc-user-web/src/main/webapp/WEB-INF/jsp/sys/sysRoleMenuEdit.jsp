<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统角色菜单授权</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<style>
.menuNameTd_1{
	padding-left:10px;
	background:#FFFFFF;
}
.menuNameTd_2{
	padding-left:30px;
	background:#3399cc;
}
.menuNameTd_3{
	padding-left:50px;
	background:#def2fa;
}
.menuNameTd_4{
	padding-left:70px;
}
</style>
</HEAD>
<BODY>
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'系统角色菜单授权'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>


<s:form action="saveRoleMenu.action" method="POST"  namespace='/sys'>

<INPUT TYPE="hidden" name="roleId" value="<s:property value='model.roleId'/>">


<!-- 按钮条 -->
<table width="98%" align="center" class="content_table" height="30" border="0" cellpadding="0" cellspacing="0" style="margin-left:20px;">
	<tr> 
	    <td width="90%" valign="middle">
           	<!-- 可选菜单div -->
	
		    <INPUT TYPE="hidden" style="BACKGROUND: #FFFFCC; FONT-SIZE: 1.2em; FONT-WEIGHT: bold; color:red; width:120px" NAME="roleName" value="<s:property value='model.roleName'/>">
			<font color="red" size="4"><b><s:property value='model.roleName'/></b></font><font size="3">角色的授权菜单：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			<s:if test="model.roleId!=null"><INPUT class="btn-custom btn_green" TYPE="submit" value="保存授权"></s:if>
	
           
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>
<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin-left:20px; margin-top: 10px;background-color:#def2fa;border-collapse: collapse;">
	<tr style="background:#def2fa" >
	    <td align="center" width="20%"  valign="top">
	    	 <!-- 所有角色div -->
	
	    <div style="margin-left: 10px;" align="left">所有系统角色：</div>
	    <div style="margin-left: 40px;"  align="left">
	    <s:iterator value="roleList">
	    	 <li style="margin-top:5px"><a href="javascript:clickRoleName('<s:property value="roleId"/>','<s:property value="rolename"/>')"><s:property value="roleName"/></a></li>
	    </s:iterator>

</div>
	
	    </td>
	    <td align="left" width="80%" valign="top" style="background:#FFFFFF">
			<table  width="400" align="left" cellpadding="0" cellspacing="0" border="0" class="menuNameTd_1">
			
				<s:iterator value="dataMap"  id="column">    
						
					<tr>
						<td class="menuNameTd_1">
							<INPUT TYPE="checkbox"  id="m_0_<s:property value="#column.key.menuId"/>" NAME="delId" <s:property value="#column.value"/> value="<s:property value="#column.key.menuId"/>"><s:property value="#column.key.menuName"/>
						</td>
					</tr>
					<s:iterator value="#column.key.menuMap" id="s">
				
							<tr>
								<td align="left">
									<table class="menuNameTd_2" width="100%" align="left" cellpadding="0" cellspacing="0" border="1" style="margin-left: 40px">
										<tr>
										<td align="left">
									        <INPUT TYPE="checkbox" id="m_<s:property value="#column.key.menuId"/>_<s:property value="#s.key.menuId"/>" NAME="delId" <s:property value="#s.value"/> value="<s:property value="#s.key.menuId"/>"><s:property value="#s.key.menuName"/>
										</td>
										</tr>
									</table>
								</td>
							</tr>
							<s:iterator value="#s.key.menuMap" id="k">
						
									<tr>
										<td>
										    <table class="menuNameTd_3" width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-left: 40px">
											    <tr>
												<td>
											    <INPUT TYPE="checkbox" id="m_<s:property value="#s.key.menuId"/>_<s:property value="#k.key.menuId"/>" NAME="delId" <s:property value="#k.value"/> value="<s:property value="#k.key.menuId"/>"><s:property value="#k.key.menuName"/>
												</td>
												</tr>
											</table>
										</td>
									</tr>
									<s:iterator value="#k.key.menuMap" id="m">
								
											<tr>
												<td class="menuNameTd_4">
													<INPUT TYPE="checkbox" id="m_<s:property value="#k.key.menuId"/>_<s:property value="#m.key.menuId"/>" NAME="delId" <s:property value="#m.value"/> value="<s:property value="#m.key.menuId"/>"><s:property value="#m.key.menuName"/>
												</td>
											</tr>
									
							</s:iterator>
						</s:iterator>
					</s:iterator>
				</s:iterator>
			</table>
		</td>
	</tr>
</table>
<br>

</s:form>
<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
<SCRIPT LANGUAGE="JavaScript">
<!--
function clickRoleName(roleId, roleName){
	document.forms[0].roleId.value = roleId;
	document.forms[0].roleName.value = roleName;
	document.forms[0].action="/sys/listMenuByRole.action";
    document.forms[0].submit();
}

//-->
</SCRIPT>
</BODY>
</HTML>

