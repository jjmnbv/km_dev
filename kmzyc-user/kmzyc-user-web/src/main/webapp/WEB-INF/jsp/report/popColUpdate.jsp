<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改报表显示列</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
</head>
<body onkeydown="changeKey();">
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:if test='rtnMessage.equals("updateColOk")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("报表显示列修改成功!");
    //-->
    </SCRIPT>
</s:if>

<s:form name="form1" action="" method="POST"  namespace='/report' onsubmit="return  Validator.Validate(this,3)">


<!-- hidden properties -->
<INPUT TYPE="hidden" name="col.colId" value="<s:property value='col.colId'/>">


<!-- 标题条 -->
<div class="pagetitle">修改报表显示列:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value=" 保存 " onclick="gotoUpdateCol();">
		</td>
	    <td width="10%" align="center">
            <INPUT class="btngray" TYPE="button" value=" 关闭 " onclick="gotoList();">
        </td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
        <th width="20%" align="right"><font color="red">*</font>字段名称：</th>
        <td colspan="3"> 
            <input name="col.fieldName" type="text" style="width:90%" dataType="Limit" max="200" min="1" msg="字段名称必输，且不超过200个字符" value="<s:property value='col.fieldName'/>">
        </td>
	</tr>
	<tr>
        <th width="20%" align="right">
            <font color="red">*</font>中文列名：
        </th>
        <td colspan="3"> 
            <input name="col.colNameCn" type="text" style="width:30%" require="true" dataType="LimitB" max="100" min="1" msg="中文列名必输，且不超过25个汉字" value="<s:property value='col.colNameCn'/>">
        </td>
    </tr>
    <tr> 
        <th width="20%" align="right">数据类型：</th>
        <td width="30%"> 
            <select name="col.colType" onchange="selColType(this.value);">
                <option <s:if test="col.colType eq 1">selected</s:if> value="1">INT</option>
                <option <s:if test="col.colType eq 2">selected</s:if> value="2">DOUBLE</option>
                <option <s:if test="col.colType eq 3">selected</s:if> value="3">STRING</option>
                <option <s:if test="col.colType eq 4">selected</s:if> value="4">DATETIME</option>
                <option <s:if test="col.colType eq 5">selected</s:if> value="5">LONG</option>
				<option <s:if test="col.colType eq 9">selected</s:if> value="9">系统param</option>
            </select>
        </td>
        <th width="20%" align="right">顺序号：</th>
        <td width="30%"> 
            <input name="col.colOrder" type="text" style="width:20%" dataType="Double" msg="横向显示比例必须是数字" value="<s:property value='col.colOrder'/>">
        </td>
    </tr>
    <tr id="colParamTr" style='display:<s:if test="col.colType eq 9">block</s:if><s:else>none</s:else>;'> 
        <th align="right">参数编码：</th>
        <td colspan="3"> 
            <input type="text" name="col.paramGp" dataType="limit" max="20" msg="参数编码不超过20个字符" style="width:30%"  value="<s:property value='col.paramGp'/>">
        </td>
    </tr>
    <tr>
        <th align="right">显示：</th>
        <td align="left"> 
            <input type="checkbox" name="col.isShow" value="1" <s:if test="col.isShow eq 1">checked</s:if>>
        </td>
        <th align="right">合计：</th>
        <td align="left"> 
            <input type="checkbox" name="col.isSum" value="1" <s:if test="col.isSum eq 1">checked</s:if>>
        </td>
    </tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value=" 保存 " onclick="gotoUpdateCol();">
		</td>
	    <td width="10%" align="center">
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

//新增报表显示列信息
function gotoUpdateCol(){
    form1.action = "/report/doUpdateCol.action";
    form1.submit();
    parent.closePop();
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


//选择显示列类型
function selColType(typeId){
    if(typeId==9){
        document.getElementById("colParamTr").style.display="block";
    }else{
        document.getElementById("colParamTr").style.display="none";
	}
}

</SCRIPT>
</BODY>
</HTML>