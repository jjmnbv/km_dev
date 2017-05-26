
$(document).ready(function() {
	var supplier_shop_select_array="abc";

	var dataArray="";
	$.ajax({
		async:'false',
		url:'/app/findAllSupplierB2BShop.action',
		success:function(data){
			dataArray = $.map(data, function (value, key) { return { value: value, data: key }; });
			supplier_shop_select_array = dataArray;
			//alert(supplier_shop_select_array);
			$('#autocomplete_forShop').autocomplete({
		    	lookup: dataArray,
		        minChars: 0
		    });
		},
		dataType:'json'
	});
	
	
	
	$("#queryBtn").click(function(){
		var flag=true;
		var shopId = $("#autocomplete_forShop").val();
		
		if(shopId==""){
			alert("请您选择需要查看的商家!");
			return false;
		}
		
		//alert(shopId);
		$.each(supplier_shop_select_array,function(i,value){
			if(shopId == value.value){
				//alert(value.value+",data="+value.data);
				$("#shopId").val(value.data);
				$("#supplierName").val(value.value);			
				flag = false;
			}
		});
		
		if(flag){
			alert("没有您输入的【"+shopId+"】商家");
			return false;
		}
		//alert("选中的shopId="+shopId)		
		$('#frm').attr("action","/app/queryShopBrowseList.action");
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	});
	
	
});



