<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回复信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" >
       $(document).ready(function(){
          $("#nwesCsReplyAddForm").validate({
               rules: {
					"nwesCsReply.replyContent": {required:true,maxlength:165,unusualChar:true},
					"nwesCsReply.replyRdate": {required:true}
	        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });  
	function showFrozenNumber(val){
		 if(val==1){
		     $("#frozenRow").hide();
		 }else if(val==2){
		      $("#frozenRow").show();
		 }
	}  
</script>
</head>
<body >
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户信息'" scope="request"/>
<s:set name="name" value="'回复信息'" scope="request"/>
<s:set name="son_name" value="'回复'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form id="nwesCsReplyAddForm" action="customService_addReply.action" method="post"  namespace="/nwesCustomService" >
<!-- 数据编辑区域 -->
<s:token/>
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="2" align="left" class="edit_title">回复信息</td>
	 <input type="hidden" name="nwesCustomService.customServiceId"  id="customServiceId" value="<s:property value='nwesCustomService.customServiceId'/>"/>
	</tr>	
	  <tr> 
		<td width="15%" align="right"><font color="red">*</font>服务方式：</td>
		<td width="80%"> 
			<select name="customServiceType" disabled="disabled" id="customServiceType">
			   <option <s:if test="nwesCustomService.customServiceType==0">selected="selected"</s:if> value='0'>在线客服</option>
			    <option <s:if test="nwesCustomService.customServiceType==1">selected="selected"</s:if>  value='1'>电话客服</option>
			    <option <s:if test="nwesCustomService.customServiceType==2">selected="selected"</s:if>  value='2'>留言</option>     
			  </select>
			</td>
		</tr>
		<tr> 
		<td width="15%" align="right"><font color="red">*</font>服务类别：</td>
			<td>			
			 <select name="customServiceMode" disabled="disabled" id="customServiceMode">
			   <option <s:if test="nwesCustomService.customServiceMode==0">selected="selected"</s:if> value='0'>咨询</option>
			   <option <s:if test="nwesCustomService.customServiceMode==1">selected="selected"</s:if>  value='1'>售后</option>
			   <option <s:if test="nwesCustomService.customServiceMode==2">selected="selected"</s:if>  value='2'>投诉</option>
			         
			    </select>
			</td>
		</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>咨询内容：</td>
		<td width="80%"> 
		<textarea  name="content" disabled="disabled" id="content" rows="12" cols="50">
		<s:property value='nwesCustomService.content'/></textarea>
	    </td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>咨询时间：</td>
		<td width="80%"> 
		<input disabled="disabled" name="nwesCustomService.customServiceDate" id="customServiceDate" value="<s:date   name='nwesCustomService.customServiceDate'  format='yyyy-MM-dd'/>"
		 type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>回复内容：</td>
		<td width="80%"> 
		<textarea  name="nwesCsReply.replyContent" id="replyContent" rows="12" cols="50">
		<s:property value='nwesCsReply.replyContent'/></textarea>
	    </td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
<br><br>

</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/nwesCustomService/customService_pageList.action";
}
</SCRIPT>
</BODY>
<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>