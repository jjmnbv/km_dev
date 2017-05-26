<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.min.js">
</script>
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/pageCommon.js">
</script>
		
</script>

		<script type="text/javascript">
$(document).ready(function() {
	var sonId = $("#customer").val();
	var parentId = $("#parentId").val();
	if (sonId != null || sonId != "") {
		querySonCustomerType(parentId, sonId);
	}
});
/**通过customer类别id 查询子客户类别 **/
function querySonCustomerType(id, value) {
	$("#customer").val("");
	if (id != "") {
		$.ajax( {
			async : false,
			url : "/growing/userLevel_ajaxOperateCustomerType.action",
			type : "POST",
			data : "customerId=" + id,
			dataType : "json",
			success : function(data) {
				if ((data != null && data != "") && data.length > 0) {
					$("#customer option").remove();
					$("#customer").show();
					for ( var i = 0; i < data.length; i++) {
						if (value != "" && value == data[i].customerTypeId) {
							$("#customer").append(
									"<option value='" + data[i].customerTypeId
											+ "' selected>" + data[i].name
											+ "</option>");
						} else {
							$("#customer")
									.append(
											"<option value='"
													+ data[i].customerTypeId
													+ "'>" + data[i].name
													+ "</option>");
						}
					}
				} else {
					$("#customer option").remove();
					$("#customer").hide();
				}
			}
		});
	} else {
		$("#customer option").remove();
		$("#customer").hide();
	}
}
function removeRepetitive(arr){
    var len = arr.length;
    var newArr = [];
    if (len < 2) return arr;
    for (var i = 0; i < len; i++) {
        for (var j = i + 1; j < len; j++) {
            if (arr[i] === arr[j]) {
                j = ++i;
            }
        }
        if(arr[i]){
        	 newArr.push(arr[i]);
         }
       
    }
    return newArr;
}
/** 选择团出窗口数据  **/
function selectOneAccount(accountId, account, name, sonCustomerId,
		customerTypeName) {
	var method = "<%=request.getAttribute("callBack")%>";
	         if(method!=null||method!=""){
	            parent.<%=request.getAttribute("callBack")%>(accountId,account,name,sonCustomerId,customerTypeName);
	         }else{
	           parent.closeOpenUserInfo(accountId,account,name,sonCustomerId,customerTypeName);
	         }
         }
	  	$(document).ready(function(){ 
	  		$('#seachInfo').click(function() {
	  		   $("#listTable input[name='loginIdss']").each(function(){
	  			    $(this).attr("checked",false);
	  			   });
	  			document.accountInfoForm.action = "/logininfo/logininfo_checkboxInfoList.action";
	  			document.accountInfoForm.submit();

	  		});

		$('#selectInfo').click(function() {
			var idschecks=$("#listTable input[name='loginIdss']:checked"); 
			if(idschecks.size()==0){
				alert('请选择用户信息','提示');
				return false;
			}else{
			   var oldNameArr = $('#loginAccount',parent.window.document).val().split(',');
			   var oldIdArr = $('#n_LoginId',parent.window.document).val().split(',');
				idschecks.each(function(){	
				  var data = this.value;
				  var aa=data.split(",");
				  var loginId=	aa[0];
				  var loginName=aa[1];
				  if(loginId){
					  oldIdArr.push(loginId);
					 }
				  if(loginName){
					  oldNameArr.push(loginName);
					 }
				 
				});
				var newNameArr = removeRepetitive(oldNameArr);
				var newIdArr = removeRepetitive(oldIdArr);
				   var method = "<%=request.getAttribute("callBack")%>";
	         if(method!=null||method!=""){
	            parent.<%=request.getAttribute("callBack")%>(newIdArr.join(','),newNameArr.join(','));
	         }else{
	           parent.closeOpenUserInfo(newIdArr.join(','),newNameArr.join(','));
	         }
		   }
		});
		
		//全选反选
		 $("#loginIdss").click(function(){
		  if($(this).attr("checked") =="checked"){ //check all
		   $("#listTable input[name='loginIdss']").each(function(){
		    $(this).attr("checked",true);
		   });
		  }else{
		   $("#listTable input[name='loginIdss']").each(function(){
		    $(this).attr("checked",false);
		   });
		  }
		 });

	});
</script>
	</head>
	<body>
		<s:form name="accountInfoForm"
			action="/logininfo/logininfo_checkboxInfoList.action"
			onsubmit="return checkAllTextValid(this)" method="post">
			<table width="98%" class="content_table" align="center"
				cellpadding="0" cellspacing="0" style="margin: 10 0 10 0px;">
				<tr>
					<td align="right">
						会员账号：
					</td>
					<td>
						<input  id="loginAccount" name="userInfoDO.loginAccount" type="text"
							value="<s:property value='userInfoDO.loginAccount'/>">
					</td>
					<td align="right">
						客户类别：
					</td>
					<td>
						<s:select id="customerTypeId"   name="userInfoDO.customerTypeId" list="customerTypeList"
							listKey="customerTypeId" listValue="name" headerKey=""
							headerValue="全部" onchange="querySonCustomerType(this.value,null)"></s:select>
						<select id="customer" name="userInfoDO.sonCustomerId"
							style="display: none;"></select>
						<input type="hidden" id="sonCustomerId"
							value="<s:property value='userInfoDO.sonCustomerId'/>">
						<input type="hidden" id="parentId"
							value="<s:property value='userInfoDO.customerTypeId'/>" />
					</td>
					<td align="right">
						真实姓名：
					</td>
					<td>
						<input id="name"  name="userInfoDO.name" type="text"
							value="<s:property value='userInfoDO.name'/>">
					</td>

					<td align="center">
						<input type="button" class="queryBtn" id="seachInfo">
						<input type="button" value="确定"  style="height:30px"  class="btn-custom" id="selectInfo">

					</td>
				</tr>
			</table>


			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="left" 
				cellspacing="0" border="1" bordercolor="#C1C8D2" id="listTable">
				<tr>
					<th>
							<input type="checkbox"   id="loginIdss">全选/全不选
					</th>
					<th align="center">
						会员账号
					</th>
					<th align="center">
						客户类别
					</th>
					<th align="center">
						真实姓名
					</th>
					<th align="center">
						电子邮箱
					</th>
					<th align="center">
						手机号码
					</th>
					<th align="center">
						账号状态
					</th>
				</tr>
				<s:iterator id="accountiterator" value="page.dataList">
					<tr>
						<td>
							<input type="checkbox" name="loginIdss"
								value="<s:property value='#accountiterator.loginId'/>,<s:property value='#accountiterator.loginAccount'/>">
						</td>
						<td align="center">
							<s:property value="#accountiterator.loginAccount" />
						</td>
						<td align="center">
							<s:property value="customerTypeName" />
						</td>
						<td align="center">
							<s:property value="name" />
						</td>
						<td align="center">
							<s:property value="email" />
						</td>
						<td align="center">
							<s:property value="mobile" />
						</td>
						<td align="center">
							<s:if test="status==1">
		                                                         正常
		                     </s:if>
							<s:elseif test="status==0">
		                                                         禁用
		                     </s:elseif>
						</td>
					</tr>
				</s:iterator>
			</table>

			<table width="58%" align="left" class="page_table">
				<tr>
					<td>
						<s:set name="form_name" value="'accountInfoForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>

