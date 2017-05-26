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
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:form action="/productRelation/viewProductDeatil.action" method="post" namespace='/productRelation' id="frm" name='frm'>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <input type="hidden" name="mainSkuId" value="<s:property value='mainSkuId'/>"/>
        <input type="hidden" name="relationId" value="<s:property value='productRelationDetail.relationId'/>"/>
        <input type="hidden" name="productRelationDetail.relationId"
               value="<s:property value='productRelationDetail.relationId'/>"/>
        <input type="hidden" name="relationType" value="<s:property value='relationType' />"/>
        <tr>
            <td></td>
            <td><input type="button" value="返回" onclick="goToBack()"/></td>
        </tr>
    </table>
    <br/>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="12%">产品编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="12%">产品标题</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">状态</th>
            <s:if test="relationType==0">
                <th align="center" width="9%">原价格</th>
            </s:if>
            <th align="center" width="8%">价格</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
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

    <!-- 分页按钮区 -->
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>

    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
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
        if (relationType == 0) {
            location.href = "/productRelation/addOtherProductRelation.action?productTied.productSkuId=" + mainSkuId
                    + "&relationId=" + relationId + "&relationType=" + relationType + "&pageNum=" + pageNum;
        } else {
            location.href = "/productRelation/addOtherTypeProductRelation.action?productTied.productSkuId=" + mainSkuId
                    + "&relationId=" + relationId + "&relationType=" + relationType + "&pageNum=" + pageNum;
        }
    }

    function goToBack() {
        var pageNum = $("#pageNum").val();
        var mainSkuId = $("input[name='mainSkuId']").val();
        location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId + "&pageNum=" + pageNum;
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
            url: "/productRelation/updatePrice.action?productRelationDetail.relationDetailId=" + relationDetailId
            + "&productRelationDetail.relationSkuPrice=" + updatePrice
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
</script>
</body>
</html>