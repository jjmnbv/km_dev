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
        <jsp:param name="titlePrefix" value="账户明细"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>账户明细</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
    <div class="row-fluid"><!--左侧菜单开始-->
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_settlement.jsp"></jsp:include>
        <!--左侧菜单结束--> <!-- 提现 -->
        <form action="AccountBasicInfo.action" method="post"
              id="accountBasicListFrm" name="accountBasicListFrm"
              namespace="settlement"></form>
        <s:form action="queryRechargeDetail.action" namespace="settlement" method="post" id="frm" name="frm">
        <s:hidden name="page" id="page"/>
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
                            <li>账户明细</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <!--开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="javascript:void(0)">账户明细</a></li>
                            <li><a href="/supplier/hisEnchashmentList.action">历史提现申请</a></li>
                            <a href="javascript:void(0)" class="btn btn-danger btnright t_show_accountBasicView">
                                <i class="icon-rmb icon-white "></i> 我要提现</a>
                        </ul>
                        <div class="alert alert-info alert-block">
                            账户可用余额： <font>￥ <fmt:formatNumber value="${rechargeDetails.amountAvlibal}" type="currency" pattern="#,##0.00"/> </font>元
                            冻结余额： <span>￥<s:property value="rechargeDetails.AmountFrozen"/></span> 元
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javaScript:void(0);" class="btn btn-success btn-mini to_TopUp">充值</a>
                        </div>
                        <!--搜索开始-->
                        <div class="com_topform">
                            <ul>
                                <li><label>流水号：</label>
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="20" class="ui-input"
                                           name="rechargeDetails.accountNumber"
                                           value="<s:property value='rechargeDetailsTemp.accountNumber'/>"/>
                                </li>
                                <li><label>开始时间：</label>
                                    <input style="width: 180px;" type="text" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="rechargeDetails.aginCreateDate" id="aginCreateDate"
                                           value="<s:date name="rechargeDetails.aginCreateDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'aginCreateDate\'),\'%y-%M-%d %H:%m:%s\'}'})">
                                        <%--
                                        <input type="text" style="width: 180px;"
                                            placeholder="" maxlength="10" class="ui-input ui-form-date"
                                            name="rechargeDetails.aginCreateDate"
                                            value='<s:date name="rechargeDetails.aginCreateDate" format="yyyy-MM-dd hh:mm:ss"/>'
                                            id="startDate" /> --%>
                                </li>
                                <li><label>结束时间：</label>
                                    <input style="width: 180px;" type="text" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="rechargeDetails.endCreateDate" id="endCreateDate"
                                           value="<s:date name="rechargeDetails.endCreateDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endCreateDate\')}'})">
                                        <%-- <input type="text" style="width: 180px;" placeholder="" maxlength="10" class="ui-input ui-form-date" name="rechargeDetails.endCreateDate"
                                                    value='<s:date name="rechargeDetails.endCreateDate" format="yyyy-MM-dd hh:mm:ss"/>'
                                                    id="endDate" /> --%>
                                </li>
                                <li><label>交易类型：</label>
                                    <s:select list="#request.rechargeDetailTypeMap" listKey="key"
                                              listValue="value"
                                              headerKey="" headerValue="全部"
                                              name="rechargeDetails.type" id="type"
                                              class="ui-form-select"/>
                                </li>
                                <li>
                                    <button class="btn btn-primary j_submitSearchBtn">
                                        <i class="icon-search icon-white "></i> 搜索
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <!--搜索结束-->

                        <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                            <thead></thead>
                            <tbody>
                            <tr>
                                <td>时间</td>
                                <td class="width200">交易流水号</td>
                                <td class="width200">交易类型</td>
                                <td class="width200">金额(元)</td>
                                <td class="width200">内容</td>
                            </tr>
                            </tbody>
                        </table>

                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                            <tbody>
                            <s:iterator value="pagintion.recordList" id="recharge" status="status">
                                <tr>
                                    <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td class="width200">${recharge.accountNumber}</td>
                                    <td class="width200">
                                        <s:iterator value="#request.rechargeDetailTypeMap" id="it">
                                            <s:if test="#it.key==#recharge.type">
                                                <s:property value="value"/>
                                            </s:if>
                                        </s:iterator>
                                    </td>
                                    <!-- 负数+ 成功-->
                                    <s:if test="#recharge.status ==1 && #recharge.amount<0">
                                        <td class="fn-red width200">
                                    </s:if>
                                    <!-- 正数 + 成功-->
                                    <s:elseif test="#recharge.status ==1 && #recharge.amount>0">
                                        <td class="fn-green width200">
                                    </s:elseif>
                                    <!-- 正数和负数 + 失败-->
                                    <s:else>
                                        <td class="width200">
                                    </s:else>
                                    <s:if test="#recharge.amount >0">+</s:if>
                                        <fmt:formatNumber value="${recharge.amount }" type="currency" pattern="#,##0.00"/>
                                    </td>
                                    <td class="width200">${recharge.content}
                                        <s:if test="#recharge.type == 1">
                                            (
                                            <s:iterator value="#request.rechargeDetailStatusMap" id="it">
                                                <s:if test="#it.key==#recharge.status">
                                                    <s:property value="value"/>
                                                </s:if>
                                            </s:iterator>
                                            )
                                        </s:if>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                        <!--结束-->
                    </div>
                </div>
            </div>
            <hr>
        </div>
        </s:form> <!-- 底部 -->
        <s:form action="/activity/toTopUp.action" method="post" id="toTopUp" name="toTopUp"></s:form>
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
        <!-- 底部  end-->
    </div>
</div>
</body>
</html>