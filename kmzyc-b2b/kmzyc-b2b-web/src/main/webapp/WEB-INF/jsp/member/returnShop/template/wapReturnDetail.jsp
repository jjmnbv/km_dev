<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="btnabs"><a href="checkWapReturnGoodsTracing.action?returnGoodsCode=${orderAlter.orderAlterCode}" class="btn btn-success btn-my btn-sm">跟踪</a></div>
                    <ul class="text-list">
                        <li>退换货编号：<span class="text-success">${orderAlter.orderAlterCode}</span></li>
                        <li>状态：<span>
                        <strong class="fn-green">
							<s:if test="orderAlter.proposeStatus==-1">已取消</s:if>
							<s:if test="orderAlter.proposeStatus==-2">已驳回</s:if>
							<s:if test="orderAlter.proposeStatus==1">已提交待审核</s:if>
                            <s:if test="orderAlter.proposeStatus==2"><s:if test="orderAlter.alterType==1">已通过待退货</s:if>
                            <s:if test="orderAlter.alterType==2">已通过待换货</s:if></s:if>
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
                        </span></li>
                        <li>最新进度：<span>[${time}] ${alterComment}</span> </li>
                    </ul>
                    <ul class="steps steps-xs">
                       <s:if test="4==orderAlter.alterType">
	                       <li <s:if test="1==orderAlter.proposeStatus"> class="active"</s:if>>
	                            <span class="step">1</span>
	                            <span class="title">提交申请</span>
	                        </li>
	                        <li <s:if test="51==orderAlter.proposeStatus"> class="active"</s:if>>
	                            <span class="step">2</span>
	                            <span class="title">客服<s:if test="orderAlter.proposeStatus==-2">驳回</s:if><s:else>审核</s:else></span>
	                        </li>
	                        <s:if test="orderAlter.proposeStatus==-2">
	                        <li class="active">
	                            <span class="step">3</span>
	                            <span class="title">完成</span>
	                        </li>
	                        </s:if><s:else>
	                        <li <s:if test="61==orderAlter.proposeStatus"> class="active"</s:if>>
	                            <span class="step">3</span>
	                            <span class="title">退款</span>
	                        </li>
	                        <li >
	                            <span class="step">4</span>
	                            <span class="title">买家收到退款</span>
	                        </li>
	                        <li <s:if test="7==orderAlter.proposeStatus"> class="active"</s:if>>
	                            <span class="step">5</span>
	                            <span class="title">完成</span>
	                        </li>
	                        </s:else>
                       </s:if>
                       <s:else>
                        <li <s:if test="1==orderAlter.proposeStatus"> class="active"</s:if>>
                            <span class="step">1</span>
                            <span class="title">提交申请</span>
                        </li>
                        <li <s:if test="2==orderAlter.proposeStatus"> class="active"</s:if>>
                            <span class="step">2</span>
                            <span class="title">康美<s:if test="orderAlter.proposeStatus==-2">驳回</s:if><s:else>审核</s:else></span>
                        </li>
                        <s:if test="orderAlter.proposeStatus==-2">
                        <li class="active">
                            <span class="step">7</span>
                            <span class="title">完成</span>
                        </li>
                        </s:if><s:else>
                        <li <s:if test="3==orderAlter.proposeStatus"> class="active"</s:if>
                        <s:elseif test="-1==orderAlter.proposeStatus"> class="active"</s:elseif>
                        <s:elseif test="-2==orderAlter.proposeStatus"> class="active"</s:elseif>>
                            <span class="step">3</span>
                            <span class="title">买家<s:if test="orderAlter.alterType==1">退货</s:if><s:if test="orderAlter.alterType==2">换货</s:if></span>
                        </li>
                        <li <s:if test="4==orderAlter.proposeStatus"> class="active"</s:if>
                        <s:elseif test="51==orderAlter.proposeStatus"> class="active"</s:elseif>
                        <s:elseif test="52==orderAlter.proposeStatus"> class="active"</s:elseif>
                        <s:elseif test="53==orderAlter.proposeStatus"> class="active"</s:elseif>
                        <s:elseif test="54==orderAlter.proposeStatus"> class="active"</s:elseif>>
                            <span class="step">4</span>
                            <span class="title">收到<s:if test="orderAlter.alterType==1">退货</s:if><s:if test="orderAlter.alterType==2">换货</s:if></span>
                        </li>
                        <li <s:if test="61==orderAlter.proposeStatus"> class="active"</s:if>
                        <s:elseif test="62==orderAlter.proposeStatus"> class="active"</s:elseif>
                        <s:elseif test="63==orderAlter.proposeStatus"> class="active"</s:elseif>>
                            <span class="step">5</span>
                            <span class="title">退款/重新发货</span>
                        </li>
                        <li>
                            <span class="step">6</span>
                            <span class="title">买家收到退款/换货</span>
                        </li>
                        <li <s:if test="7==orderAlter.proposeStatus"> class="active"</s:if>>
                            <span class="step">7</span>
                            <span class="title">完成</span>
                        </li>
                        </s:else>
                        </s:else>
                    </ul>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>申请明细</h5>
                </div>
                <div class="ibox-content">
                    <ul class="text-list">
                        <li><label>服务类型：</label>${orderAlter.alterType==2?'换货':(orderAlter.alterType==1?'退货':(orderAlter.alterType==3?'不退货退款':'超时未发货赔付'))}</li>
                        <li><label>申请凭据：</label>${orderAlter.evidence==null?'无发票':'有发票'}</li>
                        <li><label>商品名称：</label>[${orderItem.commoditySku}] &nbsp;&nbsp;${orderItem.commodityName}</li>
                        <li><label>商品编号：</label>${orderItem.commoditySku}</li>
                        <li><label>商品数量：</label>${orderAlter.alterNum}</li>
                        <li><label>问题描述：</label></li>
                        <li>
                        <textarea name="" placeholder="" cols="" rows="" class="form-control transition-height" disabled="disabled">${orderAlter.alterComment}</textarea>
                        </li>
                        <li><label>上传图片：</label></li>
                    </ul>
                    <ul class="tabs-lst listpic-inline">
                        <s:iterator value="photoList" var="potoUrl">
                  	 	   <li><div class="list-thumb"><a href="${showPath}${potoUrl.url}" target="_blank"><img src="${showPath}${potoUrl.url}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></a></div></li>
                  	   </s:iterator>
                    </ul>
                </div>
            </div>
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>退还赔付信息</h5>
                </div>
                <div class="ibox-content">
                    <ol class="text-list">
                     <s:if test="orderAlter.alterType==4">
                     	  赔付信息：
			          	 定金退款金额￥${orderAlter.deposit==null?0.00:orderAlter.deposit}元，
			          	 尾款退款金额￥${(orderAlter.finalmoney==null||orderAlter.finalmoney ==0)?0.00:orderAlter.finalmoney}元，  
			          	 定金补偿金额￥${(orderAlter.compensate==null)?0.00:orderAlter.compensate}元，
			          	  共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
			          	  （定金和尾款将原路退回您的支付账户，定金补偿金额将充入您的个人余额）
                     </s:if>
                     <s:elseif test="om.orderType==7">
                                                 赔付信息：
			          	 定金退款金额￥${orderAlter.deposit==null?0.00:orderAlter.deposit}元，
			          	 尾款退款金额￥${(orderAlter.finalmoney==null||orderAlter.finalmoney ==0)?0.00:orderAlter.finalmoney}元，  
			          	 共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
                     </s:elseif>
                     <s:elseif test="orderAlter.ruturnSum!=null&&orderAlter.ruturnSum!=0&&orderAlter.proposeStatus>1&&orderAlter.proposeStatus!=53">
			          	<li><span>
			          	  赔付信息：
			          	  商品退款金额￥${orderAlter.ruturnMoney==null?0.00:orderAlter.ruturnMoney}元，
			          	 退货返运费￥${(orderAlter.returnFare==null||orderAlter.returnFare ==0)?0.00:orderAlter.returnFare}元，  
			          	  补偿运费￥${(orderAlter.fareSubsidy==null)?0.00:orderAlter.fareSubsidy}元，
			          	  共计￥${orderAlter.ruturnSum==null?0.00:orderAlter.ruturnSum}元退款到余额及银行账户。
			          	  </span></li>
          	  		</s:elseif>
                    <s:if test="orderAlter.alterType==2">
                    <s:if test="orderAlter.proposeStatus>=3">
                        <li>买家退回给商家快递信息：${orderAlter.customerLogisticsName}  ${orderAlter.customerLogisticsNo}   
                                 时间： <s:iterator value="listVo" var="orderAlterTraceInfo" >
              	<s:if test="#orderAlterTraceInfo.operatStatus==3">
              		<s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
              	</s:if>
              </s:iterator>
              </li>
              </s:if>
              <s:else>
              <li>买家退回给商家快递信息：尚未                         	
              		<s:if test="orderAlter.alterType==2">换货</s:if>
					<s:if test="orderAlter.alterType==1">退货</s:if>
			  </li>
              </s:else>
                        <li>商家收货地址：${kmAddress}</li>
                        <li>商家收货人：${kmZipCode} &nbsp;&nbsp;邮编：${kmZipCode}</li>
                        <li>手机号码：${kmPhone}</li>
                        <!--<li>商家给买家换货快递信息：银捷速递运单号：8888 时间： 2015-04-07 09:28:35</li>
                        --><s:if test="orderAlter.proposeStatus>=6">
              <li>商家给买家换货快递信息：${orderAlter.logisticsName} &nbsp;&nbsp;运单号：${orderAlter.logisticsOrderNo} &nbsp;&nbsp;时间：
              <s:iterator value="listVo" var="orderAlterTraceInfo" >
              	<s:if test="#orderAlterTraceInfo.operatStatus==7||#orderAlterTraceInfo.operatStatus==8">
              		<s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;
              	</s:if>
              </s:iterator>
              </li>
              </s:if>
              <s:else>
              <li>商家给买家换货快递信息：尚未发货</li>
              </s:else>
                        <li>买家收货地址：${orderAlter.province}${orderAlter.city}${orderAlter.area}${orderAlter.address}</li>
                        <li>买家收货人：${orderAlter.name} &nbsp;&nbsp;邮编${orderAlter.zipcode}</li>
                        <li>买家电话：${orderAlter.phone}</li>
                     </s:if>    
                    </ol>
                </div>
            </div>
        </div>
    </div>
</div>
