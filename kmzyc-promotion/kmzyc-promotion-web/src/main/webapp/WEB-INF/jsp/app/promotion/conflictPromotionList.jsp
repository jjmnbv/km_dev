<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<title>促销活动</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

</head>
<body onload="pageInit()">
<div>
<s:form method="post"
	action="/promotion/selectConflictPromotion.action">
<s:hidden name="promotion.mutexPromotionId" id="mutexPromotionId"></s:hidden>
<s:hidden type="hidden" name="promotion.promotionId" id="promotionId"/>
<input type="hidden" name="promotion.startTime" value="<s:date name='promotion.startTime'  format='yyyy-MM-dd HH:mm:ss'/>" id="startTime"/>
<input type="hidden" name="promotion.endTime" value="<s:date name='promotion.endTime'  format='yyyy-MM-dd HH:mm:ss'/>" id="endTime"/>
<s:hidden name="promotion.shopSort" id="shopSort"></s:hidden>
<s:hidden name="promotion.supplierId" id="supplierId"></s:hidden>
<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				
				<td align="right">活动名称：<input name="promotion.promotionName" id="promotionName" type="text" 
					class="input_style" value="<s:property value='promotion.promotionName' />"></td>
				<td align="right">审核状态：
					<s:select list="#{0:'全部',1:'未审核',2:'已审核'}" listKey="key" listValue="value"
					  name="promotion.status"></s:select>
				</td>
				<td align="right">活动类型:
					<s:select list="%{#request.promotionTypeMap}" headerKey="" headerValue="全部" listKey="key" listValue="value"
					  name="promotion.promotionType"></s:select>
				</td>
				<td align="right" colspan="1"><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>
</s:form>
		<!-- 数据列表区域 -->
		<table width="94%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type="checkbox" id="checkAll" onclick="checkAll(this)"/></th>
				<th align="center">活动名称</th>
				<th align="center">筛选产品类别</th>
				<th align="center">开始日期</th>
				<th align="center">结束日期</th>
				<th align="center">活动类型</th>
				<th align="center">优先级</th>
			</tr>
			<s:iterator  value="#request.dataList" var="o">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox" name="promotionId" 
					onclick="changeThisPriority()" id='checkbox<s:property value="promotionId"/>' title='<s:property value="promotionName"/>' value='<s:property value="promotionId"/>'/></td>
					<td align="center"><s:property value="promotionName"/></td>
					<!-- 1:'全场',2:'指定商品',3:'商品类目',4:'商品品牌' -->
					<td align="center">
						<s:if test="productFilterType==1">全场</s:if>
						<s:elseif test="productFilterType==2">指定商品</s:elseif>
						<s:elseif test="productFilterType==3">商品类目</s:elseif>
						<s:elseif test="productFilterType==4">商品品牌</s:elseif>
					</td>
					<td align="center"><s:date name="startTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center"><s:date name="endTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="left">
						<s:property value="#request.promotionTypeMap[promotionType]"/>
					</td>
					<td align="left" title="promotionPriorityTd" id='promotionId<s:property value="promotionId"/>'>
						<s:property value='promotionPriority'/> 
					</td>
				</tr>
			</s:iterator>
		</table>
		<br/>
		<br/>
		<span><font color="red">
		&nbsp;&nbsp;同一个类型的活动，系统自动处理为互斥。<br>
		&nbsp;&nbsp;勾选的活动将设置为和本次活动相互排斥，即同一活动商品同一时间只能参与勾选的和本次活动中优先级最高的一个。
		</font>
		</span>
		<!-- 底部 按钮条 -->

	<table width="94%" align="center" class="edit_bottom" height="10px"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		<tr>
			<td align="center"><INPUT class="button" TYPE="button"
				value="确认" onclick="return save();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="button" value="取消" class="button" onClick="javascript:parent.myDialog.close();" />
			</td>
		</tr>
	</table>
	</div>
</body>
<script type="text/javascript">
function getPriority(){
	var ids = new Array();
	return $('input[name="promotionId"]:checked');
}
function checkInit(mutexPromotionId){
	if(mutexPromotionId=='all'){
		$("#mutexAll").attr('checked',true);
		return;
	}
	var arrayId = mutexPromotionId.split(",");
	for(var i=0;i<arrayId.length;i++){
		var checkboxid = "#checkbox"+arrayId[i];
		if($(checkboxid).attr('disabled')=='disabled'){
			
		}else{
			$(checkboxid).attr('checked',true);
		}
	}
}
function pageInit(){
	var mutexPromotionId = $("#mutexPromotionId").val();
	if(mutexPromotionId=='')return;
	checkInit(mutexPromotionId);
	
}
function changeThisPriority(){
	var checkedCount = $('input[name="promotionId"]:checked').length;
	var chedeTotal= $('td[title="promotionPriorityTd"]').length;
	$("#checkAll").attr("checked",checkedCount==chedeTotal);
}
function checkAll(o){
	$('input[name="promotionId"]').each(function(){
			this.checked = o.checked;

	});
}
function save(){
	var mutexPromotionId = "";
	var ids = getPriority();
	if(ids.length<=0){
		alert("老大，你还没选互斥的活动啊！");
		return;
	}
	parent.receiveConflicId(ids);
}
if (art.dialog.data('selectedIds')) {
	var data = art.dialog.data('selectedIds');
	checkInit(data);
	//document.getElementById('aInput').value = art.dialog.data('test');// 获取由主页面传递过来的数据
};
if (art.dialog.data('readOnly')) {
	$(".edit_bottom").hide();
};
</script>
</html>

