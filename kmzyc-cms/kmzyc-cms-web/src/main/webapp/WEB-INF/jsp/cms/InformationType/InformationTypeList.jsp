<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>资讯类别管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	var pageForm;
	$(document).ready(function(){
	var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("levelId");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
        	pageForm=document.getElementById("ADV_queryPageList");
	   });
      
	
	  /** 删除广告位信息  */
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
                          document.userLevelForm.action="/cms/InformationType_All_Delete.action";
                          document.userLevelForm.submit();
                 }
    }
	function addgoto(){
       //location.href="/cms/GotoInforTypeAdd.action"
       pageForm.action="/cms/InformationType_gotoAdd.action";
       pageForm.submit();
    }
	
    /** 进入修改广告位页面  **/
    function editUserLevel(id){
    	 //var pageNo=$("#pageNo").val();
         //location.href="/cms/InformationType_Byid.action?inforid="+id+"&pageNo="+pageNo;
         pageForm.action="/cms/InformationType_Byid.action?inforid="+id;
       	 pageForm.submit();
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
         
          $.ajax({
        url:'InformationType_ByInforId.action',
        type:'post',
        data:{"inforid":id},
        success:function (d) {
        if(d=="0"){
         location.href="/cms/InformationType_Delete.action?inforid="+id;
        }else{
         var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>此文章类型下有文章，如需删除请先清除此类型下文章。 ";
               messageDialog("消息提示","text:"+msg ,"300px","102px","text");
        }
        }
     });
          
         }
    }
	
	 
	</script>	
		
		

<style type="text/css">
.listTitle {
	height: 40px;
    line-height: 40px;
    background: #c7e3f1;
    vertical-align: middle;
    font-size: 13;
    color: #222222;
    margin-bottom: 20px;
}
.listTitle span{
padding-left: 20px;
    height: 40px;
    line-height: 40px;
    vertical-align: middle;
    margin-top: 5px;
}
.listTitle span img{
   vertical-align: middle;
}
.content_table {
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    overflow: hidden;
    padding: 6px;
}
</style>
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'资讯类别管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		        
<s:form id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/InformationType_queryInformationType.action" method="post">
				<s:token></s:token>
			<!-- 查询条件 -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
		    <table width="98%" align="center"  border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
				
					<td width="60%" valign="middle" colspan="2">
						
					</td>
				</tr>
				<tr width="60%">
					<td >
						类别名称：
						<input name="keyWords.name_keyword" type="text"  
							value="<s:property value="keyWords.name_keyword"/>">
					</td >
					<td align="left" width="30%">
						
	                               有效性： <select name="keyWords.status_keyword" id="infortype.status">
					         <option value="" <s:if test="infortype.status==null">selected="selected"</s:if> >全部</option>
					          <option value="0" <s:if test="infortype.status==0">selected="selected"</s:if> >有效</option>
					           <option value="1" <s:if test="infortype.status==1">selected="selected"</s:if> >无效</option>
					         </select>	

					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
                        <input class="addBtn" TYPE="button" value="" onClick="addgoto();">&nbsp;&nbsp;
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('levelId');">
							<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>
						 编号
					</th>
					<th>
						资讯类别名称
						
					</th>
					<th>
						资讯编码
					</th>
					<th>
						状态<!--0.有效1.无效-->
					</th>
					
					<th>
						创建日期
					</th>
					<th width="80">
						创建人
					</th>
					<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
					<th width="60">
					      操作
					</th>
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="id"/>' onClick="checkpromotionId(this);">
						</td>
						<td>
						<s:property value="id"/>
						</td>
						<td>
							<s:property value="name"/>
						</td>
						<td>
							
							<s:property value="typeCode"/>
						</td>
						
						<td>
						   
						    <s:if test="status==0">有效</s:if>
						    <s:if test="status==1">无效</s:if>
						</td>
							
						
						<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
					     	
						</td>
						<td>
								<s:property value="sysUserMap[created]"/>
						</td>
						<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
						<td>
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editUserLevel(<s:property value="id"/>)" />
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="id"/>)" />
						</td>
					</tr>
				
				</s:iterator > 
				
					
				
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>




		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		

<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 
	              
</div>
		</div>
	</body>
</html>

