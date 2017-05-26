<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="container">
	<s:form id="couponFrom" name="sform" action="queryCouponList.action"
		method="post" theme="simple">
<input value="${couponStatus}" id="searStatus" type="hidden"  name="couponStatus"  />
	<s:token></s:token>

 <s:if test='#session.isFanliUser!=true'>
		<div class="form-group">
			<div class="input-group">
				<ul class=" nav-tabs">
					<li class="group-lqhb">领取红包:</li>
					<li><div class="input-group">
							<input type="text"  id="grantId" aria-describedby="请输入红包验证码"
								placeholder="请输入红包验证码" class="form-control input-lg"> 
								<span id="basic-addon2" class="input-group-btn">
								<a class="btn btn-success " id="activeCoupon">点击领取</a>
								</span>
						</div></li>
				</ul>
			</div>
		</div>
</s:if>
		<div class="bs-example fd_nav2" data-example-id="simple-nav-tabs">
			<ul class="nav nav-tabs">
			      <li  role="presentation" id="no_use" class="active"  data-page="1" j_status="6" total-page="100"><a href="javascript:void(0)">未使用优惠券</a></li>
                  <li  role="presentation"  id="have_use" class="" data-page="1" j_status="4" total-page="100" ><a href="javascript:void(0)">已使用优惠券</a></li>
                  <li  role="presentation" id="is_valid" class=""  data-page="1" j_status="0" total-page="100"><a href="javascript:void(0)">失效优惠券</a></li>
			</ul>
		</div>

		<div  class="coupons" id="couoponList">
		 <div class="body-load text-center text-success"   style="display: none;">
                     			<i class="icon icon-spinner icon-xs"></i>加载中，请稍后...
         </div>
		</div>
		
		<div  class="coupons" id="usedCouoponList">
		 <div class="body-load text-center text-success"   style="display: none;">
                     			<i class="icon icon-spinner icon-xs"></i>加载中，请稍后...
         </div>
		</div>
		
		<div  class="coupons" id="invalidCouoponList">
		 <div class="body-load text-center text-success"   style="display: none;">
                     			<i class="icon icon-spinner icon-xs"></i>加载中，请稍后...
         </div>
		</div>
		<!-- 下一页 -->
        <div class="body-load text-center text-success" style="display: none" id="nextPage"  data-status="" >
                     		 加载更多
        </div>

	</s:form>
</div>



