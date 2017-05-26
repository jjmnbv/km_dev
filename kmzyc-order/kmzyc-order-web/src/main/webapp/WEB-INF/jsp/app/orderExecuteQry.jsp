<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>测试结转</title>
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
<script type="text/javascript">
	function lookFile(url){
		window.open(url);
	}
</script>
</head>

<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单生成'" scope="request"/>
<s:set name="son_name" value="'订单结转管理'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
<form  method="post" action="/app/orderExecuteList.action">
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th>结转开始时间：</th>
		<td><input name="map['startDate']" id="startDate" value='<s:property value="map['startDate']"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})" /></td>
		<th>结转结束时间：</th>
		<td><input name="map['endDate']" id="endDate" value='<s:property value="map['endDate']"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" /></td>
		<td><input type="submit" name="Submit" class="queryBtn" value="" /></td>
	</tr>
</table>


<br/>
<br/>
<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">

	<tr>
		<!-- 
		<th>本页<input name="checkPage" id="checkPage" type="checkbox" value="true"/>所有<input name="checkAll" id="checkAll" type="checkbox" value="true"/></th>
   		 -->
   		<th>结转时间</th>
   		<th>结转操作人</th>
   		<th>结转成功的订单总数</th>
   		<th>结转失败的订单总数</th>
   		<th>查看结转生成文件操作</th>
	</tr>
	
	<s:iterator id="orderCarry" value="page.dataList">
	<tr>
		 <!-- 
   		 <td><input class="orderIds" type="checkbox"/></td>
   		  -->
   	 
   		 <td><s:date name="#orderCarry.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
   		 <td><s:property value="#orderCarry.operator"/></td>
   		 <td><s:property value="#orderCarry.orderSum"/></td>
   		 <td><s:property value="#orderCarry.noOrderSum"/></td>
   		 
		 <td>
		 	<a class="anchors" href="javascript:void(0);"  onclick='lookFile("<s:property value='#orderCarry.excelAddress'/>")' >
		 	 查看康美中药城
		 	</a>
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