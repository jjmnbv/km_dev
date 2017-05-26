<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>新增物流订阅信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate1.11.js"></script>
		<script type="text/javascript" src="/etc/js/validate_message_CN.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script type="text/javascript">
			var listAction = "expressSub_pageList.action";
			var duplicateTip = "系统已存在相同的物流公司+物流单号";
			var availableExpCompanys =eval('<s:property value="#request['availableExpCompanys']" escape="false"/>');	 
			//增加物流公司，物流单号重复检查规则
			jQuery.validator.addMethod("duplicate", function(value, element) {
				var isDuplicate = false;
				var tempCode = $('#logisticsCode').val();
				var tempNo =$('#logisticsNo').val();
				if(tempCode!=''&&tempNo!=''){
					$.ajax({
						async:false,
						url:'expressSub_checkDuplicate.action',
						type:'POST',
						dataType:'json',
						data:{
							logisticsCode:tempCode,
							logisticsNo:tempNo,
							t_i_m_e:Math.random()
						},
						timeout:'6000',
						success:function(data){
							console.log(data);
							if(data.checkResult=='true'){
								isDuplicate=true;
							}
						},
						error:function(){
							alert('err!');
						}
					});
				  }
    			return this.optional(element) || !isDuplicate;

			}, duplicateTip);

			jQuery.validator.addMethod("logisticsComExist", function(value, element) {
				var existFlag=false;
				var logisticsCom = $('#logisticsCompany').val();
				if(logisticsCom){
					$.each(availableExpCompanys,function (i) {
						  if(availableExpCompanys[i].value==logisticsCom){
							  existFlag=true;
							  return false;
						  }
					});					
				}
    			return this.optional(element) || existFlag;

			}, "输入的快递公司不存在");

			jQuery.validator.addMethod("logisticsNoCheck", function(value, element) {
				var logisticsNoVal =  $('#logisticsNo').val();
				var ogisticsNoReg=/^[0-9a-zA-Z]*$/;
    			return this.optional(element) || ogisticsNoReg.test(logisticsNoVal);
			}, "只能输入英文和整数数字");
			
			$(function(){
				//表单校验
			    $("#expressSubscriptionForm").validate({
			    	// debug:true,
		             rules: {
							"expressSubscription.orderCode":{required:true,digits:true},
							"logisticsCompany":{required:true,logisticsComExist:true},
							"expressSubscription.logisticsNo":{required:true,duplicate:true,logisticsNoCheck:true}
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
				        	openLoadingDiv("物流订阅信息数据保存中....");
				        	form.submit();
					    }
		        });

			    $( "#logisticsCompany" ).autocomplete({
			    	minChars:0,
			    	maxHeight: 220,
			    	autoSelectFirst:true,
			    	lookup:availableExpCompanys,
			    	onSelect: function (suggestion) {
			    		$('#logisticsCode').val(suggestion.data);
			    		$('#logisticsName').val(suggestion.value.substring(0,suggestion.value.length-suggestion.data.length-2));
			    		$( "#logisticsCompany" ).next('label').removeClass("checked").addClass("checked");
			    		$( "#logisticsCompany" ).next('label').html('');
			        }
			     });

			    //默认选中顺丰
				$('#logisticsCode').val('shunfeng');
				$('#logisticsName').val('顺丰');
				$('#logisticsCompany').val('顺丰(shunfeng)');
			}); 
			
			//返回列表页面
			function gotoBack(){
				location.href=listAction;
			}


			function openShadeDiv(){
				var wnd = jQuery(window);
				var doc = jQuery(document);
				var wHeight=0;
				var wWidth=0;
				//当高度少于一屏
			    if(wnd.height()>doc.height()){
			     	wHeight = wnd.height();
			     	wWidth = wnd.width();
			    }else{
			    	//当高度大于一屏
			     	wHeight = doc.height();
			     	wWidth = doc.width();
			    }
				//创建遮罩背景
				jQuery("body").append("<div id=shadeDiv></div>");
				jQuery("body").find("#shadeDiv")
				    .width(wWidth)
				    .height(wHeight)
				    .css(
				    	{position:"absolute",
				    	top:"0px",left:"0px",
				    	background:"#ccc",
				    	filter:"Alpha(opacity=50);",
				    	opacity:"0.3",zIndex:"10000"
				    	});
			}

			
			function openLoadingDiv(promptMsg){
				var promptMsg = promptMsg ? promptMsg : '数据处理中';
				var strDiv="<div id='loadImgDiv' style='background-color: #ffffe1;z-Index:10001;border:1px solid;border-color: silver;width:240px;position: absolute;left:"+(document.body.clientWidth-420)/2+";top:"+(document.body.clientHeight-240)/2+"'>"+
							"<table align='center'>"+
								"<tr>"+
									"<td>"+
										"<img style='height:22px;width:22px' src='/etc/images/red-waiting.gif'/>"+
									"</td>"+
									"<td style='font-size: 15px;'>"+
										 promptMsg+
									"</td>"+
								"</tr>"+
							"</table>"+
						"</div>";
				//jQuery("select:enabled").attr("disabled","disabled").attr("removeDisabled","true");
				openShadeDiv();
				jQuery("body").append(strDiv);
			}

			


			
			
 	 	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'业务操作'" scope="request"/>
		<s:set name="name" value="'订单管理'" scope="request"/>
		<s:set name="son_name" value="'新增物流订阅'" scope="request"/>
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<div style="height:10px;"></div>
		<s:form id="expressSubscriptionForm" name="expressSubscriptionForm" action="expressSub_saveInsert.action" method="post" onsubmit="return checkFrom()">
			<input type="hidden" name="expressSubscription.subId" value="<s:property value='expressSubscription.subId'/>"/>
			<s:token />
			<!-- 数据编辑区域 -->
			<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class="edit_title">
						物流订阅信息
					</th>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle"><span style="color:red">*</span>订单编码：</td>
					<td width="80%">
						<input type="text" name="expressSubscription.orderCode"
									value="<s:property value='expressSubscription.orderCode'/>" maxlength="18" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle"><span style="color:red">*</span>物流公司</td>
					<td width="80%">
					<input type="hidden"  id="logisticsName" name="expressSubscription.logisticsName" 
					value="<s:property value='expressSubscription.logisticsName'/>" />
					<input type="hidden"   id = "logisticsCode"  name="expressSubscription.logisticsCode"  
						 value="<s:property value='expressSubscription.logisticsCode'/>" maxlength="50" />  
					<input id="logisticsCompany" name="logisticsCompany" maxlength="50"/>  
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle"><span style="color:red">*</span>物流单号：</td>
					<td width="80%">
					<input type="text" id="logisticsNo" name="expressSubscription.logisticsNo"
								value="<s:property value='expressSubscription.logisticsNo'/>" maxlength="50" onkeyup="this.value=this.value.replace(/[, ]/g,'')">
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">起始城市：</td>
					<td width="80%">
					<input type="text" name="expressSubscription.fromCity"
								value="<s:property value='expressSubscription.fromCity'/>" maxlength="50">
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">目的城市：</td>
					<td width="80%">
					<input type="text" name="expressSubscription.toCity"
								value="<s:property value='expressSubscription.toCity'/>" maxlength="50">
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