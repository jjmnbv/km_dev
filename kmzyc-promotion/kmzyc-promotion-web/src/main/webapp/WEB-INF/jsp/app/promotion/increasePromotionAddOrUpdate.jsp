<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<title></title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'加价购组合列表'" scope="request"/>
<s:set name="son_name" value="'添加组合'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form action="/promotion/promotionAddOrUpdate.action"	method="POST" namespace='/promotion' id="frm">
	<s:hidden name="promotion.promotionId" id="promotionId"></s:hidden>
	<s:hidden name="promotion.nature" value="2" id="nature"></s:hidden>
	<s:hidden name="promotion.productFilterType" value="2" id="productFilterType"></s:hidden>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">基本信息</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>组合名称：</th>
		  <td width="80%">
             <input type="text" name="promotion.promotionName" id="promotionName" size="70" 
             maxlength="70" value="<s:property value='promotion.promotionName' />"
				style="width:400px" onblur=""/> 
		  </td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">组合描述：</th>
			<td><label> <s:textarea name="promotion.promotionDescribe" id="promotionDescribe" rows="8" cols="45"/></label></td>
		</tr>
		<tr><td></td>
			<td align="left"><INPUT class="saveBtn" TYPE="submit"
				value="" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:history.go(-1);" />
		</tr>
		
	</table>
</s:form>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#frm").validate({
    rules: {
		"promotion.promotionName":{required:true,maxlength:50}
	},
	messages:{
		"promotion.promotionName":'请输入合法的名称'
	},
	success: function (label){
        label.removeClass("checked").addClass("checked");
    }
});
});
  </script>
</html>

