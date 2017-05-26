<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-w">
    <h3 class="step--sub-title fn-mt20">您申请的基本资料和联系信息</h3>
    <div class="ui-tiptext-container ui-tiptext-container-error fn-mt10">
        <p class="ui-tiptext ui-tiptext-error">
            您填写的会员账号已经提交了供应商申请，并正在审核中，请勿重复提交信息。<br />
            如果您提交的申请资料有误需要修改，请在我们的工作人员和您联系的时候说明进行修改。<br />
            您也可以撤回之前提交的供应商申请，然后重新填写申请表。
        </p>
    </div>

    <form class="ui-form ui-form-info fn-mt20" method="post" action="applySupplierTwo.action" id="forms2">
        <input type="hidden" name="showUserinfo.loginId" value="${showUserinfo.loginId}"/>
        <input type="hidden" name="merchantInfo.merchantId" value="${merchantInfo.merchantId}" id="merchantId"/>
        <input type="hidden" name="suppliersInfo.supplierId" value="${suppliersInfo.supplierId}"/>
        <fieldset>
            <div class="ui-form-item">
                <label class="ui-form-label">公司名称（全称）：</label>
                <p class="ui-form-text">${merchantInfo.corporateName}</p>
            </div>

            <div class="ui-form-item">
                <label class="ui-form-label">  组织机构代码：</label>
                <p class="ui-form-text">${merchantInfo.organizationCode}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">营业执照注册号：</label>
                <p class="ui-form-text">${merchantInfo.businessLicenceRegister}</p>
            </div>
             <div class="ui-form-item">
                <label class="ui-form-label">营业执照有效期：</label>
                <p class="ui-form-text">
                <s:date name='%{merchantInfo.blinceStartdate}' format='yyyy-MM-dd'/>到
                <s:date name='%{merchantInfo.blinceEnddate}' format='yyyy-MM-dd'/>
                </p>
            </div>
             <div class="ui-form-item">
                <label class="ui-form-label">法定经营范围：</label>
                <p class="ui-form-text">${merchantInfo.businessScope}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">税务登记证号：</label>
                <p class="ui-form-text">${merchantInfo.taxRegistrationCno}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">纳税人识别号：</label>
                <p class="ui-form-text">${merchantInfo.taxpayerIdnumber}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">公司地址：</label>
                <p class="ui-form-text">
                 ${merchantInfo.province}${merchantInfo.city}${merchantInfo.area}${merchantInfo.corporateLocation}
                </p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">注册资金：</label>
                <p class="ui-form-text">${merchantInfo.registerBankroll}万</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">开户银行：</label>
                <p class="ui-form-text">${merchantInfo.bankOfDeposit}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">账户名：</label>
                <p class="ui-form-text">${merchantInfo.bankAccountName}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label">银行账号：</label>
                <p class="ui-form-text">${merchantInfo.companyAccount}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label"> 公司联系人：</label>
                <p class="ui-form-text">${merchantInfo.contactsName}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label"> 公司联系人手机：</label>
                <p class="ui-form-text">${merchantInfo.mobile}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label"> 公司联系人电话：</label>
                <p class="ui-form-text">${merchantInfo.phone}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label"> 公司固定电话：</label>
                <p class="ui-form-text">${merchantInfo.fixedPhone}</p>
            </div>
            <div class="ui-form-item">
                <label class="ui-form-label"> 联系邮箱：</label>
                <p class="ui-form-text">${merchantInfo.contactsEmail}</p>
            </div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
                <thead>
                    <tr class="tablesbg">
                        <th colspan="2" class="shoptL">经营类目佣金比例：</th>
                    </tr>
                </thead>
                <tbody>
                <s:iterator value="suppliserCategoryList" var="supplierCategory">
                    <tr>
                        <s:if test="#supplierCategory.categoryParentId == 0">
                            <td class="width160 shoptR"><s:property value="#supplierCategory.categoryName"/></td>
                            <td class="shoptL">
                                <ul class="commission">
                                    <s:iterator value="suppliserCategoryList" var="supplierCategory1">
                                        <s:if test="#supplierCategory1.categoryParentId == #supplierCategory.categoryId">
                                            <li><s:property value="#supplierCategory1.categoryName"/>
                                                <em><s:property value="#supplierCategory1.commissionRatio"/>%</em>
                                            </li>
                                        </s:if>
                                    </s:iterator>
                                </ul>
                            </td>
                        </s:if>
                    </tr>
                </s:iterator>
                </tbody>
		    </table>
            <h3 class="step--sub-title">已上传的资质文件:</h3>
            <div class="ui-well ui-well-lg fn-mt10">
                <ul class="papers-img fn-inline-block">
                <s:iterator value="certificateList" var="certificate">
                    <li>
                        <a href="${supplierPath}${certificate.filePath}" target="_blank"
                           imgpath="${supplierPath}${certificate.filePath}" class="showimg">
                            <img width="190px" height="190px" src="${supplierPath}${certificate.filePath}"/>
                        </a>
                        ${certificate.fileName }&nbsp;&nbsp;
                    </li>&nbsp;&nbsp;&nbsp;&nbsp;
                </s:iterator>
                <li>
                    <a href="${supplierPath}${merchantInfo.organizationUrl}" target="_blank" title="单击看大图">
                        <img width="170px" height="170px" src="${supplierPath}${merchantInfo.organizationUrl}">
                    </a>
                组织机构代码电子版&nbsp;&nbsp;<%--  <a href="javascript:void(0);" certificate_Id="${certificate.scId}" class="j_deleteSupplier">删除</a>--%>
                &nbsp;&nbsp;&nbsp;&nbsp;
                </li>
                <li>
                <a href="${supplierPath}${merchantInfo.uploadBusinessLicencePictur}" target="_blank" title="单击看大图">
                    <img width="170px" height="170px" src="${supplierPath}${merchantInfo.uploadBusinessLicencePictur}">
                </a>
                营业执照电子版&nbsp;&nbsp;<%--  <a href="javascript:void(0);" certificate_Id="${certificate.scId}" class="j_deleteSupplier">删除</a>--%>
                &nbsp;&nbsp;&nbsp;&nbsp;
                </li>
                <li>
                <a href="${supplierPath}${merchantInfo.taxRegCertificateCopy}" target="_blank" title="单击看大图">
                    <img width="170px" height="170px" src="${supplierPath}${merchantInfo.taxRegCertificateCopy}">
                </a>
                税务登记证电子版&nbsp;&nbsp;<%--  <a href="javascript:void(0);" certificate_Id="${certificate.scId}" class="j_deleteSupplier">删除</a>--%>
                &nbsp;&nbsp;&nbsp;&nbsp;
                </li>
                </ul>
            </div>
            <div class="ui-form-item uipleft">
                <a href="javascript:void(0);" class="ui-button ui-button-success ui-button-lg fn-mr10"
                   id="ui-buttonTwo" cexiao="${suppliersInfo.supplierId}">撤销申请</a>
                <a href="javascript:void(0);" class="ui-button ui-button-default ui-button-lg"
                   id="ui-button-lg">取消</a>
            </div>
        </fieldset>
    </form>
    <s:form action="/supplier/goBackTwo.action" method="post" id="frm">
        <input type="hidden" name="showUserinfo.loginId" value="${showUserinfo.loginId}"/>
    </s:form>
</div>