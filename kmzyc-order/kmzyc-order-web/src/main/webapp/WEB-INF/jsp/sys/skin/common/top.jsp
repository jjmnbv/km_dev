<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<head>
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
<style>
.headerMenu { BORDER: #cecece 3px solid; PADDING: 5px 15px; height: 30px; line-height: 30px; overflow: hidden; COLOR: #4c4c4c; BACKGROUND: #FFFFFF; TEXT-DECORATION: none }
.headerMenu:hover { BACKGROUND: #999; COLOR: black; TEXT-DECORATION: none }
.unHeaderMenu { BORDER: #cecece 1px solid; PADDING: 5px 15px; height: 20px; line-height: 20px; overflow: hidden; COLOR: #4c4c4c; BACKGROUND: #e6e6e6; TEXT-DECORATION: none }
.unHeaderMenu:hover { BACKGROUND: #999; COLOR: black; TEXT-DECORATION: none }
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<div id="header_common">
  <table width="100%" height="60" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="180" align="center" valign="middle" rowspan=2><font color="#FFFFFF" size="6">logo</font></td>
      <td align="right" valign="middle" style="padding-right: 10px"> 上次登录：
        <s:date name="#session['sysUser'].userLasttime" format="yyyy-MM-dd" />
        &nbsp;&nbsp;
        | <a href="/sys/gotoSysMain.action" target="main" style="color:#ffffff" >桌面</a> | <a href="/sys/gotoSysUserMyInfo.action?userId=<s:property value="#session['sysUser'].userId"/>" target="main" style="color:#ffffff" >我的信息</a> | <a href="/sys/gotoSysUserPwdModify.action?userId=<s:property value="#session['sysUser'].userId"/>" target="main" style="color:#ffffff">修改密码</a> | <a href="/sys/logoutSysUser.action" target="_top" style="color:#ffffff">退出系统</a> <span class="red_a">
        <s:property value="#session['sysUser'].userReal" />
        </span></td>
    </tr>
    <tr>
      <td><div style="margin-left:20px;">
          <s:iterator id="menuiterator" value="dataList" status="idx">
            <s:if test="menuId==#session['sysUser'].headMenuId"> <a class=headerMenu href="<s:property value='menuLink'/>" onClick="changeHeadMenu(<s:property value='#idx.index+1'/>);" id="head_<s:property value='#idx.index+1'/>" target="left">
              <s:property value="menuName"/>
              </a> </s:if>
            <s:else> <a class=unHeaderMenu href="<s:property value='menuLink'/>" onClick="changeHeadMenu(<s:property value='#idx.index+1'/>);" id="head_<s:property value='#idx.index+1'/>" target="left">
              <s:property value="menuName"/>
              </a> </s:else>
          </s:iterator>
        </div></td>
    </tr>
  </table>
</div>
<script type="text/javascript">
	function changeHeadMenu(indexId){
		document.getElementById("head_"+indexId).className="headerMenu";
		for(var i=1;i<=<s:property value='dataList.size()'/>;i++){
			if(i!=indexId){
				document.getElementById("head_"+i).className="unHeaderMenu";
			}
		}
	}
</script>
</body>
</html>
