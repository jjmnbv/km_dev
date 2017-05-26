<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>供应商广告位</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>
<body>
 
 <!-- 导航栏 -->
 <script>


 


    

 function gotoList(id){
 	var pageForm= window.document.getElementById("AdcolumnUpdate");
 	pageForm.action="/cms/supplier_queryAdcolumnList.action";
 	pageForm.submit();}
 
  
 
 </script>
 

<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;供应商管理
		
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;修改广告位
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form  id="AdcolumnUpdate" name="AdcolumnUpdate" action="/cms/supplier_update.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="struts.token.name" value="struts.token" />
<input type="hidden" name="struts.token" value="UFJ5Q17IYGPSOWDXRK4AI6BDFYY0QLF" />
<!-- hidden properties -->
<INPUT TYPE="hidden" id="adcolumnId" name="cmsAdcolumn.adcolumnId" value="<s:property value="cmsAdcolumn.adcolumnId"/>">
<!-- keyWords -->
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>

<input name="colType" id="colType" type="hidden" value="<s:property value='colType'/>"/>

<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">广告位信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>供应商：</td>
		<td width="80%">
		   <s:property value="cmsSupplierAdcolumn.supplierName"/>
			<input name="cmsSupplierAdcolumn.supplierId"  type="hidden" value="<s:property value="cmsSupplierAdcolumn.supplierId"/>"/>
			<input name="cmsSupplierAdcolumn.supplierAdcolumnId"  type="hidden" value="<s:property value="cmsSupplierAdcolumn.supplierAdcolumnId"/>"/>
			<input name="cmsSupplierAdcolumn.status"  type="hidden" value="<s:property value="cmsSupplierAdcolumn.status"/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>站点：</td>
		<td width="80%">
		   <s:property value="cmsSupplierAdcolumn.siteCode"/>
			<input name="cmsSupplierAdcolumn.siteCode"  type="hidden" value="<s:property value="cmsSupplierAdcolumn.siteCode"/>"/>
			
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告位置：</td>
		<td width="80%">
		    <s:property value="cmsSupplierAdcolumn.adcolumnName"/>
			<input name="cmsSupplierAdcolumn.adcolumnId"  type="hidden" value="<s:property value="cmsSupplierAdcolumn.adcolumnId"/>"/>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告位路径：</td>
		<td width="80%">
		   
		    <input type="text" name="cmsSupplierAdcolumn.contentPath" value="<s:property value="cmsSupplierAdcolumn.contentPath"/>" size="50">
			
		</td>
	</tr>
	
	
	<tr > 
		<td width="20%" align="right"><font color="red">*</font>内容：</td>
		<td width="80%">
			<textarea name="cmsSupplierAdcolumn.content" id="content" style="height:600px;width:70%"><s:property value="cmsSupplierAdcolumn.content"/></textarea>
		</td>
	</tr>
	
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td> 
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form >




</div>

</BODY>
<!-- 消息提示页面 -->
		

<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 
	              
</div>
</HTML>