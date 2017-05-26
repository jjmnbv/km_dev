<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<title>康美中药城</title>
                 
  
                <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">


<link rel="shortcut icon" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/affixesimages/logos/favicon.png" type="image/x-icon"/>
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/reset.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/layout.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/function.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/lib/module.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/lib/tpl.css" />
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/dev/Search-page.css"/>
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/pages/registration.css" />	
<script type="text/javaScript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/jquery-core.js"></script>
<script type="text/javaScript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/suggest.js"></script>

<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/test/jquery.validate.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/md5.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/login.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/adv.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/index.js" charset="gb2312"></script>

<!-- 顶部导航条 -->
<div class="i-topbar">
<div class="l-w fn-clear">
				<p class="fn-left loginbar" id="loginbar">您好，欢迎来到康美中药城商城！[<a id="displayLogin2" href="javascript:void(0);">登录</a>]&nbsp;[<a href='<%=ConfigurationUtil.getString("searchPath")%>/html/regist.html'>注册</a>]


				</p>
				<ul class="fn-right topmenu">
					<li class="topmenu-item topmenu-item-phone"><i class="ico-phone"></i>客服热线：<strong>400-6600-518</strong></li>
					<li class="topmenu-item"><b></b><a href="">供应商入口</a></li>
<li class="topmenu-item"><b></b><a href="<%=ConfigurationUtil.getString("searchPath")%>/showOrderTrail.action">订单跟踪</a></li>
					<li class="topmenu-item topmenu-item-collect"><b></b><i class="ico-collect"></i><a href="javascript:void(0);" id="addCookie" title="康美中药城">收藏本站</a></li>
					<li class="topmenu-item"><b></b><a href="http://10.1.0.213/index.html">帮助中心</a></li>
				</ul>
			</div>
</div>
<!-- 登录 -->
<!-- 新版登录注册 -->
	<div id="viewLogin" style="display: none;">
	<div class="modal-dialog"
		style="width: 420px; height: 420px; left: 350px; top: 100px;" id="loginDialog">
		<div class="modal-dialog-hd">
			<span class="dialog-hd-tit">您尚未登录</span><a hideFocus="true"
				href="javascript:void(0);" id="hideLogin" class="close"></a>
		</div>
		<input type="hidden" id="hideMark" value="0"/>
		<div class="modal-dialog-bd login-interface" style="height: 381px" id="loginIn">
			<div class="interface-con"  id="divLogin" >
				<div class="con-title">
					<ul class="fn-clear">
						<li class="current">登录</li>
						<li ><a  href="javascript:void(0);"  id="registLi" >注册</a></li>
					</ul>
				</div>
				<div class="con" >
					<form action="login" method="post" id="login">
						<input type="hidden" name="vecodeHide" id="vecodeHide" value="1" />
						<div class="wb">邮箱/用户名</div>
						<div class="wb-k">
							<input name="loginName" id="loginName" type="text" class="kuang" />
						</div>
						<div class="wb">密码</div>
						<div class="wb-k">
							<div>
								<input name="password" id="password" type="password"
									class="kuang" />
							</div>
							<div class="cw" style="display: none;" id="failDiv"></div>
						</div>
						<div id="vaCodeDiv" style="display: none;">
							<div class="wb-k verify">
								<input name="veCode" id="veCode" class="fn-text" type="text" />
								<a href="javascript:void(0);" id="changeLogins"> 
									<img id="imageCodeLogin" width="80" height="30" src="http://10.1.0.214:8088/imageAuthCode.action" alt="" />
								</a> 
								<a href="javascript:void(0);" id="changeLogin">看不清？换一张</a>
							</div>
						</div>
						<div class="w">
							<input type="hidden" id="checkboxVa"  name="checkboxVa" value="0"></input>
							<input class="checkbox" type="checkbox" name="chkRememberMe"  id="loginCheckbox"/> <span
								class="mar">自动登录</span> <span><a href="http://10.1.0.214:8088/resetPwd.action" target="_blank">忘记密码?</a></span>
						</div>
						<div class="anniu">
							<a href="javascript:void(0);" id="formSubmit"> <span class="login-btn"></span>
							</a>
						</div>
					</form>
				</div>
			</div>
			<div class="interface-con"  id="divRegist"  style="display: none;">
				<div class="con-title">
					<ul class="fn-clear">
						<li><a href="javascript:void(0);"  id="login_dialog" >登录</a></li>
						<li class="current">注册</li>
					</ul>
				</div>
				<div class="con">
					<form name="regist" id="regist" action="regist.action" method="post">
						<div class="wb">邮箱</div>
						<div class="wb-k">
							<div>
							<input name="registEmail" id="registEmail" type="text"class="kuang"  />
							</div>
							<div class="cw" style="display: none;" id="veEmailFailDiv"></div>
						</div>
						<div class="wb">用户名</div>
						<div class="wb-k">
							<div>
							<input name="loginAccount" id="loginAccount" type="text"class="kuang"  />
							</div>
							<div class="cw" style="display: none;" id="veNameFailDiv"></div>
						</div>
					<div class="wb">请设置密码</div>
					<div class="wb-k">
							<div>
							<input name="registPassword" id="registPassword" type="password"	class="kuang" />
							</div>
							<div class="cw" style="display: none;" id="vePasswordFailDiv"></div>
							<div class="cw letter fn-clear" style="width: 239px;position: absolute;left:0;top: 32px;z-index:4;border:0;background:#fff;color:#666;">
								<span id="slevel1" class="current">弱</span>
								<span id="slevel2">中</span>
								<span id="slevel3">强</span>
							</div>
					</div>
					<div class="wb">请确认密码</div>
					<div class="wb-k">
						<div>
							<input name="vePassword" id="vePassword" type="password" class="kuang" />
						</div>
						<div class="cw" style="display: none;" id="veFailDiv"></div>
					</div>
					<div class="wb-k verify">
						<div>
							<input name="veCode" id="veCodeRegist" class="fn-text" type="text" /> 
							<a href="javascript:void(0);">
							<img id="imageCodeRegist" width="80"	height="30" src="http://10.1.0.214:8088/imageAuthCode.action" alt="" /> </a> 
							<a href="javascript:void(0);" id="changeRegist">看不清？换一张</a>
						</div>
						<div class="cw" style="display: none;" id="veCodeFailDiv"></div>
					</div>
					<div class="w">
						<input id="ckdivreg" class="checkbox" type="checkbox"  checked="checked"  name="chkRememberMe"/>
						<span class="mar">我已阅读并接受</span> <span><a href="javascript:void(0)">《用户协议》</a></span>
					</div>
					<div class="anniu">
						<a href="javascript:void(0);"  id="regist_link"><span class="signin-btn"></span></a>
					</div>
					</form>
				</div>
			</div>

		</div>
	</div>
	<div class="modal-mask"></div>
</div>
<!-- login END -->
