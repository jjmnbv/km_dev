<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新供应商</title>

<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/supplier/supplierAuditUpdate.js"></script>
<script type="text/javascript" src="/etc/js/supplier/pccs.js"></script>
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript" src="/etc/js/supplier/jquery.form.js"></script>


<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>

<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<style type="text/css">
/**输入框错误提示**/
label.error {
	background: url(/etc/images/li_err.gif) no-repeat;
	font: 12px/1 verdana, simsun, sans-serif;
	margin: 0;
	padding-left: 20px;
	height: 20px;
	line-height: 20px;
	margin-left: 10px;
}

label.checked {
	background: url(/etc/images/li_ok.gif) no-repeat;
	font: 12px/1 verdana, simsun, sans-serif;
	margin: 0;
	padding-left: 20px;
	height: 20px;
	line-height: 20px;
}
</style>

<script>
	
</script>


</head>


<body>

	<!-- 导航栏 -->
	<s:set name="parent_name" value="'供应商审核管理'" scope="request" />
	<s:set name="name" value="'供应商审核管理'" scope="request" />
	<s:set name="son_name" value="'供应商修改'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

	<s:form action="supplierUpdateNoPass.action" method="POST"
		id="frm" name="frm" enctype="multipart/form-data" namespace='/app'>
		<!-- 数据编辑区域 -->
		<input type="hidden" name="longinId" value="<s:property value="merchantOrSupplier.loginId"/>"></input>
		<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<!-- error message -->
			<s:if test="rtnMessage != null">
				<tr>
					<td colspan="2" align="center"><font color="red"><s:property
								value='rtnMessage' /> </font>
					</td>
				</tr>
			</s:if>
			<tr>
				<th colspan="2" align="left" class="edit_title">基本信息</th>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"  style="width:200px">审核不通过原因：</th>
				<td><s:property value="suppliersInfo.describe"></s:property></td>
			</tr>
			<!--<tr>
				<th align="right" class="eidt_rowTitle" style="width:200px"><font color="red">*</font>供应商类型</th>
				<td>
				<s:if test="suppliersInfo.supplierType == 4">
				<input type="hidden" value="4" id="suppliersTypeId" name="suppliersInfo.supplierType"></input>
				康美中药城
				</s:if>
				<s:else>
				<s:select list="#{2:'入驻',3:'代销'}" name="suppliersInfo.supplierType" headerKey=""  id="suppliersTypeId" headerValue="--请选择--"></s:select>
				<%--  <s:select list="#request.SuppliersTypeMap" id="suppliersTypeId" name="suppliersInfo.supplierType" headerKey="" headerValue="--请选择类型--" ></s:select> --%>
				</s:else>
				</td>
			</tr>-->
			<tr>
				<th align="right" class="eidt_rowTitle">
					申请类型：</th>
				<td>
				<input type="hidden" id="suppliersTypeId" name="suppliersInfo.supplierType" value="${suppliersInfo.supplierType}"></input>
				<s:if test="suppliersInfo.supplierType == 1">自营</s:if>
				<s:if test="suppliersInfo.supplierType == 2">入驻</s:if>
				<s:if test="suppliersInfo.supplierType == 3">代销</s:if>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>公司名称：</th>
				<td><input class="input_style" id="supplierName" size="30"
					name="merchantOrSupplier.corporateName" type="text" maxlength="40"
					value="<s:property value="merchantOrSupplier.corporateName"  />" />
					<label class="error" generated="true" style="display: none;" id="corporateNameNull">名称不能为空</label>
					<label class="error" generated="true" style="display: none;" id="corporateNameErr">此公司名称已经存在</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>公司显示名称：</th>
				<td><input class="input_style" id="companyShowName" size="30"
					name="suppliersInfo.companyShowName" type="text" maxlength="40"
					value="<s:property value="suppliersInfo.companyShowName"  />" />
					<label class="error" generated="true" style="display: none;" id="companyShowNameNull">名称不能为空</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>地址：</th>
				<td>
				 <select name="merchantOrSupplier.province" id="province" data-value="<s:property value="merchantOrSupplier.province"/>">
				
            </select>
            <select name="merchantOrSupplier.city" id="city" data-value="<s:property value="merchantOrSupplier.city"/>">
            </select>
                <select name="merchantOrSupplier.area" id="area" data-value="<s:property value="merchantOrSupplier.area"/>">
                </select>
				<input class="input_style" id="supplierLocation" size="30"
					name="merchantOrSupplier.corporateLocation" type="text" maxlength="40"
					value="<s:property value="merchantOrSupplier.corporateLocation" />" />
					<label class="error" generated="true" style="display: none;" id="corporateLocationNull">地址不能为空</label>
					
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>组织机构代码：</th>
				<td><input class="input_style" name="merchantOrSupplier.organizationCode"
					type="text" id="supplierOrganizationCode" size="30" maxlength="30"
					value="<s:property value="merchantOrSupplier.organizationCode"  />" />
					<label class="error" generated="true" style="display: none;" id="organizationCodeNull">组织机构代码不能为空</label>
					<label class="error" generated="true" style="display: none;" id="organizationCodeErr">请输入00000000-0格式的代码</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>组织机构代码电子版：</th>
				<td>
				<input type="file" name="organizationFile" id="organizationFile" onchange="organizationFileOnchange();"></input>
				<s:if test="merchantOrSupplier.organizationUrl != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.organizationUrl'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.organizationUrl'/>" style="width: 200px;height: 80px;"></img> </a></s:if>
				<input type="hidden" value="" name="organizationFileName" id="organizationFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="organizationFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>营业执照注册号：</th>
				<td><input class="input_style" name="merchantOrSupplier.businessLicenceRegister"
					type="text" size="30" id="supplierRegister" maxlength="30"
					value="<s:property value="merchantOrSupplier.businessLicenceRegister"  />" />
					<label class="error" generated="true" style="display: none;" id="businessLicenceRegisterNull">营业执照注册号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="businessLicenceRegisterErr">请输入15位数字的注册号</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>营业执照电子版：</th>
				<td>
				<input type="file" name="businessFile" id="businessFile" onchange="businessFileOnchange();"></input>
				<s:if test="merchantOrSupplier.uploadBusinessLicencePictur != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.uploadBusinessLicencePictur'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.uploadBusinessLicencePictur'/>" style="width: 200px;height: 80px;"></img> </a></s:if>
				<input type="hidden" value="" name="businessFileName" id="businessFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="businessFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">营业执照有效期：</th>
				<td><input class="" CssClass="Wdate" onFocus="WdatePicker()" id="staTime" runat="server"
					name="merchantOrSupplier.blinceStartdate" readonly="readonly" type="text" size="10" maxlength ="15"
					value="<s:date name='%{merchantOrSupplier.blinceStartdate}' format='yyyy-MM-dd'/>" />到
					<input class="" CssClass="Wdate" onFocus="WdatePicker()" id="endTime" runat="server"
					name="merchantOrSupplier.blinceEnddate" readonly="readonly" type="text" size="10" maxlength ="15"
					value="<s:date name='%{merchantOrSupplier.blinceEnddate}' format='yyyy-MM-dd'/>" />
					<label class="error" generated="true" style="display: none;" id="validTimeErr">开始日期不能大于结束日期</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">法定经营范围：</th>
				<td><input class="required input_style" id="businessScope"
					name="merchantOrSupplier.businessScope" type="text" size="30" maxlength ="40"
					value="<s:property value="merchantOrSupplier.businessScope" />" />
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>税务登记证号：</th>
				<td><input class="required input_style" id="taxRegistrationCno"
					name="merchantOrSupplier.taxRegistrationCno" type="text" size="30" maxlength ="30"
					value="<s:property value="merchantOrSupplier.taxRegistrationCno" />" />
					<label class="error" generated="true" style="display: none;" id="taxRegistrationCnoNull">税务登记证号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="taxRegistrationCnoErr">税务登记证号由15数字组成</label>
					
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>税务登记证电子版：</th>
				<td>
			   <input type="file" name="taxRegistratFile" id="taxRegistratFileId" onchange="taxRegistratFileOnchange();"></input>
				<s:if test="merchantOrSupplier.taxRegCertificateCopy != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.taxRegCertificateCopy'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.taxRegCertificateCopy'/>" style="width: 200px;height: 80px;"></img> </a></s:if>
				<input type="hidden" value="" name="taxRegistratFileName" id="taxRegistratFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="taxRegistratFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">纳税人识别号：</th>
				<td><input class="required input_style" id="taxpayerIdnumber"
					name="merchantOrSupplier.taxpayerIdnumber" type="text" size="30" maxlength ="25"
					value="<s:property value="merchantOrSupplier.taxpayerIdnumber"  />" />
					</td>
			</tr>
			
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>注册资金：</th>
				<td>
				<input class="required input_style" id="registerBankroll"
					name="merchantOrSupplier.registerBankroll" type="text" size="30" maxlength ="10"
					value="<s:property value="merchantOrSupplier.registerBankroll"  />" />万
						<label class="error" generated="true" style="display: none;" id="registerBankrollNull">请选择注册资金</label>
						<label class="error" generated="true" style="display: none;" id="registerBankrollErr">请输入整数的注册资金</label>	
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>开户银行：</th>
				<td><input class="required input_style" id="bankOfDeposit"
					name="merchantOrSupplier.bankOfDeposit" type="text" size="30" maxlength ="40"
					value="<s:property value="merchantOrSupplier.bankOfDeposit"  />" />
					<label class="error" generated="true" style="display: none;" id="bankOfDepositNull">开户银行不能为空</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>账户名：</th>
				<td><input class="required input_style" id="bankAccountName"
					name="merchantOrSupplier.bankAccountName" type="text" size="30" maxlength ="40"
					value="<s:property value="merchantOrSupplier.bankAccountName"  />" />
					<label class="error" generated="true" style="display: none;" id="bankAccountNameNull">账户名不能为空</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>银行账号：</th>
				<td><input class="required input_style" id="companyAccount"
					name="merchantOrSupplier.companyAccount" type="text" size="30" maxlength ="30"
					value="<s:property value="merchantOrSupplier.companyAccount"  />" />
					<label class="error" generated="true" style="display: none;" id="companyAccountNull">银行账号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="companyAccountErr">银行账号由数字组成</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">公司联系人：</th>
				<td><input class="required input_style" id="supplierContactsNames"
					name="merchantOrSupplier.contactsName" type="text" size="30" maxlength ="15"
					value="<s:property value="merchantOrSupplier.contactsName"  />" />
					<label class="error" generated="true" style="display: none;" id="contactsNameNull">联系人不能为空</label>
					</td>
			</tr>

			<tr>
				<th align="right" class="eidt_rowTitle">
					公司联系人手机：</th>
				<td><input class="input_style" name="merchantOrSupplier.mobile"
					type="text" size="30" id="supplierMobile" maxlength="13"
					value="<s:property value='merchantOrSupplier.mobile'     />" />
					<label class="error" generated="true" style="display: none;" id="mobileNull">联系电话不能为空</label>
					<label class="error" generated="true" style="display: none;" id="mobileErr">请输入格式正确的联系电话</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">
					公司固定电话：</th>
				<td><input class="input_style" name="merchantOrSupplier.fixedPhone"
					type="text" size="30" id="supplierFixedPhone" maxlength="24"
					value="<s:property value='merchantOrSupplier.fixedPhone'     />" />
					<label class="error" generated="true" style="display: none;" id="fixedPhoneNull">固定电话和联系人手机必填一种</label>
					<label class="error" generated="true" style="display: none;" id="fixedPhoneErr">请输入011,0111-11111格式固定电话</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">
					联系人邮箱：</th>
				<td><input class="input_style" name="merchantOrSupplier.contactsEmail"
					type="text" size="30" id="supplierContactsEmail" maxlength="30"
					value="<s:property value='merchantOrSupplier.contactsEmail'     />" />
					<label class="error" generated="true" style="display: none;" id="contactsEmailNull">联系邮箱不能为空</label>
					<label class="error" generated="true" style="display: none;" id="contactsEmailErr">请输入格式正确的联系邮箱</label>
					</td>
			</tr>
		<!-- 	<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>销售商品描述：</th>
				<td><textarea rows="" cols="" id="supplierDescribe"  name="suppliersInfo.saleProductDescribe" style="width:600px;height:80px"><s:property value="suppliersInfo.saleProductDescribe"  /></textarea>
				<label class="error" generated="true" style="display: none;" id="saleProductDescribeNull">商品描述不能为空</label>
				</td>
			</tr> -->
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>
					分配经营类目(比例为百分比)：</th>
				<td class="Supplierslist">
				<input type="hidden" name="categoryValueList" id="categoryValue" value="">
				<input type="hidden" name="categoryValueList1" id="categoryValue1" value="">
				<input type="hidden" value="" name="CommissionRatios" id="CommissionRatios">
				<input type="hidden" value="" name="sacIds" id="sacIds">
				<s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#hisList1.categoryParentId == 0">
	                    				<input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
	                    				<input type="hidden" value="${categoryId}"  class="hidSacId"></input>
	                    				<s:if test="#hisList1.sacId == null">
                    						<input type="checkbox"  id="box${categoryId}" value="${categoryId}" onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                   						</s:if>
                   						<s:else>
                   							<input type="checkbox"  id="box${categoryId}" value="${categoryId}" checked="checked" onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                   						</s:else>
                   						${categoryName}
                    						<li style="list-style-type:none;">
                    							<s:iterator value="categorysList" id="hisList2">
                    								<s:if test="#hisList2.categoryParentId != 0">
                    									<s:if test="#hisList1.categoryId == #hisList2.categoryParentId">
						                    				<input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
						                    				<input type="hidden" value="${categoryId}"  class="hidSacId"></input>
						                    			 	<s:if test="#hisList2.sacId == null">
						                    			 		<input type="checkbox"  id="box${categoryId}" value="${categoryId}" onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
						                    			 		${categoryName}
						                    			 		<input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" disabled="true" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
						                    			 	</s:if>
						                    			 	<s:else>
		                    									<input type="checkbox" id="box${categoryId}" value="${categoryId}" checked="checked" onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
						                    					${categoryName}
					                    				 		<input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
                    										</s:else>
                    									</s:if>
                    								</s:if>
                    							</s:iterator>
                    			 			</li>
                   							</br>
                    				</s:if>
                    			 </s:iterator>
                    			 </br>
				
       			 
       			 <!-- 
			   <s:iterator value="#request.supplierCategorysMapAll" status="status" id="infoList1">
                    	<s:set name="flag" value="0"  >  </s:set> 
                    			<s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#infoList1.key == #hisList1.categoryId">
                    				 <s:set name="flag"  value="1" > </s:set>
                    				 <input type="hidden" value="${sacId}" id="hid${key}"></input>
                    				 <input type="hidden" value="${categoryId}"  class="hidSacId"></input>
                    			  </s:if>
                    			 </s:iterator>
                    			 <s:if test="#flag==0">
                    			 	<li style="float: left;list-style-type:none;width: 200px;">
                    			 	<input type="checkbox" id="box${key}" value="${key}" name="categorys" onclick="checkedInput('${key}')" class="ui-form-checkbox"/>${value}
                    			 	 <input type="text" name="inputRat" disabled="true" class="inputRat" style="width: 50px;" id="inputs${key}" maxlength="5" title="请输入佣金比例"></input>%</li>
                    			 	 <input type="hidden" value=""  id="hid${key}"></input>
                    			  </s:if> 
                    			 <s:elseif test="#flag==1"  >
                    			 <s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#infoList1.key == #hisList1.categoryId">
                    				 <s:set name="flag"  value="1" > </s:set>
                    				<li style="float: left;list-style-type:none;width: 200px;"><input type="checkbox" id="box${key}" value="${key}" checked="checked" onclick="checkedInput('${key}')" name="categorys" class="ui-form-checkbox"/>${categoryName}
                    			 <input type="text" name="inputRat" class="inputRat" style="width: 50px;" id="inputs${key}" maxlength="5" value='<s:property value="commissionRatio" />' title="请输入佣金比例"></input>%
                    			<input type="hidden" value=""/>
                    			 </li>
                    			  </s:if>	
                    			 </s:iterator>
                    			  </s:elseif>
                    	</s:iterator>
                    	 -->
				</td>
			</tr>
			<!--<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>分配仓库：</th>
				<td>
				-->
				<input type="hidden" value="${suppliersWarehouse.supWarehouseId}" name="suppliersWarehouse.supWarehouseId"></input>
				<input type="hidden" value="${suppliersWarehouse.warehouseId}" name="suppliersWarehouse.warehouseId"></input>
				<!--<s:select list="#request.warehouseInfoStatusMap" value="suppliersWarehouse.warehouseId" id="warehouseId" name="suppliersWarehouse.warehouseId" headerKey="" headerValue="--请选择仓库--"></s:select>
				</td>
			</tr>
			--><tr>
				<th align="right" class="eidt_rowTitle" style="width: 170px;">资质文件：</th>
				<td><input type="hidden" value="${imagePath}" id="imagePath">
				<ul id="addFile" >
				
				<s:iterator value="suppliersCertificateList">
				<li id="hrefId${scId}" style="float: left;list-style-type:none;width: 200px;height:220px;">
					&nbsp;&nbsp;<a href="${imagePath}${filePath}" title="单击放大" target="_blank" id="hrefId${scId}"><img width="190px" height="190px" src="${imagePath}${filePath}">
					${fileName }</a>&nbsp;&nbsp; <a href="javascript:void(0);" id="del${scId}" onclick="deleteFiles(${scId})">删除</a>
					</li>
				</s:iterator>
				</ul>
				</td>
			</tr>
		</table>
		<br>
		<br>

	<input type="hidden"  name="merchantOrSupplier.merchantId" id="merchantIdVal" value="<s:property value='merchantOrSupplier.merchantId' />"   />
	<input type="hidden"  name="suppliersInfo.supplierId" id="supplierIdUp"  value="<s:property value='suppliersInfo.supplierId' />"   />
    <input type="hidden"  name="pageNum"  value="<s:property value='pageNum' />"   />
      <input type="hidden"  name="selectSuppliersInfo.corporateName"  value="<s:property value='selectSuppliersInfo.corporateName' />"   />
     <input type="hidden"  name="selectSuppliersInfo.corporateLocation"  value="<s:property value='selectSuppliersInfo.corporateLocation' />"   />
    <input type="hidden"  name="selectSuppliersInfo.contactsName"  value="<s:property value='selectSuppliersInfo.contactsName' />"   />
    <input type="hidden"  name="selectSuppliersInfo.status"  value="<s:property value='selectSuppliersInfo.status' />"   />
     <input type="hidden"  name="selectSuppliersInfo.enterpriseStatus"  value="<s:property value='selectSuppliersInfo.enterpriseStatus' />"   />
      <input type="hidden"  name="selectSuppliersInfo.supplierType"  value="<s:property value='selectSuppliersInfo.supplierType' />"   />
	</s:form>
	<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>上传纸质文件：</th>
				<td>
				<s:form action="uploadCeriticate.action" namespace="app" name="frm1" method="post" enctype="multipart/form-data" id="frm1" class="ui-form ui-form-vertical fn-t20" >
			<input type="hidden" name="supplierId" value="${suppliersInfo.supplierId}">
				<div class="ui-form-item">
					<img id="loading_img" src="" style="display: none;" />
		            <label for="" class="ui-form-label">
		                <span class="ui-form-required">*</span>请上传您的营业执照（副本）、组织机构代码证扫描件，以及您所供应商品的相关授权、资质证明文件的扫描件。
		            </label>
		            <input type="text" tabindex="9"  id="fileName" name="realName" class="ui-input fn-w280 fn-mr10" placeholder="请输入文件名称" />
		            <input tabindex="10" id="upFile" contenteditable="false" class="fn-w180 fn-mr20" name="file" type="file" />
		            <span id="error_message" class="fn-red"></span>
		            <input type="button" value="上传文件" onclick="submitForms();"/><span><s:fielderror cssStyle="color: red"/></span>
		        </div>
			</s:form>
				</td>
			</tr>
			</table>
<!-- 底部 按钮条 -->
		<table width="98%" align="center" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
			<tr>
				<td align="center"><INPUT class="saveBtn" TYPE="button" onclick="goSubmit()"
					value=""> &nbsp;&nbsp; <input type="button" class="backBtn"
					onclick="gotoList()" />
				<td width="20%" align="center"></td>
			</tr>
		</table>
	<script type="text/javascript">
	 var province = $("#province").attr('data-value');
     var city = $("#city").attr('data-value');
     var area = $("#area").attr('data-value');
     if (province != "" && city != "" && area!="") {
        addressInit('province', 'city', 'area', province, city, area);
     } else {
         addressInit('province', 'city', 'area');
     }
	//addressInit('province', 'city', 'area',$("#province").val(),$("#city").val(),$("#area").val());
	var ckTypes=false;
	var ckTypes1=false;
	var ckSupplierName=false;
	var ckSupplierLocation=false;
	var ckSupplierOrganizationCode=false;
	var ckSupplierRegister=false;
	var ckSupplierContactsName=false;
	var ckSupplierDescribe=false;
	var ckWarehouseId=false;
	var ckFixedPhone=false;
	var ckContactsEmail=false;
	var ckMobile=false;
	var ckSuppliersType=false;
	var ckTaxRegistrationCno=false;//税务登记证号
	var ckBankOfDeposit=false;//开户银行
	var ckBankAccountName=false;//账号名
	var ckCompanyAccount=true;//银行账号
	var ckCompanyShowName=false;
		function gotoList() {
			document.forms[0].action = "supplierAuditList.action";
			document.forms[0].submit();
		
		}

		function goSubmit(){
			 //var organizationFilePath=$("#organizationFile");
	    	 // var businessFilePath=$("#businessFile");
	    	//  var taxRegistratFileIdPath=$("#taxRegistratFileId");
	    	//  if(organizationFilePath.val() != ""){
	      			// if(checkFile1(organizationFilePath)==false){
	      	    		//  $("#organizationFileErr").show();
	      	    		 // return;
	      	    	  //}
	        	//  }
	      		//if(businessFilePath.val() != ""){
	      			// if(checkFile1(businessFilePath)==false){
	      	        	//  $("#businessFileErr").show();
	      	    		//  return;
	      	    	 // }
	      		//}
	      		//if(taxRegistratFileIdPath.val() != ""){
	      			// if(checkFile1(taxRegistratFileIdPath)==false){
	      	         	//  $("#taxRegistratFileErr").show();
	      	     		 // return;
	      	     //	  }
	      		//}
				
	      		var sDate = new Date(document.getElementById("staTime").value.replace(/\-/g, "\/"));
	            var eDate = new Date(document.getElementById("endTime").value.replace(/\-/g, "\/"));
	            if(sDate > eDate){
	            	$("#validTimeErr").show();
	            	//$("#validTimeNull").hide();
	            	return;
	            }else{
	            	$("#validTimeErr").hide();
	            }
	      		
	    	//	var registerBankrollVal=$("#registerBankroll").val();//检查是否选择了注册资金
	    	//	if("" == registerBankrollVal){
	    		//	 $("#registerBankrollNull").show();
	    		//	 $("#registerBankrollErr").hide();
	    		//	 return;
	    	//	}else{
	    		//	$("#registerBankrollNull").hide();
	    		//	var regBus=/^\d+$/;
	    		//	if(regBus.test(registerBankrollVal) == false){
	    			//	$("#registerBankrollErr").show();
	    			//	return;
		    		//	}else{
		    			//	$("#registerBankrollErr").hide();
			    		//	}
	    		//}
	    		
	    	//	taxRegistrationCno();//税务登记证号
	    	//	bankOfDeposit();//开户银行
	    	//	bankAccountName();//开户名称
	    		//companyAccount();//银行账号
			 suppliersTypeCk();//验证是否选择了供应商类型
			 if(ckSuppliersType == false){
					return;
				 }
			funSupplierName();//公司名称
			if(ckSupplierName==true){
				funCompanyShowName();
				if(ckCompanyShowName == true){//公司显示地址
				//funSupplierLocation();//地址
				//if(ckSupplierLocation == true){
					//funSupplierOrganizationCode();//结构组织代码
					//if(ckSupplierOrganizationCode == true){
						//funSupplierRegister();//注册码
						//if(ckSupplierRegister == true){
							//funSupplierContactsName();//联系人
							//if(ckSupplierContactsName == true){
								//mobile();
								//if(ckMobile == true){
									//fixedPhone();//验证固定电话
										//if(ckFixedPhone == true){
											//contactsEmail();//验证联系邮箱
												//if(ckContactsEmail == true){
								//funSupplierDescribe();//商品描述
								//if(ckSupplierDescribe == true){
								
								//if("2" == $("#suppliersTypeId").val()){//只有是入驻才能分配类目
									ckboxCretegory();//验证类目
									if(ckTypes1 == true){
										var cate = document.getElementsByName("categorys");
								    	var strClass="";//保存class名
										 for(var i=0;i<cate.length;i++){
											 strClass = (cate[i].className).split(' ');
								    			if(strClass[1] == 'class0'){
								    				 isTrue = false;
									    		}else{
							    				 	isTrue = "" == $("#inputs"+cate[i].value).val();
								    			}
								       		  if(cate[i].checked==true && isTrue){
								       			 alert("请输入佣金比例！");
									       		 return;
								       		 }else{
										       	if(strClass[1] != 'class0'){
									       			var ckbili=$("#inputs"+cate[i].value).val();
									       			//var reg=/^0\.\d*[1-9]\d*$/;
									       		    var reg=/^((\d|[123456789]\d)(\.\d+)?)$/;
									       			if(cate[i].checked==true && (reg.test(ckbili)== false || ckbili.substring(ckbili.indexOf(".")+1,ckbili.length).length > 2 || ckbili==0)){
										       			alert("请输入大于0小于100的佣金比例,小数最多为两位");
										       			return;
										       			}
										       		 }
									       		 }
									       	}
										
										// if(ckSuppliersType == true){
										//ckSupplierType();//验证销售渠道
										//if(ckTypes == true){
											
											//}
										// }
										//}
									//}商品描述
												//}联系邮箱
										//}//验证固定电话
								//}联系电话号码
								
								//}联系人
							//}////注册码
						//}////结构组织代码
					//}//地址
											funckWarehouseId();
											if(ckWarehouseId == true){
												$("#frm").submit();
												}			
				}
				}
		}
			//}
								
			}
		 function ckSupplierType() {//检测销售类型是否为空
		    	var types = document.getElementsByName("supplierType");
		    	var str="";//保存要新增的渠道类型名称
		    		 for(var i=0;i<types.length;i++){
		       		  if(types[i].checked==true){
		       				str+=types[i].value+",";
		       			    ckTypes=true;
		       		  }
		       		 }
		       	 if(ckTypes == false){
		       		 alert("请选择渠道！");
		      		  		return;
		       	 }else{
		       		 $("#channeTypes").val(str);
		       	 }
		    	
		    }
		 function deleteFile(codId,suppId) {
	    	 $.ajax({
	             url: '/deleteFile.action',
	             async:false,
	             data: 'scId='+codId+'&suppliersInfo.supplierId='+suppId,
	             success: function(info) {
	                if('0' == info){
	                	$("#imgs"+codId).remove();
	                }
	             }
	         });
		}
		 function ckboxCretegory(){
				var ckTypesVal="0";
				var ckTypesVa2="0";
				var cate = document.getElementsByName("categorys");
		    	var str="";//保存要新增的渠道类型名称
		    	var bili="";//保存佣金比例
		    	var sacIdStr="";//id
		    	var str1="";
		    	if("2" == $("#suppliersTypeId").val()){
		    		 for(var i=0;i<cate.length;i++){
		       		  if(cate[i].checked==true){
		       				str+=cate[i].value+",";
		       				bili+=$("#inputs"+cate[i].value).val()+",";
		       				sacIdStr+=$("#hid"+cate[i].value).val()+",";
		       				ckTypesVal="1";
		       				ckTypes1=true;
		       		  }
		       		 }
		 }
		       	if("1" == $("#suppliersTypeId").val() || "3" == $("#suppliersTypeId").val() || "4" == $("#suppliersTypeId").val()){
		       	 for(var i=0;i<cate.length;i++){
		       		  if(cate[i].checked==true){
		       				str+=cate[i].value+",";
		       				bili+=$("#inputs"+cate[i].value).val()+",";
		       				sacIdStr+=$("#hid"+cate[i].value).val()+",";
		       				ckTypesVal="1";
		       				ckTypesVa2="1";
		       				ckTypes1=true;
		       		  }
		       		 }
			       	if(ckTypesVa2 == "0"){
			       	 	var hidSacIds=$(".hidSacId");
				       	for(var i=0;i<hidSacIds.length;i++){
				       		str1+=hidSacIds[i].value+",";
					       	}
			       		ckTypes1=true;
				       	}
			       	}
		       	 if(ckTypesVal == "0" && "2" == $("#suppliersTypeId").val()){
		       		 alert("请选择类目！");
		       		ckTypes1=false;
		      		  		return;
		       	 }else{
							$("#categoryValue").val(str);
					       	$("#CommissionRatios").val(bili);
					       	$("#sacIds").val(sacIdStr);
					       	$("#categoryValue1").val(str1);
		       	
		       	 }
				}

			function funSupplierName(){
				var supplierNameVal=$("#supplierName").val();
				var merchantIdVal=$("#merchantIdVal").val();
				if("" == supplierNameVal){
					$("#corporateNameNull").show();
					$("#corporateNameErr").hide();
					ckSupplierName=false;
					$("#supplierName").focus();
					return;
					}else{
						$("#corporateNameNull").hide();
						 $.ajax({
							 type : "POST",
		                     url: 'ckCorporateName.action?_t='+new Date().getTime(),
		                     async:false,
		                     data: 'corporateName='+supplierNameVal+'&merchantId='+merchantIdVal,
		                     success: function(info) {
		                        if('0' == info){//没有存在同样的公司名称
		                        	$("#corporateNameErr").hide();
		                       	ckSupplierName=true;
		                        }else{//公司名称有一样的
		                        	ckSupplierName=false;
		                        	$("#supplierName").focus();
		                        	$("#corporateNameErr").show();
		                        }
		                     }
		                 });
						
						}
				}
			
			function funCompanyShowName(){
				var companyShowNameVal=$("#companyShowName").val();
				if("" == companyShowNameVal){
					$("#companyShowNameNull").show();
					ckCompanyShowName=false;
					$("#companyShowName").focus();
					return;
					}else{
						$("#companyShowNameNull").hide();
						ckCompanyShowName=true;
						}
				}
			function funSupplierLocation(){
				var supplierLocationObj=$("#supplierLocation");
				if("" ==  supplierLocationObj.val()){
					$("#corporateLocationNull").show();
					ckSupplierLocation=false;
					supplierLocationObj.focus();
					return;
					}else{
						$("#corporateLocationNull").hide();
						ckSupplierLocation=true;
						}
				}
			function funSupplierOrganizationCode(){
				var supplierOrganizationCodeObj=$("#supplierOrganizationCode");
				if("" == supplierOrganizationCodeObj.val()){
					$("#organizationCodeNull").show();
					ckSupplierOrganizationCode=false;
					supplierOrganizationCodeObj.focus();
					return;
					}else{
						$("#organizationCodeNull").hide();
						var reg=/^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{1}$/;
						if(reg.test(supplierOrganizationCodeObj.val()) == false){
							$("#organizationCodeErr").show();
							ckSupplierOrganizationCode=false;
							supplierOrganizationCodeObj.focus();
							return;
							}else{
								$("#organizationCodeErr").hide();
								ckSupplierOrganizationCode=true;
								}
						}
				}
			function funSupplierRegister(){
				var supplierRegisterObj=$("#supplierRegister");
				if("" == supplierRegisterObj.val()){
					$("#businessLicenceRegisterNull").show();
					ckSupplierRegister=false;
					supplierRegisterObj.focus();
					return;
					}else{
						$("#businessLicenceRegisterNull").hide();
						var regBus=/^[0-9]{15}$/;
						if(regBus.test(supplierRegisterObj.val())== false){
							$("#businessLicenceRegisterErr").show();
							supplierRegisterObj.focus();
							ckSupplierRegister=false;
							return;
							}else{
								$("#businessLicenceRegisterErr").hide();
								ckSupplierRegister=true;
								}
						}
				}

			function funSupplierContactsName(){
				var supplierContactsNameObj=$("#supplierContactsName");
				if("" == supplierContactsNameObj.val()){
					$("#contactsNameNull").show();
					supplierContactsNameObj.focus();
					ckSupplierContactsName=false;
					return;
					}else{
						$("#contactsNameNull").hide();
						ckSupplierContactsName=true;
						}
				}
			function funSupplierDescribe(){
				var supplierDescribeObj=$("#supplierDescribe");
				if("" == supplierDescribeObj.val()){
					$("#saleProductDescribeNull").show();
					supplierDescribeObj.focus();
					ckSupplierDescribe=false;
					return;
					}else{
						$("#saleProductDescribeNull").hide();
						ckSupplierDescribe=true;
						}
				}
			function funckWarehouseId(){
				var ckWarehouseIdval=$("#warehouseId").val();
				if("" == ckWarehouseIdval){
					alert("请选择仓库！");
					ckWarehouseId=false;
					return;
					}else{
						ckWarehouseId=true;
						}
				}

			function checkedInput(val){
				var inpuObj = document.getElementById("box"+val);
				var strClass = (inpuObj.className).split(' ');
				var nodeId = '';//父节点id
				if(strClass[1]=='class0'){//父类 点击   同级下所有联动
					if(inpuObj.checked){
						$(".class"+val).attr("checked", true);
						$(":input[name='inputRat"+val+"']").attr("disabled",false);
					}else{
						$(".class"+val).attr("checked", false);
						$(":input[name='inputRat"+val+"']").attr("disabled",true);
					}
				}else{//子类点击  判断自身将要勾选 检查父类是否选择，未选中则选择  将要取消选中  检查所有子类确定是否取消
					if(inpuObj.checked){//子节未勾选
						nodeId = 'box'+strClass[1].substring(5);
						if(nodeId.checked){//父节点已勾选
						}else{//未勾选就勾选
							$("#"+nodeId).attr("checked", true);
						}
						$("#inputs"+val).attr("disabled",false);
					}else{//子节点已勾选
						nodeId = 'box'+strClass[1].substring(5);
						if ($("."+strClass[1]+":checked").length>0) {//存在勾选不操作
	                    }else {//不存在勾选，去掉父节点勾选
			                $("#"+nodeId).attr("checked", false);
		                }
						$("#inputs"+val).attr("disabled",true);
					}
				}

/*
				if(inpuObj.checked){
					$("#inputs"+val).attr("disabled",false);
				}else{
					$("#inputs"+val).attr("disabled",true);
				}*/
			}
			
			function fixedPhone(){//验证固定电话
		    	var fixedPhoneVal=$("#supplierFixedPhone").val();
		    	var mobile=$("#supplierMobile").val();//联系人手机号码
		    	if("" == fixedPhoneVal && "" == mobile){
			    	$("#fixedPhoneNull").show();
			    	$("#supplierFixedPhone").focus();
			    	ckFixedPhone=false;
		    		return;
		    	}else{
		    		$("#fixedPhoneNull").hide();
			    	if("" != fixedPhoneVal){
			    		var regFix=/^0\d{2,3}-\d{5,9}|-$/;
			    		if(regFix.test(fixedPhoneVal) == false){
			    			$("#fixedPhoneErr").show();
			    			$("#supplierFixedPhone").focus();
			    			ckFixedPhone=false;
			    			return;
			    		}else{
			    			$("#fixedPhoneErr").hide();
			    			ckFixedPhone=true;
			    		}
				    	}
		    		if("" != mobile){
		    			var regMobil=/^1\d{10}$/;
			    		if(regMobil.test(mobile) == false){
			    			$("#mobileErr").show();
			    			$("#supplierMobile").focus();
			    			ckFixedPhone=false;
			    			return;
			    		}else{
			    			$("#mobileErr").hide();
			    			ckFixedPhone=true;
				    		}
			    		}
		    		
		    	}
		    }

			 function contactsEmail(){//联系邮箱
			    	var contactsEmailVal=$("#supplierContactsEmail").val();
			    	if("" == contactsEmailVal){
				    	$("#contactsEmailNull").show();
				    	$("#supplierContactsEmail").focus();
				    	ckContactsEmail=false;
			    		return;
			    	}else{
			    		$("#contactsEmailNull").hide();
			    		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			    		if(myreg.test(contactsEmailVal)== false){
			    			$("#contactsEmailErr").show();
			    			$("#supplierContactsEmail").focus();
			    			ckContactsEmail=false;
			        		return;
			    		}else{
			    			$("#contactsEmailErr").hide();
			    			ckContactsEmail=true;
			    		}
			    	}
			    }

			 function mobile() {//联系电话号码
			    	var mobile=$("#supplierMobile").val();
			    	if(mobile == ""){
				    	$("#mobileNull").show();
				    	$("#supplierMobile").focus();
				    	ckMobile=false;
			    		return;
			    	}else{
			    		$("#mobileNull").hide();
			    		var regMobil=/^1\d{10}$/;
			    		if(regMobil.test(mobile) == false){
			    			$("#mobileErr").show();
			    			$("#supplierMobile").focus();
			    			ckMobile=false;
			    			return;
			    		}else{
			    			$("#mobileErr").hide();
							ckMobile=true;
				    		}
			    	}
				}
				//判断选择供应商类型，如果是入驻就需要填写佣金比例
			//	function ckSuppType(obj) {
					//var suppliersTypeVal=obj.value;
					//if("1" == suppliersTypeVal || "3" == suppliersTypeVal){
					//	var cBoxs=$(".ui-form-checkbox");
					//	var inputS=$(".inputRat");
					//	for(var i=0;i<cBoxs.length;i++){
						//	if(cBoxs[i].checked){
							//	cBoxs[i].checked=false;
								//inputS[i].value="";
								//}
							//}
						//}
				//}
				function suppliersTypeCk() {
					var suppliersTypeVal=$("#suppliersTypeId").val();
					if("" == suppliersTypeVal){
						alert("请选择供应商类型！");
						ckSuppliersType=false;
						return;
						}else{
							ckSuppliersType=true;
							}
				}

				function taxRegistrationCno() {//检查税务登记证号不能为空
			    	var taxRegistrationCnoVal=$("#taxRegistrationCno").val();
			    	if(taxRegistrationCnoVal == ""){
			    		$("#taxRegistrationCnoNull").show();
			    		$("#taxRegistrationCnoErr").hide();
			    		ckTaxRegistrationCno=false;
			    		return;
			    	}else{
			    		$("#taxRegistrationCnoNull").hide();
			    			ckTaxRegistrationCno=true;
			    	}
				}

				 function bankOfDeposit() {//开户银行
						var bankOfDepositVal=$("#bankOfDeposit").val();
						if("" == bankOfDepositVal){
							$("#bankOfDepositNull").show();
							ckBankOfDeposit=false;
						}else{
							$("#bankOfDepositNull").hide();
							ckBankOfDeposit=true;
						}
					}

				  function bankAccountName() {//账户名
						var bankAccountNameVal=$("#bankAccountName").val();
						if("" == bankAccountNameVal){
							$("#bankAccountNameNull").show();
							ckBankAccountName=false;
						}else{
							$("#bankAccountNameNull").hide();
							ckBankAccountName=true;
						}
					}

				  function companyAccount() {//银行账号
				    	var companyAccountVal=$("#companyAccount").val();
				    	if(companyAccountVal == ""){
				    		$("#companyAccountNull").show();
				    		$("#companyAccountErr").hide();
				    		ckCompanyAccount=false;
				    		return;
				    	}else{
				    		$("#companyAccountNull").hide();
				    		var regBus=/^\d+$/;
				    		if(regBus.test(companyAccountVal) == false){
				    			$("#companyAccountErr").show();
				    			ckCompanyAccount=false;
				    			return;
				    		}else{
				    			$("#companyAccountErr").hide();
				    			ckCompanyAccount=true;
				    		}
				    	}
					}

			 $('#supplierName').blur(function () {
				 $("#corporateNameNull").hide();
		        });
			 $('#supplierLocation').blur(function () {
				 $("#corporateLocationNull").hide();
		        });
			 $('#supplierOrganizationCode').blur(function () {
				 $("#organizationCodeNull").hide();
				 $("#organizationCodeErr").hide();
		        });
		     $('#supplierRegister').blur(function () {
				 $("#businessLicenceRegisterNull").hide();
				 $("#businessLicenceRegisterErr").hide();
			        });
		     $('#supplierContactsName').blur(function () {
		    	 $("#contactsNameNull").hide();
				 $("#contactsNameNull").hide();
			        });
		     $('#supplierDescribe').blur(function () {
		    	 $("#saleProductDescribeNull").hide();
		    	 $("#saleProductDescribeErr").hide();
			        });
		     $('#supplierFixedPhone').blur(function () {
		    	 $("#fixedPhoneNull").hide();
		    	 $("#fixedPhoneErr").hide();
			        });
		     $('#supplierContactsEmail').blur(function () {
		    	 $("#contactsEmailNull").hide();
		    	 $("#contactsEmailErr").hide();
			        });
		     $('#supplierMobile').blur(function () {
		    	 $("#mobileNull").hide();
		    	 $("#mobileErr").hide();
			        });
		     /**
		      * 验证文件类型
		      * @returns {boolean}
		      */
		     function checkFile1(obj) {
		         var filepath = obj.val();
		         if(null!=filepath && "" != filepath){
		             var extStart = filepath.lastIndexOf(".");
		             var ext = filepath.substring(extStart, filepath.length).toUpperCase();
		             if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
		             	obj.val("");
		                 return false;
		             }else{
		                 return true;
		             }
		         }else {
		             return false;
		         }
		     }
	</script>



</body>

</HTML>


