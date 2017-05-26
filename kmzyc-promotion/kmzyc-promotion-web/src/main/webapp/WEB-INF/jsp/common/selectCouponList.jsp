<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<title>优惠券管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/common.js"></script>

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript">
function gotoAdd()
{
	//var reobjs = new Array();
	var objs = $("input[name='couponId']:checked");
	//objs.each(function(){
	//	reobjs.push(objs);
	//});
	var status = objs.attr('alt');
	if(status==5||status==6){
		alert('该优惠券已过期！');
		return ;
	}
	parent.receiveCoupon(objs);
}
function hanld(paramsIds){
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
</head>
<s:set name="parent_name" value="'优惠券管理'" scope="request" />

<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="couponFroms" method="post"
		action="/common/selectCouponList.action" style=" margin-top:9px">
		<s:hidden name="coupon.couponGivetypeId" value="3"></s:hidden>
		<input  type="hidden" name ="coupon.shopCode" value='<s:property value="coupon.shopCode"/>' />
		
		<!-- 标题条 -->
		<!--<div class="pagetitle">栏目管理:</div>
		<!-- 按钮条 -->
		<!-- <table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle">
				</td>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				<!-- </td>
			</tr>
		</table>

		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">优惠券名称:</td>
				<td><input name="coupon.couponName" type="text"
					class="input_style"
					value="<s:property value='coupon.couponName' />"></td>
			

				<td align="right">
                
                <INPUT TYPE="submit" class="queryBtn"
					value="">
                    <input class="addBtn"
					type="button" value="" onClick="gotoAdd();">
                    </td>
			</tr>
		</table>


		
		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"></th>
				<th width="5%" align="center">规则ID</th>
				<th width="10%" align="center">优惠券规则名称</th>
				<th width="10%" align="center">商家类别</th>
				<th width="10%" align="center">时间限制</th>
				<th width="12%" align="center">金额限制</th>
				<th width="12%" align="center">面值</th>
				<th width="12%" align="center">状态</th>
				<th width="12%" align="center">创建时间</th>
				
			</tr>
			<s:iterator id="coupiterator" value="page.dataList">
				<tr>
					<td align="center" width="5%">
						<input type="radio" name="couponId" value='<s:property value="couponId"/>'  title='<s:property value="couponName" />'/>
					</td>
					<td align="center"><s:property value="couponId" /></td>
					<td align="center"><s:property value="couponName" /></td>
					<td align="center"><s:property  value="#request.CouponSupplierType[supplierType]" /> 
                    
                    </td>
                    <td align="center"><s:if test="timeType==1">
                    <s:date name="starttime" format="yyyy-MM-dd HH:mm:ss"/>至 <br/>
                    <s:date name="endtime" format="yyyy-MM-dd HH:mm:ss"/>
                    </s:if><s:elseif test="timeType==2">
                    	从获得日起<s:property value="couponValidDay"/>天
                    </s:elseif>
                    </td>
					<td align="center"><s:property value="payLeastMoney" /></td>
					<td align="center"><s:property value="couponMoney" /></td>
					<td align="center"><s:property
							value="#request.CouponRuleStatus[status]" /></td>
					<td align="center"><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/></td>
					
					
					
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
    
 <!--   <a href="/app/testRemote.action">dddd</a> 
 --> 
</body>
</html>

