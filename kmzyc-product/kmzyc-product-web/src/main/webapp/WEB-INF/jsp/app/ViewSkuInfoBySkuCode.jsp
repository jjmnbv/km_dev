<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css"/>
    <link rel="stylesheet" href="/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/etc/js/product_add.js"></script>
    <script type="text/javascript" src="/etc/js/kindeditor_add.js"></script>
    <script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default"
            type="text/javascript"></script>
    <script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js"
            type="text/javascript"></script>
    <script language="JavaScript" src="/etc/js/jquery.blockUI.js" type="text/javascript"></script>
    <script language="JavaScript" src="/etc/js/dialog-common.js" type="text/javascript"></script>

    <title>产品发布</title>
</head>
<body>
<div style="position:absolute;align:center;top:20px;left:30px">
    <s:form action="/app/productAdd.action" enctype="multipart/form-data" method="POST" id="frm" name='frm'>
        <s:token></s:token>
    <input type="hidden" name="skuCode" value="<s:property value='skuCode'/>"/>
    <input type="hidden" name="index" value="<s:property value='tabNum'/>"/>
    <input type="hidden" id="stockOutNotExsist" value="<s:property value='stockOutNotExsist' />"/>

    <div id="content" style="width:570px">
        <div class="ct">
            <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1"
                   bordercolor="#C7D3E2" style="border-collapse: collapse">
                <tr>
                    <th colspan="3" align="center" class="edit_title"> 价格信息</th>
                </tr>
                <tr>
                    <th align="center" width="40%">商品信息</th>
                    <th align="center" width="30%">市场价</th>
                    <th align="center" width="30%">销售单价</th>
                </tr>

                <tr>
                    <td align="left">
                        <input type="hidden" name="productSkuId"
                               value="<s:property value="viewProductSku.productSkuId" />"/>
                        <input type="hidden" id="skuPrice<s:property value="viewProductSku.productSkuId" />"
                               name="skuPrice" value="<s:property value="price"/>"/>
                        <input type="hidden" id="skuMarkPrice<s:property value="viewProductSku.productSkuId" />"
                               name="skuMarkPrice" value="<s:property value="markPrice"/>"/>
                        <b>商品名称</b>：<s:property value='viewProductSku.procuctName'/>&nbsp;&nbsp;
                        <s:iterator value="viewSkuAttrs">
                            <b><s:property value="viewProductSkucategoryAttrName"/></b>：<s:property
                                value="viewProductSku.categoryAttrValue"/>&nbsp;&nbsp;
                        </s:iterator>
                    </td>
                    <td align="right" class="<s:property value="viewProductSku.productSkuId" />"><s:if
                            test='viewProductSku.markPrice != null'><s:property
                            value="%{getText('{0,number,##.##}',{viewProductSku.markPrice})}"/></s:if><s:else>暂无价格</s:else></td>
                    <td align="right" class="<s:property value="viewProductSku.productSkuId" />"><s:if
                            test='viewProductSku.price != null'><s:property
                            value="%{getText('{0,number,##.##}',{viewProductSku.price})}"/></s:if><s:else>暂无价格</s:else></td>
                </tr>
                <tr>
                    <td colspan="3" align="center">
                        <input type="button" value="查看图片" class="btnStyle"
                               onClick="skuImageView(<s:property value='viewProductSku.productSkuId'/>)"/></td>
                </tr>
            </table>
        </div>
        </s:form>
    </div>
</body>
<script type="text/javascript">
    function skuImageView(productSkuId) {
        popDialog("/basedata/findSomeImageBySkuId.action?productSkuId=" + productSkuId, "查看图片", 388, 333);
    }
    $(document).ready(
        function () {
            var index = $("input[name='index']").val();
            $("li:eq(" + index + ")").click();
        }
    );

    var stockOutNotExsist = $("#stockOutNotExsist").val();
    if (stockOutNotExsist == 1) {
        alert("资料信息不存在");
        parent.closeThis();
    }
</script>
</html>
