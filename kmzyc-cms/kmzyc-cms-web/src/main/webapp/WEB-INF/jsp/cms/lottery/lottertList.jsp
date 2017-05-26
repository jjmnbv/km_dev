<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<html>
	<head>
		<title>抽奖信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/dataList.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">
      		<%
			String picPath = PathConstants.getSitePath("picPath","mail",(Integer)session.getAttribute("siteId"));
		    %>
		var picturePreviewPath = "<%=picPath%>";
        
        function loadData(){
        	 var checkeds = $("#checkeds").val();
             var dataType = $("#dataType").val();
              if(checkeds!=""){
              	 var checks = new Array();
              	 checks = checkeds.split(',');
              	 var mychecks = 0;
              	 mychecks = $("input[type='checkbox'][name='dataId']").size();
              	 if(mychecks==0){
                  	 for(var k = 0;k<checks.length;k++){
                  	
                  		$.ajax({
                 			   type: "POST",
                 			   url: "/cms/cmsWindowDataAction_queryData.action",
                 			   data: "dataId="+checks[k]+"&dataType="+dataType,
                 			   success: function(data){
                  			
                  			 var type = "";
                  			 var myData = eval("("+data+")");
                  			 	 $str='';
         				         $str+="<tr class='dataTr'>";											     
         				         $str+="<td><input type='checkbox' name='dataIds' value='"+myData['luckDrawId']+"' onclick=checkboxChange(); /></td>";
         				      	 $str+="<td>";
         				         if(typeof myData["luckDrawId"]!="undefined")
         				      	 {$str+=myData["luckDrawId"];}
         				         $str+="</td>";
         				         $str+="<td>";
        				         if(typeof myData["titel"]!="undefined")
        				      	 {$str+=myData["titel"];}
        				        
         				         $str+="<td><img title='删除' style='cursor: pointer;' src='/etc/images/icon_delete.png'   onclick='deleteByKey(this);' /></td>";  
         				         $str+="</tr>";
                  			 
                  			 var $table = $("#list_table");  
                               $table.append($str);  
                  			   
                 			   }
                 			});
                       }
                  }
              }
       
        }
 function gotoList(){
        	var adminType = $("#adminType").val();
        	var windowId = $("#windowId").val();
        	var pageId = "";
			pageId = $("#pageId").val();
 			window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+windowId+"&adminType="+adminType+"&pageId="+pageId;
			
		}
		</script>
		
	</head>
	<body onload="loadData();">
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'数据管理'" scope="request" />
		<s:set name="son_name" value="'抽奖活动数据'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		
		<s:form name="windowDataForm"   onsubmit="return isCheck('dataIds');"
			action="/cms/cmsWindowDataAction_add.action" method="post">
			<s:token></s:token>
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<INPUT class="saveBtn" TYPE="submit" value="">
            			&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onclick="deleteSelected('dataIds');">
						&nbsp;&nbsp;
						 <input class="backBtn"  onclick="gotoList()" type="button" value=""> 
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<input type="hidden" name="dataType" id="dataType" value="<s:property value='dataType'/>"/>
						<input type="hidden" name="windowId" id="windowId" value="<s:property value='windowId' />">
						<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
						<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
					</td>
				</tr>
			
			<tr>  
				<td colspan="4"  align="center" >
				    
				</td>
			</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				<tr class="dataName">
					<th width="5%">
						<input type='checkbox' name='data'  onclick="checkAll(this,'dataIds');">
					</th>
					
					   <th align="center">
					          活动编码
					   </th>
					   <th>
							活动标题
						</th>
					
					<th width="60">操作</th>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

