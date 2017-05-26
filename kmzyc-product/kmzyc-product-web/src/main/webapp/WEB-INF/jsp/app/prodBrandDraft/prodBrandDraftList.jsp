<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>管理员管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
    <script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
    <script src="/etc/js/97dater/WdatePicker.js"></script>
    <style type="text/css">
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:set name="parent_name" value="'供应商管理'" scope="request"/>
<s:set name="name" value="'供应商品牌审核列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/app/showProdBrandDraftList.action" method="POST" namespace='/basedata' id="frm" name='frm'>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <s:if test='!rtnMessage.isEmpty()'>
        <SCRIPT LANGUAGE="JavaScript">
            <!--
            alert(document.getElementById("rtnMsg").value);
            //-->
        </SCRIPT>
    </s:if>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>申请商家：
                <input type="text" id="autocomplete_forSuppliers"
                       value="<s:property value='prodBrandDraft.merchantName'/>"
                       name="prodBrandDraft.merchantName" size="30"/>
            </td>
            <td>品牌名称：
                <s:textfield name="prodBrandDraft.brandName"
                             cssClass="input_style" id="brandName" size="30"/>
            </td>
            <td align="right">申请时间：
                <input value="<s:date name='prodBrandDraft.beforeApplyTime' format='yyyy-MM-dd HH:mm:ss' />"
                       class="Wdate" readOnly="readOnly" name='prodBrandDraft.beforeApplyTime' type="text"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                至
                <input value="<s:date name='prodBrandDraft.endApplyTime' format='yyyy-MM-dd HH:mm:ss' />"
                       class="Wdate" readOnly="readOnly" name='prodBrandDraft.endApplyTime' type="text"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
            </td>
            <td align="right">处理状态：
                <s:select list="#request.prodBrandDraftStatusMap" name="prodBrandDraft.status" id="prodBrandDraftStatus"
                          headerKey="" headerValue="所有"></s:select>
            </td>
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input TYPE="button" onClick="doSearch()" class="queryBtn">
            </td>
        </tr>
    </table>

    <!-- 数据列表区域 -->
    <table id="mytable" width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
        <tr>
            <th align="center" width="10%">申请商家</th>
            <th align="center" width="15%">品牌名称</th>
            <th align="center" width="15%">logo图片</th>
            <th align="center" width="15%">英文名称</th>
            <th align="center" width="15%">中文拼音</th>
            <th align="center" width="10%">申请时间</th>
            <th align="center" width="5%">处理状态</th>
            <th align="center" width="10%">审核时间</th>
            <th align="center" width="10%">操作</th>
        </tr>
        <s:iterator id="custiterator" value="page.dataList">
            <tr>
                <td align="center"><s:property value="merchantName"/></td>
                <td align="center"><s:property value="brandName"/></td>
                <td align="center">
                    <img width="142" height="50" alt="<s:property value="brandName"/>"
                         title="<s:property value="brandName"/>"
                         src="<s:property value="path" /><s:property value="logoPath"/>"/></td>
                <td align="center">
                    <s:property value="engName"/>
                </td>
                <td align="center">
                    <s:property value="chnSpell"/>
                </td>
                <td align="center">
                    <s:date name="applyTime" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td align="center">
                    <s:property value="#request.prodBrandDraftStatusMap[status]"/>
                </td>
                <td align="center">
                    <s:date name="checkTime" format="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td align="center">
                    <img title="查看" style="cursor: pointer;" src="/etc/images/little_icon/search.png" onclick="gotoView(<s:property value='brandId'/>)"/>
                    <s:if test="status == 2">
                        <img title="查看原因" style="cursor: pointer;" src="/etc/images/button_new/search.png" onclick="reasonView('${reasons}')"/>
                    </s:if>
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
    <br><br>
</s:form>
<script language="JavaScript">
    function gotoView(id) {
        location.href = "/app/gotoProdBrandDraftView.action?prodBrandId=" + id;
    }

    function doSearch() {
        document.getElementById('pageNo').value = 1;
        document.forms['frm'].submit();
    }

    function reasonView(reasons) {
        alert("审核不通过的原因：" +　reasons);
    }
</script>
</body>
</html>