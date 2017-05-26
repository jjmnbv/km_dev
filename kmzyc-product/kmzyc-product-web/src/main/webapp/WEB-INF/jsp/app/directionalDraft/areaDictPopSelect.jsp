<%@page contentType="text/html;charset=UTF-8"%>
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
<input type="hidden" value="<s:property value="areaIds"/>" id="selectedId" />
<table width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
	    <th bgcolor="#99ccff" align="center" width="20%"><input id="1" class="ch_0" type="checkbox" name="areaId" value="1" onClick="linkageChecked(this);" />全国/省</th>
		<th bgcolor="#99ccff" align="center" width="80%">市</th>
	</tr>
	<s:iterator id="obj" value="dataList">
	<tr>
		<td bgcolor="#FFFFCC" align="center"><input onClick="linkageChecked(this);" id="<s:property value="#obj.key.areaId"/>" class="ch_<s:property value="#obj.key.supperareaId"/>" type="checkbox" name="delId" value="<s:property value="#obj.key.areaName"/>" ><b><s:property value="#obj.key.areaName"/></b></td>
		<td bgcolor="#FFFFCC" align="left" style="word-break:break-all">
			<s:iterator id="city" value="#obj.value" >
				<span style="width:80px;display:inline-block ;" ><input onClick="linkageChecked(this);" type="checkbox" id="<s:property value="areaId"/>" class="ch_<s:property value="supperareaId"/>" type="checkbox" name="delId" value="<s:property value="areaName"/>" /><s:property value="areaName" /></span>
			</s:iterator>
		</td>
	</tr>
	</s:iterator>
</table>

<div style="margin: 6px 0px 10px 20px">
    <INPUT TYPE="button" class="btngreen" style="height:30px" value=" 保存所选 " onClick="selectList();">
</div>

<br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">
function selectList() {
	
	if($("input[type='checkbox']:checked").length==0){
		alert("请选择地区！");
		return;
	}
	var areaIds = "";
	var areaNames = "";
	if($("#1").attr("checked")){
		areaIds = "1,";
		areaNames = "全国,";
	}else{
		$("input[type='checkbox']:checked").each(function(i){
			var claName = this.className.split('_');
			
			//检查自己的上级是否已被勾选
			if($("#"+claName[1]+":checked").length==0){
				areaIds += this.id + ",";
				areaNames += this.value + ",";
			}	
		});
	}
	areaIds = areaIds.substring(0,areaIds.length-1);
	areaNames = areaNames.substring(0,areaNames.length-1);
    parent.closeOpenArea(areaIds,areaNames);
}


function linkageChecked(arg){
	$("input[class='ch_"+arg.id+"']").attr("checked",arg.checked);
	$("input[class='ch_"+arg.id+"']").each(function(i){
		$("input[class='ch_"+this.id+"']").attr("checked",this.checked);
	});
	
	var flag = true;
	$("input[class='"+arg.className+"']").each(function(i){
		if(this.checked == false){
			flag  = false;
		}
	});
	var _id = arg.className.substring(arg.className.indexOf('_')+1);
	var _input = $("input[id='"+_id+"']");
	
	
	if(_input.length>0){
		_input.attr("checked",flag);
		
		flag = true;
		var clsName = $(_input).attr("class");
		$("input[class='"+clsName+"']").each(function(i){
			if(this.checked == false){
				flag  = false;
			}
		});
		_id = clsName.substring(clsName.indexOf('_')+1);
		_input = $("input[id='"+_id+"']");
		_input.attr("checked",flag);
	}
}

$(document).ready(function() {
	if($("#selectedId").val()!=""){
		var areaIds = $("#selectedId").val().split(",");
		$(areaIds).each(function(i,value){
			var _check = $("input[id='"+value+"']")[0];
			_check.checked = true;
			linkageChecked(_check);
		});
	}
});

</SCRIPT>
</BODY>
</HTML>

