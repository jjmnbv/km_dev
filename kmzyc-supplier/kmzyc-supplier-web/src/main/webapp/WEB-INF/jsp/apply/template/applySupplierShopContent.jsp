<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="l-w">
    <div class="apply-step">
        <ol class="ui-step ui-step-4 fn-clear">
            <li class="ui-step-start ui-step-done">
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">1</i>
                    <span class="ui-step-text">验证会员</span>
                </div>
            </li>
            <li class="ui-step-start ui-step-done">
                <div class="ui-step-line">-</div>
                <div class="ui-step-icon">
                    <i class="ui-step-icon-block"></i>
                    <i class="ui-step-number">2</i>
                    <span class="ui-step-text">确认入驻协议</span>
                </div>
            </li>
            <li class="ui-step-active">
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
    <div class="fn-p20">
        <form class="ui-form ui-form-info fn-mt20" action="/ajaxJson/applySupplierShop.action" method="post" id="frm"
              enctype="multipart/form-data">
            <input type="hidden" value="${userId}" name="shopMainDraft.applyUser">
            <input type="hidden" value="${supplierId}" name="shopMainDraft.supplierId">
            <fieldset>
                <legend>店铺基本设置</legend>
                <%--<div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>店铺所属站点：</label>
                    <s:select cssClass="ui-form-select" list="#request.siteTypeMap" name="shopMainDraft.shopSite"></s:select>
                </div>--%>
                <div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>店铺名称：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.shopName" id="shopName" maxlength="40"/>
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_shopName_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="shop_error_msg"></span>
                    </p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label">店铺标题：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.shopTitle" maxlength="150"/>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>经营品牌：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.manageBrand" maxlength="150" id="manageBrand"/>（多品牌以逗号隔开）
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_manageBrand_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="manageBrand_error_msg"></span>
                    </p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>店铺负责人姓名：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.principalName" maxlength="150"
                                 id="principalName"/>
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_principalName_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="principalName_error_msg"></span>
                    </p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label">店铺负责人电话：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.contactInfo" maxlength="150" id="contactInfo"/>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label">店铺负责人手机：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.linkmanMobile" maxlength="150"
                                 id="linkmanMobile"/>
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_contactInfo_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="contactInfo_error_msg"></span></p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>客服联系方式：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.serviceQq" maxlength="20" id="serviceQq"/>
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_serviceQq_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="serviceQq_error_msg"></span></p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label"><font color="red">*</font>客服联系类型：</label>
                    <s:select cssClass="ui-form-select" list="#request.shopMainServiceTypeMap"
                              name="shopMainDraft.serviceType"></s:select>
                </div>
                <%--<div class="ui-form-item">
                        <label class="ui-form-label">店铺LOGO：</label>
                        <s:file name="logoFile" id="logoFile" cssClass="ui-input j_change_logoFile"></s:file> 注：支持后缀*.jpg/*.png/*.gif
                        <s:hidden name="shopMain.logoPath" id="logoPath"/>
                    </div>
                    <div class="ui-form-item">
                        <label class="ui-form-label">店铺展示图片：</label>
                        <s:file name="shopMainFile" id="shopMainFile" cssClass="ui-input j_change_shopMainFile"></s:file> 注：建议上传图片宽高为：300 * 199,支持后缀*.jpg/*.png/*.gif
                        <s:hidden name="shopMain.filePath" id="filePath"/>
                    </div>--%>
                <div class="ui-form-item">
                    <label class="ui-form-label">商家简介：</label>
                    <s:textarea cssClass="ui-input" name="shopMainDraft.introduce" id="introduce" cols="50" rows="4"/>
                    <p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10 j_introduce_error"
                       style="display: none;">
                        <i class="ui-icon ui-icon-error"></i><span id="introduce_error_msg"></span>
                    </p>
                </div>
                <div class="ui-form-item">
                    <label class="ui-form-label">店铺类型：</label>
                    <s:select cssClass="ui-form-select" list="#request.shopMainTypeMap"
                              name="shopMainDraft.shopType"></s:select>
                </div>
                <legend>SEO设置</legend>
                <div class="ui-form-item">
                    <label class="ui-form-label">店铺SEO关键字：</label>
                    <s:textfield cssClass="ui-input" name="shopMainDraft.shopSeoKey" maxlength="300"/>
                </div>
            </fieldset>

            <table class="ui-table ui-table-noborder fn-mt20">
                <tbody>
                <tr align="center">
                    <th class="col-w-120 fn-text-rt">&nbsp;</th>
                    <td align="left">
                        <input class="ui-button ui-button-success ui-button-lg j_submit_addShopMain"
                               id="submitButton" type="button" value="提交"/>&nbsp;&nbsp;
                        <input class="ui-button ui-button-default ui-button-lg j_shopMain_list"
                               type="button" value="返回"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<input type="hidden" id="goLogin" value="<%=basePath%>">
<s:form action="goBackThree.action" method="post" id="shopMainListForm" namespace="supplier">
    <input type="hidden" value="${userId}" name="showUserinfo.loginId"/>
    <input type="hidden" value="${supplierId}" name="supplierId"/>
</s:form>