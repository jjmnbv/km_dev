<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
    <style type="text/css">
        .tableStyle1 {
            font-size: 12px;
        }
        .content_table {
            background-color: #f5f5f5;
            border: 1px solid #e3e3e3;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
            overflow: hidden;
            padding: 6px;
        }
        input[type=text] {
            border: #C3C3C3 1px solid;
            margin-top: 0;
            line-height: 16px;
            height: 28px;
            width: 150px;
        }
    </style>
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:form action="/app/findProductForSections.action" method="POST" namespace='/app'
        id="frm" name='frm'>
    <s:hidden name="pre_sectionId"/>
    <s:hidden name="type"/>

    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" height="100" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right">编码：</td>
            <td align="left">
                <s:textfield name="product.productNo" cssClass="input_style" id="productNo"/>
            </td>
            <td align="right">类别：</td>
            <td align="left" colspan="4">
                <s:select list="#request.categoryList"
                          name="product.bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--一级类目--"
                          onchange="change2('categoryId1','categoryId2');"></s:select>
                <s:select list="#request.mCategoryList"
                          name="product.mCategoryId" id="categoryId2" listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--二级类目--"
                          onchange="change2('categoryId2','categoryId3');"></s:select>
                <s:select list="#request.sCategoryList" id="categoryId3"
                          headerKey="" headerValue="--三级类目--"
                          name="product.categoryId" listKey="categoryId" listValue="categoryName"></s:select>
            </td>
        </tr>
        <tr>
            <td align="right">名称：</td>
            <td align="left"><s:textfield name="product.name"
                                          cssClass="input_style" id="productName" size="32"/></td>
            <td align="right">品牌：</td>
            <td align="left"><s:select list="#request.productBrandMap"
                                       name="product.brandId" id="brandId" headerKey=""
                                       headerValue="--全部品牌--"></s:select></td>
            <td align="right">关键字：</td>
            <td align="left">
                <s:textfield cssClass="input_style" name="product.keyword" id="keyword" size="32"/>
            </td>
            <td align="right"></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right">&nbsp;</td>
            <td align="left"></td>
            <td>&nbsp;</td>
            <td></td>
            <td align="right"></td>
            <td align="right">
                <input type="button" onClick="doSearch()" style="height:30px" class="btngreen" value="查询"/>
                <input type="button" class="btngreen" style="height:30px" value="保存所选 " onClick="selectList();"/>
            </td>
        </tr>
    </table>


    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>
            </th>
            <th align="center" width="15%">产品名称</th>
            <th align="center" width="15%">编码</th>
            <th align="center" width="15%">SKU编码</th>
            <th align="center" width="15%">品牌</th>
            <th align="center" width="15%">关键字</th>
            <th align="center" width="15%">状态</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList">
            <tr>
                <td align="center" width="5%">
                    <input type="checkbox" name="productIdChk" value=''/>
                    <div style='display:none'><s:property value="productId"/>^<s:property value="name"/>^<s:property value="productNo"/>^<s:property value="#request.productBrandMap[brandId]"/>^<s:property value="channel"/>^<s:property value="productskuId"/>^<s:property value="productSkuCode"/></div>
                </td>
                <td align="center"><s:property value="name"/></td>
                <td align="center"><s:property value="productNo"/></td>
                <td align="center"><s:property value="productSkuCode"/></td>
                <td align="center"><s:property value="#request.productBrandMap[brandId]"/></td>
                <td align="center"><s:property value="keyword"/></td>
                <td align="center"><s:property value="#request.productStatusMap[status]"/></td>
            </tr>
        </s:iterator>
    </table>

    <!-- 分页按钮区 -->
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>
    <br>
    <br>
</s:form>

<script type="text/javascript">
    function doSearch() {
        document.getElementById('pageNo').value = 1;
        document.forms['frm'].submit();
    }

    function selectList() {
        var parent_productSkuIds = new Array();
        var obj = parent.document.getElementsByName("skuIds");
        var size = obj.length;
        for (var i = 0; i < size; i++) {
            parent_productSkuIds.push(obj[i].value);
        }

        var sizeI = parent_productSkuIds.length;
        var params = new Array();

        $("input[type='checkbox'][name='productIdChk']:checked").each(function (i) {
            var str = $(this).parent().children("div").text().split('^');//$(this).val().split('^');
            var skuId = str[5];
            var productSkuCode = str[6];
            for (var i = 0; i < sizeI; i++) {
                if (parent_productSkuIds[i] == skuId) {
                    //alert('SKU编号为：'+productSkuCode+'的产品重复，不能添加!');
                    return;
                }

            }

            params.push(str);
        });
        parent.closeOpenSku(params);
    }

    function checkedBox() {
        var parent_productSkuIds = new Array();
        //var obj = parent.document.getElementsByName("productIds");
        var obj = parent.document.getElementsByName("skuIds");
        var size = obj.length;
        for (var i = 0; i < size; i++) {
            parent_productSkuIds.push(obj[i].value);
        }

        var sizeI = parent_productSkuIds.length;

        $("input[type='checkbox']").each(function (i) {
            var str = $(this).parent().children("div").text().split('^');//$(this).val().split('^');
            var productId = str[0];
            var skuId = str[5];
            for (var i = 0; i < sizeI; i++) {
                if (parent_productSkuIds[i] == skuId) {
                    $(this).attr("checked", "checked");
                }
            }
        });
    }

    checkedBox();
</script>

</BODY>
</HTML>
