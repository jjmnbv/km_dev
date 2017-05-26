//所选产品的最小预售库存数
var minStock =0;
$(document).ready(function(){
	//初始化每人最多限购，如果为0表示不限购
	if($('#byLimit').val()!='' && $('#byLimit').val()==0){
		$('#byLimit').val('');
		$("input[name='byLimitType']").eq(1).attr("checked","checked");
		$("#byLimit").attr("disabled",true);
	}
	//修改限购数量radio
	$(".byLimity_type_class").change(function(){
		if($(this).val()=="1" && $(this).attr("checked")){
			$("#byLimit").attr("disabled",false);
		}
		if($(this).val()=="2" && $(this).attr("checked")){
			$("#byLimit").val("");
			$("#byLimit").attr("disabled",true);
		}
	});
	
	//获取所选产品的最小预售库存数
	$('.presell_Stock').each(function(index){
		var stock = $(this).text();
		if(index==0){
			minStock=stock;
		}else{
			preStock=$('.presell_Stock').eq(index-1).text();
			if(preStock>stock){
				minStock=stock;
			}
		}
	});
	
});

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

//提交表格参数
var options = {
	dataType : 'json',
	beforeSubmit: validateForm,
	success : createsuccess
};
//校验表格
function validateForm(){
	$("#submitPresellRuleId").attr("disabled","disabled");
	//校验限购数
	var byLimit=$("input[name='byLimitType']:checked").val();
	var byLimitVal=$.trim($("#byLimit").val());
	if(byLimit=="1" && byLimitVal==""){
		alert("每人最多购买限制必填");
//		showErrorMessage("byLimit","每人最多购买限制必填","byLimitTypeFont");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(parseInt(byLimitVal)>parseInt(minStock)){
		//限购数为大于0小于预售库存的整数
		alert("限购数为小于预售库存的整数");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	//定金支付截止时间,必须大于定金支付开始时间
	//尾款支付开始时间必须大于定金支付截止时间，同时尾款支付截止时间必须大于尾款支付开始时间
	//发货开始时间大于等于尾款支付开始时间
	var depositStartTime=$("#depositStartTime").val();
	var depositEndTime=$("#depositEndTime").val()
	var finalpayStartTime=$("#finalpayStartTime").val()
	var finalpayEndTime=$("#finalpayEndTime").val()
	var deliveryStartTime=$("#deliveryStartTime").val()
	var deliveryEndTime=$("#deliveryEndTime").val()
	if(depositStartTime==""){
//		showErrorMessage("depositStartTime","定金支付开始时间必填","depositEndTime");
		alert("定金支付开始时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(depositEndTime==""){
//		showErrorMessage("depositEndTime","定金支付截止时间必填","depositEndTime");
		alert("定金支付截止时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(!compareDate(depositStartTime,depositEndTime)){
//		showErrorMessage("depositTimeCompare","定金支付截止时间必填","depositEndTime");
		alert("定金支付截止时间必须大于定金支付开始时间");
		$("#submitPresellRule").removeAttr("disabled");
		return false;
	}
	if(finalpayStartTime==""){
//		showErrorMessage("finalpayStartTime","尾款支付开始时间必填","finalpayEndTime");
		alert("尾款支付开始时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(!compareDate(depositEndTime,finalpayStartTime)){
//		showErrorMessage("depositTimeCompare","定金支付截止时间必填","depositEndTime");
		alert("尾款支付开始时间必须大于定金支付截止时间");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(finalpayEndTime==""){
//		showErrorMessage("finalpayEndTime","尾款支付截止时间必填","finalpayEndTime");
		alert("尾款支付截止时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(!compareDate(finalpayStartTime,finalpayEndTime)){
//		showErrorMessage("depositTimeCompare","定金支付截止时间必填","depositEndTime");
		alert("尾款支付截止时间必须大于尾款支付开始时间");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(deliveryStartTime==""){
//		showErrorMessage("deliveryStartTime","发货开始时间必填","deliveryEndTime");
		alert("发货开始时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(deliveryEndTime==""){
//		showErrorMessage("deliveryEndTime","发货截止时间必填","deliveryEndTime");
		alert("发货截止时间必填");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(!compareDate(deliveryStartTime,deliveryEndTime)){
//		showErrorMessage("depositTimeCompare","定金支付截止时间必填","depositEndTime");
		alert("发货截止时间必须大于发货开始时间");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	if(!compareDate(finalpayStartTime,deliveryStartTime)){
//		showErrorMessage("depositTimeCompare","定金支付截止时间必填","depositEndTime");
		alert("发货开始时间大于等于尾款支付开始时间");
		$("#submitPresellRuleId").removeAttr("disabled");
		return false;
	}
	
}

function createsuccess(data){
	$("#submitPresellRuleId").removeAttr("disabled");
	alert(data.msg);
	if (data.flag == true) {
		location.href="/presell/queryPresellManagerList.action?flag=0";
	}
}
//保存预售信息
function submitPresellRule(){
	$("#presellRuleUpdateForm").ajaxSubmit(options);
}
//时间比较方法
function compareDate(starttime,endtime){
	var start = new Date(starttime.replace("-", "/").replace("-", "/"));
	var end = new Date(endtime.replace("-", "/").replace("-", "/"));
	if (end < start) {
		return false;
	}else{
		return true;
	}
}
//数据浮点类型
function checkInputIntFloat(oInput)
{
    if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}

function checkInputInt(oInput){
	if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}

function goBack(){
	location.href="/presell/queryPresellManagerList.action?flag=0";
}
