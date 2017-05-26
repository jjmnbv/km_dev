<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>会员账号冻结</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript">
		    /** 进入账户冻结页面 **/
		    function gotoAdd(){
		         document.bnesFrozenRecordForm.action="/accounts/accountFrozen_operateAdd.action";
		         document.bnesFrozenRecordForm.submit();
		    }
			/**  进入冻结账号信息页面  **/
		    function gotoUpdateStatus(id){
		    	location.href="/accounts/accountFrozen_operateThawEdit.action?FRId="+id;
		    }
			//账号层
			function queryAccountInfo(id) {
			    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");   
			}
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request"/>
		<s:set name="name" value="'账户金额冻结/解冻'" scope="request"/>
   		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
   		<div style="height:90%;overflow-y:auto; " >
		<s:form name="bnesFrozenRecordForm" onsubmit=" return checkAllTextValid(this)" method="post" action="/accounts/accountFrozen_queryAccFrozenPageList.action">
			<!-- 查询条件区域 -->
			<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0">
				
				<tr>
					<td>会员账号：<input name="username" type="text" value="<s:property value='bnesFrozenRecord.username'/>" /></td>
					<td>
						冻结操作账号：<input name="sueName" type="text" value="<s:property value='bnesFrozenRecord.sueName'/>" />
					</td>
					<td colspan="2">
					冻结时间：
						<input type="text" id="d523" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.createDateStart' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.createDateStart" onClick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d524\')}'})" />
						  		至
						<input type="text" id="d524" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.createDateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.createDateEnd" onClick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}',maxDate:'%y-%M-%d %H:%m:%s'})" />
					</td>
					
					
					<td>&nbsp;</td>
					<!-- 
					<td align="right">姓名：</td>
					<td><input name="realName" type="text" value="<s:property value='bnesFrozenRecord.realName'/>" />
					</td>
					<td align="right">投诉账户：</td>
					<td><input name="sueName" type="text" value="<s:property value='bnesFrozenRecord.sueName'/>" />
					</td>
					<td align="right">投诉账户姓名：</td>
					<td><input name="sueRealName" type="text" value="<s:property value='bnesFrozenRecord.sueRealName'/>" /></td>
					
					 -->
				</tr>
				<tr>
					<td>状态：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						 <select name="status">
						   	<option value="" <s:if test='bnesFrozenRecord.status==""'>selected="selected"</s:if> >
								全部
							</option> 
							<option value="0" <s:if test='bnesFrozenRecord.status=="0"'>selected="selected"</s:if>>
								已冻结
							</option>
							<option value="1" <s:if test='bnesFrozenRecord.status=="1"'>selected="selected"</s:if>>
								 已解冻
							</option>
						</select>
					</td>
					<td>解冻操作账号：<input name="sysUserName" type="text" value="<s:property value='bnesFrozenRecord.sysUserName'/>" /></td>
					
					<td colspan="2">
					解冻时间：
						<input type="text" id="d525" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.operatorDateStart' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.operatorDateStart" onClick="WdatePicker({el:'d525',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d526\')}'})" />
						  		至
						<input type="text" id="d526" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.operatorDateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.operatorDateEnd" onClick="WdatePicker({el:'d526',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d525\')}',maxDate:'%y-%M-%d %H:%m:%s'})" />
					</td>
				<td align="right">
                <input TYPE="submit" class="queryBtn" value="">
                <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
                </td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
				<tr>
					<th>会员账号</th>
					<!--  
						<th>真实姓名</th>
						<th>冻结类型</th>
					-->
					<th>冻结操作账号</th>
					<!-- <th>投诉账户姓名</th>
					<th>投诉日期</th> 
					<th>投诉原因</th>-->
					<th>冻结金额</th>
					<th>冻结原因</th>
					<th>冻结时间</th>
					<th>解冻操作账号</th>
					<th>解冻金额</th>
					<th>解冻原因</th>
					<th>解冻时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td align="center"><s:property value="account"/></td>
						<!-- 
						<td align="center"><s:property value="realName" /></td>
						<td align="center">
							<s:if test="#custiterator.frozenType==1">账户冻结</s:if>
							<s:elseif test="#custiterator.frozenType=2">金额冻结</s:elseif>
						</td>
						 -->
						<td align="center"><s:property value="sueName" /></td>
						<!-- <td align="center"><s:property value="sueRealName" /></td>
						<td align="center"><s:date name="sueDate" format="yyyy-MM-dd" /></td>
						<td align="center"><s:property value="sueReason" /></td> -->
						<td align="center"><s:if test="#custiterator.frozenType==2"><s:property value="frozenNumber" /></s:if></td>
						<td align="center"><s:property value="frozenReason" /></td>
						<td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><s:property value="sysUserName" /></td>
						<td align="center"><s:if test="#custiterator.frozenType==2"><s:property value="frozenNumber" /></s:if></td>
						<td align="center"><s:property value="thawReason" /></td>
						<td align="center"><s:date name="operatorDate" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><s:date name="status" />
							<s:if test="#custiterator.status==0">已冻结</s:if>
							<s:elseif test="#custiterator.status=1">已解冻</s:elseif>
						</td>
						<td>
							<s:if test="status==0">
								<img title="解冻" style="cursor: pointer;" src="/etc/images/icon_modify.png" onClick="gotoUpdateStatus(<s:property value='frozenRecordId'/>)" />
							</s:if>
						</td>
				</tr>
				</s:iterator>
			</table>
			<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'bnesFrozenRecordForm'" scope="request"></s:set> <jsp:include
						page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>

