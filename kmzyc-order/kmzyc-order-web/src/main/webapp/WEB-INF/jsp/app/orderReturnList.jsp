<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退换货列表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript">
$(function(){
	  //清空验证信息
	  $('.cleanCheck').click(function(){
		  cleanCheck();
	  });
	
	  $("#returnSearch").validate({     
	       rules: {
				"map['orderCode']": {num1:true},
				"map['proposer']": {unusualChar:true},
				"map['checker']":{unusualChar:true},
				"map['alterCode']": {num1:true}
	   		}
	   		,submitHandler: function (form) {
	   			$("#pageNo").val(1);
                form.submit();
            }
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
      };
      
      // 确认退款操作
      function operatePay(msg,action,data) {
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
    	  }else{
    		  location.reload(); 
    	  }
    	  
      };
      
      
      // 退货
      $('.returning').click(function(evt) {
	      	var alterCode = $(this).attr("name");
	      	operate(
	      			'是否确定此退换货单已退货?',
	      			'/app/orderBackdownchangeStatusAction.action',
	      			{alterCode:alterCode,status:3}
	      	);
      });
	  
      // 取件
      $('.pickup').click(function(evt) {
	      	var alterCode = $(this).attr("name");
	      	operate(
	      			'是否确定此退换货单已取件?',
	      			'/app/orderBackdownchangeStatusAction.action',
	      			{alterCode:alterCode,status:4}
	      	);
      });
      
 	// 退款
	$('.backpay').click(function(evt) {
        //需要增加超时退款
	        var fromType=$(this).attr("coId");		//通过自定义属性设定 订单服务类型  ‘2’ 超时， ‘1’普通
	    	var alterCode =$(this).attr('name');
	        if(fromType!=null&&fromType!=''&&fromType!='undefined'&&fromType==2){
				// 超时未发货赔付 
				if(0==$(this).attr('flag')){
     	    			 $(this).attr('flag',1);
	      		operatePay(
		      			'是否确定退款?',
		      			'/app/orderBackdownchangeYSStaAction.action',
		      			{alterCode:alterCode,status:61,fromType:fromType}
		      	);
				}else{
    				alert('退换货号'+alterCode+'已提交退款，请稍候！');
              }
	      	 }else{
	      		var orderType=$(this).attr("orderType_ys");		//通过自定义属性获取订单类型
	      	    if(orderType!=null&&orderType!=''&&orderType!='undefined'&&orderType=='7'){
	      			// 预售订单退款
	      			if(0==$(this).attr('flag')){
     	    			 $(this).attr('flag',1);
	      	    	operatePay(
			      			'是否确定退款?',
			      			'/app/orderBackdownchangeYSStaAction.action',
			      			{alterCode:alterCode,status:61,fromType:fromType}
			      	);
	      			}else{
	    				alert('退换货号'+alterCode+'已提交退款，请稍候！');
	              }
	      	    } else{
	      			// 普通订单退款 
	      			if(0==$(this).attr('flag')){
     	    			 $(this).attr('flag',1);
    	      		 		operatePay('是否确定退款?',
    	      		 				'/app/orderBackdownchangeStatusAction.action',
    	      		 				{'alterCode':alterCode,'status':61}
    	      		 		); 
		              }else{
		    				alert('退换货号'+alterCode+'已提交退款，请稍候！');
		              }
	      	    }
	      	}
	});
      
      // 发货
      $('.ship').click(function(evt) {
	      	var alterCode = $(this).attr("name");
		    $.blockUI({ message: ($('#question').empty().load("/app/orderBackdownreturnCodeAction.action",{alterCode:alterCode}))
		  	    , css: { position:'absolute',top:'20%' ,width: '280px' }
		    });
      });
      
      // 原件返回
      $('.backShop').click(function(evt) {
	      	var alterCode = $(this).attr("name");
		    $.blockUI({ message: ($('#question').empty().load("/app/orderBackdownreturnCodeAction.action",{alterCode:alterCode}))
		  	    , css: { position:'absolute',top:'20%' ,width: '375px' }
		    });
      });
      
      // 完成
	$('.done').click(function(evt) {
		var alterCode = $(this).attr("name");
		operate('是否确定此退换货单已完成?','/app/orderBackdownchangeStatusAction.action',{alterCode:alterCode,status:7});
	});
});
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单管理'" scope="request"/>
<s:set name="son_name" value="'退换货查询'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form id="returnSearch" action="/app/orderBackdownlistByMapAction.action" method="POST">
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">订单号：</th>
        <td>
		    <input class="condition" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
		</td>
		<th align="right">申请人：</th>
        <td>
		    <input class="condition" name="map['proposer']" type="text" value='<s:property value="map['proposer']"/>'>
		</td>
		<th align="right">审核人：</th>
        <td>
		    <input class="condition" name="map['checker']" type="text" value='<s:property value="map['checker']"/>'>
		</td>
	</tr>
	<tr>		
		<th align="right">退换货编号：</th>
        <td>
		    <input class="condition" name="map['alterCode']" type="text" value='<s:property value="map['alterCode']"/>'>
		</td>
		<th align="right">申请 从：</th>
        <td><input class="condition" type="text" name="map['createBegin']" value='<s:property value="map['createBegin']"/>' 
        id="createBegin"	onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'createEnd\')||\'2020-10-01\'}'})"/></td>
		<th align="right">审核 从：</th>
        <td><input class="condition" type="text" name="map['finishBegin']" value='<s:property value="map['finishBegin']"/>' 
        id="finishBegin"	onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'finishEnd\')||\'2020-10-01\'}'})"/></td>
	</tr>
	<tr>		
		<th align="right">状态：</th>
        <td>
		   <select class="condition" name="map['status']" value='<s:property value="map['status']"/>' style="width:156px">
		   		<option value="" <s:if test='map["status"]==""'>selected="selected"</s:if> >全部</option>
		   		<s:iterator id="type" value="proposeStatus">
		   		<option value="<s:property value='#type.key'/>" <s:if test='map["status"]==#type.key'>selected="selected"</s:if> ><s:property value="#type.value"/></option>
		   		</s:iterator>
		   </select>
		</td>
		<th align="right">时间 到：</th>
        <td><input class="condition" type="text" name="map['createEnd']" value='<s:property value="map['createEnd']"/>' 
        id="createEnd" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'createBegin\')}',maxDate:'2020-10-01'})"/></td>
		<th align="right">时间 到：</th>
        <td><input class="condition" type="text" name="map['finishEnd']" value='<s:property value="map['finishEnd']"/>' 
        id="finishEnd" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'finishBegin\')}',maxDate:'2020-10-01'})"/></td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<td align="right" colspan="4">
        <input type="submit" class="queryBtn" value="">
			<input type="reset" value=" 重置 " class="btn-custom cleanCheck">
			<input type="button" value=" 清空 " id="clean" class="btn-custom cleanCheck">&nbsp;&nbsp;
			
		</td>
		<td colspan="2"></td>
	</tr>
</table>

<br/>

<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
	<tr>
		<!-- 
		<th>本页<input name="checkPage" id="checkPage" type="checkbox" value="true"/>所有<input name="checkAll" id="checkAll" type="checkbox" value="true"/></th>
   		 -->
   		<th>申请人</th>
   		<th>退换货编号</th>
   		<th>订单号</th>
   		<th>商品标题</th>
   		<th>商品SKU</th>
   		<th>审核人</th>
   		<th>服务类型</th>
   		<th>状态</th>
   		<th>申请时间</th>
   		<th>审核时间</th>
   		<th>完成时间</th>
   		<th>操作</th>
	</tr>
	
	<s:iterator id="alter" value="page.dataList">
	<tr>
		 <!-- 
		 <td><input class="alterCodes" type="checkbox"/></td>
   		  -->
   		 <td><s:property value="#alter.proposer"/></td>
   		 <s:if test="#alter.orderType==7">
			 	 <s:if test="#alter.alterType==4">
					<td><a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&fromType=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>"><s:property value="#alter.orderAlterCode"/></a></td>
				</s:if>
				<s:else>
					 <td><a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&fromType=1&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>"><s:property value="#alter.orderAlterCode"/></a></td>
				</s:else>
		 </s:if>
		 <s:else>
  		 	<td><a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>"><s:property value="#alter.orderAlterCode"/></a></td>
  		 </s:else>
   		 
   		 <td><a href="/app/orderItemdetailAction.action?orderCode=<s:property value='#alter.orderCode'/>"><s:property value="#alter.orderCode"/></a></td>
   		 <td><s:property value="#alter.commodityName"/></td>
   		 <td><s:property value="#alter.skuCode"/></td>
   		 <td><s:property value="#alter.checker"/></td>
   		 <td><s:property value="#alter.alterTypeStr"/></td>
   		 <td><s:property value="#alter.proposeStatusStr"/></td>
   		 <td><s:date name="#alter.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
   		 <td><s:date name="#alter.checkDate" format="yyyy-MM-dd HH:mm:ss" /></td>
   		 <td><s:date name="#alter.finishDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		 <td>
		 
		 	 <s:if test="#alter.orderType==7">
			 	 <s:if test="#alter.alterType==4">
					<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&fromType=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>">查看</a>
				</s:if>
				<s:else>
					 <a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&fromType=1&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>">查看</a>
				</s:else>
			 </s:if>
			 
			 <s:else> 
			 	 <a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>">查看</a>
			 </s:else> 
			 
		 	 <s:if test="#alter.proposeStatus==1">
			 	 <s:if test="#alter.orderType==7">
			 	 	<s:if test="#alter.alterType==4">
					 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
				 	 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">审核</a>
					</s:if>
					<s:else>
					 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=1&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
				 	 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">审核</a>
					</s:else>
			 	  </s:if>
			 	 <s:else> 
			 	 	 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=1&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
			 		 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">审核</a>
				 </s:else>
			 </s:if> 
			 
			 <%--
			 <s:if test="#alter.proposeStatus==2">
		  	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="returning" name="<s:property value='#alter.orderAlterCode'/>">确认退货</a>
		  	 </s:if>
			 <s:if test="#alter.proposeStatus==3">
		  	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="pickup" name="<s:property value='#alter.orderAlterCode'/>">确认取件</a>
		  	 </s:if>
			 <s:if test="#alter.proposeStatus==4">
		  	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="quality" name="<s:property value='#alter.orderAlterCode'/>">质检</a>
		  	 </s:if>
		  	 --%>
			 <s:if test="#alter.proposeStatus==51">		
			  
			 	 <s:if test="#alter.orderType==7">
			 	 	<s:if test="#alter.alterType==4">
				 	 |&nbsp;&nbsp;<a href="javascript:void(0);" flag="0" class="backpay" coId="2" orderType_ys="<s:property value='#alter.orderType'/>" name="<s:property value='#alter.orderAlterCode'/>">确认退款</a>
					</s:if>
					<s:else>
					|&nbsp;&nbsp;<a href="javascript:void(0);" flag="0" class="backpay" coId="1" orderType_ys="<s:property value='#alter.orderType'/>" name="<s:property value='#alter.orderAlterCode'/>">确认退款</a>
					</s:else>
			 	  </s:if>
			 	 <s:else> 
					|&nbsp;&nbsp;<a href="javascript:void(0);" flag="0" class="backpay" coId="1" orderType_ys="<s:property value='#alter.orderType'/>" name="<s:property value='#alter.orderAlterCode'/>">确认退款</a>
				 </s:else>  	 
		  	 </s:if>
		  	 
			 <s:if test="#alter.proposeStatus==54">		  	
		  	<%--  |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&alterCode=<s:property value='#alter.orderAlterCode'/>" 
		  	 	class="bdck" name="<s:property value='#alter.orderAlterCode'/>">重新审核</a> --%>
			 	 <s:if test="#alter.orderType==7">
			 	 	<s:if test="#alter.alterType==4">
					 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
				 	 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">重新审核</a>
					</s:if>
					<s:else>
					 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=2&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
				 	 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">重新审核</a>
					</s:else>
			 	  </s:if>
			 	 <s:else> 
			 	 	 |&nbsp;&nbsp;<a href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&fromType=1&alterCode=<s:property value='#alter.orderAlterCode'/>&orderType=<s:property value='#alter.orderType'/>" 
			 		 class="bdck" name="<s:property value='#alter.orderAlterCode'/>">重新审核</a>
				 </s:else>
		  	 </s:if>
		  	
			 <s:if test="#alter.proposeStatus==53">		  	
		  	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="backShop" name="<s:property value='#alter.orderAlterCode'/>">返回原件</a>
		  	 </s:if>
		  	  <%--
			 <s:if test="#alter.proposeStatus==61||#alter.proposeStatus==62||#alter.proposeStatus==63">		  	 
		  	 |&nbsp;&nbsp;<a href="javascript:void(0);" class="done" name="<s:property value='#alter.orderAlterCode'/>">确认完成</a>
		     </s:if>
		  	 --%>
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

<div id="question" style="display:none;"></div>

</body>
</html>

