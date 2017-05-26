<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    var ed = "";
	//初始化页面加载
	$(function(){
		ed=$("#edit").val();
		//判断处理页面form提交路径
		if(ed=="edit"){
			$("#accountInfoForm").attr("action","/accounts/accountInfo_pageEditList.action");
		}
	});

    /** 删除账户信息  **/
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
                          document.accountInfoForm.action="/accounts/accountInfo_delete.action";
                          document.accountInfoForm.submit();
                 }
    }
    /**  进入新增账户信息页面  **/
    function gotoAdd(){
         document.accountInfoForm.action="/accounts/accountInfo_preAdd.action ";
         document.accountInfoForm.submit();
    }

    /**  进入详情个人信息页面  **/
    function gotoDetail(id){
      if(ed=="edit"){
    	  location.href="/accounts/accountInfo_preDetail.action?n_AccountId="+id+"&&edit="+ed;;
      } else {
    	  location.href="/accounts/accountInfo_preDetail.action?n_AccountId="+id;
      }
      
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/accounts/accountInfo_delete.action?n_AccountIds="+id;
         }
    }
   /**  进入修改个人信息页面  **/
    function gotoUpdate(id){
	   var ed=$("#edit").val();
	   if(ed=="edit"){
		   location.href="/accounts/accountInfo_preUpdate.action?n_AccountId="+id+"&&edit="+ed;
	   } else {
		   location.href="/accounts/accountInfo_preUpdate.action?n_AccountId="+id; 
	   }
      
    }
	
  
      /**通过customer类别id 查询子客户类别 **/
      function querySonCustomerType(id,value){
             if(id!=""){
               $.ajax({
				async:false,
				url:"accountInfo_ajaxOperateCustomerType.action",
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
      $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });
	   
	  function queryUserInfo(id,type){
	  //  alert("aaaa"+type);
	   	if(type=='1'){
	   	   
	   	     dialog("选择会员账号","iframe:/userInfo/personalBasicInfo_prePersonDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='2'){
	   	  
	   	    dialog("选择会员账号","iframe:/personalbasic/personalbasic_preExpertDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='4'){
	   	  
	   	    dialog("选择会员账号","iframe:/userInfo/commercialTenantBasicInfo_preBusinessDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   }
	  
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'账户管理'" scope="request" />

<s:if test="edit=='edit'">
	<s:set name="name" value="'账户编辑'" scope="request" />
</s:if> <s:else>
	<s:set name="name" value="'账户信息'" scope="request" />
</s:else>

<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form id="accountInfoForm"  name="accountInfoForm" action="/accounts/accountInfo_pageList.action" onsubmit=" return checkAllTextValid(this)" method="post">
<input type="hidden" id="edit" name="edit" value="<s:property value="edit"/>">
<!-- 查询条件区域 -->
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr> 
	    <td colspan="8">
          <!--  <input class="addBtn" type="button" value="" onclick="gotoAdd();"> -->
			<%-- <input class="delBtn" type="button" value=""  onclick="deleteSelected('n_AccountIds');">--%>
		</td>
	</tr>
	
	<tr>
		<td align="right">账户号：</td>
		<td>
		     <input name="accountInfo.accountLogin" type="text" value="<s:property value='accountInfo.accountLogin'/>">
		</td>
		<td align="right">账户真实姓名：</td>
		<td>
		     <input name="accountInfo.name" type="text" value="<s:property value='accountInfo.name'/>">
		</td>
		<td align="right">证件号码：</td>
		<td>
		     <input name="accountInfo.acconutId" type="text" value="<s:property value='accountInfo.acconutId'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="accountInfo.mobile" type="text" value="<s:property value='accountInfo.mobile'/>">
		</td>
		
		
	</tr>
		<tr>
		<td align="right">客户类别：</td>
		<td>
		
         	<s:select name="n_CustomerTypeId"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"></s:select>
		    <select id="customer" name="customer_son_id"  style="display:none;"></select>
		    <input type="hidden"  id="sonCustomerId" value="<s:property value='accountInfo.customer_son_id'/>">
		    <input type="hidden"  id="parentId"  value="<s:property value='accountInfo.n_CustomerTypeId'/>"/>
	
		   </td>
		<%--删除账户信息  <td align="right">邮件地址：</td>
		<td>
		    <input name="accountInfo.email" type="text" value="<s:property value='accountInfo.email'/>">
		</td> --%>
		<td align="right">创建日期从：</td>
		<td>
		<input type="text" id="d523" readonly class="Wdate"  value ="<s:date name = 'accountInfo.d_CreateDate' format='yyyy-MM-dd HH:mm:ss' />"    name="accountInfo.d_CreateDate"  onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />至：<input type="text" id="d524" readonly class="Wdate"  value ="<s:date name = 'accountInfo.endDate' format='yyyy-MM-dd HH:mm:ss' />"    name="accountInfo.endDate"  onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		</td>
		<td align="center" colspan="8">
			<INPUT TYPE="submit"  class="queryBtn" value="">
		</td>
	</tr>

</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <%--<th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_AccountIds')">
		</th> --%>
		<th align="center" >账户号</th>
		<%--<th align="center" >登录账号</th> --%>
		<th align="center" >客户类别</th>
		<th align="center" >账户真实姓名</th>
		<th align="center" >证件号码</th>
		<th align="center" >手机号码</th>
	<!--删除 	<th align="center" >邮箱地址</th> -->
		<th align="center" >账户创建日期</th>
		<th align="center" >账户金额</th>
		<th align="center" >账户冻结金额</th>
		<th align="center" >账户可用金额</th>
		<th align="center" >操作</th>
		
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	   <%--<td align="center" width="5%">
		    <input type="checkbox"  name="n_AccountIds"  value='<s:property value="n_AccountId"/>' />
		</td> --%>
		<td align="center">
		    
		    
		  <a href="#" onClick="queryUserInfo(<s:property value="n_LoginId"/>,'<s:property value="n_CustomerTypeId"/>');"><s:property value="accountLogin"/></a>
		</td>
		<%--<td align="center">
		   <s:property value="loginAccount"/>
		</td>--%>
		<td align="center">
		   <s:property value="customerName"/>
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="acconutId"/>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<%--删除 <td align="center">
		     <s:property value="email"/>
		</td> --%>
		<td align="center">
		     <s:date   name="d_CreateDate"  format="yy-MM-dd HH:mm:ss"/>
		</td>
		<td align="center">
		     <s:property value="%{formatDouble(n_AccountAmount)}"/>
		</td>
		<td align="center">
		    <s:property value="%{formatDouble(amountFrozen)}"/>
		</td>
		<td align="center">
		      <s:property value="%{formatDouble(amountAvlibal)}"/>
		</td>
		<td width="70px;">
			 <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="n_AccountId"/>)"/>
		     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoUpdate(<s:property value="n_AccountId"/>)" />
		    <%-- <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_AccountId"/>)"/>--%> 
		</td>
		
	</tr>
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'accountInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
</body>
</html>

