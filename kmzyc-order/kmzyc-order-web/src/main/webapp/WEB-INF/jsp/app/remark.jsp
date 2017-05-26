<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
    $("#remarkForm").validate({     
	       rules: {
				"infoMap['orderOperationRemark']": {realMaxlength:66}
    	   },
	   	   submitHandler: function (form) {
	            $.post(
	                	'/app/orderPaychangeInfoAction.action',
	               		$("#remarkForm").serialize(),
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
<form id="remarkForm">
	<table>
		<caption id="caption">订单号：<s:property value="order.orderCode"/></caption>
		<tr>
			<th>&nbsp;</th>
			<td><textarea id="orderOperationRemark" name="infoMap['orderOperationRemark']" cols="50" rows="5" 
			class="order.Complaints-text"><s:property value="order.orderOperationRemark"/></textarea></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input id="orderCode" type="hidden" name="infoMap['orderCode']" value='<s:property value="order.orderCode"/>'/>
				<input type="submit" id="submit0" class="saveBtn" value=""/>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>