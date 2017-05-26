<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>

<html>
	<head>
		<title>客户等级管理</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
 		 <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
	    //设置二级菜单查询默认值
	    /*
	   $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });
	    */
    /** 删除客户等级信息  **/
   /* function deleteSelected(id){
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
                          document.userLevelForm.action="/growing/userLevel_operateDeleteAll.action ";
                          document.userLevelForm.submit();
                 }
    }*/
    /**  进入新增客户等级信息页面  **/
    function gotoAdd(){
         document.userLevelForm.action="/growing/userLevel_operateAdd.action ";
         document.userLevelForm.submit();
    }
    
    /** 进入修改客户等级信息页面  **/
    function editUserLevel(id){
         location.href="/growing/userLevel_operateEdit.action?userLevelId="+id;
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id,expend_min){
    if(expend_min==0)
    {
     alert("初始化等级,请不要删除");
    }else
    {
    var rows =0;	
		$.ajax({
			async:false,
			url:"userLevel_ajaxOperateLevel.action",
			type:"POST",
			data:"userLevelId=" + id,
			dataType:"json",
			success:function(json){
 						rows = json;
 				}
 			});
 			if(rows==0){
 				if (confirm('是否确认删除?')==true) 
 				  {
      				document.userLevelForm.action="/growing/userLevel_operateDelete.action?userLevelId="+id;
      				document.userLevelForm.submit();         
    			   }
 			}else{
 	 			alert("等级已经被使用,请不要删除");
 	 		}
       }
    }
     /**通过customer类别id 查询子客户类别 **/
        function querySonCustomerType(id,value){
               if(id!=""){
                 $.ajax({
 				async:false,
 				url:"userLevel_ajaxOperateCustomerType.action",
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
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户成长'" scope="request" />
		<s:set name="name" value="'客户级别'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="userLevelForm"   onsubmit=" return checkAllTextValid(this)"
			action="/growing/userLevel_queryPageList.action" method="post">
				<s:token/>
			<!-- 查询条件 -->
		    <!-- 
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
			 
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<input class="addBtn" TYPE="button" value="" onclick="gotoAdd();">&nbsp;&nbsp;
					
					</td>
				</tr>
				
				<tr width="60%">
					<td >
						客户等级名称：
						<input name="level_name" type="text"  
							value="<s:property value='userLevel.level_name'/>">
					</td >
					<td align="left" width="30%">
						客户类别：
			         	<s:select name="n_customer_type_id"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"  ></s:select>
					    <select id="customer" name="customer_son_id"  style="display:none;"></select>
					    <input type="hidden"  id="sonCustomerId" value="<s:property value='userLevel.customer_son_id'/>">
					    <input type="hidden"  id="parentId"  value="<s:property value='userLevel.n_customer_type_id'/>"/>
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			-->
			<div>&nbsp;</div>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th  width="7%">
						级别编号
					</th>
					<th  width="8%">
						级别名称
					</th>
					<!--  
					<th width="8%">
						客户类别
					</th>
					
					<th  width="7%">
						消费额最小值
					</th>
					<th  width="7%">
						消费额最大值
					</th>
					-->
					<th  width="7%">
						最小积分
					</th>
					<th  width="7%">
						最大积分
					</th>
					
					<!-- 
					<th  width="8%">
						年度最低消费额度(元)
					</th>
						<th  width="20%">
						等级享受服务描述
					</th>
					<th  width="10%">
						有效期(月)
					</th>
					<th  width="7%">
						操作
					</th>
					 -->
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td>
							<s:property value="code" />
						</td>
						<td>
							<s:property value="level_name" />
						</td>
						<!-- 
						<td>
							<s:property value="customerName" />
						</td>
						
						<td>
						     <s:property value="%{formatDouble(expend_min)}"/>
						</td>
						<td>
						 <s:property value="%{formatDouble(expend_max)}"/>
						</td>
						 -->
						<td>
							<s:property value="score_min"/>
						</td>
						<td>
							<s:property value="score_max"/>
						</td>
						
						<!-- 
						<td>
							 <s:property value="%{formatDouble(yearMin)}"/>
						</td>
						<td>
							<s:property value="remark" />
						</td>
						<td>
							<s:property value="valid" />
						</td>
						<td>
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editUserLevel(<s:property value="n_level_id"/>)" />&nbsp;&nbsp;
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_level_id"/>, <s:property value="%{formatDouble(expend_min)}"/>)" />
						</td>
						 -->
					</tr>
				</s:iterator>
			</table>
			<!-- 
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			 -->
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

