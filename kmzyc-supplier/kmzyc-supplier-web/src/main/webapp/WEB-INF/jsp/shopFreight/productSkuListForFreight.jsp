<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="运费管理-单品运费"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>运费管理-单品运费</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
    <div class="row-fluid"><!--左侧菜单开始-->
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include> <!--左侧菜单结束-->
        <s:form action="/product/findAllProductSkuForFreight.action" namespace="product"
                method="post" id="frm" name="frm">
            <s:hidden name="page" id="page"/>
        <div class="content">
            <div class="row-fluid">
                <div class="navbar">
                </div>
            </div>

            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>店铺 <span class="divider">/</span></li>
                            <li>运费管理 <span class="divider">/</span></li>
                            <li>设置单品运费</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in"><!--内容菜单开始-->
                        <ul class="nav nav-tabs">
                            <li><a href="/supplier/toSupplierFare.action">设置店铺运费</a></li>
                            <li class="active"><a href="#home" data-toggle="tab">设置单品运费</a></li>
                        </ul>
                        <div class="com_topform">
                            <ul>
                                <li>商品标题</li>
                                <li><s:textfield style="width:140px;" name="viewProductSku.productTitle" placeholder=""
                                                 cssClass="ui-input"/></li>
                                </li>
                                <li><s:textfield style="width:120px;" name="viewProductSku.productNo" placeholder=""
                                                 cssClass="ui-input"/></li>
                                <li>SKU编码</li>
                                <li><s:textfield style="width:120px;" name="viewProductSku.productSkuCode" placeholder=""
                                                 cssClass="ui-input"/></li>
                                <li><label>商品状态：</label>
                                    <%-- <s:select list="#request.productStatusMap"
                                                    name="viewProductSku.status" headerKey=""
                                                    headerValue="--全部状态--" cssClass="ui-form-select">
                                        </s:select>--%>
                                    <s:select list="#{3:'已上架',4:'已下架'}" name="viewProductSku.status"
                                              headerKey="" headerValue="--全部状态--" cssClass="ui-form-select">
                                    </s:select>
                                <li>
                                <li>
                                    <a href="javascript:void(0);" class="btn btn-primary  j_productSkuListForFreight_search">
                                        <i class="icon-search icon-white"></i> 搜索</a>
                                </li>
                            </ul>
                        </div>

                        <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                            <tbody>
                            <tr>
                                <td>商品标题</td>
                                <td class="width150">SKU信息</td>
                                <td class="width200">商品编码</td>
                                <td class="width200">SKU编码</td>
                                <td class="width100">状态</td>
                                <td class="width100">运费</td>
                                <td class="width100">操作</td>
                            </tr>
                            </tbody>
                        </table>
                        <input type="hidden" id="rtnMsg" value="-1"/>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table  table-bordered">
                            <tbody>
                            <s:iterator value="pagintion.recordList" id="product">
                                <tr>
                                    <td><s:property value="productTitle"/></td>
                                    <td class="width150">
                                        <s:if test="viewSkuAttrs!=null || viewSkuAttrs.size!=0">
                                            <s:iterator value="viewSkuAttrs" var="v">
                                                <b><s:property value="#v.categoryAttrName"/></b>：<s:property value="#v.categoryAttrValue"/>&nbsp;&nbsp;
                                            </s:iterator>
                                        </s:if>
                                    </td>
                                    <td class="width200"><s:property value="productNo"/></td>
                                    <td class="width200"><s:property value="productSkuCode"/></td>
                                    <td class="width100"><s:property value="#request.productStatusMap[status]"/></td>
                                    <td class="width100">
                                        <s:if test="#request.freeStatusMap[freeStatus] == '是'.toString()">
                                            免邮
                                        </s:if>
                                        <s:else>
                                            <fmt:formatNumber value="${freight}" type="currency" pattern="#,##0.00"/>
                                        </s:else>
                                    </td>
                                    <td class="width100">
                                        <a data-toggle="modal" class="btn btn-mini btn-success thickbox j_modifySkuFreight"
                                           data-productSkuId="<s:property value='productSkuId'/>">设置运费</a>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                        <!-- 下分页组件 -->
                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </s:form>
        <!-- 底部 -->
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>