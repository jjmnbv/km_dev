<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<h3 class="step--sub-title">一、确认申请人会员信息</h3>
<p class="ui-well ui-well-sm fn-gray fn-mt20">
如申请成功，此会员将成为供应商主账号，主账号是唯一绑定并且不可变更。</p>
<div class="apply-step-level-form fn-mt10">
    <form class="ui-form fn-t20" name="" method="post" action="#" id="">
        <div class="ui-form-item">
            <label for="" class="ui-form-label">
                <span class="ui-form-required">*</span>会员账号： </label>
            <div class="ui-form-text">${showUserinfo.loginAccount}</div>
        </div>
        <div class="ui-form-item">
            <label class="ui-form-label" for=""><span class="ui-form-required">*</span>手机号码：</label>
            <div class="ui-form-text">${showUserinfo.mobile}</div>
        </div>
    </form>
</div>

<h3 class="step--sub-title">二、商户基本资料和联系信息</h3>
<p class="ui-well ui-well-sm fn-gray fn-mt20">请正确填写贵公司的真实资料，并确保填写的信息可以联系到贵公司进行本申请的负责人。</p>
<div class="apply-step-form fn-mt10">
<s:form class="ui-form ui-form-vertical fn-t20" name="" method="post"
	action="applySupplierTwo.action" id="forms2" enctype="multipart/form-data" namespace='/app'>

    <input type="hidden" name="showUserinfo.loginId" value="${showUserinfo.loginId}"/>
    <input type="hidden" name="merchantInfo.merchantId" value="${merchantInfo.merchantId}" id="merchantId"/>
    <input type="hidden" name="suppliersInfo.supplierId" value="${suppliersInfo.supplierId}" id="supplierIds"/>
    <div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>公司名称（全称）： </label> <input type="text"
	tabindex="1" class="ui-input fn-w280" 
	name="merchantInfo.corporateName" id="corporateName" maxlength="40"
	value="${merchantInfo.corporateName}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="corporateNameNull"><i
	class="ui-icon ui-icon-error"></i> 请输入公司名称</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="corporateNameError"><i
	class="ui-icon ui-icon-error"></i> 公司名称由数字,字母,中文组成</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="corporateNameError1"><i
	class="ui-icon ui-icon-error"></i> 您输入的公司名称已经存在</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>组织机构代码： </label> <input type="text"
	tabindex="2" class="ui-input fn-w280" maxlength="30"
	name="merchantInfo.organizationCode" id="organizationCode"
	value="${merchantInfo.organizationCode}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="organizationCodeNull"><i
	class="ui-icon ui-icon-error"></i> 请输入组织机构代码</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="organizationCodeError"><i
	class="ui-icon ui-icon-error"></i> 请输入格式为00000000-0的组织机构代码</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="organizationCodeError1"><i
	class="ui-icon ui-icon-error"></i> 您输入的组织机构代码已经存在</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>组织机构代码电子版： </label> 
<input type="file" name="organizationFile" id="organizationFile"></input>
<s:if test="merchantInfo.organizationUrl != null"><a href="${supplierPath}${merchantInfo.organizationUrl}" target="_blank"><img src="${supplierPath}${merchantInfo.organizationUrl}" style="width: 200px;height: 80px;"></img> </a></s:if>
<input type="hidden" value="" name="organizationFileName" id="organizationFileNameId"></input>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="organizationFileErr"><i
	class="ui-icon ui-icon-error"></i> 请上传bmp,png,gif,jpeg,jpg格式的图片</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="organizationFileSizeErr"><i
	class="ui-icon ui-icon-error"></i> 当前文件不可超过1M</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>营业执照注册号： </label> <input type="text"
	tabindex="3" class="ui-input fn-w280" maxlength="30"
	name="merchantInfo.businessLicenceRegister" id="business"
	value="${merchantInfo.businessLicenceRegister}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="businessNull"><i
	class="ui-icon ui-icon-error"></i> 请输入营业执照注册号</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="businessError"><i
	class="ui-icon ui-icon-error"></i> 营业执照注册号由15位数字组成</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="businessError1"><i
	class="ui-icon ui-icon-error"></i> 您输入的 营业执照注册号已经存在</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>营业执照电子版： </label> 
<input type="file" name="businessFile" id="businessFile"></input>
<input type="hidden" value="" name="businessFileName" id="businessFileNameId"></input>
<s:if test="merchantInfo.uploadBusinessLicencePictur != null"><a href="${supplierPath}${merchantInfo.uploadBusinessLicencePictur}" target="_blank"><img src="${supplierPath}${merchantInfo.uploadBusinessLicencePictur}" style="width: 200px;height: 80px;"></img></a> </s:if>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="businessFileErr"><i
	class="ui-icon ui-icon-error"></i> 请上传bmp,png,gif,jpeg,jpg格式的图片</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="businessFileSizeErr"><i
	class="ui-icon ui-icon-error"></i> 当前文件不可超过1M</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
营业执照有效期： </label> 
<input class="" CssClass="Wdate" onFocus="WdatePicker()" id="staTime" runat="server"
					name="merchantInfo.blinceStartdate" readonly="readonly" type="text" size="10" maxlength ="15"
					 format='yyyy-MM-dd'/ value="<s:date name='%{merchantInfo.blinceStartdate}' format='yyyy-MM-dd'/>"> 到
					<input class="" CssClass="Wdate" onFocus="WdatePicker()" id="endTime" runat="server"
					name="merchantInfo.blinceEnddate" readonly="readonly" type="text" size="10" maxlength ="15"
					 format='yyyy-MM-dd' value="<s:date name='%{merchantInfo.blinceEnddate}' format='yyyy-MM-dd'/>" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="validTimeNull"><i
	class="ui-icon ui-icon-error"></i> 请选择时间</p>
	<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="validTimeErr"><i
	class="ui-icon ui-icon-error"></i> 开始时间不能大于结束时间</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
法定经营范围： </label> <input type="text"
	tabindex="3" class="ui-input fn-w280" maxlength="50"
	name="merchantInfo.businessScope" id="business"
	value="${merchantInfo.businessScope}" />
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>税务登记证号： </label> <input type="text"
	tabindex="3" class="ui-input fn-w280" maxlength="30"
	name="merchantInfo.taxRegistrationCno" id="taxRegistrationCno"
	value="${merchantInfo.taxRegistrationCno}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="taxRegistrationCnoNull"><i
	class="ui-icon ui-icon-error"></i> 请输入税务登记证号</p>
	<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="taxRegistrationCnoErr"><i
	class="ui-icon ui-icon-error"></i> 税务登记证号由15数字组成</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>税务登记证电子版： </label> 
<input type="file" name="taxRegistratFile" id="taxRegistratFileId"></input>
<s:if test="merchantInfo.taxRegCertificateCopy != null"><a href="${supplierPath}${merchantInfo.taxRegCertificateCopy}" target="_blank"><img src="${supplierPath}${merchantInfo.taxRegCertificateCopy}" style="width: 200px;height: 80px;"></img> </a></s:if>
<input type="hidden" value="" name="taxRegistratFileName" id="taxRegistratFileNameId"></input>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="taxRegistratFileErr"><i
	class="ui-icon ui-icon-error"></i> 请上传bmp,png,gif,jpeg,jpg格式的图片</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="taxRegistratFileSizeErr"><i
	class="ui-icon ui-icon-error"></i> 当前文件不可超过1M</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
纳税人识别号： </label> <input type="text"
	tabindex="3" class="ui-input fn-w280" maxlength="30"
	name="merchantInfo.taxpayerIdnumber" id="business"
	value="${merchantInfo.taxpayerIdnumber}" />
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>公司地址： </label> 
<select class="sele" id="province" data-value="<s:property value="merchantInfo.province"/>" name="merchantInfo.province">
</select>
<select class="sele" id="city" data-value="<s:property value="merchantInfo.city"/>" name="merchantInfo.city">
</select>

<select class="sele" id="area" data-value="<s:property value="merchantInfo.area"/>"  name="merchantInfo.area">
</select></br>
街道详情地址：<input type="text" maxlength ="20"
	tabindex="4" class="ui-input fn-w280 fn-mr10" 
	name="merchantInfo.corporateLocation" id="contactAaddress"
	value="${merchantInfo.corporateLocation}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="contactAaddressNull"><i
	class="ui-icon ui-icon-error"></i> 请输入公司地址</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>注册资金： </label> 
<input type="text" maxlength ="15"
	tabindex="4" class="ui-input fn-w120 fn-mr10" 
	name="merchantInfo.registerBankroll" id="registerBankroll"
	value="${merchantInfo.registerBankroll}" /> 万
						
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="registerBankrollNull"><i
	class="ui-icon ui-icon-error"></i> 请输入注册资金</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="registerBankrollErr"><i
	class="ui-icon ui-icon-error"></i> 注册资金为整数</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>开户银行： </label> <input type="text" maxlength ="40"
	tabindex="4" class="ui-input fn-w280 fn-mr10" 
	name="merchantInfo.bankOfDeposit" id="bankOfDeposit"
	value="${merchantInfo.bankOfDeposit}" /> 
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="bankOfDepositNull"><i
	class="ui-icon ui-icon-error"></i> 请输入开户银行</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>账户名： </label> <input type="text" maxlength ="40"
	tabindex="4" class="ui-input fn-w280 fn-mr10" 
	name="merchantInfo.bankAccountName" id="bankAccountName"
	value="${merchantInfo.bankAccountName}" /> 
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="bankAccountNameNull"><i
	class="ui-icon ui-icon-error"></i> 请输入账户名</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
<span class="ui-form-required">*</span>银行账号： </label> <input type="text" maxlength ="30"
	tabindex="4" class="ui-input fn-w120 fn-mr10" 
	name="merchantInfo.companyAccount" id="companyAccount"
	value="${merchantInfo.companyAccount}" /> 
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="companyAccountNull"><i
	class="ui-icon ui-icon-error"></i> 请输入银行账号</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="companyAccountErr"><i
	class="ui-icon ui-icon-error"></i> 银行账号由数字组成</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
公司固定电话： </label> <input type="text"
	tabindex="4" class="ui-input fn-w120" maxlength="24"
	name="merchantInfo.fixedPhone" id="fixedPhone" value="${merchantInfo.fixedPhone}" /><span style="color:gray">(公司固定电话和联系人手机必填一项)</span>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="fixedPhoneNull"><i
	class="ui-icon ui-icon-error"></i> 请输入固定电话</p>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="fixedPhoneError"><i
	class="ui-icon ui-icon-error"></i> 请输入格式正确的固定号码</p>
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
公司联系人： </label> <input type="text" maxlength ="15"
	tabindex="4" class="ui-input fn-w120 fn-mr10" 
	name="merchantInfo.contactsName" id="contactsName"
	value="${merchantInfo.contactsName}" /> 
</div>
<div class="ui-form-item"><label for="" class="ui-form-label">
公司联系人手机： </label> <input type="text"
	tabindex="6" class="ui-input fn-w120" maxlength="12"
	name="merchantInfo.mobile" id="mobile" value="${merchantInfo.mobile}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="mobileError"><i
	class="ui-icon ui-icon-error"></i> 请输入格式正确的手机号码</p>
	<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="mobileError1"><i
	class="ui-icon ui-icon-error"></i> 请输入联系人手机或公司固定电话</p>
</div>
<!--  <div class="ui-form-item"><label for="" class="ui-form-label">
公司联系人电话： </label> <input type="text"
	tabindex="6" class="ui-input fn-w280" maxlength="16"
	name="merchantInfo.phone" id="phone" value="${merchantInfo.phone}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="phoneError"><i
	class="ui-icon ui-icon-error"></i> 请输入格式正确的手机号码</p>
</div>-->
<div class="ui-form-item"><label for="" class="ui-form-label">
联系人电子邮箱： </label> <input type="text"
	tabindex="6" class="ui-input fn-w280" maxlength="45"
	name="merchantInfo.contactsEmail" id="contactsEmail" value="${merchantInfo.contactsEmail}" />
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
	style="display: none;" id="contactsEmailError"><i
	class="ui-icon ui-icon-error"></i> 请输入格式正确的联系邮箱</p>
</div>

<div class="ui-form-item"><label for="" class="ui-form-label">
<!-- 商家所有有权限类目 -->
<input type="hidden" id="allCategoryTreeData" value="<s:property value="#request.allSupplierCategorysJson"/>"/>
<!-- 商家已选类目 -->
<input type="hidden" id="categoryTreeData" value="<s:property value="#request.supplierCategorysJson"/>"/>
<div style="display:none">
	<ul id="userHideTree" class="ztree" ></ul>
</div>
<input type="hidden" value="" name="supplierCategorys" id="supplierCategorys">
<span class="ui-form-required">*</span>选择经营类目： </label> 
<button class="btn thickbox j_shopCategory">请选择</button>
<p class="ui-form-tiptext ui-tiptext-error ui-form-inline fn-ml10"
   style="display: none;" id="categorysNull">
	<i class="ui-icon ui-icon-error"></i> 请选择经营类目</p>

</div>
	<div class="ui-form-item">
		<label for="" class="ui-form-label">
		</label>
		<table border="0" cellpadding="0" cellspacing="0" class="table table-bordered" style="width: 750px; margin-top: 0;">
			<thead>
			<tr class="tablesbg">
				<th colspan="2" class="shoptL">已选择经营类目：</th>
			</tr>
			</thead>
			<tbody id="categoryData">
			</tbody>
		</table>
	</div>
<input type="hidden" value="2" name="suppliersInfo.supplierType"/>
</div>

    <div class="ui-form-item uipleft">
        <a href="javascript:void(0);" class="ui-button ui-button-default ui-button-lg" id="ui-button-lg">上一步</a>
        <a href="javascript:void(0);" class="ui-button ui-button-success ui-button-lg fn-mr10" id="ui-buttonTwo">提交</a>
    </div>
</s:form>
</div>

<form action="goApplyAgreement.action" method="post" id="frm1">
	<input type="hidden" name="showUserinfo.loginId" value="${showUserinfo.loginId}"/>
</form>
<form action="goApplySupplierThree.action" method="post" id="gotoThreeFrm" namespace='/app'>
    <input type="hidden" value="${showUserinfo.loginId}" id="loginId" name="showUserinfo.loginId">
</form>