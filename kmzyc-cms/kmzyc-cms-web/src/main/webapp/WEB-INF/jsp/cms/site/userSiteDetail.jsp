<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>用户站点数据列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){

			$(".site").click(function (e) {
		        e.preventDefault();
		        var userId = $(this).attr("userId");
		        var siteId = $(this).attr("siteId");
		        location.href="cmsSiteData_detailData.action?userId="+userId+"&siteId="+siteId;
		    });
			
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
	   });
	 function checkpromotion(obj){
 	 var check = "";
 	 	//checkeds以逗号隔开的Id的字符串，要对应Action的属性
      check = $("#checkeds").val();
      if(obj.checked){
     	 if(check==""){
         	 check = obj.value;
          }else{
              check +=","+obj.value;
          }
     	 $("#checkeds").val(check);
      }else{
          if(check!=""){
              if(check.indexOf(","+obj.value)>-1){
             	 check = check.replace(","+obj.value, "");
              }

              if(check.indexOf(obj.value+",")>-1){
             	 check = check.replace(obj.value+",", "");
              }
              
              if(check.indexOf(obj.value)>-1){
             	 check = check.replace(obj.value, "");
              }
         	$("#checkeds").val(check);
          }
      }
  }
	   function deleteWindow(id)
 		{
 			var ok=confirm("确定删除吗？");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
	 			var pageId=$("#cmsPageId").val();
				window.location.href="/cms/cmsPageAction_delPwDate.action?windowId="+id+"&pageId="+pageId;
			}
 		}
 		function bink(id)
 		{
 			var pageId=$("#cmsPageId").val();
 			window.location.href="/cms/cmsWindowDataAction_queryPageList.action?windowId="+id+"&pageId="+pageId;
 		}
 		function bindWindow(id)
 		{
 			dialog("选择窗口","iframe:/cms/cmsPageAction_gotoBindWindow.action?pageId="+id+"&backType=1" ,"1000px","520px","iframe"); 
 		}

 		function gotoList(){
			location.href="/cms/cmsUserSite_queryForPage.action";
		}
		
		function goBack(){
			var adminType=$("#adminType").val();
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var theme_keyword=$("#theme_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_queryForPage.action?adminType="+adminType+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize;
		 }
		function closeOpenUserInfo(result){
            closeThis();
            var id=$("#cmsPageId").val();
           // window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+id;
            if(result==2)
			{
				window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+id; 
			}
        }
        function delAll()
        {
        	var ok=confirm("确定删除吗？");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
	 			var pageId=$("#cmsPageId").val();
	 			var checkeds=$("#checkeds").val();
				window.location.href="/cms/cmsPageAction_delAll.action?checkeds="+checkeds+"&pageId="+pageId;
			}
        }
        function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
 		
 		
 		function editPage(id)  //修改窗口
 		{  var pageid=$("#pageId").val();

			window.location.href="/cms/cmsWindowAction_gotoEditWin.action?pageId="+pageid+"&windowId="+id; 
 		}
 		
 		
 	
		</script>
	<style type="text/css">
		.detail_table tr td
		{
			text-align: left;
		}
		.detail_table tr td span
		{
			font-weight: bold;
		}
		.detail_table .binkBtn
		{
			background-color: buttonface;
			cursor: pointer;
			border: 1px solid #CBCBCB;
			height: 25px;
		}
	</style>
	</head>
	<body >
		
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'授权管理 '" scope="request" />
		<s:set name="name" value="'用户站点列表'" scope="request" />
		<s:set name="son_name" value="'用户站点数据详细列表'" scope="request" />
		
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;" >
		<s:form id="pageDetailForm" name="pageDetailForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsSiteData_detailData.action" method="post">
			<input type="hidden" id="cmsPageId" name="pageId" value="<s:property value="cmsPageDetail.cmsPage.pageId" />" />
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value="checkeds"/>"/>
			<input type="hidden" id="status" value="<s:property value="cmsPageDetail.cmsPage.status" />"/>
			<input name="adminType" id="adminType" type="hidden" value="<s:property value="adminType" />"/>
		    <!-- keyWords -->
			<input type="hidden" id="id_keyword" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
			<input type="hidden" id="name_keyword" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
			<input type="hidden" id="outPath_keyword" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
			<input type="hidden" id="status_keyword" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
					<table width="98%" class="detail_table list_table" cellpadding="3" align="center">
			<tr> 
				<th colspan="2" align="left" class="edit_title">用户站点数据信息</th>
			</tr>
				<tr>
					<td><span>用户编号:</span><s:property value="cmsUserSiteShow.userId" /></td>
					<td><span>用户名称:</span><s:property value="cmsUserSiteShow.username" /></td>
				</tr>
				<tr>
					<td colspan="2">
					<span>站点名称:</span>
					 <s:iterator  value="cmsUserSiteShow.siteList">
					 <s:if test="isCss!=1">
					  	<a href="javascript:void(0);" title="详情" class="site" userId="<s:property value='cmsUserSiteShow.userId'/>" siteId="<s:property value='siteId'/>" ><s:property value="name"/></a>&nbsp;&nbsp;
					  </s:if>
					  <s:else>
					  	<input type="hidden" id="userId" name="userId" value="<s:property value="cmsUserSiteShow.userId" />"/>
					  	<input type="hidden" id="siteId" name="siteId" value="<s:property value="siteId" />"/>
					  	<a href="javascript:void(0);" style="color: green;" title="详情" class="site" userId="<s:property value='cmsUserSiteShow.userId'/>" siteId="<s:property value='siteId'/>" ><s:property value="name"/></a>&nbsp;&nbsp;
					  </s:else>	
					 </s:iterator>
					</td>
				</tr>
				
			</table>
			
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr width="100%">
					
					<td align="left" width="20%">
						数据类型:
						<select name="cmsSiteData.dataType">
						<s:if test="cmsSiteData.dataType==1">
							<option value="">全部</option>
							<option value="1" selected="selected">页面</option>
							<option value="2">广告</option>
							<option value="3">活动</option>
						</s:if>
						<s:elseif test="cmsSiteData.dataType==2">
							<option value="">全部</option>
							<option value="1">页面</option>
							<option value="2" selected="selected">广告</option>
							<option value="3">活动</option>
						</s:elseif>
						
						<s:elseif test="cmsSiteData.dataType==2">
							<option value="">全部</option>
							<option value="1">页面</option>
							<option value="2">广告</option>
							<option value="3" selected="selected">活动</option>
						</s:elseif>
						
						<s:else>
							<option value="" selected="selected">全部</option>
							<option value="1">页面</option>
							<option value="2">广告</option>
							<option value="3">活动</option>
						</s:else>
							
						</select>
					</td>
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			
			
			</table>
			
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1" id="list_table">
				
				<tr class="dataName">
					<th>
						数据名称
					</th>
					<th>
						数据类型
					</th>
					
				</tr>
				<s:iterator id="custiterator" var="cus" value="page.dataList">
					<tr class="dataTr">
						
						<td>
							<s:property value="#cus.dataName" />
						</td>
					
						<td>
							<s:if test="dataType==1">
								页面
							</s:if>
							<s:elseif test="dataType==2">
								广告
							</s:elseif>
							<s:elseif test="dataType==3">
								活动
							</s:elseif>
						</td>
						
					</tr>
				</s:iterator>
			</table>
			
			
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'pageDetailForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<input class="backBtn"  onclick="gotoList()" type="button" value="">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>
		</s:form>
						<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

