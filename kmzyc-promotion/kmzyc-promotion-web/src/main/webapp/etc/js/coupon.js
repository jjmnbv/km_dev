$().ready(function(e) {
		$("#couponGivetypeId").change(function(e) {
			//手工发放
			if ($('#couponGivetypeId option:selected').val() == 1) {
				 $("#leveRow").show();
				$("#custom_leve").show();
				$("#custom_id").show();
				$("#order_money").hide();
				$("#payLeastMoney").show();
				$("#payLeastMoney_value").show();
				$("#textfield10").detach();
				$("#fafang_title").show();
				$("#timeInput").show();
				$("#Coupondays").remove();
				$("#days").remove();
				$("#given_type").append("<input type='hidden' name='are' id='are1' />");
			}
			//注册发放
			if ($('#couponGivetypeId option:selected').val() == 2) {
				$("#custom_leve").hide();
				$("#leveRow").hide();
				$("#order_money").hide();
				$("#custom_id").hide();
				$("#fafang_title").hide();
				$("#payLeastMoney").remove();
				$("#payLeastMoney_value").remove();
				$("#timeInput").show();
				$("#days").remove();
				$("#are1").remove();
			}
			//订单
			if ($('#couponGivetypeId option:selected').val() == 3) {
				$("#custom_leve").hide();
				$("#leveRow").hide();
				$("#order_money").hide();
				$("#custom_id").hide();
			 	$("#fafang_title").hide();
				$("#payLeastMoney").remove();
				$("#payLeastMoney_value").remove();
				$("#timeInput").show();
				$("#days").remove();
				$("#are1").remove();
			}
			//积分兑换
			if ($('#couponGivetypeId option:selected').val()== 4) {
				$("#custom_leve").hide();
				$("#leveRow").hide();
				$("#order_money").hide();
				$("#custom_id").hide();
				$("#fafang_title").hide();
				$("#payLeastMoney").remove();
				$("#payLeastMoney_value").remove();
				$("#timeInput").hide();
			//	alert($("#Conpondays").length);
			 	 if ($("#Conpondays").length==0){
				$("#dayInput").append(" <div id='days'> <input id='Coupondays'  type='input' name='coupon.couponValidDay'    /> 天</div> ");	
			 	 }
				$("#dayInput").removeAttr("style");
				$("#are1").remove();
		}
			//抽奖奖品
			if ($('#couponGivetypeId option:selected').val() == 5) {
				$("#custom_leve").hide();
				$("#leveRow").hide();
				$("#order_money").hide();
				$("#custom_id").hide();
				$("#fafang_title").hide();
				$("#payLeastMoney").remove();
				$("#payLeastMoney_value").remove();
				$("#timeInput").show();
				$("#days").remove();
				$("#are1").remove();
			}	
		}); 
	}); 
	function addProds() {
		var choosedPro = document.getElementById("table2").getElementsByTagName("span");
		var havechoosed = "";
		for ( var i = 0; i < choosedPro.length; i++) {
			var havechoosed = havechoosed + choosedPro[i].innerHTML + ",";
		}
		  dialog("选择优惠产品", "iframe:/app/gotoCouponProduct.action?haveChoosedPro="
			 	+ havechoosed, "750px", "455px", "iframe");
	}
	
	function addProdsNew() {
		var channel=$(".seletor").val();
		var supplierType=$('input[name="coupon.supplierType"]:checked').val();
		var choosedPro = document.getElementById("table2").getElementsByTagName("span");
		var havechoosed = "";
		for ( var i = 0; i < choosedPro.length; i++) {
			var havechoosed = havechoosed + choosedPro[i].innerHTML + ",";
		}
		//如果商家类别为空不支持商品选择
		if(supplierType==null){
			alert("请选择商家类别");
		} else{
			//指定入驻商家时获取商家shopCode
			if(supplierType==3){
				//判断是否已选择
				var div=$(".j_div");
				if(div.length==0){
					alert("请选择入驻商家");
				}else {
					var code=$('input[name="coupon.shopCode"]').val();
					dialog("选择优惠产品", "iframe:/coupon/chooseCouponProduct.action?channel="+channel+"&&callPath="+1+"&&code="+code, "750px", "455px", "iframe");
				}
			} else {
				 dialog("选择优惠产品", "iframe:/coupon/chooseCouponProduct.action?channel="+channel+"&&callPath="+1+"&&supplierTypes="+supplierType, "750px", "455px", "iframe");
			}
		}
	}
	function addSupplys() {
		var choosedPro = document.getElementById("table3").getElementsByTagName("span");
		var havechoosed = "";
		for ( var i = 0; i < choosedPro.length; i++) {
			var havechoosed = havechoosed + choosedPro[i].innerHTML + ",";
		}
		  dialog("选择供应商", "iframe:/app/gotoCouponSupply.action?haveChoosedSuplly="
			 	+ havechoosed, "750px", "455px", "iframe");
	}
	
	//删除选择的产品列表
	function del(ID) {
		$("tr[id=" + ID + "]").remove();
	}
	function delLeve(ID) {
		$("tr[id=" + "trLeve" + ID + "]").remove();
	}
	function delCustom(ID) {
		$("tr[id=" + "trcustom" + ID + "]").remove();
	}
	function selectInput() {
		$("#custom_table1  input").attr("checked", "checked");
		$("#customContent  input").attr("checked", "checked");
	    $("#editBody input").attr("checked", "checked");
	    $("#editBody1 input").attr("checked", "checked");
		$("#productContent input").attr("checked", "checked");
		$("#supplyContent input").attr("checked", "checked");
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var categoryIds = ""; //提交之前，将三级类目Id取出来
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].level != 2) {
			} else {
				categoryIds += nodes[i].categoryId + ",";
			}
		}
		$("#categoryId").val(categoryIds);
	}

	function goBack(){
		var viewType=$("#viewTypes").val();
		if(viewType!=null && viewType=='show'){
			var iframeId = window.frameElement && window.frameElement.id || '';
			if(iframeId != ''){
				parent.window.location.href=document.referrer;
			}else{
				window.location.href=document.referrer;
			}
		}else{
			window.location.href='/coupon/couponRule_PageList.action';
		}
	}

