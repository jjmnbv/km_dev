<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单同步列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request" />
		<s:set name="name" value="'订单报表'" scope="request" />
		<s:set name="son_name" value="'时代订单数据'" scope="request" />
		<s:include value="/WEB-INF/jsp/public/title.jsp" />
		<table width="98%" align="center" border="0" class="table_search">
			<tr>
				<td align="right">开始时间</td>
				<td>
					<input class="Wdate condition" id="startDate" name="map['startDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
					type="text" value='<s:property value="map['startDate']"/>'>
				</td>
				<td align="right">结束时间</td>
				<td>
					<input class="Wdate condition" id="endDate" name="map['endDate']"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
					type="text" value='<s:property value="map['endDate']"/>'></td>
				<td><input type="button" id="btnexport" class="btn-custom"  value="导出" /></td>					
			</tr>
		</table>
		<input type="hidden" class="condition" value="25" name="map['rank']" />
	</body>
	<script>
		$('#btnexport').click(function(){
			var params='';
			$('.condition').each(function(){
				params+=this.name+'='+this.value+'&';
			});
			if(params.indexOf('startDate') < 0 || params.indexOf('endDate') < 0){
				alert('开始时间、结束时间不能为空');
			}
			window.open('/app/timeOrderExport.action?'+params);
		});
	</script>
</html>