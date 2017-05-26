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


<s:if test='rtnMessage.equals("updateParamOk")'>
    <SCRIPT LANGUAGE="JavaScript">
    <!--
        alert("报表参数列修改成功!");
    //-->
    </SCRIPT>
</s:if>

<s:form name="form1" action="" method="POST"  namespace='/report' onsubmit="return  Validator.Validate(this,3)">

<!-- hidden properties -->
<INPUT TYPE="hidden" name="param.paramId" value="<s:property value='param.paramId'/>">


<!-- 标题条 -->
<div class="pagetitle">修改报表显示列:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value=" 保存 " onclick="gotoUpdateParam();">
		</td>
	    <td width="10%" align="center">
            <INPUT class="btngray" TYPE="button" value=" 关闭 " onclick="gotoList();">
        </td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
    <tr> 
        <th align="right"><font color="red">*</font>条件子句：</th>
        <td colspan="3"> 
            <input name="param.subSentence" type="text" style="width:90%" require="true" dataType="LimitB" max="50" min="1" msg="条件子句必输，且不超过25个汉字" value="<s:property value='param.subSentence'/>">
        </td>
    </tr>
    <tr>
        
        <th align="right"><font color="red">*</font>参数标题：</th>
        <td colspan="3"> 
            <input name="param.paramName" type="text" style="width:30%" dataType="LimitB" max="50" min="1" msg="参数标题必输，且不超过25个汉字" value="<s:property value='param.paramName'/>">
        </td>
    </tr>
    <tr> 
        <th width="20%" align="right">数据类型：</th>
        <td width="30%"> 
            <select name="param.dataType">
                <option value="1" <s:if test="param.dataType eq 1">selected</s:if>>INT</option>
                <option value="2" <s:if test="param.dataType eq 2">selected</s:if>>DOUBLE</option>
                <option value="3" <s:if test="param.dataType eq 3">selected</s:if>>STRING</option>
                <option value="4" <s:if test="param.dataType eq 4">selected</s:if>>TIMESTAMP</option>
                <option value="5" <s:if test="param.dataType eq 5">selected</s:if>>LONG</option>
                <option value="6" <s:if test="param.dataType eq 6">selected</s:if>>LIKE</option>
            </select>
        </td>
        <th width="20%" align="right">控件类型：</th>
        <td width="30%"> 
            <select name="param.controlType"  onchange="selType(this.value);">
                <option value="1" <s:if test="param.controlType eq 1">selected</s:if>>文本</option>
                <option value="2" <s:if test="param.controlType eq 2">selected</s:if>>读取参数表</option>
                <option value="3" <s:if test="param.controlType eq 3">selected</s:if>>读取其它表</option>
            </select>
        </td>
    </tr>

	<tr id="addTdCode" style='display:<s:if test="param.controlType eq 2">block</s:if><s:else>none</s:else>;'>
		<th align="right">参数表对应编码：</th>
        <td colspan="3">
            <input type="text" name="param.paramCode" dataType="limit" max="20" msg="参数表对应编码不超过20个字符" style="width:20%" value="<s:property value='param.paramCode'/>">
        </td> 
	</tr>
	<tr id="addTdSql" style='display:<s:if test="param.controlType eq 3">block</s:if><s:else>none</s:else>;'>
        <th align="right">参数相关执行SQL：</th>
        <td colspan="3">
            <input type="text" name="param.paramSql" dataType="limit" max="500" msg="参数相关执行sql不超过500个字符" style="width:90%" value="<s:property value='param.paramSql'/>">
        </td> 
    </tr>

    <tr> 
        <th align="right">顺序号：</th>
        <td colspan="3"> 
            <input type="text" name="param.paramOrder" dataType="Double" msg="顺序号必须为小数" style="width:10%" value="<s:property value='param.paramOrder'/>">
        </td>
    </tr>
    <tr>
        <th align="right">显示：</th>
        <td> 
            <input type="checkbox" name="param.isShow" value="1" <s:if test="param.isShow eq 1">checked</s:if>>
        </td>
        <th align="right">必须：</th>
        <td> 
            <input type="checkbox" name="param.isMust" value="1" <s:if test="param.isMust eq 1">checked</s:if>>
        </td>
    </tr>
</table>



<!-- 底部 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value=" 保存 " onclick="gotoUpdateParam();">
		</td>
	    <td width="10%" align="center">
            <INPUT class="btngray" TYPE="button" value=" 关闭 " onclick="gotoList();">
        </td>
	</tr>
</table>

<br><br>
</s:form>

<SCRIPT LANGUAGE="JavaScript">
<!--

//关闭
function gotoList(){
    parent.closeThis();
}

//新增报表显示列信息
function gotoUpdateParam(){
    form1.action = "/report/doUpdateParam.action";
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

//选择查询条件控件类型
function selType(typeId){
    if(typeId==2){
        document.getElementById("addTdCode").style.display="block";
        document.getElementById("addTdSql").style.display="none";
    }
    else if(typeId==3){
        document.getElementById("addTdCode").style.display="none";
        document.getElementById("addTdSql").style.display="block";
    }else{
        document.getElementById("addTdCode").style.display="none";
        document.getElementById("addTdSql").style.display="none";
	}
}
</SCRIPT>
</BODY>
</HTML>