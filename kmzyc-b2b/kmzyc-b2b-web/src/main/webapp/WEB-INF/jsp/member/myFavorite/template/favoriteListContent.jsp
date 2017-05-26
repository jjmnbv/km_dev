<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="sform" name="sform" action="queryFavoriteList.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<div class="l-right user-m">
		<div class="o-mt">
			<h2>我的收藏</h2>
			
		</div>
		<div class="user-m fn-t10">
		<div class="mt">
            <ul class="tab">
                <li class="curr" ><s></s><b></b><a href="queryFavoriteList.action">商品收藏</a></li>
                <li ><s></s><b></b><a href="queryFavoriteShopList.action">店铺收藏</a></li>
            </ul>
        </div>
        <div class="Inquiry">
				<div class="ui-form">
					<div class="fn-left fn-t5">
						<span>
							<input type="checkbox" value="depreciate" name="searchKeyword.depreciate" <s:if test='searchKeyword.depreciate != null && searchKeyword.depreciate != ""'>checked="checked"</s:if> id="depreciate" ><label for="depreciate">降价商品</label>
						</span>
						<span>
							<input type="checkbox" value="promotion" name="searchKeyword.promotion" <s:if test='searchKeyword.promotion != null && searchKeyword.promotion != ""'>checked="checked"</s:if> id="promotion" ><label for="promotion">促销商品</label>
						</span>
						<span>
							<input type="checkbox" value="inStock" name="searchKeyword.inStock" <s:if test='searchKeyword.inStock != null &&searchKeyword.inStock != ""'>checked="checked"</s:if> id="inStock" ><label for="inStock">现货商品</label>
						</span>
					</div>
					<s:textfield name="searchKeyword.keyword" id="searchKeyword.keyword" maxlength="20" cssClass="Inquiry-text"/>
					<input name="search" id="search" type="button" value="查 询" class="bti btn"> 
				</div>
			</div>
			<div class="mc">
				<div class="ui-table">
					<table class="ui-table user-table">
						<thead>
							<tr>
								<th class="td-s4">您收藏的商品</th>
								<th class="td-s2">商品规格</th>
								<th class="td-s10">单价（元）</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pagintion.recordList" var="entryVar" status="entryStatus">
								<tr>
									<td>
										<a href="<s:property value="productDetailUrl" /><s:property value="#entryVar.productSkuId" />.shtml" target="_blank">
											<b class="img"><img src="${productImgServerUrl}${entryVar.imgPath}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"></b>
											<div  class="td-name fn-text-left ">${entryVar.productName}</div> 
										</a>
									</td>
									<td>${entryVar.categoryAttrValueStr}</td>
									<td class="p-price fn-red">
										<p>
											<span class="fn-f18">
											<fmt:formatNumber value="${entryVar.finalPrice}" type="currency" pattern="#,##0.00"/>
											</span>
										</p>
										<s:if test='#entryVar.spreadPrice > 0'>
											<p>
												<i class="drop"></i>
												<span class="fn-gray">降</span>
												<span class="fn-f14">
												<fmt:formatNumber value="${entryVar.spreadPrice}" type="currency" pattern="#,##0.00"/>
												</span>
											</p>
										</s:if>
									</td>
									<td>
										<p>
											<s:if test='#entryVar.realStock <= 0'>
												<span><a class="btn-b btn-b-disabled" href="javascript:void(0);">缺货</a></span>
											</s:if>
											<s:else>
												<span><a href="javascript:void(0);" data-sid="<s:property value="productSkuId" />" class="red-btn shopcart">加入购物车</a></span>

												<div id="flyItem" class="fly_item" data-center="1288,-805" style="left: 335px; top: 998px; visibility: hidden; transform: translate(1288px, -805px);">
													<img src="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/item-pic.jpg" width="40" height="40">
												</div>

											</s:else>
										</p>
										<p class="fn-blue">
											<span><a href="javascript:void(0);" class="j_deleteFav" data-id="${entryVar.favoriteId}">删除</a></span>
										</p>
									</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->
            			<tiles:insertDefinition name="pagination"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</div>
