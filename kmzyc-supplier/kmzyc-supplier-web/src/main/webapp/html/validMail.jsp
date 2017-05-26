<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	session.removeAttribute("VALID-TYPE-REG");
%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 	<link rel="shortcut icon" href="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/kmzl.ico" type="image/x-icon"/>
		<script>
			if('1'=='${info}'){
				alert('已成功验证邮箱！');
			}else{
				alert('抱歉，验证邮箱赠送积分失败！');
			}
			window.location.href='<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html';
		</script>
	</head>
	<body>
	
	</body>
</html>
