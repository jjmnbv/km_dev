<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加供应商</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <script language="JavaScript">
        function gotoList() {
            document.forms[0].action = "supplierAuditList.action";
            document.forms[0].submit();
        }

        function submitForm() {
            var userName = $("#userNameId").val();
            if ("" == userName) {
                alert("请输入用户名!");
                return;
            }
            $.ajax({
                url: 'verifyUser.action',
                async: false,
                data: 'userName=' + encodeURI(encodeURI(userName)),
                success: function (info) {
                    if ('0' == info) {
                        alert("此用户不存在，请先注册用户!");
                        return;
                    } else if ('1' == info) {
                        alert("此用户申请了供应商!");
                    } else if ('2' == info) {
                        $('#frm').submit();
                    }
                },
                error: function () {
                    alert("查询失败，请联系管理员");
                }
            });
        }
    </script>
</head>
<body>

<s:set name="parent_name" value="'供应商审核管理'" scope="request"/>
<s:set name="name" value="'添加供应商'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="toSupplierAdd" method="post" namespace='/app' id="frm">

    <!-- 数据编辑区域 -->
    <table width="95%" class="edit_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px;">
        <!-- error message -->
        <s:if test="rtnMessage != null">
            <tr>
                <td colspan="2" align="center"><font color="red"><s:property
                        value='rtnMessage'/></font></td>
            </tr>
        </s:if>
        <tr>
            <th colspan="2" align="left" class="edit_title">验证注册用户</th>
        </tr>
        <tr>
            <th width="50%" align="right" class="eidt_rowTitle">请先注册新用户：</th>
            <td width="50%">
                <a href="${goB2bRegisterPath}" target="_blank">注册</a>
            </td>
        </tr>
        <tr>
            <th width="50%" align="right" class="eidt_rowTitle"><font color="red">*</font>请输入您注册的用户名称：</th>
            <td width="50%">
                <input type="text" id="userNameId" name="userName"
                       onKeyPress="if(event.keyCode==13||event.which==13){return false;}">
            </td>
        </tr>
    </table>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30"
           border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
        <tr>
            <td align="center">
                <input type="button" value="下一步" class="btn-custom nextBtn" onClick="submitForm();">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" class="backBtn" onClick="gotoList()"/>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <br>
    <br>
</s:form>
</body>
</html>