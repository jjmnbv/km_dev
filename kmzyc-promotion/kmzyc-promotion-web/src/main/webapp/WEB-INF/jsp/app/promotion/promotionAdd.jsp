<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>添加活动</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
<script language="JavaScript"
	src="/etc/js/artDialog4.1.7/artDialog.js?skin=default"
	type="text/javascript"></script>
<script language="JavaScript"
	src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js"
	type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/promotion.js"></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
.gantProduct{
width: 150px;
    display: inline-block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.emDiv, .sbDiv {
	float: left;
	position: relative;
	margin: 3px 5px 2px 0;
	white-space: nowrap;
	height: 15px;
	line-height: 15px;
	cursor: pointer;
	border-radius: 17px;
	border-style: solid;
	border-width: 1px;
	font-size: 14px;
	padding: 2px 19px;
	border-color: #edb8b8;
	background-color: #ffeaea;
	color: #c30 !important;
	display: inline-block;
	vertical-align: middle;
}

em {
	margin-left: -8px;
	vertical-align: top;
	display: inline-block;
	font-style: normal;
	text-decoration: none;
	white-space: nowrap;
	line-height: 15px;
	cursor: pointer;
	font-size: 14px;
}

.aclose, .deleteP {
	position: absolute;
	right: -2px;
	top: -1px;
	text-decoration: none;
	font-family: verdana;
	border-radius: 0 17px 17px 0;
	font-weight: bold;
	padding: 2px 5px 2px 3px;
	border-width: 1px;
	border-style: solid;
	border-color: #edb8b8 !important;
	color: #c30 !important;
}

.slabel{margin-left: 130px;}
.spanpromotiondata{
	display:block;
 	margin-top: 6px;
}
.div_index_block{
	display: block;
	margin-left: 260px;
	margin-top: 6px;
}
.div_index_block5{
	display: block;
	margin-left: 342px;
	margin-top: 6px;
}
.div_index{margin-left: 172px;}
</style>
</head>
<body>

	<s:set name="parent_name" value="'活动管理'" scope="request" />
	<s:set name="name" value="'添加活动'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">添加活动</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font
				color="red">*</font>活动标题：</th>
			<td width="80%"><input type="text"
				name="promotion.promotionTitle" id="promotionTitle" size="70"
				maxlength="70"
				value="<s:property value='promotion.promotionTitle' />"
				style="width: 400px" title="仅后台可见"
				onblur="showErrorMessage('promotionTitle','活动标题不能为空','promotionTitle')" />
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">商家类别：</th>
			<td width="80%"><s:radio
					list="#{1:'所有商家',3:'康美自营代销',2:'指定入驻商家'}" value="3"
					name="selectType" id="selectType" onchange="typeChange(this.value)"></s:radio>
				<input class="selectEm" type="button" id="selectEm"
				disabled="disabled" onClick="selectShop()" style="" value="选择">
			</td>
		</tr>

		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">所属商家：</th>
			<td width="80%" id="shopValues">
				<div id="showShopCodes">所有商家</div> <input type="hidden"
				id="shopType" name="shopType" /> <input type="hidden" id="shopCodes"
				value="1" name="shopCodes" /> <input type="hidden" id="shopNames"
				value="所有商家" name="shopNames" />
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font
				color="red">*</font>活动类型：</th>
			<td width="80%" id="shopValues"><s:select
					list="%{#request.promotionTypeMap}" listKey="key" listValue="value"
					id="promotionType" name="promotionRule.promotionType" headerKey=""
					headerValue="请选择" onchange="promotionTypeChange(this)"
					onblur="showErrorMessage('promotionType','活动类型不能为空','promotionType')"></s:select>
				<span style="font-size: 12px; color: blue" id="hint"></span></td>

		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">优惠数据：</th>
			<td width="80%" id="shopValues">
				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType11">
					<span id="promotionGift"> </span> <input type="button" name="选择赠品"
						value="选择赠品" onClick="toSelectProductForGift()">
				</div>
				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType10">
					<span style="font-size: 12px; color: red">!
						如需为每个活动商品指定特价价格，活动商品统一价格输入请留空。</span> <br> <span>价格 <input
						type="text" name="salePrice" id="salePrice" size="5" maxlength="7"
						title="请输入特价统一的价格，如果没有可以不输。" /><br>
					</span>
				</div>
				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType12">
					<span style="font-size: 12px; color: red">!						
						如需为每个活动商品指定APP特价价格，活动商品统一价格输入请留空。</span> <br> <span>价格 <input
						type="text" name="salePrice" id="salePrice12" size="5" maxlength="7"
						title="请输入特价统一的价格，如果没有可以不输。" /><br>
					</span>
				</div>
				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType8">
					<span>活动商品价格为原价的<input type="text" name="discount"
						id="discount" size="5" maxlength="6"
						title="请输入正确的百分比，录入1-100以内的整数。" />%(必填)<br>
					</span>
				</div>
				<div style="width: 50%; display: none;" class="sellUpTypeDiv"
					id="sellUpTypeDiv">
					活动库存（如有）卖光后
					<s:select list="%{#request.sellUpTypeMap}" listKey="key"
						listValue="value" id="sellUpType" name="promotion.sellUpType"></s:select>
				</div>
				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType6">
					买满
					<s:select list="#request.meetDataMap" name="meetDataType"
						listKey="key" listValue="value" id="meetDataType6"></s:select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 减免金额（元） <a
						class='man' style="display: none;" href="javascript:;"
						onclick="addDataSpan(this)">增加阶梯</a>&nbsp;&nbsp; <a class='man'
						id="lastMan6" style="display: none;" href='javascript:void(0)'
						onclick='removeDataSpan(this)'>减少阶梯</a> <span> <br>
					<br> 1.&nbsp;&nbsp;<input type="text" name="meetData1"
						id="meetData1" size="5" maxlength="6" title="请输入正确的买满金额。" /> <av>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text"
							name="prizeData1" title="请输入正确的优惠数据。"
							onclick="javascript:selectPrize(this);" id="prizeData1" size="5"
							onchange="javascript:moneyChange(this)" maxlength="6" /> <input
							type="hidden" name="prizeData1prizeData1"
							id="prizeData1prizeData1" /> </av>
					</span>
				</div>

				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType3">
					买满
					<s:select list="#request.meetDataMap" name="meetDataType"
						listKey="key" listValue="value" id="meetDataType3"></s:select>
						<label class ="slabel">赠送商品</label> <a
						class='man' style="display: none;" href="javascript:;"
						onclick="addDataSpan(this)">增加阶梯</a>&nbsp;&nbsp; <a class='man'
						id="lastMan3" style="display: none;" href='javascript:void(0)'
						onclick='removeDataSpan(this)'>减少阶梯</a> 
						<span span_index="span_index1" class="spanpromotiondata">
						  <label>1.</label>
						  <input type="text" name="meetData1" id="meetData1" size="5" maxlength="6" title="请输入正确的买满金额。" />
						  <av id="div_index1" class="div_index">
							<input type="text" name="entity1"
							title="请输入正确的优惠数据。" id="entity1" size="20"
							onchange="javascript:moneyChange(this)" maxlength="128"
							readonly="readonly" /><label>x</label> <input type="text" value="" name="num1"
							id="num1" size="4" maxlength="128" /> 件 <input type="hidden"
							name="entity1entity1" id="entity1entity1" /> <a class='man'
							data_id="div_index1" style="display: none;"
							href='javascript:void(0)' onclick='removeDataAv(this)'>删除</a>
						&nbsp;&nbsp; <a class='man' style="display: none;"
							href="javascript:;" class="nextBtn" id="selectProduct1"
							onclick="toSelectProduct(this)">选择商品</a> <%--
				，共<input type="text" name="num1" id="num1" class="productCount" size="3" maxlength="6" title="请输入正确的数字,空值表示不限制"/>件。&nbsp;&nbsp;&nbsp;&nbsp;
				 --%> </av>
					</span>

				</div>

				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType4">
					买满
					<s:select list="#request.meetDataMap" name="meetDataType"
						listKey="key" listValue="value" id="meetDataType4"></s:select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 送优惠券 <a
						class='man' style="display: none;" href="javascript:;"
						onclick="addDataSpan(this)">增加阶梯</a>&nbsp;&nbsp; <a class='man'
						id="lastMan4" style="display: none;" href='javascript:void(0)'
						onclick='removeDataSpan(this)'>减少阶梯</a> <span> <br>
					<br> 1.<input type="text" name="meetData1" id="meetData1"
						size="5" maxlength="6" title="请输入正确的买满金额。" /> <av>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="entity1"
							title="请输入正确的优惠数据。" onClick="javascript:selectPrize(this);"
							id="entity1" size="20" onChange="javascript:moneyChange(this)"
							maxlength="128" readonly /> <input type="hidden"
							name="entity1entity1" id="entity1entity1" />

						&nbsp;&nbsp;&nbsp;&nbsp; </av>
					</span>
				</div>

				<div style="width: 50%; display: none;" class="promotionType"
					id="promotionType5">
					买满
					<s:select list="#request.meetDataMap" name="meetDataType"
						listKey="key" listValue="value" id="meetDataType5"></s:select>
					<label class ="slabel"> 可换购数量</label>
					<label class ="slabel"> 可换购商品</label><a
						class='man' style="display: none;" href="javascript:;"
						onclick="addDataSpan(this)">增加阶梯</a>&nbsp;&nbsp; <a class='man'
						id="lastMan5" style="display: none;" href='javascript:void(0)'
						onclick='removeDataSpan(this)'>减少阶梯</a>
						 <span span_index="span_index1" class="spanpromotiondata"> 
					     <label>1.</label><input type="text" name="meetData1"
						id="meetData1" size="5" maxlength="6" title="请输入正确的买满金额。" />
						<av id="div_index2" class="div_index"> <input type="text"
							value="" name="num5" id="num5" size="4" maxlength="128" /> 件 <input
							type="text" name="prizeData1" id="prizeData1" size="5"
							maxlength="6" title="请输入正确的商品单价。" /><label>元换购 </label><input type="text"
							name="entity1" title="请输入正确的优惠数据。" id="entity1" size="20"
							onchange="javascript:moneyChange(this)" maxlength="128"
							readonly="readonly" /> <input type="hidden" name="entity1entity1"
							id="entity1entity1" /> <a class='man' data_id="div_index1"
							style="display: none;" href='javascript:void(0)'
							onclick='removeDataAv(this)'>删除</a> &nbsp;&nbsp;<a class='man'
							style="display: none;" href="javascript:;" class="nextBtn"
							id="selectProduct1" onclick="toSelectProduct(this)">选择商品</a> <%--
				，共	<input type="text" name="num1" id="num1" class="productCount" size="3" maxlength="6" title="请输入正确的数字,空值表示不限制"/>件。&nbsp;&nbsp;&nbsp;&nbsp;
				 --%> </av>

					</span>
				</div> </span>

			</td>
		</tr>

	</table>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><input type="button" value="下一步" onclick="javascript:submit();" class="btn-custom btnStyle_09">&nbsp;&nbsp; <input
				type="button" class="backBtn" onClick="javascript:history.back(-1);" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>

</body>

<script LANGUAGE="JavaScript">

var initHtml3 =  $("#promotionType3").html(); //切换商家数据时，初始化满赠已选列表使用
var initHtml5 =  $("#promotionType5").html(); //切换商家数据时，初始化加价购已选列表使用

//切换商家时初始化加价购、满赠列表
function initData(){
	$("#promotionType3").html(initHtml3); 
	$("#promotionType5").html(initHtml5);
}

function typeChange(selectValue){
// 	debugger;
	//var selectValue = $(o).val();
// 	$("#kangmei").removeAttr("checked");
	$("#errorshopCodes").remove();
	if(selectValue==1){
		$('#shopNames').val('');
		$('#showShopCodes').html('');
		$("#shopCodes").val("");
		$("#shopType").val("1");
		$('#shopKm').css('display','none');
		$("#selectEm").attr("disabled","disabled");
	}else	if(selectValue==3){
		$("#shopCodes").val("");
		$("#shopType").val("3");
		$("#shopNames").val("商家后期选择参与");
		$('#showShopCodes').css('display','none');
		$('#shopKm').css('display','none');
		$("#selectEm").attr("disabled","disabled");
	}else{
		$("#shopType").val("2");
		$('#shopKm').css('display','block');
		$("#shopType").val(selectValue);
		$("#shopCodes").val("");
		$("#shopNames").val("");
		$('#showShopCodes').html('');
		$('#showShopCodes').css('display','block');
		$("#selectEm").removeAttr("disabled");
	}
	var obj = document.getElementById("promotionType");
	obj.value="";
	promotionTypeChange(obj);
	
	
}

function selectShop(){
	var shopCodes = $("#shopCodes").val();
	shopCodes = shopCodes?shopCodes:"";
	dialog("选择商家","iframe:/common/selectNewProductShop.action?shopCodes="+shopCodes,"800px","600px","iframe");
}
function receiveProductShop(o){
	if(o.length<=0){
		//alert("请选择一个活动");
		return;
	}
	var showHtml ="";
	var shopCodes = $("#shopCodes").val()||"";
	var shopNames = $("#shopNames").val()||"";
	for(i=0;i<o.length;i++){
		var id = o[i].value;//o[i].attr("value");
		if(shopCodes.indexOf(id)>=0){
			continue;
		}
		var name = o[i].title;
		shopCodes = id;
		var html = '<div class="sbDiv"><em unselectable="on">'+name+'</em>'+
		 '<a class="deleteP" hidefocus="hidefocus" data-value="'+id+
		 '" onclick="javascript:delName(this);">x</a></div>';
		showHtml = html;	
		shopNames = name;	
	}
	//if(shopCodes!=""){
		$("#shopCodes").val(shopCodes);
		$("#shopNames").val(shopNames);
	//}
	$("#showShopCodes").html(showHtml);
	closeThis();
}
function delName(o){
	$("#shopCodes").val("");
	$("#shopNames").val("");
	$(o).parent().remove();
}

function promotionTypeChange(obj){
	//切换活动类型隐藏卖光操作类型
	$("#sellUpType").val('');
	$("#sellUpTypeDiv").hide();
	
	
	var shopType = getShopType();
	var type = obj.value;
	
	if(shopType==2){
		var code = $("#shopCodes").val();
		if(code||obj.value==""){}else{
			obj.value = "";
			alert("请先选择商家！");
			return;
		}
	}
	
	if(shopType==1&&type==3){
		obj.value = "";
		alert("满赠活动不能选择全部商家");
		return; 
	}
	if(shopType==1&&type==4){
		obj.value = "";
		alert("送券活动不能选择全部商家");
		return;
	}
	if(shopType==1&&type==6){
		obj.value = "";
		alert("满减活动不能选择全部商家");
		return;
	}
	if(shopType==1&&type==5){
		obj.value = "";
		alert("加价购活动不能选择全部商家");
		return;
	}
	if(shopType==1&&type==11){
		obj.value = "";
		alert("附赠活动不能选择全部商家");
		return;
	}
	if(shopType==2&&type==11){
		obj.value = "";
		alert("附赠活动不能选择入驻商家");
		return;
	}
	if(type==10 ||　type==12){
		$("#sellUpTypeDiv").show();
	}else if(type==8){//打折
		$("#sellUpTypeDiv").show();
	}
	$("#hint").html('');
	showPromotionType(type);
}
var myDialog = null;

function addDataSpan(obj){
        var type = $("#promotionType").val();
	 	var prevDiv = $("#promotionType"+type);
	 	var spanInputArray = prevDiv.children('span');
	 	//var meetDataInput = prevDiv.children("input[name^='meetData']");
		var c = spanInputArray.length;
		if(c==10)return;
		c = c+1; 
		//prizeData1
		var oneHtml = $(spanInputArray[0]).html();
		oneHtml = "<span span_index='span_index"+c+"' class='spanpromotiondata'>"+oneHtml.replace(/prizeData1/g,"prizeData"+c).replace("1",c).replace(/div_index1/g,"div_index"+c).replace(/span_index1/g,"span_index"+c)
			.replace(/entity1entity1/g,"entity"+c+"entity"+c)
			.replace(/meetData1/g,"meetData"+c)
			.replace(/num1/g,"num"+c)
			.replace(/entity1/g,"entity1"+c)+"</span>";
	    
	    console.log(oneHtml);
		$(spanInputArray[c-2]).after(oneHtml);
        
		prevDiv = $("#promotionType"+type);
	 	spanInputArray = prevDiv.children('span');
	 	var avArray = $(spanInputArray[c-1]).children("av");
	 	$(avArray[0]).find("input").val("");
	 	$(avArray[0]).val("");
	 	for(var i=0;i<avArray.length;i++){
		 	 if(i>0){
             var obj = $(avArray[i]);
             obj.next().remove();
             obj.remove();
		 	 }
	 	}
	 	$(spanInputArray[c-1]).children("br").remove();
}
function addDataSpan_back(obj){
    var type = $("#promotionType").val();
 	var prevDiv = $("#promotionType"+type);
 	var spanInputArray = prevDiv.children('span');
 	var c = spanInputArray.length;
	if(c==10)return;
	c = c+1; 
	var oneHtml = $(spanInputArray[0]).html();
	oneHtml = "<br/><span>"+oneHtml.replace(/prizeData1/g,"prizeData"+c).replace("1",c)
		.replace(/entity1entity1/g,"entity"+c+"entity"+c)
		.replace(/meetData1/g,"meetData"+c)
		.replace(/num1/g,"num"+c)
		.replace(/entity1/g,"entity1"+c)+"</span>";
	$(spanInputArray[c-2]).after(oneHtml);
}
	function removeDataSpan(obj){
		var type = $("#promotionType").val();
	 	var prevDiv = $("#promotionType"+type);
	 	var spanInputArray = prevDiv.children('span');
	 	var c = spanInputArray.length;
		if(c<=1)
			return false;
		c = c-1;
		//;
		//$(spanInputArray[c]).prev().remove();   //注释掉  modify by zhuyanling 2015.12.7
		$(spanInputArray[c]).remove();
		return true;
	}
	function removeDataAv(obj){
		var data_id = $(obj).attr("data_id");
		if(data_id.indexOf('div_index')>=0){
			var spanInputArray = $(obj).parent().children("input");
			$(spanInputArray[0]).val("");
			$(spanInputArray[1]).val("");
			$(spanInputArray[2]).val("");
			$(spanInputArray[3]).val("");
			$(obj).parent().val("");
			
		}
		else{
			//注释掉  modify by zhuyanling 2015.12.7
// 			$(obj).parent().prev().remove();
// 			$(obj).parent().next().remove();
		    $(obj).parent().remove();
		}
	}
    function removeDataDiv(obj){
		
		
			$(obj).parent().remove();
		
	}
	function receiveCoupon(objs){
		if(objs.length!=1){
			alert('请选择一项！');
			return;
		}
		 receiveCurrentInput.value=objs[0].title+"(优惠券)";
		 $(receiveCurrentInput).next().val(objs[0].value);
		closeThis();
	}
	var receiveCurrentInput;
	var receiveType;
	function selectProduct(obj,shopCodes,shopType){
		 art.dialog.data("promotionType",5);
		 myDialog = art.dialog.open("/promotion/findAllProductSku.action?productSku.product.shopCode="+shopCodes+"&productSku.product.suppliterType="+shopType, {
			   title: '选择商品',
			   width:800,
				height:700,
				drag:false,
				 lock: true
		   });
		
		 //dialog("查看所有SKU商品","iframe:/promotion/findAllProductSku.action","800px","420px","iframe",100);
	 }
	 function receiveProduct(skuId,name){
		 receiveCurrentInput.value=name;
		 $(receiveCurrentInput).next().next().val(skuId);
		 myDialog.close();
	 }
	 var  span_index=1;
	 function receiveProductNew(skuIdArray,nameArray){
		 var type = $("#promotionType").val();
		 if(type!=11){
		 var first = 0 ;
		 for(var i=0;i<skuIdArray.length;i++){
			
			 var prevDiv = $("#promotionType"+type);
			 var spanArray = prevDiv.children('span');
			 var span = $(spanArray[span_index-1]);
			 var av = span.children('av');
			 //初始化 
	         var inputArray = $(av[0]).children('input');
             var b = true;
             for(var j=0;j<av.length;j++){
                  if($(av[j]).val()==skuIdArray[i]){
                	
                    b=false;
                    break;
                  }   
                              
             }
             if(b == true){
                     if(($(inputArray[2]).val()==undefined||$(inputArray[2]).val()=="")&&first==0){
                    	 first=1;
                         if(type==3)
                         {
                    	 $(inputArray[0]).val(nameArray[i]);
                    	 $(inputArray[0]).next().next().next().val(skuIdArray[i]);
                    	 $(av[0]).val(skuIdArray[i]);
                         }else if(type==5){
                         
                         $(inputArray[2]).val(nameArray[i]);
                         $(inputArray[3]).val(skuIdArray[i]);
                         $(av[0]).val(skuIdArray[i]);
                         }
                     }else{
                         addEntrySpan(span,skuIdArray,nameArray,i);
                         
                     } 
                     
             }
             
         }
		 }else{
			 var span=$("#promotionGift");
			 
			 
			 for(var i=0;i<skuIdArray.length;i++){
		     var div = span.children('div');
			 var html = "<div data_id="+skuIdArray[i]+"><a title='"+nameArray[i]+"' class='gantProduct' onclick='javascript:toProductSku("+skuIdArray[i]+")' href='javascript:void(0)'>"+
			 nameArray[i]+"</a>&nbsp;&nbsp;<input type='text' value='' name='num11' data_id='num11'"+skuIdArray[i]+" size='4'   maxlength='128'/> 件"+
				 " <a    href='javascript:void(0)' onclick='removeDataDiv(this)'>删除</a>"+
				 "<input type='hidden' name='entity1entity1' data_id='entity1entity1' value='"+skuIdArray[i]+"' />"+
				 "</div>";
			 if(div.length<=0){
				$("#promotionGift").html(html);
				 
			 }else{
				
				 var b = true;
	             for(var j=0;j<div.length;j++){
	                  if($(div[j]).attr('data_id')==skuIdArray[i]){
	                	b=false;
	                    break;
	                  }   
	             }
	             if(b == true){
	            	 
	            	 var oldHtml = $("#promotionGift").html();
	            	 $(div[div.length-1]).after(html);
	             }
			 }
			 }
		 }
		 myDialog.close();
	 }

	 function addEntrySpan(span,skuIdArray,nameArray,dataIndex){
		 var obj = span.children('av');
		 var oneHtml = c+$(obj[0]).html();
		
		 var firstAVinput = $(obj[0]).children("input");
		 var name = firstAVinput[0].value;
		 var skuId =  firstAVinput[2].value;
		 
		 var c = obj.length;
		 oneHtml =(c+2)+oneHtml;
		 c = c+1; 
		var type = $("#promotionType").val();
        if(type==3)
		{
		oneHtml="<av id=\"div_index\""+c+" class=\""+skuIdArray[dataIndex]+" div_index_block \">"+
			"<input name=\"entity"+c+"\" title=\"请输入正确的优惠数据 。\" value=\""+nameArray[dataIndex]+"\" id=\"entity"+c+"\" size=\"20\" onchange=\"javascript:moneyChange(this)\" "+
			"maxlength=\"128\" readonly=\"readonly\" type=\"text\"><label>x  </label>"+
			"<input value=\"\" name=\"num"+c+"\" id=\"num5\" size=\"4\" maxlength=\"128\" type=\"text\"> 件"+
			"<input value=\""+skuIdArray[dataIndex]+"\" name=\"entity"+c+"entity"+c+"\" id=\"entity"+c+"entity"+c+"\" type=\"hidden\">"+
			" <a class='man'   href='javascript:void(0)' onclick='removeDataAv(this)' data_id=\""+skuIdArray[dataIndex]+"\">删除</a>"+
		    "</av>";
		}else if(type==5){
	    //加价购
		oneHtml="<av id=\"div_index\""+c+" class=\""+skuIdArray[dataIndex]+" div_index_block5 \">"+
		"<input name=\"prizeData2\" id=\"prizeData2\" size=\"5\" maxlength=\"6\" title=\"请输入正确的商品单价。\" type=\"text\">"+
		"<label>元换购  </label>"+
		"<input name=\"entity12\" title=\"请输入正确的优惠数据。\" value=\""+nameArray[dataIndex]+"\" id=\"entity12\" size=\"20\" onchange=\"javascript:moneyChange(this)\" maxlength=\"128\" readonly=\"readonly\" type=\"text\">"+
		"<input  name=\"entity2entity2\" id=\"entity2entity2\" value=\""+skuIdArray[dataIndex]+"\" type=\"hidden\">"+
		"<a class=\"man\"  style=\"\" href=\"javascript:void(0)\" onclick=\"removeDataAv(this)\" data_id=\""+skuIdArray[dataIndex]+"\">删除</a>"+
		"</av>";
        //console.log(oneHtml);
        }
	    $(obj[c-2]).after(oneHtml);
	    var prevDiv = $("#promotionType"+type);
		var spanArray = prevDiv.children('span');
		var span = $(spanArray[span_index-1]);
		var av = span.children('av');
		var inputArray = $(av[c-1]).children('input');
		$(av[c-1]).val(skuIdArray[dataIndex]);
	 }
	function selectPrize(obj){
		var type = $("#promotionType").val();
		var shopCodes = $("#shopCodes").val();
		var shopType = getShopType();
		receiveCurrentInput = obj;
		switch (type) {
		case '10'://特价
			break;
		case '8'://打折
			break;
		case '6'://满减
			break;
		case '5'://换购
			selectProduct(obj,shopCodes,shopType);
			break;
		case '4'://送券
			if(shopType==3){
				shopCodes=0;
			}
			dialog("选择优惠券","iframe:/common/selectCouponList.action?coupon.couponGivetypeId=3&coupon.shopCode="+
					shopCodes ,"700px","500px","iframe",50);
			break;
		case '3'://满赠
			selectProduct(obj,shopCodes,shopType);
			break;
		default:
			break;
		}
	}


	function toSelectProduct(o){
		var shopType = $("#shopType").val();
		if(shopType==2){
			showErrorMessage('shopCodes','请指定商家','selectEm');
		}
		var type = $("#promotionType").val();
		var shopCodes = $("#shopCodes").val();
		var shopType = getShopType();
		span_index = $(o).parent().parent().attr("span_index").replace("span_index","");
		newSelectProduct(shopCodes,shopType);
	}
	function toSelectProductForGift(){
		var shopType = $("#shopType").val();
		if(shopType==2){
			showErrorMessage('shopCodes','请指定商家','selectEm');
		}
		var type = $("#promotionType").val();
		var shopCodes = $("#shopCodes").val();
		var shopType = getShopType();
		
		newSelectProduct(shopCodes,shopType);
	}
	 function newSelectProduct(shopCodes,shopType){
		 art.dialog.data("promotionType",5);
		 myDialog = art.dialog.open("/promotion/findAllProductSku.action?productSku.product.shopCode="+shopCodes+"&productSku.product.suppliterType="+shopType, {
			   title: '选择商品',
			   width:800,
				height:700,
				drag:false,
				 lock: true
		   });
	}



	
	function moneyChange(obj){
		var type = $("#promotionType").val();
		if(type==6){
			$(obj).next().val(obj.value);
		}
	}
	
	function clearRule(){
		$("#promotionType").val('');
		$("#sellUpType").val('');
		promotionTypeChange(document.getElementById("promotionType"));
	}
	
	
$(document).ready(function(){
	var type= $(':radio[name="selectType"][checked]').val();
	typeChange(type);
	$("#frm").validate({});
});
function showPromotionType(type){
	var id = "#promotionType"+type;
	$(".promotionType").hide();
	$(id).find("span").find("input").val("");
	$(id).find("span").find("av").val("");
	//初始化功能列表
	initData();
	//加价购、满赠活动初始化件数为1

	$(".error").remove();
	//$(id).show();
	if(type!=""){
		$(id).css("display","inline");
		if(type<8){
			$("#hint").html(" 多个订单层级活动（满减、送券、满赠、加价购）作用于同一商品时，用户只能选择享受其中一个。");
			$(".man").show();
			return;
		}else{
			
			if(type==11){
				$("#hint").html("!如需为每个活动商品指定赠品，下面赠品请留空。");
			}else if(type==12){
				$("#hint").html("仅在APP端生效，优先级高于其他所有特价和折扣活动。");
			}else{
				$("#hint").html(" 多个单品层级活动（特价、折扣）作用于同一商品时，用户只能选择享受其中一个。");
			}
		}
	}	
	$(".man").hide();
}

function submit(){
	var a = showErrorMessage('promotionTitle','活动标题不能为空','promotionTitle');
	var b = showErrorMessage('promotionType','活动类型不能为空','promotionType');
	var promotionType = $("#promotionType").val();
	var div = $("#promotionType"+promotionType);
	var promotionTitle = $("#promotionTitle").val();
	if(a){
	if(promotionTitle.replace(/(^\s*)|(\s*$)/g, "")==""){
		alert("标题不能为空格！");
		return;
	}
	}else{
		return;
		
	}
	var shopType = $("#shopType").val();
	var shopCodes = $("#shopCodes").val();
	var meetDataTypeId = "meetDataType"+promotionType;
	var sellUpType = $("#sellUpType").val();
	var meetDataType = $("#"+meetDataTypeId).val();
	if(shopType==2){
		showErrorMessage('shopCodes','请指定商家','selectEm');
	}
	if(promotionType==10||promotionType==8 || promotionType==12){
		var input = div.find("input");
		var prizeData1 = input.val();
		
		if(input.attr("id")=="discount"){
			var re=/^[0-9]([.][0-9])?$/;
		    //if(re.test(prizeData1)==false||prizeData1.indexOf(".")!=-1){
			if(re.test(prizeData1/10)==false||prizeData1<=0){
				showError("discount", "请输入正确的百分比", "discount");
				return ;
			}else{
				prizeData1=prizeData1/10;
				$("#errordiscount").remove();
			}
		}else{
			
			var salePriceObj = "salePrice";
			if(promotionType==12){
				salePriceObj = salePriceObj +promotionType;
			}
			if(testPrice(prizeData1)==false||prizeData1<=0){
				if(!(prizeData1==''||prizeData1==undefined)){			    	
			    	showError(salePriceObj, "请输入正确的统一特价,大于0且小数位数不能超过两位的数字,不能包含空格。", salePriceObj);			    	
					return ;
				}else{
					$("#error"+salePriceObj).remove();
				}
			}else{
				
				$("#error"+salePriceObj).remove();
			}
		}
		
		var dataParm ={'promotionTitle':promotionTitle,'shopSort':shopType,
				'shopCodes':shopCodes,'promotionType':promotionType,
				'ruleData':prizeData1,'sellUpType':sellUpType};
		ajaxSubmit(dataParm);
		return;
	}else{
		var spanArray = div.find("span");
		var emptyInput = spanArray.find("input[value=''][class!='productCount']");
		if(emptyInput.length>0){
			if(promotionType==11){
				alert("件数不能为空");
			}else{
			showError("lastMan"+promotionType,"存在空数据","lastMan"+promotionType);
			}
			return;
		}
		//附赠活动
		if(promotionType==11){
			var giftDiv =$("#promotionGift").find("div");
			if(giftDiv.length<=0){
				var dataParmForGift ={'promotionTitle':promotionTitle,'shopSort':shopType,
						'shopCodes':shopCodes,'promotionType':promotionType,
						'ruleData':'-2','sellUpType':sellUpType};
				ajaxSubmit(dataParmForGift);
				return;
			}else{
				var gantDataArrary = "[";
				var j=0;
				while(j<giftDiv.length){
					var inputArray = $(giftDiv[j]).find("input");
					var i = 0;
				    var meetData="meetData:0",num ="num:0",prizeData="prizeData:0",entity="entity:0";
					while(i<inputArray.length){
						var id = $(inputArray[i]).attr('data_id');
						
						if(id.indexOf("num")>=0){
							presentNum= inputArray[i].value;
							num = inputArray[i].value;
							if(num&&testNum(num)==false){
								alert("件数必须是不为0的正整数。");
								return;
							}
							if(parseFloat(num)<=0){
								alert("件数必须是不为0的正整数。");
								return;
							}
							num = "num:"+num;
						}else if(id.indexOf("entity")>=0&&inputArray[i].type=="hidden"){
							entity = inputArray[i].value;
							entity = "entity:"+entity;
						}
						i++;
					}
					var dataString = "{"+meetData;
					dataString += ","+num;
					dataString += ","+prizeData;
					dataString += ","+entity+"}";
					if(j!=0){
						gantDataArrary += ","+dataString;
					}else{
						gantDataArrary += dataString;
					}
					j++
				}
				
				var dataParm ={'promotionTitle':promotionTitle,'shopSort':shopType,
						'shopCodes':shopCodes,'promotionType':promotionType,'meetDataType':meetDataType,
						'ruleData':gantDataArrary+"]"};
				ajaxSubmit(dataParm);
			}
		}else{
		$("#errorlastMan"+promotionType).remove();
		var dataArray = "[";
		var k = 0;
		while(k<spanArray.length){
			var avArray = $(spanArray[k]).find("av");
			var j=0;
			var spanInputArray =$(spanArray[k]).find("input");
			var presentMeetData = spanInputArray[0].value;
			var presentNum ;
			var meetDataTypeId = "meetDataType"+promotionType;
			var meetDataType = $("#"+meetDataTypeId).val();
			if(testPrice(presentMeetData)==false||hasDot(presentMeetData)==true){
				showError("lastMan"+promotionType,"输入的买满数据不正确,必须是大于0的整数","lastMan"+promotionType);
				break;
			}
			while(j<avArray.length)
			{
			var inputArray = $(avArray[j]).find("input");
			var i = 0;
		    var meetData="meetData:0",num ="num:0",prizeData="prizeData:0",entity="entity:0";
		    var prizeDataCheck= 0 ;
			var numCheck =0 ;
			
			while(i<inputArray.length){
				var id = inputArray[i].id;
				meetData ="meetData:"+ presentMeetData;
				if(id.indexOf("meetData")>=0){
					meetData = presentMeetData;
					if(testPrice(meetData)==false){
						showError("lastMan","输入的金额数据不正确,必须是大于0的整数","lastMan");
						break;
					}
					meetData = "meetData:"+meetData;
				}else if(id.indexOf("num")>=0){
					presentNum= inputArray[i].value;
					num = inputArray[i].value;
					numCheck=num;
					num = "num:"+num;
				}else if(id.indexOf("prizeData")>=0){
					prizeData = inputArray[i].value;
					if(testPrice(prizeData)==false){
						showError("lastMan"+promotionType,"输入的金额数据不正确","lastMan"+promotionType);
						break;
					}
					prizeDataCheck =prizeData;
					prizeData ="prizeData:"+prizeData;
				}else if(id.indexOf("entity")>=0&&inputArray[i].type=="hidden"){
					entity = inputArray[i].value;
					entity = "entity:"+entity;
				}
				if(promotionType==5||promotionType==6||promotionType==4||promotionType==3){
					//加价购时同一阶梯有一个总数量
					if(promotionType==5){
						numCheck= presentNum;
						num = "num:"+presentNum;
					}
					
					if(parseFloat(presentMeetData)<=0){
						alert("买满金额必须大于0");
						return;
					}
				}
				
				
				i++;
			}
			if(promotionType==5){ //加价购时校验统一数量
				numCheck = presentNum;
			}
			
			if(promotionType==5||promotionType==3){
				if(parseFloat(numCheck)<=0||hasDot(numCheck)==true){
					alert("件数必须大于0，并且不能为小数");
					return;
				}
			}
			if(promotionType==5||promotionType==6){
				if(parseFloat(prizeDataCheck)<=0){
				
				   alert("优惠金额必须是大于0,并且小数位不超过2位的整数或小数。");
					return;
				}
			}
			if(promotionType==6){
				if(parseFloat(meetDataType)==2){
					if(hasDot(presentMeetData)==true){
						alert("件数不能为小数");
						return;
					}
				}else{
			    if(parseFloat(prizeDataCheck)>=parseFloat(presentMeetData)){
					alert("满减活动，减免金额不能大于买满金额");
					return;
				}
				}
			}
			var dataString = "{"+meetData;
			dataString += ","+num;
			dataString += ","+prizeData;
			dataString += ","+entity+"}";
			
			if(k!=0){
				dataArray += ","+dataString;
			}else{
				dataArray += dataString;
			}
             j++
			}
			k++;
		}
		var dataParm ={'promotionTitle':promotionTitle,'shopSort':shopType,
				'shopCodes':shopCodes,'promotionType':promotionType,'meetDataType':meetDataType,
				'ruleData':dataArray+"]"
		};
		if(shopType==1&&promotionType==5){
			alert("加价购活动不能选择全部商家");
			return;
		}
		if(shopType==1&&promotionType==3){
			alert("满赠活动不能选择全部商家");
			return;
		}
		ajaxSubmit(dataParm);
		}
	}
}
	
//校验是否为小数方法
function hasDot(num) {
	return ((num + '').indexOf('.') != -1) ? true : false;
    
}
function showErrorMessage(checkId,title,afterId){
	var t = $("#"+checkId).val();
	
	
	if(t){
		
		$("#error"+checkId).remove();
		return true;
	}
	showError(checkId,title,afterId);
	return false;
}
function showError(errorId,title,afterId){
	
	if($("#error"+errorId).length>0){
		return;
	}
	var html = '<label for="error" id="error'+errorId+'" generated="true" class="error">'+title+'</label>';
	//console.log(html);
	var id = "#"+afterId;
	//console.log(id);
	$(id).after(html);
}

function ajaxSubmit(dataParm){
	var error = $(".error");
	if(error.length>0){
		return false;
	}
	$.ajax({
		dataType:'json',
		type:'post',
		url:'/promotion/addPromotionNew.action',
		async: false,
		data:dataParm,
		error:function(){
			alert('请求失败，请稍后重试或与管理员联系！');
			return false;
		},
	   	success:function(data){
	   		var code = data.code;
	   		var message = data.message;
	   		if(code==0&&message){
	   			alert("操作成功");
	   			window.location.href = "/promotion/toUpdataPromotionNew.action?promotion.promotionId="+message;
	   		}else{
	   			alert('请求失败，请稍后重试或与管理员联系！');
				return false;
	   		}
	   	}
 });
}

function Mydata(){
	this.meetData = "";
	this.num = -1;
	this.price = -1;
	this.prizeData ="";
	Mydata.prototype.toMyString = function(){
		return "meetData:"+this.meetData+",num:"+this.num+
		",price:"+this.price+",prizeData:"+this.prizeData;
	};
};
//校验是否为数字
function testNum(str){
	var re=/^[0-9]\d{0,6}$/;
	return re.test(str);
}
function testPrice(str){
	var re=/^[0-9]\d{0,6}([.][0-9]\d{0,1})?$/;
	return re.test(str);
}
function getShopType(){
	var type= $(':radio[name="selectType"][checked]').val();
	return type;
}
</script>



</html>


