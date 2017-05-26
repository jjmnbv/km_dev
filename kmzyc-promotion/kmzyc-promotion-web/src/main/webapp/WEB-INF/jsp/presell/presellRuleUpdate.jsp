<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>

<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<script type="text/javascript"  src="/etc/js/presell/presellRuleUpdate.js"></script>
<title>新增</title>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 15px;
}
.emDiv,.sbDiv{
float: left;position:relative;margin:3px 5px 2px 0;
white-space:nowrap;height:15px;line-height: 15px;
cursor:pointer;border-radius:17px;border-style:
solid;border-width:1px;font-size:14px;
padding:2px 19px;border-color:#edb8b8;
background-color:#ffeaea;color:#c30!important;
display:inline-block;vertical-align:middle;
}

em{
margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;
text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;
}
.aclose,.deleteP{position: absolute;right: -2px;top: -1px;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;
font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;}

.update_status{
color:#888888;

font-size: 12px;
}
</style>
</head>   
<body>   
	<s:set name="parent_name" value="'预售管理  '" scope="request"/>
	<s:set name="name" value="'预售商品管理'" scope="request"/>
	<s:set name="son_name" value="'新增'" scope="request"/>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<s:form action="/presell/updatePresellRule.action" method="POST" namespace='/presell' id="presellRuleUpdateForm">
	<!--   隐藏数据域!-->
	<input type="hidden" name="promotionPresell.presellId" value="<s:property value='promotionPresell.presellId' />">
	<input type="hidden" name="promotionPresell.auditStatus" value="<s:property value='promotionPresell.auditStatus' />">
	<input type="hidden" name="promotionPresell.depositStartTime" value='<s:date name='promotionPresell.depositStartTime' format='yyyy-MM-dd HH:mm:ss' />'>
	<!-- 数据编辑区域 -->
	<table width="90%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">新增预售</th>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">预售标题：</th>
			<td width="80%"><s:property value='promotionPresell.presellTitle' /></td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">商家类别：</th>
			<td width="80%"><s:property value="#request.presellSupplierTypeMap[promotionPresell.shopSort]" /></td>
		</tr>
		<s:if test="promotionPresell.shopSort==2">
			<tr>
				<th width="20%" align="right" class="eidt_rowTitle">所属商家：</th>
				<td width="80%" > <s:property value="promotionPresell.corporateName"/> </td>
			</tr>
		</s:if>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">添加预售商品：</th>
			<td width="80%" >
				<table width="98%" class="list_table" align="center" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C1C8D2">
					<tr>
						<th align="center" width="15%">产品标题</th>
						<th align="center" width="15%">SKU</th>
						<th align="center" width="10%">品牌</th>
						<th align="center" width="10%">单价</th>
						<th align="center" width="10%">实际库存</th>
						<th align="center" width="10%">预售价</th>
						<th align="center" width="10%">定金</th>
						<th align="center" width="10%">预售库存</th>
					</tr>
					<tbody id="productContent">
						<s:if test="promotionPresell.listPresellProduct!=null && promotionPresell.listPresellProduct.size>0">
							<s:iterator value="promotionPresell.listPresellProduct">
								<tr>
									<td align="center"><s:property value="productTitle" /></td>
									<td align="center"><s:property value="productSkuCode"/> </td>
									<td align="center"><s:property value="brandName"/> </td>
									<td align="center"><s:property value="price"/> </td>
									<td align="center"><s:property value="stock"/> </td>
									<td align="center"><s:property value="presellPrice"/> </td>
									<td align="center"><s:property value="depositPrice"/> </td>
									<td align="center"><span class="presell_Stock"><s:property value="presellStock"/></span> </td>
								</tr>
							</s:iterator>
						</s:if>
		            </tbody>
				</table>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">初始预售数</th>
			<td width="80%" ><input type="text" name="promotionPresell.initialPresellNum" size="70" maxlength="8" style="width: 80px" 
				onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
				value="<s:property value='promotionPresell.initialPresellNum' />" />件</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>每人最多限购</th>
		  	<td width="80%" >
			  	<span id="byLimitId">
			  		<input type="radio" name="byLimitType" class="byLimity_type_class" value="1" checked="checked"/> 限制每人最多预定
				  	<input type="text" id="byLimit" name="promotionPresell.byLimit" size="70" maxlength="8" style="width: 80px" 
						onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
						value="<s:property value='promotionPresell.byLimit' />" />件
				</span>  
				<span id="noByLimitId">
					<input type="radio" name="byLimitType" class="byLimity_type_class" value="2"/> 不限购<font id="byLimitTypeFont" color="red">（不推荐)</font>
				</span>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>定金支付起止时间</th>
		  	<td width="80%" >
		  		<input class="Wdate" type="text" id="depositStartTime"  value="<s:date name='promotionPresell.depositStartTime' format='yyyy-MM-dd HH:mm:ss' />"
			   	   disabled="disabled"/>至   
				<input class="Wdate" type="text" id="depositEndTime" name="promotionPresell.depositEndTime"  value="<s:date name='promotionPresell.depositEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					autocomplete="off" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 				 
		  	</td>
		</tr>
		<tr>
			<th width="20%"  align="right" class="eidt_rowTitle"><font color="red">*</font>尾款支付起止时间</th>
		  	<td width="80%">
				<input class="Wdate" type="text" id="finalpayStartTime" name="promotionPresell.finalpayStartTime" value="<s:date name='promotionPresell.finalpayStartTime' format='yyyy-MM-dd HH:mm:ss' />"
					autocomplete="off" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />至  
				<input class="Wdate" type="text" id="finalpayEndTime" name="promotionPresell.finalpayEndTime"  value="<s:date name='promotionPresell.finalpayEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					autocomplete="off" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>发货时间</th>
		    <td  width="80%" >
		    	<input class="Wdate" type="text" id="deliveryStartTime" name="promotionPresell.deliveryStartTime" value="<s:date name='promotionPresell.deliveryStartTime' format='yyyy-MM-dd HH:mm:ss' />"
					autocomplete="off" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />至  
				<input class="Wdate" type="text" id="deliveryEndTime" name="promotionPresell.deliveryEndTime"  value="<s:date name='promotionPresell.deliveryEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					autocomplete="off" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
		    </td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">预售说明</th>
		  	<td width="80%">
			  	<s:textarea style="height:100px;width:1014px;" name="promotionPresell.presellDescribe"  maxlength="500"  />
			</td>
		</tr>
	</table>
	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
            	<input class="saveBtn" id="submitPresellRuleId" type="button" onclick="submitPresellRule()">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input  type="button" class="backBtn" onClick="goBack()" />
			<td width="20%" align="center"></td>
		</tr>		
	</table>
	<br>
	<br>
</s:form>
</body>
</html>

