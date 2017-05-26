<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<title>促销活动例外产品</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<!--
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
-->
<script type="text/javascript"  src="/etc/js/promotion.js"></script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>
<script type="text/javascript">

</script>
</head>
<s:if test="promotion.nature==1">
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'促销活动'" scope="request"/>
<s:set name="son_name" value="'活动例外商品列表'" scope="request"/>
</s:if>
<s:if test="promotion.nature==2">
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'加价购组合列表'" scope="request"/>
<s:set name="son_name" value="'加价购商品列表'" scope="request"/>
</s:if>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:hidden name="promotion.shopSort" id="shopSort"></s:hidden>
	<s:hidden name="promotion.supplierId" id="shopCodes"></s:hidden>
	<s:form name="sectionsForm" method="post" id="frm"
		action="/promotion/queryPromotionExceptionProductList.action">
		<s:hidden name="promotionProduct.promotionId" id="promotionId"></s:hidden>
		<s:hidden name="promotion.nature" id="nature"></s:hidden>
		<input type="hidden" value="<s:property value='promotion.promotionType' />" id="promotionType"/>
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 按钮条 -->
	
		<table width="98%" align="center" class="topbuttonbar readonly" height="30"
			border="0" cellpadding="0" cellspacing="0" >
			
			<tr>
		<!--  	<s:if test="promotion.onlineStatus!=3">
				<td width="90%" valign="middle"><input class="addBtn"
					type="button" value="" onClick="gotoAdd(<s:property value='promotionProduct.promotionId' />);">
						<s:if test="promotion.status==1"> <input class="delBtn" type="button" value="" onClick="deleteProduct(<s:property value='promotionProduct.promotionId' />);"></s:if>
				</td>
				</s:if>-->
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				</td>
			</tr>
		</table>
	
		  <!-- 查询条件区域 -->
		<table width="98%"   cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right" width="120px">SKU编号：</td>
				<td><input name="promotionProduct.productSkuCode" type="text" class="input_style"
					value="<s:property value='promotionProduct.productSkuCode' />"></td>
                <td align="right" width="120px">产品标题：</td>
				<td><input name="promotionProduct.productTitle" type="text" class="input_style"
					value="<s:property value='promotionProduct.productTitle' />"></td>
				<td align="center"><INPUT TYPE="button" class="queryBtn" onclick="$('#frm').submit();"
					value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center">产品名称</th>
				<th align="center">SKU编号</th>
				<th align="center">SKU属性</th>
				<th align="center">状态</th>
				<th align="center">商家</th>
				<th align="center">品牌</th>
				
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					
					<td align="center"><s:property value="productName"/></td>
					<td align="center"><s:property value="productSkuCode" /></td>
					<td align="center"><s:iterator value="productSkuAttrList" >
					<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
					</s:iterator></td>
					<td align="center">
					<s:if test="productStatus==3">上架</s:if>
					<s:elseif test="productStatus==4">下架</s:elseif>
					</td>
					<td align="center">
					<s:property value="shopName"/>
					</td>
					<td align="center"><s:property value='brandName'/></td>
					
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0"  >
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
	</s:form>
</body>
<script type="text/javascript">
	
</script>
</html>

