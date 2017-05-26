<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,java.util.*" pageEncoding="UTF-8"%>
<%
String staticPath_WAP = ConfigurationUtil.getString("staticPath_WAP");
%>
<div class="page-box fn-p10">
	<div class="help-block text-center">
		<i class="menu-icon icon-uniE62D"></i>
			购物车内暂时没有商品，
			<c:if test="${not isLogin}">
				
			</c:if>
	            <a class="shop-co-red" href="<%=staticPath_WAP%>">去首页</a>&nbsp;&nbsp;挑选喜欢的商品!
	</div>
</div>
