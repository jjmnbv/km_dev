<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		
		<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>
		<script type="text/javascript">
		
 		function goBack()
 		{
 			var pageForm= window.document.getElementById("templateDetailForm");
 			var type=$("#templateDetailType").val();
 			if(type==13||type==12){
 				pageForm.action="/cms/cmsTemplateAction_queryVarTemplate.action";
 				
 			}else{
 				
 				pageForm.action="/cms/cmsTemplateAction_queryTemplate.action";
 			}
 			
 			pageForm.submit();
		}
		</script>
	<style type="text/css">
		.detail_table tr td
		{
			text-align: left;
		}
		.detail_table tr td span
		{
			font-weight: bold;
		}
	</style>
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'模板详情'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;" >
		<s:form id="templateDetailForm" class="templateDetailForm" name="templateDetailForm" onsubmit="return checkAllTextValid(this)" action="/cms/" method="post">
			<!-- keyWords -->
			<input type="hidden" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
			<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
			<input type="hidden" name="keyWords.theme_keyword" value="<s:property value='keyWords.theme_keyword'/>">
			<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
			<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<input type="hidden" name="keyWords.type_keyword" value="<s:property value='keyWords.type_keyword'/>">
			<input type="hidden" name="" id="templateDetailType" value="<s:property value="cmsPageDetail.cmsTemplate.type"/>"/>
			<table width="95%" class="detail_table list_table" cellpadding="3" align="center">
			<tr> 
				<th colspan="2" align="left" class="edit_title">模板信息</th>
			</tr>
			
				<tr>
					<td colspan="2"><span>模板编号:</span><s:property value="cmsPageDetail.cmsTemplate.templateId" /> </td>
				</tr>
				<tr>
					<td><span>模板名称:</span><s:property value="cmsPageDetail.cmsTemplate.name" /></td>
					<td><span>模板主题:</span><s:property value="cmsPageDetail.cmsTemplate.theme" /></td>
				</tr>
				<tr>
					<td><span>模板类型:</span><s:if test="cmsPageDetail.cmsTemplate.type==0">窗口模板</s:if><s:if test="cmsPageDetail.cmsTemplate.type==1">页面模板</s:if>
					</span><s:if test="cmsPageDetail.cmsTemplate.type==3">活动模板</s:if><s:if test="cmsPageDetail.cmsTemplate.type==4">资讯模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==5">帮助模板</s:if><s:if test="cmsPageDetail.cmsTemplate.type==6">移动端模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==9">商家入驻模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==7">窄版静态专题页模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==8">宽版静态专题页模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==10">广告模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==11">招商帮助模板</s:if>
					<!--  <s:if test="cmsPageDetail.cmsTemplate.type==15">抽奖模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==16">抽奖窗口模板</s:if>-->
					<s:if test="cmsPageDetail.cmsTemplate.type==17">招商入驻</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==18">合作模式</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==19">商家规则</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==20">商家店铺</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==21">活动中心</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==22">商户服务</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==23">活动中心帮助模板</s:if>
					<s:if test="cmsPageDetail.cmsTemplate.type==24">中药城帮助中心模板</s:if>
				
					
					
					
			
					</td>
					<td><span>状态:</span><s:if test="cmsPageDetail.cmsTemplate.status==0">有效</s:if><s:if test="cmsPageDetail.cmsTemplate.status==1">无效</s:if></td>
				</tr>
				<tr>
					<td><span>创建人:</span><s:property value="cmsPageDetail.user_Cre" /></td>
					<td><span>创建时间:</span><s:date name="cmsPageDetail.cmsTemplate.createDate"/> </td>
				</tr>
				<tr>
					<td><span>修改人:</span><s:property value="cmsPageDetail.user_Mod" /></td>
					<td><span>修改时间:</span><s:date name="cmsPageDetail.cmsTemplate.modifyDate"/></td>
				</tr>
				<tr> 
					<td colspan="2"><span>备注:</span><s:property value="cmsPageDetail.cmsTemplate.remark" /></td>
				</tr>
			</table>
			<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
			<tr> 
				<td width="20%" align="right"><font color="red">*</font>模板内容：</td>
				
				<td width="80%"> 
					当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
				</td>
			</tr>
			<tr> 
				<td colspan="2">
					<textarea   name="cmsPageDetail.cmsTemplate.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmsPageDetail.cmsTemplate.content"/></textarea>
				</td>
			</tr>
				
			</table>
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<input class="backBtn"  onclick="goBack()" type="button" value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		
		</s:form>
			
		</div>
	</body>
</html>

