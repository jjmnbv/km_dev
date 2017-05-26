<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>商城订单财务版报表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script>
			var isSub=false;
			function exportFile(){
				if(isSub){
					return;
				}
				var startDate=$('#startDate').val();
				var endDate=$('#endDate').val();
				var supplier=$('#supplier').val();
				if(startDate.length<1||endDate.length<1){
					alert("开始时间和结束时间必填！");
					return;
				}
				isSub=true;
				$.ajax({
		            async: false,
		            url: '/app/financeOrderReport.action',
		            cache:false,
		            type:'POST',
		            data: {"map['startDate']": startDate,"map['endDate']": endDate, "map['supplier']":supplier },
		            success: function (data) {
			            try{
		            	var start=data.indexOf('\'path\':\'');
		            	var end=data.indexOf('.xls');
		            	var url=data.substring(start+8,end+4);
		            	$('#downLoadLink').attr('href',url).find('span').click();
			            }catch(e){
			            	alert('生成报表出错！');
				        }
		            	isSub=false;
		            },
		            error:function(){
						alert('生成报表出错！');
			        }
		        });
			}
		</script>
	</head>
	<body>
		
		<s:form action="financeOrderReport" method="post" name="mainForm">
			<table width="98%" align="center" border="0" class="table_search">
				<tr>
					<th align="right">开始时间</th>
					<td>
						<input class="Wdate condition" id="startDate" name="map['startDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
						type="text" value='<s:property value="map['startDate']"/>'>
					</td>
					<th align="right">结束时间</th>
					<td>
						<input class="Wdate condition" id="endDate" name="map['endDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
						type="text" value='<s:property value="map['endDate']"/>'>
					</td>
					<th align="right">供应商</th>
					<td>
						<input id="supplier" name="map['supplier']" style="size: 12px;" type="text" value='<s:property value="map['supplier']"/>'>
					</td>
				</tr>
				<tr> 
					<td colspan="6" align="center">
						<input type="button" value="提交" class="btn-custom" onClick="exportFile()">&nbsp;&nbsp;
						<input type="reset" value=" 取消 " class="btn-custom"/>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>