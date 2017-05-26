<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
	String cssJsPath=ConfigurationUtil.getString("CSS_JS_PATH");			
%>
<s:form id="sform" name="sform" action="returnWapAddress.action" method="post" theme="simple" autocomplete="off" enctype="multipart/form-data">
	<s:hidden name="page" id="page" />
	<input type="hidden" value="<%=basePath%>" id="basePath" />
	<input type="hidden" value="<%=session.getId()%>" id="jsessionId" />
	<input type="hidden" value="<%=cssJsPath%>" id="cssJsPath" />
	<s:token></s:token>
	<input type="hidden" name="batchNo" id="batchNo" value="${batchNo}" />
	<input type="hidden" id="maxNum" value="${maxnum}" />
	<input type="hidden" id="orderCode" name="orderCode" value="${orderItem.orderCode}" />
	<input type="hidden" id="orderItemId" name="orderItemId" value="${orderItem.orderItemId}" />
	<div class="container category-box">
	    <div class="row">
	 		<div class="col-lg-12">
	        <div class="ibox float-e-margins">
	            <ul class="tabs-lst">
	                <li class="m-b-none">
	                    <div class="list-thumb"><a href="${productPicPath_WAP}${orderItem.productSkuId}.shtml">
	                    <img src="${productImgServerUrl}${orderItem.defaultProductImage.imgPath7}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=cssJsPath%>images/default__logo_err60_60.jpg'"/></a> </div>
                    	<div class="list-descriptions">
                        	<div class="list-descriptions-wrapper">
                            	<div class="product-name"><a href="${productPicPath_WAP}${orderItem.productSkuId}.shtml" target="_blank">[${orderItem.commoditySku}]&nbsp;&nbsp;${orderItem.commodityName}</a></div>
                            	<div class="price-spot"> 数量：<strong class="product-price">${orderItem.commodityNumber}</strong></div>
                        	</div>
                    	</div>
	                </li>
	     		</ul>
	        </div>
	        <div class="ibox float-e-margins">
	            <div class="ibox-title"><h5>服务类型：</h5></div>
	            <div class="ibox-content">
	                <s:if test="alterType==4">
	                	<button class="btn btn-primary" type="button" id="changeGoods" disabled="disabled">超时未发货赔付</button>
	                	<input type="hidden" name="alterType" id="alterType" value="4">
	                </s:if>
	                <s:else>
		                <button class="btn btn-success" type="button" id="changeGoods">换 货</button>
		                <button class="btn btn-primary" type="button" id="returnGoods">退 货</button>
		                <input type="hidden" name="alterType" id="alterType" value="2">
		                <input name="backType" type="hidden" value="1">
	            	</s:else>
	            </div>
	        </div>
	        <s:if test="alterType==4">
	        <div class="ibox float-e-margins" style="background-color: #fffdee;border: 1px solid #edd28b;height: 100%;padding: 10px 0;">	
	        	<em class="icon-tip fn-left fn-t5"></em>
                <ul>
                	<li>您购买的是预售商品，本次申请赔付按商品预售价全额退款，并额外支付给您与定金同等金额作为补偿，额外补偿的金额通过余额支付给您，余额可购买商城的商品，不可提现。</li>
                    <li>实际退款金额为：￥${orderAlter.ruturnSum}元。</li>
                    <li>定金补偿：￥${orderAlter.compensate }元。</li>
                    <li>退商品定金：￥${orderAlter.deposit }元。</li>
                    <li>退商品尾款：￥${orderAlter.finalmoney}元。</li>
                    <li>定金和尾款将原路退回您的支付账户，定金补偿金额将充入您的个人余额。</li>
                </ul>
	        </div>
	        </s:if>
	        <div class="ibox float-e-margins">
	            <s:if test="om.orderType == 7">
	            	<div class="ibox-title"><h5>申请数量：</h5></div>
		            <div class="ibox-content">
		                <p class="option">
		                    <a class="btn-del btn-reduce disable">-</a>
		                    <input name="alterNum"  id="alterNum" type="text" class="op-txt" readonly="readonly" value="${orderItem.commodityNumber}">
		                    <a class="btn-add disable" >+</a>
		                </p>
		                <p>您提交数量必须等于预定数量</p>
		            </div>
	            </s:if>
	            <s:else>
		            <div class="ibox-title"><h5>申请数量：</h5></div>
		            <div class="ibox-content">
		                <p class="option">
		                    <a class="btn-del btn-reduce">-</a>
		                    <input name="alterNum"  id="alterNum" type="text" class="op-txt" value="1">
		                    <a class="btn-add">+</a>
		                </p>
		                <p>您最多可提交数量为${orderItem.commodityNumber}个</p>
		            </div>
	            </s:else>
	        </div>
	        <input type="hidden" id="maxNum" value="${maxnum}"/>
	        <s:if test="alterType!=4">
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>申请凭据：</h5>
	            </div>
	            <div class="ibox-content">
	                <label class="checkbox-inline"><input type="radio" id="inlineCheckbox2" name="evidence" value="1"> 有发票 </label>
	              	<label class="checkbox-inline"><input type="radio" id="inlineCheckbox3" name="evidence" value="0"> 无发票</label>
	            </div>
	        </div>
	        </s:if>
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>问题描述：</h5>
	            </div>
	            <div class="ibox-content">
	                <textarea name="alterComment" id="alterComment" cols="" rows="" class="form-control transition-height"  onfocus="if(value=='请您如实填写申请原因以及商品情况'){value=''}" maxlength="500">请您如实填写申请原因以及商品情况</textarea>
	                <p class="help-block">限500字以内描述</p>
	            </div>
	        </div>
	        <div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>上传图片：</h5>
	            </div>
	            <div class="ibox-content">
	                <ul class="tabs-lst listpic-inline">
	                    <li>
	                    	<div class="list-thumb" style="display: none" id="imgdiv0"><img id="img0"></div>
	                        <span class="btn-file" id="filediv0">
	                            <span class="fileinput-new"><i class="icon-uniE630"></i></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file"  id="file0" data-value="0" class="fileName"/>
	                        </span>
	                       <div class="list-thumb" style="display: none" id="imgdiv1"><img id="img1"></div> 
	                        <span class="btn-file" id="filediv1">
	                            <span class="fileinput-new"><i class="icon-uniE630"></i></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file1" data-value="1" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv2"><img id="img2"></div>
	                        <span class="btn-file" id="filediv2">
	                            <span class="fileinput-new"><i class="icon-uniE630"></i></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file2" data-value="2" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv3"><img id="img3"></div>
	                        <span class="btn-file" id="filediv3">
	                            <span class="fileinput-new"><i class="icon-uniE630"></i></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file3" data-value="3" class="fileName"/>
	                        </span>
	                        <div class="list-thumb" style="display: none" id="imgdiv4"><img id="img4"></div>
	                        <span class="btn-file" id="filediv4">
	                            <span class="fileinput-new"><i class="icon-uniE630"></i></span>
	                            <span class="fileinput-exists">添加新图片</span>
	                            <input name="image" type="file" id="file4" data-value="4" class="fileName" />
	                        </span>
	                    </li>
	                </ul>
	                <p class="help-block">最多上传5张，每张不超过5M，支持bmp，gif，jpg，jpeg</p>
	            </div>
	        </div>
	    </div>
	</div>
    <p><div  class="btn btn-success btn-block btn-success" id="btn_upload">
    	<s:if test="alterType==4">提交</s:if><s:else>下一步</s:else></div></p>
</div>
</s:form>
