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
<script type="text/javascript" src="/etc/js/product/skuKindeditor_show.js"></script>

  </head>
  
  <body>
  	<br/>
  	<input type="hidden" id="backPage" value="<s:property value='type' />" />
  	<div style="position: absolute; align: center; top: 20px; left: 30px">
  	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
	<tr>
		<td align="left" class="eidt_rowTitle">SKU商品介绍：</td>
	</tr>
	<tr>
		<td>
			<textarea id="editor_id" name="productSku.skuIntroduce"
				style="height: 400px; width: 1014px; resize: none;"> 
						 <s:property value="productSku.skuIntroduce" />
			</textarea>
		</td>
	</tr>
	</table>
	<!-- lazy -->
	<div class="editor_change" style="visibility:hidden"></div>
		<div style="visibility:hidden">
	<textarea id="editor_lazy" name="productSku.skuIntroduceLazy"> 
		 <s:property value="productSku.skuIntroduceLazy"/>
	</textarea>
	</div>
	</div>
  </body>
</html>
