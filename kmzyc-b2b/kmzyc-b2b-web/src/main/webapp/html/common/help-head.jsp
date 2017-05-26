<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>

<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/css/default/kmb2b/help.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/css/default/kmb2b/help-center.css" />
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/script/helpmodule/help-center.js" ></script>

<!--顶部 end-->  
<div class="i-topbar">
	<div class="con fn-clear">
		<p class="fn-left loginbar">
			欢迎来到康美中药城&emsp;&emsp;<a href="/pages/login/registration.html">请登录</a>&emsp;&emsp;<a href="/pages/login/registration.html"
																			  >免费注册</a>
		</p>
		<ul class="fn-right topmenu">
			<li class="topmenu-item topmenu-item-app"><a href="">官方首页</a></li>
			<li class="topmenu-item topmenu-item-app"><a href="">会员中心</a></li>
			<li class="topmenu-item topmenu-item-app"><a href="">采购单</a><i class=""></i></li>
			<li class="topmenu-item topmenu-item-app"><a href="">我的收藏</a></li>
			<li class="topmenu-item topmenu-item-app"><a href="">我是卖家</a></li>
			<li class="topmenu-item topmenu-item-app"><a href="">客服中心</a></li>
			<li class="topmenu-item topmenu-item-app"><a href="">网站导航</a></li>
		</ul>
	</div>
</div>

<!--logo&nav-->
<div class="header-all">
	<div class="header-nav con">
		<div class="header-logo">
			<div class="header-logo-box">
				<img src="<%=ConfigurationUtil.getString("cmsPath")%>/2017/03/17/1705057.png"/>
			</div>
		</div>
		<div class="wrj-nav">
			<ul>
				<li><a href="" class="nav-active">首页</a></li>
				<li><a href="">常见问题</a></li>
				<li><a href="">联系客服</a></li>
				<li class="margin-r-none"><a href="">新手指南</a></li>
			</ul>
		</div>
	</div>
</div>
<!--logo&nav  end-->

<!--搜索框-->
<div class="search-sec">
	<div class="search-box con">
		<div class="search">
			<input type="search" placeholder="请简单描述您的问题或关键字，如“忘记密码”"/>
			<span class="input-submit">搜&nbsp;&nbsp;索</span>
			<ul class="input-question">
				<li>热门问题：</li>
				<li><a href="">密码找回</a></li>
				<li><a href="">修改信息</a></li>
			</ul>
		</div>
	</div>
</div>