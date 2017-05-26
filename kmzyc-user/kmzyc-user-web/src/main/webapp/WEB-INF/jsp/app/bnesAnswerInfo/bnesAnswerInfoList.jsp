<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
 <script type="text/javascript" src="/etc/js/dialog.js"></script>
 <script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<html>
	<head>
		<title>安全问题答案信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">


		<script type="text/javascript">
	/**  进入安全问题添加信息页面  **/
	function gotoAdd() { 
		document.bnesAnswerInfoForm.action = "/accounts/bnesAnswerInfo_preAdd.action";
		document.bnesAnswerInfoForm.submit();
	}

	function gotoSafeQuestion(){
		location.href="/accounts/safeQuestion_show.action";
	}
	    //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");
    
    
}
	
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'安全认证'" scope="request" />
		<s:set name="name" value="'安全问题'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
        <div  style="height:90%;overflow-y:auto; " >
		<s:form name="bnesAnswerInfoForm"   onsubmit="return checkAllTextValid(this)"
			action="/accounts/bnesAnswerInfo_pageList.action" 
			
			method="post">
			<!-- 查询条件 -->
			<table width="98%" align="center" height="100" border="0"
				class="content_table" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="6">
					
					<input type="button" value="问题答案"
							onclick="gotoAdd();">
							<input type="button" value="安全问题"
							onclick="gotoSafeQuestion();">
					</td>
				</tr>
				<tr>
					<td >
						账户号：
						<input name="bnesAnswerInfo.accountLogin" type="text"
							class="input_style"
							value="<s:property value='bnesAnswerInfo.accountLogin'/>">
					</td>
					<td >
						姓名：
						<input name="bnesAnswerInfo.name" type="text"
							class="input_style"
							value="<s:property value='bnesAnswerInfo.name'/>">
					</td>
					<td >
						安全问题：
						<select name="bnesAnswerInfo.safeQuestionId"  style="width:280px;">
							<option value="">请选择</option>
							<s:iterator value="safeList" id="safe">
								<s:if test="n_safe_question_id == bnesAnswerInfo.safeQuestionId">
									<option selected="selected" value="<s:property value='n_safe_question_id'/>"><s:property value="question_name"/></option>
								</s:if>
								<s:else>
									<option value="<s:property value='n_safe_question_id'/>"><s:property value="question_name"/></option>
								</s:else>
							</s:iterator>
						</select>
					</td>
					<td align="center">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" >
				<tr>
					<th align="center">
						账户号
					</th>

					<th align="center">
						姓名
					</th>
					<th align="center">
						安全问题
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList"
					var="bnesAnswer">
					<tr>
						<td align="center">
							<a href="#" onclick="queryAccountInfo(<s:property value="accountId"/>);"><s:property value="accountLogin"/></a>
						</td>

						<td align="center">
							<s:property value="name" />
						</td>
						
						<td align="center">
							<s:property value="question_name" />
						</td>
					</tr>
				</s:iterator>
			</table>





			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'bnesAnswerInfoForm'"
							scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>

		</s:form>
		</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>

