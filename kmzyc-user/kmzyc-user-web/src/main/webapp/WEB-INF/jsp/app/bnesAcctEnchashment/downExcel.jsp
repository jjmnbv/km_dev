<%@ page contentType="application/vnd.ms-excel; charset=UTF-8"%>
<%@ page language="java" import="java.util.*,java.text.SimpleDateFormat"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
  Date now = new Date();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  String nowTime = sdf.format(now);
  String esourceName = (String)request.getSession().getAttribute("esourceName");
  
 String filename = new String((esourceName + nowTime).getBytes("UTF-8"), "ISO-8859-1");
 response.addHeader("Content-Disposition", "filename=" + filename + ".xls");
%>
<html>

<style type="text/css">
table td {
	border: 1px solid #ccc;
}

table {
	border: 1px solid #ccc;
}

</style>
<head>
<meta name="Generator" content="Microsoft Excel 11">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<s:if test="bnesAcctEnchashment.enchashmentResource ==0">
		<table style="border: 1px solid #ccc;" align="center" cellpadding="1">
			<tr style="background-color: #86DB9B;">
				<td style="border: 1px solid #ccc;"><b>序号</b></td>
				<td style="border: 1px solid #ccc;"><b>用户名</b></td>
				<td style="border: 1px solid #ccc;"><b>公司名称</b></td>
				<td style="border: 1px solid #ccc;"><b>银行户名</b></td>
				<td style="border: 1px solid #ccc;"><b>身份证号</b></td>
				<td style="border: 1px solid #ccc;"><b>开户行</b></td>
				<td style="border: 1px solid #ccc;"><b>银行账号</b></td>
				<td style="border: 1px solid #ccc;"><b>提现金额（元）</b></td>
				<td style="border: 1px solid #ccc;"><b>可用余额</b></td>
				<td style="border: 1px solid #ccc;"><b>申请时间</b></td>
				<td style="border: 1px solid #ccc;"><b>审核状态</b></td>
				<td style="border: 1px solid #ccc;"><b>审核时间</b></td>
				<td style="border: 1px solid #ccc;"><b>提现完成时间</b></td>
				<td style="border: 1px solid #ccc;"><b>交易记录明细附图</b></td>
			</tr>
			<s:iterator value="exportDateList" status="st">
				<tr>
					<td align="left"
						style='border: 1px solid #ccc; vnd .ms-excel.numberformat: @'><s:property
							value="#st.count" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="loginAccount" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="corporateName" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="bankAccountName" /></td>
					<td align="left" style="border: 1px solid #ccc;mso-number-format:'\@';"><s:property
							value="remarks" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="bankOfDeposit" /></td>
					<td align="left" style="border: 1px solid #ccc;mso-number-format:'\@';"><s:property
							value="bankCardNumber" /></td>
					<td align="right" style="border: 1px solid #ccc;mso-number-format:'\#\,\#\#0\.00';"><s:property
							value="enchashmentAmount" /></td>
					<td align="right" style="border: 1px solid #ccc;mso-number-format:'\#\,\#\#0\.00';"><s:property
							value="amountAavlibal" /></td>
					<td align="center" style="border: 1px solid #ccc;"><s:date name="createDate"  format="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="right" style="border: 1px solid #ccc;"><s:property
							value="enchashmentRemarks" /></td>
					<td align="center" style="border: 1px solid #ccc;"><s:date name="modifyDate"  format="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="center" style="border: 1px solid #ccc;"><s:date name="finashDate"  format="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="right" style="border: 1px solid #ccc;"><s:property
							value="confirmRemarks" /></td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
	<s:else>
		<table style="border: 1px solid #ccc;" align="center" cellpadding="1">
			<tr style="background-color: #86DB9B;">
				<td style="border: 1px solid #ccc;"><b>序号</b></td>
				<td style="border: 1px solid #ccc;"><b>用户名</b></td>
				<td style="border: 1px solid #ccc;"><b>微商号</b></td>
				<td style="border: 1px solid #ccc;"><b>银行户名</b></td>
				<td style="border: 1px solid #ccc;"><b>身份证号</b></td>
				<td style="border: 1px solid #ccc;"><b>开户行</b></td>
				<td style="border: 1px solid #ccc;"><b>银行账号</b></td>
				<td style="border: 1px solid #ccc;"><b>提现金额（元）</b></td>
				<td style="border: 1px solid #ccc;"><b>交易记录明细附图</b></td>
			</tr>
			<s:iterator value="exportDateList" status="st">
				<tr>
					<td align="left"
						style='border: 1px solid #ccc; vnd .ms-excel.numberformat: @'><s:property
							value="#st.count" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="loginAccount" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="vsNumber" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="bankAccountName" /></td>
					<td align="left" style="border: 1px solid #ccc;mso-number-format:'\@';"><s:property
							value="remark" /></td>
					<td align="left" style="border: 1px solid #ccc;"><s:property
							value="bankOfDeposit" /></td>
					<td align="left" style="border: 1px solid #ccc;mso-number-format:'\@';" >
						<s:property value="bankCardNumber" />
					</td>
					<td align="right" style="border: 1px solid #ccc;mso-number-format:'\#\,\#\#0\.00';"><s:property
							value="enchashmentAmount" /></td>
					<td align="right" style="border: 1px solid #ccc;"><s:property
							value="confirmRemarks" /></td>
				</tr>
			</s:iterator>
		</table>
	</s:else>
</body>
</html>
