<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://gys.kmb2b.com/functions" prefix="activity" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="活动管理"></jsp:param>
    </jsp:include>
    <title>活动管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_activity.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <%-- block --%>
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
                            <li>活动 <span class="divider">/</span></li>
                            <li>活动管理 <span class="divider">/</span></li>
                            <s:if test="activitySupplierType == 1">
                                <li>平台活动</li>
                            </s:if>
                            <s:elseif test="activitySupplierType == 2">
                                <li>邀请我参加的活动</li>
                            </s:elseif>
                            <s:else>
                                <li>我已参加的活动</li>
                            </s:else>
                        </ul>
                    </div>

                    <div class="block-content collapse in">
                        <%--开始--%>
                        <ul class="nav nav-tabs">
                            <li <s:if test="activitySupplierType == 1">class="active"</s:if>>
                                <a href="/activity/getActivityList.action?activitySupplierType=1">平台活动</a></li>
                            <li <s:if test="activitySupplierType == 2">class="active"</s:if>>
                                <a href="/activity/getActivityList.action?activitySupplierType=2">邀请我参加的活动</a></li>
                            <li <s:if test="activitySupplierType == 3">class="active"</s:if>>
                                <a href="/activity/getActivityList.action?activitySupplierType=3">我已参加的活动</a></li>
                        </ul>

                        <%--<a class="icon-search icon-white to_click">点击sdfasfd</a>--%>
                        <%--搜索开始--%>
                        <s:form action="/activity/getActivityList.action" method="post" id="frm" name="frm">
                            <s:hidden name="activitySupplierType" id="activitySupplierType"></s:hidden>
                            <s:hidden name="entryType" id="entryType"/>
                            <s:hidden name="page" id="page"/>
                            <div class="com_topform">
                                <ul>
                                    <li>
                                        <label>活动ID：</label>
                                        <s:textfield name="activityParam.activityId"></s:textfield>
                                    </li>
                                    <li>
                                        <label>活动名称：</label>
                                        <s:textfield name="activityParam.activityName"></s:textfield>
                                    </li>
                                    <li>
                                        <label>活动类型：</label>
                                        <s:select name="activityParam.activityType"
                                                  list="#request.activityTypeMap"
                                                  listKey="key"
                                                  listValue="value"
                                                  headerKey=""
                                                  headerValue="所有"
                                                  cssClass="width120"/>
                                    </li>
                                    <li>
                                        <label>活动状态：</label>
                                        <s:select name="activityParam.activityStatus"
                                                  list="#request.activityStatusMap"
                                                  listKey="key"
                                                  listValue="value"
                                                  headerKey=""
                                                  headerValue="所有"
                                                  cssClass="width90"/>
                                    </li>
                                    <s:if test="activitySupplierType == 3">
                                        <li>
                                            <label>报名状态：</label>
                                            <s:select name="activityParam.entryStatus"
                                                      list="#request.entryStatusMap"
                                                      listKey="key"
                                                      listValue="value"
                                                      headerKey=""
                                                      headerValue="所有"
                                                      cssClass="width90"/>
                                        </li>
                                    </s:if>
                                    <li>
                                        <button class="btn btn-primary">
                                            <i class="icon-search icon-white activity_search"></i> 搜索
                                        </button>
                                    </li>
                                </ul>
                            </div>
                            <%--搜索结束--%>
                            <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td width="60px">活动ID</td>
                                    <td>活动名称</td>
                                    <td width="60px">活动类型</td>
                                    <td width="80px">活动费用</td>
                                    <td width="190px">
                                        <s:if test="activitySupplierType == 1 || activitySupplierType == 2">
                                            报名起止时间
                                        </s:if>
                                        <s:else>
                                            报名时间
                                        </s:else>
                                    </td>
                                    <td width="190px">活动起止时间</td>
                                    <td width="120px">活动状态</td>
                                    <s:if test="activitySupplierType == 3">
                                        <td width="120px">报名状态</td>
                                    </s:if>
                                    <td width="90px">操作</td>
                                </tr>
                                </tbody>
                            </table>
                            <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                                <tbody>
                                <s:iterator value="pagintion.recordList" id="activity_list" var="activityVo">
                                    <tr>
                                        <td width="60px"><s:property value="activityId"/></td>
                                        <td><s:property value="activityName"/></td>
                                        <td width="60px">
                                            <s:property value="#request.activityTypeMap[activityType]"/>
                                        </td>
                                        <td width="80px">
                                            <s:if test="chargeType == 1">
                                                免费
                                            </s:if>
                                            <s:elseif test="chargeType == 2">
                                                <s:property value="fixedCharge"/>
                                            </s:elseif>
                                            <s:elseif test="chargeType == 3">
                                                按推广商品数量收费（<s:property value="singleCharge"/>元/SKU）
                                            </s:elseif>
                                            <s:elseif test="chargeType == 4">
                                                按推广佣金比例收费（不低于活动价的<s:property value="commissionRate"/>%）
                                            </s:elseif>
                                        </td>
                                        <td width="190px">
                                            <s:if test="activitySupplierType == 1 || activitySupplierType == 2">
                                                从<s:date name="entryStartTime" format="yyyy-MM-dd HH:mm:ss"/><br>
                                                到<s:date name="entryEndTime" format="yyyy-MM-dd HH:mm:ss"/>
                                            </s:if>
                                            <s:else>
                                                <s:date name="entryTime" format="yyyy-MM-dd HH:mm:ss"/>
                                            </s:else>
                                        </td>
                                        <td width="190px">
                                            从<s:date name="activityStartTime" format="yyyy-MM-dd HH:mm:ss"/><br>
                                            到<s:date name="activityEndTime" format="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td width="120px">
                                            <c:set var="activityDynStatus" value="${activity:getActivityInfoDynamicStatus(activityVo)}"></c:set>
                                            <c:out value="${activityStatusMap[activityDynStatus]}"></c:out>
                                        </td>
                                        <s:if test="activitySupplierType == 3">
                                            <td width="120px">
                                                <c:set var="entryDynStatus" value="${activity:myActivityInfoDynamicStatus(activityVo)}"></c:set>
                                                <c:out value="${entryStatusMap[entryDynStatus]}"></c:out>
                                                <c:if test="${entryDynStatus == 6}">
                                                    <fontred title="" data-placement="bottom" data-toggle="tooltip"
                                                             class="tooltip-hide" data-original-title="<s:property value="remark"/>">
                                                        <s:property value="remark"/>
                                                    </fontred>
                                                </c:if>
                                            </td>
                                        </s:if>
                                        <td width="90px">
                                            <s:if test="activitySupplierType == 1">
                                                <a activity-id="<s:property value="activityId"/>"
                                                   class="to_entry btn width66 btn-mini btn-primary <c:if test="${activity:haveEntryIn(activityVo, false)}">gray</c:if>">
                                                    立即报名
                                                </a>
                                                <br>
                                            </s:if>
                                            <s:elseif test="activitySupplierType == 2">
                                                <a activity-id="<s:property value="activityId"/>"
                                                   class="to_entry btn width66 btn-mini btn-primary <c:if test="${activity:haveEntryIn(activityVo, true)}">gray</c:if>">
                                                    立即报名
                                                </a>
                                                <br>
                                            </s:elseif>
                                            <%--我参加的活动--%>
                                            <s:elseif test="activitySupplierType == 3">
                                                <c:choose>
                                                    <c:when test="${entryDynStatus == 2}"><%--报名成功--%>
                                                        <s:if test="chargeType == 4"><%--渠道推广--%>
                                                            <s:set name="nowTime" value="new java.util.Date()"></s:set>
                                                            <s:set name="startTime" value="activityStartTime"></s:set>
                                                            <s:set name="endTime" value="activityEndTime"></s:set>
                                                            <%--并且正在进行的活动--%>
                                                            <s:if test='#startTime.getTime()<#nowTime.getTime() && #nowTime.getTime()<#endTime.getTime()'>
                                                                <c:if test="${activityDynStatus != 8}">
                                                                    <a activity-id="<s:property value="activityId"/>"
                                                                       class="btn btn-mini btn-primary width66 to_append">追加推广费</a>
                                                                </c:if>
                                                            </s:if>
                                                        </s:if>
                                                    </c:when>
                                                    <c:when test="${entryDynStatus == 3}"><%--已撤销--%>
                                                        <a activity-id="<s:property value="activityId"/>"
                                                           class="btn width66 btn-mini btn-primary to_update">编辑</a>
                                                    </c:when>
                                                    <c:when test="${entryDynStatus == 4}"><%--已提交待缴费--%>
                                                        <a class="btn btn-mini btn-warning width66 to_pay"
                                                           activity-id="<s:property value="activityId"/>">立即缴费</a>
                                                        <a class="btn width66 btn-mini btn-primary to_cancel"
                                                           activity-id="<s:property value="activityId"/>">撤销报名</a>
                                                    </c:when>
                                                    <c:when test="${entryDynStatus == 5}"><%--已提交待审核--%>
                                                        <a class="btn width66 btn-mini btn-primary to_cancel"
                                                           activity-id="<s:property value="activityId"/>">撤销报名</a>
                                                    </c:when>
                                                    <c:when test="${entryDynStatus == 6}"><%--审核不通过--%>
                                                        <a activity-id="<s:property value="activityId"/>"
                                                           class="btn width66 btn-mini btn-primary to_update">编辑</a>
                                                    </c:when>
                                                    <c:when test="${entryDynStatus == 7}"><%--报名失败--%>
                                                    </c:when>
                                                </c:choose>
                                            </s:elseif>
                                            <a class="btn width66 btn-mini to_view"
                                               activity-id="<s:property value="activityId"/>">
                                                详情
                                            </a>
                                        </td>
                                    </tr>
                                </s:iterator>
                                </tbody>
                            </table>
                            <div class="fn-clear fn-mt10">
                                <%-- 分页组件 --%>
                                <tiles:insertDefinition name="paginationBottom"/>
                            </div>
                        </s:form>
                        <%--结束--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>