<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>通道信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">


		$(function(){
			$("#channelForm").validate({
			    rules: {
						"emMsgChannel.channelName": {required:true},
						"emMsgChannel.channelMaxNumber": {required:true,number:true},
						"emMsgChannel.maxNumber": {required:true,number:true}
							        	},
			  success: function (label){
			     label.removeClass("checked").addClass("checked");
			     }
			});
		});

		function gotoList(){
			location.href="/emailmsg/queryEmMsgChannelList.action";
		}

      </script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'短信邮件查询'" scope="request" />
		<s:set name="name" value="'通道信息'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<s:form id="channelForm" name="updateEmMsgChannel" action="/emailmsg/updateEmMsgChannel.action" method="post">
			<!-- 数据编辑区域 -->
			<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class=#emMsgChannel.edit_title">
						通道信息
					</th>
				</tr>
				<tr>
				<input type="hidden" name="emMsgChannel.id" value="<s:property value="emMsgChannel.id"/>"  />
					<td width="20%" class="eidt_rowTitle">通道名：</td>
					<td width="80%"><input name="emMsgChannel.channelName" value='<s:property value="emMsgChannel.channelName"/>'/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">开关：</td>
					<td width="80%">
					    <select name="emMsgChannel.channelSwitch" id="emMsgChannel.channelSwitch" >		
							<option value="1" <s:if test="emMsgChannel.channelSwitch==1"> selected="selected"</s:if>> 
							 开
							</option>
							<option value="0" <s:if test="emMsgChannel.channelSwitch==0"> selected="selected"</s:if>> 
							 关
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">一天最大条数：</td>
					<td width="80%"><input value='<s:property value="emMsgChannel.maxNumber"/>'  name="emMsgChannel.maxNumber"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">通道最大条数：</td>
					<td width="80%"><input value='<s:property value="emMsgChannel.channelMaxNumber"/>'  name="emMsgChannel.channelMaxNumber"/>
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">通道类型：</td>
					<td width="80%">
									<select name="emMsgChannel.channelType" id="emMsgChannel.channelType" >		
									<option value="1" <s:if test="emMsgChannel.channelType==1"> selected="selected"</s:if>> 
										 验证
									</option>
									<option value="2" <s:if test="emMsgChannel.channelType==2"> selected="selected"</s:if>> 
								    	通知
									</option>
									<option value="3" <s:if test="emMsgChannel.channelType==3"> selected="selected"</s:if>> 
								    	 推广
									</option>
									</select>
					</td>
				</tr>
				
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">

						<input id="buttonTrue" class="saveBtn" type="submit" value="" />
						&nbsp;&nbsp;
						<input class="backBtn" onClick="gotoList()" type="button"  value="">

					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
		<!--消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		
	</BODY>
</HTML>