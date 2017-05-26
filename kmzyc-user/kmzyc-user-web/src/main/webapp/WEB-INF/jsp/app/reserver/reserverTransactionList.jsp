<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>

</head>
<body>

<s:set name="parent_name" value="'预备金管理'" scope="request" />
<s:set name="name" value="'预备金交易列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 查询条件区域 -->
<s:form name="reserverTransactionForm"  action="/userInfo/reserverTransaction_PageList.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">用户名:</td>
		<td><input name="bnesAcctTransactionQuery.accountLogin" type="text" value='<s:property value="bnesAcctTransactionQuery.accountLogin"/>'></td>
		<td align="right" >交易状态:</td>
		<td style="padding-left: 2px"><select name="bnesAcctTransactionQuery.status" style="width:75">
		<option value="" <s:if test='bnesAcctTransactionQuery.status==""'>selected="selected"</s:if>>所有</option>
		<option value="0" <s:if test='bnesAcctTransactionQuery.status=="0"'>selected="selected"</s:if>>未付款</option>
		<option value="1" <s:if test='bnesAcctTransactionQuery.status=="1"'>selected="selected"</s:if>>成功</option>
		<option value="2" <s:if test='bnesAcctTransactionQuery.status=="2"'>selected="selected"</s:if>>失败</option></select></td>
		<td align="right">交易日期:</td>
		<td><input type="text" name="bnesAcctTransactionQuery.createDate"  id="d523" class="Wdate"  value ="<s:date name = 'bnesAcctTransactionQuery.createDate' format='yyyy-MM-dd HH:mm:ss' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		至<input type="text" name="bnesAcctTransactionQuery.endDate" id="d521" class="Wdate"  value ="<s:date name = 'bnesAcctTransactionQuery.endDate' format='yyyy-MM-dd HH:mm:ss' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" /></td>
			<td align="right">交易流水:</td>
		<td><input name="bnesAcctTransactionQuery.accountNumber" type="text" value='<s:property value="bnesAcctTransactionQuery.accountNumber"/>'></td>
		<td align="right"><INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;&nbsp;&nbsp;</td>	
    </tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >用户名</th>
		<th align="center" >交易流水</th>	
		<th align="center" >交易日期</th>		
		<th align="center" >交易金额</th>
		<th align="center" >交易类型</th>
		<th align="center" >交易状态</th>
	</tr>
  <s:iterator id="custiterator" value="page.dataList">
	<tr>
		<td align="center">
		  <s:property value="accountLogin" />
		</td>
		<td align="center">
		  <s:property value="accountNumber" />
		</td>
		<td align="center">
		<s:date name = "createDate" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td align="center">
			<s:if test="amount==0">
				<s:property value="%{formatDouble(amount)}"/>
			</s:if>
		    <s:else>
		    	<s:property value="%{formatDouble(-amount)}"/>
		    </s:else>
		</td>
		<td align="center">
		 <s:property value="content" />
		</td>
		<td align="center">
		  <s:if test="%{status==0}">
			    未付款
		  </s:if>
		  <s:elseif test="%{status==1}">
		  	    成功
		  </s:elseif>
		  <s:elseif test="%{status==2}">
		  	    失败
		  </s:elseif>
		</td>
		</tr>
	</s:iterator>
</table>
<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'reserverTransactionForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

