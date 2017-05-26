<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<title>客户积分规则管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
     $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         //querySonCustomerType(parentId,sonId);
	      }
	   });
    /** 删除客户积分规则信息 **/
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
                          document.scoreRuleForm.action="/growing/scoreRule_operateDeleteAll.action ";
                          document.scoreRuleForm.submit();
                 }
    }
    /**  进入新增客户积分规则信息页面  **/
    function gotoAdd(){
         document.scoreRuleForm.action="/growing/scoreRule_operateAdd.action";
         document.scoreRuleForm.submit();
    }
    
    /** 进入修改客户积分规则信息页面 **/
    function editScoreRule(id){
         location.href="/growing/scoreRule_operateEdit.action?scoreRuleId="+id;
    }
     /** 单条删除客户积分规则信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/growing/scoreRule_operateDelete.action?scoreRuleId="+id;
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
<body>
<!-- 标题 -->
<s:set name="parent_name" value="'客户成长'" scope="request"/>
<s:set name="name" value="'积分规则'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form  name="scoreRuleForm"  action="/growing/scoreRule_queryPageList.action"  onsubmit=" return checkAllTextValid(this)" method="post">
<!-- 按钮条 -->
 
<table width="98%" align="center" class="content_table" height="50" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td  align="right" colspan="3">
            <INPUT class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
			<!-- <input class="delBtn" type="button" value=""  onclick="deleteSelected('ruleIds');"> -->
		</td>
	</tr>
	<!-- 
	<tr width="60%">
	    <td>积分规则简述称：
		     <input name="discribe" type="text" value="<s:property value='scoreRule.discribe'/>">
		</td>
		<td align="left" width="30%">
			客户类别：
         	<s:select name="clientType"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"></s:select>
		    <select id="customer" name="customer_son_id"  style="display:none;"></select>
		   <input type="hidden"  id="sonCustomerId" value="<s:property value='scoreRule.customer_son_id'/>">
		     <input type="hidden"  id="parentId"  value="<s:property value='scoreRule.clientType'/>"/>
		</td>
		<td align="center">
			<INPUT TYPE="submit" class="queryBtn" value=" ">
		</td>
	</tr>
	 -->
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'ruleIds')">
		</th>
		<th  width="5%">积分规则编号</th>
		<!-- <th  width="10%">客户类别</th> -->
		<th  width="15%">积分规则名称</th>
		<th  width="15%">积分表达式</th>
		<th  width="27%">积分说明</th>
		<th  width="8%">每日上限</th>
		<!-- <th  width="10%">获得积分值</th> -->
		<th  width="10%">操作</th>
	</tr>
	<s:iterator id="custiterator"  value="page.dataList">
	<tr>
	    <td  width="5%">
		    <input type="checkbox"  name="ruleIds"  value='<s:property value="n_scoreRuleId"/>'>
		</td>
		<td >
		     <s:property value="code"/>
		</td>
		<!-- 
		<td >
		     <s:property value="customerName"/>
		</td>
		 -->
		<td >
		   <s:property value="discribe"/>
		</td>
		<td  >
		     <s:property value="scoreExpress"/>
		</td>
		<td>
		     <s:property value="remark"/>
		</td>
		<td >
		     <s:property value="dayMax"/>
		</td>
		<!-- 
		<td >
		     <s:property value="scoreValue"/>
		</td>
		 -->
		<td>
		 <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editScoreRule(<s:property value="n_scoreRuleId"/>)"  />&nbsp;&nbsp; 
		 <!-- <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_scoreRuleId"/>)" /> -->
		</td>
	</tr>
	</s:iterator>
</table>
<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'scoreRuleForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
	</s:form>
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</body>
</html>

