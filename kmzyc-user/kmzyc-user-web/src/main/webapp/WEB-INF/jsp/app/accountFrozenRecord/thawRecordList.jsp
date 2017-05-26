<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>账号冻结</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		//账号层
		function queryAccountInfo(id) {
			dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+ id, "900px", "760px", "iframe");
		}
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'账户冻结'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form name="bnesFrozenRecordForm" onsubmit="return checkAllTextValid(this)" method="post" action="/accounts/accountFrozen_searchFrozenRecord.action">
				<!-- 查询条件区域 -->
				<table width="98%" height="85" class="content_table" align="center"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">账户名：</td>
						<td><input name="username" type="text" value="<s:property value='bnesFrozenRecord.username'/>"/></td>
						<td align="right">姓名：</td>
						<td><input name="realName" type="text" value="<s:property value='bnesFrozenRecord.realName'/>" /></td>
						<td align="right">投诉账号：</td>
						<td><input name="sueName" type="text" value="<s:property value='bnesFrozenRecord.sueName'/>" /></td>
						<td align="right">投诉姓名：</td>
						<td><input name="sueRealName" type="text" value="<s:property value='bnesFrozenRecord.sueRealName'/>" /></td>
					</tr>
					<tr>
						<td align="right">冻结类型：</td>
						<td colspan="6"><s:select name="frozenType" value="%{bnesFrozenRecord.frozenType}" label="status" list="#{1:'冻结账户',2:'冻结金额'}" headerKey="" headerValue="全部"></s:select></td>
						<td align="right"><input TYPE="submit" class="queryBtn" value=""></td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
					<tr>
						<th>账户名</th>
						<th>真实姓名</th>
						<th>投诉账户名</th>
						<th>投诉账户姓名</th>
						<th>投诉日期</th>
						<th>投诉原因</th>
						<th>冻结类型</th>
						<th>冻结原因</th>
						<th>解冻的金额</th>
						<th>操作人</th>
						<th>操作日期</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td align="center"><a href="javascript:void(0);" onclick="queryAccountInfo(<s:property value="accountId"/>);"><s:property value="account" /></a></td>
						<td align="center"><s:property value="realName" /></td>
						<td align="center"><s:property value="sueName" /></td>
						<td align="center"><s:property value="sueRealName" /></td>
						<td align="center"><s:date name="sueDate" format="yyyy-MM-dd" /></td>
						<td align="center"><s:property value="sueReason" /></td>
						<td><s:if test="frozenType==1">账户冻结</s:if><s:elseif test="frozenType==2">金额冻结</s:elseif></td>
						<td align="center"><s:property value="frozenReason" /></td>
						<td align="center"><s:if test="frozenType==1"></s:if><s:elseif test="frozenType=2"><s:property value="frozenNumber" /></s:elseif></td>
						<td align="center"><s:property value="sysUserName" /></td>
						<td align="center"><s:date name="operatorDate"  format="yyyy-MM-dd hh:mm:ss"/></td>
					</tr>
					</s:iterator>
				</table>
				<table width="500" align="right">
					<tr>
						<td>
							<s:set name="form_name" value="'bnesFrozenRecordForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>