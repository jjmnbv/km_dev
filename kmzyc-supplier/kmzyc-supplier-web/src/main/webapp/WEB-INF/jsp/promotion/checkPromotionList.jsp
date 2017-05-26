<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
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




				<s:form action="queryPromotionListAudit.action" method="post"
					id="frm" name="frm">
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
									<li>已审核管理</li>
									<!--  <li><s:if test='"1".equals(status)'>
		未上线
		</s:if> <s:elseif test='"2".equals(status)'>
		正在进行
		</s:elseif> <s:elseif test='"3".equals()'>
		已过期
		</s:elseif> <s:else>
		已审核
		</s:else></li>-->
								</ul>
							</div>
							<div class="block-content collapse in">
								<!--开始-->

								<!-- tab内容开始 -->
								<ul class="nav nav-tabs" id="tabForOrder">
									<li
										data-src="/promotion/queryPromotionListAudit.action?status=2"
										data-status=""><a href="javascript:void(0);"
										data-toggle="tab">已审核</a></li>
									<li
										data-src="/promotion/queryPromotionListAudit.action?status=2&timeStatus=2"
										data-status="1" data-title="shouldPay"><a
										href="javascript:void(0);" data-toggle="tab">正在进行</a></li>
									<li
										data-src="/promotion/queryPromotionListAudit.action?status=2&timeStatus=1"
										data-status="2" data-title="shouldSetlle"><a
										href="javascript:void(0);" data-toggle="tab">未上线</a></li>
									<li
										data-src="/promotion/queryPromotionListAudit.action?status=2&timeStatus=3"
										data-status="4" data-title="shouldShip"><a
										href="javascript:void(0);" data-toggle="tab">已过期</a></li>
								</ul>







								<!--搜索表单开始-->
								<div class="com_topform">
									<ul>
										<li><label> 活动id： </label><input
											name="queryPromotionId" placeholder="" class="width160"
											value='<s:property value="promotion.promotionId"/>' /></li>
										<li><label> 活动名称： </label><input
											name="promotion.promotionName" placeholder=""
											class="width160"
											value='<s:property value="promotion.promotionName"/>' /></li>


										<li><labe l>活动时间：</label> <input type="text"
												class="width160" id="startTime" maxlength="10"
												name="promotion.startTime" readonly="readonly"
												placeholder=""
												onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" />
											&nbsp;至&nbsp; <input type="text" class="width160"
												id="promotion.endTime" maxlength="10"
												class="ui-input ui-form-date Wdate" name="endTime"
												readonly="readonly" placeholder=""
												onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" /></li>
										<li><label> 活动类型：</label> <s:select
												list="%{#request.promotionTypeMap}" headerKey=""
												headerValue="全部" listKey="key" listValue="value"
												name="promotion.promotionType"></s:select></li>
										<li>
											<button class="btn btn-primary j_promotionList_searchCheck">
												<i class="icon-search icon-white"></i> 搜索
											</button>
										</li>
									</ul>
								</div>
							</div>



							<table cellpadding="0" cellspacing="0" border="0"
								class="table com_tablest">
								<tbody>
									<tr>
										<td class="width60">活动id</td>
										<td>活动名称</td>
										<td class="width60">活动类型</td>
										<td class="width120">开始时间</td>
										<td class="width120">结束时间</td>
										<td class="width100">时间状态</td>
										<td class="width100">审核状态</td>
										<td class="width80">优先级</td>
										<td class="width150" style="width: 66px">操作</td>


									</tr>
								</tbody>
							</table>
							<s:iterator value="pagee.dataList" id="promotion">
								<s:set name="nowTime" value="#request.nowDate"></s:set>
								<s:set name="lessTime" value="endTime"></s:set>
								<s:set name="stTime" value="startTime"></s:set>
								<table cellpadding="0" cellspacing="0" border="0"
									class="table  table-bordered">
									<tbody>
										<tr>
											<td class="width60"><s:property value="promotionId" /></td>
											<td><s:property value="promotionName" /></td>
											<td class="width60"
												title="<s:property value='%{#request.promotionTypeMap[promotionTypeId]'/>"><s:property
													value="#request.promotionTypeMap[promotionType]" /></td>

											<td class="width120"><s:date name="startTime"
													format="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="width120"><s:date name="endTime"
													format="yyyy-MM-dd HH:mm:ss" /></td>
											<td class="width100"><s:if
													test=' #stTime.getTime()>#nowTime.getTime()'>
											    未上线
											 </s:if> <s:elseif test="#nowTime.getTime()>#lessTime.getTime()">已过期</s:elseif>
												<s:else>正在进行</s:else></td>
											<td class="width100"><s:if test="status==1">未审核</s:if> <s:else>已审核</s:else></td>
											<!--<td><s:property value="channel" /></td>-->
											<td class="width80" align="center"><input
												title='数字越大，优先级越高，按Esc退出修改' style="display: none" size="2"
												onkeyup="value=value.replace(/[^\d]/g,'') "
												value="<s:property value='promotionPriority' />"
												id="promotionPriority" name="promotionPriority" /> <span
												id="noEditpricrity" style="display: inline"><s:property
														value="promotionPriority" /></span>&nbsp; <img TYPE="button"
												style="cursor: pointer;" title="点击修改或者保存优先级"
												src="${staticUrl}${imageBaseUrl}/pen.png" id="updateCheck"  data-priority="<s:property value='promotionPriority'/>" 
												data-promotionid="<s:property value='promotionId'/>" /></td>
											<td class="width150" style="width: 66px" align="center"><s:if
													test="status==2">
													<s:if test=' #stTime.getTime()>#nowTime.getTime()'>
													
														<a style="width: 66px" id="unCheck" class="btn btn-mini"
															href="javascript:void(0);"
															data-promotionid="<s:property value='promotionId'/>"
															data-type="2"> 撤销审核</a>
							
													</s:if>
													<s:elseif test="#nowTime.getTime()>#lessTime.getTime()"></s:elseif>
													<s:else>
													
														<a style="width: 66px" id="unPromotion"
															class="btn btn-success btn-mini"
															href="javascript:void(0);"
															data-promotionid="<s:property value='promotionId'/>"
															data-type="3"> 撤销活动</a>
															
															
													</s:else>

													<%--  
														<s:if test="isSycnCache==0 || isSycnCache==2">
															<!-- 未同步或同步成功 -->
															<a style="width:66px" class="btn btn-mini synPromotionCom"
																href="javascript:void(0);"
																data-promotionid="<s:property value='promotionId'/>"
																data-type="2">同步活动商品</a>

														</s:if>
														<s:if test="isSycnCache==1">
															<!-- 正在同步 -->
															<a style="width:66px" class="btn btn-mini" href="javascript:void(0);"
																data-promotionid="<s:property value='promotionId'/>"
																data-type="2">正在同步活动商品</a>
														</s:if>
														<s:if test="isSycnCache==3">
															<!-- 同步失败 -->
															<a style="width:66px"  class="btn btn-mini synPromotionCom"
																href="javascript:void(0);"
																data-promotionid="<s:property value='promotionId'/>"
																data-type="2">同步活动商品失败</a>

														</s:if>
														--%>
												</s:if> <a style="width: 66px"
												class="btn btn-mini j_preview_promotion"
												href="javascript:void(0);"
												data-promotionid="<s:property value='promotionId'/>"
												data-type="2">查看</a> <a style="width: 66px" id="copy_check"
												class="btn btn-mini j_copy_promotion"
												href="javascript:void(0);"
												data-promotionid="<s:property value='promotionId'/>"
												data-type="2">复制</a> <s:if test="status==2&&onlineStatus==3">
												</s:if> <s:else>
													<a style="width: 66px"
														class="btn btn-mini j_update_promotion_update"
														href="javascript:void(0);"
														data-promotionid="<s:property value='promotionId'/>"
														data-type="2">编辑</a>
												</s:else> <s:if test="productFilterType!=1">
													<s:if test="status==2&&onlineStatus==3">
													</s:if>
													<s:else>
														<s:if test="productFilterType==2">
														
															<a style="width: 66px" id="manageProduct"
																class="btn btn-mini" href="javascript:void(0);"
																data-promotionid="<s:property value='promotionId'/>"
																data-type="2">管理商品</a>
																
														</s:if>
													</s:else>

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
				<s:form action="toUpdataPromotionNew" method="POST"
					namespace='/promotion' id="toPromotionInfo" name="toPromotionInfo">
				</s:form>
				<s:form action="updatePromotionEndTime" method="POST"
					namespace='/promotion' id="updatePromotionEndTime"
					name='updatePromotionEndTime'>
				</s:form>
				<s:form action="synPromotionCom" method="POST"
					namespace='/promotion' id="synPromotionCom" name='synPromotionCom'>
				</s:form>
				<s:form action="copyPromotion" method="POST" namespace='/ajaxJson'
					id="copyPromotion" name='copyPromotion'>
				</s:form>
				<s:form action="queryPromotionProductList" method="POST"
					namespace='/promotion' id="queryPromotionProductList"
					name="queryPromotionProductList">
				</s:form>
				<s:form action="updateIssuePromotion" method="POST"
					namespace='/promotion' id="updateIssuePromotion"
					name='updateIssuePromotion'>
				</s:form>





			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>