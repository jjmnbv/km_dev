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
    <script type='text/javascript'></script>
    <script type='text/javascript'></script>
    <script src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
    <script type="text/javascript" src="/etc/js/kindeditor_ZF_show.js"></script>

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
<s:set name="name" value="'组方列表查询'" scope="request"/>
<s:set name="son_name" value="'查看组方内容'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<br/>
    <%-- 数据列表区域 --%>
	<span style="padding-left:20px">
		组方名称：<input value="<s:property value="productRelation.relationName" />" readOnly="true"/>
	</span>
<table width="98%" class="list_table" align="center" cellpadding="3"
       cellspacing="0" border="1" bordercolor="#C1C8D2" id="sectionsD">
    <tr>
        <th align="center" width="5%">产品skuId</th>
        <th align="center" width="12%">副产品编号</th>
        <th align="center" width="15%">副产品sku</th>
        <th align="center" width="12%">副产品名称</th>
        <th align="center" width="10%">品牌</th>
        <th align="center" width="10%">状态</th>
        <s:if test="relationType==0">
            <th align="center" width="9%">原价格</th>
        </s:if>
        <th align="center" width="8%">价格</th>
        <s:if test="relationType==0">
            <th align="center" width="5%">更新价格</th>
        </s:if>
    </tr>
    <s:iterator id="productiterator" value="page.dataList" status="index">
        <tr>
            <td align="center">
                <s:property value="skuId"/>
            </td>
            <td align="center">

                <s:property value="productNo"/>
            </td>
            <td align="center" width="5%">
                <s:property value="productSkuCode"/>
            </td>
            <td align="center">
                <s:property value="procuctName"/>
            </td>
            <td align="center" style="word-break: break-all">
                <s:property value='brandName'/>
            </td>
            <td align="center" style="word-break:break-all">
                <s:iterator value="#request.productRelationStatusMap">
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
        </tr>
    </s:iterator>
</table>
<br/>

<%-- 组方详情 --%>
<div>
    <textarea id="editor_id" name="productRelation.relationIntroduce"
              style="height: 400px; width: 98%; resize: none;">
        <s:property value="productRelation.relationIntroduce"/>
    </textarea>
</div>
</br>
<!-- 做法视频 -->
<div>
    视频地址：<input value="<s:property value="productRelation.relationVideo" />" style="width:500px;" readOnly="true"/>
</div>
</br>
<!-- 组方简介 -->
<div>
    组方简介：<input value="<s:property value="productRelation.relationIntro" />" readOnly="true"/>
</div>
<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30"
       border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center">
            <input type="button" class="backBtn" onClick="goToBack()"/></td>
        <td width="20%" align="center"></td>
    </tr>
</table>
<input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
<script>
    function goToBack() {
        location.href = "/productRelation//queryZFProductRelation.action";
    }
</script>
</body>
</html>