<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="我要提现"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>我要提现</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_settlement.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>结算 <span class="divider">/</span></li>
                            <li>账户明细 <span class="divider">/</span></li>
                            <li>我要提现</li>
                        </ul>
                    </div>

                    <!--左侧菜单结束-->
                    <s:form action="supplierApplyFor.action" method="POST" namespace="/ajaxJson" id="supplierApplyFor">
                    <input type="hidden" name="companyAccount" value="<s:property value="accountBasics.companyAccount"/>"/>
                    <input type="hidden" name="bankOfDeposit" value="<s:property value="accountBasics.bankOfDeposit"/>"/>
                    <input type="hidden" name="bankAccountName" value="<s:property value="accountBasics.bankAccountName"/>"/>
                    <input type="hidden" class="money_appy" value="<s:property value="accountBasics.amountAvlibal"/>"/>

                    <div class="block-content collapse in">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">我要提现</a></li>
                        </ul>
                        <div class="alert"><strong>提示：</strong>请先仔细核对您的收款银行信息和提现金额是否正确！</div>

                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                            <tbody>
                            <tr class="tablesbg">
                                <td class="width200">收款银行账号</td>
                                <td class="width200">银行帐号名</td>
                                <td class="width200">开户行</td>
                            </tr>
                            <tr>
                                <td class="width200"><s:property value="accountBasics.companyAccount"/></td>
                                <td class="width200"><s:property value="accountBasics.bankAccountName"/></td>
                                <td class="width200"><s:property value="accountBasics.bankOfDeposit"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <fieldset class="form-horizontal">
                            <div class="control-group">
                                <label class="control-label" void(0)><span class="required">*</span>提现金额：
                                </label>
                                <div class="controls">
                                    <s:if test="bnesAcctEnchashment==null">
                                        <s:textfield style="width:180px;" class="span2"
                                                     name="amountAvlibal" autocomplete="off"
                                                     id="amountAvlibal"
                                                     onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
                                                     maxlength="10" placeholder=""
                                                     cssClass="ui-input"/>
                                        <font color="red">/<fmt:formatNumber value="${accountBasics.amountAvlibal}"
                                                                             type="currency" pattern="#,##0.00"/>元</font>
                                        <p class="res_error" style="color: #ccc;">请输入大于等于100的金额数字</p>
                                    </s:if>
                                    <s:else>
                                        <s:textfield style="width:180px;" class="span2"
                                                     name="bnesAcctEnchashment.enchashmentAmount"
                                                     readOnly="true"
                                                     id="amountAvlibal_n" cssClass="ui-input"/>
                                        <font color="red">/<fmt:formatNumber value="${accountBasics.amountAvlibal}"
                                                                             type="currency" pattern="#,##0.00"/>元</font>
                                    </s:else>
                                </div>
                                </br>
                                <span style="width: 945px; height: 65px; display: none">支付密码：
                                    <s:if test="bnesAcctEnchashment==null">
                                        <textarea name="enchashmentDesict" class="span2" id="enchashmentDesict"
                                                  maxlength="80" style="width: 500px;" autocomplete="off"></textarea>
                                    </s:if>
                                    <s:else>
                                        <textarea name="bnesAcctEnchashment_n" class="span2" id="enchashmentDesict_n"
                                                  disabled=true maxlength="200" style="width: 500px;">
                                            <s:property value="bnesAcctEnchashment.enchashmentDepict"/>
                                        </textarea>
                                    </s:else>
			                    </span>
                            </div>

                            <div class="control-group">
                                <s:if test="pwdResult==null">
				                    <span style="width: 945px; position: absolute;left: 80px;top: 260px;">您还未启用支付密码，不能进行提现操作。请到康美中药城商城，登录后进入安全中心，
					                    <a href="<%=ConfigurationUtil.getString("APPLY_PAY_PATH")%>showSecurityCentre.action"
                                           target="_blank">启用支付密码。</a>
				                    </span>
                                </s:if>
                                <s:else>
                                    <s:if test="bnesAcctEnchashment==null">
                                        <span style="width: 945px; position: absolute;left: 80px;top: 275px;">确认无误后请输入商家主账号的支付密码并提交提现申请!</span>
                                    </s:if>
                                    <s:else>
                                        <div class="alert alert-success" style="left: 80px;top: 250px;">
                                            <strong>提示：</strong>您已经提交了账户余额提现申请单，财务人员将在5个工作日内完成审核，请耐心等待。如需取消本期提现申请或者有其他疑问，请联系我们的客服人员。
                                        </div>
                                    </s:else>
                                    </br>
                                    <s:if test="bnesAcctEnchashment==null">
                                        <label class="control-label" void(0)> <span class="required">*</span>支付密码： </label>
                                        <div class="controls">
                                            <s:password style="width:180px;" class="span2"
                                                        name="paymentpwd" id="paymentpwd"
                                                        placeholder=""
                                                        maxlength="16" cssClass="ui-input"
                                                        autocomplete="off"/>
                                            <p class="pwd_error">此密码为商家账号的支付密码</p>
                                        </div>
                                        <div class="form-actions">
                                            <button class="btn btn-warning btn-large t_supplierApplyFor">
                                                <i class="icon-fire icon-white"></i> 申请提现
                                            </button>
                                        </div>
                                    </s:if>
                                </s:else>
                            </div>
                        </fieldset>
                        <!--结束-->
                    </div>
                    </s:form>
                </div>
            </div>
            <hr>
        </div>
    </div>
</div>
<!-- 底部 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<!-- 底部  end-->
</body>
</html>