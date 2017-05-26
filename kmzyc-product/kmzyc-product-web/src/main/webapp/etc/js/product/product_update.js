//动态生成SKU预览 Begin
function createProductSku(obj){
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
				childArray[j] = $($(this).next("label")[0]).html()+"^"+attrId+":"+attrName+":"+this.value;
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
			content = "<td>&nbsp;</td>";
			valueId = "";
			for(var j=0;j < newArray[i].length;j++){
				var str = newArray[i][j].split("^");
				valueId += "," + str[1];
				content += "<td align='center'>"+str[0]+"</td>";
			}
			valueId = valueId.substring(1);
			if($("#ct_sku #skuDataTable .oldSkuDataTr").find("input[type='hidden'][name='oldskuCheckedId'][value='"+valueId+"']").length==0){
				var rowLength = $(".skuDataTr").length;
				var _newRow = "<tr class='skuDataTr'>";
				_newRow += "<input type='hidden' name='skuCheckAttrs["+rowLength+"].skuCheckedId' value='"+valueId+"' />";
				_newRow += content;
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
}

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

//SKU属性值：添加按钮事件
function addSkuValue(obj){
	var _name = $(obj).find("input:eq(0)").attr("name");
	var _lab = "lab" + new Date().getSeconds() + "_" + new Date().getMilliseconds();
	var _htmlValue = '<input id="' + _lab + '" type="checkbox" name="' + _name + '" value="-1" onclick="dyneAddValue(this);" />';
	_htmlValue += '<label class="checkboxLabel" for="' + _lab + '"><input type="text" size="7" /></label>&nbsp;&nbsp;';
	$(obj).find("a").before(_htmlValue);
}

//动态添加的SKU属性值的checkBox事件
function dyneAddValue(obj){
	var _lab = $(obj).next("label");
	
	if($(_lab).find("input").length > 0 && $(_lab).find("input").val().trim() == ""){
		alert("请输入SKU属性值！");
		$(obj).attr("checked",false);
		return;
	}
	
	if($(obj).attr("checked")){
		$(obj).val("@"+$(_lab).find("input").val());
		$(_lab).html($(_lab).find("input").val());
		createProductSku(obj);
	}else{
		createProductSku(obj);
		var _html = '<input type="text" size="7" value="' + $(_lab).html() + '" />';
		$(_lab).html(_html);
	}
}

function removeSkuTr(arg,skuId){
	$(arg).remove();
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
	
	$(".skuDataTr").each(function(i){
		$(this).find("input[type='radio']").attr("name","skuCheckAttrs["+i+"].status");
		$(this).find("input[type='hidden'][name$='skuCheckedId']").attr("name","skuCheckAttrs["+i+"].skuCheckedId");
	});
	
}

//删除属性值
function delAttrValue(obj){
	$(obj).parent().parent().remove();
	return false;
}

function brandChange(){   
	$('#brandName').val($('#brandId').find("option:selected").text());
}

function categoryChange(){   
	$('#categoryName').val($('#categoryId3').find("option:selected").text());
}

var optionsForUp = { dataType: 'json', beforeSubmit: validateForm, success: createSuccessForUp};

function validateForm(){
}
function createSuccessForUp(data){
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
		$('#upForm').ajaxSubmit(optionsForUp);
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
		$('#downForm').ajaxSubmit(optionsForUp);
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
		error:function(){alert('请求失败，请稍后重试或与管理员联系！');},
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
	if($('#brandId option:selected').val()==''){
		$('#brandId').focus();
		alert('品牌必须选择!');
		return false;
	}
}

var options = { dataType: 'json', success: createSuccess};

function createSuccess(data){
	alert(data.msg);
	if(data.result==true){ 
		$("#listfrm").submit();
	}else{
		$("#submitButton").removeAttr("disabled");
	}
}

function submitOperation(){
	if($("#categoryId3").val()==""){
		alert("三级类目必须选择!");
		return false;
	}
	
	if($("#autocomplete").val()==""){
		alert("产品品牌必填！");
		return false;
	}
	
	if($("#productName").val()==""){
		alert("产品名称必填！");
		return false;
	}
	if($("#productTitle").val()==""){
		alert("产品主标题必填！");
		return false;
	}
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
	
	flag=true;
	var showMsg = "";

	if ($("#ct_dyna table tr").length > 1) {
		$("#ct_dyna table tr").each(function(i){
			if(this.className == "isOtcTr"){
				return true;
			}else{
				$(this).find("input[type='text']").each(function(i){
					if (this.value.replace(/(^\s*)|(\s*$)/g, "") == ""
						|| this.value == "属性名" || this.value == "属性值") {
						showMsg += "请输入【自定义属性中】的属性名或属性值！";
						flag = false;
						return false;
					}
				});
			}
		});
	}
	
	if(!flag){
		alert(showMsg);
		return false;
	}
	showMsg = "";
	
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
	
	if(flag == false){
		alert(showMsg);
		return false;
	}
	
	flag = true;
	showMsg = "";
	if($("#ct_sku table tr").length==0){
		if(!confirm("该产品无SKU规格，是否继续？")){
			flag = false;
			return false;
		}
	}else{
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

    var intro = $("#editor_id").val(), editor_change = $('.editor_change');
    editor_change.html(intro);
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
	$.ajax({
		async : false,
		url : "checkIntroduce.action?checkedIntro=" + intro,
		type : "POST",
		dataType : "json",
		success : function(json) {
			rows = json;
		}
	});
	
	if (rows == 'wrong') {
		alert("请输入文明语言");
		return false;
	}

	flag = true;
	var files = $("#certificateTR").find("input[type='file'][name='certificateFiles'][value!='']");
	if(files.length != 0){
		files.each(function(i){
			if(this.value != ""){
				var _type = this.value.substring(this.value.lastIndexOf('.')+1).toLowerCase();
				if("exe" ==_type || "sql" ==_type || "jsp" ==_type || "js" ==_type || "java" ==_type ){
					alert("文件类型错误！");
					flag = false;
					return false;
				}
			}
		});
	}
	if(!flag){
		return;
	}
	
	flag = true;
	var fileNames = $("#certificateTR").find(".exsitFileName");
	if(fileNames.length > 0 && files.length > 0){
		fileNames.each(function(i,fileName){
			files.each(function(j,file){
				if(file.value.indexOf(fileName.value) >= 0){
					alert("请不要使用相同文件名的文件！");
					flag = false;
					return false;
				}
			});
			
			if(!flag){
				return false;
			}
		});
	}
	if(!flag){
		return;
	}

	var k = $("#indexCount").val();
	var n = k;
	$("input[name$='product.productAttrDraftList.productAttrName']").each(function(i){
		$(this).attr("name","product.productAttrDraftList["+(k++)+"].productAttrName");
	});
	$("input[name$='product.productAttrDraftList.productAttrValue']").each(function(j){
		$(this).attr("name","product.productAttrDraftList["+(n++)+"].productAttrValue");
	});
	
	$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
		this.disabled = false;
	});
	
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

    //禁用掉提交按钮
    $("#submitButton").attr("disabled","disabled");
	$("#frm").ajaxSubmit(options);
}

function openSkuListDialog(productSkuId){
	popDialog("/app/toUpdateProdImageDraft.action?type=image&productSkuId="+productSkuId,"查看SKU商品","1130px","430px");	
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

function checkIntro(button) {
	var intro = $("#editor_id").val(),
    editor_change = $('.editor_change');
	editor_change.html(intro);
	$.each(editor_change.find('img'),function(i){
	    var t = $(this), src = t.attr('src');
	    t.attr('data-original',src);
	    t.attr('src','http://jscss.km1818.com/res/images/default__logo.png');
	    t.addClass('lazy');
	});

	var stzt = editor_change.find("a[name='stzt']");
	var ldbz = editor_change.find("a[name='ldbz']");
	var zljb = editor_change.find("a[name='zljb']");
	if(stzt.length){
	    stzt.attr('id','stzt');
	}
	if(ldbz.length){
	    ldbz.attr('id','ldbz');
	}
	if(zljb.length){
	    zljb.attr('id','zljb');
	}
	
	$("#editor_lazy").val(editor_change.html());

	var rows;
	$.ajax({
        async:false,
        url:"checkIntroduce.action?checkedIntro="+intro,
        type:"POST",
        dataType:"json",
        success:function(json){
              rows = json;
        }
    });
	if (rows == 'wrong') {
		alert("请输入文明语言");
		return false;
	}
	document.getElementById("frmintro").submit();
} 

function change1(sourceCategoryId,targetCategoryId){
	
	var categoryHtml = '';
	if(targetCategoryId=='categoryId2'){
		//首先，将二级和三级的清空
		$('#categoryId2').empty().append($("<option value='0'>--二级类目--</option>"));
		$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
		categoryHtml = '<option value="0">--二级类目--</option>';
	}else if(targetCategoryId=='categoryId3'){
		$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
		categoryHtml = '<option value="0">--三级类目--</option>';
	}
	$.ajax({
		dataType:'json',
		url:'/app/selectCategory.action?id='+$('#'+sourceCategoryId).val(),
		error:function(){alert('请求失败，请稍后重试或与管理员联系！');},
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
		error:function(){alert('请求失败，请稍后重试或与管理员联系！');},
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

$(document).ready(function() {
	//允许上架后可以修改品牌
	var product_add_countriesArray;
	
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
	function saveInfor() {
		 var intorduct=$("#editor_id").html(); 
	}

	$("#ct_sku input[type='checkbox'][name$='productAttrValues']").click(function(){
		createProductSku(this);
	});
	
	$(".newAttr").each(function(i){
		var _name=$(this).parent().find("input:eq(0)").attr("name");
		var _lab = "lab" + i;
		$(this).attr({name:_name,checked:"checked",disabled:"disabled",id:_lab}).val("@"+$(this).val()).next().attr("for",_lab);
	});

	$(".isCheckBoxListTr").each(function(i){
		if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
			$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",false);
		}else{
			$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",true);
		}
	});
	
	$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
		this.disabled = true;
	});
	
});