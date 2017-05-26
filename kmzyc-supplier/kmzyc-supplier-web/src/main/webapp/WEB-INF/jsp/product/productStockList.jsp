<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="商品库存列表"></jsp:param>
    </jsp:include>
    <title>商品库存列表</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
        <div class="content">
            <!-- 表单开始begin -->
            <s:form action="/product/showStockList.action" method="post" id="frm" name="frm">
            <!-- 隐藏域 -->
            <s:hidden name="page" id="page"/>
            <s:hidden name="isLessThanQuantity" id="isLessThanQuantity"></s:hidden>
            <div class="block_01">
                <!-- 面包屑begin -->
                <div class="navbar-inner">
                    <ul class="breadcrumb">
                        <i class="icon-home"></i>
                        <li>商品 <span class="divider">/</span></li>
                        <li>库存管理</li>
                    </ul>
                </div>
                <!-- 面包屑end -->
                <div class="block-content collapse in"><!--开始-->

                    <!-- tab内容开始 -->
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#home" data-toggle="tab">库存管理</a></li>
                    </ul>

                    <!-- 顶上按钮区域begin -->
                    <%-- <div class="ui-well fn-mt20">
                            <a class="ui-button ui-button-success ui-button-lg fn-mr20 j_productStock_add" href="#">
                            <i class="ui-icon ui-icon-add16"></i>
                            添加
                            </a>
                        </div> --%>
                    <!-- 顶上按钮区域end -->
                    <!--搜索表单开始-->
                    <div class="com_topform">
                        <ul>
                            <li class="comleft">
                                <input type="checkbox" id="lessThanFlag"
                                       <s:if test="isLessThanQuantity.indexOf('Y')>-1">checked='checked' </s:if> />
                                <span> 库存告警（数量≤<red>10</red>）</span>
                            </li>
                                <%--<li>
                                    <label></label><input type="checkbox" id="lessThanFlag" <s:if test="isLessThanQuantity.indexOf('Y')>-1">checked='checked' </s:if> /><span> 库存告警（数量<=<fontred>10</fontred>）</span>
                                </li>
                                --%>
                            <li>
                                <label>商品标题：</label><s:textfield name="queryParaForProductStock.product.productTitle"
                                                                 cssStyle="width:120px;"/>
                            </li>
                            <li>
                                <label>商品编码：</label><s:textfield name="queryParaForProductStock.productNo"
                                                                 cssStyle="width:120px;"/>
                            </li>
                            <li>
                                <label>SKU编码：</label><s:textfield name="queryParaForProductStock.skuAttValue"
                                                                  cssStyle="width:120px;"/>
                            </li>
                                <%--
                                <li>
                                    <label>所属仓库：</label><s:select list="#request.warehouseMap" listKey="key" listValue="value" name="queryParaForProductStock.warehouseId" headerKey="" headerValue="--选择仓库--"></s:select>
                                </li>
                                --%>
                            <li>
                                <button class="btn btn-primary j_productStockList_search"><i
                                        class="icon-search icon-white"></i> 搜索
                                </button>
                            </li>
                        </ul>
                    </div>
                    <!--搜索表单结束-->

                    <%-- 上分页组件 class="fn-clear fn-mb10" <div  class="pagination">
                        <tiles:insertDefinition name="pagination" />
                    </div> --%>


                    <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                        <tbody>
                        <tr>
                            <td>商品标题</td>
                            <td class="width100">SKU信息</td>
                            <td class="width100">商品编码</td>
                            <td class="width100">SKU编码</td>
                            <td class="width100">商家货号</td>
                            <td class="width80">品牌</td>
                            <td class="width80">上架时间</td>
                            <td class="width50">状态</td>
                            <td class="width70">库存</td>
                        </tr>
                        </tbody>
                    </table>

                    <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                        <tbody>
                        <s:iterator value="pagintion.recordList" id="productStock">
                            <tr>
                                <td class="textc font12"><s:property value="product.productTitle" escape="false"/></td>
                                <td class="width100 font12">
                                    <s:iterator value="skuAttrList">
                                        <s:property value="categoryAttrName"/>:<b><s:property value="categoryAttrValue"/></b>
                                        </br>
                                    </s:iterator>
                                </td>
                                <td class="width100 font12" align="center"><s:property value="productNo"/></td>
                                <td class="width100 font12" align="center"><s:property value="skuAttValue"/></td>
                                <td class="width100 font12"><s:property value="product.sellerSkuCode"/></td>
                                <td class="width80 font12" align="center">
                                    <s:property value="product.brandName" escape="false"/>
                                </td>
                                <td class="width80 font12">
                                    <s:date name="product.upTime" format="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td class="width50 font12"><s:property
                                        value="#request.productStatusMap[product.status]"/></td>
                                <%--<td class="width100" align="center"><s:property value='#request.warehouseMap[warehouseId]' /></td>--%>
                                <td class="width70 font12">
                                    <div class="editku j_to_update_stock" id="div_<s:property value='stockId'/>"
                                         data-stockId="<s:property value='stockId'/>"
                                         data-original-quantity="<s:property value="stockQuality" />">
                                        <s:if test="stockQuality<=10">
                                            <fontred><s:property value="stockQuality"/></fontred>
                                        </s:if>
                                        <s:else>
                                            <s:property value="stockQuality"/>
                                        </s:else>
                                    </div>
                                    <input class="j_stock_update" type="text" maxlength="8"
                                           data-stockId="<s:property value='stockId'/>"
                                           data-original-quantity="<s:property value="stockQuality" />"
                                           id="input_<s:property value="stockId"/>"
                                           value="<s:property value="stockQuality" />" style="display:none"/>
                                    <%--<a href="javscript:void(0);" title="修改" data-stockId="<s:property value='stockId'/>"
                                       class="j_productStock_update"><i class="icon-pencil"></i></a>
                                    <img title="修改" style="cursor: pointer;"
                                         src="${staticUrl}${imageBaseUrl}/little_icon/xiugai1.png"
                                         data-stockId="<s:property value='stockId'/>" class="j_productStock_update"/>--%>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                    <!--结束-->
                </div>

                <!-- 下分页组件 -->
                <div class="fn-clear fn-mt10">
                    <tiles:insertDefinition name="paginationBottom"/>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!-- 左侧内容区域开始 end -->
</s:form>
<!-- 表单结束end -->
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>