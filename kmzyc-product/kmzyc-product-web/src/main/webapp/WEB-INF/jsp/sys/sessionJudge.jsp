
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="#session['sysUser']==null">
<SCRIPT LANGUAGE="JavaScript">
	window.parent.location.href="/sys/gotoSysUserLogin.action?rtnMessage=sessionLost";
</SCRIPT>
</s:if>

