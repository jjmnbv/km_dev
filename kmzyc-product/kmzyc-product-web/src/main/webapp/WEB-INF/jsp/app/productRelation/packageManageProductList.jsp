<%@page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>套餐管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
    <script src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
    <script src="/etc/js/jquery.blockUI.js" type="text/javascript"></script>
    <script src="/etc/js/dialog-common.js" type="text/javascript"></script>
</head>
<body>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'套餐管理'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/productRelation/packageManage.action" method="POST" namespace="productRelation" id="frm" name='frm'>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="100"
           cellpadding="0" cellspacing="0">
        <tr>
            <td>产品编号：
                <s:textfield name="productTied.productNo" cssClass="input_style" id="productNo"/></td>
            <td>标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:textfield type="text" name="productTied.procuctName" cssClass="input_style" id="productName"/>
            </td>
            <td>状态：&nbsp;&nbsp;&nbsp;
                <s:select list="#request.productStatusMap" name="productTied.status" id="productStatus"
                          headerKey="" headerValue="--全部状态--"></s:select>
            </td>
        </tr>
        <tr>
            <td> 类别：&nbsp;&nbsp; &nbsp;&nbsp;
                <s:select name="productTied.bCategoryId" list="#request.categoryList" id="categoryId1"
                          listKey="categoryId" listValue="categoryName"
                          headerKey="0" headerValue="--一级类目--"
                          onchange="change1('categoryId1','categoryId2');">
                </s:select>
                <s:select name="productTied.mCategoryId" list="#request.mCategoryList" id="categoryId2"
                          listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--二级类目--"
                          onchange="change1('categoryId2','categoryId3');">
                </s:select>
                <s:select name="productTied.categoryId" list="#request.sCategoryList" id="categoryId3"
                          listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--三级类目--">
                </s:select>
            </td>
            <td>关键字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:textfield type="text" cssClass="input_style" name="productTied.keyword" id="keyword"/></td>
        </tr>
        <tr>
            <td> 品牌： &nbsp;&nbsp;&nbsp;&nbsp;
                <s:select list="#request.productBrandMap" id="brandId" headerKey="0" style="width:143px"
                          name="productTied.brandId" headerValue="--全部品牌--"></s:select>
            </td>
            <td>产品SKU编号：
                <s:textfield type="text" cssClass="input_style"  name="productTied.productSkuCode" id="keyword"/></td>
            <td>是否有套餐：
                <s:select list="#request.packageMap"
                          name="productTied.relationType" id="relationType" headerKey="" style="width:111px"
                          headerValue="--全部商品--"></s:select>
            </td>
        </tr>
        <tr>
            <td><INPUT type="submit" class="queryBtn" value="">
        </tr>
    </table>
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="15%">产品编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品标题</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">关键字</th>
            <th align="center" width="15%">状态</th>
            <th align="center" width="15%">操作</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList">
            <tr>
                <td align="center"><s:property value="productNo"/></td>
                <td align="center"><s:property value="productSkuCode"/></td>
                <td align="center"><s:property value="procuctName"/></td>
                <td align="center"><s:property value="brandName"/></td>
                <td align="center"><s:property value="keyword"/></td>
                <td align="center">
                    <s:iterator value="#request.productStatusMap">
                        <s:if test="status==key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center">
                        <%--  <img title="添加关联" style="cursor: pointer;"
                        src="/etc/images/button_new/submit_audit.png"
                        onclick="addProductRelation(<s:property value='productSkuId' />);" />
                        --%>
                    <img title="套餐查询" style="cursor: pointer;"
                         src="/etc/images/view.png"
                         onclick="queryRelationPackage(<s:property value='productSkuId'/>,
                                 '<s:property value='procuctName'/>');"/>
                </td>
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
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        alert(document.getElementById("rtnMsg").value);
    </script>
</s:if>
</body>

<script type="text/javascript">
    function gotoAdd(mainSku) {
        location.href = "/tiedSale/tiedSaleAdd.action?productTied.productSkuId=" + mainSku;
    }

    function gotoView(id) {
        location.href = "/tiedSale/tiedSaleView.action?tiedSade.mainSku=" + id;
    }

    function popSelectProductSku(trId) {
        dialog("查看所有SKU商品", "iframe:/productRelation/relationQueryProduct.action?productTied.productSkuId="
                + trId, "800px", "500px", "iframe", 70);
    }

    function queryPackage(mainId) {
        dialog("查看主产品关联组合", "iframe:/productRelation/queryPackage.action?productRelation.mainSkuId="
                + mainId, "800px", "500px", "iframe", 70);
    }

    //17.查找sku产品后，回来的参数值
    function closeOpenSku() {
        closeThis();
        location.href = "/productRelation/skuProductQuery.action";
    }

    function addProductRelation(trId) {
        myDialog = art.dialog.open('/productRelation/relationQueryProduct.action?productTied.productSkuId=' + trId, {
            title: '添加关联产品',
            width: 900,
            height: 500,
            drag: false,
            close: function () {
                $.unblockUI();
            }
        });

        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({message: ""});
    }

    function queryRelationPackage(mainId, procuctName) {
        $('#frm').attr("action", '/productRelation/queryPackageDetail.action?productRelation.mainSkuId=' + mainId + "&procuctName=" + procuctName);
        $('#frm').submit();
    }

    document.onkeydown = keyListener;
    function keyListener(e) {
        e = e ? e : event;
        if (e.keyCode == 13) {
            document.forms[0].submit();
        }
    }
</script>
</html>