<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品图片</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
	img{
		border: 1px solid #dbdbdb;
	}
	.isDefault{
		border-color: #379945;
		border-width: 2px;
	}
</style>
</head>
<body>
	<div class="fn-p20">
	<!-- 数据编辑区域 -->
	<table class="ui-table table-bordered">
		<tbody>
		<tr>
			<s:if test='#request.imageList.size() == 0'>
				<td>
					暂无图片
				</td>			
			</s:if>
			<s:iterator value="#request.imageList">
				<td align="center">
					<img alt="" width="150" height="150" src="<s:property value="imagePath" /><s:property value="imgPath" />"  <s:if test='isDefault == "0"'> class="isDefault"</s:if> >
				</td>
			</s:iterator>
		</tr>
		</tbody>
	</table>
	</div>
</body>
</html>


