

$(document).ready(function() {
	$('.ct:gt(0)').hide();
	var hdw = $('ul li');
	hdw.click(function() {
		$(this).addClass('visit').siblings().removeClass();
		var hdw_index = hdw.index(this);
		$('.ct').eq(hdw_index).show().siblings().hide();
	});
	
	
	hdw.hover(
	  function () {
	    $(this).addClass("hover");
	  },
	  function () {
	    $(this).removeClass("hover");
	  }
	);
	
	if($("#channel").val() == "ZYC"){
		$("#approvalNoText").text("产地");
		$("#approvalNo").val($("#approvalNoHidden").val());
		$("#productDescText").text("联系方式");
		$("#productDesc").val($("#productDescHidden").val());
	}else{
		$("#approvalNoText").text("批准文号");
		$("#productDescText").text("备注");
	}
	
	//添加属性值
	$("#addAttrValue").click(function () {
	  var AttrValueTR = $("#AttrValueTR");
	  var AttrValueTRHTML = '';
	  AttrValueTRHTML += '<tr>';
	  AttrValueTRHTML += '<th align="right"><font color="red">*</font><input type="text" class="{checkDynaName:true}" style="color:#999" name="product.productAttrDraftList.productAttrName" maxlength="40" value="属性名" onclick="javascript:if(this.value==\'属性名\') {this.value=\'\';this.style.color=\'#000\'}" onblur="javascript:if(this.value==\'\'){this.value=\'属性名\';this.style.color=\'#999\'} "/></th> ';
	  AttrValueTRHTML += '<td align="left"><input type="text" style="color:#999" class="{checkDynaValue:true}" name="product.productAttrDraftList.productAttrValue" maxlength="40" value="属性值" onclick="javascript:if(this.value==\'属性值\') {this.value=\'\';this.style.color=\'#000\'}" onblur="javascript:if(this.value==\'\'){this.value=\'属性值\';this.style.color=\'#999\'} "/>';
	  AttrValueTRHTML += '<a href="#" onclick="delAttrValue(this);">&nbsp;删除&nbsp;</a></td>';
	  AttrValueTRHTML += "</tr>";
	  AttrValueTR.after(AttrValueTRHTML);
	});
	function back() {
		history.back();
	}
	function checkSubmit() {
		editor.sync();
		$("form").submit();
	}
	function saveInfor()
	{
		 var intorduct=$("#editor_id").html(); 
	}
	
	if($("#opType").val()!=1){
		$(".isCheckBoxListTr").each(function(i){
			if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
				$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",false);
			}else{
				$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",true);
			}
		});
	}else{
		$(".isCheckBoxListTr").each(function(i){
			if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length == 0){
				return false;
			}
		});
	}
	
	$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
		this.disabled = true;
	});
	
	
	
	//动态生成SKU预览 Begin
	$("#ct_sku input[type='checkbox'][name$='productAttrValues']").click(function(){
		if(this.checked){
			if($(this).parent().find("input[type='checkbox'][name$='productAttrValues']:checked").length ==1 ){
				$(".oldSkuDataTr").each(function(i){
					$("#toDeleteSkuIds").val($("#toDeleteSkuIds").val()+$(this).find(".hideProductSkuId").val()+",");
				});
				$(".oldSkuDataTr").remove();
			}
		}
		
		$(".skuDataTr").remove();
		
		var array = new Array();
		var totalLength = 1;
		var index = 0;
		
		//循环每个属性
		$("#ct_sku .isCheckBoxListTr").each(function(i){
			var attrName = $(this).find("input[type='hidden'][name$='productAttrName']")[0].value;
			
			var attrId = $(this).find("input[type='hidden'][name$='relateAttrId']")[0].value;
			
			if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
				var childArray = new Array();
				//循环该属性下被选中的值
				$(this).find("input[type='checkbox'][name$='productAttrValues']:checked").each(function(j){
					childArray[j] = attrName + "：" + $($(this).next("label")[0]).html()+"^"+attrId+":"+ attrName +":"+this.value;
				});
				totalLength = totalLength * childArray.length;
				array[index] = childArray;
				++index;
			}
		});
		
		if(array.length > 0){
			var newArray = cross(array,totalLength);
			var content = "";
			var valueId = "";
			//生成预览图
			for(var i=0;i < newArray.length;i++){
				content = "";
				valueId = "";
				for(var j=0;j < newArray[i].length;j++){
					var str = newArray[i][j].split("^");
					valueId += "," + str[1];
					content += str[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				valueId = valueId.substring(1);
				if($("#ct_sku #skuDataTable .oldSkuDataTr").find("input[type='hidden'][name='oldskuCheckedId'][value='"+valueId+"']").length==0){
					var rowLength = $(".skuDataTr").length;
					var _newRow = "<tr class='skuDataTr'>";
					_newRow += "<input type='hidden' name='skuCheckAttrs["+rowLength+"].skuCheckedId' value='"+valueId+"' />";
					_newRow += "<td align='center'>";
					_newRow += content;
					_newRow += "</td>";
					_newRow += "<td align='center'>";
					_newRow += "<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode);' class='btnStyle' />";
					_newRow += "</td>";
					_newRow += "<td align='center'>";
					_newRow += "<input id='productSku"+rowLength+"_status0' type='radio' value='0' name='skuCheckAttrs["+rowLength+"].status'>";
					_newRow += "<label for='productSku"+rowLength+"_status0'>无效</label>";
					_newRow += "<input id='productSku"+rowLength+"_status1' type='radio' checked='checked' value='1' name='skuCheckAttrs["+rowLength+"].status'>";
					_newRow += "<label for='productSku"+rowLength+"_status1'>有效</label>";
					_newRow += "</td>";
					_newRow += "</tr>";
					
					$("#skuDataTable").append(_newRow);
				}
			}
		}
		
//		if($("#defaultSku").length > 0 && $(".skuDataTr").length > 0){
//			$("#toDeleteSkuIds").val($("#toDeleteSkuIds").val()+$("#defaultSku").val()+",");
//			$("#defaultSku").parent().parent().remove();
//		}
	});
	
	function cross(sourceArray,totalLength){
		// 笛卡尔积索引记录
		var record = new Array(sourceArray.length);
		for(var k=0;k<sourceArray.length;k++){
			record[k] = 0;
		}
		var results = new Array();
		// 产生笛卡尔积
		for (var i = 0; i < totalLength; i++) {
			var row = new Array();
			// 生成笛卡尔积的每组数据
			for (var index = 0; index < record.length; index++) {
				row.push(sourceArray[index][record[index]]);
			}
			results[i] = row;
			crossRecord(sourceArray, record, sourceArray.length - 1);
		}
		return results;
	}
	
	function crossRecord(sourceArray, record, level){
		record[level] = record[level] + 1;
		if (record[level] >= sourceArray[level].length && level > 0) {
			record[level] = 0;
			crossRecord(sourceArray, record, level - 1);
		}
	}
	//动态生成SKU预览 End
	
	
	//提交自定义表单数据
	$("#submitDefinitionButton").click(function () {
		
		var flag = true;
		var showMsg = "";
		
		if ($("#ct_dyna table tr").length > 1) {
			$("#ct_dyna table tr input[type='text']").each(
				function(i) {
					if (this.value.replace(/(^\s*)|(\s*$)/g, "") == ""
							|| this.value == "属性名" || this.value == "属性值") {
						showMsg += "请输入【自定义属性中】的属性名或属性值！";
						flag = false;
						return false;
					}
			});
		}
		
		if(!flag){
			alert(showMsg);
			return;
		}
		
		
		$("input[name$='product.productAttrDraftList.productAttrName']").each(function(i){
			$(this).attr("name","product.productAttrDraftList["+i+"].productAttrName");
		});
		$("input[name$='product.productAttrDraftList.productAttrValue']").each(function(i){
			$(this).attr("name","product.productAttrDraftList["+i+"].productAttrValue");
		});
		
		$("#dynaAttrfrm").submit();
		
	});
	
});

function removeSkuTr(arg,skuId){
	
	var flag = true;
	
	if(undefined==skuId){
		$(arg).remove();
	}else{
		if(confirm("确定删除该SKU商品吗？")){
			$(arg).remove();
			$("#toDeleteSkuIds").val($("#toDeleteSkuIds").val()+skuId+",");
		}else{
			flag = false;
		}
	}
	
	if(flag){
		var checkedValue = "";
		$("#ct_sku .isCheckBoxListTr").find("input[type='checkbox'][name$='productAttrValues']:checked").each(function(){
			var flag = false;
			var _checkedBox = this;
			$("#skuDataTable input[type='hidden'][name$='skuCheckedId']").each(function(j){
				var values = this.value.split(",");
				for(var i = 0;i<values.length;i++){
					checkedValue = values[i].substring(values[i].lastIndexOf(":")+1);
					if(checkedValue == _checkedBox.value){
						flag = true;
						break;
					}
				}
				
				if(flag){
					return false;
				}
			});
			this.checked = flag;
			if(!flag){
				this.disabled = false;
			}
		});
	}
	$(".skuDataTr").each(function(i){
		$(this).find("input[type='radio']").attr("name","skuCheckAttrs["+i+"].status");
		$(this).find("input[type='hidden'][name$='skuCheckedId']").attr("name","skuCheckAttrs["+i+"].skuCheckedId");
	});
	
	$(".oldSkuDataTr").each(function(i){
		$(this).find("input[type='radio'][name$='status']").attr("name","product.productSkuDrafts["+i+"].status");
		$(this).find("input[type='hidden'][name$='productSkuId']").attr("name","product.productSkuDrafts["+i+"].productSkuId");
	});
	
	
}

//删除属性值
function delAttrValue(obj){
	$(obj).parent().parent().remove();
	return false;
}


function categoryChange(){   
	$('#categoryName').val($('#categoryId3').find("option:selected").text());
}

var options = { dataType: 'json', beforeSubmit: validateForm, success: createsuccess};

function validateForm(){
	
}
function createsuccess(data){
	alert(data.msg);
	window.location.href = '/app/productShow.action';
}

function createUp() {
	if(confirm('确定上架已选产品吗？')){
		var chk_value =[]; 
		var chk_tmp = [];
		var html = "";
		  $('input[name="productIdChk"]:checked').each(function(){  
		   	chk_value.push($(this).val());    
		  });  
		for(var i=0;i<chk_value.length;i++){
			chk_tmp = chk_value[i].split("_");
			if(chk_tmp[1]!='2') {
				alert('请检查产品状态是否为已审核状态！');
				return;
			}
			html += '<input type="hidden" name="upProductId" value="'+chk_tmp[0]+'"/><input type="hidden" name="upChannel" value="'+chk_tmp[2]+'"/>';
		}
		
		$('#upForm').html(html);
		$('#upForm').ajaxSubmit(options);
	}	
}

function createDown() {
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
		$('#downForm').ajaxSubmit(options);
	}
}

function change1(sourceCategoryId,targetCategoryId){
	var categoryHtml = '';
	if(targetCategoryId=='categoryId2'){
		categoryHtml = '<option value="0">--二级类目--</option>';
	}else if(targetCategoryId=='categoryId3'){
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

function checkSelected(){
	if($('#categoryId3 option:selected').val()==''){
		$('#categoryId3').focus();
		alert('三级类目必须选择!');
		return false;
	}
	if($('#channel option:selected').val()==''){
		$('#channel').focus();
		alert('渠道必须选择!');
		return false;
	}
}

function baseInfoSubmit(){
	
	if(!$("#baseInfoFrm").validate().form()){
		return false;
	}else{
		if($("#autocomplete").length > 0){
			var flag = true;
			var _txt = $("#autocomplete").val();
			$.each(product_add_countriesArray,function(i,value){
				if(_txt == value.value){
					$("#brandId").val(value.data);
					$("#brandName").val(value.value);
					flag = false;
					return false;
				}
			});
			
			if(flag){
				alert("品牌名输入错误，没有【"+_txt+"】品牌！");
				return false;
			}
		}
		
		if($("#categoryId3").val()==""){
			alert("三级类目必须选择!");
			return false;
		}
		if($("input[type='checkbox'][name='ckChannel']:checked").length==0){
			alert("请选择产品渠道！");
			return false;
		}
		
		var _channel = "";
		$("input[type='checkbox'][name='ckChannel']:checked").each(function(){
			_channel += "," + this.value;
		});
		_channel = _channel.substring(1);
		$("#channel").val(_channel);
		
		$("#baseInfoFrm").submit();
	}
}

function baseAttrFrmSubmit(){
	if(!$("#baseAttrFrm").validate().form()){
		return false;
	}else{
		var showMsg = "";
		var flag = true;
		if($("#ct_cate table tr").length>0){
			$("#ct_cate table tr").each(function(i){
				if($(this).children("th").children("font").length>0 
					&& (($(this).children("td").children("input[type='radio']").length>0 && $(this).children("td").children("input[type='radio']:checked").length==0)
							|| ($(this).children("td").children("input[type='checkbox']").length>0 && $(this).children("td").children("input[type='checkbox']:checked").length==0)
							|| ($(this).children("td").children("input[type='text']").length>0 && $(this).children("td").children("input[type='text']").val() == ""))){
					var attrName = $(this).children("th").text();
					attrName = attrName.substring(attrName.indexOf("*")+1,attrName.indexOf("："));
					showMsg += "请选择或输入基本属性【"+attrName+"】！\r\n";
					flag = false;
				}
			});
		}
		if(flag){
			$("#baseAttrFrm").submit();
		}else{
			alert(showMsg);
		}
	}
}

function skuAttrSubmit(){
	
	var flag = true;
	var showMsg = "";
	
	if($("#ct_sku table tr").length > 0){
		$("#ct_sku table tr").each(function(i){
			if($(this).children("td").children("input[type='checkbox']").length>0
					&&$(this).children("td").children("input[type='checkbox']:checked").length==0){
				
				if($(this).children("th").children("font").length>0){
					var attrName = $(this).children("th").text();
					attrName = attrName.substring(attrName.indexOf("*")+1,attrName.indexOf("："));
					showMsg += "请选择SKU规格【"+attrName+"】！\r\n";
				}
				
				flag = false;
			}
		});
		if(flag==false){
			
			if(showMsg!=""){
				alert(showMsg);
				return false;
			}
			
			if(!confirm("有SKU规格未选择，是否继续？")){
				flag = false;
				return false;
			}else{
				flag = true;
			}
		}
	}
	if(flag == false){
		return false;
	}else{
		$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
			this.disabled = false;
		});
		
		//$("#SkuAttrFrm").submit();
		$.post(
			'/app/productDraftUpdate.action',
			$("#SkuAttrFrm").serializeArray(),
			function(html){
				$("#ct_sku").html(html);
			}
		);
	}
}


function certificateInfoSubmit(){
	var flag = false;
	var files = $("#certificateTR").find("input[type='file'][name='certificateFiles'][value!='']");
	if(files.length != 0){
		files.each(function(i){
			if(this.value != ""){
				var _type = this.value.substring(this.value.lastIndexOf('.')+1).toLowerCase();
				if("exe" ==_type || "sql" ==_type || "jsp" ==_type || "js" ==_type || "java" ==_type ){
					alert("文件类型错误！");
					flag = true;
					return false;
				}
			}
		});
	}
	if(flag){
		return;
	}
	flag = false;
	var fileNames = $("#certificateTR").find(".exsitFileName");
	if(fileNames.length > 0 && files.length > 0){
		fileNames.each(function(i,fileName){
			files.each(function(j,file){
				if(file.value.indexOf(fileName.value) >= 0){
					alert("请不要使用相同文件名的文件！");
					flag = true;
					return false;
				}
			});
			
			if(flag){
				return false;
			}
		});
	}
	if(flag){
		return;
	}
	if($("input[type='file'][name='certificateFiles']").length != 0){
		$("input[type='file'][name='certificateFiles']").each(function(i){
			if(this.value != ""){
				var _html = '<input type="hidden" name="certificateType" value="'+$(this).attr("data-fileType")+'" />';
				if($(this).attr("data-pscId")!=""){
					_html += '<input type="hidden" name="certificateFileIds" value="'+$(this).attr("data-pscId")+'" />';
				}
				$(this).parent().append(_html);
			}
		});
	}
	$("#certificateInfoButton").attr("disabled","disabled");
	 $.ajaxFileUpload({  
          url:'/app/productDraftUpdate.action',            // 需要链接到服务器地址
          data:$("#certificateFrm").serializeArray(),
          secureuri:false,  
          fileElementId:["certificateFiles"],                        // 文件选择框的id属性
          dataType: 'html',                                     // 服务器返回的格式，可以是json
          success: function (data, status)            // 相当于java中try语句块的用法
          {      
        	  $("#certificateProductDiv").html(data);
          },  
          error: function (data, status, e)            // 相当于java中catch语句块的用法
          {  
             alert("系统发生错误，请稍后再试或联系管理员！");
             $("#certificateInfoButton").attr("disabled","");
          }  
	 });  
	
	
//	$("#certificateFrm").submit();
//	
//	$.ajax({
//		async:false,
//		type:"POST",
//		url:"toUpdateProductCertificateSingle.action",
//		data:"productId="+$("certificateProductId").val(),
//		dataType:"html",
//		success:function(data){
//			$("#certificateProductDiv").html(data);
//		}
//	});
//	
}






function openSkuListDialog(productSkuId){
	popDialog("/app/toUpdateProdImageDraft.action?type=image&productSkuId="+productSkuId ,"查看SKU商品","1130px","430px");	
}



//返回我的桌面界面
function gotoList(){
    $('#listfrm').submit();
}

function gotoAdd(){
    location.href="/app/toProductCategory.action";
}

function gotoUpdate(id){
    location.href="/app/toProductUpdate.action?id="+id;
}

function gotoView(id){
	location.href="";
}

function gotoListForView(){
    $('#listfrm').submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function checkIntro(button)
{
	var intro = $("#editor_id").val(),
    editor_change = $('.editor_change');
	editor_change.html(intro);
//console.log(editor_change.html());
	$.each(editor_change.find('img'),function(i){
	    var t = $(this),
	        src = t.attr('src');
	    t.attr('data-original',src);
	    t.attr('src','http://jscss.kmb2b.com/res/images/default__logo.png');
	    t.addClass('lazy');
	
	});
	var stzt = editor_change.find("a[name='stzt']");
    var ldbz = editor_change.find("a[name='ldbz']");
    var zljb = editor_change.find("a[name='zljb']");
	var yxjs = editor_change.find("a[name='yxjs']");
	var syff = editor_change.find("a[name='syff']");
	var zzysz = editor_change.find("a[name='zzysz']");
    if(stzt.length){
        stzt.attr('id','stzt');
    }
    if(ldbz.length){
        ldbz.attr('id','ldbz');
    }
    if(zljb.length){
        zljb.attr('id','zljb');
    }
	if(yxjs){
		yxjs.attr('id','yxjs');
	}
	if(syff){
		syff.attr('id','syff');
	}
	if(zzysz){
		zzysz.attr('id','zzysz');
	}
	
	$("#editor_lazy").val(editor_change.html());
	
	var rows;
	var intro = $("#editor_id").val();
	$.ajax({
		async:false,
		url:"checkIntroduce.action?checkedIntro="+intro,
		type:"POST",
		dataType:"json",
		success:function(json){
			  rows = json;
		}
	});
	if (rows == 'wrong')
	{
		alert("请输入文明语言");
		return false;
	}
	document.getElementById("frmintro").submit();
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