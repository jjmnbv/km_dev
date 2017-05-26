<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="/etc/js/dtree/dtree.js" ></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="/etc/js/sys/sysDeptList.js" ></script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'系统配置'" scope="request" />
<s:set name="name" value="'部门管理'" scope="request" />
<s:set name="son_name" value="'部门列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form action="listSysDept.action" method="POST"  namespace='/sys'>
<input type="hidden" id="deptList" name="deptList" value="<s:property value='deptList' />" />
<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 增加一级部门 " onclick="gotoAdd();">
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#FFFFCC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a></p>
			
				<script type="text/javascript">
					d = new dTree('d');
					d.add(0,-1,'部门列表');
					<s:iterator id="gditerator1" value="deptList">
					d.add(<s:property value="deptId"/>,<s:if test="upDeptid==null||upDeptid==''">0</s:if><s:else><s:property value="upDeptid"/></s:else>,'<s:property value="deptName"/>(<s:property value="deptCd"/>)','gotoSysDeptUpdate.action?deptId=<s:property value="deptId"/>','修改部门-<s:property value="deptName"/>', 'ifr');
					</s:iterator>
					document.write(d);
				</script>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="400" src="" frameBorder="no" scrolling="no" STYLE="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>
<br>
</s:form>
<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>

