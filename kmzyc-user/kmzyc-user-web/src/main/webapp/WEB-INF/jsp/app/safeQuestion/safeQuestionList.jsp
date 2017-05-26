<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <script src="/etc/js/dialog.js"></script>
<html>
	<head>
		<title>安全问题信息管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<script type="text/javascript">
	/**  进入安全问题添加信息页面  **/
	function gotoAdd() { 
		document.safeQuestionlForm.action = "/accounts/safeQuestion_toAddQuestionShow.action";
		document.safeQuestionlForm.submit();
	}

	/**进入问题修改页面**/
	function gotoUpdate(id){
		document.safeQuestionlForm.action="/accounts/safeQuestion_editQuestion.action?safeQuestion.n_safe_question_id="+id;
		document.safeQuestionlForm.submit();	
	}
	
	function delOne(id){

		if(confirm("你确定要删除吗？")==true){
			document.safeQuestionlForm.action="/accounts/safeQuestion_delSelected.action?questionId="+id;
	        document.safeQuestionlForm.submit();
		}	
	}
	

	/**删除所选的安全问题信息**/
	
	 function deleteSelected(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;  
                    }
                }
                 if (count == 0) {
                        alert("请选择要删除的数据。");
                       
                 }else if(confirm('是否确认删除?')==true){ 
                	
                          document.safeQuestionlForm.action="/accounts/safeQuestion_delSelected.action";
                          document.safeQuestionlForm.submit();
                 }  }
	

</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'安全认证'" scope="request" />
		<s:set name="name" value="'安全问题'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:auto; " >
		<s:form name="safeQuestionlForm" onsubmit="return checkAllTextValid(this)"
			action="/accounts/safeQuestion_show.action" method="post">
			<!-- 查询条件 -->
			<table width="98%" align="center" height="100" border="0"
				class="content_table" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="2">
						<input class="addBtn" type="button" value="" onclick="gotoAdd();">
						<input class="delBtn" type="button" value="" onclick="deleteSelected('questionId');">
							 <input class="backBtn" onclick="gotoList()" type="button" value=" ">
					</td>
				</tr>
				<tr>
					<td align="right">
						安全问题名称：
					</td>
					<td>
						<input name="safeQuestion.question_name" type="text"
							class="input_style"
							value="<s:property value='safeQuestion.question_name'/>">
					</td>


					<td align="center">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" bordercolor="#C1C8D2">
				<tr>
					<th align="center" width="5%">
						<input type='checkbox' name='allbox' onclick="checkAll(this,'questionId')">
					</th>
					<th align="center">
						问题名称
					</th>

					<th align="center">
						创建日期时间
					</th>

					<th align="center">
						修改日期
					</th>
					<th align="center">
						操作
					</th>

				</tr>
				<s:iterator id="custiterator" value="safeQuestionList"
					var="question">
					<tr>
						<td align="center" width="5%">
							<input type='checkbox' name='questionId'
								value="<s:property  value='#question.n_safe_question_id' />">
						</td>
						<td align="center">
							<s:property value="#question.question_name" />
						</td>


						<td align="center">
							<s:date   name="#question.d_create_date"  format="yyyy-MM-dd"/>
						</td>

						<td align="center">
							<s:date   name="#question.d_modify_date"  format="yyyy-MM-dd"/>
						</td>


						<td align="center">
							<img title="修改" style="cursor: pointer;"
								src="/etc/images/icon_modify.png"
								onclick="gotoUpdate(<s:property value="#question.n_safe_question_id" />)" />
							<img title="删除" style="cursor: pointer;"
								src="/etc/images/icon_delete.png"
								onclick="delOne(<s:property value="#question.n_safe_question_id" />)">

						</td>
					</tr>
				</s:iterator>
			</table>





			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'safeQuestionlForm'"
							scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>

		</s:form>
		<SCRIPT LANGUAGE="JavaScript">
		function gotoList() {
			location.href = "/accounts/bnesAnswerInfo_pageList.action";
		}
	</SCRIPT>
	</div>
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>

