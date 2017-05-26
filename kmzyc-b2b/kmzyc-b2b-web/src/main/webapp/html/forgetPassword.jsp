<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
		    <jsp:param name="titlePrefix" value="忘记密码"></jsp:param>
	    </jsp:include>
		<script>
     		seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/register.css',KM.staticUrl+KM.jsBaseUrl+'view/forgetPassword.js']);
        </script> 
	</head>
	<body>
	    <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="忘记密码"></jsp:param>
	    </jsp:include>
		<div class="container">
		    <div class="form-horizontal">
		        <div class="form-group">
		           <div class="input-group">
		              <input type="text" aname="mobileTel"  id="mobileTel" placeholder="请输入已验证手机号" class="form-control input-lg" />
		              <span class="input-group-btn" id="basic-addon2"><a id="linkMoblie"  href="javascript:void(0);" class="btn btn-success" >获取验证码</a></span>
		           </div>
		        </div>
		        <div class="form-group">
		            <input type="text" name="msgCode" id="msgCode" placeholder="请输入手机获取的验证码" class="form-control input-lg tooltips" />
		        </div>
		        <div class="form-group input-group">
		            <input type="text" name="veCode" id="txtveCode" placeholder="验证码"  class="form-control input-lg" />
		            <span class="captcha-img"><img id="imageCode" src="/codeImage.action?imgKey=regImg" /></span>
		        </div>
		        <div class="form-group">
		            <a class="btn btn-success btn-block" href="javascript:void(0);" id="btnSubLogin">下一步</a>
		        </div>
		    </div>
		</div>
		<%@ include file="/html/common/wap/portal-foot.jsp" %>  
	</body>
</html>
