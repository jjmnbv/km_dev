<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品类目管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

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
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
<SCRIPT type="text/javascript">
<!--
var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		enable: true,
		removeTitle: "删除类目",
		renameTitle: "编辑类目",
		showRemoveBtn: setRemoveBtn,
		showRenameBtn: setRemoveBtn
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
		beforeClick: beforeClick,
		beforeDrag: beforeDrag,
		beforeRemove: beforeRemove,
		beforeEditName: beforeEditName
	}
};

var zNodes = <s:property value="categoryString" escape="false"/>;;
function beforeDrag(treeId, treeNodes) {
	return false;
}
//修改类目
function beforeEditName(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.cancelSelectedNode();
	treeObj.selectNode(treeNode);
	ifr.window.location.href = '/app/showCategory.action?category.categoryId='+treeNode.categoryId;
	return false;
}
//删除类目
function beforeRemove(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.cancelSelectedNode();
	treeObj.selectNode(treeNode);
	if(treeNode.isParent?confirm("你确定要删除类目["+treeNode.categoryName+"]及其子类目？") 
			: confirm("你确定要删除类目["+treeNode.categoryName+"]？")){
		location.href="/app/deleteCategory.action?category.categoryId="+treeNode.categoryId+"&category.isPhy="+treeNode.isPhy;
	}
	return false;
}


function addHoverDom(treeId, treeNode) {
	
	if(treeNode.level == 2){
		return;
	}
	if(treeNode.categoryId == 1 || treeNode.categoryId == 2 || treeNode.categoryId == 3){
		
	}else{
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加子类目' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			treeObj.cancelSelectedNode();
			treeObj.selectNode(treeNode);
			var categoryName = encodeURI(treeNode.categoryName);
			categoryName = encodeURI(categoryName);
			
			ifr.window.location.href = '/app/showCategory.action?category.parentId='+treeNode.categoryId+
                    '&category.isPhy='+treeNode.isPhy+
                    '&category.categoryCode='+(treeNode.categoryCode != undefined ? treeNode.categoryCode : "" )+
                    '&category.parentName='+categoryName;
		});
	}
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};
//增加类目
function gotoAdd(arg){
	popDialog("/app/gotoAddFirstLevelCategory.action?type="+arg,"新增顶级类目","700px","400px");
}

function closePopDiv(){
	closeThis();
	location.reload();
	//document.location.href = "/app/queryCategoryList.action?para=isFirst&type="+$("#type").val();
}
function beforeClick(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.cancelSelectedNode();
	treeObj.selectNode(treeNode);
}

var zObj;

$(document).ready(function(){
	zObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}

function setRemoveBtn(treeId, treeNode) {
	return !(treeNode.categoryId == 1 || treeNode.categoryId == 2 || treeNode.categoryId == 3);
}

function gotoEditCondition(){
	var nodes = zObj.getSelectedNodes();
	
	if(nodes.length==0){
		alert("请选择一个节点！");
		return;
	}
	
	popDialog("/app/gotoEditCategoryCondition.action?category.categoryId="+nodes[0].categoryId,"编辑运营类目条件","900px","510px");
}

function gotoCopyCategorys(){
	popDialog("/app/gotoCopyCategorys.action","编辑运营类目条件","450px","510px");
	
}

//-->
</SCRIPT>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 标题条 -->
<div class="pagetitle" style="margin: 12px 0px 6px 18px;
    color: #0771D4; font-weight:700;
    font: bold 16px "宋体"; "><s:if test='type == "busi"'>运营</s:if><s:if test='type == "phy"'>物理</s:if>类目管理:</div>

<input type="hidden" id="rtnMsg" value="<s:property value='message.content' />" />
<input type="hidden" id="type" value="<s:property value="type" />" />
<s:if test='!message.content.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
		alert(document.getElementById("rtnMsg").value);
	//-->
	</SCRIPT>
</s:if>

<!-- 按钮条 -->
<table width="98%" class="content_table"  align="center" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <input class="btn-custom " TYPE="button" value="+ 增加顶级类目 " onclick="gotoAdd('<s:property value="type" />');">
            <s:if test='type == "busi"'>
            	<input class="btn-custom" TYPE="button" value="+ 编辑条件 " onClick="gotoEditCondition();">
            	<input class="btn-custom" TYPE="button" value="+ 从物理类目中选择数据 " onClick="gotoCopyCategorys();">
            </s:if>
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1"
       bordercolor="#C1C8D2" style="margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#F1F9FC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p>
                    <a href="#" onClick="expandAll(true)">展开所有</a> |
                    <a href="#" onClick="expandAll(false)">关闭所有</a>
                </p>
                <ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="550" src=""
                    frameBorder="no" scrolling="no" style="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>

</BODY>
</HTML>

