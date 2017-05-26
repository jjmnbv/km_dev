<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" href="<%=basePath%>css/lib/module.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-core.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/member/doReturn.js"></script>
<form id="wlsform" action="changeApplyStatus.action">
	<table>
		<caption id="caption"><b>退换货物流信息</b></caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>退换单号</th>
			<td><input type="text" id="orderAlterCode" name="orderAlterCode"  value="${orderAlterCode}" readonly="readonly"/></td>
		</tr>
		<tr>
			<th>物流公司</th>
			<td>
				<select id="code" name="code">
					<option value="1">顺丰</option>
					<option value="2">圆通</option>
					<option value="3">中通</option>
				</select>
			</td>
		</tr>
		<tr>			
			<th>物流单号</th>
			<td><input type="text" id="no" name="no"  value=""/></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<div class="button">
					<div>
						<input type="hidden" name="orderAlterStatus" value="3">
					    &nbsp;&nbsp;&nbsp;&nbsp;<a class="btn-submit" href="javascript:subwlform()"><span>提交</span></a>
					</div>
				</div>			
			</td>
		</tr>
	</table>
</form>