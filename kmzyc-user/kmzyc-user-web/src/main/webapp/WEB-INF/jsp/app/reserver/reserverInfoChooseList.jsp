<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金账户</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
	 function clooseName(accountLoginName){
		 parent.closeOpenAccount(accountLoginName);
	 }
</script>
</head>
<body>

<s:set name="parent_name" value="'预备金管理'" scope="request" />
<s:set name="name" value="'账单'" scope="request" />
<s:set name="son_name" value="'选择账户'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 查询条件区域 -->
<s:form name="reserverInfoForm"  action="/userInfo/reserverBill_chooseReserver.action" method="post">
<table  width="98%" height="100" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">用户名:</td>
		<td><input name="reserverInfo.accountLogin" type="text" value='<s:property value="reserverInfo.accountLogin"/>'></td>
		<td align="right">公司名:</td>
		<td><input name="reserverInfo.corporateName" type="text" value='<s:property value="reserverInfo.corporateName"/>'></td>
	</tr>
	<tr>
		<td align="right">联系电话:</td>
		<td><input name="reserverInfo.phone" type="text" value='<s:property value="reserverInfo.phone"/>'/> </td>
		<td align="right">状态:</td>
		<td style="padding-left: 2px"><select name="reserverInfo.isAvailable" style="width:75">
		<option value="" <s:if test='reserverInfo.isAvailable==""'>selected="selected"</s:if>>所有</option>
		<option value="1" <s:if test='reserverInfo.isAvailable=="1"'>selected="selected"</s:if>>有效</option>
		<option value="2" <s:if test='reserverInfo.isAvailable=="2"'>selected="selected"</s:if>>停用</option></select></td>
		<td align="right">结算周期:</td>
		<td style="padding-left: 2px"><select name="reserverInfo.payType" style="width:75">
		<option value="" <s:if test='reserverInfo.payType==""'>selected="selected"</s:if>>所有</option>
		<option value="1" <s:if test='reserverInfo.payType=="1"'>selected="selected"</s:if>>月度结算</option>
		<option value="2" <s:if test='reserverInfo.payType=="2"'>selected="selected"</s:if>>季度结算</option>
		<option value="3" <s:if test='reserverInfo.payType=="3"'>selected="selected"</s:if>>半年结算</option>
		<option value="4" <s:if test='reserverInfo.payType=="4"'>selected="selected"</s:if>>年度结算</option></select></td>
		<td align="right"><INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;&nbsp;&nbsp;</td>	
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >选择</th>
		<th align="center" >用户名</th>
		<th align="center" >公司名</th>
		<th align="center" >联系电话</th>		
		<th align="center" >结算周期</th>
		<th align="center" >总额度</th>
		<th align="center" >可用额度</th>
		<th align="center" >状态</th>
		<th align="center" >开通时间</th>
	</tr>
  <s:iterator id="custiterator" value="page.dataList">
	<tr>
		<td align="center">
		  <input type="button" value="选择" onclick="clooseName('<s:property value="accountLogin" />')" />
		</td>
		<td align="center">
		  <s:property value="accountLogin" />
		</td>
		<td align="center">
		  <s:property value="corporateName" />
		</td>
		<td align="center">
		  <s:property value="phone" />
		</td>
		<td align="center">
		  <s:if test="%{payType==1}">
			     月度结算
			</s:if>
			<s:elseif test="%{payType==2}">
			    季度结算
			</s:elseif>
			<s:elseif test="%{payType==3}">
			    半年结算
			</s:elseif>
			<s:elseif test="%{payType==4}">
			    年度结算
			</s:elseif>
			<s:if test="%{reserveId==null}">
			    --
			</s:if>
		</td>
		<td align="center">
		  <s:if test="%{reserveId==null}">
			    --
		  </s:if><s:else>
		  		￥<s:property value="%{formatDouble(totalLimit)}" />
		  </s:else>
		</td>
		<td align="center">
		  <s:if test="%{reserveId==null}">
			    --
		  </s:if><s:else>
		  	 ￥<s:property value="%{formatDouble(remainLimit)}" />
		  </s:else>
		</td>
		<td align="center">
		  <s:if test="%{isAvailable==1}">
			    有效
		  </s:if> <s:elseif test="%{isAvailable==2}">
		                停用
		  </s:elseif>
		  <s:else>
		  	  未开通
		  </s:else>
		</td>
		<td align="center">
			<s:date name = "openDate" format="yyyy-MM-dd HH:mm:ss"/>
			<s:if test="%{reserveId==null}">
			    --
			</s:if>
		</td>
		</tr>
	</s:iterator>
</table>
<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'reserverInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

