<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<title>业务员管理</title>
<style type="text/css">
td {
	padding: 7px;
}
</style>
</head>
<body>
	<s:set name="parent_name" value="'机构管理'" scope="request" />
	<s:set name="name" value="'业务员管理'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<form action="" id="f_bagman">
		<table width="98%" height="100" class="list_table" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">业务员</td>
				<td width="70%" style="text-align: left">${bagman.name }</td>
			</tr>
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">手机号</td>
				<td width="70%" style="text-align: left">
				<c:if test="${tag == 'read' }">${bagman.mobile }</c:if>
				<c:if test="${tag != 'read' }">
					<input type="text" name="bagman.mobile" id="bmobile"
					value="${bagman.mobile }">&nbsp;&nbsp;&nbsp;<span style="color: red" id="tip_mobile"></span>
					<input type="hidden" value="${bagman.mobile }" id="true_mobile">
				</c:if>
					</td>
			</tr>
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">类型</td>
				<td width="70%" style="text-align: left"><c:if
						test="${bagman.type==1 }">兼职</c:if> <c:if
						test="${bagman.type==0 }">全职</c:if></td>
			</tr>
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">引荐机构数量</td>
				<td width="70%" style="text-align: left">${bagman.organiCount }</td>
			</tr>
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">创建日期</td>
				<td width="70%" style="text-align: left"><fmt:formatDate value="${bagman.createDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="30%" style="text-align: right;font-weight: bold;">状态</td>
				<td width="70%" style="text-align: left">
				<input type="radio" name="bagman.status" value="1" <c:if test="${tag =='read' }">disabled="disabled"</c:if> 
					<c:if test="${bagman.status==1 }">checked="checked"</c:if>>有效
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="radio" name="bagman.status" value="0" <c:if test="${tag == 'read' }">disabled="disabled"</c:if>
					<c:if test="${bagman.status==0 }">checked="checked"</c:if>>无效	
				</td>
			</tr>
			<c:if test="${tag != 'read' }">
				<tr style="border: 0px">
					<td><input type="hidden" name="bagman.id"
						value="${bagman.id }" /></td>
					<td style="text-align: left" ><input type="button" class="saveBtn"
						id="subBtn" /></td>
				</tr>
			</c:if>
		</table>
	</form>
</body>
<script type="text/javascript">
	var flag = true;
	$("#subBtn").click(
			function() {
				var mobileTrue = valid_mobile();
				valid_multi();
				$.ajaxSetup({
			        async: false
			    });
				
				if (mobileTrue && flag) {
					$.post('/crowd/updateBagMan.action', $('#f_bagman')
							.serialize(), function(data) {
						var jsonObj = eval("(" + data + ")");
						var code = jsonObj.code;
						var message = jsonObj.message;
						alert(message);
						if (code == 1)
							window.location.href = "/crowd/listBagMans.action";
					});
				}else
					return;

			});

	function valid_mobile() {
		if ($("#bmobile").val() == null || $("#bmobile").val() == '') {
			$("#tip_mobile").html("请输入手机号！");
			return false;
		}
		var mobileReg = /^1[3|4|5|6|7|8]\d{9}$/;
		if (!mobileReg.test($("#bmobile").val())) {
			$("#bmobile").focus().css("border-color", "red");
			$("#tip_mobile").html("手机号码格式错误！");
			return false;
		}
		$("#bmobile").css("border-color", "");
		$("#tip_mobile").html("");
		return true;
	}

	function valid_multi() {
		if ($("#true_mobile").val() != $("#bmobile").val()) {
			return isBmanExists();
		}
		return true;
	}
	
	function isBmanExists(){
		$.ajaxSettings.async = false; 
		$.getJSON("/crowd/isBmanExists.action", {
			'name' : '',
			'mobile' : $("#bmobile").val()
		}, function(data) {
			var code = data.code;
			var message = data.message;
			if (code == 3) {
				$("#bmobile").focus().css("border-color", "red");
				$("#tip_mobile").html("手机已注册，请重输！");
				flag = false;
				return false;
			}
			flag = true;
			return true;
		});
		
	}

	$("#bmobile").blur(function() {
		valid_mobile();
		valid_multi();
	});
</script>
</html>