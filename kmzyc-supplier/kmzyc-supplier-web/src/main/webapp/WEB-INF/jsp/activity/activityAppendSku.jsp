<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <s:form method="post" id="frm" name="frm">
        <s:hidden name="page" id="page"/>
        <s:hidden name="activityId"/>
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    追加推广费用
                </h4>
            </div>
            <div class="mp0">
                <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                    <thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="textc">活动商品：</td>
                    </tr>
                    </tbody>
                </table>
                <table cellpadding="0" cellspacing="0" border="0"
                       class="table table-bordered table-hover" style="margin-top: 10px">
                    <tbody>
                    <tr class="tdbg">
                        <td>商品名称</td>
                        <td width="100px">SUK编码</td>
                        <td width="100px">品牌</td>
                        <td width="80px">单价</td>
                        <td width="80px">库存</td>
                        <td width="60px">活动价</td>
                        <td width="90px">预计销量</td>
                        <td width="100px">推广佣金(%)</td>
                        <td width="90px">追加数量</td>
                        <td width="100px">追加费用(元)</td>
                    </tr>
                    <s:iterator value="existActivitySkuList" status="item">
                        <tr class="editTr">
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].activitySkuId"
                                   value="<s:property value='activitySkuId'/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].activityId"
                                   value="<s:property value='activityId'/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].productSkuCode"
                                   value="<s:property value="productSkuCode"/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].brandName"
                                   value="<s:property value="brandName"/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].price"
                                   value="<s:property value="price"/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].stock"
                                   value="<s:property value="stock"/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].productSkuId"
                                   value="<s:property value='productSkuId'/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].activityPrice"
                                   value="<s:property value='activityPrice'/>"/>
                            <input type="hidden" name="existActivitySkuList[<s:property value='#item.index'/>].commissionRate"
                                   value="<s:property value='commissionRate'/>"/>
                            <td><s:property value="productTitle"/></td>
                            <td><s:property value="productSkuCode"/></td>
                            <td><s:property value="brandName"/></td>
                            <td><s:property value="price"/></td>
                            <td><s:property value="stock"/></td>
                            <td><s:property value="activityPrice"/></td>
                            <td><s:property value="preSaleQuantity"/>件</td>
                            <td><s:property value="commissionRate"/>%</td>
                            <td><input type="text" style="width:50%"
                                       name="existActivitySkuList[<s:property value='#item.index'/>].preSaleQuantity"
                                       class="preSaleQuantity"/>件</td>
                            <td>
                                <span class="money">0.00</span>
                                <input type="hidden"
                                       name="existActivitySkuList[<s:property value='#item.index'/>].skuTotalPrice"/>
                            </td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
                <div class="allmoney">
                        合计：<span class="moneySpan">0.00</span>元
                </div>
                <div class="modal-footer" style="padding-bottom:10px; padding-top: 0; ">
                    <button type="button" class="btn btn-primary to_pay">
                        追加并缴费
                    </button>
                    <button type="button" class="btn btn-default close_button">取消
                    </button>
                </div>
            </div>
        </div>
    </s:form>
    <s:form action="/activity/getActivityList.action" method="post" id="toPayForm" name="toPayForm">
        <s:hidden name="activityId" id="activityId"/>
        <s:hidden name="entryType" id="entryType"/>
    </s:form>
    <%--结束--%>
</div>
</body>
</html>