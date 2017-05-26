<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.cms.parse.PathConstants"%>
<html>
	<head>
		<title>套餐页面列发布</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		
			<script type="text/javascript">
		$(document).ready(function(){
		 	$("#productRelationForm").validate({
		         rules: {
						"productRelation.relationId": {digits:true},
						
						"productRelation.mainSkuId":{digits:true,compareTo:true}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
         	 });
 		});
 jQuery.validator.addMethod("compareTo", function(value, element) {
 		var startProductId=$("#relationId").val();
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
		<s:set name="son_name" value="'套餐页面发布'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form name="productRelationForm" id="productRelationForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/pageDulitaocan_pageDuli.action" method="post">
				<table width="70%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
					<tr>
						<td colspan="2" class="edit_title">
							产品套餐内容发布
						</td>
					</tr>
					
					
					<tr>
						<td width="25%" align="right">
							按套餐主键范围发布：
						</td>
						<td width="75%">
							<input type="text" name="productRelation.relationId"  id="relationId"/>
							
							
							到
							<input type="text" name="productRelation.mainSkuId" />
							
						
						</td>
					</tr>
					
					<tr>
						<td align="right">按套餐所属站点：</td>
							<td>
								<select name="productRelation.webSite">
							      <s:if test="channelQuery.split(',').length != 1">
								<option value="<s:property value='channelQuery'/>">全部站点</option>
							      </s:if>
							   	<s:generator val="channelQuery" separator="," id="channels">   
								</s:generator> 
							<s:iterator status="channelIndex" value="#request.channels" id="channelName">
							
								<s:if test="#channelName==webSite">
									<option selected="selected" value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
								</s:if> 
								<s:else>
										<option value="<s:property value="channelName"/>"><s:property value="channelName"/></option>
								</s:else>
							     
							</s:iterator>   
							</select>      	
							</td>
						
					</tr>
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

