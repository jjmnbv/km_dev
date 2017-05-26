<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加申诉</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript">
		 $(document).ready(function(){
       
          $("#acctAppealInfo").validate({
               rules: {
               		"account":{required:true},
					"bnesAcctAppealInfo.appealTitle":{required:true,unusualChar:true,maxlength:50},
					"bnesAcctAppealInfo.appealContent":{required:true,unusualChar:true,maxlength:165}
	        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });
     //弹出 选择账号层
	function popUpAccount() {
	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","460px","iframe");
	}
	//关闭弹出窗口 
	function closeOpenAccount(accountId,account,name,amount){
	    closeThis();
	    $("#accountName").val(account);
	     $("#accountId").val(accountId);
		
	}
  
		</script>
	</head>

	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'安全认证'" scope="request" />
		<s:set name="son_name" value="'帐号申诉'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<INPUT TYPE="hidden" name="isEnable" value="1">
		<s:form action="acctAppealInfo_add.action" id="acctAppealInfo" method="POST"
			namespace='/acctBusiness'>
			<s:token/>
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<!-- error message -->
				<s:if test="rtnMessage != null">
					<tr>
						<td colspan="2" align="center">
							<font color="red"><s:property value='rtnMessage' />
							</font>
							
						</td>
					</tr>
				</s:if>
				<tr> 
		<td width="15%" align="right"><font color="red">*</font>账户号：</td>
		<td width="80%"> 
		  <input type="text"  id="accountName" name="account"  readonly />
		  <input type="hidden" id="accountId" name="bnesAcctAppealInfo.accountId"  />
		   <input type="button"  value="选中" onclick="popUpAccount()"/>
		    </td>
	    </tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>申诉标题：
					</td>
					<td width="80%">
						
					    <input type="text"  name="bnesAcctAppealInfo.appealTitle">
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>申诉内容：
					</td>
					<td width="80%">
                        <textarea rows="5" cols="20"   name="bnesAcctAppealInfo.appealContent"></textarea>
					</td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="saveBtn" type="submit" value="">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList()" type="button"
							value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>

			<br>
			<br>

		</s:form>
		<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/acctBusiness/acctAppealInfo_list.action";
}

</SCRIPT>
	</BODY>
</HTML>