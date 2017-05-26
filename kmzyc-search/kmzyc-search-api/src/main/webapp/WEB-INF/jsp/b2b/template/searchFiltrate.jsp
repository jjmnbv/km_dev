<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.kmb2b.com/functions" prefix="km" %> 
<!--商品筛选-->

<div class="m-w m-w-noborder i-select">
	<div class="wh"><h3>
	<c:if test="${keyword != null }">
	<strong>${keyword}</strong>
	</c:if>
	商品筛选</h3></div>
	<div class="wb select-cont">
		<c:if test="${!empty selectedFilter && fn:length(selectedFilter) > 0}">
		<dl class="select-attr">
		   <dt>已选条件：</dt>
			<dd>
				<a class="recall" href="${km:qsRemove(searchURL, 'f') }" style="text-decoration:none">全部撤消</a>
				<ul class="attr-list">
					<c:forEach items="${selectedFilter }" var="filter" varStatus="status">
						<c:forEach items="${filter.value }" var="entry" varStatus="status">
						<li>
							<c:choose>
								<c:when test="${filter.key eq 'attr' }">
									<a href="${km:fqDelVal(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), filter.key, km:qsJoin('_', entry.key, entry.value))}" style="text-decoration:none">
										${entry.key }：
										<strong>${entry.value }</strong><b></b>
									</a>
								</c:when>
								<c:when test="${filter.key eq 'price' }">
									<a href="${km:fqDel(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), filter.key)}" style="text-decoration:none">
										${entry.key }：
										<strong>${km:prFmat(entry.value) }</strong><b></b>
									</a>
								</c:when>
								<c:otherwise>
									<a href="${km:fqDel(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), filter.key)}" style="text-decoration:none">
										${entry.key }：
										<strong>${entry.value }</strong><b></b>
									</a>
								</c:otherwise>
							</c:choose>
						</li>
						</c:forEach>
					</c:forEach>
				</ul>
			</dd>
		</dl>
		</c:if>  
		<c:if test="${!empty productList && fn:length(productList) > 0 }">
		<c:forEach items="${facterList }" var="facter" varStatus="status">
			<c:choose>
				<c:when test="${facter.name eq '品牌'}"><!-- 品牌 -->
					<dl class="select-first">
					<dt>${facter.name }：</dt>
						<dd>
							<c:forEach items="${facter.fields}" var="field" varStatus="status">
								<a href="${km:fqAdd(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), field.code, field.name)}" class="filtrate_a" title="${field.name}" selected='${field.selected}' style="text-decoration:none">${field.name}</a>
							</c:forEach>
						</dd>
						<c:if test="${facter.fields != null && fn:length(facter.fields) > 16 }">
							<span class="s-more">更多<b></b></span>
						</c:if>
					</dl>
				</c:when>
				<c:when test="${facter.name eq '价格'}">
					<dl class="select-price">
						<dt>${facter.name }：</dt>
						<dd>
							<c:forEach items="${facter.fields}" var="field" varStatus="status">
								<a href="${km:fqAdd(km:fqDel(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), field.code), field.code, field.name)}" class="filtrate_a" selected='${field.selected}' style="text-decoration:none">${field.name}</a>
							</c:forEach>
							<input id="min_price" type="text"  value="" title="最低价" class="price-range" />
							<i></i>
							<input id="max_price" type="text"  value="" title="最高价" class="price-range" />
							<input id="priceBtn" type="button" class="btn-confirm" url="" value="确定">
						</dd>
					</dl>
				</c:when>
				<c:when test="${status.count < 5}"><!-- 属性集 -->
					<dl>
						<dt>${facter.name }：</dt>
						<dd>
							<c:forEach items="${facter.fields}" var="field" varStatus="status">
								<a href="${km:fqAdd(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), 'attr', km:qsJoin('_', facter.name, field.name))}" class="filtrate_a" selected='${field.selected}' style="text-decoration:none">${field.name}</a>
							</c:forEach>
						</dd>
					</dl>
				</c:when>
				<c:otherwise>
					<dl name="hiddenFilter" style="display:none">
						<dt>${facter.name }：</dt>
						<dd>
							<c:forEach items="${facter.fields}" var="field" varStatus="status">
								<a href="${km:fqAdd(km:qsRemove(km:qsRemove(searchURL, 'sort'), 'pn'), 'attr', km:qsJoin('_', facter.name, field.name))}" class="filtrate_a" selected='${field.selected}' style="text-decoration:none">${field.name}</a>
							</c:forEach>
						</dd>
					</dl>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</c:if>
	</div>
</div>
<c:if test="${fn:length(facterList) > 5}">
<div class="select-slide">
	<span id="open" class="close">更多选项<b></b></span>
	<span id="close" class="open" style="display:none" >收起<b></b></span>
</div>
</c:if>

<!--商品筛选结束-->
