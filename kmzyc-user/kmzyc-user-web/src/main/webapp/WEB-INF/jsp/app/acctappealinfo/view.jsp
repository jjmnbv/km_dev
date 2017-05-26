<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>查看申诉信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	</head>

	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'安全认证'" scope="request" />
		<s:set name="son_name" value="'查看申诉'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<td width="20%" align="right">
						账户号：
					</td>
					<td width="80%">
					    <input type="hidden" name="bnesAcctAppealInfo.accountAppealId" value="<s:property value='bnesAcctAppealInfo.accountAppealId'/>">
						 <input type="hidden" name="bnesAcctAppealInfo.accountId" value="<s:property value='bnesAcctAppealInfo.accountId'/>">
						<s:property value="bnesAcctAppealInfo.accountName" />

					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						申诉标题：
					</td>
					<td width="80%">
						
					    <s:property value="bnesAcctAppealInfo.appealTitle"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						申诉内容：
					</td>
					<td width="80%">
                        <s:property value='bnesAcctAppealInfo.appealContent'/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						申诉日期：
					</td>
					<td width="80%">
					    <fmt:formatDate value="${bnesAcctAppealInfo.appealCreateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						处理意见：
					</td>
					<td width="80%">
                      <s:property value='bnesAcctAppealInfo.appealSuggestion'/>
					</td>
				</tr>
			</table>


			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList()" type="button"
							value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>

		<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/acctBusiness/acctAppealInfo_list.action";
}

</SCRIPT>
	</BODY>
</HTML>