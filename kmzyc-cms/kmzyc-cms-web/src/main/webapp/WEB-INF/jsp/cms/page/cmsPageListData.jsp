<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/dataList.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">
		
       //关闭品牌弹出窗口 
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
                 			   url: "/cms/cmsSiteData_queryData.action",
                 			   data: "dataId="+checks[k]+"&dataType="+dataType,
                 			   success: function(data){
                  			
                  			 var type = "";
                  			 var myData = eval("("+data+")");
                  			
                  			 	 $str='';
         				         $str+="<tr class='dataTr'>";
         				         $str+="<td><input type='checkbox' name='dataIds' value='"+myData["pageId"]+"' onclick=checkboxChange(); /></td>";
         				         $str+="<td>";
         				         if(typeof myData["pageId"]!="undefined")
         				      	 {$str+=myData["pageId"];}
         				         $str+="</td>";
         				      	 $str+="<td>";
         				      	 if(typeof myData["name"]!="undefined")
         				      	 {$str+=myData["name"]}
         				      	 $str+="</td>";  
         				         $str+="<td>";
         				         if(typeof myData["title"]!="undefined")
         				         {$str+=myData["title"]}
         				         $str+="</td>";
         				         $str+="<td>";
         				         if(typeof myData["outputPath"]!="undefined")
         				         {$str+=myData["outputPath"]}
         				         $str+="</td>";
         				         $str+="<td>";
         				         if(typeof myData["publicType"]!="undefined")
         				         {
         				        	if(myData["publicType"]==0){
         				        		$str+="人工发布"
             				        } 
         				        	if(myData["publicType"]==1){
         				        		$str+="定时发布"
             				        } 
         				        	if(myData["publicType"]==2){
         				        		$str+="动态页面"
             				        } 
         				        	if(myData["publicType"]==3){
         				        		$str+="其他"
             				        } 
                          		 }
         				         $str+="</td>";
         				         
        				         $str+="<td>";
        				         if(typeof myData["status"]!="undefined")
        				         {
           	            			if(myData["status"]==0){
           	            				$str+="未发布";	
               	            		}	 
            				        if(myData["status"]==1){
        	            				$str+="已发布";	
            	            		}	  
            				        if(myData["status"]==2){
        	            				$str+="<div style='color:#F00'>已修改</div>";	
            	            		}	         
            	                 }
        				         $str+="</td>";
         				         
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


		</script>
		
	</head>
	<body onload="loadData();">
				<s:set name="parent_name" value="'授权管理'" scope="request" />
		<s:set name="name" value="'用户站点列表'" scope="request" />
		<s:set name="son_name" value="'数据授权列表'" scope="request" />
		<s:set name="grandson_name" value="'数据列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		
		<s:form name="windowDataForm"   onsubmit="return isCheck('dataIds');"
			action="/cms/cmsSiteData_add.action" method="post">
			<s:token></s:token>
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<INPUT class="saveBtn" TYPE="submit" value=""/>
            			&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onclick="deleteSelected('dataIds');" />
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<input type="hidden" name="dataType" id="dataType" value="<s:property value='dataType'/>"/>
						<input type="hidden" name="userId" id="userId" value="<s:property value='userId' />">
						<input type="hidden" name="siteId" id="siteId" value="<s:property value='siteId' />">
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
					<th align="center">编号</th>
					<th align="center">页面名称</th>
					<th align="center">页面标题</th>
					<th align="center">输出路径</th>
					<th align="center">发布类型</th>
					<th align="center">发布状态</th>
					<th width="60">操作</th>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

