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
                            <li><a href="javascript:void(0);" class="j_hurlProductListDetail">妥投商品明细</a></li>
                            <li><a href="javascript:void(0);" class="j_hurlFareListDetail">运费明细</a></li>
                            <li class="active"><a href="#home" data-toggle="tab" class="j_settlementRefundListDetail">退款明细</a></li>
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
                                <li><label>退换货单号</label>
                                    <s:textfield style="width:150px;"
                                                 name="settlementRefundCriteria.settlementRefundId"
                                                 placeholder="" maxlength="18" cssClass="ui-input"/>
                                </li>
                                <li><label>服务类型：</label>
                                    <select name="settlementRefundCriteria.serviceType"
                                            id="serviceType" class="ui-form-select">
                                        <option value="" <s:if test="settlementRefundCriteria.serviceType==''">selected="selected"</s:if>>
                                            所有
                                        </option>
                                        <option value="1" <s:if test="settlementRefundCriteria.serviceType==1">selected="selected"</s:if>>
                                            退货
                                        </option>
                                        <option value="2" <s:if test="settlementRefundCriteria.serviceType==2">selected="selected"</s:if>>
                                            换货
                                        </option>
                                        <option value="3" <s:if test="settlementRefundCriteria.serviceType==3">selected="selected"</s:if>>
                                            不退货退款
                                        </option>
                                        <option value="4" <s:if test="settlementRefundCriteria.serviceType==4">selected="selected"</s:if>>
                                            超时未发货赔付
                                        </option>
                                    </select>
                                </li>
                                <li><label>完成时间：</label>
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date" name="startDate"
                                           id="startDate"
                                           value="<s:date name="settlementRefundCriteria.startDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'startDate\'),\'%y-%M-%d %H:%m:%s\'}'})">
                                </li>
                                <li>
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date" name="endDate" id="endDate"
                                           value="<s:date name="settlementRefundCriteria.endDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endDate\')}'})">
                                </li>
                                <li><label>SKU编号： </label>
                                    <s:textfield style="width:150px;"
                                                 name="settlementRefundCriteria.skuNo"
                                                 placeholder=""
                                                 maxlength="18" cssClass="ui-input"/>
                                </li>
                                <li><label>商品标题： </label>
                                    <s:textfield style="width:150px;"
                                                 name="settlementRefundCriteria.productTitle"
                                                 placeholder=""
                                                 maxlength="100" cssClass="ui-input"/>
                                </li>
                                <li>
                                    <a href="javascript:void(0);"
                                       class="btn btn-primary j_settlementRefundList_search">
                                        <i class="icon-search icon-white"></i> 搜索</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0);"
                                       onclick="javascript:settlementRefundList_Export();"
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
                                <td class="width150">退换货单号</td>
                                <td class="width150">SKU编号</td>
                                <td class="width300">商品标题</td>
                                <td class="width100">服务类型</td>
                                <td class="width50">数量</td>
                                <td class="width80">退款金额</td>
                                <td class="width80">退货返运费</td>
                                <td class="width80">佣金比例</td>
                                <td class="width80">退款佣金</td>
                                <td class="width150">退款返推广服务费</td>
                                <td class="width150">退款完成时间</td>
                            </tr>
                            </tbody>
                        </table>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table  table-bordered font12">
                            <tbody>
                            <s:if test="pagintion.recordList.size()!=0">
                                <tr>
                                    <td bgcolor="#E6E6E6" colspan="5">合计</td>
                                    <td bgcolor="#E6E6E6" class="width50">
                                        <s:if test="settlementRefundmap['sumNum'] == null">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${settlementRefundmap['sumNum']}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td bgcolor="#E6E6E6" class="width80">
                                        <s:if test="settlementRefundmap['sumRefund'] == null">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${settlementRefundmap['sumRefund']}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td bgcolor="#E6E6E6" class="width80"><fmt:formatNumber value="${settlementRefundmap['sumRefundFare']}" type="currency" pattern="#,##0.00"/></td>
                                    <td bgcolor="#E6E6E6" class="width80"></td>
                                    <td bgcolor="#E6E6E6" class="width50">
                                        <s:if test="settlementRefundmap['sumComm'] == null">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${settlementRefundmap['sumComm']}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td bgcolor="#E6E6E6" class="width50">
                                        <s:if test="settlementRefundmap['sumRefundPv'] == null">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${settlementRefundmap['sumRefundPv']}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td bgcolor="#E6E6E6" class="width200"></td>
                                </tr>
                            </s:if>
                            <s:set name="total_dealNumber" value="0"/>
                            <s:set name="total_refundMoney" value="0"/>
                            <s:set name="total_commission" value="0"/>
                            <s:iterator value="pagintion.recordList" id="settlementRefundList"
                                        status="proLists">
                                <tr>
                                    <td class="width50"><s:property value="#proLists.index+1"/></td>
                                    <td class="width150">
                                            <%-- <a href="gotoReturnNotesEdit.action?alterCode=<s:property value="orderAlterCode" />"
                                            onclick="document.parent.location=this.href;return  false"
                                            target="_blank"> <s:property value="orderAlterCode" /> </a> --%>
                                        <s:property value="orderAlterCode"/>
                                    </td>
                                    <!-- SKU编号 -->
                                    <td class="width150"><s:property value="skuNo"/></td>
                                    <!-- 商品标题 -->
                                    <td class="width300"><s:property value="productTitle"/></td>
                                    <!-- 服务类型 -->
                                    <td class="width100">
                                        <s:if test="serviceType==1">退货</s:if>
                                        <s:elseif test="serviceType==2">换货</s:elseif>
                                        <s:elseif test="serviceType==3">不退货退款</s:elseif>
                                        <s:elseif test="serviceType==4">超时未发货赔付</s:elseif>
                                    </td>
                                    <!-- 数量 -->
                                    <td class="width50"><s:property value="dealNumber"/></td>
                                    <!-- 退款金额 -->
                                    <td class="width80">
                                        <s:if test="refundMoney==null||refundMoney.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${refundMoney}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <!-- 退货返运费 -->
                                    <td class="width80"><fmt:formatNumber value="${refundFare}" type="currency" pattern="#,##0.00"/></td>
                                    <!-- 佣金比例 -->
                                    <td class="width80"><fmt:formatNumber type="percent" value="${commissionRate}"/></td>
                                    <!-- 佣金-->
                                    <td class="width50">
                                        <s:if test="commission==null||commission.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${commission}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <!-- 退款返推广服务费-->
                                    <td class="width150">
                                        <fmt:formatNumber value="${refundCommodityPvSum}" type="currency" pattern="#,##0.00"/>
                                    </td>
                                    <!-- 退款完成时间-->
                                    <td class="width150"><s:date name="settlementTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                                    <s:set name="total_dealNumber" value="#total_dealNumber+dealNumber"/>
                                    <s:set name="total_refundMoney" value="#total_refundMoney+refundMoney"/>
                                    <s:set name="total_commission" value="#total_commission+commission"/>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                        </s:form>
                    </div>
                </div>
            </div>
            <hr>
        </div>
    </div>
</div>
<div id="question" style="display:none"></div>
<!-- 底部 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!-- 底部  end-->
</body>
</html>