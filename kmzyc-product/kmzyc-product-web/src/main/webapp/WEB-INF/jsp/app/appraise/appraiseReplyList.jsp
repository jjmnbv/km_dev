<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<style type="text/css">
	.tableStyle1{font-size:12px;}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>


<s:set name="parent_name" value="'产品评价管理'" scope="request" />
<s:set name="name" value="'回复审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/appReplyShowList.action" method="POST"  namespace='/app' id="frm" name='frm'>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table width="98%" class="content_table"  align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    
	</tr>
	<tr>
		<td >
			审核状态：
		
		
			<s:select name="reply.replyStatus" list="#request.auditStatusMap" headerKey="-1" headerValue="全部状态" ></s:select>		
		</td>
		<td >
			回复日期：
		
		
			<input value ="<s:date name='reply.startDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='reply.startDate' type="text" onClick="WdatePicker()"/>
			至
			<input value ="<s:date name='reply.endDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='reply.endDate' type="text" onClick="WdatePicker()"/>
		</td>
        <td >
            <INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
            <input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');">
            <INPUT class="btn-custom btnStyle" TYPE="button" value="通过" onClick="gotoAudit(1);">
			<input class="btn-custom btnStyle" type="button" value="不通过"  onclick="gotoAudit(2);">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="70%">回复内容</th>
		<th align="center" width="10%">评价时间</th>
		<th align="center" width="15%">审核状态</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList" status="st">
	<tr>
	    <td align="center" width="5%">
	    	<s:if test='replyStatus != 1'>
	    		<input type="checkbox" name="delId"  value='<s:property value="apprReplyId"/>'>
	    		<input name="checkStatus" type="hidden" value="<s:property value='replyStatus'/>" />
	    	</s:if>
		    <s:else>
		    	&nbsp;
		    </s:else>
		</td>
		<td align="center">
			<span title="<s:property value="replyContent"/>" style="width:800px;height:31px;overflow:hidden;display:block;line-height:31px;"><s:property value="replyContent"/></span>
		</td>
		<td align="center">
			<s:date name="replyDate" format="yyyy-MM-dd" />
		</td>
		<td align="center">
			<s:property value="#request.auditStatusMap[replyStatus]"/>
		</td>
	</tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
//返回我的桌面界面

function doDelete(name){
	document.forms['frm'].action="/app/deleteAppReply.action";
	document.forms['frm'].submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function gotoAudit(arg){
	if($("input[type='checkbox'][name='delId']:checked").length==0){
		alert("请选择要审核的回复！");
		return;
	}
	
	var flag = false;
	$("input[type='checkbox'][name='delId']:checked").each(function(i){
		var _input = $(this).siblings("input[name='checkStatus']")[0].value;
		if(_input != 0){
			flag = true;
			return false;
		}
	});
	
	if(flag){
		alert("已审核的回复，不能再进行审核！");
		return;
	}
	
	if(confirm("确定执行该操作吗？")){
		document.forms['frm'].action="/app/appReplyToAudit.action?statusArg="+arg;
		document.forms['frm'].submit();
	}
}

</SCRIPT>

</BODY>
</HTML>