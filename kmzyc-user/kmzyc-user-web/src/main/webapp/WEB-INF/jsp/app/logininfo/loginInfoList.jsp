<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家登录信息管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
    /** 删除登录信息  **/
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
                          document.logininfoForm.action="/logininfo/logininfo_detele.action";
                          document.logininfoForm.submit();
                 }
    }
    /**  进入新增登录信息页面  **/
    function gotoAdd(){
         document.logininfoForm.action="/logininfo/logininfo_preAdd.action?customerTypeId=1";
         document.logininfoForm.submit();
    }

    /**  进入修改登录信息页面  **/
    function gotoUpdate(id){
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
         if (count == 1) {
        	 document.logininfoForm.action="/logininfo/logininfo_getRankInfoId.action?customerTypeId=1&loginId="+obj_cehcked.value;
             document.logininfoForm.submit();
         }else{
        	 alert("请选择一个条账户信息。");
         }
         
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
<s:form  name="logininfoForm"  method="post" action="/logininfo/logininfo_pageList.action?customerTypeId=1">
<!-- 标题条 -->
<div class="pagetitle">专家登录信息管理:</div>
<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <input class="btngreen" type="button" value="+ 新增 " onclick="gotoAdd();">
            <input class="btngreen" type="button" value="* 修改 " onclick="gotoUpdate('loginIds');">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('loginIds');">
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>

<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">会员账号：</td>
		<td>
		     <input name="loginInfo.loginAccount" type="text" value="<s:property value='loginInfo.loginAccount'/>">
		</td>		
		<td align="center">
			<INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick='checkAll(this)'>
		</th>
		<th align="center" >客户级别</th>
		<th align="center" >客户类别</th>
		<th align="center" >会员账号</th>
		<th align="center" >手机号</th>
		<th align="center" >电子邮箱</th>
		<th align="center" >账号状态</th>
		<th align="center" >创建日期</th>
		<th align="center" >创建人</th>
		<th align="center" >修改日期</th>
		<th align="center" >修改日人</th>		
	</tr>
	<s:iterator id="custiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="loginIds"  value='<s:property value="n_LoginId"/>' />
		</td>
		<td align="center">
		<s:iterator id="u"  value="userLevelList">
		<s:if test="n_level_id==n_LevelId">
		<s:property value="level_name"/>
		</s:if>
		</s:iterator>
		
		</td>
		<td align="center">
		 <s:if test="#custiterator.n_CustomerTypeId==0"> 
		个人
		</s:if>
		<s:elseif test="#custiterator.n_CustomerTypeId==1">
		专家
		</s:elseif>
		<s:elseif test="#custiterator.n_CustomerTypeId==2">
		商户
		</s:elseif>
		</td>
		<td align="center">
		     <s:property value="loginAccount"/>
		</td>
		<td align="center">
		     <s:property value="mobile"/>
		</td>
		<td align="center">
		     <s:property value="email"/>
		</td>
		<td align="center">
		 <s:if test="#custiterator.n_Status==0"> 
		启用
		</s:if>
		<s:elseif test="#custiterator.n_Status==1">
		禁用
		</s:elseif>
		</td>
		<td align="center">
		<s:date name="d_CreateDate" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		    <s:property value="n_Created"/>
		</td>
		<td align="center">
		<s:date name="d_ModifyDate" format="yyyy-MM-dd"/>
		</td>
		<td align="center">
		     <s:property value="n_Modified"/>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="500"  align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'logininfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>

