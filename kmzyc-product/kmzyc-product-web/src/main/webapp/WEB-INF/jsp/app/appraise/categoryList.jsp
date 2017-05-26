<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品类目管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
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
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script language='JavaScript' src="/etc/js/dialog-common.js"></script>
<SCRIPT type="text/javascript">
<!--
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
		onClick: zTreeOnClick
	}
};

var zNodes = <s:property value="nodes" escape="false"/>;
var treeObj;
$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("treeDemo");
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}

function zTreeOnClick(event, treeId, treeNode){
	if(treeNode.level!=2){
		treeObj.expandNode(treeNode, true, false, true);
		return;
	}
	
	if($("#"+treeNode.categoryId).length>0){
		
		$("#content div").css("border","");
		
		$("#"+treeNode.categoryId).css("border","1px solid red");
		
		return;
	}
	
	
	$.post(
		"/app/findAppPropByCageId.action",
		{cateId:treeNode.categoryId},
		function(data){
			
			if($("#content div").length == 4){
				$("#content div").remove();
			}
			
			var jsonTxt = $.parseJSON(data);
			
			var _html = '<div id='+treeNode.categoryId+' style="width:99%;" ><table width="100%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">';
			_html += '<tr><th colspan="2">'+treeNode.categoryName+'</th></tr>';
			
			if(jsonTxt.length==0){
				_html += '<tr><td colspan="2" style="text-align:left;">暂时没有绑定属性</td></tr>';
			}
			
			$.each(jsonTxt,function(i,val){
	    		_html += '<tr>';
	    		_html += '<td>';
	    		_html += val.propName;
	    		_html += '</td>';
	    		_html += '<td>';
	    		$.each(val.valList,function(j,v){
	    			_html += v.point+'：'+v.propVal+'&nbsp;&nbsp;&nbsp;';
	    		});
	    		_html += '</td>';
	    		_html += '</tr>';
				
			});
			_html +="</table></div>";
			$("#content").append(_html);
		}
	);
}

function gotoBingding(arg){
	var nodes = treeObj.getCheckedNodes(true);
	if(nodes.length == 0){
		alert("请选择类目！");
		return;
	}
	var msg = "";
	if(arg=="bind"){
		msg = "只能为三级类目绑定属性，请重新选择类目！";
	}else{
		msg = "只有三级类目有评价属性，请重新选择类目！";
	}
	
	var categoryIds = "";
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].level!=2){
			if(nodes[i].isParent == false){
				alert(msg);
				return;
			}
			continue;
		}
		categoryIds += nodes[i].categoryId + ",";
	}
	categoryIds = categoryIds.substring(0,categoryIds.length-1);
	
	if(arg=="bind"){
		popDialog("/app/prodAppraisePropSelectList.action?categoryIds="+categoryIds ,"查看所有评价属性","700px","450px");
	}else{
		$.post(
			"/app/prodAppraisePropUnbind.action",
			{categoryIds:categoryIds},
			function(data){
				if("success" == data){
					alert("操作成功！");
					var c = categoryIds.split(",");
					for(var i=0;i<c.length;i++){
						if($("#"+c[i]).length>0){
							$("#"+c[i]).remove();
						}
					}
				}else{
					alert("操作失败！");
				}
			}
		);
	}
}

function closeDialog(){
	$("#content div").remove();
	closeThis();
}

//-->
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
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 标题条 -->
<s:set name="parent_name" value="'产品咨询评价管理'" scope="request" />
<s:set name="name" value="'类目评价属性'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<table width="98%" align="center" class="topbuttonbar" height="20" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <INPUT class="btn-custom btngreen" TYPE="button" value="+ 绑定评价属性 " onClick="gotoBingding('bind');">
            <INPUT class="btn-custom btngreen" TYPE="button" value="- 取消绑定的评价属性 " onClick="gotoBingding('unbind');">
		</td>
	    <td width="10%" align="center"></td>
	</tr>
</table>

<table width="98%" height="80%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin-top: 5px;background-color:#F1F9FC;border-collapse: collapse;">
	<tr style="background:#F1F9FC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="ctree" id="dtree_show">
				<p><a href="#" onClick="expandAll(true)">展开所有</a> | <a href="#" onClick="expandAll(false)">关闭所有</a></p>
				<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top" id="content" >

		</td>
	</tr>
</table>

</BODY>
</HTML>

