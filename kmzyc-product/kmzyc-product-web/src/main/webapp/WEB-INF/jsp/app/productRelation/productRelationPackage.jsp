<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品关联列表</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script src="/etc/js/warehouse/distributionInfo.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <script src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
    <script src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
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
<s:set name="name" value="'产品关联'" scope="request"/>
<s:set name="son_name" value="'产品关联列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:form action="/productRelation/queryPackage.action" method="post"
        namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" name="mainSkuId"
           value="<s:property value='productRelation.mainSkuId'/>"/>
    <input type="hidden" id="rtnMsg"
           value="<s:property value="rtnMessage"/>"/>
    <input type="hidden" name="productRelation.mainSkuId"
           value="<s:property value='productRelation.mainSkuId'/>"/>
    <br/>
    <table width="90%" class="table_search" align="center" cellpadding="0"
           cellspacing="0" style="border-collapse: collapse; font-size: 12px">
        <tr>
            <td></td>
            <td><input type="button" style="height: 30px;" value="删除 " onClick="delPackage()"/> &nbsp;
                <input type="button" style="height: 30px;" value="新增 "
                       onclick="addProductRelation(<s:property value='productRelation.mainSkuId'/>);"/>&nbsp;
                <%--<input type="button" style="height: 30px;" value="新增组方 "
                       onclick="addProductZFRelation(<s:property value='productRelation.mainSkuId'/>);"/>--%>
            </td>
        </tr>
    </table>
    <br/>

    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type="checkbox" onclick="checkAll(this)"/>
            </th>
            <th align="center" width="10%">关联名称</th>
            <th align="center" width="9%">主产品价格</th>
            <th align="center" width="7%">关联类型</th>
            <th align="center" width="10%">备注</th>
            <th align="center" width="5%">状态</th>
            <th align="center" width="10%">操作</th>
            <th align="center" width="7%">创建时间</th>
            <th align="center" width="10%">查看</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
                <td align="center">
                    <input type="checkbox" name="productRelationId" value="<s:property value='relationId'/>"/>
                </td>
                <td align="center" width="5%"><s:property value="relationName"/></td>
                <td align="center" style="word-break: break-all"><s:property value="mainSkuPrice"/></td>
                <td align="center" style="word-break: break-all"><s:iterator value="#request.productRelationType" id="ware" status="index">
                    <s:if test="#ware.key==relationType">
                        <s:property value="#ware.value"/>
                    </s:if>
                </s:iterator></td>
                <td align="center" style="word-break: break-all"><s:property value="remark"/></td>
                <td>
                    <s:iterator value="#request.productRelationValidStatus">
                        <s:if test="key==status">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <input type="hidden" name="prodRelationId" value="<s:property value='relationId'/>"/>
                <td>
                    <s:if test="status==null">
                        有效<input type="radio" name="validButton" value="1" />
                    </s:if>
                    <s:if test="status==0">
                        有效<input type="radio" name="validButton" value="1" />
                    </s:if>
                    <s:if test="status==1">
                        无效<input type="radio" name="validButton" value="0" />
                    </s:if>
                </td>
                <td><s:date name="createDate" format="yyyy-MM-dd"/></td>
                <td>
                    <s:if test="status==null">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property value='mainSkuId'/>,1);"
                             src="/etc/images/button_new/edit.png" style="cursor: pointer;" title="修改">
                    </s:if>
                    <s:if test="status==0">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property value='mainSkuId'/>,1);"
                             src="/etc/images/button_new/edit.png" style="cursor: pointer;" title="修改">
                    </s:if>
                    <s:if test="status==1">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property value='mainSkuId'/>,0);"
                             src="/etc/images/button_new/edit.png" style="cursor: pointer;" title="修改">
                    </s:if>
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

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="bottombuttonbar" height="30"
           border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center">
                <input type="button" class="backBtn" onClick="goToBack();"/></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
</s:form>
<s:form name="listForm" method="post" action="/productRelation/skuProductQuery.action" id="listForm">
    <s:hidden type="hidden" name="checkedId"/>
    <s:hidden name="productTied.productNo"/>
    <s:hidden name="productTied.procuctName"/>
    <s:hidden name="productTied.status"/>
    <s:hidden name="productTied.bCategoryId"/>
    <s:hidden name="productTied.mCategoryId"/>
    <s:hidden name="productTied.categoryId"/>
    <s:hidden name="productTied.keyword"/>
    <s:hidden name="productTied.brandId"/>
    <s:hidden name="productTied.productSkuCode"/>
    <s:hidden name="page.pageNo"/>
</s:form>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        if (msg == "addsuccess") {
            alert("添加关联成功");
        } else
            alert(msg);
        if (msg) {
            parent.closeOpenSku();
        }
    </script>
</s:if>

<script type="text/javascript">
    function viewProductRelationDeatil(relationId, editable, productName) {
        var pageNum = $("#pageNo option:selected").val();
        var mainSkuId = $("input[name='mainSkuId']").val();
        location.href = "/productRelation/viewProductDeatil.action?productRelationDetail.relationId=" + relationId +
                "&mainSkuId=" + mainSkuId + "&pageNum=" + pageNum +
                "&editable=" + editable + "&productName=" + productName;
    }

    //修改
    function editZFRelationDeatil(relationId, mainSkuId, isValid) {
        var status = '0';
        if (isValid == 0) {
            if (confirm("确认修改？")) {
                var status = isValid;
                var relai1tonId = relationId;
                var settings = {
                    type: "POST",
                    url: "/productRelation/updateStatus.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId + "&status=" + status,
                    dataType: "json",
                    error: function (data) {
                        alert(data.msg);
                    },
                    success: function (data) {
                        if (data.msg == "更改状态成功") {
                            location.href = "/productRelation/viewProductDeatil.action?productRelationDetail.relationId="
                                    + relationId + "&mainSkuId=" + mainSkuId;
                        }
                    }
                };
                $.ajax(settings);
            }
        } else {
            location.href = "/productRelation/viewProductDeatil.action?productRelationDetail.relationId=" + relationId
                    + "&mainSkuId=" + mainSkuId;
        }
    }

    // 删除套餐
    function delPackage() {
        var str = document.getElementsByName("productRelationId");
        var mainSkuId = $("input[name='mainSkuId']").val();
        var objarray = str.length;
        var chestr = "";

        for (i = 0; i < objarray; i++) {// 遍历数组
            if (str[i].checked == true) {// 如果选中，开始获得所选择的出库单ID
                chestr += str[i].value + ",";
            }
        }
        if (chestr.length < 1) {
            alert("请选择要删除的套餐");
            return false;
        }

        var answer = confirm("确认删除吗?");
        if (!answer) {
            return false;
        }
        // 发送ajax请求到action
        var settings = {
            type: "POST",
            url: "/productRelation/delPackage.action?delPackageStr=" + chestr + "&mainSkuId=" + mainSkuId,
            dataType: "json",
            error: function (data) {
                alert(data.msg);
            },
            success: function (data) {
                alert(data.msg);
                // 再查询一次审核列表
                location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId;
            }
        };
        $.ajax(settings);
    }

    function addProductRelation(trId) {
        location.href = '/productRelation/relationQueryProduct.action?productTied.productSkuId=' + trId;

    }
    function addProductZFRelation(trId, productName) {
        location.href = '/productRelation/relationQueryZFProduct.action?productTied.productSkuId=' + trId;
    }

    function viewRelationDeatil(relationId) {
        var mainSkuId = $("input[name='mainSkuId']").val();
        myDialog = art.dialog.open('/productRelation/viewProductDeatil.action?productRelationDetail.relationId=' + relationId + '&mainSkuId=' + mainSkuId, {
            title: '查询关联产品详情',
            width: 800,
            height: 500,
            drag: false,
            lock: true
        });
    }

    $(document).ready(function () {
        var pageNum = $("#pageNo option:selected").val();
        var mainSkuId = $("input[name='mainSkuId']").val();
        $("input[type='radio'][name='validButton']").click(function () {
            if (confirm("确认修改？")) {
                var status = $(this).val();
                var relai1tonId = $(this).parent().parent().find("input[name='prodRelationId']").val();
                var settings = {
                    type: "POST",
                    url: "/productRelation/updateStatus.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId
                    + "&status=" + status,
                    dataType: "json",
                    error: function (data) {
                        alert(data.msg);
                    },
                    success: function (data) {
                        alert(data.msg);
                        // 再查询一次审核列表
                        location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId
                                + "&pageNum=" + pageNum;
                    }
                };
                $.ajax(settings);
            } else {
                $('input[name=validButton]:checked').attr('checked', false);
            }
        });

        $("input[type='radio'][name='editableButton']").click(function () {
            if (confirm("状态修改后将不能再对此条信息进行任何修改，确认将状态修改为不可编辑？")) {
                var status = $(this).val();
                var relai1tonId = $(this).parent().parent().find("input[name='prodRelationId']").val();
                var settings = {
                    type: "POST",
                    url: "/productRelation/updateEditable.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId,
                    dataType: "json",
                    error: function (data) {
                        alert(data.msg);
                    },
                    success: function (data) {
                        alert(data.msg);
                        // 再查询一次审核列表
                        location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId + "&pageNum=" + pageNum;
                    }
                };
                $.ajax(settings);
            } else {
                $('input[name=editableButton]:checked').attr('checked', false);
            }
        });
    });

    function goToBack() {
        $('#listForm').submit();
    }
</script>
</body>
</html>