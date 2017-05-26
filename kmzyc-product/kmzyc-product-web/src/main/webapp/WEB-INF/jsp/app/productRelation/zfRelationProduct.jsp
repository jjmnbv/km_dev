<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
    <link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css"/>
    <link rel="stylesheet" href="/kindeditor/themes/default/default.css"/>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
    <script type="text/javascript"  href="/etc/js/warehouse/distributionInfo.js"/>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/etc/js/product_add.js"></script>
    <script type="text/javascript" src="/etc/js/kindeditor_zf.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script src='/etc/js/dialog-common.js' type='text/javascript'></script>
    <script src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <title>产品发布</title>
</head>
<body>
<div style="position: absolute; align: center; top: 20px; left: 30px">
    <s:form action="/productRelation/saveProductZFRelation.action"
            method="post" namespace='/productRelation' id="relationfrm" name='relationfrm'>
        <div id="content">
            <div>
                <table width="90%" class="table_search" align="center" cellpadding="0"
                       cellspacing="0" style="border-collapse: collapse; font-size: 12px">
                    <tr>
                        <td>
                            <input type="button" class="btn-custom" value="删除 " onclick="delSkuId()"/>
                            &nbsp;
                            <input type="button" class="btn-custom" value="新增组方产品 " onClick="addProduct()"/>
                        </td>
                    </tr>
                    <tr>
                        <input type="hidden" name="productRelation.status" value="0"/>
                        <input type="hidden" name="productRelation.editable" value="1"/>
                        <input type="hidden" name="productRelation.mainSkuId"
                               value="<s:property value='productTied.productSkuId' />"/>
                        <input type="hidden" name="productRelation.mainSkuPrice"
                               value="<s:property value='mainSkuPrice' />"/>
                        <td width="432px">主产品价格：&nbsp;&nbsp;&nbsp;<s:property
                                value="mainSkuPrice"/></td>
                        <td>组方名称：<s:textfield name="productRelation.relationName"
                                              cssClass="input_style" reg="^(\S{0,10})$" tip="名称不要超过10个汉字"
                                              id="productName"></s:textfield></td>
                    </tr>
                    <!-- 组方关联类型：4 -->
                    <input type="hidden" name="productRelation.relationType" value="4"/>
                </table>

                <!-- 数据列表区域 -->
                <table width="98%" class="list_table" align="center" cellpadding="3"
                       cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
                    <tr>
                        <th align="center" width="5%">
                            <input type="checkbox" onclick="checkAll(this)"/>
                        </th>
                        <th align="center" width="12%">产品编号</th>
                        <th align="center" width="15%">产品SKU编号</th>
                        <th align="center" width="12%">产品标题</th>
                        <th align="center" width="10%">品牌</th>
                        <th align="center" width="10%">状态</th>
                        <th align="center" width="8%">价格</th>
                        <th align="center" width="7%">操作</th>
                    </tr>
                </table>
                <br/>
                <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
                <s:hidden name="checkedId" id="checkedId"/>
                <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
                <input type="hidden" name="totalPrice" value="<s:property value='totalPrice'/>"/>
            </div>
            <!-- 组方详情 -->
            <div>
					<textarea id="editor_id" name="productRelation.relationIntroduce"
                              style="height: 400px; width: 1014px; resize: none;">
						<s:property value="productRelation.relationIntroduce"/>
					</textarea>
            </div>
            <!-- lazy -->
            <div class="editor_change" style="visibility:hidden"></div>
            <div style="visibility:hidden">
					<textarea id="editor_lazy" name="productRelation.relationIntroduceLazy">
						 <s:property value="productRelation.relationIntroduceLazy"/>
					</textarea>
            </div>
            </br>
            <!-- 做法视频 -->
            <div>
                视频地址：<s:textfield name="productRelation.relationVideo"
                                  class="video_site" id="relationVideo" style="width:500px;">
            </s:textfield>
            </div>
            </br>
            <!-- 组方简介 -->
            <div>
                组方简介：
                <s:textfield name="productRelation.relationIntro"
                             cssClass="video_intro" reg="^(\S{0,10})$" tip="简介不要超过10个汉字"
                             id="relationIntro"></s:textfield>
            </div>
        </div>
        <br/>
        <table>
            <tbody>
            <tr align="center">
                <td align="center">
                    <input id="submitButton" class="saveBtn" type="button" value="" onClick="saveList();">
                    &nbsp;&nbsp;
                    <input type="button" class="backBtn" onClick="goToBack()"/>
                </td>
            </tr>
            </tbody>
        </table>
    </s:form>
</div>

<s:form action="/app/saveSectionsDetail.action" method="POST" id="saveForm">
</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <script LANGUAGE="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        if (msg == "addsuccess") {
            alert("保存成功");
        } else if (msg == "successUpdate") {
            alert("修改成功");
        } else {
            alert("失败");

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
            $(self).parent().parent()
                    .find("input[name='relationProductPrice']")
                    .replaceWith("<input type='text' name='relationProductPrice' size='5' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' tip='范围[1-1000000]'/>");
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
                        $("#mainSkuPrice").replaceWith("<input type='text' readonly='readonly' name='productRelation.mainSkuPrice' value=" + p + "/>");
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
                                $(this).replaceWith(" <input  type='checkbox' name='checkSkuId' value=" + checkBoxValue + " />");
                            }
                        );
                        $("#relationProductSkuPrice").hide();
                    }
                    else {
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
                                priceI.replaceWith("	<input type='text' name='relationProductPrice' size='5' />");
                                //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                                var cKValue = $(this).val();
                                $(this).replaceWith(" <input  type='checkbox' name='checkSkuId'   onclick='checkBoxClick(this)'	   value=" + cKValue + "  />");
                            }
                        );
                        $("#relationProductSkuPrice").show();
                    }
                }
            );
        }
    );

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

    //删除组方下的详细子单
    function delSkuId() {
        var chestr = "";
        var str = document.getElementsByName("relationDetailId");
        var objarray = str.length;
        var isTrue = true;
        if (objarray > 0) {
            for (i = objarray; i > 0; i--) {// 遍历数组
                if (str[i - 1].checked == true) {
                    delOp(str[i - 1].value);
                    isTrue = false;
                }
            }
        }
        var str1 = document.getElementsByName("sectionsDetailIds");
        var objarray1 = str1.length;
        if (objarray1 > 0) {
            for (i = objarray1; i > 0; i--) {// 遍历数组
                if (str1[i - 1].checked == true) {
                    delOp(str1[i - 1].value);
                    isTrue = false;
                }
            }
        }
        if (isTrue) {
            alert("请选择要删除的关联产品");
        }
    }
    function delOp(thisDelTr) {
        var ids = "skuIds" + thisDelTr;
        $('#' + ids).remove();
        $('.' + ids).parent().parent().remove();
    }
    ;

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
            var html = '<tr><input type="hidden"  name="productRelationSku1"/><input type="hidden"  name="productRelationPrice1"  /><input type="hidden"   name="productRelationType1"  /><input type="hidden"  name="relationID1"  />		<td align="center"><input type="checkbox" value="' + skuId + '" id="productsDetailId"  name="sectionsDetailIds" value="0" /></td>';
            html += '<td  align="center">' + productNo + '</td>';
            html += '<td align="center">' + productSkuCode + '</td>';
            html += '<td align="center">' + productName + '</td><input type="hidden" name="sectionsDetail.productId" id="productId" value="' + productId + '"/>';
            html += '<td align="center">' + brandName + '</td>';
            html += '<input type="hidden" name="skuId" id="skuId" value="' + skuId + '"/>';
            //html += '<td align="center">'+'<s:iterator value="#request.productRelationStatusMap"  > <s:if test="'+productRelationStatus+'==key"  > <s:property   value="value"   /> </s:if></s:iterator>'+'</td>';
            html += '<td align="center">' + productRelationStatus + '</td>';
            html += '<td align="center">' + price + '</td><input type="hidden" name="price" id="price" value="' + price + '"/>';
            html += '<td align="center"><img title="删除" style="cursor: pointer; text-align: right"  class="skuIds' + skuId + '" src="/etc/images/little_icon/delete.png" onclick="delOp(' + skuId + ')" /></td></tr>';
            var hiddenStr = '<input type="hidden" id="skuIds' + skuId + '" name="skuIds" value="' + skuId + '" />';
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
        if (boxlist.length < 1) {
            alert('请添加关联产品！');
            return;
        }
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
        if ($("#editor_id").val().length == 0) {
            alert("商品介绍不能为空");
            return false;
        } else if ($.trim($(".video_intro").val()).length == 0) {
            alert("简介不能为空");
            return false;
        } else if ($.trim(productRelationRelationName).length == 0) {
            alert("关联名称不能为空");
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