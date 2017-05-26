<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
	//校验
	function validateForm() {     
	   //validate方法参数可选     
	   return $("#payForm").validate({  
	       rules: {
				"no": {required:true}
     	   }
	   }).form();     
	} 
	
	//提示框插件
    $("#submit").click(function(){
    	if(validateForm()){
            $.post(
                	'/app/orderPaychangeStausAction.action',
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
		<caption id="caption"><s:if test="status==4">出库</s:if><s:if test="status==5">配送</s:if></caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>单号</th>
            <td>
            	<input type="text"  id="no" name="no"/>
            	<input type="hidden"  id="orderCode" name="orderCode" value='<s:property value="orderCode"/>'/>
            	<input type="hidden"  id="status" name="status" value='<s:property value="status"/>'/>
            </td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="button" id="submit" class="saveBtn"/>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>