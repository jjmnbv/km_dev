<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
<head></head>
<body>
<form id="alipaysubmit" name="alipaysubmit" action="https://mapi.alipay.com/gateway.do?_input_charset=utf-8" method="get">
<input type="hidden" name="sign" value='<s:property value="#request.sPara.sign"/>'/>
<input type="hidden" name="_input_charset" value='<s:property value="#request.sPara._input_charset"/>'/>
<input type="hidden" name="target_service" value='<s:property value="#request.sPara.target_service"/>'/>
<input type="hidden" name="sign_type" value='<s:property value="#request.sPara.sign_type"/>'/>
<input type="hidden" name="service" value='<s:property value="#request.sPara.service"/>'/>
<input type="hidden" name="partner" value='<s:property value="#request.sPara.partner"/>'/>
<input type="hidden" name="return_url" value='<s:property value="#request.sPara.return_url"/>'/>
<input type="submit" value="" style="display:none;"></form>
<script>window.onload = function(){document.forms['alipaysubmit'].submit();};</script>  
</body>
</html>
