<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>账户金额冻结</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript"> 
		// 首次加载校验 
		$(document).ready(function(){
			var type =  $('#frozenType').val();
			$('#bnesFrozenRecordAddForm').validate({
				rules: {'account': {required:true},
						'sueName': {required:true,maxlength:16,checkSueName:true},
						'frozenNumber': {required:true,isNull:true,min:0.01,max:99999,money:true,compareMoney:true},
						'sueDate': {required:true,afterDate:true},
						'sueReason': {required:true,maxlength:165},
						'frozenReason': {required:true,maxlength:165}
			    	},
			    success: function (label){
					label.removeClass('checked').addClass('checked');
				}
			});
		});
		jQuery.validator.addMethod("money", function(value, element) {
			var decimal = /^-?\d+(\.\d{1,2})?$/;
			return this.optional(element) || (decimal.test(value));
		}, $.validator.format("小数位数不能超过两位"));
		
		jQuery.validator.addMethod("compareMoney", function(value, element) {
			return parseFloat($('#accountNumber').val())>=parseFloat(value);
		}, $.validator.format("冻结金额不能大于可用金额"));
	  	//判断账户是否登录账号
	 	jQuery.validator.addMethod('checkSueName', function(value, element) {
	 		return $('#n_LoginId').val()!=$('#loginId').val();
		}, '账户属于投诉会员账号下的账户');   
	  	//冻结金额关联账户判断
	 	jQuery.validator.addMethod('isNull', function(value, element) {  
	    	var num = $('#accountNumber').val();
		    return !(null==num||num=='');
		},'请先选择冻结的相关账户'); 
	 	//冻结金额数量限制 
	 	jQuery.validator.addMethod('amount', function(value, element) {  
	    	var num = $('#accountNumber').val();
		   	return parseFloat(value)<=parseFloat(num);
		},'冻结金额不能超过账户可用金额');   
		//弹出 选择账号层
		function popUpAccount() {
	    	dialog('选择账号','iframe:/accounts/accountInfo_popUpAccount.action' ,'900px','460px','iframe');
		}
		//关闭弹出窗口 
		function closeOpenAccount(accountId,account,name,amount,n_LoginId){
	    	closeThis();
	    	$('#accountName').val(account);
	     	$('#accountId').val(accountId);
	     	$('#frozenNumber').val(amount);
	      	$('#accountNumber').val(amount);
	     	$('#availableAmount').html(amount)
	      	$('#n_LoginId').val(n_LoginId);
	      	
			document.forms[0].realName.value = name;
		}
	   //弹出 选择账号层
		function popUpUserInfo() {
	    	dialog('选择登录账号','iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo' ,'900px','460px','iframe');
		}
		//关闭弹出窗口 
		function closeOpenUserInfo(accountId,account,name){
	    	closeThis();
	    	$('#sueName').val(account);
	     	$('#sueRealName').val(name);
	      	$('#loginId').val(accountId); 
	     	$('#sueName').focus();
		}
		function gotoList(){
		    location.href="/accounts/accountFrozen_queryAccFrozenPageList.action";
		}
		</script>
	</head>
	<body>
	<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request"/>
		<s:set name="name" value="'账户金额冻结'" scope="request"/>
		<s:set name="grandson_name" value="'添加'" scope="request"/>
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height:90%;overflow-y:auto;">
		<s:form action="accountFrozen_opreateSave.action" method="POST" id="bnesFrozenRecordAddForm"  namespace='/accounts' >
			<s:token/>
			<!-- 数据编辑区域 -->
			<table width="85%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr><th colspan="2" align="left" class="edit_title">账户冻结<input type="hidden" name="frozenType" id="frozenType" value="2" /></th>
				</tr>
				<tr> 
					<td width="15%"  class="eidt_rowTitle"><font color="red">*</font>会员账号：</td>
					<td width="80%"> 
					  	<input type="text"  id="accountName" name="account" value="<s:property value='bnesFrozenRecord.accountName'/>" readonly="readonly" />
					  	<input type="hidden" id="accountId" name="accountId" value="<s:property value='bnesFrozenRecord.account'/>" />
					   	<input type="hidden" id="n_LoginId" name="loginId" />
					   	<input type="button" value="选中" onclick="popUpAccount()"/>
					</td>
				</tr>
				<!-- 
				<tr> 
					<td width="15%"  class="eidt_rowTitle">真实姓名：</td>
					<td width="80%"><input name="realName"  readonly="readonly"  type="text" value=""  /></td>
				</tr>
				 -->
				 
				<tr id="frozenRows"> 
					<td width="15%" class="eidt_rowTitle">账户可用金额：</td>
					<td width="80%"><input id="accountNumber" type="hidden" type="text" />
						<span id="availableAmount"></span>
					</td>
				</tr>
				<tr id="frozenRow"> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>冻结金额：</td>
					<td width="80%"><input name="frozenNumber" id="frozenNumber" type="text" value="<s:property value='bnesFrozenRecord.frozenNumber'/>" /></td>
				</tr>
				<!-- 
				<tr> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>投诉人登录账号：</td>
					<td width="80%"> 
					 	<input type="text" id="sueName" name="sueName" readonly value="<s:property value='bnesFrozenRecord.sueName'/>" />
					 	<input type="hidden" id="loginId" name="loginId" />
					 	<input type="button" value="选择" onclick="popUpUserInfo()"/>
					</td>
				</tr>
				 
				<tr> 
					<td width="15%" class="eidt_rowTitle">投诉人姓名：</td>
					<td width="80%"><input name="sueRealName" readonly="readonly" id="sueRealName" type="text"/></td>
				</tr>
				
				<tr> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>投诉日期：</td>
					<td width="80%"><input name="sueDate" id="sueDate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d %H:%m:%s'})" readonly="readonly"/></td>
				</tr>
				
				<tr> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>投诉原因：</td>
					<td width="80%"><textarea name="sueReason" rows="6" cols="50" ></textarea></td>
				</tr>
				-->		
				<tr> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>冻结原因：</td>
					<td width="80%"><textarea name="frozenReason" rows="6" cols="50" ></textarea></td>
				</tr>		
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<input class="saveBtn" type="submit" value=" ">&nbsp;&nbsp;<input class="backBtn" onclick="gotoList()" type="button" value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
			</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>