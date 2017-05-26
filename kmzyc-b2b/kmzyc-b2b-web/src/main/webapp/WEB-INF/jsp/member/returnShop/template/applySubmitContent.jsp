<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<s:form id="sform" name="sform" action="applySubmit.action" method="post" theme="simple" autocomplete="off" enctype="multipart/form-data">
<s:hidden name="page" id="page"/>
<input type="hidden" value="<%=basePath%>" id="basePath"/>
<input type="hidden" value="<%=session.getId()%>" id="jsessionId"/>
<s:token></s:token>
          <div class="l-right user-m">
          	<div class="o-mt">
            	<h2>提交申请</h2>
                <div class="fn-clear">
                    <!--<div class="Order-Schedule">
                    	<div class="Order-Schedule-c No1"></div>
                    </div>-->
					<div class="oper-shop oper-shopbi-40">
                    	<ul>
                        	<li class="oper-shop3"><span>1</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>2</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>3</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>4</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>5</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>6</span><i></i></li>
                        	<li class="oper-shop2"><b></b><span>7</span></li>
                        </ul>
                    </div>
                    <div class="operatxt operatxt-117">      
                        <ul>
                            <li class="operatxt-shop1">提交申请</li>
                            <li class="operatxt-shop2">卖家审核</li>
                            <li class="operatxt-shop2">买家退货</li>
                            <li class="operatxt-shop2">收到退货</li>
                            <li class="operatxt-shop2">退款/重新发货</li>
                            <li class="operatxt-shop2">买家收到退款/换货</li>
                            <li class="operatxt-shop2">完成</li>
                        </ul>
                    </div>                </div>
            </div>
            <div class="m-w w-noborder fn-clear fn-t10">
            	<div class="wh"><h3>申请明细：</h3></div>
                <div class="wc">
                    <ul class="form-info">
                    	<li><label><em>*</em>服务类型：</label><div class="sh-name">
                    	<span><input name="alterType" type="radio" value="2" checked="checked" class="js_setNum0"></span><span>换货</span>
                    	<span><input name="alterType" type="radio" value="1"  class="js_setNum0"></span><span>退货</span>
                    	<%--
                    	<span><input name="alterType" type="radio" value="3"  class="js_setNum0"></span>
                    	<span>不退货退款</span>
                    	 --%>
                    	</div></li>
                        <li><label><em>*</em>申请商品：</label><div class="sh-name"><a href="${cmsPagePath}${orderItem.productSkuId}.shtml" class="fn-blue" target="_blank">[${orderItem.commoditySku}]&nbsp;&nbsp;${orderItem.commodityName}</a></div></li>
                        <li class="upload-photos-Address">
                        <label><em>*</em>商品数量：</label>
                        <div class="quantity-form sh-name fn-left">
                            <a class="decrement js_setNumf1" href="javascript:void(0)">-</a>
                            <input type="text" name="alterNum" id="alterNum" value="1" class="quantity-text js_setNumOnchange">
                            <a class="increment js_setNum1" href="javascript:void(0)">+</a>
                        </div>
                        <span class="fn-red">注：商品数量必须&lt;=您的确认收货数量.</span>
                        <div class="tisp" id="tisp" style="display:none">
                        	<em class="icon-tip fn-left fn-t5"></em>
                            <ul>
                            	<li>如果您购买时享受促销活动的优惠，您的退货商品有可能不能按商品原价全额退款；</li>
                                <li>本次退货最终退款金额将由审核人员根据退货实际情况确定，您可在退货审核通过后，到<a href="/member/queryReturnShopList.action">退换货记录</a>中查看最终退款金额。</li>
                            </ul>
                        </div>
                        </li>
                        <div id="commentDiv">
                        <li><label>申请凭据：</label><div class="sh-name"><span><input name="evidence" type="checkbox" value="1"></span><span>有发票</span></div></li>
                        <li><label>问题描述：</label>
                        <div class="sh-name item">
	                        <textarea id="alterComment" name="alterComment" cols="" rows="" class="Complaints-text fn-left"></textarea>
	                        <p style="clear:left;line-height:22px;">请您如实填写申请原因及商品情况，限500字以内描述。</p>
                        </div></li>
                        <li>
                                  <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>上传图片：</h5>
	            </div>
	            <div class="ibox-content">
	                <ul class="tabs-lst listpic-inline">
	                    <li>
	                    	<div class="list-thumb" style="display: none" id="imgdiv0"><img id="img0"></div>
	                        <span class="btn-file" id="filediv0">
	                            <span class="fileinput-new"><span class="icon-uniE630">+</span>
	                            </span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file"  id="file0" data-value="0" class="fileName"/>
	                        </span>
	                       <div class="list-thumb" style="display: none" id="imgdiv1"><img id="img1"></div> 
	                        <span class="btn-file" id="filediv1">
	                            <span class="fileinput-new"><span class="icon-uniE630">+</span></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file1" data-value="1" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv2"><img id="img2"></div>
	                        <span class="btn-file" id="filediv2">
	                            <span class="fileinput-new"><span class="icon-uniE630">+</span></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file2" data-value="2" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv3"><img id="img3"></div>
	                        <span class="btn-file" id="filediv3">
	                            <span class="fileinput-new"><span class="icon-uniE630">+</span></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file3" data-value="3" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv4"><img id="img4"></div>
	                        <span class="btn-file" id="filediv4">
	                            <span class="fileinput-new"><span class="icon-uniE630">+</span></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file4" data-value="4" class="fileName" />
	                        </span>
	                    </li>
	                </ul>
	                <p class="help-block">最多上传5张，每张不超过5M，支持bmp，gif，jpg，jpeg</p>
	                <input type="hidden" name="batchNo" id="batchNo" value="${batchNo}"/>
                   	<input type="hidden" id="maxNum" value="${maxnum}"/>
                    <input type="hidden" id="orderCode" name="orderCode" value="${orderItem.orderCode}"/>
                    <input type="hidden" id="orderItemId" name="orderItemId" value="${orderItem.orderItemId}"/>
	            </div>
	        </div>
                        </li>
		<li class="fn-t20" id="backType"><label><em>*</em>商品退回方式：</label>
		<div class="sh-name">
		<s:if test="${supplierOrderItem.supplierType==2}">
		<span><input name="backType" type="radio" value="2" checked="checked"></span><span>快递至商家，请		
		<s:if test="${supplierOrderItem.serviceType==1}">
		<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=${supplierOrderItem.serviceQQ}&amp;site=qq&amp;menu=yes" class="green-btn">联系商家</a>
		</s:if>
		<s:else>
		<a href="http://www.taobao.com/webww/ww.php?ver=3&touid=${supplierOrderItem.serviceQQ}&siteid=cntaobao&status=1&charset=utf-8" target="_blank" class="green-btn">联系商家</a>
		</s:else>
		索取退/换货地址</span><span>请先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户</span></div>
		</s:if>
		<s:else>
		<span><input name="backType" type="radio" value="1" checked="checked"></span><span>快递至康美中药城</span><span>请先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户</span></div>
		</s:else>
		
		</li>
		</div>
                        <li>
                        <label id="exchangeAdressLabel"><em>*</em>换货买家地址：</label>
						<div class="sh-name" id="exchangeAdressDiv">
						<s:if test="addressList.size() != 0">
							<s:iterator value="addressList" id="Address" status="status">
								<p><span><input class="addressId" id="<s:property value="#status.index"/>" name="addressId" type="radio" value="${Address.addressId}" <s:if test="#Address.status==0||#status.index==0">checked="checked"</s:if>></span><span>${Address.province}${Address.city}${Address.area}${Address.detailedAddress}(${Address.name} 收)${Address.cellphone}</span>
								<a href="goDeliveryAddressSet.action?addressId=${Address.addressId}">编辑</a>
								&nbsp;<a class="js_delAddress" href="javascript:void(0)" data-value="${Address.addressId}">删除</a></p>							
							</s:iterator> 
							
						</s:if>	
							<s:if test="addressList.size()==0">
								<p><span>&nbsp;</span></p>
							</s:if>                          
                            <div class="upload-photos-Address">
                            	<ul>
                            		<li><span><input class="addressId" name="addressId" id="noId" type="radio" value="" <s:if test="addressList.size()==0">checked="checked"</s:if>></span><span>使用地址</span></li>
	                                <li><label><em>*</em>收&nbsp;货&nbsp;&nbsp;人：</label><div><input id="name" name="name" type="text" class="u-text fn-left"></div></li>
	                                <li><label><em>*</em>收货地址：</label>
	                                <div>
									<select id="province" name="province" class="sele"></select> 
									<select id="city" name="city" class="sele" ></select> 
									<select id="area" name="area" class="sele"></select>
	                                </div>
	                                </li>
	                                <li><label><em>*</em>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label><input id="zipcode" name="zipCode" type="text" maxlength="6" class="u-text fn-left"></li>
	                                <li><label><em>*</em>详细地址：</label><input id="address" name="address" type="text" class="u-text fn-left"></li>
                                	<li><label><em>*</em>联系电话：</label><input id="phone" name="phone" type="text" class="u-text fn-left" ></li>
                            	</ul>
                            </div>
                        </div>
                        </li>
                    </ul>
                </div>
                <div class="button">
                	<div>
                		<s:if test="maxnum!=0">
                		<a class="btn-submit js_subform"><span>提交</span></a>
                		&nbsp;&nbsp;<a class="btn-cancel js_cancel" data-batchNo="${batchNo}"><span>取消</span></a>
                		</s:if>
                		<s:else>
                		<a class="btn-cancel js_cancel" data-batchNo="${batchNo}"><span>返回</span></a>
                		</s:else>
                	</div>
                </div>
            </div>
          </div>
       </div>
</s:form>
