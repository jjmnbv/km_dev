<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<title></title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>
</head>
<s:set name="parent_name" value="'促销系统'" scope="request"/>
<s:set name="name" value="'加价购商品组合列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="sectionsForm" method="post"
		action="queryPromotionList" namespace="/promotion">
		<s:hidden name="promotion.nature" value="2" id="nature"></s:hidden>
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 按钮条 -->
		<table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle"><input class="addBtn"
					type="button" value="" onClick="add();"> 
				</td>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				</td>
			</tr>
		</table>
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">组合名称：<input name="promotion.promotionName" id="promotionName" type="text" 
					class="input_style" value="<s:property value='promotion.promotionName' />"></td>
				<td align="right" colspan="1"><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="3%"></th>
				<th align="center" width="20%">组合名称</th>
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="radio" 
						name="promotionId" value='<s:property value="promotionId"/>' />
					</td>
					<td align="center"><s:property value="promotionName"/></td>
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
	</s:form>
</body>
<script type="text/javascript">
	function add(){
		var obj = $("input[type='radio']:checked");
		var promotionId = obj.val();
		if(promotionId){}else{
			alert("请选择一个组合！");
			return;
		}
		var name = obj.parent().next().html();
		parent.receiveIncrease(promotionId,name);
	}
</script>
</html>

