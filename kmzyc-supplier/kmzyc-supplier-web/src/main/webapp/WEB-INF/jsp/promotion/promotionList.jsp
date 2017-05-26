<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="活动列表"></jsp:param>
</jsp:include>
<title>活动列表</title>
</head>
<body>


	<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

	<!-- 左侧内容区域开始 begin -->
	<div class="container-fluid">
		<div class="row-fluid">
			<jsp:include
				page="/WEB-INF/jsp/common/menubars/leftMenu_promotion.jsp"></jsp:include>
			<div class="content">
				

				<s:form action="queryPromotionList.action" method="post" id="frm"
					name="frm">
					<!-- 隐藏域 -->
					
					<s:hidden name="page" id="page" />
					<s:hidden name="status" id="status" />
					<s:hidden name="timeStatus" id="timeStatus" />
					<div class="row-fluid">
						<!-- block -->
						<div class="block_01">
						    <div class="navbar-inner">
					<ul class="breadcrumb">
						<i class="icon-home"></i>
						<li>促销 <span class="divider">/</span></li>
						<li>未审核管理</li>

					</ul>
				</div>
							<div class="block-content collapse in">
								<!--开始-->

								<!-- tab内容开始 -->
								<ul class="nav nav-tabs" >
									<li data-src="/promotion/queryPromotionList.action?status=1"
										data-status="" class="active"><a href="javascript:void(0);"
										data-toggle="tab">未审核</a></li>
									<button class="btn btn-danger btnright" id="toAddPromotion">
										<i class="icon-gift icon-white"></i>发布促销活动
									</button>

								</ul>



							


									<!--搜索表单开始-->
									<div class="com_topform">
										<ul>
										<li><label> 活动id： </label><input
												name="queryPromotionId" placeholder=""
												class="width160" value = '<s:property value="promotion.promotionId"/>'/></li>
											<li><label> 活动名称： </label><input
												name="promotion.promotionName" placeholder=""
												class="width160" value = '<s:property value="promotion.promotionName"/>'/></li>

											<li><label>活动时间：</label> <input type="text" class="width160"
												id="startTime" maxlength="10"
												name="promotion.startTime" readonly="readonly"
												placeholder=""
												onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" />
												&nbsp;至&nbsp; <input type="text" class="width160" id="promotion.endTime"
												maxlength="10" class="ui-input ui-form-date Wdate"
												name="promotion.endTime" readonly="readonly" placeholder=""
												onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" />
											</li>
											<li><label> 活动类型：</label> <s:select
													list="%{#request.promotionTypeMap}" headerKey=""
													headerValue="全部" listKey="key" listValue="value"
													name="promotion.promotionType"></s:select></li>
											<li>
												<button class="btn btn-primary j_promotionList_search">
													<i class="icon-search icon-white"></i> 搜索
												</button>
											</li>
										</ul>
									</div>
								</div>

                               
								<div class="com_tablesbnt">
									<ul>
										

										<li>
											<button class="btn j_delete_promotionList">
												<i class=" icon-remove "></i> 删除
											</button>
										</li>
										
									</ul>
								</div>

								<table cellpadding="0" cellspacing="0" border="0"
									class="table com_tablest">
									<tbody>
										<tr>
											<td class="width50"><input type="checkbox" name="uniform_on" id="uniform_on" class="uniform_on"></td>
											<td class="width60">活动id</td>
											<td >活动名称</td>
											<td class="width60">活动类型</td>
											<td class="width120">开始时间</td>
											<td class="width120">结束时间</td>
											<td class="width100">时间状态</td>
											<td class="width100">审核状态</td>
											<td class="width50">优先级</td>
											<td class="width100" style="width:66px">操作</td>


										</tr>
									</tbody>
								</table>
								<s:iterator value="pagee.dataList" id="promotion">
									<table cellpadding="0" cellspacing="0" border="0"
										class="table  table-bordered">
										<tbody>
											<tr>
												<td class="width50"><input type="checkbox"
													name="promotionId" id = '<s:property value="promotionId"/>'
													value='<s:property value="promotionId"/>'> <input
													type="hidden" id="st<s:property value="promotionId"/>"
													value="<s:property value="status"/>" /> <input
													type="hidden" id="status<s:property value='promotionId'/>"
													value="<s:property value='status'/>" /></td>
												</td>
												<td class="width60"><s:property value="promotionId" /></td>
												<td ><s:property value="promotionName" /></td>
												<td class="width60"
													title="<s:property value='%{#request.promotionTypeMap[promotionTypeId]'/>"><s:property
														value="#request.promotionTypeMap[promotionType]" /></td>

												<td class="width120"><s:date name="startTime"
														format="yyyy-MM-dd HH:mm:ss" /></td>
												<td class="width120"><s:date name="endTime"
														format="yyyy-MM-dd HH:mm:ss" /></td>
												<td class="width100"><s:if test="onlineStatus==1">未上线</s:if>
													<s:elseif test="onlineStatus==2">正在进行</s:elseif> <s:else>已过期</s:else></td>
												<td class="width100"><s:if test="status==1">未审核</s:if>
													<s:else>已审核</s:else></td>
												<!--<td><s:property value="channel" /></td>-->
												<td class="width50" align="center"><input
													title='数字越大，优先级越高，按Esc退出修改' style="display: none" size="2"
													onkeyup="value=value.replace(/[^\d]/g,'') "
													value="<s:property value='promotionPriority' />"
													id="promotionPriority" name="promotionPriority" /> <span
													id="noEditpricrity" style="display: inline"><s:property
															value="promotionPriority" /></span>&nbsp; <s:if
														test="status==1">
														<img TYPE="button" style="cursor: pointer;"
															title="点击修改或者保存优先级"
															src="${staticUrl}${imageBaseUrl}/pen.png"
															id="update" data-priority="<s:property value='promotionPriority'/>" 
															data-promotionid="<s:property value='promotionId'/>" />
													</s:if></td>
												<td class="width150" style="width:66px"><s:if test="status==1">
												<s:if test="onlineStatus!=3">
														<a id="check"
															class="btn btn-success btn-mini j_update_promotion_check"
															href="javascript:void(0);" style="width:66px"
															data-promotionid="<s:property value='promotionId'/>"
															data-type="1"> 审核</a>
															</s:if>
													</s:if> <s:if test="productFilterType!=1">
														<a  class="btn btn-mini" style="width:66px"
															"javascript:void(0);" id="manageProduct"
															data-promotionid="<s:property value='promotionId'/>"
															data-onlineStatus="<s:property value='onlineStatus'/>">
															管理商品</a>
													</s:if> <a class="btn btn-mini j_update_promotion_update" style="width:66px"
													"javascript:void(0);" 
																data-promotionid="<s:property value='promotionId'/>">
														编辑</a> <a class="btn btn-mini j_preview_promotion" style="width:66px"
													"javascript:void(0);" 
																data-promotionid="<s:property value='promotionId'/>">
														查看</a> <a class="btn btn-mini j_copy_promotion" style="width:66px"
													"javascript:void(0);" 
																data-promotionid="<s:property value='promotionId'/>"
													id="copy"> 复制</a> <s:if test="status==1">

														<a class="btn btn-mini j_copy_promotion" style="width:66px"
															"javascript:void(0);" 
																data-promotionid="<s:property value='promotionId'/>"
															id="delete"> 删除</a>

													</s:if></td>
											</tr>
										</tbody>
									</table>
								</s:iterator>

							<div class="fn-clear fn-mt10">
						<!-- 分页组件 -->
						<tiles:insertDefinition name="paginationBottom" />
					</div>
						</div>
						
					</div>
					
				</s:form>






			</div>

			<s:form action="deletePromotion" method="POST" namespace='/ajaxJson'
				id="deletePromotion" name='deletePromotion'>
				<input type="hidden" value="" id="promotionId" name="promotionId">
			</s:form>
			<s:form action="copyPromotion" method="POST" namespace='/ajaxJson'
				id="copyPromotion" name='copyPromotion'>
			</s:form>
			<s:form action="toUpdataPromotionNew" method="POST"
				namespace='/promotion' id="toPromotionInfo" name="toPromotionInfo">
			</s:form>
			<s:form action="queryPromotionProductList" method="POST"
				namespace='/promotion' id="queryPromotionProductList"
				name="queryPromotionProductList">
			</s:form>
			<s:form action="updateIssuePromotion" method="POST"
				namespace='/promotion' id="checkPromotion"
				name='updateIssuePromotion'>
			</s:form>
			</div>
			<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>