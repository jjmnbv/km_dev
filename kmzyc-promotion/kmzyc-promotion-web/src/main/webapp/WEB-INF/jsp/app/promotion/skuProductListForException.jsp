<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择产品</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	body{
		padding:0px;
		margin:0px;
	}
	table{
		margin-left:10px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:form action="/promotion/findAllProductSkuForException.action" method="POST"   id="frm" name='frm' >
<s:hidden id="promotionId" name="promotionProduct.promotionId"></s:hidden>
<s:hidden id="shopCode" name="productSku.product.shopCode"></s:hidden>
<s:hidden id="shopType" name="productSku.product.suppliterType"></s:hidden>

<br />
<table width="97%" class="table_search" align="center" cellpadding="0" cellspacing="0" style="border-collapse: collapse;font-size:12px" >
	<tr>
    	<td rowspan="2"><INPUT TYPE="button" class="btn-custom btngreen" id="selectList" value=" 保存所选 " onClick="javascript:selectProduct();"></td>
    	<td>名称：<s:textfield name="productSku.productName" cssClass="input_style" id="productName"/>
			&nbsp;&nbsp;&nbsp;
			主标题：<s:textfield name="productSku.productTitle" cssClass="input_style" id="productTitle"/>
			&nbsp;&nbsp;&nbsp;
			SKU编码：<s:textfield cssClass="input_style" name="productSku.productSkuCode" id="productSkuCode"/>
			&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
	  <td>	类别：
			
	        
	        
	        <s:select list="#request.categoryList"
				name="productSku.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
				headerKey="" headerValue="--一级类目--"
				onchange="change2('categoryId1','categoryId2');"></s:select> 
				<s:select list="#request.mCategoryList"
				name="productSku.mCategoryId" id="categoryId2"  listKey="categoryId" listValue="categoryName" 
				headerKey="" headerValue="--二级类目--"
				onchange="change2('categoryId2','categoryId3');"></s:select> 
				<s:select list="#request.sCategoryList" id="categoryId3" 
				headerKey="" headerValue="--三级类目--" 
				name="productSku.categoryId"  listKey="categoryId" listValue="categoryName"></s:select>
	        
	        
	        
			<INPUT TYPE="submit" class="btn-custom btngray" value="查询 ">
		</td>
	</tr>
</table>
<br />

<!-- 数据列表区域 -->
<table id="dataList" class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" >
	<tr> 
	    <th align="center" width="3%"><input type='checkbox' name='allbox' onclick='checkAll(this)'></th>
	    <th align="center" width="30%">产品主标题</th>
		<th align="center" width="15%">产品名称</th>
		<th align="center" width="15%">SKU编码</th>
		<th align="center">SKU属性</th>
	</tr>
	<s:iterator id="productiterator" value="page.dataList" status="st" >
	<tr onMouseOver="this.style.backgroundColor='#def2fa'"
		onMouseOut="this.style.backgroundColor='#FFFFFF'">
	    <td align="center"><input type="checkbox" name="<s:property value="productTitle" />"  value='<s:property value="productSkuId"/>'></td>
		<td align="center"><s:property value="productTitle" /></td>
		<td align="center"><s:property value="productName" /></td>
		<td align="center"><s:property value="productSkuCode" /></td>
		<td align="center">
		<s:iterator value="productSkuAttrList" >
		<s:property value='categoryAttrName'/>：<s:property value='categoryAttrValue'/>&nbsp;&nbsp;&nbsp;&nbsp;
		</s:iterator>
		</td>
	</tr>
	</s:iterator>
</table>
<table  width="95%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>
<br />
</s:form>


<script type="text/javascript">

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function selectList(){
	}
function selectProduct_back(){
	var type = art.dialog.data('promotionType');
	if (type!='undefined'&&type!=null&&type==5) {
		var obj = $("input[type='checkbox']:checked");
		var count = obj.length;
		if(count==0){
		alert("请选择产品SKU！");
			return;
		}
		var id = obj.val();
		var name = obj.parent().next().html();
		parent.receiveProductNew(id,name);
	}else{//
		if($("input[type='checkbox']:checked").length==0){
			alert("请选择产品SKU！");
			return;
		}
		var ids = "";
		$("input[type='checkbox']:checked").each(function(i){
			if($(this).val()=='on')
			{
				ids="";
			}else{
			ids += "," + $(this).val();
			}
		});
		
		location.href="/promotion/addPromotionProduct.action?promotionProduct.category=-1&promotionProduct.promotionId="+$("#promotionId").val()+"&promotionProduct.productSkuIds="+ids.substr(1);
	}
}
function selectProduct(){

	var type = art.dialog.data('promotionType');
	if (type!='undefined'&&type!=null&&type==5) {
		var obj = $("input[type='checkbox']:checked");
		var count = obj.length;
		if(count==0){
		alert("请选择产品SKU！");
			return;
		}
		var idArray = new Array;
		var nameArray = new Array;
        for(var i=0;i<obj.length;i++){
            if(obj[0].name=='allbox')
            {   if(i<obj.length-1){
            	idArray[i] = obj[parseInt(i)+1].value;
            	nameArray[i] = obj[parseInt(i)+1].name; 
                }
            }
            else{
        	idArray[i] = obj[i].value;
        	nameArray[i] = obj[i].name;
            }
        }   
        parent.receiveProductNew(idArray,nameArray);
	}else{//
		if($("input[type='checkbox']:checked").length==0){
			alert("请选择产品SKU！");
			return;
		}
		var ids = "";
		$("input[type='checkbox']:checked").each(function(i){
			if($(this).val()=='on')
			{
				ids="";
			}else{
			ids += "," + $(this).val();
			}
		});
		
		location.href="/promotion/addPromotionProduct.action?promotionProduct.category=-1&promotionProduct.promotionId="+$("#promotionId").val()+"&promotionProduct.productSkuIds="+ids.substr(1);
	}
}
</script>
</BODY>
</HTML>

