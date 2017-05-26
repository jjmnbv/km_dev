<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /** 删除商户信息  **/ 
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
                          document.commercialTenantBasicInfoForm.action="/userInfo/commercialTenantBasicInfo_delete.action";
                          document.commercialTenantBasicInfoForm.submit();
                 }
    }
    /**  进入新增商户信息页面  **/
    function gotoAdd(){
         document.commercialTenantBasicInfoForm.action="/userInfo/commercialTenantBasicInfo_preAdd.action ";
         document.commercialTenantBasicInfoForm.submit();
    }
    
    /**  进入详情个人信息页面  **/
    function gotoDetail(id){
      location.href="/userInfo/commercialTenantBasicInfo_preDetail.action?n_CommercialTenantId="+id;
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
         document.commercialTenantBasicInfoForm.action="/userInfo/commercialTenantBasicInfo_delete.action?n_CommercialTenantIds="+id;
         document.commercialTenantBasicInfoForm.submit();
         }
    }
   /**  进入修改个人信息页面  **/
    function gotoUpdate(id){
      location.href="/userInfo/commercialTenantBasicInfo_preUpdate.action?n_CommercialTenantId="+id;
    }
    
   /* 
      function querySonCustomerLevel(id,value){
    	  if(id!=""){
              $.ajax({
				async:false,
				url:"commercialTenantBasicInfo_ajaxOperateCustomerUserLevel.action",
				type:"POST",
				data:"customerTypeId=" + id,
				dataType:"json",
				success:function(data){
					if((data!=null||data!="")&&data.length>0){
					   $("#levelList option").remove();
					   $("#levelList").show();
					   $("#levelList").append("<option value=''>"+'全部'+"</option>");
	 					for(var i=0;i<data.length;i++){
			 					if(value!=""&&value==data[i].n_level_id){
			 					 $("#levelList").append("<option value='"+data[i].n_level_id+"' selected>"+data[i].level_name+"</option>");
			 					}else{
			 					 $("#levelList").append("<option value='"+data[i].n_level_id+"'>"+data[i].level_name+"</option>");
			 					}
	 					}
	 					  $("#levelSelect").show();
				    }else{
				        $("#levelList option").remove();
				        $("#levelList").hide();
				          $("#levelSelect").hide();
				    }
			   }
			});
       }else{
            $("#levelList option").remove();
            $("#levelList").hide();
            $("#levelSelect").hide();
       }
      }
 */
      
      /**通过customer类别id 查询子客户类别 **/
   /*    function querySonCustomerRank(id,value){
             if(id!=""){
               $.ajax({
				async:false,
				url:"commercialTenantBasicInfo_ajaxOperateCustomerRank.action",
				type:"POST",
				data:"customerTypeId=" + id,
				dataType:"json",
				success:function(data){
					if((data!=null||data!="")&&data.length>0){
					   $("#rankList option").remove();
					     $("#rankList").show();
					     $("#rankList").append("<option value=''>"+'全部'+"</option>");
	 					for(var i=0;i<data.length;i++){
			 					if(value!=""&&value==data[i].rankId){
			 					 $("#rankList").append("<option value='"+data[i].rankId+"' selected>"+data[i].rankName+"</option>");
			 					}else{
			 					 $("#rankList").append("<option value='"+data[i].rankId+"'>"+data[i].rankName+"</option>");
			 					}
	 					}
	 					$("#rankSelect").show();
	 					$("#rankSelect").show();
				    }else{
				        $("#rankList option").remove();
				        $("#rankList").hide();
				        $("#rankSelect").hide();
				    }
			   }
			});
        }else{
             $("#rankList option").remove();
			   $("#rankList").hide();
			   $("#rankSelect").hide();
        }


            
             
      }
      
      $(document).ready(function(){
	      var nCustomerTypeId  =  $("#n_CustomerTypeId").val();
	      var  nLevelId  =  $("#n_LevelId").val();
	      var  nRankId  =  $("#n_RankId").val();
	      if(nCustomerTypeId!=null||nCustomerTypeId!=""){
	    	  querySonCustomerRank(nCustomerTypeId,nRankId);
	    	  querySonCustomerLevel(nCustomerTypeId,nLevelId);
	      }
	   }); */
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'客户资料'" scope="request" />
<s:set name="name" value="'商户'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="commercialTenantBasicInfoForm" action="/userInfo/commercialTenantBasicInfo_pageList.action" 
    onsubmit=" return checkAllTextValid(this)"  method="post">
    <s:token/>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<!-- <tr>
 		<td colspan="8">
 			<input class="addBtn" type="button" value="" onclick="gotoAdd();">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('n_CommercialTenantIds');">
 		</td>
 	</tr> -->
 	
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="commercialTenantBasicInfo.loginAccount" type="text" value="<s:property value='commercialTenantBasicInfo.loginAccount'/>">
		</td>
		<td align="right">联系人姓名：</td>
		<td>
		     <input name="commercialTenantBasicInfo.contactsName" type="text" value="<s:property value='commercialTenantBasicInfo.contactsName'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="commercialTenantBasicInfo.mobile" type="text" value="<s:property value='commercialTenantBasicInfo.mobile'/>">
		</td>
		<td align="right">公司名称：</td>
		<td>
		     <input name="commercialTenantBasicInfo.corporateName" type="text" value="<s:property value='commercialTenantBasicInfo.corporateName'/>">
		</td>
		<td align="right">客户类别：</td>
		<td>
		     <select name="commercialTenantBasicInfo.n_CustomerTypeId"  style="width:150px;">
		   		<option value="">全部</option>
		     <s:iterator value="customerList" id="customerList">
		     	<s:if test="customerTypeId==commercialTenantBasicInfo.n_CustomerTypeId">
					<option selected="selected" value="<s:property value='customerTypeId'/>"><s:property value="name"/></option>
				</s:if>
				<s:else>
					<option value="<s:property value='customerTypeId'/>"><s:property value="name"/></option>
				</s:else>
			</s:iterator>
			</select>
		</td>
		<%-- <td align="right"  id="levelSelect"   style="display:none;">客户级别：</td>
		<td>
		     <select name="commercialTenantBasicInfo.n_LevelId" id="levelList"  style="display:none;width:150px;">
				<option></option>
				<input type="hidden"  id="n_LevelId"  value="<s:property value='commercialTenantBasicInfo.n_LevelId'/>"/>
				<input type="hidden"  id="n_CustomerTypeId"  value="<s:property value='commercialTenantBasicInfo.n_CustomerTypeId'/>"/>
			</select>
		</td>
		<td align="right"  id="rankSelect"  style="display:none;">客户头衔：</td>
		<td>
			<select name="commercialTenantBasicInfo.n_RankId" id="rankList"  style="display:none;width:150px;">
				<option></option>
				<input type="hidden"  id="n_RankId"  value="<s:property value='commercialTenantBasicInfo.n_RankId'/>"/>
			</select>
		</td> --%>
		<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	 <!--  <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_CommercialTenantIds')">
		</th>  -->
		<th align="center" >会员账号</th>
		<th align="center" >联系人姓名</th>
		<!-- <th align="center" >联系人所在部门</th> -->
		<th align="center" >手机号码</th>
		<th align="center" >公司名称</th>
		<th align="center" >客户类别</th>
		<!-- <th align="center" >客户级别</th> -->
		<!-- <th align="center" >客户头衔</th> -->
		<th align="center" >信用等级</th>
		<th align="center" >可用积分</th>
		<th align="center" >总积分</th>
		<th align="center" >成立日期</th>
		<th align="center" >商户状态</th>
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	   <!--  <td align="center" width="5%">
		    <input type="checkbox"  name="n_CommercialTenantIds"  value='<s:property value="n_CommercialTenantId"/>' />
		</td> -->
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		   <s:property value="contactsName"/>
		</td>
	<%-- 	<td align="center">
		     <s:property value="contactsDepartment"/>
		</td> --%>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<td align="center">
		     <s:property value="corporateName"/>
		</td>
		<td align="center">
		     <s:property value="customerName"/>
		</td>
		<%-- <td align="center">
		     <s:property value="levelName"/>
		</td> --%>
		<%-- <td align="center">
		     <s:property value="rankName"/>
		</td> --%>

		<td align="center">
		     <s:property value="creditRating"/>
		</td>
		
		<td align="center">
		     <s:property value="n_AvailableIntegral"/>
		</td>
	    <td align="center">
		     <s:property value="n_TotalIntegral"/>
		</td>
		<td align="center">
		<s:date   name="d_FoundDate"  format="yyyy-MM-dd"/>
		     <s:property value="address"/>
		</td>
		<td>
		
		<s:if test="#accountiterator.isValid==0">禁用</s:if>
		<s:if test="#accountiterator.isValid==1">可用</s:if>
		</td>
		<td>
		     <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="n_CommercialTenantId"/>)"/>
		    <s:if test="#accountiterator.n_CustomerTypeId==4">
		   <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoUpdate(<s:property value="n_CommercialTenantId"/>)" />
		       <!--  <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_CommercialTenantId"/>)"/>
		        -->  
		     </s:if>
			
		</td>
	</tr>
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'commercialTenantBasicInfoForm'"  scope="request"></s:set>
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

