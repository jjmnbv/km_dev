<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>经验明细管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
    /** 删除经验明细   **/
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
                          document.bloodIntegralInfoForm.action="/bloodIntegralInfo/integralInfo_detele.action ";
                          document.bloodIntegralInfoForm.submit();
                 }
    }
    
    /** 单条删除经验明细  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/bloodIntegralInfo/integralInfo_detele.action?integralInfoIds="+id;
         }
    }
    //弹出 选择账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/userInfo/personalBasicInfo_preDetail.action?n_PersonalId="+id ,"900px","460px","iframe");
}

</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'账户管理'" scope="request" />
		<s:set name="name" value="'经验明细'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
			<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="bloodIntegralInfoForm" onsubmit=" return checkAllTextValid(this)"
			action="/bloodIntegralInfo/integralInfo_pageList.action" method="post">
			<!-- 查询条件 -->
			<table width="98%" align="center" border="0"
				class="content_table" cellpadding="0" cellspacing="0">
				<!--<tr>
					<td width="80%" valign="middle" colspan="2">
					<!--	<input class="addBtn" TYPE="button" value="" onclick="add();">
						&nbsp;&nbsp;-->
						<!--<input class="delBtn" type="button" value=""
							onclick="deleteSelected('integralInfoIds');">
					</td>
				</tr>-->
				<tr>
					<td>
						会员账号
						<s:if test="%{showType!=null}">
						   <input name="loginAccount" type="text" readonly
							value="<s:property value='bloodIntegralInfo.loginAccount'/>">
							
							 <input name=loginId type="hidden" 
							value="<s:property value='bloodIntegralInfo.loginId'/>">
							
							<input name="showType" type="hidden"
							value="<s:property value='showType'/>">
						</s:if>
						<s:else>
						   <input name="loginAccount" type="text"
							value="<s:property value='bloodIntegralInfo.loginAccount'/>">
						</s:else>
					</td>
					<td align="right">
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;
                        <input class="delBtn" type="button" value=""
							onclick="deleteSelected('integralInfoIds');">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='allbox' onClick="checkAll(this,'integralInfoIds')">
					</th>
					<th>
						会员账号
					</th>
					<th>
						经验数
					</th>
					<th>
						经验简述
					</th>
					<th>
						添加时间
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="integralInfoIds"
								value='<s:property value="integralInfoId"/>'>
						</td>
						<td>
							<s:property value="loginAccount" />
							
							
						</td>
						<td>
							<s:property value="integralNumber" />
						</td>
						<td>
							<s:property value="discribe" />
						</td>
						<td>
							<s:date name="createDate" format="yyyy-MM-dd" />
						</td>
						<td>
							<img title="删除" style="cursor: pointer;"
								src="/etc/images/icon_delete.png"
								onclick="deleteByKey(<s:property value="integralInfoId"/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'bloodIntegralInfoForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		
	</body><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

