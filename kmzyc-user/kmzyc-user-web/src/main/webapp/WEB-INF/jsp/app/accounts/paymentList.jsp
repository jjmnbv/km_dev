<%@page contentType="text/html;charset=UTF-8" isELIgnored="false" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>账户充值信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/pageCommon.js">
</script>
		<script type="text/javascript">
function deleteSelected(id) {
	var obj = document.getElementsByName(id);
	var count = 0;
	var obj_cehcked = null;
<%-- 遍历所有用户，找出被选中的用户--%>
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    count++;
                    obj_cehcked = obj[i];
                }
            }
             if (count == 0) {
                    alert("请选择要删除的数据。");
                    return false;
             }else if(confirm('是否确认删除?')==true){ 
                
            	  document.paymentForm.action="/accounts/payment_deleteAllPayment.action ";
         	     document.paymentForm.submit();
             }
}
<%--/**  进入新增账户充值信息页面  **/--%>

function gotoAdd(){
     document.paymentForm.action="/accounts/payment_addPayments.action ";
     document.paymentForm.submit();
}
<%--/** 进入修改充值信息页面  **/--%>
function editPayment(id){
     location.href="/accounts/payment_addPayment.action?accountTransactionId="+id;
}

<%--单条删除充值信息 --%>
function  deleteByKey(id){
     if(confirm("是否确认删除? ")==true){
       location.href="/accounts/payment_deletePayment.action?accountTransactionId="+id;
     }
}
     <%-- 全选js  --%>
     function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    <%--/*var ct = ele.getAttribute("type");*/--%>
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
}

     
     <%--//弹出层 选择账号 --%>
     
 	function popUpAccount() {
 	 
 	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","500px","iframe");
 	}
 
 	<%-- //关闭弹出窗口 --%>
 	
	function closeOpenAccount(accountId,account){
	    closeThis();
	    <%--document.forms[0].accountId.value = accountId;--%>
		document.forms[0].accountLogin.value = account;
	}
	
	    //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");
    
    
}
</script>

	</head>
	<body>

		<s:set name="parent_name" value="'财务管理'" scope="request" />
		<s:set name="name" value="'充值'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="paymentForm" onsubmit="return checkAllTextValid(this)"
				action="/accounts/payment_showPayment.action" method="post">
				<%--查询条件区域  --%>
				<table width="98%" align="center"  border="0"
					class="content_table" cellpadding="0" cellspacing="0">
				 
					<tr>
						<td align="right" > 账户号： </td>
						 <TD>
							<input type="hidden" id="accountId"
								name="bnesAcctTransactionQuery.accountId"
								value="<s:property value='bnesAcctTransactionQuery.accountId'/>" />
							<input id="accountLogin"
								name="bnesAcctTransactionQuery.accountLogin" type="text"
								class="input_stype"
								value="<s:property value='bnesAcctTransactionQuery.accountLogin'/>" />
							<input type="button" class="btn-custom" value="选择" onClick="popUpAccount()">
						</td>

					
				 
						<td align="right" >开始时间：</td>
					<td> 
		<input type="text" name="bnesAcctTransactionQuery.createDate"  id="d523" class="Wdate"  value ="<s:date name = 'bnesAcctTransactionQuery.createDate' format='yyyy-MM-dd HH:mm:ss' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		</td>
		<td align="right"> 结束时间：</td>
		<TD> 
		<input type="text" name="bnesAcctTransactionQuery.endDate" id="d521" class="Wdate"  value ="<s:date name = 'bnesAcctTransactionQuery.endDate' format='yyyy-MM-dd HH:mm:ss' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" />
		</TD>
		 
								<TD align="right">交易对象：</td>
						<td>
							<select name="bnesAcctTransactionQuery.trasObject">
							
									<option value="" <s:if test='bnesAcctTransactionQuery.trasObject==""'>selected="selected"</s:if> >
										请选择
									</option>
								 
									<option value="2" <s:if test='bnesAcctTransactionQuery.trasObject=="2"'>selected="selected"</s:if>>
									 易宝
									</option>
										<option value="3" <s:if test='bnesAcctTransactionQuery.trasObject=="3"'>selected="selected"</s:if>>
									 支付宝
									</option>
							
							</select>
						</TD>	
						 
					</tr>
					
						<tr>	
					<td align="right">充值流水号  ：</td>
				   <td> 
						   <input name="bnesAcctTransactionQuery.accountNumber" type="text" value="<s:property value='bnesAcctTransactionQuery.accountNumber'/>">
						
						</td> 	
						
							<td align="right"> 外部单号：</td>
				   <td> 
						   <input name="bnesAcctTransactionQuery.otherOrder" type="text"
								value="<s:property value='bnesAcctTransactionQuery.otherOrder'/>">
						
						</td> 	
						<TD align="right">充值状态：</td>
						<td>
							<select name="bnesAcctTransactionQuery.status">
								<option value="" <s:if test='bnesAcctTransactionQuery.status==""'>selected="selected"</s:if> >请选择</option>
								<option value="0" <s:if test='bnesAcctTransactionQuery.status=="0"'>selected="selected"</s:if>>未付款</option>
								<option value="1" <s:if test='bnesAcctTransactionQuery.status=="1"'>selected="selected"</s:if>>充值成功</option>
								<option value="2" <s:if test='bnesAcctTransactionQuery.status=="2"'>selected="selected"</s:if>>充值失败</option>
								<option value="3" <s:if test='bnesAcctTransactionQuery.status=="3"'>selected="selected"</s:if>>充值准备</option>
							
							</select>
						</TD>
					<td align="right"> 	充值内容：  </td>
					<TD> 
				   <input name="bnesAcctTransactionQuery.content" type="text" value="<s:property value='bnesAcctTransactionQuery.content'/>">
					</TD>
				
					</td>
					
						<td  align="right"  >
							<INPUT TYPE="submit" class="queryBtn" value="">
						</td>
					</tr>
				 
				</table>
				<%-- 数据列表区域 --%>
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">


					<tr>
						<%-- <th width="5%">
						<input type='checkbox' name='allbox' onclick='checkAll(this)'>
					</th>--%>
						<th width="10%">
							账户号
						</th>
						<th width="10%">
							充值流水号
						</th>
						<th width="6%">
							充值金额
						</th>
						<th width="10%">
							充值状态
						</th>
						<th width="10%">
							充值内容
						</th>
						<th width="15%">
						  外部单号
						</th>
						<th width="5%">
						  交易对象
						</th>
						<th width="13%">
							充值创建日期
						</th>
					 

					</tr>


					<s:iterator id="custiterator" value="page.dataList">
						<tr>
						
							<td>

								<a href="#" onClick="queryAccountInfo(<s:property value="accountId"/>);"><s:property
										value="accountLogin" />
								</a>
							</td>
							<td>
								<s:property value="accountNumber" />
							</td>
							<td>
								<s:property value="%{formatDouble(amount)}" />
							</td>

							<td>
								<s:if test="status==0">未付款</s:if>
								<s:elseif test="status==1">成功</s:elseif>
								<s:elseif test="status==2">失败</s:elseif>
								<s:elseif test="status==3">付款中</s:elseif>
							</td>
							<td>
								<div >
									<s:property value="content" />
								</div>

							</td>
					
 				<TD>
						
						<s:property value="otherOrder" />
						</TD>
						
						<TD> 
						
			<s:if test="trasObject==1">
						 订单系统
						</s:if>
			<s:if test="trasObject==2">
						易宝
						</s:if>
	     <s:if test="trasObject==3">
						支付宝
						</s:if>
						
						</TD>
						
								<td>
								<s:date name="createDate" format="yy-MM-dd HH:mm:ss" />
							</td>
 
						</tr>
					</s:iterator>
				</table>
				<table width="500" align="right">
					<tr>
						<td>
							<s:set name="form_name" value="'paymentForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
