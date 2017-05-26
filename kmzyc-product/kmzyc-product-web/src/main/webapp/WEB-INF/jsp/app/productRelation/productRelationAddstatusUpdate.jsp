<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>列表选择SKU码</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css"/>
    <link href="/etc/js/warehouse/distributionInfo.js"/>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script src='/etc/js/dialog-common.js' type='text/javascript'></script>
    <script src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
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
<s:set name="name" value="'套餐管理'" scope="request"/>
<s:set name="son_name" value="'编辑套餐'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<br/>

<s:form action="/productRelation/updateProductRelationTaoCan.action"
        method="post" namespace='/productRelation' id="relationfrm">
    <input type="hidden" name="productRelation.relationId" value="<s:property value='productRelation.relationId'/>"/>
    <input type="hidden" name="updateType" value="1"/>
    <table width="95%" class="edit_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px;">
        <tr>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>套餐名称：</th>
            <td>
                <s:textfield
                        name="productRelation.relationName" cssClass="input_style"
                        tip="备注不要超过20个汉字" id="relationNameId" maxlength="20"></s:textfield>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>所属商家：</th>
            <td>
                <s:if test='productRelation.supplierType == 1'>
                    康美自营代销
                </s:if>
                <s:if test='productRelation.supplierType == 2'>
                    指定入驻商家
                </s:if>
                &nbsp;&nbsp;&nbsp;
                <input type="hidden" value="<s:property value='productRelation.supplierId'/>"
                       id="hi_supplierId"/>
                <s:property value='productRelation.supplierName'/>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>套餐价格：</th>
            <td>
                <s:textfield
                        name="productRelation.totalRelationPrice" cssClass="input_style" id="totalRelationPriceId"
                        maxlength="8"></s:textfield>
            </td>
        </tr>
        <%--<tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>套餐PV值：</th>
            <td>
                <s:textfield
                        name="productRelation.pvValue" cssClass="input_style" id="pvId" maxlength="8"></s:textfield>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>合作方收益金额：</th>
            <td>
                <s:textfield
                        name="productRelation.costIncomeRatio" cssClass="input_style" id="costIncomeRatioId"
                        maxlength="8"></s:textfield>
            </td>
        </tr>--%>
    </table>
    &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="新增产品 " onclick="addProduct()" />
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
        <tr>
            <th align="center" width="5%">
                <input type="checkbox" onclick="checkAll(this)">
            </th>
            <th align="center" width="10%">产品SKU编号</th>
            <th align="center" width="10%">产品标题</th>
            <th align="center" width="10%">SKU描述</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">状态</th>
            <th align="center" width="9%">销售价格</th>
            <th align="center" width="9%">数量</th>
            <th align="center" width="8%">价格小计</th>
            <th align="center" width="8%">操作</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr><input type="hidden" name="skuIds" value='<s:property value="skuId" />'/>
                <input type="hidden" name="prices" value="<s:property value='price'/>"/>
                <input type="hidden" name="ckAdd"/>
                <td align="center"><input type="checkbox" id="productsDetailId" name="sectionsDetailIds" value="0"/>
                </td>
                <td align="center" width="5%"><s:property value="productSkuCode"/></td>
                <input type="hidden" name="relationDetailIdList" value='<s:property value="relationDetailId" />'/>
                <td align="center"><s:property value="productTitle"/></td>
                <td>
                    <s:iterator value="productSkus" id="sku1">
                        <s:if test="#sku1.productSkuCode == #productiterator.productSkuCode">
                            <s:property value="skuAttrs"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='prodBrand.brandName'/>
                </td>
                <td align="center" style="word-break:break-all">
                    <s:iterator value="#request.productRelationStatusMap">
                        <s:if test="status == key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                    <s:if test="status == 4">已下架</s:if>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='price'/>
                </td>
                <td align="center" style="word-break: break-all">
                    <input type="text" maxlength="2" name="proCount" value="<s:property value="productCount"/>"
                           class="cl_count" id="price" onblur="countPrice(this);"/>
                </td>
                <td align="center" style="word-break: break-all" class="class_price">
                    <s:property value='price*productCount'/>
                </td>
                <td>
                    <input type="button" value="删除" onclick="delTr(this,<s:property value="relationDetailId"/>)">
                </td>
            </tr>
        </s:iterator>
    </table>
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD1">
        <tr>
            <th align="center" width="5%" colspan="2">分开购买合计:</th>
            <th align="center" width="10%"></th>
            <th align="center" width="10%"></th>
            <th align="center" width="10%"></th>
            <th align="center" width="10%"></th>
            <th align="center" width="10%"></th>
            <th align="center" width="10%"></th>
            <th align="center" width="9%"></th>
            <th align="center" width="9%" id="td_2"><s:property value="proCounts"/></th>
            <th align="center" width="8%" id="td_1"><s:property value="totalPirc"/></th>
            <th align="center" width="8%"></th>
        </tr>
    </table>

    <br/>
    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="bottombuttonbar" height="30"
           border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
                <input class="saveBtn" type="button" onClick="saveList()" value="" id="saveBtnId">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="goToBack()"/></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <input type="hidden" name="pageNum" id="pageNum"
           value="<s:property value='pageNum'/>"/>
    <s:hidden name="checkedId" id="checkedId"/>

    <input type="hidden" id="rtnMsg"
           value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" name="totalPrice"
           value="<s:property value='totalPrice'/>"/>
</s:form>

<s:form action="/productRelation/packageManage.action" method="POST" id="rtForm1">
    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>

</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <script LANGUAGE="JavaScript">
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
    //var html1='	<tr> <td>分开购买合计</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td id="td_1"></td><td ></td></tr>';
    //$('#sectionsD').append(html1);
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
            $(this).parent().parent().find("input[name='productRelationSku']")
                    .attr("name", "list[" + i + "]." + "relationSkuId").attr("value", productRelationSkuId);
            var productRelationPrice = $(this).parent().parent().find("input[name='relationProductPrice']").val();
            if (productRelationPrice) {
                count = count + 1;
            }
            $(this).parent().parent()
                    .find("input[name='productRelationPrice']")
                    .attr("name","list[" + i + "]." + "relationSkuPrice").attr("value", productRelationPrice);
            $(this).parent().parent().find("input[name='productRelationType']")
                    .attr("name", "list[" + i + "]." + "relationDetailType")
                    .attr("value", 0);
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
            $(self).parent().parent().find("input[name='relationProductPrice']")
                    .replaceWith("<input type='text' name='relationProductPrice' size='5' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' tip='范围[1-1000000]'/>");

            //	重新加载js,验证出现错误提示用的
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript = document.createElement("script");
            oScript.type = "text/javascript";
            oScript.src = "/etc/js/validate/easy_validator.pack.js";
            oHead.appendChild(oScript);
        } else {
            $(self).parent().parent().find("input[name='relationProductPrice']")
                    .replaceWith("<input type='text'  name='relationProductPrice' size='5'/>");
            //	重新加载js,验证出现错误提示用的
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript = document.createElement("script");
            oScript.type = "text/javascript";
            oScript.src = "/etc/js/validate/easy_validator.pack.js";
            oHead.appendChild(oScript);
        }
    }

    $(document).ready(function () {
        $("input[type='radio']").click(function () {
            if ($(this).val() != 0) {
                var p = $("#oldMainPrice")[0].innerHTML;
                $("#mainSkuPrice").replaceWith(
                        "<input type='text' readonly='readonly' name='productRelation.mainSkuPrice' value=" + p + "/>");

                $("input[name='checkSkuId']").each(function (i) {
                    var checkBoxValue = $(this).val();
                    var productRelationPrice = $(this).parent().parent().find("input[name='productOldPriceRe']").val();
                    $(this).parent().parent().find("input[name='relationProductPrice']").val(productRelationPrice);
                    var priceI = $(this).parent().parent().find("input[name='relationProductPrice']");
                    priceI.hide();
                    priceI.parent().hide();
                    //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                    $(this).replaceWith("<input type='checkbox' name='checkSkuId' value=" + checkBoxValue + "/>");
                });
                $("#relationProductSkuPrice").hide();
            } else {
                $("input[name='productRelation.mainSkuPrice']")
                        .replaceWith("<input type='text' id='mainSkuPrice' tip='请输入1-1000000的整数' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' onblur='addScript();' name='productRelation.mainSkuPrice'/>");

                //	重新加载js,验证出现错误提示用的
                var oHead = document.getElementsByTagName('HEAD').item(0);
                var oScript = document.createElement("script");
                oScript.type = "text/javascript";
                oScript.src = "/etc/js/validate/easy_validator.pack.js";
                var oScript1 = document.createElement("script");
                oHead.appendChild(oScript);
                $("input[name='checkSkuId']").each(function (i) {
                    var priceI = $(this).parent().parent().find("input[name='relationProductPrice']");
                    // 注意这一行与下面一行的次序不能调换
                    priceI.parent().show();
                    priceI.replaceWith("<input type='text' name='relationProductPrice' size='5'/>");

                    //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                    var cKValue = $(this).val();
                    $(this).replaceWith("<input type='checkbox' name='checkSkuId' onclick='checkBoxClick(this)' value=" + cKValue + "/>");
                });
                $("#relationProductSkuPrice").show();

            }
        });
    });

    function firstPage() {
        document.getElementById('pageNo').value = 1;
        goPage();
    }
    function lastPage() {
        document.getElementById('pageNo').value = <s:property value='page.pageCount'/>;
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
            document.forms[0].action = "/productRelation/relationQueryProduct.action?page.pageNo="
                    + s + "&productRelation.relationType=" + typeValueNum;
            document.forms[0].submit();
        }
    }

    /**
     *以下函数用于记录的选择
     */
    function checkCkSelected(oForm) {
        //window.event.returnValue = false;
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
            /*var ct = ele.getAttribute("type");*/
            if ((ele.type == "checkbox")) {
                if (ck.checked != ele.checked)
                    ele.click();
            }
        }
    }

    function checkSelected(sName) {
        //window.event.returnValue = false;
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
        //if(document.getElementById("ra_ziying").checked == false && document.getElementById("ra_ruzhu").checked == false){
        //alert("请先选择所属商家！");
        //return;
        //}
        var suppIdVal;
        var supplierTyes;
        if (document.getElementById("hi_supplierId") != "") {
            if ($("#re_supplireNmae").val() == "") {
                alert("请选择入驻商家！");
                return;
            } else {
                suppIdVal = $("#hi_supplierId").val();
                supplierTyes = 2;
            }
        }
        if (document.getElementById("hi_supplierId").value == "") {
            supplierTyes = 1;
        }
        var mainSkuId = $("input[name='productRelation.mainSkuId']").val();
        var pageNum = $("#pageNum").val();
        var url = "/productRelation/addOtherTypeProductRelationTaoCan.action?productTied.supplierCode="
                + suppIdVal + "&pageNum=" + pageNum + "&supplierTyes=" + supplierTyes;
        myDialog = art.dialog.open(url, {
            title: '添加套装产品',
            width: 900,
            height: 500,
            drag: false,
            close: function () {
                $.unblockUI();
            }
        });
        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({
            message: ""
        });
        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({
            message: ""
        });
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
        var productTitle;
        var productNo;
        var skuId;
        var productSkuCode;
        var brandName;
        var price;
        var skuAttrs;
        var sizeI = params.length;
        for (var i = 0; i < sizeI; i++) {
            var str = params[i];
            productNo = str[0];
            productSkuCode = str[1];
            productTitle = str[2];
            brandName = str[3];
            price = str[5];
            skuId = str[6];
            productId = str[7];
            skuAttrs = str[8];
            productRelationStatus = str[9];
            var html = '<tr><input type="hidden"  name="skuIdsAdd" value="' + skuId + '"/><input type="hidden"  name="pricesAdd" value="' + price + '"/><input type="hidden"  name="ckAdd"/>		<td align="center"><input type="checkbox" id="productsDetailId"  name="sectionsDetailIds" value="0" /></td>';
            html += '<td align="center">' + productSkuCode + '</td>';
            html += '<td align="center">'
                    + productTitle
                    + '</td>';
            html += '<td align="center">' + skuAttrs + '</td>';
            html += '<td align="center">' + brandName + '</td>';
            //html += '<td align="center">'+'<s:iterator value="#request.productRelationStatusMap"  > <s:if test="'+productRelationStatus+'==key"  > <s:property   value="value"   /> </s:if></s:iterator>'+'</td>';
            html += '<td align="center">' + productRelationStatus + '</td>';
            html += '<td align="center">' + price + '</td>';
            html += '<td align="center">'
                    + '<input type="text" maxlength="2" name="proCountAdd" class="cl_count" id="price" onblur="countPrice(this);"/>'
                    + '</td><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" /><input type="hidden" />';
            html += '<td align="center"  class="class_price">' + 0 + '</td>';
            html += '<td align="center">' + '<input type="button" value="删除" onclick="delTr(this)">' + '</td>' + '</tr>';
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
        if ($("#relationNameId").val() == "") {
            alert("套餐名称不能为空！");
            return;
        }
        if ($("#totalRelationPriceId").val() == "") {
            alert("套餐价格不能为空！");
            return;
        } else {
            var reg = /(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/;
            if (reg.test($("#totalRelationPriceId").val()) == false) {
                alert("请输入正确的套餐价格！");
                return;
            }
        }
        if ($("#pvId").val() == "") {
            alert("套餐PV值不能为空！");
            return;
        } else {
            var reg = /(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/;
            if (reg.test($("#pvId").val()) == false) {
                alert("请输入正确的套餐PV值！");
                return;
            }
        }
        if ($("#costIncomeRatioId").val() == "") {
            alert("合作方收益金额不能为空！");
            return;
        } else {
            var reg = /(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/;
            if (reg.test($("#costIncomeRatioId").val()) == false) {
                alert("请输入正确的合作方收益金额！");
                return;
            }
        }

        var proCountObjsLength = document.getElementsByName("ckAdd");
        var proCountObjs = $(".cl_count");
        var pattern = /^(?:[1-9][0-9]?|10000000)$/;
        var pattern1 = /^(?:[2-9][1-9]?|10000000)$/;
        if (proCountObjsLength.length == 0) {
            alert("请添加套餐商品！");
            return;
        } else {
            if (proCountObjsLength.length == 1) {
                for (var i = 0; i < proCountObjs.length; i++) {
                    if (proCountObjs[i].value == "") {
                        alert("请输入商品数量！");
                        return;
                    } else {
                        if (pattern.test(proCountObjs[i].value) == false || proCountObjs[i].value == 1) {
                            alert("商品为一个的套餐，商品数量必须输入大于1的整数！");
                            return;
                        }
                    }
                }
            }
            for (var i = 0; i < proCountObjs.length; i++) {
                if (proCountObjs[i].value == "") {
                    alert("请输入商品数量！");
                    return;
                } else {
                    if (pattern.test(proCountObjs[i].value) == false) {
                        alert("请输入数量大于0的整数！");
                        return;
                    }
                }
            }
        }
        $("#relationfrm").submit();
        document.getElementById("saveBtnId").disabled = true;
    }

    function goToBack() {
        var obj = document.getElementsByName("skuIdsAdd");
        if (obj.length > 0) {
            if (confirm('您添加的产品还未保存，确定返回？')) {
                $("#rtForm1").submit();
            }
        } else {
            $("#rtForm1").submit();
        }
    }


    function chooseSupplier() {//选择供应商弹窗口
        var ra_Val;
        if (document.getElementById("ra_ziying").checked) {
            ra_Val = $("#ra_ziying").val();
        }
        if (document.getElementById("ra_ruzhu").checked) {
            ra_Val = $("#ra_ruzhu").val();
        }

        var url = "/productRelation/showSupplierInfo.action?selectSupplierType=" + ra_Val;
        myDialog = art.dialog.open(url, {
            title: '选择供应商',
            width: 900,
            height: 500,
            drag: false,
            close: function () {
                $.unblockUI();
            }
        });
        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({
            message: ""
        });
        $.blockUI.defaults.overlayCSS.opacity = '0.5';
        $.blockUI({
            message: ""
        });
    }

    function closeOpenSupplier(params) {
        $("#re_supplireNmae").val("");
        $("#re_supplireId").val("");
        var supplierIdVal;
        var supplierNameVal;
        var sizeI = params.length;
        for (var i = 0; i < sizeI; i++) {
            var str = params[i];
            supplierIdVal = str[0];
            supplierNameVal = str[1];
        }
        $("#re_supplireNmae").val(supplierNameVal);
        $("#re_supplireId").val(supplierIdVal);
        $("#hi_supplireName").val(supplierNameVal);
        closeThis();
    }

    function on_ziying1() {
        $("#re_supplireNmae").val("康美自营代销");
        //$("#re_supplireId").val("221");
        $("#hi_supplireName").val("康美自营代销");
        document.getElementById("button_selectSupplier").disabled = false;
        var table = document.getElementById("sectionsD"),
                trs = table.getElementsByTagName("tr");
        for (var i = trs.length - 1; i > 0; i--) {
            table.deleteRow(i);
        }
        document.getElementById("button_selectSupplier").disabled = true;
    }
    function on_ruzhu1() {
        $("#re_supplireNmae").val("");
        $("#re_supplireId").val("");
        document.getElementById("button_selectSupplier").disabled = false;
        var table = document.getElementById("sectionsD"),
                trs = table.getElementsByTagName("tr");
        for (var i = trs.length - 1; i > 0; i--) {
            table.deleteRow(i);
        }
        //$("#re_supplireNmae").val($("#hi_supplireName").val());
    }

    function countPrice(obj) {
        var count1 = obj.value;
        var tr1 = obj.parentNode.parentNode;
        var pri1 = tr1.cells[6].innerHTML;
        tr1.cells[8].innerHTML = count1 * parseFloat(pri1);
        var countObj = $(".cl_count");//商品数量对象
        var priceObj = $(".class_price");//价格小计对象
        var tab = document.getElementById("sectionsD"); //表格对象
        var pri2All = 0;
        var count2All = 0;
        for (var p = 0; p < priceObj.length; p++) {
            var pri2 = priceObj[p].innerHTML;
            pri2All = parseFloat(pri2) + pri2All;
        }
        for (var j = 0; j < countObj.length; j++) {
            var count2 = countObj[j].value;
            if (count2 == '') {
                count2 = 0;
            }
            count2All = parseInt(count2) + count2All;
        }
        document.getElementById("td_1").innerHTML = pri2All.toFixed(2);
        document.getElementById("td_2").innerHTML = count2All;
    }

    function delTr(obj, id) {
        var answer = confirm("确认删除?");
        if (!answer) {
            return false;
        }

        if ("" != id) {
            $.ajax({
                url: 'deleteRelationDeit.action',
                async: false,
                data: 'detailId=' + id,
                success: function (info) {
                    if ('0' == info) {
                        alert("系统异常，删除失败!");
                        return;
                    } else {
                        //alert("开启成功!");
                        //document.forms[1].action= "supplierAuditList.action";
                        //  document.forms[1].submit();
                    }
                }
            });
        }
        var pri3 = 0;
        var row = obj.parentNode.parentNode; //A标签所在行
        var tb = row.parentNode; //当前表格
        var rowIndex = row.rowIndex; //A标签所在行下标
        // var countObj=document.getElementsByName("proCount");//商品数量对象
        var countObj = $(".cl_count");
        var countDle = countObj[rowIndex - 1].value;
        var tr = obj.parentNode.parentNode;
        var cells = row.cells;
        var pri2All = document.getElementById("td_1").innerHTML;
        var count2All = document.getElementById("td_2").innerHTML;
        document.getElementById("td_1").innerHTML = (pri2All - parseFloat(cells[9].innerHTML)).toFixed(2);
        document.getElementById("td_2").innerHTML = count2All - parseInt(countDle);
        tb.deleteRow(rowIndex); //删除当前行
    }
</script>
</body>
</html>