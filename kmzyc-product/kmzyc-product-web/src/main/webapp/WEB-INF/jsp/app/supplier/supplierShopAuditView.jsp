<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看店铺信息</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script type='text/javascript' src="/etc/js/jquery-latest.pack.js"></script>
    <script type='text/javascript' src="/etc/js/dialog.js"></script>
    <Script type='text/javascript' src="/etc/js/97dater/WdatePicker.js"></Script>
    <script type='text/javascript'src='/etc/js/dialog-common.js'></script>
    <script type='text/javascript'src='/etc/js/artDialog4.1.7/artDialog.js?skin=default'></script>
    <script type='text/javascript'src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js'></script>
    <script type='text/javascript'src='/etc/js/jquery.blockUI.js'></script>
</head>
<body onkeydown="changeKey();">
<!-- 导航栏 -->
<s:set name="parent_name" value="'供应商管理'" scope="request"/>
<s:set name="name" value="'供应商店铺列表'" scope="request"/>
<s:set name="son_name" value="'供应商店铺审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3"
       cellspacing="0" border="1" bordercolor="#C7D3E2"
       style="border-collapse: collapse; font-size: 12px;">
    <!-- error message -->
    <s:if test="rtnMessage != null">
        <tr>
            <td colspan="2" align="center">&quot;<font color="red"><s:property
                    value='rtnMessage'/> </font>
            </td>
        </tr>
    </s:if>
    <tr>
        <th colspan="2" align="left" class="edit_title">基本信息</th>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">店铺名称：</th>
        <td><s:property value="showShopMain.shopName"/>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">店铺标题：</th>
        <td><s:property value="showShopMain.shopTitle"/></td>
    </tr>
    <%--
    <tr>
        <th align="right" class="eidt_rowTitle">店铺LOGO：</th>
        <td><img width="142px" height="50px" src="${imagePaths}${showShopMain.logoPath}"></td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">店铺展示图片：</th>
        <td><img width="300px" height="199px" src="${imagePaths}${showShopMain.filePath}"></td>
    </tr>
    --%>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">经营品牌：</th>
        <td width="80%"><s:property value="showShopMain.manageBrand"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">店铺负责人姓名：</th>
        <td width="80%"><s:property value="showShopMain.principalName"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">店铺负责人电话：</th>
        <td width="80%"><s:property value="showShopMain.contactInfo"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">店铺负责人手机：</th>
        <td width="80%"><s:property value="showShopMain.linkmanMobile"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">商户名称：</th>
        <td width="80%"><s:property value="showShopMain.corporateName"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">客服联系方式：</th>
        <td width="80%"><s:property value="showShopMain.serviceQq"/>
        </td>
    </tr>
    <tr>
        <th width="20%" align="right" class="eidt_rowTitle">店铺类型：</th>
        <td width="80%">
            <s:if test="showShopMain.shopType == 1">旗舰店</s:if>
            <s:if test="showShopMain.shopType == 2">专营店</s:if>
            <s:if test="showShopMain.shopType == 3">专卖店</s:if>
        </td>
    </tr>
    <tr>
        <th width="20%" class="eidt_rowTitle" align="right"></font>店铺简介：</th>
        <td width="80%"><s:property value="showShopMain.introduce"/>
        </td>
    </tr>
    <%--
        <tr>
            <th align="right" class="eidt_rowTitle">详细描述：</th>
            <td><s:property value="showShopMain.shopName" /></td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺级别：</th>
            <td><s:property value="showShopMain.shopLevel" />
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺站点：</th>
            <td><s:property value="showShopMain.shopSite"/>
            </td>
        </tr>
    --%>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30"
       border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
    <tr>
        <td align="left">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="backBtn" onClick="gotoList()"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="btn-custom" value="审核通过"
                   onClick="auditSupplier(<s:property value='showShopMain.shopId'/>)" class="btnStyle">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="btn-custom" value="不通过"
                   onClick="notAuditSupplierShop(<s:property value='showShopMain.shopId'/>)" class="btnStyle">
        <td width="20%" align="center">&nbsp; <br>
        </td>
    </tr>
</table>

<br>
<br>

<form name="frm" method="post">
    <input type="hidden" name="pageNum" value="<s:property value='pageNum' />"/>
    <input type="hidden" name="searchShopMain.shopTitle" value="<s:property value='searchShopMain.shopTitle' />"/>
    <input type="hidden" name="searchShopMain.shopName" value="<s:property value='searchShopMain.shopName' />"/>
    <input type="hidden" name="searchShopMain.status" value="<s:property value='searchShopMain.status' />"/>
    <input type="hidden" name="searchShopMain.auditStatus" value="<s:property value='searchShopMain.auditStatus' />"/>
</form>

<script language="JavaScript">
    function gotoList() {
        document.forms[0].action = "/app/supplierShopList.action";
        document.forms[0].submit();

    }
    function auditSupplier(id) {
        var st = true;
        $.ajax({
            url: '/app/ckSupplierShopStatus.action',
            async: false,
            data: 'shopId=' + id,
            success: function (info) {
                if ('0' == info) {
                    alert("此店铺没有提交审核!");
                    st = false;
                    document.forms[0].action = "/app/supplierShopList.action";
                    document.forms[0].submit();
                }
            }
        });
        if (st) {
            var answer = confirm("确认审核通过吗?");
            if (!answer) {
                return false;
            }
            $.ajax({
                url: '/app/auditSupplierShop.action',
                async: false,
                data: 'shopId=' + id + "&ckType=" + 1,
                success: function (info) {
                    if ('0' == info) {
                        alert("系统异常，审核失败!");
                        document.forms[0].action = "/app/supplierShopList.action";
                        document.forms[0].submit();
                        return;
                    } else {
                        alert("审核成功!");
                        document.forms[0].action = "/app/supplierShopList.action";
                        document.forms[0].submit();
                    }
                }
            });
        }
    }

    function notAuditSupplierShop(id) {
        $.ajax({
            url: '/app/ckSupplierShopStatus.action',
            async: false,
            data: 'shopId=' + id,
            success: function (info) {
                if ('0' == info) {
                    alert("此店铺没有提交审核!");
                    document.forms[0].action = "/app/supplierShopList.action";
                    document.forms[0].submit();
                }
            }
        });
        popDialog('/app/supplierShopForCause.action?shopId=' + id, '填写不通过的理由', 550, 330);
    }
    function closes() {
        document.forms[0].action = "/app/supplierShopList.action";
        document.forms[0].submit();
        closeThis();
    }
</script>
</body>
</html>