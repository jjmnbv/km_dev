<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">

function showMoliles(id,_this){
	 if($(_this).html().length < 12){
		 return;
	 }
	 var obj = $('#receiverId_'+id);
	 console.log(obj.length);
	 if(obj.is(':visible')){
		 obj.hide();
	 }else{
		 obj.show();
	 }
}



</script>

</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'短信邮件查询'" scope="request" />
<s:set name="name" value="'短信发送查询'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form  name="msgQueryForm" action="/emailmsg/queryMsgSendList.action" onsubmit="return checkAllTextValid(this)" method="post">
<!-- 查询条件区域 -->
<table  width="98%"  class="content_table" align="center" cellpadding="0" cellspacing="0" >
	
	<tr>
		<td align="right">手机号：</td>
		<td>
		     <input name="msgSendTask.receiver" type="text" value="<s:property value='msgSendTask.receiver'/>">
		</td>
		<td align="right">内容：</td>
		<td>
		     <input name="msgSendTask.content" type="text" value="<s:property value='msgSendTask.content'/>">
		</td>
		<td align="right">发送时间从</td>
		<td>
			<input type="text" id="d523" class="Wdate"  value ="<s:date name = 'msgSendTask.sendTime' format='yyyy-MM-dd HH:mm:ss' />"    name="msgSendTask.sendTime"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
		<td align="right">至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td>
			<input type="text" id="d524" class="Wdate"  value ="<s:date name = 'msgSendTask.createTime' format='yyyy-MM-dd HH:mm:ss' />"    name="msgSendTask.createTime"  onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
		
		<td align="right">状态：</td>
		<td>
		    <select name="msgSendTask.isSuccess">
				<option value="" <s:if test='msgSendTask.isSuccess==""'>selected="selected"</s:if>>
					请选择
				</option>
				<option value="0" <s:if test='msgSendTask.isSuccess==0'>selected="selected"</s:if>>
					 失败
				</option>
				<option value="1" <s:if test='msgSendTask.isSuccess==1'>selected="selected"</s:if>>
			  		 成功
				</option>
			<!-- 		<option value="1" <s:if test='msgSendTask.isSuccess==1'>selected="selected"</s:if>>
			  		 成功
				</option> -->
				
					<option value="3" <s:if test='msgSendTask.isSuccess==3'>selected="selected"</s:if>>
			  	达上限
				</option>
					<option value="4" <s:if test='msgSendTask.isSuccess==4'>selected="selected"</s:if>>
			  		黑名单
				</option>
				<!-- 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单 -->
		</td>
	</tr>
	<tr>
		<td align="right">短信类型： </td>
		<td>
			<select name="msgSendTask.msgType">
				<option value="" <s:if test='msgSendTask.msgType==""'>selected="selected"</s:if>>
					请选择
				</option>
				<s:iterator id="msgTemplate" value="msgTypeMap">
					<option value="<s:property value="#msgTemplate.key"/>" <s:if test='msgSendTask.msgType==#msgTemplate.key'>selected="selected"</s:if>>
						 <s:property value="value"/>
					</option>
				</s:iterator>
	      	</select>
	    </td>
		<td align="right">发送次数： </td>
		<td>
		     <input name="msgSendTask.sendCount" type="text" value="<s:property value='msgSendTask.sendCount'/>">
		</td>
		<td colspan="6" align="right">
			<input type="submit" class="queryBtn" value="" >
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <%--<th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_AccountIds')">
		</th> --%>
		<th align="center" width="4%" >ID</th>
		<th align="center" width="11%" >手机号</th>
		<th align="center" width="5%">短信类型</th>
		<th align="center" width="37%">内容</th>
		<th align="center" width="8%">发送类型</th>
		<th align="center" width="12%">发送时间</th>
		<th align="center" width="5%">发送次数</th>
		<th align="center" width="5%">状态</th>
		<th align="center" width="4%">重发ID</th>
		<th align="center" width="5%">通道</th>
		<th align="center" width="10%">接收回调</th>
	</tr>
	<s:iterator id="msgiterator"  value="page.dataList">
	<tr>
	<td>
	      <s:property value="id"/>
		</td>
		<s:if test="#msgiterator.receiver.length()>11">
			<td id="receiverShort" align="center" onclick="showMoliles(<s:property value="id"/>,this)">
			     <s:property value="receiver.substring(0,10)"/>...
			</td>
		</s:if>
		<s:if test="#msgiterator.receiver.length()<=11">
			<td id="receiverShort" align="center" onclick="showMoliles(<s:property value="id"/>,this)">
			     <s:property value="receiver"/>
			</td>
		</s:if>
		<td>
	      <s:iterator id="templateStatus"  value="msgTypeMap">
				<s:if test='#msgiterator.msgType==#templateStatus.key'><s:property value="#templateStatus.value"/></s:if>
			</s:iterator>
		</td>
		<td align="center">
		     <s:property value="content"/>
		</td>
		<td align="center">
		   <s:if test="#msgiterator.type==0">定时发送</s:if>
		   <s:if test="#msgiterator.type==1">立即发送</s:if>
		</td>
		<td align="center">
		     <s:date name="sendTime" format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		<td align="center">
		     <s:property value="sendCount"/>
		</td>
		<td align="center">
		   <s:if test="#msgiterator.isSuccess==0">失败</s:if>
		   <s:if test="#msgiterator.isSuccess==1">成功</s:if>
		   <s:if test="#msgiterator.isSuccess==3">达上限</s:if>
		   <s:if test="#msgiterator.isSuccess==4">黑名单</s:if>
		</td>
		<td align="center">
		 <s:property value="repeatSendId"/>
		</td>
		<td align="center">
		 <s:property value="msgChannel"/>
		</td>
		<td align="center">
		 <s:property value="sendSuccess"/>
		</td>
	</tr>
	<tr id="receiverId_<s:property value="id"/>" style="display: none">
	<td colspan="7"><s:property value="receiver"/></td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'msgQueryForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
</body>
</html>

