<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="商品仓库管理"></jsp:param>
    </jsp:include>
    <title>修改商品库存</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <div class="navbar">
                </div>
            </div>
            <div class="row-fluid"><!-- block -->
                <div class="block_01 shopheight">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>商品 <span class="divider">/</span></li>
                            <li>库存管理 <span class="divider">/</span></li>
                            <li>修改库存</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in"><!-- tab内容 -->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">修改库存</a></li>
                            </li>
                        </ul>

                        <!--内容菜单开始-->
                        <!-- 内容修改表单 -->
                        <form class="form-horizontal" action="/product/updateProductStock.action" method="post" id="frm">
                            <!-- 隐藏域 -->
                            <s:hidden name="productStock.stockId"/>
                            <s:hidden name="queryParaForProductStock.skuAttValue"></s:hidden>
                            <s:hidden name="queryParaForProductStock.product.productTitle"></s:hidden>
                            <s:hidden name="queryParaForProductStock.productNo"></s:hidden>
                            <s:hidden name="pageB"></s:hidden>
                            <s:hidden name="startindexB"></s:hidden>
                            <s:hidden name="endindexB"></s:hidden>

                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label" for="typeahead">所属仓库： </label>
                                    <div class="controls">
                                        <input type="text" class="span3" id="typeahead"
                                               data-provide="typeahead" data-items="4" disabled="disabled"
                                               value="<s:property value="#request.suppliersWarehouseMap[productStock.warehouseId]"/>"/>
                                        <p></p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <label class="control-label" for="typeahead">商品SKU： </label>
                                    <div class="controls">
                                        <input type="text" class="span3" id="typeahead"
                                               data-provide="typeahead" data-items="4" disabled="disabled"
                                               value="<s:property value="productStock.skuAttValue"/>">
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="typeahead">商品标题： </label>
                                    <div class="controls">
                                        <input type="text" class="span3" id="typeahead"
                                               data-provide="typeahead" data-items="4" disabled="disabled"
                                               value="<s:property value="productStock.product.productTitle"/>">
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="typeahead">商品编号： </label>
                                    <div class="controls">
                                        <input type="text" class="span3" id="typeahead"
                                               data-provide="typeahead" data-items="4" disabled="disabled"
                                               value="<s:property value="productStock.productNo"/>">
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="typeahead"><span class="required">*</span>库存数量： </label>
                                    <div class="controls">
                                        <s:textfield id="targetQuantity" name="productStock.stockQuality"
                                                     maxlength="10" data-provide="typeahead" data-items="4"/>
                                        <p class="j_targetQuantity_error_span" style="display: none;">
                                            <span id="targetQuantity_error_msg"></span>
                                        </p>
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="typeahead">备注： </label>
                                    <div class="controls">
                                        <s:textarea name="productStock.remark" id="introduce" cols="100" rows="3"/>
                                        <p></p>
                                    </div>
                                </div>
                                <!-- 底部按钮区域 -->
                                <div class="form-actions">
                                    <a href="javascript:void(0);" class="btn btn-success btn-large j_update_productStock">
                                        <i class="icon-arrow-up icon-white"></i> 保 存 </a>
                                    <a href="javascript:void(0);" class="btn btn-success btn-large j_back_productStockList">
                                        <i class="icon-arrow-up icon-white"></i> 返 回 </a>
                                </div>
                            </fieldset>
                        </form>

                        <!-- 返回表单 -->
                        <s:form action="/product/showStockList.action" method="post" id="listfrm" name="listfrm">
                            <s:textfield name="queryParaForProductStock.skuAttValue"/>
                            <s:hidden name="queryParaForProductStock.product.productTitle"></s:hidden>
                            <s:hidden name="queryParaForProductStock.productNo"></s:hidden>
                            <s:hidden name="pageB"></s:hidden>
                            <s:hidden name="startindexB"></s:hidden>
                            <s:hidden name="endindexB"></s:hidden>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>