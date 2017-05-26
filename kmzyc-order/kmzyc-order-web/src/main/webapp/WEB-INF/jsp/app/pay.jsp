<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- 
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
 -->
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
    $("#payForm").validate({     
	       rules: {
				"operator": {required:true},
				"account": {required:true},
				"orderMoney":{required:true,decimal1:true,max:<s:property value="notPay"/>}
    	   },
	   	   submitHandler: function (form) {
	            $.post(
	                	'/app/orderPaypayAction.action',
	               		$("#payForm").serialize(),
	 	           		function(result){
	  			            alert(result);
	  			            if(result.indexOf("失败")>0){
	  			            	history.go(0);
	  			            }else{
	  			            	location.reload();
	  			            }
	  	     			}
	            );	
          }
	});
	
 });
</script>
<form id="payForm">
	<table>
		<caption id="caption">订单支付</caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>操作人</th>
			<td><input type="text" name="operator" value="orderpay"/></td>
		</tr>
		<tr>
			<th>支付方式</th>
			<td>
				<select name="paymentWay">
					<option value="1">余额支付</option>
					<option value="2">优惠券支付</option>
					<option value="3">网银/信用卡支付</option>
					<option value="4">第三方支付</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>客户账号</th>
			<td><input type="text"  id="account" name="account" value="orderpay"></td>
		</tr>
		<tr>			
			<th>订单号</th>
			<td><input type="text"  id="orderCode" name="orderCode" readonly="readonly" value='<s:property value="orderCode"/>'/></td>
		</tr>
		<tr>
			<th>未支付金额</th>
			<td><input type="text"  id="notPay"  name="notPay" value='<s:property value="notPay"/>' disabled="disabled"/></td>
		</tr>
		<tr>
			<th>付款金额</th>
			<td><input type="text"  id="orderMoney"  name="orderMoney" value='<s:property value="notPay"/>'/></td>
		</tr>
		<tr>			
			<th>第三方支付流水号</th>
			<td><input type="text"  id="outsidePayStatementNo"  name="outsidePayStatementNo"  isrequired="true"/></td>
		</tr>
		<tr>
			<th>付款/退款标识</th>
            <td>
            	<select name="flag">
					<option value="1">付款</option>
					<option value="2">退款</option>
				</select>
            </td>
		</tr>
		<tr>
			<th>优惠券编号</th>
            <td><input type="text"  id="preferentialNo" name="preferentialNo"/></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="submit" id="submit0" class="saveBtn" value=""/>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>