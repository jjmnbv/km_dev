<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 
<input type="hidden" id="defaultProductImg_id"  value="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err170_170.jpg"/>
<div class="l-right">
	<div class="o-mt">
        <div class="personal-box">
        	<div class="personal-left"></div>
        	<div class="personal-right"></div>
        	<div class="per-tx-box">
        		<a id="editUserImg_a" href="javascript:void(0)">
					<div class="personal-touxiang">
						<img id="userImg" src="" alt="" onerror="this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__man_err100_100.jpg';"/>
					</div>
	        	</a>
	        	<div class="per-content">
	        		<div class="per-con-box">
		        		<h4>${b2b_sessionUserName}</h4>
		        		<div class="per-zh">账户安全：
					    	<div class="progress-bar progress-bar-best tip_best" style="display: none;">
		        				<p></p>							        				
		        			</div>
		        			<span class="progress-title tip_best" style="display: none;">好</span>
		        			<div class=" progress-bar progress-bar-good tip_good" style="display: none;">
		        				<p></p>						        				
		        			</div>
		        			<span class="progress-soso tip_good" style="display: none;">一般</span>
		        			<div class=" progress-bar progress-bar-bad tip_bad" style="display: none;">
		        				<p></p>							        				
		        			</div>
		        			<span class="progress-title tip_bad" style="color: #d7000f;display: none;">差</span>
					    </div>
		        		<div class="per-yz">
							<div class="mobile-yz" id="phone_verify_y" style="display: none;">
								<a href="goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=true">
			        				<i></i>
			        				<span>手机已验证</span>
		        				</a>
		        			</div>
						  	<div class="mobile-yz-none" id="phone_verify_n">
		        				<a href="goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=false">
			        				<i></i>
			        				<span>手机未验证</span>
		        				</a>
		        			</div>
						 	<div class="mobile-mm" id="pay_password_y" style="display: none;">
						 		<a href="goPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=true">
		        					<i></i>
		        					<span>已设置支付密码</span>
		        				</a>
		        			</div>
						    <div class="mobile-mm-none" id="pay_password_n">
						    	<a href="goPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=false">
		        					<i></i>
	                        		<span>未设置支付密码</span>
			                    </a>
	        			 	</div>
		        		</div>
	        		</div>
	        	</div>
        	</div>
        	<div class="per-xx-box">
        		<div class="per-xx">
        			<div class="per-xx-left">
        				<ul class="per-icon-box">
        					<li>
        					<a href="queryOrderList.action?_r=<%=System.currentTimeMillis()%>&searchKeyword.orderStatus=1">
        						<b class="per-icon-one"></b>
        						<p>待付款<span>(<span id="notPayOrder_amount"></span>)</span></p>
        					</a>
        					</li>
        					<li>
        					<a href="queryOrderList.action?_r=<%=System.currentTimeMillis()%>&searchKeyword.orderStatus=3">
        						<b class="per-icon-two"></b>
        						<p>配货中<span>(<span id="doneOrder_amount"></span>)</span></p>
        					</a>
        					</li>
        					<li>
        					<a href="queryIsEvaluateList.action?_r=<%=System.currentTimeMillis()%>&isOrderList=3">
        						<b class="per-icon-three"></b>
        						<p>已评价<span>(<span id="prodAppraise_amount"></span>)</span></p>
        					</a>
        					</li>
        					<li>
        					<a href="queryNoEvaluateList.action?_r=<%=System.currentTimeMillis()%>&isOrderList=2">
        						<b class="per-icon-four"></b>
        						<p>待评价<span>(<span id="notAssessOrder_amount"></span>)</span></p>
        					</a>
        					</li>
        				</ul>
        			</div>
        			<div class="per-xx-right">
        				<ul class="per-quan">
        					<li><a href="#">余额：<i id="available_amount"></i></a></li>
        					<li><a href="queryCouponList.action?_r=<%=System.currentTimeMillis()%>&couponStatus=3">优惠券：<span id="coupon_amount">10张</span>&emsp;<i>领取</i></a></li>
        					<li><a href="queryFavoriteList.action?_r=<%=System.currentTimeMillis()%>&searchKeyword.depreciate=1">我的收藏：<span>降价商品</span><i>（<span id="reducePrice_sku"></span>）</i></a></li>
        					<li><a href="queryFavoriteList.action?_r=<%=System.currentTimeMillis()%>&searchKeyword.promotion=1">促销商品：<i>（<span id="sale_sku"></span>）</i></a></li>
        					<li><a href="queryFavoriteList.action?_r=<%=System.currentTimeMillis()%>&searchKeyword.inStock=1">现货商品：<i>（<span id="instock_sku"></span>）</i></a></li>
        				</ul>
        			</div>
        		</div>
        	</div>
        </div>
        
        <div class="sale-lately-box">
        	<div class="sale-title">
        		<h3>最近浏览的商品</h3>
        		<span class="toggle-sale"><a href="javascript:void(0);" id="change_browsing_his"><i></i>换一批</a></span>				        		
        	</div>
        	<ul class="lately-items" id="browsingHisTip_div">
        		<!-- 浏览历史  -->
        	</ul>
        </div>
	</div>
</div>