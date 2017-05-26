<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="店内分类管理"></jsp:param>
</jsp:include>
<title>店内分类管理</title>
<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<s:if test="msgTip=='noShop' || 'noShop'.equals(msgTip)">
	<div class="container-fluid">
		<div class="row-fluid">
			<!--左侧菜单开始-->
			<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
			<!--左侧菜单结束-->
			<div class="content">
				<div class="row-fluid">
					<!-- block -->
					<div class="block_01 shopheight">
						<div class="navbar-inner">
							<ul class="breadcrumb">
								<i class="icon-home"></i>
								<li>店铺 <span class="divider">/</span></li>
								<li>店内分类</li>
							</ul>
						</div>
						<div class="block-content prompt">
							亲爱的商家，您好！您还没有申请店铺，请先去提交申请哦。
						</div>
						<div class="btnnobg">
							<a data-id="s_1" data-href="/supplier/toAddOrUpdateShopMain.action" href="javascript:void(0);"
							   class="btn btn-success btn-large j_shop_apply">立即申请店铺</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:if>
<s:else>
<div class="container-fluid">
<div class="row-fluid"> 
	<!--左侧菜单开始-->
	<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
  	<!--左侧菜单结束-->
  
  	<div class="content">
    <div class="row-fluid"> 
		<!-- block -->
		<div class="block_01">
		<!-- 面包屑 -->
		<div class="navbar-inner">
          	<ul class="breadcrumb">
				<i class="icon-home"></i>
				<li>店铺 <span class="divider">/</span></li>
				<li>店铺分类</li>
		  	</ul>
        </div>
    
    	<input type="hidden" id="treeData" value="<s:property value="jsonDataForTreeNode"/>"/>
        <div class="block-content collapse in"> 
			<!--内容菜单开始-->
			<ul class="nav nav-tabs" id="tabsForShop">
				<li><a href="#home" data-toggle="tab">店铺分类</a></li>
				<li><a href="#home" data-toggle="tab">已分类商品</a></li>
				<li><a href="#home" data-toggle="tab">未分类商品</a></li>
			</ul>
			<!--内容菜单结束-->
          	<!-- 店内分类管理 begin -->
			<div class="ct"  id="shopCatList" style="display:none;">
				<div class="alert alert-block">
					1、商品分类有助于商家在后台更方便管理自己的商品，前端店铺的分类展示也能更好引导消费者购买。<br>
					2、商品只能归类到子分类。添加顶级分类时，请同时添加子分类。
				</div>
				<br/>
				<button class="btn btn-danger j_showAddDiaolog" level_type="1"><i class="icon-plus icon-white"></i>添加顶级分类</button>
				<br/>
				<br/>
				<!-- 树结构显示 begin -->
				<form id="categoryFrm"  name="categoryFrm" method="post" action="supplier/showShopCategoryList.action">		
					<input type="hidden" id="basePath" value="${basePath }"/>
					<s:hidden name="shopId" id="shopId"></s:hidden>
						<%--<div class="ui-well fn-mt20">
							<input type="button"  class="ui-button ui-button-success j_showAddDiaolog" level_type="1" value="添加顶级类目"/>
						</div>--%>
					<table width="100%">
						<tr>
						    <td align="left" valign="top">
						    	<p style="text-align:left">
									<input type="button"  class="ui-button ui-button-success j_expandAll" href="#"  value="展开所有"/> &nbsp;|&nbsp;
									<input type="button"  class="ui-button ui-button-success j_unExpandAll" value="关闭所有"/></p>
						    	<div class="dtree">			
									<ul id="tree" class="ztree"></ul>
								</div>
						    </td>
						</tr>
					</table>
				</form>
				<!-- 树结构显示 end -->
				<!-- 新增 begin -->
				<div id="addContent" style="display:none;">
					<form class="ui-form ui-form-info fn-mt20" action="/supplier/saveShopCategoryByAjax.action" method="post" id="saveFrm">
					<s:hidden name="shopId" id="shopIdForAdd"/>
					<s:hidden name="shopCategory.categoryLevel" id="categoryLevelForAdd"/>
					<s:hidden name="shopCategory.parentCategoryId" id="parentCategoryIdForAdd"/>
					<fieldset>
						<legend>店内分类基本信息</legend>
						<div class="ui-form-item">
							<label class="ui-form-label"><b style="color:red;">*&nbsp;</b>类目名称：</label>
							<input type="text" class="width200" maxLength="20" name="shopCategory.categoryName" id="categoryNameForAdd" />
						</div>
						<div class="ui-form-item">
							<label class="ui-form-label"><b style="color:red;">*&nbsp;</b>类目排序号：</label>
							<s:textfield name="shopCategory.sortno" id="sortNoForAdd" maxlength="4"></s:textfield>
						</div>
						<!-- 顶级栏目才做是否推荐  或者只有在父级目录为重点推荐时自己才可做选择是否重点推荐-->
						<div class="ui-form-item j_isSuggestContent" >
						<%--  <label class="ui-form-label" for="productStock">是否重点推荐：</label>
							<s:radio list="#{'0':'不推荐','1':'推荐'}" value="0" name="isSuggest" theme="simple"></s:radio>  --%>
						</div>
						<!-- 子级类目新增 -->
						<%--   <s:hidden name="shopCategory.categoryCode" id="parentCategoryCodeForAdd"/> --%>
						<div class="ui-form-item j_parentName_content">
								<label class="ui-form-label">上级类目：</label>
								<s:property value="parentCategoryName"/>
						</div>
						<div class="ui-form-item j_isExpandAll_content">
							<%-- <label class="ui-form-label" for="productStock">是否默认展开：</label>
								<s:radio list="#{'0':'不展开','1':'展开'}" value="0" name="shopCategory.isExpandAll" theme="simple"></s:radio>  --%>
						</div>
						<div class="ui-form-item">
							<label class="ui-form-label">类目备注：</label>
							<s:textarea  cssClass="ui-input" name="shopCategory.remark" id="introduceForAdd" cssStyle="margin: 0px 0px 3px; width: 312px; height: 91px; resize:none"/>
						</div>
						</br>
						<p style="text-align:center;">
							<input id="submitButton" class="ui-button ui-button-success ui-button-lg j_shopCategoryForAdd" type="button" value="保存">&nbsp;&nbsp;
							<input type="button" class="ui-button ui-button-default ui-button-lg j_closeDialog" value="关闭"/></p>
						</br>
					</fieldset>
				</form>
				</div>
				<!-- 新增 end -->

				<!-- 修改表单  begin-->
				<div class="fn-p20" id="updateContent" style="display:none">
				<form class="ui-form ui-form-info fn-mt20" action="/supplier/updateShopCategoryByAjax.action" method="post" id="updateFrm">
					<s:hidden name="shopCategory.shopCategoryId" id="shopCategoryIdForUpdate"/>
					<s:hidden name="shopCategory.shopId" id="shopIdForUpdate"/>
					<s:hidden name="shopCategory.parentCategoryId" id="parentIdForUpdate"/>
					<s:hidden name="shopCategory.categoryLevel" id="categoryLevelForUpdate"/>
					<s:hidden name="originalName" id="originalName"/>
					<s:hidden name="originalSortNo" id="originalSortNo"/>
					<fieldset>
						<legend>店内分类基本信息</legend>
						<%--<span id="tip" style="color:red;"></span>--%>
						<div class="ui-form-item">
							<label class="ui-form-label"><b style="color:red;">*&nbsp;</b>类目名称：</label>
							<s:textfield name="shopCategory.categoryName" id="categoryNameForUpdate" cssClass="width200" maxlength="20"></s:textfield>
						</div>
						<div class="ui-form-item">
							<label class="ui-form-label"><b style="color:red;">*&nbsp;</b>类目排序号：</label>
							<s:textfield name="shopCategory.sortno" id="sortNoForUpdate" maxlength="4"></s:textfield>
						</div>
						<%-- 顶级栏目才做是否推荐 或者说只有在顶级目录是做推荐的时候才有推荐按钮做勾选 --%>
						<div class="ui-form-item j_isSuggestContent_forUpdate">
						</div>
						<%-- 只有一级类目才有是否默认展开 --%>
						<div class="ui-form-item j_isExpandAll_content_forUpdate">
						</div>
						<div class="ui-form-item">
							<label class="ui-form-label">类目备注：</label>
							<s:textarea cssClass="ui-input" name="shopCategory.remark" id="introduceForUpdate"
										cssStyle="margin: 0px 0px 3px; width: 312px; height: 91px;resize:none;"/>
						</div>
						<br/>
						<p style="text-align:center;">
							<input id="submitButton" class="ui-button ui-button-success ui-button-lg j_shopCategoryForUpdate" type="button" value="确认修改">&nbsp;&nbsp;
							<input type="button" class="ui-button ui-button-default ui-button-lg j_closeDialog" value="关闭"/></p>
						</br>
					</fieldset>
				</form>
				</div>
				<!-- 修改表单  end-->

				<!-- 删除表单begin -->
				<form id="deleteForm" method="post" action="/supplier/deleteShopCategoryByAjax.action">
					<s:hidden name="shopId" id="shopId"></s:hidden>
					<s:hidden name="shopCategory.shopCategoryId" id="shopCategoryId"></s:hidden>
				</form>
				<!-- 删除表单end -->
				<!-- 排序表单 begin -->
				<form id="updateSortForm" method="post" action="/supplier/updateSortNoByAjax.action">
					<s:hidden name="categoryId" id="sourceCategoryId"></s:hidden>
					<s:hidden name="sourceSortNo" id="sourceSortNo"></s:hidden>
					<s:hidden name="targetCategoryId" id="targetCategoryId"></s:hidden>
					<s:hidden name="targetSortNo" id="targetSortNo"></s:hidden>
				</form>
				<!-- 排序表单 end -->
			</div>
			<!-- 店内分类管理 end -->
			
			<!-- 分页查询表单 -->
			<form id="frm"  name="frm" method="post" action="/suppliers/showProductListForShopCategory.action">	
			<s:hidden name="page" id="page" />
			<s:hidden name="showType"/>
			<s:hidden name="shopId" id="shopId"/>
			
			<!-- 已分类商品 begin -->
			<div class="ct" id="relationList" style="display:none;">
		 		<div class="alert alert-block">
					店铺分类仅显示上架及上架过的商品，如果要修改商品分类，可待商品上架后进行修改
				</div>

				<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
					<tbody>
					<tr class="trbg">
						<th>商品标题</th>
						<th>商品编码</th>
						<th>店内分类</th>
						<th>操作</th>
					</tr>
			  		<s:iterator value="#request.relationList" id="product">
						<tr>
							<td><s:property value="product.productTitle" /></td>
							<td><s:property value="product.productNo" /></td>
							<td>
							<span id="shopCategoryNames_<s:property value='product.id'/>">
								 <s:iterator value="shopCategorys" status="count">
									<s:property value="categoryName" /><s:if test="!#count.last">, </s:if>
								 </s:iterator>
							 </span> &nbsp;&nbsp;&nbsp;
							 <span id="shopCategoryIds_<s:property value='product.id'/>" style="display:none">
								 <s:iterator value="shopCategorys">
									<s:property value="shopCategoryId" />,
								 </s:iterator>
							 </span>
							</td>
							<td>
								<a href="javascript:void(0);" data-productid="<s:property value='product.id'/>"
								   class="j_manage_alreadyShopCat" >编辑</a>&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" data-productid="<s:property value='product.id'/>"
								   class="j_view_detail" data-showType="relationList">详情</a>
							</td>
						</tr>
					 </s:iterator>
					</tbody>
				</table>
			</br>
			</div>
			<!-- 已分类商品 end -->						
			
			<!-- 未分类商品 begin 20150911 update 改版后这个tab已经去掉了 20151013 需求重新将其启用-->
			<div class="ct" id="unRelationList" style="display:none;">
				<div class="alert alert-block">
					店铺分类仅显示上架及上架过的商品，如果要修改商品分类，可待商品上架后进行修改
				</div>
				<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
					<tbody>		
					<tr class="trbg">
						<th>商品标题</th>
						<th>商品编码</th>
						<th>店内分类</th>
						<th>操作</th>
					</tr>
					<s:iterator value="pagintion.recordList" id="product">
						<tr>
							<td><s:property value="product.productTitle" /></td>
							<td><s:property value="product.productNo" /></td>
							<td><span id="showName_<s:property value='product.productId'/>"></span></td>
							<td>
								<a href="javascript:void(0);" data-productid="<s:property value='product.id'/>"
								   class="j_manage_unShopCat" >编辑</a>&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" data-productid="<s:property value='product.id'/>"
								   class="j_view_detail" data-showType="unRelationList" >详情</a>
							</td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			</div>		
			<!-- 未分类商品 end -->	
			<!-- 分页组件 begin  -->
			<div class="fn-clear fn-mt10" id="pagination">
				<tiles:insertDefinition name="paginationBottom" />
			</div>
			<!-- 分页组件 end  -->	
			</form>
        </div>
	</div>
    </div>
	</div>
</div>
</div>
</s:else> 

<!-- 未分类商品选择分类的时候弹出的选择框 20150911 update 大改版后这个已经去掉了  20151013 重新加上-->
<div id="unRelationDiv" style="display:none">
	<form id="relationForm" method="post" action="/supplier/relationProductWithShopCategoryByAjax.action">
		<input type="hidden" name="productPara.id" id="productId">
		<input type="hidden" name="shopCategoryIds" id="shopCategoryIds"/>
		
		<div class="fn-p20">
			<div class="ui-breadcrumb"></br>&nbsp;&nbsp;未分类商品选择分类</div>
			<div class="newtree">
				<ul id="treeForUnrelation" class="ztree"></ul>
			</div>
			<br/>
			<p style="text-align: center">
				<input class="ui-button ui-button-success  j_save_shopCategory" type="button" value="保存">&nbsp;&nbsp;
				<input type="button" class="ui-button ui-button-default  j_closeForUnrelation" value="关闭" />
			</p>
			</br>
		</div>
	</form>
</div>
<!-- 未分类商品选择分类的时候弹出的选择框 -->
<!-- 已分类分类商品选择分类的时候弹出的选择框 -->
<div id="relationDiv" style="display:none">
	<form id="relationForm" method="post" action="/supplier/relationProductWithShopCategoryByAjax.action">
		<input type="hidden" name="productPara.id" id="productId">
		<input type="hidden" name="shopCategoryIds" id="shopCategoryIds"/>
	
		<div class="fn-p20">
			<div class="ui-breadcrumb"></br>&nbsp;&nbsp;已分类商品选择分类</div>
			<div class="newtree">
				<ul id="treeForRelation" class="ztree"></ul>
			</div>
			<br />
			<p style="text-align: center">
				<input class="ui-button ui-button-success  j_save_shopCategory" type="button" value="保存">&nbsp;&nbsp;
				<input type="button" class="ui-button ui-button-default  j_closeForRelation" value="关闭" />
			</p>
			</br>
		</div>
	</form>
</div>
<!-- 已分类商品选择分类的时候弹出的选择框 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<script type="text/javascript">
	shopCategoryTreeData=document.getElementById("treeData").value;
	if("none"!=shopCategoryTreeData){
		shopCategoryTreeData = eval('(' + shopCategoryTreeData + ')');
	}else{
		shopCategoryTreeData="none";
	}
</script>
</body>
</html>