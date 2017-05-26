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
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->

<s:form id="ajaxSubmit" enctype="multipart/form-data">
    <div class="container-fluid">
        <div class="row-fluid">
            <!--左侧菜单开始-->
            <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_settlement.jsp"></jsp:include>
            <!--左侧菜单结束-->
            <div class="content" id="content" data-url="${basePath}">
                <div class="row-fluid">
                    <!-- block -->
                    <div class="block_01">
                        <div class="navbar-inner">
                            <ul class="breadcrumb">
                                <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>
                                    &nbsp;</a></i>
                                <li>商家后台 <span class="divider">/</span></li>
                                <li>结算 <span class="divider">/</span></li>
                                <li>充值</li>
                            </ul>
                        </div>
                        <div class="block-content collapse in">
                            <!--开始-->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#home" data-toggle="tab">充值</a></li>
                            </ul>
                            <form class="form-horizontal">
                                <fieldset>
                                    <div class="control-group">
                                        <label class="control-label"><span class="required">*</span>请输入支付金额：
                                        </label>
                                        <div class="controls">
                                            <input id="rechargeAmount" name="rechargeAmount" type="text"
                                                   class="u-text80">元
                                            <span id="rechargeAmount_tip">只能填写大于等于100，小于等于50000的整数金额</span>
                                            <span><font id="rechargeAmount_error" class="red"></font></span>
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label"><span class="required">*</span>支付方式：
                                        </label>

                                        <div class="controls">
                                            <ul id="myTab" class="nav nav-tabs paybox-tab">
                                                <li class="ui-tab-trigger-item current active" id="payzf">
                                                    <a href="#pay" data-toggle="tab">
                                                        支付平台
                                                    </a>
                                                </li>
                                                <%--<li class="ui-tab-trigger-item " id="saveCard">
                                                    <a href="#bank" data-toggle="tab">网上银行</a>
                                                </li>--%>
                                            </ul>
                                            <div id="myTabContent" class="tab-content paybox">
                                                <div class="tab-pane fade active in " id="pay" name="pay">
                                                    <div class="payresult" id="pay-list">
                                                        <%--<div class="paybox-tit"><strong>支付平台</strong> 支持网银与其他平台支付方式
                                                        </div>--%>
                                                        <div id="div2">
                                                            <ul class="bank-list fn-clear">
                                                                <li class="none">
                                                                    <input type="radio" class="radio" name="payment" value="3" checked="checked"/>
                                                                    <div class="bank-info">
                                                                        <label>
                                                                            <a href="javascript:void(0);">
                                                                                <img width="125" height="28" alt="支付宝支付"
                                                                                     src="http://jscss.kmb2b.com/res/images/common/pay/alipay.png"/>
                                                                            </a>
                                                                        </label>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                                                <%--<div class="tab-pane fade" id="bank" name="saveCard">
                                                    <div class="payresult" id="saveCard-list" style="">
                                                        <div class="paybox-tit"><strong>网银支付</strong> 需开通网银</div>
                                                        <jsp:include page="/WEB-INF/jsp/common/alibanks.jsp"></jsp:include>
                                                    </div>
                                                </div>--%>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                            <div class="form-actions">
                                <a id="submit_a" data-id="<s:property value='payHttp'/>"
                                   class="btn btn-danger btn-large">马上充值</a>　
                                <s:if test="pageType"></s:if>
                                <input type="hidden" id="pageType" value="<s:property value='pageType'/>"/>
                                <a href="javascript:void(0);" id="brackButton" class="btn btn-large">返回</a>
                            </div>
                            <!--结束-->
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
        </div>
    </div>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!--弹出提示框-->
<div id="myPop" style='display:none;'>
    <div id="myAlert" class="modal">
        <div class="modal-header">
            <button data-dismiss="modal" class="close" type="button">&times;</button>
            <h3>支付</h3>
        </div>
        <div class="modal-body">
            <div class="pay-info">

            </div>
            <p>请您在新打开的网上银行窗口进行支付操作，支付完成前请不要关闭该窗口。</p>
            <br/>

            <div class="modal-footer">
                <a data-dismiss="modal" class="btn btn-primary" href="javaScript:void(0);">已完成支付</a>
                <a data-dismiss="modal" class="btn btn-danger but-error"
                   href="<s:property value="rootPath"/>helps/help-payment-faqs.shtml" target="_blank">支付遇到问题</a>
            </div>
        </div>
    </div>
</div>
<!--弹出提示框-->
</body>
</html>