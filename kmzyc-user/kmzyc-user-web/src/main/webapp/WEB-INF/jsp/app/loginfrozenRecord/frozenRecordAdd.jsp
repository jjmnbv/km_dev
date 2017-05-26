<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加冻结信息</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
	<script src="/etc/js/dialog.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
	<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
	<Script src="/etc/js/97dater/WdatePicker.js"></Script>
	</head>
	<script type="text/javascript"> 
	   $(document).ready(function(){
          $("#bnesFrozenRecordAddForm").validate({
               rules: {
					"username": {required:true,checkStatus:true},
					"sueName": {required:true,checkUsername:true},
					"frozenReason":{required:true,maxlength:165,unusualChar:true}
	        	}
          	});
        });
	   jQuery.validator.addMethod("checkUsername", function(value, element) {
	 		return value!=$("#username").val();
		}, "请不要选择相同的登录账户");
		
	   jQuery.validator.addMethod("checkStatus", function(value, element) {
	 	 	var rows = 0;
	 	 	var id=$("#loginId").val(); 
	 			$.ajax({
	 				async:false,
	 				url:"frozenRecord_checkStatus.action",
	 				type:"POST",
	 				data:"loginIn_Id=" + id,
	 				dataType:"json",
	 				success:function(json){
	 						rows = json;
	 				}
	 			});
	 			if(rows==1){
	 			return true;
	 			
	 			}else{
	 			 return false;
	 	 		}	
		}, "登录账户已冻结");
		
		jQuery.validator.addMethod("checkSueDate", function(value, element) {
	       	var d=new Date(Date.parse(value.replace(/-/g, "/")));
	    	var curDate=new Date();
	        return d>curDate;
		}, "输入的日期不能大于当前日期");
		
		//弹出 选择账号层
		function popUpAccount() {
		     dialog("选择会员账号","iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfos" ,"900px","500px","iframe");
		}
		//关闭弹出窗口 
		function closeOpenUserInfos(accountId,account,name){
		    closeThis();
		    $("#username").val(account);
		     $("#loginId").val(accountId);
			document.forms[0].realName.value = name;
		}
		
		   //弹出 选择账号层
		function popUpUserInfo() {
		    dialog("选择会员账号","iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo" ,"900px","500px","iframe");
		}
		//关闭弹出窗口 
		function closeOpenUserInfo(accountId,account,name){
		    closeThis();
		    $("#sueName").val(account);
		     $("#sueRealName").val(name);
		       $("#sueName").focus();
		}
		
		function showFrozenNumber(val){
			 if(val==1){
			     $("#frozenRow").hide();
			 }else if(val==2){
			      $("#frozenRow").show();
			 }
		}

		function gotoList(val){
		    if(val!=null){
		    	window.close();
		    }else{
		       location.href="/loginfrozenRecord/frozenRecord_pageList.action";
		    }
		}
	</script>
	<body>
	<!-- 导航栏 -->
	<s:set name="parent_name" value="'客户资料'" scope="request"/>
	<s:set name="name" value="'会员账号冻结/解冻'" scope="request"/>
	<s:set name="son_name" value="'添加冻结账号'" scope="request"/>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
	<s:form action="frozenRecord_add.action" method="POST" id="bnesFrozenRecordAddForm"  namespace='/loginfrozenRecord' >
	<s:token/>
	<INPUT TYPE="hidden" name="bnesFrozenRecord.frozenType" value="0">
	<!-- hidden properties -->
	<input type="hidden" name="showType" value="<s:property value='showType'/>">
	<!-- 数据编辑区域 -->
	<table width="85%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr>
		   <th colspan="2" align="left" class="edit_title">登录帐户冻结</th>
		</tr>
		<tr> 
			<td width="20%" class="eidt_rowTitle" align="right"><font color="red">*</font>登录帐户名：</td>
			
			<td width="80%"> 
			   <s:if test="%{showType!=null}">
			   <input type="text" id="username" readOnly="true" name="username"  value="<s:property value='loginInfo.loginAccount'/>" />
			   <input type="hidden"  name="loginId" id="loginId" value="<s:property value='loginInfo.n_LoginId'/>"/>
			   </s:if>
			   <s:else>
			       <input type="text" id="username" readOnly="true" name="username"  value="<s:property value='bnesFrozenRecord.username'/>" />
			        <input type="button"  value="选中" onclick="popUpAccount()"/>
			       <input type="hidden"  name="loginId" id="loginId" value="<s:property value='bnesFrozenRecord.loginId'/>"/>
			   </s:else>
			</td>
		</tr>
		
			<!-- <td width="20%" class="eidt_rowTitle" align="right">真实姓名：</td>   20160818 隐藏-->
			 <s:if test="personBasicInfo!=null">
			 <input type="hidden" name="realName"  readOnly="true" id="realName" type="text" value="<s:property value='personBasicInfo.name'/>"  />		
			 </s:if>
			 <s:elseif test="commercialTenantBasicInfo!=null">
			    <input type="hidden" name="realName"  readOnly="true" id="realName" type="text" value="<s:property value='commercialTenantBasicInfo.contactsName'/>"  />
			   </s:elseif>
			 <s:else>
			    <input type="hidden" name="realName"  readOnly="true" id="realName" type="text" value=""  />
				<span id="megrealName" style="color:red"></span>
			 </s:else>
			 
			 
			
			<tr style="display:none"> 
			<td width="15%" class="eidt_rowTitle" align="right"><font color="red">*</font>冻结操作账号：</td>
			<td width="80%"> 
			  <input type="text" readOnly="true" id="sueName" name="sueName"  value="<s:property value='bnesFrozenRecord.sueName'/>" />
			<!--   <input type="button"  value="选中" onclick="popUpUserInfo()"/> -->
			</td>
		</tr>


			<!-- <td width="15%" class="eidt_rowTitle" align="right">投诉人姓名：</td>  20160818隐藏-->
				<input  type="hidden"  name="sueRealName" readOnly="true" id="sueRealName" type="text"/>



		<tr style="display:none"> 
			<td width="20%" class="eidt_rowTitle" align="right"><font color="red">*</font>冻结日期：</td>
			<td width="80%"> 
				<input name="sueDate" id="sueDate" readonly="readonly" type="text"   value="<fmt:formatDate value="${bnesFrozenRecord.sueDate }" pattern="yyyy-MM-dd HH:mm:ss" />" />
				
				<span id="megsueDate" style="color:red"></span>
			</td>
		</tr>
		

			<!-- <td width="20%" class="eidt_rowTitle" align="right"><font color="red">*</font>投诉原因：</td>  20160818隐藏-->
				<textarea style="display:none"  name="sueReason" rows="8" cols="40"></textarea>
			

		<tr> 
			<td width="20%" class="eidt_rowTitle" align="right"><font color="red">*</font>冻结原因：</td>
			<td width="80%"> 
				<textarea  name="frozenReason" rows="8"  cols="40"><s:property value='bnesFrozenRecord.frozenReason'/></textarea>
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
	</body>
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>