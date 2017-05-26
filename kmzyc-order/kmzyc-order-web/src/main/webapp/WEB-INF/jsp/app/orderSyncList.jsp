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
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		/** 全选js  **/
		function checkAlls(o){
		  	$('input[name="syncIds"]').each(function(){
				this.checked = o.checked;
			});
		}
		$(function () {
			$('.exportSync').click(function(){
				var params='';
				$('.condition').each(function(){
					params+=this.name+'='+this.value+'&';
				});
				window.open('/app/exportSync.action?'+params);
			});
		});
		
		/**同步*/
		function updateStatus(id){
			var title = "";
			title = "是否确定同步?";
			var yes = confirm(title);
			if(yes==false)return;
			$.ajax({
				url:'/app/syncOrder.action',
				type:'post',
				dataType:'json',
				data:{'syncId':id},
				success:function(data){
					var success = data.success;
					if(success==true){
						alert("操作成功！");
						window.location.reload();
					}else{
						alert(data.title);
						return;
					}
				},
				error:function(){
					alert('操作失败请联系管理员');
				}
			});
		}
		/**同步所有失败订单*/
		function syncFailOrders(){
			var title = "";
			title = "是否确定同步?";
			var yes = confirm(title);
			if(yes==false)return;
			$.ajax({
				url:'/app/syncOrder.action',
				type:'post',
				dataType:'json',
				data:{'syncId':0},
				success:function(data){
					var success = data.success;
					if(success==true){
						alert("操作成功！");
						window.location.reload();
					}else{
						alert(data.title+"或无可同步的失败订单!");
						return;
					}
				},
				error:function(){
					alert('操作失败请联系管理员');
				}
			});
		}
		/**同步所有选中订单*/
		function syncCheckedOrders(){
			var title = "";
			title = "是否确定同步?";
			var syncIds ="" ;
			$('input[name="syncIds"]').each(function(){
					if(this.checked==true){
						if(syncIds==""){
							syncIds = this.value.trim();
						}else{
							syncIds = syncIds+","+ this.value;
						}
					}
		
			});
			if(syncIds==""){
				alert("没有勾选！");
			return;
			}
			var yes = confirm(title);
			if(yes==false)return;
			$.ajax({
				url:'/app/syncOrder.action',
				type:'post',
				dataType:'json',
				data:{'syncIds':syncIds,'syncId': -1},
				success:function(data){
					var success = data.success;
					if(success==true){
						alert("操作成功！");
						window.location.reload();
					}else{
						alert(data.title);
						return;
					}
				},
				error:function(){
					alert('操作失败请联系管理员');
				}
			});
		}
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'时代订单同步'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/showOrderSyncList.action" method="POST">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th align="right">订单号：</th>
		        <td>
				    <input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
				</td>
				<th align="right">下单账号：</th>
		        <td>
				    <input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
				</td>
			</tr>
			<tr>				
				<th align="right">时代编号：</th>
		        <td>
				    <input class="condition" name="map['outCode']" type="text" value='<s:property value="map['outCode']"/>'>
				</td>
				<th align="right">同步状态：</th>
		        <td>
				    <select class="condition" name="map['syncFlag']" style="width:156px">
				   		<option value="" <s:if test='map["syncFlag"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["syncFlag"]=="1"'>selected="selected"</s:if> >成功</option>
				   		<option value="0" <s:if test='map["syncFlag"]=="0"'>selected="selected"</s:if> >失败</option>
				   		<option value="2" <s:if test='map["syncFlag"]=="2"'>selected="selected"</s:if> >未同步</option>
				   </select>
				</td>
			</tr>
			<tr>
			<th align="right">同步时间：</th>
		        <td>
				    <input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'>
				</td>
				<th align="right">到：</th>
		        <td>
					<input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'>
                   <th align="right"><input type="submit" class="queryBtn" value=""/>
				<input type="button" class="btn-custom exportSync" value="导出"/></th>
				</td>
			</tr>
			<tr>
				
			</tr>
		</table>
		<br/>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
			    <th align="center" width="4%"><input type='checkbox' name='allbox' onClick="checkAlls(this)"></th>
		   		<th width="10%">订单号</th>
		   		<th width="8%">下单账号</th>
		   		
		   		<th width="8%">类型</th>
		   		<th width="8%">订单状态</th>
		   		<th width="6%">订单金额</th>
		   		
		   		<th width="6%">预估pv</th>
		   		<th width="6%">实际同步值</th>
		   		<th width="10%">订单支付/完成时间</th>
		   		<th width="6%">同步状态</th>
		   		<th width="10%">同步时间</th>
		   		<th width="10%">操作</th>
			</tr>
			<s:iterator id="order" value="page.dataList">
			<tr>
				<td align="center" width="5%">
				<input type="checkbox" name="syncIds" value='<s:property value="#order.orderCode"/>' />
				</td>
		   		 <td>
		   		 <a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>"><s:property value="#order.orderCode"/></a>
		   		 </td>
		   		 <td><s:property value="#order.customerAccount"/><br><font  style="color:#888888"><s:property value="#order.outCode"/></font></td>
		   		<td><s:if test="#order.orderType==1">普通商品订单</s:if>
				     <s:elseif test="#order.orderType==2">抽奖商品订单</s:elseif>
				     <s:elseif test="#order.orderType==3">时代二次购物</s:elseif>
				     <s:elseif test="#order.orderType==4">时代注册</s:elseif>
				     <s:elseif test="#order.orderType==5">时代升级</s:elseif>
				</td>	 
		   		 <td><s:property value="#order.orderStatusStr"/></td>
		   		 <td><s:property value="#order.commoditySum"/></td>
		   		 
		   		
		   		 <td>PV:<s:property value="#order.orderPv"/></td>
		   		  <td>PV:<s:property value="#order.syncPv"/></td>
		   		  <td>支付:<s:date name="#order.payDate" format="yyyy-MM-dd HH:mm:ss" /><br>完成:<s:date name="#order.finishDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td><s:if test="#order.syncFlag==1">成功</s:if>
				     <s:elseif test="#order.syncFlag==0">失败</s:elseif>
				     <s:else> 未同步</s:else>
				 </td>
		   		 <td><s:if test="#order.syncFlag!=2"><s:date name="#order.syncDate" format="yyyy-MM-dd HH:mm:ss" /></s:if></td>
				 <td align="center">
				 	<s:if test="#order.syncFlag!=1">
					<input type="button"  style="cursor: pointer;" title="提交订单数据"  value="同步" onClick="updateStatus('<s:property value='#order.orderCode'/>')">&nbsp;&nbsp;
					</s:if>
				</td>
			</tr>
			</s:iterator>
			
		</table>
		
		<br/>
		<table class="page_table" width="98%" align="center">
		   <tr>
		   <td>
				<input TYPE="button"  class="btn-custom" style="cursor: pointer;" value="同步选中订单" src="/etc/images/button_new/submit.png" onClick="syncCheckedOrders()" />
			</td>
			<td> <input type="button"  class="btn-custom" onClick="syncFailOrders()"  value ="同步所有失败订单"/> </td>						
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>

