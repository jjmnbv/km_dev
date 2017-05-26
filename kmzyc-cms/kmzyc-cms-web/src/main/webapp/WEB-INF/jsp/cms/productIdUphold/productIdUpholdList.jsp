<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>产品新旧SkuId列表</title>
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
	   
		</script>
		
	</head>
	<body >
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'产品新旧SkuId维护'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form class="pageForm" name="pageForm" id="pageForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPageAction_queryForPage.action" method="post">
			<table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
						<INPUT class="addBtn" TYPE="button" value="" onClick="addImage()"/>
					</td>
				</tr> 
				
			</table>
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th>现有产品SkuId</th>
					<th>之前产品SkuId</th>
					<th>操作</th>
				</tr>
			<s:iterator id="column" value="upholdMap">
				<tr>
					<td><s:property value="key"/></td>
					<td><s:property value="value"/></td>
					<td>
						<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick='deleteImage("<s:property value="key"/>=<s:property value="value"/>")' />
					</td>	
				</tr>
			</s:iterator>
			</table>
		</s:form>
					<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
	
		//跳至增加页
		function addImage()
		{
			window.location.href="/cms/productIdUpholdAction_toAddUphold.action";
		}
		
		//删除图片
		function deleteImage(delSku)
		{
			var ok=confirm("确定删除吗？删除后将不能恢复！");
	   		if(ok==false)
	   		{
	   			return ;
	   		}
	   		else
	   		{
				window.location.href="/cms/productIdUpholdAction_delUphold.action?delSku="+delSku;
			}
		}
	</script>
</html>
