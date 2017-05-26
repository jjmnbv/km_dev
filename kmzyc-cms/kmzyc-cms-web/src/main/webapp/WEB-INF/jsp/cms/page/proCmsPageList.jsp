<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-latest.pack.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">

		 /** 选择团出窗口数据  **/
        function selectOneAccount(){
          	var checkeds = "";
          	checkeds = $("#checkeds").val();
          	var userId = $("#userId").val();
          	var siteId = $("#siteId").val();
          	if(checkeds == ""){
          		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
               messageDialog("消息提示","text:"+msg ,"300px","102px","text");
               var timer_alert = setTimeout(function() {
       				messageCloseThis();
       			}, 2000);
               return;
           }
	           parent.closePage(checkeds,userId,siteId);
        }
        
		
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
        	
        	$("#pageForm").validate({
		         rules: {
		         		"keyWords.id_keyword":{keywordIdChar:true,maxlength:10}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
	   });
	   
		</script>
		
	</head>
	<body >
		
		<s:form class="pageForm" name="pageForm" id="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPageAction_queryByPage.action" method="post">
			<!-- hidden -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<input type="hidden" id="userId" name="userId" value="<s:property value='userId' />">
			<input type="hidden" id="siteId" name="siteId" value="<s:property value='siteId' />">
			<input type="hidden" id="dataType" name="dataType" value="<s:property value='dataType' />">
			<table width="98%" align="center" height="60" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr>	
				
					<td width="40%">
						页面编号:<input type="text" id="id_keyword" name="keyWords.id_keyword" value="<s:property value='keyWords.id_keyword'/>">
					</td>
					<td width="40%">
						页面名称:<input type="text" id="name_keyword" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
					</td>
				</tr>
				<tr>
					<td width="40%">
						输出路径:<input type="text" id="outPath_keyword" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
					</td>
					<td width="25%">
						发布状态:<select name="keyWords.status_keyword" id="status_keyword">
							<option value="" <s:if test="keyWords.status_keyword==''">selected="selected"</s:if>>全部</option>
					        <option value="0" <s:if test="keyWords.status_keyword==0">selected="selected"</s:if>>未发布</option>
					        <option value="1" <s:if test="keyWords.status_keyword==1">selected="selected"</s:if>>已发布</option>
					        <option value="2" <s:if test="keyWords.status_keyword==2">selected="selected"</s:if>>已修改</option>
					        </select>
					</td>
					<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
					<td align="right"><input type="submit" class="queryBtn" value=""/></td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>编号</th>
					<th>页面名称</th>
					<th>页面标题</th>
					<th>输出路径</th>
					<th>发布类型</th>
					<th>发布状态</th>
				</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td width="5%">
					
							
							 <s:if test="flag!=1">
							 <input type="checkbox" name="levelId" id="levelId" value='<s:property value="pageId"/>' onclick="checkpromotionId(this);">
		     				</s:if>
					</td>
					<td><s:property value="pageId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="title"/></td>
					<td><s:property value="outputPath"/></td>
					<td>
						<s:if test="publicType==0">人工发布</s:if>
						<s:if test="publicType==1">定时发布</s:if>
						<s:if test="publicType==2">动态页面</s:if>
						<s:if test="publicType==3">其他</s:if>
					</td>
					<td><s:if test="status==0 ">未发布</s:if><s:if test="status==1">已发布</s:if><s:if test="status==2"><div style="color:#F00">已修改</div></s:if></td>
					
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
			
			<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="button" value=" " onclick="selectOneAccount();">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>
			
		</s:form>
					<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
<script type="text/javascript">
		var pageForm= window.document.getElementById("pageForm");
		$(function(){
			//跳转到添加页面
	   		$("#admin0").click(function(){
	   			pageForm.action="/cms/cmsPageAction_gotoAddPage.action"; 
 				pageForm.submit();
	   		});
	   		$("#admin1").click(function(){
 				pageForm.action="/cms/cmsPageAction_gotoAddPage.action"; 
 				pageForm.submit();
	   		});
	   		
	   		//页面批量删除
	   		$(".delBtn").click(function(){
	   			var checks=$("#checkeds").val();
	   			if(checks=='')
	   			{
	   				var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		            messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
	   				return ;
	   			}
	   			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   			if(ok==false)
	   			{
	   				return ;
	   			}
	   			else
	   			{
					window.location.href="/cms/cmsPageAction_delAllPage.action?checkeds="+checks;
				}
	   		});
	   });
		//关闭弹出层
		function closeOpenUserInfo(result){
            closeThis();
            if(result==1)
			{
				window.location.href="/cms/cmsPageAction_queryPage.action";
			}
        }
        //返回
		function gotoList(){
  			 history.back();
 		}
 		//跳转编辑页
 		function editPage(pageId)
 		{
 			pageForm.action="/cms/cmsPageAction_gotoEditPage.action?pageId="+pageId;
 			pageForm.submit();
 		}
 		function adminEditPage(pageId)
 		{
 			pageForm.action="/cms/cmsPageAction_gotoEditPage.action?pageId="+pageId;
 			pageForm.submit();
 		}
 		//单个页面删除
 		function deletePage(id)
 		{
 			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsPageAction_delPage.action?pageId="+id;
			}
 		}
 		//跳转绑定页面
 		function bindWindow(id)
 		{
 			dialog("选择窗口","iframe:/cms/cmsPageAction_gotoBindWindow.action?pageId="+id ,"900px","530px","iframe");  
 		}
 		//详情页
 		function pageDetail(pageId)
 		{
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_pageDetail.action?adminType=0&pageId="+pageId+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize;
 		}
 		function adminPageDetail(pageId)
 		{
 			var id_keyword=$("#id_keyword").val(); 
	   		var name_keyword=$("#name_keyword").val();
	   		var outPath_keyword=$("#outPath_keyword").val();
	   		var status_keyword=$("#status_keyword").val();
	   		var pageNo=$("#pageNo_keyWords").val();
	   		var pageSize=$("#pageSize_keyWords").val();
	   		window.location.href="/cms/cmsPageAction_pageDetail.action?pageId="+pageId+"&keyWords.id_keyword="+id_keyword
	   				+"&keyWords.name_keyword="+name_keyword+"&keyWords.outPath_keyword="+outPath_keyword+"&keyWords.status_keyword="
	   				+status_keyword+"&keyWords.pageNo="+pageNo+"&keyWords.pageSize="+pageSize;
 		}
 		//页面发布
 		function publish(id)
 		{
 			window.location.href="/cms/pagePublish_parse.action?adminType=0&cmsPage.pageId="+id;
 		}
 		function adminPublish(id)
 		{
 			window.location.href="/cms/pagePublish_parse.action?cmsPage.pageId="+id;
 		}
 		//页面预览
 		function preview(id)
 		{
 			window.open("/cms/pagePublish_preview.action?cmsPage.pageId="+id);
 			
 		}
 		//提示信息显示
 		function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
 		function publish(){
 			window.location.href="/cms/cmsPageAction_publish.action";
 	 	}
</script>
	</body>
</html>

