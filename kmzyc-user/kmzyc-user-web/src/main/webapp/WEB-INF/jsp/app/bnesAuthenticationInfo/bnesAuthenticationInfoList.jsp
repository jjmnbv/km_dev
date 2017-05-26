<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名认证信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /**  进入审核信息页面  **/
    function gotoAuditing(id){
      location.href="/accounts/bnesAuthenticationInfo_preAuditing.action?authenticationId="+id;
    }
        //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");
    
    
}
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'安全认证'" scope="request" />
<s:set name="name" value="'实名认证'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
  <div  style="height:90%;overflow-y:auto; " >
<s:form name="bnesAuthenticationInfoForm" action="/accounts/bnesAuthenticationInfo_pageList.action" 
   onsubmit="return checkAllTextValid(this) " method="post">
<!-- 查询条件区域 -->
<table width="98%" height="100" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">账户号：</td>
		<td>
		     <input name="bnesAuthenticationInfo.accountLogin" type="text" value="<s:property value='bnesAuthenticationInfo.accountLogin'/>">
		</td>
		<td align="right">账户真实姓名：</td>
		<td>
		     <input name="bnesAuthenticationInfo.name" type="text" value="<s:property value='bnesAuthenticationInfo.name'/>">
		</td>
		<td align="right">身份证号码：</td>
		<td>
		     <input name="bnesAuthenticationInfo.acconutId" type="text" value="<s:property value='bnesAuthenticationInfo.acconutId'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="bnesAuthenticationInfo.mobile" type="text" value="<s:property value='bnesAuthenticationInfo.mobile'/>">
		</td>
		
	</tr>
	<tr>
	<td align="left" colspan="4" >认证审批是否通过：
			<select name="bnesAuthenticationInfo.examinationValue">
				
				<s:if test="bnesAuthenticationInfo.examinationValue==0">
					<option value="">全部</option>
					<option value="1">通过</option>
					<option selected="selected" value="0">未通过</option>
				</s:if>
				<s:elseif test="bnesAuthenticationInfo.examinationValue==1">
					<option value="">全部</option>
					<option value="1" selected="selected">通过</option>
					<option  value="0">未通过</option>
				</s:elseif>
				<s:else>
				<option value="" selected="selected">全部</option>
					<option value="1" >通过</option>
					<option  value="0">未通过</option>
				</s:else>
				
			</select>
	</td>
		
		<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >账户号</th>
		<th align="center" >联系人姓名</th>
		<th align="center" >客户类别</th>
		<th align="center" >手机号码</th>
		<th align="center" >证件号码</th>
		<th align="center" >认证审批是否通过</th>
		<th align="center" >认证创建日期</th>
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
		<td align="center">
		    
		     <a href="#" onclick="queryAccountInfo(<s:property value="accountId"/>);"><s:property value="accountLogin" /></a>
		     
		</td>
		<td align="center">
		   <s:property value="name"/>
		</td>
		<td align="center">
		   <s:property value="customerName"/>
		</td>
		<td align="center">
		     <s:property value="acconutId"/>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		
		<td align="center">
			<s:if test="examinationValue==0">
				未通过
			</s:if>
			<s:else>
				通过
			</s:else>
		</td>
		<td align="center">
		<s:date   name="createDate"  format="yyyy-MM-dd"/>
		</td>
		<td>
		     <img title="审核" style="cursor: pointer;" src="/etc/images/thread.gif"  onclick="gotoAuditing(<s:property value="authenticationId"/>)"/>
		</td>
	</tr>
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'bnesAuthenticationInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
<!-- 消息提示页面 -->
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

