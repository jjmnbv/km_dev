<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form id="sform" name="sform" action="queryOrderList.action" method="post" theme="simple">
<input name="page" type="hidden"  id="page">
<input name="pagenumbs" type="hidden" value="${page}" id="pages">
<input name="isSureOk" type="hidden" value="${isSureOk}" id="isSureOk">
<input name="orderCodes1" type="hidden" value="${orderMainCode}" id="orderMainCode"> </input>
<input name="orderMainCode" type="hidden" value="" id="code">
<input name="orderUserid" type="hidden" value="" id="userId">
<input name="orderMainStatus" type="hidden" value="" id="status"/>
<input name="applyContent" type="hidden" value="" id="zxApplyContent">	
<div class="l-right user-m">
	<div class="o-mt">
     	<h2>我的订单</h2>
        <div class="Inquiry" >
             <div class="ui-form">
                       
					<select name="searchKeyword.createDate"
						id="searchKeyword.createDate" class="sele j_queryByTime">
						<c:forEach var="item" items="${createDateOptionsMap}">
							<option <c:if test="${searchKeyword.createDate==item.key}"> selected="selected"</c:if> value="${item.key}">${item.value}</option>
						</c:forEach>
					</select> 
					     
					<select  name="searchKeyword.orderStatus" id="searchKeyword.orderStatus"	class="sele j_queryByStatus">
						<option <c:if test="${searchKeyword.orderStatus==0}"> selected="selected"</c:if> value="0" >全部状态</option>
						<option <c:if test="${searchKeyword.orderStatus==1}"> selected="selected"</c:if> value="1">未付款</option>
						<option <c:if test="${searchKeyword.orderStatus==2}"> selected="selected"</c:if> value="2">已付款</option>
						<option <c:if test="${searchKeyword.orderStatus==3}"> selected="selected"</c:if> value="3">配货中</option>
						<option <c:if test="${searchKeyword.orderStatus==4}"> selected="selected"</c:if> value="4">已发货</option>
						<option <c:if test="${searchKeyword.orderStatus==5}"> selected="selected"</c:if> value="5">已完成</option>
						<option <c:if test="${searchKeyword.orderStatus==7}"> selected="selected"</c:if> value="7">已取消</option>
						<option <c:if test="${searchKeyword.orderStatus==8}"> selected="selected"</c:if> value="8">取消审核中</option>
					</select>
			
			    <input type="text" name="searchKeyword.keyword" maxlength="20" value="商品名称、商品编号、订单编号" id="searchKeyword.keyword" class="Inquiry-text j_dealKeyword" />
				<input name="search" id="search" type="button" value="搜 索" class="btn-b btn-b-success j_querySearch" />
			</div>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mt">
					<ul class="tab">
						<li class="curr"><s></s><b></b><a>全部订单</a>
					</ul>
		</div>
		<div class="mc">
			<div class="ui-table">
				<table class="ui-table user-table">
					<thead>
						<tr>
							<th class="td-s7">订单编号</th>
							<th class="td-s2">订单商品</th>
							<th class="td-s3">收货人</th>
							<th class="td-s3">下单日期</th>
							<th class="td-s3">总金额</th>
							<th class="td-s3">订单状态</th>
							<th class="td-128">操作</th>
						</tr>
					</thead>
					<tbody>
					<%
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.HOUR,-15*24);
						pageContext.setAttribute("checkDate", cal.getTime());
					%>
					 <c:forEach var="orderMainVar" items="${pagintion.recordList }" varStatus="orderMainStatus">
						<tr  <c:if test="${orderMainVar.parentOrderCode != null}"> class="strike" </c:if>
						     
						     <c:if test="${orderMainVar.orderStatus==16}">class="strike strike-hd"</c:if>>
							<td 
							<c:if test="${orderMainVar.parentOrderCode != null}"> class="strike-bd-first" </c:if>
							
							<c:if test="${orderMainVar.orderStatus==16}">class="strike-hd-first"</c:if>>
							
							<c:choose>
								<c:when test="${orderMainVar.orderType == 7 }">
									<a class="fn-blue" href="javascript:void(0);"> ${orderMainVar.orderCode}</a>
								</c:when>
								<c:otherwise>
									<a class="fn-blue" href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">
									${orderMainVar.orderCode}</a>
								</c:otherwise>
							</c:choose>
							
							</td>
							<td class="fn-text-left td-s2">
							<c:if test="${orderMainVar.orderStatus != 16}">
							<c:forEach var="orderItemVar" items="${orderMainVar.orderItemList}" varStatus="orderItemStatus">
		<input value="${orderItemVar.defaultProductImage.skuId}"  name="rows${orderMainVar.orderId}"  type="hidden" /> 
									<a name="productlink" href="${cmsPagePath}${orderItemVar.defaultProductImage.skuId}.shtml" target="_blank">
								<img  src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}"
								 onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'">
									</a>
								</c:forEach>
							</c:if>
							
							</td>
							<td>${orderMainVar.consigneeName}</td>
							<td>
								<fmt:formatDate value="${orderMainVar.createDate}" pattern="yyyy-MM-dd"/><br/>
								<fmt:formatDate value="${orderMainVar.createDate}" pattern="HH:mm:ss"/>
							</td>
							<td style="padding: 1px 1px 1px 1px;">
								<c:choose>
									<c:when test="${orderMainVar.parentOrderCode != null}">
         										&nbsp;
   									  </c:when>
									<c:otherwise>
										
          								<fmt:formatNumber value="${orderMainVar.amountPayable}" type="currency" pattern="#,##0.00元"/>
          								<span style="color:#AFADAD;padding: 0px 0px 0px 0px;">
	          								<c:if test="${orderMainVar.orderType == 7 }">          									           								            								
			           								<br/>定金:<fmt:formatNumber value="${orderMainVar.depositSum}" type="currency" pattern="#,##0.00"/>
			           								<!--<c:choose>
			           									<c:when test="${orderMainVar.orderStatus >  1}">(已支付)</c:when>
			           									<c:otherwise>(未支付)</c:otherwise>
			           								</c:choose>-->
			           								<br/>尾款:<fmt:formatNumber value="${orderMainVar.amountPayable-orderMainVar.depositSum}" type="currency" pattern="#,##0.00元"/>
			           								<!--<c:choose>
			           									<c:when test="${orderMainVar.orderStatus > 1 && orderMainVar.orderStatus !=  23}">(已支付)</c:when>
			           									<c:otherwise>(未支付)</c:otherwise>
			           								</c:choose>-->	           								
		          							</c:if>
          								</span>
    								</c:otherwise>
								</c:choose> 
					        </td>
					           <c:choose>
								<c:when test="${orderMainVar.orderStatus == -2}"><td >取消审核中</td></c:when>
								<c:when test="${orderMainVar.orderStatus == -1}"><td >已取消</td></c:when>
								<c:when test="${orderMainVar.orderStatus ==  1}"><td class="fn-red"> 未付款</td></c:when>
								<c:when test="${orderMainVar.orderStatus ==  23}"><td class="fn-red"> 待付尾款</td></c:when>
								<c:when test="${orderMainVar.orderStatus ==  2|| orderMainVar.orderStatus == 20|| orderMainVar.orderStatus == 21|| orderMainVar.orderStatus == 22}"><td >已付款</td></c:when>
								<c:when test="${orderMainVar.orderStatus == 3 || orderMainVar.orderStatus == 4|| orderMainVar.orderStatus == 15|| orderMainVar.orderStatus == 18}"><td>配货中</td></c:when>
								<c:when test="${orderMainVar.orderStatus == 5}"><td class="j_status${orderMainStatus.index}">已发货</td></c:when>
								<c:when test="${orderMainVar.orderStatus == 6}"><td class="j_status${orderMainStatus.index}">已完成</td></c:when>
								<c:when test="${orderMainVar.orderStatus == 12}"><td class="fn-red">送货失败</td></c:when>
								<c:when test="${orderMainVar.orderStatus == 16}"><td>已拆分</td></c:when>
								<c:when test="${orderMainVar.orderStatus == -3}"><td>订单异常</td></c:when>
								</c:choose>
							
							<td>
							<c:if test="${orderMainVar.orderType != 7 }">
							 <c:choose>
								<c:when test="${orderMainVar.orderStatus == -1 }">
									<p class="fn-blue fn-t5">
										<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
										<span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<span><a href="javascript:void(0);" class="j_sureOrderDelete" data-orderCode="${orderMainVar.orderCode}"data-userId="${orderMainVar.customerId}">删除</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
									</p>
								</c:when>
								<c:when test="${orderMainVar.orderStatus == -2}">
									<p class="fn-blue fn-t5">
										<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
										<span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
									</p>
								</c:when>
								
								<c:when test="${orderMainVar.orderStatus == 1}">
									<p><span><a href="javascript:void(0);" data-orderCode="${orderMainVar.orderCode}" class="red-btn j_pay">付款</a></span></p>
                                    <p class="fn-blue fn-t5">
                                    	<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                    	<span><a href="javascript:void(0);" class="j_sureToCancl" data-orderType="${orderMainVar.orderType}" data-orderStatus="${orderMainVar.orderStatus}" data-orderCode="${orderMainVar.orderCode}" data-userId="${orderMainVar.customerId}" data-type="-1">取消</a></span>
                                    </p>
								</c:when>
                                <c:when test="${orderMainVar.orderStatus == 2 || orderMainVar.orderStatus == 25|| orderMainVar.orderStatus == 20|| orderMainVar.orderStatus == 21|| orderMainVar.orderStatus == 22}">
                                    <p class="fn-blue fn-t5">
                                       <span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                       <span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<span><a href="javascript:void(0);" class="j_sureToCancl"  data-orderType="${orderMainVar.orderType}"   data-orderStatus="${orderMainVar.orderStatus}" data-orderCode="${orderMainVar.orderCode}" data-userId="${orderMainVar.customerId}" data-type="-1">取消</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
                                    </p>
                                </c:when>
								<c:when test="${orderMainVar.orderStatus == 3 || orderMainVar.orderStatus == 4}">
									<p class="fn-blue fn-t5">
										<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
										<span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
									</p>
								</c:when>
								<c:when test="${orderMainVar.orderStatus == 5}">
									<p>
										<span><a href="javascript:void(0);" id="sureToshou" data-orderCode="${orderMainVar.orderCode}" data-type="6"
									 	class="green-btn j_sureTosureOrder" prows="${orderMainStatus.index}">确认收货</a></span>
									</p>
                                    <p class="fn-blue fn-t5">
										<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                    	<span class="j_byAg"><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
								</c:when>
								
								<c:when test="${orderMainVar.orderStatus == 6 }">
								  <c:choose>
								   <c:when test="${orderMainVar.assessStatus == 1}">
									 <span><a href="gotoAssessProduct.action?orderCodes=${orderMainVar.orderCode}&isOrderList=1&pagenumber=${page}" class="yellow-btn"  >
									 评价</a></span>
							        </c:when> 
							        <c:when test="${orderMainVar.assessStatus == 2}">
									 <span><a href="gotoAssessProduct.action?orderCodes=${orderMainVar.orderCode}&isOrderList=1&pagenumber=${page}" class="yellow-btn"  >
									已评价</a></span>
							       </c:when>
							      </c:choose> 
                                    <p class="fn-blue fn-t5">
                                        <span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                        <span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<c:if test="${checkDate<=orderMainVar.finishDate }">
											<span><a href="applyPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">退换货</a></span>
										</c:if>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
                                    </p>
								</c:when>
								
								<c:when test="${orderMainVar.orderStatus == 7 }">
									<p class="fn-blue fn-t5">
										<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
										<span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<span><a href="applyPrepare.action?searchKeyword.keyword=${orderMainVar.orderCode}&checkOverDate=false">退换货</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
									</p>
								</c:when>
								<c:when test="${orderMainVar.orderStatus == 12}">
									<p><span>联系客服</span></p>
                                    <p class="fn-blue fn-t5">
                                    	<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                    	<span><a href="javascript:void(0);" class="j_byAgain" data-orderId="rows${orderMainVar.orderId}" >再购买</a></span>
										<span><a href="javascript:void(0);" class="j_sureToCancl" data-orderCode="${orderMainVar.orderCode}" data-type="-1">取消</a></span>
										<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
											<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
										</div>
                                    </p>
								</c:when>
								<c:when test="${orderMainVar.orderStatus == 16}">
                                    <p class="fn-blue fn-t5">
                                    	<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                    </p>
								</c:when>
								<c:otherwise>
								   <p class="fn-blue fn-t5">
                                    	<span><a href="queryOrderDetail.action?orderMainCode=${orderMainVar.orderCode}">查看</a></span>
                                    </p>
								</c:otherwise>
								
								 </c:choose>
								 </c:if>
							</td>
						</tr>
					  </c:forEach> 
					</tbody>
				 </table>
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
</div>
</form>
