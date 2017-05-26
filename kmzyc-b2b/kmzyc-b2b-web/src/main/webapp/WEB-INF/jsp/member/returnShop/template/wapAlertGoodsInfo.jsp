<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--内容区域-->
<s:form id="sform" name="sform" action="changeWapApplyStatus.action" method="post" theme="simple">
	<input type="hidden" id="orderAlterCode" name="orderAlterCode" value="${orderAlter.orderAlterCode}"/>
	<input type="hidden" id="orderAlterStatus" name="orderAlterStatus" value="3"/>
	<s:token></s:token>
	<input type="hidden" value="${orderAlter.alterType}" id="alterType"/>  
	<div class="container">
	    <div class="row">
	        <div class="col-lg-12">
	            <div class="ibox float-e-margins">
	                <div class="ibox-content">
	                    <div class="btnabs">
	                    	<a href="/member/checkWapReturnGoodsTracing.action?returnGoodsCode=${orderAlter.orderAlterCode}" class="btn btn-success btn-my btn-sm">跟踪</a>
	                    </div>
	                    <ul class="text-list">
	                        <li>退换货编号：<span class="text-success">${orderAlter.orderAlterCode}</span></li>
	                        <li>状态：
	                        	<span>
								<s:if test="orderAlter.proposeStatus == -1">已取消</s:if>
								<s:elseif test="orderAlter.proposeStatus == -2">已驳回</s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 1">已提交待审核</s:elseif>
	                            <s:elseif test="orderAlter.proposeStatus == 2 && orderAlter.alterType==2">已通过待换货</s:elseif>
	                            <s:elseif test="orderAlter.proposeStatus == 2 && orderAlter.alterType==1">已通过待退货</s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 3">已退货待取件</s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 4">已取件质检</s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 51"><td>已通过待退款</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 52"><td>已通过待发货</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 53"><td>已驳回待返回原件</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 61"><td>已退款待确认</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 62"><td>已发货待签收</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 63"><td>已返回原件待签收</td></s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 7">已完成</s:elseif>
								<s:elseif test="orderAlter.proposeStatus == 12">送货失败</s:elseif>
	                        	</span>
	                        </li>
	                        <li>最新进度：<span>[${time}] ${alterComment}</span> </li>
	                    </ul>
	                    <ul class="steps steps-xs">
	                        <li id="step1">
	                            <span class="step">1</span>
	                            <span class="title">提交申请</span>
	                        </li>
	                        <li id="step2">
	                            <span class="step">2</span>
	                            <span class="title">康美审核</span>
	                        </li>
	                        <li class="active" id="step3">
	                            <span class="step">3</span>
	                            <span class="title">买家退货</span>
	                        </li>
	                        <li id="step4">
	                            <span class="step">4</span>
	                            <span class="title">收到退货</span>
	                        </li>
	                        <li id="step5">
	                            <span class="step">5</span>
	                            <span class="title">退款/重新发货</span>
	                        </li>
	                        <li id="step6">
	                            <span class="step">6</span>
	                            <span class="title">买家收到退款/换货</span>
	                        </li>
	                        <li id="step7">
	                            <span class="step">7</span>
	                            <span class="title">完成</span>
	                        </li>
	                    </ul>
	                </div>
	            </div>
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>商品返回地址</h5>
	                </div>
	                <div class="ibox-content">
	                    <ul class="text-list">
	                        <li><label>商品退回地址：</label>${kmAddress}</li>
	                        <li><label>商家收货人：</label>${kmName}</li>
	                        <li><label>商家电话：</label>${kmPhone}</li>
	                        <li><label>邮件：</label>${kmZipCode}</li>
	                    </ul>
	                </div>
	            </div>
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>友情提示</h5>
	                </div>
	                <div class="ibox-content">
	                    <ol class="text-list">
	                        <li>请先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户。</li>
	                        <li>填写快递单位和运单号，您可以随时查看退换货进度跟踪。</li>
	                        <s:if test="null!=orderAlter.fareSubsidy&&0!=orderAlter.fareSubsidy">
	                        <li class="text-danger">
	                        	经审核，由于本次退换货属于商家责任，康美中药城为您的退货运费补偿${orderAlter.fareSubsidy}元。
	                           	 请您先行垫付运费，运费补偿将在本次退换货完成时返还到您的账户余额中。
	                        </li>
	                        </s:if>
	                    </ol>
	                </div>
	            </div>
	            <div class="ibox float-e-margins">
	                <div class="ibox-title">
	                    <h5>快递单号录入</h5>
	                </div>
	                <div class="ibox-content">
	                    <div class="form-horizontal">
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">快递公司：</label>
	                            <div class="col-sm-9">
	                                <select class="form-control" name="code"> 
	                           		<s:iterator value="logisticsMap" >    
	                         	        <option value="<s:property value='key'/>"><s:property value="value"/></option>
	  					          	</s:iterator>    
	                                </select>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">运单号：</label>
	                            <div class="col-sm-9">
	                                 <input name="no" id="no" type="text" maxLength="30" size="16" class="form-control">
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	            <div class="ibox float-e-margins">
	            	<s:if test="orderAlter.alterType == 2">
	                <div class="ibox-title">
	                    <h5>换货接收地址</h5>
	                    <div class="ibox-tools">
	                        <a class="collapse-link"><i class="icon-uniE607"></i></a>
	                    </div>
	                </div>
	                </s:if>
	                <div class="ibox-content">
	                	<s:if test="orderAlter.alterType == 2">
	                    <div class="checkbox">
		                    <s:if test="null!=addressList && addressList.size() > 0">
	        				<div class="form-horizontal" id="addressList">
		                    <s:iterator value="addressList" id="addr" status="status">
		                        <div class="radio">
		                            <label>
		                                <input class="addressId" name="addressVar.addressId" type="radio" value="${addr.addressId}" <s:if test="#addr.addressId==orderAlter.addressId">checked="checked"</s:if>/></span><span>${addr.province}${addr.city}${addr.area}${addr.detailedAddress}(${addr.name} 收)${addr.cellphone}</span>&nbsp;
		                                <a href="javascript:void(0)" class="j_edit_address" data-name="${addr.name}" data-province='${addr.province}'
										data-city='${addr.city}' data-area='${addr.area}' data-detailedAddress='${addr.detailedAddress}' data-postalcode='${addr.postalcode}'
										data-cellphone='${addr.cellphone}' data-naddressId='${addr.addressId}' data-email='${addr.email}' data-telephone='${addr.telephone}'>编辑</a>&nbsp;
		                                <s:if test="${addr.status!=0}">&nbsp;<a href="javascript:void(0);" class="j_del_address" data-value="${addr.addressId}">删除</a></s:if>
		                            </label>
		                        </div>
		                   	</s:iterator>
		                   	</div>
		                   	</s:if>
		                	<div class="radio">
		                 		<label><input name="addressVar.addressId" id="newAddr" type="radio" value="" <s:if test="addressList.size()==0||orderAlter.addressId==null">checked="checked"</s:if>>使用新地址</label>
							</div>
	                    </div>
	                    <div id="editAddress" class="form-horizontal" <s:if test="null!=addressList && addressList.size()!=0">style="display:none;"</s:if>>
				            <div class="form-group ">
				                <input type="text" id="name" name="addressVar.name" maxlength="15" class="form-control input-lg" placeholder="收货人姓名" />
				            </div>
				            <div class="form-group">
				                <input type="text" id="mobile" name="addressVar.mobile" maxlength="11" class="form-control input-lg" placeholder="手机号码" />
				            </div>
				            <div class="form-group">
				                <input type="text" id="postalcode" name="addressVar.postalcode" maxlength="6" class="form-control input-lg" placeholder="邮政编码" />
				            </div>
				            <div class="form-group">
				                <select id="province" name="addressVar.province" class="form-control input-lg"></select>
				            </div>
				            <div class="form-group">
				                <select id="city" name="addressVar.city" class="form-control input-lg"></select>
				            </div>
				            <div class="form-group">
				                <select id="area" name="addressVar.area" class="form-control input-lg"></select>
				            </div>
				            <div class="form-group">
				            	<input type="text" class="form-control" placeholder="详细地址" maxlength="60" id="detailedAddress" name="addressVar.detailedAddress" />
				            </div>
				            <div class="form-group">
				                <a class="btn btn-success" href="javascript:void(0);" id="j_save_address">保存地址</a>
				            </div>
				        </div>
				        </s:if>
	                    <div class="form-horizontal">
	                        <div class="form-group">
	                            <div class="col-sm-offset-3 col-sm-9">
	                                <button type="button" class="btn btn-success btn-my btn-sm btn-success" id="submit-button">提 交</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</s:form>