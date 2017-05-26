function saveParentPage(){
	if ($("input:checked").length == 0) {
		alert("未勾选类目");
		return false;
	} 
	var parent_categoryIds = new Array();
	var obj = parent.document.getElementsByName("categoryIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_categoryIds.push(obj[i].value);
	}
	var sizeI = parent_categoryIds.length;
	var params = new Array();
	$("input[type='checkbox'][name='categoryId']:checked").each(function(i){
		var str = $(this).val().split(',');
		var categoryId = str[0];
		var categoryName = str[1];
		for(var i=0;i<sizeI;i++){
			if(parent_categoryIds[i]==categoryId){
				//alert('重复，不能添加!');
				return;
			}
		}
		params.push(str);
	});
	parent.closeOpenCategory(params);
}

function checkedBox(){
	var parent_categoryIds = new Array();
	//var obj = parent.document.getElementsByName("productIds");
	var obj = parent.document.getElementsByName("categoryIds");
	var size = obj.length;
	for(var i=0;i<size;i++){
		parent_categoryIds.push(obj[i].value);
	}
	var sizeI = parent_categoryIds.length;
	$("input[type='checkbox']").each(function(i){
		var str = $(this).val().split(',');
		var categoryId = str[0];
		for(var i=0;i<sizeI;i++){
			if(parent_categoryIds[i]==categoryId){
				$(this).attr("checked","checked");
			}
		}
	});
}