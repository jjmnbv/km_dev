<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>记录列表</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'应用日志'" scope="request" />
		<s:set name="name" value="'操作记录'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="cmsLogForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsLogAction_queryForPage.action" method="post">
			<table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>	
				<td align="left" width="22%" >
						操作人：
						<input name="cmsLog.userName" type="text"  
							value="<s:property value="cmsLog.userName"/>">
					</td >
					<td align="left" width="22%" >
					      操作模块：
						<input name="cmsLog.moduleName" type="text"  
							value="<s:property value="cmsLog.moduleName"/>">
					</td >
				   <td align="left" width="20%">
						开始时间:<input name="cmsLog.consoleOperatorDate" type="text" readonly value="<s:date name="cmsLog.consoleOperatorDate" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="left" width="20%">
						结束时间:<input name="cmsLog.endDate" type="text" readonly value="<s:date name="cmsLog.endDate" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th>操作时间</th>
					<th>操作人</th>
					<th>操作类型</th>
					<th>操作模块</th>
					<th>操作内容</th>
				</tr>
			<s:iterator id="cmsLog" value="page.dataList">
				<tr>
				<TD><s:date name="consoleOperatorDate" format="yyyy-MM-dd HH:mm:ss"/></TD>
				<TD><s:property value="userName"/></TD>
				<TD>
				<s:if test="type==1">
				新建
				</s:if>
				<s:if test="type==2">
				修改
				</s:if>
				<s:if test="type==3">
				删除
				</s:if>
				<s:if test="type==4">
				绑定
				</s:if>
				</TD>
				
				<TD>
				<s:property value="moduleName"/>
				</TD>
				<TD><s:property value="moduleContent"/></TD>
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'cmsLogForm'" scope="request"></s:set>
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

