<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发放编辑</title>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">

<link href="/etc/css/validate.css"  rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
 <Script src="/etc/js/grantCoupon.js"></Script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
<script language="javascript" type="text/javascript"  src="/etc/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
 
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>

<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>

</head>
<body >
 
	 <s:set name="parent_name" value="'优惠劵管理'" scope="request" />
	
	<s:set name="name" value="'优惠券发放设置'" scope="request" />
	<s:set name="name" value="'编辑'" scope="request" />
	
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
   
	<s:form action="/coupon/updateGrantCoupon.action" method="POST" namespace="/coupon" id="updateCouponForm">
	<s:token></s:token>
		
	 
	<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="20%"  align="right"><font color="red">*</font>选择优惠券规则：</td>
				<td width="80%" align="left"><input type="button"  onclick="selectCoupon();" value="选择"></input>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="couponDetail()" id="couponNameShow"><s:property value="coupongrantSeting.couponName"/></a> 
					<input type="hidden" name="coupongrantSeting.couponId"  id="couponIdHid" value="<s:property value='coupongrantSeting.couponId'/>"/>
					<input type="hidden" name="coupongrantSeting.couponIssuingId"  id="couponIssuingIdHid" value="<s:property value='coupongrantSeting.couponIssuingId'/>"/>
					<input type="hidden" id="couponTypeId" value="<s:property value='coupongrantSeting.couponGivetypeId'/>"/>
				</td>
			</tr>
			<tr>
				<td width="20%"  align="right" valign="top"><font color="red">*</font>发布类型：</td>
				<td width="80%" align="left">
				<s:if test="coupongrantSeting.couponGivetypeId==1">
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType1" checked="checked" value="1" onchange="typeChange(this.value)"><label for="selectType1">手工发放（选择目标后立即开始发劵）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType2" value="2" onchange="typeChange(this.value)"><label for="selectType2">注册发放（会员注册成功时按此规则发优惠券+时代会员首次登录康美商城）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType3" value="6" onchange="typeChange(this.value)"><label for="selectType3">不记名发放（先生成优惠券，会员获得密码后，通过激活操作关联使用）</label>
				</s:if>
				<s:elseif test="coupongrantSeting.couponGivetypeId==2">
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType1" value="1" onchange="typeChange(this.value)"><label for="selectType1">手工发放（选择目标后立即开始发劵）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType2"  checked="checked"  value="2" onchange="typeChange(this.value)"><label for="selectType2">注册发放（会员注册成功时按此规则发优惠券+时代会员首次登录康美商城）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType3" value="6" onchange="typeChange(this.value)"><label for="selectType3">不记名发放（先生成优惠券，会员获得密码后，通过激活操作关联使用）</label>
				</s:elseif>
				<s:elseif test="coupongrantSeting.couponGivetypeId==6">
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType1" value="1" onchange="typeChange(this.value)"><label for="selectType1">手工发放（选择目标后立即开始发劵）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType2"    value="2" onchange="typeChange(this.value)"><label for="selectType2">注册发放（会员注册成功时按此规则发优惠券+时代会员首次登录康美商城）</label><p/>
					<input type="radio" name="coupongrantSeting.couponGivetypeId" id="selectType3"  checked="checked" value="6" onchange="typeChange(this.value)"><label for="selectType3">不记名发放（先生成优惠券，会员获得密码后，通过激活操作关联使用）</label>
				</s:elseif>
				</td>
			</tr>
			</table>
			<p/>	
			<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr> 
					<th colspan="3" align="left" class="edit_title">发放详细规则</th>
				</tr>
				<tr>
					<td>
					<s:if test="coupongrantSeting.couponGivetypeId==1">
						<!--手工发放  -->
						<div id="handworkGrantDiv">
							<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
								<tr>
									<td width="20%"  align="right" valign="top">发放范围方式：</td>
									<td width="80%"  align="left">
										<input type="radio" name="grantWay" id="grantWay1" checked="checked" value="1"><label for="grantWay1">指定具体会员账号发放</label><p/>
									</td>
								</tr>
								<tr>
									<td width="20%"  align="right" valign="top">
										<div id="grantWay1Div">指定会员账号：</div>
									</td>	
									<td width="80%"  align="left" valign="middle">
										<input type="button" id="userSelectBtn"  onclick="selectUser();" value="选择"/>
										<div id="grantWay1TableDiv" >
											<table width="426" height="39" border="1" align="left"
												cellspacing="0" id="grantWay1_table">
												<tbody>
														<tr>
															<td width="100">
							                                <input type="checkbox" name="checkbox"
																id="select_leveall"> <label for="checkbox"></label></td>
															<th width="169">会员账号</th>
															<th width="156">会员姓名</th>
															<th width="187">操作</th>
														</tr>
							                     </tbody>
							                     <tbody  id="customContent"></tbody>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<!--注册发放  -->
						<div id="regestGrantDiv" style="display: none">
							 <table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
								<tr>
									<td  width="20%"  align="right">开始发放时间：</td>
									<td width="80%"  align="left"><input id="d4311" class="Wdate"
				type="text" name="coupongrantSeting.issuingStartTime" value="<s:date name='coupongrantSeting.issuingStartTime' format='yyyy-MM-dd HH:mm:ss' />"
                  required="true"	
		onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/ ></td>
								</tr>
								<tr>
									<td width="20%" align="right">截止发放时间：</td>
									<td width="80%" align="left"><input id="d4312" class="Wdate" type="text"
							name="coupongrantSeting.issuingEndTime"  value="<s:date name='coupongrantSeting.issuingEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					 required="true"		 onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								</tr>
							</table>
						</div>
						<!--不记名发放  -->
						<div id="bearerGrantDiv" style="display: none">
							<table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
						    	<tr>
						    		<td width="20%" align="right">生成优惠券数量：</td>
						    		<td width="80%" align="left"><input type="text" id="issuingCount" name="coupongrantSeting.issuingCount"/></td>
						    	</tr>
						    </table>
						</div>
					</s:if>
					<s:elseif test="coupongrantSeting.couponGivetypeId==2">
						<!--手工发放  -->
						<div id="handworkGrantDiv" style="display: none">
							<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
								<tr>
									<td width="20%"  align="right" valign="top">发放范围方式：</td>
									<td width="80%"  align="left">
										<input type="radio" name="grantWay" id="grantWay1" checked="checked" value="1"><label for="grantWay1">指定具体会员账号发放</label><p/>
									</td>
								</tr>
								<tr>
									<td width="20%"  align="right" valign="top">
										<div id="grantWay1Div">指定会员账号：</div>
									</td>	
									<td width="80%"  align="left" valign="middle">
										<input type="button" id="userSelectBtn"  onclick="selectUser();" value="选择"/>
										<div id="grantWay1TableDiv" >
											<table width="426" height="39" border="1" align="left"
												cellspacing="0" id="grantWay1_table">
												<tbody>
														<tr>
															<td width="100">
							                                <input type="checkbox" name="checkbox"
																id="select_leveall"> <label for="checkbox"></label></td>
															<th width="169">会员账号</th>
															<th width="156">会员姓名</th>
															<th width="187">操作</th>
														</tr>
							                     </tbody>
							                     <tbody  id="customContent"></tbody>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<!--注册发放  -->
						<div id="regestGrantDiv" >
							 <table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
								<tr>
									<td  width="20%"  align="right">开始发放时间：</td>
									<td width="80%"  align="left"><input id="d4311" class="Wdate"
				type="text" name="coupongrantSeting.issuingStartTime" value="<s:date name='coupongrantSeting.issuingStartTime' format='yyyy-MM-dd HH:mm:ss' />"
                  required="true"	
		onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/ ></td>
								</tr>
								<tr>
									<td width="20%" align="right">截止发放时间：</td>
									<td width="80%" align="left"><input id="d4312" class="Wdate" type="text"
							name="coupongrantSeting.issuingEndTime"  value="<s:date name='coupongrantSeting.issuingEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					 required="true"		 onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								</tr>
							</table>
						</div>
						<!--不记名发放  -->
						<div id="bearerGrantDiv" style="display: none">
							<table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
						    	<tr>
						    		<td width="20%" align="right">生成优惠券数量：</td>
						    		<td width="80%" align="left"><input type="text"  id="issuingCount" name="coupongrantSeting.issuingCount"/></td>
						    	</tr>
						    </table>
						</div>
					</s:elseif>
					<s:else>
						<!--手工发放  -->
						<div id="handworkGrantDiv" style="display: none">
							<table width="98%" align="center" height="35" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
								<tr>
									<td width="20%"  align="right" valign="top">发放范围方式：</td>
									<td width="80%"  align="left">
										<input type="radio" name="grantWay" id="grantWay1" checked="checked" value="1"><label for="grantWay1">指定具体会员账号发放</label><p/>
									</td>
								</tr>
								<tr>
									<td width="20%"  align="right" valign="top">
										<div id="grantWay1Div">指定会员账号：</div>
									</td>	
									<td width="80%"  align="left" valign="middle">
										<input type="button" id="userSelectBtn"  onclick="selectUser();" value="选择"/>
										<div id="grantWay1TableDiv" >
											<table width="426" height="39" border="1" align="left"
												cellspacing="0" id="grantWay1_table">
												<tbody>
														<tr>
															<td width="100">
							                                <input type="checkbox" name="checkbox"
																id="select_leveall"> <label for="checkbox"></label></td>
															<th width="169">会员账号</th>
															<th width="156">会员姓名</th>
															<th width="187">操作</th>
														</tr>
							                     </tbody>
							                     <tbody  id="customContent"></tbody>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
						<!--注册发放  -->
						<div id="regestGrantDiv" style="display: none">
							 <table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
								<tr>
									<td  width="20%"  align="right">开始发放时间：</td>
									<td width="80%"  align="left"><input id="d4311" class="Wdate"
				type="text" name="coupongrantSeting.issuingStartTime" value="<s:date name='coupongrantSeting.issuingStartTime' format='yyyy-MM-dd HH:mm:ss' />"
                  required="true"	
		onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/ ></td>
								</tr>
								<tr>
									<td width="20%" align="right">截止发放时间：</td>
									<td width="80%" align="left"><input id="d4312" class="Wdate" type="text"
							name="coupongrantSeting.issuingEndTime"  value="<s:date name='coupongrantSeting.issuingEndTime' format='yyyy-MM-dd HH:mm:ss' />"
					 required="true"		 onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
								</tr>
							</table>
						</div>
						<!--不记名发放  -->
						<div id="bearerGrantDiv" >
							<table width="98%" align="center" height="35" border="0"	class="content_table"
						    		cellpadding="0" cellspacing="0">
						    	<tr>
						    		<td width="20%" align="right">生成优惠券数量：</td>
						    		<td width="80%" align="left"><input type="text" id="issuingCount" name="coupongrantSeting.issuingCount" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/></td>
						    	</tr>
						    </table>
						</div>
					</s:else>	
					</td>
				</tr>
				
			</table>

		
			<!-- 底部 按钮条 -->
			<table width="98%" align="center" class="edit_bottom" height="30"
				border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
				<tr>
					<td align="center">
                    <s:if test="viewType!='show'">
                    <!-- 点击事件进行校验-->
                    <INPUT class="saveBtn" TYPE="button"   value=""  onClick="editVerifyData()">
                    </s:if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input  type="button" class="backBtn" onClick="javascript:history.go(-1);" />
					<td width="20%" align="center"></td>
				</tr>
		  </table>
		<br>
		<br>
	</s:form>
</body>
<style type="text/css">
#auto_div {
	width: 515px;
	height: 230px;
	/*word-break:break-all;*/
	word-wrap: break-word;
	/*自动出现滚动条*/
	overflow: auto; /*超出div部份则自动隐藏*/
	*overflow-x: hidden;
	border: 1px solid #666;
}

#leve_div {
	width: 444px;
	height: 118px;
	word-wrap: break-word;
	overflow: auto;
	*overflow-x: hidden;
	border: 1px solid #666;
}
</style>
<script type="text/javascript">
//初始化
	//初始化
	$().ready(function(e) {
		//手工发放
		if ($("#giveType").val()==1) {
			 $("#leveRow").show();
			$("#custom_leve").show();
			$("#custom_id").show();
			$("#order_money").hide();
			$("#payLeastMoney").show()
			$("#payLeastMoney_value").show();
			$("#textfield10").detach();
			$("#fafang_title").show();
			$("#timeInput").show();
			$("#Coupondays").remove();
			$("#days").remove();
		}
		//注册发放
		if ($("#giveType").val() == 2) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
			$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").show();
			$("#days").remove();
		}
		//订单
		if ($("#giveType").val() == 3) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
		 	$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").show();
			$("#days").remove();
		}
		//积分兑换
		if ($("#giveType").val()== 4) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
			$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").hide();
		//	alert($("#Conpondays").length);
		 	 if ($("#Conpondays").length==0){
			$("#dayInput").html("");
			var days =$("#validDay").val();
			//alert(days)
			 $("#dayInput").append(" <div id='days'> <input id='Coupondays'  type='input' name='coupon.couponValidDay' value="+days+"    /> 天</div> ");	
		 	 }
			$("#dayInput").removeAttr("style");
	}
		 	});
</script>

 
</HTML>


