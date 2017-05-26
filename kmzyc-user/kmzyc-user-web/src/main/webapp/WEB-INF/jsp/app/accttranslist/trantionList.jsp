<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link rel="stylesheet" href="/etc/css/lottery.css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
function deleteSelected(id){
    var obj = document.getElementsByName(id);
            var count = 0;
            var obj_cehcked = null;
           <%--// 遍历所有用户，找出被选中的用户--%> 
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

<%--/** 单条删除充值信息  **/--%> 
function  deleteByKey(id){
     if(confirm("是否确认删除? ")==true){
       location.href="/accounts/payment_deletePayment.action?accountTransactionId="+id;
     }
}
<%-- /** 全选js  **/--%>  
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

     <%--//弹出层 选择账号--%>  
 	function popUpAccount() {
 	 
 	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","500px","iframe");
 	}
 	 <%--//关闭弹出窗口 --%> 
	function closeOpenAccount(accountId,account){
	    closeThis();
	    document.forms[0].accountId.value = accountId;
		document.forms[0].accountLogin.value = account;
	} 


	
	
	    //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");


    
    
}


	   //导出报表
	   	function exportTrationReport(){
			document.trationForm.action="/accounts/tration_exportTrationReport.action";
            document.trationForm.submit();
            document.trationForm.action="/accounts/tration_showTrationList.action";
		}
	   
</script>
</head>
<body>
<s:if test="showType==null">
<s:set name="parent_name" value="'账务管理'" scope="request"/>
<s:set name="name" value="'交易记录'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
</s:if>
  <div  style="height:90%;overflow-y:auto; " >
			<s:form name="trationForm" onsubmit="return checkAllTextValid(this)" action="/accounts/tration_showTrationList.action" method="post">
				<%--<!-- 查询条件区域 -->--%>
				<table width="98%"  class="content_table" align="center"
					cellpadding="0" cellspacing="0">
		 
						<tr>
							<td align="right" > 会员账号： </td>
							 <TD>
								<s:if test="showType!=null">
									<s:property value='bnesAcctTransListQuery.accountLogin'/>
									<s:hidden name="showType"></s:hidden>
									<input type="hidden" id="accountId"
										name="bnesAcctTransListQuery.accountId"
										value="<s:property value='bnesAcctTransListQuery.accountId'/>" />
									<input id="accountLogin"
										name="bnesAcctTransListQuery.accountLogin" type="hidden"
										class="input_stype" readonly
										value="<s:property value='bnesAcctTransListQuery.accountLogin'/>" />
								</s:if>
								<s:else>
									<input type="hidden" id="accountId"
										name="bnesAcctTransListQuery.accountId"
										value="<s:property value='bnesAcctTransListQuery.accountId'/>" />
									<input id="accountLogin"
										name="bnesAcctTransListQuery.accountLogin" type="text"
										class="input_stype"
										value="<s:property value='bnesAcctTransListQuery.accountLogin'/>" />
	<!-- 								<input type="button" value="选择" onclick="popUpAccount()"> -->
								</s:else>
							</td>
	
						<td align="right">交易类型：</td>
						<td>
						<s:select list="#request.transactionTypeEnumMap" listKey="key" listValue="value" headerKey="" headerValue="--请选择--" name="bnesAcctTransListQuery.type"></s:select>
						</td>
							
						<td align="right">交易对象：</td>
						<td>
							<select name="bnesAcctTransListQuery.trasObject">
									<option value="" <s:if test='bnesAcctTransListQuery.trasObject==""'>selected="selected"</s:if> >
										请选择
									</option>
									<option value="1" <s:if test='bnesAcctTransListQuery.trasObject=="1"'>selected="selected"</s:if>>
								    	订单交易
									</option>
									<option value="3" <s:if test='bnesAcctTransListQuery.trasObject=="3"'>selected="selected"</s:if>>
									 	支付宝
									</option>
									<option value="4" <s:if test='bnesAcctTransListQuery.trasObject=="4"'>selected="selected"</s:if>>
									 	财付通
									</option>
									<option value="8" <s:if test='bnesAcctTransListQuery.trasObject=="8"'>selected="selected"</s:if>>
									 	线下财务
									</option>
									<option value="2" <s:if test='bnesAcctTransListQuery.trasObject=="2"'>selected="selected"</s:if>>
									 	易宝
									</option>
									
							</select>
						</td>
					</tr>
					
					<tr>	
						<td align="right">交易内容：  </td>
						<td> 
					   	  <input name="bnesAcctTransListQuery.content" type="text" value="<s:property value='bnesAcctTransListQuery.content'/>">
						</td>
						<td align="right">交易流水号：</td>
					   <td> 
						  <input name="bnesAcctTransListQuery.accountNumber" type="text" value="<s:property value='bnesAcctTransListQuery.accountNumber'/>">
					   </td> 	
							
					   <td align="right"> 外部单号： </td>
					   <td> 
						  <input name="bnesAcctTransListQuery.otherOrder" type="text" value="<s:property value='bnesAcctTransListQuery.otherOrder'/>">
					   </td> 	
					</tr>
					<tr>
					   <td align="right">交易状态：</td>
					   <td>
							<select name="bnesAcctTransListQuery.status">
									<option value="" <s:if test='bnesAcctTransListQuery.status==""'>selected="selected"</s:if> >
										请选择
									</option>
									<option value="1" <s:if test='bnesAcctTransListQuery.status=="1"'>selected="selected"</s:if>>
							     	成功
									</option>
								<option value="2" <s:if test='bnesAcctTransListQuery.status=="2"'>selected="selected"</s:if>>
									 失败
									</option>
							</select>
						</td>
						
						<td align="right" >交易时间：</td>
						<td> 
						<input type="text" name="bnesAcctTransListQuery.createDate"  id="d523" class="Wdate"  value ="<s:date name = 'bnesAcctTransListQuery.createDate' format='yyyy-MM-dd HH:mm:ss' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
						</td>
						<td align="right"> 至：</td>
						<td> 
						<input type="text" name="bnesAcctTransListQuery.endDate" id="d521" class="Wdate"  value ="<s:date name = 'bnesAcctTransListQuery.endDate' format='yyyy-MM-dd HH:mm:ss' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" />
						</td>
						
						<td   align="right" colspan="2">
							<INPUT TYPE="submit" class="queryBtn" value="">
								<a href="javascript:void(0);"  onclick="exportTrationReport()" class="btn-custom">导出报表</a>
						</td>
					</tr>
				</table>
				<%--<!-- 数据列表区域 -->--%>
				<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
					<tr>
						<s:if test="showType==null">
						<th width="10%">
							会员账号
						</th>
						</s:if>
						<th width="15%">
							交易流水号
						</th>
						<th width="6%">
							交易金额
						</th>
						<th width="6%">
							交易前账户金额
						</th>
						<th width="6%">
							交易后账户金额
						</th>
						<th width="15%">
							交易内容
						</th>
						<th width="5%">
							交易类型
						</th>
						<th width="5%">
							交易状态
						</th>
                        
                        <th width="15%">
							 外部单号
						</th>
							<th width="6%">
							交易对象
						</th>
						<th width="12%">
							交易日期
						</th>
	 
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
						<s:if test="showType==null">
							<td>
								<s:property value="accountLogin" />
							</td>
							</s:if>
							<td>
								<s:property value="accountNumber" />
							</td>
							<td>
								<s:property value="%{formatDouble(moneyAmount)}" />
							</td>
							<td>
								<s:property value="%{formatDouble(beforeAmount)}" />
							</td>
							<td>
								<s:property value="%{formatDouble(afterAmount)}" />
							</td>
							<td>
								<s:property value="content" />
							</td>
							<td>
							<s:property   value="#request.transactionTypeEnumMap[type]"  />
							</td>
									<td>
								<s:if test="status==0">
未付款
</s:if>
								<s:if test="status==1">
成功
</s:if>
								<s:if test="status==2">
失败
</s:if>

							</td> 
							
						<TD>
						
						<s:property value="otherOrder" />
						</TD>
						
						<TD> 
						
														<s:if test="trasObject==1">
 订单交易
</s:if>
								<s:if test="trasObject==2">
易宝
</s:if>
								<s:if test="trasObject==3">
支付宝
</s:if>
								<s:if test="trasObject==4">
财付通
</s:if>
								<s:if test="trasObject==5">
微信
</s:if>
								<s:if test="trasObject==7">
康美通
</s:if>
								<s:if test="trasObject==8">
线下财务
</s:if>
						
						</TD>
							<td>
								<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />

							</td>

	 
						</tr>
					</s:iterator>
				</table>
		<table width="98%" align="center" class="page_table">
					<tr>
						<td>
							<s:set name="form_name" value="'trationForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
</body>
</html>
