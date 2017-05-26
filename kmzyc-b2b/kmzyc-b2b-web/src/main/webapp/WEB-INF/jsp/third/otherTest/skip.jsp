<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head></head>
<body>
<form id="tempForm" name="tempForm" action="http://weixin.kangmei.com.cn/wechat/oauth/index.html" method="post">
<input type="hidden" name="userJsonData" value='<s:property value="#request.userJsonData"/>'/>
<input type="hidden" name="state" value='<s:property value="#request.state"/>' />
<input type="hidden" name="postStr" value='<s:property value="#request.postStr"/>' />
<input type="submit" value="" style="display:none;"></form>
<script>window.onload = function(){document.forms['tempForm'].submit();};</script>  
</body>
</html>
