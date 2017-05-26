<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="活动详情"></jsp:param>
    </jsp:include>
    <title>活动缴费</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_activity.jsp"></jsp:include>
        <div class="content" id="content" data-url="${basePath}">
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>活动<span class="divider">/</span></li>
                            <li>活动报名<span class="divider">/</span></li>
                            <li>活动缴费</li>
                        </ul>
                    </div>
                    <s:hidden id="msg" name="resultMessage.message"></s:hidden>
                    <s:hidden id="isSuccess" name="resultMessage.isSuccess"></s:hidden>
                    <%--操作类型 first/append  首次和追加--%>
                    <s:hidden id="entryType" name="entryType"></s:hidden>
                    <s:hidden id="activityId" name="activityId"></s:hidden>
                    <div class="block-content">
                        <!--开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#">活动缴费</a></li>
                        </ul>
                        <div class="alert">
                            <button class="close" data-dismiss="alert">×</button>
                            <strong>提示：</strong>
                            收费活动提交成功后需要您缴纳活动费用，否则康美无法进行审核操作。
                            活动费用直接从账户余额中扣除，如余额不足，请及时进行充值。
                        </div>

                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table  table-bordered">
                            <tbody>
                            <tr>
                                <td class="width200 shoptR2">活动名称：</td>
                                <td class="textc">
                                    <s:property value="activity.activityName"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="width200 shoptR2">活动ID：</td>
                                <td class="textc">
                                    <s:property value="activity.activityId"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="width200 shoptR2">公司名称：</td>
                                <td class="textc">
                                    <c:out value="${seesionSupplierCompany}"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="width200 shoptR2">可用余额：</td>
                                <td class="textc">
                                    <fmt:formatNumber value="${amountAvlibal}" type="currency" pattern="#,##0.00"/>元
                                    <a href="javaScript:void(0);"
                                       class="btn btn-success btn-mini to_TopUp">充值</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="width200 shoptR2">活动费用：</td>
                                <td class="textc">
                                    <fontred><s:property value="activityTotalPrice"/></fontred>
                                    元
                                </td>
                            </tr>
                            <tr>
                                <td class="width200 shoptR2">支付密码：</td>
                                <td class="textc">
                                    <s:password style="width:180px;" class="span2" name="paymentPwd"
                                                id="paymentPwd" placeholder="" maxlength="16"
                                                cssClass="ui-input" autocomplete="off"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="form-actions">
                            <%--btn_submit--%>
                            <a class="btn btn-danger btn-large btn_submit">确认支付</a>
                            <a class="btn btn-large back_button">返回</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--支付结果状态-->
<div id="myAlert" class="modal hide">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button">&times;</button>
        <h3>提示</h3>
    </div>
    <div class="modal-body">
        <div class="pay-info">
            <span>
                <!--缴费成功-->
                <green style="font-size: 18px; font-weight: normal;">缴费成功!</green>
                <!--缴费失败-->
                <red style="font-size: 18px; font-weight: normal;">缴费失败，账户余额不足!</red>
            </span>
        </div>
        <p style="text-align: center"><a style='cursor:pointer;' class='to_TopUp'>立即充值</a></p>
        <br/>

        <div class="modal-footer">
            <a data-dismiss="modal" class="btn btn-primary btn_ok">确认</a>
            <a data-dismiss="modal" class="btn btn-default btn_cancel">取消</a>
        </div>
    </div>
</div>
<!--支付结果状态 end-->
<s:form action="/activity/getActivityList.action?activitySupplierType=3" method="post" id="backFrm" name="backFrm">
    <s:hidden name="page" id="pageList" value="1"/>
</s:form>
<s:form action="/activity/toTopUp.action" method="post" id="toTopUp" name="toTopUp">
</s:form>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>

