<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="fn-p20">
	<table class="ui-table table-bordered">
		<tr>
			<th colspan="2">订单评价详情</th>
		</tr>
		<tr>
			<th>订单编号:</th>
			<td><s:property value='orderAssessInfo.orderCode'/></td>
		</tr>
		<tr>
			<th>客户名:</th>
			<td>
			<s:property value='orderAssessInfo.guestNum'/>
			</td>
		</tr>
	<s:iterator id="assessDetail" value="oadlist">
		<tr>
			<td width="30%" ><s:property value='#assessDetail.assessName'/></td>
			<td width="70%">
			<s:if test="#assessDetail.assessScore==1">
			非常不满意
			</s:if>
			<s:if test="#assessDetail.assessScore==2">
			不满意
			</s:if>
			<s:if test="#assessDetail.assessScore==3">
			一般
			</s:if>
			<s:if test="#assessDetail.assessScore==4">
			满意
			</s:if>
			<s:if test="#assessDetail.assessScore==5">
			非常满意
			</s:if>
			</td>
		</tr>		
	</s:iterator>
		<tr>
			<th colspan="2">商品评价详情</th>
		</tr>
		<tr>
			<td colspan="2">
				<table class="ui-table table-bordered">
					<tr>
						<th>产品名称</th>
						<th>满意度</th>
						<th>评价内容</th>
					</tr>
					<s:iterator id="paList" value="#request.prodAssessList">
						<tr>
							<td><a href="<s:property value="#request.productViewPath"/><s:property value="#paList.productSkuId" />.html" target="_blank"><s:property value="#paList.productName" /></a></td>
							<td><s:property value="#paList.point" />分（<s:property value="#paList.satisficing" />）</td>
							<td>
								<s:property value="#paList.appraiseContent" />
								<s:if test='#paList.addContent != ""'>
									（<span style="color:green;">追评：</span><s:property default="暂无追评" value="#paList.addContent" />）
								</s:if>
							</td>
						</tr>
					</s:iterator>				
				</table>
			</td>
		</tr>
		<!--<tr> 
		<th width="30%" class="eidt_rowTitle">评价内容：</th>
		<td width="70%" > 
			<textarea rows="4" cols="30" disabled="disabled" id="assessContext" name="orderAssessInfo.assessContext" ><s:property value='orderAssessInfo.assessContext'/></textarea> 
		</td>
		
	</tr>
		-->
		<tr>
			<td colspan="2" align="center">
				<input value="返回" type="button" class="ui-button ui-button-default ui-button-lg" id="cancel" />
			</td>
		</tr>
	</table>
	
</div>