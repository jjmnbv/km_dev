<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
 
<title>审核药方信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
</head>
<body onkeydown="changeKey();">
		<!-- 数据编辑区域 -->
		<table width="95%"  width="400px" height="200px" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2">
			 <input type="hidden"  name="purchaseListDO.purchaseId"  id="purchaseId"  value="<s:property value='purchaseListDO.purchaseId'/>"  />
			 <input type="hidden"  name="purchaseListDO.presStatus"  id="presStatus"  value="3"  />
			
			
			<tr>
				<th colspan="" align="left" class="edit_title">审核情况：</th>
				<td><textarea name="yj" clos="20" rows="10" id="texta"></textarea></td>
			</tr>
		</table>

		<!-- 底部 按钮条 -->
		<table width="98%" align="center" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
			<tr>
			<td align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				&nbsp;&nbsp;&nbsp; 
				&nbsp;&nbsp;&nbsp;<input class="btnStyle" type="button" onclick="purchase()" value="提交">
				<td width="20%" align="center">&nbsp; <br>
				</td>
			</tr>
		</table>
		<br>
		<br>
	<SCRIPT LANGUAGE="JavaScript">
	 

		function purchase(){
			var texts=$("#texta").val();
	 
			var purchaseId=$("#purchaseId").val(); 

			var loginId=$("#loginId").val(); 
			var presStatus=$("#presStatus").val();
			if("" == texts){
				alert("请输入理由！");
				return;
				}
			 $.ajax({ 
	             url: '/userInfo/prescripinfo_noPassPres.action',
	             async:false,
	             data: 'purchaseListDO.reviewDescription='+texts+'&purchaseListDO.purchaseId='+purchaseId+"&purchaseListDO.presStatus="+presStatus,     
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


