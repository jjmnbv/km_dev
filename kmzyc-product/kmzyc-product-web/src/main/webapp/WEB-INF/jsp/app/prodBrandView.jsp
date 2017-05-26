<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>查看品牌</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
	<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
	<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
	<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/etc/js/Form.js"></script>
	<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/kindeditor/kindeditor.js"></script>
	<script type="text/javascript" src="/kindeditor/lang/zh_CN.js"></script>
	<script type="text/javascript" src="/kindeditor/plugins/code/prettify.js"></script>
	<script type="text/javascript" src="/etc/js/brand/brandKindeditor_show.js"></script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础数据'" scope="request"/>
<s:set name="name" value="'品牌管理'" scope="request"/>
<s:set name="son_name" value="'品牌查看'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/basedata/saveProdBrand.action" method="POST"  namespace='/basedata' onsubmit="return Validator.Validate(this,2)">
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
		<th align="right" class="eidt_rowTitle">品牌名：</th>
		<td><s:property value='model.brandName'/></td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">国籍：</th>
		<td><s:property value='model.nation'/></td>
	</tr>
	<tr> 
		<th width="20%" align="right" class="eidt_rowTitle">英文名称：</th>
		<td width="80%"><s:property value='model.engName'/></td>
	</tr>

	<tr> 
		<th width="20%" align="right" class="eidt_rowTitle">中文拼音：</th>
		<td width="80%"><s:property value='model.chnSpell'/></td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">品牌主页：</th>
		<td><s:property value='model.homepage'/></td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">Logo图片：</th>
		<td> 
			<s:if test="model.logoPath == null">
				暂无图片
			</s:if>
			<s:else>
				<a href="<s:property value="path"/><s:property value='model.logoPath'/>" target="_blank" ><img width="142" height="50" src="<s:property value="path"/><s:property value='model.logoPath'/>" /></a>
			</s:else>
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">展馆主图：</th>
		<td>
			<s:if test="model.pavilionPicPath == null">
				暂无图片
			</s:if>
			<s:else>
				<a href="<s:property value="path"/><s:property value="model.pavilionPicPath"/>" target="_blank" ><img height="100" src="<s:property value="path"/><s:property value="model.pavilionPicPath"/>" /></a>
			</s:else>
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">详情介绍图片或视频：</th>
		<td>
			<s:if test="model.introduceFilePath == null">
				暂无文件
			</s:if>
			<s:else>
				<s:property value="path"/><s:property value="model.introduceFilePath"  />
			</s:else>
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">荣誉证书：</th>
		<td id="certificateFilesRow">
			<s:iterator value="#request.certifiMap">
				<a href="<s:property value="path"/><s:property />" target="_blank" ><img height="100" src="<s:property value="path"/><s:property />" /></a>&nbsp;
			</s:iterator>
		</td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">联系方式：</th>
		<td id="textRow">
			<s:iterator value="#request.contactMap" var="contact" >
				<p style="margin:2px 0px;"><s:property value="#contact.key"/>：<s:property value="#contact.value"/></p>
			</s:iterator>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">描述：</th>
		<td><s:property value='model.des'/></td>
	</tr>
	<tr>
		<th align="right" class="eidt_rowTitle">是否有效：</th>
		<td>
			<s:if test="model.isValid == 1">有效</s:if>
			<s:if test="model.isValid == 0">无效</s:if>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">备注：</th>
		<td><s:property value='model.remark'/></td>
	</tr>
	<tr> 
		<th colspan="2" align="left" class="edit_title">品牌介绍</th>
	</tr>
	<tr> 
		<td align="left" colspan="2">
			<textarea id="editor_id" name="brandIntroduce" style="height:400px;width:1014px;resize:none;">
				 <s:property value="model.brandIntroduce"/>
			</textarea>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>
</s:form>

<script>
	function gotoList(){
		location.href="/basedata/prodBrandShow.action";
	}

	function showPath(){
		alert(document.getElementById("logoPath").value);
	}
</script>
</body>
</html>