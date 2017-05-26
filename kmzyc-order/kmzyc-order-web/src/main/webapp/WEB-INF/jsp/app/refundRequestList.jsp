<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>退款请求列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'退款请求'" scope="request"/>
		<s:set name="son_name" value="'退款请求列表'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="refundForm" action="/app/refundRequest.action" method="post">
			<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
				<tr>
					<th align="right">请求来源：</th>
		        	<td>
						<select name="map['requestType']" id="sel_sellerType" style="width:156px">
				    		<option value="" <s:if test='map["requestType"]==""'>selected="selected"</s:if>>---请选择---</option>
				    		<option value="1" <s:if test='map["requestType"]=="1"'>selected="selected"</s:if>>取消订单</option>
				    		<option value="2" <s:if test='map["requestType"]=="2"'>selected="selected"</s:if>>退换货</option>
				    	</select>
					</td>
					<th align="right">请求单号：</th>
		        	<td>
				    	<input class="condition" name="map['orderCode']" id="code" type="text" value='<s:property value="map['orderCode']"/>'>
					</td>
				</tr>
				<tr>
					<th align="right">支付平台：</th>
		        	<td>
				    	<select name="map['platformCode']" id="sel_platformCode" style="width:156px">
				    		<option value="" <s:if test='map["platformCode"]==""'>selected="selected"</s:if>>---请选择---</option>
				    		<option value="3" <s:if test='map["platformCode"]=="3"'>selected="selected"</s:if>>支付宝</option>
				    	</select>
					</td>
					<th align="right">销售类型：</th>
		        	<td>
				    	<select name="map['sellerType']" id="sel_sellerType" style="width:156px">
				    		<option value="" <s:if test='map["sellerType"]==""'>selected="selected"</s:if>>---请选择---</option>
				    		<option value="1" <s:if test='map["sellerType"]=="1"'>selected="selected"</s:if>>自营</option>
				    		<option value="2" <s:if test='map["sellerType"]=="2"'>selected="selected"</s:if>>第三方</option>
				    	</select>
					</td>
					<th align="right">商家名称：</th>
		        	<td>
				    	<input class="condition" name="map['sellerShop']" type="text" value='<s:property value="map['sellerShop']"/>'>
					</td>
				</tr>
				<tr>
					<th align="right">状态：</th>
		        	<td>
				    	<select name="map['status']" id="sel_status" style="width:156px">
				    		<option value="" <s:if test='map["status"]==""'>selected="selected"</s:if>>---请选择---</option>
				    		<option value="0" <s:if test='map["status"]=="0"'>selected="selected"</s:if>>待处理</option>
				    		<option value="1" <s:if test='map["status"]=="1"'>selected="selected"</s:if>>已完成</option>
				    	</select>
					</td>					
					<th align="right">创建时间从：</th>
		        	<td>
						<input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>' />
                        到
                        						<input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>' />

					</td>						
		        	<td>
                    <input type="button" class="queryBtn" id="queryBtn" value=""/>
                        						<input type="button" value=" 清空 " class="btn-custom" id="clean"/>
						
					</td>
				</tr>
			</table>
			<br/>
			&nbsp;&nbsp;&nbsp;<input type="button" value=" 提交退款 " id="submitRefund" /><br/>
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
				<tr>
			   		<th><label><input type="checkbox" id="ck_all" name="ck_all" />全选</label></th>
			   		<th>请求来源</th>
			   		<th>订单号</th>
			   		<!-- <th>渠道</th> -->
			   		<th>销售类型</th>
			   		<th>商家名称</th>
			   		<th>支付平台</th>
			   		<th>退款金额</th>
			   		<th>请求时间</th>
			   		<th>提交时间</th>
			   		<th>完成时间</th>
			   		<th>处理状态</th>
			   		<th>处理人</th>
				</tr>
				<s:iterator id="rr" value="page.dataList">
				<tr style="<s:property value="(#rr.status==0 && null==#rr.requestDate)?'background-color:red;':'' "/>">
			   		<td>
			   			<s:if test="#rr.status==0">
						<input type="checkbox" name="rrid" value="<s:property value="#rr.rrid"/>" subFlag="<s:property value="(#rr.status==0 && null!=#rr.requestDate)?'1':'' "/>"/>	
						</s:if>
			   		</td>
			   		<td><s:property value="1==#rr.refundType?'取消订单':'退换货'"/></td>
			   		<td>
			   			<a href="/app/orderItemdetailAction.action?orderCode=<s:property value="#rr.orderCode"/>&map['byStatus']="><s:property value="#rr.orderCode"/></a>
					</td>
			   	<%-- 	<td><s:property value="#rr.channel"/></td> --%>
			   		<td><s:property value="1==#rr.sellerType?'自营':'第三方'"/></td>
			   		<td><s:property value="#rr.sellerShop"/></td>
			   		<td><s:property value="#rr.platformCode"/></td>
			   		<td><s:property value="#rr.refurnMoney"/></td>
			   		<td><s:date name="#rr.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			   		<td><s:date name="#rr.requestDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			   		<td><s:date name="#rr.finishDate" format="yyyy-MM-dd HH:mm:ss" /></td>
			   		<td><s:property value="#rr.status==1?'已完成':'待处理'"/></td>
			   		<td><s:property value="#rr.operater"/></td>
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
	</body>
	<script>
		$(function(){
			var sendDataed=false;
			var platformCode=$('#sel_platformCode').val();
			$('#clean').click(function(){
				$('#refundForm input').each(function(){
					if(this.type!='button'){
						$(this).val('');
					}
				});
				$('#refundForm select').each(function(){
					this.options[0].selected = true;
				});
			});

			$('#queryBtn').click(function(){
				if(''==$('#sel_platformCode').val()){
					alert('请选择支付平台！');
				}/*删除渠道 else if(''==$('#sel_channel').val()){
					alert('请选择渠道！');
				} */else{
					$('#refundForm').submit();
				}
			});

			$('#ck_all').click(function(){
				$('input[name="rrid"]').attr('checked',$(this).attr("checked"));
			});
			
			$('#submitRefund').click(function(){
				if(sendDataed){
					alert('请刷新后提交！');
					return;
				}
				var reqIds='';
				var hashsub=false;
				$('input[name="rrid"]:checked').each(function(){
					if('1'==$(this).attr('subFlag')){
						hashsub=true;
					}
					reqIds+=$(this).val()+',';
				});
				if(''==reqIds){
					alert('请选择需退款的记录！');
					return;
				}
				if(hashsub && !confirm('存在已提交的请求，确认是否提交！')){
					return;
				}
				if(!confirm('确认提交退款请求')){
					return;
				}
				$.ajax({
		            async: false,
		            url: '/app/sendRefundData.action',
		            cache:false,
		            type:'post',
		            data: {'requestIds':reqIds,'platformCode':platformCode},
		            success: function (data) {
			            if(null==data){
			            	alert('退款请求已提交，请勿重复提交');
			            }else{
			            	sendDataed=true;
				            $('#refundForm').after('<div style="display:none;">'+data+'</div>');
				        }
		            },
		            error:function(){
						alert('提交退款请求出错，请稍后再提交！');
			        }
		        });
			});  
		});
	</script>
</html>