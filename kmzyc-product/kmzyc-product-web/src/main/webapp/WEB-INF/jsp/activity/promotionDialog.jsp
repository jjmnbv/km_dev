<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表选择SKU码</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>

<script type="text/javascript"
	src="/etc/js/validate/easy_validator.pack.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/etc/js/common.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css"    />
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}

table {
	margin-left: 10px;
}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
	<!-- 数据列表区域 -->
	<s:if test="promotionInfo == null">
		<div>
			无该促销活动！
		</div>
	</s:if>
	<s:else>
		<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th  align="center" width="20%">活动名称</th>
				<th  align="center" width="10%">类型</th>
				<th  align="center" width="20%">商家</th>
				<th  align="center" width="30%">起止时间</th>
				<th  align="center" width="10%">时间状态</th>
				<th  align="center" width="10%">审核状态</th>
			</tr>
			<tr>
				<td align="center" width="20%">
					<s:property value="promotionInfo.promotionTitle" />
				</td>
				<td align="center" width="10%">
					<s:iterator value="#request.promotionTypeMap"  >
					  <s:if test="promotionInfo.promotionType==key"  >
					  <s:property   value="value"   />  
					  </s:if>
					</s:iterator>
				</td>
				<td align="center" width="20%">
					<s:property value="promotionInfo.shopNames" />
				</td>
				<td align="center" width="30%">
					<s:date name="promotionInfo.startTime" format="yyyy-MM-dd HH:mm:ss" />
					</br>
					&nbsp;至&nbsp;
					</br>
					<s:date name="promotionInfo.endTime" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td align="center" width="10%">
					<s:if test='stTime>nowTime'>
						未上线
					</s:if> 
					<s:elseif test="nowTime>enTime">已过期</s:elseif>
					<s:else>正在进行</s:else>
				</td>
				<td align="center" width="10%">
					<s:iterator value="#request.PromotionStatusMap"  >
					  <s:if test="promotionInfo.status==key"  >
					  <s:property   value="value"   />  
					  </s:if>
					</s:iterator>
				</td>
			</tr>
		</table> 
	</s:else>   
</BODY>
</HTML>

