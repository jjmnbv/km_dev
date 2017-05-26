  $(document).ready(function(){
          $("#frm").validate({
               rules: {
					"product.brandId":{required:true},
					"product.name":{required:true,maxlength:128,unusualChar:true},
					"product.channel":{required:true},
					"product.drugCateCode":{required:true}
					//"product.approvalType":{required:true}
	        	},
	           success: function (label){
	            label.removeClass("checked").addClass("checked");
	           }
          });
    });