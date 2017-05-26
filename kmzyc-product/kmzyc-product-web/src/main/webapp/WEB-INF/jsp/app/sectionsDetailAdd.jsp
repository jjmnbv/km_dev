<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'明细栏目管理'" scope="request"/>
<s:set name="son_name" value="'明细栏目添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>增加明细</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
    <Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:form name="popForm" action="saveSectionsDetail.action" id="sectionsDetailForm" method="POST" namespace='/sys'
        enctype="multipart/form-data">
    <!-- hidden properties -->
    <input type="hidden" name="isEnable" value="1">
    <input type="hidden" name="menuUpid"
           value="<s:property value='model.menuUpid'/>">
    <input type="hidden" name="menuLv"
           value="<s:property value='model.menuLv'/>">
    <input type="hidden" name="createUser"
           value="<s:property value="#session['sysUser'].userId"/>">
    <input type="hidden" name="pre_sectionId" id="sectionsId"
           value="<s:property value="sectionsDetail.sectionsId"/>"/>
    <input type="hidden" name="sectionsDetail.sectionsId" id="sectionsId"
           value="<s:property value="sectionsDetail.sectionsId"/>"/>
    <input type="hidden" name="sectionsDetail.sectionsDetailId"
           id="sectionsDetailId"
           value="<s:property value="sectionsDetail.sectionsDetailId"/>"/>
    <input type="hidden" name="viewType" id="viewType"
           value="<s:property value="viewType"/>"/>
    <input type="hidden" name="old_image" id="old_image"
           value="<s:property value="sectionsDetail.image"/>"/>


    <table width="95%" class="edit_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px;">
        <!-- error message -->
        <s:if test="rtnMessage != null">
            <tr>
                <td colspan="2" align="center"><font color="red"><s:property value='rtnMessage'/></font></td>
            </tr>
        </s:if>
        <tr>
            <th colspan="2" align="left" class="edit_title">新增明细</th>
        </tr>

        <tr>
            <th align="right" class="eidt_rowTitle"><font color="red">*</font>产品名称：</th>
            <td>
                <s:if test="viewType=='add'">
                    <input type="text" id="productName" value="" name="productName" readonly="true"/>
                    <input type="hidden" name="sectionsDetail.productId" id="productId" value="" readonly/>
                </s:if>
                <s:if test="viewType=='edit'">
                    <input type="text" name="productName" value="" name="productName" readonly="true"/>
                    <input type="hidden" name="sectionsDetail.productId" id="productId" readonly="true"
                           value="<s:property value='sectionsDetail.productId' />"/>
                </s:if>
                &nbsp;&nbsp;
                <input type="button" class="button-2s" value="选择" onClick="popSelectProduct()">
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">产品编号：</th>
            <td>
                <input type="text" id="productNo" name="productNo" value="" readonly="true"/>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">产品排序：</th>
            <td>
                <s:if test="viewType=='add'">
                    <input type="text" name="sectionsDetail.sortno" value=""/>
                </s:if>
                <s:if test="viewType=='edit'">
                    <input type="text" name="sectionsDetail.sortno"
                           value="<s:property value='sectionsDetail.sortno' />"/>
                </s:if>
            </td>
        </tr>
    </table>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30"
           border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
        <tr>
            <td align="center">
                <input type="submit" class="saveBtn" value=""/>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="gotoList();"/>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <br>
    <br>
</s:form>
<script language="JavaScript">

    $(document).ready(function () {
        $("#sectionsDetailForm").validate({
            rules: {
                "productName": {required: true, maxlength: 64, unusualChar: true},
                "productNo": {required: true, maxlength: 32, unusualChar: true},
                "sectionsDetail.sortno": {required: true, number: true, unusualChar: true, rangelength: [0, 2]}
            },
            success: function (label) {
                label.removeClass("checked").addClass("checked");
            }
        });
    });

    function popSelectProduct() {
        dialog("产品选择", "iframe:/app/findProductForSections.action?type=stock", "1000px", "550px", "iframe", 50);
    }

    function closeOpenSku(productId, productName, productNo) {
        closeThis();
        document.forms[0].productId.value = productId;
        document.forms[0].productName.value = productName;
        document.forms[0].productNo.value = productNo;
    }

    function saveClass() {
        if (Validator.Validate(window.document.popForm, 3)) {
            window.document.popForm.submit();
            parent.closePopDiv2();
        }
    }

    function closeDiv() {
        parent.closeThis();
    }

    function changePath(arg) {
        document.getElementById("sectionsDetailimage").value = arg.value;
    }

</script>
<style type="text/css">
    /**输入框错误提示**/
    label.error {
        background: url(/etc/images/li_err.gif) no-repeat;
        font: 12px/ 1 verdana, simsun, sans-serif;
        margin: 0;
        padding-left: 20px;
        height: 20px;
        line-height: 20px;
        margin-left: 10px;
    }

    label.checked {
        background: url(/etc/images/li_ok.gif) no-repeat;
        font: 12px/ 1 verdana, simsun, sans-serif;
        margin: 0;
        padding-left: 20px;
        height: 20px;
        line-height: 20px;
        margin-left: 10px;
    }
</style>
</body>
</html>