$(function(){
	checkedBox();
});

function saveInviteSuppliers(){
	var params=new Array();
	var disSuppliers=new Array();
	$("input[type='checkbox'][name='supplierId']:disabled").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		disSuppliers.push(supplierId);
	});
	$("input[type='checkbox'][name='supplierId']:checked").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		var flag=true;
		for(var i=0;i<disSuppliers.length;i++){
			if(disSuppliers[i]==supplierId){
				flag=false;
				break;
			}
		}
		if(flag){
			params.push(supplierId);
		}
		
	})
	if(params.length<1){
		alert("未勾选关联的产品");
		return false;
	}
	parent.closeOpenAndSaveSupplier(params);
}

function checkedBox(){
	var supplierIds = new Array();
	var obj = parent.document.getElementById("invitedSuppliers").value.split(',');
	for(var i=0;i<obj.length;i++){
		supplierIds.push(obj[i]);
	}
	var sizeI = supplierIds.length;
	$("input[type='checkbox']").each(function(i){
		var str = $(this).val().split(',');
		var supplierId = str[0];
		for(var i=0;i<sizeI;i++){
			if(supplierIds[i]==supplierId){
				$(this).attr("checked","checked");
				$(this).attr("disabled","disabled");
			}
		}
	});
}



