<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <style type="text/css">
        .tableStyle1 {
            font-size: 12px;
        }
    </style>
    <script type="text/javascript" scr="/etc/js/jquery-1.8.3.js"></script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'产品搭售'" scope="request"/>
<s:set name="son_name" value="'产品搭售添加'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/tiedSale/addSearch.action" method="POST"
        namespace="tiedSale" id="frm" name='frm'>
    <input type="hidden" name="product.productSkuId"
           value="<s:property value='product.productSkuId' />"/>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="100"
           cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <strong> 主产品名称：<s:property value="prod.procuctName"/> </strong>
            </td>
        </tr>
        <tr>
        </tr>
        <tr>
            <!-- 根据查询字段的多少判断colspan-->
            <td width="80%" valign="middle" colspan="5">
                <input class="addBtn" TYPE="button" value="" onclick="tiedSalecommit();"/>
                <input class="backBtn" type="button" onclick="back()">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        </tr>
        <tr>
            <td>产品货物编号： <s:textfield type="text" name="product.productNo"
                                     cssClass="input_style" id="productNo"/></td>
            <td>名称：&nbsp;&nbsp;<s:textfield type="text"
                                            name="product.procuctName" cssClass="input_style" id="productName"/>
            </td>
            <td>状态：&nbsp; &nbsp;&nbsp;<s:select
                    list="#request.productStatusMap" name="product.status"
                    id="productStatus" headerKey="" headerValue="--全部状态--"></s:select>
            </td>
        </tr>
        <tr>
            <td>类别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <s:select
                    list="#request.categoryList" id="categoryId1" listKey="categoryId"
                    listValue="categoryName" headerKey="0" headerValue="--一级类目--"
                    onchange="change1('categoryId1','categoryId2');"></s:select> <select
                    id="categoryId2" onchange="change1('categoryId2','categoryId3');">
                <option value="0">--二级类目--</option>
            </select> <select id="categoryId3" name="product.categoryId">
                <option value="0">--三级类目--</option>
            </select>
            </td>
            <td>关键字：<s:textfield type="text" cssClass="input_style"
                                 name="product.keyword" id="keyword"/></td>
        </tr>
        <tr>
            <td>品牌：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:select
                    list="#request.productBrandMap" id="brandId" headerKey=""
                    style="width: 116px" name="product.brandId" headerValue="--全部品牌--"></s:select>
            </td>
            <td><input type="button" onclick="search()" class="queryBtn"
                       value="">
            </td>
        </tr>
    </table>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           id="dataTable" cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%"><input type='checkbox'
                                                 id='allbox' name='allbox' onclick='checkAll(this)'></th>
            <th align="center" width="10%">产品货物编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品名称</th>
            <th align="center" width="12%">品牌</th>
            <th align="center" width="10%">关键字</th>
            <th align="center" width="10%">状态</th>
            <th align="center" width="15%">搭售类型</th>

        </tr>
        <s:iterator id="productiterator" value="page.dataList" id="mode"
                    status="index">
            <tr class="dataTr">
                <td align="center" width="5%"><input type="checkbox"
                                                     name="productSkuId" value="<s:property value='productSkuId' />"/>
                </td>
                <input type="hidden" name="mainSku"
                       value="<s:property value='prod.productSkuId' />"/>
                <td align="center"><s:property value="productNo"/></td>
                <td align="center"><s:property value="productSkuCode"/>
                </td>
                <td align="center"><s:property value="procuctName"/></td>
                <td align="center"><s:property value="brandName"/></td>
                <td align="center"><s:property value="keyword"/></td>
                <td align="center"><s:if test="status==3">已上架</s:if> <s:elseif
                        test="status==2">已审核,待上架</s:elseif> <s:elseif test="status==1">待审核</s:elseif>
                    <s:elseif test="status==0">草稿</s:elseif> <s:elseif
                            test="status==5">系统下架</s:elseif> <s:elseif test="status==4">已下架</s:elseif>
                </td>
                <td>
                    <s:iterator value="#request.tiedSadeType" id="entry">
                        <s:set name="cf" value="0"></s:set>
                        <s:iterator value="tiedSaleListed" id="target">
                            <s:if test="#entry.key==#target.tiedSadeType&&#mode.productSkuId==#target.tiedSadeSku">
                                <s:set name="cf" value="1"/>
                            </s:if>
                        </s:iterator>
                        <s:if test="#cf==0">
                            <input type="radio" name="tiedSaleType<s:property  value='#index.index'/>"
                                   value="<s:property value='#entry.key'/>">
                            <s:property value="#entry.value"/>
                        </s:if>
                        <s:elseif test="#cf==1">
                            <input type="radio" name="tiedSaleType" disabled="true">
                            <em><s:property value="#entry.value"/></em>
                        </s:elseif>
                    </s:iterator>
                </td>
                <input type="hidden" name="productTiedSaleType"/>
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
</body>
<script type="text/javascript">
    function gotoAdd(id) {
        var obj = document.getElementsByName(id);
        var count = 0;
        // 遍历所有用户，找出被选中的用户
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                count++;
            }
        }
        if (count == 0) {
            alert("请选择产品搭售。");
        } else if (confirm('是否确认该商品搭售?') == true) {
            document.frm.action = "/tiedSale/saveTied.action";
            document.frm.submit();
        }
    }

    function gotoView(id) {
        location.href = "/tiedSale/tiedSaleView.action?tiedSade.mainSku=" + id;
    }

    function search() {
        document.frm.action = "/tiedSale/addSearch.action";
        document.frm.submit();
    }

    function back() {
        document.frm.action = "/tiedSale/tiedSaleShow.action";
        document.frm.submit();
    }

    $().ready(function () {
        $("a").toggle(function () {
            $(this).siblings().hide();
        }, function () {
            $(this).siblings().show();
        });
    });

    function tiedSalecommit() {
        var flag = false;
        var flag1 = false;
        var array = $("input:checkbox:checked");
        if (array.length == 0) {
            alert("请选择产品搭售");
            flag = true;
            return;
        }
        array.each(function (i) {
            $(this).attr("name", "list[" + i + "]." + "tiedSadeSku");
            $(this).parent().parent()
                    .find("input[name='mainSku']")
                    .attr("name", "list[" + i + "]." + "mainSku");
            if ($(this).parent().parent().find("input:radio:checked").length != 1) {
                flag1 = true;
            }

            var typeValue = $(this).parent().parent().find("input:radio:checked").attr("value");
            $(this).parent().parent().find("input[name='productTiedSaleType']")
                    .attr("name", "list[" + i + "]." + "tiedSadeType")
                    .attr("value", typeValue);
        });
        if (flag1) {
            alert("请选择搭售的类型");
            return false;
        }
        if (flag) {
            return false;
        }
        document.frm.action = "/tiedSale/saveTied.action";
        document.frm.submit();
    }

    $(document).ready(
            function () {
                var trs = $("table .dataTr");
                $(trs).each(function () {
                            var radioLength = $(this).find("input:radio:enabled").length;
                            if (radioLength == 0) {
                                $(this).find(":checkbox").attr("disabled", "true");
                            }
                        }
                );
            }
    );
</script>
</HTML>