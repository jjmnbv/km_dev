<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    <title>SKU商品介绍</title>
    
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet"/>
<link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet"/>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>

<script charset="utf-8"  src="/kindeditor/kindeditor.js"></script> 
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script> 
<script type="text/javascript" src="/etc/js/productDraft/skuDraftKindeditor_add.js"></script>
<script type="text/javascript">
	function submitIntroduceForm(){
		 var intro = $("#editor_id").val(),
	     editor_change = $('.editor_change');
		 
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
			
			if (rows == 'wrong')
			{
				alert("请输入文明语言");
				return false;
			}
		 
		 editor_change.html(intro);
		
	    $.each(editor_change.find('img'),function(i){
	        var t = $(this),
	            src = t.attr('src');
	        t.attr('data-original',src);
	        t.attr('src','http://jscss.km1818.com/res/images/default__logo.png');
	        t.addClass('lazy');

	    });
	    
	    $("#editor_lazy").val(editor_change.html());
	    
	    $("#skuIntroduceFrm").submit();
	}
	
	function gotoList(){
		location.href = "/app/findAllProductSkuDraft.action";
	}
	
</script>
  </head>
  
  <body>
  	<br/>
  	<input type="hidden" id="backPage" value="<s:property value='type' />" />
  	<div style="position: absolute; align: center; top: 20px; left: 30px">
  	<s:set name="parent_name" value="'产品编辑'" scope="request"/>
	<s:set name="name" value="'产品图片与介绍'" scope="request"/>
	<s:set name="son_name" value="'SKU商品介绍编辑'" scope="request"/>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
  	<s:form action="/app/updateSkuIntroduce.action" method="post" name="skuIntroduceFrm" id="skuIntroduceFrm">
  	<s:hidden name="productSkuDraft.productSkuId"></s:hidden>
  	<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
  	<s:if test="!rtnMessage.isEmpty()">
  		<SCRIPT LANGUAGE="JavaScript">
		<!--
			alert(document.getElementById("rtnMsg").value);
		//-->
	</SCRIPT>
  	</s:if>
  	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
	<tr>
		<td align="left" class="eidt_rowTitle">商品信息：
		<b><s:property value="productSkuDraft.productTitle" />
		<s:iterator value="productSkuDraft.attributeValues" >
			<s:property value="attribute" />：<b><s:property value="value" /></b>&nbsp;&nbsp;&nbsp;
		</s:iterator>
		</b>
		</td>
	</tr>
	<tr>
		<td align="left" class="eidt_rowTitle">SKU商品介绍：</td>
	</tr>
	<tr>
		<td>
			<textarea id="editor_id" name="productSkuDraft.skuIntroduce"
				style="height: 400px; width: 1014px; resize: none;"> 
						 <s:property value="productSkuDraft.skuIntroduce" />
			</textarea>
		</td>
	</tr>
	<tr>
		<td align="center"><input class="saveBtn" type="button" onclick="submitIntroduceForm()"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button"
			class="backBtn" onclick="gotoList()" /></td>
		<td width="20%" align="center"></td>
	</tr>
		
	</table>
	<!-- lazy -->
	<div class="editor_change" style="visibility:hidden"></div>
		<div style="visibility:hidden">
	<textarea id="editor_lazy" name="productSkuDraft.skuIntroduceLazy"> 
		 <s:property value="productSkuDraft.skuIntroduceLazy"/>
	</textarea>
	</div>
	</s:form>
	</div>
  </body>
</html>
