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
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">



</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'邮件发送查询'" scope="request" />
<s:set name="name" value="'邮件发送查询'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form  name="mailQueryForm" action="/emailmsg/queryMailSendList.action" onsubmit="return checkAllTextValid(this)" method="post">
<!-- 查询条件区域 -->
<table  width="98%"  class="content_table" align="center" cellpadding="0" cellspacing="0" >
	
	<tr>
		<td align="right">邮箱： </td>
		<td>
		     <input name="mailSendTask.receiver" type="text" value="<s:property value='mailSendTask.receiver'/>">
		</td>
		<td align="right">邮件类型：</td>
		<td>
		  <select name="mailSendTask.templateType">
				<option value="" <s:if test='mailSendTask.templateType==""'>selected="selected"</s:if>>
					请选择
				</option>
				<s:iterator id="mailTemplate" value="mailTemplateMap">
				<option value="<s:property value="#mailTemplate.key"/>" <s:if test='mailSendTask.templateType==#mailTemplate.key'>selected="selected"</s:if>>
					 <s:property value="value"/>
				</option>
				</s:iterator>
	      </select>
		</td>
		<td align="right">发送时间从</td>
		<td>
		<input type="text" id="d523" class="Wdate"  value ="<s:date name = 'mailSendTask.sendTime' format='yyyy-MM-dd HH:mm:ss' />"    name="mailSendTask.sendTime"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
		<td align="right">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>
			<input type="text" id="d524" class="Wdate"  value ="<s:date name = 'mailSendTask.createTime' format='yyyy-MM-dd HH:mm:ss' />"    name="mailSendTask.createTime"  onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
		<td align="right">状态：</td>
		<td>
			<select name="mailSendTask.isSuccess">
				<option value="" <s:if test='mailSendTask.isSuccess==""'>selected="selected"</s:if>>
					请选择
				</option>
				<option value="0" <s:if test='mailSendTask.isSuccess==0'>selected="selected"</s:if>>
					 失败
				</option>
				<option value="1" <s:if test='mailSendTask.isSuccess==1'>selected="selected"</s:if>>
			  		 成功
				</option>
	      </select>
	      <td>
		</td>
	</tr>
	<tr>
		<td align="right">发送次数： </td>
		<td>
		     <input name="mailSendTask.sendCount" type="text" value="<s:property value='mailSendTask.sendCount'/>">
		</td>
		<td  colspan="8" align="right">
			<input type="submit" class="queryBtn" value="" >
		</td>
	</tr>
		
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <%--<th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_AccountIds')">
		</th> --%>
		<th align="center" width="20%" >邮箱</th>
		<th align="center" width="20%">邮件类型</th>
		<th align="center" width="20%">发送时间</th>
		<th align="center" width="8%">发送次数</th>
		<th align="center" width="12%">状态</th>
	</tr>
	<s:iterator id="mailiterator"  value="page.dataList">
	<tr>
		<td align="center">
		     <s:property value="receiver"/>
		</td>
		<td align="center">
			<s:iterator id="templateStatus"  value="mailTemplateMap">
				<s:if test='#mailiterator.templateType==#templateStatus.key'><s:property value="#templateStatus.value"/></s:if>
			</s:iterator>
		</td>
		<td align="center">
		     <s:date  name="sendTime"  format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td align="center">
		     <s:property value="sendCount"/>
		</td>
		<td align="center">
		   <s:if test="#mailiterator.isSuccess==0">失败</s:if>
		   <s:if test="#mailiterator.isSuccess==1">成功</s:if>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'mailQueryForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
<!-- 消息提示页面 -->
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</div>
</body>
</html>

