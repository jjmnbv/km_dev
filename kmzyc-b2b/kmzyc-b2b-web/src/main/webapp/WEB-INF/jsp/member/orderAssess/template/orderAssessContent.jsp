<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="AssessContentFrom" name="sform"
	action="saveAssessContent.action" method="post" theme="simple">
	<!--隐藏数据域-->
	<input name="prodappraisePoint" type="hidden" id="prodapp" value="" />
	<input name="orderappraisePoint" type="hidden" id="orderapp" value="" />
	<input name="prodappraiseContent" type="hidden" id="prodContent" value="" />
	<input name="orderCodes" type="hidden" id="orderCod" 	value="<s:property  value="orderMain.orderCode" />" />
	<input name="orderHavePoint" type="hidden" id="orderHavePoint" value="<s:property  value="orderHavePoint" />" />
	<input name="saveOk1" type="hidden" id="saveOks" value="<s:property  value="saveOk" />" />
	<input name="pagenumber" type="hidden" id="pagenumbers" value="<s:property  value="pagenumber" />" />
	<input name="isOrderList" type="hidden" id="isOrderListId" value="<s:property  value="isOrderList" />" />
	<input name="cars" type="hidden" id="carsId"  />
	<input name="specal" type="hidden" id="specalIdP"  />
	<input name="specalA" type="hidden" id="specalIdA"  />
	<input name="lenght1" type="hidden" id="lengthP"  />
	<input name="length2" type="hidden" id="lengthA"  />
	<s:hidden name="page" id="page" />
	<s:token></s:token>

	<div class="l-right user-m">
		<div class="o-mt">
			<h2>商品评分</h2>
			<div class="OrderInfo">
				<ul class="Order-Number">
					<li>
						订单编号：
						<s:property value="orderMain.orderCode" />
					</li>
					<li>
						状态:
						<s:if test="orderMain.orderStatus == -1">已取消</s:if>
						<s:if	test="orderMain.orderStatus == 1">未付款</s:if>
						<s:if test="orderMain.orderStatus == 2 ||  orderMain.orderStatus == 3 || orderMain.orderStatus == 4|| orderMain.orderStatus == 20|| orderMain.orderStatus == 21|| orderMain.orderStatus == 22">已付款</s:if>
						<s:if test="orderMain.orderStatus == 5">已发货</s:if>
						<s:if test="orderMain.orderStatus == 6">已完成</s:if>
						<s:if test="orderMain.orderStatus == 7">已评价</s:if>
					</li>

					<p id="statusInfo">
				<s:if test="expressPath.data!=null">
				     <strong>最新进度:</strong> <span>[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>]
				     </span>
					 <s:property value="expressPath.data[0].context" /> 
				  </s:if>
				  <s:elseif test="listorder!=null">
			          <strong>最新进度:</strong> <span>[<s:date name="listorder[0].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
					</span>
					<s:property value="listorder[0].operateInfo" />	  
				  </s:elseif>
				  <s:else>暂无进度  </s:else>
						<%-- <a href="initOrderLogistics.action?orderTrailInfo.expressComName=${orderMain.logisticsName }&orderTrailInfo.expressNo=${orderMain.logisticsOrderNo }&orderMainId=${orderMainId}&orderTrailInfo.orderNo=${orderMain.orderCode}&orderTrailInfo.orderStatus=${orderMain.orderStatus}&backFlag=1&isOrderList=${isOrderList}&pagenumber=${pagenumber}"
						class="fn-blue j_orderLogisticsUrl">进度跟踪</a> --%>
					</p>
				</ul>
			</div>
		</div>

		<div class="user-m fn-t10">
			<div class="mc">
				<div class="m-w w-noborder fn-clear">
					<div class="wc" style="height: 151px;">
						<s:if test='orderAssessPoint == null'>
							<div id="pingjia">
	
								<div class="star" title="宝贝描述相符"> <font style="float: left;">宝贝描述相符：</font>
									<ul>
										<li>
											<a href="javascript:;">1</a>
										</li>
										<li>
											<a href="javascript:;">2</a>
										</li>
										<li>
											<a href="javascript:;">3</a>
										</li>
										<li>
											<a href="javascript:;">4</a>
										</li>
										<li>
											<a href="javascript:;">5</a>
										</li>
									</ul>
									<span></span>
									<p></p>
									<input type="hidden" name="point" class="orderPoint" />
									<input	type="hidden" name="score" />
								</div>
								<!--star end-->
								
								<div class="star" title="卖家发货速度">
									<font style="float: left;">卖家发货速度：</font>
									<ul>
										<li>
											<a href="javascript:;">1</a>
										</li>
										<li>
											<a href="javascript:;">2</a>
										</li>
										<li>
											<a href="javascript:;">3</a>
										</li>
										<li>
											<a href="javascript:;">4</a>
										</li>
										<li>
											<a href="javascript:;">5</a>
										</li>
									</ul>
									<span></span>
									<p></p>
									<input type="hidden" name="point" class="orderPoint" />
									<input	type="hidden" name="score" />
								</div>
								<!--star end-->
							
								<div class="star" title="物流配送速度"> <font style="float: left;">物流配送速度： </font>
									<ul>
										<li>
											<a href="javascript:;">1</a>
										</li>
										<li>
											<a href="javascript:;">2</a>
										</li>
										<li>
											<a href="javascript:;">3</a>
										</li>
										<li>
											<a href="javascript:;">4</a>
										</li>
										<li>
											<a href="javascript:;">5</a>
										</li>
									</ul>
									<span></span>
									<p></p>
									<input type="hidden" name="point" class="orderPoint" />
									<input	type="hidden" name="score" />
								</div>
								<!--star end-->

								<div class="star" title="售前售后服务">
									<font style="float: left;">售前售后服务：</font>
									<ul>
										<li>
											<a href="javascript:;">1</a>
										</li>
										<li>
											<a href="javascript:;">2</a>
										</li>
										<li>
											<a href="javascript:;">3</a>
										</li>
										<li>
											<a href="javascript:;">4</a>
										</li>
										<li>
											<a href="javascript:;">5</a>
										</li>
									</ul>
									<span></span>
									<p></p>
									<input type="hidden" name="point" class="orderPoint" />
									<input type="hidden" name="score" />
								</div>
								<span id="prompt<s:property value='#st.index' />" style=
			   						 "display: none" class="ico-tag" name="orderWarning"	>亲，你还没给我打分哦!
			   						</span>
								<!--star end-->
							</div>
						</s:if>

						<s:if test='orderAssessPoint!=null'>
							<div id="pingjia">
								<div class="star" title="宝贝描述相符">
									<font style="float: left;">宝贝描述相符</font>
									<span class="sml-star" style=" padding-left:10px">
										<span class="sml-star-default">
											<span class="sml-star-present s<s:property value='orderAssessPoint.Assess_Type_one' />">
										</span>
									</span>
								</span>
								<span>
									<strong><s:property value='orderAssessPoint.Assess_Type_one' />
										分</strong> 
									<s:property value="#request.orderAssessMap[orderAssessPoint.Assess_Type_one]"  />
								</span>
								<input type="hidden" name="point"
										value="<s:property value='orderAssessPoint.Assess_Type_one' />"  class="orderPoint" />
								<input type="hidden" name="score"
										value="<s:property value='orderAssessPoint.Assess_Type_one' /> " />
							</div>
							<!--star end-->

							<div class="star" title="包装是否完好">
								<font style="float: left;">卖家发货速度</font>
								<span class="sml-star" style=" padding-left:10px">
									<span class="sml-star-default">
										<span class="sml-star-present s<s:property value='orderAssessPoint.Assess_Type_two' />">
									</span>
								</span>
							</span>

							<span>
								<strong><s:property value='orderAssessPoint.Assess_Type_two' />
									分</strong> 
								<s:property value="#request.orderAssessMap[orderAssessPoint.Assess_Type_two]"  />
							</span>

							<p></p>
							<input type="hidden" name="point"
										value="<s:property value='orderAssessPoint.Assess_Type_two' />" class="orderPoint" />
							<input type="hidden" name="score"
										value="<s:property value='orderAssessPoint.Assess_Type_two' />" />
						</div>
						<!--star end-->

						<div class="star" title="卖家发货速度">
							<font style="float: left;">物流配送速度</font>
							<span class="sml-star" style=" padding-left:10px">
								<span class="sml-star-default">
									<span class="sml-star-present s<s:property value='orderAssessPoint.Assess_Type_three' />">
								</span>
							</span>
						</span>

						<span>
							<strong>
								<s:property value='orderAssessPoint.Assess_Type_three' />
								分
							</strong>
							<s:property value="#request.orderAssessMap[orderAssessPoint.Assess_Type_three]"  />
						</span>
						<p></p>
						<input type="hidden" name="point" class="orderPoint"
										value="<s:property value='orderAssessPoint.Assess_Type_three' />" />
						<input type="hidden" name="score"
										value="<s:property value='orderAssessPoint.Assess_Type_three' />" />
					</div>
					<!--star end-->

					<div class="star" title="售前售后服务">
						<font style="float: left;">售前售后服务</font>
						<span class="sml-star" style=" padding-left:10px">
							<span class="sml-star-default">
								<span class="sml-star-present s<s:property value='orderAssessPoint.Assess_Type_four' />">
							</span>
						</span>
					</span>

					<span>
						<strong>
							<s:property value='orderAssessPoint.Assess_Type_four' />
							分
						</strong>
						<s:property value="#request.orderAssessMap[orderAssessPoint.Assess_Type_four]"  />
					</span>
					<p></p>
					<input type="hidden" name="point" class="orderPoint"
										value="<s:property value='orderAssessPoint.Assess_Type_four' />" />
					<input type="hidden" name="score"
										value="<s:property value='orderAssessPoint.Assess_Type_four' />" />
				</div>
				<!--star end-->
			</div>
		</s:if>
	</div>
</div>
</div>
<div class="Evaluate goods-grade">
<!--明细循环-->
<s:iterator value="orderItemList" var="orderItem" status='st'>

	<div class="item fn-clear fn-t10">

		<div class="user">
			<div class="u-icon">
				<a href="<s:property value="productDetailUrl" />/${orderItem.productSkuId}.html" target="_blank" >
				<img	src="${productImgServerUrl}${orderItem.defaultProductImage.imgPath}"  title="${orderItem.commodityName}"    onerror="this.onerror=''; this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'" />
				</a>
			</div>
		<div class="p-name">
			<a href="<s:property value="productDetailUrl" />/${orderItem.productSkuId}.html" target="_blank" >
									${orderItem.commodityName}
			</a>
	</div>
	<input name="appraiseId[<s:property value='#st.index' />]" value="${orderItem.prodApraiseList.appraiseId}" class="appendPraiseId" type="hidden">
	</div>

<div class="i-item">
<div class="arrowLeft"></div>
<div class="i-item-c">
	<div class="evaluate-m">
		<p class="start_p">
			<label>评分：</label>
		 
				<!-- 在没有评分的时候 -->
				<s:if test="#orderItem.prodApraiseList==null">
					<div class="o-topic" >
						<span>
							<div class="star" title="包装">
								<ul>
									<li>
										<a href="javascript:;">1</a>
									</li>
									<li>
										<a href="javascript:;">2</a>
									</li>
									<li>
										<a href="javascript:;">3</a>
									</li>
									<li>
										<a href="javascript:;">4</a>
									</li>
									<li>
										<a href="javascript:;">5</a>
									</li>
								</ul>
								<span></span>
								<p></p>
								<input type="hidden" name="point" id="productPoint"
															class="product_point" value="${orderItem.prodApraiseList.point}" />
								<input  type="hidden" name="score" value="${orderItem.prodApraiseList.point}" />
							</div>
						</span>
					</div>
			</s:if>
		 
			<!-- 在有评分的时候 -->
			<s:else>
				<div class="o-topic">
					<span class="sml-star">
						<span class="sml-star-default">
							<span class="sml-star-present s${orderItem.prodApraiseList.point}"></span>
						</span>
					</span>
					
					<span class="star">
						<strong>${orderItem.prodApraiseList.point}分</strong>
						<s:property value="#request.orderAssessMap[#orderItem.prodApraiseList.point]" />
					</span>
				
					<input type="hidden" name="point" id="productPoint" class="product_point" value="${orderItem.prodApraiseList.point}" />
					<input type="hidden" name="score" value="${orderItem.prodApraiseList.point}" />
				</div>
			</s:else>

			<s:if test="#orderItem.prodApraiseList==null">
			<dl class="grade-item">
				<dt class="grade-item-title fn-left">评价：</dt>
				<dd class="fn-block">
				<textarea textinput="textinput" cols="131" rows="10"  name="contents[<s:property value='#st.index' />]"  
				 style="width:672px" maxlength="300" minlength="10"	id="prod_Content" class="prod_Content"></textarea>
				<div class="grade-info">
			   			<span class="msg-text fn-right">0-300字</span>
			   			<span id="prompt<s:property value='#st.index' />"  name="prodWarning" style=
			   			"display: none" class="ico-tag">你的评分是偶们前进的动力!</span>
			   			<span id="complete<s:property value='#st.index' />"  name="completeing" style=
			   			"display: none" class="ico-tag">请评分,并填写评价!</span>
			   			<span id="nospecial<s:property value='#st.index' />" style=
			   			"display: none" class="ico-tag" name="specialWarning"  >请勿填写特殊字符!</span>
			   			<span id="moreTen<s:property value='#st.index' />" style=
			   			"display: none" class="ico-tag" name="moreTenWarning"  >请填写大于10个字符的评价!</span>
			   			 
			   	 </div>
				</dd> 
			</dl>
			</s:if>
		 
		<s:else>			
			<dl class="grade-item">
				<dt class="grade-item-title fn-left">评价：</dt>
				<dd class="fn-block">
				${orderItem.prodApraiseList.appraiseContent}
			<input  type="hidden" class="prod_Content" value="${orderItem.prodApraiseList.appraiseContent}" name="contents[<s:property value='#st.index' />]" /> 
				</dd> 
			</dl>
		</s:else>
		
		
	  <s:iterator value="#orderItem.appraiseReplyList" var="appraiseAplyList">
		<s:iterator value="#appraiseAplyList" var="appraiseAply" status="applyStr">
			  	<s:property value="appraiseAply.replyStatus" />
			<s:if test="#appraiseAply.replyStatus == 1 " >
	 		<dl class="grade-item"> 
			<dt class="grade-item-title fn-left">回复：</dt>
			<dd class="fn-block">
			<p class="goods-grade-tips">
				<s:property value="#appraiseAply.replContent" />
			</p>
			</dd>
			 </dl>
			</s:if>
		</s:iterator>
	</s:iterator> 
	
<s:iterator value="#orderItem.appraiseAdd" var="appraiseAddList"  >

	<s:iterator value="#appraiseAddList" var="appraiseAdd" status='vail'>
		<!-- 追加评价框是否存在是根据是否有评价,追平内容是否为空 -->
		<s:if  test="#orderItem.prodApraiseList.appraiseContent!=null && #appraiseAdd.addContent==null">
			<dl class="grade-item">
				<dt class="grade-item-title fn-left">追评：</dt>
				<dd class="fn-block">
			   	<textarea 	textinput="textinput" cols="131" rows="10"	name="appendContents[<s:property value='#st.index' />]"  class="appendContent"
			   	maxlength="300" style="width:672px"></textarea>
			   		<div class="grade-info">
			   			<span class="msg-text fn-right">0-300字</span>
			   			<span id="appendContent<s:property value='#st.index' />" style=
			   			"display: none" class="ico-tag" name="prodWarning"  >你的评分是偶们前进的动力</span>
			   			<span id="nospecial<s:property value='#st.index' />" style=
			   			"display: none" class="ico-tag" name="specialWarning"  >请勿填写特殊字符!</span>
			   			<span id="moreTen<s:property value='#st.index' />" style=
			   			"display: none" class="ico-tag" name="moreTenWarning"  >请填写大于10个字符的评价!</span>   			
			   		 </div>
			           </dd>
			</dl>
		</s:if>

		<s:if test="#appraiseAdd.addContent!=null"  >
			<dl class="grade-item">
				<dt class="grade-item-title fn-left">追评：</dt>
				<dd class="fn-block">
			   	${appraiseAddList.addContent}
					<input  name="appendContents[<s:property value='#st.index' />]"  class="appendContent" value="${appraiseAddList.addContent}" type="hidden">
			           </dd>
			</dl>
		</s:if>
	</s:iterator>
</s:iterator>
</div>

</div>
</div>
</div>

</s:iterator>
</div>
<!--明细订单循环-->
</div>
	<div style="left: 666px; top: 982px; display: block; display: none" class="tooltip bottom" id="starTitle">
		<div class="tooltip-inner">请给星星打分</div>
		<div class="tooltip-arrow"><i></i>
		</div>
	</div>
	<div class="button">
		<div>
		<s:if test="prodHavePoint== 0">
		<a class="btn-submit fn-r10"   id="subButon" href="javascript:void(0)" ><span>发表</span></a>
		</s:if>
		<a	class="btn-cancel" href=""><span>返回</span></a>
		</div>
	</div>
</div>
</s:form>
<div>
<input type="hidden" id="orderMainId" value="<s:property value='orderMain.orderId' />" />
<input type="hidden" id="orderStatusId" value="<s:property value='orderMain.orderStatus' />" />
</div>

<script type="text/javascript">

</script>
</div>
