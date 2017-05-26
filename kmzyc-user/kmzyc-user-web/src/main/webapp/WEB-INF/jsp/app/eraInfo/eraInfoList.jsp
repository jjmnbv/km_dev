<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>地址管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/app/eraInfo/eraInfoList.js"></script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户基本资料'" scope="request" />
		<s:set name="name" value="'时代会员管理'" scope="request" />
		<s:set name="son_name" value="'列表管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="eraInfoForm" action="/userInfo/eraInfo_pageList.action" method="post">
				<!-- 查询条件 -->
				<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">时代编号：</td>
						<td><input name="eraInfo.eraNo" type="text" value='<s:property value="eraNo"/>' ></td>
						<td align="right">用户名：</td>
						<td><input name="eraInfo.loginAccount" type="text" value='<s:property value="loginAccount"/>' ></td>
						<td align="right">推荐人：</td>
						<td><input name="eraInfo.recommendedNo" type="text" value='<s:property value="recommendedNo"/>' /></td>
						<td align="right">真实姓名：</td>
						<td><input name="eraInfo.name" type="text" value='<s:property value="name"/>' ></td>
					</tr>
					<tr>
						<td align="right">时代等级：</td><td>
							<select name="eraInfo.eraGradeName" style="width:173px">
								<option value="" <s:if test='eraInfo.eraGradeName==""'>selected="selected"</s:if> >所有</option>
								<option value="银卡" <s:if test='eraInfo.eraGradeName=="银卡"'>selected="selected"</s:if> >银卡</option>
								<option value="金卡" <s:if test='eraInfo.eraGradeName=="金卡"'>selected="selected"</s:if> >金卡</option>
								<option value="钻卡" <s:if test='eraInfo.eraGradeName=="钻卡"'>selected="selected"</s:if> >钻卡</option>
							</select>
						</td>
						<td align="right">创建日期从：</td>
						<td>
						<input type="text" id="d523" class="Wdate"  value ="<s:date name = 'eraInfo.createStartDate' format='yyyy-MM-dd HH:mm:ss' />"    name="eraInfo.createStartDate"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
						<td align="right">至：</td>
						<td>
							<input type="text" id="d524" class="Wdate"  value ="<s:date name = 'eraInfo.createEndDate' format='yyyy-MM-dd HH:mm:ss' />"    name="eraInfo.createEndDate"  onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						</td>
						<td align="center"><INPUT TYPE="submit" class="queryBtn" value=""></td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
							<input type='checkbox' name='allbox'
								onclick="checkAll(this,'eraInfoIds')">
						</th>
						<th>
							时代会员编号
						</th>
						<th>
							用户名
						</th>
						<th>
							时代等级
						</th>
						<th>
							等级折扣
						</th>
						<th>
							推荐人编号
						</th>
						<th>
							真实姓名
						</th>
						<th>
							昵称
						</th>
						<th>
							创建时间
						</th>
						<th>
							操作
						</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="eraInfoIds"
									value='<s:property value="eraInfoId"/>'>
							</td>
							<td>
								<s:property value="eraNo" />
							</td>
							<td>
								<s:property value="loginAccount" />
							</td>
							<td>
								<s:property value="eraGradeName" />
							</td>
							<td>
								<s:property value="eraGradeRate" />
							</td>
							<td>
								<s:property value="recommendedNo" />
							</td>
							<td>
								<s:property value="name" />
							</td>
							<td>
								<s:property value="nickname" />
							</td>
							<td>
							   <s:date name="createDate" format="yyyy-MM-dd kk:mm:ss"/>
							</td>
							<td>
								<img title="查看时代会员档案" style="cursor: pointer;" src="/etc/images/icon/shidai.png"
									onclick="queryEraInfoDetail(<s:property value="eraInfoId"/>,<s:property value="loginId"/>)" />
								<img title="查看康美会员档案" style="cursor: pointer;" src="/etc/images/icon/kang.png"
									onclick="queryUserDetail(<s:property value="personalId"/>)" />
							</td>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'eraInfoForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
