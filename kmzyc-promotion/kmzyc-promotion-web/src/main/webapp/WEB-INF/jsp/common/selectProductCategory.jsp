<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<title>产品类目选择</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

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
</head>
<body>

<!-- 标题条 -->
<div class="pagetitle">选择类目:</div>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#FFFFCC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="#" onclick="expandAll(true)">展开所有</a> | <a href="#" onclick="expandAll(false)">关闭所有</a></p>
			
					<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	</tr>
	<tr>
		<td align="center"><INPUT class="saveBtn" TYPE="submit"
				value="" onclick="return save();">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<input type="button" class="backBtn" onClick="javascript:parent.myDialog.close();" />
		</tr>
</table>
<SCRIPT type="text/javascript">

var setting = {
	
	view: {
		selectedMulti: false
	},
	check: {
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
	callback: {
		//beforeCheck: beforeCheck,
		//onCheck: onCheck
	}
	
};

var zNodes = <s:property value="categoryString" escape="false"/>;;

function createTree() {
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
}
$(document).ready(function(){
	createTree();
	if (art.dialog.data('selectedIds')) {
		var data = art.dialog.data('selectedIds');
		hanld(data);
		//document.getElementById('aInput').value = art.dialog.data('test');// 获取由主页面传递过来的数据
	};

});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}
function save(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	try{
		parent.receiveProductCategory(nodes);
	}catch (e){
		alert('请父级页面实现receiveProductCategory(nodes)方法');
	}
}

function hanld(paramsIds){
	if(paramsIds=='')return;
	var ids = paramsIds.split(",");
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getNodes();
	for(var i=0;i<ids.length;i++){
		var id = ids[i];
		if(id){
			var node = treeObj.getNodeByParam("categoryId",id,null);
			if(node){
				treeObj.checkNode(node, true, true); 
			}
		}
	}
}
//-->
</SCRIPT>
</BODY>
</HTML>

