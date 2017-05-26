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
	<jsp:param name="titlePrefix" value="活动更新"></jsp:param>
</jsp:include>
<title>活动更新</title>
</head>
<style>
#myTextarea{
	height:300px;
	width:600px;
	margin-top:15px; 
}
</style>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
		<div class="row-fluid">
			<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_promotion.jsp"></jsp:include>
			
<div class="content">
			
					
<div class="row-fluid">
		<div class='block_01'>
		
						<div class="navbar-inner">
							<ul class="breadcrumb">
								<i class="icon-home"></i>
								<li>促销 <span class="divider">/</span></li>
								<li>促销详细信息编辑</li>
								
							</ul>
						</div>
		<s:form action="/promotion/updatePromotion.action"	method="POST" namespace='/promotion' id="frm">
		<div class="block-content collapse in"><!--开始-->
		<input type="hidden" value="<s:property value="ruleData"/>" id ="ruleDataValue"/>
		<input type="hidden" value ="<s:property value="promotion.promotionType"/>" id="promotionType" name ="promotion.promotionType"></input>
		<input type="hidden" value ="<s:property value="promotion.promotionId"/>" id="promotionId" name ="promotion.promotionId"></input>
		<input type="hidden" value ="<s:property value="promotion.promotionId"/>" id="promotionProduct.promotionId" name ="promotionProduct.promotionId"></input>
		<input type="hidden" value ="<s:property value="promotion.channel"/>" id="channel" name ="promotion.channel"></input>
		<input type="hidden" value ="<s:property value="promotion.mutexPromotionId"/>" id="selcetMutexPromotionIds" ></input>
		<input type="hidden" value ="<s:property value="promotion.promotionFilterProductName"/>" id="promotionFilterProductName" ></input>
		<input type="hidden" value='<s:property value="promotion.productFilterType"/>' id="productFilterTypeDB"/>
		<input type="hidden" value='<s:property value="promotion.productFilterSql"/>' id="productFilterSqlDB"/>	
		<input type="hidden" value='<s:property value="promotion.status"/>'  name="status" id="status"/>	
		<input type="hidden" value="<s:property value="type"/>" name ="type" id = "type"></input>	
			<table cellpadding="0" cellspacing="0" border="0"
	class="table  table-bordered">
				
					<tr class="tablesbg">
						<th class="ui-table-title" colspan="4">基本信息</th>
					</tr>
				
				
				<tr>
					<td class="width200 shoptR">所属商家：</td>
					<td class="tdleft" ><s:property value="promotion.shopNames"/></td>
				</tr>
				<tr>
					<td class="width200 shoptR">类型：</td>
					<td class="tdleft" ><s:property value="#request.promotionTypeMap[promotion.promotionType]"/> <s:property value="productDraftVo.categoryList[1].categoryName"/>  <s:property value="productDraftVo.categoryList[0].categoryName"/></td>
				</tr>
				<tr>
					<td class="width200 shoptR">优惠数据：</td>
					
		  	         <td class="tdleft" colspan="3" id="ruleData">
            	    <s:if test="null== promotion.promotionNote || ''==promotion.promotionNote">
	            	<s:iterator value="promotion.ruleDatas" var="dataObj" >
					<s:if test="promotion.promotionType==6">
					满<fmt:formatNumber  value="${meetData}" pattern="#" type="number"/>
					<s:if test="meetDataType==1">
					元</s:if>
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
				</s:else>
		  	</td>
				</tr>
				
				<tr>
					<td class="width200 shoptR"><font color="red">*</font>标题：</td>
					<td class="tdleft" colspan="3">  <input <s:if test="promotion.status==2">disabled="true"</s:if> type="text" name="promotion.promotionTitle" id="promotionTitle" size="70" 
             maxlength="70" value="<s:property value='promotion.promotionTitle' />"
				style="width:400px" onblur=""/> </td>
				</tr>	
				<!-- maliqun add 20141225 -->		
				<tr>
					<td class="width200 shoptR"><font color="red">*</font>页面展示名称：</td>
					<td class="tdleft" > <input type="text" name="promotion.promotionName" id="promotionName" size="70" 
             maxlength="70" value="<s:property value='promotion.promotionName' />"
				style="width:400px" onblur=""/> </td>
				</tr>
				<tr>
					<td class="width200 shoptR">广告语：</td>
						<td class="tdleft"><label><input type="text" name="promotion.slogan" value="<s:property value='promotion.slogan' />"
				id="slogan" size="20" maxlength="50" style="width:322px"/> </label></td>
					
					
					</tr>
							 <tr>
			<td class="width200 shoptR"><font color="red">*</font>活动时间：</td>
			<td class="tdleft" colspan="3">从<input <s:if test="promotion.status==2">disabled="true"</s:if> name="promotion.startTime" id="startTime" 
					type="text" class="span3" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>"/> 
			到
			<input <s:if test="promotion.status==2">disabled="true"</s:if> name="promotion.endTime" id="endTime"
					type="text" class="span3" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d 0:0:0'})" 
					value="<s:date name='promotion.endTime' format='yyyy-MM-dd HH:mm:ss'/>"/>
			</td>
		</tr>
				
				<tr>
				<td class="width200 shoptR">筛选商品类型：</td>
		  <td class="tdleft tdinline" colspan="3">
		
	<!-- 	  <s:radio  name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌'}"/> -->
			   <s:if test="promotion.promotionType==8||promotion.promotionType==10">
		   <s:radio  name="promotion.productFilterType" id="productFilterType"
			  list="#{2:'指定商品'}"/> 
		  </s:if>
		  <s:if test="promotion.promotionType!=8&&promotion.promotionType!=10&&promotion.status==2">
		  <s:radio  name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品'}" disabled="true"/>
			  	
		  </s:if>
		  <s:if test="promotion.promotionType!=8&&promotion.promotionType!=10&&promotion.status!=2">
		   <s:radio name="promotion.productFilterType" id="productFilterType"
			  list="#{1:'全场',2:'指定商品'}" />
			  	
		 
		  </s:if>
		   	<input class="selectEm"  id="selectEm"  type="hidden" disabled="disabled" value="选择"/>
		 	<input type="hidden" name="promotion.productFilterSql" 
   		 	id="productFilterSql" value='<s:property value="promotion.productFilterSql"/>'/>
		
		  <div id="productFilterNames"><br/>
		  </div>
	  </td>
	  <!-- 
				</tr>
				   <tr>
		 	<th align="right" class="eidt_rowTitle">强制排他：</th>
			<td>
				<input type="checkbox" id="mutexAll" <s:if test="promotion.mutexPromotionId=='all'" >checked="checked"</s:if> />设置为强制排它
			</td>
	        </tr>
				<tr>
			<th align="right" class="ui-table-th">互斥ID</th>
			<td class="fn-text-lt" colspan="3">
			<div>
			<s:iterator value="pagee.dataList" id="pro">
			<div class="sbDiv"><em unselectable="on"><s:property value="promotionName"/></em>
			<a class="deleteP" hidefocus="hidefocus" data-value="<s:property value="sellerId"/>" >x</a>
			</div>
			</s:iterator>
			<input class="selectComPromotion" type="button" style="float:left;" id="selectComPromotion" <s:if test="promotion.mutexPromotionId=='all'" >disabled="disabled"</s:if> style="float:left;"  value="选择">
			</div>
			<span>
			<input type="hidden" style="display: inline;" name="promotion.mutexPromotionId"  
			data-value='<s:property value="promotion.mutexPromotionId"/>' 
			value='<s:property value="promotion.mutexPromotionId"/>' id="mutexPromotionId" 
			size="50"/>
			
			</span>
			</td>
		</tr>
		-->
	  
	  
		<tr>
			<td class="width200 shoptR">活动描述：</td>
			<td class="tdleft" ><label> <s:textarea  style="height:100px;width:450px; color:#ccc;" name="promotion.promotionDescribe" id="promotionDescribe" rows="8" cols="45" /></label></td>
		</tr>
		
			</table>
			</s:form>
		</div>
		
		<div class="form-actions"><a id="updatePromotion" href="#" class="btn btn-success btn-large">保存</a></div>
		
		
		<input id="type" name="type" value='<s:property value="type"/>' type="hidden"/>
		
		</div>
		<s:form action="queryPromotionList" method="POST"
	namespace='/promotion' id="queryPromotionList" name="queryPromotionList">
</s:form>
</div>
</div>
</div>
</div>
</body>
</html>