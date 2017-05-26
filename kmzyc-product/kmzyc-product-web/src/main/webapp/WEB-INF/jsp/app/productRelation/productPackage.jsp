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
<s:set name="name" value="'套餐管理'" scope="request"/>
<s:set name="son_name" value="'套餐列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp" %>
<s:form action="/productRelation/queryPackageDetail.action" method="post" namespace='/productRelation' id="frm" name='frm'>
    <s:hidden name="checkedId" id="checkedId"/>
    <%--<input type="hidden" name="mainSkuId" value="<s:property value='productRelation.mainSkuId'/>" />--%>
	<%--<input type="hidden" name="channel" id="channel" value="<s:property value='channel'/>" />--%>
    <%--<input type="hidden" name="productRelation.mainSkuId" value="<s:property value='productRelation.mainSkuId'/>" />--%>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
<!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" height="50" cellpadding="0" cellspacing="0">
        <tr>
            <td>套餐名称： <s:textfield name="selectProductRelation.relationName"/></td>
            <td>套餐产品SKU：&nbsp;&nbsp;
                <s:textfield type="text" name="selectProductRelation.productSkuCode" cssClass="input_style"/>
            </td>
            <td>所属商家：&nbsp;&nbsp;
                <s:textfield type="text" name="selectProductRelation.supplierName" cssClass="input_style"/>
            </td>
            <td>状态： &nbsp;&nbsp;
                <s:select list="#request.productRelationValidStatus" name="selectProductRelation.status"
                          headerKey="" headerValue="--全部状态--"></s:select>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right">
                <input type="submit" class="queryBtn" value=""/>&nbsp;
                <input type="button" class="batchDelBtn" onclick="delPackage()"/>&nbsp;
                <input type="button" class="addBtn" onclick="addProductRelation()">&nbsp;
                <input class="upShelfBtn" type="button" onclick="upOrDownShelf(0);">&nbsp;
                <input type="button" class="downShelfBtn" onclick="upOrDownShelf(1);">
            </td>
        </tr>
        <br/>
<!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type="checkbox" onclick="checkAll(this)"/>
            </th>
            <th align="center" width="5%">套餐ID</th>
            <th align="center" width="10%">套餐名称</th>
            <th align="center" width="10%">商品数量</th>
            <th align="center" width="9%">所属商家</th>
            <%--<th align="center" width="9%">所属站点</th>--%>
            <th align="center" width="10%">套餐价格</th>
            <%-- <th align="center" width="10%">备注</th> --%>
            <th align="center" width="5%">状态</th>
            <th align="center" width="7%">创建时间</th>
            <%--  <th align="center" width="7%">编辑状态</th>--%>
            <th align="center" width="10%">操作</th>
        </tr>
        <s:iterator id="productiterator" value="page.dataList" status="index">
            <tr>
                <td align="center">
                    <input type="checkbox" name="productRelationId" value="<s:property value='relationId'/>"
                           data-type="<s:property value='status'/>"/>
                <%--<input type="hidden" name="relationType" value="<s:property value='relationType'/>" />--%>
                </td>
                <td align="center" width="5%"><s:property value="relationId"/></td>
                <td align="center" width="5%"><s:property value="relationName"/></td>
                <td align="center" width="5%"><s:property value="allProCount"/></td>
                <td align="center" width="5%"><s:property value="supplierName"/></td>
                <%--<td align="center" width="5%"><s:property value="webSite"/></td>--%>
                <td align="center" style="word-break: break-all"><s:property value="totalRelationPrice"/></td>
                <td>
                    <s:iterator value="#request.productRelationValidStatus">
                        <s:if test="key==status">
                            <s:property value="value"/>
                        </s:if>
                    </s:iterator>
                </td>
                <%--<td align="center" style="word-break: break-all"><s:property value="remark" /></td>--%>
                <%--<input type="hidden" name="prodRelationId" value="<s:property value='relationId'/>" />--%>
                <td><s:date name="createDate" format="yyyy-MM-dd"/></td>
                <%--<td>
                    <s:if test="editable==0">可编辑
                        <input type="radio" name="editableButton" value="1" id ="editableButton"/>
				    </s:if><s:elseif test="editable!=0">
		                不可编辑
		            </s:elseif>
                </td>--%>
                <%-- 下架：可编辑 --%>
                <td><s:if test="status == 4">
                    <img src="/etc/images/button_new/edit.png"
                         onclick="viewProductRelationDeatil(<s:property value='relationId'/>,1)"
                         style="word-break: break-all" title="编辑"/></s:if>
                    <%-- 新增：可编辑，可删除 --%>
                    <s:if test="status == 2">
                        <img src="/etc/images/button_new/edit.png"
                             onclick="viewProductRelationDeatil(<s:property value='relationId'/>,2)"
                             style="word-break: break-all" title="编辑"/>
                        <img onClick="delRelation(<s:property value='relationId'/>);"
                             src="/etc/images/little_icon/delete.png" style="cursor: pointer;" title="删除">
                    </s:if>
                    <s:else>
                        <%-- 上架：只可查看 --%>
                        <img onClick="gotoView(<s:property value='relationId'/>);" src="/etc/images/view.png"
                             style="cursor: pointer;" title="查看">
                    </s:else>
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
                <%--<input type="button" class="backBtn" onClick="goToBack();" /> --%>
            </td>
            <td width="20%" align="center"></td>
        </tr>
    </table>
</s:form>
<s:form name="listForm" method="post" action="/productRelation/packageManage.action" id="listForm">
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
            alert("添加成功！");
        }
        else if (msg == "successUpdate") {
            alert("修改成功！");
        } else {
            alert(msg);
        }
    </script>
</s:if>
    <script type="text/javascript">
        function viewProductRelationDeatil(relationId, type) {
            if ("1" == type) {
                var pageNum = $("#pageNo option:selected").val();
                //var  mainSkuId=$("input[name='mainSkuId']").val();
                document.forms[0].action = "/productRelation/viewRelationDeatil.action?productRelationDetail.relationId=" + relationId + "&pageNum=" + pageNum;
                document.forms[0].submit();
            } else {
                document.forms[0].action = "/productRelation/viewRelationDeatilAddstr.action?productRelationDetail.relationId=" + relationId;
                document.forms[0].submit();
            }
            //location.href="/productRelation/viewProductPackageDeatil.action?productRelationDetail.relationId="+relationId+"&pageNum="+pageNum;
            /*
             if(editable==0){
             location.href="/productRelation/viewProductDeatil.action?productRelationDetail.relationId="+relationId+"&mainSkuId="+mainSkuId+"&pageNum="+pageNum+"&editable="+editable;
             }
             else{
             location.href="/productRelation/viewDetailUnderOnNotEdible.action?productRelationDetail.relationId="+relationId+"&mainSkuId="+mainSkuId+"&pageNum="+pageNum+"&editable="+editable;
             }
             */
        }
        function gotoView(relationId) {
            var pageNum = $("#pageNo option:selected").val();
            document.forms[0].action = "/productRelation/viewRelationDeatil.action?productRelationDetail.relationId=" + relationId + "&pageNum=" + pageNum + "&viewType=view";
            document.forms[0].submit();
            //location.href="/productRelation/viewProductPackageDeatil.action?productRelationDetail.relationId="+relationId+"&pageNum="+pageNum+"&viewType=view";
        }
        //上下架套餐
        function upOrDownShelf(type) {
            var str = document.getElementsByName("productRelationId");
            var mainSkuId = $("input[name='mainSkuId']").val();
            var status = "";
            var objarray = str.length;
            var chestr = "";
            var operateType = "";
            if (type == 0)
                operateType = "up";
            else
                operateType = "down";
            for (i = 0; i < objarray; i++) {// 遍历数组
                if (str[i].checked == true) {// 如果选中，开始获得所选择的出库单ID
                    chestr += str[i].value + ",";
                    status = str[i].attributes['data-type'].nodeValue;
                    if (type == 0) {// 如果为上架，状态必须为下架或新增状态
                        if (status == 3) {
                            alert("确认上架产品的状态是否正确");
                            return false;
                        }
                    } else if (status == 2 || status == 4) {//下架必须为上架商品
                        alert("确认下架产品的状态是否正确");
                        return false;
                    }
                }
            }

            if (chestr.length < 1) {
                if (type == 0)
                    alert("请选择要上架的套餐");
                else
                    alert("请选择要下架的套餐");
                return false;
            }

            if (type == 0) {
                var answer = confirm("确认上架吗?");
                if (!answer) {
                    return false;
                }
            } else {
                var answer = confirm("确认下架吗?");
                if (!answer) {
                    return false;
                }
            }

            // 发送ajax请求到action
            var settings = {
                type: "POST",
                url: "/productRelation/upOrDownShelf.action?operateType=" + operateType + "&delPackageStr=" + chestr,
                dataType: "json",
                error: function (data) {
                    alert(data.msg);
                },
                success: function (data) {
                    alert(data.msg);
                    $("#frm").submit();
                    // 再查询一次审核列表
                    //location.href = "/productRelation/queryPackageDetail.action?productRelation.mainSkuId="+mainSkuId;
                }
            };
            $.ajax(settings);
        }

        // 删除套餐
        function delPackage() {
            var relationType = document.getElementsByName("relationType");
            var str = document.getElementsByName("productRelationId");
            //var  mainSkuId=$("input[name='mainSkuId']").val();
            var objarray = str.length;
            var chestr = "";
            var status = "";

            for (i = 0; i < objarray; i++) {// 遍历数组
                if (str[i].checked == true) {// 如果选中，开始获得所选择的出库单ID
                    chestr += str[i].value + ",";
                    status = str[i].attributes['data-type'].nodeValue;
                    if (status == 3 || status == 4) {
                        alert("只有新增状态的套餐可以删除！");
                        return false;
                    }
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

        function delRelation(id) {
            var answer = confirm("确认删除吗?");
            if (!answer) {
                return false;
            }
            var chestr = id + ",";
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

        function addProductRelation() {
            location.href = '/productRelation/addRelation.action?productTied.productSkuId=';
        }

        function viewRelationDeatil(relationId) {
            var mainSkuId = $("input[name='mainSkuId']").val();
            myDialog = art.dialog.open('/productRelation/viewProductDeatil.action?productRelationDetail.relationId='
                    + relationId + '&mainSkuId=' + mainSkuId, {
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
                if (confirm("状态修改后将不能再次改变，确认将状态修改为有效？")) {
                    var status = $(this).val();
                    var relai1tonId = $(this).parent().parent().find("input[name='prodRelationId']").val();
                    var settings = {
                        type: "POST",
                        url: "/productRelation/updateStatus.action?relationId=" + relai1tonId + "&mainSkuId=" + mainSkuId,
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
                            location.href = "/productRelation/queryPackage.action?productRelation.mainSkuId=" + mainSkuId
                                    + "&pageNum=" + pageNum;
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