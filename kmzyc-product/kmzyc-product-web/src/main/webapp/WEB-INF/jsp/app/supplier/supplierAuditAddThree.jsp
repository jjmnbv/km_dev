<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加供应商</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/supplier/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/supplier/supplierAuditUpdate.js"></script>
</head>
<body>

<s:set name="parent_name" value="'供应商审核管理'" scope="request"/>
<s:set name="name" value="'添加供应商'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		
		<tr>
			<th colspan="2" align="left" class="edit_title">上传资质文件</th>
		</tr>
		<tr>
				<th align="right" class="eidt_rowTitle">资质文件：</th>
				<td><input type="hidden" value="${imagePath}" id="imagePath">
				<ul id="addFile">
				<s:iterator value="suppliersCertificateList">
				<li id="hrefId${scId}" style="float: left;list-style-type:none;width: 200px;height:220px;">
					&nbsp;&nbsp;<a href="${imagePath}${filePath}" title="单击放大" target="_blank" id="hrefId${scId}"><img width="190px" height="190px" src="${imagePath}${filePath}">
					${fileName }</a>&nbsp;&nbsp; <a href="javascript:void(0);" id="del${scId}" onclick="deleteFiles(${scId})">删除</a>
				</li>
				</s:iterator>
				</ul>
				</td>
			</tr>
		<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>上传纸质文件：</th>
				<td>
				<s:form action="uploadCeriticate.action" namespace="app" name="frm1" method="post" enctype="multipart/form-data" id="frm1" class="ui-form ui-form-vertical fn-t20" >
			<input type="hidden" name="supplierId" value="${suppliersInfo.supplierId}">
				<div class="ui-form-item">
					<img id="loading_img" src="" style="display: none;" />
		            <label for="" class="ui-form-label">
		                <span class="ui-form-required">*</span>请上传您的营业执照（副本）、组织机构代码证扫描件，以及您所供应商品的相关授权、资质证明文件的扫描件。
		            </label>
		            <input type="text" tabindex="9"  id="fileName" name="realName" class="ui-input fn-w280 fn-mr10" placeholder="请输入文件名称" />
		            <input tabindex="10" id="upFile" contenteditable="false" class="fn-w180 fn-mr20" name="file" type="file" />
		            <span id="error_message" class="fn-red"></span>
		            <input type="button" value="上传文件" onclick="submitForms();"/><span><s:fielderror cssStyle="color: red"/></span>
		        </div>
			</s:form>
				</td>
			</tr>
		
	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
			<!--  <input type="button" class="saveBtn" onclick="submitForm();" >
			&nbsp;&nbsp;&nbsp;&nbsp;-->
				<input type="button" class="" onclick="gotoList()" value="返回列表"/>
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>


<script LANGUAGE="JavaScript">
	function gotoList() {
		document.forms[0].action = "supplierAuditList.action";
		document.forms[0].submit();
	}

	
</script>


</body>
</html>


