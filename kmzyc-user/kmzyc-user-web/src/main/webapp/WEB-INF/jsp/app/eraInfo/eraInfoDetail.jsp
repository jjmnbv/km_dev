<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.app.util.Constants"%>
<html>
	<head>
		<title>时代会员信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">
		//返回事件
		function gotoList(){
			location.href="/userInfo/eraInfo_pageList.action";
		}
		//刷新获取数据 
		function gotoRefresh(){
			//获取时代编号
			var eraNo=$("input[name=eraNo]").val();
			var eraInfoId=$("input[name=eraInfoId]").val();
			location.href="/userInfo/eraInfo_refreshErainfo.action?eraInfo.eraNo="+eraNo+"&&eraInfo.eraInfoId="+eraInfoId;
		}
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户基本资料'" scope="request" />
		<s:set name="name" value="'时代会员管理'" scope="request" />
		<s:set name="son_name" value="'时代会员详情'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<s:form id="eraInfo" action="eraInfo_refreshErainfo.action" method="post" namespace="/userInfo">
			<input type="hidden" name="eraNo" value="<s:property value='eraInfo.eraNo'/>"/>
			<input type="hidden" name="eraInfoId" value="<s:property value='eraInfo.eraInfoId'/>"/>
			<s:token />
			<!-- 数据编辑区域 -->
			<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class="edit_title">
						时代会员信息
					</th>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">康美中药城会员ID：</td>
					<td width="80%"><s:property value="eraInfo.eraId"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">时代会员编号：</td>
					<td width="80%"><s:property value="eraInfo.eraNo"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">康美健康账号：</td>
					<td width="80%"><s:property value="loginInfo.loginAccount"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">电子邮箱：</td>
					<td width="80%"><s:property value="loginInfo.email"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">手机号码：</td>
					<td width="80%"><s:property value="loginInfo.mobile"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">当前体验积分：</th>
					<td width="80%"><s:property value="eraInfo.expIntegralValue"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">时代等级：</th>
					<td width="80%"><s:property value="eraInfo.eraGradeName"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">时代折扣：</th>
					<td width="80%"><s:property value="eraInfo.eraGradeRate"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">联系电话：</th>
					<td width="80%"><s:property value="eraInfo.contactInformation"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">推荐人编号：</th>
					<td width="80%"><s:property value="eraInfo.recommendedNo"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">性别：</th>
					<td width="80%"><s:property value="eraInfo.sex"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">生日：</th>
					<td width="80%"><s:date name="eraInfo.birthday" format="yyyy-MM-dd"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">真实姓名：</th>
					<td width="80%"><s:property value="eraInfo.name"/> </td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">昵称：</th>
					<td width="80%"><s:property value="eraInfo.nickname"/></td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">证件号码：</th>
					<td width="80%"><s:property value="eraInfo.certificateNumber"/></td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">创建时间：</th>
					<td width="80%"> <s:date name="eraInfo.createDate" format="yyyy-MM-dd kk:mm:ss"/></td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">更新时间：</th>
					<td width="80%"> <s:date name="eraInfo.modifyDate" format="yyyy-MM-dd kk:mm:ss"/></td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="refreshBtn" onclick="gotoRefresh()" type="button" value=" ">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList()" type="button"   
							value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</BODY>
</HTML>