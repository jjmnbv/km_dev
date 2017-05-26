<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
    	<jsp:param name="titlePrefix" value="我的时代"></jsp:param>
    </jsp:include>

</head>
    <script type="text/javascript">
        seajs.use(['<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/base/jquery/1.8.3/jquery.js',
            '<%=ConfigurationUtil.getString("CSS_JS_PATH")%>js/common/common/config.js'],function($,Config){
            $(document).ready(function(){
                var originalImgName = '<s:property value="#request.userImgName"/>';

                if(originalImgName == null || originalImgName == ""){
                    $("#originalUserImg").attr("src", Config.DEFAULT_USER_IMG);
                    $("#middleUserImg").attr("src", Config.DEFAULT_USER_IMG_MIDDLE);
                    $("#smallUserImg").attr("src",Config.DEFAULT_USER_IMG_SMALL);
                }else{
                    var middleImageName = originalImgName.substring(0, originalImgName.lastIndexOf(".")) + "_mid"
                            + originalImgName.substring(originalImgName.lastIndexOf("."));
                    var smallImageName = originalImgName.substring(0, originalImgName.lastIndexOf(".")) + "_sma"
                            + originalImgName.substring(originalImgName.lastIndexOf("."));
                    var randomNum = new Date().getTime();
                    $("#originalUserImg").attr("src", Config.USER_IMG_PATH + originalImgName + "?i=" + randomNum);
                    $("#middleUserImg").attr("src", Config.USER_IMG_PATH + middleImageName + "?i=" + randomNum);
                    $("#smallUserImg").attr("src", Config.USER_IMG_PATH + smallImageName + "?i=" + randomNum);
                }
            });
        });
    </script>

<body>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath")%>"/>
<form id="uploadform" method="post" enctype="multipart/form-data">
    <dl class="upfile">
        <div class="upfile-scan">
            <input id="upFile" name="upFile" type="file">
            <img id="loading_img" src="" style="display: none;" />
            <p>请选择jpg、gif格式，小于2M的图片</p>
        </div>
        <div class="upfile-cont">
            <div class="upfile-pic">
                <img id="originalUserImg" width="200" height="200" alt="" src=""></div>
            <div class="upfile-create">
                <p>您上传的头像会生成两种尺寸</p>
                <div class="create-pic">
                    <img id="middleUserImg" width="100" height="100" alt="" src=""><span>100x100</span>
                </div>
                <div class="create-pic">
                    <img id="smallUserImg" width="48" height="48" alt="" src=""><span>48x48</span>
                </div>
            </div>
        </div>
        <div class="upfile-btns up-fn-left">
            <span id="error_message" class="fn-red"></span>
            <button id="submit_button"  class="btn-b btn-b-disabled" type="button">保存</button>
            <%--<button type="hidden" id=""--%>
        </div>
    </dl>
</form>
<script type="text/javascript">
//    document.getElementById('submit_button').onclick = function(){
//        window.frameElement.trigger('close');
//    };
</script>
</body>
<%--</kh:html>--%>
</html>
