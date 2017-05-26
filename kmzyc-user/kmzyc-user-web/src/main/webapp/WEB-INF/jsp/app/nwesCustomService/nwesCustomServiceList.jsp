<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>服务信息管理</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
	    //设置二级菜单查询默认值
	   $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });
    /** 删除调查服务信息  **/
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
                          document.nwesCustomServiceForm.action="/nwesCustomService/customService_detele.action ";
                          document.nwesCustomServiceForm.submit();
                 }
    }
    /**  进入服务信息页面  **/
    function gotoAdd(){
         document.nwesCustomServiceForm.action="/nwesCustomService/customService_preAdd.action ";
         document.nwesCustomServiceForm.submit();
    }
    
    /** 回复服务信息页面  **/
    function edit(id){
         location.href="/nwesCustomService/customService_preUpdate.action?customService_Id="+id;
    }
    
    /** 单条删除服务信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
         document.nwesCustomServiceForm.action="/nwesCustomService/customService_detele.action?customServiceIds="+id;
         document.nwesCustomServiceForm.submit();
          
         }
    }
    /**  进入详情个人信息页面  **/
    function gotoDetail(id){
      location.href="/nwesCustomService/customService_preDetail.action?customService_Id="+id;
    }
    
     /**通过customer类别id 查询子客户类别 **/
        function querySonCustomerType(id,value){
               if(id!=""){
                 $.ajax({
 				async:false,
 				url:"customService_ajaxOperateCustomerType.action",
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
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户信息'" scope="request" />
		<s:set name="name" value="'服务信息'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
			<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="nwesCustomServiceForm" onsubmit=" return checkAllTextValid(this)"
			action="/nwesCustomService/customService_pageList.action" method="post">
			<!-- 查询条件 -->
		  <s:token/>
		    <table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr>
					<td align="left">
						客户姓名：
						<input name="loginName" type="text"  
							value="<s:property value='nwesCustomService.loginName'/>">
					</td >
					<td align="left" >
						客户类别：
			         	<s:select name="customerTypeId"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"></s:select>
					    <select id="customer" name="customer_son_id"  style="display:none;"></select>
					    <input type="hidden"  id="sonCustomerId" value="<s:property value='nwesCustomService.customer_son_id'/>">
					    <input type="hidden"  id="parentId"  value="<s:property value='nwesCustomService.customerTypeId'/>"/>
					</td>
					<td align="left">
						服务方式：
			         	<SELECT name="nwesCustomService.customServiceType">
			         	<option value="">全部</option>
			         	<option <s:if test="nwesCustomService.customServiceType==0">selected="selected"</s:if> value='0'>在线客服</option>
			         	<option <s:if test="nwesCustomService.customServiceType==1">selected="selected"</s:if>  value='1'>电话客服</option>
			         	<option <s:if test="nwesCustomService.customServiceType==2">selected="selected"</s:if>  value='2'>留言</option>
			         	</SELECT>
					</td>
					<td align="left">
						服务类别：
			         	<SELECT name="nwesCustomService.customServiceMode">
			         	<option value="">全部</option>
			         	<option <s:if test="nwesCustomService.customServiceMode==0">selected="selected"</s:if> value='0'>咨询</option>
			         	<option <s:if test="nwesCustomService.customServiceMode==1">selected="selected"</s:if>  value='1'>售后</option>
			         	<option <s:if test="nwesCustomService.customServiceMode==2">selected="selected"</s:if>  value='2'>投诉</option>
			         	</SELECT>
					</td>
					<td align="right"  >
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;
                        <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th>
						回复人
					</th>
					<th>
						客户姓名
					</th>
					<th>
						客户类别
					</th>
					<th>
						服务方式
					</th>
					<th>
						服务类别
					</th>
					<th>
						咨询内容
					</th>
					<th>
						是否已回复
					</th>
					<th>
						咨询时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						
						<td>
							<s:property value="userName" />
						</td>
						<td>
							<s:property value="loginName" />
						</td>
						<td>
				        <s:property value='customerName'/>
						</td>
						
						<td>					
						<s:if test="customServiceType==0">
						在线客服
						</s:if>
						<s:if test="customServiceType==1">
						电话客服
						</s:if>
						
						<s:if test="customServiceType==2">
						留言
						</s:if>
						</td>
						<td>
						<s:if test="customServiceMode==0">
						咨询
						</s:if>
						<s:if test="customServiceMode==1">
						售后
						</s:if>
						
						<s:if test="customServiceMode==2">
					          投诉
						</s:if>
						</td>
						<td>
						<s:if test="content.length()>15">
   						 <s:property value='content.substring(0,15)'/>...
 						</s:if>   
						 <s:else>      
     					<s:property value='content'/>
 						</s:else>
						
						</td>
						<td>
						<s:if test="replyStatus==0">
						未回复
						</s:if>
						<s:if test="replyStatus==1">
						已回复
						</s:if>
							
						</td>
						<td>
							<s:date name="customServiceDate" format="yyyy-MM-dd"/>
						</td>
						
						<td>
						<s:if test="replyStatus==0">
						  <img title="回复" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="edit(<s:property value="customServiceId"/>)" />
						</s:if>
						<s:else>
						  <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="customServiceId"/>)" />
						  <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="customServiceId"/>)" />
						
						</s:else>

						</td>
					</tr>
				</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'nwesCustomServiceForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		
	</body>
	  <!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
</html>

