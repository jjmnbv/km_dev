<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>余额使用限制</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
			function submitDate(){
				alert("startsfd");
				$.ajax({
					async:'false',
					type:"post",
					data: {meetAmt:$("#meetAmt").val(),usableAmt:$("#usableAmt").val()},
					url:'/app/limitBalanceUseAction.action',
					success:function(data){
						alert("提交成功！");
					}
				});
			}
		</script>
	</head>
	<body>
	<s:set name="parent_name" value="'业务操作'" scope="request"/>
	<s:set name="name" value="'功能管理'" scope="request"/>
	<s:set name="son_name" value="'余额使用限制'" scope="request"/>

		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/limitBalanceUseAction.action" method="POST">
			<table>
				<tr>
					<td>
			针对云商且不是供应商或采购商的用户，每笔订单，每满<input id="meetAmt" name="meetAmt"/>元可用<input id="usableAmt" name="usableAmt"/>元余额支付
					</td>
				</tr>
				<tr>
					<td>
						(例：如设置每满100元可用10元余额支付，则280元的订单，最多可用余额20元，已此类推)
					</td>
				</tr>
				<tr>
					<td align="center">
				<input type="button" value=" 提交 " id="submitBtn" onclick="submitDate()"/>
						<%--<s:submit  value="提交"/>--%>
					</td>
				</tr>
			</table>


		</form>

	</body>
</html>