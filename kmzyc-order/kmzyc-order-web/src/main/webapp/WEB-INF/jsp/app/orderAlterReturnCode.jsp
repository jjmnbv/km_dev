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
				"no": {required:true}
    	   },
	   	   submitHandler: function (form) {
	            $.post(
	                	'/app/orderBackdownchangeStatusAction.action',
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
		<caption id="caption"><b>返回原件流信息</b></caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>退换单号</th>
			<td><input type="text" id="alterCode" name="alterCode"  value='<s:property value="alterCode"/>' size="30" readonly="readonly"/></td>
		</tr>
		<tr>
			<th>物流公司</th>
			<td>
                <select class="sele" id="code" name="code"> 
                    <s:iterator value="logisticsMap" >    
                     	<option value="<s:property value='key'/>"><s:property value="value"/></option>
					</s:iterator> 
				</select>
			</td>
		</tr>
		<tr>			
			<th>物流单号</th>
			<td><input type="text" id="no" name="no"  value="" size="30" maxlength="30" onkeyup="this.value=this.value.replace(/[^0-9a-zA-Z]/g,'')" /></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="hidden" name="status" value="63"/>
				<input type="submit" id="submit0" class="saveBtn" value=""/>
				<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>