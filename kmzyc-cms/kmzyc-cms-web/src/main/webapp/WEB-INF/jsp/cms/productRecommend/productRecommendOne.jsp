<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
		<script src="/etc/js/jquery-latest.pack.js"></script>

<!--<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
-->
<link rel="stylesheet" href="/etc/js/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="/etc/js/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
	<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.js"></script>
    <script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
    <script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exedit-3.5.min.js"></script>
	<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.excheck-3.5.js"></script>
	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	</script>	
		
<!-- 导航栏 -->

		<div class="recommend-box">
<form id="ADV_queryPageList" name="userLevelForm" action="/cms/productRecommendActionOne.do" method="post">
			<!-- 查询条件 -->
		   
			<div class="ui-form-item">
                                <input type="text" placeholder="名称或编码" class="ui-input ui-input280" name="selectViewProductInfo.productNameOrCode" value="<s:property value="selectViewProductInfo.productNameOrCode"/>">
                                
                            
                           <input class="ui-select165 ui-input" placeholder="选择店铺类目" id="citySel" type="text" readonly value="" style="width:120px;" onclick="showMenu();"  />&nbsp;&nbsp; <a class="ui-widget-btn v-a-t" href="javascript:empty();"><span>清空类目</span></a>
                            <input type="hidden" id="citySelValue" name="selectViewProductInfo.shopCategoryId" value="<s:property value='selectViewProductInfo.shopCategoryId' />"></input>
                            
                            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
</div>
                          
                                <a class="ui-widget-btn v-a-t" href="javascript:serSubmit();"><span>搜索</span></a>
                                <a class="ui-widget-btn v-a-t" href="javascript:addPro();"><span>批量加入</span></a>
                            </div>
			<!-- 隐藏域 -->
			<input type="hidden" value='<s:property value="shopI"/>' name="shopI" id="shopId">
			<input type="hidden" value='<s:property value="windowId"/>' name="windowId" id="windowId">
			<input type="hidden" value='<s:property value="supplierId"/>' name="supplierId" id="supplierId">
			
			<!-- 数据列表区域 -->
			<table width="98%" class="ui-table" cellpadding="3" align="center"
				cellspacing="0">
				<thead>
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAll(this,'levelId')">
					</th>
					<th>
						产品名称
					</th>
					
					<th class="w80">
						商品编码
						
					</th>
					
					
					<th class="w100">
					<s:if test="selectViewProductInfo.shopCategoryId == null">
					物理类目
					</s:if><s:else>
						     店铺类目
					</s:else>
			                              
					</th>
					
					<th class="w100">
			              SKU编码
					</th>
				<th class="w60">
					           价格
					</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator  value="page.dataList">
					<tr>
						<td>
							<input type="checkbox" name="levelId" class="checkbox1"
								value='<s:property value="productSkuId"/>^<s:property value="productId"/>'>
								
						</td>
						<td>
						<s:property value="procuctName"/>
						
						</td>
						<td>
							
							<s:property value="productNo"/>
						</td>
						
						<td>
							<s:if test="selectViewProductInfo.shopCategoryId == null">
					<s:property value="categoryName"/>
					</s:if><s:else>
						   
						     <s:property value="categoryObj.categoryName"/>
					</s:else>
							
						</td>
						
						<td>
						    <s:property value="productSkuCode"/>
						</td>
						
					
						
						
					<td>
					<s:property value="price"/>
						</td>
					</tr>
				
				</s:iterator > 
				</tbody>
					
				
			</table>
			<div class="clear p10">
				 <div class="ui-page ui-page-sm right">
				 <table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
				
				 </div>
			
			</div>
			
		</form>

	<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
		
		<script>
		function serSubmit(){
			$("#ADV_queryPageList").submit();
			}

		function empty(){
			$("#citySelValue").val("");
			$("#citySel").val("");
			
			}
		
		function addPro(){
			var checkboxObj=document.getElementsByName("levelId");
			var ck=false;
			var cheVals="";
			var cheProIdVals="";
		//	var params = new Array();
			for(var i=0;i<checkboxObj.length;i++){
				if(checkboxObj[i].checked){
					ck=true;
					var str=checkboxObj[i].value.split('^');
					cheVals+=str[0]+",";//获取skuId
					cheProIdVals+=str[1]+",";//获取产品id
					}
				}
			if(ck == false){
				alert("请选择商品！");
				return;
				}
			$.ajax({
				 url: 'addProductRecommendAction.do',
		          async:false,
		          data: 'productSkuIds='+cheVals+'&productIds='+cheProIdVals+"&windowId="+$("#windowId").val()+'&shopI='+$("#shopId").val(),
		          success: function(info) {
		             if('0' == info){
		             	alert("系统异常!");
		     			return;
		             }else{
		             	alert("添加成功!");
		             	parent.document.getElementById("iframepage1").src='showListRecommend.do?windowId='+$("#windowId").val()+'&shopI='+$("#shopId").val();
		             }
		          }
				});
			}

		function change1(sourceCategoryId,targetCategoryId){
			
			var categoryHtml = '';
			var ck=$("#categoryId1").val();
			if("0" == ck){
				$('#categoryId2').empty().append($("<option value='0'>--二级类目--</option>"));
				return;
				}
			if(targetCategoryId=='categoryId2'){
				//首先，将二级和三级的清空
				$('#categoryId2').empty().append($("<option value='0'>--二级类目--</option>"));
				$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
				categoryHtml = '<option value="0">--二级类目--</option>';
				//$("#categoryId3").find("option[text='--三级类目--']").attr("selected",true);
			}else if(targetCategoryId=='categoryId3'){
				$('#categoryId3').empty().append($("<option value='0'>--三级类目--</option>") ); 
				categoryHtml = '<option value="0">--三级类目--</option>';
			}
			$.ajax({
				dataType:'json',
				url:'queryShopCategoryByAjax.do?parentId='+$('#'+sourceCategoryId).val()+'&shopI='+$("#shopId").val(),
				error:function(){alert('请求失败，请稍后重试或与管理员联系！')},
				success:function(data){
					//console.log("status="+status);
					console.log(data);
					//alert(data.length);
					//var categoryList = data.categoryList;
					//var size = categoryList.length;
					//alert(size);
					for(var i=0;i<data.length;i++){
						categoryHtml += "<option value="+data[i].shopCategoryId+">"+data[i].categoryName+"</option>";
					}
					$('#'+targetCategoryId).html(categoryHtml);
				}
			});
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

		$(document).ready(function(){
			
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
					treeObj.checkNode(node,true,true);
					
					treeObj.expandAll(true);
			    }
		}); 
 </script>
		
	</body>
</html>

