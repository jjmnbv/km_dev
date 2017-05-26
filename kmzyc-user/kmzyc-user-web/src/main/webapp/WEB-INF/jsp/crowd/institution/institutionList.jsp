<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构查询</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
function gotoUpdate(id){
	  location.href="/crowd/institutionUpdate.action?institutionInfo.id="+id;
}
function gotoDetail(id){
	  location.href="/crowd/institutionDetail.action?institutionInfo.id="+id;
}
function submitForm(){
	document.getElementsByName("page.pageNo")[0].value=1;
	$("#InstitutionForm").submit();
}
</script>
</head>
<body>
	<!-- 标题条 -->
	<s:set name="parent_name" value="'机构管理'" scope="request" />

	<s:set name="name" value="'机构列表'" scope="request" />

	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<div style="height: 90%; overflow-y: scroll;">
		<form id="InstitutionForm" name="InstitutionfoForm"
			action="/crowd/queryInstitutionList.action"
			onsubmit=" return checkAllTextValid(this)" method="post">
			<input type="hidden" id="edit" name="edit"
				value="<s:property value="edit"/>">
			<!-- 查询条件区域 -->
			<table width="98%" height="100" class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="8">
						<!--  <input class="addBtn" type="button" value="" onclick="gotoAdd();"> -->
						<%-- <input class="delBtn" type="button" value=""  onclick="deleteSelected('n_AccountIds');">--%>
					</td>
				</tr>

				<tr>
					<td align="right">机构编码：</td>
					<td><input name="institutionInfo.institutionCode" type="text"
						value="<s:property value='institutionInfo.institutionCode'/>">
					</td>
					<td align="right">机构名称：</td>
					<td><input name="institutionInfo.institutionName" type="text"
						value="<s:property value='institutionInfo.institutionName'/>">
					</td>
					<td align="right">验证手机：</td>
					<td><input name="institutionInfo.mobile"
						type="text"
						value="<s:property value='institutionInfo.mobile'/>">
					</td>
					<td align="right">业务员：</td>
					<td><input name="institutionInfo.bagmanName" type="text"
						value="<s:property value='institutionInfo.bagmanName'/>">
					</td>

				</tr>
<tr>
					<td align="right">状态：</td>
					<td><select name="institutionInfo.status">
							<option value=""
								<s:if test='institutionInfo.status==""'>selected="selected"</s:if>>
								请选择</option>
							<option value="0"
								<s:if test='institutionInfo.status=="0"'>selected="selected"</s:if>>
								无效</option>
							<option value="1"
								<s:if test='institutionInfo.status=="1"'>selected="selected"</s:if>>
								有效</option>
					</select></td>
                    					<td align="right">有效开始时间从：</td>
					<td><input type="text" id="d523" readonly
						class="Wdate"
						value="<s:date name = 'institutionInfo.spreadStartDate' format='yyyy-MM-dd HH:mm:ss' />"
						name="institutionInfo.spreadStartDate"
						onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					至：<input type="text" id="d524" readonly
						class="Wdate"
						value="<s:date name = 'institutionInfo.spreadEndDate' format='yyyy-MM-dd HH:mm:ss' />"
						name="institutionInfo.spreadEndDate"
						onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</td>
                    					<td align="left" colspan="8"><INPUT TYPE="button" onClick="submitForm()" 
						class="queryBtn" value=""></td>
</tr>
			</table>


			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" bordercolor="#C1C8D2">
				<tr>
					<%--<th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_AccountIds')">
		</th> --%>
					<th align="center">机构编码</th>
					<%--<th align="center" >登录账号</th> --%>
					<th align="center">机构名称</th>
					<th align="center">验证手机</th>
					<th align="center">引荐注册人数</th>
					<th align="center">注册分利总现金</th>
					<th align="center">业务员</th>
					<th align="center">有效开始时间</th>
					<th align="center">审核人</th>
					<th align="center">审核时间</th>
					<th align="center">激活状态</th>
					<th align="center">状态</th>
					<th align="center">操作</th>

				</tr>
				<s:iterator id="accountiterator" value="page.dataList">
					<tr>
						<%--<td align="center" width="5%">
		    <input type="checkbox"  name="n_AccountIds"  value='<s:property value="n_AccountId"/>' />
		</td> --%>

						<td align="center"><s:property value="institutionCode" /></td>
						<td align="center"><s:property value="institutionName" /></td>
						<td align="center"><s:property value="mobile" /></td>
						<td align="center"><s:property value="referrerNum" /></td>
						<td align="center"><s:property value="referrerSum" /></td>
						<td align="center"><s:property value='bagmanName' /></td>
						<td align="center"><s:date name="spreadStartDate"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><s:property value="auditorName" /></td>
						<td align="center"><s:date name="auditeDate"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td align="center"><s:if test="actState==0">待激活</s:if> <s:elseif
								test="actState==1">已激活</s:elseif></td>
						<td align="center"><s:if test="status==0">无效</s:if> <s:elseif
								test="status==1">有效</s:elseif></td>
						<td width="100px;"><img title="查看" style="cursor: pointer;" src="/etc/images/icon/search.png" onClick="gotoDetail(<s:property value="id"/>)">
                         
							<img title="修改" style="cursor: pointer;" src="/etc/images/icon/xiugai.png" onClick="gotoUpdate(<s:property value="id"/>)"> <%-- <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_AccountId"/>)"/>--%>
						</td>

					</tr>
				</s:iterator>
			</table>

			<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'InstitutionfoForm'"
							scope="request"></s:set> <jsp:include
							page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>
		</form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</body>
</html>

