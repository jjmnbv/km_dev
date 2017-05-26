<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
    /** 删除专家信息  **/
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
                          document.personalbasicForm.action="/personalbasic/personalbasic_detele.action";
                          document.personalbasicForm.submit();
                 }
    }
    /**  进入新增专家信息页面  **/
    function gotoAdd(){
         document.personalbasicForm.action="/personalbasic/personalbasic_preAdd.action";
         document.personalbasicForm.submit();
    }

    /**  进入修改专家信息页面  **/
    function gotoUpdate(id){
         location.href="/personalbasic/personalbasic_preUpdate.action?personalId="+id;
    }
       /** 单条删除专家信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
         document.personalbasicForm.action="/personalbasic/personalbasic_detele.action?personalIds="+id;
         document.personalbasicForm.submit();
         }
    }
    
      /**  进入详情个人信息页面  **/
    function gotoDetail(id){
      location.href="/personalbasic/personalbasic_preDetail.action?personalId="+id;
    }
   /** 全选js  **/
      function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    /*var ct = ele.getAttribute("type");*/
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
    }
</script>
</head>
<body>
<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户资料'" scope="request" />
		<s:set name="name" value="'专家信息管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
			<div  style="height:90%;overflow-y:scroll; " >
<s:form  name="personalbasicForm" onsubmit=" return checkAllTextValid(this)"  method="post" action="/personalbasic/personalbasic_pageList.action">
<s:token/>
<!-- 查询条件区域 -->
<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="loginAccount" type="text" value="<s:property value='personalBasicInfo.loginAccount'/>">
		</td>
		<td align="right">姓名：</td>
		<td>
		     <input name="name" type="text" value="<s:property value='personalBasicInfo.contactsName'/>">
		</td>
		<td align="right">手机号码：</td>
		<td>
		     <input name="mobile" type="text" value="<s:property value='personalBasicInfo.mobile'/>">
		</td>
		<td align="right">证件号码：</td>
		<td>
		     <input name="certificateNumber" type="text" value="<s:property value='personalBasicInfo.certificateNumber'/>">
		</td>
		</tr>
		<tr>
		<td align="right">客户级别：</td>
		<td>
		     <select name="personalBasicInfo.n_LevelId">
		     <option value="">全部</option>
		     <s:iterator value="levelList" id="levelList">
		     	<s:if test="n_level_id == personalBasicInfo.n_LevelId">
					<option selected="selected" value="<s:property value='n_level_id'/>"><s:property value="level_name"/></option>
				</s:if>
				<s:else>
					<option value="<s:property value='n_level_id'/>"><s:property value="level_name"/></option>
				</s:else>
			</s:iterator>
			</select>
		</td>
		<td align="right">客户头衔：</td>
		<td>
			<select name="personalBasicInfo.n_RankId">
			<option value="">全部</option>
			<s:iterator value="rankList">
			<s:if test="personalBasicInfo.n_RankId==rankId">
			<option selected="selected" value="<s:property value='rankId'/>"><s:property value="rankName"/></option>
			</s:if>
			<s:else>
				<option value="<s:property value='rankId'/>"><s:property value="rankName"/></option>
			</s:else>
				
			</s:iterator>
			</select>
		</td>
		<td align="right" colspan="4">
			<INPUT TYPE="submit" class="queryBtn" value="">
             			<input class="addBtn" type="button" value="" onClick="gotoAdd();">
			 <input class="delBtn" type="button" value=""  onclick="deleteSelected('personalIds');">
		</td>
	</tr>
			</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick='checkAll(this)'>
		</th>
		<th align="center" >会员账号</th>
		<th align="center" >姓名</th>
		<th align="center" >年龄</th>
		<th align="center" >生日</th>
		<th align="center" >性别</th>
		<th align="center" >手机号</th>
		<th align="center" >证件号码</th>
		<th align="center" >客户级别</th>
		<th align="center" >客户头衔</th>
		<th align="center" >信用值</th>
		<th align="center" >可用积分</th>
		<th align="center" >总积分</th>
		<th align="center" >经验值</th>
		<th align="center" >账号状态</th>
		<th align="center" >操作</th>
		
	</tr>
	<s:iterator id="custiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="personalIds"  value='<s:property value="n_PersonalId"/>' />
		</td>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		     <s:property value="n_Age"/>
		</td>
		<td align="center">
		<s:date name="d_Birthday" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		<s:if test="#custiterator.sex==0">男</s:if>
		<s:if test="#custiterator.sex==1">女</s:if>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<td align="center">
		     <s:property value="certificateNumber"/>
		</td>
		<td align="center">
		     <s:property value="levelName"/>
		</td>
		<td align="center">
		     <s:property value="rankName"/>
		</td>
		<td align="center">
		     <s:property value="n_IndividualCreditValue"/>
		</td>
		
		<td align="center">
		     <s:property value="n_AvailableIntegral"/>
		</td>
		<td align="center">
		     <s:property value="n_TotalIntegral"/>
		</td>
		<td align="center">
		     <s:property value="n_EmpiricalValue"/>
		</td>
		<td align="center">
		<s:if test="#custiterator.login_Status==0">禁用</s:if>
		<s:if test="#custiterator.login_Status==1">可用</s:if>
		</td>
		<td>
		     <img title="详情" style="cursor: pointer;" src="/etc/images/icon_msn.gif"  onclick="gotoDetail(<s:property value="n_PersonalId"/>)" />
		<s:if test="#custiterator.login_Status==1">
			 <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="gotoUpdate(<s:property value="n_PersonalId"/>)" />
		    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="n_PersonalId"/>)" />
		</s:if>
		</td>
	</tr>
	
	</s:iterator>
</table>

<table width="500"  align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'personalbasicForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>  
</body>
   <!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</html>

