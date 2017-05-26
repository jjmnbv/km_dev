<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<form action="queryIsEvaluateList.action" name="frmIsEvaluate" id="frmIsEvaluate" method="post" >
<input type="hidden"  value="<s:property value='isOrderList' />" name="isOrderList"  id="isOrderListId" />
<input name="pagenumber" type="hidden" id="pagenumbers" value="<s:property value='page' />" />
<s:hidden name="page" id="page" />
<div class="l-right user-m">
<div class="o-mt">
<h2>我的评价</h2>
</div>
<div class="user-m fn-t10">
<div class="mt">
<ul class="tab">
	<li><s></s><b></b><a name="waitAssess" href="javascript:void(0);">待评价订单</a></li>
	<li class="curr"><s></s><b></b><a name="haveAssess" href="javascript:void(0);">已评价订单</a></li>
</ul>
</div>
<div class="mc">
<div class="ui-table">
<s:iterator value="#request.pagintion.recordList">
	<table class="ui-table user-table fn-b10">
		<thead>
			<tr>
				<th class="td-s11 fn-text-left">订单编号： <a href="javascript:void(0);" class="fn-blue" name="orderCodeName" data-id="<s:property value="orderCode" />" ><s:property value="orderCode" /></a>
				<span class="fn-l10">下单时间：<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></span></th>
				<th ><a class="fn-right fn-blue " href="javascript:void(0)" name="wantAssess" data-id="<s:property value="orderCode" />" >查看评分</a></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="commodities">
				<tr>
					<td>
					
						<a href="<s:property value="productDetailUrl" />/<s:property value="skuId" />.html" target="_blank" >
						<b class="img"><img src="<s:property value="productImgServerUrl" /><s:property value="imgPath" />" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></b>
							<div class="td-name fn-text-left fn-blue">
								<s:property value="commodityName"/>
								<p class="fn-gray">
									<s:iterator value="attrValues"><s:property value="attribute" />：<s:property value="value" />&nbsp;&nbsp;</s:iterator>
								</p>
							</div>
						</a>
					</td>
					<td>
						<s:if test='apprContent != null'>
							<div class="fn-left td-s8">
								<p class="fn-clear">
									<span class="star"><span class="star-default"><span class="star-present s<s:property value="point" />"></span></span></span>
								</p>
								<p title="<s:property value="apprContent" />" class="fn-text-left fn-hide33"><s:property value="apprContent" /></p>
							</div>
							<div class="fn-right td-s1">
								<s:if test='addToContent == 0'>
									<a class="green-btn fn-right" data-id="<s:property value='orderCode'/>" href="javascript:void(0)" name="appendContent" />追加</a>
								</s:if>
								<s:if test='addToContent == 1'>
									<a class="green-btn fn-right" data-id="<s:property value='orderCode'/>" href="javascript:void(0)" name="viewContent" />查看评价</a>
								</s:if>
							</div>
						</s:if>
						<s:else>
							<div class="fn-right td-s1">
								<p>
									<a class="green-btn fn-right" name="wantAssess" data-id="<s:property value="orderCode"  />" href="javascript:void(0)" >我要评价</a>
								</p>
							</div>
						</s:else>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:iterator>
</div>
<div class="fn-tr fn-t10">
<div class="ui-page">
	<!-- 分页组件 -->
	<tiles:insertDefinition name="pagination"/>
</div>
</div>
</div>
</div>
</div>
</form>
</div>
