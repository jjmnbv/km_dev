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
        <jsp:param name="titlePrefix" value="历史提现申请"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>历史提现申请</title>
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
                            <li>历史提现申请</li>
                        </ul>
                    </div>
                    <!--左侧菜单结束-->
                    <s:form action="hisEnchashmentList.action" method="post" id="frm" name="frm" namespace="supplier">
                    <s:hidden name="page" id="page"/>
                    <div class="block-content collapse in"><!--开始-->
                        <ul class="nav nav-tabs">
                            <li><a href="/settlement/queryRechargeDetail.action">账户明细</a></li>
                            <li class="active"><a href="JavaScript:void(0);">历史提现申请</a></li>
                        </ul>

                        <!--搜索开始-->
                        <div class="com_topform">
                            <ul>
                                <li><label>流水号：</label>
                                    <s:textfield name="bnesAcctEnchashmentCriteria.accountTransactionId"
                                            placeholder="" cssClass="ui-input" style="width:150px;"/>
                                </li>
                                <li><label>开始时间：</label>
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="startDate" id="startDate"
                                           value="<s:date name="bnesAcctEnchashmentCriteria.startDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'startDate\'),\'%y-%M-%d %H:%m:%s\'}'})">
                                    &nbsp;至&nbsp;
                                    <input type="text" style="width: 180px;" placeholder=""
                                           maxlength="10" readonly="readonly"
                                           class="ui-input ui-form-date"
                                           name="endDate" id="endDate"
                                           value="<s:date name="bnesAcctEnchashmentCriteria.endDate" format="yyyy-MM-dd HH:mm:ss" />"
                                           onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endDate\')}'})">
                                </li>
                                <li><label>交易类型：</label>
                                    <select name="bnesAcctEnchashmentCriteria.enchashmentStatus"
                                            id="enchashmentStatus" class="ui-form-select">
                                        <option value="" <s:if test="bnesAcctEnchashmentCriteria.enchashmentStatus==''">selected="selected"</s:if>>
                                            所有
                                        </option>
                                        <option value="1" <s:if test="bnesAcctEnchashmentCriteria.enchashmentStatus==1">selected="selected"</s:if>>
                                            提现完成
                                        </option>
                                        <option value="2" <s:if test="bnesAcctEnchashmentCriteria.enchashmentStatus==2">selected="selected"</s:if>>
                                            审核拒绝
                                        </option>
                                        <option value="3" <s:if test="bnesAcctEnchashmentCriteria.enchashmentStatus==3">selected="selected"</s:if>>
                                            审核通过
                                        </option>
                                        <option value="4" <s:if test="bnesAcctEnchashmentCriteria.enchashmentStatus==4">selected="selected"</s:if>>
                                            提现失败
                                        </option>
                                    </select>
                                </li>
                                <li>
                                    <button class="btn btn-primary j_hisEnchashmentList_search">
                                        <i class="icon-search icon-white"></i> 搜索
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <!--搜索结束-->
                        <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                            <thead></thead>
                            <tbody>
                            <tr>
                                <td class="width200">申请时间</td>
                                <td class="width300">收款银行/账号</td>
                                <td class="width150">金额(元)</td>
                                <td class="width200">审核状态</td>
                                <td class="width200">对应交易流水/审核说明</td>
                            </tr>
                            </tbody>
                        </table>

                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                            <tbody>
                            <s:iterator value="pagintion.recordList">
                                <tr>
                                    <td class="width200"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td class="width300"><s:property value="bankOfDeposit"/>/<s:property value="companyAccount"/></td>
                                    <td class="width150"><fmt:formatNumber value="${enchashmentAmount}" type="currency" pattern="#,##0.00"/></td>
                                    <td class="width200">
                                        <s:if test="enchashmentStatus==0"><fontred>待审核</fontred></s:if>
                                        <s:elseif test="enchashmentStatus==1">提现完成</s:elseif>
                                        <s:elseif test="enchashmentStatus==2"><fontred>审核拒绝</fontred></s:elseif>
                                        <s:elseif test="enchashmentStatus==3"><fontred>审核通过</fontred></s:elseif>
                                        <s:elseif test="enchashmentStatus==4"><fontred>提现失败</fontred></s:elseif>
                                    </td>
                                    <td class="width200">
                                        <%-- 流水号存在状态--%>
                                        <%--<s:property value="accountTransactionId" />
                                            <s:if test="enchashmentStatus==1">
                                            </s:if>
                                            <s:if test="enchashmentStatus==2">
                                                <i class="icon-question-sign"></i>
                                                <s:property value="remarks" />
                                            </s:if> --%>
                                        <s:if test="enchashmentStatus==1">
                                            <s:property value="accountTransactionId" escape="false"/>
                                        </s:if>
                                        <s:elseif test="enchashmentStatus==2">
                                            <s:property value="remarks" escape="false"/>
                                        </s:elseif>
                                        <s:elseif test="enchashmentStatus==3">
                                            <s:property value="accountTransactionId" escape="false"/>
                                        </s:elseif>
                                        <s:elseif test="enchashmentStatus==4">
                                            <s:property value="confirmRemarks" escape="false"/>
                                        </s:elseif>
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
        </div>
    </div>
</div>
</s:form>
<!-- 底部 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!-- 底部  end-->
</body>
</html>