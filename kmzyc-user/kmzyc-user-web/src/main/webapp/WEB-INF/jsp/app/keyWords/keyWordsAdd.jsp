<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加敏感词信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" >


$().ready(function() {
	
	
$("#keyWordsAdd").validate({
	rules: { 
		
		"keyWords":{required: true,maxlength:50},
		"repWords":{maxlength:50}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	 
});

	

</script>
</head>
<body >

<%-- <!-- 导航栏 -->--%>

<s:set name="parent_name" value="'客户活动管理'" scope="request"/>
<s:set name="name" value="'敏感词'" scope="request"/>
<s:set name="son_name" value="'添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form  id ="keyWordsAdd" name="keyWordsForm" action="/userInfo/keyWords_saveKeyWords.action" 
onsubmit=" return checkAllTextValid(this)" method="post">
<s:token/>
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="2" align="left" class="edit_title">敏感词过滤信息</th>
	</tr>
	  <tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>敏感词：</th>
		<td width="80%"> 
			<textarea id="keyWords" name="keyWords" cols="1" rows="5" ><s:property value="keyWordsDO.keyWords" /></textarea></td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle">敏感词替换为：</th>
		<td width="80%"> 
			<textarea id="repWords" name="repWords" cols="1" rows="5" ><s:property value="keyWordsDO.repWords" /></textarea>
		</td>
	</tr>
	 <tr> 
		<td width="20%"  class="eidt_rowTitle" ><font color="red">*</font>状态：</th>
		<td width="80%"> 
		<select name="status"  id="status">
						   	
							<option value="1">
								有效
							</option>
							<option value="0">
								无效
							</option>
						</select>
		</td>
	</tr>
	<tr> 
		<td width="20%"  class="eidt_rowTitle"><font color="red">*</font>类型：</th>
		<td width="80%"> 
		<select name="wordsType"  id="wordsType">
						   	
							<option value="1">
								有效（前端输入内容）
							</option>
							<option value="2">
								用户名
							</option>
						</select>
		
		</td>
	</tr>
	</table>

<%--<!-- 底部 按钮条 --> --%>

<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/userInfo/keyWords_pageList.action";
}
</SCRIPT>
</body>
</html>

