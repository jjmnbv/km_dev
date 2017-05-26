<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%@ page import="com.kmzyc.promotion.sys.model.SysMenu"%>
<%@ page import="com.kmzyc.promotion.sys.model.SysUser"%>
<%@ page import="com.kmzyc.promotion.sys.bean.SysMenuBean"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%  
    OgnlValueStack stack = (OgnlValueStack)request.getAttribute("struts.valueStack");
 	List dataList= (List)stack.findValue("dataList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<base target="main" />
<head>
<meta name="renderer" content="webkit|ie-comp|ie-stand"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript">
function showmenu(targetid,img){
    if (document.getElementById){
        target=document.getElementById(targetid);
            if (target.style.display=="block"){
                target.style.display="none";
                img.src="/etc/images/sys/menu_off.png";
            } else {
                target.style.display="block";
                img.src="/etc/images/sys/menu_on.png";
            }
    }
}
</script>
<style type="text/css">
<!--
html {
	scrollbar-face-color: #A4DDFF;
	scrollbar-highlight-color: #AAE8FD;
	scrollbar-shadow-color: #0095E1;
	scrollbar-3dlight-color: #0095E1;
	scrollbar-arrow-color: #02338A;
	scrollbar-track-color: #C8EBF7;
	scrollbar-darkshadow-color: #C8EBF7;
	overflow-x: hidden;
	overflow-y: auto;
	margin:0px;
	padding:0px;
}
-->
</style>
</head>
<body style="background-color:transparent;">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<%if(dataList!=null&&dataList.size()>0){%>
	<%
		for(int i=0;i<dataList.size();i++){
		SysMenu menu=(SysMenu)dataList.get(i);
		
		Integer userId = new Integer("0");
		String headMenuId = menu.getMenuId().toString();
		if(session.getAttribute("sysUser") != null){
		  SysUser sysuser = (SysUser)session.getAttribute("sysUser"); 
		  userId = sysuser.getUserId();
		}
		 SysMenuBean bean = new SysMenuBean();
		 List subList = bean.getSubMenuListByUserId(userId,Integer.valueOf(headMenuId));
		 if(subList!=null&&subList.size()>0){ 
	 %>
			<div class="menubox">
				<div class="menuTitle">
					<div class="menuico" style="cursor: hand" onclick="showmenu('ff<%=menu.getMenuId()%>',image<%=menu.getMenuId()%>);">
						<img src="/etc/images/sys/menu_off.png" id="image<%=menu.getMenuId()%>" name="image<%=menu.getMenuId()%>" onclick="return false;" /> 
					</div>
					<div class="menutext">
						<%if(menu.getMenuLink()!=null&&!menu.getMenuLink().equals("")&&menu.getMenuLink().indexOf(".")>0){ %>
							<a href="<%=menu.getMenuLink()%>" class="menutexts" target="main"><%=menu.getMenuName()%></a>
						<%}else{ %>
							<span style="cursor:pointer;" class="menutexts" onclick="showmenu('ff<%=menu.getMenuId()%>',image<%=menu.getMenuId()%>);" ><%=menu.getMenuName()%></span>
						<%} %>
					</div><!--
					<div class="menupic">
						<img src="/etc/images/sys/menu_ico1.gif" alt="" width="28" height="13" />
					</div>
				--></div>
				<div class="menutexts" id="ff<%=menu.getMenuId()%>" style="display: none;">
				<%for(int j=0;j<subList.size();j++){
					SysMenu subMenu=(SysMenu)subList.get(j);
				%>
				   <ul>
						<li>
							<p>
								<a href="<%=subMenu.getMenuLink()%>" class="menutexts" target="main"><%=subMenu.getMenuName()%></a>
							</p>
						</li>
					</ul>
				<%}%>
				</div>
			</div>
			<%}else{%>
				<div class="menumaintexts">
					 <ul>
						<li>
							<p>
								<a href="<%=menu.getMenuLink()%>" class="menutexts" target="main"><%=menu.getMenuName()%></a>
							</p>
						</li>
					</ul>
				</div>
			<%}%>
			
			
		
<%}}else{ %>

<%} %>

</body>
</html>
