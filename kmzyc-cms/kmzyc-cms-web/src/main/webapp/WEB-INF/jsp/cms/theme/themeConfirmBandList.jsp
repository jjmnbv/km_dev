<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>产品信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/dataList.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>

		<script type="text/javascript">
	
	   
           function gotoList(id){
 	var pageForm= window.document.getElementById("windowDataForm");
 	pageForm.action="/cms/cmsThemeAction_gotoBandList.action";
 	pageForm.submit();
 
 }

		</script>
		
	</head>
	<body >
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'数据管理'" scope="request" />
		<s:set name="son_name" value="'绑定数据确认'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		
		<s:form name="windowDataForm"  id="windowDataForm"  onsubmit="return isCheck('dataIds');"
			action="/cms/cmsThemeAction_addBandData.action" method="post">
			<s:token></s:token>
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<INPUT class="saveBtn" TYPE="submit" value=""/>
            			&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onclick="deleteSelected('dataIds');" />
						
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<input type="hidden" name="cmsTheme.themeId" id="themeId" value="<s:property value='cmsTheme.themeId' />">
						<input type="hidden" name="cmsThemeTemplate.themeId" id="cmsThemeTemplate.themeId" value="<s:property value='cmsTheme.themeId' />">
					   <input class="backBtn"  onclick="gotoList()" type="button" value="">
					</td>
				</tr>
			
			<tr>  
				<td colspan="4"  align="center" >
				    
				</td>
			</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				<tr class="dataName">
					<th width="5%">
						<input type='checkbox' name='data'  onclick="checkAll(this,'dataIds');">
					</th>
				<th align="center">编号</th>
				<th align="center">名称</th>
				<th align="center">主题</th>
				<th align="center">类型</th>
					<th width="60">操作</th>
				</tr>
				<s:iterator id="cmsTemplate"  value="templateList">
					<tr  class='dataTr'>
					     <td>
						     <s:if test="flag!=1"><input  type='checkbox' name='dataIds' value='<s:property value="templateId"/>' onclick="checkpromotionId(this);">
						     </s:if>
					     </td>
						<td align="center">
						     <s:property value="templateId"/>
						</td>
							<td align="center">
						     <s:property value="name"/>
						</td>
						<td align="center">
						     <s:property value="theme"/>
						</td>
					
						<td align="center">
						   
						
						 
						   
						   	
		   <s:if test="type==13">窗口变量模板</s:if>
		   <s:if test="type==12">页面变量模板</s:if>
						  
						</td>
						<td align="center">
						    <img title='删除' style='cursor: pointer;' src='/etc/images/icon_delete.png'   onclick='deleteByKey(this);' />
						</td>
					
					</tr>
					
	       </s:iterator>
			</table>
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

