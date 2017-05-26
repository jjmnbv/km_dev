<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退换货单详情</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<style type="text/css">
li {list-style-type:none;line-height:25px}
.Complaints-text{width:600px;height:100px}
.upload-photos{ padding:10px 0px;}
.upload-photos li{ height:50px; border:1px solid #e6e6e6; float:left; margin-right:10px;}
.upload-photos li img{ width:50px; height:50px;}
.upload-photos-Address{ background-color:#fffdee; border:1px solid #edd28b; padding:10px 0px; height:100%; width:600px;}
.upload-photos-Address label{ float:left;}
.upload-photos-form{}
.upload-photos-form span{ padding-left:45px;}
.upload-photos-Address .tisp{ padding-left:80px;}
.upload-photos-Address .tisp ul{ display:block; float:left;}
.upload-photos-Address .tisp ul li{ line-height:25px; color:#e1a156;}
font{color:blue}
</style>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/order_tab.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript">
$(function(){
	
	//取消
    //$('#cancel').bind('click', $.unblockUI);
	$('.backBtn').click(function(){
		history.go(-1);
	});
	
    $("#backForm").validate({     
	       rules: {
				"name": {required:true},
				"phone": {required:true,cellphone:true},
				"address":{required:true,realMaxlength:200}
    	   },
	   	   submitHandler: function (form) {
	            $.post(
	                	'/app/orderBackdownbackDownAction.action',
	               		$("#backForm").serialize(),
	  	           		function(result){
	  			            alert(result);
	  			            if(result.indexOf("失败")>0){
	  			            	history.go(0);
	  			            }else{
	  			            	location.href="/app/orderBackdownlistByMapAction.action";
	  			            }
	  	     			}
	            );		
          }
	});
	
    //sumbit
    $(".submit").click(function(){
    	
    	var flag = $(this).attr("name");
    	//alert(flag+flag.indexOf('ys_'));
    	if(flag.indexOf("ys_")>-1){
    		//超时赔付
    		//alert("超时赔付");
    		//return;
        	$(this).attr("disabled","disabled");
        	var alterCode = '<s:property value="alter.orderAlterCode"/>';
        	var type = $(this).attr("name")=="ys_pass"?2:-2;
            $.post(
                	'/app/orderBackdownbackDownCKYSAction.action',//修改aciton
               		{alterCode:alterCode,type:type,comment:$("#comment").val()},
     	           		function(result){
     			            alert(result);
      			            if(result.indexOf("失败")>-1){
      			            	history.go(0);
      			            }else if(result.indexOf("已配送")>-1){
      			            	$("#ys_pass").attr("disabled","disabled");
      			            }else{
      			            	location.href="/app/orderBackdownlistByMapAction.action";
      			            }
     	     			}
              );
    		
    	}else{
    		//判断是普通订单还是预售订单
    		var orderTypeJudge='<s:property value="orderType"/>';
    		if(orderTypeJudge!=null&&orderTypeJudge!=''&&orderTypeJudge!='undefined'&&orderTypeJudge=='7'){
    			 //预售
    			var totalTK = (parseFloat($("#returnMoney").val())+parseFloat($("#finalmoney").val())).toFixed(2);
    			var returnSum = $("#returnSum").val();
    			if(totalTK != returnSum){
    				alert("预售总金额计算错误！");
    				return;
    			} 
    			
    		}else{
    			//普通
        		 var totalTK = (parseFloat($("#returnMoney").val())+parseFloat($("#returnFare").val())+parseFloat($("#fareSubsidy").val())).toFixed(2);
    			var returnSum = $("#returnSum").val();
    			if(totalTK != returnSum){
    				alert("总金额计算错误！");
    				return;
    			}
    		} 
    		$(this).attr("disabled","disabled");
    		var alterCode = '<s:property value="alter.orderAlterCode"/>';
    		var type = $(this).attr("name")=="pass"?2:-2;
        	$.post(
            		'/app/orderBackdownbackDownCKAction.action',
           			{alterCode:alterCode,type:type,fareSubsidy:$("#fareSubsidy").val()
            			,returnMoney:$("#returnMoney").val(),returnFare:$("#returnFare").val(),returnSum:$("#returnSum").val(),preferentialAmount:$("#preferentialAmount").val(),comment:$("#comment").val()},
 	           			function(result){
 			            	alert(result);
  			            	if(result.indexOf("失败")>0){
  			            		history.go(0);
  			            	}else{
  			            		location.href="/app/orderBackdownlistByMapAction.action";
  			            	}
 	     				}
          	);
        
    	}
        
    });
  
    // 操作
    function operate(msg,action,data) {
  	   if(confirm(msg)){
  		  $.post(
	            	action,
	            	data,
	           		function(result){
			            alert(result);
			            if(result.indexOf("失败")>-1){
			            	
			            	history.go(0);
			            }else{
  			            	location.href="/app/orderBackdownlistByMapAction.action";
			            }
	     			}
	          );
  	    };
    };
    
    // 退货
    $('.returning').click(function(evt) {
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	operate(
	      			'是否确定此退换货单已退货?',
	      			'/app/orderBackdownchangeStatusAction.action',
	      			{alterCode:alterCode,status:3}
	      	);
    });
	  
    // 取件
    $('.pickup').click(function(evt) {
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	operate(
	      			'是否确定此退换货单已取件?',
	      			'/app/orderBackdownchangeStatusAction.action',
	      			{alterCode:alterCode,status:4}
	      	);
    });
    
    // 退款
    $('.backpay').click(function(evt) {
    	    //需要增加超时退款
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	var fromType='<s:property value="fromType"/>';
	      	if(fromType!=null&&fromType!=''&&fromType!='undefined'&&fromType==2){
	      		operate(
		      			'是否确定退款?',
		      			'/app/orderBackdownchangeYSStaAction.action',
		      			{alterCode:alterCode,status:61,fromType:fromType}
		      	);
	      		
	      	}else{
	      		var orderTypeJudge='<s:property value="orderType"/>';
	    		if(orderTypeJudge!=null&&orderTypeJudge!=''&&orderTypeJudge!='undefined'&&orderTypeJudge=='7')
	      	    	operate(
			      			'是否确定退款?',
			      			'/app/orderBackdownchangeYSStaAction.action',
			      			{alterCode:alterCode,status:61,fromType:fromType}
			      	);
	    		else
	      	    	operate(
		      			'是否确定退款?',
		      			'/app/orderBackdownchangeStatusAction.action',
		      			{alterCode:alterCode,status:61}
		      	);
	      	}
	      	
    });
    
    // 补单
    $('.additional').click(function(evt) {
      		var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	operate(
	      			'是否确定补单?',
	      			'/app/orderBackdownadditionalAction.action',
	      			{alterCode:alterCode}
	      	);
    });
    
    // 发货
    $('.ship').click(function(evt) {
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	//operate(
	      	//		'是否确定此退换货单已发货?',
	      	//		'/app/orderBackdownchangeStatusAction.action',
	      	//		{alterCode:alterCode,status:6}
	      	//);
	      	//TODO
		    $.blockUI({ message: ($('#question').empty().load("/app/orderBackdownreturnCodeAction.action",{alterCode:alterCode}))
		  	    , css: { position:'absolute',top:'20%' ,width: '280px' }
		    });
    });
    
    // 原件返回
    $('.backShop').click(function(evt) {
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	//operate(
	      	//		'是否确定此退换货单已发货?',
	      	//		'/app/orderBackdownchangeStatusAction.action',
	      	//		{alterCode:alterCode,status:6}
	      	//);
	      	//TODO
		    $.blockUI({ message: ($('#question').empty().load("/app/orderBackdownreturnCodeAction.action",{alterCode:alterCode}))
		  	    , css: { position:'absolute',top:'20%' ,width: '350px' }
		    });
    });
    
    // 完成
    $('.done').click(function(evt) {
	      	var alterCode = '<s:property value="alter.orderAlterCode"/>';
	      	operate(
	      			'是否确定此退换货单已完成?',
	      			'/app/orderBackdownchangeStatusAction.action',
	      			{alterCode:alterCode,status:7}
	      	);
    });
    
	// 申请
    $('.req').click(function(evt) {
    	var itemId = $(this).attr("name");
        $.blockUI({ message: ($('#question').empty().load("/app/orderBackdownshowAction.action?itemId="+itemId+"&currid="+Math.random()))
        	, css: { position:'absolute',left:'25%', top:'5%' ,width: '500px' }
    		, overlayCSS: { cursor: 'default' }
        });
    });
	
	//计算总额 
	$('.cg').blur(function(){
		 var orderTypeJudge='<s:property value="orderType"/>';	//获取订单类型
 		if(orderTypeJudge!=null&&orderTypeJudge!=''&&orderTypeJudge!='undefined'&&orderTypeJudge=='7'){
 			//预售订单时进入此处计算
			var totalTK = (parseFloat($("#returnMoney").val())+parseFloat($("#finalmoney").val())).toFixed(2);
			$("#returnSumShow").text(totalTK);
			$("#returnSum").val(totalTK);
 		}else {
 			var totalTK = (parseFloat($("#returnMoney").val())+parseFloat($("#returnFare").val())+parseFloat($("#fareSubsidy").val())).toFixed(2);
			$("#returnSumShow").text(totalTK);
			$("#returnSum").val(totalTK);
 		}
	});
	
	//审核 
	$('.check').click(function(){
		var fromType='<s:property value="fromType"/>';
		var orderType='<s:property value="orderType"/>';
		location.href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&alterCode=<s:property value='alter.orderAlterCode'/>&fromType="+fromType+"&orderType="+orderType;
		
	});
	
	//重新审核 
	$('.reCheck').click(function(){
		var fromType='<s:property value="fromType"/>';
		var orderType='<s:property value="orderType"/>';
		location.href="/app/orderBackdownshowBackdownCKAction.action?returnPage=3&alterCode=<s:property value='alter.orderAlterCode'/>&fromType="+fromType+"&orderType="+orderType;
	});
 });
 
function amount(th){
    var regStrs = [
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字,除了点
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
    ];
    for(i=0; i<regStrs.length; i++){
        var reg = new RegExp(regStrs[i][0]);
        th.value = th.value.replace(reg, regStrs[i][1]);
    }
};

function checkReturnMoney(th){
   var maxdate = parseFloat($("#returnMoneyc").val());
   if(th.value == null || th.value == "" || th.value < 0){
	   th.value = maxdate;
   }
   var inputDate = parseFloat(th.value);
   
	   if(maxdate < inputDate){
		   alert("商品退款金额不能大于"+maxdate+"元");
		   th.value = maxdate;
	   }
   
}

function checkReturnFare(th){
	 var maxdate = parseFloat($("#returnFarec").val());
	   var inputDate = parseFloat(th.value);
	   if(th.value == null || th.value == "" || th.value < 0){
		   th.value = 0.00
	   }
	   if(maxdate < inputDate){
		   alert("退货返运费不能大于"+maxdate+"元");
		   th.value = 0.00;
	   }
}


function checkfareSubsidy(th){
	   var inputDate = th.value;
	   if(inputDate == null || inputDate =="" || inputDate < 0){
		   th.value = 0.00
	   }
}
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'退换货管理'" scope="request"/>
<s:if test="returnPage==2">
<s:set name="son_name" value="'退换货查看'" scope="request"/>
</s:if>
<s:if test="returnPage==3">
<s:set name="son_name" value="'退换货审核'" scope="request"/>
</s:if>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<div style="margin:10px">
    <button id="return" class="backBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;
    <s:if test="returnPage==2">
	<button class="check" style="height:30px" <s:if test="alter.proposeStatus!=1">disabled="disabled"</s:if>>审核</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<%--
	<button class="returning" <s:if test="alter.proposeStatus!=2">disabled="disabled"</s:if>>确认退货</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button class="pickup" <s:if test="alter.proposeStatus!=3">disabled="disabled"</s:if>>确认取件</button>&nbsp;&nbsp;&nbsp;&nbsp;
	--%>
	<button class="reCheck"  style="height:30px" <s:if test="alter.proposeStatus!=54">disabled="disabled"</s:if>>重新审核</button>&nbsp;&nbsp;&nbsp;&nbsp;
    <button class="backpay" style="height:30px" <s:if test="alter.proposeStatus!=51">disabled="disabled"</s:if>>确认退款</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button class="backShop" style="height:30px" <s:if test="alter.proposeStatus!=53">disabled="disabled"</s:if>>返回原件</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<%--
	<button class="ship" <s:if test="alter.proposeStatus!=52">disabled="disabled"</s:if>>确认发货</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button class="done" <s:if test="alter.proposeStatus!=61&&alter.proposeStatus!=62&&alter.proposeStatus!=63">disabled="disabled"</s:if>>确认完成</button>&nbsp;&nbsp;&nbsp;&nbsp;
	 --%>
	<s:if test="isAdditional">
	<button class="additional">补单</button>&nbsp;&nbsp;&nbsp;&nbsp;
	</s:if>
	</s:if>
</div>
<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
<ul id="tabs">
	<s:if test="returnPage==2">
	<li class="visit"><b>基本信息</b></li>
	<li><b>操作流水</b></li>
	<li><b>退款流水</b></li>
	</s:if>
</ul>		
<div id="content">
<div class="ct">
<form id="backForm">
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>申请明细：</h3></div>
      <div class="wc">
          <ul class="form-info ">
          	<li><div class="upload-photos-form "><span>服务类型：<font><s:property value="alter.alterTypeStr"/></font></span><span>申请凭据：<font><s:property value="alter.evidence==null?'无':'有发票'"/></font></span><span>商品数量：<font><s:property value="alter.alterNum"/></font></span></div></li>
              <li><div class="upload-photos-form"><span>商品名称：<font><a href='<s:property value="cmsPagePath"/><s:property value="item.productSkuId"/>.shtml' class="fn-blue" target="_blank">[<s:property value="item.commoditySku"/>]&nbsp;&nbsp;<s:property value="item.commodityName"/></a></font></span><span>商品编号：<font><s:property value="item.commoditySku"/></font></span></div></li>
              <li><label>问题描述：</label><div class="sh-name item"><textarea name="" cols="" rows="" class="Complaints-text" readonly><s:property value="alter.alterComment"/></textarea></div></li>
              <li>
              <li><label>审核说明：</label><div class="sh-name item"><textarea name="comment" id="comment" cols="" rows="" class="Complaints-text" <s:if test="returnPage!=3||alter.proposeStatus==54">readonly="readonly"</s:if>><s:property value="alter.auditComment"/></textarea></div></li>
              <li>
              <li>&nbsp;</li>
              <label>上传图片：</label>
              <div class="sh-name">
                  <ul class="upload-photos">	
                  	 <s:iterator value="photoList" id="potoUrl">
                  	 	<li><img src="<s:property value='showPath'/><s:property value='#potoUrl.url'/>"></li>
                  	 </s:iterator>
                  </ul>
              </div>
          </ul>
      </div>
  </div>
  <br/>
  <br/>
   <!-- 以前的普通退换货 -->
  <s:if test="fromType==''||fromType==null||fromType==1">
    <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>退换赔付信息：</h3><span style="color:red;margin-left:40px"><s:if test="isSuit">由于该订单包含活动商品，请手动计算！</s:if></span></div>
      <div class="wc">
          <ul class="form-info ">
          	  <li><span>
          	  赔付信息：
          	  
          	  <!-- 查看按钮的展示页面 -->
          	 <s:if test="returnPage!=3">	
	          	  
	          	  <!-- 预售订单查看 -->
		          <s:if test="orderType==7">
						 <br/>商品定金：<font>￥<s:if test="alter.alterType==2&&alter.proposeStatus==1">0.00</s:if>
						 <s:else><s:property  value='alter.deposit==null?0.00:alter.deposit'/></s:else></font>&nbsp;元，
						 
						 <br/>商品尾款：<font>￥<s:if test="alter.alterType==2&&alter.proposeStatus==1">0.00</s:if>
						 <s:else><s:property value='alter.finalmoney==null?0.00:alter.finalmoney'/></s:else></font>&nbsp;元，
						 
						 <br/>共计<font>￥<span id="returnSumShow"><s:if test="alter.alterType==2&&alter.proposeStatus==1">0.00</s:if>
						 <s:else><s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/></s:else></span></font>&nbsp;元退款到余额及银行账户.
				</s:if>
				
	          	 <!-- 普通订单查看 --> 
	          	  <s:else>
		          	 <br/>商品退款金额<font>￥
		          	 <s:if test="alter.alterType==2&&alter.proposeStatus==1">
		          	 0.00
		          	 </s:if>
		          	 <s:else>
		          	 <s:property value='alter.ruturnMoney==null?0.00:alter.ruturnMoney'/>
		          	 </s:else>
		          	 </font>元，
		          	 <s:if test="alter.proposeStatus>1&&alter.proposeStatus!=53">
		          	 <br/>退货返运费&nbsp;&nbsp;<font>￥<s:property value='alter.returnFare==null?0.00:alter.returnFare'/></font>元，
		          	 </s:if>
		          	 <s:else>
		          	 <br/>退货返运费&nbsp;&nbsp;<font>￥<s:property value='0.00'/></font>元，
		          	 </s:else>
		          	 <br/>补&nbsp;偿&nbsp;运&nbsp;费&nbsp;&nbsp;&nbsp;&nbsp;<font>￥<s:property value='alter.fareSubsidy==null?0.00:alter.fareSubsidy'/></font>元，
		          	  <br/>共计<font>￥<span id="returnSumShow">
		          	   <s:if test="alter.alterType==2&&alter.proposeStatus==1">
		          	 0.00
		          	 </s:if>
		          	 <s:else>
		          	  <s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/>
		          	  </s:else>
		          	  </span></font>元退款到余额及银行账户.
	          	  </s:else> 
          	 </s:if>
          	 
          	 <!-- 审核按钮的展示页面 -->
          	 <s:else>	
          	 
          	 	<!-- 预售订单审核 -->
	          	 <s:if test="orderType==7">		
	          	 	<s:if test="alter.alterType==2||alter.alterType==1||alter.alterType==3">
						 <br/>商品定金：<font>￥<input class="cg" disabled="disabled" type="text" name="" id="returnMoney" <s:if test="alter.alterType==2&&alter.proposeStatus==1">value="0.00" </s:if>
						 <s:else>value="<s:property value='alter.deposit==null?0.00:alter.deposit'/>"
						 </s:else>onkeyup="amount(this)" onafterpaste="amount(this)" onBlur = "checkReturnMoney(this)" /></font>元，
						  
						 <br/>商品尾款：<font>￥<input  class="cg" disabled="disabled" type="text" name="" id="finalmoney" <s:if test="alter.alterType==2&&alter.proposeStatus==1">value="0.00" </s:if>
						 <s:else>value="<s:property value='alter.finalmoney==null?0.00:alter.finalmoney'/>"
						 </s:else> onkeyup="amount(this)" onafterpaste="amount(this)" onBlur = "checkReturnMoney(this)"/></font>元，
						 
						  <br/>共计<font>￥<span id="returnSumShow"><s:if test="alter.alterType==2&&alter.proposeStatus==1">0.00</s:if>
						  <s:else><s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/></s:else></span></font>元退款到余额及银行账户.
	          	 	</s:if>
          	 	</s:if> 
          	 	
				<!-- 普通订单审核 -->
	          	  <s:else>	
		          	 <br/>商品退款金额<font>￥<input class="cg"  type="text" name="" id="returnMoney" <s:if test="alter.alterType==2&&alter.proposeStatus==1">value="0.00"</s:if>
		          	 <s:else>value="<s:property value='alter.ruturnMoney==null?0.00:alter.ruturnMoney'/>"
		          	 </s:else> onkeyup="amount(this)" onafterpaste="amount(this)" onBlur = "checkReturnMoney(this)"/></font>元，
		          	 
		          	 <br/>退货返运费&nbsp;
		          	 <font>￥<input class="cg"  type="text" name="" id="returnFare" value="0.00" onKeyUp="amount(this)" onafterpaste="amount(this)" onBlur = "checkReturnFare(this)"/></font>元，
		          	
		          	 <br/>补&nbsp;偿&nbsp;运&nbsp;费&nbsp;&nbsp;&nbsp;&nbsp;
		          	 <font>￥<input class="cg" type="text" name="" id="fareSubsidy" value="<s:property value='alter.fareSubsidy==null?0.00:alter.fareSubsidy'/>" 
		          	 onkeyup="amount(this)" onafterpaste="amount(this)" onBlur = "checkfareSubsidy(this)"/></font>元，
		          	 
		          	 <br/>共计<font>￥<span id="returnSumShow"><s:if test="alter.alterType==2&&alter.proposeStatus==1">0.00</s:if><s:else> 
		          	 <s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/></s:else></span></font>元退款到余额及银行账户.
	          	 </s:else> 
          	 </s:else>
          	 
          	 <%--
          	 <s:if test="null!=alter.preferentialAmount&&0!=alter.preferentialAmount">
          	 <br/>返还面值为&nbsp;&nbsp;&nbsp;&nbsp;<font>￥<input <s:if test="returnPage!=3">disabled="disabled"</s:if> type="text" name="" id="preferentialAmount" value="<s:property value='alter.preferentialAmount'/>"/></font>元优惠券一张，
          	 </s:if>
          	  --%>
          	  
          	 <s:if test="orderType==7">
          	 	<!-- 预售订单时进入  -->		
	          	 <input type="hidden" id = "returnSum" <s:if test="alter.alterType==2">value="0.00"</s:if><s:else> value="<s:property value='alter.ruturnSum==null?0.00:alter.ruturnSum'/>" </s:else> />
	          	 <input type="hidden" id = "returnMoneyc" value="<s:property value='alter.deposit==null?0.00:alter.deposit'/>"/>
	          	 <input type="hidden" id = "returnFarec" value="0.00"/>
	         </s:if>
	         <s:else> 	 
	          	 <input type="hidden" id = "returnSum" <s:if test="alter.alterType==2">value="0.00"</s:if><s:else> value="<s:property value='alter.ruturnSum==null?0.00:alter.ruturnSum'/>" </s:else> />
	          	 <input type="hidden" id = "returnMoneyc" value="<s:property value='alter.ruturnMoney==null?0.00:alter.ruturnMoney'/>"/>
	          	 <input type="hidden" id = "returnFarec" value="<s:property value='alter.returnFare==null?0.00:alter.returnFare'/>"/>
	         </s:else>
	         
          	  </span></li>
          	   <s:if test="orderType==7&&alter.alterType==2">
              <s:if test="alter.proposeStatus>=3">
              <li><span>退回快递信息：<font><s:property value="alter.customerLogisticsName"/></font></span><span>&nbsp;&nbsp;运单号：<font><s:property value="alter.customerLogisticsNo"/></font> </span>
              </li>
              </s:if>
              <s:else>
              <li><span>退回快递信息：<font>尚未退货</font></span></li>
              </s:else>
              <s:if test="alter.proposeStatus>=5">
              <li><span>换货快递信息：<font><s:property value="alter.LogisticsName"/></font></span><span>&nbsp;&nbsp;运单号：<font><s:property value="alter.logisticsOrderNo"/></font></span>   
			  </li>
              </s:if>
              <s:else>
              <li><span>换货快递信息：<font>尚未发货</font></span></li>
              </s:else>
              <li><span>商家给买家换货快递信息：<font><s:property value="alter.province"/><s:property value="alter.city"/><s:property value="alter.area"/><s:property value="alter.address"/></font></span></li>
              <li><span>买家收货人：<font><s:property value="alter.name"/></font></span></li>
              <li><span>买家电话：<font><s:property value="alter.phone"/></font></span></li>
              </s:if>
              <%--
              <s:if test="alter.addressId==null">
              </s:if>
              <s:else>
              <li><span>商家给买家换货快递信息：<font><s:property value="faddress.province"/><s:property value="faddress.city"/><s:property value="faddress.area"/><s:property value="faddress.detailedAddress"/></font></span></li>
              <li><span>买家收货人：<font><s:property value="faddress.name"/></font></span></li>
              <li><span>买家电话：<font><s:property value="faddress.cellphone"/></font></span></li>              
              </s:else>
              --%>
              <s:if test="returnPage==3">
              <li>&nbsp;</li>
              <li>
              <button class="submit" name="pass">通过</button>&nbsp;&nbsp;&nbsp;&nbsp;
              <s:if test="alter.proposeStatus!=54">
			  <button class="submit" name="veto">驳回</button>&nbsp;&nbsp;&nbsp;&nbsp;
			  </s:if>
			  </li> 
			  </s:if>
          </ul>
      </div>
  </div>
  </s:if>
  <!-- 预售--超时未发货 -->
  <s:if test="fromType==2">
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3>超时未发货赔付信息：</h3></div>
      <div class="wc">
          <ul class="form-info ">
          	  <li><span>
          	  赔付信息：
          	 <s:if test="returnPage!=3">
          	 <br/>退商品定金<font>￥<s:property value="alter.deposit==null?0.00:alter.deposit"/></font>元，

          	 <br/>退商品尾款&nbsp;&nbsp;<font>￥<s:property value='alter.finalmoney==null||alter.finalmoney==""?0.00:alter.finalmoney'/></font>元，

          	 <br/>定&nbsp;金&nbsp;补&nbsp;偿&nbsp;&nbsp;&nbsp;&nbsp;<font>￥<s:property value='alter.compensate==null?0.00:alter.compensate'/></font>元，
          	  <br/>共计<font>￥<span id="returnSumShow"><s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/></span></font>元退款到余额及银行账户.
          	 </s:if>
          	 <s:else>
          	 <br/>退商品定金<font>￥<input   type="text" name="" id="deposit" disabled="disabled" readOnly="readOnly"  value="<s:property value='alter.deposit==null?0.00:alter.deposit'/>" /></font>元，
          	 <br/>退商品尾款&nbsp;<font>￥<input   type="text" name="" id="finalmoney" disabled="disabled" value="<s:property value='alter.finalmoney==null?0.00:alter.finalmoney'/>" /></font>元，
          	 <br/>定&nbsp;金&nbsp;补&nbsp;偿&nbsp;&nbsp;&nbsp;&nbsp;<font>￥<input  type="text" name="" disabled="disabled"  id="compensate" value="<s:property value='alter.compensate==null?0.00:alter.compensate'/>"  /></font>元，
          	 <br/>共计<font>￥<span id="returnSumShow"><s:property value="alter.ruturnSum==null?0.00:alter.ruturnSum"/></span></font>元退款到余额及银行账户.
          	 </s:else>
          	 <!-- 下面三个是否需要改动? -->
          	 <input type="hidden" id = "returnSum" <s:if test="alter.alterType==2">value="0.00"</s:if><s:else> value="<s:property value='alter.ruturnSum==null?0.00:alter.ruturnSum'/>" </s:else> />
          	 <input type="hidden" id = "returnMoneyc" value="<s:property value='alter.ruturnMoney==null?0.00:alter.ruturnMoney'/>"/>
          	 <input type="hidden" id = "returnFarec" value="<s:property value='alter.returnFare==null?0.00:alter.returnFare'/>"/>
          	  </span></li>
              <s:if test="returnPage==3">
              <li>&nbsp;</li>
              <li>
              <button class="submit" id ="ys_pass" name="ys_pass"  <s:if test="order.orderStatus==5">disabled="disabled"</s:if> >通过</button>&nbsp;&nbsp;&nbsp;&nbsp;
              <s:if test="alter.proposeStatus!=54"><!-- 是否需要改动? -->
			  <button class="submit" id ="ys_veto" name="ys_veto">驳回</button>&nbsp;&nbsp;&nbsp;&nbsp;
			  </s:if>
			  </li> 
			  </s:if>
          </ul>
      </div>
  </div>
  </s:if>

		<%--
		<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
				<td>
					商品SKU：<input type="text" value='<s:property value="item.commoditySku"/>' readonly="readonly" />
					商品名称：<input type="text" value='<s:property value="item.commodityName"/>' readonly="readonly" />
					商品价格：<input type="text" value='<s:property value="item.commodityUnitPrice"/>' readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					商品应退金额：<input type="text" class="cg" id="returnMoney" name="returnMoney" value='<s:property value="null==alter.ruturnMoney?0.00:alter.ruturnMoney"/>' <s:if test="returnPage!=3">readonly="readonly"</s:if> />
					补贴运费：<input type="text" class="cg" id="fareSubsidy" name="fareSubsidy" value='<s:property value="null==alter.fareSubsidy?0.00:alter.fareSubsidy"/>' <s:if test="returnPage!=3">readonly="readonly"</s:if> />
					应退总金额：<input type="text" id="returnSum" value='<s:property value="alter.ruturnSum"/>' readonly="readonly" />
					<s:if test="returnPage==3">
					建议补发优惠券面额：<input type="text" value='<s:property value="alter.discountAmount"/>' readonly="readonly" />
					</s:if>
				</td>
			</tr>
			<tr>
				<td >服务类型：<s:property value="alter.alterTypeStr"/>
				商品退回方式：<s:property value="alter.backTypeStr"/>
				</td>
			</tr>
			<tr>
				<td>商品数量：<s:property value="alter.alterNum"/>
				申请凭据：<input disabled="disabled" type="checkbox"  <s:if test="alter.evidence==1">checked="checked"</s:if> />有发票</td>
			</tr>
			<tr><td>问题描述：
			<textarea  readonly="readonly"  rows="5" cols="25"><s:property value="alter.alterComment"/></textarea>
			</td></tr>
			<tr><td>上传图片：
			<s:iterator value="photoList" var="photo">
			<img src="<s:property value="showPath"/><s:property value="photo.url"/>"/>
			</s:iterator>
			</td></tr>
			<s:if test="alter.alterType==2">
			<tr><td>收货人：<s:property value="alter.name"/></td></tr>
			<tr><td>收货电话：<s:property value="alter.phone"/></td></tr>
			<tr><td>地址：<s:property value="alter.address"/></td></tr>
			</s:if>
		</table>
		--%>
</form>
</div>
<s:if test="returnPage==2">
<div class="ct" id="ct_cate">
<br/>
	<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
		<tr>
		   	<th>流水号</th>
		   	<th>时间</th>
		   	<th>操作人</th>
		   	<th>操作类型</th>
		   	<th>退款总金额</th>
		    <s:if test="fromType==''||fromType==null||fromType==1">
		   	<th>退换货单状态</th>
		   	</s:if>
		   	<s:if test="fromType==2">
		   	<th>超时未发货赔付货单状态</th>
		   	</s:if>
		   	<th>操作信息</th>
		</tr>
		<s:iterator id="orderOperate" value="operates">
		<tr>
	   	 	 <td><s:property value="#orderOperate.statementId"/></td>
	   	 	 <td><s:date name="#orderOperate.nowOperateDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   	 	 <td><s:property value="#orderOperate.nowOperator"/></td>
	   	 	 <td><s:property value="#orderOperate.nowOperateTypeStr"/></td>
	   	 	 <td><s:property value="#orderOperate.nowReturnSum"/></td>
	   	 	 <td><s:property value="#orderOperate.nowAlterStatusStr"/></td>
	   	 	 <td><s:property value="#orderOperate.operateInfo"/></td>
		</tr>
		</s:iterator>
	</table>				
	</div>
	<div class="ct" id="ct_sku">
	<br/>
	<table class="list_table" width="100%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
		<tr>
		   	<th>流水号</th>
		   	<th>退款方式</th>
		   	<th>退款平台</th>
		   	<th>退款状态</th>
		   	<th>客户账号</th>
		   	<th>退款金额</th>
		   	<th>生成时间</th>
		   	<th>退款完成时间</th>
		   	<th>第三方退款流水号</th>
		   	<th>付/退款</th>
		   	<th>优惠券编号</th>
		</tr>
		<s:iterator id="orderPay" value="pays">
		<tr>
	   	 	 <td><s:property value="#orderPay.payStatementNo"/></td>
	   	 	 <td>
	   	 	 <s:if test="#orderPay.paymentWayStr!=null">
	   	 	 <s:property value="#orderPay.paymentWayStr"/>
	   	 	 </s:if>
	   	 	 <s:if test="#orderPay.paymentWayStr==null">
	   	 		第三方支付平台支付
	   	 	 </s:if>
	   	 	 </td>
			 <td><s:property value="#orderPay.platFormName"/></td>
	   	 	 <td><s:property value="#orderPay.stateStr"/></td>
	   	 	 <td><s:property value="#orderPay.account"/></td>
	   	 	 <td><s:property value="#orderPay.orderMoney"/></td>
	   	 	 <td><s:date name="#orderPay.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   	 	 <td><s:date name="#orderPay.endDate" format="yyyy-MM-dd HH:mm:ss" /></td>
	   	 	 <td><s:property value="#orderPay.outsidePayStatementNo"/></td>
	   	 	 <td><s:property value="#orderPay.flagStr"/></td>
	   	 	 <!-- 
	   	 	 <td><s:property value="#orderPay.preferentialNo"/></td>
	   	 	  -->
	   	 	 <td>
	   	 	 	<s:if test="null!=#orderPay.preferentialNo">
	   	 	 		<s:text name="format.num.zero">
	   	 	 			<s:param value="#orderPay.preferentialNo"/>
	   	 			</s:text>
	   	 		</s:if>
	   	 	 </td>
		</tr>
		</s:iterator>
	</table>				
	</div>
	</s:if>
	</div>
</div>
<!-- 
<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
 -->
<div id="question" style="display:none"></div>
	
</body>
</html>
