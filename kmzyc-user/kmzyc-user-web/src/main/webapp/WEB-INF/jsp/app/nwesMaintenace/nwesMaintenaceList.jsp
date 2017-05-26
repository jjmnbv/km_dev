<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>维护信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
	    //设置二级菜单查询默认值
	  /* $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });*/
    /** 删除维护信息  **/
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
                          document.nwesMaintenaceForm.action="/nwesMaintenace/maintenace_detele.action ";
                          document.nwesMaintenaceForm.submit();
                 }
    }
    /**  进入新增客维护信息页面  **/
    function gotoAdd(){
         document.nwesMaintenaceForm.action="/nwesMaintenace/maintenace_preAdd.action ";
         document.nwesMaintenaceForm.submit();
    }
    
    /** 进入修改维护信息页面  **/
    function edit(id){
         location.href="/nwesMaintenace/maintenace_preUpdate.action?maintenace_Id="+id;
    }
       /** 进入维护记录信息页面  **/
    function gotoDetail(id){
         location.href="/nwesMaintenace/maintenace_preDetail.action?maintenace_Id="+id;
    }
    /** 单条删除维护信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
          document.nwesMaintenaceForm.action="/nwesMaintenace/maintenace_detele.action?maintenaceIds="+id;
          document.nwesMaintenaceForm.submit();
         }
    }
     /**通过customer类别id 查询子客户类别 **/
     /*   function querySonCustomerType(id,value){
               if(id!=""){
                 $.ajax({
 				async:false,
 				url:"maintenace_ajaxOperateCustomerType.action",
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
        }*/
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户信息'" scope="request" />
		<s:set name="name" value="'维护记录'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
			<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="nwesMaintenaceForm" onsubmit=" return checkAllTextValid(this)"
			action="/nwesMaintenace/maintenace_pageList.action" method="post">
			<s:token/>
			<!-- 查询条件 -->
		    <table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				</tr>
				<tr width="60%">
					<td >
						反馈人姓名：
						<input name="loginName" type="text"  
							value="<s:property value='newsCustomerSurvey.loginName'/>">
					</td >
					<td align="right"  >
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;
                        <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onClick="deleteSelected('maintenaceIds');">
					</td>
                   
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='allbox'  onclick="checkAll(this,'maintenaceIds')">
					</th>
					<th>
						维护人
					</th>
					<th>
						反馈人姓名
					</th>
					<th>
						反馈问题
					</th>
					<th>
						维护信息
					</th>
					<th>
						维护时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="maintenaceIds"
								value='<s:property value="maintenaceId"/>'>
						</td>
						<td>
							<s:property value="userName" />
						</td>
						
						<td>
							<s:property value="loginName" />
						</td>
						<td>
						<s:if test="question.length()>18">
   						 <s:property value='question.substring(0,18)'/>...
 						</s:if>   
						 <s:else>      
     					<s:property value='question'/>
 						</s:else>
						</td>
						<td >
						<s:if test="maintenaceSchedule.length()>18">
   						 <s:property value='maintenaceSchedule.substring(0,18)'/>...
 						</s:if>   
						 <s:else>      
     					<s:property value='maintenaceSchedule'/>
 						</s:else>
						
						</td>
						<td>
							<s:date name="maintenaceDate" format="yyyy-MM-dd"/>
						</td>
						<td>
						     <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="maintenaceId"/>)" />
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="edit(<s:property value="maintenaceId"/>)" />
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="maintenaceId"/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'nwesMaintenaceForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
	<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

