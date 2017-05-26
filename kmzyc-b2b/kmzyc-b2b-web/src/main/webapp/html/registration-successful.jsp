<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
					<!-- 头部修改为引入文件的方式，并且通过KM.staticUrl+KM.jsBaseUrl 控制是在压缩版本还是未压缩版本-->
			<jsp:include page="/WEB-INF/jsp/common/loginTemplate.jsp">
		    	<jsp:param name="titlePrefix" value=""></jsp:param>
		    </jsp:include>
		<title>注册 - 康美中药城,中国首个线上药材市场,康美药业股份有限公司旗下</title>
        <meta name="apple-itunes-app" content="app-id=432274380" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="renderer" content="webkit" />
		<meta name="description" content="康美中药城"/>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
		<meta content="yes" name="apple-mobile-web-app-capable" />
		<meta content="black" name="apple-mobile-web-app-status-bar-style" />
		<meta content="telephone=no" name="format-detection" />
     	<script>
     		_ozprm="user_id=<%=new Date().getTime()%>";
     		seajs.use([KM.staticUrl+KM.cssBaseUrl+'pages/register.css',KM.staticUrl+KM.jsBaseUrl+'view/registration-successful.js']);
        </script>
    </head>
	<body>
	    <!--头部-->
	  	<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="注册成功"></jsp:param>
	    </jsp:include>
	    <div class="container">
			<div>
			    <div>
					<div class="warn">
				        <span class="icon-uniE045 font-w5 warn-success"></span>
				        <p class="warn-text">注册成功</p>
				  		<p class="warn-text" id="showTime"><span id="secounds">3</span>秒之后跳转页面</p>
					</div>
			      	<div class="form-group">
			       		<a href="javascript:void(0);" id="btnMaiLinks" class="btn btn-success btn-block">返回前页面</a>
			       		<input id="info" value="${info}" type="hidden" />
			      	</div>
			    </div>
			</div>
		</div>
	   	<%@ include file="/html/common/wap/portal-foot.jsp" %>
	</body>
</html>
