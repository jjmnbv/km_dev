<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="l-w">
    <div class="apply-step">
        <ol class="ui-step ui-step-4 fn-clear">
            <li class="ui-step-active">
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">1</i>
                    <span class="ui-step-text">验证会员</span>
                </div>
            </li>
            <li>
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">2</i>
                    <span class="ui-step-text">确认入驻协议</span>
                </div>
            </li>
            <li>
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">3</i>
                    <span class="ui-step-text">提交申请表格</span>
                </div>
            </li>
            <li>
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">4</i>
                    <span class="ui-step-text">平台审核</span>
                </div>
            </li>
            <li class="ui-step-end">
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">√</i>
                    <span class="ui-step-text">确认服务协议</span>
                </div>
            </li>
        </ol>
    </div>
    <h3 class="step--sub-title">申请成为供应商请先验证会员资格</h3>
    <p class="ui-well ui-well-sm fn-gray fn-mt20">
        <input type="hidden" value="${goToCkPath}" id="b2bPath">
        <input type="hidden" value="<%=basePath%>" id="goLoginPath"/>
        申请康美中药城供应商资格，必须先注册成为康美中药城的正式会员，并
        <a href="${goToCkPath}" target="_blank">通过手机验证</a>。还不是会员？
        <a href="${goToRegisterPath}" target="_blank">马上去注册</a>&nbsp;&nbsp;（<a href="<%=basePath%>">登录供应商</a>）
    </p>

    <div class="apply-step-form fn-mt10">
        <form class="ui-form ui-form-vertical fn-t20" name="" method="post" 
              action="verifyMember.action" id="froms">
            <div class="ui-form-item uipleft">
                <label class="ui-form-label">
                    <span class="ui-form-required">*</span>手机号：
                </label>
                <input type="text" tabindex="1" class="ui-input fn-w180" name="userName" id="userNameId"
                       maxlength="21"/>
                <span class="ui-form-other"><abbr title="注册使用的手机号" class="fn-blue"></abbr></span>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" style="display: none;"
                   id="userNameNull">
                    <i class="ui-icon ui-icon-error"></i>
                    请输入手机号
                </p>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" style="display: none;"
                   id="userNameLength">
                    <i class="ui-icon ui-icon-error"></i>
                    请您输入正确的手机号
                </p>
            </div>
            <div class="ui-form-item uipleft">
                <label class="ui-form-label">
                    <span class="ui-form-required">*</span>登录密码：
                </label>
                <input type="password" tabindex="2" class="ui-input fn-w180" name="password" id="passwordId"
                       maxlength="21"/>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" style="display: none;" id="pwdNull">
                    <i class="ui-icon ui-icon-error"></i>
                    请输入密码
                </p>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" style="display: none;"
                   id="pwdLength">
                    <i class="ui-icon ui-icon-error"></i>
                    密码长度在6-20字符之间
                </p>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" id="noPassError"
                   style="display: none;">
                    <i class="ui-icon ui-icon-error"></i>
                    您输入的账户名和密码不匹配，请重新输入
                </p>
            </div>
            <div class="ui-form-item uipleft">
                <label class="ui-form-label"><span class="ui-form-required">*</span>请输入您在下图中看到的字符</label>
                <input type="text" tabindex="3" id="verifyCode" name="fourcode" autocomplete="off"
                       maxlength="4" data-explain="请输入右图中字符，不区分大小写。"
                       class="ui-input ui-input-checkcode"/>
                <a href="javascript:void(0);" class="ImgCode">
                    <img align="absMiddle" title="点击刷新图片校验码" id="imageCode"
                         src="" class="ui-checkcode-imgcode-img"/>
                </a>
                <a class="fn-blue ImgCode" href="javascript:void(0);">看不清，换一张</a>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" id="verifyCodeError"
                   style="display: none;">
                    <i class="ui-icon ui-icon-error"></i>
                    验证码错误
                </p>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" id="verifyCodeNull"
                   style="display: none;">
                    <i class="ui-icon ui-icon-error"></i>
                    请输入验证码
                </p>
                <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10" id="error1"
                   style="display: none;">
                    <i class="ui-icon ui-icon-error"></i>
                    验证会员失败，刷新后重新输入
                </p>
            </div>
            <div class="ui-form-item uipleft">
                <a href="<%=basePath%>" class="ui-button ui-button-default ui-button-lg">取消</a>&nbsp;&nbsp;
                <a tabindex="4" href="javascript:void(0);" id="ui-button"
                   class="ui-button ui-button-success ui-button-lg fn-mr10">下一步</a>
                还不是会员？<a href="${goToRegisterPath}" target="_blank">立即注册</a>
            </div>
        </form>
        <form action="" method="post" id="frm1">
            <input type="hidden" value="" id="loginId" name="showUserinfo.loginId">
        </form>
        <form action="" method="post" id="frm2">
            <input type="hidden" value="" id="supplierId" name="supplierId">
        </form>
    </div>
</div>
