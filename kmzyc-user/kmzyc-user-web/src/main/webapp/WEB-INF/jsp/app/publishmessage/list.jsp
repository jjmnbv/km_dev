<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>消息中心管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">

		
  
  /** 删除消息信息  **/
function deleteSelected(id) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var cehcked = false;
	// 遍历所有用户，找出被选中的用户
	for ( var i = 0; i < obj.length; i++) {
		
		if (obj[i].checked) {
			cehcked=true;
		}
	}
	if (!cehcked) {
		alert("请选择要删除的数据。");
		return false;
	} else if (confirm('是否确认删除?') == true) {
		document.publishMessageForm.action = "/acctBusiness/publishMessage_delete.action";
		document.publishMessageForm.submit();
	}
}

/**  进入新消息信息页面  **/
function gotoAdd() {
 //  alert("aaaaaaa");
	document.publishMessageForm.action = "/acctBusiness/publishMessage_preAdd.action ";
	document.publishMessageForm.submit();
//	wondiw.location.href="<%=basePath%>jsp/app/publishmessage/add.jsp";
}

/**进入问题修改页面**/
function gotoUpdate(id) {
	document.publishMessageForm.action = "/acctBusiness/publishMessage_preEdit.action?bnesInfoPrompt.infoPromptId="+ id;
	document.publishMessageForm.submit();
}

 /** 单条删除信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           document.publishMessageForm.action = "/acctBusiness/publishMessage_delete.action?bnesInfoPrompt.infoPromptId="+id;
	       document.publishMessageForm.submit();
         }
    }
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'消息中心'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="publishMessageForm" method="post"
				action="/acctBusiness/publishMessage_list.action">
				<s:token/>
				<!-- 查询条件区域 -->
				<table width="98%" height="100" class="content_table" align="center"
					cellpadding="0" cellspacing="0">
					<tr>
						<td colspan="8">
							<input class="addBtn" type="button" onclick="gotoAdd();"
								style="cursor: pointer;">
							<input class="delBtn" type="button" value=""
								onclick="deleteSelected('infoPromptIds');">
						</td>
					</tr>
					<tr>
						<td width="100">
							消息标题：
						</td>
						<td>
							<input name="bnesInfoPrompt.title" type="text"
								value="<s:property value='title'/>">
						</td>
							<td>
							发送平台：
							<select name="bnesInfoPrompt.messagePlatform" id="messagePlatform">
						    <option value="">
								请选择
							</option>
							<option value="1">
								b2b平台
							</option>
							<option value="2">
								供应商平台
							</option>
							<!-- <option value="3">
								云商平台
							</option> -->
						</select>
						</td>
						<td>
							消息类型：
							<select name="bnesInfoPrompt.type">
							
									<option value="" <s:if test='bnesInfoPrompt.type==""'>selected="selected"</s:if>>
										请选择
									</option>
							
									<option value="1" <s:if test='bnesInfoPrompt.type=="1"'>selected="selected"</s:if>>
										安全消息
									</option>
							
							
									<option value="2" <s:if test='bnesInfoPrompt.type=="2"'>selected="selected"</s:if>>
										产品信息
									</option>
							
							
									<option value="3"  <s:if test='bnesInfoPrompt.type=="3"'>selected="selected"</s:if>>
										订单信息
									</option>
									
									<option value="4"  <s:if test='bnesInfoPrompt.type=="4"'>selected="selected"</s:if>>
										审核信息
									</option>
							
							</select>
						</td>


						<td>
							<INPUT TYPE="submit" class="queryBtn" value="">
						</td>
					</tr>
				</table>

				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th align="center" width="5%">
							<input type='checkbox' name='allbox'
								onclick="checkAll(this,'infoPromptIds')">
						</th>
						<th>
							消息类型
						</th>
						<th>
							发布状态
						</th>
						<th>
							标题
						</th>
						<th>
							内容
						</th>
						<th>
							是否定时发布
						</th>
						<th>
							发布日期
						</th>
						<th>
							操作
						</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td align="center" width="5%">
								<input type="checkbox" name="infoPromptIds"
									value='<s:property value="#custiterator.infoPromptId"/>' />
							</td>
							<td align="center">
								<s:if test="#custiterator.type==1">安全消息</s:if>
								<s:if test="#custiterator.type==2">产品信息</s:if>
								<s:if test="#custiterator.type==3">订单信息</s:if>
								<s:if test="#custiterator.type==4">审核信息</s:if>
							</td>

							<td align="center">

								<s:if test="#custiterator.status==1">已发布</s:if>
								<s:if test="#custiterator.status==0">待发布</s:if>
							</td>
							<td align="center">
								<s:property value="title" />
							</td>
							<td align="center">
								<s:property value="content" escape="false"/> 

							</td>
							<td align="center">
								<s:if test="#custiterator.isTime==1">是</s:if>
								<s:if test="#custiterator.isTime==0">否</s:if>
							</td>
							<td align="center">
								<s:date name="releaseDate" format="yyyy-MM-dd" />

							</td>
							<td align="center">
								<s:if test="#custiterator.status==0">
									<img title="修改" style="cursor: pointer;"
										src="/etc/images/icon_modify.png"
										onclick='gotoUpdate(<s:property value="infoPromptId"/>)' />


									<img title="删除" style="cursor: pointer;"
										src="/etc/images/icon_delete.png"
										onclick="deleteByKey(<s:property value="infoPromptId"/>)">
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</table>

				<table width="500" align="right">
					<tr>
						<td>
							<s:set name="form_name" value="'publishMessageForm'"
								scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

