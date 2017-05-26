<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head></head>
<body>
<form id="tempForm" name="tempForm" action="http://weixin.kangmei.com.cn/wechat/redirect/index.html" method="post">
<input type="hidden" name="postStr" value='<s:property value="#request.postStr"/>' />
<input type="submit" value="" style="display:none;"></form>
<script>window.onload = function(){document.forms['tempForm'].submit();};</script>  
</body>
</html>
