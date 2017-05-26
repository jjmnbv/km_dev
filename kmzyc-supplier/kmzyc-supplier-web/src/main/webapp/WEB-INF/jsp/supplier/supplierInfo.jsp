<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="商家信息"></jsp:param>
    </jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>商家信息</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>店铺 <span class="divider">/</span></li>
                            <li>商家信息</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <!--内容菜单开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">商家信息</a></li>
                        </ul>
                        <!--内容菜单结束-->
                        <!--提示开始-->
                        <div class="alert">
                            <strong>提示：</strong> 如果您的商户信息发生变动，请联系在线客服进行资料更新，或者您也可以直接联系您的专属客户经理。
                        </div>
                        <!--提示结束-->
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table  table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="2" class="shoptL">登录账号信息：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="width160 shoptR">用户名：</td>
                                <td class="shoptL"><s:property value="supplierAccountInfo.loginAccount"/></td>
                            </tr>
                            <%--<tr>
                              <td class="width160 shoptR">邮箱地址：</td>
                              <td class="shoptL"><s:property value="supplierAccountInfo.email" /></td>
                            </tr>--%>
                            <tr>
                                <td class="width160 shoptR">手机号码：</td>
                                <td class="shoptL"><s:property value="supplierAccountInfo.mobile"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0"
                               class="table table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="9" class="shoptL">商家基本信息：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="width160 shoptR">公司名称：</td>
                                <td class="shoptL" width="30%"><s:property value="supplierBaseInfo.corporateName"/></td>
                                <td class="width160 shoptR">公司地址：</td>
                                <td class="shoptL">
                                    <s:property value="supplierBaseInfo.province"/><s:property value="supplierBaseInfo.city"/><s:property value="supplierBaseInfo.area"/><s:property value="supplierBaseInfo.corporateLocation"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">联系电话：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.mobile"/></td>
                                <td class="width160 shoptR">固定电话：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.fixedPhone"/></td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">联系邮箱：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.contactsEmail"/></td>
                                <td class="width160 shoptR">法定经营范围：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.businessScope"/></td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">组织机构代码：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.organizationCode"/></td>
                                <td class="width160 shoptR">注册资金：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.registerBankroll"/>万
                                </td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">营业执照注册号：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.businessLicenceRegister"/></td>
                                <td class="width160 shoptR">营业执照有效期：</td>
                                <td class="shoptL">
                                    <s:date name='%{supplierBaseInfo.blinceStartdate}' format='yyyy-MM-dd'/> 至 <s:date name='%{supplierBaseInfo.blinceEnddate}' format='yyyy-MM-dd'/>
                                </td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">税务登记证号：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.taxRegistrationCno"/></td>
                                <td class="width160 shoptR">纳税人识别号：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.taxpayerIdnumber"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="9" class="shoptL">财务信息：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="width160 shoptR">银行开户行：</td>
                                <td class="shoptL" width="30%"><s:property value="supplierBaseInfo.bankOfDeposit"/></td>
                                <td class="width160 shoptR">银行账号：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.companyAccount"/></td>
                            </tr>
                            <tr>
                                <td class="width160 shoptR">账户名：</td>
                                <td class="shoptL"><s:property value="supplierBaseInfo.bankAccountName"/></td>
                                <td class="width160 shoptR">账户余额：</td>
                                <td class="shoptL"><s:property value="supplierAccountInfo.amountAvlibal"/> 元
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="2" class="shoptL">经营类目佣金比例：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="suppliserCategoryList" var="supplierCategory1">
                                <s:if test="#supplierCategory1.categoryParentId == 0">
                                    <tr>
                                    <td class="width160 shoptR">${categoryName}</td>
                                    <td class="shoptL">
                                        <ul class="commission">
                                            <s:iterator value="suppliserCategoryList" var="supplierCategory2">
                                                <s:if test="#supplierCategory1.categoryId == #supplierCategory2.categoryParentId">
                                                    <li>${categoryName}<em>${commissionRatio}%</em>
                                                    </li>
                                                </s:if>
                                            </s:iterator>
                                        </ul>
                                    </td>
                                </s:if>
                                <s:if test="#supplierCategory1.categoryParentId == 0">
                                    </tr>
                                </s:if>
                            </s:iterator>
                            </tbody>
                        </table>
                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="2" class="shoptL">资质文件：
                                    <button class="btn btn-danger btn-mini btnR j_supplier_uploadfile">
                                        新增文件
                                    </button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <s:iterator value="certificateList" var="certificate">
                                        <div class="media photo">
                                            <a class="isop-left" href="${supplierPath}${certificate.filePath}" target="_blank">
                                                <img class="isophoto" src="${supplierPath}${certificate.filePath}">${certificate.fileName}
                                            </a>
                                        </div>
                                    </s:iterator>
                                    <div class="media photo">
                                        <a class="isop-left" href="${supplierPath}${supplierBaseInfo.organizationUrl}"
                                           target="_blank">
                                            <img width="170px" height="170px" class="isophoto"
                                                 src="${supplierPath}${supplierBaseInfo.organizationUrl}">组织机构代码电子版</a>
                                    </div>
                                    <div class="media photo">
                                        <a class="isop-left" href="${supplierPath}${supplierBaseInfo.uploadBusinessLicencePictur}" target="_blank">
                                            <img width="170px" height="170px" class="isophoto"
                                                 src="${supplierPath}${supplierBaseInfo.uploadBusinessLicencePictur}">营业执照电子版</a>
                                    </div>
                                    <div class="media photo">
                                        <a class="isop-left" href="${supplierPath}${supplierBaseInfo.taxRegCertificateCopy}" target="_blank">
                                            <img width="170px" height="170px" class="isophoto"
                                                 src="${supplierPath}${supplierBaseInfo.taxRegCertificateCopy}">税务登记证电子版</a>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered uploadfile" style="display: none;">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="2" class="shoptL">上传资质文件：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <s:actionerror/>
                                    <form action="uploadCeriticate.action" namespace="supplier"
                                          name="frm" method="post" enctype="multipart/form-data"
                                          id="frm" class="ui-form ui-form-vertical fn-t20">
                                        <div class="ui-form-item">
                                            <img id="loading_img" src="" style="display: none;"/>
                                            <div class="alert">
                                                <strong>提示：</strong>
                                                请上传您的营业执照（副本）、组织机构代码证扫描件，以及您所供应商品的相关授权、资质证明文件的扫描件。
                                            </div>
                                            <input type="text" tabindex="9" maxlength="20" name="realName"
                                                   class="ui-input fn-w280 fn-mr10 j_realName"
                                                   placeholder="请输入文件名称"/>
                                            <input tabindex="10" id="upFile" contenteditable="false"
                                                   class="fn-w180 fn-mr20 j_file" name="file" type="file"/>
                                            <span id="error_message" class="fn-red"></span>
                                            <button class="btn btn-danger btn-mini btnR j_submitBtn">上传文件</button>
                                        </div>
                                    </form>
                                    <iframe id="upload-target" src="" name="upload-target" style="display: none;"></iframe>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>