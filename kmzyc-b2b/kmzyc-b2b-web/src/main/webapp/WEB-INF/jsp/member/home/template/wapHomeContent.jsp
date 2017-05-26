<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 

 <input type="hidden" id="defaultProductImg_id"  value="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err170_170.jpg"/>
 <!-- 用户头像路径 -->
 <input type="hidden" id="userImag" value="<%=ConfigurationUtil.getString("USER_IMG_PATH")%>" />
 <!-- 产品链接 -->
 <input type="hidden" id="productPath" value="<%=ConfigurationUtil.getString("productPicPath_WAP")%>"  />
 
      <div class="container">
    <div class="head-img">
        <span class="my-img ">
        	<img id="userImg" onerror="this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__man_err100_100.jpg';"/>
        </span>
        <p>${b2b_sessionUserName}</p>
        <p><span id="available_amount">余额：</span> <span id="available_integral">积分：</span></p>
        <ul class="head-list">
            <li><a href="<%=request.getContextPath()%>/member/queryWapOrderList.action?searchKeyword.orderStatus=1&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE64C"></i>待付款</a><span class="badge" id="wait-toPay"></span></li>
            <li><a href="<%=request.getContextPath()%>/member/queryWapOrderList.action?searchKeyword.orderStatus=2&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE64B"></i>待发货</a><span class="badge"  id="payOrderAmount"></span></li>
        </ul>
    </div>
    <div class="mian-box">
        <ul class="navlist">
            <li><a href="<%=request.getContextPath()%>/member/queryWapOrderList.action?&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE64A"></i>我的订单<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/member/accountManage.action?_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE649"></i>账户管理<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/member/goWapCoupon.action?&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE648"></i>我的优惠券<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/wapAfterSales.action?r=<%=System.currentTimeMillis()%>"><i class="icon-uniE647"></i>售后管理<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/member/goWapMyFavorite.action?r=<%=System.currentTimeMillis()%>"><i class="icon-uniE601"></i>我的收藏<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/member/goWapMyFavorite.action?show=1&r=<%=System.currentTimeMillis()%>"><i class="icon-uniE646"></i>浏览记录<span class="icon-uniE61F"></span></a></li>
        </ul>
    </div>
</div>
