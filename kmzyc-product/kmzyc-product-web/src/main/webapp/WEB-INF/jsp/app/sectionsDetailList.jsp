<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看明细</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
    <Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.form.js"></script>
    <Script src="/etc/js/97dater/WdatePicker.js"></Script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type="text/javascript" src="/etc/js/sections/sections.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <script type="text/javascript">
        function popSelectProduct() {
            var sections_id = $("#pre_sectionId").val();
            popDialog('/app/findProductForSections.action?type=stock&pre_sectionId=' + sections_id, '查询栏目产品', 900, 500);
        }

        function gotoSectionDetail() {
            location.href = "/app/querySectionsDetailList.action";
        }

        function gotoList() {
            $('#listForm').submit();
        }
        //增加栏目明细
        function addSectionsDetail() {
            var sections_id = $("#pre_sectionId").val();
            location.href = "/app/gotoSectionsDetailAdd.action?pre_sectionId=" + sections_id + "&viewType=add";
        }

        //去修改栏目明细
        function gotoUpdateSectionDetail(sectionsDetailId) {
            dialog("修改栏目明细", "iframe:/app/gotoSectionsDetailAdd.action?pre_sectionId=" + sectionsDetailId + "&viewType=edit", "600px", "390px", "iframe");
        }

        function closeOpenSku(params) {
            var productId;
            var productName;
            var productNo;
            var productBrand;
            var skuId;
            var productSkuCode;
            var sizeI = params.length;
            for (var i = 0; i < sizeI; i++) {
                var str = params[i];
                productId = str[0];
                productName = str[1];
                productNo = str[2];
                productBrand = str[3];
                skuId = str[5];
                productSkuCode = str[6];
                var html = '<tr><td align="center"><input type="checkbox" id="sectionsDetailId"  name="sectionsDetailIds" value="0" /></td>';
                html += '<td align="center"><input type="text" name="sortno" '
                        + 'maxlength="2" id="sortno" style="width: 30px" value="" /></td>';
                html += '<td align="center">' + productBrand + '</td>';
                html += '<td align="center">' + productName + '</td>';
                html += '<td align="center">' + productNo + '</td>';
                html += '<td align="center">' + productSkuCode + '</td><input type="hidden" name="skuId" id="skuId" value="' + skuId + '"/>';
                html += '</tr>';
                var hiddenStr = '<input type="hidden" name="productIds" value="' + productId + '" />';
                hiddenStr += '<input type="hidden" name="skuIds" value="' + skuId + '" />';
                $('#saveForm').append(hiddenStr);
                $('#sectionsD').append(html);
            }
            closeThis();
        }


        //光标移动
        function changeKey() {
            var tr = event.srcElement.getAttribute("type");
            if ("textarea" != tr && "button" != tr) {
                if (13 == event.keyCode) {
                    event.keyCode = 9;
                }
            }
        }

        /** 全选js  **/
        function checkAll(ck) {
            if ($("#select_all").attr("checked")) {
                $("[name = sectionsDetailIds]:checkbox").attr("checked", true);
            }
            else {
                $("[name = sectionsDetailIds]:checkbox").attr("checked", false);
            }
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

        function submitForm() {
            var hiddenStr = '';
            $('input[name="sectionsDetailIds"]').each(function () {
                hiddenStr += '<input type="hidden" name="sectionsDetailIds" value="' + $(this).val() + '" />';
            });
            $('input[name="sortno"]').each(function () {
                hiddenStr += '<input type="hidden" name="sortno" value="' + $(this).val() + '" />';
            });
            if (hiddenStr == "") {
                alert("请添加产品，再点击保存!");
                return false;
            }
            $('#saveForm').append(hiddenStr);
            $('#saveForm').submit();
        }
    </script>
</head>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'明细栏目管理'" scope="request"/>

<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body onkeydown="changeKey();">

<s:form action="/app/querySectionsDetailList.action" method="POST" id="sectionsDetailForm" namespace='/app'>
    <input type="hidden" id="rtnMsg" value="${msg}"/>
    <input type="hidden" name="identifi" value="<s:property value="identifi" />"/>
    <!-- hidden properties -->
    <input type="hidden" name="isEnable" value="1">
    <input type="hidden" name="userSt" value="1">
    <input type="hidden" name="pre_sectionId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>
    <input type="hidden" name="sections.sectionsId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>

    <!-- 数据编辑区域 -->
    <table width="98%" class="content_table" align="center" cellpadding="0" style=" margin-top: 18px; "
           cellspacing="0">
        <tr>
            <td>
                <input class="addBtn" TYPE="button" onClick="popSelectProduct()" value=""/>
                <input class="delBtn" type="button" value="" onClick="batchDeleteSections();"/>
            </td>
        </tr>
        <tr>
            <td>
                <!-- 数据列表区域 -->
                <table width="95%" class="list_table" align="center" id="sectionsD"
                       cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
                    <tr id="sectionsDetailHeaderTr">
                        <th width="6%" align="center">
                            <input type='checkbox' name='allbox' id="select_all" onclick='checkAll(this)'
                                   style="margin-left: 11px;"> <%--<label for="checkbox"></label>--%>
                        </th>
                        <th width="26%" align="center">排序</th>
                        <th width="17%" align="center">品牌</th>
                        <th width="20%" align="center">产品名称</th>
                        <th width="16%" align="center">产品编号</th>
                        <th width="" align="center">SKU编号</th>
                    </tr>
                    <s:iterator id="custiterator" value="page.dataList" var="bigList" status="status">
                        <tr id="sectionsDetailTr_${status.index}">
                            <td align="center">
                                <input type="checkbox" id="sectionsDetailId" name="sectionsDetailIds"
                                       value='<s:property value="sectionsDetailId"/>'/>
                                <input type="hidden" name="productIdsTmp" value='${productId}'/>
                                <input type="hidden" name="skuIdsTmp" value="${skuId}"/>
                            </td>
                            <td align="center"><s:property value="sectionsCode"/>
                                <input type="text" name="sortno" onkeyup="value=value.replace(/[^0-9]/g,'')"
                                       reg="^((?!0)\d{1,2})$" tip="请输入0-99的整数" maxlength="2"
                                       id="sortno" style="width: 30px"
                                       value="<s:property value='sortno'/>"/>
                            </td>
                            <s:iterator id="custiterator55" value="#bigList.product">
                                <td align="center"><s:property value="#request.productBrandMap[brandId]"/></td>
                                <td align="center"><s:property value="name"/></td>
                                <td align="center"><s:property value="productNo"/></td>
                                <td align="center">
                                    <s:iterator id="proSku" value="#bigList.productSku">
                                        <s:if test="#proSku.productId==#bigList.productId">
                                            <s:property value="productSkuCode"/>
                                        </s:if>
                                    </s:iterator>
                                </td>
                            </s:iterator>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
    </table>
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
                <input class="saveBtn" type="button" onClick="submitForm()" value=""/>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="gotoList()"/></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <!-- 隐藏数据区 -->

    <br>
    <br>
    <s:if test='!msg.isEmpty()'>
        <script language="JavaScript">
            alert(document.getElementById("rtnMsg").value);
        </script>
    </s:if>
</s:form>
<s:form action="/app/saveSectionsDetail.action" method="POST" id="saveForm">
    <input type="hidden" name="pre_sectionId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>
    <input type="hidden" name="identifi" value="<s:property value="identifi" />"/>
    <input type="hidden" name="sections.sectionsId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>
</s:form>
<s:form action="/app/delSectionsDetail.action" method="POST" id="delForm">
    <input type="hidden" name="identifi" value="<s:property value="identifi" />"/>
    <input type="hidden" name="pre_sectionId" id="pre_sectionId1" value="<s:property value="pre_sectionId"/>"/>
    <input type="hidden" name="sections.sectionsId" id="pre_sectionId" value="<s:property value="pre_sectionId"/>"/>

</s:form>
<s:form name="listForm" method="post" action="/app/querySectionsList.action" id="listForm">
    <s:hidden type="hidden" name="checkedId"/>
    <s:hidden name="sectionsForSelectPara.sectionsCode"/>
    <s:hidden name="sectionsForSelectPara.sectionsName"/>
    <s:hidden name="sectionsForSelectPara.identification"/>
    <s:hidden name="page.pageNo"/>
</s:form>
<script type="text/javascript">
    addProductIdToSaveForm();
</script>
</body>
</html>