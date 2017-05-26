<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams,com.pltfm.app.util.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>地址信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jsAddress.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			var province = $("#eprovince").val();
			var city = $("#ecity").val();
			var area = $("#earea").val();
			if (province != "" && city != "") {
				addressInit('province', 'city', 'area', province, city, area);
			} else {
				addressInit('province', 'city', 'area');
			}
			
			jQuery.validator.addMethod("checkAddressNum", function(value, element) {
		 	 	var addressCount = 0;
		 			$.ajax({
		 				async:false,
		 				url:"address_checkAddressNum.action",
		 				type:"POST",
		 				data:"login_Id=" + $("#loginId").val(),
		 				dataType:"json",
		 				success:function(json){
		 					addressCount =parseInt(json);
		 				}
		 			});
		 			if(addressCount>=10){
		 				return false;
		 			}else{
		 	 			return true;
		 	 		}
		 			
			}, "当前客户的收货地址最多只能添加10条");
			
			
			$("#addressAdd").validate( {
				rules : {
					"accountName" : {
						required : true,
						maxlength : 25,
						checkAddressNum:true
					},
					"name" : {
						required : true,
						maxlength : 16
					},
					"detailedAddress" : {
						required : true,
						maxlength : 85
					},
					"addName" : {
						maxlength : 16
					},
					"postalcode" : {
						required : true,
						isZipCode : true,
						maxlength : 6
					},
					"telephone" : {
						telephone : true,
						maxlength : 14
					},
					"cellphone" : {
						required : true,
						cellphone : true,
						maxlength : 14
					},
					"area" : {
						required : true,
					}
				},
				success : function(label) {
					label.removeClass("checked").addClass("checked");
				}
			});
		});
		
		//弹出层 选择账号
		function popUpAccount() {
			dialog("选择账号", "iframe:/accounts/accountInfo_popUpAccount.action?sourcePage=address", "900px",
					"460px", "iframe");
		}
		//关闭弹出窗口 
		function closeOpenAccount(accountId, account, name, amount, loginId) {
			closeThis();
			document.forms[0].n_accountId.value = accountId;
			document.forms[0].accountName.value = account;
			document.forms[0].loginId.value = loginId;
		}
		function gotoList(){
		    location.href="/accounts/address_queryPageList.action?address.loginId="+document.getElementById("loginId").value;
		}
		
		
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户资料'" scope="request" />
		<s:set name="name" value="'地址管理'" scope="request" />
		<s:set name="son_name" value="'添加'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<s:form id="addressAdd" action="address_operateSave.action"
			method="post" namespace="/growing" >
			<s:token />
			<input type="hidden" id="eprovince" value="<s:property value='address.province'/>" />
			<input type="hidden" id="ecity" value="<s:property value='address.city'/>" />
			<input type="hidden" id="earea" value="<s:property value='address.area'/>" />
			<input type="hidden" name="d_createdate" value='<s:date name="address.d_createdate" format="yyyy-MM-dd HH:mm:ss" />' />  
			<input  type='hidden' id="loginId"  name="loginId"  value="<s:property value='address.loginId'/>" />
			<!-- 数据编辑区域 -->
			<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class="edit_title">
						地址管理
					</th>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>会员账号：
						</td>
						<td width="80%">
							<s:if test="%{address.n_accountId!=null}">
								<s:property value='address.accountLogin' />
								<input type="hidden" name="accountLogin"
										value="<s:property value='address.accountLogin'/>">
								<input type="hidden" name="n_accountId"
									value="<s:property value='address.n_accountId'/>">
							</s:if>
							<s:else>
								<div>
									<input type="hidden" name="n_accountId"
										value="<s:property value='address.n_accountId'/>">
									<input type="text" name="accountName" readonly
										value="<s:property value='address.accountLogin'/>">
										<s:if test="%{showType==null}">
										   <input type="button" value="选择" onclick="popUpAccount()" />
										</s:if>
									
								</div>
							</s:else>
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>收货人：
						</td>
						<td width="80%">
							<input id="code" name="name" type="text"
								value="<s:property value='address.name'/>" />
						</td>
				</tr>
				
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>手机号码：
						</td>
						<td width="80%">
							<input name="cellphone" type="text" maxlength="11" value="<s:property value='address.cellphone'/>" />
							<input type="hidden" name="n_addressId" value="<s:property value='address.n_addressId'/>" />
						</td>
				</tr>
				
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>收货地址：
						</td>
						<td width="80%">
							<select id="province" name="province" style="width: 120px;"></select>
							<select id="city" name="city" style="width: 120px;"></select>
							<select id="area" name="area" style="width: 120px;"></select>
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>详细地址：
						</td>
						<td width="80%">
							<input name="detailedAddress" type="text"
								value="<s:property value='address.detailedAddress' />" />
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>邮编：
						</td>
						<td width="80%">
							<input name="postalcode" type="text" maxlength="6"
								value="<s:property value='address.postalcode'/>" />
						</td>
				</tr>
				
				<tr>
					<td width="20%" class="eidt_rowTitle">
						联系电话：
						</td>
						<td width="80%">
							<input name="telephone" type="text"
								value="<s:property value='address.telephone'/>" />
						</td>
				</tr>
				
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>默认地址：
						</td>
						<td width="80%">
							<s:if test="%{address.status!=null}">
								<s:radio name="status" value="%{address.status}" label="status"
									list="#{0:'是',1:'否'}"></s:radio>
							</s:if>
							<s:else>
								<s:radio name="status" value="1" label="status"
									list="#{0:'是',1:'否'}"></s:radio>
							</s:else>
						</td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="saveBtn" type="submit" value=" ">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList()" type="button" value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>
