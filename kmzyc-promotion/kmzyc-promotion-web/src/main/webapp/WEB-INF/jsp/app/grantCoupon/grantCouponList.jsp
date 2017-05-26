<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<title>优惠券的发放记录</title>
<script type="text/javascript">
function gotoAdd()
{
	location.href="/coupon/couponShow.action?viewType=add"; 
}
//修改优惠券
function queryCouponDetail(CouponId)
{
	location.href="/coupon/queryCouponDetail.action?viewType=view&couponId="+CouponId; 
}

function gotoShow(CouponId)
{
	location.href="/coupon/queryCouponDetail.action?viewType=show&couponId="+CouponId; 
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
		url: "/coupon/canDelCoupon.action?couponProductId="+seledId,
		dataType : "json",
		success: function(data){ 
		if (data)
		{
		if(data=='weifafang')
		{
		
		location.href="/coupon/doDelCoupon.action?couponProductId="+seledId; 
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
    		location.href="/coupon/satrOrPuseCoupon.action?couponProductId="+couponId+"&startParam="+type;
    	}else{
    		return;
    	}
    		}
    	else{
    		var confrimMes = "您是否确认停用？启用之后用户注册则不能获取该条优惠券"	;	
    		if (confirm(confrimMes) == true){
    			location.href="/coupon/satrOrPuseCoupon.action?couponProductId="+couponId+"&startParam="+type;
        	}else{
        		return;
        	}
    	}
    	
    }
    
    //新建发放
    function grantCoupon(){
    	location.href="/coupon/grantCouponAdd.action";
    }
  //查看
 function gotoDetail(value){
		window.location.href="/coupon/detailCoupon.action?coupongrantSeting.couponIssuingId="+value;
	}
  function gotoStart(value){
	  $.ajax({
			type: "post",
			url: "/coupon/chechtimeOutCoupon.action?coupongrantSeting.couponIssuingId="+value,
			dataType : "json",
			success: function(data){
				if(data=="false"){
					alert("你选择的发放已经到截止时间,不能启动！");
					$("#grantCouponFormAjax").submit();
				}else if(data=="true"){
					location.href="/coupon/startGrantCoupon.action?coupongrantSeting.couponIssuingId="+value;
				}
			},
			error: function(){
				alert("处理异常！请稍后重试...");
			}
	  });
	  
  }
  function gotoPause(value){
	  location.href="/coupon/pauseGrantCoupon.action?coupongrantSeting.couponIssuingId="+value;
  }
  function gotoClose(value){
	  location.href="/coupon/closeGrantCoupon.action?coupongrantSeting.couponIssuingId="+value;
  }
  function gotoDelete(value){
	  var confrimMes = "您是否确认删除未发放的设置？"	;	
		if (confirm(confrimMes) == true){
			  location.href="/coupon/deleteCouponGrant.action?coupongrantSeting.couponIssuingId="+value;
		}else{
			return;
		}
	
  }
  function gotoEdit(value){
	  location.href="/coupon/editCouponGrant.action?coupongrantSeting.couponIssuingId="+value;
  } 
</script>
</head>
<s:set name="parent_name" value="'优惠券发放设置'" scope="request" />
 <s:set name="name" value="'列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="grantCouponFroms" method="post" id="grantCouponFormAjax"
		action="/coupon/gotoGrantCouponList.action" style=" margin:20px">
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">发放类型：</td>
				<td>
					<s:select name="coupongrantSeting.couponGivetypeId"
						id="couponGivetypeId"  list="#{1:'手工发放',2:'注册发放',6:'不记名发放'}"   headerKey=""
						headerValue="---全部类型---">
					</s:select>
				</td>
				<td align="right">规则ID:</td>
				<td>
					<input name="coupongrantSeting.couponId" type="text" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
						class="input_style" value="<s:property value='coupongrantSeting.couponId' />">
				</td>
				<td align="right">规则名称:</td>
				<td>
					<input name="coupongrantSeting.couponName" type="text"
						class="input_style" value="<s:property value='coupongrantSeting.couponName' />">
				</td>
			</tr>
			
			<tr>
				<td align="right">状态:</td>
				<td>
					<s:select name="coupongrantSeting.isStatus" id="isStatus" 
						list="#{1:'未开始',2:'已完成',3:'进行中',4:'暂停',5:'截止'}" headerKey=""
						headerValue="---全部状态--">
					</s:select>
				</td>
				<td colspan="3">
					&nbsp;
				</td>
				<td align="right">
					<input TYPE="submit" class="queryBtn" value="">
				</td>
			</tr>
			
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
					<th width="10%" align="center">发放类型</th>
				<th width="8%" align="center">规则ID</th>
				<th width="11%" align="center">优惠券规则名称</th>
				<th width="20%" align="center">发放时间</th>
				<th width="8%" align="center">状态</th>
				<th width="8%" align="center">金额限制</th>
				<th width="8%" align="center">面值</th>
				<th width="16%" align="center">操作</th>
			</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td width="43">
							<input type="checkbox" name="couponIssuingId"
								value='<s:property value="couponIssuingId"/>'>
					</td>
					<td>
						<s:if test="couponGivetypeId==1">
							手工发放
						</s:if>
						<s:elseif test="couponGivetypeId==2">
							注册发放
						</s:elseif>
						<s:elseif test="couponGivetypeId==6">
							不记名发放
						</s:elseif>
					</td>
					<td>
							<s:property value="couponId" />
					</td>
					<td>
							<s:property value="couponName" />
					</td>
					<!-- 发放时间 需要处理 <s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />-->
					<td>
						<s:if test="couponGivetypeId==2">
							<s:date name="issuingStartTime" format="yyyy-MM-dd HH:mm:ss" /><br/>
								至<br/>
							<s:date name="issuingEndTime" format="yyyy-MM-dd HH:mm:ss" />
						</s:if>
						<s:else>
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
						</s:else>
					</td>
					<td>
							<s:if test="isStatus==1">未开始</s:if>
							<s:elseif test="isStatus==3">进行中</s:elseif>
							<s:elseif test="isStatus==4">暂停</s:elseif>
							<s:elseif test="isStatus==5">截止</s:elseif>
							<s:else >已完成</s:else>
					</td>
					<td>
							<s:property value="payLeastMoney" />
					</td>
					<td>
							<s:property value="couponMoney" />
					</td>
					<!-- 操作 需要处理  -->
					<td align="center">
							<img title="查看" style="cursor:pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="couponIssuingId"/>)" />
							<s:if test="couponGivetypeId==2">
									<s:if test="isStatus==1">
										<img title="马上生效" style="cursor: pointer;" src="/etc/images/start_2.png"  onclick="gotoStart(<s:property value="couponIssuingId"/>)" />
										<img title="查看/编辑" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoEdit(<s:property value="couponIssuingId"/>)" />
										<img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="couponIssuingId"/>)" />
									</s:if>
									<s:elseif test="isStatus==3">
										<img title="暂停" style="cursor: pointer;" src="/etc/images/pause_2.png"  onclick="gotoPause(<s:property value="couponIssuingId"/>)" />
										<img title="截止" style="cursor: pointer;" src="/etc/images/stop.png"  onclick="gotoClose(<s:property value="couponIssuingId"/>)" />
									</s:elseif>
									<s:elseif test="isStatus==4">
										<img title="重新启动" style="cursor: pointer;" src="/etc/images/start_2.png"  onclick="gotoStart(<s:property value="couponIssuingId"/>)" />
									</s:elseif>
							</s:if>
					</td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
			<td><input type="checkbox" onClick="checkAll(this,'luckDrawIds')">全选  
	   		<!--  <img title="批量删除" onclick="pauseSelected('luckDrawIds');" style="cursor: pointer;" src="/etc/images/batch_delete.png" />-->
	  	    <input type="button" class="btn-custom" onClick="grantCoupon();" value="新建发放" style="cursor: pointer;"/> 
	  	    </td>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
	</s:form>
    
    <!--  <a href="/app/testRemote.action">dddd</a> --> 
 
</body>
</html>
