<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-latest.pack.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript">
		
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
        	$("#pageForm").validate({
		         rules: {
						"cmsWindow.windowId": {keywordIdChar:true,maxlength:22},
						"cmsWindow.name": {maxlength:42},
						"cmsWindow.theme": {maxlength:42}
			        	},
			     success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
	   });
		</script>
		
	</head>
	<body >
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="windowForm" id="pageForm" action="/cms/cmsPageAction_gotoBindWindow.action" method="post">
		<table width="98%" align="center" height="30" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="50%">
						编号:<input type="text" name="cmsWindow.windowId" value="<s:property value='cmsWindow.windowId'/>"/>
					</td>
					<td>
						名称:<input type="text" name="cmsWindow.name" value="<s:property value='cmsWindow.name'/>">
					</td>
				</tr>
				<tr>
					<td width="50%">
						主题:<input type="text" name="cmsWindow.theme" value="<s:property value='cmsWindow.theme'/>">
					</td>
					<td>
						时间:<input type="text" name="cmsWindow.startTime" value="<s:date name="cmsWindow.startTime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						至:<input type="text" name="cmsWindow.endTime" value="<s:date name="cmsWindow.endTime" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
				</tr>
		</table>
		<table width="98%" align="center" height="30" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td></td>
					<td align="right"><input type="submit" class="queryBtn" value=""/>&nbsp;<INPUT class="saveBtn" TYPE="button" value="" onclick="doSubmit()"/></td>
				</tr>
		</table>
		<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			<input type="hidden" name="pageId" id="pageId" value="<s:property value='pageId'/>"/>
			<input type="hidden" name="backType" id="backType" value="<s:property value='backType'/>"/>
			<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>"/>
			<tr>
			    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
				<th align="center">编号</th>
				<th align="center">窗口名称</th>
				<th align="center">窗口主题</th>
				<th align="center">创建日期</th>
				<th align="center">状态</th>
			</tr>
			<s:iterator id="cmsWindowiterator"  value="page.dataList">
			<tr>
			     <td>
				     <input type="checkbox" name="promotionIds" value='<s:property value="windowId"/>' onclick="checkpromotionId(this);">
				     
			     </td>
				<td align="center">
				     <s:property value="windowId"/>
				</td>
				<td align="center">
				     <s:property value="name"/>  
				</td>
				<td align="center">
				     <s:property value="theme"/>
				</td>
				<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="center">
				     <s:if test="status==0">有效</s:if><s:if test="status==1">无效</s:if>
				</td>
			</tr>
			</s:iterator>
		</table>
		
		<table width="98%"  align="center" class="page_table">
			<tr>
				<td>
					<s:set name="form_name"  value="'windowForm'"  scope="request"></s:set>
					<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
				</td>
			</tr>
		</table>
		<!-- 底部 按钮条 -->
		<!--<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
			<tr> 
				<td align="left">
					<INPUT class="saveBtn" TYPE="button" value="" onclick="doSubmit()"/>
				</td>
				<td width="20%" align="center"></td>
			</tr>
		</table> 
		--></s:form>
			
		</div>
	</body>
	<script type="text/javascript">
		//关闭弹出层
		function closeOpenUserInfo(accountId,account,name){
            closeThis();
            $("#loginAccount").val(account);
             $("#n_LoginId").val(accountId);
        }
        //表单提交
        function doSubmit()
        {
        	var checks=$("#checkeds").val();
        	if(checks=='')
        	{
        		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
		        messageDialog("消息提示","text:"+msg ,"300px","102px","text");
		        var timer_alert = setTimeout(function() {
	 				messageCloseThis();
	 			}, 2000);
        		return ;
        	}
        	var pageId=$("#pageId").val();
        	var backType=$("#backType").val();
        	var adminType = $("#adminType").val();
        	var datas = "checkeds="+checks+"&pageId="+pageId+"&backType="+backType;
        	if($("#adminType").val()!=null && $("#adminType").val()!="undefined" && $("#adminType").val()!=""){
            	datas = datas+"&adminType="+adminType;
            }
            
        	
        	$.ajax({
				url:"/cms/cmsWindowPageAction_add.action?ajax=yes",
				data:datas,
				dataType:"json",
				type:"get",
				success:function(result){
					parent.closeOpenUserInfo(result);
				},
				error:function(){
					alert("出错了");
				}
			});
        }
	</script>
</html>

