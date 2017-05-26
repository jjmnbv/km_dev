<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
    <title>添加新品牌</title>
</head>
<body>
<form action="" id="freightFrm" method="post">
    <s:token></s:token>
    <s:hidden name="viewProductSku.productId"></s:hidden>
    <div style="height: 400px;">
        <table width="90%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1"
               bordercolor="#C7D3E2" style="border-collapse: collapse">
            <tr>
                <th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">基本信息</th>
            </tr>
            <tr>
                <th width="20%" align="right" class="eidt_rowTitle">产品名称：</th>
                <td width="80%">
                    <s:property value="viewProductSku.procuctName"/>
                    <input name="productSku.productSkuId" type="hidden" value="<s:property value="skuId" />"/>
                </td>
            </tr>
            <tr>
                <th width="20%" align="right" class="eidt_rowTitle">SKU编码：</th>
                <td width="80%">
                    <s:property value="viewProductSku.productSkuCode"/>
                </td>
            </tr>
            <tr>
                <th width="20%" align="right" class="eidt_rowTitle">SKU信息：</th>
                <td width="80%">
                    <s:iterator value="#request.attrList">
                        <s:property value="categoryAttrName"/>：<b><s:property value="categoryAttrValue"/></b>&nbsp;&nbsp;&nbsp;
                    </s:iterator>
                </td>
            </tr>
            <tr>
                <th width="20%" align="right" class="eidt_rowTitle">价格：</th>
                <td width="80%">
                    成本价：<b><s:property value="viewProductSku.skuCostPrice"/></b>&nbsp;&nbsp;&nbsp;
                    市场价：<b><s:property value="viewProductSku.markPrice"/></b>&nbsp;&nbsp;&nbsp;
                    销售单价：<b><s:property value="viewProductSku.price"/></b>&nbsp;&nbsp;&nbsp;
                    重量：<b><s:property value="viewProductSku.unitWeight"/></b>&nbsp;&nbsp;&nbsp;
                </td>
            </tr>
            <tr>
                <th style="background-color: #def2fa;" align="left" class="eidt_rowTitle" colspan="2">编辑商品运费</th>
            </tr>
            <tr>
                <th width="30%" align="right" class="eidt_rowTitle">
                    是否免邮：
                </th>
                <td width="70%" align="left">
                    <s:radio list="#request.freeStatusMap" name="viewProductSku.freeStatus"></s:radio>
                </td>
            </tr>
            <tr>
                <th width="30%" align="right" class="eidt_rowTitle">
                    请填写运费：
                </th>
                <td width="70%" align="left">
                    <s:textfield id="freight" maxlength="7" name="viewProductSku.freight"></s:textfield>
                </td>
            </tr>
        </table>
        <table width="90%" class="edit_bottom" align="center" cellpadding="0" cellspacing="0" border="0">
            <tr align="center">
                <td align="center">
                    <input id="submitButton" onclick="submitFreightFrm();"
                           class="btnStyle" style="height: 30px;" type="button" value="保存"/>
                    &nbsp;&nbsp;
                    <input class="btnStyle" type="button" value="返回"
                           style="height: 30px;" onClick="gotoListForView();"/>
                </td>
            </tr>
        </table>
    </div>
</form>

<script type="text/javascript">

    $(document).keypress(function (e) {
        if (e.which == 13) {
            e.preventDefault();
            $("#submitButton").click();
        }
    });

    $(document).ajaxStart(function () {
        $.blockUI({
            message: '<img src="/etc/images/waiting.gif" style="vertical-align:middle;height:32px;" /><b style="font-size:25px;vertical-align:middle;">正在修改，请稍后...</b>',
            css: {width: '40%'}
        });
    });
    $(document).ajaxComplete(function () {
        $.unblockUI();
    });

    if ($("input[type='radio'][name='viewProductSku.freeStatus']:checked").val() == 1) {
        $("#freight").attr("disabled", true);
    }

    $("input[type='radio'][name='viewProductSku.freeStatus']").change(function () {
        if (this.value == "1") {
            $("#freight").attr("disabled", true);
        } else {
            $("#freight").attr("disabled", false);
        }
    });

    function submitFreightFrm() {
        if (!$("#freight").attr("disabled")) {
            if ($("#freight").val() == "") {
                alert("请填写运费！");
                $("#freight").select();
                return;
            } else {
                var priceTest = /^\d+(\.?\d{1,2})?$/;
                if (!priceTest.test($("#freight").val())) {
                    alert("请输入正确的数字，且请不要超过2位小数！");
                    $("#freight").select();
                    return;
                }
            }
        }
        $.post(
                "/app/saveSkuFreight.action",
                $("#freightFrm").serializeArray(),
                function (msg) {
                    if ("1" === msg) {
                        alert("操作成功！");
                        parent.saveSkuFreight();
                    } else {
                        alert("系统发生异常，请稍后再试或联系管理人员！");
                    }
                }
        );
    }

    function gotoListForView() {
        parent.closeThis();
    }

</script>
</body>
</html>