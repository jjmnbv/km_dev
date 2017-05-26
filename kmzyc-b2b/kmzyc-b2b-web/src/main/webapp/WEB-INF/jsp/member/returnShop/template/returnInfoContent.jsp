<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="changeApplyStatus.action" method="post" theme="simple">
<s:token></s:token>
<input type="hidden" value="${orderAlter.alterType}" id="alterType"/>
<div class="l-right user-m">
	<div class="o-mt">
  	<h2>退回商品</h2>
      <div class="OrderInfo">
      	<ul class="Order-Number">
          	<li>退换货编号：${orderAlter.orderAlterCode}</li>
              <li>状态:
              		<strong class="fn-green">
							<s:if test="orderAlter.proposeStatus == -1">已取消</s:if>
							<s:if test="orderAlter.proposeStatus == -2">已驳回</s:if>
							<s:if test="orderAlter.proposeStatus == 1">已提交待审核</s:if>
                            <s:if test="orderAlter.proposeStatus == 2">
                            <s:if test="orderAlter.alterType == 2">已通过待换货</s:if>
                            <s:if test="orderAlter.alterType == 1">已通过待退货</s:if>
                            </s:if>
							<s:if test="orderAlter.proposeStatus == 3">已退货待取件</s:if>
							<s:if test="orderAlter.proposeStatus == 4">已取件质检</s:if>
							<s:if test="orderAlter.proposeStatus == 51"><td>已通过待退款</td></s:if>
							<s:if test="orderAlter.proposeStatus == 52"><td>已通过待发货</td></s:if>
							<s:if test="orderAlter.proposeStatus == 53"><td>已驳回待返回原件</td></s:if>
							<s:if test="orderAlter.proposeStatus == 61"><td>已退款待确认</td></s:if>
							<s:if test="orderAlter.proposeStatus == 62"><td>已发货待签收</td></s:if>
							<s:if test="orderAlter.proposeStatus == 63"><td>已返回原件待签收</td></s:if>
							<s:if test="orderAlter.proposeStatus == 7">已完成</s:if>
							<s:if test="orderAlter.proposeStatus == 12">送货失败</s:if>
              		</strong>
              		</li>
              <p><strong>最新进度:</strong><span>[${time}]</span>${alterComment}
              <a href="checkReturnGoodsTracing.action?returnGoodsCode=${orderAlter.orderAlterCode}" class="fn-blue">进度跟踪</a></p>
          </ul>
      </div>
      <div class="fn-clear">
          <!--<div class="Order-Schedule">
          	<div class="Order-Schedule-c No1"></div>
          </div>-->
		<div class="oper-shop oper-shopbi-40">
                 	<ul>
                     	<li class="oper-shop3"><span>1</span><i></i></li>
                     	<li class="oper-shop1"><b></b><span></span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                     	<li class="oper-shop2"><b></b><span>7</span></li>
                     </ul>
                 </div>
                 <div class="operatxt operatxt-117">  
                     <ul>
                         <li class="operatxt-shop3">提交申请
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==1">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop3">卖家审核
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==2">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop1">买家退货
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==3">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">收到退货<p>
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==4">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">退款/重新发货
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==5">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">买家收到退款/换货
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==6">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                         <li class="operatxt-shop2">完成
                         <p>
                            <s:iterator value="listVo" var="orderAlterTraceInfo" >
                            <s:if test="#orderAlterTraceInfo.operatStatus==7">
                                <s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/><br/>
								<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
                            </s:if>
                            </s:iterator>
                         </p></li>
                     </ul>
                 </div>                </div>
         </div>
         <%-- 		<s:if test="loginInfo.emailStatus==1">
                    <div class="fn-bggreen fn-green fn-text-center fn-t20">
                    	<strong>商家收货地址同时发送到您邮箱：<s:property value="loginInfo.email"/> 。手机已验证用户同时发送短信到您手机上。</strong>
                    </div>
                </s:if>
                    
                    <ul class="form-info user-block-tip">
                    	<li><label>商品退回地址：</label>${kmAddress}</li>
                        <li><label>商&nbsp;家&nbsp;收&nbsp;货&nbsp;人：</label>${kmName}</li>
                        <li><label>商&nbsp;&nbsp;家&nbsp;&nbsp;&nbsp;电&nbsp;&nbsp;&nbsp;话：</label>${kmPhone}</li>
                        <li><label>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label>${kmZipCode}</li>
                    </ul> --%>
                    
                    <s:if test="null!=orderAlter.fareSubsidy&&0!=orderAlter.fareSubsidy">
                    <div class="fn-bgyellow fn-text-center">
                                                             经审核，由于本次退换货属于商家责任，康美中药城为您的退货运费补偿 ${orderAlter.fareSubsidy} 元。<br>
                                                             请您先行垫付运费，运费补偿将在本次退换货完成时返还到您的账户余额中。
                    </div>
                    </s:if>
                    <ul class="fn-f12 fn-b10">
                    	<div class="user-block-tip fn-gray fn-pl10">请先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户。<br>
                                                                           填写快递单位和运单号，您可以随时查看退换货进度跟踪。</div>
                        <div class="user-block-tip">                                                   
                    	<li>

                        <label class="fn-l50 fn-left fn-t5"><em class="fn-red">*</em>快递公司：</label>
                        <select class="sele" name="code"> 
                         <s:iterator value="logisticsMap" >    
                         	   <option value="<s:property value='key'/>"><s:property value="value"/></option>
  						</s:iterator>    
     
                        </select>
                        <label class="fn-l50"><em class="fn-red">*</em>运单号：</label>
                        <input name="no"  type="text" maxLength="30" size="16" class="u-text">
                        </li>
                        </div>
            		</ul>
            		<s:if test="orderAlter.alterType==2">
                    <h3>请确认您的换货接收地址</h3>            
            		<div class="form-info fn-f12 fn-bgyellow">
					
           			<s:iterator value="addressList" id="addVar" status="status">
						<p class="fn-b5"><span>
						<input class="addressId" name="addressVar.addressId" type="radio" value="${addVar.addressId}" <s:if test="#addVar.addressId==orderAlter.addressId">checked="checked"</s:if>/></span><span>${addVar.province}${addVar.city}${addVar.area}${addVar.detailedAddress}(${addVar.name} 收)${addVar.cellphone}</span>&nbsp;
							<a href="goDeliveryAddressSet.action?addressId=${addVar.addressId}">编辑</a>&nbsp;
							<a class="js_delAddress" href="javascript:void(0)" data-value="${addVar.addressId}">删除</a></p></p>
					</s:iterator>
					<s:if test="addressList.size()==0">
						<p><span>&nbsp;</span></p>
					</s:if>
					
					<p class="fn-b5"><span>
					<input class="addressId" name="addressVar.addressId" id="noId" type="radio" value="" <s:if test="addressList.size()==0||orderAlter.addressId==null">checked="checked"</s:if>></span><span>使用地址</span></p>
                    <s:if test="addressList.size()==0||orderAlter.addressId==null">
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>收&nbsp;货&nbsp;&nbsp;人：</label>
                    <input id="name" name="addressVar.name" value="${orderAlter.name}" type="text" class="u-text fn-left"/></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>收货地址：</label>
					<select id="province" name="addressVar.province" data-value="${orderAlter.province}" class="sele"></select> 
					<select id="city" name="addressVar.city" data-value="${orderAlter.city}" class="sele"></select> 
					<select id="area" name="addressVar.area" data-value="${orderAlter.area}" class="sele"></select>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label>
                    <input id="zipcode" name="addressVar.postalcode" value="${orderAlter.zipcode}" type="text" maxlength="6" class="u-text fn-left"/></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>详细地址：</label>
                    <input id="address" name="addressVar.detailedAddress" value="${orderAlter.address}" type="text" class="Complaints-text fn-left"></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>联系电话：</label>
                    <input id="phone" name="addressVar.cellphone" value="${orderAlter.phone}" type="text" class="u-text fn-left"></p>
                    </s:if>
                    <s:else>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>收&nbsp;货&nbsp;&nbsp;人：</label>
                    <input id="name" name="addressVar.name"  type="text" class="u-text fn-left"/></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>收货地址：</label>
					<select id="province" name="addressVar.province"  class="sele"></select> 
					<select id="city" name="addressVar.city"  class="sele"></select> 
					<select id="area" name="addressVar.area"  class="sele"></select>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label>
                    <input id="zipcode" name="addressVar.postalcode"  type="text" maxlength="6" class="u-text fn-left"/></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>详细地址：</label>
                    <input id="address" name="addressVar.detailedAddress"  type="text" class="Complaints-text fn-left"></p>
                    <p class="fn-b5 fn-clear"><label class="fn-t5"><em>*</em>联系电话：</label>
                    <input id="phone" name="addressVar.cellphone"  type="text" class="u-text fn-left"></p>
                    </s:else>
                </div>
                </s:if>
                <input type="hidden" id="orderAlterCode" name="orderAlterCode" value="${orderAlter.orderAlterCode}"/>
                <input type="hidden" id="orderAlterStatus" name="orderAlterStatus" value="3"/>
                <div class="button"><div><a class="btn-submit fn-r10 js_subform"><span>提交</span></a>
                <a class="btn-cancel js_cancel"><span>取消</span></a></div></div>
          </div>
</div>
  <!--fn-right-->
</div>
<!--内容区 END-->	
<div id="question" style="display:none"></div>
</s:form>