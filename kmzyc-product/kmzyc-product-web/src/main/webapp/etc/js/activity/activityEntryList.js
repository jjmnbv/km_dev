function doSearch(){
		$('#frm').attr("action","/supplierActivity/activityEntryList.action");
		document.getElementById('pageNo').value = 1;
		$('#frm').submit();
	}

/**
 * 跳转到活动商家报名列表页面
 * @return
 */
function querySupplierEntryList(activityId) {
	location.href = '/supplierActivity/querySupplierEntryList.action?activitySupplierEntry.activityId='+activityId;
}

/**
 * 跳转到活动商家报名产品列表页面
 * @return
 */
function querySupplierProductList(activityId) {
	location.href = '/supplierActivity/querySupplierProductList.action?activitySku.activityId='+activityId;
}