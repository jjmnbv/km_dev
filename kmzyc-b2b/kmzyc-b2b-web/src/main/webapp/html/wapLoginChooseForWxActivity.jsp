<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
    	<jsp:param name="titlePrefix" value="账号选择"></jsp:param>
    </jsp:include>
    
    <!-- 单独引用myhome.css -->
    <link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/css/default/new2/css/myhome.css"/>
	<!-- 单独引入js -->
	<script>
   		seajs.use([KM.staticUrl+KM.jsBaseUrl+'view/wapLoginChooseForWxActivity.js']);
	</script>	
	<%
	  	request.setAttribute("comeFrom", (String)request.getParameter("comeFrom"));
	    request.setAttribute("returnUrl", (String)request.getParameter("returnUrl"));
	    String sessionLoginAccount=(String)session.getAttribute("b2b_sessionUserName");
	    if(sessionLoginAccount==null || "null".equals(sessionLoginAccount)){
	      request.setAttribute("isLogin",false);
	    }else{
	      request.setAttribute("isLogin",true);
	      request.setAttribute("loginAccount",sessionLoginAccount);
	    }
	    
	%>
</head>
<body class="l-w1000">
	<!--顶部登陆条-->
	<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="账号选择"></jsp:param>
	</jsp:include>
	
	<section class="w-bulletin">
    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img1.jpg">
    	<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>reswap/images/w-bulletin-img2.jpg" style="display:none;">
    </section>
	
	<!-- 中间区域内容begin -->

		<div class="container">
		<div class="wxsq-primary j_login_choose" data-type='wx'>
		
		<div class="weiico"><span class="icon-uniE655"></span></div>
		<div class="wxtitle">
		<h2 class="wxtext">微信账号登录</h2>
		<p class="wxtext2">授权微信账号登录商城</p>
		</div>
		<div class="wxtitler"><i class="icon-uniE61F"></i></div>
		</div>
		<div class="wxsq-primary j_login_choose" data-type='commonLogin' >
		<div class="kmtitle">
		<p class="kmtext2">不，我已经有了康美中药城账号</p>
		</div>
		<div class="kmtitler"><i class="icon-uniE61F"></i></div>
		</div>
		<input type="hidden" id="comeFrom" value="${comeFrom }" />
		<input type="hidden" id="returnUrl" value="${returnUrl}"/>
		</div>
   <!-- 中间区域内容end -->
		
	
    <!--底部-->
	<!--footer 底部-->
	<footer class="footer">
    <div class="footer-login">
    	<s:if test="${isLogin==true}">
    		<strong>${loginAccount }</strong><a href="javascript:void(0);"  id="exitLogin">【退出】</a>&nbsp;
    	</s:if>
    	<s:else>
    		<a href='http://m.kmb2b.com/html/registration.jsp' class='register'>【注册】</a>
    	</s:else>
       
		
        <span class="footer-right"><a href='tel:4006600518'><i class="icon-uniE633"></i>联系客服</a></span>
    </div>
    <div class="ft-version">
       	<a href="http://www.kmb2b.com/index.html?device=mobile">电脑版</a>
  		<a href="http://m.kmb2b.com" class="fn-green">触屏版</a>
   		<a href="http://m.kmb2b.com/wap/thepapp.html">客户端</a>
    </div>
    <div class="ft-copyright">
        互联网B2B药品交易服务资格证粤C20140003<br/>
        © 康美中药材数据信息服务有限公司版权所有
    </div>
</footer>
<!--footer 底部 end-->


<script type="text/javascript">
function _load(fun){
    if (window.attachEvent) {
        window.attachEvent('onload', fun);
    } else {
        window.addEventListener('load', fun, false);
    }
}
function async_load(src) {
    var live800_script = document.createElement('script');
    live800_script.type = 'text/javascript';
    live800_script.async = true;
    live800_script.src = src;
    document.body.appendChild(live800_script);
}
_load(function () {
  	//CNZZ统计代码
    async_load("http://s22.cnzz.com/z_stat.php?id=1000398254&web_id=1000398254");
});
</script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/reswap/js/util/99click/om_code.js" ></script>
<!-- 99click -->
<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath_WAP")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath_WAP")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath_WAP")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath_WAP")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath_WAP")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath_WAP")%>"/> 
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath_WAP")%>"/> 
<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath_WAP")%>"/> 
<input type="hidden" id="searchPath" data_id="<%=ConfigurationUtil.getString("searchPath_WAP")%>"/> 
<input type="hidden" id="user_img_path" data-bb="1" data_id=""/> 
</body>
</html>
