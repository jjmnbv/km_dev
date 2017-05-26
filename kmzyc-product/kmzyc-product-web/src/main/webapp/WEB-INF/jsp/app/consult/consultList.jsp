<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>咨询管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
    <script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></script>
    <script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/dialog.js"></script>
    <script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
    <script type="text/javascript" src="/etc/js/common.js"></script>
    <style type="text/css">
        .tableStyle1 {
            font-size: 12px;
        }
    </style>
    <script type="text/javascript">
        /**  进入处理咨询的页面  **/
        function gotoAnswer(consultId) {
            $("#sPreConsultId").val(consultId);
            document.forms[0].action = "/app/gotoConsultReply.action";
            document.forms[0].submit();
        }
        /**  进入处理咨询回复修改的页面  **/
        function gotoAnswerUpdate(consultId) {
            $("#sPreConsultId").val(consultId);
            document.forms[0].action = "/app/gotoConsultReplyUpdate.action";
            document.forms[0].submit();
        }
        /** 进入审核的页面 **/
        function gotoCheck(consultId) {
            $("#sPreConsultId").val(consultId);
            document.forms[0].action = "/app/gotoConsultCheck.action";
            document.forms[0].submit();
        }
        /**  自动审核**/
        function autoCheck() {
            location.href = "/app/autoCheck.action";
        }
        /**查看咨询详情**/
        function gotoViewConsult(consultId) {
            dialog("查看审核明细", "iframe:/app/gotoViewConsult.action?pre_consultId=" + consultId, "600px", "400px", "iframe");
        }

        function consultCheckPass(consultId) {
            var answer = confirm("确认审核通过吗?");
            if (!answer) {
                return false;
            }
            $.ajax({
                url: '/app/consultCheckPass.action',
                async: false,
                data: 'consultIdVal=' + consultId,
                success: function (info) {
                    if ('0' == info) {
                        alert("系统异常，审核失败!");
                        location.href = "/app/gotoQueryConsultList.action";
                        return;
                    } else {
                        alert("审核成功!");
                        location.href = "/app/gotoQueryConsultList.action";
                    }
                }
            });
        }

        function consultCheckNoPass(consultId) {
            var answer = confirm("确认审核不通过吗?");
            if (!answer) {
                return false;
            }
            $.ajax({
                url: '/app/consultCheckPass.action',
                async: false,
                data: 'consultIdVal=' + consultId + '&ckType=' + 1,
                success: function (info) {
                    if ('0' == info) {
                        alert("系统异常，审核失败!");
                        location.href = "/app/gotoQueryConsultList.action";
                        return;
                    } else {
                        alert("审核成功!");
                        location.href = "/app/gotoQueryConsultList.action";
                    }
                }
            });
        }
    </script>
</head>
<s:set name="parent_name" value="'产品咨询评价管理'" scope="request"/>
<s:set name="name" value="'产品咨询管理'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
<s:form name="consultForm" method="post" action="/app/gotoQueryConsultList.action">
    <!-- 标题条 -->
    <!-- 按钮条 -->

    <!-- 查询条件区域 -->
    <table width="98%" class="content_table searcharea" align="center" cellpadding="0"
           cellspacing="0">
        <tr>
            <td>产品标题：
                <input name="newSearchConsut.productTitle" type="text" class="input_style"
                       value="<s:property value='newSearchConsut.productTitle'/>"></td>
            <td>咨询内容：
                <input name="newSearchConsut.consultContent" type="text" class="input_style"
                       value="<s:property value='newSearchConsut.consultContent'/>"></td>
            <td>回复状态：
                <s:select list="#request.ConsultReplyMap" name="newSearchConsut.replyStatus"
                          headerKey="" headerValue="--全部状态--">
                </s:select>
            </td>
        </tr>
        <s:date name='quartzprophy.triggerStarttime' format='yyyy-MM-dd'/>
        <tr>
            <td>咨询时间：
                <input name="newSearchConsut.consultStart"
                       type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                       value="<s:date name='newSearchConsut.consultStart'  format='yyyy-MM-dd' />"></td>
            <td>到：
                <input name="newSearchConsut.consultEnd"
                       type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                       value="<s:date name='newSearchConsut.consultEnd'  format='yyyy-MM-dd'  />"></td>
            <td>咨询类型：
                <s:select list="#request.ConsultTypeMap" name="newSearchConsut.type"
                          headerKey="" headerValue="--全部咨询类型--"></s:select>
            </td>
            <td><input type="submit" class="queryBtn" value="">&nbsp;
                <%--<input type="button" onClick="autoCheck()" style="cursor: pointer;"
                       class="btn-custom btnStyle" value="自动审核"/>--%>
            </td>
        </tr>
    </table>
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" align="center" cellpadding="3"
           style="cursor:pointer;" cellspacing="0" border="1" bordercolor="#C1C8D2">
        <tr>
            <th align="center" width="15%">产品标题</th>
            <th width="6%" align="center">咨询人</th>
            <th width="6%" align="center">咨询类型</th>
            <th width="19%" align="center">咨询内容</th>
            <th width="4%" align="center">回复状态</th>
            <th width="22%" align="center">回复内容</th>
            <th width="4%" align="center">审核状态</th>
            <th width="11%" align="center">咨询时间</th>
            <th width="27%" align="center">操作</th>
        </tr>
        <s:iterator id="custiterator" value="page.dataList">
            <tr>
                <td align="center" width="8%"><s:property value="productTitle"/></td>
                <td align="center"><s:property value="custNickname"/></td>
                <td align="center"><s:property value="#request.ConsultTypeMap[type]"/></td>
                <td align="center"><s:property value="consultContent"/></td>
                <td align="center">
                    <s:property value="#request.ConsultReplyMap[replyStatus]"/>
                </td>
                <td align="center"><s:property value="replyContent"/></td>
                <td align="center">
                    <s:property value="#request.ConsultCheckMap[checkStatus]"/></td>
                <td align="center"><s:date name="consultDate" format="yyyy-MM-dd hh:mm:ss"/></td>
                <td align="center">
                    <input type="hidden" style="cursor: pointer;" value="<s:property value='consultId'/>">
                    <s:if test="replyStatus==0">
                    <input type="button" style="cursor: pointer;" value=" 回复 "
                           class="btnStyle" onClick="gotoAnswer(<s:property value='consultId'/>)">
                    </s:if>
                    <s:if test="replyStatus==1">
                    <input TYPE="button" style="cursor: pointer;" value=" 修改回复 "
                           class="btnStyle" onClick="gotoAnswerUpdate(<s:property value='consultId'/>)">
                    </s:if>
                    &nbsp;&nbsp;
                    <%--    <s:if test="checkStatus==1 &&replyStatus==1">
						 <img type="button" style="cursor: pointer;" title="审核通过"  src="/etc/images/button_new/pass.png" onclick="consultCheckPass(<s:property value='consultId'/>)">
				  </s:if>--%>
                    <%--<s:if test="checkStatus==2 && replyStatus==1"> -->
                    <img TYPE="button" style="cursor: pointer;" value="审核不通过" src="/etc/images/button_new/check.png" title="审核不通过"
						 onClick="gotoCheck(<s:property value='consultId'/>)">
                     <img type="button" style="cursor: pointer;" title="审核不通过" src="/etc/images/button_new/notpass.png" onclick="consultCheckNoPass(<s:property value='consultId'/>)">
				  </s:if> --%>
                    <s:if test="replyStatus==1">
                    <img title="审核" style="cursor: pointer;" src="/etc/images/button_new/check.png"
                         onClick="gotoCheck(<s:property value='consultId'/>)">
                    </s:if>
                    <img title="查看" style="cursor: pointer;" src="/etc/images/button_new/select.png"
                         onClick="gotoViewConsult(<s:property value='consultId'/>)"/>
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
    <input type="hidden" value="" name="pre_consultId" id="sPreConsultId"/>
</s:form>
</body>
</html>