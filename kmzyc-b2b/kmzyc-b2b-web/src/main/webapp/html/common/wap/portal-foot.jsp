<%@ page language="java" pageEncoding="UTF-8" import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<!--底部-->
<footer class="footer">
	<div class="footer-login" id="loginbar">
		[<a id="displayLogin2" href="http://m.kmb2b.com/html/wapLogin.jsp" class="entry">登录</a>]&nbsp;
		[<a href="http://m.kmb2b.com/html/registration.jsp" class="register">注册</a>
		<span class="footer-right"><a href="tel:4006600518"><i class="icon-uniE634"></i>联系客服</a></span>]
	</div>
    <div class="ft-version">
        <a href="http://www.kmb2b.com/index.html?device=mobile">电脑版</a>
  		<a href="http://m.kmb2b.com" class="fn-green">触屏版</a>
    </div>
    <div class="ft-copyright">
		互联网B2B药品交易服务资格证粤C20140003<br/>
		© 广东康美中药城大药房连锁有限公司版权所有
    </div>
</footer>
<!--底部 end-->
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
