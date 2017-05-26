/**
 * 按指定类型（类目、供应商）查询
 * 
 * @return
 */
function addDialog(type) {
	var checkedId = null;
	if (type == 0) {// 按类目查询
		var url = "/supplierActivity/categorysSelect.action";// ?checkedId="+
																// checkedId;
		myDialog = art.dialog.open(url, {
			title : '选择指定类目',
			width : 750,
			height : 500,
			drag : false,
			close : function() {
				$.unblockUI();
			}
		});
		$.blockUI.defaults.overlayCSS.opacity = '0.5';
		$.blockUI( {
			message : ""
		});
		$.blockUI.defaults.overlayCSS.opacity = '0.5';
		$.blockUI( {
			message : ""
		});
	} else {// 按商家查询
		var url = "/supplierActivity/shopSelect.action";// ?checkedId="+
														// checkedId;
		myDialog = art.dialog.open(url, {
			title : '选择供应商',
			width : 900,
			height : 500,
			drag : false,
			close : function() {
				$.unblockUI();
			}
		});
		$.blockUI.defaults.overlayCSS.opacity = '0.5';
		$.blockUI( {
			message : ""
		});
	}
}