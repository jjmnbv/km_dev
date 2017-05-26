<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>新增物流订阅信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/validate_message_CN.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript">
			var listAction = "expressSub_pageList.action";
			var duplicateTip = "系统已存在相同的物流公司+物流单号";
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
						dataType:'text',
						data:{
							logisticsCode:tempCode,
							logisticsNo:tempNo,
							t_i_m_e:Math.random()
						},
						timeout:'6000',
						success:function(data){
							console.log(data);
							if(data=='true'){
								isDuplicate=true;
								 $('#logisticsNo').next('label').removeClass("checked").addClass("error");
								 $('#logisticsNo').next('label').html(duplicateTip);
							}else{
								$('#logisticsNo').next('label').removeClass("checked").addClass("checked");		
								$('#logisticsCode').next('label').html('');
							}
						},
						error:function(){
							alert('err!');
						}
					});
				  }
    			return this.optional(element) || !isDuplicate;

			}, duplicateTip);  
			
			$(function(){
				//默认选中顺丰
				$('#logisticsCode').val('shunfeng');
				$('#logisticsName').val('顺丰');
				
				//表单校验
			    $("#expressSubscriptionForm").validate({
		             rules: {
							"expressSubscription.orderCode":{required:true},
							"expressSubscription.logisticsNo":{required:true,duplicate:true}
			        	},
			         success: function (label){
			            label.removeClass("checked").addClass("checked");
			         }
		        });
			}); 
			
			//返回列表页面
			function gotoBack(){
				location.href=listAction;
			}

			//修改物流公司	
			function changeCompany(){
				$('#logisticsName').val($('#logisticsCode option:selected').text());
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
					<td width="20%" class="eidt_rowTitle">订单编码：</td>
					<td width="80%">
						<input type="text" name="expressSubscription.orderCode"
									value="<s:property value='expressSubscription.orderCode'/>" maxlength="18">
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">渠道：</td>
					<td width="80%">
						<select name="expressSubscription.channel" Style="width:145px">
							<option value="B2B">B2B</option>
							<option value="B2C">B2C</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">物流公司</td>
					<td width="80%">
					<input type="hidden" id="logisticsName" name="expressSubscription.logisticsName" 
					value="<s:property value='expressSubscription.logisticsName'/>" />
					<s:select list="#request.companyList"  
							  listValue="logisticsName " 
							  listKey="logisticsCode "
							  cssStyle="width:145px"
							  onchange="javascript:changeCompany()"  
							  name="expressSubscription.logisticsCode" 
							  id="logisticsCode" 
							  value="<s:property value='expressSubscription.logisticsCode'/>">
					</s:select>  
					</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">物流单号：</td>
					<td width="80%">
					<input type="text" id="logisticsNo" name="expressSubscription.logisticsNo"
								value="<s:property value='expressSubscription.logisticsNo'/>" maxlength="50">
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