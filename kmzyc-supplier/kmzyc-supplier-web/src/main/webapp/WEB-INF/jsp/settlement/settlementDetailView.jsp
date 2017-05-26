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
        <jsp:param name="titlePrefix" value="结算管理"></jsp:param>
    </jsp:include>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>结算管理</title>
</head>
<body>
<!-- 顶部导航开始 -->
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<!-- 顶部导航结束 -->
<div class="container-fluid">
    <div class="row-fluid"><!--左侧菜单开始-->
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_settlement.jsp"></jsp:include>
        <!--左侧菜单结束-->
        <form id="frm" name="frm" action="hurlProductListDetail.action" method="post"></form>
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
                            <li>结算 <span class="divider">/</span></li>
                            <li>结算管理</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <div class="mod mod-stage-forward">
                            <div class="unit-stage unit-stage-orange">
                                <ul class="list five-stage">
                                    <s:bean name="org.apache.struts2.util.Counter">
                                        <s:param name="first" value="1"/>
                                        <s:param name="last" value="6"/>
                                        <s:iterator status="status">
                                            <!-- 生成结算单 -->
                                            <s:if test="(#status.count==1)&&(sellerSettlement.settlementStatus>=1)">
                                                <li class="stage-node stage-first-current">
                                                    <!-- stage-first-current绿色箭头往右 -->
                                                    <div class="stage-label">生成结算单</div>
                                                    <div class="stage-num">1</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.settlementCreateTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <s:if test="(#status.count==1)&&(sellerSettlement.settlementStatus<1)">
                                                <li class="stage-node stage-then">
                                                    <!-- stage-then灰色字体 -->
                                                    <div class="stage-label">生成结算单</div>
                                                    <div class="stage-num">1</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.settlementCreateTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <!-- 生成结算单 end -->

                                            <!-- 商家确认及左侧进度条 -->
                                            <s:if test="(#status.count==2)&&(sellerSettlement.settlementStatus>=2)">
                                                <li class="stage-bar stage-bar-pass">
                                                    <!-- stage-bar-pass绿色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-first-current">
                                                    <!-- stage-first-current绿色箭头往右 -->
                                                    <div class="stage-label">商家确认</div>
                                                    <div class="stage-num">2</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.serllerConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <s:if test="(#status.count==2)&&(sellerSettlement.settlementStatus<2)">
                                                <li class="stage-bar stage-bar-then">
                                                    <!-- stage-bar-then灰色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-then">
                                                    <!-- stage-then灰色字体 -->
                                                    <div class="stage-label">商家确认</div>
                                                    <div class="stage-num">2</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.serllerConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <!-- 商家确认及左侧进度条 end -->

                                            <!-- 运营确认及左侧进度条 -->
                                            <s:if test="(#status.count==3)&&(sellerSettlement.settlementStatus>=3&&sellerSettlement.settlementStatus!=5)">
                                                <li class="stage-bar stage-bar-pass">
                                                    <!-- stage-bar-pass绿色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-first-current">
                                                    <!-- stage-first-current绿色箭头往右 -->
                                                    <div class="stage-label">运营确认</div>
                                                    <div class="stage-num">3</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.operateConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <s:if test="(#status.count==3)&&(sellerSettlement.settlementStatus<3||sellerSettlement.settlementStatus==5)">
                                                <li class="stage-bar stage-bar-then">
                                                    <!-- stage-bar-then灰色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-then">
                                                    <!-- stage-then灰色字体  -->
                                                    <div class="stage-label">运营确认</div>
                                                    <div class="stage-num">3</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.operateConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <!-- 运营确认及左侧进度条 end -->

                                            <!-- 财务审批及左侧进度条 -->
                                            <s:if test="(#status.count==4)&&(sellerSettlement.settlementStatus>=4&&sellerSettlement.settlementStatus!=5)">
                                                <li class="stage-bar stage-bar-pass">
                                                    <!-- stage-bar-pass绿色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-first-current">
                                                    <!-- stage-first-current绿色箭头往右 -->
                                                    <div class="stage-label">财务审批</div>
                                                    <div class="stage-num">4</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.financialConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <s:if test="(#status.count==4)&&(sellerSettlement.settlementStatus<4||sellerSettlement.settlementStatus==5)">
                                                <li class="stage-bar stage-bar-then">
                                                    <!-- stage-bar-then灰色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-then">
                                                    <!-- stage-then灰色字体  -->
                                                    <div class="stage-label">财务审批</div>
                                                    <div class="stage-num">4</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.financialConfirmTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <!-- 财务审批及左侧进度条 end -->

                                            <!-- 结出到余额 -->
                                            <s:if test="(#status.count==6)&&(sellerSettlement.settlementStatus>=6)">
                                                <li class="stage-bar stage-bar-pass">
                                                    <!-- stage-bar-pass绿色 -->
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-current">
                                                    <!-- stage-current绿色箭头往右 -->
                                                    <div class="stage-label">结出到余额</div>
                                                    <div class="stage-num">√</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.settlementFinishTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <s:if test="(#status.count==6)&&(sellerSettlement.settlementStatus<6)">
                                                <li class="stage-bar stage-bar-then">
                                                    <div class="stage-far"></div>
                                                </li>
                                                <li class="stage-node stage-last-then">
                                                    <div class="stage-label">结出到余额</div>
                                                    <div class="stage-num">√</div>
                                                    <div class="stage-data">
                                                        <s:date name="sellerSettlement.settlementFinishTime" format="yyyy-MM-dd HH:mm:ss"/>
                                                    </div>
                                                </li>
                                            </s:if>
                                            <!-- 结出到余额 end -->
                                        </s:iterator>
                                    </s:bean>
                                </ul>
                            </div>
                        </div>
                        <table border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
                            <thead>
                            <tr class="tablesbg">
                                <th colspan="7" class="shoptL">入驻商家结算清单：</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="width140 shoptR">结算单号：</td>
                                <td class="shoptL"><s:property value="sellerSettlement.settlementNo"/></td>
                                <td class="width140 shoptR">结算账期：</td>
                                <td class="shoptL"><s:property value="sellerSettlement.settlementPeriod"/>(<s:property value="sellerSettlement.settlementPeriodExp"/>)
                                </td>
                            </tr>
                            <tr>
                                <td class="width140 shoptR">供应商：</td>
                                <td class="shoptL"><s:property value="sellerSettlement.sellerName"/></td>
                                <td class="width140 shoptR">店铺名称：</td>
                                <td class="shoptL"><s:property value="sellerSettlement.shopName"/></td>
                            </tr>
                            <tr>
                                <td class="width140 shoptR">结算币种：</td>
                                <td class="shoptL">人民币</td>
                                <td class="width140 shoptR">结算单状态：</td>
                                <td class="shoptL">
                                    <s:if test="sellerSettlement.settlementStatus==1">未确认</s:if>
                                    <s:elseif test="sellerSettlement.settlementStatus==2">商家已确认</s:elseif>
                                    <s:elseif test="sellerSettlement.settlementStatus==3">运营已确认</s:elseif>
                                    <s:elseif test="sellerSettlement.settlementStatus==4">财务审核通过</s:elseif>
                                    <s:elseif test="sellerSettlement.settlementStatus==5">财务审核拒绝</s:elseif>
                                    <s:elseif test="sellerSettlement.settlementStatus==6">已结出</s:elseif>
                                </td>
                            </tr>
                            <tr>
                                <td class="width140 shoptR">结算项目：</td>
                                <td colspan="3" class="shoptL"><!--结算项目开始-->
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table table-bordered">
                                        <tbody>
                                        <tr class="tablesbg">
                                            <td colspan="2" class="width200">期内货款汇总</td>
                                            <td colspan="2" class="width200">运费汇总</td>
                                            <td colspan="2" class="width150">期内佣金汇总</td>
                                            <td colspan="2" class="width200">期内推广服务费汇总</td>
                                            <td class="width200">差异调整</td>
                                            <td class="width150">本期应结金额</td>
                                        </tr>
                                        <tr>
                                            <td>妥投实收</td>
                                            <td>退款金额</td>
                                            <td>妥投运费</td>
                                            <td>退货返运费</td>
                                            <td>妥投佣金</td>
                                            <td>退款佣金</td>
                                            <td>妥投推广服务费</td>
                                            <td>退款返推广服务费</td>
                                            <td>&nbsp;明细</td>
                                            <td class="shoptL"></td>
                                        </tr>
                                        <tr>
                                            <td class="width200">+<fmt:formatNumber value="${sellerSettlement.receiveSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">-<fmt:formatNumber value="${sellerSettlement.refundSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200"><fmt:formatNumber value="${sellerSettlement.fareSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">-<fmt:formatNumber value="${sellerSettlement.returnFareSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">-<fmt:formatNumber value="${sellerSettlement.commissionSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">+<fmt:formatNumber value="${sellerSettlement.refundComSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">-<fmt:formatNumber value="${sellerSettlement.addpvsum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200">+<fmt:formatNumber value="${sellerSettlement.returnpvsum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200"><fmt:formatNumber value="${sellerSettlement.diffAdjSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="shoptL"></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="width200"><fmt:formatNumber value="${sellerSettlement.receiveSum-sellerSettlement.refundSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td colspan="2" class="width200"><fmt:formatNumber value="${sellerSettlement.fareSum-sellerSettlement.returnFareSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td colspan="2" class="width200"><fmt:formatNumber value="${sellerSettlement.refundComSum-sellerSettlement.commissionSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td colspan="2" class="width200"><fmt:formatNumber value="${sellerSettlement.returnpvsum-sellerSettlement.addpvsum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="width200"><fmt:formatNumber value="${sellerSettlement.diffAdjSum}" type="currency" pattern="#,##0.00"/></td>
                                            <td class="shoptL"><fmt:formatNumber value="${sellerSettlement.currSettleAccounts+sellerSettlement.diffAdjSum}" type="currency" pattern="#,##0.00"/></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <s:if test="sellerSettlement.settlementStatus!=1">
                                        <p class="textcolor">
                                            <s:if test="sellerSettlement.sellerConfirmation != null">
                                                商家确认意见:<s:property value="sellerSettlement.sellerConfirmation"/>
                                            </s:if>
                                        </p>
                                    </s:if>
                                    <s:else>
                                        <p class="textcolor">请认真核对结算数据并确认，如有异议，请提交商家确认意见。</p>
                                    </s:else>
                                    <div class="controls">
                                        <a href="JavaScript:void(0);" class="btn btn-success j_view_settlementSimple"
                                           data-settlementNo="<s:property value="sellerSettlement.settlementNo"/>">
                                            <i class=" icon-eye-open icon-white "></i> 查看明细
                                        </a>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <a href="javascript:void(0);" class="btn btn-success"
                                           onclick="javascript:exportSettleOrder_Export('<s:property value="sellerSettlement.settlementNo"/>');">导出</a>
                                    </div>
                                    <div class="controls textR">康美中药材数据信息服务有限公司<br>
                                        <s:date name="sellerSettlement.settlementCreateTime" format="yyyy-MM-dd HH:mm:ss"/></div>
                                    <!--结算项目结束-->
                                </td>
                            </tr>
                            <tr>
                                <s:if test="sellerSettlement.settlementStatus==1">
                                    <s:form action="merchantConfirmation.action" method="POST" namespace="/ajaxJson" id="merchantConfirmation">
                                        <input type="hidden" name="settlementNo" value="<s:property value="sellerSettlement.settlementNo"/>"/>
                                        <input type="hidden" name="sellerId" value="<s:property value="sellerSettlement.sellerId"/>"/>
                                        <td class="width200 shoptR">商家确认意见：</td>
                                        <td colspan="3" class="shoptL">
                                            <div class="alert alert-info">
                                                如对结算单信息有任何异议，请在下方确认意见中填写，并提交。运营人员将和您联系确认后进行调整。如无误，请直接点击提交按钮，进入下一个结算环节。
                                            </div>
                                            <div class="controls">
							                    <textarea name="sellerConfirmation" id="sellerConfirmation" maxlength="200"
                                                          class="input-xlarge textarea span6" placeholder="" style="height: 100px"></textarea>
                                            </div>
                                            <div class="controls  j_merchantConfirmation">
                                                <a href="javascript:void(0);" class="btn btn-success">
                                                    <i class=" icon-arrow-up icon-white"></i> 提交
                                                </a>
                                            </div>
                                        </td>
                                    </s:form>
                                </s:if>
                            </tr>
                            </tbody>
                        </table>
                        <div id="question" style="display:none"></div>
                    </div>
                </div>
            </div>
            <hr>
        </div>
        <!-- 底部 -->
        <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
        <!-- 底部  end-->
    </div>
</div>
</body>
</html>