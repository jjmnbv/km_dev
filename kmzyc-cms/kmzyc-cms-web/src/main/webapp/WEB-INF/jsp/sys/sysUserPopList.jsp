<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.sys.model.SysUser"%>
<%@ page import="com.pltfm.sys.model.SysDept"%>
<%@ page import="com.pltfm.sys.bean.SysDeptBean"%>
<%@ page import="com.pltfm.sys.bean.SysUserBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="/sys/listSysUserPop.action" method="POST"  namespace='/sys' id="frm" name='frm'>
<!-- 标题条 -->
<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
             <input name="siteId" type="hidden" value="<s:property value='siteId'/>"> 
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>


<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>部门：</td>
		<td>
			<%
			  //获取部门
			  SysDeptBean deptbean = SysDeptBean.instance();
			  java.util.List deptList =  deptbean.getSysDeptList(new SysDept());
			  request.setAttribute("deptList",deptList);
			%>
			<s:select name="deptId" emptyOption="true" list="#request.deptList" listKey="deptId" listValue="deptName" value="%{model.deptId}"/>
			<input type="hidden" name="types"
							value="1" id="types" />
		</td>
		<td align="right">用户名：</td>
        <td>
		    <input name="userName" type="text" value="<s:property value='model.userName'/>">
		</td>
		<td align="right">真实姓名：</td>
		<td>
		     <input name="userReal" type="text" value="<s:property value='model.userReal'/>">
		</td>
		<td>
			<INPUT TYPE="button" onclick="chSubmit()" class="button-blue-1" value=" 查询 ">
			 <input  name="pageNo1" id="pageNo1" type="hidden" value="0">
			 			<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
			<INPUT TYPE="button" onclick="chUsers()" class="button-blue-1" value=" 保存 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">	    
	    <input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'delId')">   
		</th>
		<th align="center" width="15%">用户名</th>
		<th align="center" width="15%">真实姓名</th>
		<th align="center" width="10%">联系电话</th>
		<th align="center" width="10%">性别</th>
		<th align="center" width="20%">所属部门</th>
		<th align="center" width="15%">岗位角色</th>
	</tr>
	<% SysUserBean userbean = SysUserBean.instance(); %>
	<s:iterator id="custiterator" value="page.dataList">
	<%SysUser list_obj = (SysUser)request.getAttribute("custiterator");%>
	<tr>
	    <td align="center" width="5%">
	    <s:if test="flagUser!=1">
	       <input type="checkbox" name="delId" value='<s:property value="userId"/>' onclick="checkpromotionId(this);">
		    </s:if>
		</td>
		<td align="center"><s:property value="userName"/></td>
		<td align="center"><s:property value="userReal"/></td>
		<td align="center"><s:property value="userPhone"/></td>
		<td align="center">
			<%
			  String userSex = list_obj.getUserSex();  //性别
			%>
			<%if("0".equals(userSex)){%>女<%}else if("1".equals(userSex)){%>男<%}else{%>不详<%}%>
			<!-- <%=StaticParams.getParamNameByCd("all","custSex",userSex)%>  -->
		</td>
		<td align="center">
		    <%
			  //部门名称获取
			  SysDept dept = deptbean.getSysDeptDetail(list_obj.getDeptId());
			  String deptname ="";
			  if(dept!=null) deptname=dept.getDeptName();
			%>
			<%=deptname%>
		</td>
		<td align="center">
<%
            SysUser userRole = userbean.getSysUserRoleDetail(list_obj.getUserId());
			String roleNameStr = userRole.getRoleName();
%>
            <%=roleNameStr%>
		</td>
	</tr>
	</s:iterator>
</table>

<!-- 分页按钮区 -->
<table  width="98%" align="center" cellpadding="0" cellspacing="0">
    <tr>
	    <td>
			<%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
		</td>
	</tr>
</table>

<br><br>


</s:form>
<SCRIPT LANGUAGE="JavaScript">
   /** 授权用户信息  */
   function chUsers(){   
    var obj = document.getElementsByName("delId");
		var objLen = obj.length;
		var i;
		var k = 0;
		for (i = 0; i < objLen; i++) {
			if (obj[i].checked == true) {
				k = k + 1
				break;
			}

		}
		if (k == 0) {
			alert("请选择要授权的用户！");
			return false;
		}
    $.ajax({
                cache: true,
                type: "POST",
                url:"/cms/cmsSite_addUser.action",
                data:$('#frm').serialize(),// 你的formid
                async: false,
                error: function(request) {
                    alert("授权异常");
                },
                success: function(data) {
                        parent.closeOpenSiteDiv();
                }
            });  
    }
    
    
     function chSubmit(){   
       document.getElementById("pageNo1").value=1;
       document.getElementById('frm').submit();
      }
</SCRIPT>

</BODY>
</HTML>
