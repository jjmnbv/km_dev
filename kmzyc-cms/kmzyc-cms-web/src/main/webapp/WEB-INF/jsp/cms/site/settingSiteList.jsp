<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>站点授权列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
			$(".site").click(function (e) {
		        e.preventDefault();
		        var userId = $(this).attr("userId");
		        var siteId = $(this).attr("siteId");
		        location.href="cmsSiteData_warrant.action?userId="+userId+"&siteId="+siteId;
		    });
			
			var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
    	/*	var checks = "";
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
        	}*/
        	
        	$("#pageForm").validate({
		         rules: {
		         		"cmsUserSite.userSiteId":{digits:true,maxlength:10}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
	   });
	   
		</script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'授权管理 '" scope="request" />
		<s:set name="name" value="'用户站点列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="pageForm" id="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsUserSite_queryForPage.action" method="post">
			<table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr width="100%">
					
					<td align="left" width="20%">
						用户名称:<input type="text" name="cmsUserSite.sysUser.userName" value="<s:property value='cmsUserSite.sysUser.userName'/>">
					</td>
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
				
			
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAll(this,'levelId')">
					</th>
					<th>用户名称</th>
					<th>站点名称</th>
					<th>操作</th>
				
				</tr>
			<s:iterator value="page.dataList" var="cus">
				<tr>
					<td width="5%">
							<input type="checkbox" name="levelId" id="levelId" value='<s:property value="userSiteId"/>' onClick="checkpromotionId(this);">
					</td>
					<td><s:property value="username"/></td>
					<td>
					 <s:iterator  value="siteList">
					  <a href="javascript:void(0);" title="授权" class="site" userId="<s:property value='#cus.userId'/>" siteId="<s:property value='siteId'/>" ><s:property value="name"/></a>&nbsp;&nbsp;
					 </s:iterator>
					</td>
					<td>
					<img title="详情" style="cursor: pointer;" src="/etc/images/icon_detail.png"   onclick='detail(<s:property value="userId"/>)' />
					</td>	
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'pageForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
					<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
<script type="text/javascript">
		//详情
		function detail(userId){
			location.href="cmsSiteData_detail.action?userId="+userId;
		}

</script>
	</body>
</html>

