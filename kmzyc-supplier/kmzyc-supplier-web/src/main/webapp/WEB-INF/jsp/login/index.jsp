<%@page contentType="text/html;charset=UTF-8" import="com.kmzyc.framework.constants.Constants" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html class="no-js">
<head>
<title>康美中药城 - 供应商平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="供应商平台"></jsp:param>
</jsp:include>
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
        <div class="container-fluid">
            <div class="row-fluid">
            <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_index.jsp"></jsp:include>
                <div class="content">
                    <div class="row-fluid">
                        <!-- block -->
                        <div class="block_01">
                        <div class="navbar-inner">
                            <ul class="breadcrumb">
                                <i class="icon-home"></i>
                                <li>首页</li>
                            </ul>
                       	</div>
                        <div class="block-content collapse in">
                        <!--用户信息开始-->
                            <div class="user_info">
                                <div class="uileft">
                                    <div class="logo">
                                        <%
                                            String headImgUrl = (String) session.getAttribute(Constants.SESSION_SUPPLIER_HEADIMAGE);
                                            if (null != headImgUrl && !"".equals(headImgUrl) && !"#".equals(headImgUrl)) {
                                        %>
                                        <img src="${userImagePath}<%=session.getAttribute(Constants.SESSION_SUPPLIER_HEADIMAGE) %>"/>
                                        <%} %>
                                        </div>
                                        <div class="edit">
                                            <a id="s_1" href="javascript:void(0)" type="button"
                                               class="j_set_shopmain"><i class="icon-edit grayedit"></i> 编辑店铺设置</a>
                                        </div>
                                    </div>
                                    <div class="uiright">
                                        <div class="uirtitle"><%=session.getAttribute(Constants.SESSION_USER_NAME) %>
                                        </div>
                                        <div class="uiinfo">
                                            <ul>
                                                <li>
                                                    <label>店铺名称：</label>
                                                    <a id="j_index_openShopUrl" href="javascript:void(0);"
                                                       data-href="<%=session.getAttribute(Constants.SESSION_SUPPLIER_SHOPURL) %>"><%=session.getAttribute(Constants.SESSION_SUPPLIER_SHOPNAME) %>
                                                    </a>
                                                </li>
                                                <li>
                                                    <label>店铺类型：<%=session.getAttribute(Constants.SESSION_SUPPLIER_SHOPTYPE) %>
                                                    </label>
                                                </li>
                                                <li>
                                                    <label>公司名称：<%=session.getAttribute(Constants.SESSION_SUPPLIER_COMPANY) %>
                                                    </label>
                                                </li>
                                            </ul>
                                            <ul>
                                                <li>
                                                    <label>手机号：<%=session.getAttribute(Constants.SESSION_SUPPLIER_MOBILE) %>
                                                    </label>
                                                </li>
                                                <li>
                                                    <label>邮箱地址：<%=session.getAttribute(Constants.SESSION_SUPPLIER_EMIAL) %>
                                                    </label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
<!--用户信息结束-->
<!--商品订单提示开始--> 
<div class="user_pro">
<div class="span6">
                            <!-- block -->
                            <div class="user_block">
                                <div class="navbar"><div class="user_inner">商品提示</div><div class="user_innerp">您需要关注的商品信息以及待处理事项</div></div>
                                <div class="block-content collapse in">
<div class="user_prot"><ul>
<li><label>出售中的商品：</label><s:property value="#request.countInfo.upshelfCount"/>件</li>
<!-- 非上架以外的所有商品状态,草稿库中的所有,以及正式库中的下架状态 -->
<li><label>待售中的商品：</label><s:property value="#request.countInfo.downshelfCount+#request.countInfo.alreadyAuditCount+#request.countInfo.unsuccessAuditCount+#request.countInfo.waitAuditCount+#request.countInfo.draftCount"/>件</li></ul></div>
<div class="user_probnt">
<ul>
<ul>
<li class="green"><a data-id="p_3" data-href="/productDraft/showProductDraftList.action?auditStatus=2" href="javascript:void(0);" class="j_quicklink_index"> 已审核待上架</a>
<s:if test="#request.countInfo.alreadyAuditCount>0">
 <span class="badge badge-success pull-top">
<s:if test="#request.countInfo.alreadyAuditCount<100">
	<s:property value="#request.countInfo.alreadyAuditCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>

</li>
<li class="green"><a data-id="p_3" data-href="/productDraft/showProductDraftList.action?auditStatus=6" href="javascript:void(0);" class="j_quicklink_index"> 审核未通过</a>

<s:if test="#request.countInfo.unsuccessAuditCount>0">
 <span class="badge badge-success pull-top">
<s:if test="#request.countInfo.unsuccessAuditCount<100">
	<s:property value="#request.countInfo.unsuccessAuditCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
<li class="green"><a data-id="p_3" data-href="/productDraft/showProductDraftList.action?auditStatus=1" href="javascript:void(0);" class="j_quicklink_index"> 审核中</a>

<s:if test="#request.countInfo.waitAuditCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.waitAuditCount<100">
	<s:property value="#request.countInfo.waitAuditCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
<li class="green"><a data-id="p_3" data-href="/productDraft/showProductDraftList.action?auditStatus=0" href="javascript:void(0);" class="j_quicklink_index"> 未审核</a>
<s:if test="#request.countInfo.draftCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.draftCount<100">
	<s:property value="#request.countInfo.draftCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</span></li>
<li class="green"><a data-id="p_2" data-href="/product/showList.action?auditStatus=3" href="javascript:void(0);" class="j_quicklink_index"> 已上架</a>
<s:if test="#request.countInfo.upshelfCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.upshelfCount<100">
	<s:property value="#request.countInfo.upshelfCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
<li class="green"><a data-id="p_3" data-href="/product/showList.action?auditStatus=4" href="javascript:void(0);" class="j_quicklink_index"> 已下架</a>
<s:if test="#request.countInfo.downshelfCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.downshelfCount<100">
	<s:property value="#request.countInfo.downshelfCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if></li>
</ul>
</div>
                            </div>
                            <!-- /block -->
                        </div> 
                        
                        
</div>

<div class="span6">
                            <!-- block -->
                            <div class="user_block">
                                <div class="navbar"><div class="user_inner">订单提示</div><div class="user_innerp">您需要关注的订单信息以及待处理事项</div></div>
                                <div class="block-content collapse in">
<div class="user_prot"><ul>
<li data-query-type="today"><label>今日成交订单：</label><s:property value="#request.countInfo.todaySuccessCount"/>件</li>
<li data-query-type="yesterday"><label>昨日成交订单：</label><s:property value="#request.countInfo.yesterdaySuccessCount"/>件</li></ul></div>
<div class="user_probnt">
<ul>

<li class="green"><a data-id="o_1" data-href="/order/showOrderListByStatus.action?orderStatusForMenuQuery=4&titleValue=shouldShip" class="j_quicklink_index" href="javascript:void(0);"> 待发货</a>
<s:if test="#request.countInfo.waitShipCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.waitShipCount<100">
	<s:property value="#request.countInfo.waitShipCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
<li class="green"><a data-id="o_1" data-href="/order/showOrderListByStatus.action?orderStatusForMenuQuery=22&titleValue=shouldSetlle" class="j_quicklink_index" href="javascript:void(0);"> 待结转</a>
<s:if test="#request.countInfo.waitSettCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.waitSettCount<100">
	<s:property value="#request.countInfo.waitSettCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
<li class="green"><a data-id="o_1" data-href="/order/showOrderListByStatus.action?orderStatusForMenuQuery=1&titleValue=shouldPay" class="j_quicklink_index" href="javascript:void(0);"> 待付款</a>
<s:if test="#request.countInfo.waitPayCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.waitPayCount<100">
	<s:property value="#request.countInfo.waitPayCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</span></li>
<li class="green"><a data-id="o_1" data-href="/order/showOrderListByStatus.action?orderStatusForMenuQuery=6&titleValue=complete" class="j_quicklink_index" href="javascript:void(0);"> 已完成</a>
<s:if test="#request.countInfo.alreadyCompleteCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.alreadyCompleteCount<100">
	<s:property value="#request.countInfo.alreadyCompleteCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</span></li>
<li class="green"><a data-id="o_2" data-href="/supplier/findAllReturnNotes.action" class="j_quicklink_index" href="javascript:void(0);"> 退换货</a>
<s:if test="#request.countInfo.returnOrderCount>0">
<span class="badge badge-success pull-top">
<s:if test="#request.countInfo.returnOrderCount<100">
	<s:property value="#request.countInfo.returnOrderCount"/>
</s:if>
<s:else>
	99+
</s:else>
</span>
</s:if>
</li>
</ul>
</div>
</div>
</div>
</div>
</div>
<!-- 今日订单以及昨日订单提交表单 -->
<form action="/order/showOrderListByStatus.action" id="orderFrm" method="post">
	<input type="hidden" id="orderBeginDate" name="orderBeginDate" />
	<input type="hidden" id="orderEndDate" name="orderEndDate" />
</form>
<!--商品订单提示结束--> 
<!--底部按钮开始-->
<div class="user_bottom">
<ul>
<li id="p_1" class="btn btn-large j_add_product"><i class="icon-bottom01"></i>添加新品 </li>
<li id="po_1" class="btn btn-large j_add_promotion"><i class="icon-bottom02"></i>发布活动 </li>
<li id="p_4" class="btn btn-large j_product_stock_list"><i class="icon-bottom03"></i>库存管理</li>
<li id="s_1" class="btn btn-large j_set_shopmain"><i class="icon-bottom04"></i>店铺设置 </li>
<li id="se_1" class="btn btn-large j_account_balance"><i class="icon-bottom05"></i>账户余额 </li>
</ul>
</div>

<!--底部按钮结束--> 
                    </div>
                </div>
            </div>
        </div>  
        </div>
        </div>          
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>