<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单物流信息列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
		var detailAction = 'expressSub_querySubWithDetail.action';
		var initSaveAction = "expressSub_initInsert.action";
		var reSubscribeAction = "expressSub_reSubscribeExpres.action";
		var sefTarget="_self";
		
		$(function(){
			//页面加载操作
			$("#subDateStart").attr("readonly",true);
			$("#subDateEnd").attr("readonly",true);
			$("#latestPushDateStart").attr("readonly",true);
			$("#latestPushDateEnd").attr("readonly",true);
				  });
				//end
		
		//查看物流跟踪信息			
		function viewDetail(logisticsCode,logisticsNo){
	        var url = detailAction + "?map['logisticsCode']=" + logisticsCode + "&map['logisticsNo']=" + logisticsNo;
	        var winOptions = 'toolbar=no, left=200, top=180, menubar=no,scrollbars=no, resizable=yes width=800,height=500';
	        var viewDetailWin = window.open(url, 'viewDetails', winOptions);
	        viewDetailWin.focus();  
	    }

		function insertSub(){
			 document.forms[0].action=initSaveAction;
		     document.forms[0].target=sefTarget;
		     document.forms[0].submit();
		}

		function checkSubscriptionForm(){
			$("#pageNo").val('1');
			return true;
		}
		

		//防止频繁点击, 待处理
		function reSubscribeExpres(paramSubId,paramLogisticsCode,paramLogisticsNo){
			//校验
			$.ajax({
				async:false,
				url:'expressSub_checkIsHaveSubscribed.action',
				type:'POST',
				dataType:'json',
				data:{
					subId:paramSubId,
					logisticsCode:paramLogisticsCode,
					logisticsNo:paramLogisticsNo,
					t_i_m_e:Math.random()
				},
				timeout:'6000',
				success:function(data){
					console.log(data);
					if(data.scribFlag=='HAVE_SCRIBED'){
						alert("该记录已经订阅成功，请点击查询刷新页面！");
					}else if(data.scribFlag=='IS_SCRIBEDING'){
						alert("该记录正在订阅中。。。请稍后刷新页面！");
					}else if(data.scribFlag=='NOT_SCRIBEDED'){
						document.forms[0].action=reSubscribeAction+"?expressSubscription.subId="+paramSubId;
					    document.forms[0].target=sefTarget;
					    document.forms[0].submit();
					}
				},
				error:function(){
					alert('尝试订阅发生了未知错误，请稍后再试或联系系统管理员!');
				}
			});	
		}
		
	    
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'订单物流信息'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<form id="subscriptionListSearch" action="expressSub_pageList.action" method="POST" onSubmit="javascript:checkSubscriptionForm()">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<td align="left">
					订单号：<input class="condition"   name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>' style="width:145px;" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
				</td>
		        <td align="left">
		           	物流单号：<input class="condition" name="map['logisticsNo']" type="text" value='<s:property value="map['logisticsNo']"/>' style="width:145px;" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
				</td>
		        <td align="left" >
				   	订阅时间：
				   <input class="Wdate condition" id="subDateStart" name="map['subDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'subDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})"         type="text" value='<s:property value="map['subDateStart']"/>' style="width:145px;">
					至
				   <input class="Wdate condition" id="subDateEnd"   name="map['subDateEnd']"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'subDateStart\')}'})" type="text" value='<s:property value="map['subDateEnd']"/>' style="width:145px;">
				</td>
				<td align="left">
		           	快递状态：<select class="condition" id="expressStatus" name="map['expressStatus']" style="width:156px">
					   		<option value=""  <s:if test="map['expressStatus']==''">selected="selected"</s:if>>全部</option>
					   		<option value="0" <s:if test="map['expressStatus']==0 && map['expressStatus']!=''">selected="selected"</s:if> >在途</option>
					   		<option value="1" <s:if test="map['expressStatus']==1">selected="selected"</s:if> >揽件</option>
					   		<option value="2" <s:if test="map['expressStatus']==2">selected="selected"</s:if> >疑难</option>
					   		<option value="3" <s:if test="map['expressStatus']==3">selected="selected"</s:if> >签收</option>
					   		<option value="4" <s:if test="map['expressStatus']==4">selected="selected"</s:if> >退签</option>
					   		<option value="5" <s:if test="map['expressStatus']==5">selected="selected"</s:if> >派件</option>
					   		<option value="6" <s:if test="map['expressStatus']==6">selected="selected"</s:if> >退回</option>
					   		<option value="7" <s:if test="map['expressStatus']==7">selected="selected"</s:if> >转单</option>
				   		</select>
				</td>
				<td></td>
			</tr>
			<tr>
				<td align="left">
				   推送状态：<select class="condition" id="latestPushStatus" name="map['latestPushStatus']" style="width:150px">
					   		<option value=""  <s:if test="map['latestPushStatus']==''">selected="selected"</s:if> >全部</option>
					   		<option value="1" <s:if test="map['latestPushStatus']==1">selected="selected"</s:if> >成功</option>
					   		<option value="2" <s:if test="map['latestPushStatus']==2">selected="selected"</s:if> >失败</option>
				   		</select>
				</td>
				<td align="left">
					推送时间：
					<input class="Wdate condition" id="latestPushDateStart" name="map['latestPushDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'latestPushDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})"         type="text" value='<s:property value="map['latestPushDateStart']"/>' style="width:145px;" >
					至
				   <input  class="Wdate condition"  id="latestPushDateEnd"  name="map['latestPushDateEnd']"   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'latestPushDateStart\')}'})" type="text" value='<s:property value="map['latestPushDateEnd']"/>' style="width:145px;" >
				</td>		
		        
				<td align="left">
				   跟踪状态：<select class="condition" id="trackStatus" name="map['trackStatus']" style="width:156px">
					   		<option value=""  <s:if test="map['trackStatus']==''">selected="selected"</s:if> >全部</option>
					   		<option value="1" <s:if test="map['trackStatus']==1">selected="selected"</s:if> >未订阅</option>
					   		<option value="6" <s:if test="map['trackStatus']==6">selected="selected"</s:if> >订阅失败</option>
					   		<option value="2" <s:if test="map['trackStatus']==2">selected="selected"</s:if> >监控中</option>
					   		<option value="3" <s:if test="map['trackStatus']==3">selected="selected"</s:if> >中止</option>
					   		<option value="4" <s:if test="map['trackStatus']==4">selected="selected"</s:if> >重新推送</option>
					   		<option value="5" <s:if test="map['trackStatus']==5">selected="selected"</s:if> >结束</option>
				   		</select>
				</td>
				<td align="left">&nbsp;&nbsp;
					<input type="submit" class="queryBtn" value=""/>&nbsp;&nbsp;
					<input type="button" onClick="insertSub()"  class="btn-custom"  value="新增订阅"/>&nbsp;
					<!-- <input type="button" value="更多选项 " id="moreButton" /> -->
		        </td>
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="15%">订单号</th>
		   		<!--删除 <th width="5%">渠道</th> -->
		   		<th width="10%">物流公司</th>
		   		<th width="6%">物流单号</th>
		   		<th width="10%">订阅时间</th>
		   		<th width="10%">跟踪状态</th>
		   		<th width="10%">上次推送</th>
		   		<th width="10%">推送时间</th>
		   		<th width="6%">快递状态</th>
		   		<th width="10%">操作</th>
			</tr>
			
			<s:iterator id="sub" value="page.dataList">
			<tr>
		   		 <td><s:property value="#sub.orderCode"/></td>
		<%--    		 <td><s:property value="#sub.channel"/></td> --%>
		   		 <td><s:property value="#sub.logisticsName"/></td>
		   		 <td><s:property value="#sub.logisticsNo"/></td>
		   		 <td><s:date name="#sub.subDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td><s:property value="#sub.trackStatusName"/></td>
		   		 <td><s:property value="#sub.latestPushStatusName"/></td>
		   		 <td><s:date name="#sub.latestPushDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td><s:property value="#sub.expressStatusName"/></td>
		   		 <td>
		   		 	<a href="javascript:viewDetail('<s:property value="#sub.logisticsCode"/>','<s:property value="#sub.logisticsNo"/>')">详情</a>
		   		 	<s:if test="#sub.trackStatus==1||#sub.trackStatus==6">
		   		 		<a href="javascript:reSubscribeExpres('<s:property value="#sub.subId"/>','<s:property value="#sub.logisticsCode"/>','<s:property value="#sub.logisticsNo"/>')">重新订阅</a>
		   		 	</s:if>
		   		 </td>
			</tr>
			</s:iterator>
		</table>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>