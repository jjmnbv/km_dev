<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>手机验证</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
function  mobileValidate(id,mobileStatus,mobile)
{  
    if(!mobile){
        alert('验证手机不能为空!');
        }
    else{
			document.moblievalidateForm.action="/moblieEmailValidate/moblieEmailValidate_mobileValidateSubmit.action?moblieEmailValidate.loginId="+id+"&moblieEmailValidate.mobileStatus="+"1";

		  
			document.moblievalidateForm.submit();
    }
	 
  
 
 }
 
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'安全验证'" scope="request" />
		<s:set name="name" value="'手机验证'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="moblievalidateForm" method="post"
				action="/moblieEmailValidate/moblieEmailValidate_mobilePageList.action">
				<s:token/>
				<!-- 查询条件区域 -->
				<table width="98%" class="content_table" align="center"
					cellpadding="0" cellspacing="0">
				 
					<tr>
						<td width="100">
							账户号：
						</td>
						<td>
							<input name="moblieEmailValidate.loginAccount" type="text"
								value="<s:property value='loginAccount'/>">
						</td>
						<td>
							手机：
					 	<input name="moblieEmailValidate.mobile" type="text"
								value="<s:property value='mobile'/>">
						</td>
                     	<td >手机验证状态：<select name="mobileStatus">
		<option value=""  <s:if test='mobileStatus==""'>selected="selected"</s:if>>全部</option>
		<option value="0" <s:if test='mobileStatus=="0"'>selected="selected"</s:if> >未验证</option>
		<option value="1" <s:if test='mobileStatus=="1"'>selected="selected"</s:if>>已验证</option>

		</select></td>

						<td>
							<INPUT TYPE="submit" class="queryBtn" value="">
						</td>
					</tr>
				</table>

				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
					 
							<th>
						  账户号
						</th>
						<th>
						   手机
						</th>
						<th>
					     账号状态
						</th>
						<th>
						  手机验证状态
						</th>
						<th> 
						 创建日期
						</th>
					 
						<th>
							操作
						</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							 
								<td align="center">
								<s:property value="loginAccount" />
							</td>
							<td align="center">
								 <s:property value="mobile" />
							</td>
								<td align="center">
								<s:if test="#custiterator.Status==0">禁用</s:if>
								<s:if test="#custiterator.Status==1">可用</s:if>
							</td>
							
									<td align="center">
								<s:if test="#custiterator.mobileStatus==0">未验证</s:if>
								<s:if test="#custiterator.mobileStatus==1">已验证</s:if>
							</td>
							<td align="center">
								<s:date name="createDate"  format="yyyy-MM-dd HH:mm:ss"/>

							</td>
							<td align="center">
							<img title="手机验证" style="cursor: pointer;"src="/etc/images/icon_msn.gif"
							onclick='mobileValidate("<s:property value="loginId"/>","<s:property value="mobileStatus"/>","<s:property value="mobile"/>")'/>
							
								 
							</td>
						</tr>
					</s:iterator>
				</table>
				<table width="500" align="right">
					<tr>
						<td> 
							<s:set name="form_name" value="'moblievalidateForm'"
								scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

