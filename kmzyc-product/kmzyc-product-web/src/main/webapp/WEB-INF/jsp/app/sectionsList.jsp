<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
    <title>栏目管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <style type="text/css">
        .tableStyle1 {
            font-size: 12px;
        }
    </style>
    <script type="text/javascript">
        /** 删除账户信息  **/
        function deleteSelected(id) {
            var obj = document.getElementsByName(id);
            var count = 0;
            var checkedValue = null;
            // 遍历所有用户，找出被选中的用户
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    count++;
                    checkedValue = obj[i];
                }
            }
            if (count == 0) {
                alert("请选择要删除的数据。");
                return false;
            } else if (confirm('是否确认删除?') == true) {
                document.sectionsForm.action = "/accounts/accountInfo_delete.action";
                document.sectionsForm.submit();
            }
        }

        function del_sect() {
            if (!checkIdSelected()) {
                alert('请选择你要删除的栏目!')
            } else {
                if (confirm("您确定要删除所选数据吗？")) {
                    location.href = "/app/gotoSectionsDel.action?delSec_id=" + selectedId;
                }
            }
        }
        //选中的栏目id
        var selectedId = "";
        function checkIdSelected() {
            var r = false;
            var b = "";
            $("input[name=sectionsId]").each(function () {
                if ($(this).attr("checked")) {
                    r = true;
                    b = b + this.value + ",";
                    return;
                }
            });
            selectedId = b;
            return r;
        }

        /**  进入新增栏目信息页面  **/
        function gotoAdd() {
            location.href = "/app/gotoSectionsAdd.action?viewType=add";
        }

        /**  查看明细  **/
        function querySectionsDetail(pre_sectionId, identification) {
            location.href = "/app/querySectionsDetailList.action?sections.sectionsId=" + pre_sectionId + "&identifi=" + identification;
        }

        /**  进入修改账户信息页面  **/
        function gotoUpdate(id) {
            document.sectionsForm.action = "/app/gotoSectionsAdd.action?sections.sectionsId=" + id + "&viewType=edit";
            document.sectionsForm.submit();
        }

        /** 全选js  **/
        function checkAll(ck) {
            for (var i = 0; i < ck.form.all.tags("input").length; i++) {
                var ele = ck.form.all.tags("input")[i];
                if ((ele.type == "checkbox")) {
                    if (ck.checked != ele.checked)
                        ele.click();
                }
            }
        }
    </script>
</head>
<s:set name="parent_name" value="'定制管理'" scope="request"/>
<s:set name="name" value="'栏目管理'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
<s:form name="sectionsForm" method="post" id="sectionsForm" action="/app/querySectionsList.action">
    <s:hidden name="checkedId" id="checkedId"/>
    <input type="hidden" id="rtnMsg" value="${msg}"/>
    <!-- 标题条 -->
    <!--<div class="pagetitle">栏目管理:</div>
    <!-- 按钮条 -->

    <!-- 查询条件区域 -->
    <table width="98%" class="content_table" align="center" cellpadding="0"
           cellspacing="0">
        <tr>
            <td align="right">栏目编号:</td>
            <td><input name="sectionsForSelectPara.sectionsCode" type="text" class="input_style"
                       value="<s:property value='sectionsForSelectPara.sectionsCode' />">
            </td>
            <td align="right">栏目名称：</td>
            <td><input name="sectionsForSelectPara.sectionsName" type="text" class="input_style"
                       value="<s:property value='sectionsForSelectPara.sectionsName' />">
            </td>
            <td align="right">栏目标识：</td>
            <td><input name="sectionsForSelectPara.identification" type="text" class="input_style"
                       value="<s:property value='sectionsForSelectPara.identification' />">
            </td>
            <td align="center">
                <input TYPE="submit" class="queryBtn" value=""/>
                <input class="addBtn" type="button" value="" onClick="gotoAdd();"/>
                <input class="delBtn" type="button" value="" onClick="del_sect();"/>
            </td>
        </tr>
    </table>


    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="5%">
                <input type='checkbox' name='allbox' onclick='checkAll(this)'/>
            </th>
            <th width="18%" align="center">栏目名称</th>
            <th width="" align="center">栏目标识</th>
            <th width="18%" align="center">栏目编号</th>
            <th width="19%" align="center">创建日期</th>
            <th width="" align="center">操作</th>
        </tr>
        <s:iterator id="custiterator" value="page.dataList">
            <tr>
                <td align="center" width="5%">
                    <input type="checkbox" name="sectionsId" value='<s:property value="sectionsId"/>'/>
                </td>
                <td align="center"><s:property value="sectionsName"/></td>
                <td align="center"><s:property value="identification"/></td>
                <td align="center"><s:property value="sectionsCode"/></td>
                <td align="center"><s:date name="createTime" format="yyyy-MM-dd"/></td>
                <td align="center">
                    <img type="button" style="cursor: pointer;" title="修改" value="修改"
                         src="/etc/images/button_new/edit.png"
                         onClick="gotoUpdate(<s:property value='sectionsId'/>)"/>
                    <img title="查看/添加产品" style="cursor: pointer;" src="/etc/images/button_new/select.png"
                         onClick="querySectionsDetail(<s:property value='sectionsId'/>,'<s:property value="identification"/>')"/>
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
    <s:if test='!msg.isEmpty()'>
        <script language="JavaScript">
            alert(document.getElementById("rtnMsg").value);
        </script>
    </s:if>
</s:form>
<s:form name="listForm" method="post" action="/app/querySectionsList.action" id="listForm">
    <s:hidden name="checkedId" id="checkedId"/>
</s:form>
</body>
</html>

