
var havechoosed="";
//初始化选择商品
$(document).ready(function() {
	var strs= new Array(); 
	//获取iframe父类的对象：已选择的商品
	var choosedPro = window.parent.document.getElementById("productContent").getElementsByTagName("span");
	for ( var i = 0; i < choosedPro.length; i++) {
		havechoosed = havechoosed + choosedPro[i].innerHTML + ",";
	}
	
	//获取之前选择的商品		
	//存在有选择的商品默认选择
	if(havechoosed){
		//获取选择的编码数组
		strs=havechoosed.split(",");
		$(".j_productSkuId").each(function(){
			for (var int = 0; int < strs.length; int++) {
				if($(this).val()==strs[int]){
					$(this).attr("checked",true);
				}
			}
		});
	}	
	$(".j_productSkuId").click(function () {
		var _this = $(this);		
		var strs= new Array(); 
		strs=havechoosed.split(",");			
		for (var int = 0; int < strs.length; int++) {
			if(_this.val()==strs[int] && !_this.attr("checked")){
				havechoosed = havechoosed.replace(_this.val()+",","");
			}
		}		
	});

});

//选择商品
function chooseProduct(){
	if(!checkIdSeled()){
 		 alert('请选择选商品！');
 	}else if(!checkProductNo()){
 		 alert("预售活动只能选择一个商品!");
 	}else{
 		 if (confirm("您确定要选择这些商品吗？")){
 			 parent.closeThis();
		 $.ajax({
			type: "post",
			async:'false',
			url: "/presell/getPresellProductReturnTable.action",
			dataType : "json",
			data:{"productArry":select_code},
			success: function(data){
				window.$=parent.window.$;
				$("#productContent").html("");
				$("#productContent").append(data);
			},
			error: function(e){
				//请求出错处理
				alert('出错了');
			}
		});
	   }
 	}
}

 var select_code="";
 
 //判断选择的产品是否为同一商品
 function checkProductNo(){
	 var initProductNo="";
	 var selectProductNo="";
	 var rstFlag=true;
	 $("input[name=productSkuId]").each(function(index){
		 if($(this).attr("checked")){
			 selectProductNo=$("input[name=productNo]").eq(index).val();
			 if(initProductNo==""){
				 initProductNo=selectProductNo;
			 }else{
				 if(initProductNo != selectProductNo){
					 rstFlag=false;
					 return false;
				 }
			 }
		 }
	 });
	 return rstFlag;
 }
 
 //判断是否选择
function checkIdSeled(){
	var r=false;
	var  b = "";
	$("input[name=productSkuId]").each(function(){
		if( $(this).attr("checked") ){
			r=true;
		 	b=b+$(this).attr("value")+",";
			return ;
		}});
	select_code=b;
	return r;
}
    
