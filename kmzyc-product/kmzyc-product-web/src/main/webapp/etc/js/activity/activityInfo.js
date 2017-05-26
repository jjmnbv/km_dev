	$(function(){
		
	});
	var selectActivityId="";
	var options = { dataType: 'json', success: createsuccess};
	
	function createsuccess(data){
		alert(data.msg);
		if(data.flag==true){ 
			doSearch();
		}
	}
	
	function doSearch(){
		$('#frm').attr("action","/supplierActivity/activityList.action");
		document.getElementById('pageNo').value = 1;
		$('#frm').submit();
	}
	
	function gotoAdd(){
		location.href="/supplierActivity/toActivityAdd.action";
	}
	
	function toViewActivityInfo(id){
		location.href="/supplierActivity/findActivityInfoById.action?activityInfo.activityId="+id;
	}
	
	function toModifyActivity(id){
		location.href="/supplierActivity/toActivityModify.action?activityInfo.activityId="+id;
	}
	
	function deleteActivity(activityId){
		if(confirm('确定删除该活动吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/deleteActivity.action?activityId='+activityId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var flag = data.flag;
					var msg = data.msg;
					alert(msg);
					if(flag==true){
						doSearch();
					}
				}
			});
		}
	}
	
	function stopActivity(activityId){
		if(confirm('确定终止该活动吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/stopActivity.action?activityId='+activityId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var flag = data.flag;
					var msg = data.msg;
					alert(msg);
					if(flag==true){
						doSearch();
					}
				}
			});
		}
	}
	
	function submitAuditActivity(activityId){
		if(confirm('确定提交审核该活动吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/submitAuditActivity.action?activityId='+activityId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var flag = data.flag;
					var msg = data.msg;
					alert(msg);
					if(flag==true){
						doSearch();
					}
				}
			});
		}
	}
	
	function cancelSubmitAuditActivity(activityId){
		if(confirm('确定撤销提审该活动吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/cancelSubmitAuditActivity.action?activityId='+activityId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var flag = data.flag;
					var msg = data.msg;
					alert(msg);
					if(flag==true){
						doSearch();
					}
				}
			});
		}
	}
	
	function cancelAuditActivity(activityId){
		if(confirm('确定撤销审核该活动吗？')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/cancelAuditActivity.action?activityId='+activityId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					var flag = data.flag;
					var msg = data.msg;
					alert(msg);
					if(flag==true){
						doSearch();
					}
				}
			});
		}
	}
	
	function inviteSuppliers(activityId){
		selectActivityId=activityId;
		$.ajax({
			dataType:'json',
			traditional:true,
			url:'/supplierActivity/queryInvitedSupplier.action',
			data:{"activityInfo.activityId":selectActivityId},
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(data){
				var flag = data.result;
				var msg = data.msg;
				var suppliers =data.invitedSuppliers;
				$('#invitedSuppliers').val(suppliers.join(','));
				if(flag==true){
					var url = "/supplierActivity/selectSupplierForInvite.action?activityInfo.activityId="+selectActivityId;
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
				}else{
					alert(msg);
				}
			}
		});
	}
	
	function closeOpenAndSaveSupplier(suppliers){
		$.ajax({
			dataType:'json',
			traditional:true,
			url:'/supplierActivity/saveInviteSuppliers.action',
			data:{"activityInfo.activityId":selectActivityId,"supplierIds":suppliers},
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(data){
				closeThis();
				var flag = data.result;
				var msg = data.msg;
				alert(msg);
				if(flag){
					//重新刷活动信息列表
					window.location.href="/supplierActivity/activityList.action";
				}
			}
		});
	}
	
	function listInviteSuppliers(activityId){
		window.location.href= "/supplierActivity/queryInviteSupplierEntryList.action?activitySupplierEntry.activityId="+activityId;
	}