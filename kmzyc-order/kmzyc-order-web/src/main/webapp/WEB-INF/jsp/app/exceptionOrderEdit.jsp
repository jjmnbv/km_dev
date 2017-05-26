<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>新增异常订单</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate1.11.js"></script>
		<script type="text/javascript" src="/etc/js/validate_message_CN.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript">
			var listAction = "/app/qryExceptionOrderPageList.action";
			var orderCodeCheckTip="输入的订单号不存在或当前订单号是一个父订单的订单编码";
			var duplicateCheckTip = "输入的订单号已经是一个异常订单，请重新输入";
			var duplicateFlag =false;
			//增加物流公司，物流单号重复检查规则
			jQuery.validator.addMethod("orderCodeIsRight", function(value, element) {
				var checkFlag = false;
				var orderCode = $('#orderCode').val();
				if(orderCode!=''){
					$.ajax({
						async:false,
						url:'checkExceptionOrderCode.action',
						type:'POST',
						dataType:'json',
						data:{
							"orderCode":orderCode,
	    					t_i_m_e:Math.random()
						},
						timeout:'6000',
						success:function(data){
							console.log(data);
							if(data.checkResult!='FLASE'){
								checkFlag=true;
								if(data.checkResult=='DUPLICATE'){
									duplicateFlag = true;
								}
							}
						},
						error:function(){
							alert('校验新增异常的订单编码出错，请稍后重试或联系系统管理员');
						}
					});
				  }
    			return this.optional(element) || checkFlag;

			}, orderCodeCheckTip);

			jQuery.validator.addMethod("orderCodeDuplicateCheck", function(value, element) {
				console.log("duplicateFlag:"+duplicateFlag);
    			return this.optional(element) || !duplicateFlag;
			}, duplicateCheckTip);
			
			$(function(){
				//表单校验
			    $("#orderExceptionForm").validate({
			    	 //debug:true,
		             rules: {
							"orderCode":{required:true,digits:true,orderCodeIsRight:true,orderCodeDuplicateCheck:true},
							"orderOperationRemark":{required:true,maxlength:100}
			        	},
			        	success: function (label){
				            label.html("").removeClass("checked").addClass("checked");
				         },
			        	highlight: function(element, errorClass, validClass) {
			        		$(element).next('label').removeClass(errorClass).addClass(validClass);		
							$(element).next('label').html('');
			        	},
			        	unhighlight: function(element, errorClass,validClass) {
			        		$(element).next('label').removeClass(validClass).addClass(errorClass);
				        },
				        submitHandler:function(form){
					        form.submit();
					    }
		        });
			}); 
			
			//返回列表页面
			function gotoBack(){
				location.href=listAction+"?map['status']=-3";
			}
 	 	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'新增异常订单'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<s:form id="orderExceptionForm" name="orderExceptionForm" action="addExceptionOrder.action" method="post" onsubmit="return checkFrom()">
			<s:token />
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class="edit_title">
						异常订单信息
					</th>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle"><span style="color:red">*</span>订单编码：</td>
					<td width="80%">
						<input type="text" id="orderCode" name="orderCode"  maxlength="18" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle"><span style="color:red">*</span>后台备注：</td>
					<td width="80%">
					<textarea  id="orderOperationRemark" name="orderOperationRemark" style="width:55%;height:100px"></textarea>
					</td>
				</tr>
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="saveBtn" type="submit" value=" ">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoBack()" type="button"  value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/public/message.jsp"></jsp:include>
	</BODY>
</HTML>