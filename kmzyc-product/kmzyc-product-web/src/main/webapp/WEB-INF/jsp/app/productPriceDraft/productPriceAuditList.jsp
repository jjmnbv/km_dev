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
;
</style>

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script src="/etc/js/jquery-1.8.3.js"></script>
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

<s:set name="parent_name" value="'产品审核管理'" scope="request" />
<s:set name="name" value="'价格及重量审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<form action="/app/productDraftAuditShow.action?type=priDraft" method="POST" namespace='/app'
	id="frm" name='frm'>
	<s:hidden name="checkedId" id="checkedId"/>
	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" align="center"
		cellpadding="0" cellspacing="0">
	
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
			<td align="right">品牌：</td>
			<td align="left"><input type="text" id="autocomplete" value="<s:property value='productForSelectPara.searchBrandName'/>" name="productForSelectPara.searchBrandName" size="32" /></td>
			<td align="right">关键字：</td>
			<td align="left"><s:textfield cssClass="input_style"
				name="productForSelectPara.keyword" id="keyword" size="32"/></td>
		</tr>
		<tr>
			<td align="right" colspan="6"><INPUT TYPE="button" onClick="doSearch()" class="queryBtn"
				value="">&nbsp;
                <input type="button" value="审核通过" class="btn-custom btnStyle"  onclick="batchAuditProduct('2')" />
			&nbsp;
			<input type="button" value="不通过" class="btn-custom  btnStyle"  onclick="batchAuditProduct('6')" /></td>
		</tr>
	</table>


	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center" width="5%"><input type='checkbox' id='allbox'
				name='allbox' onclick='checkAll(this)'></th>
			<th align="center" width="20%">产品标题</th>
			<th align="center" width="8%">编码</th>
			<th align="center" width="8%">品牌</th>
			<th align="center" width="8%">状态</th>
			<th align="center" width="8%">类型</th>
			<th align="center">商家名称</th>
			<th align="center">SKU信息</th>
			<th align="center" width="5%">操作</th>
		</tr>
		<s:iterator id="productiterator" value="page.dataList">
			<tr>
				<td align="center" width="5%"><input type="checkbox"
					name="productIdChk"
					value='<s:property value="productId"/>'>
				</td>
				<td align="center"><s:property value="productTitle" escape="false" /></td>
				<td align="center"><s:property value="productNo" />
				</td>
				<td align="center"><s:property value="prodBrand.brandName" /></td>
				<td align="center"><s:property
					value="#request.productDraftStatusMap[priceStatus]" /></td>
				<td align="center"><s:property
					value="#request.DraftTypeMap[opType]" /></td>
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
						</tr>
					</s:iterator>
					</table>
				</td>
				<td align="center">
					<img title="审核" style="cursor: pointer;" src="/etc/images/little_icon/search.png"  onclick="gotoAuditProductPrice(<s:property value='productId'/>)" />
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
	
	<div id="auditReason" style="display: none;">
		<textarea id="reasonArea" rows="10" cols="100" style="resize: none;" ></textarea>
	</div>
	<s:hidden name="reasonText" id="reasonText" ></s:hidden>
	<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
</form>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>

<s:if test='"priDraftSuccess".equals(type)'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("操作成功!");
	//-->
	</SCRIPT>
</s:if>
<s:if test='"priDraftError".equals(type)'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert("系统发生错误,操作失败!");
	//-->
	</SCRIPT>
</s:if>

</BODY>
<script type="text/javascript">
	function gotoAuditProductPrice(id){
		location.href = "/app/findProductSkusAndAttrValues.action?type=priDraft&productId="+id;
	}
	function doSearch(){
		document.getElementById('pageNo').value = 1;
		document.forms['frm'].submit();
	}
	
	function batchAuditProduct(status){
		var chkObj = $('input[name="productIdChk"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要审核的产品!')
	   		return;
	   	}
		
		if(confirm('确定审核已选产品吗？')){
			$("#auditStatus").val(status);
			if(6==status){
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {  
				    	$("#reasonText").val($("#reasonArea").val());
				    	this.close();
						document.getElementById("frm").action = "/app/auditProductPriceDraft.action";
						document.getElementById("frm").submit();
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}else{
				document.getElementById("frm").action = "/app/auditProductPriceDraft.action";
				document.getElementById("frm").submit();
			}
		}
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
	
</script>
</HTML>
