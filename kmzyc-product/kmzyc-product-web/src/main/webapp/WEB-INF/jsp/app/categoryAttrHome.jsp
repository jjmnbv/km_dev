<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类目属性管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
<!--
var setting = {
	view: {
		selectedMulti: false
	},
	data: {
		key: {
			name: "categoryName"
		},
		simpleData: {
			enable: true,
			idKey: "categoryId",
			pIdKey: "parentId",
			rootPId: 0
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};

var zNodes = <s:property value="categoryString" escape="false"/>;;

//点击类目
function zTreeOnClick(event, treeId, treeNode) {
	ifr.window.location.href = '/app/queryCategoryAttrList.action?categoryAttr.categoryId='+treeNode.categoryId;
	return false;
}

function closePopDiv(){
	closeThis();
	alert("保存完毕！");
	location.href="/app/queryCategoryList.action";
}
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}
//-->
</SCRIPT>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 标题条 -->
<div class="pagetitle">类目属性管理:</div>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2"
	   style="margin:auto; margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#F1F9FC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="#" onClick="expandAll(true)">展开所有</a> | <a href="#" onClick="expandAll(false)">关闭所有</a></p>
				<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="550" src="" frameBorder="no"
					scrolling="no" style="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>
</body>
</html>