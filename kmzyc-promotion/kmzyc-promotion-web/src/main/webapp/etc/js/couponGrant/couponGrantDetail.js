
	function selectCouponGrant(){
		var couponIssuingId=$("#couponIssuingId").val();
		var isStatus=$("#isStatus").val();
		var couponGrantId=$("#couponGrantId").val();
		var customerName=$("#customerName").val();
		var couponStatus=$("#couponStatus").val();
		if(couponGrantId != "" && !(/^[-]?\d+$/).test(couponGrantId)){
			alert('请输入数字');
			return;
		}
		var url="/coupon/selectCouponGrant.action?couponGrantVO.couponIssuingId="+couponIssuingId;
		if(isStatus!=""){
			url=url+"&couponGrantVO.actStatus="+isStatus;
		}
		if(couponGrantId!=""){
			url=url+"&couponGrantVO.couponGrantId="+couponGrantId;
		}
		if(customerName!=""){
			url=url+"&couponGrantVO.customerName="+customerName;
		}
		if(couponStatus!=""){
			url=url+"&couponGrantVO.couponStatus="+couponStatus;
		}
		location.href=url;
	}
	function exportCouponGrant(){
		var couponIssuingId=$("#couponIssuingId").val();
		var isStatus=$("#isStatus").val();
		var couponGrantId=$("#couponGrantId").val();
		var customerName=$("#customerName").val();
		var couponStatus=$("#couponStatus").val();
		var url="/coupon/exportCouponGrant.action?couponGrantVO.couponIssuingId="+couponIssuingId;
		if(isStatus!=""){
			url=url+"&couponGrantVO.actStatus="+isStatus;
		}
		if(couponGrantId!=""){
			url=url+"&couponGrantVO.couponGrantId="+couponGrantId;
		}
		if(customerName!=""){
			url=url+"&couponGrantVO.customerName="+customerName;
		}
		if(couponStatus!=""){
			url=url+"&couponGrantVO.couponStatus="+couponStatus;
		}
		location.href=url;
	}
	
	//查看优惠券规则
	function couponDetail(){
		var couponId=$("#couponIdHid").val();
		dialog("查看规则", "iframe:/coupon/couponRule_pageShow.action?viewType=show&couponId="+couponId,
			 	 "750px", "455px", "iframe");
	}
	function backPage(){
		window.location.href="/coupon/gotoGrantCouponList.action";
	}
	function test(){
		window.location.href="/agreement/test.action";
	}
	

