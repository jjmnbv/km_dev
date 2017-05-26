<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户金额修改明细</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /** 删除账户信息  **/ 
 
</script>
</head>
<body>
<!-- 标题条 -->
<div>
	<table  width="98%" height="30" class="content_table" align="center" cellpadding="0" cellspacing="0" >
		<tr>
			<td width="75px" align="left">会员账号：</td>
			<td>
		     <s:property value="#request.accountLogin"/>
			</td>
		</tr>
	</table>

</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form  name="modifyAcotInfoForm" action="/accounts/modifyAcut_showModifyHisory.action" method="post">
<input type="hidden" id="type" name="type"   value="<s:property value='bnesAcctTransactionQuery.type'/>" />
<INPUT TYPE="hidden" name="accountId" id="accountId" value="<s:property value="bnesAcctTransactionQuery.accountId"/>">
<!-- 数据列表区域 -->
<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
		<tr>
	   <%--  <th width="5%">
						<input type='checkbox' name='allbox' onclick='checkAll(this)'>
					</th>--%>
		<!-- <th width="10%">账户号</th> -->
		<th width="20%">账户金额修改流水号</th>
		<th width="10%">修改金额</th>
		<th width="10%">修改状态</th>
		<th width="35%">账户金额修改备注</th>
		<th width="25%">修改时间</th>
	</tr>
<s:iterator id="custiterator"  value="page.dataList">
<tr>
<%--
<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="accountTransactionId"/>' />
						</td>

		 --%> 
		 <!--  
<td>
 <s:property value="accountLogin"/>
</td>
 -->
	<td>
	<s:property value="accountNumber"/>
	</td>
	<td>
	   <s:property value="%{formatDouble(amount)}"/>
	</td>
	
	<td>
		<s:if test="status==0">
		修改处理失败
		</s:if>
		<s:if test="status==1">
		修改成功
		</s:if>
		<s:if test="status==2">
		修改理中
		</s:if>
		<s:if test="status==3">
		修改金额退回
		</s:if>
	</td>
	<td>
		<s:property value="content"/>
	</td>
	<td>
		<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
	</td>

	</tr>	
	</s:iterator>
	</table>

	<table width="98%" align="center" class="page_table">
		<tr>
			<td>
				<s:set name="form_name"  value="'modifyAcotInfoForm'"  scope="request"></s:set>
				<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
			</td>
		</tr>
	</table>
	</s:form>
	</div>
	</body>
	</html>