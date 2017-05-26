<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品编辑'" scope="request" />
<s:set name="name" value="'价格及重量列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<form action="/app/productDraftAuditShow.action?type=price" method="POST" namespace='/app'
	id="frm" name='frm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table"  align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td width="80%" valign="middle" colspan="6">
			</td>
		</tr>
		<tr>
			<td align="right">编码：</td>
			<td align="left"><s:textfield name="productForSelectPara.productNo"
				cssClass="input_style" id="productNo" /></td>
			<td align="right">标题：</td>
			<td align="left"><s:textfield name="productForSelectPara.productTitle"
				cssClass="input_style" id="productName" size="32"/></td>
			<td align="right">类别：</td>
			<td align="left">
				<s:select list="#request.categoryList"
				name="bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="productForSelectPara.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
			</td>
		</tr>
		<tr>
			<td align="right">状态：</td>
			<td align="left"><s:select list="#request.productDraftStatusMap"
				name="productForSelectPara.priceStatus" id="productStatus" headerKey=""
				headerValue="--全部状态--"></s:select></td>
			<td align="right">关键字：</td>
			<td align="left"><s:textfield cssClass="input_style"
				name="productForSelectPara.keyword" id="keyword" size="32"/></td>
		</tr>
		<tr>
			<td align="right">品牌：</td>
			<td align="left"><input type="text" id="autocomplete" value="<s:property value='productForSelectPara.searchBrandName'/>" name="productForSelectPara.searchBrandName" size="32" /></td></tr>
            <td align="right" colspan="6">
                <input TYPE="button" onclick="doSearch()" class="queryBtn"/>&nbsp;
                <input type="button" value="申请审核" class="btn-custom btnStyle" onclick="batchAuditPrice();"/>&nbsp;
				<input type="button" value="批量发布" class="btn-custom btnStyle" onclick="batchReleasePrice();" />
            </td>
	</table>

	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="5%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'></th>
			<th align="center" width="15%">产品标题</th>
			<th align="center" width="8%">编码</th>
			<th align="center" width="8%">品牌</th>
			<th align="center" width="8%">产品价格状态</th>
			<th align="center" width="8%">类型</th>
			<th align="center" width="10%">商家名称</th>
			<th align="center">SKU信息</th>
			<th align="center" width="5%">操作</th>
		</tr>
		<s:iterator id="productiterator" value="page.dataList">
			<tr>
				<td align="center" width="5%">
                    <input type="checkbox" name="productIdChk"
					value='<s:property value="productId"/>_<s:property value="priceStatus"/>_<s:property value="opType"/>'>
				</td>
				<td align="center"><s:property value="productTitle" escape="false" /></td>
				<td align="center"><s:property value="productNo" /></td>
				<td align="center"><s:property value="prodBrand.brandName" /></td>
				<td align="center">
                    <s:property value="#request.productDraftStatusMap[priceStatus]" />
                </td>
				<td align="center">
                    <s:property value="#request.DraftTypeMap[opType]" /></td>
				<td align="center">
					<s:property value="merchantName" />
				</td>
				<td>
					<table>
					<tr>
						<th>SKU编码</th>
						<th>SKU描述</th>
						<th>市场价</th>
						<th>成本价</th>
						<th>销售单价</th>
						<th>重量</th>
						<th>PV值</th>
						<th>条形码</th>
					</tr>
					<s:iterator value="productSkuDrafts">
						<tr>
							<td><s:property value="productSkuCode"/></td>
							<td><s:property value="skuAttrs"/></td>
							<td><s:property value="markPrice"/></td>
							<td><s:property value="costPrice"/></td>
							<td><s:property value="price"/></td>
							<td><s:property value="unitWeight"/></td>
							<td><s:property value="pvValue"/></td>
							<td><s:property value="skuBarCode"/></td>
						</tr>
					</s:iterator>
					</table>
				</td>
				<td align="center">
					<s:if test="priceStatus != 1">
						<img title="修改价格" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"  onclick="gotoUpdatePrice(<s:property value='productId'/>);" />
					</s:if>
					<s:if test="opType == 3 && priceStatus != 1 ">
						<img title="删除" style="cursor: pointer;" src="/etc/images/little_icon/delete.png"  onclick="deleteProduct(<s:property value='productId'/>);" />
					</s:if>
					<img title="查看价格" style="cursor: pointer;" src="/etc/images/little_icon/jiage.png"  onclick="gotoViewPrice(<s:property value='productId'/>);" />
					<s:if test="priceStatus == 6">
						<img title="查看原因" style="cursor: pointer;" src="/etc/images/little_icon/bohui.png"  onclick="viewPriceReasons(<s:property value='productId'/>);" />
						<input type="hidden" value="<s:property value="priceReasons" />" id="priceReasons_<s:property value='productId'/>" />
					</s:if>
					<s:if test="priceStatus == 0 ">
						<img title="申请审核" style="cursor: pointer;" src="/etc/images/little_icon/tijiao.png"  onclick="submitAuditPrice(<s:property value='productId'/>);" />
					</s:if>
					<s:if test="priceStatus == 1 ">
						<img title="撤回申请" style="cursor: pointer;" src="/etc/images/little_icon/chehui.png"  onclick="recallAuditPrice(<s:property value='productId'/>);" />
					</s:if>
					<s:if test="opType == 3 && priceStatus == 2 ">
						<img title="发布" style="cursor: pointer;" src="/etc/images/little_icon/shangjia.png"  onclick="releasePrice(<s:property value='productId'/>);" />
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</table>

	<!-- 分页按钮区 -->
	<table width="98%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
			</td>
		</tr>
	</table>
	<br>
	<br>
</form>

<s:form action="/app/batchReleaseProductPrice.action" method="POST" namespace='/app' id="batchReleaseForm">
</s:form>
<s:form action="/app/batchAuditProductPrice.action" method="POST" namespace='/app' id="batchAuditForm">
</s:form>

<s:if test='"priceSuccess".equals(type)'>
	<script language="JavaScript">
	<!--
		alert("修改价格成功!");
	//-->
	</script>
</s:if>
<s:if test='"priceFail".equals(type)'>
	<script language="JavaScript">
	<!--
		alert("系统发生错误,修改价格失败!");
	//-->
	</SCRIPT>
</s:if>

<s:if test='"priceSuccessFromOfficial".equals(type)'>
	<script language="JavaScript">
	<!--
		alert("修改价格成功，已跳转到产品价格草稿表！");
	//-->
	</SCRIPT>
</s:if>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>

</body>
<script type="text/javascript">
	function gotoUpdatePrice(id){
		document.getElementById("frm").action = "/app/findProductSkusAndAttrValues.action?type=price&productId="+id;
		document.getElementById("frm").submit();
	}
	
	function gotoViewPrice(id){
		document.getElementById("frm").action = "/app/findProductSkusAndAttrValues.action?type=priceView&productId="+id;
		document.getElementById("frm").submit();
	}
	
	function submitAuditPrice(id){
		$.post("/app/gotoCheckSkuPriceAndWeight.action",
			{productId:id},
			function(data){
				if("1"==data){
					location.href = "/app/gotoSubmitAuditProductPrice.action?auditStatus=1&productId="+id;
				}else{
					alert("该产品还有价格、重量或者pv未填写，不能申请审核！");
				}
			}
		);
	}
	
	function recallAuditPrice(id){
		location.href = "/app/gotoSubmitAuditProductPrice.action?auditStatus=0&productId="+id;
	}
	
	function deleteProduct(productId){
		if(confirm('确定删除该产品吗？')){
			location.href = '/app/deleteProductDraft.action?type=price&productId='+productId;
		}
	}
	
	function viewPriceReasons(productId){
		art.dialog({   
			title:'审核不通过的原因',
		    content: $("#priceReasons_"+productId).val(),   
		    width:500,
		    height:300,
		    drag:true,
		    lock:true,
		    cancelVal: '关闭',   
		    cancel: true //为true等价于function(){}   
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

	function releasePrice(productId){
		if(confirm("确定要发布该产品的价格吗？")){
            $.ajax({
                dataType:'json',
                url:'/app/releaseProductPrice.action?productId='+productId,
                error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
                success:function(data){
                    alert(data.message);
                    if (data.isSuccess) {
                        document.forms['frm'].submit();
                    }
                }
            });
		}
	}
	
	function doSearch(){
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}

	var options = { dataType: 'json', success: createSuccess};
	
	function createSuccess(data){
		alert(data.msg);
		if(data.result){
			document.forms['frm'].submit();
		}
	}
	
	function batchReleasePrice(){
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要发布价格的产品!')
	   		return;
	   	}
		
		if(confirm('确定要发布所选产品的价格吗？')){
			var chk_value =[]; 
			var chk_tmp = [];
			var html = "";
			  $('input[name="productIdChk"]:checked').each(function(){  
			   	chk_value.push($(this).val());    
			  });  
			for(var i=0;i<chk_value.length;i++){
				chk_tmp = chk_value[i].split("_");
				if(chk_tmp[1]!='2') {
					alert('请检查产品状态是否为"已审核,待上架"!');
					return;
				}
				if(chk_tmp[2]!='3') {
					alert('请检查产品操作状态是否为"单独修改价格"!');
					return;
				}
				html += '<input type="hidden" name="productIds" value="'+chk_tmp[0]+'"/>';
			}
			
			$('#batchReleaseForm').html(html);
			$('#batchReleaseForm').ajaxSubmit(options);
		}
	}
	
	function batchAuditPrice(){
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要审核价格的产品!')
	   		return;
	   	}
		
		if(confirm('确定要审核所选产品的价格吗？')){
			var chk_value =[]; 
			var chk_tmp = [];
			var html = "";
			  $('input[name="productIdChk"]:checked').each(function(){  
			   	chk_value.push($(this).val());    
			  });  
			for(var i=0;i<chk_value.length;i++){
				chk_tmp = chk_value[i].split("_");
				if(chk_tmp[1]!='0') {
					alert('请检查产品状态是否为"草稿"!');
					return;
				}
				html += '<input type="hidden" name="productIds" value="'+chk_tmp[0]+'"/>';
			}
			
			$('#batchAuditForm').html(html);
			$('#batchAuditForm').ajaxSubmit(options);
		}
	}
</script>
</html>