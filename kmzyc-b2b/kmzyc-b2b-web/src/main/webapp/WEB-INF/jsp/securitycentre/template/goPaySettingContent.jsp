<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div class="l-right user-m">
<div class="o-mt">
<h2>支付功能设置</h2>
</div>

<!--进度条-身份验证--> <!--进度条-身份验证end--> <!--文本框-->
<div class="pay-con">
<form class="ch-fm" action="<%=basePath%>member/postPaySetting.action"
	method="post" id="form">
	<input value='<s:property value="#request.changePayPasswordInfo.payRange" />' type='hidden' id='payRangeType'></input>
<div class="pay-qi">
<ul>
	<li class="fn-red">请选择以下启用支付密码的条件，提交订单时，将根据您的设置启用支付密码，以保障账户资金安全</li>
	<li><input type="checkbox" value="1" name="payRange" id="payRange1"
		checked="checked" disabled="true">使用余额时</li>
	<li><input type="checkbox" value="2" name="payRange" id="payRange2">使用优惠券时</li>
	<li style="display:none"><input type="checkbox" value="5" name="payRange"  id="payRange5">预备金</li>
	<!-- <li><input type="checkbox" value="3" name="payRange" id="payRange3">使用礼品卡时（不包含实体卡）</li>-->
	<!--	<li><input type="checkbox" value="4" name="payRange" id="payRange4">使用积分时</li> -->
</ul>
</div>
<div class="button"><a href="javascript:void(0);"
	 class="btn-submit" id ="submit"><span>提交</span></a></div>

</form>
</div>
<!--文本框--> <!--fn-right end--></div>
</div>