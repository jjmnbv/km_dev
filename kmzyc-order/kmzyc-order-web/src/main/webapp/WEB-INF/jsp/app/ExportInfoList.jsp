<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>商城订单财务版</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery.dragndrop.js"></script>
<script type="text/javascript" src="/etc/js/showframe.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<Script src="/etc/js/Form.js" type="text/javascript"></Script>
<Script  src="/etc/js/97dater/WdatePicker.js"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
	function lookFile(url){ 
		window.open(url);
	};
	
	function openWindow(){
	 var winOptions = 'toolbar=no, left=200, top=180, menubar=no,scrollbars=no, resizable=yes, location=no,status=no, width=600,height=300';
	 window.open("/app/gotoFinanceOrderReport.action","生成报表",winOptions);
	};
	function check(){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		if(startDate.length<1 || endDate.length<1){
			alert("请选择操作时间");
			return false;
		}
		return true;
	}
</script>
</head>

<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单报表'" scope="request"/>
<s:set name="son_name" value="'商城订单财务版'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
<form  method="post" action="/app/queryExportInfoList.action" onsubmit="return check()">
<input type="hidden" name="map['exportType']" value = "0"/>
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">操作时间从：</th>
	       	<td>
				<input class="Wdate condition" id="startDate" name="map['startDate']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'startDate\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['startDate']"/>' />
	                                                                                     至
	            <input class="Wdate condition" id="endDate" name="map['endDate']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'endDate\')}'})" type="text" value='<s:property value="map['endDate']"/>' />
	
			</td>
			<td><input type="submit" name="Submit" class="queryBtn" value="" /></td>	
			<td><input type="button" value="生成报表"  class="btn-custom" onclick="openWindow()"/>	</td>
	</tr>
	<tr>

</table>


<br/>
<br/>
<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="table-layout:fixed;border-collapse: collapse;font-size:12px">

	<tr>
		
   		<th>操作时间</th>
   		<th>操作人</th>
   		<th width="40%">报表生成条件</th>
   		<th>报表生成状态</th>
   		<th>操作</th>
	</tr>
	
	<s:iterator id="exportInfo" value="page.dataList">
	<tr>
   		 <td ><s:date name="#exportInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
   		 <td ><s:property value="#exportInfo.operator"/></td>
   		 <td ><s:property value="#exportInfo.exportContent"/></td>
   		 <td > 
   		  
   		 	<s:if test="#exportInfo.exportStatus==0">&nbsp;&nbsp;&nbsp;&nbsp; 生成中 </s:if>
   		 	<s:if test="#exportInfo.exportStatus==1">&nbsp;&nbsp;&nbsp;&nbsp; 已完成 </s:if>
   		 </td>
   		 
   		 
		 <td >
		 	<s:if test="#exportInfo.exportStatus == 0">请耐心等待，稍后下载... </s:if>
		 	<s:if test="#exportInfo.exportStatus == 1">
			 	<a class="anchors" href="javascript:void(0);"  onclick='lookFile("<s:property value='#exportInfo.url'/>")' >
			 	 下载
			 	</a>
		 	</s:if>
		</td>
	</tr>
	</s:iterator>
	
</table>

<br/>
<table class="page_table" width="98%" align="center">
   <tr>
     <td align="right" ><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
   </tr>
</table>
</form>
<div id="question" style="display:none"></div>
<label></label>
</body>
</html>