<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
	    	    	    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
	    	<jsp:param name="titlePrefix" value="找回密码"></jsp:param>
	    </jsp:include>
    </head>
    <body class="l-w1000">
		<!--头部登陆条-->
		<%@ include file="/html/common/portal-common-top.jsp" %>
		<!--头部登陆条结束-->
		<!--logo开始-->
		<div class="i-shorthead">
				<div class="l-w fn-clear">
					<h1 class="logo fn-left">

						<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html"><img height="115" alt="康美中药城" src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/logo.png" /></a>

					</h1>
				</div>
			</div>
		<tiles:insertDefinition name="sendPwdContent"/>
		<%@ include file="/html/common/portal-common-foot.jsp" %>
	</body>
</html>
