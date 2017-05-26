<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>账户金额解冻</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		    // 首次加载校验 
		    $(document).ready(function(){
				$("#bnesFrozenRecordUpdateForm").validate({
					rules: {"thawReason": {required:true,maxlength:165}},
					success: function (label){
						label.removeClass("checked").addClass("checked");
						}
				});
			});
		    function gotoList(){
			    location.href="/accounts/accountFrozen_queryAccFrozenPageList.action";
			}
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'账务管理'" scope="request"/>
		<s:set name="name" value="'账户金额冻结/解冻'" scope="request"/>
		<s:set name="son_name"  value="'解冻金额'"></s:set>
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height:90%;overflow-y:auto; " >
		<s:form action="accountFrozen_opreateUpdate.action" method="POST" id="bnesFrozenRecordUpdateForm" namespace='/accounts' >
			<s:token/>
			<input type="hidden" name="frozenRecordId" value="<s:property value='bnesFrozenRecord.frozenRecordId'/>"/>
			<input type="hidden" name="accountId" value="<s:property value='bnesFrozenRecord.accountId'/>"/>
			<input type="hidden" name="frozenType" value="<s:property value='bnesFrozenRecord.frozenType'/>"/>
			<input type="hidden" name="frozenNumber" value="<s:property value='bnesFrozenRecord.frozenNumber'/>"/>
			<table width="85%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr><th colspan="2" align="left" class="edit_title">解冻金额</th></tr>
				<!-- 
				<tr> 
					<td width="15%" class="eidt_rowTitle"><font color="red">*</font>账户冻结类型：</td>
					<td width="80%"> 
				    <s:if test="%{bnesFrozenRecord.frozenType==1}">账户冻结</s:if>
				    <s:elseif test="%{bnesFrozenRecord.frozenType==2}">金额冻结</s:elseif>
				</td>
			  </tr>
			   -->
			   <tr> 
				<td width="15%" class="eidt_rowTitle">会员账号：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.account"/></td>
			</tr>
			
			<tr> 
				<td width="15%" class="eidt_rowTitle">冻结操作账号：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.sueName"/></td>
			</tr>
			<!-- 
			<tr> 
				<td width="15%" class="eidt_rowTitle">真实姓名：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.realName"/></td>
			</tr>
			 -->
			<s:if test="%{bnesFrozenRecord.frozenType==2}">
			<tr> 
				<td width="15%" class="eidt_rowTitle">冻结金额：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.frozenNumber"/></td>
			</tr>
			</s:if>
			<!--  
			<tr> 
				<td width="15%" class="eidt_rowTitle"><font color="red">*</font>投诉人登录账号：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.sueName"/></td>
			</tr>
			<tr> 
				<td width="15%" class="eidt_rowTitle">投诉人姓名：</td>
				<td width="80%"><s:property value="bnesFrozenRecord.sueRealName"/></td>
			</tr>
			
			<tr> 
				<td width="15%" class="eidt_rowTitle">投诉日期：</td>
				<td width="80%"><s:date name="bnesFrozenRecord.sueDate" format="yyyy-MM-dd"/></td>
			</tr>
			<tr> 
				<td width="15%" class="eidt_rowTitle"><font color="red">*</font>投诉原因：</td>
				<td width="80%"> 
					<s:property value="bnesFrozenRecord.sueReason"/>
			</tr>-->		
			<tr> 
				<td width="15%" class="eidt_rowTitle">冻结原因：</td>
				<td width="80%"> 
					<s:property value="bnesFrozenRecord.frozenReason"/>
				</td>
				</tr>
		
			<tr>
				<td width="15%" class="eidt_rowTitle">冻结时间：</td>
				<td width="80%"><s:date name="bnesFrozenRecord.createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>	
				<tr>
				<td width="15%" class="eidt_rowTitle"><font color="red">*</font>解冻原因：</td>
				<td width="80%"> 
					<textarea name="thawReason" rows="6" cols="50" ></textarea>
				</td>
			</tr>		
		</table>
		<!-- 底部 按钮条 -->
		<table width="60%" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
			<tr> 
				<td align="left">
					<input class="saveBtn" type="submit" value=" ">&nbsp;&nbsp;<input class="backBtn" onclick="gotoList()" type="button" value=" ">
				</td>
				<td width="20%" align="center"></td>
			</tr>
		</table>
		</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>