<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>调查记录管理</title>
			<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
    /** 删除调查记录信息  **/
    function deleteSelected(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;
                        obj_cehcked = obj[i];
                    }
                }
                 if (count == 0) {
                        alert("请选择要删除的数据。");
                        return false;
                 }else if(confirm('是否确认删除?')==true){ 
                          document.newsCustomerSurveyForm.action="/newsCustomerSurvey/customerSurvey_detele.action ";
                          document.newsCustomerSurveyForm.submit();
                 }
    }
    /**  进入新增客调查记录信息页面  **/
    function gotoAdd(){
         document.newsCustomerSurveyForm.action="/newsCustomerSurvey/customerSurvey_preAdd.action ";
         document.newsCustomerSurveyForm.submit();
    }
      /** 进入调查记录信息页面  **/
    function gotoDetail(id){
         location.href="/newsCustomerSurvey/customerSurvey_preDetail.action?surveyId="+id;
    } 
    /** 进入修改调查记录信息页面  **/
    function edit(id){
         location.href="/newsCustomerSurvey/customerSurvey_preUpdate.action?surveyId="+id;
    }
    
    /** 单条删除调查记录信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
          document.newsCustomerSurveyForm.action="/newsCustomerSurvey/customerSurvey_detele.action?customerSurveyIds="+id;
          document.newsCustomerSurveyForm.submit(); 
         }
    }
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户信息'" scope="request" />
		<s:set name="name" value="'调查记录'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="newsCustomerSurveyForm" onsubmit=" return checkAllTextValid(this)"
			action="/newsCustomerSurvey/customerSurvey_pageList.action" method="post">
			<s:token/>
			<!-- 查询条件 -->
		    <table width="98%" align="center"  border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				
				<tr width="60%">
					<td >
						客户姓名：
						<input name="name" type="text"  
							value="<s:property value='newsCustomerSurvey.name'/>">
					</td >
					<td align="right"  >
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;
                        <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onClick="deleteSelected('maintenaceIds');">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='allbox'  onclick="checkAll(this,'customerSurveyIds')">
					</th>
					<th>
						调查人
					</th>
					<th>
						客户姓名
					</th>
					<th>
						调查内容
					</th>
					<th>
						使用情况
					</th>
					<th>
						调查时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="customerSurveyIds"
								value='<s:property value="customerSurveyId"/>'>
						</td>
						<td>
							<s:property value="userName" />
						</td>
						<td>
							<s:property value="name" />
						</td>
						<td>
						<s:if test="content.length()>18">
   						 <s:property value='content.substring(0,18)'/>...
 						</s:if>   
						 <s:else>  
						 <s:property value="content" />    
 						</s:else>
							
						</td>
						<td>
						<s:if test="condition.length()>18">
   						 <s:property value='condition.substring(0,18)'/>...
 						</s:if>   
						 <s:else>      
     					<s:property value='condition'/>
 						</s:else>
						</td>
						<td>
							<s:date name="recordDate" format="yyyy-MM-dd"/>
						</td>
						<td>
						     <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="customerSurveyId"/>)" />
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="edit(<s:property value="customerSurveyId"/>)" />
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="customerSurveyId"/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'newsCustomerSurveyForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
	</body>
	<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

