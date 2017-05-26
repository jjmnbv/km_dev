/**
 * 跳转到活动商家报名详情页面
 * @return
 */
function querySupplierEntryDeatil(activityId,supplierEntryId) {
	location.href = '/supplierActivity/querySupplierEntryDeatil.action?activitySupplierEntry.activityId='+activityId+"&activitySupplierEntry.supplierEntryId="+supplierEntryId;
}