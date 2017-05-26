<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>供应商管理</title>
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
        	pageForm= window.document.getElementById("supplier_queryPageList");
	   });
      
	
	//返回
		 function gotoList(){
  			 history.back();
 		}
	
	
    /** 进入修改广告位页面  **/
    function editUserLevel(id){
     //   var pageNo=$("#pageNo").val();
         location.href="/cms/supplier_byId.action?cmsSupplierAdcolumn.supplierAdcolumnId="+id;
    }
    function adminEditUserLevel(id){
        //var pageNo=$("#pageNo").val();
         //location.href="/cms/Adcolumn_Byid.action?Advid="+id+"&pageNo="+pageNo;
         pageForm.action="/cms/Adcolumn_Byid.action?Advid="+id;
 	    	pageForm.submit();
         }
  
		function addgoto(){
	
       //location.href="/cms/GotoAdcolumnAdd.action"
        location.href="/cms/supplier_gotoAdd.action";
 	  
    }
	 
	</script>	
		
		

<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;供应商管理
		
		
	  </span>
</div>
		<div  style="height:90%;overflow-y:scroll; " >
		        
<s:form  id="" name="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/supplier_queryAdcolumnList.action" method="post">
			<s:token></s:token>
		
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
				
					<td width="60%" valign="middle" colspan="2">
					            <input class="addBtn" TYPE="button" value="" onclick="addgoto();" >
							<input type="hidden" id="checkeds" name="checkeds" value=""/>
					</td>
				</tr>
				<tr width="60%">
					<td >
			供应商：
			
			<s:select list="shopMainList" listKey="supplierId" listValue="shopName" name="cmsSupplierAdcolumn.supplierId" headerKey="" headerValue="全部">
			</s:select>
					</td >
					
			<td >
			广告位：
			<s:select list="cmsAdcolumnList" listKey="adcolumnId" listValue="name" name="cmsSupplierAdcolumn.adcolumnId" headerKey="" headerValue="全部">
			</s:select>
			</td >
		
				
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
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
						供应商
					</th>
					<th>
						广告位
					</th>
				   <th>
						广告位路径
					</th>
					<th>
						站点
					</th>
				
					
					<th width="60">
					      操作
					</th>
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="supplierAdcolumnId"
								value='<s:property value="supplierAdcolumnId"/>' onclick="checkpromotionId(this);">
								
						</td>
						<td>
						<s:property value="supplierName"/>
						</td>
						<td>
							<s:property value="adcolumnName"/>
						</td>
						<td>
							<s:property value="contentPath"/> 
						</td>
						<td>
							<s:property value="siteCode"/>
						</td>
					
					   <td>  
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editUserLevel(<s:property value="supplierAdcolumnId"/>)" />
						   
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
		</s:form >



	<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>


<div width="100%">

		 
	              
</div>
		</div>
	</body>
</html>

