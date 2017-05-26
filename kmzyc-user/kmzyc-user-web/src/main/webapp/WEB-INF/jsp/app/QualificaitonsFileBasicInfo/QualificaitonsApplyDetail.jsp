<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改采购商管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"type="text/css" />
			<link type="text/css" href="/etc/js/ueditor/themes/default/css/ueditor.css"/>
		
</script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js">
</script>
	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.config.js">  </script>
	 	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.all.js">  </script>
 

		<script type="text/javascript">
 
   
 


</script>
	</head>

	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'采购商管理'" scope="request" />
		<s:set name="name" value="'采购资格申请管理'" scope="request" />
		<s:set name="son_name" value="'详情'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<s:form action="/qualificaitonsFile/qualificaitonsFile_qualificaitonsFileEdit.action" id="qualificaitonsFileEditForm"
				method="POST" namespace='qualificaitonsFileEditForm'  name="qualificaitonsFileEditForm">
				<s:token/>
				<!-- 数据编辑区域 -->
				<table width="80%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
					<!-- error message -->
					<s:if test="rtnMessage != null">
						<tr>
							<td colspan="2" align="center">
								<font color="red"><s:property value='rtnMessage' /> </font>

							</td>
						</tr>
					</s:if>
					
						<tr>
						<td width="20%" align="right">
							<font color="red">*</font>用户名：
						</td>
						<td width="80%">
						 <s:property value='qualificationsApplyDO.userName' />

						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>申请类型：
						</td>
						<td width="80%">
			
               <s:if test='applyTypes==1'>
						<INPUT type="checkbox" name="qualificationName"  checked="checked"     disabled="disabled" value="1" >OTC </input>
						
						</s:if>
						
						<s:else>
						<INPUT type="checkbox" name="qualificationName"      disabled="disabled"  value="1" >OTC </input>
						</s:else>
				 
						   <s:if test='applyTypes1==2'>
							<INPUT type="checkbox" name="qualificationName"  checked="checked"   disabled="disabled"value="2" >医疗器械</input>
							</s:if>
								<s:else>
										<INPUT type="checkbox" name="qualificationName"    disabled="disabled" value="2" >医疗器械</input>
								</s:else>
							 <s:if test='applyTypes2==3'>
							<INPUT type="checkbox" name="qualificationName" checked="checked"    disabled="disabled" value="3" >处方药</input>
							</s:if>
							<s:else>
							<INPUT type="checkbox" name="qualificationName"   disabled="disabled"   value="3" >处方药 </input>
							</s:else>
						 

						</td>
					</tr>
							<tr>
						<td width="20%" align="right">
							<font color="red">*</font>电话：
						</td>
						<td width="80%">
						 		 <s:property value='qualificationsApplyDO.tel' /> 
						</td>
					</tr>
					
							<tr>
						<td width="20%" align="right">
							<font color="red">*</font>资质文件：
						</td>
						<td width="80%">
						<s:iterator value="#session.list" >
		<a href="<s:property value='QualificaitonsFileImage'/><s:property value='fileUrl'/>" title="单击放大" target="_blank" id="hrefId101"><img title="资质文件" width="100" height="100" style="cursor: pointer;" src="<s:property value='QualificaitonsFileImage'/><s:property value='fileUrl'/>" />
		
		 查看源文件</a>       		</s:iterator>
						</td>
					</tr>
					
							<TR>
					 <td width="20%" align="right"><font color="red">*</font>申请原因：</td>
 	 	 
 	 	 <td width="80%">
 	 	   <s:property value='qualificationsApplyDO.applyReasons'/>
 	 	 </td>
					</TR>
			 
			 
					<tr  id="smsTextId">
						<td width="20%" align="right">
							<font color="red">*</font>备注：
						</td>
						<td width="80%">
						
						  <s:property value='qualificationsApplyDO.remarks'/>
 						</td>
					</tr>
			 
			
					 

				 
				 
					
				</table>


				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
				  
							<input class="backBtn" onclick="gotoList()" type="button"
								value=" ">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>

				<br>
				<br>

			</s:form>
		</div>
		<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/qualificaitonsApply/qualificaitonsApply_pageList.action";
}

 



</SCRIPT>
	</BODY>
</HTML>