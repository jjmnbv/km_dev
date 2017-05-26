<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改品牌</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
	<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
	<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
	<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
	<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/kindeditor/kindeditor.js"></script>
	<script type="text/javascript" src="/kindeditor/lang/zh_CN.js"></script>
	<script type="text/javascript" src="/kindeditor/plugins/code/prettify.js"  ></script>
	<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
	<script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
	<script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
	<script type="text/javascript" src="/etc/js/brand/brand_update.js"></script>
	<script type="text/javascript" src="/etc/js/brand/brandKindeditor_add.js"></script>
	<style type="text/css">
	#certificateFilesRow p {
		margin: 2px 0px;
	}

	#textRow p {
		margin: 2px 0px;
	}
	</style>
</head>
<body>
<div style="position: absolute; align: center; top: 20px; left: 30px">
<!-- 导航栏 -->
	<s:set name="parent_name" value="'基础数据'" scope="request" />
	<s:set name="name" value="'品牌管理'" scope="request" />
	<s:set name="son_name" value="'品牌修改'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<s:form action="/basedata/updateProdBrand.action" method="POST" id="brandForm"
			namespace='/basedata' enctype="multipart/form-data">
	<br>
	<ul id="tabs">
		<li class="visit"><b>基本信息</b></li>
		<li><b>品牌介绍</b></li>
	</ul>
	
	<div id="content">
	<div class="ct"><!-- 数据编辑区域 -->
		<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse; font-size: 12px;">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="2" align="center"><font color="red"><s:property value='rtnMessage' /></font></td>
				</tr>
			</s:if>
			<tr>
				<th colspan="2" align="left" class="edit_title">基本信息</th>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">品牌名：</th>
				<td><input type="hidden" id="brandId" name="brandId" value='<s:property value="brandId" />'>
						<%-- <s:property value='model.brandName'/>--%>
					<input class="input_style" name="brandName" type="text" value="<s:property value='model.brandName'/>" maxlength="20" />
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>国籍：</th>
				<td>
					<select name="nation">
						<option value="国内" <s:if test='model.nation == "国内"'> selected="selected"</s:if>>国内</option>
						<option value="国外" <s:if test='model.nation == "国外"'> selected="selected"</s:if>>国外</option>
					</select>
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">英文名称：</th>
				<td width="80%">
					<input class="input_style" name="engName" type="text" maxlength="60" value="<s:property value='model.engName'/>" /></td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">
					<font color="red">*</font>中文拼音：</th>
				<td width="80%">
					<input class="input_style" name="chnSpell" type="text" maxlength="60" value="<s:property value='model.chnSpell'/>" />&nbsp;&nbsp;(如耐克：NAIKE)
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">品牌主页：</th>
				<td><input class="input_style" name="homepage" type="text"
					maxlength="20" value="<s:property value='model.homepage'/>" />&nbsp;&nbsp;(如：www.NIKE.com)
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">Logo图片：</th>
				<td><s:file onchange="changePath(this);" name="logoFile" value="" /> (请选择118*46的图片)&nbsp;&nbsp;&nbsp;
					<s:if test="model.logoPath == null">
						暂无图片
					</s:if>
					<s:else>
					已有图片：<a href="<s:property value="path"/><s:property value='model.logoPath'/>" target="_blank">
						<img width="142" height="50" src="<s:property value="path"/><s:property value='model.logoPath'/>" /></a>
					</s:else>
					<input type="hidden" id="logoPath" name="logoPath" value="<s:property value="model.logoPath"/>" />
					<font color="red" id="messages"><s:property value='message' /></font>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">展馆主图：</th>
				<td><input type="file" name="pavilionFile"
					onchange="changePavilionPath(this)" /> &nbsp;&nbsp;&nbsp;
					<s:if test="model.pavilionPicPath == null">
						暂无图片
					</s:if>
					<s:else>
					已有图片：<a href="<s:property value="path"/><s:property value="model.pavilionPicPath"/>" target="_blank">
								<img height="100" src="<s:property value="path"/><s:property value="model.pavilionPicPath"/>" /></a>
					</s:else>
					<input type="hidden" id="pavilionPath" name="pavilionPicPath" value="<s:property value="model.pavilionPicPath"/>" />
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">详情介绍图片或视频：</th>
				<td><input type="file" name="introduceFile" onchange="changeIntroducePath(this)" /> &nbsp;&nbsp;&nbsp;
					<s:if test="model.introduceFilePath == null">
						暂无文件
					</s:if>
					<s:else>
					已有文件：<s:property value="path" /><s:property value="model.introduceFilePath" />
					</s:else>
					<input type="hidden" id="introducePath" name="introduceFilePath" value="<s:property value="model.introduceFilePath"  />" />
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">荣誉证书：</th>
				<td id="certificateFilesRow">
					<p><input type="hidden" id="isDelete" name="isDeleteCertificate" value="0">
						<input class="btnStyle" type="button" value="增加一条" onclick="addFileRow();">
					</p>
					<s:iterator value="#request.certifiMap">
						<span>
							<a href="<s:property value="path"/><s:property />" target="_blank">
								<img height="100" src="<s:property value="path"/><s:property />" /></a>&nbsp;
							<input type="hidden" name="oldCertificateFilesPath" value="<s:property />">
							<input class="btnStyle" type="button" value="删除" onclick="deleteFileRow(this.parentNode);">
						</span>
					</s:iterator>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">联系方式：</th>
				<td id="textRow">
					<input class="btnStyle" type="button" value="增加一条" onclick="addTextRow();">
					<s:iterator value="#request.contactMap" var="contact">
					<p>
						<input type="text" name="contactTypes" value="<s:property value="#contact.key"/>" size="5" />：&nbsp;
						<input type="text" name="contactValues" value="<s:property value="#contact.value"/>" size="50" />&nbsp;
						<input class="btnStyle" type="button" value="删除" onclick="deleteTextRow(this.parentNode);"/>
					</p>
					</s:iterator>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">描述：</th>
				<td><textarea name="des" cols="50" rows="4" wrap="PHYSICAL"><s:property value='model.des' /></textarea></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">是否有效：</th>
				<td>
					<select name="isValid">
						<option value="1" <s:if test='model.isValid == "1"'> selected="selected"</s:if>>是</option>
						<option value="0" <s:if test='model.isValid == "0"'> selected="selected"</s:if>>否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">备注：</th>
				<td><textarea name="remark" cols="50" rows="4" wrap="PHYSICAL"><s:property value='model.remark' /></textarea></td>
			</tr>
		</table>
	</div>
	<div class="ct">
		<textarea id="editor_id" name="brandIntroduce" style="height: 400px; width: 1014px; resize: none;">
			 <s:property value="model.brandIntroduce" />
		</textarea>
	</div>
	</div>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
				<input class="saveBtn" TYPE="button" onclick="submitBrandAddForm();" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="backBtn" onclick="gotoList()" />
			</td>
			<td width="20%" align="center"></td>
		</tr>
	</table>
	<br>
	<br>
				
	<!-- lazy -->
	<div class="editor_change" style="visibility:hidden" ></div>
	<div style="visibility:hidden">
		<textarea id="editor_lazy" name="brandIntroduceLazy" > 
			 <s:property value="model.brandIntroduceLazy"/>
		</textarea>
	</div>
	</s:form>
</div>
</body>
</html>