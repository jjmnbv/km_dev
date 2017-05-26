<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/etc/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="/etc/js/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
<script src="/etc/js/dialog.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.excheck-3.5.js"></script> 
</head>
<body>


<!-- 导航栏 -->
<div class="ui-dialog-z" style=" width: 650px;">
	 <div class="ui-dialog-z-header">
		<h4>店铺分类</h4>
	</div>
    <div data-role="content" class="ui-dialog-z-content">
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                <form id="frm" action="saveShopCategorySettingForWindow.do" method="post" class="ui-form">     
                	<s:hidden name="windowId"></s:hidden>   
                	<s:hidden name="shopId"/>  
               		<s:hidden name="shopI"/>  
                    <fieldset>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">栏目标题: </label>
                            <div class="ui-form-explain">
                                <input type="text" placeholder="" maxlength="64" class="ui-input j_title" name="cmsWindowData.user_defined_name" value="<s:property value="cmsWindowData.user_defined_name"/>">                          
                                <span class="ui-tpis">
                                <s:if test="cmsWindowData.status==1">
                                	<input type="checkbox" value="1" class="j_status"  checked ="true">显示栏目标题
                                </s:if>
                                <s:else>
                                 	<input type="checkbox"  value="0"  class="j_status" >显示栏目标题
                                </s:else>
                                
                                <input type="hidden" name="cmsWindowData.status" id="status"/>
                               </span>
                            </div>
                        </div>   
                      	<div class="ui-form-item">
                           <label class="ui-label">选择分类:</label>
                            	<input class="ui-select165" id="citySel" type="text" readonly value="" style="width:400px;" onclick="showMenu();"  />
                               	&nbsp;&nbsp;&nbsp;&nbsp;<a id="menuBtn" href="#" onclick="showMenu(); return false;">选择</a>&nbsp;&nbsp;&nbsp;&nbsp;已选 <span id="selectCount"></span> 个分类
	                            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
									<ul id="treeDemo" class="ztree" style="margin-top:0; width:300px; height: 200px;"></ul>
								</div>
								<s:hidden name="shopCategoryIdStr" id="categoryIdStr"></s:hidden>   
						</div> 
                         <div class="ui-form-item">
                            <label class="ui-label" for="" >是否展开:</label>
                          	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-tpis">
                          	<input type="hidden" name="isExpand" id="expand"/>
                           <input type="checkbox" id="expand" class="j_isExpand" checked="checked">展开分类</span>                         	
                        </div>
                    </fieldset>
                </form>
            </div>
            
            <div class="ui-dialog-z-footer">
		        <a class="btn ui-btn-save j_shopCategory_save" TYPE="button" href="#"  >保存</a>
		        <a class="btn ui-btn-cancel j_shopCategory_cancel" TYPE="button" href="#">取消</a>
    		</div>
            
        </div>
    </div>
    

</div>
<script type="text/javascript">

$(".j_shopCategory_save").bind("click",function(e){
	e.preventDefault();
	
	var title=$(".j_title").val();
	
	var status=$(".j_status")[0].checked;	
	
	
	$("#status").val(1);
	if(!status){
		$("#status").val(0);
	}
	
	
	if(status){
		if(title==""){
			alert("请您填写要显示的栏目标题!");
			return false;
		}				
	}		
	
	//全局过滤特殊字符
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]","g");
	
	
	if(title!="" && pattern.test(title)){
		alert("您输入的标题中含特殊字符,请重新输入!");
		return;
	}		
	
	
	var isExpand=$(".j_isExpand")[0].checked;
	$("#expand").val(1);
	
	
	if(!isExpand){
		$("#expand").val(0);
	}
	
	//如果选中了默认展开但是没有选中任何分类
	if(isExpand){
		if($("#categoryIdStr").val()==''){
			alert("请选择您需要默认推荐展开的分类!");
			return ;
		}		
	}	
	console.log("isExpand="+$("#expand").val()+",title="+title+",idStr="+$("#categoryIdStr").val()+"status="+$(".j_status").val());
	$("#frm").ajaxSubmit({dataType:'json',success:function(jsonResult){
		if(jsonResult.result=="true"){
			alert("操作成功!");	
			try {
				 document.domain = 'kmb2b.com';
		         window.frameElement.trigger('close');
		     } catch (error) {
		         window.console && console.log('error',error);
		     }
		}else if(jsonResult.result=="error"){
			alert("操作失败,请联系管理员或重试!");
		}else if(jsonResult.result=="unValid"){
			alert("您输入的标题中含非法字符,请重新输入!");
		}
		
	}});
	
});
		
/**
 * 关闭按钮绑定事件
 */
$(".j_shopCategory_cancel").bind("click",function(e){
	e.preventDefault();	
	 try {
		 document.domain = 'kmb2b.com';
		 //console.log(window.frameElement);
		 document.domain = 'kmb2b.com';
         window.frameElement.trigger('close');
     } catch (error) {
         window.console && console.log('error',error);
     }
});

	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		   
		var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
		//默认选中已展开的
		   var allPrentNodes=$.fn.zTree.getZTreeObj("treeDemo").getNodes();
           var shopCategoryId=$("#categoryIdStr").val();
           
           console.log("shopCategoryId="+shopCategoryId);
           var selectCount=0;
           //循环勾选
           if(shopCategoryId !=''){
               var idArrays=shopCategoryId.substring(0,shopCategoryId.length-1).split(',');              
               var selectName="";
               
               for(var i=0;i<allPrentNodes.length;i++){
            	   
            	   for(var j=0;j<idArrays.length;j++){                    
                        if(parseInt(idArrays[j])==parseInt(allPrentNodes[i].shopCategoryId)){
                        	selectCount=selectCount+1;
                        	selectName += allPrentNodes[i].categoryName + ",";
                        	 treeObj.checkNode(allPrentNodes[i], true, true);
                         }
                        
                        if(allPrentNodes[i].isParent){
                        	 //对比子类是否选中
                            var children=allPrentNodes[i].children;
                            for(var k=0;k<children.length;k++){                                   
                           	 if(parseInt(idArrays[j])==parseInt(children[k].shopCategoryId)){
                                	selectCount=selectCount+1;
                                	selectName += children[k].categoryName + ",";
                                	 treeObj.checkNode(children[k], true, true);
                                 }
                             }
                        }       
                   }     
               
               }
           }
           
           $("#selectCount").html(selectCount);
           var cityObj = $("#citySel");
		   cityObj.attr("value", selectName);
           // 默认选中   end
		   treeObj.expandAll(true);
	}); 
   
	function zTreeOnExpand(event, treeId, treeNode) {
		$("#treeDemo").scrollTop(document.getElementById(treeNode.tId).offsetTop - 50);
	};

   var setting = {
		   check: {
				enable: true,
				autoCheckTrigger: false,
				chkboxType: { "Y": "p", "N": "s" }
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
				beforeClick: beforeClick,
				onCheck: onCheck
			}
		};

        var zNodes = <s:property value="jsonDataForTree" escape="false"/>;
        console.log(zNodes);

        
        function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		

		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var value = "";
			var idStr="";
			var selectCount=0;
			for (var i=0, l=nodes.length; i<l; i++) {
				selectCount=selectCount+1;
				v += nodes[i].categoryName + ",";
				idStr+=nodes[i].shopCategoryId+",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#citySel");
			cityObj.attr("value", v);
			
			//可显示在span上面
			$("#selectCount").html(selectCount);
			$("#categoryIdStr").val(idStr);
		}

		function showMenu() {
			var cityObj = $("#citySel");
			var cityOffset = $("#citySel").offset();
			//console.log("cityOffset.left="+cityOffset.left+",cityOffset.top="+cityOffset.top+",cityObj.outerHeight()="+cityObj.outerHeight())
			//$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
			$("#menuContent").css({left:"100px", top:"86px"}).slideDown("fast");
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