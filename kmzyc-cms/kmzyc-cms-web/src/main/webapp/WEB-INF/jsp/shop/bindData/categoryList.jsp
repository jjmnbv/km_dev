<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品类目管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css?3">
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


var setting = {
	view: {
		selectedMulti: false
	},
	check: {
		chkboxType : {"Y" : "ps", "N" : "ps" },
		enable: true
	},
	data: {
		key: {
			name: "categoryName"
		},
		simpleData: {
			enable: true,
			idKey: "shopCategoryId",
			pIdKey: "parentCategoryId",
			rootPId: 0
		}
	},
	callback: {
		onClick: zTreeOnClick
	}
};

var zNodes = <s:property value="nodes" escape="false"/>;
var treeObj;
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("treeDemo");

	var nodes = treeObj.getNodes();


	
	var arrayObj = new Array();
	arrayObj = $("#checkDataIdS").val().split(',');
	for (var i=0; i < arrayObj.length; i++) {
		var node = treeObj.getNodesByParam("shopCategoryId", arrayObj[i] , null);
		if(node[0] != undefined){
			treeObj.checkNode(node[0], true, false);
		}
	}
	
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}

function zTreeOnClick(event, treeId, treeNode){
	
	if($("#"+treeNode.shopCategoryId).length>0){
		
		$("#content div").css("border","");
		
		$("#"+treeNode.shopCategoryId).css("border","1px solid red");
		
		return;
	}
	
}


/** 关闭类目弹出层  **/
function selectOneAccount(){
	var treeObj = "";
	treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = new Array();
	nodes = treeObj.getCheckedNodes(true);

	var checkeds = "";
	if(nodes.length>0){
		
		for(var i =0;i<nodes.length;i++){
		//	console.log("nodes[i].shopCategoryId:"+nodes[i].shopCategoryId);
			checkeds += nodes[i].shopCategoryId+",";
		}
		
		  checkeds = checkeds.substring(0,checkeds.lastIndexOf(','));
		 
	}
	var windowId = $("#windowId").val();
	/*if(checkeds==""){
		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
        messageDialog("消息提示","text:"+msg ,"300px","102px","text");
        var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
        return;
	}*/
	//console.log("checkeds:"+checkeds);
	
	 parent.closeCategory(checkeds,windowId);
}


function checkboxs(v){
	if(v.checked){
		treeObj.checkAllNodes(true);
	}else{
		treeObj.checkAllNodes(false);
	}
	
}

	function closeWindow(){
	try {
				document.domain = 'kmb2b.com';
            	window.frameElement.trigger('close');
	        } catch (error) {
	            window.console && console.log('error',error);
	        }
}

</SCRIPT>

<style type="text/css">
	.ctree a{
		color: #333333;
    	text-decoration: none;
	}
	.ctree{
		font-family: Verdana,Geneva,Arial,Helvetica,sans-serif;
   		font-size: 11px;
    	white-space: nowrap;
	}
	.list_table{
		margin:0px;
	}
	#content div{
		margin-top:5px;
	}
</style>

</head>
<body>


<s:form  name="viewProductInfoForm" action="/shop/shopCategoryAction_gotoShopCategory.do" method="post">

<div style=" width: 500px;" class="ui-dialog-z">
    <div class="ui-dialog-z-header">
        <h4>关联店内分类</h4>
     <!--    <a style="display: block;" class="ui-dialog-z-close" title="关闭本框" href="javascript:;" data-role="close">×</a>--> 
    </div>
    <div  class="ui-dialog-z-content" data-role="content">
        <div class="edit-tabs-con">
                    <ul class="mod-list ui-tab-cnt ctree"  id="dtree_show">
                       <input type="hidden" name="windowId" id="windowId" value="<s:property value='windowId' />">
	    		   <input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
	    		   <input type="hidden" name="checkDataIdS" id="checkDataIdS" value="<s:property value='checkDataIdS' />">
	    		    <input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
                        <span class="ui-edit-btn"><a href="#">展开所有</a></span>|<span class="ui-edit-btn"><a href="#">关闭所有</a></span>
                        <p><span class="ui-edit-btn">全选</span><input type="checkbox" value="checkbox" onclick="checkboxs(this);"></p>
                        <div id="treeDemo" class="ztree">
                       
                        </div> 
                       
                    </ul>
                    
                </div>
    </div>              
    <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" onclick="selectOneAccount();">确认</a>
      <!--  <a class="btn ui-btn-cancel" onclick="closeWindow ();">取消</a> --> 
    </div>
</div>

<!-- 
<table width="98%" height="80%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" >
	<tr>
	    <td align="left" width="30%"  valign="top">
	    	<div class="ctree" id="dtree_show">
	    		<input type="hidden" name="windowId" id="windowId" value="<s:property value='windowId' />">
	    		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
	    		<input type="hidden" name="checkDataIdS" id="checkDataIdS" value="<s:property value='checkDataIdS' />">
	    		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
	    		
				<p><a href="#" onclick="expandAll(true)">展开所有</a> | <a href="#" onclick="expandAll(false)">关闭所有</a></p>
				<br/>
				全选<input type="checkbox" id="box" onclick="checkboxs(this);" />
				<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	</tr>
</table>
 -->

</s:form>
				<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</BODY>
</HTML>

