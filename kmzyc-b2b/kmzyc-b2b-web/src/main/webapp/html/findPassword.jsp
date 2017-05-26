<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
		    <jsp:param name="titlePrefix" value="找回密码"></jsp:param>
	    </jsp:include>
		<script>
		if('${goReset}'==1){
			seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/register.css',KM.staticUrl+KM.jsBaseUrl+'view/findPassword.js']);
		}else{
			window.location.href='/html/forgetPassword.jsp';
		}
        </script> 
	</head>
	<body>
	    <jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="找回密码"></jsp:param>
	    </jsp:include>
	    <%session.removeAttribute("B2B_FINDPWD"); %>
	    <div class="container">
		    <div class="form-horizontal">
		        <div class="form-group">
		            <input type="password" placeholder="8-20位的新密码：不能用纯数字或英文字母" name="newPassword" id="newPassword" class="form-control input-lg tooltips" />
		        </div>
		        <div class="form-group">
		            <input type="password" placeholder="确认密码" name="confirmPassword" id="confirmPassword" class="form-control input-lg tooltips" />
		        </div>
		        <div class="form-group">
		            <a class="btn btn-success btn-block" id="btnSubReset" href="javascript:void(0);">提交</a>
		        </div>
		    </div>
		</div>
		<%@ include file="/html/common/wap/portal-foot.jsp" %> 
	</body>
</html>
