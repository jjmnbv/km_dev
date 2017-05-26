<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加供应商</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/supplier/supplierAuditAddTwo.js"></script>
<script type="text/javascript" src="/etc/js/supplier/pccs.js"></script>
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>

</head>
<body>

<s:set name="parent_name" value="'供应商审核管理'" scope="request"/>
<s:set name="name" value="'添加供应商'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="supplierAdd.action" method="post" id="frm" enctype="multipart/form-data" namespace='/app'>

	<!-- 数据编辑区域 -->
	<table width="95%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		<tr>
			<th colspan="2" align="left" class="edit_title">商户基本资料和联系信息</th>
		</tr>
		<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>供应商类型</th>
				<td>
				<s:select list="#{2:'入驻',3:'代销'}" name="suppliersInfoAdd.supplierType" headerKey=""  id="suppliersTypeId" headerValue="--请选择--" onchange="ckSuppType()"></s:select>
				<%--   <s:select list="#request.SuppliersTypeMap" id="suppliersTypeId" name="suppliersInfoAdd.supplierType" headerKey="" headerValue="--请选择类型--" onchange="ckSuppType()"></s:select>--%>
				 <label class="error" generated="true" style="display: none;" id="supplierTypesNull">请选择类型</label>
				</td>
			</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>公司名称（全称）：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.corporateName" id="corporateNameId" maxlength="40">
			<label class="error" generated="true" style="display: none;" id="corporateNameNull">名称不能为空</label>
			<label class="error" generated="true" style="display: none;" id="corporateNameErr">此公司名称已经存在</label>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red"></font>公司地址：</th>
			<td width="80%">
			 <select name="merchantInfo.province" id="province" data-value="<s:property value="merchantInfo.province"/>">
            </select>
            <select name="merchantInfo.city" id="city">
            </select>
                <select name="merchantInfo.area" id="area">
                </select>
			<input type="text" name="merchantInfo.corporateLocation" id="corporateLocationId" maxlength="40">
			<label class="error" generated="true" style="display: none;" id="corporateLocationNull">地址不能为空</label>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red"></font>组织机构代码：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.organizationCode" id="organizationCodeId" maxlength="13">(请输入00000000-0格式的代码)
			<label class="error" generated="true" style="display: none;" id="organizationCodeNull">组织机构代码不能为空</label>
					<label class="error" generated="true" style="display: none;" id="organizationCodeErr">请输入00000000-0格式的代码</label>
			</td>
		</tr>
		<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>组织机构代码电子版：</th>
				<td>
				<input type="file" name="organizationFile" id="organizationFile" onChange="organizationFileOnchange();"></input>(请上传bmp,png,gif,jpeg,jpg格式的图片)
				<input type="hidden" value="" name="organizationFileName" id="organizationFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="organizationFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle"><font color="red"></font>营业执照注册号：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.businessLicenceRegister" id="businessLicenceRegisterId" maxlength="16">(请输入15位数字的注册号)
			<label class="error" generated="true" style="display: none;" id="businessLicenceRegisterNull">营业执照注册号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="businessLicenceRegisterErr">请输入15位数字的注册号</label>
			</td>
		</tr>
		<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>营业执照电子版：</th>
				<td>
				<input type="file" name="businessFile" id="businessFile" onChange="businessFileOnchange();"></input>(请上传bmp,png,gif,jpeg,jpg格式的图片)
				<input type="hidden" value="" name="businessFileName" id="businessFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="businessFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">营业执照有效期：</th>
				<td><input class="" CssClass="Wdate" onFocus="WdatePicker()" id="staTime" runat="server"
					name="merchantInfo.blinceStartdate" readonly type="text" size="10" maxlength ="15"
					 format='yyyy-MM-dd'/> 到
					<input class="" CssClass="Wdate" onFocus="WdatePicker()" id="endTime" runat="server"
					name="merchantInfo.blinceEnddate" readonly type="text" size="10" maxlength ="15"
					 format='yyyy-MM-dd'/>
					 <label class="error" generated="true" style="display: none;" id="validTimeErr">开始日期不能大于结束日期</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">法定经营范围：</th>
				<td><input class="required input_style" id="businessScope"
					name="merchantInfo.businessScope" type="text" size="30" maxlength ="40" />
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>税务登记证号：</th>
				<td><input class="required input_style" id="taxRegistrationCno"
					name="merchantInfo.taxRegistrationCno" type="text" size="30" maxlength ="15"
					value="" />(税务登记证号由15数字组成)
					<label class="error" generated="true" style="display: none;" id="taxRegistrationCnoNull">税务登记证号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="taxRegistrationCnoErr">税务登记证号由15数字组成</label>
					
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>税务登记证电子版：</th>
				<td>
			   <input type="file" name="taxRegistratFile" id="taxRegistratFileId" onChange="taxRegistratFileOnchange();"></input>(请上传bmp,png,gif,jpeg,jpg格式的图片)
				<input type="hidden" value="" name="taxRegistratFileName" id="taxRegistratFileNameId"></input>
				<label class="error" generated="true" style="display: none;" id="taxRegistratFileErr">请上传bmp,png,gif,jpeg,jpg格式的图片</label>
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">纳税人识别号：</th>
				<td><input class="required input_style" id="taxpayerIdnumber"
					name="merchantInfo.taxpayerIdnumber" type="text" size="30" maxlength ="15" />
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>注册资金：</th>
				<td>
				<input class="required input_style" id="registerBankroll"
					name="merchantInfo.registerBankroll" type="text" size="30" maxlength ="10"
					value="" />万&nbsp;&nbsp;(请输入整数的注册资金)
						<label class="error" generated="true" style="display: none;" id="registerBankrollNull">请选择注册资金</label>
						<label class="error" generated="true" style="display: none;" id="registerBankrollErr">请输入整数的注册资金</label>	
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>开户银行：</th>
				<td><input class="required input_style" id="bankOfDeposit"
					name="merchantInfo.bankOfDeposit" type="text" size="30" maxlength ="40"
					value="" />
					<label class="error" generated="true" style="display: none;" id="bankOfDepositNull">开户银行不能为空</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>账户名：</th>
				<td><input class="required input_style" id="bankAccountName"
					name="merchantInfo.bankAccountName" type="text" size="30" maxlength ="40"/>
					<label class="error" generated="true" style="display: none;" id="bankAccountNameNull">账户名不能为空</label>
					</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle"><font color="red"></font>银行账号：</th>
				<td><input class="required input_style" id="companyAccount"
					name="merchantInfo.companyAccount" type="text" size="30" maxlength ="30" />
					<label class="error" generated="true" style="display: none;" id="companyAccountNull">银行账号不能为空</label>
					<label class="error" generated="true" style="display: none;" id="companyAccountErr">银行账号由数字组成</label>
					</td>
			</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">申请联系人：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.contactsName" id="contactsNameId" maxlength ="15">
			<label class="error" generated="true" style="display: none;" id="contactsNameNull">联系人不能为空</label>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">公司联系人手机：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.mobile" id="mobileId" maxlength="12">
			<label class="error" generated="true" style="display: none;" id="mobileNull">联系电话不能为空</label>
					<label class="error" generated="true" style="display: none;" id="mobileErr">请输入格式正确的联系电话</label>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">公司固定电话：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.fixedPhone" id="fixedPhoneId" maxlength="16">(请输入011,0111-111111格式固定电话)
			<label class="error" generated="true" style="display: none;" id="fixedPhoneNull">固定电话和联系人手机必填一种</label>
					<label class="error" generated="true" style="display: none;" id="fixedPhoneErr">请输入011,0111-11111格式固定电话</label>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">联系人邮箱：</th>
			<td width="80%">
			<input type="text" name="merchantInfo.contactsEmail" id="contactsEmailId" maxlength="30">
			<label class="error" generated="true" style="display: none;" id="contactsEmailNull">联系邮箱不能为空</label>
					<label class="error" generated="true" style="display: none;" id="contactsEmailErr">请输入格式正确的联系邮箱</label>
			</td>
		</tr>
		
		<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>
					分配经营类目(比例为百分比)：</th>
				<td class="Supplierslist">
				<input type="hidden" value="" name="supplierCategorys" id="supplierCategorys">
				<s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#hisList1.categoryParentId == 0">
	                    				<input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
	                    				<input type="hidden" value="${categoryId}"  class="hidSacId"></input>
	                    				<s:if test="#hisList1.sacId == null">
                    						<input type="checkbox"  id="box${categoryId}" value="${categoryId}" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                   						</s:if>
                   						<s:else>
                   							<input type="checkbox"  id="box${categoryId}" value="${categoryId}"  onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                   						</s:else>
                   						${categoryName}
                    						<li style="list-style-type:none;">
                    							<s:iterator value="categorysList" id="hisList2">
                    								<s:if test="#hisList2.categoryParentId != 0">
                    									<s:if test="#hisList1.categoryId == #hisList2.categoryParentId">
						                    				<input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
						                    				<input type="hidden" value="${categoryId}"  class="hidSacId"></input>
						                    			 	<s:if test="#hisList2.sacId == null">
						                    			 		<input type="checkbox"  id="box${categoryId}" value="${categoryId}" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
						                    			 		${categoryName}
						                    			 		<input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" disabled="true" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
						                    			 	</s:if>
						                    			 	<s:else>
		                    									<input type="checkbox" id="box${categoryId}" value="${categoryId}"  onclick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
						                    					${categoryName}
					                    				 		<input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" disabled="true" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
                    										</s:else>
                    									</s:if>
                    								</s:if>
                    							</s:iterator>
                    			 			</li>
                   							</br>
                    				</s:if>
                    			 </s:iterator>
                    			 <label class="error" generated="true" style="display: none;" id="categorysNull">请选择类目</label>
                    			 </br>

	<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center">
			<input type="button" value="下一步" class="btn-custom nextBtn"  onClick="submitForm();" >
			&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="backBtn" onClick="gotoList()" />
			<td width="20%" align="center"></td>
		</tr>
	</table>

	<br>
	<br>
<!-- 数据隐藏区域 -->
<input type="hidden" name="merchantInfo.merchantId" value="${merchantInfo.merchantId}" id="merchantId">
<input type="hidden" value="${longinId}" name="longinId"></input>
</s:form>


<script LANGUAGE="JavaScript">
addressInit('province', 'city', 'area');

	function gotoList() {
		history.back(-1);
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
	}

	$('#corporateNameId').blur(function () {
		 $("#corporateNameNull").hide();
	   });
	$('#corporateLocationId').blur(function () {
		 $("#corporateLocationNull").hide();
	   });
	$('#organizationCodeId').blur(function () {
		 $("#organizationCodeNull").hide();
		 $("#organizationCodeErr").hide();
	   });
	$('#businessLicenceRegisterId').blur(function () {
		 $("#businessLicenceRegisterNull").hide();
		 $("#businessLicenceRegisterErr").hide();
	       });
	$('#contactsNameId').blur(function () {
		 $("#contactsNameNull").hide();
		 $("#contactsNameNull").hide();
	       });
	$('#productDesc').blur(function () {
		 $("#saleProductDescribeNull").hide();
		 $("#saleProductDescribeErr").hide();
   });
	$('#fixedPhoneId').blur(function () {
		 $("#fixedPhoneNull").hide();
		 $("#fixedPhoneErr").hide();
	       });
	$('#contactsEmailId').blur(function () {
		 $("#contactsEmailNull").hide();
		 $("#contactsEmailErr").hide();
	       });
	$('#mobileId').blur(function () {
		 $("#mobileNull").hide();
		 $("#mobileErr").hide();
	       });
</script>
</body>
</html>