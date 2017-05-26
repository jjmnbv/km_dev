<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品类目管理</title>
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
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
<!--
var setting = {
	view: {
		selectedMulti: false
	},
	edit: {
		enable: true
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
	check:{
		enable: true,
		nocheckInherit: true
	},
	callback: {
		onClick: zTreeOnClick
	}
};

var zNodes = <s:property value="phyCategorys" escape="false"/>;;
var zObj;
$(document).ready(function(){
	zObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$(zObj.getNodes()).each(function(i){
		changeViewChecked(this);	
	});
	
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}
function zTreeOnClick(event, treeId, treeNode){
	if(treeNode.level!=2){
		zObj.expandNode(treeNode, true, false, true);
		return;
	}
}

function changeViewChecked(node){
	if(node.level != 0){
		node.nocheck = true;
	}
	if(node.children){
		$(node.children).each(function(i){
			changeViewChecked(this);
		});
	}
}
function saveCategorys(){
	var nodes = zObj.getCheckedNodes(true);
	if(nodes.length == 0){
		alert("请选择要复制的类目！");
		return;
	}
	var copyCategorys = "";
	$(nodes).each(function(i){
		copyCategorys += this.categoryId + ",";
	});
	copyCategorys = copyCategorys.substring(0,copyCategorys.length-1);
	$.ajax({
	   type: "POST",
	   url: "/app/saveCopyCategorys.action",
	   data: {copyCategoryIds : copyCategorys},
	   success: function(msg){
		   if(msg == "success"){
				alert("操作成功！");
				parent.closePopDiv();
		   }else{
				alert("系统发生错误，操作失败！");
		   }
	   }
	});
	
}


//-->
</SCRIPT>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btngreen" TYPE="button" value="+ 复制 " onclick="saveCategorys();">
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#FFFFCC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="#" onclick="expandAll(true)">展开所有</a> | <a href="#" onclick="expandAll(false)">关闭所有</a></p>
					<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	</tr>
</table>

</BODY>
</HTML>

