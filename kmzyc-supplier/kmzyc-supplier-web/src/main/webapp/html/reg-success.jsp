<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>注册成功 - 康美中药城</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="Keywords" content="康美中药城"/>
        <meta name="Description" content="康美中药城"/>
        <link rel="shortcut icon" href="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/kmzl.ico" type="image/x-icon"/>
        <link type="text/css" rel="stylesheet" href="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>style/default/html/reg-success.css"/>
        <script src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>script/seajs/2.0.2/sea.js"></script>
        <script src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>script/config.js"></script>
        <script>
            var KMZL = {
                DEBUG: 0, //
                VERSION: '12312312323', //版本号
                BASEURI: '',
                PAGEACTION: '',
                USERINFO: {
                    id: '',
                    name: '',
                    avatar: ''
                }
            };
            seajs.use('<%=ConfigurationUtil.getString("CSS_JS_PATH")%>script/view/html/reg-success.js');
        </script>
    </head>
	<body class="l-w1000">
	<!--头部登陆条-->
	<%@ include file="/html/common/portal-top.jsp" %>
	<!--头部登陆条结束-->
	<!--logo开始-->
	<div class="logo-box">
	   <div class="l-w i-registration-logo">
	      <a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>index.html"><img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/registion_logo.png" width="303" height="77" /></a>
	   </div>
	</div>
	<!--logo结束-->
	 
	<!--成功状态显示-->
	<div class="reg-success">
	  <div class="success-text">
	      <p class="zc"><i></i>恭喜，注册成功。您获得了<span>${regInfo.regPoint}</span>积分的奖励</p>
	      <p style="color: #000;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      	注册成功，即将跳转到首页！<span id="remainTime">10</span>秒</p>
	      <div class="ng">
	        <ul>
	        <li><i class="point"><img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/reg-point.png" /></i><span>您注册的邮箱为:</span><strong>${regInfo.mail}</strong></li>
	        <li><i class="point"><img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/common/reg-point.png" /></i><span>邮箱可用来登陆、找回密码、订购产品等</span></li>
	        </ul>
	      </div>  
	      
	      <div class="yanzhen">我们已向您的邮箱发送一封验证邮件，验证邮箱后将获得${regInfo.mailValidPoint}积分</div>
	      <div class="success-btn">
	        <s:if test="#request.regInfo.mailLink != null">
	        <span class="anniu"><a href="javascript:void(0)" id="btnMaiLink" style="text-indent:6px;font-size:12px;" title="${regInfo.mailLink}">查看验证邮件</a></span>
	        </s:if>
	        <span class="text">没有收到？</span>
	        <a href="javascript:void(0);" id="btnResend">再次发送</a>
	      </div>
	  </div>
	</div>
	<!--显示状态结束-->
	<!--底部开始-->
	<%@ include file="/html/common/portal-foot.jsp" %>
</body>
</html>
