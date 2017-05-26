<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="店铺浏览量列表"></jsp:param>
    </jsp:include>
    <title>店铺浏览量列表</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<!-- 左侧内容区域开始 begin -->
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_charts.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <!-- 面包屑begin -->
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>统计 <span class="divider">/</span></li>
                            <li>店铺统计</li>
                        </ul>
                    </div>

                    <!-- 面包屑end -->
                    <s:if test="'noShop'.equals(#request.tips)">
                        <br/>
                        <p>抱歉,您暂时没有www.kmb2b.com站点的店铺!</p>
                    </s:if>
                    <s:elseif test="'noPublish'.equals(#request.tips)">
                        <br/>
                        <p>抱歉,您的店铺暂时还没有发布,没有任何浏览量,请先发布再启用店铺浏览量!</p>
                    </s:elseif>
                    <s:else>
                        <div class="block-content collapse in"><!--开始-->
                            <div class="alert">
                                <button class="close" data-dismiss="alert">&times;</button>
                                <strong>危险：</strong> 统计图展示了您添加的店铺地址在搜索时间段内的访问量走势情况。
                            </div>
                            <!--搜索开始-->
                            <!-- 隐藏域 -->
                            <input type="hidden" id="dataList" value='<s:property value="#request.groupData"/>'/>
                            <input type="hidden" id="dataSize" value='<s:property value="#request.groupDataSize"/>'/>
                            <s:if test="#request.isOpenBrowseStatus">
                                <div class="controls charttop">
                                    <button class="btn btn-primary j_openBrowseStatus">
                                        <i class="icon-retweet icon-white"></i>启用浏览量
                                    </button>
                                </div>
                            </s:if>

                            <s:form action="queryShopBrowseList" method="post" id="frm" name="frm" namespace="supplier">
                                <s:hidden name="shopBrowseInfo.shopId" id="shopId"/>
                                <s:hidden name="page" id="page"/>

                                <div class="com_topform">
                                    <ul>
                                        <li><label>标题：</label>
                                            <s:textfield name="shopBrowseInfo.titleForQuery" cssStyle="width:200px;"/>
                                        </li>
                                        <li><label>URL地址：</label>
                                            <s:textfield name="shopBrowseInfo.urlForQuery" cssStyle="width:300px;"/>
                                        </li>
                                        <li><label>开始时间：</label>
                                            <input type="text" maxlength="10" readonly="readonly" name="startDate" id="startDate"
                                                   value="<s:property value='startDate'/>"
                                                   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d\'}'})"/>
                                        </li>
                                        <li><label>结束时间：</label>
                                            <input type="text" maxlength="10" readonly="readonly" name="endDate" id="endDate"
                                                   value="<s:property value='endDate'/>"
                                                   onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startDate\')}'})"/>
                                        </li>
                                        <li><label>时间范围：</label>
                                            <select id="timeType" name="shopBrowseInfo.timeTypeQuery">
                                                <option value="0" <s:if test="shopBrowseInfo.timeTypeQuery==0">selected="selected"</s:if>>所有</option>
                                                <option value="1" <s:if test="shopBrowseInfo.timeTypeQuery==1">selected='selected'</s:if>>当天</option>
                                                <option value="2" <s:if test="shopBrowseInfo.timeTypeQuery==2">selected='selected'</s:if>>最近七天</option>
                                                <option value="3" <s:if test="shopBrowseInfo.timeTypeQuery==3">selected='selected'</s:if>>当月</option>
                                                <option value="4" <s:if test="shopBrowseInfo.timeTypeQuery==4">selected='selected'</s:if>>上月</option>
                                            </select>
                                        <li>
                                        <li>
                                            <s:if test="#request.isOpenBrowseStatus">
                                                <div id="queryBtn" style="display:none;">
                                                    <button class="btn btn-primary j_browseCondition_search">
                                                        <i class="icon-search icon-white"></i> 搜索
                                                    </button>
                                                </div>
                                            </s:if>
                                            <s:else>
                                                <button class="btn btn-primary j_browseCondition_search">
                                                    <i class="icon-search icon-white"></i> 搜索
                                                </button>
                                            </s:else>
                                        </li>
                                    </ul>
                                </div>
                                <div class="ctitel">
                                    <ul>
                                        <li>网站访问量统计<span>总浏览量：<font>
                                            <s:if test="#request.totalCount!=null"><s:property value="#request.totalCount"/></s:if>
                                            <s:else>0</s:else>
                                            </font></span>
                                        </li>
                                    </ul>
                                </div>
                                <div id="chartsContainer"></div>
                                <table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered charttop">
                                    <tbody>
                                    <tr class="tablesbg">
                                        <td>URL/链接</td>
                                        <td class="width400">标题</td>
                                        <td class="width150">浏览量</td>
                                    </tr>
                                    <s:iterator value="pagintion.recordList">
                                        <tr>
                                            <td class="textL"><s:property value="url"/></td>
                                            <td class="width400"><s:property value="title"/></td>
                                            <td class="width150"><s:property value="count"/></td>
                                        </tr>
                                    </s:iterator>
                                    </tbody>
                                </table>
                                <div class="fn-clear fn-mt10">
                                    <!-- 分页组件 -->
                                    <tiles:insertDefinition name="paginationBottom"/>
                                </div>
                            </s:form>
                        </div>
                    </div>
                </div>
            </s:else>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>