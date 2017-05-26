<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--面包屑breadcrumb END-->
    <div class="l-left">
        <div class="mykm">
            <div class="mc">
            	<dl class="geren-center">
					<dt><a href="<%=request.getContextPath()%>/member/goHome.action?_r=<%=System.currentTimeMillis()%>">个人中心</a></dt>
				</dl>
                <dl>
                    <dt>交易管理</dt>
                    <dd id="dl1" style="display: block;">
                        <div id="myOrder_Div">
                            <a href="<%=request.getContextPath()%>/member/queryOrderList.action?_r=<%=System.currentTimeMillis()%>">我的订单</a>
                        </div>
                        <div id="myFavorite_Div">
                            <a href="<%=request.getContextPath()%>/member/queryFavoriteList.action?_r=<%=System.currentTimeMillis()%>">我的收藏</a>
                        </div>
                        <div id="myCoupon_div">
                            <a href="<%=request.getContextPath()%>/member/queryCouponList.action?_r=<%=System.currentTimeMillis()%>&couponStatus=3">我的优惠劵</a>
                        </div>

                    </dd>
                </dl>
                <dl>
                    <dt>个人设置</dt>
                    <dd id="dl2" style="display: block;">
                        <div id="userInfo_Div">
                            <a href="<%=request.getContextPath()%>/member/goUserInfo.action?_r=<%=System.currentTimeMillis()%>">个人信息</a>
                        </div>

                        <div id="securityCenter">
                            <a href="<%=request.getContextPath()%>/member/showSecurityCentre.action?_r=<%=System.currentTimeMillis()%>">安全中心</a>
                        </div>

                        <div id="myAddress_Div">
                            <a href="<%=request.getContextPath()%>/member/goDeliveryAddress.action?_r=<%=System.currentTimeMillis()%>">收货地址</a>
                        </div>
                    </dd>
                </dl>

                <dl>
                    <dt>我的余额</dt>
                    <dd id="dl3" style="display: block;">
                        <div id="accountQuery_Div">
                            <a href="<%=request.getContextPath()%>/member/queryConsumptionDetail.action?_r=<%=System.currentTimeMillis()%>">账号查询</a>
                        </div>
                        <div id="rechargeDetails_Div">
                            <a href="<%=request.getContextPath()%>/member/queryRechargeDetail.action?_r=<%=System.currentTimeMillis()%>">余额明细</a>
                        </div>
                    </dd>
                </dl>
                
                <dl>
                    <dt>售后管理</dt>
                    <dd id="dl4" style="display: block;">
                        <div id="myEvaluate_Div">
                            <a href="<%=request.getContextPath()%>/member/queryNoEvaluateList.action?_r=<%=System.currentTimeMillis()%>&isOrderList=2">我的评价</a>
                        </div>
                        <div id="apply_Div">
                            <a href="<%=request.getContextPath()%>/member/applyPrepare.action?_r=<%=System.currentTimeMillis()%>">退换货申请</a>
                        </div>
                        <div id="applyList_Div">
                            <a href="<%=request.getContextPath()%>/member/queryReturnShopList.action?_r=<%=System.currentTimeMillis()%>">退换货记录</a>
                        </div>
                        <div id="complaints_Div">
                            <a href="<%=request.getContextPath()%>/member/gotoComplaint.action?_r=<%=System.currentTimeMillis()%>">建议投诉</a>
                        </div>
                    </dd>
                </dl>
            </div>
        </div>
    </div>
    <!--fn-left end-->