<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="活动信息"></jsp:param>
</jsp:include>
<title>活动更新</title>
</head>
<style>
#myTextarea {
	height: 300px;
	width: 600px;
	margin-top: 15px;
}
</style>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
		<div class="row-fluid">
			<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_promotion.jsp"></jsp:include>
	<div class="content">
		<div class="row-fluid">
		
			
					
				
			<div class="row-fluid">
				<div class='block_01'>
				<div class="navbar-inner">
						<ul class="breadcrumb">
							<i class="icon-home"></i>
							<li>促销 <span class="divider">/</span></li>
							<li>查看活动详情</li>
							
						</ul>
					</div>
					<input type="hidden"
						value="<s:property value="promotion.mutexPromotionId" />"
						id="mutexPromotionId" />
                      <input type="hidden" value="<s:property value="ruleData"/>" id ="ruleDataValue"/>
                      <input type="hidden" value ="<s:property value="promotion.promotionType"/>" id="promotionType" name =promotion.promotionType></input>
					<table cellpadding="0" cellspacing="0" border="0"
						class="table  table-bordered">
						<colgroup>
							<col class="col-w-120" align="right" />
							<col align="left" />
							<col class="col-w-120" align="right" />
							<col align="left" />
						</colgroup>
						<thead>
							<tr>
								<th class="ui-table-title" colspan="4">基本信息</th>
							</tr>
						</thead>

						<tr>
							<th align="right" class="ui-table-th">所属商家：</th>
							<td class="tdleft" ><s:property
									value="promotion.shopNames" /></td>
						</tr>
						<tr>
							<th align="right" class="ui-table-th">类型：</th>
							<td class="tdleft" ><s:property
									value="#request.promotionTypeMap[promotion.promotionType]" /> 
								<s:property value="productDraftVo.categoryList[1].categoryName" />
								 <s:property
									value="productDraftVo.categoryList[0].categoryName" /></td>
						</tr>
						<tr>
							<th width="80" align="right" class="ui-table-th">优惠数据：</th>

							<td class="tdleft" id="ruleData" ><s:if test="null== promotion.promotionNote || ''==promotion.promotionNote">
	            	<s:iterator value="promotion.ruleDatas" var="dataObj" >
					<s:if test="promotion.promotionType==6">
					满<fmt:formatNumber  value="${meetData}" pattern="#" type="number"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:else>
					件
					</s:else>
					，减<s:property value="prizeData"/>元
					</s:if>
					<s:elseif test="promotion.promotionType==5">
					满<s:property value="meetData"/>元，
					加<s:property value="prizeData"/>元，可得skuId为<a class="toProductSku" title="点击预览" href="javascript:;" data-Id="<s:property value='entityId'/>"  onclick="javascript:toProductSku(<s:property value='entityId'/>)">
					<s:property value="entityId"/></a>的商品
					</s:elseif>
					<s:elseif test="promotion.promotionType==4">
					满<fmt:formatNumber  value="${meetData}" pattern="#" type="number"/>
					<s:if test="meetDataType==1">
					元
					</s:if>
					<s:else>
					件
					</s:else>
					，送id为<a title="点击预览" href="/app/queryCouponDetail.action?viewType=show&couponId=<s:property value='entityId'/>"><s:property value="entityId"/></a>（优惠券）
					</s:elseif>
					<s:elseif test="promotion.promotionType==3">满<s:property value="meetData"/>元，
					赠skuId为<a class="toProductSku" title="点击预览" href="javascript:;" data-Id="<s:property value='entityId'/>" onclick="javascript:toProductSku(<s:property value='entityId'/>)"><s:property value="entityId"/></a>的商品</s:elseif>
					；
					</s:iterator>
				</s:if>
            	<s:else>
            		<s:property value='promotion.promotionNote' />
				</s:else></td>
						</tr>

						<tr>
							<th width="80" align="right" class="ui-table-th">标题：</th>
							<td class="tdleft" ><s:property
									value="promotion.promotionTitle" /></td>
						</tr>
						<!-- maliqun add 20141225 -->
						<tr>
							<th width="80" align="right" class="ui-table-th">页面展示名称：</th>
							<td class="tdleft" ><s:property
									value="promotion.promotionName" /></td>
						</tr>
						<tr>
							<th width="80" align="right" class="ui-table-th">广告语：</th>
							<td class="tdleft"><s:property value="promotion.slogan" /></td>


						</tr>
						<tr>
							<th align="right" class="ui-table-th">优先级：</th>
							<td class="tdleft" ><label><s:property
										value="promotion.promotionPriority" /></label></td>
						</tr>
						<tr>
							<!--  	<th align="right" class="ui-table-th">互斥ID</th>-->
							<td class="tdleft" colspan="3"><s:property
									value="promotion.mutexPromotionId" /> <s:if
									test="promotion.mutexPromotionId!=null&&!promotion.mutexPromotionId.equals('all')">
									<INPUT TYPE="button" class="button-3s" value="查看"
										onclick="queryMutalPromotionList('<s:property value="promotion.mutexPromotionId" />')" />
								</s:if></td>
						</tr>
						<tr>
							<th align="right" class="ui-table-th">活动时间：</th>
							<td class="tdleft" >从<input 
								name="promotion.startTime" id="startTime" disabled="true"
								type="text" class="span3"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})"
								value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>" />
								到 <input  name="promotion.endTime" id="endTime" disabled="true"
								type="text" class="span3"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})"
								value="<s:date name='promotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>" />
							</td>
						</tr>
						<tr>
						
							<td align="right" class="ui-table-th">筛选商品类型：</td>
							<td class="tdleft tdinline"  colspan="3"><s:if test="promotion.productFilterType==2">
		   <s:radio onclick="radio_click(this.value)" disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		  </s:if>
		  <s:elseif test="promotion.productFilterType==1">
		  <s:radio onclick="radio_click(this.value)" disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场'}"/>
			 </s:elseif>
			  <s:elseif test="promotion.productFilterType==3">
		  <s:radio onclick="radio_click(this.value)" disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{3:'商品类目'}"/>
			  </s:elseif>
			  <s:elseif test="promotion.productFilterType==4">
		  <s:radio onclick="radio_click(this.value)" disabled="true" name="promotion.productFilterType" id="productFilterType"
			  list="#{4:'商品品牌'}"/>
			  </s:elseif></td>
			 
						</tr>
						<s:if test="promotion.productFilterType==2">
							<tr>
								<th align="right" class="ui-table-th">活动商品</th>
								<td class="tdleft" colspan="3"><INPUT TYPE="button"
									class="button-2s" value="查看" /></td>
							</tr>
						</s:if>
						<tr>
							<th align="right" class="ui-table-th">活动描述：</th>
							<td class="tdleft" ><label> <s:property
										value="promotion.promotionDescribe" /></label></td>
						</tr>

					</table>
					<div class="form-actions">
          <button class="btn btn-primary j_promotion_list" id="gotoListForPromotion" id="gotoListForPromotion"><i class="icon-chevron-left icon-white"></i> 返回</button>
        </div>
				</div>

                 
 
			

				<s:form action="" method="post" id="frm" name="frm">
				    <input type="hidden" value='<s:property value="promotion.status"/>'  name="status" id="status"/>
					<input type="hidden" value="detail" name="type" id="type"></input>
					<input type="hidden" value="<s:property value="type"/>"
						name="backType" id="backType"></input>
					<input type="hidden"
						value="<s:property value="promotion.promotionId"/>"
						name="promotionProduct.promotionId" id="promotionId">
				</s:form>
			</div>
		</div>
	</div>
	</div>
</body>
</html>