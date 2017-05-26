<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改资质文件</title>
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
 
   
		 $(document).ready(function(){
			 
		     $("#qualificaitonsFileEditForm").validate({
	             rules: {
						 
						"qualificaitonsFileDO.fileName":{required:true}
					 
		        	},
		         success: function (label){
		            label.removeClass("checked").addClass("checked");
		            }
	        });
			 });


</script>
	</head>

	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'采购商管理'" scope="request" />
		<s:set name="name" value="'资质文件管理'" scope="request" />
		<s:set name="son_name" value="'修改'" scope="request" />
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
						 <input type="hidden" name="qualificaitonsFileDO.id" id="id"
								value="<s:property value='qualificaitonsFileDO.id' />" />
							<input type="text" name="qualificaitonsFileDO.userName" id="userName"  disabled="disabled"
								value="<s:property value='qualificaitonsFileDO.userName' />" />

						</td>
					</tr>
							<tr>
						<td width="20%" align="right">
							<font color="red">*</font>文件名：
						</td>
						<td width="80%">
						 		<input type="text" name="qualificaitonsFileDO.fileName" id="fileName"
								value="<s:property value='qualificaitonsFileDO.fileName' />" />
						</td>
					</tr>
					
							<tr>
						<td width="20%" align="right">
							<font color="red">*</font>资质文件：
						</td>
						<td width="80%">
					<a href="<s:property value='qualificaitonsFileDO.fileUrl'/>" title="单击放大" target="_blank" id="hrefId101">	<img title="资质文件" width="160" height="155" style="cursor: pointer;" src="<s:property value='qualificaitonsFileDO.fileUrl'/>" />
		
		查看源文件</a>
		
						</td>
					</tr>
					
							<TR>
					 <td width="20%" align="right"><font color="red">*</font>有效开始时间：</td>
	 <td>	<input type="text" name="qualificaitonsFileDO.beginDate"  id="d523" class="Wdate"  value ="<s:date name = 'qualificaitonsFileDO.beginDate' format='yyyy-MM-dd ' />"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',maxDate:'#F{$dp.$D(\'d521\')}'})"  /> 
		 
					</td>
					</TR>
					
							<TR>
					 <td width="20%" align="right"><font color="red">*</font>有效结束时间：</td>
	 <td>	 
	 <input type="text" name="qualificaitonsFileDO.endDate" id="d521" class="Wdate"  value ="<s:date name = 'qualificaitonsFileDO.endDate' format='yyyy-MM-dd' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd ',minDate:'%y-%M-{%d+1}'})" /> 	
					</td>
					</TR>
					
			
					<tr  id="smsTextId">
						<td width="20%" align="right">
							<font color="red">*</font>删除状态：
						</td>
						<td width="80%">
						
								<select name="qualificaitonsFileDO.deleted">
									 
								<option value="0" <s:if test='qualificaitonsFileDO.deleted=="0"'>selected="selected"</s:if>>
										未删除
									</option>
									<option value="1" <s:if test='qualificaitonsFileDO.deleted=="1"'>selected="selected"</s:if>>
									  已删除
									</option>
								 
							</select>
 						</td>
					</tr>
			 
			
					 

				 
				 
					
				</table>


				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input class="saveBtn" type="submit" value=" ">
							&nbsp;&nbsp;
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
    location.href="/qualificaitonsFile/qualificaitonsFile_pageList.action";
}

</SCRIPT>
	</BODY>
</HTML>