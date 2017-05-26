<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<title>业务员管理</title>
</head>
<body>
	<s:set name="parent_name" value="'机构管理'" scope="request" />
	<s:set name="name" value="'业务员管理'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<form action="" id="f_addBagMan">
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td >业务员姓名：</td>
				<td><input type="text" name="bagman.name" id="bname"
					style="width: 150px" value="" required> <span style="color: red"
					id="tip_name"></span></td>
                    				<td >手机号：</td>
				<td><input type="text" name="bagman.mobile"
					style="width: 150px" id="bmobile" required> <span
					style="color: red" id="tip_mobile"></span></td>
                    				<td >类型：</td>
				<td><select style="width: 150px" name="bagman.type" id="btype"
					required>
						<option value="">请选择</option>
						<option value="0" >全职</option>
						<option value="1" >兼职</option>
				</select> <span style="color: red" id="tip_type"></span></td>
                				<td><input type="button" class="btn-custom"
					value="+ 新增业务员" id="saveBagman"></td>
			</tr>

		</table>

	</form>

	<form action="/crowd/listBagMans.action" id="f_listBagMan" name="f_listBagMan">
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td>业务员：<input
					type="text" name="bagman.name" size="15" value="${bagman.name }"></td>
				<td >手机： <input
					type="text" name="bagman.mobile" size="15" value="${bagman.mobile }"></td>
				<td>类型：  <select
					name="bagman.type" style="width: 100px">
						<option value="">请选择</option>
						<option value="0" <c:if test='${bagman.type==0 }'>selected="selected"</c:if>>全职</option>
						<option value="1" <c:if test='${bagman.type==1 }'>selected="selected"</c:if>>兼职</option>
				</select>
				</td>
				<td >状态： <select
					name="bagman.status" style="width: 100px">
						<option value="">请选择</option>
						<option value="0" <c:if test='${bagman.status==0 }'>selected="selected"</c:if>>无效</option>
						<option value="1" <c:if test='${bagman.status==1 }'>selected="selected"</c:if>>有效</option>
				</select>
				</td>
                    				<td >创建时间： <input
					type="text" id="d523" readonly class="Wdate" size="15" style="width: 158px"
					value="<s:date name = 'bagman.createStart' format='yyyy-MM-dd HH:mm:ss' />"
					name="bagman.createStart" 
					onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />

				至 <input
					type="text" id="d524" readonly class="Wdate" size="15" style="width:158px"
					value="<s:date name = 'bagman.createEnd' format='yyyy-MM-dd HH:mm:ss' />"
					name="bagman.createEnd"
					onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
                    				<td ><input type="button" class="queryBtn"
					id="queryList"></td>
			</tr>
		</table>

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="10%">业务员</th>
				<th align="center" width="15%">手机</th>
				<th align="center" width="15%">推广机构数量(家)</th>
				<th align="center" width="10%">类型</th>
				<th align="center" width="10%">创建人</th>
				<th align="center" width="15%">创建日期</th>
				<th align="center" width="10%">状态</th>
				<th align="center" width="15%">操作</th>

			</tr>
			<c:forEach items="${page.dataList }" var="man">
				<tr>
					<td align="center">${man.name }</td>
					<td align="center">${man.mobile }</td>
					<td align="center">${man.organiCount }</td>
					<td align="center"><c:if test="${man.type==1 }">兼职</c:if> <c:if
							test="${man.type==0 }">全职</c:if></td>
					<td align="center">${man.createBy }</td>
					<td align="center"><fmt:formatDate value="${man.createDate }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center"><c:if test="${man.status==1 }">有效</c:if> <c:if
							test="${man.status==0 }">无效</c:if></td>
					<td align="center"><input type="button" value="查看" onClick="viewBagman(${man.id},'read')"/>&nbsp;&nbsp;&nbsp;<input
						type="button" value="编辑"  onclick="viewBagman(${man.id},'edit')"/></td>
				</tr>
			</c:forEach>
		</table>

		<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'f_listBagMan'"
							scope="request"></s:set> <jsp:include
							page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
		</table>
	</form>


</body>

<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">

	$(function() {
		//表单提示语清除
		function clearTips() {
			$("#bname").css("border-color", "");
			$("#tip_name").html("");
			$("#bmobile").css("border-color", "");
			$("#tip_mobile").html("");
			$("#btype").css("border-color", "");
			$("#tip_type").html("");
		}

		$("#btype").change(function(){
			valid_empty();
		});
		//验证表单元素为空
		function valid_empty() {
			if ($("#bname").val() == null || $("#bname").val() == '') {
				$("#bname").focus().css("border-color", "red");
				$("#tip_name").html("姓名不能为空");
				return false;
			}
			if ($("#bmobile").val() == null || $("#bmobile").val() == '') {
				$("#bmobile").focus().css("border-color", "red");
				$("#tip_mobile").html("手机号不能为空");
				return false;
			}
			if ($("#btype").val() == null || $("#btype").val() == '') {
				$("#btype").focus().css("border-color", "red");
				$("#tip_type").html("业务员类型不能为空");
				return false;
			}
			clearTips();
			return true;
		}

		//验证表单元素长度过长
		function valid_length() {
			if ($("#bname").val().length < 2 || $("#bname").val().length > 7) {
				$("#bname").focus().css("border-color", "red");
				$("#tip_name").html("不符合姓名长度范围：2~13");
				return false;
			}
			var mobileReg = /^1[3|4|5|6|7|8]\d{9}$/;
			if (!mobileReg.test($("#bmobile").val())) {
				$("#bmobile").focus().css("border-color", "red");
				$("#tip_mobile").html("手机号码格式错误！");
				return false;
			}
			$("#bname").css("border-color", "");
			$("#tip_name").html("");
			$("#bmobile").css("border-color", "");
			$("#tip_mobile").html("");
			return true;
		}

		$("#saveBagman")
				.click(
						function() {
							if (valid_empty() && valid_length()) {
								$
										.post(
												'/crowd/saveBagMan.action',
												$("#f_addBagMan").serialize(),
												function(data) {
													var jsonObj = eval("("
															+ data + ")");
													var code = jsonObj.code;
													var message = jsonObj.message;
													valid_handle(code, message);
													if (code == 1)
														window.location.href = "/crowd/listBagMans.action";
												});
							}

						});

		//输入框提示处理
		function valid_handle(code, message) {
			if (code == 2) {
				$("#bname").focus().css("border-color", "red");
				$("#tip_name").html(message);
			}
			if (code == 3) {
				$("#bmobile").focus().css("border-color", "red");
				$("#tip_mobile").html(message);
			}
			if (code == 1) {
				$("#bname").css("border-color", "");
				$("#tip_name").html("");
				$("#bmobile").css("border-color", "");
				$("#tip_mobile").html("");
			}
			if (code == -1) {
				alert(message);
			}
		}

		//表单元素异步验证是否已存在
		$("#bname,#bmobile").blur(function() {
			//if (valid_empty() && valid_length()) {
			$.getJSON("/crowd/isBmanExists.action", {
				'name' : $("#bname").val(),
				'mobile' : $("#bmobile").val()
			}, function(data) {
				var code = data.code;
				var message = data.message;
				valid_handle(code, message);
			});
			//}

		});

		//条件查询按钮事件
		$("#queryList").click(function() {
			$("#f_listBagMan").submit();
		});

	});

		//跳转详情页
		function viewBagman(id,tag){
			window.location.href = "/crowd/editBagman.action?bagid=" + id + "&&tag=" + tag;
		}
</script>
</html>