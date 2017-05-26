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
        <s:form action="settlementList.action" method="post" id="frm" name="frm" namespace="settlement">
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
                            <li>结算管理</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in"><!--开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">结算管理</a></li>
                        </ul>
                        <!--搜索开始-->
                        <div class="com_topform">
                            <ul>
                                <li><label>结算单号：</label>
                                    <s:textfield style="width:180px;" name="criteria.settlementNo"
                                                 placeholder="" maxlength="12"
                                                 cssClass="ui-input"/></li>
                                <li><label>账期：</label>
                                    <input style="width: 180px;" type="text" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="startDate" id="startDate"
                                           value="<s:date name="criteria.startDate" format="yyyy-MM-dd" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startDate\'),\'%y-%M-%d %H:%m:%s\'}'})">
                                </li>
                                <li>
                                    <input style="width: 180px;" type="text" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="endDate" id="endDate"
                                           value="<s:date name="criteria.endDate" format="yyyy-MM-dd" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endDate\')}'})">
                                </li>
                                <li><label>状态：</label>
                                    <select name="criteria.settlementStatus" id="settlementStatus" class="ui-form-select">
                                        <option value="" <s:if test="criteria.settlementStatus==''">selected="selected"</s:if>>
                                            所有
                                        </option>
                                        <option value="1" <s:if test="criteria.settlementStatus==1">selected="selected"</s:if>>
                                            未确认
                                        </option>
                                        <option value="2" <s:if test="criteria.settlementStatus==2">selected="selected"</s:if>>
                                            商家已确认
                                        </option>
                                        <option value="3" <s:if test="criteria.settlementStatus==3">selected="selected"</s:if>>
                                            运营已确认
                                        </option>
                                        <option value="4" <s:if test="criteria.settlementStatus==4">selected="selected"</s:if>>
                                            财务审核通过
                                        </option>
                                        <option value="5" <s:if test="criteria.settlementStatus==5">selected="selected"</s:if>>
                                            财务审核拒绝
                                        </option>
                                        <option value="6" <s:if test="criteria.settlementStatus==6">selected="selected"</s:if>>
                                            已结出
                                        </option>
                                    </select>
                                </li>
                                <li>
                                    <button class="btn btn-primary j_settlementList_search">
                                        <i class="icon-search icon-white "></i> 搜索
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <!--搜索结束-->
                        <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                            <tbody>
                            <tr>
                                <td class="width150">结算单号</td>
                                <td class="width150">账期</td>
                                <td class="width150">结算时间</td>
                                <td class="width100">期内货款</td>
                                <td class="width100">运费</td>
                                <td class="width150">平台佣金</td>
                                <td class="width100">推广服务费</td>
                                <td class="width100">差异调整</td>
                                <td class="width100">应结金额</td>
                                <td class="width100">结算状态</td>
                                <td class="width100">操作<br>
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                            <tbody>
                            <s:iterator value="pagintion.recordList" id="shopMain">
                                <tr>
                                    <td class="width150"><s:property value="settlementNo"/></td>
                                    <td class="width150"><s:property value="settlementPeriod"/></td>
                                    <td class="width150"><s:date name="settlementCreateTime" format="yyyy-MM-dd"/> <br>
                                        <s:date name="settlementCreateTime" format="HH:mm:ss"/></td>
                                    <td class="width100">
                                        <s:if test="receiveSum==null||receiveSum.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${receiveSum-refundSum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <s:if test="fareSum==null||fareSum.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${fareSum-returnFareSum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width150">
                                        <s:if test="commissionSum==null||commissionSum.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${refundComSum-commissionSum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <s:if test="addpvsum==null||addpvsum.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${returnpvsum-addpvsum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <s:if test="diffAdjSum==null||diffAdjSum.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${diffAdjSum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <s:if test="currSettleAccounts==null||currSettleAccounts.isEmpty()">0.00</s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${currSettleAccounts+diffAdjSum}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <s:if test="settlementStatus==1">未确认</s:if>
                                        <s:elseif test="settlementStatus==2">商家已确认</s:elseif>
                                        <s:elseif test="settlementStatus==3">运营已确认</s:elseif>
                                        <s:elseif test="settlementStatus==4">财务审核通过</s:elseif>
                                        <s:elseif test="settlementStatus==5">财务审核拒绝</s:elseif>
                                        <s:elseif test="settlementStatus==6">已结出</s:elseif>
                                    </td>
                                    <td class="width100">
                                        <a href="JavaScript:void(0);"
                                           data-settlementNo="<s:property value="settlementNo" />"
                                           class="btn btn-mini btn-success j_view_settlementDetail">查看详情</a><br>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>

                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
        </div>
        </s:form>
        <!-- 底部 -->
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
        <!-- 底部  end-->
    </div>
</div>
</body>
</html>