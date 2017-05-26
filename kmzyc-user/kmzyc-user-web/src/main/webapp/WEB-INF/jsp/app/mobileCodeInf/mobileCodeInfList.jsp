<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手机随机码信息管理</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<script type="text/javascript">


    //账号层
function queryAccountInfo(id) {                                                    
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");  
}
</script>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'安全认证'" scope="request" />
<s:set name="name" value="'手机验证'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
 <div  style="height:90%;overflow-y:auto; " >
<s:form  name="mobileCodeInfForm" action="/accounts/mobileCodeInf_pageList.action" method="post" onsubmit=" return checkAllTextValid(this)">
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	
	<tr>
		<td align="right">账户号：</td>
		<td>
		     <input name="mobileCodeInf.accountLogin" type="text" value="<s:property value='mobileCodeInf.accountLogin'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="mobileCodeInf.mobile" type="text" value="<s:property value='mobileCodeInf.mobile'/>">
		</td>
		<td align="right">最后发送随机码时间：</td>
		<td>
		     <input name="mobileCodeInf.lastSendTattedcodeTime"  readonly  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" type="text" value="<s:property value='mobileCodeInf.lastSendTattedcodeTime'/>">
		</td>
		
		<td align="center">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >账户号</th>
		<th align="center" >手机号码</th>
		<th align="center" >随机码</th>
		<th align="center" >失效时间值(分钟)</th>
		<th align="center" >最后发送随机码时间</th>
		<th align="center" >状态</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
		<td align="center">
		   
		     
		     <a href="#" onClick="queryAccountInfo(<s:property value="n_AccountId"/>);"><s:property value="accountLogin"/></a>
		     
		</td>
		<td align="center">
		   <s:property value="mobile"/>
		</td>
		<td align="center">
		     <s:property value="tattedCode"/>
		</td>
		<td align="center">
		     <s:property value="n_FailureTimeValue"/>
		</td>
		<td align="center">
		<s:property value="lastSendTattedcodeTime" />  
		</td>
		<td align="center">
									<s:if test="is_Status==1">
已验证
</s:if>
						<s:if test="is_Status==0">
未验证
</s:if>
		    
		</td>
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'mobileCodeInfForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>

