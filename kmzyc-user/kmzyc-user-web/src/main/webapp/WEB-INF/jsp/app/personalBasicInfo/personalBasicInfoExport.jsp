<%@ page contentType="application/msexcel;charset=UTF-8" %> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String time=(String)request.getAttribute("time");
	if(time==null){
	  time="";
	}
   	response.setContentType("application/msexcel");
   	response.setHeader("Content-disposition","inline; filename="+time.replaceAll(" ","").replaceAll(":","")+".xls");
%>

<html>
	<head>
		<title>个人客户数据报表</title>
	</head>
	<body>
  		<table width="100%" align="center" border="1" cellpadding="0" cellspacing="0">
			<tr>
	   			<td colspan="9" style="font-weight: bold;font-size: 42px;text-align:center;">个人客户统计简表</td>
			</tr>
			<tr>
				<td>会员用户名</td>
				<td>已验证手机号码</td>
				<td>已验证电子邮箱</td>
				<td>会员等级</td>
				<td>可用积分</td>
				<td>注册时间</td>
				<td>默认收件人</td>
				<td>默认收获省市县镇</td>
				<td>默认收货地址</td>
			</tr>
		 	<s:iterator value="personalBasicInfoList" id="l"> 
			<tr>
				<td style="text-align:left;vnd.ms-excel.numberformat:@"><s:property value="loginAccount"/> </td>
				<td style="text-align:left;vnd.ms-excel.numberformat:@"><s:property value="mobile"/></td>
				<td style="text-align:left;"><s:property value="email"/></td>
				<td style="text-align:left;"><s:property value="levelName"/></td>
				<td style="text-align:left;vnd.ms-excel.numberformat:@"><s:property value="n_AvailableIntegral"/></td>
				<td style="text-align:left;"><s:date name="d_CreateDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td style="text-align:left;"><s:property value="name"/></td>
				<td style="text-align:left;"><s:property value="province"/><s:property value="city"/><s:property value="area"/><s:property value="town"/></td>
				<td style="text-align:left;"><s:property value="detailedAddress"/></td> 
			</tr>
			</s:iterator>  
		</table>
	</body>
</html>