<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
	
	$(".saveBtn").bind("click",function(){
		
		
		var title = $("#title").val();
		
		var money = $("#money").val();
		
		if(!title){
			alert("调整事务标题不能为空");return;
		}
		if(!money || isNaN(money)){
			alert("调整金额只能为数字");
			$("#money").select();
			return;
		}
		$(".saveBtn").unbind('click');
		 $.post('saveDiffAdj.action', $("#remarkForm").serialize(),
	           		function(result){
			            if(result.indexOf("error")>0){
			            	alert("保存出错");
			            }else{
			            	location.reload();
			            }
	     			}
         );	
		 
	});
	
	
	$("#money").change(function(){
		var crrVal = $("#money").val();
		var index = crrVal.indexOf(".");
		var poStr = crrVal.substring(index+1,crrVal.length);
		if(poStr.length>3){
			crrVal=Number(crrVal).toFixed(2);
		}
		$("#money").val(crrVal);
	});
	
 });
</script>
<form id="remarkForm">
	<table>
		<caption id="caption">填写差异调整记录<s:property value="order.orderCode"/></caption>
		<tr>
			<th>&nbsp;</th>
			<td>发生账期：</td>
			<td><input type="text" name="diffAdj.currSettmentNo" disabled="disabled" value="${settlementPeriod }"></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>调整事务标题：</td>
			<td><span  style="color: red" >*</span><input type="text" id="title" name="diffAdj.adjTitle" maxlength="30"></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>调整金额：</td>
			<td><span  style="color: red" >*</span><input type="text" id="money" name="diffAdj.adjMoney" maxlength="10"></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>详细说明：</td>
			<td><textarea  name="diffAdj.adjDetail" cols="45" rows="5" maxlength="500"> </textarea></td>
		</tr>

		<tr>
			<th>&nbsp;</th>
			<td></td>
			<td>
				<input type="hidden" name="diffAdj.currSettmentNo" value="${sno}"/>
				<input type="button" id="submit0" class="saveBtn" value=""/> &nbsp;<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>