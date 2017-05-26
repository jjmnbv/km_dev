$(document).ready(function() {
	var product_add_countriesArray;
	
	
	jQuery.validator.addMethod("checkDynaName",function(value, element){
		return value!="属性名" ; 
	},"请输入属性名");
	
	jQuery.validator.addMethod("checkDynaValue",function(value, element){
		return value!="属性值"; 
	},"请输入属性值");
	
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
	
	
	$("#channel").change(function(){
		if(this.value == "ZYC"){
			$("#approvalNoText").text("产地");
			$("#approvalNo").val("");
			$("#productDescText").text("联系方式");
			$("#productDesc").val("");
		}else{
			$("#approvalNoText").text("批准文号");
			$("#approvalNo").val("");
			$("#productDescText").text("备注");
			$("#productDesc").val("");
		}
	}).change();
	
	
	
	//添加属性值
	$("#addAttrValue").click(function () {
	  var AttrValueTR = $("#AttrValueTR");
	  var AttrValueTRHTML = '';
	  AttrValueTRHTML += '<tr>';
	  AttrValueTRHTML += '<th align="right"><font color="red">*</font><input type="text" style="color:#999" name="product.productAttrDraftList.productAttrName" maxlength="20" value="属性名" onclick="javascript:if(this.value==\'属性名\') {this.value=\'\';this.style.color=\'#000\'}" onblur="javascript:if(this.value==\'\'){this.value=\'属性名\';this.style.color=\'#999\'} "/></th> ';
	  AttrValueTRHTML += '<td align="left"><input type="text" style="color:#999" name="product.productAttrDraftList.productAttrValue" maxlength="40" value="属性值" onclick="javascript:if(this.value==\'属性值\') {this.value=\'\';this.style.color=\'#000\'}" onblur="javascript:if(this.value==\'\'){this.value=\'属性值\';this.style.color=\'#999\'} "/>';
	  AttrValueTRHTML += '<a href="#" onclick="delAttrValue(this);">&nbsp;删除&nbsp;</a></td>';
	  AttrValueTRHTML += "</tr>";
	  AttrValueTR.append(AttrValueTRHTML);
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
	
	//动态生成SKU预览 Begin
	$("#ct_sku input[type='checkbox'][name$='categoryAttrValues']").click(function(){
		
		$(".skuDataTr").remove();
		
		var array = new Array();
		var totalLength = 1;
		var index = 0;
		
		//循环每个属性
		$("#ct_sku .isCheckBoxListTr").each(function(i){
			var attrName = $(this).find("input[type='hidden'][name$='categoryAttrName']")[0].value;
			
			var attrId = $(this).find("input[type='hidden'][name$='categoryAttrId']")[0].value;
			
			if($(this).find("input[type='checkbox'][name$='categoryAttrValues']:checked").length>0){
				var childArray = new Array();
				//循环该属性下被选中的值
				$(this).find("input[type='checkbox'][name$='categoryAttrValues']:checked").each(function(j){
					childArray[j] = attrName + "：" + $($(this).next("label")[0]).html()+"^"+attrId+":"+attrName+":"+this.value;
				});
				totalLength = totalLength * childArray.length;
				array[index] = childArray;
				++index;
			}
		});
		
		if(array.length > 0){
			var newArray = cross(array,totalLength);
			//生成预览图
			for(var i=0;i < newArray.length;i++){
				var content = "";
				var valueId = "";
				for(var j=0;j < newArray[i].length;j++){
					var str = newArray[i][j].split("^");
					valueId += "," + str[1];
					content += str[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				var rowLength = $(".skuDataTr").length;
				var _newRow = "<tr class='skuDataTr'>";
				_newRow += "<input type='hidden' name='skuCheckAttrs["+rowLength+"].skuCheckedId' value='"+valueId.substring(1)+"' />";
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
	
	
	
/*	//提交表单数据
	$("#submitButton").click(function () {
		$("input[name$='product.productAttrList.productAttrName']").each(function(i){
			$(this).attr("name","product.productAttrList["+i+"].productAttrName");
		});
		$("input[name$='product.productAttrList.productAttrValue']").each(function(i){
			$(this).attr("name","product.productAttrList["+i+"].productAttrValue");
		});
		$("#frm:visible").submit();
	});*/
	
});
function removeSkuTr(arg){
	$(arg).remove();
	var checkedValue = "";
	$("#ct_sku .isCheckBoxListTr").find("input[type='checkbox'][name$='categoryAttrValues']:checked").each(function(){
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
	});
	$(".skuDataTr").each(function(i){
		$(this).find("input[type='radio']").attr("name","skuCheckAttrs["+i+"].status");
		$(this).find("input[type='hidden'][name$='skuCheckedId']").attr("name","skuCheckAttrs["+i+"].skuCheckedId");
	});
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

function submitOperation(){
//	var voptions = {
//		rules: {
//				"product.brandId":{required:true},
//				"product.name":{required:true,maxlength:128,unusualChar:true},
//				"product.channel":{required:true},
//				"product.drugCateCode":{required:true}
//     	},
//        success: function (label){
//        	label.removeClass("checked").addClass("checked");
//        }
//	};
	
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
	if($("input[type='checkbox'][name='ckChannel']:checked").length==0){
		alert("请选择产品渠道！");
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
	
	
//	if(!$("#frm").validate().form()){
//		return false;
//	}
	
	
	
	flag = true;
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
	
//	$("input[name$='product.productAttrList.productAttrName']").each(function(i){
//		if(this.value==""||this.value=="属性名"){
//			$(this).parent().parent().remove();
//		}
//	});
//	
//	$("input[name$='product.productAttrList.productAttrValue']").each(function(j){
//		if(this.value==""||this.value=="属性值"){
//			$(this).parent().parent().remove();
//		}
//	});
	
	flag = true;
	if($("input[type='file'][name='certificateFiles']").length != 0){
		$("input[type='file'][name='certificateFiles']").each(function(i){
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
	
	if($("input[type='file'][name='certificateFiles']").length != 0){
		$("input[type='file'][name='certificateFiles']").each(function(i){
			if(this.value != ""){
				var _html = '<input type="hidden" name="certificateType" value="'+$(this).attr("data-mapKey")+'" />';
				$(this).parent().append(_html);
			}
		});
	}
	
	$("input[name$='product.productAttrDraftList.productAttrName']").each(function(i){
		$(this).attr("name","product.productAttrDraftList["+i+"].productAttrName");
	});
	$("input[name$='product.productAttrDraftList.productAttrValue']").each(function(j){
		$(this).attr("name","product.productAttrDraftList["+j+"].productAttrValue");
	});
	
	var _channel = "";
	$("input[type='checkbox'][name='ckChannel']:checked").each(function(){
		_channel += "," + this.value;
	});
	_channel = _channel.substring(1);
	$("#channel").val(_channel);

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
	//var intro = $("#editor_id").val();
	$.ajax({
		async : false,
		url : "checkIntroduce.action?checkedIntro=" + intro,
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
	
	if(flag){
		$("#frm").submit();
	}
	
}

//删除属性值
function delAttrValue(obj){
	$(obj).parent().parent().remove();
	return false;
}

function categoryChange(){   
	$('#categoryName').val($('#categoryId3').find("option:selected").text());
}

function gotoList(){
	 location.href="/app/productShow.action";
}


