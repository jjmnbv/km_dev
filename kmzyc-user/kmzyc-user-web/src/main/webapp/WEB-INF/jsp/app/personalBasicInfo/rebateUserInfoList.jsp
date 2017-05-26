<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>个人信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/warehouse/purchaseInfo.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$(".hideTr").hide();
			});
		
		    document.onkeydown=function(e){
		    	if (e.keyCode == 13) {
			    	querySubmit();
		    	}
			};
		    
		    //查询提交
		    function querySubmit(){
		    	 document.personalBasicInfoForm.action="/userInfo/queryRebateUserInfo.action";
		    	 document.personalBasicInfoForm.submit();
		    }
		    function showAddress(id){
		    	if($("."+id).css("display")=="none"){
		    	$("."+id).show(); 
		    	$(".hideTr").css('background-color','yellow');
		    	}
		    	else{
		    		$("."+id).hide(); 
		    	}
		    }
			function gotoOrder(name){
		    	var url = "<%=ConfigurationUtil.getString("order_url_path")%>/app/order51FanliListByMap.action?map['customerAccount']="+name;
		    	window.location.href = url;
		    }
		</script>
	</head>
	<body>
	<s:set name="parent_name" value="'返利网用户管理'" scope="request" />
		<s:set name="name" value="'返利网用户列表'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<div  style="height:90%;overflow-y:auto; " >
		<s:form  name="personalBasicInfoForm" action="/userInfo/queryRebateUserInfo.action" onsubmit=" return checkAllTextValid(this)" method="post">
		<s:if test="'updateOK'.equals(rtnMessage)">
			<script language="javascript">
				alert("修改个人信息成功!");
			</SCRIPT>
		</s:if>
		<s:if test="'addOK'.equals(rtnMessage)">
		    <script language="javascript">
				alert("新增个人成功!");
			</SCRIPT>
		</s:if>
		<s:if test="'deleteOK'.equals(rtnMessage)">
		    <script language="javascript">
				alert("删除个人成功!");
			</script>
		</s:if>
		<table width="98%" height="100" class="content_table" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="right">用户名：</td>
				<td><input name="personalBasicInfo.name" type="text" value="<s:property value='personalBasicInfo.name'/>"></td>
				<td align="right">手机号码：</td>
				<td><input name="personalBasicInfo.mobile" type="text" value="<s:property value='personalBasicInfo.mobile'/>"></td>
				<%-- <td align="right">邮件地址：</td>
				<td><input name="personalBasicInfo.email" type="text" value="<s:property value='personalBasicInfo.email'/>"></td> --%>
			</tr>
			<tr>
				<td align="right">创建日期从：</td>
				<td><input type="text" id="d523" class="Wdate"  value ="<s:date name = 'personalBasicInfo.d_CreateDate' format='yyyy-MM-dd HH:mm:ss' />" name="personalBasicInfo.d_CreateDate"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				<td align="right">至：</td>
				<td><input type="text" id="d524" class="Wdate"  value ="<s:date name = 'personalBasicInfo.endDate' format='yyyy-MM-dd HH:mm:ss' />"    name="personalBasicInfo.endDate"  onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				<td align="right" colspan="4"><input type="button" class="queryBtn" value="" onclick="querySubmit()"></td>
				<td><a href="javascript:void(0);"  onclick="gotoOrder('logac235');">相关订单</a> </td>
			</tr>
		</table>
		
		<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" >会员账号</th>
				<th align="center" >用户名</th>
				<th align="center" >手机号</th>
				<!-- <th align="center" >邮箱地址</th> -->
				<th align="center" >创建日期</th>
				<!--<th align="center" >佣金</th>  -->
				<th align="center" >操作</th>
			</tr>
			<s:iterator id="periterator"  value="page.dataList">
			<tr>
				<td align="center"><s:property value="loginAccount"/></td>
				<td align="center"><s:property value="name"/></td>
				<td align="center"><s:property value="mobile"/></td>
			<%-- 	<td align="center"><s:property value="email"/></td> --%>
				<td align="center"><s:date name="d_CreateDate" format="yy-MM-dd HH:mm:ss" /></td>
				<!--<td align="center"><s:property value="n_TotalIntegral"/></td>-->
				<td>
					<a href ="javascript:void(0)" onclick="showAddress(<s:property value="n_LoginId"/>);" >收货地址（<s:property value='addressList.size'/>）<b class="down"></b></a>
				     &nbsp;&nbsp;&nbsp;
				    <a href="javascript:void(0);"  onclick="gotoOrder('<s:property value="loginAccount"/>');">相关订单</a> 
				</td>
			</tr>
			<tr class="hideTr <s:property value="n_LoginId"/>" id ="tr_<s:property value="n_LoginId"/>" >
			    <th style="background-color:#BEBEBE;" align="center" >收件人</th>
				<th style="background-color:#BEBEBE;" align="center" >收件地址</th>
				<th style="background-color:#BEBEBE;" align="center" >详细地址</th>
				<th style="background-color:#BEBEBE" align="center" >电话</th>
				<th style="background-color:#BEBEBE"  align="center" >邮编</th>
	 		</tr>
			<s:iterator id="periteratorA"  value="addressList">
			<tr class="hideTr <s:property value="n_LoginId"/>" >
				<td align="center" ><s:property value="name"/></td>
				<td align="center"><s:property value="province"/>&nbsp;<s:property value="city"/>&nbsp;<s:property value="area"/></td>
				<td align="center"><s:property value="detailedAddress"/></td>
				<td align="center"><s:property value="cellphone"/></td>
				<td align="center"><s:property value="postalcode"/></td>
			</tr>
			</s:iterator>
			</s:iterator>
		</table>
		<table width="98%"   align="center" class="page_table">
			<tr>
				<td>
					<s:set name="form_name"  value="'personalBasicInfoForm'"  scope="request"></s:set>
					<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
				</td>
			</tr>
		</table>
		</s:form>
		</div>
	</body>
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</html>
