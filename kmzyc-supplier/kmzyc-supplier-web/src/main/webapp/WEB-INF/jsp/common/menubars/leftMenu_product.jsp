<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
 <!--商品 左侧菜单开始-->
<div class="divleft">
<ul class="nav nav-list bs-docs-sidenav">
	<li><div class="leftlogo"><ul><li><p><img src="${staticUrl}${imageBaseUrl}leftgogo02.png"></p><span>商品</span></li></ul></div></li>
	<li id="p_1"><a href="/productDraft/toProductDraftCategory.action"> 添加新商品</a></li>
	<li id="p_2"><a href="/product/showList.action?auditStatus=3"> 出售中的商品</a></li>
	<li id="p_3"><a href="/productDraft/showProductDraftList.action?auditStatus=2"> 待售中的商品</a></li>
	<li id="p_4"><a href="/product/showStockList.action"> 库存管理</a></li>
	<li id="p_5"><a href="/prodBrandDraft/showProdBrandDraftList.action">品牌管理</a></li>
</ul>
</div>
<!--左侧菜单结束-->
