<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>供应商列表</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <style type="text/css">
        .tableStyle1 {
            font-size: 12px;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:set name="parent_name" value="'添加套餐'" scope="request"/>
<s:set name="name" value="'选择供应商'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="showSupplierInfo.action" method="POST" id="frm" name='frm'>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="100"
           cellpadding="0" cellspacing="0">
        <tr>
            <%-- 根据查询字段的多少判断colspan--%>
            <td width="80%" valign="middle" colspan="4">
                <input class="addBtn" type="button" value="" onclick="addSupplier();"/>
        </tr>
        <tr>
            <td>名称：&nbsp; &nbsp; <s:textfield name="selectSuppliersInfo.corporateName" cssClass="input_style"/></td>
            <td></td>
            <td>
                商户类型：&nbsp; &nbsp;
                <s:if test="selectSupplierType==1">
                    <s:select name="selectSuppliersInfo.supplierType" headerKey="" list="#{3:'代销'}"></s:select>
                    <input value="1" type="hidden" name="selectSupplierType"></input>
                </s:if>
                <s:else>
                    <s:select name="selectSuppliersInfo.supplierType" headerKey="" list="#{2:'入驻'}"></s:select>
                    <input type="hidden" value="2" name="selectSupplierType"></input>
                </s:else>
            </td>
            <td></td>
        </tr>
        <tr>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td><input type="submit" class="queryBtn" value=""/></td>
            <td></td>
        </tr>
    </table>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%"></th>
            <th align="center" width="10%">商户编号</th>
            <th align="center" width="14%">公司名称</th>
            <th align="center" width="10%">商户类型</th>
        </tr>
        <s:if test="selectSupplierType==1">
            <tr>
                <td><input type="radio" name="radios" value='221,康美自营'/></td>
                <td align="center">221</td>
                <td align="center">康美自营</td>
                <td align="center">自营</td>
            </tr>
        </s:if>
        <s:iterator id="supplieriterator" value="page.dataList">
            <tr>
                <td align="center" width="5%"><input type="radio" id="check" name="radios" value='<s:property value="supplierId"/>,<s:property value='corporateName' />'>
                </td>
                <td align="center"><s:property value="supplierId"/></td>
                <td align="center"><s:property value="corporateName"/>
                </td>
                <td align="center">
                    <s:if test="supplierType == 1">自营</s:if>
                    <s:elseif test="supplierType == 2">入驻</s:elseif>
                    <s:elseif test="supplierType == 3">代销</s:elseif>
                </td>
            </tr>
        </s:iterator>
    </table>

    <!-- 分页按钮区 -->
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>

    <br>
    <br>
</s:form>
<form name="frm" method="post">
    <input type="hidden" name="pageNum" value="<s:property value='pageNum' />"/>
    <input type="hidden" name="selectSuppliersInfo.corporateName"
           value="<s:property value='selectSuppliersInfo.corporateName' />"/>
    <input type="hidden" name="selectSuppliersInfo.corporateLocation"
           value="<s:property value='selectSuppliersInfo.corporateLocation' />"/>
    <input type="hidden" name="selectSuppliersInfo.contactsName"
           value="<s:property value='selectSuppliersInfo.contactsName' />"/>
    <input type="hidden" name="selectSuppliersInfo.status"
           value="<s:property value='selectSuppliersInfo.status' />"/>
    <input type="hidden" name="selectSuppliersInfo.enterpriseStatus"
           value="<s:property value='selectSuppliersInfo.enterpriseStatus' />"/>
</form>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        alert(document.getElementById("rtnMsg").value);
    </script>
</s:if>
<script language="JavaScript">
    function addSupplier() {
        var radioObj = document.getElementsByName("radios");
        var cks = "0";
        for (var i = 0; i < radioObj.length; i++) {
            if (radioObj[i].checked) {
                cks = "1";
            }
        }
        if (cks == "0") {
            alert("请选择商户！");
            return;
        }
        var parent_supplierIds = new Array();
        var params = new Array();
        $("input[type='radio'][name='radios']:checked").each(function (i) {
            var str = $(this).val().split(',');
            params.push(str);
        });
        var obj = parent.document.getElementsByName("skuIds");
        if (parent.document.getElementById("ra_ruzhu").checked && obj.length > 0) {
            var table = parent.document.getElementById("sectionsD"),
                    trs = table.getElementsByTagName("tr");
            for (var i = trs.length - 1; i > 0; i--) {
                table.deleteRow(i);
            }
        }
        parent.closeOpenSupplier(params);
    }
</script>
</body>
</html>