/*jslint  browser: true, white: true, plusplus: true */
/*global $, countries */

$(function () {
	
	var suppliersArray;
	$.ajax({
		async:'false',
		url:'/cms/pagePublish_queryShopMainList.action',
		success:function(data){
		//   data=62:xxx;
		   console.log(data);
			suppliersArray = $.map(data, function (value,key) { 
				return { value:value, data: key };
				
				
				//value, key
				// return { value: value, data: key
				
			});
		//	product_add_suppliersArray = suppliersArray;
			$('#autocomplete_forSuppliers').autocomplete({
		    	lookup: suppliersArray,
		        minChars: 0
		    });
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
			//alert("aaa:"+XMLHttpRequest+"...."+textStatus+"...."+errorThrown);
			},
		dataType:'json'
	});
	
});