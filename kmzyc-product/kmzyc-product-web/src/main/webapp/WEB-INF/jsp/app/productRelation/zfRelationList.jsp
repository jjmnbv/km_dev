<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>组方列表</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-1.8.3.js"></script>
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
<s:set name="name" value="'组方列表查询'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>

<s:form action="/productRelation/queryZFProductRelation.action" method="post"
        namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" id="rtnMsg"
           value="<s:property value="rtnMessage"/>"/>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="50"
           cellpadding="0" cellspacing="0">
        <tr>
            <td>关联名称： <s:textfield name="selectProductRelation.relationName"/></td>
            <td>主产品SKU：&nbsp;&nbsp;<s:textfield type="text" name="selectProductRelation.productSkuCode"
                                                cssClass="input_style"/></td>
            <td>主产品编号：&nbsp;&nbsp;<s:textfield type="text" name="selectProductRelation.productNo"
                                               cssClass="input_style"/></td>
            <td>状态： &nbsp;&nbsp;<s:select list="#request.zfProductRelationValidStatus"
                                          name="selectProductRelation.status" headerKey=""
                                          headerValue="--全部状态--"></s:select>
            </td>
            <td>
                <input value="<s:property value="productName"  />" name="productName" type="hidden"></input>
                <input type="button" class="batchDelBtn" onclick="delPackage()"/>
                <input type="submit" class="queryBtn" value=""/>
            </td>
        </tr>
    </table>
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type="checkbox" onclick="checkAll(this)"/></th>
            <th align="center" width="5%">组方Id</th>
            <th align="center" width="5%">主产品skuId</th>
            <th align="center" width="5%">主产品编号</th>
            <th align="center" width="10%">主产品sku编号</th>
            <th align="center" width="10%">主产品名称</th>
            <th align="center" width="9%">关联名称</th>
            <th align="center" width="10%">操作</th>
            <th align="center" width="7%">创建时间</th>
            <th align="center" width="5%">查看组方</th>
            <%-- <th align="center" width="10%">备注</th> --%>
            <%--  <th align="center" width="7%">编辑状态</th>--%>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
                <td align="center">
                    <input type="checkbox" name="productRelationId" value="<s:property value='relationId'/>"
                           data-type="<s:property value='status'/>"/>
                    <input type="hidden" name="mainSkuId" value="<s:property value='mainSkuId'/>"/>
                </td>
                <td align="center" width="5%"><s:property value="relationId"/></td>
                <td align="center" width="5%"><s:property value="mainSkuId"/></td>
                <td align="center" width="5%"><s:property value="productNo"/></td>
                <td align="center" width="5%"><s:property value="productSkuCode"/></td>
                <td align="center" width="5%"><s:property value="productName"/></td>
                <td align="center" width="5%"><s:property value="relationName"/></td>
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
                <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td>
                    <img onClick="gotoView(<s:property value='relationId'/>);" src="/etc/images/button_new/select.png"
                         style="cursor: pointer;" title="查看">
                    <s:if test="status==null">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property value='mainSkuId'/>,1);" src="/etc/images/button_new/edit.png"
                             style="cursor: pointer;" title="修改">
                    </s:if>
                    <s:if test="status==0">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property
                                value='mainSkuId'/>,1);" src="/etc/images/button_new/edit.png" style="cursor: pointer;"
                             title="修改">
                    </s:if>
                    <s:if test="status==1">
                        <img onClick="editZFRelationDeatil(<s:property value='relationId'/>,<s:property
                                value='mainSkuId'/>,0);" src="/etc/images/button_new/edit.png" style="cursor: pointer;"
                             title="修改">
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
            <td align="center"></td>
            <td width="20%" align="center"></td>
        </tr>
    </table>

</s:form>


<s:if test='!rtnMessage.isEmpty()'>
    <script LANGUAGE="JavaScript">
        var msg = document.getElementById("rtnMsg").value;
        if (msg == "addsuccess") {
            alert("添加成功！");
        } else if (msg == "successUpdate") {
            alert("修改成功！");
        } else {
            alert(msg);
        }
    </script>
</s:if>


<script type="text/javascript">
    //查看详情
    function gotoView(relationId) {
        var pageNum = $("#pageNo option:selected").val();
        document.forms[0].action = "/productRelation/viewZFProductDeatil.action?productRelationDetail.relationId=" + relationId;
        document.forms[0].submit();
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
                    url: "/productRelation/updateStatus.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId
                    + "&status=" + status,
                    dataType: "json",
                    error: function (data) {
                        alert(data.msg);
                    },
                    success: function (data) {
                        if (data.msg == "更改状态成功") {
                            document.forms[0].action = "/productRelation/updateZFProductDeatil.action?productRelationDetail.relationId=" + relationId;
                            document.forms[0].submit();
                        }
                    }
                };
                $.ajax(settings);
            }
        } else {
            document.forms[0].action = "/productRelation/updateZFProductDeatil.action?productRelationDetail.relationId=" + relationId;
            document.forms[0].submit();
        }
    }

    // 删除组合
    function delPackage() {
        var relationType = document.getElementsByName("relationType");
        var str = document.getElementsByName("productRelationId");
        var objarray = str.length;
        var chestr = "";
        var status = "";

        for (var i = 0; i < objarray; i++) {// 遍历数组
            if (str[i].checked == true) {// 如果选中，开始获得所选择的出库单ID
                chestr += str[i].value + ",";
                status = str[i].attributes['data-type'].nodeValue;
                if (status == 3 || status == 4) {
                    alert("只有新增状态的组方可以删除！");
                    return false;
                }
            }
        }
        if (chestr.length < 1) {
            alert("请选择要删除的组方");
            return false;
        }

        var answer = confirm("确认删除吗?");
        if (!answer) {
            return false;
        }
        // 发送ajax请求到action
        var settings = {
            type: "POST",
            url: "/productRelation/delRelationInfo.action?delPackageStr=" + chestr,
            dataType: "json",
            error: function (data) {
                alert(data.msg);
            },
            success: function (data) {
                alert(data.msg);
                // 再查询一次审核列表
                $("#frm").submit();
                //location.href = "queryPackageDetail.action";
            }
        };
        $.ajax(settings);
    }

    $(document).ready(function () {
        var pageNum = $("#pageNo option:selected").val();
        $("input[type='radio'][name='validButton']").click(function () {
            if (confirm("确认修改？")) {
                var status = $(this).val();
                var relai1tonId = $(this).parent().parent().find("input[name='productRelationId']").val();
                var mainSkuId = $(this).parent().parent().find("input[name='mainSkuId']").val();
                var settings = {
                    type: "POST",
                    url: "/productRelation/updateStatus.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId + "&status=" + status,
                    dataType: "json",
                    error: function (data) {
                        alert(data.msg);
                    },
                    success: function (data) {
                        alert(data.msg);
                        // 再查询一次审核列表
                        $("#frm").submit();
                    }
                };
                $.ajax(settings);
            } else {
                $('input[name=validButton]:checked').attr('checked', false);
            }
        });
            });
</script>
</body>
</html>