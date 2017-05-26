function brandSearch(){
		$('#frm').attr("action","/supplierActivity/brandSelect.action");
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}
function saveParentPage(){
	if ($("input:checked").length == 0) {
		alert("未勾选品牌！");
		return false;
	} 
	var parent_brandIds = new Array();
	var obj = parent.document.getElementsByName("brandIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_brandIds.push(obj[i].value);
	}
	var sizeI = parent_brandIds.length;
	var params = new Array();
	$("input[type='checkbox'][name='supplierId']:checked").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		var supplierName = str[1];
		for(var i=0;i<sizeI;i++){
			if(parent_brandIds[i]==supplierId){
				//alert('重复，不能添加!');
				return;
			}
		}
		params.push(str);
	});
	parent.closeOpenBrand(params);
}

function checkedBox(){
	var parent_brandIds = new Array();
	//var obj = parent.document.getElementsByName("productIds");
	var obj = parent.document.getElementsByName("brandIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_brandIds.push(obj[i].value);
	}
	var sizeI = parent_brandIds.length;
	$("input[type='checkbox']").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		for(var i=0;i<sizeI;i++){
			if(parent_brandIds[i]==supplierId){
				$(this).attr("checked","checked");
			}
		}
	});
}



