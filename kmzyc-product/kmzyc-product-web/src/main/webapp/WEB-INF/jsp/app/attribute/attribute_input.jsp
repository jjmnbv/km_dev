<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>属性维护</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body onkeydown="changeKey();">
<s:form action="/product/attribute_save.action" method="POST"  namespace='/product' onsubmit="return Validator.Validate(this,2)">


<!-- 标题条 -->
<div class="pagetitle">添加属性:</div>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="submit" style="height:30px" value=" 保存 ">
		</td>
        <td width="10%" align="center"><a href="#" onClick="gotoList();">>&nbsp;返回&nbsp;</a></td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="95%" class="tableInput1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="modeltitle">基本信息</th>
	</tr>
	<tr> 
		<th  align="right"><font color="red">*</font>所属类目：</th>
		<td> 
			<input name="catId" type="text" require="true" value=""/>
		</td>
	</tr>
		<tr> 
		<th width="20%" align="right">属性名称：</th>
		<td width="80%"> 
			<input name="name" type="text" require="false" dataType="LimitB" max="20" min="0"  />
		</td>
	</tr>
		<tr> 
		<th align="right"><font color="red">*</font>状态：</th>
		<td> 
			<select name="status" >
			    <option value="unvalid">生效</option>
				<option value="deleted">删除</option>

				<option value="unvalid">不生效</option>
			</select>
		</td>
	</tr>
		<tr> 
		<th align="right"><font color="red">*</font>输入类型：</th>
		<td> 
			<select name="inputType" >
				<option value="input">文本</option>
				<option value="radio">单选</option>
				<option value="list">多选</option>
				<option value="select">下拉</option>
			</select>
		</td>
	</tr>
	
	<tr> 
		<th align="right"><font color="red">*</font>是否必选：</th>
		<td> 
			<select name="isReq" >
			    <option value="N">否</option>
				<option value="Y">是</option>
			</select>
		</td>
	</tr>
		<tr> 
		<th align="right"><font color="red">*</font>是否SKU：</th>
		<td> 
			<select name="isSku" >
			    <option value="N">否</option>
				<option value="Y">是</option>
				
			</select>
		</td>
	</tr>
			<tr> 
		<th align="right"><font color="red">*</font>是否导航：</th>
		<td> 
			<select name="isNav" >
			    <option value="N">否</option>
				<option value="Y">是</option>
				
			</select>
		</td>
	</tr>
        <tbody >
        	<tr> 
		      <th colspan="2"  width="20%"  align="left" class="modeltitle">
		          <INPUT id="addAtt" class="btngreen" TYPE="button" value="添加属性值">
		      </th>
	        </tr>
        </tbody>
        <tbody id= "custom">
        </tbody>
        
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<INPUT class="btngreen" TYPE="submit" value=" 保 存 ">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="#" onClick="gotoList();">>&nbsp;返回&nbsp;</a>
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">

//添加属性值
$("#addAtt").click(function () {
    var _custom = $("#custom");
    var _customhtml = "";
    _customhtml += "<tr>";
    _customhtml += "<td align='left' width='20%'><span>属性值:</span> ";
    _customhtml += "</td>";
    _customhtml += "<td align='left' width='80%'> <input name='attValue' type='text'/>";
    _customhtml += "<a href='#' onclick='delAttCustom(this);'>&nbsp;删除&nbsp;</a></td>";
    _customhtml += "</tr>";
    _custom.append(_customhtml);
});
//删除属性值
function delAttCustom(obj){
	$(obj).parent().parent().remove();
}

function gotoList(){
    location.href="/product/attribute_input.action";
}


//光标移动
function changeKey()
{
	//var tr=event.srcElement.getAttribute("type");
	//if("textarea"!=tr && "button" != tr)
	//{
	//		if(13 == event.keyCode)
	//		{
	//			event.keyCode=9;
	//		}
  //}
}


</SCRIPT>


</BODY>
</HTML>


