<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	<h3 class="step--sub-title">三、上传更多资质文件</h3>
    <p class="ui-well ui-well-sm fn-gray fn-mt20">
        请上传您所供应商品的相关授权，资质证明文件的扫描件。更详细的信息，我们就更能精确定位贵公司的产品，从而提高申请成功率。
    </p>
    <div>
        <p>已上传的资质文件:</p>
        <div class="ui-well ui-well-lg fn-mt10">
            <ul class="papers-img fn-inline-block">
                <s:iterator value="certificateList" var="certificate">
                <li id="imgs${certificate.scId}">
                <a target="_blank" href="${supplierPath}${certificate.filePath}" title="单击查看大图">
                    <img width="170px" height="170px"
                         src="${supplierPath}${certificate.filePath}">
                </a>${certificate.fileName }&nbsp;&nbsp;
                <a href="javascript:void(0);" scId="${certificate.scId}" suppId="${certificate.supplierId}"
                   class="j_deleteSupplier">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                </li>
                </s:iterator>
            </ul>
        </div>
    </div>
    <div class="fn-mt10">
        <s:actionerror/>
        <input type="hidden" id="userId" value="${showUserinfo.loginId}" name="showUserinfo.loginId">
        <form action="uploadCeriticate.action" namespace="supplier" name="frm" method="post"
              enctype="multipart/form-data" id="frm" class="ui-form ui-form-vertical fn-t20" >
            <input type="hidden" name="supplierId" value="${supplierId}">
            <div class="ui-form-item">
                <img id="loading_img" src="" style="display: none;" />
                <label class="ui-form-label">
                    <span class="ui-form-required"></span>请上传您所供应商品的相关授权、资质证明文件的扫描件。
                </label>
                <input type="text" tabindex="9"  name="realName" class="ui-input fn-w280 fn-mr10 j_realName"
                       maxlength="12" placeholder="请输入文件名称" />
                <input tabindex="10" id="upFile" contenteditable="false" class="fn-w180 fn-mr20 j_file"
                       name="file" type="file" />
                <span id="error_message" class="fn-red"></span>
                <input type="button" class="ui-button ui-button-success j_submitBtn" value="上传文件"/>
                <span><s:fielderror cssStyle="color: red"/></span>
            </div>
        </form>
        <s:form action="goAddShop.action" method="post" id="frm1">
			<input type="hidden" id="userId" value="${showUserinfo.loginId}" name="userId">
			<input type="hidden" name="supplierId" value="${supplierId}">
        </s:form>
        <s:form action="goBackTwo.action" method="post" id="frm2">
			<input type="hidden" value="${showUserinfo.loginId}" name="showUserinfo.loginId">
        </s:form>
    </div>
    <iframe id="upload-target" src="" name="upload-target" style="display: none;"></iframe>
    <div class="ui-form-item uipleft">
        <a href="javascript:void(0);" class="ui-button ui-button-default ui-button-lg"
           id="ui-button-lg" userids="${showUserinfo.loginId}">上一步</a>&nbsp;&nbsp;
        <a href="javascript:void(0);" class="ui-button ui-button-success ui-button-lg fn-mr10"
           id="finish" supplierId="${supplierId}" userid="${showUserinfo.loginId}">完成</a>
        <a href="javascript:void(0);" id="applyShop">申请店铺</a>
    </div>
    <input type="hidden" id="goLogin" value="<%=basePath%>">
</div>