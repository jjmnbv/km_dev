<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		$(function(){
						
			  if($("#checkpage50").val() == "true"){
	   			  document.getElementById('pageSize').value = 50;
			  }
			 
			
   			  
			  $(".moreCondition").hide();
			  $("#lessButton").hide();
			
			  //清空验证信息
			  $(".cleanCheck").click(function(){
				  cleanCheck();
			  });
			
			  $("#orderListSearch").validate({   
			       rules: {
						"map['orderCode']": {commonChar:true},
						"map['commodityName']": {unusualChar:true},
						"map['commodityCode']":{num1:true},
						"map['consigneeName']": {unusualChar:true},
						//"map['consigneeMobile']": {cellphone:true},
						"map['agencyNumber']":{num1:true},
						"map['customerAccount']": {unusualChar:true},
						"map['stockOutID']": {num1:true},
						"map['settlementNo']":{num1:true},
						"map['distributionId']":{num1:true}
			   		}   
			   		,submitHandler: function (form) {
			   			$("#pageNo").val(1);
			   			var pds= $('#payDateStart').val();
			   			var pde= $('#payDateEnd').val();
			   			if(pds.length>0&&0==pde.length){
							alert('请选择支付结束时间！');
							return;
				   		}else if(pde.length>0&&0==pds.length){
				   			alert('请选择支付开始时间！');
							return;
					   	}
		                form.submit();
		            }
			  });
			  $("#payDateRange").change(function(){
				  //var range = $("#payDateRange").val();
					  $('#payDateStart').val(""); 
					  $('#payDateEnd').val("");
			  });
			  $("#createDateRange").change(function(){
				     // var range = $("#payDateRange").val();
					  $('#createDateStart').val(""); 
					  $('#createDateEnd').val("");
			  });
		
			  function recover(){
				  $(".condition").not(":disabled").val("");
			  };
			  
			  // 清空
			  $("#clean").click(function(){
				  recover();
			  });
			  
				// 查看评价
				$(".check").click(function() {
					var orderCode = $(this).attr("name");
				    $.blockUI({ message: ($('#question').empty().load("/app/assessShowAction.action",{orderCode:orderCode}))
				  	    , css: { position:'absolute',top:'20%' ,width: '430px' }
				    });
				});
				
			  
			  // 支付测试
			  $(".pay").click(function() {
				  var orderCode = $(this).attr("name");
		          $.blockUI({ message: ($('#question').empty().load("/app/orderItemshowRemarkAction.action",{orderCode:orderCode,currid:Math.random()}))
		              , css: { position:'absolute',top:'20%' ,width: '430px' }
		          });
				  //dialog("订单支付","iframe:/app/orderPayshowAction.action?orderCode="+orderCode,"430px","370px","iframe");
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
			  
		      // 撤单
		      $('.cd').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定将此订单撤销?',
			      			//'/app/orderPaycancelAction.action',
			      			//{orderCode:orderCode}
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:-1}
			      	);
		      });
		      
		      // 出库
		      $('.Outbound').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定此订单已出库?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:4}
			      	);
			      	/*
		            $.blockUI({ message: ($('#question').empty().load("/app/orderPayshowStockoutAction.action",{orderCode:orderCode,status:4,currid:Math.random()}))
		        	 	, css: { top:'30%', left:'35%' ,width: '310px'}
		            });
			      	*/
		      });
		      
		      // 配送
		      $('.ship').click(function(evt) {
			      	var logisticsOrderNo=$(this).attr('data-value');
			      	if(null==logisticsOrderNo || 0==logisticsOrderNo.length){
				      	alert('此订单没有填写配送！');
			      	}else{
				      	operate(
				      			'是否确定此订单已配送?',
				      			'/app/orderPaychangeStausAction.action',
				      			{orderCode:$(this).attr("name"),status:5}
				      	);
			      	}
		      });
		      
		      // 完成订单
		      $('.done').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定此订单已完成?',
			      			'/app/orderPaychangeStausAction.action',
			      			{orderCode:orderCode,status:6}
			      	);
		      });
		      
		      // 删除订单
		      $('.delete').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定删除此订单?',
			      			'/app/orderPaychangeDisabledAction.action',
			      			{orderCode:orderCode,disabled:2}
			      	);
		      });
		      
		      // 恢复订单
		      $('.recovery').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定恢复此订单?',
			      			'/app/orderPaychangeDisabledAction.action',
			      			{orderCode:orderCode,disabled:1}
			      	);
		      });
		      
		      // 永久删除订单
		      $('.drop').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定永久删除此订单?',
			      			'/app/orderPaychangeDisabledAction.action',
			      			{orderCode:orderCode,disabled:3}
			      	);
		      });
		      
		      // 改变标记状态
		      $('.handle').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定标记此订单为已处理?',
			      			'/app/orderPayhandleAction.action',
			      			{orderCode:orderCode,state:2}
			      	);
		      });
		      // 改变标记状态
		      $('.unhandle').click(function(evt) {
			      	var orderCode = $(this).attr("name");
			      	operate(
			      			'是否确定取消标记此订单?',
			      			'/app/orderPayhandleAction.action',
			      			{orderCode:orderCode,state:1}
			      	);
		      });
		      // 后台备注
		      $('.remark').click(function(evt) {
					var orderCode = $(this).attr("name");
				    $.blockUI({ message: ($('#question').empty().load("/app/orderItemshowRemarkAction.action",{orderCode:orderCode}))
				  	    , css: { position:'absolute',top:'20%' ,width: '480px' }
				    });
				});
		      
		      // 含药品/医疗器械订单审核通过
		      $('.medicCheckPass').click(function(evt) {
					var orderCode = $(this).attr("name");
			      	operate(
			      			'此订单包含药品/医疗器械，是否确认审核通过?',
			      			'/app/orderPaymedicCheckAction.action',
			      			{orderCode:orderCode,checkFlag:1}
			      	);
				});
		      // 含药品/医疗器械订单审核不通过
		      $('.medicCheckFail').click(function(evt) {
					var orderCode = $(this).attr("name");
			      	operate(
			      			'此订单包含药品/医疗器械，是否确认审核不通过?',
			      			'/app/orderPaymedicCheckAction.action',
			      			{orderCode:orderCode,checkFlag:0}
			      	);
				});
		
			  
			  // 选择本页
			  $("#checkPage").click(function(){
				  alert($(this).attr("checked")==true);
				  if($(this).attr("checked")==true){
					  $(":checkbox").not($("#checkAll")).removeAttr("checked");
					  //$(":checkbox").not($("#checkAll")).not($("#checkPage")).removeAttr("checked");
				  }else{
					  $(":checkbox").not($("#checkAll")).attr("checked","checked");
					  //$(":checkbox").not($("#checkAll"))..not($("#checkPage"))attr("checked","checked");
				  }
			  });
			  
			  //更多条件
			  $("#moreButton").click(function(){
				  $(".moreCondition").show();
				  $("#lessButton").show();
				  $("#moreButton").hide();
			  });
			  
			  //更少条件
			  $("#lessButton").click(function(){
				  $(".hideCondition").val("");
				  $(".moreCondition").hide();
				  $("#lessButton").hide();
				  $("#moreButton").show();
			  });
			  
			  // 选择所有
			  $("#checkAll").click(function(){
				  alert($(this).attr("checked")=="checked");
				  if($(this).attr("checked")=="checked"){
					  //$(":checkbox").not($("#checkAll")).removeAttr("checked");
					  $(":checkbox").not($(this)).removeAttr("checked");
				  }else{
					  $(":checkbox").not($(this)).attr("checked","checked");
					  //$(":checkbox").not($("#checkAll")).attr("checked","checked");
				  }
			  });
		
		
			  $("#select_other_support").click(function(){
				  if($("#select_other_support").val()=="2"){
					  $("#commerceNameSpan").css('display', 'block');
					  $("#commerceNameDiv").css('display', 'block');
					  }
				  if($("#select_other_support").val()=="" || $("#select_other_support").val()=="1"){
					  $("#commerceNameSpan").css('display', 'none');
					  $("#commerceNameDiv").css('display', 'none');
					  $("#autocomplete").val(null);
					  }
				  });
		var countriesArray;
		
			  $.ajax({
					async:'false',
					url:'/app/orderfindMerchantAction.action',
					success:function(data){
						  countriesArray = $.map(data, function (value, key) { 
							return { value: value, data: key }; 
							});
						 // product_add_countriesArray = countriesArray;
						$('#autocomplete').autocomplete({
					    	lookup: countriesArray,
					        minChars: 0
					    });
					},
					dataType:'json'
				}); 
		
			  
		});
		</script>
	</head>
	<body>
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:if test="map['byStatus']">
		<s:if test="map['status']==1">
		<s:set name="son_name" value="'待付款订单'" scope="request"/>
		</s:if>
		<s:if test="map['status']==3">
		<s:set name="son_name" value="'待出库订单'" scope="request"/>
		</s:if>
		<s:if test="map['status']==4">
		<s:set name="son_name" value="'待配送订单'" scope="request"/>
		</s:if>
		<s:if test="map['status']==5">
		<s:set name="son_name" value="'待完成订单'" scope="request"/>
		</s:if>
		<s:if test="map['status']==6">
		<s:set name="son_name" value="'已完成订单'" scope="request"/>
		</s:if>
		<s:if test="map['assess']==true">
		<s:set name="son_name" value="'已评价订单'" scope="request"/>
		</s:if>
		<s:if test='map["commerceId"]=="1"'>
		<s:set name="son_name" value="'有效订单(自营)'" scope="request"/>
		</s:if>
		<s:if test='map["commerceId"]=="2"'>
		<s:set name="son_name" value="'有效订单(第三方)'" scope="request"/>
		</s:if>
		<s:if test='map["nstatus"]!= null && map["nstatus"]!=""'>
		  <input type="hidden" id="checkpage50" value="<s:property value="map['p50']"/>"/>
		</s:if>
		</s:if>
		<s:else>
		<s:set name="son_name" value="'订单列表查询'" scope="request"/>
		</s:else>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/orderlistByMapAction.action" method="POST">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
				<th align="right">订单号：</th>
		        <td>
				    <input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
				</td>
				<th align="right">订单状态：</th>
		        <td>
		           <s:if test="map['byStatus']">
		           <input type="hidden" name="map['byStatus']" value='true'/>
		           <input type="hidden" name="map['status']" value='<s:property value="map['status']"/>'/>
				   <select disabled="disabled" class="condition" name="map['status']" value='<s:property value="map['status']"/>' style="width:156px">
				   		<s:if test='map["nstatus"]!= null && map["nstatus"]!=""'>
				   			<option value="">有效订单</option>
				   		</s:if>
				   		<s:else>
				   		<option value="" <s:if test='map["status"]==""'>selected="selected"</s:if> >全部</option>
				   		<s:iterator id="st" value="orderStatus">
				   		<option value="<s:property value='#st.key'/>" <s:if test='map["status"]==#st.key'>selected="selected"</s:if> ><s:property value="#st.value"/></option>
				   		</s:iterator>
				   		</s:else>
				   </select>
				   </s:if>
				   <s:else>
				   <select class="condition" name="map['status']" value='<s:property value="map['status']"/>' style="width:156px">
				   		<option value="" <s:if test='map["status"]==""'>selected="selected"</s:if> >全部</option>
				   		<s:iterator id="st" value="orderStatus">
				   		<s:if test="#st.key!=16">
				   		<option value="<s:property value='#st.key'/>" <s:if test='map["status"]==#st.key'>selected="selected"</s:if> ><s:property value="#st.value"/></option>
				   		</s:if>
				   		</s:iterator>
				   </select>
				   </s:else>
				</td>
			</tr>
			<tr>		
				<th align="right">下单账号：</th>
		        <td>
				    <input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
				</td>
				<th align="right">收货人：</th>
		        <td>
				    <input class="condition" name="map['consigneeName']" type="text" value='<s:property value="map['consigneeName']"/>'>
				</td>
				<th align="right">收货电话：</th>
		        <td>
				    <input class="condition" name="map['consigneeMobile']" type="text" value='<s:property value="map['consigneeMobile']"/>'>
				</td>
			</tr>
			<tr>
				<th align="right">下单时间从：</th>
		        <td>
				    <input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'>
				</td>
				<th align="right">下单时间到：</th>
		        <td>
					<input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'>
				</td>
				<th align="right">下单时间范围：</th>
		        <td>
				    <select class="condition" id="createDateRange" name="map['createDateRange']" style="width:156px">
				   		<option value="" <s:if test='map["createDateRange"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="today" <s:if test='map["createDateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				   		<option value="ysday" <s:if test='map["createDateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				   		<option value="tsweek" <s:if test='map["createDateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				   		<option value="lsweek" <s:if test='map["createDateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				   		<option value="tsmonth" <s:if test='map["createDateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				   		<option value="lsmonth" <s:if test='map["createDateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				   </select>
				</td>
			</tr>
			<tr>
				<th align="right">支付时间从：</th>
		        <td>
		       		<input type="hidden" id="nstatus" name="nstatus"  value='<s:property value="map['nstatus']"/>' />
				    <input class="Wdate condition" id="payDateStart" name="map['payDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['payDateStart']"/>'>
				</td>
				<th align="right">支付时间到：</th>
		        <td>
					<input class="Wdate condition" id="payDateEnd" name="map['payDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'payDateStart\')}'})" type="text" value='<s:property value="map['payDateEnd']"/>'>
				</td>
				<th align="right">支付时间范围：</th>
		        <td>
				    <select class="condition" id="payDateRange" name="map['payDateRange']" style="width:156px">
				   		<option value="" <s:if test='map["payDateRange"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="today" <s:if test='map["payDateRange"]=="today"'>selected="selected"</s:if> >今天</option>
				   		<option value="ysday" <s:if test='map["payDateRange"]=="ysday"'>selected="selected"</s:if> >昨天</option>
				   		<option value="tsweek" <s:if test='map["payDateRange"]=="tsweek"'>selected="selected"</s:if> >本周</option>
				   		<option value="lsweek" <s:if test='map["payDateRange"]=="lsweek"'>selected="selected"</s:if> >上周</option>
				   		<option value="tsmonth" <s:if test='map["payDateRange"]=="tsmonth"'>selected="selected"</s:if> >本月</option>
				   		<option value="lsmonth" <s:if test='map["payDateRange"]=="lsmonth"'>selected="selected"</s:if> >上月</option>
				   </select>
				</td>
			</tr>
			<tr class="moreCondition">		
				<th align="right">出库单号：</th>
		        <td>
				    <input class="condition hideCondition" name="map['stockOutID']" type="text" value='<s:property value="map['stockOutID']"/>'>
				</td>
				<th align="right">结转单号：</th>
				<td>
				     <input class="condition hideCondition" name="map['settlementNo']" type="text" value='<s:property value="map['settlementNo']"/>'>
				</td>
				<th align="right">配送单号：</th>
		        <td>
				    <input class="condition hideCondition" name="map['distributionId']" type="text" value='<s:property value="map['distributionId']"/>'>
				</td>
			</tr>
			<tr class="moreCondition">			
				<th align="right">商品名称：</th>
		        <td>
				    <input class="condition hideCondition" name="map['commodityName']" type="text" value='<s:property value="map['commodityName']"/>'>
				</td>	
				<s:if test='map["commerceId"]=="2"'>
					<th align="right"><span class="moreCondition">所属商家：</span></th>
				<td><div id="commerceNameDiv">	
		          <input id="autocomplete" name="map['commerceName']" value='<s:property value="map['commerceName']"/>' type="text" class="condition hideCondition">
				   </div>
				</s:if>
				<s:else>
					<th align="right">客服账户：</th>
			        <td>
					    <input class="condition hideCondition" name="map['agencyNumber']" type="text" value='<s:property value="map['agencyNumber']"/>'>
					</td>
				</s:else>
				<th align="right">商品编号：</th>
				<td>
				     <input class="condition hideCondition" name="map['commodityCode']" type="text" value='<s:property value="map['commodityCode']"/>'>
				</td>
			</tr>
			<tr class="moreCondition">
				<th align="right"><span class="moreCondition">订单来源：</span></th>
				<td>	
		           <span class="moreCondition">
		           	<select class="condition hideCondition" name="map['orderSource']" value='<s:property value="map['orderSource']"/>' style="width:156px">
		           		<option value="" <s:if test='map["orderSource"]==""'>selected="selected"</s:if> >全部</option>
		           		<option value="1" <s:if test='map["orderSource"]=="1"'>selected="selected"</s:if> >网页</option>
		           		<option value="2" <s:if test='map["orderSource"]=="2"'>selected="selected"</s:if> >APP</option>
		           		<!-- <option value="3" <s:if test='map["orderSource"]=="3"'>selected="selected"</s:if> >微信</option> -->
		           		<option value="4" <s:if test='map["orderSource"]=="4"'>selected="selected"</s:if> >WAP</option>
		           	</select>
				   </span>
				</td>
				<th align="right"><span class="moreCondition">销售类型：</span></th>
				<td>	
		           <span class="moreCondition">	
				   <select id="select_other_support" class="condition hideCondition" name="map['commerceId']" value='<s:property value="map['commerceId']"/>' style="width:156px">
				       	<option value="" <s:if test='map["commerceId"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["commerceId"]=="1"'>selected="selected"</s:if> >自营</option>
				   		<option value="2" <s:if test='map["commerceId"]=="2"'>selected="selected"</s:if> >第三方</option>
				   </select>
				   </span>
				</td>
				<s:if test='map["commerceId"] !="2"'>
			  	<th align="right"><span  id="commerceNameSpan" <s:if test='map["commerceName"]==""'> style="display:none" </s:if>><span class="moreCondition">所属商家：</span></span></th>
				<td><div id="commerceNameDiv" <s:if test='map["commerceName"]==""'> style="display:none" </s:if>>	
		          <input id="autocomplete" name="map['commerceName']" value='<s:property value="map['commerceName']"/>' type="text" class="condition hideCondition">
				   </div>
				</td>
			   </s:if>
			</tr>
			<tr class="moreCondition">
				<th align="right"><span class="moreCondition">		下单类型：</span></th>
		        <td>	
		           <span class="moreCondition">	
				   <select class="condition hideCondition" name="map['orderPurchaserType']" value='<s:property value="map['orderPurchaserType']"/>' style="width:156px">
				   		<option value="" <s:if test='map["orderPurchaserType"]==""'>selected="selected"</s:if> >全部</option>
				   		<s:iterator id="type" value="purchaserTypes">
				   		<option value="<s:property value='#type.key'/>" <s:if test='map["orderPurchaserType"]==#type.key'>selected="selected"</s:if> ><s:property value="#type.value"/></option>
				   		</s:iterator>
				   </select>
				   </span>
				</td>
				<th align="right"><span class="moreCondition">订单类型：</span></th>
				<td>	
		           <span class="moreCondition">	
				   <select class="condition hideCondition" name="map['order_Type']" value='<s:property value="map['order_Type']"/>' style="width:156px">
				       	<option value="" <s:if test='map["order_Type"]==""'>selected="selected"</s:if> >全部</option>
				       	<s:iterator id="type2" value="orderType">
					       	<s:if test="#type2.key==1 or #type2.key==7">
						   		<option value="<s:property value='#type2.key'/>" <s:if test='map["order_Type"]==#type2.key'>selected="selected"</s:if> ><s:property value="#type2.value"/></option>
					       	</s:if>
				   		</s:iterator>
				   </select>
				   </span>
				</td>
				
		        <s:if test='map["commerceId"]!="2"'>
		        <th align="right"><span class="moreCondition">客服处理：</span></th>
				<td>	
					<span class="moreCondition">
				    <select class="condition hideCondition" id="handle" name="map['handle']" value='<s:property value="map['handle']"/>'  style="width:156px">
				   		<option value="" <s:if test='map["handle"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["handle"]=="1"'>selected="selected"</s:if> >未处理</option>
				   		<option value="2" <s:if test='map["handle"]=="2"'>selected="selected"</s:if> >已处理</option>
				   </select>	
				   </span>
				</td>
				</s:if>
			</tr>
			<tr >
				<td colspan="6" align="right" >
				
				
                <input type="submit" class="queryBtn" value="" class="btn-custom"/>
					<input type="reset" value=" 重置 " class="btn-custom cleanCheck"/>
					<input type="button" value=" 清空 " id="clean" class="btn-custom cleanCheck"/>
				
					<input type="button" value="更多选项 " id="moreButton" class="btn-custom" />
					<input type="button" value="隐藏选项" id="lessButton" class="btn-custom" />
		        </td>
		    </tr>
		 </table>
		<br/>
		<br/>
		<b>&nbsp;&nbsp;&nbsp;&nbsp;订单总金额：<s:property value="totalMoney"/>&nbsp;&nbsp;&nbsp;&nbsp;实付总额：<s:property value="totalActual"/></b>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="10%">订单号</th>
		   		<!--删除 <th width="3%">渠道</th> -->
		   		<th width="5%">订单来源</th>
		   		<th width="10%">下单账号</th>
		   		<th width="4%">销售类型</th>
		   		<s:if test='map["commerceId"]=="2"'>
		   		<th width="10%">公司名称</th>
		   		</s:if>
		   		<th width="5%">收货人</th>
		   		<th width="4%">订单状态</th>
		   		<th width="8%">订单金额</th>
		   		<th width="8%">订单生成时间</th>
		   		<s:if test='map["commerceId"]!="2"'>
		   		<th width="4%">处理状态</th>
		   		<th width="5%">处理人</th>
		   		</s:if>
		   		<th width="10%">操作</th>
			</tr>
			
			<s:iterator id="order" value="page.dataList">
			<tr>
		   		 <td>
		   		 	<s:if test="#order.orderStatus==16">&nbsp;&nbsp;&nbsp;&nbsp;[主]</s:if><s:if test="#order.parentOrderCode!=null">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;┗</s:if><a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>"><s:property value="#order.orderCode"/></a>
		   		 	<s:if test="null!=#order.orderOperationRemark"><br/><font style="color:red;"><s:property value="#order.orderOperationRemark"/></font></s:if>	
		   		 </td>
		  <%--  		 <td><s:property value="#order.orderChannel"/></td> --%>
		   		 <td><s:property value="#order.orderSourceStr"/></td>
		   		 <td><s:property value="#order.customerAccount"/></td>
		   		 <td><s:property value="null==#order.commerceId?'自营':'第三方'"/></td>
		   		 <s:if test='map["commerceId"]=="2"'>
		   		 <td><s:property value="#order.commerceName"/></td>
		   		 </s:if>
		   		 <td><s:property value="#order.consigneeName"/></td>
		   		 <td><s:property value="#order.orderStatusStr"/></td>
		   		 <td><font size="1">
			   		   <s:property value="#order.amountPayable"/><br/>
			   		 <s:if test="orderType==7">		<!-- 预售订单时展示 -->
				   		定金：<s:property value="#order.depositSum"/><s:if test="#order.paidDeposit>0&&#order.paidDeposit!=null">  (已支付)</s:if><s:else>  (未支付)</s:else><br/>
				   		尾款：<s:property value="#order.finalPayment"/><s:if test="#order.noFinalPayment>0">  (已支付)</s:if><s:else>  (未支付)</s:else>
			   		</s:if>
		   		</font></td><td><s:date name="#order.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <s:if test='map["commerceId"]!="2"'>
		   		 <td><s:property value="#order.handleState==2?'已处理':'未处理'"/></td>
		   		 <td><s:property value="#order.handleAccount"/></td>
		   		 </s:if>
				 <td>
				 	 <a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>">查看</a> 
				 	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="remark" name="<s:property value='#order.orderCode'/>">备注</a> 
				 	 <s:if test="#order.handleState!=2">
				 	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="handle" name="<s:property value='#order.orderCode'/>">处理标记</a> 
					 </s:if>
					 <s:else>
					 <s:if test="#order.handleAccount==loginAccount">
				 	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="unhandle" name="<s:property value='#order.orderCode'/>">取消标记</a> 
					 </s:if>
					 </s:else>
					 <%-- 
					 <s:if test="#order.orderStatus==1||#order.orderStatus==2||#order.orderStatus==20||#order.orderStatus==21||#order.orderStatus==22">
				 	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="cd" name="<s:property value='#order.orderCode'/>">撤单</a>
				 	 </s:if>
					 
					 <s:if test="#order.orderStatus==1">
					 |&nbsp;&nbsp;<a href="javascript:void(0);" class="pay" name="<s:property value='#order.orderCode'/>">支付测试</a>
					 </s:if>
					 --%>
					 <s:if test="#order.orderStatus==3">
					 |&nbsp;&nbsp;<a href="javascript:void(0);" class="Outbound" name="<s:property value='#order.orderCode'/>">确认出库</a>
					 </s:if>
					<%-- <s:if test="#order.orderStatus==4">
					 |&nbsp;&nbsp;<a href="javascript:void(0);" class="ship" data-value="<s:property value='#order.logisticsOrderNo'/>" name="<s:property value='#order.orderCode'/>">确认配送</a>
					 </s:if>
					 
					 <s:if test="#order.orderStatus==5">
					 |&nbsp;&nbsp;<a href="javascript:void(0);" class="done" name="<s:property value='#order.orderCode'/>">确认完成</a>
					 </s:if>
					 <s:if test="#order.assessStatus!=2&&#order.assessStatus!=3">
					 |&nbsp;&nbsp;<a href='/app/fillOutOrderAssess.action?orderCode=<s:property value="#order.orderCode"/>&customerAccount=<s:property value="#order.customerAccount"/>&orderId=<s:property value="#order.orderId"/>'>订单评价</a>
					 </s:if>
					 </s:if>
					 --%>
					 <s:if test="#order.orderStatus==6&&(#order.assessStatus==2||#order.assessStatus==3)">
					 	|&nbsp;&nbsp;<a href="javascript:void(0);" class="check" name="<s:property value='#order.orderCode'/>">查看评价</a>
					 </s:if>
					 <%--
					 <s:if test="#order.disabled==1&&(#order.orderStatus==-1||#order.orderStatus==6||#order.orderStatus==7)">
					 	|&nbsp;&nbsp;<a href="javascript:void(0);" class="delete" name="<s:property value='#order.orderCode'/>">删除</a>
					 </s:if>
					 <s:if test="#order.disabled==2">
					 	|&nbsp;&nbsp;<a href="javascript:void(0);" class="recovery" name="<s:property value='#order.orderCode'/>">恢复</a>
					 	|&nbsp;&nbsp;<a href="javascript:void(0);" class="drop" name="<s:property value='#order.orderCode'/>">永久删除</a>
					 </s:if>		
					  --%>
					  <!-- 
						|&nbsp;&nbsp;<a href='/app/orderBackdownshowBackDownAction.action?returnPage=1&orderCode=<s:property value="#order.orderCode"/>'>退换货申请</a>
				 		-->
				 		<!-- 
					 	<s:if test="#order.orderStatus==2&&#order.medicFlag==1">
						 	<s:if test="#order.checkFlag==0">
							 |&nbsp;&nbsp;<a href="javascript:void(0);" class="medicCheckPass" name="<s:property value='#order.orderCode'/>">审核通过</a>
							 </s:if>
						 	<s:if test="#order.checkFlag==1">
							 |&nbsp;&nbsp;<a href="javascript:void(0);" class="medicCheckFail" name="<s:property value='#order.orderCode'/>">审核不通过</a>
							 </s:if>
						 </s:if>
						 -->
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
		<div id="question" style="display:none"></div>
	</body>
</html>