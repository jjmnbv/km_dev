<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<%@ page import="com.pltfm.sys.model.SysRole"%>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<%@ page import="com.pltfm.sys.model.SysRoleMenu"%>
<%@ page import="com.pltfm.sys.bean.SysRoleBean"%>
<%@ page import="com.pltfm.sys.bean.SysMenuBean"%>
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
	background:#CEE7FF
}
.menuNameTd_3{
	padding-left:50px;
	background:#CCFFCC
}
.menuNameTd_4{
	padding-left:70px;
}
</style>
</HEAD>
<BODY>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<%
  //获取该角色已有的授权菜单列表
  ArrayList dlst = (ArrayList)request.getAttribute("dataList");
  //System.out.println("****************** dlst.size()="+ (dlst!=null?String.valueOf(dlst.size()):"空！"));
  //封装menuId list
  ArrayList rMenuIdList = new ArrayList();
  if(dlst!=null  &&  dlst.size()>0){
      for(int i=0; i<dlst.size(); i++){
          rMenuIdList.add( ((SysRoleMenu)dlst.get(i)).getMenuId() );
      }
  }
  //System.out.println("****************** rMenuIdList.size()="+ (rMenuIdList!=null?String.valueOf(rMenuIdList.size()):"空！"));

  //获取所有系统角色
  SysRoleBean rolebean = new SysRoleBean();
  SysRole sysrole = new SysRole();
  List roleList = rolebean.getSysRoleList(sysrole);

  //获取菜单
  SysMenuBean menubean = new SysMenuBean();
  List upmenuList = menubean.getAllUpMenuList();
%>

<s:form action="saveRoleMenu.action" method="POST"  namespace='/sys'>

<s:if test='rtnMessage.equals("saveOk")'>
<SCRIPT LANGUAGE="JavaScript">
	alert("保存授权成功！");
</SCRIPT>
</s:if>

<INPUT TYPE="hidden" name="roleId" value="<s:property value='model.roleId'/>">

<div class="model_title"><h2 >【系统角色菜单授权】</h2></div>

<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td align="left">
		    
		</td>
		<td>
		</td>
	</tr>
</table>
<br>

<div style="width:800px;">
    <!-- 所有角色div -->
	<div style="float:left; width:200px; background:#E7E7E7; border:1px solid blue; padding: 0px 0px 10px 0px">
	    <div style=" background:#6699FF; color:#FFFFFF; font-weight:bold;  padding: 5px 0px 2px 5px">所有系统角色：</div>
<%
  if(roleList != null  && roleList.size()>0){
	  for(int i=0; i<roleList.size(); i++){
		    SysRole role = (SysRole)roleList.get(i);
%>
          <li style="margin-top:5px"><a href="javascript:clickRoleName('<%=role.getRoleId()%>','<%=role.getRoleName()%>')"><%=role.getRoleName()%></a></li>
<%
	  }//end for
  }	
%>
	</div>
	<!-- 可选菜单div -->
	<div style="float:left; margin-left:60px; width:400px; border:1px solid blue;">
	    <div style=" background:#6699FF; color:#FFFFFF; font-weight:bold;  padding: 0px 0px 0px 5px">
		    <INPUT TYPE="hidden" style="BACKGROUND: #FFFFCC; FONT-SIZE: 1.2em; FONT-WEIGHT: bold; color:red; width:120px" NAME="roleName" value="<s:property value='model.roleName'/>">
			<font color="red" size="4"><b><s:property value='model.roleName'/></b></font><font size="3">角色的授权菜单：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
			<s:if test="model.roleId!=null"><INPUT class="btn_green" TYPE="submit" value="保存授权"></s:if>
	</div>


<table  width="400" align="center" cellpadding="0" cellspacing="0" border="0" class="menuNameTd_1">
<% String checkedTag = "";
  //遍历所有最上层菜单
  if(upmenuList != null  && upmenuList.size()>0){
	  for(int i=0; i<upmenuList.size(); i++){
	    SysMenu upmenu = (SysMenu)upmenuList.get(i);
		  //判断该菜单是否已被授权给该角色
		if(rMenuIdList.contains(upmenu.getMenuId()) ){
			checkedTag = "checked";
		}else{
			checkedTag = "";
		}
		%>
		<tr>
			<td class="menuNameTd_1">
				<INPUT TYPE="checkbox"  id="m_0_<%=upmenu.getMenuId()%>" NAME="delId" <%=checkedTag%> value="<%=upmenu.getMenuId()%>"><%=upmenu.getMenuName()%>
			</td>
		</tr>
		<%
		List submenuList = menubean.searchListByUpId(upmenu.getMenuId().toString());
  		if(submenuList != null  && submenuList.size()>0){
	  		for(int j=0; j<submenuList.size(); j++){
	            SysMenu submenu = (SysMenu)submenuList.get(j);
				if(rMenuIdList.contains(submenu.getMenuId()) ){
					checkedTag = "checked";
				}else{
					checkedTag = "";
				}
				%>
				<tr>
					<td align="left">
						<table class="menuNameTd_2" width="100%" align="left" cellpadding="0" cellspacing="0" border="1" style="margin-left: 40px">
							<tr>
							<td align="left">
						        <INPUT TYPE="checkbox" id="m_<%=upmenu.getMenuId()%>_<%=submenu.getMenuId()%>" NAME="delId" <%=checkedTag%> value="<%=submenu.getMenuId()%>"><%=submenu.getMenuName()%>
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<%
				List subsubmenuList = menubean.searchListByUpId(submenu.getMenuId().toString());
				if(subsubmenuList != null  && subsubmenuList.size()>0){
	 				for(int k=0; k<subsubmenuList.size(); k++){
			            SysMenu subsubmenu = (SysMenu)subsubmenuList.get(k);
						if(rMenuIdList.contains(subsubmenu.getMenuId()) ){
							checkedTag = "checked";
						}else{
							checkedTag = "";
						}
						%>
						<tr>
							<td>
							    <table class="menuNameTd_3" width="100%" cellpadding="0" cellspacing="0" border="0" style="margin-left: 40px">
								    <tr>
									<td>
								    <INPUT TYPE="checkbox" id="m_<%=submenu.getMenuId()%>_<%=subsubmenu.getMenuId()%>" NAME="delId" <%=checkedTag%> value="<%=subsubmenu.getMenuId()%>"><%=subsubmenu.getMenuName()%>
									</td>
									</tr>
								</table>
							</td>
						</tr>
						<%
						List subsubsubmenuList = menubean.searchListByUpId(subsubmenu.getMenuId().toString());
						if(subsubsubmenuList != null  && subsubsubmenuList.size()>0){
			  				for(int l=0; l<subsubsubmenuList.size(); l++){
					            SysMenu subsubsubmenu = (SysMenu)subsubsubmenuList.get(l);
								if(rMenuIdList.contains(subsubsubmenu.getMenuId()) ){
									checkedTag = "checked";
								}else{
									checkedTag = "";
								}
								%>
								<tr>
									<td class="menuNameTd_4">
										<INPUT TYPE="checkbox" id="m_<%=subsubmenu.getMenuId()%>_<%=subsubsubmenu.getMenuId()%>" NAME="delId" <%=checkedTag%> value="<%=subsubsubmenu.getMenuId()%>"><%=subsubsubmenu.getMenuName()%>
									</td>
								</tr>
								<%
							}
						}
					}
				}
			}
		}	
	}
}
%>
</table>
	</div>


</div>

</s:form>
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

