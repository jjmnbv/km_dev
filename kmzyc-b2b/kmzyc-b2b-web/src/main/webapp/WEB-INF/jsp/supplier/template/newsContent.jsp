<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" errorPage="" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<s:form id="sform" name="sform" action="initSupplierNews.action" method="post" theme="simple">
<s:hidden name="page" id="page"/>
<input name="pagenumbs" type="hidden" value="<s:property value='page' />" id="pages">
<!-- 资讯内容 -->
<div class="template-body">
	<!-- 内容区域头部 -->
    	<jsp:include page="/html/common/ColumnWindow_${shopId}.jsp"></jsp:include>
        <!-- 内容区域左边 -->
     <div class="m-wrap">
            <jsp:include page="/html/common/ContactWindow_${shopId}.jsp"></jsp:include>
            <div class="m-r">
            	<div class="m-r-tit">
	                <ul class="fn-clear">
	                    <li>商家动态</li>
	                </ul>
            	</div>

            	<div class="m-r-cont">
            	<ul class="m-dynamic-list">
	            	 <s:iterator id="supplierList" status="st" value="#request.pagintion.recordList">
	                    		<li>
	                    			<h4><span><s:property value="#st.count"/></span>
	                    			<a href="getNewsDetailByNewsId.action?newsId=<s:property value="newsId"/>&&shopId=<s:property value="shopId"/>" target="_blank"><s:property value="newsTitle"/></a>
	                    			</h4>
	                    			<%-- <div class="m-list-info fn-clear">
		                            		<div class="m-list-txt">
		                                    	<c:out value="${fn:length(newsContent)>180?fn:substring(newsContent,0,180):newsContent}${fn:length(newsContent)>180?'...':'' }" />
		                            		</div>
                        			</div> --%>
	                    		</li>
	                 </s:iterator>
	            </ul>
	                <div class="fn-tr fn-t10 fn-b10 fn-r10" style="overflow:hidden;">
						<!-- 分页 -->
						<tiles:insertDefinition name="pagination"/>
					</div>
			
            </div>

            </div>
        </div>
</div>
</s:form>
