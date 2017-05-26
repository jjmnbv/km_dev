<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择地区</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>

<style type="text/css">
	body{
		padding:0px;
		margin:0px;
	}
	table{
		margin-left:10px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="gotoPopSelectRole.action" method="POST"  namespace='/sys'>

<br>

<div style="margin: 10px 0px 6px 20px">
    <INPUT TYPE="button" class="btngreen" style="height:30px" value=" 保存所选 " onClick="selectList();">
</div>

<table width="200" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" colspan="8"><input id="checkAll" class="ch_0" type="checkbox" name="checkAll" value="1" <s:if test='#request.areaMap[1]!=null'>checked="checked"</s:if> />全国</th>
	</tr>
	<s:iterator id="obj" value="#request.provinceList" status="sta">
	<s:if test="#sta.getIndex()%4==0">
		<tr>
	</s:if>
		<td bgcolor="#FFFFCC" align="center" style="word-break:break-all" width="5%">
			<input id="province"  type="checkbox" name="province" value='<s:property value="areaId"/>_<s:property value="areaName"/>' 
			<s:if test='#request.areaMap[1]!=null'>checked="checked"</s:if>
			<s:elseif test='#request.areaMap[areaId]!=null'>checked="checked"</s:elseif>
			>
		</td>
		<td width="15%">
			<span style="width:80px;display:inline-block ;" ><b><s:property value="areaName"/></b></span>
		</td>
	<s:if test="#status.modules(4)">
		</tr>
	</s:if>
	
	</s:iterator>
</table>

<div style="margin: 6px 0px 10px 20px">
    <INPUT TYPE="button" class="btngreen" style="height:30px" value=" 保存所选 " onClick="selectList();">
</div>

<br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">

$(document).ready(function() {
    $("#checkAll").click(function() {
    	$('input[name="province"]').attr("checked",this.checked);
	    var $subBox = $("input[name='province']");
	    $subBox.click(function(){
	    	$("#checkAll").attr("checked",$subBox.length == $("input[name='province']:checked").length ? true : false);
	    });
    }); 

});

function selectList() {
	if($("input[type='checkbox']:checked").length==0){
		alert("请选择地区！");
		return;
	}
	var areaIds = "|";
	var areaNames = "";
	var values = new Array();
	if($("#checkAll").attr("checked")){
		areaIds = "|1|";
		areaNames = "全国";
	}else{
		$("input[name='province']:checked").each(function(i){
			var strs = $(this).val().split('_');
			areaIds += strs[0] + "|";
			areaNames += strs[1] + "|";
		});
	}
	//areaIds = areaIds.substring(0,areaIds.length-1);
	if(areaNames.indexOf("|")>0){
		areaNames = areaNames.substring(0,areaNames.length-1);
	}
    parent.closeOpenArea(areaIds,areaNames);
}


</SCRIPT>


</BODY>
</HTML>

