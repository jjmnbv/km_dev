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
<s:set name="name" value="'评价管理'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/prodAppraiseShow.action" method="POST"  namespace='/app' id="frm" name='frm'>

<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />

<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>
<!-- 查询条件区域 -->
<table width="98%" class="content_table"  height="100" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
		<!-- 根据查询字段的多少判断colspan-->
	    
	</tr>
	<tr>
		<td align="right">
			产品标题：
		</td>
		<td align="left">
			<input name="selectProdApp.productSku.product.productTitle" class="input_style" type="text" value="<s:property value='selectProdApp.productSku.product.productTitle'/>">
		</td>
		<td align="right">
			产品购买日期：
		</td>
		<td align="left">
			<input value ="<s:date name='selectProdApp.startBuyDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.startBuyDate' type="text" onClick="WdatePicker()"/>
			至
			<input value ="<s:date name='selectProdApp.endBuyDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.endBuyDate' type="text" onClick="WdatePicker()"/>		
		</td>
		<td align="right">
			满意度：
		</td>
		<td align="left">
			<s:select name="selectProdApp.satisficing" list="{'很不满意','不满意','一般','满意','很满意' }" headerKey="" headerValue="全部" ></s:select>
		</td>
	</tr>
	<tr>
		
		<td align="right">
			评价人：
		</td>
		<td align="left">
		    <input name="selectProdApp.custName" class="input_style" type="text" value="<s:property value='selectProdApp.custName'/>">
		</td>
		<td align="right">
			评价日期：
		</td>
		<td align="left">
			<input value ="<s:date name='selectProdApp.startAppDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.startAppDate' type="text" onClick="WdatePicker()"/>
			至
			<input value ="<s:date name='selectProdApp.endAppDate' format='yyyy-MM-dd' />" class="Wdate" readOnly="readOnly" name='selectProdApp.endAppDate' type="text" onClick="WdatePicker()"/>
		</td>
		<td align="right">
			审核状态：
		</td>
		<td align="left">
			<s:select name="selectProdApp.checkStatus" list="#request.auditStatusMap" headerKey="-1" headerValue="全部状态" ></s:select>		
		</td>
	</tr>
	<tr>
	<td align="right">
			回复状态：
		</td>
		<td align="left">
		<s:select name="selectProdApp.serchType" headerKey="" headerValue="--全部状态--" list="#{0:'未回复',1:'已回复,待审核',2:'已回复,审核通过',3:'已回复,审核不通过'}"></s:select>
		</td>
		<td align="right">
			追加状态：
		</td>
		<td align="left">
			<s:select name="selectProdApp.serchType1" headerKey="" headerValue="--全部状态--" list="#{0:'未追评',1:'已追评,待审核',2:'已追评,审核通过',3:'已追评,审核不通过'}"></s:select>
		</td>
        <td  colspan="4" align="right">
            <INPUT TYPE="button" onClick="doSearch()" class="queryBtn" value="">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('delId');">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table id="mytable" width="98%" class="list_table"  cellpadding="3" align="center" cellspacing="0" border="1">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox' onclick='checkAll(this)'>
		</th>
		<th align="center" width="15%">产品标题</th>
		<th align="center" width="10%">产品购买日期</th>
		<th align="center" width="10%">满意度</th>
		<th align="center" width="6%">评价人名称</th>
		<th align="center" width="15%">评价时间</th>
		<th align="center" width="7%">审核状态</th>
		<th align="center" width="8%">回复状态</th>
		<th align="center" width="8%">追评状态</th>
		<th align="center" width="6%">精华帖</th>
		<th align="center" width="5%">备注</th>
		<th align="center" width="24%">操作</th>
	</tr>
	<s:iterator id="custiterator" value="page.dataList">
	<tr>
	    <td align="center" width="5%">
	    	<s:if test='checkStatus != 1'>
		    	<input type="checkbox" name="delId"  value='<s:property value="appraiseId"/>'>
		    </s:if>
		    <s:else>
		    	&nbsp;
		    </s:else>
		</td>
		<td align="center"><s:property value="productSku.product.productTitle" escape="false" /></td>
		<td align="center"><s:date name="prodBuyDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		<td align="center"><s:property value="satisficing"/></td>
		<td align="center">
			<s:property value="custName"/>
		</td>
		<td align="center">
			<s:date name="appraiseDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
		<td align="center">
			<s:property value="#request.auditStatusMap[checkStatus]"/>
		</td>
		
		
		<td align="center">
			<s:if test="replyContent == 0">未回复</s:if><s:else>
			<s:if test="replyStatus == 0">已回复,待审核</s:if>
			<s:if test="replyStatus == 1">已回复,审核通过</s:if>
			<s:if test="replyStatus == 2">已回复,审核不通过</s:if>
			</s:else>
		</td>
		<td align="center">
			<s:if test="addtoContent == 0">未追加</s:if><s:else>
			<s:if test="checkStatus1 == 0">已追加,待审核</s:if>
			<s:if test="checkStatus1 == 1">已追加,审核通过</s:if>
			<s:if test="checkStatus1 == 2">已追加,审核不通过</s:if>
			</s:else>
		</td>
		<td align="center">
			<s:if test="elite == 0 || elite == null">否</s:if>
			<s:if test="elite == 1">是</s:if>
		</td>
		<td align="center">
			<s:property value="des"/>
		</td>
		<td align="center">
			<!-- <s:if test='checkStatus == 0'>-->
				
			<!--</s:if>-->
			<!--<s:if test="replyContent == 1">
				<img title="回复审核" style="cursor: pointer;" src="/etc/images/button_new/submit.png"  onclick="gotoReplyAudit(<s:property value='appraiseId'/>)" />
			</s:if>
			<s:if test="addtoContent == 1">
				<img title="追加评价审核" style="cursor: pointer;" src="/etc/images/button_new/return.png"  onclick="gotoAudit(<s:property value='appraiseId'/>)" />
			</s:if>-->
			<img title="审核" style="cursor: pointer;" src="/etc/images/button_new/audit.png"  onclick="gotoReplyAudit(<s:property value='appraiseId'/>)" />
			<img title="查看" style="cursor: pointer;" src="/etc/images/button_new/select.png"  onclick="gotoView(<s:property value='appraiseId'/>)" />
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

<input type="hidden" value="" name="prodAppId" id="showProdAppId">
<input type="hidden" value="" name="type" id="showType">
</s:form>

<SCRIPT LANGUAGE="JavaScript">
//返回我的桌面界面

function gotoView(id){
	$("#showType").val("view");
	$("#showProdAppId").val(id);
    //location.href="/app/gotoProdAppraiseView.action?prodAppId="+id+"&type=view";
   document.forms['frm'].action="/app/gotoProdAppraiseView.action";
   document.forms['frm'].submit();
}
function gotoAudit(id){
	$("#showType").val("view");
    location.href="/app/gotoProdAppraiseView.action?prodAppId="+id+"&type=audit";
}

function doDelete(name){
	document.forms['frm'].action="/app/deleteProdAppraise.action";
	document.forms['frm'].submit();
}

function doSearch(){
	document.getElementById('pageNo').value = 1;
	document.forms['frm'].submit();
}

function gotoReplyAudit(id){
	$("#showType").val("replyView");
	$("#showProdAppId").val(id);
	document.forms['frm'].action="/app/gotoProdAppraiseView.action";
	document.forms['frm'].submit();
	//location.href="/app/gotoProdAppraiseView.action?prodAppId="+id+"&type=replyView";
}
</SCRIPT>

</BODY>
</HTML>