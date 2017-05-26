<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>详细信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" >
       $(document).ready(function(){
          $("#nwesCsReplyAdd").validate({
               rules: {
					"replyContent": {required:true},
					"replyRdate": {required:true}
	        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
        });  
	   //弹出 选择账号层
	function popUpUserInfo() {
	    dialog("选择会员账号","iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo" ,"900px","500px","iframe");
	}
	//关闭弹出窗口 
	function closeOpenUserInfo(accountId,account,name,sonCustomerId,customerTypeName){
	    closeThis();
	     $("#loginName").val(name);
	      $("#customerTypeId").val(sonCustomerId);
	      $("#customerTypeName").val(customerTypeName);
	      
	}
	
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
<s:set name="name" value="'详细信息'" scope="request"/>
<s:set name="son_name" value="'详细'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form id="nwesCsReplyAddForm" action="customService_addReply.action" method="post"  namespace="/nwesCustomService" >
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="2" align="left" class="edit_title">详细信息</td>	
	</tr>	
	<tr> 
		<td width="15%" align="right"><font color="red">*</font>客户姓名：</td>
		<td width="80%"> 
		  <s:property value="nwesCustomService.loginName"/>
		</td>
	 </tr>
	 <tr> 
		<td width="15%" align="right"><font color="red">*</font>回复人：</td>
		<td width="80%"> 
		  <s:property value="nwesCustomService.loginId"/>
		</td>
	 </tr>
	 <tr> 
		<td width="15%" align="right"><font color="red">*</font>服务方式：</td>
		<td width="80%"> 
				<s:if test="nwesCustomService.customServiceType==0">在线客服</s:if> 
			    <s:if test="nwesCustomService.customServiceType==1">电话客服</s:if>
			    <s:if test="nwesCustomService.customServiceType==2">留言</s:if> 
		</td>
	</tr>
	<tr> 
		<td width="15%" align="right"><font color="red">*</font>服务类别：</td>
			<td>
			 	<s:if test="nwesCustomService.customServiceMode==0">咨询</s:if> 
			    <s:if test="nwesCustomService.customServiceMode==1">售后</s:if>
			    <s:if test="nwesCustomService.customServiceMode==2">投诉</s:if> 
			</td>
		</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>咨询内容：</td>
		<td width="80%"> 
		<s:property value="nwesCustomService.content"/>
	    </td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>咨询时间：</td>
		<td width="80%"> 
		<s:date   name="nwesCustomService.customServiceDate" format="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>回复内容：</td>
		<td width="80%"> 
		<s:property value="nwesCsReply.replyContent"/>
	    </td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>回复时间：</td>
		<td width="80%"><s:date   name="nwesCsReply.replyRdate"  format="yyyy-MM-dd"/>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		
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
</HTML>