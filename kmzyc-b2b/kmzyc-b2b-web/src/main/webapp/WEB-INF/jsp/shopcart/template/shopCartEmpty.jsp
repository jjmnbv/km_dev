<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="kh" uri="kh"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="shopcart l-w">
	<div class="shopcart-hd fn-clear">
		<h2 class="shopcart-tit fn-left">我的购物车</h2>
	</div>
	<div class="cart-empty">
		 <div class="empty-msg">
			<dl><dt></dt>
				<dd class="fn-block">购物车内暂时没有商品，
				<c:if test="${shopCar.userIsLogin==false }">
					<a class="j_displayLogin" href="javascript:;">登录</a>后，将显示您之前加入的商品。
					<br>
				</c:if>
               		<a href="<%=ConfigurationUtil.getString("staticPath")%>">去首页</a>挑选喜欢的商品!
               	</dd>
			</dl>
		</div>
	</div>
</div>

