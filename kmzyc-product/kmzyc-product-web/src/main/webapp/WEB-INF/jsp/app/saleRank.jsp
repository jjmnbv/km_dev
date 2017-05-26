<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>

<script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>

<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/product_add.js"></script>
<script type="text/javascript" src="/etc/js/kindeditor_add.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript"
	src="/etc/js/validate/easy_validator.pack.js"></script>
<title>产品销量排行榜</title>
</head>
<body>
	<div style="position:absolute;align:center;top:20px;left:30px">
		<s:form action="/saleRank/querySaleRank.action" method="POST" id="frm"
			name='frm'>

			<ul id="tabs">
				<li width="33%"><b>同品牌</b></li>
				<li width="33%"><b>同类别</b></li>
				<li width="33%"><b>同价位</b></li>
				<s:textfield name="productSku.productSkuCode"  id="productCode"
					style="width:120px;height:40px;border: 1px solid #CBCBCB;"></s:textfield>
				开始日期：
				<input id="d4311"
					value="<s:date name='beginDate' format='yyyy-MM-dd' />"
					class="Wdate" readOnly="readOnly" name='beginDate' type="text"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')}',dateFmt:'yyyy-MM-dd'})"  reg="[^0]" tip="请选择入库仓库" />

				结束日期：
				<input value="<s:date name='endDate' format='yyyy-MM-dd'/>"
					id="d4312" class="Wdate" readOnly="readOnly" name="endDate"
					dataType="Date"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',dateFmt:'yyyy-MM-dd'})" reg="[^0]" tip="请选择入库仓库" />


				<a href="#"
					style="font:20px '宋体';text-decoration:none;width:155px;height:40px;"
					onclick="formCommit()"> 查询 </a>
			</ul>

			<div id="content" style="width:1155px">
				<div class="ct">
					<table width="100%" class="edit_table" align="center"
						cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2"
						style="border-collapse: collapse">
						<tr>
							<th>产品名称</th>
							<th>产品SKUCODE</th>
							<th>品牌</th>
							<th>价格</th>
							<th>销量</th>
						</tr>

						<s:iterator value="#request.sameBrandMap" id="entry">
							<tr>
								<td width="20%" align="left"><s:property
										value="key.procuctName" /></td>
								<td width="20%" align="left"><s:property
										value="key.productSkuCode" /></td>
								<td width="20%" align="left"><s:property
										value='key.brandName' /></td>
								<td width="20%" align="left"><s:property
										value="key.price" />
								</td>
								<td width="20%" align="left"><s:property value="#entry.value" />
								</td>
							</tr>
						</s:iterator>

					</table>
				</div>
				<div class="ct" id="ct_cate">
					<table width="100%" class="edit_table" align="center"
						cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2"
						style="border-collapse: collapse">

						<tr>
							<th>产品名称</th>
							<th>产品SKUCODE</th>
							<th>品牌</th>
							<th>价格</th>
							<th>销量</th>
						</tr>


						<s:iterator value="#request.sameCategoryMap" id="entry">
							<tr>
								<td width="20%" align="left"><s:property
										value="key.procuctName" /></td>
								<td width="20%" align="left"><s:property
										value="key.productSkuCode" /></td>
								<td width="20%" align="left"><s:property
										value='key.brandName' /></td>
								<td width="20%" align="left"><s:property
										value="key.price" />
								</td>
								<td width="20%" align="left"><s:property value="#entry.value" />
								</td>
							</tr>

						</s:iterator>


					</table>
				</div>



				<div class="ct" id="ct_sku">
					<table width="100%" class="edit_table" align="center"
						cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2"
						style="border-collapse: collapse">

						<tr>
							<th>产品名称</th>
							<th>产品SKUCODE</th>
							<th>品牌</th>
							<th>价格</th>
							<th>销量</th>
						</tr>
						<s:iterator value="#request.samePriceMap" id="entry">
							<tr>
								<td width="20%" align="left"><s:property
										value="key.procuctName" /></td>
								<td width="20%" align="left"><s:property
										value="key.productSkuCode" /></td>
								<td width="20%" align="left"><s:property
										value='key.brandName' /></td>
								<td width="20%" align="left"><s:property
										value="key.price" />
								</td>
								<td width="20%" align="left"><s:property value="#entry.value" />
								</td>
							</tr>

						</s:iterator>
					</table>
				</div>
			</div>

			<br />
			<table>
			</table>
		</s:form>
	</div>
</body>


<script>
	function formCommit() {
	
	   var productCode=     $("#productCode").val();
	  if(productCode.length==0){
	  
	  alert("请输入要查询的SkuCode");
	  return false;
	  }
	
		document.frm.submit();

	}
</script>

</html>
