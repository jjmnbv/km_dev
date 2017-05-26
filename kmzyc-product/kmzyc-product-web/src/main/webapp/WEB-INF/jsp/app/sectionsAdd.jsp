<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加新栏目</title>
    <link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <Script src="/etc/js/97dater/WdatePicker.js"></Script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
</head>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'栏目管理'" scope="request"/>
<s:if test="viewType=='add'">
    <s:set name="son_name" value="'栏目添加'" scope="request"/>
</s:if>
<s:if test="viewType=='edit'">
    <s:set name="son_name" value="'栏目修改'" scope="request"/>
</s:if>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body onkeydown="changeKey();">
<s:form action="/app/saveSections.action" method="POST" id="sectionsForm" namespace='/sys'>

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<INPUT TYPE="hidden" name="userSt" value="1">
<input name="sections.sectionsId" id="sectionsId" type="text" style="display:none;"
       value="<s:property value='sections.sectionsId'/>"/>
<input name="viewType" id="viewType" type="text" style="display:none;"
       value="<s:property value='viewType'/>"/>
<input name="sections.createTime" type="text" style="display:none;"
       value="<s:property value='sections.createTime'/>"/>

<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2"
       style="border-collapse: collapse">
    <!-- error message -->
    <s:if test="rtnMessage != null">
        <tr>
            <td colspan="2" align="center">
                <font color="red"><s:property value='rtnMessage'/></font>
            </td>
        </tr>
    </s:if>
    <tr>
        <th colspan="2" align="left" class="edit_title">基本信息</th>
    </tr>
    <tr>
        <th align="right"><font color="red">*</font>栏目名称:</th>
        <td>
            <s:if test="viewType=='add'">
                <input name="sections.sectionsName" id="sectionsName" type="text" value=""/>
            </s:if>
            <s:if test="viewType=='edit'">
                <input name="sections.sectionsName" id="sectionsName" type="text"
                       value="<s:property value='sections.sectionsName'/>"/>
            </s:if>
        </td>
    </tr>
    <tr>
        <th align="right"><font color="red">*</font>栏目标识:</th>
        <td>
            <s:if test="viewType=='add'">
                <s:token></s:token>
                <input name="sections.identification" id="identification" type="text" value=""/>
            </s:if>
            <s:if test="viewType=='edit'">
                <input name="sections.identification" id="identification" type="text"
                       value="<s:property value='sections.identification'/>"/>
            </s:if>
        </td>
    </tr>
    <tr>
        <th align="right" width="20%">栏目编号:</th>
        <td width="80%">
            <s:if test="viewType=='add'">
                <input name="sections.sectionsCode" type="text" id="sectionsCodes" value=""/>
            </s:if>
            <s:if test="viewType=='edit'">
                <input name="sections.sectionsCode" type="text" id="sectionsCodes"
                       value="<s:property value='sections.sectionsCode'/>"/>
            </s:if>
        </td>
    </tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="bottombuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="80%" align="center">
            <input class="saveBtn" type="button" onclick="submitCheckForm();" value=""/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" name="button" onClick="gotoList();" class="backBtn"/>
        </td>
        <td width="20%" align="center"></td>
    </tr>
</table>
<br><br>

</s:form>
<s:form name="listForm" method="post" action="/app/querySectionsList.action" id="listForm">
    <s:hidden type="hidden" name="checkedId"/>
    <s:hidden name="sectionsForSelectPara.sectionsCode"/>
    <s:hidden name="sectionsForSelectPara.sectionsName"/>
    <s:hidden name="sectionsForSelectPara.identification"/>
    <s:hidden name="page.pageNo"/>
</s:form>

<script language="JavaScript">
    $(document).ready(function () {
        $("#sectionsForm").validate({
            rules: {
                "sections.sectionsName": {required: true, maxlength: 16, unusualChar: true},
                "sections.identification": {required: true, maxlength: 50, unusualChar: true}
            },
            success: function (label) {
                label.removeClass("checked").addClass("checked");
            }
        });
    });

    function checkSectionsName() {
        $.ajax({
            dataType: 'json',
            url: '/app/checkSectionsName.action?name=' + $('#sectionsName').val(),
            error: function () {
                alert('请求失败，请稍后重试或与管理员联系！')
            },
            success: function (date) {
                if (date.result == false) {
                    alert("栏目名重复!");
                    $('#sectionsName').select();
                    return;
                } else {
                    checkIdentification();
                }
            }
        });
    }

    function checkIdentification() {
        $.ajax({
            dataType: 'json',
            url: '/app/checkIdentification.action?identification=' + $('#identification').val(),
            error: function () {
                alert('请求失败，请稍后重试或与管理员联系！')
            },
            success: function (date) {
                if (date.result == false) {
                    alert("栏目标识重复!");
                    $('#identification').select();
                    return;
                } else {
                    $('#sectionsForm').submit();
                }
            }
        });
    }

    function checkSectionsNameByModify() {
        $.ajax({
            dataType: 'json',
            url: '/app/checkSectionsNameByModify.action?name=' + $('#sectionsName').val() + '&id=' + $('#sectionsId').val(),
            error: function () {
                alert('请求失败，请稍后重试或与管理员联系！')
            },
            success: function (date) {
                if (date.result == false) {
                    alert("栏目名重复!");
                    $('#sectionsName').select();
                    return;
                } else {
                    checkIdentificationByModify();
                }
            }
        });
    }

    function checkIdentificationByModify() {
        $.ajax({
            dataType: 'json',
            url: '/app/checkIdentificationByModify.action?identification=' + $('#identification').val() + '&id=' + $('#sectionsId').val(),
            error: function () {
                alert('请求失败，请稍后重试或与管理员联系！')
            },
            success: function (date) {
                if (date.result == false) {
                    alert("栏目标识重复!");
                    $('#identification').select();
                    return;
                } else {
                    $('#sectionsForm').submit();
                }
            }
        });
    }

    function submitCheckForm() {
        if ($('#viewType').val() == 'add') {
            checkSectionsName();
        } else {
            $('#sectionsForm').attr("action", "/app/updateSections.action");
            checkSectionsNameByModify();
        }
    }

    function gotoList() {
        $('#listForm').submit();
    }
    //增加栏目明细
    function addSectionsDetail() {
        dialog("新增栏目明细", "iframe:/app/gotoSectionsDetailAdd.action", "500px", "310px", "iframe");
    }

    //光标移动
    function changeKey() {
        var tr = event.srcElement.getAttribute("type");
        if ("textarea" != tr && "button" != tr) {
            if (13 == event.keyCode) {
                event.keyCode = 9;
            }
        }
    }
</script>
<style type="text/css">
    /**输入框错误提示**/
    label.error {
        background: url(/etc/images/li_err.gif) no-repeat;
        font: 12px/1 verdana, simsun, sans-serif;
        margin: 0;
        padding-left: 20px;
        height: 20px;
        line-height: 20px;
        margin-left: 10px;
    }

    label.checked {
        background: url(/etc/images/li_ok.gif) no-repeat;
        font: 12px/1 verdana, simsun, sans-serif;
        margin: 0;
        padding-left: 20px;
        height: 20px;
        line-height: 20px;
        margin-left: 10px;
    }
</style>
</body>
</html>