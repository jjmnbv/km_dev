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
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		
		$(document).ready(function(){
			var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
			
		});
        	
		</script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'手机端应用列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="pageForm" id="pageForm"  action="/cms/cmsAppManageAction_queryPageList.action" method="post">
			<!-- hidden -->
			<table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>	
					<td width="40%">
						手机端应用编号：<input type="text" id="appmanageId" name="cmsAppManage.appmanageId" value="<s:property value='cmsAppManage.appmanageId'/>">
					</td>
					<td width="40%">
						手机端应用名称：<input type="text" id="name" name="cmsAppManage.name" value="<s:property value='cmsAppManage.name'/>">
					</td>
				</tr>
				<tr>
					<td width="40%">
						版本名：<input type="text" id="version" name="cmsAppManage.version" value="<s:property value='cmsAppManage.version'/>">
					</td>
					<td width="40%">
						手机端平台：<input type="text" id="terrace" name="cmsAppManage.terrace" value="<s:property value='cmsAppManage.terrace'/>">
					</td>
					<td align="right">
                    <input type="submit" class="queryBtn" value=""/>
                    						<!-- hidden -->
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<!-- /hidden -->
						
						<INPUT class="addBtn" TYPE="button" value="" />
						<input class="delBtn" type="button" value=""/>
                    
                    </td>
				</tr>
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>手机端应用编号</th>
					<th>手机端应用名称</th>
					<th>版本名</th>
					<th>版本号</th>
					<th>下载地址</th>
					<th>强制下载</th>
					<th>手机端平台</th>
					<th >
					         修改日期
						</th>
						<th >
						         修改人
						</th>
					<th>操作</th>
				</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td width="5%">
							<input type="checkbox" name="levelId" id="levelId"
								value='<s:property value="appmanageId"/>' onClick="checkpromotionId(this);">
					</td>
					<td><s:property value="appmanageId"/></td>
					<td><s:property value="name"/></td>
					<td><s:property value="version"/></td>
					<td><s:property value="versioncode"/></td>
					<td><s:property value="download"/></td>
					<td>
						<s:if test="isCoerce==1">是</s:if>
						<s:if test="isCoerce==2">否</s:if>
					</td>
					<td><s:property value="terrace"/></td>
							<td>
							<s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:property value="sysUserMap[modified]"/>
							</td>
					<td>
						
						<img title="详情" style="cursor: pointer;" src="/etc/images/icon_detail.png"   onclick='detailApp(<s:property value="appmanageId"/>)' />
						<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   onclick='editApp(<s:property value="appmanageId"/>)' />
						<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteApp(<s:property value="appmanageId"/>)' />
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



		var pageForm= window.document.getElementById("pageForm");
		$(function(){

			$("#pageForm").validate({
		         rules: {
						"cmsAppManage.appmanageId": {digits:true,maxlength:42},
						"cmsAppManage.name":{maxlength:42},
						"cmsAppManage.version":{maxlength:42},
						"cmsAppManage.terrace":{maxlength:42}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
			
			//跳转到添加页面
	   		$(".addBtn").click(function(){
	   			pageForm.action="/cms/cmsAppManageAction_gotoAddAppManage.action"; 
 				pageForm.submit();
	   		});
	   		
	   		//页面批量删除
	   		$(".delBtn").click(function(){
		   		var checks = '';
	   			var levelIds = document.getElementsByName("levelId");
	   			for(var k=0;k<levelIds.length;k++){
					if(levelIds[k].checked == true){
						checks=checks+levelIds[k].value+",";
					}
		   		}
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
							window.location.href="/cms/cmsAppManageAction_delAll.action?checks="+checks;
				}
	   		});
		});
	   		
	   		
	   		
	   		
	   
	   
	   
	   
	   
        //返回
		function gotoList(){
  			 history.back();
 		}
 		//跳转编辑页
 		function detailApp(manageId)
 		{
 			pageForm.action="/cms/cmsAppManageAction_gotoDetailAppManage.action?manageId="+manageId;
 			pageForm.submit();
 		}

 		//跳转编辑页
 		function editApp(manageId)
 		{
 			pageForm.action="/cms/cmsAppManageAction_gotoEditAppManage.action?manageId="+manageId;
 			pageForm.submit();
 		}
 		
 	
 		//单个页面删除
 		function deleteApp(id)
 		{
 			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/cmsAppManageAction_del.action?manageId="+id;
			}
 		}
 	
 		//提示信息显示
 		function showTips(msgTips)
 		{
 			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>"+msgTips;
		    messageDialog("消息提示","text:"+msg ,"300px","102px","text");
 		}
 		
</script>
	</body>
</html>

