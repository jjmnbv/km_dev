<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<title>专家经验规则管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
    /** 删除专家经验规则信息 **/
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
                          document.bloodIntegralRuleForm.action="/bloodIntegralRule/integralRule_detele.action ";
                          document.bloodIntegralRuleForm.submit();
                 }
    }
    /**  进入新增专家经验规则信息页面  **/
    function gotoAdd(){
         document.bloodIntegralRuleForm.action="/bloodIntegralRule/integralRule_operateAdd.action";
         document.bloodIntegralRuleForm.submit();
    }
    
    /** 进入修改专家经验规则信息页面 **/
    function editScoreRule(id){
         location.href="/bloodIntegralRule/integralRule_preUpdate.action?integralRule_Id="+id;
    }
     /** 单条删除专家经验规则信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
         document.bloodIntegralRuleForm.action="/bloodIntegralRule/integralRule_detele.action?integralRuleIds="+id;
         document.bloodIntegralRuleForm.submit();
         }
    }
</script>
</head>
<body>
<!-- 标题 -->
<s:set name="parent_name" value="'客户成长'" scope="request"/>
<s:set name="name" value="'专家经验'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form  name="bloodIntegralRuleForm" onsubmit=" return checkAllTextValid(this)"  action="/bloodIntegralRule/integralRule_queryPageList.action"  method="post">
<!-- 按钮条 -->
<s:token/>
<table width="98%" align="center" border="0"	class="content_table"　cellpadding="0" cellspacing="0">

	<tr width="60%">
	    <td  >经验规则简述：
		     <input name="bloodIntegralRule.discribe" type="text" value="<s:property value='bloodIntegralRule.discribe'/>">
		</td>
		<td  align="right">
			<INPUT TYPE="submit" class="queryBtn" value=" ">
                        <INPUT class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
			<input class="delBtn" type="button" value=""  onclick="deleteSelected('integralRuleIds');">
		</td>
	</tr>
</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'integralRuleIds')">
		</th>
		<th  width="10%">专家经验值编号</th>
		<th  width="60%">规则名称</th>
		<th  width="20%">经验数量</th>
		<th  width="10%">操作</th>
	</tr>
	<s:iterator id="custiterator"  value="page.dataList">
	<tr>
	    <td  width="5%">
		    <input type="checkbox"  name="integralRuleIds"  value='<s:property value="integralRuleId"/>'>
		</td>
		<td >
		     <s:property value="code"/>
		</td>
		<td >
		   <s:property value="discribe"/>
		</td>
		<td>
		     <s:property value="integralnumber"/>
		</td>
		<td>
		 <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editScoreRule(<s:property value="integralRuleId"/>)"  /> &nbsp;&nbsp;
		 <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="integralRuleId"/>)" />
		</td>
	</tr>
	</s:iterator>
</table>
<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'bloodIntegralRuleForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
	</s:form>
	
</body><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

