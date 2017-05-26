<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<!--<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />-->
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
       $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });
       /**通过customer类别id 查询子客户类别 **/
        function querySonCustomerType(id,value){
          if(id!=""){
                 $.ajax({
 				async:false,
 				url:"/growing/userLevel_ajaxOperateCustomerType.action",
 				type:"POST",
 				data:"customerId=" + id,
 				dataType:"json",
 				success:function(data){
 					if((data!=null||data!="")&&data.length>0){
 					   $("#customer option").remove();
 					     $("#customer").show();
	 					for(var i=0;i<data.length;i++){
			 					if(value!=""&&value==data[i].customerTypeId){
			 					 $("#customer").append("<option value='"+data[i].customerTypeId+"' selected>"+data[i].name+"</option>");
			 					}else{
			 					 $("#customer").append("<option value='"+data[i].customerTypeId+"'>"+data[i].name+"</option>");
			 					}
	 					}
 				    }else{
 				        $("#customer option").remove();
 				        $("#customer").hide();
 				    }
 			   }
 			});
          }else{
               $("#customer option").remove();
 			   $("#customer").hide();
          }
        }
        /** 选择团出窗口数据  **/
         function selectOneAccount(accountId,account,name,sonCustomerId,customerTypeName){
            var method="<%=request.getAttribute("callBack")%>";
	         if(method!=null||method!=""){
	            parent.<%=request.getAttribute("callBack")%>(accountId,account,name,sonCustomerId,customerTypeName);
	         }else{
	           parent.closeOpenUserInfo(accountId,account,name,sonCustomerId,customerTypeName);
	         }	             
         }
</script>
</head>
<body>
<s:form  name="accountInfoForm" action="/logininfo/logininfo_queryPageBasicUserInfo.action" 
    onsubmit="return checkAllTextValid(this)" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="userInfoDO.loginAccount" type="text" value="<s:property value='userInfoDO.loginAccount'/>">
		</td>
		<td align="right">客户类别：</td>
		<td>
		
		<s:select name="userInfoDO.customerTypeId"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"  ></s:select>
					    <select id="customer" name="userInfoDO.sonCustomerId"  style="display:none;"></select>
					    <input type="hidden"  id="sonCustomerId" value="<s:property value='userInfoDO.sonCustomerId'/>">
					    <input type="hidden"  id="parentId"  value="<s:property value='userInfoDO.customerTypeId'/>"/>	
		</td>
		<td align="right">真实姓名：</td>
		<td>
		     <input name="userInfoDO.name" type="text" value="<s:property value='userInfoDO.name'/>">
		</td>
		
		<td align="center">
			<input type="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th>选择</th>
		<th align="center" >会员账号</th>
		<th align="center"  width="80">客户类别</th>
		<th align="center" >真实姓名</th>
		<th align="center" >电子邮箱</th>
		<th align="center" >手机号码</th>
		<th align="center"  width="80" >账号状态</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	     <td>
	      <s:if test="status==1">
		           
	     <input type="button"  value="选择" onClick="selectOneAccount(<s:property value='loginId'/>,'<s:property value="loginAccount"/>','<s:property value="name"/>','<s:property value="customerTypeId"/>','<s:property value="customerTypeName"/>')" />
	       </s:if>
	     </td>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		     <s:property value="customerTypeName"/>  
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="email"/>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
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

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'accountInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>

