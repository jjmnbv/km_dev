<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://kmpro.km1818.com/functions" prefix="activity" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>

<script type="text/javascript" src="/etc/js/activity/activitySupplierEntryDeatil.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
</head>
<body>

<s:set name="parent_name" value="'已报活动商品'" scope="request" />
<s:set name="name" value="'详情'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form id="ajaxSubmit" enctype="multipart/form-data">
	<div id="activitySkuIdsDiv">
	</div>	

	<!-- 基本信息区域 -->
	基本信息：
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<input type="hidden" name="activitySupplierEntry.supplierEntryId" value="<s:property value='activitySupplierEntry.supplierEntryId'/>" />
		<input type="hidden" name="activitySupplierEntry.activityId" value="<s:property value='activitySupplierEntry.activityId'/>" />
		<input type="hidden" name="activitySupplierEntry.activityName" value="<s:property value='activitySupplierEntry.activityName'/>" />
		<input type="hidden" name="activitySupplierEntry.supplierId" value="<s:property value='activitySupplierEntry.supplierId'/>" />
		<input type="hidden" name="activitySupplierEntry.activityType" value="<s:property value='activitySupplierEntry.activityType'/>" />
		<input type="hidden" name="activityStartTime" value='<s:date name="activitySupplierEntry.createStartTime" format="yyyy-MM-dd HH:mm:ss" />' />
		<input type="hidden" name="activityEndTime" value='<s:date name="activitySupplierEntry.createEndTime" format="yyyy-MM-dd HH:mm:ss" />' />
		<tr>
			<td>公司名称：</td>
			<input type="hidden" name="activitySupplierEntry.companyShowName" value="<s:property value='activitySupplierEntry.companyShowName'/>" />
			<td><s:property value="activitySupplierEntry.companyShowName" /></td>
		</tr>
		<tr>
			<td>店铺名称：</td>
			<input type="hidden" name="activitySupplierEntry.shopName" value="<s:property value='activitySupplierEntry.shopName'/>" />
			<td><s:property value="activitySupplierEntry.shopName" /></td>
		</tr>
		<tr>
			<td>登录账号：</td>
			<input type="hidden" name="activitySupplierEntry.loginAccount" value="<s:property value='activitySupplierEntry.loginAccount'/>" />
			<td><s:property value="activitySupplierEntry.loginAccount" /></td>
		</tr>
		<tr>
			<td>联系电话：</td>
			<input type="hidden" name="activitySupplierEntry.mobile" value="<s:property value='activitySupplierEntry.mobile'/>" />
			<td><s:property value="activitySupplierEntry.mobile" /></td>
		</tr>
		<tr>
			<td>报名时间：</td>
			<td><s:date name="activitySupplierEntry.entryTime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td>活动费用：</td>
			<td>
				<s:if test="activitySupplierEntry.chargeType==1">
					免费
				</s:if>
				<s:if test="activitySupplierEntry.chargeType==2">
					<s:property value="activitySupplierEntry.fixedCharge" />
				</s:if>
				<s:elseif test="activitySupplierEntry.chargeType==3">
					按推广商品数量收费(<s:property value="activitySupplierEntry.singleCharge" />/SKU)
				</s:elseif>
				<s:elseif test="activitySupplierEntry.chargeType==4">
					按推广佣金比例收费(不低于活动价的<fmt:formatNumber value="${activitySupplierEntry.commissionRate*100}" pattern="#0.00" />%)
				</s:elseif>
			</td>
		</tr>
		<tr>
			<td>是否缴费：</td>
			<td>
				<input type="hidden" id="activityPaymentStatus"  value="<s:property value='activitySupplierEntry.activityPaymentStatus'/>"/>
				<s:if test="activitySupplierEntry.activityPaymentStatus==2"  >
					是
				</s:if>
				<s:else>
					否
				</s:else>
			</td>
		</tr>
		<tr>
			<td>审核状态：</td>
			<td>
				<input type="hidden" id="strAuditStatus"  value="<s:property value='activitySupplierEntry.auditStatus'/>"/>
				<s:iterator value="#request.auditStatusMap"  >
				  <s:if test="activitySupplierEntry.auditStatus==key"  >
				  	<s:property  value="value"   />  
				  </s:if>
				</s:iterator>
			</td>
		</tr>
		<div id="auditReason" style="display: none;">
			<textarea id="reasonArea" rows="10" cols="100" style="resize: none;" ></textarea>
		</div>
	</table>
	<!-- 数据列表区域 -->
	活动商品：
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<!-- 促销推广 -->
		<s:if test="activitySupplierEntry.activityType == 1">
			<tr>
				<th align="center" width="">商品标题</th>
				<th align="center" width="">SKU</th>
				<th align="center" width="">品牌名称</th>
				<th align="center" width="">单价</th>
				<th align="center" width="">库存</th>
				<th align="center">促销活动ID</th>
				<th align="center">标记为审核不通过</th>
			</tr>
			<s:iterator value="page.dataList" status="index">
				<tr>
					<td align="center" width="">
						<s:property value="productTitle"/>
					</td>
					<td align="center">
						<s:property value="productSkuCode"/>
					</td>
					<td align="center">
						<s:property value="brandName"/>
					</td>
					<td align="center">
						<fmt:formatNumber value="${price}" pattern="#0.00" />
					</td>
					<td align="center">
						<s:property value="maxStock"/>
					</td>
					<td align="center">
					<s:if test="promotionId == null">
						
					</s:if>
					<s:else>
						<s:property value="promotionId"/>&nbsp;&nbsp;<a href="javaScript:selectPromotion(<s:property value="promotionId"/>);">查看</a>
					</s:else>
					</td>
					<td align="center">
						<s:if test="auditStatus == 2">
							<input type="checkbox" name="activitySkuId" checked="checked" disabled value='<s:property value="activitySkuId" />'/>
						</s:if>
						<s:else>
							<input type="checkbox" name="activitySkuId" value='<s:property value="activitySkuId" />'/>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</s:if>
		<!-- 图文推广 -->
		<s:elseif test="activitySupplierEntry.activityType == 2">
			<tr>
				<th align="center" width="">商品标题</th>
				<th align="center" width="">SKU</th>
				<th align="center" width="">品牌名称</th>
				<th align="center" width="">单价</th>
				<th align="center" width="">库存</th>
				<th align="center">已上传图片</th>
				<th align="center">标记为审核不通过</th>
			</tr>
			<s:iterator value="page.dataList" status="index">
				<tr>
					<td align="center" width="">
						<s:property value="productTitle"/>
					</td>
					<td align="center">
						<s:property value="productSkuCode"/>
					</td>
					<td align="center">
						<s:property value="brandName"/>
					</td>
					<td align="center">
						<fmt:formatNumber value="${price}" pattern="#0.00" />
					</td>
					<td align="center">
						<s:property value="maxStock"/>
					</td>
					<td align="center">
					<s:if test="activitySkuImage == null">
						
					</s:if>
					<s:else>
						<a href="<s:property value="imagePath"/><s:property value="activitySkuImage"/>" target="_blank">查看图片</a>
					</s:else>
					</td>
					<td align="center">
						<s:if test="auditStatus == 2">
							<input type="checkbox" name="activitySkuId" checked="checked" disabled value='<s:property value="activitySkuId" />'>
						</s:if>
						<s:else>
							<input type="checkbox" name="activitySkuId" value='<s:property value="activitySkuId" />'>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</s:elseif>
		<!-- 渠道推广 -->
		<s:elseif test="activitySupplierEntry.activityType == 3">
			<tr>
				<th align="center" width="">商品标题</th>
				<th align="center" width="">SKU</th>
				<th align="center" width="">品牌名称</th>
				<th align="center" width="">单价</th>
				<th align="center" width="">库存</th>
				<th align="center">活动价</th>
				<th align="center">推广佣金（%）</th>
				<th align="center">预销数量</th>
				<th align="center">标记为审核不通过</th>
			</tr>
			<s:iterator value="page.dataList" status="index">
				<tr>
					<td align="center" width="">
						<s:property value="productTitle"/>
					</td>
					<td align="center">
						<s:property value="productSkuCode"/>
					</td>
					<td align="center">
						<s:property value="brandName"/>
					</td>
					<td align="center">
						<fmt:formatNumber value="${price}" pattern="#0.00" />
					</td>
					<td align="center">
						<s:property value="maxStock"/>
					</td>
					<td align="center">
						<input type="hidden" name="activitySkuList[<s:property value="#index.index" />].activityPrice" value='<s:property value="activityPrice" />'/>
						<fmt:formatNumber value="${activityPrice}" pattern="#0.00" />
					</td>
					<td align="center">
						<s:property value="commissionRate*100"/>%
					</td>
					<td align="center">
					<input type="hidden" name="activitySkuList[<s:property value="#index.index" />].preSaleQuantity" value='<s:property value="preSaleQuantity" />'/>
						<s:property value="preSaleQuantity"/>
					</td>
					<td align="center">
						<input type="hidden" name="activitySkuList[<s:property value="#index.index" />].productSkuId"  value='<s:property value="productSkuId" />'/>
						<s:if test="auditStatus == 2">
							<input type="checkbox" name="activitySkuId" checked="checked" disabled value='<s:property value="activitySkuId" />'>
						</s:if>
						<s:else>
							<input type="checkbox" name="activitySkuId" value='<s:property value="activitySkuId" />'>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</s:elseif>
	</table>
	</br>
	</br>
	<input type="hidden" id="activityStartTime"   value="<fmt:formatDate value='${activitySupplierEntry.createStartTime}' pattern='yyyy-MM-dd HH:mm:ss' />"/>
	<div style="text-align:center;" id="hid">
		<input class="btnStyle" id="btnStylePass" type="button" onclick="activityAuditEntry(<s:property value="activitySupplierEntry.activityId" />,<s:property value="activitySupplierEntry.supplierEntryId" />,1)" value="审核通过">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btnStyle" id="btnStyleNoPass" type="button" onclick="activityAuditEntry(<s:property value="activitySupplierEntry.activityId" />,<s:property value="activitySupplierEntry.supplierEntryId" />,2)" value="不通过">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btnStyle" id="returnB" type="button" onclick="returnBack(<s:property value="activitySupplierEntry.activityId" />)" value="返回">
	</div>
	<br>
</s:form>
<script type="text/javascript">
	hideButton();
</script>
</html>
