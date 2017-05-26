<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新品牌</title>

<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>

<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 

<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/brand/brand_add.js"></script>
<script type="text/javascript" src="/etc/js/brand/brandKindeditor_add.js"></script>

<style type="text/css">
 #certificateFilesRow p{
 	margin:2px 0px;
 }
 #textRow p{
 	margin:2px 0px;
 }
</style>

</head>
<body>
<div style="position:absolute;align:center;top:20px;left:30px">
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础数据'" scope="request"/>
<s:set name="name" value="'品牌管理'" scope="request"/>
<s:set name="son_name" value="'品牌添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/basedata/saveProdBrand.action" method="POST"
	namespace='/basedata' id="brandForm" enctype="multipart/form-data">

<s:token></s:token>
	<br>
	<ul id="tabs">
		<li class="visit"><b>基本信息</b></li>
		<li><b>品牌介绍</b></li>
	</ul>

	<div id="content">
		<div class="ct">
		<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="2" align="center"><font color="red"><s:property
						value='rtnMessage' /></font></td>
				</tr>
			</s:if>
			<tr>
				<th colspan="2" align="left" class="edit_title">基本信息</th>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>品牌名：</th>
				<td><input class="input_style" name="brandName" type="text" 
					value="<s:property value='model.brandName'/>" maxlength="20" /></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>国籍：</th>
				<td><select name="nation">
					<option value="国内">国内</option>
					<option value="国外">国外</option>
				</select></td>
			</tr>
			<tr>
				<th width="20%" align="right"  class="eidt_rowTitle" >英文名称：</th>
				<td width="80%"><input class="input_style" name="engName" type="text"
					value="<s:property value='model.engName'/>" maxlength="60" /></td>
			</tr>
	
			<tr>
				<th width="20%"  class="eidt_rowTitle" align="right"><font color="red">*</font>中文拼音：</th>
				<td width="80%"><input class="input_style" name="chnSpell" type="text"
					value="<s:property value='model.chnSpell'/>"  maxlength="60" />&nbsp;&nbsp;(如耐克：NAIKE)
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">品牌主页：</th>
				<td><input class="input_style" name="homepage" dataType="Url" type="text" size="30"
					value="<s:property value='model.homepage'/>" maxlength="200" />&nbsp;&nbsp;(如：www.NIKE.com)
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">Logo图片：</th>
				<td><input type="file" name="logoFile" onchange="changeLogoPath(this)"  /> (请选择118*46的图片)<input
					type="hidden" id="logoPath" name="logoPath" value="" />
					<font color="red" id="messages"><s:property value='message' /></font>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">展馆主图：</th>
				<td><input type="file" name="pavilionFile" onchange="changePavilionPath(this)"  /><input
					type="hidden" id="pavilionPath" name="pavilionPicPath" value="" /></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">详情介绍图片或视频：</th>
				<td><input type="file" name="introduceFile" onchange="changeIntroducePath(this)" /><input
					type="hidden" id="introducePath" name="introduceFilePath" value="" /></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">荣誉证书：</th>
				<td id="certificateFilesRow">
					<INPUT class="btnStyle" TYPE="button" value="增加一条" onclick="addFileRow();">
					<p>
						<input type="file" name="certificateFiles" onchange="changeCertificateFilesPath(this)" />
						<INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);">
						<input type="hidden" name="certificateFilesPath" value="" />
					</p>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">联系方式：</th>
				<td id="textRow">
					<INPUT class="btnStyle" TYPE="button" value="增加一条" onclick="addTextRow();">
					<p><input type="text" name="contactTypes" value="地址" size="5" />：<input type="text"" name="contactValues" value="" size="50" />&nbsp;<INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);"></p>
					<p><input type="text" name="contactTypes" value="邮编" size="5" />：<input type="text"" name="contactValues" value="" size="50" />&nbsp;<INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);"></p>
					<p><input type="text" name="contactTypes" value="电话" size="5" />：<input type="text"" name="contactValues" value="" size="50" />&nbsp;<INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);"></p>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">描述：</th>
				<td><textarea name="des" cols="50" rows="4" wrap="PHYSICAL"><s:property
					value='model.des' /></textarea></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">是否有效：</th>
				<td>
					<select name="isValid">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">备注：</th>
				<td><textarea name="remark" cols="50" rows="4" wrap="PHYSICAL"><s:property
					value='model.remark' /></textarea></td>
			</tr>
		</table>
		</div>
		<div class="ct">
			<textarea id="editor_id" name="brandIntroduce"  
				style="height:400px;width:1014px;resize:none;"> 
				 <s:property value="brandIntroduce"/>
			</textarea>
		</div>
	</div>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="button" onclick="submitBrandAddForm()"
				value="">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onclick="gotoList()" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>
	
	<!-- lazy -->
	<div class="editor_change" style="visibility:hidden" ></div>
	<div style="visibility:hidden">
		<textarea id="editor_lazy" name="brandIntroduceLazy" > 
			 <s:property value="brandIntroduceLazy"/>
		</textarea>
	</div>

</s:form>
</div>

</BODY>
</HTML>


