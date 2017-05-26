<%@page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    <%@ page isELIgnored="false"%>
    $(function () {
        //取消
        $('#cancel').bind('click', $.unblockUI);
    });
</script>
<form id="remarkForm">
    <table>
        <tr>
            <th>&nbsp;</th>
            <td style="background-color: #F2FAF3;line-height: 30px;font-size: 16px;font-weight: 900;">
                <a href="${filePath }">下载</a></td>
            <td></td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td></td>
            <td>
                <input type="button" class="ui-button ui-button-success" value="返回" id="cancel"/>
            </td>
        </tr>
    </table>
</form>