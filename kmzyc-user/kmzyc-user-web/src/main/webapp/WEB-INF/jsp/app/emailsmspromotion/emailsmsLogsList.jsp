<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送短信记录</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>
<!-- 标题条 -->
		<s:set name="parent_name" value="'推广短信'" scope="request" />
		<s:set name="name" value="'发送记录'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
       <div  style="height:90%;overflow-y:auto; " >
<s:form  name="emailInfoForm" id="emailInfoForm"  onsubmit=" return checkAllTextValid(this)"
action="/emailsmslogs/emailsmslogs_pageListSmsLogs.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0">
					
					<tbody>
	<tr>
	<td align="right">推广标题：</td>
		<td>
		     <input name="emailSmsLog.title" type="text" value="<s:property value='emailSmsLog.title'/>">
		</td>
		
			<td align="right">手机号码：</td>
		<td>
		     <input name="emailSmsLog.mobile" type="text" value="<s:property value='emailSmsLog.mobile'/>">
		</td>
	 </tr>
	<tr>
		<td align="right">账户号：</td>
		<td>
		     <input name="emailSmsLog.loginName" type="text" value="<s:property value='emailSmsLog.loginName'/>">
		</td>
			<td align="right">推广类型：</td>
		<td>
		      <select name="emailSmsLog.promotionType">
									<option value="" <s:if test='emailSmsLog.promotionType==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="1" <s:if test='emailSmsLog.promotionType=="1"'>selected="selected"</s:if>>
										 短信
									</option>
									<!--删除邮件业务 <option value="2" <s:if test='emailSmsLog.promotionType=="2"'>selected="selected"</s:if>>
										 邮件
									</option> -->	 
							</select>
		</td>
		<td align="right">发送状态：</td>
		<td>
		<select name="status">
									<option value="" <s:if test='emailSmsLog.status==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="0" <s:if test='emailSmsLog.status=="0"'>selected="selected"</s:if>>
										 发送失败
									</option>
									<option value="1" <s:if test='emailSmsLog.status=="1"'>selected="selected"</s:if>>
										发送成功
									</option>
									<option value="2" <s:if test='emailSmsLog.status=="2"'>selected="selected"</s:if>>
								 未发送
									</option>
											<option value="2" <s:if test='emailSmsLog.status=="4"'>selected="selected"</s:if>>
							 发送异常
									</option>
							</select>
							</td>
		

		 
		<td align="center">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	<th align="center" >推广标题</th>
	<th align="center" >推广类型</th>
	<th align="center" >账户号</th>
	<!-- <th align="center" >邮件地址</th> -->
	<th align="center" >手机号码</th>
	<th align="center" >发送日期</th>
	<th align="center" >状态</th>
	</tr>
	<s:if test="#request.isMenu=='true'">
		<tr>
			<td colspan="100">
				请输入具体条件进行查询，如果想查询全部数据请直接点击查询按钮
			</td>
		</tr>
	</s:if>
	<s:else>
	<s:iterator id="emailInfoiterator"  value="page.dataList">
	<tr>
	
	<td align="center">
		     <s:property value="title"/>
		</td>
		
		<td align="center">
		
		<s:if test="promotionType==1">
		      短信
		     </s:if>
		     	<s:if test="promotionType==2">
		   邮件
		     </s:if>
		</td>
		<td align="center">
		     <s:property value="loginName"/>
		</td>
	<%-- 	<td align="center">
		   <s:property value="email"/>
		</td> --%>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<td align="center">
		<s:date name="sendDate"   format="yyyy-MM-dd"/>
		</td>
		<td align="center">
									<s:if test="status==1">
									发送成功
</s:if>
						<s:if test="status==0">
                                                  发送失败
</s:if>
					<s:if test="status==2">
                                                 未发送
                     	<s:if test="status==4">
                     	发送异常
                     	
                     	</s:if>
</s:if>
		    
		</td>
	</tr>
	</s:iterator>
	</s:else>
</table>

<s:if test="#request.isMenu!='true'">
<table width="98%" align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'emailInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:if>
</s:form>
</div>
</body>
</html>

