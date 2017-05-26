<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增报表</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="saveReportMain.action" method="POST"  namespace='/report' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">

<!-- 标题条 -->
<div class="pagetitle">新增报表:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
	    <td width="10%" align="center">
            <a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
        </td>
	</tr>
</table>


<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
		<th colspan="4" align="left" class="modeltitle">报表主信息</th>
	</tr>
    <tr> 
        <th width="20%" align="right"><font color="red">*</font>报表名称：</th>
        <td width="30%"> 
            <input name="reportName" type="text" style="width:98%" require="true" dataType="LimitB" max="100" min="1" msg="报表名称必输，且不超过50个汉字">
        </td>
        <th width="20%" align="right">报表编号：</th>
        <td width="30%"> 
            <input name="reportNo" type="text" style="width:98%" require="false" dataType="LimitB" max="40" min="0" msg="报表编号必输，且不超过20个汉字">
        </td>
    </tr>
    <tr> 
        <th align="right">所属分组：</th>
        <td> 
            <input name="reportGroup" type="text" style="width:98%"  dataType="LimitB" max="20" min="0" msg="所属分组不﨣过10个汉字">
        </td>
        <th align="right">横向显示比例：</th>
        <td> 
            <input name="dispPn" type="text" style="width:60%" value="100" require="false" dataType="Double"  msg="横向显示比例必须是数字">%（请填入数字）
        </td>
    </tr>
</table>

<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
		<th colspan="2" align="left" class="modeltitle">报表sql语句</th>
	</tr>
    <tr> 
        <th width="20%" align="right">select</th>
        <td width="80%"> 
            <textArea type="text" name="selectSql" rows="2" style="width:95%"></textArea>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right">from</th>
        <td width="80%"> 
            <textArea type="text" name="reportSqlFrom" rows="3" style="width:95%" require="false" dataType="Limit" max="200" min="0" msg="from语句不超过200个字符"></textArea>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right">where</th>
        <td width="80%"> 
            <textArea type="text" name="reportSqlWhere" rows="4" style="width:95%" require="false" dataType="Limit" max="500" min="0" msg="where语句不超过500个字符"></textArea>
        </td>
    </tr>
</table>
 
<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td width="90%" valign="middle">
			<INPUT class="btngreen" TYPE="submit" value=" 保存 ">
		</td>
		<td width="10%" align="center">
            <a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a>
        </td>
	</tr>
</table>

<br><br>
</s:form>
<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/report/listReportMain.action";
}


//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}

</SCRIPT>
</BODY>
</HTML>