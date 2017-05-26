<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="结算管理"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>结算管理</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
    <div class="row-fluid"><!--左侧菜单开始-->
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_settlement.jsp"></jsp:include>
        <!--左侧菜单结束-->
        <div class="content">
            <div class="row-fluid">
                <div class="navbar">

                </div>
            </div>
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>结算 <span class="divider">/</span></li>
                            <li>结算单明细</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <div class="alert alert-info alert-block">
                            结算单号：<s:property value="sellerSettlement.settlementNo"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            结算账期：<s:property value="sellerSettlement.settlementPeriod"/>(<s:property value="sellerSettlement.settlementPeriodExp"/>)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            商家/店铺：<s:property value="sellerSettlement.sellerName"/>(<s:property value="sellerSettlement.shopName"/>)
                        </div>
                        <!--开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab" class="j_hurlProductListDetail">妥投商品明细</a></li>
                            <li><a href="javascript:void(0);" class="j_hurlFareListDetail">运费明细</a></li>
                            <li><a href="javascript:void(0);" class="j_settlementRefundListDetail">退款明细</a></li>
                            <li><a href="javascript:void(0);" class="j_diffAdjListDetail">差异调整明细</a></li>
                        </ul>

                        <input type="hidden" name="settlementNo" id="settlementNo" value="<s:property value="settlementNo"/>">
                        <s:form action="" method="post" id="frm1" name="frm1"></s:form>
                        <s:form action="" method="post" id="frm" name="frm">
                        <s:hidden name="page" id="page"/>
                        <input type="hidden" value="<s:property value="sellerSettlement.settlementPeriod"/>" id="settlementPeriod">
                        <!--搜索开始-->
                        <div class="com_topform">
                            <ul>
                                <li><label>商品标题：</label>
                                    <s:textfield style="width:140px;"
                                                 name="hurlProductCriteria.productTile"
                                                 placeholder=""
                                                 maxlength="100" cssClass="ui-input"/></li>
                                <li><label>订单编号：</label>
                                    <s:textfield style="width:170px;"
                                                 name="hurlProductCriteria.orderCode" placeholder=""
                                                 maxlength="18" cssClass="ui-input"/></li>
                                <li><label>SKU编码：</label>
                                    <s:textfield style="width:140px;"
                                                 name="hurlProductCriteria.skuNo" placeholder=""
                                                 maxlength="18" cssClass="ui-input"/></li>
                                <li><label>完成时间：</label>
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="startDate" id="startDate"
                                           value="<s:date name="hurlProductCriteria.startDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'startDate\'),\'%y-%M-%d %H:%m:%s\'}'})">
                                </li>
                                <li><input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="endDate" id="endDate"
                                           value="<s:date name="hurlProductCriteria.endDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endDate\')}'})">
                                </li>
                                <li><a href="javascript:void(0);"
                                       class="btn btn-primary  j_hurlProductList_search">
                                    <i class="icon-search icon-white"></i> 搜索</a>
                                </li>
                                <li><a href="javascript:void(0);"
                                       onclick="javascript:hurlProductList_Export();"
                                       class="ui-button ui-button-success">导出</a>
                                </li>
                            </ul>
                        </div>
                        <!--搜索结束-->

                        <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest font12">
                            <thead></thead>
                            <tbody>
                            <tr>
                                <td class="width50">序号</td>
                                <td class="width200">订单编号</td>
                                <td class="width150">SKU编码</td>
                                <td class="width300">商品标题</td>
                                <td class="width50">数量</td>
                                <td class="width100">实收小计</td>
                                <td class="width100">佣金比例</td>
                                <td class="width100">佣金</td>
                                <td class="width100">推广服务费</td>
                                <td class="width100">应结金额</td>
                                <td class="width100">订单完成时间</td>
                            </tr>
                            </tbody>
                        </table>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table  table-bordered font12">
                            <tbody>
                            <s:if test="pagintion.recordList.size()!=0">
                                <tr>
                                    <td bgcolor="#E6E6E6" colspan="4">合计</td>
                                    <td class="width50" bgcolor="#E6E6E6">
                                        <s:if test="hurlProductmap['sumNum'] == null">
                                            0.00
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${hurlProductmap['sumNum']}"
                                                              type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100" bgcolor="#E6E6E6">
                                        <s:if test="hurlProductmap['sumReceive'] == null">
                                            0.00
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${hurlProductmap['sumReceive']}"
                                                    type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100" bgcolor="#E6E6E6"></td>
                                    <td class="width100" bgcolor="#E6E6E6">
                                        <s:if test="hurlProductmap['sumComm'] == null">
                                            0.00
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${hurlProductmap['sumComm']}"
                                                              type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100" bgcolor="#E6E6E6">
                                        <s:if test="hurlProductmap['sumPv'] == null">
                                            0.00
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${hurlProductmap['sumPv']}"
                                                              type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100" bgcolor="#E6E6E6">
                                        <s:if test="hurlProductmap['sumAccounts'] == null">
                                            0.00
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber
                                                    value="${hurlProductmap['sumAccounts']}"
                                                    type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100" bgcolor="#E6E6E6"></td>
                                </tr>
                            </s:if>
                            <s:set name="total_commodityNumber" value="0"/>
                            <s:set name="total_receiveSub" value="0"/>
                            <s:set name="total_commission" value="0"/>
                            <s:set name="total_settleAccounts" value="0"/>
                            <s:iterator value="pagintion.recordList" id="proList" status="proLists">
                                <tr>
                                    <td class="width50"><s:property value="#proLists.index+1"/></td>
                                    <td class="width200">
                                            <%--<a href="order/showOrderItemDetail.action?orderCode=<s:property value="orderCode" />" onclick="document.parent.location=this.href;return  false">
                                                <s:property value="orderCode" />
                                            </a>--%>
                                        <s:property value="orderCode"/>
                                    </td>
                                    <td class="width150">
                                            <%--<a href="<%=ConfigurationUtil.getString("CMS_PRODUCT_PATH")%><s:property value="skuId" />.html"
                                                    target="_blank"><s:property value="skuNo" />
                                                </a>--%>
                                        <s:property value="skuNo"/>
                                    </td>
                                    <td class="width300"><s:if test="inSuit==1"><b style="color: red;">【套餐内】</b></s:if><s:property value="productTile"/></td>
                                    <td class="width50"><s:property value="commodityNumber"/></td>
                                    <td class="width100"><fmt:formatNumber value="${receiveSub}" type="currency" pattern="#,##0.00"/></td>
                                    <td class="width100"><fmt:formatNumber type="percent" value="${commissionRate }"/></td>
                                    <td class="width100"><fmt:formatNumber value="${commission}" type="currency" pattern="#,##0.00"/></td>
                                    <td class="width100"><fmt:formatNumber value="${commodityPvSum}" type="currency" pattern="#,##0.00"/></td>
                                    <td class="width100"><fmt:formatNumber value="${settleAccounts}" type="currency" pattern="#,##0.00"/></td>
                                    <td class="width100"><s:date name="settlementTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                                </tr>
                                <s:set name="total_commodityNumber" value="#total_commodityNumber+commodityNumber"/>
                                <s:set name="total_receiveSub" value="#total_receiveSub+receiveSub"/>
                                <s:set name="total_commission" value="#total_commission+commission"/>
                                <s:set name="total_settleAccounts" value="#total_settleAccounts+settleAccounts"/>
                            </s:iterator>
                            </tbody>
                        </table>
                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                        </s:form>
                        <!--结束-->
                    </div>
                </div>
            </div>
            <hr>
        </div>
        <div id="question" style="display:none"></div>
        <!-- 底部 -->
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
        <!-- 底部  end-->
    </div>
</div>
</body>
</html>