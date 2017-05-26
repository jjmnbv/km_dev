<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
    function selectOneAccount(accountId,account,name,amount,n_LoginId){
          parent.closeOpenAccount(accountId,account,name,amount,n_LoginId);
    }
</script>
</head>
<body>
<s:form  name="accountInfoForm" action="/accounts/accountInfo_popUpAccount.action" 
 onsubmit="return checkAllTextValid(this)" method="post">
 <input type="hidden" name="sourcePage" value="<s:property value='sourcePage'/>" />
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="accountInfo.accountLogin" type="text" value="<s:property value='accountInfo.accountLogin'/>">
		</td>
		<s:if test="#request.sourcePage==null || #request.sourcePage!='address'">
		<td align="right">账户真实姓名：</td>
		<td>
		     <input name="accountInfo.name" type="text" value="<s:property value='accountInfo.name'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="accountInfo.mobile" type="text" value="<s:property value='accountInfo.mobile'/>">
		</td>
		</s:if>
		<td align="center">
			<input type="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th>选择</th>
		<th align="center" >账户号</th>
		<th align="center" >账户真实姓名</th>
		<s:if test="#request.sourcePage==null || #request.sourcePage!='address'">
		<th align="center" >证件号码</th>
		</s:if>
		<th align="center" >手机号码</th>
		<s:if test="#request.sourcePage==null || #request.sourcePage!='address'">
		<th>账户可用金额</th>
		</s:if>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	     <td>
	     <input type="button"  value="选择" onclick="selectOneAccount(<s:property value='n_AccountId'/>,'<s:property value="accountLogin"/>','<s:property value="name"/>',<s:property value="amountAvlibal"/>,<s:property value='n_LoginId'/>)" />
	     </td>
		<td align="center">
		     <s:property value="accountLogin"/>
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<s:if test="#request.sourcePage==null || #request.sourcePage!='address'">
			<td align="center">
			     <s:property value="acconutId"/>
			</td>
		</s:if>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<s:if test="#request.sourcePage==null || #request.sourcePage!='address'">
			<td align="center">
			       <s:property value="%{formatDouble(amountAvlibal)}"/>
			</td>
		</s:if>
	</tr>
	</s:iterator>
</table>

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'accountInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>

