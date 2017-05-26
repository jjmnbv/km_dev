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
function gotoAdd()
{
	location.href="/app/couponShow.action?viewType=add"; 
}
//修改优惠券
function queryCouponDetail(CouponId)
{
	location.href="/app/queryCouponDetail.action?viewType=view&couponId="+CouponId; 
}

function gotoShow(CouponId)
{
	location.href="/app/queryCouponDetail.action?viewType=show&couponId="+CouponId; 
}
//删除   
    function del_sect()
    {
 	 if(!checkIdSeled()){
 	alert('请选择你要删除的优惠券规则')}
 	 else{
   if (confirm("您确定要删除这条数据吗？")){
	   //先进行校验
	  $.ajax({
		type: "post",
		url: "/app/canDelCoupon.action?couponProductId="+seledId,
		dataType : "json",
		success: function(data){ 
		if (data)
		{
		if(data=='weifafang')
		{
		
		location.href="/app/doDelCoupon.action?couponProductId="+seledId; 
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
    //启用注册类型的优惠券
    function startValide (couponId, type){
    	if (type == '1'){
    	var confrimMes = "您是否确认启用？启用之后用户注册则可以获取该条优惠券"	;
    	if (confirm(confrimMes) == true){
    		location.href="/app/satrOrPuseCoupon.action?couponProductId="+couponId+"&startParam="+type;
    	}else{
    		return;
    	}
    		}
    	else{
    		var confrimMes = "您是否确认停用？启用之后用户注册则不能获取该条优惠券"	;	
    		if (confirm(confrimMes) == true){
    			location.href="/app/satrOrPuseCoupon.action?couponProductId="+couponId+"&startParam="+type;
        	}else{
        		return;
        	}
    	}
    	
    }
    
</script>
</head>
<s:set name="parent_name" value="'优惠券规则管理'" scope="request" />
<s:set name="name" value="'规则管理'" scope="request" />
 
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="couponFroms" method="post"
		action="/app/gotoQueryCouponList.action" style=" margin-top:9px">
		<!-- 标题条 -->
		<!--<div class="pagetitle">栏目管理:</div>
		<!-- 按钮条 -->
		<table width="98%" align="center" class="topbuttonbar" 
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
				<td align="right">优惠券规则名称:</td>
				<td><input name="coupon.couponName" type="text"
					class="input_style"
					value="<s:property value='coupon.couponName' />"></td>
				<td align="right">发放类型：</td>
				<td><s:select name="coupon.couponGivetypeId"
						id="couponGivetypeId" list="#request.CouponGrantType" headerKey=""
						headerValue="---全部类型---">
					</s:select></td>

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
				<th align="center" width="5%"></th>
				<th width="10%" align="center">优惠券规则名称</th>
				<th width="13%" align="center">发放类型</th>
				<th width="11%" align="center">优惠券抵扣金额</th>
				<th width="10%" align="center">优惠券规则状态</th>
				<th width="8%" align="center">优惠券最低消费金额</th>
				<th width="8%" align="center">是否有效</th>
				<th width="22%" align="center">操作</th>
			</tr>
			<s:iterator id="coupiterator" value="page.dataList">
				<tr>
					<td align="center" width="5%"><input type="radio"
						name="couponId" value='<s:property value="couponId"/>' /></td>
					<td align="center"><s:property value="couponName" /></td>
					<td align="center"><s:property  value="#request.CouponGrantType[couponGivetypeId]" /> 
                    
                    </td>
					<td align="center"><s:property value="couponMoney" />元</td>
					<td align="center"><s:property
							value="#request.CouponStatus[status]" /></td>
					<td align="center"><s:property value="payLeastMoney" /></td>
					<td align="center">
					<s:if test="isValide==1">
						无效
					 </s:if>
					 <s:else>有效</s:else>
					</td>
					<td align="center">
					<s:if test="#request.CouponStatus[status]=='未发放'">
						<img type="button" style="cursor: pointer;" title="修改"
								src="/etc/images/button_new/edit.png" onClick="queryCouponDetail(<s:property value='couponId'/>)">
					</s:if> 
                    <img  title="查看"  style="cursor: pointer;" src="/etc/images/button_new/select.png"  
             			 onClick="gotoShow(<s:property value='couponId'/>)" />
             		<s:if test="couponGivetypeId==2">
             			<s:if test="isValide==1">
             			<img type="button" style="cursor: pointer;" title="启用" 
								src="/etc/images/little_icon/tijiao.png" onClick="startValide(<s:property value='couponId' />,<s:property value='isValide' />)">
             			</s:if>
             			<s:else>
             			<img type="button" style="cursor: pointer;" title="停用"
								src="/etc/images/little_icon/chehui.png" onClick="startValide(<s:property value='couponId' />,<s:property value='isValide' />)">
             			</s:else>
             		</s:if>
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
    
    <!--  <a href="/app/testRemote.action">dddd</a> --> 
 
</body>
</html>

