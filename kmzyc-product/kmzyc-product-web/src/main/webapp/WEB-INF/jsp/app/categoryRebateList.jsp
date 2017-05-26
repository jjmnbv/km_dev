<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商家类目佣金管理</title>
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
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
<Script language="JavaScript" type="text/javascript" src="/etc/js/Form.js"></Script>
<script language="JavaScript" src="/etc/js/dialog.js" type="text/javascript"></script>
<SCRIPT type="text/javascript">
<!--
var setting = {
	view: {
		addDiyDom: addDiyDom
	},
	check: {
        enable: true,
        autoCheckTrigger: true,
        chkStyle: "checkbox",
        chkboxType: { "Y": "ps", "N": "ps" }
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
	callback: {//用户勾选树   设置回调函数
        beforeCheck:addRebateInput
    }
};

var zNodes = <s:property value="categoryString" escape="false"/>;
var isTrue=true;

IDMark_A = "_a";
function addDiyDom(treeId, treeNode) {
}
function addRebateInput(treeId,treeNode){
	var aObj='';
	var editStr = "";
	var defaultRebate = 0;
	if(isTrue){//初始化时不进方法体
		if(treeNode.parentId == 0 && !(treeNode.checked)){//父节点、未勾选(要勾选)
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			treeObj.expandNode(treeNode,true,true,true);//勾选时展开
			var arr = treeNode.children;
			for(var i =0;i<arr.length;i++){
				aObj = 	$("#" + arr[i].tId + IDMark_A);
				if(typeof(arr[i].defaultRebate) == 'undefined'||typeof(arr[i].defaultRebate) == undefined){
					defaultRebate=0;
				}else{
					defaultRebate = arr[i].defaultRebate;
				}
				editStr = "<input style='width:50px;' class='demoIcon' id='inputId_" +arr[i].categoryId+ "' value='"+defaultRebate+"' title='默认佣金' name='defaultRebate'/><span>%</span>";
				aObj.append(editStr);
			}
		}else if(treeNode.parentId == 0){//为父节点、已勾选(要取消)
			var arr = treeNode.children;
			for(var i =0;i<arr.length;i++){
				aObj = 	$("#inputId_" + arr[i].categoryId);
				aObj.next().remove();
				aObj.remove();
			}
		}else if(treeNode.parentId != 0 && !(treeNode.checked)){//子节点、未勾选(要勾选)
			aObj = 	$("#" + treeNode.tId + IDMark_A);
			if(typeof(treeNode.defaultRebate) == 'undefined'||typeof(treeNode.defaultRebate) == undefined){
				defaultRebate=0;
			}else{
				defaultRebate = treeNode.defaultRebate;
			}
			editStr = "<input style='width: 50px;' class='demoIcon' id='inputId_" +treeNode.categoryId+ "' value='"+defaultRebate+"' title='默认佣金' name='defaultRebate'/><span'>%</span>";
			aObj.append(editStr);
		}else if(treeNode.parentId != 0){//子节点、已勾选(要取消)
			aObj = 	$("#inputId_" + treeNode.categoryId);
			aObj.next().remove();
			aObj.remove();
		}
	}else{
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		treeObj.expandNode(treeNode,true,true,true);//勾选时展开
		isTrue = true;
	}
}

function initRebateInput(treeId,treeNode){
	if(treeNode.parentId == 0 && !(treeNode.checked)){//父节点、未勾选(要勾选)
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		isTrue = false;//定义第一次初始化为false不进addRebateInput方法体
		treeObj.checkNode(treeNode,true,false,true);//勾选触发
		if(treeNode.children.length>0){
			var arr = treeNode.children;
			for(var i=0;i<arr.length;i++){
				if(typeof(arr[i].defaultRebate) == 'undefined'||typeof(arr[i].defaultRebate) == undefined){//子节点判断是否有默认返点，没有则表示没记录，不勾选
				}else{
					treeObj.checkNode(arr[i],true,false,true);
				}
			}
		}
	}
}

$(document).ready(function(){
	zObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	var nodes = zObj.getNodes();
	for (var i = 0, l = nodes.length; i < l; i++) {
		if(nodes[i].isParent){
			if(nodes[i].children.length>0){
				var isCk = nodes[i].children;
				for(var j=0;j<isCk.length;j++){
					if(typeof(isCk[j].defaultRebate) == 'undefined'||typeof(isCk[j].defaultRebate) == undefined){
					}else{
						initRebateInput("treeDemo",nodes[i]);
					}
				}
			}
		}
	}
});
function expandAll(flag){
	$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
}

//检查是否为1-99之间正整数
function isUnsignedInteger(a)
{
	var reg =/^((\d|[123456789]\d)(\.\d+)?)$/;
	return reg.test(a);
}
function save(){
	var nodes = zObj.getCheckedNodes(true);
	if(nodes.length == 0){
		alert("请选择要设置的类目！");
		return;
	}
    var datasAdd="";
    var idVal="";
	for(var i=0;i<nodes.length;i++){
	    var defaultRebate = 0;
		idVal = "#inputId_" +nodes[i].categoryId;
		if(nodes[i].parentId != 0){
			if(isUnsignedInteger($(idVal).val())== false || $(idVal).val().substring($(idVal).val().indexOf(".")+1,$(idVal).val().length).length > 2 || $(idVal).val()==0){
				alert("请输入大于0小于100的佣金比例,小数最多为两位！");
				return;
			}
		}
		if(typeof($(idVal).val()) == 'undefined' || typeof($(idVal).val()) == undefined || nodes[i].parentId == 0){
			defaultRebate = "";
		}else{
			defaultRebate = parseFloat($(idVal).val())/100;
		}
		if(i==0){
    		datasAdd += '[{categoryId:'+nodes[i].categoryId+',parentId:'+nodes[i].parentId+',defaultRebate:"'+defaultRebate+'"},';
    	}else if(i == nodes.length - 1){
    		datasAdd += '{categoryId:'+nodes[i].categoryId+',parentId:'+nodes[i].parentId+',defaultRebate:"'+defaultRebate+'"}]';
    	}else{
    		datasAdd += '{categoryId:'+nodes[i].categoryId+',parentId:'+nodes[i].parentId+',defaultRebate:"'+defaultRebate+'"},';
    	}	
	}
	$.ajax({
	   type: "POST",
	   //url: "/app/saveCopyCategorys.action",
	   url: "/app/saveCategoryRebateList.action",
	   data: {categorysDate : datasAdd},
	   success: function(data){
		   if(data == "success"){
				alert("操作成功！");
				location.href="/app/queryCategoryRebateList.action?type=phy";
		   }else{
				alert("系统发生错误，操作失败！");
            	location.href="/app/queryCategoryRebateList.action?type=phy";
		   }
	   }
	});
}
//-->
</SCRIPT>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 标题条 -->
<div class="pagetitle">商家类目佣金管理:</div>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2" style="margin: auto; margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#F1F9FC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="#" onClick="expandAll(true)">展开所有</a> | <a href="#" onClick="expandAll(false)">关闭所有</a></p>
			
					<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	    <td align="left" width="70%" valign="top">
	    	<iframe id="ifr" name="ifr" width="100%" height="400" src="" frameBorder="no" scrolling="no" STYLE="background-color:transparency" allowTransparency="true"></iframe>
		</td>
	</tr>
</table>
<div style="margin: 12px 0px 6px 18px;"><input class="btngreen" type="button" style="height:30px" value="保存" onClick="save();"></div>

</BODY>
</HTML>

