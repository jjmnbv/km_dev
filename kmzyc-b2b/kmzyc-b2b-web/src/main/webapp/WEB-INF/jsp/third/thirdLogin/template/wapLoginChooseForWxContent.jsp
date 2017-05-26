<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 中间区域内容begin -->

<div class="container">
<div class="wxsq-primary j_wxLogin">

<div class="weiico"><span class="icon-uniE655"></span></div>
<div class="wxtitle">
<h2 class="wxtext">微信账号购买</h2>
<p class="wxtext2">授权微信账号登录商城</p>
</div>
<div class="wxtitler"><i class="icon-uniE61F"></i></div>
</div>
<div class="wxsq-primary j_commonLogin">
<div class="kmtitle">
<p class="kmtext2">不，我已经有了康美中药城账号</p>
</div>
<div class="kmtitler"><i class="icon-uniE61F"></i></div>
</div>
<input id="comeFrom" value="<s:property value="#request.comeFrom"/>" />
<input id="returnUrl" value="<s:property value="#request.returnUrl"/>" />
</div>

<!-- 中间区域内容end -->
