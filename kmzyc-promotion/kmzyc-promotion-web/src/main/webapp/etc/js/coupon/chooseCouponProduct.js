
var havechoosed="";
//初始化选择商品
$(document).ready(function() {
	var strs= new Array(); 
	//获取iframe父类的对象：已选择的商品
	var choosedPro = window.parent.document.getElementById("table2").getElementsByTagName("span");
	for ( var i = 0; i < choosedPro.length; i++) {
		havechoosed = havechoosed + choosedPro[i].innerHTML + ",";
	}
	
	//获取之前选择的商品		
	//havechoosed = document.getElementById("haveChoosedPro").value;	
	//存在有选择的商品默认选择
	if(havechoosed){
		//获取选择的编码数组
		strs=havechoosed.split(",");
		$(".j_productSkuCode").each(function(){
			for (var int = 0; int < strs.length; int++) {
				if($(this).val()==strs[int]){
					$(this).attr("checked",true);
				}
			}
		});
	}	
	
	$(".j_productSkuCode").click(function () {
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
function coupproduct()
{
	 if(!checkIdSeled())
 		 {
 		 alert('请选择选商品！');
 		 }
 	 else
 		 {
   if (confirm("您确定要选择这些商品吗？"))
	   {
		
	 parent.closeThis();
	 $.ajax({
		type: "post",
		async:'false',
		url: "/coupon/getIdreturnTable.action",
		dataType : "json",
		data:{"productArry":select_name,"haveChoosedPro":havechoosed},
		success: function(data){
 		window.$ = window.parent.$;
		 $("#productContent").html("");
		 $("#editBody").html(""); 
		$("#productContent").append(data);
		},
		error: function(){
			//请求出错处理
			alert('出错了');
		}});
	   }}}

 var select_name="";
 var select_id="";
 
 //判断是否选择
function checkIdSeled(){
	var r=false;
	var  b = "";var c="";
	$("input[name=productSkuCode]").each(function(){
		if( $(this).attr("checked") ){
			r=true;
		 	b=b+$(this).attr("value")+",";
			c=c+$(this).attr("id")+",";
			return ;
		}});
	select_name=b;
	select_id=c;
	return r;
}
    
function doSearch()
{
	location.href="/app/chooseCouponProduct.action"; 
}
