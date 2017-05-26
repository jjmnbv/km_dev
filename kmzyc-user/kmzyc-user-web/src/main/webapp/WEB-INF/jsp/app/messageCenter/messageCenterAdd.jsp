<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加消息信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript">
	//弹出 选择账号层
	function popUpAccount() {
		dialog(
				"选择会员账号",
				"iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo",
				"900px", "500px", "iframe");
	}
	//关闭弹出窗口 
	function closeOpenUserInfo(accountId, account, name) {
		closeThis();
		$("#username").val(account);
		$("#accountId").val(accountId);
	}

	function showFrozenNumber(val) {
		if (val == 1) {
			$("#frozenRow").hide();
		} else if (val == 2) {
			$("#frozenRow").show();
		}
	}
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'消息信息'" scope="request" />
		<s:set name="son_name" value="'添加'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<s:form action="messageCenter_add.action" method="POST"
			id="bnesMessageCenterAddForm" namespace='/messageCenter'>

			<!-- hidden properties -->
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<!-- error message -->
				<s:if test="rtnMessage != null">
					<tr>
						<td colspan="2" align="center">
							<font color="red"><s:property value='rtnMessage' /> </font>
						</td>
					</tr>
				</s:if>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>查阅人：
					</td>
					<td width="80%">

						<input type="text" id="username" name="username" />
						<input type="button" value="选中" onclick="popUpAccount();"/>
						<input type="hidden" name="bnesMessageCenter.accountId"
							id="accountId"
							value="<s:property value='bnesFrozenRecord.loginId'/>" />

						<span id="megtitle" style="color: red"></span>
					</td>
				</tr>

				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>消息标题：
					</td>
					<td width="80%">
						<select name="bnesMessageCenter.infoPromptId">
							<option></option>
							<s:iterator id="titleList" value="titleList">
								<option value="<s:property value="infoPromptId"/>">
									<s:property value="title" />
								</option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<font color="red">*</font>是否查阅：
					</td>
					<td width="80%">
						<select name="bnesMessageCenter.status">
							<option value="0">
								否
							</option>
							<option value="1">
								是
							</option>
						</select>
					</td>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>查看日期日期：
						</td>
						<td width="80%">
							<input name="bnesMessageCenter.checkDate" id="checkDate"
								type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							<span id="megcheckDate" style="color: red"></span>
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
						<input class="backBtn" onclick=
	gotoList();;;;
type="button"
							value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>

			<br>
			<br>

		</s:form>
		<SCRIPT LANGUAGE="JavaScript">
	function gotoList() {
		location.href = "/messageCenter/messageCenter_pageList.action";
	}
</SCRIPT>
	</BODY>
</HTML>