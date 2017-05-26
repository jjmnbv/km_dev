<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>物理类目PV比例维护</title>
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
</head>
<SCRIPT type="text/javascript">
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
		callback: {//用户勾选树
	        beforeCheck:beforeCheck
	    }
	};

	var zNodes = <s:property value="categoryString" escape="false"/>;
	var isTrue=true;

	IDMark_A = "_a";
	function addDiyDom(treeId, treeNode) {
	}

    //获取默认比例
    function getDefaultRebate(treeNode) {
        var defaultRebate = treeNode.defaultRebate;
        if(typeof(defaultRebate) == 'undefined'||typeof(defaultRebate) == undefined){
            defaultRebate=0;
        }
        return defaultRebate;
    }

    //追加input默认比例的输入框
    function appendInput(treeNode) {
        var aObj = $("#" + treeNode.tId + IDMark_A);
        var defaultRebate = getDefaultRebate(treeNode);
        var editStr = "<input style='width: 50px;' class='demoIcon' id='inputId_" + treeNode.categoryId + "' value='" + defaultRebate + "' title='默认佣金' name='defaultRebate'/><span'>%</span>";
        aObj.append(editStr);
    }

    //移除比例输入框
    function removeInput(treeNode) {
        var aObj = $("#inputId_" + treeNode.categoryId);
        aObj.next().remove();
        aObj.remove();
    }

    //树节点勾选前上事件
	function beforeCheck(treeId,treeNode){
        var children ;
        var subChildren ;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        if(treeNode.parentId == 0 && !(treeNode.checked)){//父节点、未勾选(要勾选)
            treeObj.expandNode(treeNode,true,true,true);//勾选时展开当前父节点下所有

            children = treeNode.children;
            if (children != undefined || children.length != 0) {//二级类目
                for (var i = 0; i < children.length; i++) {
                    subChildren = children[i].children;
                    if (subChildren != undefined || subChildren.length != 0) {//三级类目,将所有三级类目加上输入框
                        for (var j = 0; j < subChildren.length; j++) {
                            appendInput(subChildren[j]);
                        }
                    }
                }
            }
        }else if(treeNode.parentId == 0){//为父节点、已勾选(要取消)
            children = treeNode.children;
            if (children != undefined || children.length != 0) {//二级类目
                for (var i = 0; i < children.length; i++) {
                    subChildren = children[i].children;
                    if (subChildren != undefined || subChildren.length != 0) {//三级类目,将所有三级类目移除输入框
                        for (var j = 0; j < subChildren.length; j++) {
                            removeInput(subChildren[j]);
                        }
                    }
                }
            }
        }else if(treeNode.parentId != 0 && !(treeNode.checked)){//子节点、未勾选(要勾选)
            children = treeNode.children;
            if (children == undefined || children.length == 0) {//三级类目加入输入框
                appendInput(treeNode);
            } else {
                treeObj.expandNode(treeNode,true,true,true);//勾选时展开当前二级类目下所有
                for(var i =0;i<children.length;i++){//二级类目下所有的三级类目加入输入框
                    appendInput(children[i]);
                }
            }
        }else if(treeNode.parentId != 0){//子节点、已勾选(要取消)
            children = treeNode.children;
            if (children == undefined || children.length == 0) {//三级类目移除输入框
                removeInput(treeNode);
            } else {
                for(var i =0;i<children.length;i++){//二级类目下所有的三级类目移除输入框
                    removeInput(children[i]);
                }
            }
        }
	}

	$(document).ready(function(){
		zObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zObj.getNodes();
        var children;
        var subChildren;
        var defaultRebate;
        var treeNode;

        for (var i = 0, l = nodes.length; i < l; i++) {//一级类目
            children = nodes[i].children;
            if (children != undefined) {
                for (var t = 0; t < children.length; t++) {//二级类目
                    subChildren = children[t].children;
                    if (subChildren != undefined) {
                        for (var j = 0; j < subChildren.length; j++) {//遍历三级类目
                            treeNode = subChildren[j];
                            defaultRebate = treeNode.defaultRebate;
                            if (typeof(defaultRebate) != 'undefined' && typeof(defaultRebate) != undefined) {
                                treeObj.expandNode(children[t],true,true,true);//展开二级类目
                                treeObj.checkNode(treeNode, true, true, true);//勾选触发
                            }
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
    function isUnsignedInteger(a) {
        var reg =/^((\d|[123456789]\d)(\.\d+)?)$/;
        return reg.test(a);
    }

	function save(){
		var nodes = zObj.getCheckedNodes(true);
		if(nodes.length == 0){
			alert("请选择要设置的类目！");
			return;
		}

	    var jsonData="[";
	    var input="";
        for (var i = 0; i < nodes.length; i++) {
            var defaultRebate = 0;
            input = "#inputId_" +nodes[i].categoryId;
            if(nodes[i].parentId != 0 && nodes[i].children == undefined){
                if(isUnsignedInteger($(input).val())== false
                        || $(input).val().substring($(input).val().indexOf(".")+1,$(input).val().length).length > 2
                        || $(input).val()==0){
                    alert("请输入大于0小于100的PV比例,小数最多为两位！");
                    return;
                } else {
                    defaultRebate = parseFloat($(input).val())/100;
                    jsonData += '{categoryId:'+nodes[i].categoryId+',defaultRebate:"'+defaultRebate+'"},';
                }
            }
        }
        if (jsonData != "[") {
            jsonData = jsonData.substring(0, jsonData.length-1) + "]";
        }

		$.ajax({
		   type: "POST",
		   url: "/app/savePVCategory.action",
		   data: {categorysDate : jsonData},
		   success: function(data){
			   if(data == "success"){
					alert("操作成功！");
					location.href="/app/queryPVCategoryList.action";
			   }else{
					alert("系统发生错误，操作失败！");
	            	//location.href="/app/queryPVCategoryList.action";
			   }
		   }
		});
	}
</script>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<!-- 标题条 -->
<div class="pagetitle">物理类目PV比例维护:</div>
<table width="98%" class="content_table"  align="center" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <input style="margin: 12px 0px 6px 18px;" class="btn-custom " TYPE="button" value="保存" onclick="save();">
		</td>
	</tr>
</table>

<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2"
       style="margin: auto; margin-top: 5px;background-color:#FFFFCC;border-collapse: collapse;">
	<tr style="background:#F1F9FC">
	    <td align="left" width="30%"  valign="top">
	    	<div class="dtree" id="dtree_show">
				<p><a href="#" onClick="expandAll(true)">展开所有</a> | <a href="#" onClick="expandAll(false)">关闭所有</a></p>
					<ul id="treeDemo" class="ztree"></ul>
			</div>
	    </td>
	</tr>
</table>
</body>
</html>