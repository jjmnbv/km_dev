<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" errorPage="" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="initSupplierNews.action" method="post" theme="simple">
<s:hidden name="page" id="page"/>
<input name="pagenumbs" type="hidden" value="<s:property value='page' />" id="pages">
<!-- 资讯内容 -->
<div class="template-body">
	<!-- 内容区域头部 -->
    	  <jsp:include page="/html/common/ColumnWindow_${shopId}.jsp"></jsp:include>
        <!-- 内容区域左边 -->
      <div class="m-wrap">
               <!--联系我们-->
               <jsp:include page="/html/common/ContactWindow_${shopId}.jsp"></jsp:include>
           <div class="m-r">
            	<div class="m-r-tit">
	            	<ul class="fn-clear">
	                    <li>商家动态</li>
	                </ul>
	             </div>
                <div class="m-r-cont">
	                <h1 class="shop-details-tit"><s:property value="supplierNews.newsTitle"/></h1>
	                <p class="shop-details-date">发布时间：<s:date name="supplierNews.createTime" format="yyyy-MM-dd HH:mm:ss"/></p>
	                <p><s:property value="supplierNews.newsContent" escape="false"/></p>
            	</div>
        </div>
</div>
</s:form>