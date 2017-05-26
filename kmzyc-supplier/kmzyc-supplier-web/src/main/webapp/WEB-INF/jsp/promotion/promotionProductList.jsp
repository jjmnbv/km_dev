<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="商品发布列表"></jsp:param>
</jsp:include>
<title>活动管理列表</title>

</head>
<body>

	<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
	<!-- 左侧内容区域开始 begin -->
	<div class="container-fluid">
		<div class="row-fluid">
			<jsp:include
				page="/WEB-INF/jsp/common/menubars/leftMenu_promotion.jsp"></jsp:include>
			<!-- 当前位置 s-->
			<div class="content">
			<div class="row-fluid">
					<!-- block -->
					<div class="block_01">
				<div class="navbar-inner">
					<ul class="breadcrumb">
						<i class="icon-home"></i>
						<li>促销 <span class="divider">/</span></li>
						<li>未审核管理<span class="divider">/</span></li>
						<li>商品管理</li>
					</ul>
				</div>

				<!-- 当前位置 e-->
				<!-- 按钮 s-->
				<div class="ui-well fn-mt20">
					<s:if test="type!='detail'">
						<a
							class="ui-button ui-button-success ui-button-lg fn-mr20 j_add_promotion_product"
							href="#">添加
						</a>
						<s:if test="promotion.status==1">
							<a
								class="ui-button ui-button-success ui-button-lg fn-mr20 j_delete_productList"
								href="#"> 删除
							</a>
						</s:if>
						
					</s:if>
				</div>
				<!-- 按钮 e-->

				<s:form action="queryPromotionProductList.action" method="post"
					id="frm" name="frm">
					<s:hidden name="page" id="page" />
					<input type="hidden" value="manage" name="type"></input>
					<input type="hidden" value="<s:property value="type"/>" name="type"
						id="backType"></input>
					<input type="hidden"
						value="<s:property value="promotion.promotionId"/>"
						id="promotionId" name=promotionProduct.promotionId></input>
					<input type="hidden"
						value="<s:property value="promotion.channel"/>" id="channel"
						name=promotion.channel></input>
					<input type="hidden" value='<s:property value="promotion.status"/>'
						name="status" id="status" />
					<div class="row-fluid">
						<div class="block_01">
							<div class="block-content collapse in">
								<!-- 查询条件 s-->
							

									<div class="com_topform">
										<ul>
											<li><span>SKU编号：</span></li>
											<li><s:textfield name="promotionProduct.productSkuCode"
												placeholder="" cssClass="ui-input" /></li>
											<li>
												<button
													class="btn btn-primary j_promotionProductList_search">
													<i class="icon-search icon-white"></i> 搜索
												</button>
											</li>
										</ul>

									</div>
								
								<!-- 查询条件 e-->
								<!-- 数据 s -->
								<table cellpadding="0" cellspacing="0" border="0"
									class="table com_tablest">
									<tbody>
										<tr>
											<td class="width50"><input type="checkbox" name="uniform_on" id="uniform_on" class="uniform_on"></td>
											<td class="width150">产品名称</td>
											<td class="width100">SKU编号</td>
											<td class="width200">SKU属性</td>
											<s:if test="promotion.promotionType==10">
												<s:if test="promotion.nature==1">
													<td class="width50">活动特价</td>
												</s:if>
												<s:if test="promotion.nature==2">
													<td class="width50">加价</td>
												</s:if>
												<td class="width50">原始定价</td>
											</s:if>
											<s:if test="promotion.nature==1&&promotion.promotionType>=8">
												<td class="width50">最少购买</td>
												<td class="width50">最多购买</td>
												<td class="width50">活动库存</td>
											</s:if>
											<td class="width100">状态变更时间
											</th>
											<td class="width50">状态
											</th>
											<s:if
												test="promotion.onlineStatus == 1 || promotion.onlineStatus == 2">
												<td class="width50">操作</td>
											</s:if>
										</tr>
									</tbody>
								</table>
								<s:iterator value="pagee.dataList" id="promotion">
									<table cellpadding="0" cellspacing="0" border="0"
										class="table  table-bordered">
										<tbody>
											<tr>
												<td class="width50"><s:if test="promotion.status==1"><input type="checkbox"
													name="promotionProductId"
													value='<s:property value="promotionProductId"/>'></s:if></td>
												<td class="width150"><s:property value="productName" /></td>
												<td class="width100"><s:property value="productSkuCode" /></td>
												<td class="width200"><s:iterator
														value="productSkuAttrList">
														<s:property value='categoryAttrName' />：<s:property
															value='categoryAttrValue' />&nbsp;&nbsp;&nbsp;&nbsp;
													</s:iterator></td>
												<s:if test="promotion.promotionType==10">
													<td class="width50"><input class="salePrice"
														title='<s:property value="promotionProductId"/>'
														style="display: none" size="10px"
														value="<s:property value="price" />"
														id="price<s:property value="promotionProductId"/>"
														name="price" /> <span id="noEditprice"
														style="display: inline"><s:property value="price" /></span>
														<s:if
															test="(promotion.status==1||status==1)&&promotion.promotionData==null">
															<s:if test="type!='detail'">
																<img TYPE="button" style="cursor: pointer;"
																	title="点击修改或者保存" class="readonly" id="updatePrice"
																	src="${staticUrl}${imageBaseUrl}/little_icon/xiugai1.png" />
															</s:if>
														</s:if></td>
													<td class="width50"><s:property value="originalPrice" /></td>
												</s:if>

												<s:if test="promotion.nature==1&&promotion.promotionType>=8">
													<td class="width50"><input class="xiangou"
														title='<s:property value="promotionProductId"/>'
														style="display: none" size="10px"
														value="<s:property value="minBuy" />" id="minBuy"
														name="minBuy" /> <span id="noEditprice"
														style="display: inline"><s:property value="minBuy" /></span>
														<s:if test="type!='detail'">
															<s:if
																test="promotion.onlineStatus == 1 || promotion.onlineStatus == 2">
																<s:if test="promotion.status == 2&&status!=1">
																</s:if>
																<s:else>
																	<img TYPE="button" style="cursor: pointer;"
																		title="点击修改或者保存" class="readonly"
																		src="${staticUrl}${imageBaseUrl}/little_icon/xiugai1.png"
																		id="updateMin" />
																</s:else>
															</s:if>
														</s:if></td>
													<td class="width50"><input class="xiangou"
														title='<s:property value="promotionProductId"/>'
														style="display: none" size="10px"
														value="<s:property value="maxBuy" />" id="maxBuy"
														name="maxBuy" /> <span id="noEditprice"
														style="display: inline"> <s:property value="maxBuy" />
													</span> <s:if test="type!='detail'">
															<s:if
																test="promotion.onlineStatus == 1 || promotion.onlineStatus == 2">
																<s:if test="promotion.status == 2&&status!=1">
																</s:if>
																<s:else>
																	<img TYPE="button" style="cursor: pointer;"
																		title="点击修改或者保存" class="readonly"
																		src="${staticUrl}${imageBaseUrl}/little_icon/xiugai1.png"
																		id="updateMax" />
																</s:else>
															</s:if>

														</s:if></td>
													<td class="width50"><input class="xiangou"
														title='<s:property value="promotionProductId"/>'
														style="display: none" size="10px"
														value="<s:property value="promotionStock" />"
														id="promotionStock" name="promotionStock" /> <span
														id="noEditprice" style="display: inline"> <s:property
																value="promotionStock" />
													</span> <s:if test="type!='detail'">
															<s:if
																test="promotion.onlineStatus == 1 || promotion.onlineStatus == 2">
																<s:if test="promotion.status == 2&&status!=1">
																</s:if>
																<s:else>
																	<img TYPE="button" style="cursor: pointer;"
																		title="点击修改或者保存" class="readonly"
																		src="${staticUrl}${imageBaseUrl}/little_icon/xiugai1.png"
																		id="updateStock" />
																</s:else>
															</s:if>
														</s:if></td>
												</s:if>
												<td class="width50"><s:date name="modifyTime"
														format="yyyy-MM-dd HH:mm:ss" /></td>
												<td class="width50"><s:if test="status==1">未上线</s:if> <s:elseif
														test="status==2">正在进行</s:elseif> <s:else>已过期</s:else></td>
												<s:if
													test="promotion.onlineStatus == 1 || promotion.onlineStatus == 2">
													<td class="width50"><s:if test="status==2">
															<!-- 已上线 -->
															<s:if test="promotion.status==2">
																<img class="updateStatus" TYPE="button"
																	style="cursor: pointer;" title="下线产品"
																	src="${staticUrl}${imageBaseUrl}/little_icon/xiayi1.png"
																	data-promotionid="<s:property value='promotionProductId'/>"
																	data-updateStatusType="3"
																	data-promotionType="<s:property value='promotion.promotionType'/>">&nbsp;&nbsp;
																	</s:if>
														</s:if> <s:if test="status==1">
															<!-- 未上线 -->
															<img class="updateStatus" TYPE="button"
																style="cursor: pointer;" title="上线产品"
																src="${staticUrl}${imageBaseUrl}/little_icon/shangyi1.png"
																data-promotionid="<s:property value='promotionProductId'/>"
																data-updateStatusType="2"
																data-promotionType="<s:property value='promotion.promotionType'/>">&nbsp;&nbsp;
														</s:if> <s:if test="status==3">
															<!-- 已过期 -->
															<img TYPE="button" style="cursor: pointer;"
																title="已过期不允许操作"
																src="${staticUrl}${imageBaseUrl}/little_icon/stopuse.png">&nbsp;&nbsp;
														</s:if></td>
												</s:if>
											</tr>
										</tbody>
									</table>
								</s:iterator>
								
								<!-- 数据 e -->
							</div>
						</div>
					</div>
					<div class="fn-clear fn-mt10">
					<!-- 分页组件 -->
					<tiles:insertDefinition name="paginationBottom" />
				</div>
				</s:form>
				
				<s:form action="deletePromotionProduct" method="POST"
					namespace='/ajaxJson' id="deletePromotionProduct"
					name='deletePromotionProduct'>
				</s:form>
			</div>
		</div>
		</div>
		</div>
		</div>
		
		<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>