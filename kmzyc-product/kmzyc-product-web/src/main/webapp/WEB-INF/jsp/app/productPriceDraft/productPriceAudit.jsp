<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品发布</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>

<style type="text/css">
	.iframe{
		top:-10px;
	}
	.edit_title{
		padding:0px;
		font-size:12px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'产品发布'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<input type="hidden" name="productId" value="<s:property value="productId" />" ></input>
<!-- 数据编辑区域 -->
<table width="98%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;margin-left:12px;float:left;">
	<tr> 
		<th align="center" class="edit_title">商品信息</th>
		<th align="center" class="edit_title" width="80px">市场价</th>
		<th align="center" class="edit_title" width="80px">成本价</th>
		<th align="center" class="edit_title" width="80px">销售单价</th>
		<th align="center" class="edit_title" width="110px">重量（单位：克）</th>
		<th align="center" class="edit_title" width="110px">PV值（康美中药城）</th>
		<th align="center" class="edit_title" width="110px">条形码</th>
	</tr>
	<s:iterator value="product.productSkuDrafts" >
		<tr> 
			<td align="left">
				<input type="hidden" name="productSkuId" value="<s:property value="productSkuId" />" />
				<input type="hidden" id="skuPrice<s:property value="productSkuId" />" name="skuPrice" value="<s:property value="price"/>" />
				<input type="hidden" id="skuMarkPrice<s:property value="productSkuId" />" name="skuMarkPrice" value="<s:property value="markPrice"/>" />
				<b>商品名称</b>：<s:property value='product.productName'/>&nbsp;&nbsp;
				<b>商家名称</b>：
				<s:if test="product.merchantName==null">
						康美
				</s:if>
				<s:else>
					<s:property value="product.merchantName" />
				</s:else>&nbsp;&nbsp;
				<s:iterator value="attributeValues">
					<b><s:property value="attribute" /></b>：<s:property value="value" />&nbsp;&nbsp;
				</s:iterator>
			</td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='markPrice != null'><s:property value="%{getText('{0,number,##.##}',{markPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='costPrice != null'><s:property value="%{getText('{0,number,##.##}',{costPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='price != null'><s:property value="%{getText('{0,number,##.##}',{price})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='unitWeight != null'><s:property value="%{getText('{0,number,##.##}',{unitWeight})}" /></s:if><s:else>暂无重量</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='pvValue != null'><s:property value="%{getText('{0,number,##.##}',{pvValue})}" /></s:if><s:else>暂无重量</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" data="ratio" ><s:property value="skuBarCode" /></td>
		</tr>
	</s:iterator>
</table>
<br />
<!-- 底部 按钮条 -->
<table width="98%" align="left" class="edit_bottom" height="30"
	border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
	<tr>
		<td align="center">
			<input type="button" class="backBtn" onclick="gotoList()" />
			&nbsp;&nbsp;
			<input type="button" value="通过" class="btn-custom btnStyle"  onclick="auditPrice(<s:property value='product.productId'/>,2)" />
			&nbsp;&nbsp;
			<input type="button" value="不通过" class="btn-custom  btnStyle"  onclick="auditPrice(<s:property value='product.productId'/>,6)" />
		</td>
	</tr>
</table>

<div id="auditReason" style="display: none;">
	<textarea id="reasonArea" rows="10" cols="100" style="resize: none;" ></textarea>
</div>
<s:form action="/app/auditProductPriceDraft.action" method="post" namespace="app" id="auditForm" name="auditForm">
	<s:token></s:token>
	<s:hidden name="reasonText" id="reasonText" ></s:hidden>
	<input name="productIdChk" type="hidden" id="productId" value="<s:property value='product.productId'/>" />
	<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
</s:form>

<s:form action="/app/productDraftAuditShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="productForSelectPara.productNo"/>
	<s:hidden name="productForSelectPara.productName"/>
	<s:hidden name="productForSelectPara.bCategoryId"/>
	<s:hidden name="productForSelectPara.mCategoryId"/>
	<s:hidden name="productForSelectPara.categoryId"/>
	<s:hidden name="productForSelectPara.status"/>
	<s:hidden name="productForSelectPara.keyword"/>
	<s:hidden name="productForSelectPara.brandId"/>
	<s:hidden name="page.pageNo"/>
	<s:hidden name="type" />
</s:form>
</body>
<Script language="JavaScript">
	function gotoList(){
		document.getElementById("listfrm").submit();
		
		//location.href="/app/productDraftShow.action?type=priDraft";
	}
	
	function auditPrice(id,auditStatus){
		if(confirm("确定审核价格吗？")){
			$("#auditStatus").val(auditStatus);
			if(6==auditStatus){
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {  
				    	$("#reasonText").val($("#reasonArea").val());
				    	this.close();
						document.getElementById("auditForm").submit();
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}else{
				document.getElementById("auditForm").submit();
			}
		}
	}
	
</Script>
</html>