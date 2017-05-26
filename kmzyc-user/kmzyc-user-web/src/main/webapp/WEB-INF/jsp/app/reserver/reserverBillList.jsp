<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
	//点击手动生成账单事件
	function clickAdd(){
		  var tbl = document.getElementById("chooseTable");
          if (tbl.style.display == "block") {
        	  tbl.style.display = "none";
          } else if (tbl.style.display == "none") {
        	  tbl.style.display = "block" ;
          }
	}
	//生成账单提交
	function newBillSubmit(){
		//点击提交隐藏生成账单条件框
		document.getElementById("chooseTable").style.display = "none";
		location.href="/userInfo/reserverBill_billAdd.action";
		document.reserverBillAddForm.submit();
	}
	//弹出层 选择账号
	function choose() {
		dialog("选择账号", "iframe:/userInfo/reserverBill_chooseReserver.action", "900px",
				"460px", "iframe");
	}
	//关闭弹出窗口 
	function closeOpenAccount(accountLoginName) {
		closeThis();
		document.getElementById("chooseLoginName").value=accountLoginName;
	}
	
</script>
</head>
<body>

<s:set name="parent_name" value="'预备金管理'" scope="request" />
<s:set name="name" value="'账单列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form name="reserverBillAddForm"  action="/userInfo/reserverBill_billAdd.action" method="post">
	<s:token></s:token>
	<table id="chooseTable" style="display: none;" width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
		<tr>
			<td align="right">时间:</td>
			<td ><input type="text" name="reserverBill.startDateStarttime"  id="d523" class="Wdate"  value ="<s:date name = 'reserverBill.startDateStarttime' format='yyyy-MM-dd' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"  />
			至<input type="text" name="reserverBill.startDateEndtime" id="d521" class="Wdate"  value ="<s:date name = 'reserverBill.startDateEndtime' format='yyyy-MM-dd' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d523\')}',maxDate:'%y-%M-%d'})" /></td>
			<td align="right">用户名:</td>
			<td><input type="text" id="chooseLoginName" name="reserverBill.accountLogin" value='<s:property value="reserverBill.accountLogin"/>'/>
			<input type="button" value="选择账号"  style="height: 30px;" onClick="choose()"></td>
			<td align="right"><INPUT TYPE="submit"   style="height: 30px;" value="提交" onClick="newBillSubmit()"></td>
		</tr>
	</table>
</s:form>
<!-- 查询条件区域 -->
<s:form name="reserverBillForm"  action="/userInfo/reserverBill_PageList.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	
	<tr>
		<td align="right">用户名:</td>
		<td><input name="reserverBill.accountLogin" type="text" value='<s:property value="reserverBill.accountLogin"/>'></td>
		<td align="right">结算日期:</td>
		<td width="40%"><input type="text" name="reserverBill.settlementDateStarttime"  id="d523" class="Wdate"  value ="<s:date name = 'reserverBill.settlementDateStarttime' format='yyyy-MM-dd' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		至<input type="text" name="reserverBill.settlementDateEndtime" id="d521" class="Wdate"  value ="<s:date name = 'reserverBill.settlementDateEndtime' format='yyyy-MM-dd' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d523\')}'})" /></td>
		<td align="right">是否已逾期:</td>
		<td style="padding-left: 2px"><select name="reserverBill.overduePay" style="width:75">
			<option value="" <s:if test='reserverBill.overduePay==""'>selected="selected"</s:if>>所有</option>
			<option value="1" <s:if test='reserverBill.overduePay=="1"'>selected="selected"</s:if>>是</option>
			<option value="2" <s:if test='reserverBill.overduePay=="2"'>selected="selected"</s:if>>否</option>
		</select></td>
	</tr>
	<tr>
		<td align="right">公司名:</td>
		<td><input name="reserverBill.corporateName" type="text" value='<s:property value="reserverBill.corporateName"/>'></td>
		<td align="right">开始日期</td>
		<td><input type="text" name="reserverBill.startDateStarttime"  id="d523" class="Wdate"  value ="<s:date name = 'reserverBill.startDateStarttime' format='yyyy-MM-dd' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		至<input type="text" name="reserverBill.startDateEndtime" id="d521" class="Wdate"  value ="<s:date name = 'reserverBill.startDateEndtime' format='yyyy-MM-dd' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d523\')}'})" />	</td>
		<td align="right"><INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;&nbsp;<input type="button"  class="addBtn" onClick="clickAdd()"/></td>	
	</tr>
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >用户名</th>
		<th align="center" >公司名</th>
		<th align="center" >账单名</th>
		<th align="center" >开始日期</th>		
		<th align="center" >结算日期</th>		
		<th align="center" >还款日期</th>
		<th align="center" >本期账单</th>
		<th align="center" >本期应还</th>
		<th align="center" >本期已还</th>
		<th align="center" >提前还款</th>
		<th align="center" >上期应还</th>
		<th align="center" >上期已还</th>
		<th align="center" >本期调整金额</th>
		<th align="center" >是否逾期</th>
	</tr>
  <s:iterator id="custiterator" value="page.dataList">
	<tr>
		<td align="center">
		  <s:property value="accountLogin" />
		</td>
		<td align="center">
		  <s:property value="corporateName" />
		</td>
		<td align="center">
		  <s:property value="billName" />
		</td>
		<td align="center">
		<s:date name = "startDate" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		<s:date name = "settlementDate" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		    <s:date name = "repayDate" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(bill)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(repay)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(currentRepayed)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(advanceRepayed)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(lastPriodRepay)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(lastPriodHavepay)}" />
		</td>
		<td align="center">
		 ￥<s:property value="%{formatDouble(missPay)}" />
		</td>
		<td align="center">
		  <s:if test="%{overduePay==1}">
			    是
		  </s:if>
		  <s:elseif test="%{overduePay==2}">
		  	    否
		  </s:elseif>
		</td>
		</tr>
	</s:iterator>
</table>
<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'reserverBillForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

