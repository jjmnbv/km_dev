<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.app.maps.ProductStatusMap" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品发布价格管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.form.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:set name="parent_name" value="'产品发布'" scope="request"/>
<s:set name="name" value="'价格及重量列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<form action="/app/productPriceShow.action?type=price" method="POST" namespace='/app' id="frm" name='frm'>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="100" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right">编码：</td>
            <td align="left">
                <s:textfield name="productForSelectPara.productNo" cssClass="input_style" id="productNo"/>
            </td>
            <td align="right">标题：</td>
            <td align="left">
                <s:textfield name="productForSelectPara.productTitle" cssClass="input_style" id="productName" size="32"/>
            </td>
            <td align="right">类别：</td>
            <td align="left">
                <s:select list="#request.categoryList"
                          name="productForSelectPara.bCategoryId" id="categoryId1" listKey="categoryId"
                          listValue="categoryName"
                          headerKey="" headerValue="--一级类目--"
                          onchange="change2('categoryId1','categoryId2');"></s:select>
                <s:select list="#request.mCategoryList"
                          name="productForSelectPara.mCategoryId" id="categoryId2" listKey="categoryId"
                          listValue="categoryName"
                          headerKey="" headerValue="--二级类目--"
                          onchange="change2('categoryId2','categoryId3');"></s:select>
                <s:select list="#request.sCategoryList" id="categoryId3"
                          headerKey="" headerValue="--三级类目--"
                          name="productForSelectPara.categoryId" listKey="categoryId"
                          listValue="categoryName"></s:select>
            </td>
        </tr>
        <tr>
            <td align="right">状态：</td>
            <td align="left">
                <s:select list="#request.productStatusMap" name="productForSelectPara.status"
                          id="productStatus" headerKey="" headerValue="--全部状态--"></s:select>
            </td>
            <td align="right">关键字：</td>
            <td align="left">
                <s:textfield cssClass="input_style" name="productForSelectPara.keyword" id="keyword" size="32"/>
            </td>
        </tr>
        <tr>
            <td align="right">品牌：</td>
            <td align="left">
                <s:select list="#request.productBrandMap" name="productForSelectPara.brandId"
                          id="brandId" headerKey="" headerValue="--全部品牌--"></s:select>
            </td>
            <td>
                <input type="submit" class="queryBtn" value=""/>
            </td>
        </tr>
    </table>

    <!-- 数据列表区域 -->
    <table id="mytable" width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0"
           border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                &nbsp;
            </th>
            <th align="center" width="15%">产品标题</th>
            <th align="center" width="10%">编码</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="8%">状态</th>
            <th align="center">商家名称</th>
            <th align="center">SKU信息</th>
            <th align="center" width="5%">操作</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="st">
            <tr>
                <td align="center" width="5%">
                        <s:property value="#st.index + 1"/>
                <td align="center"><s:property value="productTitle" escape="false"/></td>
                <td align="center"><s:property value="productNo"/></td>
                <td align="center"><s:property value="brandName"/></td>
                <td align="center"><s:property value="#request.productStatusMap[status]"/></td>
                <td align="center">
                    <s:property value="merchantName"/>
                </td>
                <td>
                    <table>
                        <tr>
                            <th>SKU编码</th>
                            <th>SKU描述</th>
                            <th>市场价</th>
                            <th>成本价</th>
                            <th>销售单价</th>
                            <th>重量</th>
                            <th>PV值</th>
                            <th>条形码</th>
                        </tr>
                        <s:iterator value="productSkus">
                            <tr>
                                <td><s:property value="productSkuCode"/></td>
                                <td><s:property value="skuAttrs"/></td>
                                <td><s:property value="markPrice"/></td>
                                <td><s:property value="costPrice"/></td>
                                <td><s:property value="price"/></td>
                                <td><s:property value="unitWeight"/></td>
                                <td><s:property value="pvValue"/></td>
                                <td><s:property value="skuBarCode"/></td>
                            </tr>
                        </s:iterator>
                    </table>
                </td>
                <td align="center">
                    <img title="修改价格" style="cursor: pointer;" src="/etc/images/little_icon/xiugai.png"
                         onclick="gotoUpdatePrice(<s:property value='id'/>,'<s:property value="productNo"/>','update')"/>
                    <img title="查看价格" style="cursor: pointer;" src="/etc/images/little_icon/jiage.png"
                         onclick="gotoUpdatePrice(<s:property value='id'/>,'<s:property value="productNo"/>','view')"/>
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

</form>
<s:if test='"priceSuccess".equals(type)'>
    <script language="JavaScript">
        <!--
        alert("修改价格成功!");
        //-->
    </script>
</s:if>
<s:if test='"priceFailFromOfficial".equals(type)'>
    <script language="JavaScript">
        <!--
        alert("系统发生错误，请稍后再试或联系管理员！");
        //-->
    </script>
</s:if>
</body>
<script type="text/javascript">
    function gotoUpdatePrice(id, productNo, arg) {
        if (arg == "update") {
            $.post("/app/findSameProductFromDraft.action",
                    {productId: id},
                    function (data) {
                        if (data == 1) {
                            alert("该产品已在草稿中存在记录，请到草稿中修改！");
                            return;
                        } else if (data == 2) {
                            if (!confirm("该产品的价格已在草稿中存在记录，是否继续？\r\n继续则会覆盖草稿中的价格！")) {
                                return;
                            }
                        }
                        document.getElementById("frm").action = "/dire/findSingleProduct.action?productId=" + id + "&productNo=" + productNo + "&type=" + arg;
                        document.getElementById("frm").submit();
                    }
            );
        } else {
            document.getElementById("frm").action = "/dire/findSingleProduct.action?productId=" + id + "&productNo=" + productNo + "&type=" + arg;
            document.getElementById("frm").submit();
        }
    }
</script>
</html>