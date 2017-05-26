<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="活动管理"></jsp:param>
    </jsp:include>
    <title>活动管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_activity.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <%-- block --%>
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
                            <li>活动 <span class="divider">/</span></li>
                            <li>活动推广效果<span class="divider">/</span> </li>
                            <li>销量统计</li>
                        </ul>
                    </div>

                    <div class="block-content collapse in">
                        <%--开始--%>
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">销量统计</a></li>
                        </ul>

                        <%--搜索开始--%>
                        <s:form action="/supplierActivityResultAction/findMyChannelActivitySalesList.action" method="post" id="frm" name="frm">
                            <s:hidden name="activityId" id="activityId"></s:hidden>
                            <s:hidden name="page" id="page"/>
                            <div class="com_topform">
                                <ul>
                                    <li>
                                        <label>商品标题：</label>
                                        <s:textfield align="left" name="activitySkuParam.productName"></s:textfield>
                                    </li>
                                    <li>
                                        <label>SKU：</label>
                                        <s:textfield name="activitySkuParam.productSkuCode"></s:textfield>
                                    </li>
                                    <li>
                                        <a class="btn width66 btn-mini btn-primary channel_search" >搜索</a>
                                    </li>
                                </ul>
                            </div>
                            <%--搜索结束--%>
                            <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td align="center" width="300px">商品标题</td>
                                    <td align="center" width="150px">SKU</td>
                                    <td align="center" width="150px">单价</td>
                                    <td align="center" width="150px">活动价</td>
                                    <td align="center" width="150px">推广佣金(%)</td>
                                    <td align="center" width="150px">预销数量</td>
                                    <td align="center" width="150px">已销数量</td>
                                    <td align="center" width="">操作</td>
                                </tr>
                                </tbody>
                            </table>
                            <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                                <tbody>
                                <s:iterator value="pagintion.recordList" id="activitySku_list" var="activitySkuVo">
                                    <tr>
                                        <td align="center" width="300px"><s:property value="productName"/></td>
                                        <td align="center" width="150px"><s:property value="productSkuCode"/></td>
                                        <td align="center" width="150px"><s:property value="price"/></td>
                                        <td align="center" width="150px"><s:property value="activityPrice"/></td>
                                        <td align="center" width="150px">
                                        	<s:property value="commissionRate"/>%
                                        </td>
										<td align="center" width="150px">
											<s:property value="preSaleQuantity"/>
											<s:if test="appendPreSaleQuantity>0">
												<a class="link to_getAppendList" tabindex="0" style="cursor: pointer;" 
													activity-id="<s:property value="activityId"/>" product-skuId="<s:property value="productSkuId"/>"
												>
												<span style="font-family: '应用字体 Regular', 应用字体; text-decoration: underline; color: rgb(0, 102, 153);">
													（已追加<s:property value='appendPreSaleQuantity' />）</span>
												</a>
											</s:if>
											
										</td>
										<td align="centser" width="150px">
											<s:property value="saleQuantity"/>
										</td>
                                        <td align="center" width="">
                                        	<a class="btn width66 btn-mini btn-primary to_getSaleDetail"
                                        	 	supplierEntry-id="<s:property value="supplierEntryId"/>" 
                                        	 	product-skuId="<s:property value="productSkuId"/>"
                                        	>销售明细 </a>
										</td>
                                    </tr>
                                </s:iterator>
                                </tbody>
                            </table>
                            <div class="fn-clear fn-mt10">
                                <%-- 分页组件 --%>
                                <tiles:insertDefinition name="paginationBottom"/>
                            </div>
                        </s:form>
                        <%--结束--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>