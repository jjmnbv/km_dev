<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="queryReturnShopList.action" method="post" theme="simple">
<s:hidden name="page" id="page"/>
<s:token></s:token>
<div class="l-right user-m">
 	<div class="o-mt">
   	<h2>退换货记录</h2>
       <div class="Inquiry">
       	<div class="ui-form">
            	<%-- 
				<s:select name="searchKeyword.createDate" id="searchKeyword.createDate" list="createDateOptionsMap" 
					onchange="javascript:query()" cssClass="sele" />
				<s:select name="searchKeyword.status" id="searchKeyword.status" list="#{0:'全部状态',1:'已提交待审核',2:'已通过待退货',3:'已退货待取件',4:'已取件待退款/发货',5:'已退款待确认',6:'已发货待签收',7:'已完成',8:'已驳回'}"
					onchange="javascript:query()" cssClass="sele" />
				--%>
				<s:textfield name="searchKeyword.keyword" id="searchKeyword.keyword" 
					maxlength="20" cssClass="Inquiry-text j_dealKeyword"/>　
				<input name="search" id="search" type="button" value="搜 索" class="bti btn j_querySearch">
           </div>
       </div>
   </div>
   <div class="user-m fn-t10">
       <div class="mc">
       	<div class="ui-table">
           	<table class="ui-table user-table ">
                   <thead>
                       <tr>
                            <th class="td-s7">退换货编号</th>
                            <th class="td-s7">订单编号</th>
                            <th class="td-s1">商品</th>
                            <th class="td-s5">商品数量</th>
                            <th>申请时间</th>
                            <th class="td-s1">状态</th>
                            <th class="td-s10">操作</th>
                       </tr>
                   </thead>
                   <tbody>
                   <s:iterator value="pagintion.recordList" var="var" status="st">
                       <tr>
						   <td><a class="fn-blue" href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">${var.orderAlterCode}</a></td>
						   <td><a class="fn-blue" href="queryOrderDetail.action?orderMainCode=${var.orderCode}">${var.orderCode}</a></td>
						   <td>
						  		<a name="productlink" href="${cmsPagePath}${var.orderItem.productSkuId}.shtml" target="_blank">
									<img  src="${productImgServerUrl}${var.orderItem.defaultProductImage.imgPath}" title="${var.orderItem.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'">
								</a>
						   </td>
                           <td>${var.alterNum}</td>
						   <td>
								<s:date name="#var.createDate" format="yyyy-MM-dd"/><br/>
								<s:date name="#var.createDate" format="HH:mm:ss"/>
						   </td>
							<s:if test="#var.proposeStatus == -2"><td>已驳回</td></s:if>
							<s:if test="#var.proposeStatus == -1"><td>已取消</td></s:if>
							<s:if test="#var.proposeStatus == 1"><td class="fn-red">已提交待审核</td></s:if>
                            <s:if test="#var.proposeStatus == 2"><td>
                            <s:if test="#var.alterType == 2">已通过待换货</s:if>
                            <s:if test="#var.alterType == 1">已通过待退货</s:if>
                            </td></s:if>
							<s:if test="#var.proposeStatus == 3"><td>
							<s:if test="#var.alterType == 2">已换货待取件</s:if>
                            <s:if test="#var.alterType == 1">已退货待取件</s:if>
							</td></s:if>
							<s:if test="#var.proposeStatus == 4"><td>已取件待质检</td></s:if>
							<s:if test="#var.proposeStatus == 51"><td>已质检待退款</td></s:if>
							<s:if test="#var.proposeStatus == 52"><td>已质检待发货</td></s:if>
							<s:if test="#var.proposeStatus == 53"><td>已驳回待返回原件</td></s:if>
							<s:if test="#var.proposeStatus == 54"><td>换货转退货</td></s:if>
							<s:if test="#var.proposeStatus == 59"><td>退款中</td></s:if>
							<s:if test="#var.proposeStatus == 61"><td>已退款待确认</td></s:if>
							<s:if test="#var.proposeStatus == 62"><td>已发货待签收</td></s:if>
							<s:if test="#var.proposeStatus == 63"><td>已返回原件待签收</td></s:if>
							<s:if test="#var.proposeStatus == 7"><td>已完成</td></s:if>
							<s:if test="#var.proposeStatus == 12"><td class="fn-red">送货失败</td></s:if>
 							<td>
								<s:if test="#var.proposeStatus == -1||#var.proposeStatus == -2">
									<p class="fn-blue">
										<span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
										<%--					
										<span><a href="javascript:operateOrderAlter('${var.orderAlterCode}',-3,'','','确定删除此记录吗？')">删除</a></span>
										 --%>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 1">
								    <p><span><a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-status="-1" data-msg="确认要取消此申请吗？" class="yellow-btn js_operateOrderAlter">取消</a></span></p>
                                    <p class="fn-blue">
										<span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
                                    </p>
								</s:if>
                                <s:if test="#var.proposeStatus == 2">
                                	<p><span><a href="javascript:void(0)" data-code="${var.orderAlterCode}" class="yellow-btn js_doReturn">
                                    <s:if test="#var.alterType == 2">换货</s:if>
                                    <s:if test="#var.alterType == 1">退货</s:if>
                                	</a></span></p>
                                    <p class="fn-blue">
                                   		<span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
                                    </p>
                                </s:if>
								<s:if test="#var.proposeStatus == 3">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 4">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 51">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 52">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 53">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 54">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 59">
									<p class="fn-blue">
									    <span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 61">
									<p><span><a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-status="7" data-msg="确定已收到退款？"  class="green-btn js_operateOrderAlter">确认收款</a></span></p>
                                    <p class="fn-blue"><span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span></p>
								</s:if>
								<s:if test="#var.proposeStatus == 62">
									<p><span><a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-status="7" data-msg="确定已收到退货？" class="green-btn js_operateOrderAlter">确认收货</a></span></p>
                                    <p class="fn-blue"><span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span></p>
								</s:if>
								<s:if test="#var.proposeStatus == 63">
									<p><span><a href="javascript:void(0)" data-code="${var.orderAlterCode}" data-status="7" data-msg="确定已收到返回原件？" class="green-btn js_operateOrderAlter">确认收货</a></span></p>
                                    <p class="fn-blue"><span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span></p>
								</s:if>
								<s:if test="#var.proposeStatus == 7">
									<p class="fn-blue">
										<span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span><span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
										<%--
										<span><a href="gotoAppendAssessProduct.action?orderCodes=${var.orderCode}">追加评价</a></span>
									 	--%>
									</p>
								</s:if>
								<s:if test="#var.proposeStatus == 12">
									<p><span><a href="javascript:void(0)" class="fn-red">联系客服</a></span></p>
                                    <p class="fn-blue">
                                    	<span><a href="queryApplyDetail.action?orderAlterCode=${var.orderAlterCode}">查看</a></span>
                                    	<span><a href="checkReturnGoodsTracing.action?returnGoodsCode=${var.orderAlterCode}">跟踪</a></span>
                                    </p>
								</s:if>
							</td>
				   </s:iterator>
                   </tbody>
               </table>
			<div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件  -->
            			<tiles:insertDefinition name="pagination"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</s:form>
