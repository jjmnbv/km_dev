<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>列表选择SKU码</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script src="/etc/js/warehouse/distributionInfo.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
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
<s:set name="son_name" value="'套餐列表'" scope="request"/>
<s:set name="son_name" value="'编辑套餐列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:form action="/productRelation/viewProductDeatil.action"
        method="post" namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" name="mainSkuId" value="<s:property value='mainSkuId'/>"/>
    <input type="hidden" name="relationType" value="0"/>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" name="totalPrice" value="<s:property value='totalPrice'/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <input type="hidden" name="relationId" value="<s:property value='productRelationDetail.relationId'/>"/>
        <input type="hidden" name="productRelationDetail.relationId"
               value="<s:property value='productRelationDetail.relationId'/>"/>
        <tr>
            <td width="432px" style="font-weight:normal;font-size:18px;">
                主产品名称：&nbsp;&nbsp;&nbsp;<s:property value="productName"/>
        </tr>
        <s:if test="packageStatus==2">
            <tr>
                <td>
                    &nbsp;
                    <input type="button" value="新增产品 " onclick="addProduct()"/>
                </td>
            </tr>
        </s:if>
    </table>
    <br/>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
        <tr>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品名称</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">状态</th>
            <th align="center" width="9%">销售价格</th>
            <th align="center" width="8%">套餐中价格</th>
            <th align="center" width="5%">更新价格</th>
            <th align="center" width="12%">操作</th>
        </tr>

        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
                <input type="hidden" id="relation<s:property value='#index.index'/>" name="relationDetailId"
                       value="<s:property value="relationDetailId" />"/>
                <td align="center" width="5%"><s:property value="productSkuCode"/></td>
                <td align="center"><s:property value="procuctName"/></td>
                <td align="center" style="word-break: break-all"><s:property value='brandName'/></td>
                <td align="center" style="word-break:break-all">
                    <s:iterator value="#request.productRelationStatusMap">
                        <s:if test="status==key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center" style="word-break: break-all"><s:property value='price'/></td>
                <td align="center" class="newPrice" style="word-break: break-all" name="newPrice">
                    <s:property value="newPrice"/>
                </td>
                <td align="center" style="word-break: break-all" width="30px">
                    <s:if test="packageStatus==2||packageStatus==4">
                        <s:textfield name="updatePrice" width="30px"></s:textfield>
                    </s:if>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:if test="packageStatus==2||packageStatus==4">
                        <img title="修改" style="cursor: pointer;text-align: right"
                             src="/etc/images/button_new/modify.png" onclick="toEditPDetail(this)"/>
                    </s:if>
                    <s:if test="packageStatus==2">
                        <img title="删除" data-id="<s:property value="relationDetailId" />"
                             style="cursor: pointer;text-align: right" src="/etc/images/little_icon/delete.png"
                             onclick="delProduct(this)"/>
                    </s:if>
                </td>
            </tr>
        </s:iterator>
    </table>
    <br/>

    <!-- 分页按钮区 -->
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>
    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="bottombuttonbar" height="30"
           border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
                <s:if test="packageStatus==2">
                    <input class="saveBtn" type="button" onClick="saveList()" value=""/>
                </s:if>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="goToBack()"/></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>

</s:form>
<s:form action="/app/saveSectionsDetail.action" method="POST" id="saveForm">
    <input type="hidden" name="pre_sectionId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>
    <input type="hidden" name="identifi" value="<s:property value="identifi" />"/>
    <input type="hidden" name="sections.sectionsId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>
</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        if (msg == "success") {
            alert("保存成功");
        }
        else {
            alert("保存失败");
        }
    </script>
</s:if>

<script>
    function addProduct() {
        var relationType = $("input[name='relationType']").val();
        var mainSkuId = $("input[name='mainSkuId']").val();
        var pageNum = $("#pageNum").val();
        var relationId = $("input[name='relationId']").val();
        var checkedId = $("#checkedId").val();
        if (relationType == 0) {
            var url = "/productRelation/addOtherTypeProductRelation.action?productTied.productSkuId=" + mainSkuId +
                    "&relationId=" + relationId + "&relationType=" + relationType +
                    "&pageNum=" + pageNum + "&checkedId=" + checkedId ;
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
        } else {
            var url = "/productRelation/addOtherTypeProductRelation.action?productTied.productSkuId="+ mainSkuId
                    + "&relationId=" + relationId + "&relationType=" + relationType
                    + "&pageNum=" + pageNum + "&checkedId=" + checkedId;
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
        }
    }


    function goToBack() {
        var pageNum = $("#pageNum").val();
        var checkedId = $("#checkedId").val();
        var mainSkuId = $("input[name='mainSkuId']").val();
        var obj = document.getElementsByName("skuIds");
        if (obj.length > 0) {
            if (confirm('您有新增的产品没有保存，确定返回？')) {
                location.href = "/productRelation/queryPackageDetail.action?productRelation.mainSkuId=" + mainSkuId
                        + "&pageNum=" + pageNum + "&checkedId=" + checkedId;
            }
        } else {
            location.href = "/productRelation/queryPackageDetail.action?productRelation.mainSkuId=" + mainSkuId
                    + "&pageNum=" + pageNum + "&checkedId=" + checkedId;
        }
    }


    // 删除套餐下的详细子单
    function delProduct(self) {
        var str = document.getElementsByName("relationDetailId");
        var relationDetailId = $(self).attr('data-id');
        var mainSkuId = $("input[name='mainSkuId']").val();
        var relationId = $("input[name='relationId']").val();
        var answer = confirm("确认删除吗?");
        if (!answer) {
            return false;
        }
        // 发送ajax请求到action
        var settings = {
            type: "POST",
            url: "/productRelation/delPackageDetail.action?productRelationDetail.relationId=" + relationId + "&packageDetailStr=" + relationDetailId + "&mainSkuId=" + mainSkuId,
            dataType: "json",
            error: function (data) {
                alert(data.msg);
            },
            success: function (data) {
                alert(data.msg);
                $(self).parent().parent().remove();
            }
        };
        $.ajax(settings);
    }

    function toDelete(self) {
        if (confirm('确定删除？'))
            $(self).parent().parent().remove();
    }


    function toEditPDetail(self) {
        var mainSkuId = $("input[name='mainSkuId']").val();
        var relationId = $("input[name='relationId']").val();
        var relationDetailId = $(self).parent().parent().find("input[name='relationDetailId']").val();
        var updatePrice = $(self).parent().parent().find("input[name='updatePrice']").val();
        var price = updatePrice.replace(/^\s*|\s*$/g, "");
        if (price.length == 0) {
            alert("输入值不能为空");
            return;
        }
        if (price.search(/(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/) == -1) {
            alert("输入值只能为数字");
            return;
        }
        if (Number(price) > 10000000) {
            alert("你的价格过大，请重新输入");
            return;
        }
        var answer = confirm("确认更改吗?");
        if (!answer) {
            return false;
        }
        // 发送ajax请求到action
        var settings = {
            type: "POST",
            url: "/productRelation/updatePrice.action?productRelationDetail.relationDetailId=" + relationDetailId + "&productRelationDetail.relationSkuPrice=" + updatePrice
            + "&productRelationDetail.relationId=" + relationId + "&mainSkuId=" + mainSkuId,
            dataType: "json",
            error: function (data) {
                alert(data.msg);
            },
            success: function (data) {
                alert(data.msg);
                $a = $(self).parent().parent().find(".newPrice");
                $a.html(updatePrice);
                $(self).parent().parent().find("input[name='updatePrice']").val('');
            }
        };
        $.ajax(settings);
    }

    function toEditPDetail2(self) {
        var mainSkuId = $("input[name='mainSkuId']").val();
        var relationId = $("input[name='relationId']").val();
        var relationDetailId = $(self).parent().parent().find("input[name='relationDetailId']").val();
        var updatePrice = $(self).parent().parent().find("input[name='updatePrice']").val();

        var price = updatePrice.replace(/^\s*|\s*$/g, "");
        if (price.length == 0) {
            alert("输入值不能为空");
            return;
        }
        if (price.search(/(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/) == -1) {
            alert("输入值只能为数字");
            return;
        }
        if (Number(price) > 10000000) {
            alert("你的价格过大，请重新输入");
            return;
        }

        var answer = confirm("确认更改吗?");
        if (!answer) {
            return false;
        }
        $(self).parent().parent().find(".newPrice").html(updatePrice);
        $(self).parent().parent().find("input[name='updatePrice']").val('');
        alert("修改成功");
    }

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
            var html = '<tr><input type="hidden"  name="productRelationSku1"/><input type="hidden" name="sectionsDetailIds"/><input type="hidden"  name="productRelationPrice1"  /><input type="hidden"   name="productRelationType1"  /><input type="hidden"  name="relationID1"  />	';
            html += '<td align="center">' + productSkuCode + '</td>';
            html += '<td align="center">' + productName + '</td>';
            html += '<td align="center">' + brandName + '</td>';
            html += '<input type="hidden" name="skuId" id="skuId" value="' + skuId + '"/>';
            //html += '<td align="center">'+'<s:iterator value="#request.productRelationStatusMap"  > <s:if test="'+productRelationStatus+'==key"  > <s:property   value="value"   /> </s:if></s:iterator>'+'</td>';
            html += '<td align="center">' + productRelationStatus + '</td>';
            html += '<td align="center">' + price + '</td><input type="hidden" name="price" id="price" value="' + price + '"/>';
            html += '<td align="center"  style="word-break: break-all" class="newPrice"><input name="newPrice" type="text"/></td>';
            html += ' <td  align="center"  style="word-break: break-all" width="30px" ></td>';
            html += ' <td  align="center"  style="word-break: break-all" ><img title="删除" style="cursor: pointer;text-align: right" src="/etc/images/little_icon/delete.png"  onclick="toDelete(this)" />';
            var hiddenStr = '<input type="hidden" name="productIds" value="' + productId + '" /></tr>';
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
        var bl = true;
        $('input[name="newPrice"]').each(function () {
            var newPrice = $(this).val();
            if (newPrice == '')
                bl = false;
        });
        if (bl == false) {
            alert('新添加的商品未设置套餐中的价格！');
            return;
        }
        $('input[name="updatePrice"]').each(function () {
            var updatePrice = $(this).val();
            if (updatePrice != '') {
                bl = false;
            }
        });
        if (bl == false) {
            if (!confirm('有更新的价格未提交，是否继续操作？')) return;
        }
        var relationId = $("input[name='relationId']").val();
        // var boxlist=$("input[name='sectionsDetailIds']");
        var skuIds = $("input[name='skuId']");
        var newPrices = $("input[name='newPrice']");
        var skuIds1 = $("input[name='productRelationSku1']");
        var newPrices1 = $("input[name='productRelationPrice1']");
        var productRelationTypes1 = $("input[name='productRelationType1']");
        var relationIDs1 = $("input[name='relationID1']");
        for (var i = 0; i < skuIds.length; i++) {
            skuIds1[i].value = skuIds[i].value;
            newPrices1[i].value = newPrices[i].value;
            productRelationTypes1[i].value = 0;
            relationIDs1[i].value = relationId;
        }
        $('#frm').attr("action", '/productRelation/saveOtherProductPackage.action');
        $("#frm").submit();
    }
</script>
</body>
</html>