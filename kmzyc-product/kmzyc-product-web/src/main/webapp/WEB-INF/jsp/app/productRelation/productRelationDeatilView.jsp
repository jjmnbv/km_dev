<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>列表选择SKU码</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script src="/etc/js/warehouse/distributionInfo.js"></script>
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
<s:set name="name" value="'产品关联'" scope="request"/>
<s:set name="son_name" value="'产品关联列表'" scope="request"/>
<s:set name="son_name" value="'编辑产品关联'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:form action="/productRelation/viewProductDeatil.action"
        method="post" namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" name="mainSkuId" value="<s:property value='mainSkuId'/>"/>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" name="totalPrice" value="<s:property value='totalPrice'/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <input type="hidden" name="relationId" value="<s:property value='productRelationDetail.relationId'/>"/>
        <input type="hidden" name="productRelationDetail.relationId"
               value="<s:property value='productRelationDetail.relationId'/>"/>
        <input type="hidden" name="relationType" value="<s:property value='relationType' />"/>
        <tr>
            <td></td>
            <td><input type="button" value="删除 " onclick="delProduct()"/>
                &nbsp;
                <input type="button" value="新增 " onclick="addProduct()"/>
            </td>
        </tr>
    </table>
    <br/>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
        <tr>
            <th align="center" width="5%"><input type="checkbox" onclick="checkAll(this)"></th>
            <th align="center" width="12%">产品编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品名称</th>
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
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
                <td align="center">
                    <input type="checkbox" name="relationDetailId" value="<s:property value='relationDetailId'/>"/>
                </td>
                <input type="hidden" id="relation<s:property value='#index.index'/>"
                       value="<s:property value="relationDetailId" />"/>
                <td align="center">
                    <s:property value="productNo"/>
                </td>
                <td align="center" width="5%"><s:property value="productSkuCode"/></td>
                <td align="center">
                    <s:property value="procuctName"/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='brandName'/>
                </td>
                <td align="center" style="word-break:break-all">
                    <s:iterator value="#request.productStatusMap">
                        <s:if test="status==key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <s:if test="relationType==0">
                    <td align="center" style="word-break: break-all">
                        <s:property value='price'/>
                    </td>
                </s:if>
                <td align="center" style="word-break: break-all">
                    <s:property value="newPrice"/>
                </td>
                <s:if test="relationType==0">
                    <td align="center" style="word-break: break-all" width="30px">
                        <s:textfield name="updatePrice" width="30px"></s:textfield>
                    </td>
                </s:if>
                <s:if test="relationType==0">
                    <td align="center" style="word-break: break-all">
                        <img title="修改" style="cursor: pointer;text-align: right"
                             src="/etc/images/button_new/modify.png" onclick="toEditPDetail(this)"/>

                    </td>
                </s:if>
            </tr>
        </s:iterator>
    </table>
    <br/>

    <!-- 分页按钮区 -->
    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="bottombuttonbar" height="30"
           border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
                <input class="saveBtn" type="button" onClick="saveList()" value="">
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
        } else {
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
                    "&pageNum=" + pageNum + "&checkedId=" + checkedId;
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
            var url = "/productRelation/addOtherTypeProductRelation.action?productTied.productSkuId=" + mainSkuId +
                    "&relationId=" + relationId + "&relationType=" + relationType + "&pageNum=" + pageNum +
                    "&checkedId=" + checkedId;
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
                location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId
                        + "&pageNum=" + pageNum + "&checkedId=" + checkedId;
            }
        } else {
            location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId
                    + "&pageNum=" + pageNum + "&checkedId=" + checkedId;
        }
    }


    // 删除套餐下的详细子单
    function delProduct() {
        var str = document.getElementsByName("relationDetailId");
        var mainSkuId = $("input[name='mainSkuId']").val();
        var objarray = str.length;
        var chestr = "";
        var relationId = $("input[name='relationId']").val();

        for (i = 0; i < objarray; i++) {// 遍历数组
            if (str[i].checked == true)// 如果选中，开始获得所选择的出库单ID
            {
                chestr += str[i].value + ",";
            }
        }
        if (chestr.length < 1) {
            alert("请选择要删除的关联产品");
            return false;
        }
        var answer = confirm("确认删除吗?");
        if (!answer) {
            return false;
        }
        // 发送ajax请求到action
        var settings = {
            type: "POST",
            url: "/productRelation/delPackageDetail.action?productRelationDetail.relationId=" + relationId
            + "&packageDetailStr=" + chestr + "&mainSkuId=" + mainSkuId,
            dataType: "json",
            error: function (data) {
                alert(data.msg);
            },
            success: function (data) {
                alert(data.msg);
                location.href = "/productRelation/viewProductDeatil.action?productRelationDetail.relationId=" + relationId
                        + "&mainSkuId=" + mainSkuId;
            }
        };
        $.ajax(settings);
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
                document.frm.submit();

            }
        };
        $.ajax(settings);
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

        $('#frm').attr("action", '/productRelation/saveOtherProductRelation.action');
        $("#frm").submit();
    }
</script>
</body>
</html>