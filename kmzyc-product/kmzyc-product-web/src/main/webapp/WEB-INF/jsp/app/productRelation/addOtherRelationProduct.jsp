<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/common.js"></script>
    <script src="/etc/js/jquery-1.8.3.js"></script>
    <script src="/etc/js/validate/easy_validator.pack.js"></script>
    <style type="text/css">
        body {
            padding: 0px;
            margin: 0px;
        }
        table {
            margin-left: 10px;
        }
    </style>
    <title>列表选择SKU码</title>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:form action="/productRelation/addOtherProductRelation.action"
        method="post" namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <tr>
            <td>状态：&nbsp;<s:select list="#request.productRelationStatusMap"
                                   name="productTied.status" id="productStatus" headerKey=""
                                   headerValue="--全部状态--"></s:select>
            </td>
        </tr>
        <tr>
            <td>类别： <s:select list="#request.categoryList" id="categoryId1"
                              listKey="categoryId" listValue="categoryName"
                              headerKey="" headerValue="--一级类目--"
                              onchange="change1('categoryId1','categoryId2');"></s:select>
                <select id="categoryId2" onChange="change1('categoryId2','categoryId3');">
                    <option value="">--二级类目--</option>
                </select>
                <select id="categoryId3" name="productTied.categoryId">
                    <option value="">--三级类目--</option>
                </select>
            </td>
            <td>品牌：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:select list="#request.productBrandMap" id="brandId"
                          headerKey="" style="width:116px"
                          name="productTied.brandId" headerValue="--全部品牌--"></s:select>
            </td>
        </tr>
        <tr>
            <td>产品编号：&nbsp; <s:textfield name="productTied.productNo" cssClass="input_style" id="productNo"/></td>
            <td>名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:textfield type="text" name="productTied.procuctName" cssClass="input_style" id="productName"/>
                <input type="hidden" name="productTied.productSkuId"
                       value="<s:property value='productTied.productSkuId'/>"/>
                <input type="hidden" name="relationId" value="<s:property value='relationId' />"/>
                <input type="hidden" name="relationType" value="<s:property value='relationType' />"/>
                <input TYPE="button" onClick="search()" class="btngray" style="height:30px" value="查询"/></td>
        </tr>
        <tr>
            <td><input type="button" class="btngreen" style="height:30px" value="保存所选 " onclick="selectList();"/></td>
        </tr>
    </table>
    <br/>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>
            </th>
            <th align="center" width="15%">产品编号</th>
            <th align="center" width="15%">产品SKU编号</th>
            <th align="center" width="15%">产品名称</th>
            <th align="center" width="10%">品牌</th>
            <th align="center" width="10%">关键字</th>
            <th align="center" width="10%">状态</th>
            <th align="center" width="10%">销售价格</th>
            <th align="center" width="5%">关联产品价格</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="stuts">
            <tr>
                <td align="center">
                    <s:set name="flag" value="0"></s:set>
                    <s:iterator value="relationEdDetails" id="detail">
                        <s:if test="#detail.relationSkuId==productSkuId">
                            <s:set name="flag" value="1"></s:set>
                        </s:if>
                    </s:iterator>
                    <s:if test="#flag==0">
                        <input type="checkbox" name="checkSkuId" value="<s:property value='productSkuId'/>"/>
                    </s:if>
                    <s:elseif test="#flag!=0">
                        <input type="checkbox" name="checkSkuId" value="<s:property value='productSkuId'/>" disabled="true"/>
                    </s:elseif>
                </td>
                <input type="hidden" name="productRelationSku"/>
                <input type="hidden" name="productRelationPrice"/>
                <input type="hidden" name="productRelationType"/>
                <td align="center" width="5%"><s:property value="productNo"/></td>
                <td align="center" style="word-break: break-all">
                    <s:property value="productSkuCode"/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='procuctName'/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value='brandName'/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value="keyword"/>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:iterator value="#request.productRelationStatusMap">
                        <s:if test="status==key">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <td align="center" style="word-break: break-all">
                    <s:property value="price"/>
                </td>
                <td align="center" style="word-break: break-all">
                    <input type="text" name="relationProductPrice"/>
                </td>
                <input type="hidden" name="relationID"/>
            </tr>
        </s:iterator>
    </table>

    <table width="95%" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>
                <%@ include file="/WEB-INF/jsp/public/pager.jsp" %>
            </td>
        </tr>
    </table>
    <br/>
    <input type="hidden" name="pageNum" id="pageNum" value="<s:property value='pageNum'/>"/>
</s:form>

<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        alert(msg);
        if (msg) {
            parent.closeOpenSku();
        }
    </script>
</s:if>

<script type="text/javascript">
    function search() {
        document.frm.submit();
    }

    function selectList() {
        if ($("input:checked").length == 0) {
            alert("请添加关联的产品");
            return false;
        }
        var relationId = $("input[name='relationId']").val();
        var boxlist = $("input[name='checkSkuId']:checked");
        var count = 0;
        boxlist.each(function (i) {
            var productRelationSkuId = $(this).val();
            $(this).parent().parent().find("input[name='productRelationSku']").attr("name", "list1[" + i + "]." + "relationSkuId")
                    .attr("value", productRelationSkuId);
            var productRelationPrice = $(this).parent().parent().find("input[name='relationProductPrice']").val();
            if (productRelationPrice) {
                count = count + 1;
            }
            $(this).parent().parent().find("input[name='productRelationPrice']").attr("name", "list1[" + i + "]." + "relationSkuPrice")
                    .attr("value", productRelationPrice);
            $(this).parent().parent().find("input[name='productRelationType']").attr("name", "list1[" + i + "]." + "relationDetailType")
                    .attr("value", 0);
            $(this).parent().parent().find("input[name='relationID']").attr("name", "list1[" + i + "]." + "relationId")
                    .attr("value", relationId);
        });

        if (count == 0) {
            alert("请为关联的产品输入价格");
            return false;
        }
        document.frm.action = "/productRelation/saveOtherProductRelation.action";
        document.frm.submit();
    }

    $(document).ready(function () {
        $("input[name='checkSkuId']").click(function () {
            if ($(this).attr("checked") == true) {
                $(this).parent().parent().find("input[name='relationProductPrice']").replaceWith("<input type='text'  name='relationProductPrice'   tip='范围[1-1000000]'  reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$'     />");
                //	重新加载js,验证出现错误提示用的
                var oHead = document.getElementsByTagName('HEAD').item(0);
                var oScript = document.createElement("script");
                oScript.type = "text/javascript";
                oScript.src = "/etc/js/validate/easy_validator.pack.js";
                oHead.appendChild(oScript);
            } else {
                $(this).parent().parent().find("input[name='relationProductPrice']")
                        .replaceWith("<input type='text'  name='relationProductPrice'/>");
                //	重新加载js,验证出现错误提示用的
                var oHead = document.getElementsByTagName('HEAD').item(0);
                var oScript = document.createElement("script");
                oScript.type = "text/javascript";
                oScript.src = "/etc/js/validate/easy_validator.pack.js";
                oHead.appendChild(oScript);
            }
        });
    });
</script>
</body>
</html>