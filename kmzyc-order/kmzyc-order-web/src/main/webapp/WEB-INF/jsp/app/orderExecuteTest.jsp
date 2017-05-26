<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>手动结转</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.dragndrop.js"></script>
		<script type="text/javascript" src="/etc/js/showframe.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script src="/etc/js/Form.js" type="text/javascript"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
			function lookFile(url){
				window.open(url);
			}
			$(function () {
				$('#btnsubmit').one('click', function () {
					if($('#StartDate').val().length<1 ||$('#endDate').val().length<1 ){
						alert('结转时间必须非空!');
						return;
					}
					this.value='正在结转.....';
					//不加定时器会导致文本显示延迟
					setTimeout(function(){
						$.ajax({url:'/app/orderExecuteHandle.action?StartDate='+$('#StartDate').val()+'&endDate='+$('#endDate').val()/*删除渠道 +'&orderChannel='+$('#orderChannel').val() */,
							type: 'post',
		                    datatype: 'text',
		                    timeout:10000,
		                    async: false,
		                    success:function (rs) {
		                    	var obj=eval('('+rs.replace('}null','}')+')');
		                        if(null==obj || null==obj.id){
									alert('系统异常！');
									location.reload();
			                    }else if(-1==obj.id){
									alert('正在结转，请稍后！');
			                    }else if(0==obj.id){
									alert('无可结转订单！');
				                }else if(1==obj.id){
									alert('结转程序已启动，两分钟后可到结转列表查看结果！');
				                }
		                        setTimeout("location.reload();",10000);
		                    }
		                });
					},100);		                
		        });
			});
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单生成'" scope="request"/>
		<s:set name="son_name" value="'订单手动结转'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<br/>
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td>支付完成时间 从：<input name="StartDate" type="text" id="StartDate" dataType="Require" msg="必须选择开始时间" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})" /></td>
				<td>到：<input name="endDate" id="endDate" type="text" class="Wdate" dataType="Require" msg="必须选择结束时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'StartDate\')}'})" /></td>
				<td><input type="button" name="btnsubmit" id="btnsubmit" class="btn-custom" value="  结     转    " /></td>
			</tr>
		</table>
		<br/>
		<br/>
	</body>
</html>