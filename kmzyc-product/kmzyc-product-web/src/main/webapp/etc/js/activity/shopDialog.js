function shopSearch(){
		$('#frm').attr("action","/supplierActivity/shopSelect.action");
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}

function saveParentPage(){
	if ($("input:checked").length == 0) {
		alert("未勾选商家");
		return false;
	} 
	var parent_supplierIds = new Array();
	var obj = parent.document.getElementsByName("supplierIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_supplierIds.push(obj[i].value);
	}
	var sizeI = parent_supplierIds.length;
	var params = new Array();
	$("input[type='checkbox'][name='supplierId']:checked").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		var supplierName = str[1];
		for(var i=0;i<sizeI;i++){
			if(parent_supplierIds[i]==supplierId){
				//alert('重复，不能添加!');
				return;
			}
		}
		params.push(str);
	});
	parent.closeOpenSupplier(params);
}

function checkedBox(){
	var parent_supplierIds = new Array();
	//var obj = parent.document.getElementsByName("productIds");
	var obj = parent.document.getElementsByName("supplierIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_supplierIds.push(obj[i].value);
	}
	var sizeI = parent_supplierIds.length;
	$("input[type='checkbox']").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		for(var i=0;i<sizeI;i++){
			if(parent_supplierIds[i]==supplierId){
				$(this).attr("checked","checked");
			}
		}
	});
}



