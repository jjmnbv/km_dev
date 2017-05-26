<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="账号选择"></jsp:param>
    </jsp:include>
    
    <!-- 单独引用myhome.css -->
    <link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/css/default/new2/css/myhome.css"/>
	<!-- 单独引入js -->
	<script>
   		seajs.use([KM.staticUrl+KM.jsBaseUrl+'view/wapLoginChooseForWx.js']);
	</script>	
	<%
	  	request.setAttribute("comeFrom", (String)request.getParameter("comeFrom"));
	    request.setAttribute("returnUrl", (String)request.getParameter("returnUrl"));	
	%>
</head>
<body class="l-w1000">
	<!--顶部登陆条-->
	<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="账号选择"></jsp:param>
	</jsp:include>
	
	<section class="w-bulletin">
    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img1.jpg">
    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img2.jpg" style="display:none;">
    </section>
	
	<!-- 中间区域内容begin -->

		<div class="container">
		<div class="wxsq-primary j_login_choose" data-type='wx'>
		
		<div class="weiico"><span class="icon-uniE655"></span></div>
		<div class="wxtitle">
		<h2 class="wxtext">微信账号购买</h2>
		<p class="wxtext2">授权微信账号登录商城</p>
		</div>
		<div class="wxtitler"><i class="icon-uniE61F"></i></div>
		</div>
		<div class="wxsq-primary j_login_choose" data-type='commonLogin' >
		<div class="kmtitle">
		<p class="kmtext2">不，我已经有了康美中药城账号</p>
		</div>
		<div class="kmtitler"><i class="icon-uniE61F"></i></div>
		</div>
		<input type="hidden" id="comeFrom" value="${comeFrom }" />
		<input type="hidden" id="returnUrl" value="${returnUrl}" width="300px;"/>
		</div>
   <!-- 中间区域内容end -->
		
	
    <!--底部-->
	<%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>
