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
    <Script src="/etc/js/97dater/WdatePicker.js"></Script>
    <script type="text/javascript" src="/etc/js/pageCommon.js"></script>
    <script type="text/javascript" src="/etc/js/checkeds.js"></script>
    <script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
    <script>
        $(document).ready(function () {
            var checks = "";
            checks = $("#checkeds").val();
            if (checks != "") {
                var checkboxs = document.getElementsByName("siteId");
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
        /* function deleteSelected(id){
         var obj = document.getElementsByName(id);
         var count = 0;
         var obj_cehcked = null;
         // 遍历所有用户，找出被选中的用户
         for (var i = 0; i < obj.length; i++) {
         if (obj[i].checked) {
         count++;
         obj_cehcked = obj[i];
         }
         }
         if (count == 0) {
         alert("请选择要删除的数据。");
         return false;
         }else if(confirm('是否确认删除?')==true){
         document.cmsSiteForm.action="/cms/cmsSite_delCmsSite.action";
         document.cmsSiteForm.submit();
         }
         }*/
        function addgoto() {

            location.href = "/cms/GotoAdcolumnAdd.action"
        }

        /** 进入修改站点页面  **/
        function editcmsSite(id) {
            var pageNo = $("#pageNo").val();
            location.href =
                    "/cms/cmsSite_gotoEditCmsSite.action?siteId=" + id + "&pageNo=" + pageNo;
        }
        //详情
        function detail(siteId) {
            location.href = "/cms/cmsSite_gotoDetail.action?siteId=" + siteId;
        }

        //授权
        function warrant(siteId) {
            dialog("选择窗口", "iframe:/sys/listSysUserPop.action?siteId=" + siteId, "900px", "530px",
                   "iframe");
        }
        function closeOpenSiteDiv() {
            closeThis();

        }
        function addgoto() {
            var pageNo = $("#pageNo").val();
            location.href = "/cms/cmsSite_gotoAddCmsSite.action";
        }

        /** 单条删除站点信息  **/
        function deleteByKey(id) {
            if (confirm("是否确认删除? ") == true) {
                $.ajax({
                           url: "/cms/cmsSite_checkUserSite.action",
                           async: false,
                           data: "siteId=" + id,
                           type: 'post',
                           success: function (result) {
                               ok = result;
                           }
                       });
                if (ok == 0) {
                    location.href = "/cms/cmsSite_delCmsSite.action?siteIds=" + id;
                } else {
                    alert("此站点已关连到用户请不要删除！")
                }

            }
        }

    </script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'站点管理'" scope="request"/>
<s:set name="name" value="'站点记录'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div style="height: 90%; overflow-y: scroll;">
    <s:form class="pageForm" name="cmsSiteForm"
            onsubmit="return checkAllTextValid(this)"
            action="/cms/cmsSite_queryForPage.action" method="post">
        <table width="98%" align="center" border="0"
               class="content_table" cellpadding="0" cellspacing="0">
            <tr>
                <td width="60%" valign="middle" colspan="2">

                </td>
            </tr>
            <td>
                站点名：
                <input name="cmsSite.name" type="text"
                       value="<s:property value="cmsSite.name"/>">
            </td>
            <td align="left" width="22%">
                站点域名：
                <input name="cmsSite.url" type="text"
                       value="<s:property value="cmsSite.url"/>">
            </td>
            <td>
                状态:
                <select name="cmsSite.status">
                    <option value=""
                            <s:if test="cmsSite.status==null">selected="selected"</s:if>>
                        全部
                    </option>
                    <option value="0"
                            <s:if test="cmsSite.status==0">selected="selected"</s:if>>
                        有效
                    </option>
                    <option value="1"
                            <s:if test="cmsSite.status==1">selected="selected"</s:if>>
                        无效
                    </option>
                </select>
            </td>
            <td align="right">
                <INPUT TYPE="submit" class="queryBtn" value="">
                <input class="addBtn" TYPE="button" value="" onClick="addgoto();">
                &nbsp;&nbsp;


                <input type="hidden" id="checkeds" name="checkeds"
                       value="<s:property value='checkeds'/>"/>
            </td>

            </tr>
        </table>
        <table width="98%" class="list_table" cellpadding="3" align="center">
            <tr>
                <th width="5%">
                    <input type='checkbox' name='siteIds'
                           onclick="checkAll(this,'siteIds')">
                </th>
                <th>
                    站点名
                </th>
                <th>
                    站点英文名
                </th>
                <th>
                    站点域名
                </th>
                    <%--<th>--%>
                    <%--渠道信息--%>
                    <%--</th>--%>
                <th>
                    页面模板路径
                </th>
                <th>
                    页面输出路径
                </th>
                <th>
                    状态
                </th>
                <th>
                    修改日期
                </th>
                <th>
                    修改人
                </th>
                <th>
                    操作
                </th>
            </tr>
            <s:iterator id="cmsSite" value="page.dataList">
                <tr>
                    <td width="5%">
                        <input type="checkbox" name="siteIds"
                               value='<s:property value="siteId"/>'
                               onclick="checkpromotionId(this);">
                    </td>
                    <TD>
                        <s:property
                                value="name"/>

                    </TD>
                    <TD>
                        <s:property
                                value="engName"/>

                    </TD>
                    <TD>
                        <s:property
                                value="url"/>

                    </TD>
                        <%--<TD>--%>
                        <%--<s:property value="channel" />--%>
                        <%--</TD>--%>
                    <TD>
                        <s:property value="templatePath"/>
                    </TD>
                    <TD>
                        <s:property value="pageOutputPath"/>
                    </TD>
                    <TD>
                        <s:if test="status==0">
                            有效
                        </s:if>
                        <s:if test="status==1">
                            无效
                        </s:if>
                    </TD>
                    <td>
                        <s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <s:property value="sysUserMap[modified]"/>
                    </td>
                    <td>
                        <img title="修改" style="cursor: pointer;"
                             src="/etc/images/icon_modify.png"
                             onclick='editcmsSite(<s:property value="siteId"/>)'/>
                        <img title="删除" style="cursor: pointer;"
                             src="/etc/images/icon_delete.png"
                             onclick='deleteByKey(<s:property value="siteId"/>)'/>
                    </td>
                </tr>
            </s:iterator>
        </table>
        <table class="page_table" width="98%" align="center" cellpadding="0"
               cellspacing="0" border="0">
            <tr>
                <td>
                    <s:set name="form_name" value="'cmsSiteForm'" scope="request"></s:set>
                    <jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
                </td>
            </tr>
        </table>
    </s:form>
    <!-- 消息提示页面 -->
    <jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</div>
</body>
</html>

