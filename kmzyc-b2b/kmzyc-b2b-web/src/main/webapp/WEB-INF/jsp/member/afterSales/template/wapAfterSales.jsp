<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="container">
    <div class="mian-box">
        <ul class="navlist">
            <li><a href="<%=request.getContextPath()%>/member/applyWapPrepare.action?r=<%=System.currentTimeMillis()%>"><i class="icon-return-request"></i>退换货申请<span class="icon-uniE61F"></span></a></li>
            <li><a href="<%=request.getContextPath()%>/member/queryWapReturnShopList.action?r=<%=System.currentTimeMillis()%>"><i class="icon-return-record"></i>退换货记录<span class="icon-uniE61F"></span></a></li>
        </ul>
    </div>
</div>



    