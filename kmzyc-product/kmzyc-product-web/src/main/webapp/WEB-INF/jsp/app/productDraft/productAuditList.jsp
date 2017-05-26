<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script src="/etc/js/97dater/WdatePicker.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.form.js"></script>
    <script src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <script src="/etc/js/dialog-common.js"></script>
    <script type="text/javascript" src="/etc/js/productDraft/product.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/demo.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        <!--
        alert(document.getElementById("rtnMsg").value);
        //-->
    </script>
</s:if>
<s:set name="parent_name" value="'产品审核管理'" scope="request"/>
<s:set name="name" value="'产品信息审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<form action="/app/productDraftShow.action?type=audit" method="POST" namespace='/app'
      id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <%--<!-- 查询条件区域 -->--%>
    <table width="98%" class="content_table" align="center"
           cellpadding="0" cellspacing="0">
        <tr>
            <td align="right">编码：</td>
            <td align="left"><s:textfield name="productForSelectPara.productNo"
                                          cssClass="input_style" id="productNo"/></td>
            <td align="right">标题：</td>
            <td align="left"><s:textfield name="productForSelectPara.productTitle"
                                          cssClass="input_style" id="productName" size="32"/></td>
            <td align="right">类别：</td>
            <td align="left">
                <s:select list="#request.categoryList"
                          name="bCategoryId" id="categoryId1" listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--一级类目--"
                          onchange="change2('categoryId1','categoryId2');"></s:select>
                <s:select list="#request.mCategoryList"
                          name="mCategoryId" id="categoryId2" listKey="categoryId" listValue="categoryName"
                          headerKey="" headerValue="--二级类目--"
                          onchange="change2('categoryId2','categoryId3');"></s:select>
                <s:select list="#request.sCategoryList" id="categoryId3"
                          headerKey="" headerValue="--三级类目--"
                          name="productForSelectPara.categoryId" listKey="categoryId"
                          listValue="categoryName"></s:select>
            </td>
        </tr>
        <tr>
            <td align="right">品牌：</td>
            <td align="left">
                <input type="text" id="autocomplete"
                       value="<s:property value='productForSelectPara.searchBrandName'/>"
                       name="productForSelectPara.searchBrandName" size="32"/>
            </td>
            <td align="right">关键字：</td>
            <td align="left">
                <s:textfield cssClass="input_style"
                             name="productForSelectPara.keyword"
                             id="keyword" size="32"/>
            </td>
        </tr>
        <tr>
            <td align="right">提交审核时间：</td>
            <td align="left">
                <input name="productForSelectPara.beforeToCheckTime" readonly
                       type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       value="<s:property value='productForSelectPara.beforeToCheckTime' />">
                &nbsp;至&nbsp;
                <input name="productForSelectPara.afterToCheckTime" readonly
                       type="text" class="input_style" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       value="<s:property value='productForSelectPara.afterToCheckTime' />">
            </td>
            <td align="right">商家名称：</td>
            <td align="left">
                <input type="text" id="autocomplete_forSuppliers"
                       value="<s:property value='productForSelectPara.supplierNameForSearch'/>"
                       name="productForSelectPara.supplierNameForSearch" size="32"/>
            </td>
            <td align="right">商家类型：</td>
            <td align="left"><s:select list="#request.SupplyTypeMap"
                                       name="productForSelectPara.supplyType" headerKey="0"
                                       headerValue="--请选择--"></s:select>
            </td>
        </tr>
        <tr>
            <td align="right" colspan="6">
                <input type="button" onClick="doSearch()" class="queryBtn" value=""/>&nbsp;
                <input type="button" value="审核通过" class="btn-custom btnStyle" onclick="batchAuditProduct('2')"/>&nbsp;
                <input type="button" value="不通过" class="btn-custom btnStyle" onclick="batchAuditProduct('6')"/>
            </td>
        </tr>
    </table>

    <%--<!-- 数据列表区域 -->--%>
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/></th>
            <th align="center" width="20%">产品标题</th>
            <th align="center">商家类型</th>
            <th align="center" width="8%">编码</th>
            <th align="center">品牌</th>
            <th align="center" width="12%">关键字</th>
            <th align="center" width="8%">状态</th>
            <th align="center" width="8%">类型</th>
            <th align="center">商家名称</th>
            <th align="center">提交审核时间</th>
            <th align="center">操作</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList">
            <tr>
                <td align="center" width="5%">
                    <input type="checkbox" name="productIdChk" value='<s:property value="productId"/>'>
                </td>
                <td align="center"><s:property value="productTitle" escape="false"/></td>
                <td align="center">
                    <s:property value="#request.SupplyTypeMap[supplyType]"/>
                </td>
                <td align="center"><s:property value="productNo"/></td>
                <td align="center"><s:property value="prodBrand.brandName"/></td>
                <td align="center"><s:property value="keyword"/></td>
                <td align="center"><s:property value="#request.productStatusMap[status]"/></td>
                <td align="center"><s:property value="#request.DraftTypeMap[opType]"/></td>
                <td align="center">
                    <s:if test="merchantName==null">
                        康美
                    </s:if>
                    <s:else>
                        <s:property value="merchantName"/>
                    </s:else>
                </td>
                <td align="center">
                    <s:date name="toCheckTime" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td align="center">
                    <img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png"
                         onclick="gotoAuditViewProduct(<s:property value='productId'/>)"/>
                </td>
            </tr>
        </s:iterator>
    </table>

    <%--<!-- 分页按钮区 -->--%>
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>
    <br>
    <br>

    <div id="auditReason" style="display: none;">
        <textarea id="reasonArea" rows="10" cols="100" style="resize: none;"></textarea>
    </div>
    <s:hidden name="reasonText" id="reasonText"></s:hidden>
    <s:hidden name="auditStatus" id="auditStatus"></s:hidden>
</form>
<s:form action="/app/productuUpShelf.action" method="POST"
        namespace='/app' id="upForm" name='upForm'>
</s:form>
<s:form action="/app/productuDownShelf.action" method="POST"
        namespace='/app' id="downForm" name='downForm'>
</s:form>
<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <s:hidden name="productForSelectPara.status" id="statusChecked"/>
</s:form>
</BODY>
</HTML>
