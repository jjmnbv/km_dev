<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>同步订单信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/app/appCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	//表单校验
    $("#syncOrder2BaseForm").validate({
    	 debug:true,
         rules: {
				"map['orderCode']":{required:true}
        	},
	        submitHandler:function(form){
        		openLoadingDiv("订单信息正在同步中....");
	        	$.ajax({
	    			async:false,
	    			url:'/app/syncOrder2Base.action',
	    			type:'POST',
	    			dataType:'json',
	    			data:{
	        			"map['orderCode']":$('#orderCode').val(),
	    				t_i_m_e:Math.random()
	    			},
	    			timeout:'6000',
	    			success:function(data){
	    				console.log(data);
	    				if(data.syncResult="SUCESS"){
	    					alert("已成功将订单信息同步到总部系统!");
	    				}else{
	    					alert("订单信息同步到总部系统时失败，请稍后重试或联系系统管理员!");
	    				}
	    				closeLoadingDiv();
	    			},
	    			error:function(){
	    				console.log('error!');
	    				alert("订单信息同步到总部系统时失败，请稍后重试或联系系统管理员!");
	    				closeLoadingDiv();
	    			}
	    		});	
		    }
    });
});

</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'订单同步对接'" scope="request"/>
<s:set name="name" value="'手动同步'" scope="request"/>
<s:set name="son_name" value="'列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/public/title.jsp"></jsp:include>
 <div  style="height:90%;overflow-y:auto; " >
<s:form  id="syncOrder2BaseForm" enctype="multipart/form-data"  method="POST" >
<s:token/>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- 数据编辑区域 -->
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr>
		<td colspan="2" align="left" class="edit_title">手动同步订单</td>
	</tr>
	<tr>
		<td width="20%" align="center"  class="edit_title">订单号列表：</br></br><span style="color:red">多个订单号以”，”隔开</span></td>
		<td width="80%">
		<textarea  id="orderCode" name="map['orderCode']" style="width:85%;height:100px"></textarea>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input  id="syncButton" type="submit" style="width:60px;height:40px" value="同步" />&nbsp;&nbsp;
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>
</s:form>
</div>
</BODY>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/public/message.jsp"></jsp:include>
</HTML>