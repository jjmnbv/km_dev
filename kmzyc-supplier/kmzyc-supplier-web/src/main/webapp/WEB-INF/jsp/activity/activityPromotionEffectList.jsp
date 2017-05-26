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
                            <li>活动推广效果 <span class="divider">/</span></li>
                            <li>已推广活动 </li>
                        </ul>
                    </div>

                    <div class="block-content collapse in">
                        <%--开始--%>
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">已推广活动</a></li>
                        </ul>

                        <%--搜索开始--%>
                        <s:form action="/supplierActivityResultAction/findMyPromotionEffectList.action" method="post" id="frm" name="frm">
                            <s:hidden name="activitySupplierType" id="activitySupplierType"></s:hidden>
                            <s:hidden name="page" id="page"/>
                            <div class="com_topform">
                                <ul>
                                    <li>
                                        <label>活动ID：</label>
                                        <s:textfield align="left" name="activityParam.activityId" onkeyup="value=value.replace(/[^\d]/g,'')"></s:textfield>
                                    </li>
                                    <li>
                                        <label>活动名称：</label>
                                        <s:textfield name="activityParam.activityName"></s:textfield>
                                    </li>
                                    <li>
                                        <label>活动类型：</label>
                                        <s:select list="#request.activityTypeMap"
						                    name="activityParam.activityType" id="" headerKey=""
						                    headerValue="--全部--">
						                </s:select>
                                    </li>
                                    <li>
                                        <label>状态：</label>
                                        <s:select list="#{5:'活动进行中',6:'活动已结束',8:'活动中止'}" name="activityParam.activityStatus" id="" headerKey=""
						                    headerValue="--全部--"> 
						                </s:select>
                                    </li>
                                    <li>
                                        <label>报名时间：</label>
										<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" 
											id="entryStartTime" name="activityParam.entryStartTime" style="width:180px;" value='' 
											onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'entryStartTime\'),\'%y-%M-%d %H:%m:%s\'}'})" />
										至&nbsp;
										<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" 
											id="entryEndTime" name="activityParam.entryEndTime" style="width:180px;" value='' 
											onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'entryEndTime\'),\'%y-%M-%d %H:%m:%s\'}'})" />
                                    </li>
                                    <li>
                                        <label>活动时间：</label>
                                        <input type="text"  class="ui-input ui-form-date" style="width:180px;" readonly="readonly" placeholder=""
											id="activityStartTime" name="activityParam.activityStartTime" maxlength="10"
											onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" />
										至&nbsp;
										<input type="text"  class="ui-input ui-form-date" style="width:180px;" readonly="readonly" placeholder=""
											id="activityEndTime" maxlength="10" name="activityParam.activityEndTime"
											onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'2008-03-08 11:30:00',maxDate:'2100-03-10 20:59:30'})" />
                                    </li>
                                    <li>
                                        <a class="btn width66 btn-mini btn-primary activity_search" >搜索</a>
                                    </li>
                                </ul>
                            </div>
                            <%--搜索结束--%>
                            <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
                                <thead>
                                </thead>
                                <tbody>
                                <tr>
                                    <td align="center" width="50px">活动ID</td>
                                    <td align="center" width="200px">活动名称</td>
                                    <td align="center" width="150px">活动类型</td>
                                    <td align="center" width="150px">活动费用</td>
                                    <td align="center" width="250px">报名起止时间</td>
                                    <td align="center" width="250px">活动起止时间</td>
                                    <td align="center" width="150px">状态</td>
                                    <td align="center" width="">操作</td>
                                </tr>
                                </tbody>
                            </table>
                            <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered">
                                <tbody>
                                <s:iterator value="pagintion.recordList" id="activity_list" var="activityVo">
                                    <tr>
                                        <td align="center" width="50px"><s:property value="activityId"/></td>
                                        <td align="center" width="200px"><s:property value="activityName"/></td>
                                        <td align="center" width="150px">
                                            <s:property value="#request.activityTypeMap[activityType]"/>
                                        </td>
                                        <td align="center" width="150px">
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
                                        <td align="center" width="250px">
                                               	从<s:date name="entryStartTime" format="yyyy-MM-dd HH:mm:ss"/><br>
                                               	到<s:date name="entryEndTime" format="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td align="center" width="250px">
									                 从<s:date name="activityStartTime" format="yyyy-MM-dd HH:mm:ss"/><br>
									                                   到<s:date name="activityEndTime" format="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td align="center" width="150px">
											${activity:activityInfoDynamicStatus(activityVo)}
                                        </td>
                                        <td align="center">
											 <a class="btn width66 btn-mini btn-primary to_getResult" 
											 activity-id="<s:property value="activityId"/>" activity-type="<s:property value="activityType"/>">活动效果</a>
											 
											 <a class="btn width66 btn-mini btn-primary to_view" 
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