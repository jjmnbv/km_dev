function doSearch() {
	$('#frm').attr("action", "/supplierActivity/findPromotionEffectList.action");
	document.getElementById('pageNo').value = 1;
	$('#frm').submit();
}

/**
 * 跳转到活动商家销量列表页面
 * 
 * @return
 */
function querySupplierSalesList(activityId, activityType) {
	location.href = '/supplierActivity/findActivitySupplierSalesList.action?supplierEntryParam.activityId='
			+ activityId + "&supplierEntryParam.activityType=" + activityType;
}

/**
 * 跳转到销量统计页面
 * 
 * @param activityId
 * @param activityType
 * @param supplierEntryId
 */
function queryPromotionGoodsList(activityId, supplierEntryId, activityType) {
	// 活动类型1：促销推广2：图文推广3：渠道推广
	if (activityType == 1) {
		location.href = '/supplierActivity/findPromotionActivitySupplierSales.action?activitySkuParam.activityId='
				+ activityId
				+ '&activitySkuParam.supplierEntryId='
				+ supplierEntryId
				+ '&activityType='
				+ activityType;
	} else if (activityType == 2) {
		location.href = '/supplierActivity/findTeletextActivitySupplierSales.action?activitySkuParam.activityId='
				+ activityId
				+ '&activitySkuParam.supplierEntryId='
				+ supplierEntryId
				+ '&activityType='
				+ activityType;
	} else if (activityType == 3) {
		location.href = '/supplierActivity/findChannelSupplierProductSalesList.action?activitySkuParam.activityId='
				+ activityId
				+ '&activitySkuParam.supplierEntryId='
				+ supplierEntryId
				+ '&activityType='
				+ activityType;
	}
}

/**
 * 跳转到活动商家销量明细页面
 * 
 * @return
 */
function querySkuSalesDetail(activityId,supplierEntryId, skuId,activityType) {
	location.href = '/supplierActivity/findActivitySkuSalesDetail.action?supplierEntryId='
			+ supplierEntryId + "&skuId=" + skuId
			+ "&activityId=" + activityId
			+ "&activityType=" + activityType
			;
}

/**
 * 跳转到追加推广明细页面
 * 
 * @param activityId
 * @param supplierEntryId
 */
function queryAppendProductDetail(activityId, supplierEntryId, skuId,activityType) {
	location.href = '/supplierActivity/findSupplierAppendProductList.action?activitySkuParam.activityId='
			+ activityId
			+ '&activitySkuParam.supplierEntryId=' + supplierEntryId 
			+ '&activitySkuParam.productSkuId=' + skuId
			+ '&activityType=' + activityType
			;
}

/**
 * 跳转到订单详情页面
 * 
 * @return
 */
function queryOrderDetail(orderCode) {
	location.href = '/supplierActivity/findOrderDetail.action?orderMainParam.orderCode='
			+ orderCode;
}

/**
 * 查看订单详情
 * @param orderCode
 */
function getOrderDetail(orderCode) {
	location.href = '/order/showOrderItemDetail.action?orderCode='
		+orderCode+"&_t="+new Date().getTime();
}


/**
 * 返回活动推广效果列表
 */
function goBackActivityPromotionList(){
	location.href = "/supplierActivity/findPromotionEffectList.action";
}

/**
 * 返回销量统计列表
 */
function goBackSupplierSalesList(activityId,activityType){
	location.href = '/supplierActivity/findActivitySupplierSalesList.action?supplierEntryParam.activityId='
		+ activityId + "&supplierEntryParam.activityType=" + activityType;
}
