<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:hidden name="page" id="page"/>
<s:token></s:token>
<div class="l-right user-m">
	<div class="o-mt">
  	<h2>退换货记录查看</h2>
      <div class="OrderInfo">
      	<ul class="Order-Number">
          	<li>退换货编号：${orderAlter.orderAlterCode}</li>
              <li>状态:
              		<strong class="fn-green">
							<s:if test="orderAlter.proposeStatus==-1">已取消</s:if>
							<s:if test="orderAlter.proposeStatus==-2">已驳回</s:if>
							<s:if test="orderAlter.proposeStatus==1">已提交待审核</s:if>
                            <s:if test="orderAlter.proposeStatus==2">已通过审核</s:if>
							<s:if test="orderAlter.proposeStatus==3">
							<s:if test="orderAlter.alterType==1">已退货待取件</s:if>
							<s:if test="orderAlter.alterType==2">已换货待取件</s:if>
							</s:if>
							<s:if test="orderAlter.proposeStatus==4">已取件待质检</s:if>
							<s:if test="orderAlter.proposeStatus==51">已通过待退款</s:if>
							<s:if test="orderAlter.proposeStatus==52">已通过待发货</s:if>
							<s:if test="orderAlter.proposeStatus==53">未通过质检</s:if>
							<s:if test="orderAlter.proposeStatus==54">换货转退货</s:if>
							<s:if test="orderAlter.proposeStatus==59">退款中</s:if>
							<s:if test="orderAlter.proposeStatus==61">已退款待确认</s:if>
							<s:if test="orderAlter.proposeStatus==62">已发货待签收</s:if>
							<s:if test="orderAlter.proposeStatus==63">已返回原件待签收</s:if>
							<s:if test="orderAlter.proposeStatus==7">已完成</s:if>
							<s:if test="orderAlter.proposeStatus==12">送货失败</s:if>
              		</strong>
                            <s:if test="orderAlter.proposeStatus==1">
                            <a data-code="${orderAlter.orderAlterCode}" data-status="-1" data-msg="确认要取消此申请吗？"  class="btn-add js_operateOrderAlter"><span>取消</span></a>
                            </s:if>
                            <s:if test="orderAlter.proposeStatus==2">
                            <a data-code="${orderAlter.orderAlterCode}"  class="btn-add js_doReturn"><span>
                            	<s:if test="orderAlter.alterType==2">换货</s:if>
								<s:if test="orderAlter.alterType==1">退货</s:if>
                            </span></a>
                            </s:if>
							<s:if test="orderAlter.proposeStatus==61">
	                        <a data-code="${orderAlter.orderAlterCode}" data-status="7" data-msg="确定已收到退款？"  class="btn-add js_operateOrderAlter"><span>确认收款</span></a>
							</s:if>
							<s:if test="orderAlter.proposeStatus==62">
                            <a data-code="${orderAlter.orderAlterCode}" data-status="7" data-msg="确定已收到换货？"  class="btn-add js_operateOrderAlter"><span>确认收货</span></a>
							</s:if>
							<s:if test="orderAlter.proposeStatus==63">
                            <a data-code="${orderAlter.orderAlterCode}" data-status="7" data-msg="确定已收到返回原件？"  class="btn-add js_operateOrderAlter"><span>确认收货</span></a>
							</s:if>
							<s:if test="orderAlter.proposeStatus==12">
                            <a data-code="${orderAlter.orderAlterCode}" data-status="3" data-msg="确定？"  class="btn-add js_operateOrderAlter"><span>取消</span></a>
							</s:if>
              		</li>
              <p><strong>最新进度:</strong><span>[${time}]</span>${alterComment}<a href="checkReturnGoodsTracing.action?returnGoodsCode=${orderAlter.orderAlterCode}" class="fn-blue">进度跟踪</a></p>
          </ul>
      </div>
      <div class="fn-clear">
          <!--<div class="Order-Schedule">
          	<div class="Order-Schedule-c No1"></div>
          </div>-->
		<div class="oper-shop oper-shopbi-40">
                 	<ul>
                 	<s:if test="-2==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span></li>
                     </s:if>
                 	<s:if test="-1==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span></li>
                     </s:if>
                 	<s:if test="1==orderAlter.proposeStatus">
                     	<li class="oper-shop1"><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="2==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="3==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="4==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="51==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="52==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="53==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="54==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="61==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="62==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="63==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </s:if>
                 	<s:if test="7==orderAlter.proposeStatus">
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>2</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop3"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span></li>
                     </s:if>
                     </ul>
                 </div>
                 <div class="operatxt operatxt-117">  
                     <ul>
                     <%--
		   			 <s:iterator id="type" value="operateTypes">
		   				<s:property value='#type.key''#type.value'/>"
		   		  	 	<s:if test="#var+1>orderAlter.operateTypes">
                     	 	<li class="oper-shop3"><span></span><i>${#var+1}</i><i></i></li>
                     	 	</s:if>
                     	 	<s:elseif test="#var+1<orderAlter.proposeStatus">
                     	 	<li class="oper-shop1"><span></span><i>${#var+1}</i><i></i></li>
                     	 	</s:elseif>
                     	 	<s:else>
                     	 	<li class="oper-shop2"><span></span><i></i></li>
                     	 	</s:else>
                     </s:iterator>
                      --%>
                     <s:if test="-2==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">康美中药城驳回
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">完成
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="-1==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">取消申请
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==-1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">完成
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==-1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="1==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家
                         	<s:if test="orderAlter.alterType==2">换货</s:if>
							<s:if test="orderAlter.alterType==1">退货</s:if>
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">收到
                            <s:if test="orderAlter.alterType==2">换货</s:if>
							<s:if test="orderAlter.alterType==1">退货</s:if>
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">
                            <s:if test="orderAlter.alterType==2">重新发货</s:if>
							<s:if test="orderAlter.alterType==1">退款</s:if>
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">
                            <s:if test="orderAlter.alterType==2">买家收到换货</s:if>
							<s:if test="orderAlter.alterType==1">买家收到退款</s:if>
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="2==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">收到退货<p>
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="3==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="4==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="51==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="52==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="53==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="54==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="61==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="62==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="63==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     <s:if test="7==orderAlter.proposeStatus">
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2&&#orderAlterTraceInfo.status!=51">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">收到退货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">退款/重新发货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6||#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">买家收到退款/换货
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">完成
                         <p>
                            <s:iterator value="listVoline" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==10">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </s:if>
                     </ul>
                 </div>                
             </div>
         </div>
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>申请明细：</h3></div>
      <div class="wc">
          <ul class="form-info ">
          	  <li><div class="upload-photos-form "><span>服务类型：${orderAlter.alterType==2?'换货':(orderAlter.alterType==1?'退货':(orderAlter.alterType==3?'不退货退款':'超时未发货赔付'))}</span><span>申请凭据：${orderAlter.evidence==null?'无':'有发票'}</span></div></li>
              <li><div class="upload-photos-form"><span>商品名称：<a href="${cmsPagePath}${orderItem.productSkuId}.shtml" class="fn-blue" target="_blank">[${orderItem.commoditySku}]&nbsp;&nbsp;${orderItem.commodityName}</a></span><span>商品编号：${orderAlter.orderItem.commoditySku}</span><span>商品数量：${orderAlter.alterNum}</span></div></li>
              <li><label>问题描述：</label><div class="sh-name item"><textarea name="" cols="" rows="" class="Complaints-text" readonly="readonly">${orderAlter.alterComment}</textarea></div></li>
              <li>
              <s:if test="null!=orderAlter.auditComment">
              <li><label>审核说明：</label><div class="sh-name item"><textarea name="" cols="" rows="" class="Complaints-text" readonly="readonly">${orderAlter.auditComment}</textarea></div></li>
              <li>
              </s:if>
              <s:if test="null!=orderAlter.qualityComment">
              <li><label>质检说明：</label><div class="sh-name item"><textarea name="" cols="" rows="" class="Complaints-text" readonly="readonly">${orderAlter.qualityComment}</textarea></div></li>
              <li>
              </s:if>
              <label>上传图片：</label>
              <div class="sh-name">
                  <ul class="upload-photos">	
                  	 <s:iterator value="photoList" var="potoUrl">
                  	 	<li><img src="${showPath}${potoUrl.url}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></li>
                  	 </s:iterator>
                  </ul>
              </div>
              </li>
          </ul>
      </div>
  </div>
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>退换赔付信息：</h3></div>
      <div class="wc">
          <ul class="form-info ">
          <s:if test="orderAlter.alterType==4">
          <li><span>
                          赔付信息：
			  定金退款金额￥${orderAlter.deposit==null?0.00:orderAlter.deposit}元，
			   尾款退款金额￥${(orderAlter.finalmoney==null||orderAlter.finalmoney ==0)?0.00:orderAlter.finalmoney}元，  
			  定金补偿金额￥${(orderAlter.compensate==null)?0.00:orderAlter.compensate}元，
			  共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
			  （定金和尾款将原路退回您的支付账户，定金补偿金额将充入您的个人余额）
			  </span></li>
            </s:if>
             <s:elseif test="om.orderType==7">
             <li><span>
                             赔付信息：
			     定金退款金额￥${orderAlter.deposit==null?0.00:orderAlter.deposit}元，
			     尾款退款金额￥${(orderAlter.finalmoney==null||orderAlter.finalmoney ==0)?0.00:orderAlter.finalmoney}元，  
			     共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
			  </span></li>
            </s:elseif>
          <s:elseif test="orderAlter.ruturnSum!=null&&orderAlter.ruturnSum!=0&&orderAlter.proposeStatus>1&&orderAlter.proposeStatus!=53">
          	  <li><span>
          	  赔付信息：
          	  商品退款金额￥${orderAlter.ruturnMoney==null?0.00:orderAlter.ruturnMoney}元，
          	 退货返运费￥${(orderAlter.returnFare==null||orderAlter.returnFare ==0)?0.00:orderAlter.returnFare}元，  
          	  补偿运费￥${(orderAlter.fareSubsidy==null)?0.00:orderAlter.fareSubsidy}元，
          	  共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
          	 <%--
          	 <s:if test="null!=orderAlter.preferentialAmount&0!=orderAlter.preferentialAmount">
          	  返还面值为￥${orderAlter.preferentialAmount}元优惠券一张；
          	 </s:if>
          	 --%>
          	  </span></li>
          	  </s:elseif>
              <s:if test="orderAlter.alterType==2">
              <s:if test="orderAlter.proposeStatus>=3">
              <li><span>买家退回给商家快递信息：${orderAlter.customerLogisticsName}</span><span>运单号：${orderAlter.customerLogisticsNo} </span>
              <span>时间：
              <s:iterator value="listVo" var="orderAlterTraceInfo" >
              	<s:if test="#orderAlterTraceInfo.operatStatus==3">
              		<s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
              	</s:if>
              </s:iterator>
              </span></li>
              </s:if>
              <s:else>
              <li><span>买家退回给商家快递信息：尚未                         	
              		<s:if test="orderAlter.alterType==2">换货</s:if>
					<s:if test="orderAlter.alterType==1">退货</s:if>
			  </span></li>
              </s:else>
              <%-- <li><span>商家收货地址：${kmAddress}</span></li>
              <li><span>商家收货人：${kmName}</span><span>邮编：${kmZipCode}</span></li>
              <li><span>手机号码：${kmPhone}</span></li> --%>
              
              <s:if test="orderAlter.proposeStatus>=6">
              <li><span>商家给买家换货快递信息：${orderAlter.logisticsName}</span><span>运单号：${orderAlter.logisticsOrderNo}</span>   
              <span>时间：
              <s:iterator value="listVo" var="orderAlterTraceInfo" >
              	<s:if test="#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
              		<s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
              	</s:if>
              </s:iterator>
              </span></li>
              </s:if>
              <s:else>
              <li><span>商家给买家换货快递信息：尚未发货</span></li>
              </s:else>
              <li><span>买家收货地址：${orderAlter.province}${orderAlter.city}${orderAlter.area}${orderAlter.address}</span></li>
              <li><span>买家收货人：${orderAlter.name}</span><span>邮编${orderAlter.zipcode}</span></li>
              <li><span>买家电话：${orderAlter.phone}</span></li>
              </s:if>
          </ul>
      </div>
  </div>
  <div class="button"><div><a class="btn-submit"  id="cancel"><span>返回</span></a></div></div>
</div>
  <!--fn-right-->
</div>
<!--内容区 END-->	
<div id="question" style="display:none"></div>
