<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%
	String jscssPath=ConfigurationUtil.getString("CSS_JS_PATH");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<title>确认地址</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<link rel="shortcut icon" href="affixesimages/logos/favicon.png" type="image/x-icon"/>
		<link rel="apple-touch-icon-precomposed" href="<%=jscssPath%>images/common/wap/touch-icon.png" />
		<script type="text/javascript" src="<%=jscssPath%>weixin/js/base/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=jscssPath%>weixin/js/base/jsAddress.js"></script>
		<script type="text/javascript" src="<%=jscssPath%>weixin/js/order/addaddress.js"></script>
		<link type="text/css" rel="stylesheet" href="<%=jscssPath%>weixin/css/base.css"/>
		<link type="text/css" rel="stylesheet" href="<%=jscssPath%>weixin/css/w-global.css"/>
		
	</head>
	<body>
		<header class="s-header">
			<nav>
				<h1>填写配送信息</h1>
			</nav>
		</header>
		<section class="s-summary">
			<div class="s-summary-inner">
				<div class="s-consignee-tit">
					请认真填写配送信息，以保证货品送达
				</div>
	<form id="wapAddressForm" action="saveAddress.action" method="post" name="wapAddressForm" class="ui-form consignee-form ui-form-vertical">
        <fieldset>
            <div class="ui-form-item fn-clear">
                <label class="ui-form-label" for="">省份：</label>
                <select id="province" name="province"></select>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label" for="">城市：</label>
                <select id="city" name="city"></select>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label" for="">区县：</label>
                <select id="area" name="area"></select>
            </div>                        
            <div class="ui-form-item">
                <label class="ui-form-label" for="">
                    <span class="ui-form-required">*</span>详细地址：</label>
                <input type="text" placeholder="无需重复填写省市区" id="detailedAddress" name="detailedAddress" class="ui-input" />
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label" for="">
                    <span class="ui-form-required">*</span>收货人：
                </label>
                <input type="text" placeholder="收货人姓名" id="name" name="name"  class="ui-input" />
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label" for="">
                    <span class="ui-form-required">*</span>手机号：
                </label>
                <input type="text" placeholder="手机号" id="mobile" name="mobile"  class="ui-input" />
            </div>                        
        </fieldset>
    </form>
			</div>
			<a href="javascript:(0);" class="s-btn">保存</a>
		</section>		
	</body>
</html>
