$(document).ready(function(){
	var type= $(':radio[name="selectSupplierType"][checked]').val();
	supplierTypeChange(type);
//	$("#frm").validate({});
});

//选择商家类型
function supplierTypeChange(shopSort){
	if(shopSort==1){
		$('#showSuppliers').html('');
		$("#shopSort").val("1");
		$("#supplierId").val("");
		//清除商品
		$("#productContent").html('');
		$("#selectEm").attr("disabled","disabled");
	}else{
		$('#showSuppliers').html('');
		$("#shopSort").val("2");
		$("#supplierId").val("");
		//清除商品
		$("#productContent").html('');
		$("#selectEm").removeAttr("disabled");
	}
}

//选择入驻商家，弹出商家页面
function selectSupplier(){
	var supplierId = $("#supplierId").val();
	supplierId = supplierId?supplierId:"";
	dialog("选择商家","iframe:/common/selectNewProductShop.action?shopCodes="+supplierId,"800px","600px","iframe");
}
//选择商家页面的回调函数
function receiveProductShop(o){
	if(o.length<=0){
		alert("请选择一个商家");
		return;
	}
	var showHtml =$("#showSuppliers").html();
	var supplierId = $("#supplierId").val()||"";
	for(i=0;i<o.length;i++){
		var id = o[i].value;//o[i].attr("value");
		if(supplierId.indexOf(id)>=0){
			continue;
		}
		var name = o[i].title;
		supplierId = id;
		var html = '<div class="sbDiv"><em unselectable="on">'+name+'</em>'+
		 '<a class="deleteP" hidefocus="hidefocus" data-value="'+id+
		 '" onclick="javascript:delName(this);">x</a></div>';
		showHtml = html;	
	}
	$("#supplierId").val(supplierId);
	$("#showSuppliers").html(showHtml);
	closeThis();
}
//删除所选的入驻商家
function delName(o){
	$("#supplierId").val("");
	$(o).parent().remove();
	//清除商品
	$("#productContent").html('');
}

/**
 * 显示错误信息，在校验的字段后面
 * @param checkId  需校验的字段
 * @param title  提示的title内容
 * @param afterId  错误提示信息显示哪个节点的后面
 * @returns {Boolean}
 */
function showErrorMessage(checkId,title,afterId){
	var t = $("#"+checkId).val();
	if(t){
		$("#error"+checkId).remove();
		return true;
	}
	showError(checkId,title,afterId);
	return false;
}

function showError(errorId,title,afterId){
	if($("#error"+errorId).length>0){
		return;
	}
	var html = '<label for="error" id="error'+errorId+'" generated="true" class="error">'+title+'</label>';
	//console.log(html);
	var id = "#"+afterId;
	//console.log(id);
	$(id).after(html);
}

/**  进入选择预售产品页面  **/
function gotoAddProduct(){
	var channel = $("#channel").val();
	//商家Id
	var supplierId = $("#supplierId").val();
	//商家类别
	var shopSort = $("#shopSort").val();
	if(shopSort==2 && $("#supplierId").val()==""){
		alert("请选择商家！");
		return;
	}
	if(shopSort==1){
		dialog("查看所有SKU商品","iframe:/presell/findProductSkuForPresell.action?productSku.channel="+
				channel+"&productSku.product.suppliterType="+shopSort ,"800px","600px","iframe",50);
	}else{
		dialog("查看所有SKU商品","iframe:/presell/findProductSkuForPresell.action?productSku.channel="+
				channel+"&productSku.product.shopCode="+supplierId+"&productSku.product.suppliterType="+shopSort ,"800px","600px","iframe",50);
	}
}

//提交表格参数
var options = {
	dataType : 'json',
	beforeSubmit: validateForm,
	success : createsuccess
};
//校验表格
function validateForm(){
	$("#submitPresellPro").attr("disabled","disabled");
	if($.trim($("#presellTitle").val())==""){
		alert("预售标题必填");
		$("#submitPresellPro").removeAttr("disabled");
		return false;
	}
	if($("#productContent").html()==""){
		alert("预售商品必填");
		$("#submitPresellPro").removeAttr("disabled");
		return false;
	}
	var tipFlag=true;
	var resultFlag=true
	//校验预售价格,预售价必须小于等于商品原价
	$(".presell_price").each(function(index){
		var presellPrice=$(this).val();
		if(presellPrice==""){
			$(this).focus();
			alert("预售价必填！");
			resultFlag =false;
			$("#submitPresellPro").removeAttr("disabled");
			return false;
		}
		if(presellPrice.indexOf(".")>0 && presellPrice.substring(presellPrice.indexOf(".")+1,presellPrice.length).length > 2 || presellPrice==0){
			$(this).focus();
			alert("请输入大于0的预售价格,小数最多为两位");
			resultFlag =false;
			$("#submitPresellPro").removeAttr("disabled");
			return false;
		}
		var price= $(".price_class").eq(index).text();
		if(parseFloat(presellPrice) > parseFloat(price)){
			$(this).focus();
			alert("预售价必须小于等于商品原价");
			resultFlag =false;
			$("#submitPresellPro").removeAttr("disabled");
			return false;
		}
	});
	
	if(resultFlag){
		//定金大于0小于预售价，最高不得超过商品原价的20%，支持两位小数
		$(".deposit_price").each(function(index){
			var depositPrice=$(this).val();
			if(depositPrice==""){
				$(this).focus();
				alert("定金必填！");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
			if(depositPrice.indexOf(".")>0 && depositPrice.substring(depositPrice.indexOf(".")+1,depositPrice.length).length > 2 || depositPrice==0){
				$(this).focus();
				alert("请输入大于0的定金,小数最多为两位");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
			//预售价
			var presellPrice= $(".presell_price").eq(index).val();
			if(parseFloat(depositPrice) >= parseFloat(presellPrice)){
				$(this).focus();
				alert("定金小于预售价");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
			//原价
			var price= $(".price_class").eq(index).text();
			if(parseFloat(depositPrice) > parseFloat(price*0.2)){
				$(this).focus();
				alert("定金不得超过商品原价的20%");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
		});
	}
	if(resultFlag){
		//预售库存必须大于0小于等于99999
		$(".presell_stock").each(function(){
			var stock=$(this).val();
			if(stock==""){
				$(this).focus();
				alert("预售库存必填！");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
			if(parseInt(stock)<=0||parseInt(stock)>99999){
				$(this).focus();
				alert("预售库存必须大于0小于等于99999！");
				resultFlag =false;
				$("#submitPresellPro").removeAttr("disabled");
				return false;
			}
		});
	}
	return resultFlag;
}
function createsuccess(data){
	$("#submitPresellPro").removeAttr("disabled");
	alert(data.msg);
	if (data.flag == true) {
		location.href="/presell/toUpdatePresellRule.action?promotionPresell.presellId="+data.presellId;
	}
}
//保存预售信息
function submitPresell(){
	$("#presellProductAddForm").ajaxSubmit(options);
}

function checkInputIntFloat(oInput)
{
    if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}

function checkInputInt(oInput){
	if('' != oInput.value.replace(/[1-9][0-9]*/,''))
    {
        oInput.value = oInput.value.match(/[1-9][0-9]*/) == null ? '' :oInput.value.match(/[1-9][0-9]*/);
    }
}

function goBack(){
	location.href="/presell/queryPresellManagerList.action?flag=0";
}
