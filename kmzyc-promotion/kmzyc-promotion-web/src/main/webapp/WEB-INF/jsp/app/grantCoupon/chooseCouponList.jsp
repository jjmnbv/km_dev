<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js">
</script>
<script type="text/javascript" src="/etc/js/common.js">
</script>
<script type="text/javascript" >

function couponRule()
{
	 if(!checkIdSeled())
 		 {
 		 alert('请您选择对应的优惠券规则')
 		 }
 	 else
 		 {
   if (confirm("您确定要选择这些优惠券规则吗？"))
	   {
	 parent.closeThis();
	 $.ajax({
		type: "post",
		url: "/coupon/getReturnRule.action?couponId="+select_name,
		dataType : "json",
		success: function(data){
	 		window.$ = window.parent.$;
	 		var arrData=data.split(",");
	 		$("#couponIdHid").val(arrData[0]);
			$("#couponNameShow").html(arrData[1]);
			if(arrData[3]!=""&&arrData[3]!=null&&arrData[3]!="null"){
				$("#startime").html("开始时间必须小于规则有效结束时间("+arrData[4]+")");
			}
			if(arrData[4]!=""&&arrData[4]!=null&&arrData[4]!="null"){
				$("#endtime").html("结束时间必须小于规则有效结束时间("+arrData[4]+")");
			}
		},
		error: function(){
			//请求出错处理
			alert('出错了')
		}});
	   }}}
 var select_name="";
 var select_id="";
    function checkIdSeled(){
		var r=false;
		var  b = "";var c="";
		$("input[name=couponId]").each(function(){
			if( $(this).attr("checked") ){
				r=true;
			 	b=$(this).attr("value");
				c=c+$(this).attr("id")+",";
				return ;
			}});
		select_name=b;
		select_id=c;
		return r;}

</script>
<html>
	<head>
		<title>优惠券规矩管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	</head>
	<body >
		<s:form name="couponRuleForm"
			action="/coupon/chooseCouponRule.action" method="post">
            <input  type="hidden" name="haveChoosedLev" id="haveChoosedLev" value="<s:property value='haveChoosedLev' />" />
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr width="60%">
					<td >
						规则ID：
						<input name="coupon.couponId" type="text"  
							value="<s:property value='coupon.couponId'/>">
					</td >
					<td >
						规则名称：
						<input name="coupon.couponName" type="text"  
							value="<s:property value='coupon.couponName'/>">
					</td >
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="43">
						
					</th>
					<th width="178">
						规则ID
					</th>
					<th width="247">
						优惠券规则名称
					</th>
					<th width="368">
						时间限制
					</th>
						<th width="368">
						金额限制
					</th>
					<th width="368">
						面值
					</th>
					<th width="368">
						状态
					</th>
				</tr>
                
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td width="43">
							<input type="radio" name="couponId"
								value='<s:property value="couponId"/>'>
						</td>
						<td>

							<s:property value="couponId" />
						</td>
						<td>
							<s:property value="couponName" />
						</td>
						<td>
							<s:property value="valid" />
						</td>
						<td>
							<s:property value="payLeastMoney" />
						</td>
						<td>
							<s:property value="couponMoney" />
						</td>
						<td>
							<s:if test="status==1">
								未发放
							</s:if>
							<s:elseif test="status==5">
								已过期
							</s:elseif>
							<s:else>
								已发放
							</s:else>
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
		<!-- 其他页面引用区 -->
 
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td style="text-align:center">
	    <input type="button" name="choose"  class="btn-custom btnStyle" id="ok_choose" value="确定选择" onClick="couponRule()"></td>
	</tr>
</table>
 
		
	</s:form>
		
		</div>
	</body>
</html>



