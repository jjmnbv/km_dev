<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<title>优惠券规则管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/common.js"></script>

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript">
//添加规则
function gotoAdd()
{
	location.href="/coupon/couponRule_pageShow.action?viewType=add"; 
}
//修改优惠券
function gotoUpdate(CouponId)
{
	location.href="/coupon/couponRule_pageShow.action?viewType=view&couponId="+CouponId; 
}
//查看优惠券规则
function gotoShow(CouponId)
{
	location.href="/coupon/couponRule_pageShow.action?viewType=show&couponId="+CouponId; 
}
//发放优惠券规则跳转
function gotoPutOut(CouponId){
	location.href="/coupon/editGrant.action?coupongrantSeting.couponId="+CouponId; 
}

//单个删除优惠券规则
function gotoDelete(CouponId){
	if (confirm("您确定要删除该规则吗？")){
		  //先进行校验
		  $.ajax({
			type: "post",
			url: "/coupon/canDelCoupon.action?couponProductId="+CouponId,
			dataType : "json",
			success: function(data){ 
			if (data)
			{
			if(data=='weifafang')
			{
			location.href="/coupon/couponRule_ruleDelete.action?couponId="+CouponId; 
			}
			if(data=='yifafang')
			{
				alert('此优惠券规则已经发放，不能删除');
			}}},
			error: function(){
				//请求出错处理
				alert('出错了')
			}});
	}
}
//多个删除优惠券规则   
    function del_sect()
    {
 	 if(!checkIdSeled()){
 	alert('请选择你要删除的优惠券规则')}
 	 else{
   if (confirm("确定要删除？")){
	   //先进行校验
	  $.ajax({
		type: "post",
		url: "/coupon/canDelCoupon.action?couponProductId="+seledId,
		dataType : "json",
		success: function(data){ 
		if (data)
		{
		if(data=='weifafang')
		{
		location.href="/coupon/couponRule_ruleDelete.action?couponProductId="+seledId; 
		}
		if(data=='yifafang')
		{
			alert('此优惠券规则已经发放，不能删除');
		}}},
		error: function(){
			//请求出错处理
			alert('出错了')
		}});
		 
	   }}}
  //选中的栏目id
 	var seledId="";
    function checkIdSeled(){
		var r=false;
		var  b = "";
		$("input[name=couponId]").each(function(){
			if( $(this).attr("checked") ){
				r=true;
				b=b+this.value+",";
				return ;
			}});
		seledId=b;
		return r;}
 
    
</script>
</head>
<s:set name="parent_name" value="'优惠券规则管理'" scope="request" />
<s:set name="name" value="'规则管理'" scope="request" />
 
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="couponFroms" method="post"
		action="/coupon/couponRule_PageList.action" style=" margin-top:9px">
		<!-- 标题条 -->
		<!--<div class="pagetitle">栏目管理:</div>
		<!-- 按钮条 -->
		<table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle">
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
				<td align="right" width="10%">优惠券规则名称:</td>
				<td><input name="coupon.couponName" type="text" class="input_style" value="<s:property value='coupon.couponName' />"></td>
				<td align="right">商家类别：</td>
				<td><s:select name="coupon.supplierType"
						id="supplierType" list="#request.CouponSupplierType" headerKey=""
						headerValue="---所有---"></s:select>
				</td>
				<td align="right">状态：</td>
				<td><s:select name="coupon.status"
						id="status" list="#request.CouponRuleStatus" headerKey=""
						headerValue="---所有---"></s:select> 
				</td>

				<td align="right"><INPUT TYPE="submit" class="queryBtn"
					value="">
                    <input class="addBtn"
					type="button" value="" onClick="gotoAdd();"> <input
					class="delBtn" type="button" value="" onClick="del_sect();"></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" ><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th  align="center">规则ID</th>
				<th  align="center">优惠券规则名称</th>
				<th  align="center">商家类别</th>
				<th  align="center">时间限制</th>
				<th  align="center">金额限制</th>
				<th  align="center">面值</th>
				<th  align="center">状态</th>
				<th  align="center">创建时间</th>
				<th  align="center">操作</th>
			</tr>
			<s:iterator id="coupiterator" value="page.dataList">
				<tr>
					<td align="center" width="5%">
						<s:if test="#request.CouponRuleStatus[status]=='未发放'">
							<input type="checkbox" name="couponId" value='<s:property value="couponId"/>' />
						</s:if><s:else>
							<input type="checkbox" disabled="disabled" name="couponId" value='<s:property value="couponId"/>' />
						</s:else>
					</td>
					<td align="center"><s:property value="couponId" /></td>
					<td align="center"><s:property value="couponName" /></td>
					<td align="center"><s:property  value="#request.CouponSupplierType[supplierType]" /></td>
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
					
					
					
					<td align="center">
					<img  title="查看"  style="cursor: pointer;" src="/etc/images/button_new/jiage.png"  
             			 onClick="gotoShow(<s:property value='couponId'/>)" />
					<s:if test="#request.CouponRuleStatus[status]=='未发放'">
						<img type="button" style="cursor: pointer;" title="发放"
									src="/etc/images/button_new/kaitong.png" onClick="gotoPutOut(<s:property value='couponId'/>)">
						<img type="button" style="cursor: pointer;" title="修改"
									src="/etc/images/button_new/edit.png" onClick="gotoUpdate(<s:property value='couponId'/>)">
						<img type="button" style="cursor: pointer;" title="删除"
								src="/etc/images/button_new/delete.png" onClick="gotoDelete(<s:property value='couponId'/>)">
					</s:if> <s:elseif test="#request.CouponRuleStatus[status]=='已发放'">
						<img type="button" style="cursor: pointer;" title="发放"
									src="/etc/images/button_new/kaitong.png" onClick="gotoPutOut(<s:property value='couponId'/>)">
					</s:elseif>
					</td>
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
    
    <!--  <a href="/coupon/testRemote.action">dddd</a> --> 
 
</body>
</html>

