<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<title>500</title>
<jsp:include page="/WEB-INF/jsp/common/template.jsp"></jsp:include>
</head>
<body>
<%@ include file="/html/common/portal-common-head.jsp" %>
<div class="l-w">t
    <div class="system system-500">
        <h2>500</h2>
        <dl>
            <dt><span id="timer">5</span>秒后返回<a id="back_id" href="javascript:void(0);">首页</a></dt>
        </dl>
        
    </div>
</div>
<!--底部-->
<%@ include file="/html/common/portal-common-foot.jsp" %>
<%
    String referer = request.getHeader("Referer");
%>
<script>
    seajs.use(['<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/base/jquery/1.8.3/jquery.js',
        '<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/common/common/config.js'],function($, Config){
        var timer = 1;
        var redirectSeconds = 5;//跳转剩余秒数
        var interval = 1000;//计时器间隔，单位毫秒
        $(document).ready(function(){
            $("#timer").text(redirectSeconds)
            timer = setTimeout(countDown, interval);

            $("#back_id").on("click", function(){
                goBack();
            });
        });

        function countDown(){
            if(redirectSeconds == 0){ //达到0秒时跳转页面
                goBack();
                return;
            }
            redirectSeconds--;
            $("#timer").text(redirectSeconds);
            timer = setTimeout(countDown, interval)
        }

        function goBack(){
            var hasReferer = <%=!(referer == null || "".equals(referer))%>;
            if(hasReferer){
                window.history.back();
            }else{
                window.location.href = Config.CMS_PATH + "index.html";
            }

        }
    })
</script>

</body>
</html>
