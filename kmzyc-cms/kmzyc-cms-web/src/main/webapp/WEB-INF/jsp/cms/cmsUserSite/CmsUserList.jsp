<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>站点列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script>
      //详情
	function details(userId){

			//location.href="/cms/cmsSite_gotoDetail.action?siteId="+siteId;
			location.href="/cms/cmsSite_gotoDetail.action?userId="+userId;
		}

     //授权
	function popSelectSite(userId){
	
	  //dialog("选择窗口","iframe:/sys/listSysUserPop.action?siteId="+siteId,"900px","530px","iframe");  
	    dialog("选择站点","iframe:/cms/cmsSite_popUPquery.action?userId="+userId,"900px","530px","iframe");
		}	
	function closeOpenSiteDiv(){
      closeThis();
       var cmsUserSiteForm=document.getElementById("cmsUserForm");
                           cmsUserSiteForm.action="/cms/cmsSite_querySiteUserPage.action";
                  		   cmsUserSiteForm.submit();
	}			         	 
	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'站点管理'" scope="request" />
		<s:set name="name" value="'站点授权'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="cmsUserForm" id="cmsUserForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsSite_querySiteUserPage.action" method="post">

				<table width="98%" align="center"  border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td width="60%" valign="middle" colspan="2">
							<input type="hidden" id="checkeds" name="checkeds"
								value="<s:property value='checkeds'/>" />
						</td>
					</tr>
					<td align="left" width="22%">
						用户名：<s:property value="cmsUse.userName"/>
						<input name="cmsUser.userName" type="text"
							value="<s:property value="cmsUser.userName"/>">
					</td>
					<td align="left" width="22%">
						真实姓名：
						<input name="cmsUser.userReal" type="text"
							value="<s:property value="cmsUser.userReal"/>">
					</td>
					<td align="right">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>

					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th>
							用户名
						</th>
						<th>
						          站点名
						</th>
						<th>
							真实姓名
						</th>
						<th>
						          联系电话
						</th>
						<th>
							性别
						</th>
						<th>
							操作
						</th>
					</tr>
					
					<s:iterator id="cmsUser" value="page.dataList">
						<tr>
							<TD>
								<s:property
										value="userName" />								
							</TD>
							<TD>
							<s:iterator id="siteList" value="cmsSiteList">
							<s:if test="#cmsUser.userId==#siteList.userId">
					                <s:property value="siteName" />&nbsp; 
					          </s:if>
					          </s:iterator>
				               </TD>
							<TD>
								<s:property value="userReal" />
							</TD>
							<TD>
								<s:property value="userPhone" />
							</TD>
							<TD>
								<s:if test="userSex==0">
				女
				</s:if>
								<s:if test="userSex==1">
				男
				</s:if>
							</TD>
							<td>

								<img title="详情" style="cursor: pointer;"
									src="/etc/images/icon_detail.png"
									onclick='details(<s:property value="userId"/>)' />
								<img title="授权" style="cursor: pointer;"
									src="/etc/images/icon_select.png"
									onclick='popSelectSite(<s:property value="userId"/>)' />
							</td>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'cmsUserForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

