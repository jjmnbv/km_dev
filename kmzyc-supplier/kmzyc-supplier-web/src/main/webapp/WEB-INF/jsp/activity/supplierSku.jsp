<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="活动详情"></jsp:param>
    </jsp:include>
    <title>活动详情</title>
</head>
<body>
<div class="block-content collapse in">
    <%--搜索开始--%>
    <s:form action="/activity/getProductSkuList4Activity.action" method="post" id="frm" name="frm">
        <s:hidden name="page" id="page"/>
        <s:hidden name="brandIds"></s:hidden>
        <s:hidden name="haveSkuId"></s:hidden>
        <div class="com_topform">
            <ul>
                <li>
                    <label>SUK编码：</label>
                    <s:textfield name="viewProductSku.productSkuCode"></s:textfield>
                </li>
                <li>
                    <label>产品标题：</label>
                    <s:textfield name="viewProductSku.productTitle"></s:textfield>
                </li>
                <li>
                    <label>物理类目：</label>
                        <s:select list="#request.categoryList"
                                  name="viewProductSku.bCategoryId" id="categoryId1"
                                  listKey="categoryId" listValue="categoryName"
                                  headerKey="" headerValue="所有"
                                  data-sourceid="categoryId1" data-targetid="categoryId2"
                                  data-subTargetId="categoryId3" cssClass="j_change_bCategory"/>
                <li>
                <li>
                    <s:select list="#request.mCategoryList"
                              name="viewProductSku.mCategoryId" id="categoryId2"
                              listKey="categoryId" listValue="categoryName"
                              headerKey="" headerValue="所有"
                              data-sourceid="categoryId2" data-targetid="categoryId3"
                              cssClass="j_change_mCategory"></s:select>
                </li>
                <li>
                    <s:select list="#request.sCategoryList"
                              headerKey="" headerValue="所有"
                              id="categoryId3" name="viewProductSku.categoryId"
                              listKey="categoryId" listValue="categoryName"
                              cssClass=""></s:select>
                </li>
                <li>
                    <button class="btn btn-primary"><i class="icon-search icon-white sku_search"></i> 搜索</button>
                </li>
            </ul>
        </div>
        <%--搜索结束--%>

        <table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-hover"
               style="margin-top: 10px">
            <tbody>
            <tr class="tdbg">
                <td width="50px">
                    <input type="checkbox" id="optionsCheckbox" value="option1" class="allChecked checkt">
                </td>
                <td width="100px">SUK编码</td>
                <td width="200px">产品标题</td>
                <td width="200px">SUK描述</td>
                <td width="80px">品牌</td>
            </tr>
            <s:iterator value="pagintion.recordList" id="product">
                <tr id="<s:property value="productSkuId"/>">
                    <td><input type="checkbox" class="checkt skuIdChecked"
                               name="productSkuId"
                               sku-code="<s:property value="productSkuCode"/>"
                               product-title="<s:property value="productTitle"/>"
                               brand-name="<s:property value="searchBrandName"/>"
                               price="<s:property value="price"/>"
                               stock="<s:property value="systemCode"/>"
                               value="<s:property value="productSkuId"/>">
                    </td>
                    <td><s:property value="productSkuCode"/></td>
                    <td>
                        <s:property value="productTitle"/>
                    </td>
                    <td>
                        <s:property value="categoryName" escape="false"/>
                    </td>
                    <td><s:property value="searchBrandName"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <div class="fn-clear fn-mt10">
            <%-- 分页组件 --%>
            <tiles:insertDefinition name="paginationBottom"/>
        </div>

        <div >
            <button type="button" class="btn btn-primary select_sku" data-toggle="collapse">
                确认
            </button>
            <button type="button" class="btn close_button"
                    data-dismiss="modal">取消
            </button>
        </div>
    </s:form>
    <%--结束--%>
</div>
</body>
</html>