<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/etc/js/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="/etc/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">

<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
	<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.js"></script>
    <script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
    <script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
	<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.excheck-3.5.js"></script>
   
</head>
 
<body>
<!-- 导航栏 -->

 

<style type="text/css">

</style>
<!-- 导航栏 -->

<div class="ui-dialog-z" style=" width: 650px;">
   
   
   <div class="ui-dialog-z-header">
<h4>商品排行</h4>

</div>
    <div data-role="content" class="ui-dialog-z-content" style="height: 60%;">
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                <form id="frm" action="addProductToptenz.do" method="post" name="" class="ui-form">
                     <input type="hidden"  name="cmsWindowData.windowId" value="<s:property value='windowId' />">
                     <input type="hidden"  name="shopI" value="<s:property value='shopI' />">
                      <input type="hidden"  name="cmsShopData.windowId" value="<s:property value='windowId' />">
                     <input type="hidden"  name="cmsWindowData.windowDataId" value="<s:property value='cmsWindowData.windowDataId' />">
                      <input type="hidden"  name="cmsShopData.shopDataId" value="<s:property value='cmsShopData.shopDataId' />">
                    <fieldset>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">栏目标题</label>
                            <div class="ui-form-explain">
                                <input type="text" placeholder="" class="ui-input" value="<s:property value='cmsWindowData.user_defined_name' />" name="cmsWindowData.user_defined_name" id="user_defined_name" >
                                <s:if test="cmsWindowData.user_defined_url == 1">
                                <span class="ui-tpis"><input id="checkbox" type="checkbox" value="1" name="cmsWindowData.user_defined_url" checked ="true">显示栏目标题</span>
                                </s:if>
                                <s:else>
            		  <span class="ui-tpis"><input id="checkbox"  type="checkbox" value="1" name="cmsWindowData.user_defined_url" >显示栏目标题</span>
				</s:else>
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">商品分类</label>
                            <body>
                            <div class="ui-form-explain">
                            <input class="ui-select165" id="citySel" type="text" readonly value="" style="width:120px;" onclick="showMenu();"  />
                            <input type="hidden" id="citySelValue" name="cmsShopData.classifyId" value="<s:property value='cmsShopData.classifyId' />"></input>
                               <a id="menuBtn" href="#" onclick="showMenu(); return false;">请选择</a>
                            </div>
                            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
                            </body>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="" >关键字</label>
                            <div class="ui-form-explain">
                                <input type="text" placeholder="" class="ui-input" value="<s:property value='cmsShopData.keyword' />"  name="cmsShopData.keyword" id="keyword">
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">价格范围</label>
                            <div class="ui-form-explain">
                                <input type="text" placeholder="起始价格" class="ui-input" name="cmsShopData.pricestart" id="pricestart" value="<s:property value='cmsShopData.pricestart' />"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
                                <span>—</span>
                                <input type="text" placeholder="终止价格" class="ui-input" name="cmsShopData.priceend" id="priceend" value="<s:property value='cmsShopData.priceend' />" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')" >
                                <span class="ui-tips">元</span>
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">显示数量</label>
                            <div class="ui-form-explain">
                                <select class="ui-select165" name="cmsShopData.shownumber" id="shownumber">
                                    <option value="5" <s:if test="cmsShopData.shownumber==5" >selected='selected'</s:if> >5个商品</option>
                                    <option value="8" <s:if test="cmsShopData.shownumber==8" >selected='selected'</s:if> >8个商品</option>
                                    <option value="10" <s:if test="cmsShopData.shownumber==10">selected='selected'</s:if> >10个商品</option>
                                    <option value="15" <s:if test="cmsShopData.shownumber==15" >selected='selected'</s:if> >15个商品</option>
                                    <option value="20" <s:if test="cmsShopData.shownumber==20" >selected='selected'</s:if> >20个商品</option>
                                    <option value="50" <s:if test="cmsShopData.shownumber==50" >selected='selected'</s:if> >50个商品</option>
                                </select>
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">排序方式</label>
                            <div class="ui-form-explain">
                                <select class="" name="cmsShopData.ranktype" id="ranktype">
                                   
                                    <option value="1" <s:if test="cmsShopData.ranktype==1">selected='selected'</s:if> >按销量从高到低排序</option>
                                    <option value="2"<s:if test="cmsShopData.ranktype==2" >selected='selected'</s:if> >按收藏数从高到低排序</option>
                                </select>
                                <s:if test="cmsShopData.isShow == 1">
                                <span class="ui-tpis"><input type="checkbox" value="1" name="cmsShopData.isShow" checked="true">显示销量数据</span>
                                </s:if>
                                <s:else>
                                 <span class="ui-tpis"><input type="checkbox" value="1" name="cmsShopData.isShow" >显示销量数据</span>
                                </s:else>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
    <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" TYPE="button" onclick="javascript:submit();" >保存</a>
        <a class="btn ui-btn-cancel" TYPE="button" onclick="javascript:closeWin();">取消</a>
    </div>

</div>

<script type="text/javascript">



	
$(document).ready(function(){
	var result='<s:property value='result' />';
	
	if(result=='error'){
	alert("你输入的内容存在敏感词，请重新输入！");
	}
	if(result=='success'){
		alert("保存成功！");
		closeWin();
	}
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	var classifyId = $("#citySelValue").attr("value");
	   if(classifyId!="")
	   {
		 //默认选中专业节点
			 var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			 var node = treeObj.getNodeByParam("shopCategoryId", classifyId);
			var cityObj = $("#citySel");
			var cityObjValue = $("#citySelValue");
			cityObj.attr("value", node.categoryName);
			cityObjValue.attr("value", classifyId);
		
		
	    }
}); 
   
function zTreeOnExpand(event, treeId, treeNode) {
	$("#treeDemo").scrollTop(document.getElementById(treeNode.tId).offsetTop - 24);
	};
   function submit(){
	   if($("#checkbox").attr("checked")){
		   if($("#user_defined_name").val()){
			  // continue;
		   } else{
			   alert("标题不能为空!");
			   return;
		   }
	   }
	   //var user_defined_name= showErrorMessage('user_defined_name','栏目标题不能为空','user_defined_name');
	   if($("#pricestart").val()){
		   var priceend= showErrorMessage('priceend','终止价格不能为空','priceend');
		   if(!priceend){
            return
			   }
	   }
	   if($("#priceend").val()){
		   var pricestart= showErrorMessage('pricestart','起始价格不能为空','pricestart');
		   if(!pricestart){
	            return
				   }
	   }
	   if($("#pricestart").val())
	   {
	   if($("#pricestart").val()<0||$("#priceend").val()<0){
		   if($("#pricestart").val()<0)
		   showError('pricestart','起始价格不能小于0','pricestart');
		   else
		   $("#error"+"pricestart").remove();
		   if($("#priceend").val()<0)
			   showError('priceend','终止价格不能小于0','priceend');
		   else
			   $("#error"+"priceend").remove();
		   return;
		
	   }
	   }


	   
	 $("#frm").submit();

	}
   function showErrorMessage(checkId,title,afterId){
		var t = $("#"+checkId).val();
		if(t){
			$("#error"+checkId).remove();
			return true;
		}
		showError(checkId,title,afterId);
		return false;
	}
	function showError(errorId,title,afterId){
		if($("#error"+errorId).length>0){
			return;
		}
		var html = '<label for="error" id="error'+errorId+'" generated="true" class="error">'+title+'</label>';
		var id = "#"+afterId;
		$(id).after(html);
	}
	
 	//关闭弹出窗口
 	function stylesCloseOpenDialog(content)
 	{
 		closeThis();
 		var json = eval('(' + content + ')'); 
 		editor.setValue(json.content);
 		$("#templateId").val(json.templateId);
 		$("#stylesId").val(json.stylesId);
 		$("#preview").attr("src",imageOut+json.remark);
 		$("#previewHref").attr("href",imageOut+json.remark);
 		$("#preDiv").show();
    }
    
    //返回
   function gotoList(id){
  	var pageForm= window.document.getElementById("addPageForm");
 	pageForm.action="/cms/cmsPageAction_queryForPage.action";
 	pageForm.submit();
  }

   function closeWin() {
		try {
     		document.domain = 'kmb2b.com';
            window.frameElement.trigger('close');
        } catch (error) {
            window.console && console.log('error',error);
        }
		$("#floatBox").hide("slow");
		$("#floatBoxBg").hide("slow");

	}
   var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
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
				onClick: onClick,
				onCheck: onCheck
			}
		};

        var zNodes = <s:property value="categoryString" escape="false"/>;

		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}

		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var value = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].categoryName + ",";
				value = nodes[i].shopCategoryId;
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel");
			var cityObjValue = $("#citySelValue");
			cityObj.attr("value", v);
			cityObjValue.attr("value", value);
			
		}

		function showMenu() {
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

</script>
</BODY>
</HTML>