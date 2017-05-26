<%@ page language="java" import="com.kmzyc.zkconfig.ConfigurationUtil,java.util.*,com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("loginUserId",session.getAttribute(Constants.SESSION_USER_ID));
	String wapCssPath = ConfigurationUtil.getString("CSS_JS_PATH");
	 String  cmsPagePath= ConfigurationUtil.getString("CMS_PAGE_PATH");
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="pord-mk-div">
        <div class="prod-list">
        <div class="pic-box"> <i class="xz-icon htop"><input type="checkbox" class="styled" /></i><em class="packages">套餐</em><strong>${normalMap[id].name}</strong></div>
        <div class="spec-box">¥ <fmt:formatNumber pattern="0.00" value="${normalMap[id].finalPrice}"/>
        </div>
        <div class="quan-box">
          <div class="quan-mk"> <a href="javascript:void(0)" class="decrement">-</a> <i class="quantity-mk">
            <input type="text" class="quantity-input" value="1">
          </i> <a href="javascript:void(0)" class="increment">+</a></div>
        </div>
        <div class="total-box">¥<fmt:formatNumber pattern="0.00" value="${normalMap[id].finalPrice*normalMap[id].amount}"/></div>
        <div class="oper-box">
          <p><a href="javascript:void(0)">加入收藏</a></p>
          <p><a href="javascript:void(0)">删除</a></p>
        </div>
      </div>
       <!--taocanchanpin -->
      <c:forEach items="${normalMap[id].productList}" var="productList">
      <div class="prod-list">
        <div class="pic-box"> <i class="xz-icon gray"></i> <span><a href="javascript:void(0)"><img src="<%=wapCssPath%>/images/common/shop-img.jpg" width="67" height="67"></a></span> <font>
         <a href="javascript:void(0)"> <p>  ${productList.name}
         </p></a>
        </font></div>
        <div class="spec-box"><fmt:formatNumber pattern="0.00" value="${productList.finalPrice}"/></div>
        <div class="quan-box"> ${productList.amount}</div>
        <div class="total-box"> <fmt:formatNumber pattern="0.00" value="${productList.finalPrice * productList.amount}"/></div>
        <div class="oper-box">
          <p>&nbsp;</p>
        </div>
      </div>
      </c:forEach>
    </div>
