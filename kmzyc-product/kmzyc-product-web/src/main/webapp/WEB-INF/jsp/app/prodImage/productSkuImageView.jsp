<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加新品牌</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <style type="text/css">
        img {
            border: 2px solid gray;
        }
        .isDefault {
            border-color: #078C43;
            border-width: 2px;
        }
    </style>
</head>
<body>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1"
       bordercolor="#C7D3E2" style="border-collapse: collapse; font-size: 12px;">
    <tr>
        <s:if test='#request.imageList.size() == 0'>
            <td>
                暂无图片
            </td>
        </s:if>
        <s:iterator value="#request.imageList">
            <td align="center">
                <img alt="" width="150" height="150" src="<s:property value="imagePath" /><s:property value="imgPath8" />"
                <s:if test='isDefault == "0"'> class="isDefault"</s:if> >
            </td>
        </s:iterator>
    </tr>
</table>
</body>
</html>