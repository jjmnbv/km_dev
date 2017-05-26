  
	var options = { dataType: 'json', beforeSubmit: validateForm, success: createsuccess};
	
	var optionsForBatchDel = { dataType: 'json', beforeSubmit: validateForm, success: createsuccessForBatchDel};
	
	var optionsForBatchAudit = { dataType: 'json', beforeSubmit: validateForm, success: createsuccessForBatchAudit};
	
	
	function validateForm(){
		
	}
	function createsuccess(data){
		alert(data.msg);
		//window.location.href = '/app/productShow.action';
		if(data.result==true){ 
			gotoListForView();
		}
	}
	function createsuccessForBatchDel(data){
		alert(data.msg);
		if(data.result==true){ 
			 $('#frm').submit();
		}
	}
	function createsuccessForBatchAudit(data){
		alert(data.msg);
		window.location.href = '/app/auditProductShow.action';
	}
	
	function createUp() {
		var chkObj = $('input[name="productIds"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要上架的产品!');
	   		return;
	   	}
		
		if(confirm('确定上架已选产品吗？')){
			var flag = true;
			$('input[name="productIds"]:checked').each(function(){  
				if($("#st"+this.value).val()!="2"){
					alert("只有审核通过的商品才能上架！");
					flag = false;
					return false;
				}
			});  
			
			if(flag){
				$("#frm").attr("action","/app/upshelfProductDraft.action");
				$("#frm").submit();
			}
		}	
	}
	
	function createDown() {
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要下架的产品!')
	   		return;
	   	}
		
		if(confirm('确定下架已选产品吗？')){
			var chk_value =[]; 
			var chk_tmp = [];
			var html = "";
			  $('input[name="productIdChk"]:checked').each(function(){  
			   	chk_value.push($(this).val());    
			  });  
			for(var i=0;i<chk_value.length;i++){
				chk_tmp = chk_value[i].split("_");
				if(chk_tmp[1]!='3') {
					alert('请检查产品状态是否为已上架状态！');
					return;
				}
				html += '<input type="hidden" name="upProductId" value="'+chk_tmp[0]+'"/>';
			}
			
			$('#downForm').html(html);
			$('#statusChecked').val('4');//下架状态
			$('#downForm').ajaxSubmit(options);
		}
	}
	
	function deleteProduct(productId){
		if(confirm('确定删除该产品吗？')){
			location.href = '/app/deleteProductDraft.action?type=product&productId='+productId;
		}
	}
	
	function submitTheAudit(value){
		if(confirm('确定提交审核该产品吗？')){
//			$.ajax({
//				dataType:'json',
//				url:'/app/submitTheAudit.action?id='+value,
//	 			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
//				success:function(date){
//					var result = date.result;
//					var msg = date.msg;
//					alert(msg);
//					if(result==true){
//						document.location.href='/app/productShow.action';
//						//gotoListForView();
//					}
//				}
//			});
			location.href = "/app/submitDraftTheAudit.action?productId="+value;
		}
	}
	
	function batchSubmitTheAudit(){
		var chkObj = $('input[name="productIds"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要提交审核的产品!');
	   		return;
	   	}
		
		if(confirm('确定提交审核已选产品吗？')){
			var flag = true;
			$('input[name="productIds"]:checked').each(function(){  
				if($("#st"+this.value).val()!="0"){
					alert("只有草稿商品才能提交审核！");
					flag = false;
					return false;
				}
			});  
			
			if(flag){
				$("#frm").attr("action","/app/batchSubmitDraftTheAudit.action");
				$("#frm").submit();
			}
		}
	}
	
	function submitTheUnAudit(value){
		if(confirm('确定取消审核该产品吗？')){
			location.href = "/app/submitDraftTheUnAudit.action?productId="+value;
		}
	}
	
	function auditProduct(productId,auditStatus){
		if(confirm('确定审核该产品吗？')){
			$("#auditStatus").val(auditStatus);
			if(6==auditStatus){
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {  
				    	$("#reasonText").val($("#reasonArea").val());
				    	this.close();
						document.getElementById("auditForm").submit();
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}else{
				document.getElementById("auditForm").submit();
			}
			
		}
	}
	
	//批量审核
	function batchAuditProduct(auditStatus){
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要审核的产品!')
	   		return;
	   	}
		
		if(confirm('确定审核已选产品吗？')){
			$("#auditStatus").val(auditStatus);
			if(6==auditStatus){
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {  
				    	$("#reasonText").val($("#reasonArea").val());
				    	this.close();
						document.getElementById("frm").action = "/app/batchAuditProductDraft.action";
						document.getElementById("frm").submit();
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}else{
				document.getElementById("frm").action = "/app/batchAuditProductDraft.action";
				document.getElementById("frm").submit();
			}
		}
	}
	
	function change1(sourceCategoryId,targetCategoryId){
		
		var categoryHtml = '';
		if(targetCategoryId=='categoryId2'){
			//首先，将二级和三级的清空
			$('#categoryId2').empty().append($("<option value='0'>--二级类目--</option>"));
			$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
			categoryHtml = '<option value="0">--二级类目--</option>';
			//$("#categoryId3").find("option[text='--三级类目--']").attr("selected",true);
		}else if(targetCategoryId=='categoryId3'){
			$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
			categoryHtml = '<option value="0">--三级类目--</option>';
		}
		$.ajax({
			dataType:'json',
			url:'/app/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(date){
				var categoryList = date.categoryList;
				var size = categoryList.length;
				
				for(var i=0;i<size;i++){
					categoryHtml += '<option value="'+categoryList[i].categoryId+'">'+categoryList[i].categoryName+'</option>';
				}
				$('#'+targetCategoryId).html(categoryHtml);
			}
		});
	}
	
	function change2(sourceCategoryId,targetCategoryId){
		
		var categoryHtml = '';
		if(targetCategoryId=='categoryId2'){
			categoryHtml = '<option value="">--二级类目--</option>';
		}else if(targetCategoryId=='categoryId3'){
			categoryHtml = '<option value="">--三级类目--</option>';
		}
		
		if($('#'+sourceCategoryId).val()==""){
			$('#'+targetCategoryId).html(categoryHtml);
			if(sourceCategoryId=='categoryId1'){
				$('#categoryId3').html('<option value="">--三级类目--</option>');
			}
			return;
		}
		
		$.ajax({
			dataType:'json',
			url:'/app/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(date){
				var categoryList = date.categoryList;
				var size = categoryList.length;
				
				for(var i=0;i<size;i++){
					categoryHtml += '<option value="'+categoryList[i].categoryId+'">'+categoryList[i].categoryName+'</option>';
				}
				$('#'+targetCategoryId).html(categoryHtml);
			}
		});
	}
	
	function previewProductInfoPage(value){
		$.ajax({
			dataType:'json',
			url:'/app/previewProductDraftInfoPage.action?id='+value,
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(data){
				var pageUrl = data.pageUrl;
				if(pageUrl!=null && pageUrl!=""){
					window.open(pageUrl, "_blank")
				}else{
					alert('预览失败!');
				}
			}
		});
	}
	
	//提交产品分类表单,将3级全选择完，才能提交
	function submitForm(){
		var optionVal1 = $('#categoryId1')[0].selectedIndex
		var optionVal2 = $('#categoryId2')[0].selectedIndex
		var optionVal3 = $('#categoryId3')[0].selectedIndex
		if(optionVal1==0 || optionVal2==0 || optionVal3==0){
			alert("分类级别选择不完整!");
			return false;
		}
		$('#categoryForm').submit();
	}
	
	function brandChange(){   
		$('#brandName').val($('#brandId').find("option:selected").text());
	}

	function categoryChange(){   
		$('#categoryName').val($('#categoryId3').find("option:selected").text());
	}
	
	
	function checkProductName(obj){
		if($(obj).val()=="") return;
		$.ajax({
			dataType:'json',
			url:'/app/checkProductName.action?name='+$(obj).val(),
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(date){
				if(date.result==false){
					alert(date.msg);
					$(obj).select();
					return;
				}
			}
		});
	}

	
	function gotoList(){
	    location.href="/app/productShow.action";
	}
	
	function gotoAuditList(){
		location.href="/app/auditProductShow.action";
	}

	function gotoAdd(){
	    location.href="/app/toProductCategory.action";
	}

	function gotoUpdate(id){
		$('#frm').attr("action","/app/toProductDraftUpdate.action?productId="+id);
		$('#frm').submit();
	    //location.href="/app/toProductUpdate.action?product.id="+id+"&product.bCategoryId="+$('#categoryId1').val()+"&product.mCategoryId="+$('#categoryId2').val();
	}

	function gotoView(id){
		location.href="/app/viewProduct.action?product.id="+id+"&product.bCategoryId="+$('#categoryId1').val()+"&product.mCategoryId="+$('#categoryId2').val();
	}
	
	function gotoAuditView(id){
		location.href="/app/viewProductForAudit.action?product.id="+id;
	}
	
	function gotoViewProduct(id){
		$('#frm').attr("action","/app/viewProductDraft.action?productId="+id);
		$('#frm').submit();
	}
	

	function gotoAuditViewProduct(id){
		$('#frm').attr("action","/app/viewProductForAuditDraft.action?productId="+id);
		$('#frm').submit();
	}

	function gotoListForView(){
	    $('#listfrm').submit();
	}
	
	function doSearch(){
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}

	function batchDeleteProductDraft() {
		var chkObj = $('input[name="productIds"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要删除的产品!')
	   		return;
	   	}
		
		if(confirm('确定删除已选产品吗？')){
			var html = "";
			$('input[name="productIds"]:checked').each(function(){  
				html += '<input type="hidden" name="upProductIds" value="'+$(this).val()+'"/>';
			});  
			
			$('#batchDeleteForm').html(html);
			$('#batchDeleteForm').ajaxSubmit(optionsForBatchDel);
		}	
	}
	