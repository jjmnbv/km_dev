/**
 * 跳转到活动商家报名详情页面
 * @return
 */
function querySupplierEntryDeatil(activityId,supplierEntryId) {
	location.href = '/supplierActivity/querySupplierEntryDeatil.action?activitySupplierEntry.activityId='+activityId+"&activitySupplierEntry.supplierEntryId="+supplierEntryId;
}
/**
 * 跳转到活动商家报名列表页面
 * @return
 */
function exportActivitySupplierProduct() {
	var url="/supplierActivity/exportActivitySupplierProductList.action";
	$("#frm").attr("action", url);
	$("#frm").submit();
	var url="/supplierActivity/querySupplierProductList.action";
	$("#frm").attr("action", url);
}