<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加广告位</title>
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


 function gotoList(){
    //window.location.href="/cms/Adcolumn_queryAdcolumn.action";
    var pageForm= window.document.getElementById("AdcolumnAdd");
    
 	pageForm.action="/cms/supplier_queryAdcolumnList.action";
 	pageForm.submit();
 }

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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;广告位管理
		
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加广告位
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >         
<s:form  id="AdcolumnAdd"  name="AdcolumnAdd" action="/cms/supplier_Add.action" method="POST" enctype="multipart/form-data">

<s:token></s:token>
<input type="hidden" name="struts.token.name" value="struts.token" />
<input type="hidden" name="struts.token" value="UFJ5Q17IYGPSOWDXRK4AI6BDFYY0QLF" />
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">

<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">广告位信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>店铺名称：</td>
		<td width="80%">                                                                           
			<s:select list="shopMainList" listKey="shopId" listValue="shopName" name="shopMain.shopId" headerKey="" headerValue="">
			</s:select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告位模板：</td>
		<td width="80%">
			<s:select list="templateList" listKey="templateId" listValue="theme" name="cmsTemplate.templateId" headerKey="" headerValue="">
			</s:select>
		</td>
	</tr>
	<!--
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告名称：</td>
		<td width="80%">
			<input name="cmsAdcolumn.name" id="corporateName" type="text" value="<s:property value="cmsAdcolumn.name"/>"/ size="50">供应商名称_广告位模板名称
		</td>
	</tr>
	 <tr> 
		<td width="20%" align="right"><font color="red">*</font>输出路径：</td>
		<td width="80%"> 
			<input name="cmsAdcolumn.output" id="output" type="text" value="<s:property value="cmsAdcolumn.output"/>" / size="50">/portal/adv/supplier/shopMain店铺ID_模板ID.shtml
		</td>
	</tr>
      -->

	
	

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