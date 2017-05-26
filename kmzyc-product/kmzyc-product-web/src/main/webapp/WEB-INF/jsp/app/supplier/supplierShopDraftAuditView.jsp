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
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
</head>
<body>

	<!-- 导航栏 -->
	<s:set name="parent_name" value="'供应商管理'" scope="request" />
	<s:set name="name" value="'供应商店铺列表'" scope="request" />
	<s:set name="son_name" value="'供应商店铺审核'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	


		<!-- 数据编辑区域 -->
		<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="2" align="center">&quot;<font color="red"><s:property
								value='rtnMessage' /> </font>
					</td>
				</tr>
			</s:if>
			<tr>
				<th colspan="2" align="left" class="edit_title">基本信息</th>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺名称：</th>
				<td>
					<s:property value="showShopMainDraft.shopName" />
					<input type="hidden" value="showShopMainDraft.shopId" id="shopId" >
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺标题：</th>
				<td><s:property value="showShopMainDraft.shopTitle" /></td>
			</tr>
			<!--  <tr>
				<th align="right" class="eidt_rowTitle">店铺LOGO：</th>
				<td><img width="142px" height="50px" src="${imagePaths}${showShopMainDraft.logoPath}"></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺展示图片：</th>
				<td><img width="300px" height="199px" src="${imagePaths}${showShopMainDraft.filePath}"></td>
			</tr>-->
			
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">经营品牌：</th>
				<td width="80%"><s:property value="showShopMainDraft.manageBrand" />
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">店铺负责人姓名：</th>
				<td width="80%"><s:property value="showShopMainDraft.principalName" />
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">店铺负责人电话：</th>
				<td width="80%"><s:property value="showShopMainDraft.contactInfo" />
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">店铺负责人手机：</th>
				<td width="80%"><s:property value="showShopMainDraft.linkmanMobile" />
				</td>
			</tr>
				<tr>
				<th width="20%" align="right" class="eidt_rowTitle">商户名称：</th>
				<td width="80%"><s:property value="showShopMainDraft.corporateName" />
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">客服联系方式：</th>
				<td width="80%"><s:property value="showShopMainDraft.serviceQq" />
				</td>
			</tr>
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">店铺类型：</th>
				<td width="80%">
				<s:if test="showShopMainDraft.shopType == 1">旗舰店</s:if>
				<s:if test="showShopMainDraft.shopType == 2">专营店</s:if>
				<s:if test="showShopMainDraft.shopType == 3">专卖店</s:if>
				</td>
			</tr>
			<tr>
				<th width="20%" class="eidt_rowTitle" align="right"></font>店铺简介：</th>
				<td width="80%"><s:property value="showShopMainDraft.introduce" />
				</td>
			</tr>
			<%--
			<tr>
				<th align="right" class="eidt_rowTitle">详细描述：</th>
				<td><s:property value="showShopMainDraft.shopName" /></td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺级别：</th>
				<td><s:property value="showShopMainDraft.shopLevel" />
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺站点：</th>
				<td><s:property value="showShopMainDraft.shopSite" />
				</td>
			</tr>--%>
		</table>

		<!-- 底部 按钮条 -->
		<table width="98%" align="center" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
			<tr>
			<td align="left">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				<input type="button" class="btn-custom" value="审核通过" onClick="auditSupplier('2')" class="btnStyle">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn-custom" value="不通过" onClick="auditSupplier('3')" class="btnStyle">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="backBtn" onClick="gotoList()" />&nbsp;&nbsp;&nbsp;&nbsp;
				 <td width="20%" align="center">&nbsp; <br>
				</td>
			</tr>
		</table>

		<br>
		<br>
		<div id="auditReason" style="display: none;">
			<textarea id="reasonArea" rows="10" cols="100" style="resize: none;" ></textarea>
		</div>

   <form name="frm"  method="post" id="returnFrm" >
      <input type="hidden"  name="pageNum"  value="<s:property value='pageNum' />"   />
      <input type="hidden"  name="searchShopMainDraft.shopTitle"  value="<s:property value='searchShopMainDraft.shopTitle' />"   />
     <input type="hidden"  name="searchShopMainDraft.shopName"  value="<s:property value='searchShopMainDraft.shopName' />"   />
    <input type="hidden"  name="searchShopMainDraft.status"  value="<s:property value='searchShopMainDraft.status' />"   />
    <input type="hidden"  name="searchShopMainDraft.auditStatus"  value="<s:property value='searchShopMainDraft.auditStatus' />"   />
   </form>
   
   <s:form action="/app/auditProductDraft.action" method="post" namespace="app" id="auditForm" name="auditForm">
		<s:hidden name="describe" id="reasonText" ></s:hidden>
		<input name="shopIdList" type="hidden" value="<s:property value='showShopMainDraft.shopId'/>" />
		<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
	</s:form>
   
	<SCRIPT LANGUAGE="JavaScript">
		function gotoList() {
		   document.forms[0].action= "/app/showShopMainDraftList.action";
		    document.forms[0].submit();
		
		}
		
		$(document).ajaxStart(function() {
			$.blockUI({ 
				message: '<img src="/etc/images/wait.gif" style="vertical-align:middle;height:42px;" /><b style="font-size:30px;vertical-align:middle;">正在操作，请稍后...</b>', 
				css: {width:'40%'}
			});
        });
		$(document).ajaxComplete(function() {
			$.unblockUI();
        });
		
		function auditSupplier(auditStatus){
			if(confirm('确定审核该店铺吗？')){
				$("#auditStatus").val(auditStatus);
				if("3"==auditStatus){
					var dia = art.dialog({   
						title:'请填写审核不通过的原因',
					    content: $("#auditReason").html(),   
					    drag:true,
					    lock:true,
					    ok: function () {
					    	$("#reasonText").val($("#reasonArea").val());
					    	$.post(
					    		"/app/updateShoMainDraftAudit.action",
					    		$("#auditForm").serializeArray(),
					    		function(data){
					    			if("1"==data){
					    				alert("操作成功！");
					    				dia.close();
					    				location.href = "/app/showShopMainDraftList.action";
					    			}else{
					    				alert("操作失败，请联系管理人员或稍后再试！");
					    			}
					    		},"json"
					    	);
					    	
					    },   
					    cancelVal: '关闭',   
					    cancel: true //为true等价于function(){}   
					}); 
				}else{
					$.post(
				    		"/app/updateShoMainDraftAudit.action",
				    		$("#auditForm").serializeArray(),
				    		function(data){
				    			if("1"==data){
				    				alert("操作成功！");
				    				location.href = "/app/showShopMainDraftList.action";
				    			}else{
				    				alert("操作失败，请联系管理人员或稍后再试！");
				    			}
				    		},"json"
				    	);
				}
				
			}
		}
	</SCRIPT>
	</BODY>
</HTML>


