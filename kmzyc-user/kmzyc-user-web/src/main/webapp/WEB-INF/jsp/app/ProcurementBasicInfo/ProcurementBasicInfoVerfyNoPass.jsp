<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
 
<title>审核采购商信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<!--<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />-->
 <script src="/etc/js/dialog.js"></script>
</head>
<body onkeydown="changeKey();">
		<!-- 数据编辑区域 -->
<table width="100%" cellpadding="10" cellspacing="0" border="0" bordercolor="#fff">
			 <input type="hidden"  name="commercialTenantBasicInfo.n_LoginId"  id="n_LoginId"  value="<s:property value='commercialTenantBasicInfo.n_LoginId'/>"  />
			<tr>
				<th colspan="" align="right">请填写原因：</th>
				<td><textarea name="yj" clos="20" rows="10" id="texta" style="width:300px; height:100px;"></textarea></td>
			</tr>
</table>

		<!-- 底部 按钮条 -->
		<table width="98%" align="center" height="30"border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
			<tr>
			<td align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				&nbsp;&nbsp;&nbsp; 
				&nbsp;&nbsp;&nbsp;<input class="btnStyle" type="button" onClick="batchAuditSupplier()" value="提交" style="width: 60px;height: 35px;">
				<td width="20%" align="center">&nbsp; <br>
				</td>
			</tr>
		</table>
		<br>
		<br>
	<SCRIPT LANGUAGE="JavaScript">
	 

		function batchAuditSupplier(){
			var texts=$("#texta").val();
	 
			var n_LoginId=$("#n_LoginId").val();
			if("" == texts){
				alert("请输入理由！");
				return;
				}
			 $.ajax({ 
	             url: '/userInfo/commercialTenantBasicInfo_notPass.action',
	             async:false,
	             data: 'commercialTenantBasicInfo.description='+texts+'&commercialTenantBasicInfo.n_LoginId='+n_LoginId+"&n_CustomerTypeId="+4,
		             
	             success: function(info) {
	                if('0' == info){
	                	alert("审核失败，请联系管理员！");
	                	 window.location.href="supplierAuditList.action";
	                }else{
	                	 
	                	 parent.closes();
	                  
		                }
	             }
	         });
			}
	</SCRIPT></BODY>
</HTML>


