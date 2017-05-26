<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
	<head>
		<title>积分明细</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
	   	<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		 <script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		 <Script src="/etc/js/97dater/WdatePicker.js"></Script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:if test="%{showType==null}">
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'账户管理'" scope="request" />
		<s:set name="son_name"  value="'积分明细'"></s:set>
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		</s:if>
		<div  style="height:90%;overflow-y:auto; " >
		<s:form name="scoreInfoForm"  onsubmit=" return checkAllTextValid(this)"
			action="/accounts/scoreInfo_queryPageList.action" method="post">
			<!-- 查询条件 -->
			<table width="98%" align="center" height="50" border="0"  class="content_table" cellpadding="5" cellspacing="0">
				<tr>
						<s:if test="%{showType!=null}">
						<td width="22%">会员账号：<s:property value='scoreInfo.loginAccount'/>
						<input name="loginAccount" type="hidden"  readonly="readonly"
								value="<s:property value='scoreInfo.loginAccount'/>">
							
						<input name="loginId" type="hidden" 
							value="<s:property value='loginId'/>">	    
							
						<input name="showType" type="hidden" 
							value="<s:property value='showType'/>">
							</td>
							<td>总积分：<s:property value='#request.userTotalScore' /></td>
							<td>可用积分：<s:property value='#request.userAvlibalScore'/></td>	
						</s:if>
						<s:else>
							<td>会员账号：
						  		<input name="loginAccount" type="text" 
									value="<s:property value='scoreInfo.loginAccount'/>">
						   </td>
						   <td>积分明细类型：
						   <select name="scoreInfo.scoreType">
						   		<option value="" <s:if test='scoreInfo.scoreType==""'>selected="selected"</s:if> >
									全部
								</option> 
								<option value="1" <s:if test='scoreInfo.scoreType=="1"'>selected="selected"</s:if>>
									积累
								</option>
								<option value="0" <s:if test='scoreInfo.scoreType=="0"'>selected="selected"</s:if>>
									 消费
								</option>
							</select>
						   </td>
						 </s:else>
						   
				</tr>
				<tr>
				<s:if test="%{showType!=null}">
				<td>积分明细类型：
						   <select name="scoreInfo.scoreType">
						   		<option value="" <s:if test='scoreInfo.scoreType==""'>selected="selected"</s:if> >
									全部
								</option> 
								<option value="1" <s:if test='scoreInfo.scoreType=="1"'>selected="selected"</s:if>>
									积累
								</option>
								<option value="0" <s:if test='scoreInfo.scoreType=="0"'>selected="selected"</s:if>>
									 消费
								</option>
							</select>
						   </td>
				</s:if>
				<%-- <td>积分规则名称：
					<s:select list="#request.scoreRuleMap" listKey="key" listValue="value" headerKey="" headerValue="全部" name="scoreInfo.n_scoreRuleId">
					</s:select>
				</td> --%>
						   <td>添加时间：
						 		<input type="text" id="d523" class="Wdate" readonly="readonly" value="<s:date name = 'scoreInfo.d_createDateStart' format='yyyy-MM-dd HH:mm:ss' />" name="scoreInfo.d_createDateStart" onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d524\')}'})" />
						  		至
						  		<input type="text" id="d524" class="Wdate" readonly="readonly" value="<s:date name = 'scoreInfo.d_createDateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="scoreInfo.d_createDateEnd" onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}',maxDate:'%y-%M-%d %H:%m:%s'})" />
						  </td>
					<td align="center">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th>
						会员账号
					</th>
					<th>
						积分明细类型
					</th>
					<!-- <th>
						积分规则名称
					</th> -->
					<th>
						积分数
					</th>
					
					<th>
						添加时间
					</th>
				</tr>
				<s:if test="#request.isMenu=='true'">
					<tr>
						<td colspan="100">
							请输入具体条件进行查询，如果想查询全部数据请直接点击查询按钮
						</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td>
							<s:property value="loginAccount" />
						</td>
						<td>
						    <s:if test='scoreType=="1"'>积累</s:if>
							<s:elseif test='scoreType=="0"'>消费</s:elseif>
						</td>
						<%-- <td>
							<s:property value="discribe" />
						</td> --%>
						<td>
							<fmt:formatNumber value="${scoreNumber}" type="number" pattern="#0.00" minFractionDigits="0"/>
						</td>
						<td>
							<s:date name="d_createDate" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
				</s:else>
				
				
				
				
			</table>
			<s:if test="#request.isMenu!='true'">
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'scoreInfoForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
			</s:if>
		</s:form>
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

