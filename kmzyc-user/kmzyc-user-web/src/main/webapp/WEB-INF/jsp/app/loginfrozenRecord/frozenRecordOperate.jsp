<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>解冻登录帐户</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"> 
//验证帐户号
   $(document).ready(function(){
          $("#bnesFrozenRecordAddForm").validate({
               rules: {
					"bnesFrozenRecord.thawReason": {required:true,maxlength:165,unusualChar:true}
	        	}
          });
        });
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户业务'" scope="request"/>
<s:set name="name" value="'解冻登录帐户'" scope="request"/>
<s:set name="son_name" value="'解冻'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form action="defrostingRecord_frozen.action" method="POST" id="bnesFrozenRecordAddForm"  namespace='/loginfrozenRecord' >
<s:token/>
<input name="bnesFrozenRecord.frozenRecordId"  type="hidden" value="<s:property value='bnesFrozenRecord.frozenRecordId'/>"  />
<input name="bnesFrozenRecord.loginId"  type="hidden" value="<s:property value='bnesFrozenRecord.loginId'/>"  />
<!-- hidden properties -->
<input type="hidden" name="showType" value="<s:property value='showType'/>">
<!-- 数据编辑区域 -->
<table width="80%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr>
	   <th colspan="2" align="left" class="edit_title">登录帐户解冻</th>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>登录帐户名：</td>
		<td width="80%"> 
		  <s:hidden name="bnesFrozenRecord.username"/>
		<s:property value="bnesFrozenRecord.username"/>
		</td>
	</tr>
	
	<%-- <tr> 
		<td width="20%" align="right"><font color="red">*</font>真实姓名：</td>
		<td width="80%"> 
		    <s:hidden name="bnesFrozenRecord.realName"/>
			<s:property value="bnesFrozenRecord.realName"/>
		</td>
	</tr> --%>
	 <tr> 
		<td width="20%" align="right"><font color="red">*</font>冻结操作账号：</td>
		<td width="80%"> <s:property value="bnesFrozenRecord.sueName"/>
		</td>
	</tr>
<%-- 	<tr> 
		<td width="20%" align="right"><font color="red">*</font>投诉人姓名：</td>
		<td width="80%"> 
			<s:property value="bnesFrozenRecord.sueRealName"/>
	</tr> --%>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>冻结日期：</td>
		<td width="80%"> 
			<s:date   name="bnesFrozenRecord.sueDate"  format="yyyy-MM-dd HH:mm:ss"/>
		</td>
	</tr>
<%-- 	<tr> 
		<td width="20%" align="right"><font color="red">*</font>投诉原因：</td>
		<td width="80%"> 
			<s:property value="bnesFrozenRecord.sueReason"/>
			
		</td>
	</tr> --%>	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>冻结原因：</td>
		<td width="80%"> 
			<s:property value="bnesFrozenRecord.frozenReason"/>
		</td>
	</tr>	
	<tr>
	<td width="20%" align="right"><font color="red">*</font>解冻原因：</td>
		<td width="80%"> 
			<textarea id="thawReason"  name="bnesFrozenRecord.thawReason" rows="5"  cols="30"><s:property value='bnesFrozenRecord.thawReason'/>
			</textarea>
		</td>
	</tr>		
</table>
<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit" value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList(<s:property value='showType'/>)" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(val){
    if(val!=null){ 
      window.close();
    }else{
     location.href="/loginfrozenRecord/frozenRecord_pageList.action";
    }
   
}

</SCRIPT>
</BODY><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>