<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

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
                img.src="/etc/images/sys/menu_on.png";
            } else {
                target.style.display="block";
                img.src="/etc/images/sys/menu_off.png";
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

	 <s:iterator value="dataMap" id="column">    
	 	
	 		<s:if test="#column.value.size>0">
	 						<div class="menubox">
				<div class="menuTitle">
					<div class="menuico" style="cursor: hand" onclick="showmenu('ff<s:property value="#column.key.menuId" />',image<s:property value="#column.key.menuId" />);">
					<img src="/etc/images/sys/menu_on.png"   id="image<s:property value="#column.key.menuId" />" name="image<s:property value="#column.key.menuId" />" onclick="return false;" />
					</div>
					<div class="menutext">
						
						<s:if test="#column.key.menuLink!=null&&#column.key.menuLink.indexof('.')>0">
							<a href="<s:property value="#column.key.menuLink" />" class="menutexts" target="main"><s:property value="#column.key.menuName" /></a>
						</s:if>
					   <s:else>
					   		<span class="menutexts"  style="cursor:pointer;" onclick="showmenu('ff<s:property value="#column.key.menuId" />',image<s:property value="#column.key.menuId" />);"><s:property value="#column.key.menuName" /></span>
					   </s:else>
							
						
					</div><!--
					<div class="menupic">
						<img src="/etc/images/sys/menu_ico1.gif" alt="" width="28" height="13" />
					</div>
				--></div>
				<div class="menutexts" id="ff<s:property value="#column.key.menuId" />" style="display: none;">
				<s:iterator value="#column.value" status="s">
				   <ul>
						<li>
							<p>
								<a href="<s:property value="menuLink"/>" class="menutexts" target="main"><s:property value="menuName"/></a>
							</p>
						</li>
					</ul>
				
				</s:iterator>
				</div>
			</div>
	 		</s:if>
	 		<s:else>
	 			<div class="menumaintexts">
					 <ul>
						<li>
							<p>
								<a href="<s:property value="#column.key.menuLink" />" class="menutexts" target="main"><s:property value="#column.key.menuName" /></a>
							</p>
						</li>
					</ul>
				</div>
	 		</s:else>
	 	
	 	
	 </s:iterator>


</body>
</html>
