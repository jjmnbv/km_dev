<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pltfm.app.vobject.OrderAssessDetailVO" %>
<%@ page import="com.pltfm.app.entities.OrderAssessInfo" %>

 
<html>
	<head>
		<title>订单评价信息</title>
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
	$(function(){	   
		
		$(".check").click(function() {
			var orderCode = $(this).attr("name");
		    $.blockUI({ message: ($('#question').empty().load("/app/assessShowAction.action",{orderCode:orderCode}))
		  	    , css: { position:'absolute',top:'20%' ,width: '430px' }
		    });
		});
		
		 // 操作
	      function operate(msg,action,data) {
	    	  if(confirm(msg)){
	    		  $.post(
	  	            	action,
	  	            	data,
	  	           		function(result){
	  			            alert(result);
	  			            if(result.indexOf("失败")>0){
	  			            	history.go(0);
	  			            }else{
	  			            	location.reload();
	  			            }
	  	     			}
	  	          );
	    	  };
	      }
		$(".delete").click(function(){
			var orderCode = $(this).attr("name");
				if(confirm('是否确定将此评价删除?')){
					location.href = '/app/deleteAssessInfo.action?orderCode='+orderCode
					
				}
			
		});
	});  
	</script>
</head>
	<body>

<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价'" scope="request"/>
<s:set name="son_name" value="'订单评价查询'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
 <form  name="orderAssessForm" action="/app/qryAssessInfo.action" method="post">
 <table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">订单号：</th>
        <td>
		    <input class="condition"  name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
		</td>
		
		<th align="right">客户账号：</th>
        <td>
		    <input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
		</td>
		
	</tr>
	<tr>
		<th align="right">评价开始时间：</th>
		<td><input name="map['startDate']" id="startDate" value='<s:property value="map['startDate']"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})" /></td>
		<th align="right">评价结束时间：</th>
		<td><input name="map['endDate']" id="endDate" value='<s:property value="map['endDate']"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" />&nbsp;&nbsp;<td align="left"><input type="submit" class="queryBtn" value=""></td>
		
	</tr>
	<tr>
		<td colspan="4" align="center"> </td>
	</tr>
	
</table>
 
<!--<br>  -->
			<!-- 数据列表区域 -->
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
				<tr>
					<!--  <th width="5%">
						<input type='checkbox' name='orderId'  onclick="checkAll(this,'orderId')">
					</th>-->
					<!--  <th>
					     订单编号   
					</th>-->
					<th>
					           订单号
					</th>
					<th>
						客户账号
					</th>
					
					<th>
						评价总分
					</th>
					
					<th>
						评价生成时间
					</th> 
				

					<th>
						操作
					</th>
				</tr>
					
				    <s:iterator id="oAssessInfo" value="page.dataList">
			
					  <tr>
						
						<td>
						    <s:property value="#oAssessInfo.orderCode"/>
							
						</td>
			            <td><input type="hidden" id="customerAccount" value="<s:property value='#oAssessInfo.guestNum'/>"/>
			               <s:property value="#oAssessInfo.guestNum"/>
			               <!--   <a href='/app/getCustomerScoreAction.action?guestNum=<s:property value="#oAssessInfo.guestNum"/>'><s:property value="#oAssessInfo.guestNum"/></a>-->
			            </td>
			            
			           <td>
			           
			               <s:property value="#oAssessInfo.assessMark"/>
                          
					  </td>
					    <td>
						   <s:date name="#oAssessInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
					<td>
					<a href="javascript:void(0);" class="check" name="<s:property value='#oAssessInfo.orderCode'/>" >查看</a>
					<a href="javascript:void(0);" class="delete" name="<s:property value='#oAssessInfo.orderCode'/>" >删除</a>
					</td>
					  

					</tr>
				</s:iterator>
				
</table>

<br/>
<table class="page_table" width="98%" align="center">
   <tr>
     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
   </tr>
</table>
 </form> 

	<!-- 
	<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
	 -->
	<div id="question" style="display:none"></div>
	
	</body>
</html>
 
