<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预览sql语句</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="form1" action="" method="POST"  namespace='/report' onsubmit="return  Validator.Validate(this,3)">


<!-- 标题条 -->
<div class="pagetitle"></div>

<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr align="center"> 
	    <td>
            预览sql语句:
        </td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
        <td align="center"> 
            <textArea cols="" rows="8" style="width:80%;text-align:left"><s:property value='viewSql'/></textArea>
        </td>
    </tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr align="center"> 
	    <td>
            <INPUT class="btngray" TYPE="button" value=" 关闭 " onclick="gotoList();">
        </td>
	</tr>
</table>

<br>
<br>
</s:form>

<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    parent.closeThis();
}

</SCRIPT>
</BODY>
</HTML>