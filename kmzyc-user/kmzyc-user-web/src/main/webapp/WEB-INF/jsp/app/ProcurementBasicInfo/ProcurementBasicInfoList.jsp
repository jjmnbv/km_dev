<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>采购商审核</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">

    /**  进入详情个人信息页面  **/
    function gotoDetail(id){
      location.href="/userInfo/commercialTenantBasicInfo_procurementDetail.action?n_CommercialTenantId="+id;
    } 
 
   /**  进入修改个人信息页面  **/
    function gotoUpdate(id){
      location.href="/userInfo/commercialTenantBasicInfo_procurementUpdate.action?n_CommercialTenantId="+id;
    }

    function gotoVerify(id)
    {
        location.href="/userInfo/commercialTenantBasicInfo_procurementVerify.action?n_CommercialTenantId="+id;
     }
    
   
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

      
      /**通过customer类别id 查询子客户类别 **/
      function querySonCustomerRank(id,value){
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
      
 
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'采购商管理'" scope="request" />
<s:set name="name" value="'采购商审核列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="ProcurementBasicInfoForm" action="/userInfo/commercialTenantBasicInfo_procurementPageList.action" 
    onsubmit=" return checkAllTextValid(this)"  method="post">
    <s:token/>
<!-- 查询条件区域 -->
<table width="98%" height="130" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
 		<td colspan="8">
 			 
		<!--	<input class="delBtn" type="button" value=""  onclick="deleteSelected('n_CommercialTenantIds');"> -->
 		</td>
 	</tr>
 	
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
		
	</tr>
	
		<tr>
		<td align="right">工商注册号：</td>
		<td>
		     <input name="commercialTenantBasicInfo.businessLicenceRegister" type="text" value="<s:property value='commercialTenantBasicInfo.businessLicenceRegister'/>">
		</td>
		<td align="right">申请状态：</td>
		<td>
		  		<select name="commercialTenantBasicInfo.status">
							
									<option value="" <s:if test='commercialTenantBasicInfo.status==""'>selected="selected"</s:if>>
										请选择
									</option>
									<option value="1" <s:if test='commercialTenantBasicInfo.status=="1"'>selected="selected"</s:if>>
										 撤回申请
									</option>
							
									<option value="2" <s:if test='commercialTenantBasicInfo.status=="2"'>selected="selected"</s:if>>
										 提交申请
									</option>
							
							
									<option value="3" <s:if test='commercialTenantBasicInfo.status=="3"'>selected="selected"</s:if>>
								   审核通过
									</option>
							
								<option value="4" <s:if test='commercialTenantBasicInfo.status=="4"'>selected="selected"</s:if>>
								   审核不通过
									</option>
								 
							
							</select>
		</td>
	 
		
	</tr>
	<tr>
		
		
		<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	  <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'n_CommercialTenantIds')">
		</th> 
		<th align="center" >会员账号</th>
		<th align="center" >联系人姓名</th>
		<th align="center" >手机号码</th>
			<th align="center" >信用值</th>
		<th align="center" >公司名称</th>
		<th align="center" >工商注册号</th>
		<th align="center" >执照有效起始日期</th>
		<th align="center" >执照有效结束日期</th>
		<th align="center" >成立日期</th>
        <th align="center" >申请状态</th>
        <th align="center" >审核备注</th>
        
		<th align="center" >操作</th>
	</tr>
	<s:iterator id="accountiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="n_CommercialTenantIds"  value='<s:property value="n_CommercialTenantId"/>' />
		</td>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		   <s:property value="contactsName"/>
		</td>
	 
		<td align="center">
		     <s:property value="mobile"/>
		</td>
			<td align="center">
		     <s:property value="creditRating"/>
		</td>
		<td align="center">
		     <s:property value="corporateName"/>
		</td>	
		<td align="center">
		     <s:property value="businessLicenceRegister"/>
		</td>
		<td align="center">
		     	<s:date   name="blinceStartdate"  format="yyyy-MM-dd" />
		</td>
		
		<td align="center">
		     	<s:date   name="blinceEnddate"  format="yyyy-MM-dd" />
		</td>
		<td align="center">
		<s:date   name="d_FoundDate"  format="yyyy-MM-dd" />
		</td>


	<td align="center">
	 
		 	<s:if test="status==1">撤回申请</s:if>
		 	<s:if test="status==2">提交申请</s:if>
	        <s:if test="status==3">审核通过</s:if>
	        <s:if test="status==4">审核不通过</s:if>
		</td>
		
			<td align="center">	
			<s:if test="status!=3"><s:property value="description"/></s:if>
		     
		</td>
		<td>
		     <img title="详情" style="cursor: pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="n_CommercialTenantId"/>)"/>
		  
			
				<s:if test="status==2">
			 <img title="审核" style="cursor: pointer;" src="/etc/images/u177_normal.png"  onclick="gotoVerify(<s:property value="n_CommercialTenantId"/>)" />
			 		     <img title="修改" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoUpdate(<s:property value="n_CommercialTenantId"/>)" />
			 
			</s:if>
		</td>
	</tr>
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'ProcurementBasicInfoForm'"  scope="request"></s:set>
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

