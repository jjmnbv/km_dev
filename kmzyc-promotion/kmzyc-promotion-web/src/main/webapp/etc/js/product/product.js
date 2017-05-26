  
	$(function(){
		if($("#returnType").val()=="productDraftError"){
			alert("系统发生错误，提交失败，请稍后再试或联系管理员！");
		}
	});

	var options = { dataType: 'json', beforeSubmit: validateForm, success: createsuccess};
	
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
	function createsuccessForBatchAudit(data){
		alert(data.msg);
		window.location.href = '/app/auditProductShow.action';
	}
	
	function createUp() {
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要上架的产品!')
	   		return;
	   	}
		
		if(confirm('确定上架已选产品吗？')){
			var chk_value =[]; 
			var chk_tmp = [];
			var html = "";
			  $('input[name="productIdChk"]:checked').each(function(){  
			   	chk_value.push($(this).val());    
			  });  
			for(var i=0;i<chk_value.length;i++){
				chk_tmp = chk_value[i].split("_");
				if(chk_tmp[1]!='2' && chk_tmp[1]!='4'&& chk_tmp[1]!='5') {
					alert('请检查产品状态是否正确！');
					return;
				}
				html += '<input type="hidden" name="upProductId" value="'+chk_tmp[0]+'"/><input type="hidden" name="upChannel" value="'+chk_tmp[2]+'"/>';
			}
			
			$('#upForm').html(html);
			$('#statusChecked').val('3');//上架状态
			$('#upForm').ajaxSubmit(options);
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
			$.ajax({
				dataType:'json',
				url:'/app/deleteProduct.action?id='+productId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(date){
					var result = date.result;
					var msg = date.msg;
					alert(msg);
					if(result==true){
						document.location.href='/app/productShow.action';
					}
				}
			});
		}
	}
	
	function submitTheAudit(value){
//		var productId = null;
//		var status = null;
		if(confirm('确定提交审核该产品吗？')){
//			if(value.indexOf('_')>0){
//				var values = value.split('_');
//				productId=values[0];
//				if(values[1]=='0'){
//					$('#statusChecked').val('1');//待审核状态
//					$('#checkedId').val(productId);
//				}
//			}else{
//				productId = value;
//				$('#statusChecked').val('1');//待审核状态
//				$('#checkedId').val(productId);
//			}
			$.ajax({
				dataType:'json',
				url:'/app/submitTheAudit.action?id='+value,
	 			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(date){
					var result = date.result;
					var msg = date.msg;
					alert(msg);
					if(result==true){
						document.location.href='/app/productShow.action';
						//gotoListForView();
					}
				}
			});
		}
	}
	
	function previewProductInfoPage(value){
		$.ajax({
			dataType:'json',
			url:'/app/previewProductInfoPage.action?id='+value,
			error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
			success:function(date){
				var pageUrl = date.pageUrl;
				if(pageUrl!=null && pageUrl!=""){
					window.open(pageUrl, "_blank")
				}else{
					alert('预览失败!');
				}
			}
		});
	}
	
	function auditProduct(productId){
		if(confirm('确定审核该产品吗？')){
			$.ajax({
				dataType:'json',
				url:'/app/auditProduct.action?id='+productId,
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(date){
					var result = date.result;
					var msg = date.msg;
					alert(msg);
					if(result==true){
						document.location.href='/app/auditProductShow.action';
					}
				}
			});
		}
	}
	
	//批量审核
	function batchAuditProduct(){
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要审核的产品!')
	   		return;
	   	}
		
		if(confirm('确定审核已选产品吗？')){
			var chk_value =[]; 
			var chk_tmp = [];
			var html = "";
			  $('input[name="productIdChk"]:checked').each(function(){  
			   	chk_value.push($(this).val());    
			  });  
			for(var i=0;i<chk_value.length;i++){
				chk_tmp = chk_value[i].split("_");
				if(chk_tmp[1]!='1') {
					alert('请检查产品状态是否为未审核状态！');
					return;
				}
				html += '<input type="hidden" name="upProductId" value="'+chk_tmp[0]+'"/>';
			}
			
			$('#batchAuditForm').html(html);
			$('#batchAuditForm').ajaxSubmit(optionsForBatchAudit);
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
			url:'/promotion/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
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
		}else{
			if(sourceCategoryId=='categoryId1'){
				$('#categoryId3').html('<option value="">--三级类目--</option>');
			}
		}
		
		$.ajax({
			dataType:'json',
			url:'/promotion/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
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
		$.post(
			"/app/findSameProductFromDraft.action",
			{productId:id},
			function(data){
				var strAction = "/app/toProductUpdate.action?product.id="+id;
				if(data != 0){
					strAction = "/app/toProductUpdate.action?isExsitProduct=1&product.id="+id;
					if(data == 1){
						if(!confirm("该产品已在草稿中存在记录，是否继续？\r\n继续则会覆盖草稿中的信息！")){
							return;
						}
					}
					
					if(data == 2){
						if(!confirm("该产品的价格已在草稿中存在记录，是否继续？\r\n继续则会覆盖草稿中的价格！")){
							return;
						}
					}
					
				}
				$('#frm').attr("action",strAction);
				$('#frm').submit();
			}
		);
	}

	function gotoView(id){
		location.href="/app/viewProduct.action?product.id="+id+"&product.bCategoryId="+$('#categoryId1').val()+"&product.mCategoryId="+$('#categoryId2').val();
	}
	
	function gotoAuditView(id){
		location.href="/app/viewProductForAudit.action?product.id="+id;
	}
	
	function gotoViewProduct(id,type){
		$('#frm').attr("action","/app/viewProduct.action?product.id="+id+"&type="+type);
		$('#frm').submit();
	}
	

	function gotoAuditViewProduct(id){
		$('#frm').attr("action","/app/viewProductForAudit.action?product.id="+id);
		$('#frm').submit();
	}

	function gotoListForView(){
		
		if($("#returnType").val()!=""){
			$('#listfrm').attr("action", "/app/findCertificateFilesProduct.action");
		}
	    $('#listfrm').submit();
	}
	
	function doSearch(){
		$('#frm').attr("action","/app/productShow.action");
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}
	
	function exportProductInfo(){
		$('#frm').attr("action","/app/exportProductInfo.action");
		$('#frm').submit();
	}
	
	function modifyPostil(productId){
		var _txt = $("#pos_"+productId).text();
		if($("#st_"+productId).val() == "default"){
			$("#pos_"+productId).html('<textarea id="txt_'+productId+'" rows="3" cols="16" style="resize: none;font-size:12px;">'+_txt+'</textarea><br /><input type="button" value="取消" onclick="cancelModify('+productId+',\''+_txt+'\');" style="padding:0px;" />');
			$("#img_"+productId).attr("src","/etc/images/little_icon/tongguo.png");
			$("#img_"+productId).attr("title","确认批注信息");
			$("#st_"+productId).val("modify");
			$("#txt_"+productId).select();
		}else{
			$.post(
				"/app/updateProductPostil.action",
				{productId:productId,postilStr:$("#txt_"+productId).val()},
				function(data){
					if(data == "-1"){
						$("#pos_"+productId).text(_txt);
						alert("系统发生错误，请联系管理员或稍后再试！");
					}else{
						$("#pos_"+productId).text($("#txt_"+productId).val());
					}
					$("#img_"+productId).attr("src","/etc/images/little_icon/huifu.png");
					$("#img_"+productId).attr("title","修改批注信息");
					$("#st_"+productId).val("default");
				},"text"
			);
		}
	}
	
	function cancelModify(productId,txt){
		$("#pos_"+productId).text(txt);
		$("#img_"+productId).attr("src","/etc/images/little_icon/huifu.png");
		$("#img_"+productId).attr("title","修改批注信息");
		$("#st_"+productId).val("default");
	}
	
	
	function doCertificateSearch(){
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}
	

	