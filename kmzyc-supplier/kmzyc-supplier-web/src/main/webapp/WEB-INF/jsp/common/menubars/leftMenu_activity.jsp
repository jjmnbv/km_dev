<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%--结算管理 左侧菜单开始--%>
<div class="divleft">
    <ul class="nav nav-list bs-docs-sidenav">
        <li>
            <div class="leftlogo">
                <ul>
                    <li><p><img src="${staticUrl}${imageBaseUrl}leftgogo06.png"></p><span>活动</span></li>
                </ul>
            </div>
        </li>
        <li id="ac_1"><a href="/activity/getActivityList.action?activitySupplierType=1">活动管理</a></li>
        <li id="ac_2"><a href="/supplierActivityResultAction/findMyPromotionEffectList.action">活动推广效果</a></li>
    </ul>
</div>
<%--左侧菜单结束--%>
