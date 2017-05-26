<<<<<<< HEAD
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
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

function gotoAdd(){
     document.paymentForm.action="/accounts/payment_addPayments.action ";
     document.paymentForm.submit();
}
/** 进入修改充值信息页面  **/
function editPayment(id){
     location.href="/accounts/payment_addPayment.action?accountTransactionId="+id;
}

/** 单条删除充值信息  **/
function  deleteByKey(id){
     if(confirm("是否确认删除? ")==true){
       location.href="/accounts/payment_deletePayment.action?accountTransactionId="+id;
     }
}
     /** 全选js  **/
     function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    /*var ct = ele.getAttribute("type");*/
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
}

     //弹出层 选择账号
 	function popUpAccount() {
 	 
 	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","340px","iframe");
 	}
 	//关闭弹出窗口 
	function closeOpenAccount(accountId,account){
	    closeThis();
	    document.forms[0].accountId.value = accountId;
		document.forms[0].accountLogin.value = account;
	}
</script>

</head>
<body>

<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'找回密码'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form  name="paymentForm" action="/accounts/pwd_showPasswordList.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<!--<tr>
					<td width="80%" valign="middle" colspan="2">
						<input class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('levelId');">
					</td>
				</tr>-->
				<tr>
					<td width="90%" valign="middle" colspan="2">
						账户号：
						<input type="hidden" id="accountId"   name="bnesAcctTransactionQuery.accountId" value="<s:property value='bnesAcctTransactionQuery.accountId'/>" />
						<input id="accountLogin" name="bnesAcctTransactionQuery.accountLogin"  type="text"  class="input_stype"
							value="<s:property value='bnesAcctTransactionQuery.accountLogin'/>" />
							<input type="button"  value="选择" onClick="popUpAccount()">
					</td>
					
					<td align="center">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
	
	
		<tr>
	    <th width="5%">
						<input type='checkbox' name='allbox' onclick='checkAll(this)'>
					</th>
		<th >账户号</th>
		<th>真实姓名</th>
		<th>手机号码</th>
	<!--删除 	<th>电子邮箱</th> -->
		<th>账户类别</th>
		<th>账户注册日期</th>
		<th>操作</th>
	</tr>
	

<s:iterator id="custiterator"  value="page.dataList">
<tr>
<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="accountTransactionId"/>' />
						</td>

		   
<td>
 <s:property value="accountLogin"/>
</td>
<td>
<s:property value="accountNumber"/>
</td>
<td>
<s:property value="amount"/>
</td>

<td>
<s:if test="status==0">
充值退回失败
</s:if>
<s:if test="status==1">
充值成功
</s:if>
<s:if test="status==2">
充值退处理中
</s:if>
<s:if test="status==3">
充值全部退回
</s:if>
</td>
<td>
<s:property value="content"/>
</td>
<td>
<s:property value="createDate"/>
</td>
<td>
<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editPayment(<s:property value="accountTransactionId"/>)" />
<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="accountTransactionId"/>)" />
							
						</td>
	</tr>	
	</s:iterator>
	</table>
	<table width="500"  align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'paymentForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>
=======
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
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

function gotoAdd(){
     document.paymentForm.action="/accounts/payment_addPayments.action ";
     document.paymentForm.submit();
}
/** 进入修改充值信息页面  **/
function editPayment(id){
     location.href="/accounts/payment_addPayment.action?accountTransactionId="+id;
}

/** 单条删除充值信息  **/
function  deleteByKey(id){
     if(confirm("是否确认删除? ")==true){
       location.href="/accounts/payment_deletePayment.action?accountTransactionId="+id;
     }
}
     /** 全选js  **/
     function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    /*var ct = ele.getAttribute("type");*/
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
}

     //弹出层 选择账号
 	function popUpAccount() {
 	 
 	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","340px","iframe");
 	}
 	//关闭弹出窗口 
	function closeOpenAccount(accountId,account){
	    closeThis();
	    document.forms[0].accountId.value = accountId;
		document.forms[0].accountLogin.value = account;
	}
</script>

</head>
<body>

<s:set name="parent_name" value="'账户管理'" scope="request"/>
<s:set name="name" value="'找回密码'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form  name="paymentForm" action="/accounts/pwd_showPasswordList.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" align="center"  border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<!--<tr>
					<td width="80%" valign="middle" colspan="2">
						<input class="addBtn" TYPE="button" value="" onclick="gotoAdd();">
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('levelId');">
					</td>
				</tr>-->
				<tr>
					<td width="90%" valign="middle" colspan="2">
						账户号：
						<input type="hidden" id="accountId"   name="bnesAcctTransactionQuery.accountId" value="<s:property value='bnesAcctTransactionQuery.accountId'/>" />
						<input id="accountLogin" name="bnesAcctTransactionQuery.accountLogin"  type="text"  class="input_stype"
							value="<s:property value='bnesAcctTransactionQuery.accountLogin'/>" />
							<input type="button" class="btn-custom" value="选择" onClick="popUpAccount()">
					</td>
					
					<td align="center">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
	
	
		<tr>
	    <th width="5%">
						<input type='checkbox' name='allbox' onclick='checkAll(this)'>
					</th>
		<th >账户号</th>
		<th>真实姓名</th>
		<th>手机号码</th>
	<!--删除 	<th>电子邮箱</th> -->
		<th>账户类别</th>
		<th>账户注册日期</th>
		<th>操作</th>
	</tr>
	

<s:iterator id="custiterator"  value="page.dataList">
<tr>
<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="accountTransactionId"/>' />
						</td>

		   
<td>
 <s:property value="accountLogin"/>
</td>
<td>
<s:property value="accountNumber"/>
</td>
<td>
<s:property value="amount"/>
</td>

<td>
<s:if test="status==0">
充值退回失败
</s:if>
<s:if test="status==1">
充值成功
</s:if>
<s:if test="status==2">
充值退处理中
</s:if>
<s:if test="status==3">
充值全部退回
</s:if>
</td>
<td>
<s:property value="content"/>
</td>
<td>
<s:property value="createDate"/>
</td>
<td>
<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editPayment(<s:property value="accountTransactionId"/>)" />
<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="accountTransactionId"/>)" />
							
						</td>
	</tr>	
	</s:iterator>
	</table>
	<table width="500"  align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'paymentForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>
>>>>>>> bf662895ab20a46b15746d708f6dc7b1e6937155
