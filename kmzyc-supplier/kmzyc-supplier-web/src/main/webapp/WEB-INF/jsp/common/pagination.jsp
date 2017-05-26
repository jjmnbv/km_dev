<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%--<kh:html--%>
        <%--title="我的健康-康美中药城"--%>
        <%--css=""--%>
        <%--js="page/pagintion">--%>
<%--<script language="JavaScript">--%>
	<%--var pageCounts = ${pagintion.totalpage};--%>
<%--</script>--%>
<input type="hidden" id="startindex" value="${pagintion.startindex}"/>
<input type="hidden" id="endindex" value="${pagintion.endindex}"/>
<input type="hidden" id="totalpageHid" value="${pagintion.totalpage}"/>
<div class="fn-tr j_pageDiv fn-t10" data-pageCounts="${pagintion.totalpage}">
	<div class="ui-page fn-right">
		<c:if test="${page==1}"><a href="javascript:void(0)" class="ui-page-item ui-page-disabled ui-page-item-prev">上一页</a></c:if>
		<c:if test="${page!=1}"><a href="javascript:void(0)" data-value="${page-1}" class="ui-page-item j_topage ui-page-item-prev">上一页</a></c:if>
		
		<c:forEach begin="${pagintion.pageindex.startindex}" end="${pagintion.pageindex.endindex}" var="wp">
		    <c:if test="${pagintion.page==wp}"><a href="javascript:void(0)" class="ui-page-item ui-page-item-current">${wp}</a></c:if>
		    <c:if test="${pagintion.page!=wp}"><a href="javascript:void(0)" data-value="${wp}" class="ui-page-item j_topage">${wp}</a></c:if>
		</c:forEach>
		
		<c:if test="${page==pagintion.totalpage || pagintion.totalpage==0}"><a href="javascript:void(0)" class="ui-page-item ui-page-disabled ui-page-item-next">下一页</a></c:if>
		<c:if test="${page!=pagintion.totalpage && pagintion.totalpage!=0}"><a href="javascript:void(0)" data-value="${page+1}" class="j_topage ui-page-item ui-page-item-next">下一页</a></c:if>
		
<%--
		<span class="ui-page-item ui-page-item-info ui-page-text">共${pagintion.totalpage}页&nbsp;&nbsp;到第</span>
		<span><input type="text" value="${page}"  id="gotoPage" name="gotoPage" maxlength="4" class="ui-page-number"></span>
        <span class="ui-page-item ui-page-item-info ui-page-text">页</span>		
		<span><input name="gotoPage1"  type="button" id="gotoPage1" class="ui-page-btnconfirm" onClick="javascript:submitGotoPage()" value="确定"></span>
 --%><span class="ui-page-item ui-page-item-info ui-page-text">每页
			<s:select id="pageSize" name="pageSize" list="#{10:'10',20:'20',30:'30',50:'50',100:'100',200:'200'}"/>
			条</span>
		<span class="ui-page-item ui-page-item-info ui-page-text">共${pagintion.totalpage}页&nbsp;&nbsp;${pagintion.totalRecords}条记录&nbsp;&nbsp;到第</span><span><input type="text" id="gotoPage" name="gotoPage" value="${page}" maxlength="4" class="ui-page-number"></span><span class="ui-page-item ui-page-item-info ui-page-text">页</span><span><input name="gotoPage1"  type="button" id="gotoPage1" class="ui-page-btnconfirm" value="确定"></span>
	</div>
</div>
<%--</kh:html>--%>