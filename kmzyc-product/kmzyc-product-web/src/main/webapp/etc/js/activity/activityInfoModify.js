	$(function(){
		
	});

	var options_modify = { dataType: 'json', beforeSubmit: validateForm_modify,success: createsuccess_modify};
	
	function createsuccess_modify(data){
		alert(data.msg);
		if(data.flag==true){ 
			location.href="/supplierActivity/activityList.action";
		}
	}
	
	function validateForm_modify(){
		
		if($("#activityName").val()==""){
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
//			$("#entryStartTime").focus();
			alert("活动报名开始时间必填！");
			return false;
		}
		if($("#entryEndTime").val()==""){
//			$("#entryEndTime").focus();
			alert("活动报名截止时间必填！");
			return false;
		}
		
		
		var endtime = $('#entryStartTime').val();
//		 var starttime = dateStr;
		 var start = new Date();
		 var end = new Date(endtime.replace("-", "/").replace("-", "/"));
		 if (end < start) {
	      alert('活动报名时间不能小于当前时间！');
	      return false;
		 }
		
		 if (!compareDate($('#entryStartTime').val(),$('#entryEndTime').val())) {
	        alert('活动报名结束时间不能小于开始时间！');
	        return false;
		 }
		
		
		if($("#activityStartTime").val()==""){
//			$("#activityStartTime").focus();
			alert("活动开始时间必填！");
			return false;
		}
		if($("#activityEndTime").val()==""){
//			$("#activityEndTime").focus();
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
		 var time = parseFloat(diff) / 1000;
	     if (null != time && "" != time) {
	        if (time >= 60 * 60 && time < 60 * 60 * 24) {
	            time = parseInt(time / 3600.0);
	            if(time<2){
	            	alert('报名结束时间与活动开始时间之间应大于2小时！');
	                return false;
	            }
	        }
	        else {
	        	alert('报名结束时间与活动开始时间之间应大于2小时！');
	            return false;
	        }
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
//			if($("#editor_activityIntroduce_id").val().trim()==""){
//				$("#editor_activityIntroduce_id").focus();
//				alert("活动说明必填！");
//				return false;
//			}
//			if($("#editor_activityQuestions_id").val().trim()==""){
//				$("#editor_activityQuestions_id").focus();
//				alert("活动答疑必填！");
//				return false;
//			}
	}
	
	
	function submitModify(){

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
			
//		if(flag){
			$("#frm").ajaxSubmit(options_modify);
//		}
		
	}
	

	