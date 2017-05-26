<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<title>促销活动</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=simple" type="text/javascript"></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
	
}
#imageDIV img { max-width:600px; myimg:expression(onload=function(){ this.style.width=(this.offsetWidth > 600)?"600px":"auto"}); }
</style>
<script type="text/javascript">
	function showUploadImage(code){
		if(myDialog)myDialog.close();
		var id = $("#promotionId").val()
		 dialog("修改图片","iframe:/promotion/toUploadImage.action?promotion.promotionId="+id+
				 "&imageCode="+code,"550px","450px","iframe",50);
	}
	var myDialog;
	function promotionTinyIconShow(obj){
		if(myDialog)myDialog.close();
		if($(obj).prev().prev().val()){
			var src = $("#imageUrl").val()+$(obj).prev().prev().val();
			myDialog = art.dialog({
				follow:obj,
				//lock: true,
				padding: 0,
				title: '图片',
				//width: '20em',
			   // height: '100',
			    content: '<div id="imageDIV"><img src="'+src+'"></div>'
			    //cancel:true
			});
		}
	}
	function promotionTinyIconClose(){
		//if(myDialog)myDialog.close();
	}
</script>
</head>
<s:set name="parent_name" value="'促销系统'" scope="request"/>
<s:set name="name" value="'促销活动'" scope="request"/>
<s:if test="promotion.promotionId==null">
<s:set name="son_name" value="'添加促销活动属性信息'" scope="request"/>
</s:if><s:else>
<s:set name="son_name" value="'修改促销活动属性信息'" scope="request"/>
</s:else>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
<s:form action="/promotion/updatePromotionAttrInfo.action" method="POST" namespace='/promotion' id="frm">
	<s:hidden name="promotion.promotionId" id="promotionId"></s:hidden>
	<input type="hidden" value='<s:property value="#request.imageUrl"/>' id="imageUrl"/>
	<s:hidden name="promotionAttr.promotionAttrId" id="promotionAttrId"></s:hidden>
	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">属性信息</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">促销活动名称：</th>
		  <td width="80%">
             <input type="text" name="promotion.promotionName" id="promotionName" size="32" 
             maxlength="32" value="<s:property value='promotion.promotionName'/>" readonly='readonly' onblur=""/> 
		  </td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">活动标签：</th>
			<td><label><input type="text" name="promotionAttr.promotionTag" value="<s:property value='promotionAttr.promotionTag' />"
				id="slogan" size="20" maxlength="50" style="width:322px"/> </label></td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">活动小图标：</th>
			<td>
				<s:textfield name="promotionAttr.promotionTinyIcon" id="promotionTinyIcon" 
				size="32" maxlength="512" readonly="true" require="true"/>
				<INPUT TYPE="button" class="button-2s" value="修改" onclick="showUploadImage(3)">
				<a href="javascript:void(0)" onmouseover="promotionTinyIconShow(this)" onmouseout="promotionTinyIconClose()">查看</a>
			</td>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">活动大图标：</th>
		 <td>
			<s:textfield name="promotionAttr.promotionBigIcon" id="promotionBigIcon" size="32" maxlength="512" readonly="true" require="true"
				/>
			<INPUT TYPE="button" class="button-2s" value="修改" onclick="showUploadImage(4)">
			<a href="javascript:void(0)" onmouseover="promotionTinyIconShow(this)" onmouseout="promotionTinyIconClose()">查看</a>
			</td>
		</tr>
	  <tr>
		  <th align="right" class="eidt_rowTitle">活动图片一：</th>
		 <td>
			<s:textfield name="promotionAttr.promotionImgFirst" id="promotionImgFirst" size="32" maxlength="512" readonly="true" require="true"/>
			<INPUT TYPE="button" class="button-2s" value="修改" onclick="showUploadImage(1)">
			<a href="javascript:void(0)" onmouseover="promotionTinyIconShow(this)" onmouseout="promotionTinyIconClose()">查看</a>
			</td>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">活动图片二：</th>
		 <td>
			<s:textfield name="promotionAttr.promotionImgSecond" id="promotionImgSecond" size="32" maxlength="512" readonly="true" require="true"/>
			<INPUT TYPE="button" class="button-2s" value="修改" onclick="showUploadImage(2)">
			<a href="javascript:void(0)" onmouseover="promotionTinyIconShow(this)" onmouseout="promotionTinyIconClose()">查看</a>
			</td>
		</tr>
		<tr>
		  <th align="right" class="eidt_rowTitle">活动地址：</th>
		 <td>
			<s:textfield name="promotionAttr.promotionUrl" id="promotionUrl" size="32" maxlength="512" readonly="true" require="true"
				/>
			</td>
		</tr>
	</table>
	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:history.go(-1);" />
			<td width="20%" align="center"></td>
		</tr>
	</table>
	<br>
	<br>
</s:form>
</body>
</html>

