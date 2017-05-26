<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>最近登录信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>

</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'客户成长'" scope="request" />
<s:set name="name" value="'最近登录'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="latestLoginForm" onsubmit=" return checkAllTextValid(this)"
 action="/userInfo/latestLogin_pageList.action" method="post">
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="latestLogin.loginAccount" type="text" value="<s:property value='latestLogin.loginAccount'/>">
		</td>
		<td align="right">IP地址：</td>
		<td>
		     <input name="latestLogin.loginIp" type="text" value="<s:property value='latestLogin.loginIp'/>">
		</td>
		<td align="right">登录时间：</td>
		<td>
		<input type="text" id="d523" class="Wdate" readonly value="<s:date name = 'latestLogin.d_DateStart' format='yyyy-MM-dd HH:mm:ss' />" name="latestLogin.d_DateStart" onClick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d524\')}'})" />
						  		至
		<input type="text" id="d524" class="Wdate" readonly value="<s:date name = 'latestLogin.d_DateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="latestLogin.d_DateEnd" onClick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}',maxDate:'%y-%M-%d %H:%m:%s'})" />
					
		</td>
		
		<td align="right">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	   <%--<th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_Ids')">
		</th> --%> 
		<th align="center" width="15%">会员账号</th>
		<th align="center" width="50%">登录模块</th>
		<th align="center" width="20%">IP地址</th>
		<th align="center" width="15%">登录时间</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	  <%--  <td align="center" width="5%">
		    <input type="checkbox"  name="n_Ids"  value='<s:property value="n_Id"/>' />
		</td>--%>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		
		<td align="center">
		<s:if test="loginModule=='康美之恋商城：登录'">
		     登录康美商城
		</s:if>
		<s:else>
			<s:property value="loginModule"/>
		</s:else>
		</td>
		<td align="center">
		   <s:property value="loginIp"/>
		</td>
		<td align="center">
		<s:date name="d_Date" />
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'latestLoginForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>

