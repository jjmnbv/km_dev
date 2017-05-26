<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.kmzyc.promotion.sys.model.SysMenu"%>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
</head>
<body>
<input id="rtnMsg" type="hidden" value="<s:property value="rtnMsg" />" >
<script>
	<s:if test='message.module=="保存类目"'>
		<s:if test='message.code==0'>
			parent.closePopDiv();
		</s:if>
	</s:if>
	<s:elseif test='message.module=="删除类目属性"'>
		<s:if test='message.code==0'>
			<s:if test='!rtnMsg.isEmpty()'>
				alert(document.getElementById('rtnMsg').value+'与产品有关联，不能删除！');
			</s:if>
			<s:else>
				alert("操作成功！");
			</s:else>
			location.href = '/app/queryCategoryAttrList.action?categoryAttr.categoryId=<s:property value="categoryAttr.categoryId"/>';
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="保存类目属性"'>
		<s:if test='message.code==0'>
			parent.closePopDiv();
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="保存明细栏目"'>   
		<s:if test='message.code==0'>
			parent.closePopDiv();
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="保存咨询回复"'>   
		<s:if test='message.code==0'>
			parent.closePopDiv();
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="修改定时上架任务"'>   
		<s:if test='message.code==0'>
			parent.closePopDiv();
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="保存促销活动产品"'>   
		<s:if test='message.code==0'>
			parent.closePopDiv(<s:property value='promotionProduct.promotionId' />);
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="删除促销活动产品"'>   
		<s:if test='message.code==0'>
		location.href = '/promotion/queryPromotionProductList.action?promotionProduct.promotionId=<s:property value="promotionProduct.promotionId"/>';
		</s:if>
	</s:elseif>
	<s:elseif test='message.module=="删除促销活动"'>   
		<s:if test='message.code==0'>
		location.href = '/promotion/queryPromotionList.action';
		</s:if>
		<s:if test='message.code==1'>
		alert("已发布的活动不能删除！");
		location.href = '/promotion/queryPromotionList.action';
		</s:if>
	</s:elseif>
	
	<s:elseif test="message.module=='promotionAdd'">   
		<s:if test="promotion.productFilterType==2">
			if(confirm("操作成功，是否现在为该促销活动添加活动商品？")){
				location.href = '/promotion/queryPromotionProductList.action?promotionProduct.promotionId=<s:property value="promotion.promotionId"/>';
			}else{
				<%//?promotion.promotionId=<s:property value="promotion.promotionId%>
				location.href = '/promotion/queryPromotionList.action?promotion.nature=<s:property value="promotion.nature"/>&promotion.promotionId=<s:property value="promotion.promotionId"/>';
			}
		</s:if>
		<s:else>
			alert("操作成功");
			location.href = '/promotion/queryPromotionList.action?promotion.nature=<s:property value="promotion.nature"/>&promotion.promotionId=<s:property value="promotion.promotionId"/>';
		</s:else>
	</s:elseif>
	
	<s:elseif test='message.module=="保存优惠券成功"'>
	<s:if test='message.code==0'>
	alert("保存成功");
	location.href ="/app/gotoQueryCouponList.action";
	</s:if>
	</s:elseif>
	
	<s:elseif test='message.module=="优惠券保存出错"'>
	<s:if test='message.code==0'>
	alert("系统出现异常，优惠券保存失败!");
	location.href ="/app/gotoQueryCouponList.action";
	</s:if>
	</s:elseif>
</script>

</BODY>
</HTML>