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


<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'回复评价'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/findNeedReplyAppr.action" method="POST"  namespace='/app' id="frm" name='frm'>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	   
	</tr>
	<tr>
		<td align="right">
			产品标题：
		</td>
		<td align="left">
			<input name="selectProdApp.productSku.product.productTitle" class="input_style" type="text" value="<s:property value='selectProdApp.productSku.product.productTitle' />">
		</td>
		<td align="right">
			满意度：
		</td>
		<td align="left">
			<s:select name="selectProdApp.satisficing" list="{'很不满意','不满意','一般','满意','很满意' }" headerKey="" headerValue="全部" ></s:select>
		</td>
		<td align="right">
			评价日期：
		</td>
		<td align="left">
			<input value ="<s:date name='selectProdApp.startAppDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.startAppDate' type="text" onClick="WdatePicker()"/>
			至
			<input value ="<s:date name='selectProdApp.endAppDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.endAppDate' type="text" onClick="WdatePicker()"/>
		</td>
	</tr>
	<tr>
		<td align="right">
			是否回复：
		</td>
		<td align="left">
			<s:select name="selectProdApp.replyContent" headerKey="" headerValue="--全部状态--" list="#{0:'未回复',1:'已回复'}"></s:select>
		</td>
         <td >
            <INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">&nbsp;
            
		</th>
		<th align="center" width="15%">产品标题</th>
		<th align="center" width="15%">评价内容</th>
		<th align="center" width="10%">满意度</th>
		<th align="center" width="10%">评价时间</th>
		<th align="center" width="15%">审核状态</th>
		<th align="center" width="15%">回复状态</th>
		<th align="center" width="10%">备注</th>
		<th align="center" width="15%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList" status="st">
	<tr>
	    <td align="center" width="5%">
		    <s:property value="#st.index + 1" />
		</td>
		<td align="center"><s:property value="productSku.product.productTitle" escape="false" /></td>
		<td align="center">
			<span title="<s:property value="appraiseContent"/>" style="width:200px;height:31px;overflow:hidden;display:block;line-height:31px;"><s:property value="appraiseContent"/></span>
		</td>
		<td align="center"><s:property value="satisficing"/></td>
		<td align="center">
			<s:date name="appraiseDate" format="yyyy-MM-dd" />
		</td>
		<td align="center">
			<s:property value="#request.auditStatusMap[checkStatus]"/>
		</td>
		<td>
			<s:if test="replyContent == 0">未回复</s:if><s:else>已回复</s:else>
		</td>
		<td align="center">
			<span title="<s:property value="des"/>" style="width:200px;height:31px;overflow:hidden;display:block;line-height:31px;"><s:property value="des"/></span>
		</td>
		<td align="center">
			<!--<s:if test='replyContent == null || replyContent == 0'>-->
			<!--</s:if>-->
			<img title="回复" style="cursor: pointer;" src="/etc/images/button_new/modify.png"  onclick="gotoReply(<s:property value='appraiseId'/>)" />
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
<input type="hidden" value="" name="type" id="showType"></input>
<input type="hidden" value="" name="prodAppId" id="showProdAppId"></input>
</s:form>
<SCRIPT LANGUAGE="JavaScript">
//返回我的桌面界面

function gotoReply(id){
	$("#showType").val("reply");
	$("#showProdAppId").val(id);
	document.forms['frm'].action="/app/gotoProdAppraiseView.action";
	document.forms['frm'].submit();
    //location.href="/app/gotoProdAppraiseView.action?prodAppId="+id+"&type=reply";
}

function doDelete(name){
	document.forms['frm'].action="/app/deleteProdAppraise.action";
	document.forms['frm'].submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

</SCRIPT>

</BODY>
</HTML>