<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/qrcode/base64.js"></script>
<script type="text/javascript" src="/etc/qrcode/canvas2image.js"></script>
<script type="text/javascript" src="/etc/qrcode/html2canvas.js"></script>
<script type="text/javascript" src="/etc/qrcode/qrcode.js"></script>
<title>二维码展示页</title>
</head>
<body>
	<br />
	<p style="text-align: center; width: 10%";>
		<input type="button" class="btngreen" value="下载" onclick="savePngs()">
		
		<input type="hidden" value="<%=ConfigurationUtil.getString("crowd_client_url")%>" id="crowd_client_url">
	</p>
	<br />
	<div id="qrcodeImage" align="center">
		<input type="hidden" value="${codeArray }" id="codeArray">
		<table>
			<tr>
				<c:forEach items="${result }" var="code" varStatus="sts">
					<td width="15%"><div id="${code.institutionCode }"
							style="text-align: center"></div>
						<p style="text-align: center; font-size: 30px;">${code.institutionCode }</p></td>
					<c:if
						test="${sts.index == 2 || (sts.index > 2 && (sts.index+1)%3==0) }">
			</tr>
			<tr height="18%">
				</c:if>
				</c:forEach>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		var crowd_client_url = $("#crowd_client_url").val();
		var array = $("#codeArray").val().replace('[', ' ').replace(']', ' ')
				.split(",");
		$.each(array, function(i, value) {
			var id = '#' + value.trim();
			$(id).qrcode({
				text : crowd_client_url + '/goRegister.do?c=' + value.trim(), // 要编码的字符串
				width : 300, // 定义宽度
				height : 300
			});
		});
	});

	function savePngs() {
		html2canvas($("#qrcodeImage"), {
			onrendered : function(oCanvas) {
				/* var strDataURI = oCanvas.toDataURL(); */
				Canvas2Image.saveAsPNG(oCanvas);
			}
		});
	}
</script>
</html>
