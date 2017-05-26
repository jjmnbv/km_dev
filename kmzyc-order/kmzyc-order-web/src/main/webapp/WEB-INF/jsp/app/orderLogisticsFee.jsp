<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改订单运费信息</title>
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

	jQuery.validator.addMethod("limitInteger",function(value, element){
		var pattern = /^[0-9].*$/;
		return this.optional(element) || (pattern.test(value)); 
	},"请填写正数！");
	
    $("#infoForm").validate({     
	       rules: {
				fare: {required: true,number:true,maxlength:7,number:true,limitInteger:true}
    	   },
    	   messages:{
    		   fare:{maxlength: "字符长度不能大于7个字符"}
        	   },
	   	   submitHandler: function (form) {
	            $.post('/app/orderPayupdateFareAction.action',
	               		$("#infoForm").serialize(),
	  	           		function(result){
	  			            alert(result);
	  			            if(result.indexOf("失败")>0){
	  			            	history.go(0);
	  			            }else{
	  			            	location.href='/app/orderItemdetailAction.action?orderCode=<s:property value="order.orderCode"/>';
	  			            }
	  	     			}
	            );		
          }
	});
	
    $("#saves").click(function(){
		var orderCode = '<s:property value="order.orderCode"/>';
		var data = $("#infoForm").serialize();
		//alert(data);
        $.post(
            	'/app/orderPayupdateFareAction.action',
            	data,
 	           		function(result){
 			            alert(result);
  			            if(result.indexOf("失败")>0){
  			            	history.go(0);
  			            }else{
  			            	location.href="/app/orderItemdetailAction.action?orderCode="+orderCode;
  			            }
 	     			}
          );
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
  			            	location.href="/app/orderBackdownlistByMapAction.action";
			            }
	     			}
	          );
  	    };
    };
    
 });
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单管理'" scope="request"/>
<s:set name="son_name" value="'修改订单运费'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<div style="margin:10px">
    <button id="return" class="backBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 
    <button id="save" class="saveBtn"></button>&nbsp;&nbsp;&nbsp;&nbsp;
     -->
</div>

<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">	
<div id="content">
<div class="ct">
<form id="infoForm">
  <div class="m-w w-noborder fn-clear fn-t10">
    <h3>订单号：<s:property value="order.orderCode"/></h3>
  	<div class="wh"><h3>更改运费信息：</h3></div>
      <div class="wc">
          <ul class="form-info">
          	      <input id="orderCode" type="hidden" name="orderCode" value='<s:property value="order.orderCode"/>'/>
              <li>运费：<input id="fare" type="text" name="fare" value='<s:property value="order.fare"/>'/></li>
          </ul>
      </div>
  </div>
 
  <div class="m-w w-noborder fn-clear fn-t10">
  	<div class="wh"><h3></h3></div>
      <div class="wc">
          <ul class="form-info ">
          	    <li><input type="submit" id="submit0" class="saveBtn" value=""/></li>
          </ul>
      </div>
  </div>
</form>
</div>
	</div>
</div>
<!-- 
<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
 -->
<div id="question" style="display:none"></div>
	
</body>
</html>