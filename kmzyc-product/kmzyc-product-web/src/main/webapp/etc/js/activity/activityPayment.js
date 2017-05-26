	$(function(){
		
	});
//	var options = { dataType: 'json', beforeSubmit: validateForm, success: createsuccess};
//	
//	function validateForm(){
//		
//	}
//	
//	function createsuccess(data){
//		alert(data.msg);
//		if(data.flag==true){ 
//			window.location.href="/supplierActivity/queryActivitySupplierCostDetail.action";
//		}
//	}
	
	function activityEndRefundment(activityId,supplierEntryId){
		if(confirm('确定进行退款操作吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/activityEndRefundment.action?activityId='+activityId+"&supplierEntryId="+supplierEntryId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var result = data.flag;
					var msg = data.msg;
					alert(msg);
					if(result==true){
						$('#frm').submit();
					}
				}
			});
		}
	}
	
	function activityCancleRefundment(activityId,supplierEntryId){
		if(confirm('确定进行退款操作吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/activityCancleRefundment.action?activityId='+activityId+"&supplierEntryId="+supplierEntryId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var result = data.flag;
					var msg = data.msg;
					alert(msg);
					if(result==true){
						$('#frm').submit();
					}
				}
			});
		}
	}
	