<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript"
	src="/etc/js/artDialog4.1.7/artDialog.js?skin=default"
	type="text/javascript"></script>
<script language="JavaScript"
	src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js"
	type="text/javascript"></script>
<title>促销活动产品</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css">
<!--
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
-->
<script type="text/javascript" src="/etc/js/promotion.js"></script>
<script language="JavaScript" src="/etc/js/dialog.js"
	type="text/javascript"></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
.gantProduct{
width: 100px;
    display: inline-block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<script type="text/javascript">

	function gotoList(){
		
		location.href="/promotion/queryPromotionList.action";
	}

	function closePopDiv(promotionId){
		closeThis();
		
		var result="保存完毕！如所添加商品已存在该活动中则不会添加！";
		
		
		alert(result);
		location.href="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId;
	}
    
    /**  进入选择产品页面  **/
    function gotoAdd(promotionId){
		var shopCodes = $("#shopCodes").val();
		var shopSort = $("#shopSort").val();
    	 dialog("查看所有SKU商品","iframe:/promotion/findAllProductSku.action?promotionProduct.promotionId="
    	 +promotionId+"&productSku.product.shopCode="+shopCodes+"&productSku.product.suppliterType="+shopSort ,"800px","600px","iframe",50);
    }

    function deleteProduct(promotionId){
    	if($("input[name='promotionProductId']:checked").length==0){
    		alert("请选择产品SKU！");
    		return;
    	}
    	if(!confirm("是否确定删除选中的值?")){
    		return;
    	}
    	var ids = "";
    	$("input[name='promotionProductId']:checked").each(function(i){
    		ids += "," + $(this).val();
    	});
    	location.href="/promotion/deletePromotionProduct.action?promotionProduct.promotionId="+promotionId+"&promotionProduct.promotionProductIds="+ids.substr(1);
    }
    function update(obj){
    	var priceSpan=$(obj).prev();
    	var priceInput=$(obj).prev().prev();
    	var isShow =priceInput.css('display');
    	if(isShow=='none'){
    		$(".salePrice").css({'display':'none'});
    		priceInput.css({'display':'inline'});
    		
    		//priceSpan.css({'display':'none'});
    		priceInput.focus();
    		return;
    	}else{
    		//save
    		var price = priceInput.val();
    		var id = priceInput.attr('data_id');
    		if(price){
    			if(parseFloat(price)<=0){
    				alert("请输入大于0的特价价格!");
    				return false;
    			}
    			$.ajax({
    				type:'post',
    				url:'/promotion/updatePromotionProductPrice.action',
    				data:{'promotionProduct.promotionProductId':id,'promotionProduct.price':price},
    				dataType:'json',
    				success:function(data){
    					var code = data.code;
    					if(code==0){
	    					priceInput.css({'display':'none'});
	    					//var p = priceInput.val();
	    					//var pf =parseFloat(p);
	    					//priceInput.val(p);
	    		    		priceSpan.html(price);
	    		    		priceSpan.css({'display':'inline'});
	    		    		//alert('修改成功');
    		    		}else{
    		    			alert('价格修改失败');
    		    		}
    				}
    			});
    		}
    		priceInput.focus();
    	}
    }
    
    function validate(value){
        if(!(/^(\+|-)?\d+$/.test( value )) || value <= 0){  
            return false;  
        }else{  
            return true;  
        }  
    }
    
    function validate2(value){
        if(!(/^(\+|-)?\d+$/.test( value )) || value <= 1){  
            return false;  
        }else{  
            return true;  
        }  
    }
    function updateForGrant(obj){
    	var objSpan=$(obj).prev();
    	var objInput=$(obj).prev().prev();
    	var id = objInput.attr('data_id');
    	var isShow =objInput.css('display');
    	if(isShow=='none'){
    		$(".xiangou").css({'display':'none'});
    		objInput.css({'display':'inline'});
    		//objSpan.css({'display':'none'});
    		objInput.focus();
    		return;
    	}
    }
    function updateForXianGou(obj){
    	//
    	
    	var objParamName = $(obj).prev().prev().attr('name');
    	var objSpan=$(obj).prev();
    	var objInput=$(obj).prev().prev();
    	var id = objInput.attr('data_id');
    	var isShow =objInput.css('display');
    	if(isShow=='none'){
    		$(".xiangou").css({'display':'none'});
    		objInput.css({'display':'inline'});
    		//objSpan.css({'display':'none'});
    		obj

    		.focus();
    		return;
    	}else if(objParamName=='minBuy'){
    		//save
    		var minBuy = objInput.val();
    		if(!validate2(minBuy)){
    			alert('请输入大于1的正整数!');
    			return;
    		}
    		
    		if(minBuy){
    			$.ajax({
    				type:'post',
    				url:'/promotion/updatePromotionProductForXianGou.action',
    				data:{'promotionProduct.promotionProductId':id,'promotionProduct.minBuy':minBuy},
    				dataType:'json',
    				success:function(data){
    					if(data.result==true){
	    					objInput.css({'display':'none'});
	    					var p = objInput.val();
	    					objSpan.html(p);
	    					objSpan.css({'display':'inline'});
	    		    		//alert('修改成功');
    		    		}else{
    		    			alert(data.msg);
    		    		}
    				}
    			});
    		}
    		objInput.focus();
    	}else if(objParamName=='maxBuy'){
    		//save
    		var maxBuy = objInput.val();
    		if(!validate(maxBuy)){
    			alert('请输入正整数!');
    			return;
    		}
    		
    		if(maxBuy){
    			$.ajax({
    				type:'post',
    				url:'/promotion/updatePromotionProductForXianGou.action',
    				data:{'promotionProduct.promotionProductId':id,'promotionProduct.maxBuy':maxBuy},
    				dataType:'json',
    				success:function(data){
    					if(data.result==true){
	    					objInput.css({'display':'none'});
	    					var p = objInput.val();
	    					objSpan.html(p);
	    					objSpan.css({'display':'inline'});
	    		    		//alert('修改成功');
    		    		}else{
    		    			alert(data.msg);
    		    		}
    				}
    			});
    		}
    		objInput.focus();
    	}else if(objParamName=='grantNum'){
    		var grantNum = objInput.val();
    		if(!validate(grantNum)){
    			alert('请输入正整数!');
    			return;
    		}
    		if(grantNum){
    			$.ajax({
    				type:'post',
    				url:'/promotion/updatePromotionProductDataNum.action',
    				data:{'promotionProductData.promotionProductDataId':id,'promotionProductData.num':grantNum},
    				dataType:'json',
    				success:function(data){
    					var code = data.code;
    					if(code==0){
	    					objInput.css({'display':'none'});
	    					var p = objInput.val();
	    					objSpan.html(p);
	    					objSpan.css({'display':'inline'});
	    		    		//alert('修改成功');
    		    		}else{
    		    			alert('修改失败');
    		    		}
    				}
    			});
    		}
    		objInput.focus();
    	}else{
    		//save
    		var promotionStock = objInput.val();
    		if(!validate(promotionStock)){
    			alert('请输入正整数!');
    			return;
    		}
    		
    		if(promotionStock){
    			$.ajax({
    				type:'post',
    				url:'/promotion/updatePromotionProductPrice.action',
    				data:{'promotionProduct.promotionProductId':id,'promotionProduct.promotionStock':promotionStock},
    				dataType:'json',
    				success:function(data){
    					var code = data.code;
    					if(code==0){
	    					objInput.css({'display':'none'});
	    					var p = objInput.val();
	    					objSpan.html(p);
	    					objSpan.css({'display':'inline'});
	    		    		//alert('修改成功');
    		    		}else{
    		    			alert('活动库存数量修改失败');
    		    		}
    				}
    			});
    		}
    		objInput.focus();
    	}
    }
    
    document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){ // 按 Esc要做的事情
        	
          }
        if(e && e.keyCode==113){ // 按 F2要做的事情
           }            
         if(e && e.keyCode==13){ // enter 键要做的事情
        	 var inputPrice = document.activeElement;
        	 var obj = $(inputPrice).next().next().get(0);
        	 var classType =  $(inputPrice).attr("class");
        	 if(classType=='xiangou'){
        		 updateForXianGou(obj);
        	 }else{
        		 update(obj); 
        	 }
        	
        }
    }; 
</script>
</head>
<s:if test="promotion.nature==1">
	<s:set name="parent_name" value="'促销管理'" scope="request" />
	<s:set name="name" value="'促销活动'" scope="request" />
	<s:set name="son_name" value="'活动商品列表'" scope="request" />
</s:if>
<s:if test="promotion.nature==2">
	<s:set name="parent_name" value="'促销管理'" scope="request" />
	<s:set name="name" value="'加价购组合列表'" scope="request" />
	<s:set name="son_name" value="'加价购商品列表'" scope="request" />
</s:if>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:hidden name="promotion.shopSort" id="shopSort"></s:hidden>
	<s:hidden name="promotion.supplierId" id="shopCodes"></s:hidden>
	<s:form name="sectionsForm" method="post" id="frm"
		action="/promotion/queryPromotionProductList.action">
		<s:hidden name="promotionProduct.promotionId" id="promotionId"></s:hidden>
		<s:hidden name="promotion.nature" id="nature"></s:hidden>
		<input type="hidden"
			value="<s:property value='promotion.promotionType' />"
			id="promotionType" />
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 按钮条 -->

		<!--<table width="98%" align="center" class="topbuttonbar readonly"
			height="30" border="0" cellpadding="0" cellspacing="0">

			<tr>
				<s:if test="promotion.onlineStatus!=3">
					<td width="90%" valign="middle"></td>
				</s:if>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				<!--</td>
			</tr>
		</table>-->

		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right" width="120px">SKU编号：</td>
				<td><input name="promotionProduct.productSkuCode" type="text"
					class="input_style"
					value="<s:property value='promotionProduct.productSkuCode' />"></td>

				<td align="right"><INPUT TYPE="button" class="queryBtn"
					onclick="$('#frm').submit();" value="">
                    <input class="addBtn"
						type="button" value=""
						onClick="gotoAdd(<s:property value='promotionProduct.promotionId' />);">
						<s:if test="promotion.status==1">
							<input class="delBtn" type="button" value=""
								onClick="deleteProduct(<s:property value='promotionProduct.promotionId' />);">
						</s:if>
                        </td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th align="center">产品名称</th>
				<th align="center">SKU编号</th>
				<th align="center">SKU属性</th>
				<s:if test="promotion.promotionType==10 || promotion.promotionType==12">
					<s:if test="promotion.nature==1">
						<th align="center">活动特价</th>
					</s:if>
					<s:if test="promotion.nature==2">
						<th align="center">加价</th>
					</s:if>
					<th align="center">原始定价</th>
				</s:if>
				<s:if
					test="promotion.nature==1&&promotion.promotionType>=8&&promotion.promotionType!=11">
					<th align="center">最少购买</th>
					<th align="center">最多购买</th>
					<th align="center">活动库存</th>

				</s:if>
				<s:if
					test="promotion.promotionType==11&&promotion.promotionData!=-1">
					<th align="center">赠品</th>
				</s:if>
				<th align="center">状态变更时间</th>
				<th align="center">活动产品状态</th>
				<th align="center">操作</th>
			</tr>
			<s:iterator value="page.dataList">
			    <s:set name="productStatus" value="status" />
			    <tr onMouseOver="this.style.backgroundColor='#def2fa'"
					onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox"
						name="promotionProductId"
						value='<s:property value="promotionProductId"/>' /></td>
					<td align="center"><s:property value="productName" /></td>
					<td align="center"><s:property value="productSkuCode" /></td>
					<td align="center"><s:iterator value="productSkuAttrList">
							<s:property value='categoryAttrName' />：<s:property
								value='categoryAttrValue' />&nbsp;&nbsp;&nbsp;&nbsp;
					</s:iterator></td>
					<s:if test="promotion.promotionType==10 || promotion.promotionType==12">
						<td align="center"><input maxlength="8" class="salePrice"
							title='请输入特价金额'
							data_id='<s:property value="promotionProductId"/>'
							priceAttr='<s:property value="promotionProductId"/>'
							style="display: none;width: 100px;" 
							onkeyup="if(isNaN(this.value))execCommand('undo')"
							value="<s:property value="price" />"
							id="price<s:property value="promotionProductId"/>" name="price" />
							<span id="noEditprice" style="display: inline"><s:property
									value="price" /></span> <s:if
								test="promotion.status==1&&promotion.promotionData==null">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:update(this);" />
							</s:if> <s:if
								test="promotion.status==2&&status==1&&promotion.promotionData==null">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:update(this);" />
							</s:if></td>
						<td><s:property value="originalPrice" /></td>
					</s:if>
					<s:if
						test="promotion.nature==1&&promotion.promotionType>=8&&promotion.promotionType!=11">
						<td><input maxlength="8" class="xiangou" title='请输入限购数据'
							data_id='<s:property value="promotionProductId"/>'
							style="display: none;width: 100px;"
							onkeyup="if(isNaN(this.value))execCommand('undo')"
							value="<s:property value="minBuy" />" id="minBuy" name="minBuy" />
							<span id="noEditprice" style="display: inline"> <s:property
									value="minBuy" />
						</span> <s:if test="promotion.status==2&&status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if> <s:if test="promotion.status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if></td>
						<td><input maxlength="8" class="xiangou" title='请输入限购数据'
							data_id='<s:property value="promotionProductId"/>'
							style="display: none;width: 100px;" 
							onkeyup="if(isNaN(this.value))execCommand('undo')"
							value="<s:property value="maxBuy" />" id="maxBuy" name="maxBuy" />
							<span id="noEditprice" style="display: inline"> <s:property
									value="maxBuy" />
						</span> <s:if test="promotion.status==2&&status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if> <s:if test="promotion.status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if></td>
						<td><input maxlength="8" class="xiangou" title='请输入限购数据'
							data_id='<s:property value="promotionProductId"/>'
							style="display: none;width: 100px;" 
							onkeyup="if(isNaN(this.value))execCommand('undo')"
							value="<s:property value="promotionStock" />" id="promotionStock"
							name="promotionStock" /> <span id="noEditprice"
							style="display: inline"> <s:property
									value="promotionStock" />
						</span> <s:if test="promotion.status==2&&status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if> <s:if test="promotion.status==1">
								<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
									class="readonly" src="/etc/images/icon_modify.png"
									onclick="javascript:updateForXianGou(this);" />
							</s:if></td>
					</s:if>
           
					<s:if
						test="promotion.promotionType==11&&promotion.promotionData!=-1">
						<td align="center">

							<div id="<s:property value='promotionProductId'/>">
								<s:iterator value="promotionProductDataList">
									<div>
										<a class="gantProduct" title="<s:property value="productTitle" />" id="a_"
											onclick="javascript:toProductSku(<s:property value="productSkuId"/>)"
											href="javascript:void(0);" ><s:property
												value="productTitle" /></a> x <input maxlength="8"
											class="xiangou" title='请输入赠品件数'
											data_id='<s:property value="promotionProductDataId"/>'
											style="display: none;width: 100px;"
											onkeyup="if(isNaN(this.value))execCommand('undo')"
											value="<s:property value="num" />" name="grantNum" /> <span
											style="display: inline"> <s:property value="num" />
										</span> 件 
										<s:if test="#productStatus==1">
											<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
												class="readonly" src="/etc/images/icon_modify.png"
												onclick="javascript:updateForXianGou(this);" />

											<a class="readonly"
												id="<s:property value="promotionProductDataId"/>"
												onclick="javascript:deleteGant(<s:property value="promotionProductDataId"/>)"
												href="javascript:void(0);" title="删除">删除</a>
										</s:if>
										<s:if test="promotion.status==1">
											<img TYPE="button" style="cursor: pointer;" title="点击修改或者保存"
												class="readonly" src="/etc/images/icon_modify.png"
												onclick="javascript:updateForXianGou(this);" />

											<a class="readonly"
												id="<s:property value="promotionProductDataId"/>"
												onclick="javascript:deleteGant(<s:property value="promotionProductDataId"/>)"
												href="javascript:void(0);" title="删除">删除</a>

										</s:if>
										<br>
									</div>
								</s:iterator>
								<s:if test="promotion.status==2&&status==1">
									<input maxlength="8" class="readonly" type="button"
										promotionProductId="<s:property value='promotionProductId' />"
										productSkuId="<s:property value='productSkuId' />"
										onclick="javascript:selectGrantProduct(this);" value="添加赠品">
								</s:if>
								<s:if test="promotion.status==1">
									<input class="readonly" type="button"
										promotionProductId="<s:property value='promotionProductId' />"
										productSkuId="<s:property value='productSkuId' />"
										onclick="javascript:selectGrantProduct(this);" value="添加赠品">
								</s:if>
							</div>

						</td>
					</s:if>
					<td align="center"><s:date name="modifyTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center"><s:if test="status==1">未上线</s:if> <s:elseif
							test="status==2">正在进行</s:elseif> <s:else>已过期</s:else></td>
					<td align="center"><s:if test="promotion.onlineStatus!=3">
							<s:if test="promotion.status==2&&status==2">
								<!-- 已上线 -->
								<img class="readonly" TYPE="button" style="cursor: pointer;"
									title="下线产品" src="/etc/images/button_new/offline.png"
									onClick="updateStatus(3,<s:property value='promotionProductId'/>)">&nbsp;&nbsp;
					</s:if>
							<s:if test="promotion.status==2&&status==1">
								<!-- 未上线 -->
								<img class="readonly" TYPE="button" style="cursor: pointer;"
									title="上线产品" src="/etc/images/button_new/online.png"
									onClick="updateStatus(2,<s:property value='promotionProductId'/>)">&nbsp;&nbsp;
					</s:if>
						</s:if></td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>

			<tr>
				<td align="center" class="readonly"><input type="button"
					class="backBtn" onClick="javascript:goBack()" /></td>
			</tr>

		</table>
	</s:form>
</body>
<script type="text/javascript">
	function goBack(){
		var url = '/promotion/queryPromotionList.action';
		window.location.href = url+'?promotion.promotionId='+$('#promotionId').val()+'&promotion.nature='+$('#nature').val();
	}
	$(document).ready(function(){
		var readonly = art.dialog.data('readOnly');
		//alert(readonly);
		if(readonly==true){
			$(".readonly").hide();
		}
		
		
	});
	/**设置商品状态*/
	function updateStatus(type,id){
		var title = "";
		var promotionType =$("#promotionType").val();
		var css = $("#price"+id).css('display');
		if(css!=undefined&&(css.indexOf('inline')!=-1||css.indexOf('block')!=-1)){
			alert("请先完成价格修改操作！");
			return;
		}
		if(promotionType==10 || promotionType==12){
			var priceId = "price"+id;
			var price = $("#"+priceId).val();
			if(parseFloat(price)<=0||price==undefined||price.length<1){
				alert("未输入正确价格!");
				return;
			}
		}
		
		if(type==2){
			title = "是否确定将活动商品上线？";
		}else{
			title = "是否确定将活动商品下线？";
		}
		var yes = confirm(title);
		if(yes==false)return;
		$.ajax({
			url:'/promotion/updateIssuePromotionProduct.action',
			type:'post',
			dataType:'json',
			data:{'promotionProduct.promotionProductId':id},
			success:function(data){
				var success = data.success;
				if(success==true){
					alert("操作成功,产品上下线操作缓存更新会有一定延迟！");
					window.location.reload();
				}else{
					alert(data.title);
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
			}
		});
	}
	var myDialog = null;
	function selectGrantProduct(obj){
		var idArray = new Array;
		
		var promotionProductId=$(obj).attr('promotionProductId');
		var productSkuId= $(obj).attr('productSkuId');
		var promotionId =$("#promotionId").val();
		art.dialog.data('promotionProductId', promotionProductId); 
		art.dialog.data('productSkuId', productSkuId); 
		var shopCodes = $("#shopCodes").val();
		var shopSort = $("#shopSort").val();  
		myDialog = art.dialog.open("/promotion/findAllProductSkuForGrant.action?promotionProduct.promotionId="
    	 +promotionId+"&productSku.product.shopCode="+shopCodes+"&productSku.product.suppliterType="+shopSort, {
			   title: '选择商品',
			   width:800,
				height:700,
				drag:false,
				 lock: true
		});
	}
	 function receiveProductForGrant(skuIdArray,nameArray){
		 
		 var promotionId =$("#promotionId").val();
		 var promotionProductId = art.dialog.data('promotionProductId');
		 var productSkuId = art.dialog.data('productSkuId');
	     
		 var skuIds ='';
		 for(var i=0;i<skuIdArray.length;i++){
			 if(i==0){
				 skuIds=skuIdArray[0];
			 }else{
				 skuIds=skuIds+","+skuIdArray[i];
			 }
			 
		 }
		 
		  $.ajax({
				type:'post',
				url:'/promotion/addPromotionProductData.action',
				data:{'promotionProductData.promotionProductId':promotionProductId,'promotionProductData.prarentSkuId':productSkuId,'promotionProductData.promotionId':promotionId,'productSkuIds':skuIds},
				dataType:'json',
				success:function(data){
					var code = data.code;
					if(code==0){
						alert('添加成功,如果存在已添加商品不会重复添加。');
						myDialog.close();
						location.href="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
		    		}else{
		    			alert('添加失败');
		    		}
				}
			});
	 }
	 function deleteGant(id){
		 $.ajax({
				type:'post',
				url:'/promotion/deletePromotionProductData.action',
				data:{'promotionProductData.promotionProductDataId':id},
				dataType:'json',
				success:function(data){
					var code = data.code;
					if(code==0){
						alert('删除成功');
						$("#"+id).parent().remove();
					}else{
		    			alert('删除失败');
		    		}
				}
			});
	 }
</script>
</html>

