<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<div class="block-content">
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">活动基本信息</a></li>
    </ul>
    <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
        <tbody>
        <tr>
            <td class="width200 shoptR2">活动标题：</td>
            <td class="textc">
                <s:property value="activity.activityName"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动类型：</td>
            <td class="textc">
                <s:property value="#request.activityTypeMap[activity.activityType]"/>
                <s:hidden name="activity.activityType" id="activityType"></s:hidden>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动费用：</td>
            <td class="textc">
                <s:hidden name="activity.chargeType" id="chargeType"></s:hidden>
                <s:if test="activity.chargeType == 1">
                    免费
                </s:if>
                <s:elseif test="activity.chargeType == 2">
                    <s:hidden name="activity.activityCharge.fixedCharge" id="fixedCharge"></s:hidden>
                    <s:property value="activity.activityCharge.fixedCharge"/>
                </s:elseif>
                <s:elseif test="activity.chargeType == 3">
                    <s:hidden name="activity.activityCharge.singleCharge" id="singleCharge"></s:hidden>
                    按推广商品数量收费（<s:property value="activity.activityCharge.singleCharge"/>元/SKU）
                </s:elseif>
                <s:elseif test="activity.chargeType == 4">
                    <s:hidden name="activity.activityCharge.commissionRate" id="commissionRate"></s:hidden>
                    按推广佣金比例收费（不低于活动价的<s:property value="activity.activityCharge.commissionRate"/>%）
                </s:elseif>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">商家范围：</td>
            <td class="textc">
                <s:if test="activity.supplierChoiceType == 1">
                    全部商家
                </s:if>
                <s:elseif test="activity.supplierChoiceType == 2">
                    按商家店铺大于等于<s:property value="activity.activitySupplierScore.greatEqualPoint"/>分选择
                </s:elseif>
                <s:elseif test="activity.supplierChoiceType == 3">
                    <%--按类目选择:--%>
                    <s:iterator value="activity.activityCategorysList" status="item">
                        <s:property value="categoryName"/>
                        <s:if test="!#item.last">
                            、
                        </s:if>
                    </s:iterator>
                </s:elseif>
                <s:elseif test="activity.supplierChoiceType == 4">
                    指定商家
                </s:elseif>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">品牌范围：</td>
            <td class="textc">
                <s:if test="activity.brandChoiceType == 1">
                    全部品牌
                </s:if>
                <s:elseif test="activity.brandChoiceType == 2">
                    <%--指定品牌：--%>
                    <s:iterator value="activity.activityBrandList" status="item">
                        <s:property value="brandName"/>
                        <s:if test="#item.first">
                            <s:set name="brandIds" value="brandId"></s:set>
                        </s:if>
                        <s:else>
                            <s:set name="brandIds" value="#brandIds + ',' + brandId"></s:set>
                        </s:else>
                        <s:if test="!#item.last">
                            、
                        </s:if>
                    </s:iterator>
                </s:elseif>
                <s:hidden name="brandIds" id="brandIds"></s:hidden>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">商家报名名额限制：</td>
            <td class="textc">
                <s:if test="activity.supplierMaximum == 0">
                    不限制
                </s:if>
                <s:elseif test="activity.supplierMaximum > 0">
                    限制<s:property value="activity.supplierMaximum"/>个商家
                </s:elseif>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动商品数量限制：</td>
            <td class="textc">
                <s:if test="activity.skuMaximum == 0">
                    不限制
                </s:if>
                <s:elseif test="activity.skuMaximum > 0">
                    限制<s:property value="activity.skuMaximum"/>个商品
                </s:elseif>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">报名起止时间：</td>
            <td class="textc">
                <s:date name="activity.entryStartTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;至&nbsp;<s:date name="activity.entryEndTime" format="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动起止时间：</td>
            <td class="textc">
                <s:date name="activity.activityStartTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;至&nbsp;<s:date name="activity.activityEndTime" format="yyyy-MM-dd HH:mm:ss"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动标签：</td>
            <td class="textc">
                <s:property value="activity.activityLabel"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">活动级别：</td>
            <td class="textc">
                <s:property value="#request.activityLevelMap[activity.activityLevel]"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">所属行业：</td>
            <td class="textc">
                <s:property value="activity.industry"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">关键字：</td>
            <td class="textc">
                <s:property value="activity.activitySeo"/>
            </td>
        </tr>
        <tr>
            <td class="width200 shoptR2">简介：</td>
            <td class="textc">
                <s:property value="activity.activityDesc"/>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<div class="block-content">
    <ul id="myTab" class="nav nav-tabs navbottom">
        <li class="active"><a href="#tab01" data-toggle="tab">活动说明</a></li>
        <li><a href="#tab02" data-toggle="tab">活动答疑</a></li>
    </ul>
    <div class="content-box">
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="tab01">
                <s:property value="activity.activityIntroduce" escape="false"/>
            </div>
            <div class="tab-pane fade" id="tab02">
                <s:property value="activity.activityQuestions" escape="false"/>
            </div>
        </div>
    </div>
</div>