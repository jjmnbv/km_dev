<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript">
      
function couponCustom()
{
	 if(!checkIdSeled())
 		 {
 		 alert('请您选择对应的会员')
 		 }
 	 else
 		 {
   if (confirm("您确定要选择这些会员吗？"))
	   {
	 parent.closeThis();
	 var havechoosed   =  $("#cus").val();
	 $.ajax({
		type: "post",
		url: "/coupon/getRetruncustom.action?customArry="+select_name+"&haveChoosedLev="+havechoosed,
		dataType : "json",
		success: function(data){
 		window.$ = window.parent.$;
		$("#customContent").html("")
		$("#customContent").append(data);
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
		$("input[name=customLoginId]").each(function(){
			if( $(this).attr("checked") ){
				r=true;
			 	b=b+$(this).attr("value")+",";
				c=c+$(this).attr("id")+",";
				return ;
			}});
		select_name=b;
		select_id=c;
		return r;}
  
</script>
</head>
<body>
<s:form  name="accountInfoForm" action="/coupon/chooseCouponcustom.action" 
    onsubmit="return checkAllTextValid(this)" method="post">
  <input  type="hidden" name="haveChoosedCustom"  id="cus" value="<s:property value='haveChoosedCustom' />"  /> 
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right">登录账号：</td>
		<td>
		     <input name="user.loginAccount" type="text" value="<s:property value='userInfoDO.loginAccount'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="user.mobile" type="text" value="<s:property value='userInfoDO.mobile'/>">
		</td>
	</tr>
	<tr>
		<td align="right">真实姓名：</td>
		<td>
		     <input name="user.name" type="text" value="<s:property value='userInfoDO.name'/>">
		</td>
		<td></td>
		<td align="right">
			<input type="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='customLogin'  onclick="checkAll(this,'levelId')"></th>
		<th align="center" >登录账号</th>
		<th align="center"  width="80">客户类别</th>
		<th align="center" >真实姓名</th>
		<th align="center" >电子邮箱</th>
		<th align="center" >手机号码</th>
		<th align="center"  width="80" >账号状态</th>
	</tr>
			<s:iterator id="custiterator" value="page.dataList">
	<tr>
	     <td>
	      <s:if test="status==1">
		   	<input type="checkbox" name="customLoginId"
								value='<s:property value="loginId"/>'>        
	       </s:if>
	     </td>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		     <s:property value="customerTypeName"/>  
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="email"/>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<td align="center">
		      <s:if test="status==1">
		             正常
		      </s:if>
		      <s:elseif test="status==0">
		             禁用
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
 
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td style="text-align:center">
	    <input type="button" name="choose"  class="btn-custom btnStyle" id="ok_choose" value="确定选择" onClick="couponCustom()"></td>
	</tr>
</table> 
 
</s:form>
</body>
</html>

