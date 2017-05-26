<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>手机端应用详细</title>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 
<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;border-bottom:1px solid #c7e3f1;vertical-align:middle;font-size:14;color:#333;margin:0}
.listTitle span{padding-left:20px;height:30px;line-height:30px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
.list_table tr input
			{
				width:220px;
			}
</style>
			
	</head>
	<body>
	<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;手机端应用列表&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;详细手机端应用
	  </span>
</div>

		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="dateForm" id="dateForm" method="post" enctype="multipart/form-data">
			<!-- 数据列表区域 -->
			<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">手机端应用信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>手机端应用名称：</td>
		<td width="80%">
			<s:property value='cmsAppManage.name'/>
		</td>
	</tr>
 	<tr> 
		<td width="20%" align="right"><font color="red">*</font>版本名：</td>
		<td width="80%">
		<s:property value='cmsAppManage.version'/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>版本号：</td>
		<td width="80%">
		<s:property value='cmsAppManage.versioncode'/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>下载地址：</td>
		<td width="80%"> 
			<s:property value='cmsAppManage.download'/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>是否强制下载：</td>
		<td width="80%"> 
			<s:if test="cmsAppManage.isCoerce == 2">
			否
			</s:if>
			<s:elseif test="cmsAppManage.isCoerce == 1">
				是
			</s:elseif>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>手机端平台：</td>
		<td width="80%"> 
			<s:property value='cmsAppManage.terrace'/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">升级的功能列表信息：</td>
		<td width="80%"> 
			<s:property value='cmsAppManage.remark'/>
		</td>
	</tr>
</table>
			<!-- 底部 按钮条 -->
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						
						<input class="backBtn" type="button" value="" onclick="gotoList()">
					</td>
				</tr>
			</table>
		</s:form>
		
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">

    //返回
	   function gotoList(){
	  	var pageForm= window.document.getElementById("dateForm");
	 	pageForm.action="/cms/cmsAppManageAction_queryPageList.action";
	 	pageForm.submit();
	  }
</script>
</html>

