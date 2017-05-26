<%@page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
<%@ page isELIgnored="false"%>
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
	
 });
</script>
<form id="remarkForm">
	<table>
		<caption id="caption"><a href="${filePath }">下载</a></caption>
		<tr>
			<th>&nbsp;</th>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td></td>
			<td>
				<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
		
	</table>
</form>