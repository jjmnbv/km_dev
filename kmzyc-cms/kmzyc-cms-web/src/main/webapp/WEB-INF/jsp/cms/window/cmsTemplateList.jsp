<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>模板管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
		 	$("#templateForm").validate({
		         rules: {
						"cmsTemplate.name": {maxlength:42}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
		 });
		</script>
	<style type="text/css">
		.content_table
		{
			margin-left:20px;
		}
	</style>
	</head>
	<body >
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="templateForm" name="templateForm" id="templateForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsWindowAction_templateSel.action" method="post">
			<table width="98%" align="center" height="50" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%">
						模板名称:<input type="text" name="cmsTemplate.name" value="<s:property value='cmsTemplate.name'/>">
					</td>
					<td>
						类型:<select name="cmsTemplate.type">
								<option value="-1" <s:if test="cmsTemplate.type==-1">selected="selected"</s:if>>全部模板</option>
								<option value="0" <s:if test="cmsTemplate.type==0">selected="selected"</s:if>>窗口模板</option>
								<option value="1" <s:if test="cmsTemplate.type==1">selected="selected"</s:if>>页面模板</option>
								<option value="3" <s:if test="cmsTemplate.type==3">selected="selected"</s:if>>活动模板</option>
							</select>
					</td>
					<!--<td>
						状态:<select name="cmsTemplate.status">
								<option value="-1" <s:if test="cmsTemplate.status==-1">selected="selected"</s:if>>全部</option>
								<option value="0" <s:if test="cmsTemplate.status==0">selected="selected"</s:if>>有效</option>
								<option value="1" <s:if test="cmsTemplate.status==1">selected="selected"</s:if>>无效</option>
							</select>
					</td>
					--><td><input type="submit" class="queryBtn" value=""/></td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th>模板主键</th>
					<th>模板名称</th>
					<th>模板主题</th>
					<th>类型</th>
					<th>修改日期</th>
					<th>状态</th>
					<th width="60">操作</th>
				</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td><s:property value="templateId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="theme"/></td>
					<td><s:if test="type==0">窗口模板</s:if><s:if test="type==1">页面模板</s:if></td>
					<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><s:if test="status==1">无效</s:if><s:if test="status==0">有效</s:if></td>
					<td>
						<img title="选择" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='selTempate(<s:property value="templateId"/>)' />
					</td>	
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'templateForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
			
		
		</div>
	</body>
	<script type="text/javascript">
		//模板选择
		function selTempate(id)
		{
			$.ajax({
				url:"/cms/cmsWindowAction_selTemplate.action?ajax=yes",
				data:"windowId="+id,
				type:"post",
				success:function(result){
					parent.closeOpenDialog(result)
				},
				error:function(){
					alert("出错了");
				}
			});
		}
	</script>
</html>

