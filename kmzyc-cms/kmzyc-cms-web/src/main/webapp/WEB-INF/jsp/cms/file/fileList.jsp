<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript">
		
	    function download(index){
		    var path = $("#path"+index).val();
		    var name = $("#name"+index).val();
		    
	    	$.ajax({
	    		   type: "POST",
	    		   url: "/cms/applicationLogAction_writeLog.action",
	    		   data: "filePath="+path+"&fileName="+name,
	    		   success: function(msg){
		    		   if(msg="success"){
		    			   showTips("下载成功！");
		    			   var timer_alert = setTimeout(function() {
		    					messageCloseThis();
		    				}, 2000);
			    	   }
	    		   }
	    		});
		}

	    function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}

		</script>

	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'应用日志'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPageAction_queryPage.action" method="post">
			<table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr>
					<th>点击下载默认下载到本地D盘</th>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>日志名称</th>
					<th>日志大小</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
			<s:iterator id="custiterator" var="list" value="fileList" status="index">
			 <s:if test="#index.index<20">
				<tr>
					<td width="5%">
							<input type="checkbox" name="levelId" id="levelId"
								value='<s:property value="pageId"/>' onClick="checkpromotionId(this);">
					</td>
					<td><s:property value="file.name"/></td>
					<td><s:property value="size"/></td>
					<td><s:property value="time"/></td>
					<td>
						<img title="下载" style="cursor: pointer;" src="/etc/images/down.png"  onclick="download('<s:property value='#index.index'/>');" />
						<input type="hidden" id="path<s:property value='#index.index'/>" value="<s:property value='file.absoluteFile'/>"/>
						<input type="hidden" id="name<s:property value='#index.index'/>" value="<s:property value='file.name'/>"/>
					</td>	
				</tr>
			 </s:if>
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
				<iframe src="" name="show"  style="width:0;height:0"></iframe>
			</table>
		</s:form>
		
		</div>
	</body>
</html>

