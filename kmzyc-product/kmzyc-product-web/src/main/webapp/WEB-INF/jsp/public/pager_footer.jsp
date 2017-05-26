<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<table width="100%" border=0  cellspacing="0" cellpadding="5">
    <tr height="1" class="toolbar" valign="right" border=0>
        <td valign="bottom" align="left">

			共<font color='#008000'> <s:property value='page.recordCount'/> </font>条记录，
			第<font color='#008000'> <s:property value='page.pageNo'/> </font>页，
			共<font color='#008000'> <s:property value='page.pageCount'/> </font>页
        </td>
        <td align="right">

			<s:if test="(page.pageCount==0)||(page.pageCount==1)">
				<img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0>
				<img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0>
				<img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0>
				<img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0>
			</s:if>
			<s:else>
				<s:if test="page.pageNo==1">
					<img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0>
					<img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0>
					<a href="#" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0></a>
					<a href="#" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0></a>
				</s:if>
				<s:elseif test="page.pageNo==page.pageCount">
					<a href="#" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0></a>
					<a href="#" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0></a>
					<img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0>
					<img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0>
				</s:elseif>
				<s:else>
					<a href="#" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" align="absmiddle" border=0></a>
					<a href="#" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" align="absmiddle" border=0></a>
					<a href="#" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" align="absmiddle" border=0></a>
					<a href="#" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" align="absmiddle" border=0></a>
				</s:else>
			</s:else>

        </td>
    </tr>
</table>
