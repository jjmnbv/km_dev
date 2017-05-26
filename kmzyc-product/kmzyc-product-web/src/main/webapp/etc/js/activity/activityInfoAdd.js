$(function() {
	$('#activityType').change(function() {
		var activityTypeVal = $('#activityType').val();
		elementHide(activityTypeVal);
		clearElementChecked();
	});
	
	
	$(".activityChargeTypeClass").change(function() {
		if($(this).val()=="2" && $(this).attr("checked")=="checked"){
			$('#fixedCharge').attr("disabled",false);
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
			$('#commissionRate').attr("disabled",true);
			$('#commissionRate').val("");
		}else if($(this).val()=="3" && $(this).attr("checked")=="checked"){
			$('#singleCharge').attr("disabled",false);
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#commissionRate').attr("disabled",true);
			$('#commissionRate').val("");
		}else if($(this).val()=="4" && $(this).attr("checked")=="checked"){
			$('#commissionRate').attr("disabled",false);
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
		}else{
			$('#commissionRate').attr("disabled",true);
			$('#commissionRate').val("");
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
		}
	});
	
	$("input[type='radio'][name='supplierChoiceType']").change(function() {
		$(document).find("input[type='hidden'][name='categoryIds']").remove();
		$(document).find("input[type='hidden'][name='supplierIds']").remove();
		$(document).find("span[name='categoryClick']").remove();
		$(document).find("span[name='supplierClick']").remove();
		if($(this).val()=="2" && $(this).attr("checked")=="checked"){
			$('#greatEqualPoint').attr("disabled",false);
			$('#choiceCategory').attr("disabled",true);
			$('#choiceSupplier').attr("disabled",true);
		}else if($(this).val()=="3" && $(this).attr("checked")=="checked"){
			$('#choiceCategory').attr("disabled",false);
			$('#greatEqualPoint').attr("disabled",true);
			$('#greatEqualPoint').val("");
			$('#choiceSupplier').attr("disabled",true);
		}else if($(this).val()=="4" && $(this).attr("checked")=="checked"){
			$('#choiceSupplier').attr("disabled",false);
			$('#choiceCategory').attr("disabled",true);
			$('#greatEqualPoint').attr("disabled",true);
			$('#greatEqualPoint').val("");
			
		}else{
			$('#greatEqualPoint').attr("disabled",true);
			$('#greatEqualPoint').val("");
			$('#choiceCategory').attr("disabled",true);
			$('#choiceSupplier').attr("disabled",true);
		}
	});
	
	$("input[type='radio'][name='brandChoiceType']").change(function() {
		$(document).find("input[type='hidden'][name='brandIds']").remove();
		$(document).find("span[name='brandClick']").remove();
		if($(this).val()=="0" && $(this).attr("checked")=="checked"){
			$('#choiceBrand').attr("disabled",true);
		}else{
			$('#choiceBrand').attr("disabled",false);
		}
	});
	
	$("input[type='radio'][name='supplierMaximumRadio']").change(function() {
		if($(this).val()=="0" && $(this).attr("checked")=="checked"){
			$('#supplierMaximum').val("");
			$('#supplierMaximum').attr("disabled",true);
		}else{
			$('#supplierMaximum').attr("disabled",false);
		}
	});
	
	$("input[type='radio'][name='skuMaximumRadio']").change(function() {
		if($(this).val()=="0" && $(this).attr("checked")=="checked"){
			$('#skuMaximum').val("");
			$('#skuMaximum').attr("disabled",true);
		}else{
			$('#skuMaximum').attr("disabled",false);
		}
	});
	
});

function checkInputIntFloat(oInput)
{
    if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
    }
}

var options = {
	dataType : 'json',
	beforeSubmit: validateForm,
	success : createsuccess
};

function compareDate(starttime,endtime){
	var start = new Date(starttime.replace("-", "/").replace("-", "/"));
	var end = new Date(endtime.replace("-", "/").replace("-", "/"));
	if (end < start) {
		return false;
	}else{
		return true;
	}
}

function validateForm(){
	
	if($("#activityName").val().trim()==""){
		$("#activityName").focus();
		alert("活动名称必填！");
		return false;
	}
	if($("#activityType").val()==""){
		$("#activityType").focus();
		alert("活动类型必填！");
		return false;
	}
	
	var activityChannlIsChecked = false;
	if($("#activityType").val()=="3"){
		$("input[type='checkbox'][name='activityChannls']:checked").each(function(){
			activityChannlIsChecked = $(this).attr("checked");
			if(activityChannlIsChecked){
				return false;
			}
		});
		if(!activityChannlIsChecked){
			alert("活动渠道必填！");
			return false;
		}
	}
	
	if($("input[type='radio'][name='activityChargeType']:checked").val()=="2"){
		var fixedChargeVal = $('#fixedCharge').val();
		if(fixedChargeVal==""){
			$("#fixedCharge").focus();
			alert("固定收费必填！");
			return false;
		}
		if(fixedChargeVal.indexOf(".")>0 && fixedChargeVal.substring(fixedChargeVal.indexOf(".")+1,fixedChargeVal.length).length > 2 || fixedChargeVal==0){
			$("#fixedCharge").focus();
			alert("请输入大于0的固定收费,小数最多为两位");
			return false;
		}
	}else if($("input[type='radio'][name='activityChargeType']:checked").val()=="3"){
		var singleChargeVal = $('#singleCharge').val();
		if(singleChargeVal==""){
			$("#singleCharge").focus();
			alert("单品收费必填！");
			return false;
		}
		if(singleChargeVal.indexOf(".")>0 && singleChargeVal.substring(singleChargeVal.indexOf(".")+1,singleChargeVal.length).length > 2 || singleChargeVal==0){
			$("#singleCharge").focus();
			alert("请输入大于0的单品收费,小数最多为两位");
			return false;
		}
		
	}else if($("input[type='radio'][name='activityChargeType']:checked").val()=="4"){
		var commissionRateVal = $('#commissionRate').val();
		if(commissionRateVal==""){
			$("#commissionRate").focus();
			alert("佣金比例必填！");
			return false;
		}
		if(commissionRateVal.indexOf(".")>0 && commissionRateVal.substring(commissionRateVal.indexOf(".")+1,commissionRateVal.length).length > 2 || commissionRateVal==0){
			$("#commissionRate").focus();
			alert("请输入大于0的佣金比例,小数最多为两位");
			return false;
		}
		
	}else{
		
	}
	
	if($("input[type='radio'][name='supplierChoiceType']:checked").val()=="2"){
		var greatEqualPointVal = $('#greatEqualPoint').val();
		if(greatEqualPointVal==""){
			$("#greatEqualPoint").focus();
			alert("店铺评分必填！");
			return false;
		}
		if(greatEqualPointVal==0 || greatEqualPointVal>5){
			alert("请输入0-5之间的店铺评分!");
			return false;
		}
	}else if($("input[type='radio'][name='supplierChoiceType']:checked").val()=="3"){
		if($("input[type='hidden'][name='categoryIds']").val()==undefined){
			$("#choiceCategory").focus();
			alert("指定经营类目必选！");
			return false;
		}
	}else if($("input[type='radio'][name='supplierChoiceType']:checked").val()=="4"){
		if($("input[type='hidden'][name='supplierIds']").val()==undefined){
			$("#choiceCategory").focus();
			alert("指定入驻商家必选！");
			return false;
		}
	}else{
		
	}
	
	if($("input[type='radio'][name='brandChoiceType']:checked").val()=="2"){
		if($("input[type='hidden'][name='brandIds']").val()==undefined){
			$("#choiceBrand").focus();
			alert("指定品牌必选！");
			return false;
		}
	}
	
	if($("input[type='radio'][name='supplierMaximumRadio']:checked").val()=="1"){
		var supplierMaximumVal = $('#supplierMaximum').val();
		if(supplierMaximumVal==""){
			$("#supplierMaximum").focus();
			alert("商家报名名额限制必填！");
			return false;
		}
		if(supplierMaximumVal.indexOf("0")==0){
			$("#supplierMaximum").focus();
			alert("请输入合法的商家报名名额限制!");
			return false;
		}
		if(supplierMaximumVal==0){
			$("#supplierMaximum").focus();
			alert("请输入大于0的商家报名名额限制!");
			return false;
		}
	}
	
	if($("input[type='radio'][name='skuMaximumRadio']:checked").val()=="1"){
		var skuMaximumVal = $('#skuMaximum').val();
		if(skuMaximumVal==""){
			$("#skuMaximum").focus();
			alert("活动商品数量限制必填！");
			return false;
		}
		if(skuMaximumVal.indexOf("0")==0){
			$("#skuMaximum").focus();
			alert("请输入合法的活动商品数量限制!");
			return false;
		}
		if(skuMaximumVal==0){
			$("#skuMaximum").focus();
			alert("请输入大于0的活动商品数量限制!");
			return false;
		}
	}
	
	
	if($("#entryStartTime").val()==""){
//		$("#entryStartTime").focus();
		alert("活动报名开始时间必填！");
		return false;
	}
	if($("#entryEndTime").val()==""){
//		$("#entryEndTime").focus();
		alert("活动报名截止时间必填！");
		return false;
	}
	
//	 var d = new Date(),dateStr = '';
//	 dateStr += d.getFullYear();
//	 dateStr += d.getMonth() + 1;
//	 dateStr += d.getDate();
//	 dateStr += d.getHours(); 
//	 dateStr += d.getMinutes(); 
//	 dateStr += d.getSeconds();
	
	var endtime = $('#entryStartTime').val();
//	 var starttime = dateStr;
	 var start = new Date();
	 var end = new Date(endtime.replace("-", "/").replace("-", "/"));
	 if (end < start) {
      alert('活动报名时间不能小于当前时间！');
      return false;
	 }
	
//	 endtime = $('#entryEndTime').val();
//	 starttime = $('#entryStartTime').val();
	 if (!compareDate($('#entryStartTime').val(),$('#entryEndTime').val())) {
        alert('活动报名结束时间不能小于开始时间！');
        return false;
	 }
	
	
	if($("#activityStartTime").val()==""){
//		$("#activityStartTime").focus();
		alert("活动开始时间必填！");
		return false;
	}
	if($("#activityEndTime").val()==""){
//		$("#activityEndTime").focus();
		alert("活动结束时间必填！");
		return false;
	}
	
	if (!compareDate($('#activityStartTime').val(),$('#activityEndTime').val())) {
	    alert('活动结束时间不能小于开始时间！');
	    return false;
	}
	 
	 if (!compareDate($('#entryEndTime').val(),$('#activityStartTime').val())) {
       alert('活动开始时间不能小于报名结束时间！');
       return false;
	 }
	 
	 var oldtime=Date.parse($('#entryEndTime').val());  
	 var newtime=Date.parse($('#activityStartTime').val());  
	 var diff=newtime-oldtime;
//	 var time = parseFloat(diff) / 1000;
//     if (null != time && "" != time) {
//        if (time >= 60 * 60 && time < 60 * 60 * 24) {
//            time = parseInt(time / 3600.0);
//            if(time<2){
//            	alert('报名结束时间与活动开始时间之间应大于2小时！');
//                return false;
//            }
//        }
//        else {
//        	alert('报名结束时间与活动开始时间之间应大于2小时！');
//            return false;
//        }
//     }
	 var time =2*60*60*1000; //2小时 
	 if(diff < time){  
         alert("报名结束时间与活动开始时间之间应大于2小时，请检查!");  
         return false;  
     } 
	 
	if($('#activitySeo').val().trim()!=""){
		var seoVal = $('#activitySeo').val();
		var seoValArray = seoVal.split(',');
		var seoLength = seoValArray.length;
		if(seoLength>5){
			alert('活动关键字录入不合法！');
			$('#activitySeo').focus();
		    return false;
		}
		for(var i=0;i<seoLength;i++){
			if(seoValArray[i].length>6 || seoValArray[i].length==0){
				alert('活动关键字每组最多6个有效字符！');
				$('#activitySeo').focus();
			    return false;
			}
		}
		
	}
	
	
	if($("#activityDesc").val().trim()==""){
		$("#activityDesc").focus();
		alert("活动介绍必填！");
		return false;
	}
	if($("#activityDesc").val().length>200){
		$("#activityDesc").focus();
		alert("活动介绍最多输入200个中文字！");
		return false;
	}
//	if($("#editor_activityIntroduce_id").val().trim()==""){
//		$("#editor_activityIntroduce_id").focus();
//		alert("活动说明必填！");
//		return false;
//	}
//	if($("#editor_activityQuestions_id").val().trim()==""){
//		$("#editor_activityQuestions_id").focus();
//		alert("活动答疑必填！");
//		return false;
//	}
}

function createsuccess(data) {
	alert(data.msg);
	if (data.flag == true) {
		location.href="/supplierActivity/activityList.action";
	}
}


// 清除选中
function clearElementChecked() {
	$("input[type='checkbox'][name='activityChannl']:checked").each(function() {
		$(this).attr("checked", false);
	});
	$("input[type='radio'][name='activityChargeType']").each(function() {
		if ($(this).val() == "1" && $("#activityType").val() != "3") {
			$(this).attr("checked", true);
			$('#commissionRate').attr("disabled",true);
			$('#commissionRate').val("");
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
		} else if ($("#activityType").val() == "3" && $(this).val() == "4") {
			$(this).attr("checked", true);
			$('#commissionRate').attr("disabled",false);
			$('#commissionRate').val("");
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
		} else {
			$(this).attr("checked", false);
			$('#commissionRate').attr("disabled",true);
			$('#commissionRate').val("");
			$('#fixedCharge').attr("disabled",true);
			$('#fixedCharge').val("");
			$('#singleCharge').attr("disabled",true);
			$('#singleCharge').val("");
		}
		
	});
	
}

// 元素隐藏
function elementHide(activityType) {
	if (activityType == 1 || activityType == 2) {// 促销推广//图文推广
		$('#activityChannlTr').hide();
		$('#activityChargeType_1').show();
		$('#activityChargeType_2').show();
		$('#activityChargeType_3').show();
		$('#activityChargeType_4').hide();
	} else if (activityType == 3) {// 渠道推广
		$('#activityChannlTr').show();
		$('#activityChargeType_1').hide();
		$('#activityChargeType_2').hide();
		$('#activityChargeType_3').hide();
		$('#activityChargeType_4').show();
	} else {
		$('#activityChannlTr').hide();
		$('#activityChargeType_1').show();
		$('#activityChargeType_2').show();
		$('#activityChargeType_3').show();
		$('#activityChargeType_4').show();
	}
}

function gotoList() {
	window.location.href = '/supplierActivity/activityList.action';
}

function changeLogoPath(arg) {
	$("#logoPath").val($(arg).val());
}


function gotoList() {
	window.location.href = '/supplierActivity/activityList.action';
}

function changeLogoPath(arg) {
	$("#logoPath").val($(arg).val());
}

function submitOperation(){

    var intro_activityIntroduce = $("#editor_activityIntroduce_id").val(),
        editor_activityIntroduce_change = $('.editor_activityIntroduce_change');
    editor_activityIntroduce_change.html(intro_activityIntroduce);
    
    var intro_activityQuestions = $("#editor_activityQuestions_id").val(),
    editor_activityQuestions_change = $('.editor_activityQuestions_change');
    editor_activityQuestions_change.html(intro_activityQuestions);
    //console.log(editor_change.html());

    $.each(editor_activityIntroduce_change.find('img'),function(i){
        var t = $(this),
            src = t.attr('src');
        t.attr('data-original',src);
        t.attr('src','http://jscss.km1818.com/res/images/default__logo.png');
        t.addClass('lazy');

    });

    $("#editor_activityIntroduce_lazy").val(editor_activityIntroduce_change.html());
    
    $.each(editor_activityQuestions_change.find('img'),function(i){
    	var t = $(this),
    	src = t.attr('src');
    	t.attr('data-original',src);
    	t.attr('src','http://jscss.km1818.com/res/images/default__logo.png');
    	t.addClass('lazy');
    	
    });
    
    $("#editor_activityQuestions_lazy").val(editor_activityQuestions_change.html());
	
	var rows;
	//var intro = $("#editor_id").val();
	$.ajax({
		async : false,
		url : "/app/checkIntroduce.action?checkedIntro=" + intro_activityIntroduce,
		type : "POST",
		dataType : "json",
		success : function(json) {
			rows = json;
		}
	});
	
	if (rows == 'wrong')
	{
		alert("请输入文明语言");
		return false;
	}
	
	$.ajax({
		async : false,
		url : "/app/checkIntroduce.action?checkedIntro=" + intro_activityQuestions,
		type : "POST",
		dataType : "json",
		success : function(json) {
			rows = json;
		}
	});
	
	if (rows == 'wrong')
	{
		alert("请输入文明语言");
		return false;
	}
		
//	if(flag){
		$("#frm").ajaxSubmit(options);
//	}
	
}

/**
 * 查询类目信息
 * 
 * @return
 */
function categorysSelect() {
	var url = "/supplierActivity/categorysSelect.action";
	myDialog = art.dialog.open(url, {
		title : '指定经营类目',
		width : 900,
		height : 500,
		drag : false,
		close : function() {
			$.unblockUI();
		}
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});

}
/**
 * 弹窗子面获取类目数据渲染至父页面
 * 
 * @param params
 * @return
 */
function closeOpenCategory(params) {
	var categoryId;
	var categoryName;
	var sizeI = params.length;
	for ( var i = 0; i < sizeI; i++) {
		var str = params[i];
		categoryId = str[0];
		categoryName = str[1];
		var delId="'categoryIds"+categoryId+"'";
		var html = '<input type="hidden" id="categoryIds'+categoryId+'" name="categoryIds" value="'+categoryId+'"/>'
				+'<span class="j_personInfo categoryIds'+categoryId+'" name="categoryClick" style="display: inline;">'
				+'<div name="categoryInfo" data-name="'+categoryName+'" data-id="'+categoryId+'" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">'
				+'<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">'
					+categoryName
				+'</em>'
				+'<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('+delId+');" hidefocus="hidefocus">'
					+'x'
				+'</a>'
			+'</div>'
		+'</span>';
//		var hiddenStr = '<input type="hidden" id="categoryIds'+categoryId+'" name="categoryIds" value="'+categoryId+'"/>';
//		$('#sonPageInfo').append(hiddenStr);
		$('#sonPageValue').append(html);
	}
	closeThis();
}

/**
 * 查询入驻商家
 * 
 * @return
 */
function shopSelect() {
	var url = "/supplierActivity/shopSelect.action";
	myDialog = art.dialog.open(url, {
		title : '指定入驻商家',
		width : 900,
		height : 500,
		drag : false,
		close : function() {
			$.unblockUI();
		}
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
}

/**
 * 弹窗子面获取入驻供应商数据渲染至父页面
 * 
 * @param params
 * @return
 */
function closeOpenSupplier(params) {
	var supplierId;
	var supplierName;
	var sizeI = params.length;
	for ( var i = 0; i < sizeI; i++) {
		var str = params[i];
		supplierId = str[0];
		supplierName = str[1];
		var delId="'supplierIds"+supplierId+"'";
		var html = '<input type="hidden" id="supplierIds'+supplierId+'" name="supplierIds" value="'+supplierId+'"/>'
					+'<span class="j_personInfo supplierIds'+supplierId+'" name="supplierClick" style="display: inline;">'
							+'<div name="supplierInfo" data-name="'+supplierName+'" data-id="'+supplierId+'" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">'
							+'<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">'
								+supplierName
							+'</em>'
							+'<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('+delId+');" hidefocus="hidefocus">'
								+'x'
							+'</a>'
						+'</div>'
					+'</span>';
//		var hiddenStr = '<input type="hidden" id="supplierIds'+supplierId+'" name="supplierIds" value="'+supplierId+'"/>';
//		$('#sonPageInfo').append(hiddenStr);
		$('#sonPageValue').append(html);
	}
	closeThis();
}

/**
 * 查询品牌信息
 * 
 * @return
 */
function brandSelect() {
	var url = "/supplierActivity/brandSelect.action";
	myDialog = art.dialog.open(url, {
		title : '选择品牌',
		width : 900,
		height : 500,
		drag : false,
		close : function() {
			$.unblockUI();
		}
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
}

/**
 * 弹窗子面获取品牌数据渲染至父页面
 * 
 * @param params
 * @return
 */
function closeOpenBrand(params) {
	var brandId;
	var supplierName;
	var sizeI = params.length;
	for ( var i = 0; i < sizeI; i++) {
		var str = params[i];
		brandId = str[0];
		brandName = str[1];
		var delId="'brandIds"+brandId+"'";
		var html = '<input type="hidden" id="brandIds'+brandId+'" name="brandIds" value="'+brandId+'"/>'
					+'<span class="j_personInfo brandIds'+brandId+'" name="brandClick" style="display: inline;">'
							+'<div name="brandInfo" data-name="'+brandName+'" data-id="'+brandId+'" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">'
							+'<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">'
								+brandName
							+'</em>'
							+'<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:delOp('+delId+');" hidefocus="hidefocus">'
								+'x'
							+'</a>'
						+'</div>'
					+'</span>';
//		var hiddenStr = '<input type="hidden" id="brandIds'+brandId+'" name="brandIds" value="'+brandId+'"/>';
//		$('#sonPageBrandInfo').append(hiddenStr);
		$('#sonPageBrandValue').append(html);
	}
	closeThis();
}

/**
 * 取消选择
 */
function delOp(thisDelTr){
	$("#"+thisDelTr).remove();
	$("."+thisDelTr).remove(); 
}



