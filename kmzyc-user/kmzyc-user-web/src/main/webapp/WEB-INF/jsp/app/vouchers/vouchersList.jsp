<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电子消费券</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
function deleteSelected(id){
    var obj = document.getElementsByName(id);
            var count = 0;
            var obj_cehcked = null;
            // 遍历所有用户，找出被选中的用户
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    count++;
                    obj_cehcked = obj[i];
                }
            }
             if (count == 0) {
                    alert("请选择要删除的数据。");
                    return false;
             }else if(confirm('是否确认删除?')==true){ 
                
            	  document.paymentForm.action="/accounts/payment_deleteAllPayment.action ";
         	     document.paymentForm.submit();
             }

}
/**  进入新增账户充值信息页面  **/

function gotoAdd() {
	document.paymentForm.action = "/accounts/payment_addPayments.action ";
	document.paymentForm.submit();
}
/** 进入修改充值信息页面  **/
function editPayment(id) {
	location.href = "/accounts/payment_addPayment.action?accountTransactionId="
			+ id;
}

/** 单条删除充值信息  **/
function deleteByKey(id) {
	if (confirm("是否确认删除? ") == true) {
		location.href = "/accounts/payment_deletePayment.action?accountTransactionId="
				+ id;
	}
}
/** 全选js  **/
function checkAll(ck) {
	for ( var i = 0; i < ck.form.all.tags("input").length; i++) {
		var ele = ck.form.all.tags("input")[i];
		/*var ct = ele.getAttribute("type");*/
		if ((ele.type == "checkbox")) {
			if (ck.checked != ele.checked)
				ele.click();
		}
	}
}

//弹出 选择账号层
function popUpUserInfo() {
	dialog(
			"选择会员账号",
			"iframe:/logininfo/logininfo_queryPageBasicUserInfo.action?callBack=closeOpenUserInfo",
			"900px", "500px", "iframe");
}
//关闭弹出窗口 
function closeOpenUserInfo(accountId, account, name) {
	closeThis();
	$("#custName").val(account);

}
</script>

	</head>
	<body>

<div  style="height:90%;overflow-y:auto; " >
<s:form id="vouchersForm" name="vouchersForm"  onsubmit="return checkAllTextValid(this)" method="post" action="/accounts/vouchers_showVouchersList.action" >
<input value='<s:property value="coupons.custmoId" />' type="hidden" name="coupons.custmoId"/>
	<table width="98%" height="70" class="content_table" align="center"
		cellpadding="0" cellspacing="2">
		<tr>
			<td>
				会员账号：<s:property value="coupons.loginAccount" />
			</td>
			<td>
				优惠券规则ID：
				<input name="coupons.couponId"  type="text" 
									value='<s:property value="coupons.couponId"/>'>
			</td>
			<td>
				发放类型：
				<select name="coupons.grantType" >
    				<option value=""   <s:if test='coupons.grantType==""'>selected="selected"</s:if>>全部</option>
    				<option value="11" <s:if test='coupons.grantType=="11"'>selected="selected"</s:if>>手工类型发放</option>
				    <option value="61" <s:if test='coupons.grantType=="61"'>selected="selected"</s:if>>不记名发放</option>
				    <option value="51" <s:if test='coupons.grantType=="51"'>selected="selected"</s:if>>抽奖奖品发放</option>
				    <option value="41" <s:if test='coupons.grantType=="41"'>selected="selected"</s:if>>积分兑换发放</option>
				    <option value="31" <s:if test='coupons.grantType=="31"'>selected="selected"</s:if>>订单满足活动类型发放</option>
				    <option value="21" <s:if test='coupons.grantType=="21"'>selected="selected"</s:if>>注册类型发放</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				优惠券名称：<input name="coupons.couponName" type="text" 
									value='<s:property value="coupons.couponName"/>'>
			</td>
			<td>
				优惠券状态：
				<select name="coupons.couponStatus" >	
				    <option value=""  <s:if test='coupons.couponStatus==""'>selected="selected"</s:if>  >全部</option>
				    <option value="2" <s:if test='coupons.couponStatus=="2"'>selected="selected"</s:if> >已发放</option>
				    <option value="4" <s:if test='coupons.couponStatus=="4"'>selected="selected"</s:if>>已使用</option>
				    <option value="1" <s:if test='coupons.couponStatus=="1"'>selected="selected"</s:if>>未发放</option>
				    <option value="3" <s:if test='coupons.couponStatus=="3"'>selected="selected"</s:if>>未使用</option>
				    <option value="5" <s:if test='coupons.couponStatus=="5"'>selected="selected"</s:if>>已过期</option>
				    <option value="7" <s:if test='coupons.couponStatus=="7"'>selected="selected"</s:if>>冻结</option>
				    <option value="6" <s:if test='coupons.couponStatus=="6"'>selected="selected"</s:if>>已作废</option>
				</select>
			</td>
			<td>
				<INPUT TYPE="submit" class="queryBtn" value="">
			</td>
		</tr>		
	</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th>发放券ID</th>
						<th>优惠券规则ID</th>
						<th>优惠券名称</th>
						<th>发放类型</th>
						<th>面值（元）</th>
						<th>不记名优惠券编码</th>
						<th>激活码</th>
						<th>可用时间范围</th>
						<th>使用时间</th>
						<th>优惠券状态</th>
						
						<!-- 
						<th>
							消费券名称
						</th>
						<th>
							消费券类型
						</th>
						
						<th>
							折扣金额
						</th>
						<th>
							数量
						</th>
						
						<th>
							优惠券状态
						</th>
						<th>
							消费券开始日期
						</th>
						<th>
							消费券截止日期
						</th>
						-->
					</tr>



					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td><s:property value="couponGrantId" /></td>
							<td><s:property value="couponId" /></td>
							<td>
								<s:property value="couponName" />
							</td>
							<td>
						   		<s:if test="couponGivetypeId==1">手工发放</s:if> 
						   		<s:if test="couponGivetypeId==2">注册发放</s:if>
						   		<s:if test="couponGivetypeId==3">订单发放</s:if>
						   		<s:if test="couponGivetypeId==4">积分兑换</s:if>
							</td>
							<td>
								<s:property value="couponMoney" />
							</td>
							<!--  
							<td>
								1
							</td>
							-->
							<td><s:property value="couponInfoNo" /></td>
							<td><s:property value="activeCode" /></td>
							<td>
								<s:date name="startTime"  format=" yyyy-MM-dd hh:mm:ss"  />
								至<s:date name="endTime"  format=" yyyy-MM-dd hh:mm:ss"/>
							</td>
							<td><s:date name="grantUsetime"  format=" yyyy-MM-dd hh:mm:ss"/></td>
							<td>

								<s:if test="couponStatus==1">
									未发放
								</s:if>
								<s:if test="couponStatus==2">
								已发放
								</s:if>
								<s:if test="couponStatus==3">
									未使用
								</s:if>
								<s:if test="couponStatus==4">
								已使用
								</s:if>
								<s:if test="couponStatus==5">
								已过期
									</s:if>
							<s:if test="couponStatus==6">
							已作废
							</s:if>
							<s:if test="couponStatus==7">
							冻结
							</s:if>

							</td>
							

						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'vouchersForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
