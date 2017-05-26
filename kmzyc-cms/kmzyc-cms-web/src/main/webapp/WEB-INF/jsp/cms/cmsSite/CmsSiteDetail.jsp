<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams" %>
<html>
<head>
    <title>站点列表</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet"
          type="text/css"/>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <script type="text/javascript" src="/etc/js/pageCommon.js"></script>
    <script type="text/javascript" src="/etc/js/checkeds.js"></script>
    <script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/messages_cn.js"></script>
    <script type="text/javascript"><!--
    $(document).ready(function () {
        var checks = "";
        checks = $("#checkeds").val();
        if (checks != "") {
            var checkboxs = document.getElementsByName("userSiteIds");
            var myarr = new Array();
            myarr = checks.split(',');
            for (var i = 0; i < checkboxs.length; i++) {
                for (var j = 0; j < myarr.length; j++) {
                    if (checkboxs[i].value == myarr[j]) {
                        checkboxs[i].checked = true;
                        break;
                    }
                }
            }
        }
    });

    /** 删除站点信息  */
    function delAll(id) {
        var obj = document.getElementsByName(id);
        var checks = $("#checkeds").val();
        if (checks == '') {
            alert("请选择要删除的数据。");
            return false;
        } else if (confirm('是否确认删除?') == true) {
            var siteId = $("#siteId").val();
            location.href =
                    "/cms/cmsSite_delCmsUserSites.action?checkeds=" + checks + "&siteId=" + siteId;

        }
    }
    /** 单条删除站点信息  **/
    function deleteByKey(id) {
        if (confirm("是否确认删除? ") == true) {
            var siteId = $("#siteId").val();
            location.href =
                    "/cms/cmsSite_delUserSiteIds.action?userSiteIds=" + id + "&siteId=" + siteId;
        }
    }
    function goBack() {
        window.location.href = "/cms/cmsSite_querySiteUserPage.action";
    }
    //授权
    function warrant(siteId) {
        dialog("选择窗口", "iframe:/sys/listSysUserPop.action?siteId=" + siteId, "900px", "530px",
               "iframe");
    }
    function closeOpenSiteDiv() {
        closeThis();
        var cmsUserSiteForm = document.getElementById("cmsUserSiteForm");
        cmsUserSiteForm.action = "/cms/cmsSite_gotoDetail.action";
        cmsUserSiteForm.submit();
    }
    --></script>
    <style type="text/css">
        .detail_table tr td {
            text-align: left;
        }

        .detail_table tr td span {
            font-weight: bold;
        }

        .detail_table .binkBtn {
            background-color: buttonface;
            cursor: pointer;
            border: 1px solid #CBCBCB;
            height: 25px;
        }
    </style>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础功能'" scope="request"/>
<s:set name="name" value="'站点详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div style="height: 90%;">
    <s:form id="cmsUserSiteForm" name="cmsUserSiteForm"
            onsubmit="return checkAllTextValid(this)"
            action="/cms/cmsSite_gotoDetail.action" method="post">

        <table width="98%" class="detail_table list_table" cellpadding="3"
               align="center">
            <tr>
                <th colspan="2" align="left" class="edit_title">
                    站点信息
                </th>
            </tr>
            <tr>
                <input type="hidden" name="siteId"
                       value="<s:property value="cmsSite.siteId"/>" id="siteId"/>

                <td>
                    <span>站点名称:</span>
                    <s:property value="cmsSite.name"/>
                </td>
                <td>
                    <span>站点英文名称:</span>
                    <s:property value="cmsSite.engName"/>
                </td>
                <td>
                    <span>站点域名:</span>
                    <s:property value="cmsSite.url"/>
                </td>
            </tr>
            <tr>
                <td>
                    <span>页面模板路径:</span>
                    <s:property value="cmsSite.templatePath"/>
                </td>
                <td>
                    <span>页面输出路径:</span>
                    <s:property value="cmsSite.pageOutputPath"/>
                </td>
            </tr>
            <tr>
                <td>
                    <%--<span>渠道信息:</span>--%>
                    <%--<s:property value="cmsSite.channel"/>--%>
                </td>
                <td>
                    <span>状态:</span>

                    <s:if test="cmsSite.status==0">有效</s:if>

                    <s:if test="cmsSite.status==1">无效</s:if>
            </tr>
            <tr>
                <td colspan="2">
                    <span>备注:</span>
                    <s:property value="cmsSite.siteId"/>
                </td>

            </tr>

            <s:if test="adminType!=0">
                <tr>
                    <td colspan="2">
								<span>已选择的用户:<input type="button" value="继续选择"
                                                    class="binkBtn"
                                                    onclick="warrant(<s:property
                                                            value="cmsSite.siteId"/>)"/>
										 <input
                                                 type="button" value="删除" class="binkBtn"
                                                 onclick="delAll('userSiteIds')"/> </span>
                        用户名：<input type='test' name='cmsUserSite.sysUser.userName'
                                   value="<s:property value="cmsUserSite.sysUser.userName"/>">
                        <input type="submit" value="查询" class="binkBtn"/>
                    </td>

                </tr>
            </s:if>
        </table>
        <table width="98%" class="list_table" cellpadding="3" align="center">
            <input type="hidden" id="checkeds" name="checkeds"
                   value="<s:property value='checkeds'/>"/>
            <tr>
                <th width="5%">
                    <input type='checkbox' name='userSiteIds'
                           onclick="checkAllpop(this,'userSiteIds')">
                </th>
                <th>
                    站点名
                </th>
                <th>
                    用户名
                </th>
                <th>
                    操作
                </th>
            </tr>
            <s:iterator id="cmsSite" value="page.dataList">
                <tr>
                    <td width="5%">
                        <input type="checkbox" name="userSiteIds"
                               value='<s:property value="userSiteId"/>'
                               onclick="checkpromotionId(this);">
                    </td>
                    <TD>
                        <s:property value="siteName"/>
                    </TD>
                    <TD>
                        <s:property value="sysUserMap[userId]"/>
                    </TD>
                    <td>
                        <img title="删除" style="cursor: pointer;"
                             src="/etc/images/icon_delete.png"
                             onclick='deleteByKey(<s:property value="userSiteId"/>)'/>
                    </td>
                </tr>
            </s:iterator>
        </table>
        <table class="page_table" width="98%" align="center" cellpadding="0"
               cellspacing="0" border="0">
            <tr>
                <td>
                    <s:set name="form_name" value="'cmsUserSiteForm'" scope="request"></s:set>
                    <jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
                </td>
            </tr>
        </table>
        <table width="60%" class="edit_bottom" height="30" border="0"
               cellpadding="0" cellspacing="0">
            <tr>
                <td align="left">
                    <input class="backBtn" onclick="goBack()" type="button" value="">
                </td>
                <td width="20%" align="center"></td>
            </tr>
        </table>
    </s:form>
    <!-- 消息提示页面 -->
    <jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</div>
</body>
</html>

