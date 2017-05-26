<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关注信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>

</head>
<body>
<s:set name="parent_name" value="'客户活动信息'" scope="request" />
<s:set name="name" value="'关注信息'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
  <div  style="height:90%;overflow-y:auto; " >
<s:form name="followForm" action="/userInfo/follow_pageList.action" method="post">
<!-- 查询条件区域 -->
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'followIds')">
		</th>
		<th align="center" >客户账号</th>
		<th align="center" >客户名称</th>
		<th align="center" >商品名称</th>
		<th align="center" >商品价格</th>
		<th align="center" >商品图片</th>
		<th align="center" >商品URL</th>
		<th align="center" >浏览时间</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="followIds"  value='<s:property value="followId"/>' />
		</td>
		<td align="center">
		     <s:property value="followId"/>
		</td>
		<td align="center">
		   <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="followContent"/>
		</td>
		<td align="center">
		     <s:property value="followURL"/>
		</td>
		<td align="center">
		<s:date name="followDate" format="yyyy-MM-dd"/>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'followForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>

</body>
</html>

