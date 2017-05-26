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
<script type="text/javascript">
function gotoUpdate(id){
	 location.href="/emailmsg/queryEmMsgChannelDetail.action?emMsgChannelId="+id;
}
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'短信邮件查询'" scope="request" />
<s:set name="name" value="'短信通道'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll;" >
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" width="20%" >通道名</th>
		<th align="center" width="20%">0：关,1：开</th>
		<th align="center" width="10%">一天最大条数</th>
		<th align="center" width="10%">通道最大条数</th>
		<th align="center" width="20%">通道类型</th>
		<th align="center" width="20%">操作</th>
	</tr>
	<s:iterator   value="emMsgChannelList">
	<tr>
		<th align="center" width="20%"><s:property value="channelName"/></th>
		<th align="center" width="20%"><s:property value="channelSwitch"/></th>
		<th align="center" width="20%"><s:property value="maxNumber"/></th>
		<th align="center" width="20%"><s:property value="channelMaxNumber"/></th>
		<th align="center" width="20%"><s:property value="channelType"/></th>
		<th align="center" width="20%">
		<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png" onclick="gotoUpdate(<s:property value="id"/>)">
		</th>
	</tr>
	</s:iterator>
</table>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</div>
</body>
</html>

