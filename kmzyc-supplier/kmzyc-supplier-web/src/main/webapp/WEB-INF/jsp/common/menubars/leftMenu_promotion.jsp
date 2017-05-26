<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
 <!--促销管理 左侧菜单开始-->
<div class="divleft">
<ul class="nav nav-list bs-docs-sidenav">
	<li><div class="leftlogo"><ul><li><p><img src="${staticUrl}${imageBaseUrl}leftgogo04.png"></p><span>促销</span></li></ul></div></li>
    <li id="po_1" ><a href="/promotion/toAddPromotion.action" id ="promotion1"> 发布促销活动</a></li>
	<li id="po_2" ><a href="/promotion/queryPromotionListAudit.action?status=2" id ="promotion2"> 已审核管理</a></li>
	<li id="po_3" ><a href="/promotion/queryPromotionList.action?status=1" id ="promotion3"> 未审核管理</a></li>

</ul>
</div>
<!--左侧菜单结束-->