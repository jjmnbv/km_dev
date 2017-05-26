<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<title>促销活动规则列表</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>

</head>
<body>
	<s:form name="queryPromotionRuleForm" method="post"
		action="/promotion/toSelectPromotionRule.action">
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">规则名称：</td>
				<td><input name="promotionRule.promotionRuleRuleName" id="promotionRuleRuleName" type="text" class="input_style"
					/></td>
				<td align="right">规则类别：
					<s:select  list="#request.promotionTypeMap" 
					headerValue="-全部-"  headerKey="" listKey="key"  listValue="value"
					 name="promotionRule.promotionTypeId"></s:select>
				</td>
				<%/**
				<td align="right">规则状态：
					<s:select  list="#{0:'已删除'}" listKey="key" listValue="value" headerKey="1" headerValue="有效" name="promotionRule.promotionRuleType"></s:select>
				</td>
				*/ %>
				<td align="center"><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="tableStyle1" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"></th>
				<th align="center">规则名称</th>
				<th align="center">规则类别</th>
				<th align="center">规则表达式</th>
				<th align="center">表达式说明</th>
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox" id="selectedId<s:property value="promotionRuleId"/>"
						name="promotionId" alt="<s:property value="promotionTypeId" />" title="<s:property value="promotionRuleRuleName"/>" value='<s:property value="promotionRuleId"/>' /></td>
					<td align="center"><s:property value="promotionRuleRuleName"/></td>
					<td align="center"><s:property value="#request.promotionTypeMap[promotionTypeId]"/></td>
					<td align="center"><s:property value="promotionRuleExpression" /></td>
					<td align="center"><s:property value="promotionRuleExplain" /></td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
		<!-- 底部 按钮条 -->
	<table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="" onclick="return save();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" class="backBtn" onClick="javascript:parent.myDialog.close();" />
			<td width="20%" align="center"></td>
		</tr>
	</table>
	</s:form>
	<script type="text/javascript">
function save(){
	var o = $('input[name="promotionId"]:checked');
	var selectedLength = o.length;
	if(selectedLength==1){
		var id = o[0].value;
		var name = o[0].title;
		var type = o[0].alt;
		parent.receiveRule(id,name,type);
		//return {id,name};
		//parent.close();
	}else{
		alert("请选择一条规则");
	}
}
if (art.dialog.data('selectedIds')) {
	var data = art.dialog.data('selectedIds');
	hanld(data);
	//document.getElementById('aInput').value = art.dialog.data('test');// 获取由主页面传递过来的数据
};
function hanld(paramsIds){
	if(paramsIds=='')return;
	var ids = paramsIds.split(",");
	for(var i=0;i<ids.length;i++){
		var id = ids[i];
		if(id){
			var markId='selectedId'+id;
			document.getElementById(markId).checked=true;
		}
	}
}
</script>
</body>
</html>

