<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加产品关联</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css"/>
    <link href="/etc/js/warehouse/distributionInfo.js"/>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type='text/javascript' src='/etc/js/dialog-common.js'></script>
    <script type='text/javascript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default'></script>
    <script type='text/javascript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js'></script>
    <script type='text/javascript' src='/etc/js/jquery.blockUI.js'></script>
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
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'产品关联'" scope="request"/>
<s:set name="son_name" value="'添加产品关联'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<br/>

<s:form action="/productRelation/saveProductRelation.action"
        method="post" namespace='/productRelation' id="relationfrm" name='relationfrm'>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <tr>
            <td>
                <input type="button" value="新增产品 " onclick="addProduct()"/>
            </td>
        </tr>
        <tr>
            <input type="hidden" name="productRelation.mainSkuId" value="<s:property value='productTied.productSkuId' />"/>
            <input type="hidden" name="productRelation.editable" value="1"/>
            <input type="hidden" name="productRelation.mainSkuPrice" value="<s:property value='mainSkuPrice' />"/>
            <td width="432px">主产品价格：&nbsp;&nbsp;&nbsp;<s:property value="mainSkuPrice"/>
            </td>
            <td>关联名称：<s:textfield name="productRelation.relationName" cssClass="input_style"
                                  reg="^(\S{0,10})$" tip="备注不要超过10个汉字" id="productName"></s:textfield>
            </td>
        </tr>
        <tr>
            <td>关联类型 ：
                <s:iterator value="#request.productRelationType">
                    <s:if test="key==1">
                        <input type="radio" name="productRelation.relationType" checked="checked"
                               value="<s:property value="key"  />"/>
                    </s:if>
                    <s:else>
                        <input type="radio" name="productRelation.relationType" value="<s:property value="key"  />"/>
                    </s:else>
                    <s:property value="value"/>
                </s:iterator>
            </td>
        </tr>
    </table>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
        <tr>
            <th align="center" width="5%"><input type="checkbox" onclick="checkAll(this)"></th>
            <th align="center" width="12%">产品编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品标题</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">状态</th>
            <s:if test="relationType==0">
                <th align="center" width="9%">原价格</th>
            </s:if>
            <th align="center" width="8%">价格</th>
            <s:if test="relationType==0">
                <th align="center" width="5%">更新价格</th>
            </s:if>
            <s:if test="relationType==0">
                <th align="center" width="7%">操作</th>
            </s:if>
        </tr>
    </table>
    <br/>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="bottombuttonbar" height="30"
           border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
                <input class="saveBtn" TYPE="button" onClick="saveList()" value="">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="goToBack()"/></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" name="totalPrice" value="<s:property value='totalPrice'/>"/>
</s:form>

<s:form action="/app/saveSectionsDetail.action" method="POST" id="saveForm">
</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <SCRIPT language="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        if (msg == "success") {
            alert("保存成功");
        } else {
            alert("保存失败");
        }

        if (msg) {
            parent.closeOpenSku();
        }
    </script>
</s:if>


<script type="text/javascript">
    function doSearch() {
        document.frm.submit();
    }

    function selectList(theButton) {
        var chekcLength = $("input[name='checkSkuId']:checked").length;
        if (chekcLength == 0) {
            alert("请选择关联的产品");
            return false;
        }
        var boxlist = $("input[name='checkSkuId']:checked");
        var count = 0;
        boxlist.each(function (i) {
            var productRelationSkuId = $(this).val();
            $(this).parent().parent().find(
                    "input[name='productRelationSku']").attr("name",
                    "list[" + i + "]." + "relationSkuId").attr("value",
                    productRelationSkuId);
            var productRelationPrice = $(this).parent().parent().find(
                    "input[name='relationProductPrice']").val();
            if (productRelationPrice) {
                count = count + 1;
            }
            $(this).parent().parent().find(
                    "input[name='productRelationPrice']").attr("name",
                    "list[" + i + "]." + "relationSkuPrice").attr("value",
                    productRelationPrice);
            $(this).parent().parent().find(
                    "input[name='productRelationType']").attr("name",
                    "list[" + i + "]." + "relationDetailType").attr(
                    "value", 0);
        });

        if (count == 0) {
            alert("请为关联的产品输入价格");
            return false;
        }

        var productRelationRelationName = $("input[name='productRelation.relationName']").val();
        var mainPriceInput = $("input[name='productRelation.mainSkuPrice']").val();
        if (mainPriceInput.length == 0) {
            alert("请输入主产品价格");
            return false;
        }

        if (productRelationRelationName.length == 0) {
            alert("请输入关联名称");
            return false;
        }
        $("#relationfrm").submit();
    }

    function addScript() {
        //	重新加载js,验证出现错误提示用的
        var oHead = document.getElementsByTagName('HEAD').item(0);
        var oScript = document.createElement("script");
        oScript.type = "text/javascript";
        oScript.src = "/etc/js/validate/easy_validator.pack.js";
        oHead.appendChild(oScript);
    }

    function checkBoxClick(self) {
        if ($(self).attr("checked") == true) {
            $(self).parent().parent().find("input[name='relationProductPrice']").replaceWith("   <input type='text'  name='relationProductPrice'   size='5' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' tip='范围[1-1000000]'     />");
            //	重新加载js,验证出现错误提示用的
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript = document.createElement("script");
            oScript.type = "text/javascript";
            oScript.src = "/etc/js/validate/easy_validator.pack.js";
            oHead.appendChild(oScript);
        } else {
            $(self).parent().parent().find("input[name='relationProductPrice']").replaceWith(
                    " <input type='text'  name='relationProductPrice' size='5'  />");
            //	重新加载js,验证出现错误提示用的
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript = document.createElement("script");
            oScript.type = "text/javascript";
            oScript.src = "/etc/js/validate/easy_validator.pack.js";
            oHead.appendChild(oScript);
        }
    }

    $(document).ready(
        function () {
            $("input[type='radio']").click(
                function () {
                    if ($(this).val() != 0) {
                        var p = $("#oldMainPrice")[0].innerHTML;
                        $("#mainSkuPrice").replaceWith("<input type='text' readonly='readonly' name='productRelation.mainSkuPrice'  value=" + p + "   />");
                        $("input[name='checkSkuId']").each(
                            function (i) {
                                var checkBoxValue = $(this).val();
                                var productRelationPrice = $(this).parent().parent()
                                        .find("input[name='productOldPriceRe']").val();
                                $(this).parent().parent().find("input[name='relationProductPrice']").val(productRelationPrice);
                                var priceI = $(this).parent().parent().find("input[name='relationProductPrice']");
                                priceI.hide();
                                priceI.parent().hide();
                                //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                                $(this).replaceWith("<input type='checkbox' name='checkSkuId' value=" + checkBoxValue + "/>");
                            }
                        );
                        $("#relationProductSkuPrice").hide();
                    } else {
                        $("input[name='productRelation.mainSkuPrice']").replaceWith("<input type='text' id='mainSkuPrice' tip='请输入1-1000000的整数' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' onblur='addScript();' name='productRelation.mainSkuPrice'/>");
                        //	重新加载js,验证出现错误提示用的
                        var oHead = document.getElementsByTagName('HEAD').item(0);
                        var oScript = document.createElement("script");
                        oScript.type = "text/javascript";
                        oScript.src = "/etc/js/validate/easy_validator.pack.js";
                        var oScript1 = document.createElement("script");
                        oHead.appendChild(oScript);
                        $("input[name='checkSkuId']").each(
                            function (i) {
                                var priceI = $(this).parent().parent().find("input[name='relationProductPrice']");
                                priceI.parent().show();// 注意这一行与下面一行的次序不能调换
                                //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                                priceI.replaceWith("	<input type='text' name='relationProductPrice' size='5' />");
                                var cKValue = $(this).val();
                                $(this).replaceWith(" <input type='checkbox' name='checkSkuId' onclick='checkBoxClick(this)' value=" + cKValue + "/>");

                            }
                        );
                        $("#relationProductSkuPrice").show();
                    }
                }
            );
        }
    );

    function firstPage() {
        document.getElementById('pageNo').value = 1;
        goPage();
    }
    function lastPage() {
        document.getElementById('pageNo').value =<s:property value='page.pageCount'/>;
        goPage();
    }
    function priorPage() {
        document.getElementById('pageNo').value = parseInt(document.getElementById('pageNo').value) - 1;
        goPage();
    }
    function nextPage() {
        document.getElementById('pageNo').value = parseInt(document.getElementById('pageNo').value) + 1;
        goPage();
    }
    function goPage() {
        var s = document.getElementById('pageNo').value;
        var typeValue = $("input[name='productRelation.relationType']:checked ").val();
        if (typeValue == 0) {
            document.forms[0].action = "/productRelation/relationQueryProduct.action?page.pageNo=" + s;
            document.forms[0].submit();
        } else {
            var typeValueNum = Number(typeValue);
            document.forms[0].action = "/productRelation/relationQueryProduct.action?page.pageNo=" + s + "&productRelation.relationType=" + typeValueNum;
            document.forms[0].submit();
        }
    }


    /**
     *以下函数用于记录的选择
     */
    function checkCkSelected(oForm) {
        for (var i = 0; i < oForm.all.tags("input").length; i++) {
            var ele = oForm.all.tags("input")[i];
            var ct = ele.getAttribute("type");
            if ((ele.type == "checkbox") && (ele.checked == true))
                return true;
        }
        return false;
    }

    function checkAll(ck) {
        var inputs = ck.form.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            var ele = inputs[i];
            if ((ele.type == "checkbox")) {
                if (ck.checked != ele.checked)
                    ele.click();
            }
        }
    }

    function checkSelected(sName) {
        var chs = document.getElementsByName(sName);
        for (var i = 0; i < chs.length; i++) {
            var ele = chs[i];
            if (ele.type == "checkbox" && ele.checked == true)
                return true;
        }
        return false;
    }

    function deleteSelected(sName) {
        if (!checkSelected(sName)) {
            alert("请选择要删除的数据！");
            return false;
        }
        if (confirm("你确定要删除选中的数据？")) {
            doDelete(sName);
        }
    }

    function outlineMyRow(ckr) {
        var otr = ckr.parentElement.parentElement;
        if (otr.tagName.toUpperCase() == "TR") {
            if (ckr.checked == true) {
                ckr.ocls = otr.className;
                otr.className = "select";
            } else {
                otr.className = ckr.ocls;
            }
        }
    }


    function addProduct() {
        var mainSkuId = $("input[name='productRelation.mainSkuId']").val();
        var pageNum = $("#pageNum").val();
        var url = "/productRelation/addOtherTypeProductRelation.action?productTied.productSkuId=" + mainSkuId +
                "&pageNum=" + pageNum;
        myDialog = art.dialog.open(url, {
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
        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({message: ""});
    }


    $(document).ready(
        function () {
            var typeValue = $("input[name='relationType']").val();
            if (typeValue != 0) {
                $("input[name='productRelation.relationType'][value=" + typeValue + "]").click();
            }
        }
    );

    function closeOpenSku(params) {
        var productId;
        var productName;
        var productNo;
        var skuId;
        var productSkuCode;
        var brandName;
        var price;
        var sizeI = params.length;
        for (var i = 0; i < sizeI; i++) {
            var str = params[i];
            productNo = str[0];
            productSkuCode = str[1];
            productName = str[2];
            brandName = str[3];
            price = str[5];
            skuId = str[6];
            productId = str[7];
            productRelationStatus = str[8];
            var html = '<tr><input type="hidden"  name="productRelationSku1"/><input type="hidden"  name="productRelationPrice1"  /><input type="hidden"   name="productRelationType1"  /><input type="hidden"  name="relationID1"  />		<td align="center"><input type="checkbox" id="productsDetailId"  name="sectionsDetailIds" value="0" /></td>';
            html += '<td  align="center">' + productNo + '</td>';
            html += '<td align="center">' + productSkuCode + '</td>';
            html += '<td align="center">' + productName + '</td><input type="hidden" name="sectionsDetail.productId" id="productId" value="' + productId + '"/>';
            html += '<td align="center">' + brandName + '</td>';
            html += '<input type="hidden" name="skuId" id="skuId" value="' + skuId + '"/>';
            //html += '<td align="center">'+'<s:iterator value="#request.productRelationStatusMap"  > <s:if test="'+productRelationStatus+'==key"  > <s:property   value="value"   /> </s:if></s:iterator>'+'</td>';
            html += '<td align="center">' + productRelationStatus + '</td>';
            html += '<td align="center">' + price + '</td><input type="hidden" name="price" id="price" value="' + price + '"/></tr>';

            var hiddenStr = '<input type="hidden" name="productIds" value="' + productId + '" />';
            hiddenStr += '<input type="hidden" name="skuIds" value="' + skuId + '" />';

            $('#saveForm').append(hiddenStr);
            $('#sectionsD').append(html);
        }
        closeThis();
    }

    function addProductIdToSaveForm(productId) {
        var hiddenStr = '';

        $('input[name="productIdsTmp"]').each(function () {
            hiddenStr += '<input type="hidden" name="productIds" value="' + $(this).val() + '" />';
        });
        $('input[name="skuIdsTmp"]').each(function () {
            hiddenStr += '<input type="hidden" name="skuIds" value="' + $(this).val() + '" />';
        });
        $('#saveForm').append(hiddenStr);
    }
    addProductIdToSaveForm();

    function saveList() {
        var relationId = $("input[name='relationId']").val();
        var boxlist = $("input[name='sectionsDetailIds']");
        boxlist.each(function (i) {
            var productRelationSkuId = $(this).parent().parent().find("input[name='skuId']").val();
            $(this).parent().parent().find("input[name='productRelationSku1']").attr("name", "list1[" + i + "]." + "relationSkuId")
                    .attr("value", productRelationSkuId);
            var productRelationPrice = $(this).parent().parent().find("input[name='price']").val();
            $(this).parent().parent().find("input[name='productRelationPrice1']").attr("name", "list1[" + i + "]." + "relationSkuPrice")
                    .attr("value", productRelationPrice);
            $(this).parent().parent().find("input[name='productRelationType1']").attr("name", "list1[" + i + "]." + "relationDetailType")
                    .attr("value", 0);
            $(this).parent().parent().find("input[name='relationID1']").attr("name", "list1[" + i + "]." + "relationId")
                    .attr("value", relationId);
        });
        var productRelationRelationName = $("input[name='productRelation.relationName']").val();
        if (productRelationRelationName.length == 0) {
            alert("请输入关联名称");
            return false;
        }
        $("#relationfrm").submit();
    }

    function goToBack() {
        var pageNum = $("#pageNum").val();
        var mainSkuId = $("input[name='productRelation.mainSkuId']").val();
        var obj = document.getElementsByName("skuIds");
        if (obj.length > 0) {
            if (confirm('您添加的产品关联还未保存，确定返回？')) {
                location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId + "&pageNum=" + pageNum;
            }
        } else {
            location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId + "&pageNum=" + pageNum;
        }
    }
</script>
</body>
</html>