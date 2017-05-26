<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /** 删除账户信息  **/ 
    function gotoUpdates(id,accountLogin){
    	location.href="/accounts/modifyAcut_editAcotAmout.action?accountId="+id+"&accountLogin="+accountLogin;
	 dialog("账号金额修改","iframe:/accounts/modifyAcut_editAcotAmout.action?accountId="+id+"&accountLogin="+accountLogin ,"800px","500px","iframe"); 
    }
    function gotoUpdate(id){
    	location.href="/accounts/modifyAcut_editAcotAmout.action?accountId="+id;
    }
    function closeOpenDiv(){
        closeThis();
        location.href="/accounts/modifyAcut_showAcotAmout.action";
       
    }
    function gotoDetails(id){
    	location.href="/accounts/modifyAcut_showModifyHisory.action?accountId="+id+"&accountLogin="+accountLogin;
        }
    function gotoDetail(id,accountLogin){
    	 dialog("账号金额修改","iframe:/accounts/modifyAcut_showModifyHisory.action?accountId="+id+"&accountLogin="+accountLogin ,"900px","500px","iframe");
        }
    
        //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");
}    
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'账务管理'" scope="request" />
<s:set name="name" value="'账户后台充值'" scope="request" />
<s:set name="son_name" value="'查询'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form  name="modifyAcotInfoForm" onsubmit="return checkAllTextValid(this)" action="/accounts/modifyAcut_showAcotAmout.action" method="post">
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >

	
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="accountInfo.accountLogin" type="text" value="<s:property value='accountInfo.accountLogin'/>">
		</td>
		<!--  
		<td align="right">账户真实姓名：</td>
		<td>
		     <input name="accountInfo.name" type="text" value="<s:property value='accountInfo.name'/>">
		</td>
		<td align="right">身份证号码：</td>
		<td>
		     <input name="accountInfo.acconutId" type="text" value="<s:property value='accountInfo.acconutId'/>">
		</td>
		-->
		<td align="right">手机号码：</td>
		<td>
		     <input name="accountInfo.mobile" type="text" value="<s:property value='accountInfo.mobile'/>">
		</td>
		
		<td align="center">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >会员账号</th>
		<!--  <th align="center" >账户真实姓名</th>
		<th align="center" >身份证号码</th>-->
		<th align="center" >手机号码</th>	
		<th align="center" >账户金额</th>
		<th align="center" >账户可用金额</th>
		<th align="center" >账户冻结金额</th>
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
		<td align="center">
		    
		     <s:property value="accountLogin" />
		     
		</td>
		<!-- 
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="acconutId"/>
		</td>
		 -->
		<td align="center">
		     <s:property value="mobile"/>
		</td>

		<td align="center">
		   <s:property value="%{formatDouble(n_AccountAmount)}"/>
		</td>
		
		<td align="center">
		    <s:property value="%{formatDouble(amountAvlibal)}"/>
		</td>
		<td align="center">
		   <s:property value="%{formatDouble(amountFrozen)}"/>
		   
		</td>
		<td width="70px;">
			 <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value='n_AccountId'/>,'<s:property value="accountLogin"/>')"/>
		     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoUpdate(<s:property value='n_AccountId'/>)" />
		     
		</td>
		
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'modifyAcotInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>