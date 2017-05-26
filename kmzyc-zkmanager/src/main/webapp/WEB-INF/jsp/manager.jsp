
<%@ page language="java"
	import="com.km.commons.config.core.ConfigurationUtil"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
  String contextPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>zookeeper配置管理</title>
<script src="<%=contextPath%>/resources/jquery/jquery-latest.pack.js"></script>
<style type="text/css">
.main {
	width: 100%;
	min-width: 1008px;
	overflow: hidden;
	font-family: "微软雅黑";
}

.main-body {
	width: 968px;
	position: relative;
	left: 50%;
	margin-left: -489px;
	overflow: hidden;
	background-color: #2d90ea;
	padding: 20px;
	font-size: 14px;
	color: #ffffff;
}

.main-body2 {
	background-color: #ffffff;
	padding: 0;
	width: 1008px;
	margin-top: 8px;
}

.main-body a {
	display: inline-block;
	padding: 5px 10px;
	background-color: #003667;
	color: #ffffff;
	margin-right: 10px;
	border-radius: 4px;
	margin-bottom: 30px;
	text-decoration: none;
}

.main-body a:hover {
	background-color: #1e77c8;
}

.ln-l, .ln-r {
	position: relative;
	float: left;
	width: 186px;
	background-color: #d6ecff;
	height: 510px;
	padding: 0 20px;
	/*overflow: hidden;*/
	color: #000000;
	overflow-y:auto;
}
.ln-l li{
 word-break: break-all;
}
.ln-r {
	width: 732px;
	float: right;
}

.ln-l ul {
	padding: 14px;
	margin: 0;
	overflow: hidden;
}

.ln-l li a {
	display: block;
	background-color: transparent;
	padding: 0;
	margin-bottom: 0;
	/* height: 30px;*/
	line-height: 30px;
	color: #000000;
}

.ln-l li a:hover {
	background-color: transparent;
	color: #1e77c8;
}

.button {
	text-align: center;
}

.titet, .sj {
	margin: 10px 0;
	color: #000000;
}

.box {
	border: 1px solid #e5e6e7;
	width: 670px;
}

.sj .box {
	height: 400px;
	position: relative;
	margin-left: 42px;
}

.sj label {
	position: absolute;
}

.enter {
	float: right;
}

.enter a {
	color: #f2fa9f;
}

.login {
	position: fixed;
	width: 360px;
	background-color: #2d90ea;
	top: 30%;
	z-index: 999;
	left: 50%;
	margin-left: -160px;
	border-radius: 5px;
	box-shadow: 0 0 3px rgba(51, 51, 51, 0.3);
	-moz-box-shadow: 0 0 3px rgba(51, 51, 51, 0.3);
	-ms-box-shadow: 0 0 3px rgba(51, 51, 51, 0.3);
	-o-box-shadow: 0 0 3px rgba(51, 51, 51, 0.3);
	-webkit-box-shadow: 0 0 3px rgba(51, 51, 51, 0.3);
}

.login-close {
	color: #fff;
	cursor: pointer;
	display: block;
	font-family: tahoma;
	font-size: 22px;
	font-weight: 700;
	height: 18px;
	line-height: 14px;
	position: absolute;
	right: 12px;
	text-decoration: none;
	top: 12px;
	width: 18px;
	z-index: 10;
}

.login-content {
	padding: 0 5px 5px;
}

.login-title {
	color: #fff;
	font-family: "微软雅黑", "黑体", Arial;
	font-size: 16px;
	height: 36px;
	line-height: 32px;
	text-indent: 10px;
}

.login-container {
	background-color: #fff;
	border-radius: 5px;
	font-size: 12px;
	overflow: hidden;
	padding: 18px 18px 28px 18px;
}

.item {
	text-align: right;
}

.item-fore1 {
	height: 38px;
	padding: 0;
	line-height: 38px;
}

.item .itxt {
	border: 0 none;
	float: none;
	font-family: "宋体";
	font-size: 14px;
	line-height: 18px;
	overflow: hidden;
	border: 1px solid #bdbdbd;
	width: 236px;
}

.login-btn {
	text-align: center;
}

.login-btn a {
	display: block;
	background-color: #2d90ea;
	border-radius: 5px;
	height: 30px;
	line-height: 30px;
	margin-top: 20px;
	text-decoration: none;
	color: #fff;
	font-size: 16px;
}
</style>
<script type="text/javascript">
	var errorMessage = {
		'directoryNotEmpty' : '目录不为空',
		'exception' : '系统异常',
		'directoryExists' : '目录或者文件已经存在',
		'success' : '操作成功',
		'nonlogin' : '需要登录',
		'noUrl':'路径不存在',
		'outError':'导出文件失败',
		'urlFormatError':'导出路径格式错误，需以“/”开头且不能包含“\\”'
		}
	function edit() {
		var nodeValue = document.getElementById("nodeValue");
		nodeValue.removeAttribute("readonly");
		$("#edit").hide();
		$(".editHide").show();
	}

	function popAdd(path, suffix) {
		
		var name = window.prompt("名称:")
		if (name) {
			var contextPath = $("#contextPath").val() + "/index";
			if (suffix) {
				//name = name + ".properties"
			}
			
			$.getJSON(contextPath + "/add", {
				path : encodeURI(path,"utf-8"),
				nodeName : encodeURI(name,"utf-8")
			}, function(data) {
				var code = data.code;
				if (code == 'success') {
					alert("添加成功!");
					window.location.reload();
				} else {
					alert(errorMessage[code]);
				}
			});
		}
	}
	//导出目录
	function cOut(path) {
		var name = window.prompt("目录:")
		if (name) {
			var contextPath = $("#contextPath").val() + "/index";

			$.getJSON(contextPath + "/out", {
				path : encodeURI(path,"utf-8"),
				url :  encodeURI(name,"utf-8")
			}, function(data) {
				var code = data.code;
				if (code == 'success') {
					alert("导出成功!");
				} else {
					alert(errorMessage[code]);
				}
			});
		}
	}
	function del(path) {
		if (!confirm("是否确认删除该目录?")) {
			return;
		}
		var contextPath = $("#contextPath").val() + "/index";
		$.getJSON(contextPath + "/del", {
			path : encodeURI(path,"utf-8")
		}, function(data) {
			var code = data.code;
			if (code == 'success') {
				var url = window.location.href;
				url = url.substring(0, url.lastIndexOf("/"));
				alert("删除成功!");
				window.location.href = url;
			} else {

				alert(errorMessage[code]);
			}
		});
	}
	function save(path) {
		var nodeValue = document.getElementById("nodeValue");
		if (nodeValue.getAttribute("readonly") == 'readonly') {
			return;
		}
		var nodeCont = nodeValue.value;
		var contextPath = $("#contextPath").val() + "/index";
		$.post(contextPath + "/edit", {
			path : encodeURI(path,"utf-8"),
			nodeCont : encodeURI(nodeCont,"utf-8")
		}, function(data) {
			var code = data.code;
			if (code == 'success') {
				nodeValue.setAttribute("readonly", "readonly");
				alert("保存成功");
				$(".editHide").hide();
				$("#edit").show();
			} else {
				alert(errorMessage[code]);
			}
		}, "json");
	}
	function historyBack(path) {
		var url = path;

		url = "/zkManager/index" + url.substring(0, url.lastIndexOf("/"));
		window.location.href = url;
	}
	function cancel() {
		var oldvalue = $("#oldvalue").val();
		$(".editHide").hide();
		var nodeValue = document.getElementById("nodeValue");
		nodeValue.value = oldvalue;
		nodeValue.setAttribute("readonly", "readonly");
		$("#edit").show();
	}
	function showLogin() {
		//$("#error").html("");
		$("#mark").show();
		$("#mark").bind("click", function(e) {
			//$(this).unbind();
			$(this).hide();
			$("#loginForm").fadeOut("normal");
			$("#error").html("");
			$("#uname").val("");
			$("#pass").val("");
		});
		$("#loginForm").show();
	}
	function closeLogin() {
		$("#mark").hide();
		$("#loginForm").hide();
		$("#error").html("");
		$("#uname").val("");
		$("#pass").val("");
	}
	function login() {
		var username = $("#uname").val();
		var password = $("#pass").val();
		if (password && username)
			$.getJSON("/zkManager/index/login", {
				username : encodeURI(username,"utf-8"),
				password : encodeURI(password,"utf-8")
			}, function(data) {
				var code = data.code;
				if (code == 'success') {
					window.location.reload();
				} else if (code == 'fail') {
					$("#error").html("登录失败");
				} else {
					$("#error").html("系统异常");
				}
			});
	}
</script>
</head>

<body>
	<input type="hidden" value="<%=contextPath%>" id="contextPath" />
	<div class="main">
		<div class="main-body">
			<c:if test="${not isAdmin}">
				<div class="enter">
					<a href="javascript:void(0);" onclick="showLogin();">登录</a>
				</div>
			</c:if>
			<p>
				当前zookeeper服务器：<span><%=ConfigurationUtil.getString("zk.host")%></span>
			</p>
			<p>
				当前路径：<span id="cruupath">${path}</span>
			</p>
			<a href="javascript:;" onclick="historyBack('${path}');">返回上一级</a>
			<c:if test="${isAdmin}">
				<c:set var="levelList" value="${fn:split(path, '//')}"></c:set>
				<c:set var="levelNum" value="${fn:length(levelList)}"></c:set>
				<a href="javascript:;" onclick="del('${path}')" data="${path}">删除当前</a>
				<c:if test="${not fn:endsWith(path, '.properties')}">
					
					<c:if test="${levelNum<=4}">
						<a href="javascript:;" onclick="popAdd('${path}','12313')"
							data="${path}">增加节点</a>
					</c:if>
				</c:if>
				<a href="javascript:;" onclick="cOut('${path}')" data="${path}">导出目录</a>
			</c:if>
		</div>
		<div class="main-body main-body2">
			<div class="ln-l">
				<ul>
					<c:forEach items="${childerNodes}" var="nodeName">
						<li><a href="<%=contextPath%>/index${path}/${nodeName}">${nodeName}</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="ln-r">
				<form>
					<div class="titet">
						目录：<input class="box" type="text" value="${path}"
							disabled="disabled" />
					</div>
					<div class="sj">
						<label>数据：</label>
						<textarea title="编码为utf-8,最好不要使用中文" id="nodeValue"
							readonly="readonly" class="box">${dataValue}</textarea>
					</div>
				</form>
				<c:if test="${isAdmin}">
					<div class="button">
						<a href="javascript:;" id="edit" onclick="edit();">编辑</a> <a
							href="javascript:;" class="editHide" id="save"
							style="display: none" onclick="save('${path}');">保存</a> <a
							href="javascript:;" class="editHide" id="" style="display: none"
							onclick="cancel();">取消</a>
					</div>
				</c:if>
				<input type="hidden" value="${dataValue}" id="oldvalue">
			</div>
		</div>
		<div class="login" id="loginForm" style="display: none">
			<a class="login-close" href="javascript:;" onclick="closeLogin()"
				title="关闭" style="display: block;">×</a>
			<div class="login-content">
				<div class="login-title">LogIn</div>
				<div class="login-container">
					<form>
						<div class="item item-fore1">
							<label class="login-label" for="Username">Username：</label> <input
								type="text" id="uname" placeholder="" autocomplete="off"
								tabindex="1" name="loginname" class="itxt">
						</div>
						<div class="item item-fore1">
							<label class="login-label" for="Password">Password：</label> <input
								type="password" id="pass" placeholder="" autocomplete="off"
								tabindex="1" name="loginname" class="itxt">
						</div>
						<div class="item" id="error"
							style="text-align: left; color: red; height: 14px;"></div>
						<div class="login-btn">
							<a href="javascript:void(0);" onclick="login();"
								class="btn-entry">登录</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div
		style="position: absolute; top: 0; left: 0; height: 100%; width: 100%; z-index: 1; display: none; background-color: #191919; opacity: 0.6"
		id="mark"></div>
</body>
</html>
