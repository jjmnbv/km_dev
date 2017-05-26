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
		$(function(){
			
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
						"map['consigneeMobile']": {cellphone:true},
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
				  //var range = $("#payDateRange").val();
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
		<s:set name="parent_name" value="'返利网订单管理'" scope="request"/>
		<s:set name="name" value="'返利网订单列表'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderListSearch" action="/app/order51FanliListByMap.action" method="POST">
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
				   		<option value="<s:property value='#st.key'/>" <s:if test='map["status"]==#st.key'>selected="selected"</s:if> ><s:property value="#st.value"/></option>
				   		</s:iterator>
				   </select>
				   </s:else>
				</td>
								<th align="right">下单账号：</th>
		        <td>
				    <input class="condition" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
				</td>
			</tr>
			<tr>		

				<th align="right">收货人：</th>
		        <td>
				    <input class="condition" name="map['consigneeName']" type="text" value='<s:property value="map['consigneeName']"/>'>
				</td>
				<th align="right">收货电话：</th>
		        <td>
				    <input class="condition" name="map['consigneeMobile']" type="text" value='<s:property value="map['consigneeMobile']"/>'>
				</td>
                				<th align="right">下单时间从：</th>
		        <td>
				    <input class="Wdate condition" id="createDateStart" name="map['createDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['createDateStart']"/>'>
                    到
                    <input class="Wdate condition" id="createDateEnd" name="map['createDateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createDateStart\')}'})" type="text" value='<s:property value="map['createDateEnd']"/>'>
				</td>
			</tr>
			<tr>

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
                				<th align="right">支付时间从：</th>
		        <td>
		       		<input type="hidden" id="nstatus" name="nstatus"  value='<s:property value="map['nstatus']"/>' />
				    <input class="Wdate condition" id="payDateStart" name="map['payDateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'payDateEnd\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['payDateStart']"/>'>
                    到
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
				<th align="right"></th>
		        <td>
				</td>
				<th align="right"></th>
				<td>
				</td>
				<th align="right"></th>
		        <td>
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
					<th align="right"></th>
			        <td>
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
			<tr >
				<!--<th align="right"><span class="moreCondition">	</span></th>
		        <td>	
		           <span class="moreCondition">	
				   </span>
				</td>-->
				
				<td align="right" colspan="6">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="reset" value=" 重置 " class="btn-custom cleanCheck"/>&nbsp;&nbsp;
					<input type="button" value=" 清空 " id="clean" class="btn-custom cleanCheck"/>&nbsp;&nbsp;
					<input type="submit" class="queryBtn" value=""/>&nbsp;&nbsp;
					<input type="button"class="btn-custom"  value="更多选项 " id="moreButton" />
					<input type="button" class="btn-custom" value="隐藏选项" id="lessButton" />
		        </td>
		       
			</tr>
		</table>
		
		<br/>
		<b>&nbsp;&nbsp;&nbsp;&nbsp;订单总金额：<s:property value="totalMoney"/>&nbsp;&nbsp;&nbsp;&nbsp;实付总额：<s:property value="totalActual"/></b>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="15%">订单号</th>
		   		<th width="6%">订单来源</th>
		   		<th width="10%">下单账号</th>
		   		<th width="6%">销售类型</th>
		   		<s:if test='map["commerceId"]=="2"'>
		   		<th width="10%">公司名称</th>
		   		</s:if>
		   		<th width="10%">收货人</th>
		   		<th width="6%">订单状态</th>
		   		<th width="6%">返利佣金</th>
		   		<th width="6%">订单金额</th>
		   		<th width="10%">订单生成时间</th>
		   		<th width="10%">订单支付时间</th>
		   		<th width="10%">操作栏</th>
			</tr>
			<s:if test="page.dataList.size==0">
			<tr style="background-color: rgb(238, 249, 243);"><td  colspan="11">没有符合条件的订单，请尝试其它查询条件!</td></tr>
			</s:if>
			<s:else>
			<s:iterator id="order" value="page.dataList">
			<tr>
		   		 <td>
		   		 	<a href="/app/orderItemOutAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>">
		   		 	<s:property value="#order.orderCode"/></a>
		   		 	<s:if test="null!=#order.orderOperationRemark"><br/><font style="color:red;"><s:property value="#order.orderOperationRemark"/></font></s:if>	
		   		 </td>
		   		 <td><s:property value="#order.orderSourceStr"/></td>
		   		 <td><s:property value="#order.customerAccount"/></td>
		   		 <td><s:property value="null==#order.commerceId?'自营':'第三方'"/></td>
		   		 <s:if test='map["commerceId"]=="2"'>
		   		 <td><s:property value="#order.commerceName"/></td>
		   		 </s:if>
		   		 <td><s:property value="#order.consigneeName"/></td>
		   		 <td><s:property value="#order.orderStatusStr"/></td>
		   		  <td><s:property value="#order.orderCommissionl"/></td>
		   		 <td><s:property value="#order.amountPayable"/></td>
		   		 <td><s:date name="#order.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		 <td><s:date name="#order.payDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		   		  <td><a href="/app/orderItemOutAction.action?orderCode=<s:property value='#order.orderCode'/>&map['byStatus']=<s:property value='map["byStatus"]'/>">查看</a></td>
			</tr>
			</s:iterator>
			</s:else>
			
			
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