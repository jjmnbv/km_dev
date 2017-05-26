<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="退换货列表"></jsp:param>
    </jsp:include>
    <title>退换货列表</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_order.jsp"></jsp:include>

        <div class="content">
            <s:form action="findAllReturnNotes.action" method="post" id="frm" name="frm" namespace="supplier">
                <s:hidden name="page" id="page"/>
                <div class="row-fluid"><!-- block -->
                    <div class="block_01">
                        <div class="navbar-inner">
                            <ul class="breadcrumb">
                                <i class="icon-home"></i>
                                <li>订单 <span class="divider">/</span></li>
                                <li>退换货订单</li>
                            </ul>
                        </div>

                        <div class="block-content collapse in"><!--开始-->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#home" data-toggle="tab">退换货订单</a></li>
                            </ul>
                            <!--搜索开始-->

                            <div class="com_topform">
                                <ul>
                                    <li>
                                        <s:select id="queryTypeForOrderNo" name="queryTypeForOrderNo"
                                                  list="#request.orderNoQueryTypeMap" listKey="key"
                                                  listValue="value" headerKey="" style="width:100px;"></s:select>
                                    </li>
                                    <li>
                                        <s:textfield id="queryTypeForOrderNoValue" name="queryTypeForOrderNoValue"
                                                     cssStyle="width:200px;"/>
                                    </li>
                                    <li><label>申请人：</label>
                                        <s:textfield id="proposer" name="selectOrderAlterVo.proposer"/>
                                    </li>
                                    <li>
                                        <label>申清时间：</label>
                                        <input type="text" placeholder="" maxlength="10" readonly="readonly"
                                               class="ui-input ui-form-date" name="createBegin" style="width:180px;"
                                               value='<s:property value="createBegin" />' id="createBegin"
                                               onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'createEnd\'),\'%y-%M-%d %H:%m:%s\'}'})"/>
                                        &nbsp;至&nbsp;
                                        <input type="text" placeholder="" maxlength="10" readonly="readonly"
                                               class="ui-input ui-form-date" style="width:180px;" name="createEnd"
                                               value='<s:property value="createEnd" />' id="createEnd"
                                               onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'createBegin\')}'})"/>
                                    </li>
                                    <li><label>退换货状态：</label>
                                            <s:select id="proposeStatus" name="selectOrderAlterVo.proposeStatus"
                                                      list="#request.orderAlterProposeStatusMap" listKey="key"
                                                      listValue="value" headerKey="" headerValue="--全部--"></s:select>
                                    </li>
                                    <li>
                                        <button class="btn btn-primary j_returnNot_search">
                                            <i class="icon-search icon-white"></i> 搜索
                                        </button>
                                        &nbsp;&nbsp;
                                        <button class="btn btn-primary j_returnNot_export">导出订单</button>
                                    </li>
                                </ul>
                            </div>
                            <!--搜索结束-->
                            <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                                <thead></thead>
                                <tbody>
                                <tr>
                                    <td>商品</td>
                                    <td class="width120">SKU编码</td>
                                    <td class="width80">服务类型</td>
                                    <td class="width100">申请人</td>
                                    <td class="width90">申请时间</td>
                                    <td class="width100">审核人</td>
                                    <td class="width90">审核时间</td>
                                    <td class="width60">订单状态</td>
                                    <td class="width70">操作</td>
                                </tr>
                                </tbody>
                            </table>

                            <s:iterator value="pagintion.recordList">
                                <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                                    <thead>
                                    <tr class="tablesbg">
                                        <th colspan="8" class="textL">退换货编号 ：
                                            <a href='javascript:void(0);' class="hrefOrderAlterCode" data-hr='<s:property value="orderAlterCode" />'><s:property value="orderAlterCode"/></a>
                                            订单号：<a href='javascript:void(0);' class="hrefOrderCode" data-hr1='<s:property value="orderCode" />'><s:property value="orderCode"/></a>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" class="newform">
                                                <tbody>
                                                <tr>
                                                    <td width="120px">
                                                        <a href="<s:property value='#request.productCMSPath'/><s:property value='productSkuId'/>.shtml"
                                                            target="_blank" class="pull-left">
                                                            <img class="thumbnail" src="<s:property value="imagePath"/><s:property value="imgPath" />">
                                                        </a>
                                                    </td>
                                                    <td class="textc">
                                                        <a href="<s:property value='#request.productCMSPath'/><s:property value='productSkuId'/>.shtml"
                                                            target="_blank" title="<s:property value="commodityTitle" />">
                                                            <s:property value="commodityTitle"/>
                                                        </a>
                                                    </td>
                                                    <td width="120px"><s:property value="skuCode"/></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                        <td class="width80"><s:property value="alterTypeStr"/></td>
                                        <td class="width100"><s:property value="proposer"/></td>
                                        <td class="width90"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class="width100"><s:property value="Checker"/></td>
                                        <td class="width90"><s:date name="checkDate" format="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td class="width60"><s:property value="proposeStatusStr"/></td>
                                        <td class="width70">
                                                <%--<s:if test='handleResult >= 2'>
                                                    <button class="btn btn-mini btn-success width66 j_view_returnNot" data-alterCode="<s:property value='orderAlterCode'/>" data-eraInfoId="<s:property value=''/>">处理</button>
                                                </s:if>
                                                <s:if test='proposeStatus==1'>
                                                    <button class="btn btn-mini btn-success width66 j_audit_returnOrder" data-alterCode="<s:property value='orderAlterCode'/>" data-orderCode='<s:property value="orderCode" />'>审核</button>
                                                    <br/>
                                                </s:if>
                                                --%>
                                            <button class="btn btn-mini btn-primary j_view_returnNot"
                                                    data-alterCode="<s:property value='orderAlterCode'/>">查看详情
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </s:iterator>
                            <!--结束-->
                        </div>
                        <!-- 下分页组件 -->
                        <div class="fn-clear fn-mt10">
                            <tiles:insertDefinition name="paginationBottom"/>
                        </div>
                    </div>
                </div>
            </s:form>
            <s:form action="" method="POST" id="viewForm"></s:form>
            <s:form action="/ajaxJson/closeShop.action" method="POST" id="closeForm"></s:form>
            <s:form action="/ajaxJson/deleteShopMain.action" method="POST" id="deleteForm"></s:form>
            <s:form action="/ajaxJson/applyShop.action" method="POST" id="applyForm"></s:form>
            <s:form action="/ajaxJson/cancelShop.action" method="POST" id="cancelForm"></s:form>
            <s:form action="/ajaxJson/publishHomePage.action" method="POST" id="publishForm"></s:form>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>