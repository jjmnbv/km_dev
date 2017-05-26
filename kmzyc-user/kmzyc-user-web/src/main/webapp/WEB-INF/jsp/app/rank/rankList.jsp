<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>客户头衔信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
 		 <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
		 //设置二级菜单查询默认值
	   $(document).ready(function(){
	      var sonId  =  $("#sonCustomerId").val();
	       var  parentId  =  $("#parentId").val();
	      if(sonId!=null||sonId!=""){
	         querySonCustomerType(parentId,sonId);
	      }
	   });
    /** 删除客户头衔信息  **/
    function deleteByKey(id,scoreMin){
    if(scoreMin==0)
    {
     alert("初始化头衔,请不要删除");
    }else
    {
    var rows =0;	
		$.ajax({
			async:false,
			url:"rank_ajaxcountRank.action",
			type:"POST",
			data:"rank_Id=" + id,
			dataType:"json",
			success:function(json){
 						rows = json;
 				}
 			});
 			if(rows==0){
 				if (confirm('是否确认删除?')==true) 
 				  {
      				document.rankForm.action="/rank/rank_operateDetele.action?rankIds="+id;
      				document.rankForm.submit();         
    			   }
 			}else{
 	 			alert("头衔已经被使用,请不要删除");
 	 		}
      }
    }
    /**  进入新增客户头衔信息页面  **/
    function gotoAdd(){
         document.rankForm.action="/rank/rank_operateAdd.action ";
         document.rankForm.submit();
    }
    /**  进入修改客户头衔信息页面  **/
    function gotoUpdate(id){
    location.href="/rank/rank_operateEdit.action?n_rId="+id;
        	
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
         /**通过customer类别id 查询子客户类别 **/
    function querySonCustomerType(id,value){
               if(id!=""){
                 $.ajax({
 				async:false,
 				url:"rank_ajaxOperateCustomerType.action",
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
		<s:set name="parent_name" value="'客户成长'" scope="request" />
		<s:set name="name" value="'客户头衔'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="rankForm"   onsubmit=" return checkAllTextValid(this)"
			action="/rank/rank_queryPageList.action" method="post">
			<s:token/>
			<!-- 查询条件 -->
		    <table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr width="60%">
					<td >
						头衔名称：
						<input name="rank.rankName" class="input_stype" type="text"
							value="<s:property value='rank.rankName'/>">
					</td >
					<td align="left" width="30%">
						客户类别：
			       <s:select name="customerTypeId"  list="customerTypeList"  listKey="customerTypeId"  listValue="name"  headerKey=""  headerValue="全部"  onchange="querySonCustomerType(this.value,null)"></s:select>
					    <select id="customer" name="customer_son_id"  style="display:none;"></select>
					     <input type="hidden"  id="sonCustomerId" value="<s:property value='rank.customer_son_id'/>">
		                <input type="hidden"  id="parentId"  value="<s:property value='rank.customerTypeId'/>"/>
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;<input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="10%">
						头衔编号
					</th>
					<th width="20%">
						头衔名称
					</th>
					<th width="20%">
						客户类型
					</th>
					
					<th width="20%">
						积分/经验值最小值
					</th>
					<th width="20%">
						积分/经验值最大值
					</th>
					<th width="10%">
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td align="center">
							<s:property value="code" />
						</td>
						<td align="center">
							<s:property value="rankName" />
						</td>
						<td align="center">
							<s:property value="customerName" />
						</td>
						
						<td align="center">
							<s:property value="scoreMin" />
						</td>
						<td align="center">
							<s:property value="scoreMax" />
						</td>
						<td>
							<img title="修改" style="cursor: pointer;"
								src="/etc/images/icon_modify.png"
								onclick="gotoUpdate(<s:property value="rankId"/>)" />&nbsp;&nbsp;
   							<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   
   							onclick="deleteByKey(<s:property value="rankId"/>,<s:property value="scoreMin" />)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table width="500" align="right">
				<tr>
					<td>
						<s:set name="form_name" value="'rankForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		</div>
	</body>
	<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

