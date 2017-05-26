<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改品牌</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

</head>
<body>

<!-- 导航栏 -->
<s:set name="parent_name" value="'基础数据'" scope="request"/>
<s:set name="name" value="'品类管理'" scope="request"/>
<s:set name="son_name" value="'品类修改'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/basedata/updateOprationAttr.action" method="POST" id="frm"  namespace='/basedata'  >

<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">基本信息</th>
	</tr>
	<tr> 
		<th  align="right" class="eidt_rowTitle" width="20%"><font color="red">*</font>名称：</th>
		<td width="80%"> 
			<input type="hidden" id="operationAttrId" name="attr.operationAttrId" value='<s:property value="attr.operationAttrId" />' >
			<input class="input_style" maxlength="20" name="attr.operationAttrName" type="text" value="<s:property value='attr.operationAttrName'/>" />
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">是否有效：</th>
		<td>
			<select name="attr.status">
				<option value="1" <s:if test='attr.status == "1"'> selected="selected"</s:if> >有效</option>
				<option value="0" <s:if test='attr.status == "0"'> selected="selected"</s:if> >无效</option>
			</select>
		</td>
	</tr>
	<tr>
			<th align="right" class="eidt_rowTitle">是否导航属性：</th>
			<td>
				<select name="attr.isNav">
					<option value="1" <s:if test='attr.isNav == 1'> selected="selected"</s:if> >是</option>
				<option value="0" <s:if test='attr.isNav == 0'> selected="selected"</s:if> >否</option>
				</select>
			</td>
		</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">备注：</th>
		<td> 
			<textarea name="attr.remark" cols="50" rows="4"><s:property value='attr.remark'/></textarea> 
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>


<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
    location.href="/basedata/busiAttributeList.action";
}

$(function(){
	$("#frm").validate({
		rules: {
			"attr.operationAttrName":{required:true,maxlength:50,unusualChar:true,checkRepeatName:true},
			"attr.remark":{maxlength:200,unusualChar:true}
	    	},
		success: function (label){
			label.removeClass("checked").addClass("checked");
		}
   });
	
	
	jQuery.validator.addMethod("checkRepeatName", function(value, element) {
		var flag = true;
		$.ajax({
				async:false,
				url:"/basedata/operationAttrCheckRepeatName.action",
				type:"post",
				data:{repeatName:value,operationAttrId:$("#operationAttrId").val()},
				success:function(data){
					if(data=="repeat"){
						element.select();
						flag = false;
					}
				}
		});
		return flag;
	}, "该属性名名已存在，请重新命名！");
	
});

</SCRIPT>


</BODY>
</HTML>


