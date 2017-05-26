<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维护记录</title>
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
          $("#nwesMaintenaceAdd").validate({
               rules: {
					"loginName": {required:true},
					"question":{required:true,maxlength:165,unusualChar:true},
					"maintenaceSchedule":{required:true,maxlength:165,unusualChar:true},
					"maintenaceDate":{required:true,afterDate:"#maintenaceDate"}
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
	    $("#customerTypeId").val(sonCustomerId);
	     $("#loginName").val(name);
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
<s:set name="name" value="'维护记录'" scope="request"/>
<s:if test="nwesMaintenace.maintenaceId==null">
<s:set name="son_name" value="'添加'" scope="request"/>
</s:if><s:else>
<s:set name="son_name" value="'修改'" scope="request"/>
</s:else>

<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form id="nwesMaintenaceAdd" action="maintenace_add.action" method="post"  namespace="/nwesMaintenace" >
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
		<td colspan="2" align="left" class="edit_title">维护记录信息</td>
		  <input type="hidden" name="nwesMaintenace.maintenaceId"  id="maintenaceId" value="<s:property value='nwesMaintenace.maintenaceId'/>"/>
	      <input type="hidden"  id="customerTypeId" name="nwesMaintenace.customerTypeId"   value="<s:property value='nwesMaintenace.customerTypeId'/>"/>	
	</tr>
   <!-- 
   <tr> 
		<td width="20%"  align="right"><font color="red">*</font>调查人：</td>
		<td width="80%" > 
		  <input type="text" id="loginName" name="loginName"  value="<s:property value='nwesMaintenace.name'/>" />
		  <input type="button"  value="选中" onclick="popUpAccount()"/>
		  <input type="hidden" name="loginId" id="loginId" value="<s:property value='nwesMaintenace.loginId'/>"/>
		</td>
	</tr>
 -->	
	<tr> 
		<td width="15%" align="right"><font color="red">*</font>反馈人姓名：</td>
		<td width="80%"> 
		  <input type="text" readonly="readonly" id="loginName" name="loginName"  value="<s:property value='nwesMaintenace.loginName'/>" />
		  <input type="button"  value="选中" onclick="popUpUserInfo()"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>反馈问题：</td>
		<td width="80%"> 
		<textarea  name="question" id="question" rows="12" cols="50"><s:property value='nwesMaintenace.question'/></textarea>
	    </td>
	</tr>
    <tr> 
		<td width="20%" align="right"><font color="red">*</font>维护信息：</td>
		<td width="80%"> 
		<textarea  name="maintenaceSchedule" id="maintenaceSchedule" rows="12" cols="50"><s:property value='nwesMaintenace.maintenaceSchedule'/></textarea>
		</td>
	</tr>
	  <tr> 
        <td width="20%"   align="right"><font color="red">*</font>维护日期：</td>
		<td width="80%"> 
			<input name="maintenaceDate" id="maintenaceDate" 
			value="<s:date   name='nwesMaintenace.maintenaceDate'  format='yyyy-MM-dd'/>" 
			type="text" readonly  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
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
    location.href="/nwesMaintenace/maintenace_pageList.action";
}
</SCRIPT>
</BODY><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>