<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.cms.parse.PathConstants"%>
<html>
	<head>
		<title>详细页面列发布</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link href="/etc/js/autocomplete/autocompletestyles.css" rel="stylesheet" type="text/css" />
			 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		
		<script type="text/javascript"  src="/etc/js/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript"  src="/etc/js/autocomplete/jquery.mockjax.js"></script>
		 
		<script type="text/javascript"  src="/etc/js/autocomplete/demo.js"></script>
		
		<script type="text/javascript">
		$(document).ready(function(){
		 	$("#pageDetailForm").validate({
		         rules: {
						"viewProductInfo.startProductId": {digits:true},
						"viewProductInfo.productSkuCode": {digits:true},
						"viewProductInfo.endProductId":{digits:true,compareTo:true}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
         	 });
 		});
 jQuery.validator.addMethod("compareTo", function(value, element) {
 		var startProductId=$("#startProductId").val();
 		if(value<startProductId)
 			return false;
 		return true;
}, "不能小于起始主键!");
 
 
 
 
		</script>
		
		   
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'cms系统'" scope="request" />
		<s:set name="name" value="'页面管理'" scope="request" />
		<s:set name="son_name" value="'详细页面发布'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form name="pageDetailForm" id="pageDetailForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/runDetailPubilsh_runnableDetailPubilsh.action" method="post">
				<!--   /cms/pagePublish_detailPublish.action  --> 
				<table width="70%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
					
					<tr>
						<td colspan="2" class="edit_title">
							产品详细内容发布
						</td>
					</tr>
					<tr>
					   <td width="25%" align="right">
							按页面模板发布：
						</td>
						<td width="75%">
							<select name="templateType">
						        <option value="4" selected="selected">详情页标准模板</option>
						        <option value="20">详情页秒杀模板</option>
						         <option value="22">详情页处方模板</option>
						         <option value="30">详情页预售模板</option>
						       </select>
						</td>
					</tr>
					<tr>
					   <td width="25%" align="right">
							秒杀模板截止时间：
						</td>
						<td width="75%">
						   	<input name="promotionsEndDate" id="promotionsEndDate" type="text" readonly="readonly" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						
						</td>
					</tr>
					<tr>
						<td width="25%" align="right">
							按产品SKU编号范围发布：
						</td>
						<td width="75%" align="left">
							<input type="text" name="viewProductInfo.startProductId" id="startProductId"/>
							到
							<input type="text" name="viewProductInfo.endProductId" />
						</td>
					</tr>
					<tr>
						<td width="25%" align="right">
							按产品SKU_CODE发布：
						</td>
						<td width="75%">
							<input type="text" name="viewProductInfo.productSkuCode" id="productSkuCode"/>
						</td>
					</tr>
					 
					<tr>
					   <td width="25%" align="right">
							按商家发布：
						</td>
						<td width="75%">
						<input type="text" id="autocomplete_forSuppliers" name="viewProductInfo.shopName">
						</td>
					</tr>
					<tr>
						<td width="25%" align="right">
							按产品所属品牌发布：
						</td>
						<td width="75%">
							<s:select name="viewProductInfo.brandId" list="brandList"
							  headerKey="" headerValue="===请选择==="	listKey="brandId" listValue="brandName"></s:select>
						</td>
					</tr>
					
			    
				
					<%--<tr>
						<td align="right">按产品所属渠道：</td>
							<td>
								<select name="channel">
							<s:if test="channelQuery.split(',').length != 1">
								<option value="<s:property value='channelQuery'/>">全部渠道</option>
							</s:if>
							   	<s:generator val="channelQuery" separator="," id="channels">   
								</s:generator> 
							<s:iterator status="channelIndex" value="#request.channels" id="channelName">
							
								<s:if test="#channelName==channel">
									<option selected="selected" value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
								</s:if> 
								<s:else>
										<option value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
								</s:else>
							     
							</s:iterator>   
							
							
							
							</select>      	
							</td>
						
					</tr>--%>
				</table>
				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input class="publishBtn" type="submit" value="">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>

