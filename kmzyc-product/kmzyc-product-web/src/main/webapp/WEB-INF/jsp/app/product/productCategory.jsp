<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加产品基本资料</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script>
        function gotoList() {
            history.back(-1);
        }
    </script>
</head>
<body>

<s:set name="parent_name" value="'产品管理'" scope="request"/>
<s:set name="name" value="'产品分类'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="toProductAdd" method="post" namespace='/app' id="categoryForm">
    <!-- 数据编辑区域 -->
    <table width="95%" class="edit_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px;">
        <!-- error message -->
        <s:if test="rtnMessage != null">
            <tr>
                <td colspan="2" align="center"><font color="red"><s:property
                        value='rtnMessage'/></font></td>
            </tr>
        </s:if>
        <tr>
            <th colspan="2" align="left" class="edit_title">类目选择</th>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类别：</th>
            <td width="80%">
                <s:select list="#request.categoryList" id="categoryId1" listKey="categoryId" listValue="categoryName"
                          headerKey="0" headerValue="--一级类目--"
                          onchange="change1('categoryId1','categoryId2');"></s:select>
                <select id="categoryId2" onChange="change1('categoryId2','categoryId3');">
                    <option value="0">--二级类目--</option>
                </select>
                <select id="categoryId3" name="product.categoryId">
                    <option value="0">--三级类目--</option>
                </select>
            </td>
        </tr>

        <tr>
            <th width="20%" align="right" class="eidt_rowTitle"><font color="red">*</font>产品类型：</th>
            <td width="80%">
                <s:radio list="#request.productTypeMap" listKey="key" listValue="value" id="productType"
                         name="product.productType" value="0"></s:radio>
            </td>
        </tr>

    </table>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30"
           border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
        <tr>
            <td align="center">
                <input type="button" value="下一步" class="btn-custom nextBtn" onclick="submitForm();"/>
                <input type="button" class="backBtn" onClick="gotoList()"/>
            <td width="20%" align="center"></td>
        </tr>
    </table>

    <br>
    <br>
</s:form>


</body>
</html>


