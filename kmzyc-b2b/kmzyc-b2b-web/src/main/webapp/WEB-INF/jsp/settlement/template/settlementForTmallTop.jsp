<%@ page language="java"
	import="com.kmzyc.zkconfig.ConfigurationUtil"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="i-shorthead">
	<div class="l-w fn-clear">
		<h1 class="logo fn-left">

			<a href="<%=ConfigurationUtil.getString("staticPath")%>" hidefocus="true"><img
				 height="115"
				src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/logo.png"
				alt="康美中药城"></a>

		</h1>
		<div class="progress">
			<ul class="progress-2">
				<li class="step-1">
					<i></i>1、我的购物车
				</li>
  				<li class="step-2">
             		<i></i>2.填写核对订单信息
     			</li>
             	<li class="step-3">
                	<i></i>3.成功提交订单
             	</li>
       		</ul>
		</div>
	</div>
</div>
