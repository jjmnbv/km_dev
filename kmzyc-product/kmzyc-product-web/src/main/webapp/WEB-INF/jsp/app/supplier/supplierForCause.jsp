<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新供应商</title>

<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<Script language="JavaScript" src="/etc/js/Form.js"
	type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>

 <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
  <script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
 <script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
</head>
<body onkeydown="changeKey();">
		<!-- 数据编辑区域 -->
		<table width="100%" class="edit_table" align="center" width="400px" height="200px" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			 <input type="hidden"  name="suppliersInfo.supplierId"  id="supplierId" value="<s:property value='suppliersInfo.supplierId' />"   />
			<tr>
				<th colspan="" align="left" class="edit_title">请填写原因：</th>
				<td><textarea name="yj" clos="20" rows="10" id="texta"></textarea></td>
			</tr>
		</table>

		<!-- 底部 按钮条 -->
		<table width="98%" align="center" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
			<tr>
			<td align="left" style="padding-left: 200px" >
				<input class="btnStyle" type="button" onclick="batchAuditSupplier()" value="提交">
				<td width="20%" align="center">&nbsp; <br>
				</td>
			</tr>
		</table>
		<br>
		<br>
	<SCRIPT LANGUAGE="JavaScript">
		function gotoList() {
		   document.forms[0].action= "/basedata/prodSupplierShow.action";
		    document.forms[0].submit();
		}

		function batchAuditSupplier(){
			var texts=$("#texta").val();
			var suppId=$("#supplierId").val();
			if("" == texts){
				alert("请输入理由！");
				return;
				}
			 $.ajax({
	             url: 'notPass.action',
	             async:false,
	             data: 'describe='+encodeURI(encodeURI(texts))+'&suppliersInfo.supplierId='+suppId,
	             success: function(info) {
	                if('0' == info){
	                	alert("审核失败，请联系管理员！");
	                	 window.location.href="supplierAuditList.action";
	                }else{
	                	alert("审核成功！");
	                	parent.closes();
		                }
	             }
	         });
			}
	</SCRIPT></BODY>
</HTML>


