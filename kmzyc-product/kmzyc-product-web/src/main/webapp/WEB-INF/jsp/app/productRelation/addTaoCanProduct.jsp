<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>列表选择SKU码</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <style type="text/css">
        body {
            padding: 0px;
            margin: 0px;
        }
        table {
            margin-left: 10px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:form action="/productRelation/addOtherTypeProductRelationTaoCan.action"
        method="post" namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" id="mainSkuId" value="<s:property value="productTied.productSkuId"/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <tr>
            <td>产品SKU编号：&nbsp;
                <s:textfield name="productTied.productSkuCode" cssClass="input_style" id="productNo"/>
            </td>
            <td></td>
            <td> 类别：&nbsp;&nbsp; &nbsp;&nbsp;
                <s:select name="bCategoryId" list="#request.categoryList" id="categoryId1" listKey="categoryId"
                          listValue="categoryName"
                          headerKey="" headerValue="--一级类目--"
                          onchange="change2('categoryId1','categoryId2');">
                </s:select>
                <s:select name="mCategoryId" list="#request.mCategoryList" id="categoryId2" listKey="categoryId"
                          listValue="categoryName"
                          headerKey="" headerValue="--二级类目--"
                          onchange="change2('categoryId2','categoryId3');">
                </s:select>
                <s:select name="productTied.categoryId" list="#request.sCategoryList" id="categoryId3"
                          listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--三级类目--">
                </s:select>
            </td>
        </tr>
        <tr>
            <td>产品标题：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:textfield type="text" name="productTied.productTitle" cssClass="input_style" id="productName"/>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td>
                <input type="hidden" name="productTied.productSkuId" value="<s:property value='productTied.productSkuId'/>"/>
                <input type="hidden" name="relationId" value="<s:property value='relationId'/>"/>
                <input type="hidden" name="relationType" value="<s:property value='relationType'/>"/>
                <input type="hidden" name="productTied.supplierCode" value="<s:property value='productTied.supplierCode'/>"/>
                <input type="hidden" name="supplierTyes" value="<s:property value='supplierTyes'/>"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="button" class="btngreen" value="保存所选 " onclick="selectList();"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" onclick="doSearch()" class="btngray" value=" 查询 "/>
            </td>
            <td></td>
            <td></td>
        </tr>
    </table>
    <br/>
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>
            </th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="15%">产品标题</th>
            <th align="center" width="10%">SKU描述</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">状态</th>
            <th align="center" width="10%">销售价格</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="stuts">
            <tr>
                <td align="center" width="5%">
                    <input type="checkbox" name="productIdChk">
                    <div style='display:none'><s:property value="productNo"/>^<s:property value="productSkuCode"/>^<s:property value="productTitle"/>^<s:property value="prodBrand.brandName"/>^<s:property value="channel"/>^<s:property value="price"/>^<s:property value="productSkuId"/>^<s:property value="productId"/>^<s:iterator value="productSkus" id="pro_sku"><s:if test="#productiterator.productSkuCode == #pro_sku.productSkuCode"><s:property value="skuAttrs"/></s:if></s:iterator>^<s:iterator value="#request.productRelationStatusMap"><s:if test="status==key"><s:property value="value"/></s:if></s:iterator></div>
                </td>
                <input type="hidden" name="productRelationSku"/>
                <input type="hidden" name="productRelationPrice"/>
                <input type="hidden" name="productRelationType"/>
                <td align="center" style="word-break: break-all">
                    <s:property value="productSkuCode"/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='productTitle'/>
                </td>
                <td>
                    <s:iterator value="productSkus" id="pro1">
                        <s:if test="#productiterator.productSkuCode == #pro1.productSkuCode">
                            <s:property value="skuAttrs"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='prodBrand.brandName'/>
                </td>
                <td align="center" style="word-break: break-all" name="productRelationStatus">
                    <s:iterator value="#request.productStatusMap">
                        <s:if test="status==key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value="price"/>
                    <input type="hidden" name="productOldPriceRe" value="<s:property value='price'/>"/>
                </td>
                <input type="hidden" name="relationID"/>
            </tr>
        </s:iterator>
    </table>
    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>
    <br/>
    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        alert(msg);
        if (msg) {
            parent.closeOpenSku();
        }
    </script>
</s:if>
<script type="text/javascript">
    function doSearch() {
        document.frm.submit();
    }

    function selectList() {
        if ($("input:checked").length == 0) {
            alert("未勾选套餐的产品");
            return false;
        }
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
            var skuId = str[6];
            var productSkuCode = str[1];
            for (var i = 0; i < sizeI; i++) {
                if (parent_productSkuIds[i] == skuId) {
                    alert('SKU编号为：' + productSkuCode + '的产品重复，不能添加!');
                    return;
                }
            }
            str[9] = $(this).parent().parent().find("td[name='productRelationStatus']").html();
            params.push(str);
        });

        parent.closeOpenSku(params);
    }

    function checkedBox() {
        var parent_productSkuIds = new Array();
        var obj = parent.document.getElementsByName("skuIds");
        var size = obj.length;
        for (var i = 0; i < size; i++) {
            parent_productSkuIds.push(obj[i].value);
        }
        var sizeI = parent_productSkuIds.length;
        $("input[type='checkbox']").each(function (i) {
            var str = $(this).parent().children("div").text().split('^');
            var productId = str[7];
            var skuId = str[6];
            for (var i = 0; i < sizeI; i++) {
                if (parent_productSkuIds[i] == skuId) {
                    $(this).attr("checked", "checked");
                }
            }
        });
    }
    checkedBox();
</script>
</body>
</html>