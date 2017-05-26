/**
 * 活动已开始、审核状态不为未审核则隐藏审核按钮
 * @return
 */
function hideButton() {
	var strAuditStatus = $("#strAuditStatus").val();
	var timestamp=new Date().getTime();
	var entryEndTime = new Date($("#activityStartTime").val().replace("-", "/").replace("-", "/"));
	//报名状态不为未审核或者当前时间大于报名结束时间
	if(strAuditStatus != 0 ){
		$('#btnStylePass').hide();
		$('#btnStyleNoPass').hide();
	}else if(timestamp>entryEndTime){
		$('#btnStylePass').hide();
	}
}

/**
 * 跳转到活动商家报名列表页面
 * @return
 */
function returnBack(activityId) {
	location.href = '/supplierActivity/querySupplierEntryList.action?activitySupplierEntry.activityId=' + activityId;
}

/**
 * 通过促销查询促销信息
 * @param promotionId
 * @return
 */
function selectPromotion(promotionId) {
	var url = "/supplierActivity/selectPromotion.action?promotionInfo.promotionId="+promotionId;
	myDialog = art.dialog.open(url, {
		title : '',
		width : 800,
		height : 100,
		drag : false,
		close : function() {
			$.unblockUI();
		}
	});
	$.blockUI.defaults.overlayCSS.opacity = '0.5';
	$.blockUI( {
		message : ""
	});
}

var ckNum=0;
/**
 * 审核
 * @return
 */
function activityAuditEntry(activityId,supplierEntryId,auditStatus) {
	var isReturn ;
	//审核通过：校验验证状态、缴费状态（未缴费不可审核通过）
	if(auditStatus == 1){//审核通过
		var isCk=false;
		$("input[name=activitySkuId]").each(function() {
	        if ($(this).attr("checked")) {
	        	isCk = true;
	        }
	    });
		if(isCk){
			alert("商品被标记，不可进行审核通过操作！");
			return false;
		}else{
			if(confirm('确定审核该商家吗？')){
				isReturn = verifyStauts(supplierEntryId,auditStatus);
				if(isReturn){
					returnBack(activityId);
					return false;
				}
				ckNum += 1;
				if(ckNum>1){
					return false;
				}
				$("#returnB").attr("disabled", true);
				$("#btnStylePass").attr("disabled", true);
				$("#btnStyleNoPass").attr("disabled", true);
	    		$('#ajaxSubmit').ajaxSubmit({
	    			type:"post",
					url:'/supplierActivity/activityAuditEntry.action?activitySupplierEntry.auditStatus='+auditStatus,
	    			async: false,
					error:function(){alert('请求失败，请稍后重试或与管理员联系！');returnBack(activityId);},
					success:function(data){
						var rDate = eval("("+data+")");
						alert(rDate.msg);
						returnBack(activityId);
					}
				});
			}
		}
	}else if(auditStatus == 2){//审核不通过：校验验证状态  
		var isCk=false;
//		var activitySkuIds="";
		$("input[name=activitySkuId]").each(function() {
			 if ($(this).attr("checked")) {
				 if(!(isCk)){//第一次删掉div中数据
					 $('#activitySkuIdsDiv input').remove();  
				 }  
				 isCk = true;
				 var html = '<input type="hidden"  name="activitySkuIds" value="'+$(this).val()+'"/>';
				 $('#activitySkuIdsDiv').append(html);
	         } 
	    });
//		if(isCk){
			if(confirm('确定审核该商家吗？')){
				isReturn = verifyStauts(supplierEntryId,auditStatus);
				if(isReturn){
					returnBack(activityId);
					return false;
				}
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {  
				    	var remark = $("#reasonArea").val();
				    	if($.trim(remark) == ''){
				    		alert("未填写审核不通过原因！");
					    	return false;
				    	}else{
				    		ckNum += 1;
							if(ckNum>1){
								return false;
							}
							$("#btnStylePass").attr("disabled", true);
							$("#btnStyleNoPass").attr("disabled", true);
							$("#returnB").attr("disabled", true);
							$(".aui_state_highlight").attr("disabled", true);
				    		$('#ajaxSubmit').ajaxSubmit({
				    			type:"post",
								url:'/supplierActivity/activityAuditEntry.action?activitySupplierEntry.remark='+remark+'&activitySupplierEntry.auditStatus='+auditStatus,
				    			async: false,
								error:function(){alert('请求失败，请稍后重试或与管理员联系！');returnBack(activityId);},
								success:function(data){
									var rDate = eval("("+data+")");
									alert(rDate.msg);
									returnBack(activityId);
								}
							});
				    	}
				    	this.close();
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}
//		}else{
//			alert("未指定不可通过商品，不可进行审核操作！");
//			return false;
//		}
	}else{
		alert("审核类型错误！")
	}
}
/**
 * 审核校验
 * @param supplierEntryId
 * @return
 */
function verifyStauts(supplierEntryId,strAuditStatus){
	var isReturn = false;
	$.ajax({
		dataType:'json',
		url:'/supplierActivity/verifyStauts.action?activitySupplierEntry.supplierEntryId='+supplierEntryId,
		async: false,
		error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
		success:function(data){
			var flag = data.flag;
			var chargeType = data.chargeType;//活动收费类型 1：免费 2：固定收费 3：单品收费 4：返佣
			var auditStatus = data.auditStatus;//审核状态 0：未审核 1：已审核 2：审核不通过
			var paymentStatus = data.paymentStatus;//款项状态1：待缴费 2：已缴费 3：待退款 4：已退款 
			var activityStartTime = data.activityStartTime;//活动开始时间
			var timestamp=Math.round(new Date().getTime());
			/*
			 为不影响活动开始自动退款   先开放活动开始允许'审核不通过'操作
			if(timestamp>activityStartTime){
				alert("活动已开始，不能进行审核");
				isReturn = true;
			}else{
				if(strAuditStatus == 2){//审核不通过不用查看是否缴费、只需检查是否状态已存在
					if(strAuditStatus == auditStatus){
						alert("请检查审核状态！");
						isReturn = true;
					}else{
						isReturn = false;
					}
				}else{//审核不为不通过需要验证是否免费、不免费是否缴费
					if(chargeType != 1){
						if(paymentStatus != 2){
							alert("该商家尚未缴费，不能进行审核");
							isReturn = true;
						}
					}else if(strAuditStatus == auditStatus){
						alert("请检查审核状态！");
						isReturn = true;
					}else{
						isReturn = false;
					}
				}
			}
			 */
			
			if(strAuditStatus == 2){//审核不通过不用查看是否缴费、只需检查是否状态已存在
				if(strAuditStatus == auditStatus){
					alert("请检查审核状态！");
					isReturn = true;
				}else{
					isReturn = false;
				}
			}else{//审核不为不通过需要验证是否免费、不免费是否缴费
				if(timestamp>activityStartTime){
					alert("活动已开始，不能进行审核");
					isReturn = true;
				}else if(chargeType != 1){
					if(paymentStatus != 2){
						alert("该商家尚未缴费，不能进行审核");
						isReturn = true;
					}
				}else if(strAuditStatus == auditStatus){
					alert("请检查审核状态！");
					isReturn = true;
				}else{
					isReturn = false;
				}
			}
			
		}
	});
	return isReturn;
}