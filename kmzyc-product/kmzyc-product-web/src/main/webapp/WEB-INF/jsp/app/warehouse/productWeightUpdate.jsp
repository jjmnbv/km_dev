<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品重量</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<style type="text/css">
	.iframe{
		top:-10px;
	}
	.edit_title{
		padding:0px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'产品重量发布'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/updateProductSKUWeight.action" method="POST"
	namespace="/app" id="frm" onsubmit="return checkWeight();" >
<div>
<!-- 数据编辑区域 -->
<table width="58%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;margin-left:12px;float:left;">
	<tr> 
		<th align="center" class="edit_title">商品信息</th>
		<th align="center" class="edit_title" width="150px">重量（单位：克）</th>
		<s:if test='type == "update"'>
			<th align="center" class="edit_title">操作</th>
		</s:if>
	</tr>
	<s:iterator value="#request.productSkuList" >
		<tr> 
			<td align="left">
				<input type="hidden" name="productSkuId" value="<s:property value="productSkuId" />" ></input>
				<input type="hidden" id="sku<s:property value="productSkuId" />" name="skuWeight" value="<s:property value='unitWeight' />"></input>
				<b>商品名称</b>：<s:property value='procuctName'/>&nbsp;&nbsp;
				<b>商家名称</b>：
				<s:if test="merchantName==null">
						康美
				</s:if>
				<s:else>
					<s:property value="merchantName" />
				</s:else>&nbsp;&nbsp;
				<s:iterator value="viewSkuAttrs">
					<b><s:property value="categoryAttrName" /></b>：<s:property value="categoryAttrValue" />&nbsp;&nbsp;
				</s:iterator>
			</td>
			<td align="right" id="<s:property value="productSkuId" />" ><s:if test='unitWeight != null'><s:property value="%{getText('{0,number,##.##}',{unitWeight})}" /></s:if><s:else>暂无重量</s:else></td>
			<s:if test='type == "update"'>
				<td align="center">
					<input type="button" class="btnStyle" value="修 改" onclick="weightChange(this,<s:property value="productSkuId" />)" />
				</td>
			</s:if>
		</tr>
	</s:iterator>
</table>
<br />
<!-- 底部 按钮条 -->
<table width="48%" align="left" class="edit_bottom" height="30"
	border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
	<tr>
		<td align="center">
			<s:if test='type == "update"'>
				<INPUT class="saveBtn" TYPE="submit" value="" />
				&nbsp;&nbsp;
			</s:if>
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
</div>
</s:form>
</body>
<Script language="JavaScript">
	function gotoList(){
		location.href="/app/productShow.action?type=weight";
	}
	
	function checkWeight(){
		if($("input[class='btnStyle'][value='确 定']").length>0){
			alert("请确认重量！");
			return false;
		}
		return true;
	}
	
	function weightChange(btn,priceInput){
		if(btn.value=='修 改'){
			var _txt = $("#"+priceInput).text();
			if(_txt=='暂无重量'){
				_txt = '';
			}
			var str = '<input id="asd'+priceInput+'" size="8" type="text" value="'+_txt+'"  maxlength="9"  />';
			$("#"+priceInput).html(str);
			btn.value = '确 定';
			setTimeout(function(){$("#asd"+priceInput).select();},10);
		}else{
			var _txt = $("#"+priceInput+" input").val();
			if(_txt!=''){
				var priceTest = /^\d+(\.?\d{1,2})?$/;
				if(!priceTest.test(_txt)){
					alert("请输入正确的数字，或请不要超过2位小数！");
					setTimeout(function(){$("#asd"+priceInput).select();},10);
					return;
				}
			}
			$("#sku"+priceInput).val(_txt);
			if(_txt==''){
				_txt = "暂无重量";
			}
			$("#"+priceInput).text(_txt);
			btn.value = '修 改';
		}
	}
</Script>
</html>