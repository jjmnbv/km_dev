<%@ page language="java" import="com.kmzyc.framework.constants.Constants" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<%String cssAndJsPath = ConfigurationUtil.getString("cssAndJsPath"); %>

<!--顶部导航开始-->
<div class="navbar navbar-fixed-top">
    <div class="navbar-top">
        <div class="container-fluid">
            <a class="brand"><img src="<%=cssAndJsPath %>images/common/km_logo.png"></a>
            <div class="nav-collapse collapse j_click_topmenu">
                <div class="user_top">
                    <ul>
                        <li>
                          <span>
                          <%if (!"--".equals((String) session.getAttribute(Constants.SESSION_SUPPLIER_SHOPNAME))) { %>
                            <%=session.getAttribute(Constants.SESSION_SUPPLIER_SHOPNAME)%>
                          <%} else { %>
                            <br/>
                          <%} %>
                          </span>
                            <p><a id="j_top_openShopUrl" href="javascript:void(0);"
                                  data-href="<%=session.getAttribute(Constants.SESSION_SUPPLIER_SHOPURL) %>"
                                  title="我的店铺"><i class="icon-filter icon-white"></i> 店铺</a></p>
                            <p><a target="_blank" href="http://www.kmb2b.com/member/goChangePassword.action"
                                  title="修改密码"><i class="icon-wrench icon-white"></i> 修改密码</a></p>
                            <p><a href="javascript:void(0);" id="j_top_loginout" title="安全退出"><i
                                    class="icon-off icon-white"></i> 退出</a></p>
                        </li>
                    </ul>
                </div>
                <ul class="nav">
                    <li id="i_">
                        <a href="/supplier/loginSuccess.action">首页</a>
                    </li>
                    <li id="p_" class="dropdown">
                        <a id="j_top_openP" href="javascript:void(0);" data-id="p_2"
                           data-href="/product/showList.action?auditStatus=3" data-toggle="dropdown"
                           class="dropdown-toggle">商品</a>
                        <ul class="dropdown-menu" id="menu1">
                            <li id="p_1"><a href="/productDraft/toProductDraftCategory.action">添加新商品</a></li>
                            <li class="divider"></li>
                            <li id="p_2"><a href="/product/showList.action?auditStatus=3">出售中的商品</a></li>
                            <li class="divider"></li>
                            <li id="p_3"><a href="/productDraft/showProductDraftList.action?auditStatus=2">待售中的商品</a>
                            </li>
                            <li class="divider"></li>
                            <li id="p_4"><a href="/product/showStockList.action">库存管理</a></li>
                            <li class="divider"></li>
                            <li id="p_5"><a href="/prodBrandDraft/showProdBrandDraftList.action">品牌管理</a></li>
                        </ul>
                    </li>
                    <li id="o_" class="dropdown">
                        <a id="j_top_openO" href="javascript:void(0);" data-id="o_1"
                           data-href="/order/showOrderListByStatus.action" class="dropdown-toggle"
                           data-toggle="dropdown">订单 </a>
                        <ul class="dropdown-menu" id="menu2">
                            <li id="o_1"><a tabindex="-1" href="/order/showOrderListByStatus.action">所有订单</a></li>
                            <li class="divider"></li>
                            <li id="o_2"><a tabindex="-1" href="/supplier/findAllReturnNotes.action">退换货订单</a></li>
                        </ul>
                    </li>
                    <li id="po_" class="dropdown">
                        <a id="j_top_openPo" href="javascript:void(0);" data-id="po_2"
                           data-href="/promotion/queryPromotionListAudit.action?status=2" class="dropdown-toggle"
                           data-toggle="dropdown">促销 </a>
                        <ul class="dropdown-menu" id="menu3">
                            <li id="po_1">
                                <a tabindex="-1" href="/promotion/toAddPromotion.action">发布促销活动</a>
                            </li>
                            <li class="divider"></li>
                            <li id="po_2">
                                <a tabindex="-1" href="/promotion/queryPromotionListAudit.action?status=2">已审核管理</a>
                            </li>
                            <li class="divider"></li>
                            <li id="po_3">
                                <a tabindex="-1" href="/promotion/queryPromotionList.action?status=1">未审核管理</a></li>
                        </ul>
                    </li>
                    <li id="s_" class="dropdown">
                        <a id="j_top_openS" href="javascript:void(0);" data-id="s_1"
                           data-href="/supplier/toAddOrUpdateShopMain.action" class="dropdown-toggle"
                           data-toggle="dropdown">店铺 </a>
                        <ul class="dropdown-menu" id="menu4">
                            <li id="s_1"><a tabindex="-1" href="/supplier/toAddOrUpdateShopMain.action">店铺设置</a></li>
                            <li class="divider"></li>
                            <li id="s_2"><a tabindex="-1" href="/supplier/templetChoice.action">店铺装修</a></li>
                            <li class="divider"></li>
                            <li id="s_3"><a tabindex="-1" href="/supplier/showShopCategoryList.action">店铺分类</a></li>
                            <li class="divider"></li>
                            <li id="s_4"><a tabindex="-1" href="/supplier/toShopMainDescribe.action">商家介绍</a></li>
                            <li class="divider"></li>
                            <li id="s_5"><a tabindex="-1" href="/supplier/showSupplierInfo.action">商家信息</a></li>
                            <li class="divider"></li>
                            <li id="s_6"><a tabindex="-1" href="/supplier/toSupplierFare.action">运费管理</a></li>
                        </ul>
                    </li>
                    <li id="se_" class="dropdown">
                        <a id="j_top_openSe" href="javascript:void(0);" data-id="se_1"
                           data-href="/settlement/queryRechargeDetail.action" class="dropdown-toggle"
                           data-toggle="dropdown">结算 </a>
                        <ul class="dropdown-menu" id="menu5">
                            <li id="se_1"><a tabindex="-1" href="/settlement/queryRechargeDetail.action">账户明细</a></li>
                            <li class="divider"></li>
                            <li id="se_2"><a tabindex="-1" href="/settlement/settlementList.action">结算管理</a></li>
                        </ul>
                    </li>
                    <%--<li id="ac_" class="dropdown">
                        <a id="j_top_openAc" href="javascript:void(0);" data-id="ac_1"
                           data-href="/activity/getActivityList.action?activitySupplierType=1" class="dropdown-toggle"
                           data-toggle="dropdown">活动</a>
                        <ul class="dropdown-menu" id="menu6">
                            <li id="ac_1">
                                <a tabindex="-1" href="/activity/getActivityList.action?activitySupplierType=1">活动管理</a></li>
                            <li class="divider"></li>
                            <li id="ac_2">
                                <a tabindex="-1" href="/supplierActivityResultAction/findMyPromotionEffectList.action">活动推广效果</a></li>
                        </ul>
                    </li>--%>
                    <%--<li id="c_"><a href="/supplier/queryShopBrowseList.action"><i class="icon-random"></i> 统计</a></li>--%>
                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
    </div>
</div>
<!--顶部导航结束-->
<form action="/supplier/loginout.action" id="loginoutfrm" name="loginoutfrm" method="post" style="display: none;"></form>
