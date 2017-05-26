<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开通预备金</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/etc/css/lottery.css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script src="/etc/js/dialog.js"></script>
</head>
<body>
<s:set name="parent_name" value="'预备金管理'" scope="request" />
<s:set name="name" value="'开通预备金'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<!-- 预备金信息 -->
<s:form id="reForm" name="reserverInfoForm"  action="/userInfo/reserverInfo_submitInfo.action" method="post">
<input type="hidden" name="reserveId" value="<s:property value='reserveId'/>"/>
<input type="hidden" name="userLoginId" value="<s:property value='userLoginId'/>"/>
<input type="hidden" name="opeaderId" value="<s:property value='opeaderId'/>"/>
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="opeaderId==1">
		<tr> 
		<th colspan="2" align="left" class="edit_title">预备金详情</th>
		</tr>
	</s:if>
	<s:elseif test="opeaderId==4">
		<tr> 
		<th colspan="2" align="left" class="edit_title">预备金调整</th>
		</tr>
	</s:elseif>
	<s:else>
		<tr> 
		<th colspan="2" align="left" class="edit_title">开通预备金</th>
		</tr>
	</s:else>
	<tr>
		<td width="20%"  class="eidt_rowTitle">用户名:</td>
		<td width="80%"><s:property value="accountInfo.accountLogin"/></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">公司名:</td>
		<td width="80%"><s:property value="accountInfo.corporateName"/></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">注册邮箱:</td>
		<td width="80%"><s:property value="accountInfo.email"/></td>
	</tr>
	<s:if test="opeaderId==4">
		<tr>
		<td width="20%"  class="eidt_rowTitle">预备金总额度：</td>
		<td width="80%"><s:property value="%{formatDouble(reserverInfo.totalLimit)}"/></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">预备金可用额度：</td>
		<td width="80%"><s:property value="%{formatDouble(reserverInfo.remainLimit)}"/></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">结算周期：</td>
		<td width="80%"><s:if test="%{reserverInfo.payType==1}">月度结算</s:if>
			<s:elseif test="%{reserverInfo.payType==2}">季度结算</s:elseif>
			<s:elseif test="%{reserverInfo.payType==2}">半年结算</s:elseif>
			<s:else>年度结算</s:else></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">调整金额：</td>
		<td width="80%"><input name="changePay" type="text" value="" onKeyUp="this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')" maxlength="10"></td>
		</tr>
		<tr>
		<td width="20%"  class="eidt_rowTitle">交易内容：</td>
		<td width="80%"><textarea rows="70" cols="5"class="valid" name="content"></textarea></td>
		</tr>
	</s:if>
	<s:else>
		<s:if test="opeaderId!=2">
			<tr>
				<td width="20%"  class="eidt_rowTitle">可用额度:</td>
				<td width="80%">￥<s:property value="%{formatDouble(reserverInfo.remainLimit)}"/></td>
			</tr>
			<tr>
				<td width="20%"  class="eidt_rowTitle">状态:</td>
					<td width="80%"><s:if test="reserverInfo.isAvailable==1">
						有效
					</s:if> <s:elseif test="reserverInfo.isAvailable==2">
						停用
					</s:elseif>
				</td>
			</tr>
			<tr>
				<td width="20%"  class="eidt_rowTitle">开通时间:</td>
				<td width="80%"><s:date name="reserverInfo.openDate" format="yyyy-mm-dd hh:MM:ss"/>
				</td>
			</tr>
		</s:if>
		<s:if test="editId==1">
			<tr>
			<td width="20%"  class="eidt_rowTitle">总额度:</td>
			<td width="80%">￥<s:property value='%{formatDouble(reserverInfo.totalLimit)}'/></td>
			</tr>
			<tr>
			<td width="20%"  class="eidt_rowTitle">还款周期:</td>
			<td width="80%"><s:radio disabled="true" name="reserverInfo.payType" list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple"></s:radio></td>
			</tr>
			<tr>
			<td width="20%"  class="eidt_rowTitle">联系电话:</td>
			<td width="80%"><s:property value='reserverInfo.phone'/></td>
			</tr>
		</s:if>
		<s:else>
			<tr>
			<td width="20%"  class="eidt_rowTitle">总额度:</td>
			<td width="80%"><input name="reserverInfo.totalLimit" type="text" value="<s:property value='reserverInfo.totalLimit'/>" onKeyUp="this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')" maxlength="10"></td>
			</tr>
			<s:if test="opeaderId==2">
				<tr>
				<td width="20%"  class="eidt_rowTitle">还款周期:</td>
				<td width="80%"><s:radio name="reserverInfo.payType"  list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple"></s:radio><lable id="settlementTypeerror"></lable></td>
				</tr>
			</s:if>
			<s:else>
				<tr>
				<td width="20%"  class="eidt_rowTitle">还款周期:</td>
				<td width="80%"><s:radio name="reserverInfo.payType" disabled="true" list="%{#{'1':'月度结算','2':'季度结算','3':'半年结算','4':'年度结算'}}" theme="simple"></s:radio><lable id="settlementTypeerror"></lable></td>
				</tr>
			</s:else>
			<tr>
			<td width="20%"  class="eidt_rowTitle">联系电话:</td>
			<td width="80%"><input name="reserverInfo.phone" type="text" value="<s:property value='reserverInfo.phone'/>" maxlength="11"></td>
			</tr>		
		</s:else>
		
	</s:else>
	<tr> 
		<th colspan="2" align="center" class="edit_title"> 
		    <s:if test="editId==2||editId==3||editId==4">
		    	<input type="submit" class="btn-custom" value="提交"/>
		    </s:if>
			<input type="button" class="backBtn" value="" onClick="returnBack()"/>
	    </th>
	</tr>
</table>
</s:form>
<script type="text/javascript">
    function returnBack(){
    	location.href = "/userInfo/reserverInfo_PageList.action";
    }
    
    
    
    $().ready(function(){
    	jQuery.validator.addMethod("cellphone1",function(value, element){
        	var tel =/^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/ ; 
        	return this.optional(element) || (tel.test(value)); 
        },"请正确填写手机号码");
    	/* juqery验证 */
		$("#reForm").validate({
			rules: {
				"reserverInfo.totalLimit":{required: true,number: true},
				"reserverInfo.phone":{required: true,cellphone1:true},
				"reserverInfo.payType":{required:true},
				"changePay":{number:true,required: true}
			},
			success: function (label){
	            label.removeClass("checked").addClass("checked");
	        },
			errorPlacement:function(error,element) {
				if (element.attr("type") == "radio"){
					error.insertAfter("#settlementTypeerror");
				} else {
					error.insertAfter(element);
				}
			} 
		 });
    	
    });
</script>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

