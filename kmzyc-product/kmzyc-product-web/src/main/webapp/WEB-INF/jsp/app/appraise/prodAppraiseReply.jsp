<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<style type="text/css">
	.eidt_rowTitle{padding:0px;}
</style>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'产品评价'" scope="request"/>
<s:set name="name" value="'评价管理'" scope="request"/>
<s:set name="son_name" value="'客服回复'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/saveAppraiseReply.action" method="POST" namespace='/app' onsubmit="return Validator.Validate(this,2)"  id="frm" name='frm'>
<s:token></s:token>

<input type="hidden" name="reply.appraiseId" value="<s:property value='prodApp.appraiseId'/>" />
<input type="hidden" name="reply.recCustId" value="<s:property value='prodApp.custId'/>" />
<input type="hidden" name="reply.recipient" value="<s:property value='prodApp.custName'/>" />
<input type="hidden" name="prodApp.replyContent" value="<s:property value='prodApp.replyContent'/>" />
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="4" align="left" class="edit_title">
			评价信息
			<s:hidden name="prodApp.appraiseId"></s:hidden>
		</th>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">产品名称：</th>
		<td> 
			<s:property value='prodApp.productName'/>
		</td>
		<th align="right" class="eidt_rowTitle">产品购买日期：</th>
		<td> 
			<s:date name='prodApp.prodBuyDate' format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th width="10%" align="right" class="eidt_rowTitle">分数：</th>
		<td width="40%"> 
			<s:property value='prodApp.point'/>分
		</td>
		<th width="10%" align="right" class="eidt_rowTitle">满意度：</th>
		<td width="40%"> 
			<s:property value='prodApp.satisficing'/>
		</td>
	</tr>
	<s:if test='prodApp.propName1!=null||prodApp.propName2!=null'>
		<tr> 
			<th width="10%" align="right" class="eidt_rowTitle">
				<s:property value='prodApp.propName1'/><s:if test='prodApp.propName1!=null'>：</s:if>
			</th>
			<td width="40%"> 
				<s:if test='prodApp.propVal1!=null'>
					<s:property value='prodApp.propVal1'/>
					&nbsp;&nbsp;
					<s:property value='prodApp.propPoint1'/>分
				</s:if>
			</td>
			<th width="10%" align="right" class="eidt_rowTitle">
				<s:property value='prodApp.propName2'/><s:if test='prodApp.propName2!=null'>：</s:if>
			</th>
			<td width="40%"> 
				<s:if test='prodApp.propVal1!=null'>
					<s:property value='prodApp.propVal2'/>
					&nbsp;&nbsp;
					<s:property value='prodApp.propPoint2'/>分
				</s:if>
			</td>
		</tr>
	</s:if>
	<tr> 
		<th align="right" class="eidt_rowTitle">评价人名称：</th>
		<td> 
			<s:property value='prodApp.custName'/>
		</td>
		<th align="right" class="eidt_rowTitle">评价时间：</th>
		<td> 
			<s:date name="prodApp.appraiseDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">内容：</th>
		<td colspan="3"> 
			<s:property value='prodApp.appraiseContent'/>
		</td>
	</tr>
	<s:if test='#request.addContentList != null && #request.addContentList.size != 0'>
		<s:iterator value="#request.addContentList">
			<tr> 
				<th align="right" class="eidt_rowTitle">追加评价：</th>
				<td colspan="3"> 
					<s:property value='addContent' /><span style="color:red;margin-left:20px;">【<s:property value="#request.auditStatusMap[checkStatus]"/>】</span><br/>
				</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr> 
			<th align="right" class="eidt_rowTitle">追加评价：</th>
			<td colspan="3"> 
				暂无追加评价
			</td>
		</tr>
	</s:else>
	<tr> 
		<th align="right" class="eidt_rowTitle">是否为精华帖：</th>
		<td colspan="3">
			<s:if test='prodApp.elite == 1'>
				是
			</s:if>
			<s:else>
				否
			</s:else>
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">备注：</th>
		<td colspan="3">
			<s:property value='prodApp.des'/>
		</td>
	</tr>
	<s:if test='#request.replyList != null && #request.replyList.size != 0'>
	<s:iterator value="#request.replyList">
	<input type="hidden" value="${apprReplyId}" name="reply.apprReplyId"></input>
		<tr> 
		<th align="right" class="eidt_rowTitle">客服回复：</th>
		<td colspan="3"> 
			<textarea cols="100" rows="5" id="replyContent" name="reply.replyContent"><s:property value='replyContent'/> </textarea>
		</td>
		</tr>
	</s:iterator>
	</s:if>
	<s:else>
	<tr> 
		<th align="right" class="eidt_rowTitle">客服回复：</th>
		<td colspan="3"> 
	<s:textarea name="reply.replyContent" cols="100" rows="5" id="replyContent" ></s:textarea>
	</td>
	</tr>
	</s:else>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<input type="button" class="btn-custom btnStyle" value="回复" onClick="reply();" />
			<input type="button" class="backBtn" onClick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
</s:form>
<form method="post">
<input type="hidden" name="selectProdApp.productSku.product.productTitle" value="<s:property value='selectProdApp.productSku.product.productTitle' />">
<input type="hidden" name="selectProdApp.satisficing" value="<s:property value='selectProdApp.satisficing' />">
<input type="hidden" name="selectProdApp.startAppDate" value="<s:date name='selectProdApp.startAppDate' format='yyyy-MM-dd' />">
<input type="hidden" name="selectProdApp.endAppDate" value="<s:date name='selectProdApp.endAppDate' format='yyyy-MM-dd' />">
<input type="hidden" name="selectProdApp.replyContent" value="<s:property value='selectProdApp.replyContent' />">

</form>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
	document.forms[1].action= "/app/findNeedReplyAppr.action";
	document.forms[1].submit();
    //location.href="/app/findNeedReplyAppr.action";
}

function reply(){
	var _val = $("#replyContent").val();
	
	if(_val==""){
		alert("回复信息不能为空！");
		return;
	}
	if(_val.length > 500){
		alert("回复的字数不要超过500！");
		return;
	}
	
	var tel=/^[^\|"'<>&$%{}#+:*=|]*$/;
	if(!tel.test(_val)){
		alert("回复的信息不能包含特殊字符！");
		return;
	}
	
	document.forms['frm'].submit();
}

</SCRIPT>
</BODY>
</HTML>


