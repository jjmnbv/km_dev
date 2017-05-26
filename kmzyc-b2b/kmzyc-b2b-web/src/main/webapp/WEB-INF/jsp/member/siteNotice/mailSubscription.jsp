<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>

            <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="邮件订阅"></jsp:param>
    </jsp:include>
</head>
<body>
		<!--会员信息头部-->
        <%@ include file="/html/common/portal-common-head.jsp" %> 
        
        <div class="i-page fn-clear">
		    <!-- 会员信息左部 -->
			<tiles:insertDefinition name="memberCenterLeft"/>        
	        <!--站内通知内容区-->
			<tiles:insertDefinition name="mailSubscriptionContent"/>
    	</div>     
               
        <!-- 底部导航链接 -->
        <%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>
