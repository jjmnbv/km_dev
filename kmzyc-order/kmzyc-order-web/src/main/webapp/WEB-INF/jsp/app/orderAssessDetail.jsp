<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
	
 });
</script>
<form id="payForm">
	<table cellpadding="3" cellspacing="0" border="1">
		<caption id="caption">订单评价详情</caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>订单编号:</th>
			<td><s:property value='orderAssessInfo.orderCode'/></td>
		</tr>
		<tr>
			<th>客户名:</th>
			<td>
			<s:property value='orderAssessInfo.guestNum'/>
			</td>
		</tr>
	<s:iterator id="assessDetail" value="oadlist">
		<tr>
			<td width="80%" ><s:property value='#assessDetail.assessName'/></td>
			<td width="20%">
			<s:if test="#assessDetail.assessScore==1">
			非常不满意
			</s:if>
			<s:if test="#assessDetail.assessScore==2">
			不满意
			</s:if>
			<s:if test="#assessDetail.assessScore==3">
			一般
			</s:if>
			<s:if test="#assessDetail.assessScore==4">
			满意
			</s:if>
			<s:if test="#assessDetail.assessScore==5">
			非常满意
			</s:if>
			</td>
		</tr>		
	</s:iterator>
		
		<tr> 
		<td width="30%" class="eidt_rowTitle">评价内容：</th>
		
		
		<td width="70%" > 																							
			<textarea rows="4" cols="30" disabled="disabled" id="assessContext" name="orderAssessInfo.assessContext" ><s:property value='orderAssessInfo.assessContext'/></textarea> 
		</td>
		
	</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>