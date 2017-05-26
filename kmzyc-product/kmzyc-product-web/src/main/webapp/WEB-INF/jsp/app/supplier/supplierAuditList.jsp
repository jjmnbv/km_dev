<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>供应商审核列表</title>
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
<s:set name="parent_name" value="'供应商审核管理'" scope="request"/>
<s:set name="name" value="'供应商审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="supplierAuditList.action" method="POST" id="frm" name='frm'>
    <input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td>名称：</td>
            <td><s:textfield name="selectSuppliersInfo.corporateName" cssClass="input_style"/></td>
            <td>申请人：</td>
            <td><s:textfield name="selectSuppliersInfo.contactsName" cssClass="input_style"/></td>
            <td>申请状态：</td>
            <td>
                <s:select list="#request.suppliersStatusMap" name="selectSuppliersInfo.status"
                          id="channel" headerKey="" style="width:111px" headerValue="--请选择--"></s:select>
            </td>
        </tr>
        <tr>
            <td>企业状态：</td>
            <td>
                <s:select list="#{0:'关闭',1:'开启'}" name="selectSuppliersInfo.enterpriseStatus"
                          headerKey="" style="width:111px" headerValue="--请选择--"></s:select></td>
            <td>商户类型：</td>
            <td>
                <s:select list="#{1:'自营',2:'入驻',3:'代销'}" name="selectSuppliersInfo.supplierType"
                          headerKey="" style="width:111px" headerValue="--请选择--"></s:select>
            </td>
            <td></td>
            <td>
                <input type="submit" class="queryBtn" value=""/>&nbsp;
                <input type="button" onClick="exportSupplierInfo()" class="btn-custom" value="导出"/>&nbsp;
                <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();"/>
                <%--  <input class="delBtn" type="button" value="" onclick="gotoDel('merchantId');" />--%>
            </td>
        </tr>
        <tr>
        </tr>
    </table>


    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="4%">
                <input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/>
            </th>
            <th align="center" width="10%">公司名称</th>
            <th align="center" width="10%">申请人</th>
            <th align="center" width="10%">商户类型</th>
            <th align="center" width="10%">联系电话</th>
            <th align="center" width="15%">公司地址</th>
            <th align="center" width="10%">申请时间</th>
            <th align="center" width="10%">申请状态</th>
            <th align="center" width="10%">企业状态</th>
            <th align="center" width="15%">操作</th>
        </tr>
        <s:iterator id="supplieriterator" value="page.dataList">
            <tr>
                <td align="center" width="5%">
                    <input type="checkbox" <s:if test="status == 3"> disabled="disabled" </s:if>
                           id="check" name="merchantId" value='<s:property value="merchantId"/>'/>
                </td>
                <td align="center"><s:property value="corporateName"/>
                </td>
                <td align="center"><s:property value="contactsName"/>
                </td>
                <td align="center">
                    <s:if test="supplierType == 1">自营</s:if>
                    <s:elseif test="supplierType == 2">入驻</s:elseif>
                    <s:elseif test="supplierType == 3">代销</s:elseif>
                    <s:elseif test="supplierType == 4">康美中药城</s:elseif>
                </td>
                <td align="center"><s:property value="mobile"/>
                </td>
                <td align="center">
                    <s:property value="province"/><s:property value="city"/><s:property value="area"/><s:property value="corporateLocation"/>
                </td>
                <td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td align="center">
                    <s:if test="status == 2">提交申请</s:if>
                    <s:elseif test="status == 3">审核通过</s:elseif>
                    <s:elseif test="status == 4">审核未通过</s:elseif>
                    <s:elseif test="status == 5">待商家确认</s:elseif>
                </td>
                <td align="center">
                    <s:if test="enterpriseStatus == 0">关闭</s:if>
                    <s:elseif test="enterpriseStatus == 1">开启</s:elseif>
                </td>
                <td align="center">
                    <s:if test="status == 3 || status == 5">
                        <img title="修改" style="cursor: pointer;"
                             src="/etc/images/button_new/modify.png"
                             onclick="gotoUpdate(<s:property value='merchantId'/>)"/>
                    </s:if>
                    <s:if test="status == 4">
                        <img title="修改" style="cursor: pointer;"
                             src="/etc/images/button_new/modify.png"
                             onclick="gotoUpdateNopass(<s:property value='merchantId'/>)"/>
                    </s:if>
                    <s:if test="status == 2">
                        <img title="审核" style="cursor: pointer;"
                             src="/etc/images/button_new/view.png"
                             onclick="gotoView(<s:property value='merchantId'/>)"/>
                    </s:if>
                    <s:if test="status == 3">
                        <s:if test="loginStatus == 2">
                            <img onClick="closeMerchanStatus(<s:property value='supplierId'/>)"
                                 src="/etc/images/button_new/ban01.png" title="禁止登录" style="cursor: pointer;"
                                 type="button">
                        </s:if>
                        <s:if test="loginStatus == 1">
                            <img onClick="openMerchanStatus(<s:property value='supplierId'/>)"
                                 src="/etc/images/button_new/ban01on.png" title="恢复登录" style="cursor: pointer;"
                                 type="button">
                        </s:if>
                        <s:if test="businessStatus == 1">
                            <img onClick="openBusinessStatus(<s:property value='supplierId'/>)"
                                 src="/etc/images/button_new/ban02on.png" title="恢复业务" style="cursor: pointer;"
                                 type="button">
                        </s:if>
                        <s:if test="businessStatus == 2">
                            <img onClick="openBusinessStatus(<s:property value='supplierId'/>,'1')"
                                 src="/etc/images/button_new/ban02.png" title="禁止业务" style="cursor: pointer;"
                                 type="button">
                        </s:if>
                        <s:if test="closeStatus == 1">
                            <img onClick="openCloseStatus(<s:property value='supplierId'/>,'1')"
                                 src="/etc/images/button_new/upshelf.png" title="开启" style="cursor: pointer;"
                                 type="button">
                        </s:if>
                        <s:if test="closeStatus == 2">
                            <img onClick="openCloseStatus(<s:property value='supplierId'/>)"
                                 src="/etc/images/button_new/notpass.png" title="关闭" style="cursor: pointer;"
                                 type="button">
                        </s:if>
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
    <input type="hidden" name="selectSuppliersInfo.status" value="<s:property value='selectSuppliersInfo.status' />"/>
    <input type="hidden" name="selectSuppliersInfo.enterpriseStatus"
           value="<s:property value='selectSuppliersInfo.enterpriseStatus' />"/>
</form>
<s:if test='!rtnMessage.isEmpty()'>
    <script language="JavaScript">
        alert(document.getElementById("rtnMsg").value);
    </script>
</s:if>
<script language="JavaScript">
    //返回我的桌面界面
    function gotoList() {
        location.href = "/basedata/gotoSysMain.action";
    }

    function gotoAdd() {
        location.href = "/app/addSupplierShow.action";
    }
    function gotoUpdateNopass(id) {
        var pageNum = $("#pageNo option:selected").val();
        document.forms[0].action = "gotoSupplierUpdateNopass.action?merchantOrSupplier.merchantId=" + id + "&pageNum=" + pageNum + "&noPass=" + 4;
        document.forms[0].submit();
    }

    function gotoUpdate(id) {
        var pageNum = $("#pageNo option:selected").val();
        document.forms[0].action = "gotoSupplierUpdate.action?merchantOrSupplier.merchantId=" + id + "&pageNum=" + pageNum;
        document.forms[0].submit();
    }

    function gotoView(id) {
        var pageNum = $("#pageNo option:selected").val();
        document.forms[0].action = "gotoMerchantSupplierView.action?merchantOrSupplier.merchantId=" + id + "&pageNum=" + pageNum;
        document.forms[0].submit();
    }

    function gotoDel(id) {
        alert("删除操作已经屏蔽。");
        return;
        var chestr = "";
        var obj = document.getElementsByName(id);
        var count = 0;
        // 遍历所有用户，找出被选中的用户
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                count++;
                chestr += obj[i].value + ",";
            }
        }
        if (count == 0) {
            alert("请选择要删除的数据。");
            return false;
        }
        var answer = confirm("确认删除吗?");
        if (!answer) {
            return false;
        }
        $.ajax({
            url: 'delSuppliers.action',
            async: false,
            data: 'delId=' + chestr,
            success: function (info) {
                if ('0' == info) {
                    alert("系统异常，删除失败!");
                    return;
                } else {
                    alert("删除成功!");
                    location.href = "supplierAuditList.action";
                }
            }
        });
    }
    function closeMerchanStatus(merchanId) {
        var answer = confirm("确认禁止吗?禁止后此供应商将不能登录");
        if (!answer) {
            return false;
        }
        $.ajax({
            url: '/app/updateMerchanStatus.action',
            async: false,
            data: 'supplierId=' + merchanId,
            success: function (info) {
                if ('1' == info) {
                    alert("关闭成功!");
                    document.forms[1].action = "supplierAuditList.action";
                    document.forms[1].submit();
                } else {
                    alert("系统异常，操作失败!");
                    return;
                }
            }
        });
    }
    function openMerchanStatus(merchanId) {
        var answer = confirm("确认开启吗?");
        if (!answer) {
            return false;
        }
        $.ajax({
            url: '/app/updateMerchanStatus.action',
            async: false,
            data: 'supplierId=' + merchanId + "&updateMerchanType=" + 1,
            success: function (info) {
                if ('1' == info) {
                    alert("开启成功!");
                    document.forms[1].action = "supplierAuditList.action";
                    document.forms[1].submit();
                } else {
                    alert("系统异常，操作失败!");
                    return;
                }
            }
        });
    }
    function openBusinessStatus(merchanId, val) {
        if (val == '1') {
            var answer = confirm("禁止后：\n 1,此供应商相关产品将下架 \n 2,供应商店铺页面将不可访问  \n 3,无法进行上架操作 \n 是否继续？\n");
            if (answer) {
                $.ajax({
                    url: '/app/updateBusinessStatus.action',
                    async: false,
                    data: 'supplierId=' + merchanId,
                    success: function (info) {
                        if (info == 1) {
                            alert("操作成功!");
                            document.forms[1].action = "supplierAuditList.action";
                            document.forms[1].submit();
                        } else {
                            alert("系统异常，操作失败!");
                            return;
                        }
                    }

                });

            }
        } else {
            var answer = confirm("确认恢复业务？ \n 恢复业务后，原已下架商品不会自动上架，需要手动操作重新上架!");
            if (answer) {
                $.ajax({
                    url: "/app/updateBusinessStatus.action",
                    async: false,
                    data: 'supplierId=' + merchanId + "&updateMerchanType=" + 1,
                    success: function (info) {
                        if (info == 1) {
                            alert("操作成功!");
                            document.forms[1].action = "supplierAuditList.action";
                            document.forms[1].submit();
                        } else {
                            alert("系统异常，操作失败!");
                            return;
                        }
                    }

                });
            }
        }
    }

    //开启，关闭
    function openCloseStatus(merchanId, val) {
        if (val != '1') {
            var answer = confirm("关闭后：\n 1,此供应商相关产品将下架 \n 2,供应商店铺页面将不可访问  \n 3,后台新增商品，选择商家时不可选择被关闭的商家 \n 4,不能登录供应商平台  \n是否继续？\n");
            if (answer) {
                $.ajax({
                    url: '/app/updateCloseStatus.action',
                    async: false,
                    data: 'supplierId=' + merchanId,
                    success: function (info) {
                        if (info == 1) {
                            alert("操作成功!");
                            document.forms[1].action = "supplierAuditList.action";
                            document.forms[1].submit();
                        } else {
                            alert("系统异常，操作失败!");
                            return;
                        }
                    }
                });
            }
        } else {
            var answer = confirm("确认开启？ \n 重新开启供应商后，原已下架商品不会自动上架，需要手动操作重新上架");
            if (answer) {
                $.ajax({
                    url: "/app/updateCloseStatus.action",
                    async: false,
                    data: 'supplierId=' + merchanId + "&updateMerchanType=" + 1,
                    success: function (info) {
                        if (info == 1) {
                            alert("操作成功!");
                            document.forms[1].action = "supplierAuditList.action";
                            document.forms[1].submit();
                        } else {
                            alert("系统异常，操作失败!");
                            return;
                        }
                    }

                });
            }
        }
    }

    function exportSupplierInfo() {
        $('#frm').attr("action", "/app/exportSupplierAuditList.action");
        $('#frm').submit();
        $('#frm').attr("action", "/app/supplierAuditList.action");
    }
</script>
</body>
</html>