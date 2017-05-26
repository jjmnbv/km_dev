<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
        <jsp:include page="/WEB-INF/jsp/common/template.jsp">
			<jsp:param name="titlePrefix" value="注册成功"></jsp:param>
		</jsp:include>
        <link rel="shortcut icon" href="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/kmzl.ico" type="image/x-icon"/>
        <link type="text/css" rel="stylesheet" href="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>css/default/html/reg-success.css"/>
        
        <script src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/seajs/2.0.2/sea.js"></script>
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
            seajs.use('<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/config.js');
            seajs.use('<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/view/html/reg-success.js');
        </script>
    </head>
	<body>
	<!--头部登陆条-->
	<%@ include file="/html/common/portal-common-top.jsp" %>
	<!--头部登陆条结束-->
	<!--logo开始-->
	<div class="logo-box">
   		<div class="l-w i-registration-logo page-logo">
       		<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>/index.html"><img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>/images/common/logo.png" height="115"></a>
   		</div>
	</div>
	<!--logo结束-->

	<!--成功状态显示-->
	<div class="reg-success">
	  <div class="success-text">
	      <p class="zc"><i></i>恭喜，注册成功!　即将跳转到首页！<span id="remainTime">10</span>秒</p>
	      <div class="ng">
	        <ul>
	         <li><span>您注册的手机号为:</span><strong>${headMobile}*****${bottomMobile}</strong></li>
			 <li><span>您的手机已验证，可用来登录、找回密码、订购产品等</span></li>
	        </ul>
	      </div>
	      <div class="success-btn">
	        <span class="anniu"><a href="javascript:void(0);" id="btnMaiLinks" style="font-size:12px; text-align:center" >前往首页</a></span>
	      </div>
	  </div>
	</div>
	<!--显示状态结束-->
	<!--底部开始-->
	<script>_ozprm="user_id=<%=new Date().getTime()%>";</script>
	<%@ include file="/html/common/portal-common-foot.jsp" %>
</body>
</html>
