<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<script language="JavaScript" type="text/javascript" src="/etc/js/dtree/dtree.js" ></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:if test='rtnMessage.equals("updateOK")'>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
            alert("菜单修改成功!");
		//-->
		</SCRIPT>
</s:if>
 <s:if test='rtnMessage.equals("addOK")'>
		<SCRIPT LANGUAGE="JavaScript">
		<!--
            alert("菜单增加成功!");
		//-->
		</SCRIPT>
</s:if>

<!-- 标题条 -->
<div class="pagetitle">菜单管理:</div>


<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 增加顶级菜单 " onclick="gotoAdd();">
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
					<!--
					d = new dTree('d');
					d.add(0,-1,'菜单列表');
					<s:iterator id="gditerator1" value="dataList">
					d.add(<s:property value="menuId"/>,<s:if test="menuUpid==null||menuUpid==''">0</s:if><s:else><s:property value="menuUpid"/></s:else>,'<s:property value="menuName"/>','gotoSysMenuUpdate.action?menuId=<s:property value="menuId"/>','修改菜单-<s:property value="menuName"/>', 'ifr');
					</s:iterator>
					document.write(d);
			
					//-->
				</script>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="400" src="" frameBorder="no" scrolling="no" STYLE="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>

<SCRIPT LANGUAGE="JavaScript">
<!--

function gotoAdd(){
	dialog("新增一级菜单","iframe:gotoSysMenuAdd.action?menuUpid=&menuLv=1","500px","310px","iframe");
}

function zAlert(){
	Dialog.alert("你点击了一个按钮");
}

function gotodelete(menuId){
	var _isdel=document.getElementById("isdel_"+menuId).value;
    if(_isdel==0){
		alert("下面有子菜单，不能删除！！！");
		return false;
	}
	else if(confirm("你确定要删除选中的数据？")==true){
		location.href="/sys/gotoSysMenuDelete.action?menuId="+menuId;

	}
}

function closePopDiv(){
	closeThis();
}

function closePopDiv2(){
	closeThis();
	alert("保存完毕！");
	location.href="/sys/listMenu.action";
}

-->
</SCRIPT>

</BODY>
</HTML>

