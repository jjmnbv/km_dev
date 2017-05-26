<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="店铺设置"></jsp:param>
    </jsp:include>
    <title>店铺设置</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>店铺 <span class="divider">/</span></li>
                            <li>店铺设置</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <!--开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">店铺设置</a></li>
                        </ul>
                        <form class="form-horizontal" action="/ajaxJson/addShopMain.action" method="post" id="frm"
                              name="frm" enctype="multipart/form-data">
                            <%--<s:select list="#request.siteTypeMap" name="shopMainDraft.shopSite" cssStyle="display:none;"></s:select>--%>
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label"><span class="required">*</span>店铺名称：</label>
                                    <div class="controls">
                                        <s:textfield cssClass="span5" data-provide="typeahead"
                                                     name="shopMainDraft.shopName" id="shopName" maxlength="40"/>
                                        <p class="help-block" id="j_shopName_error">店铺名称必须填写</p>
                                        <input type="hidden" name="oldName" value="<s:property value='shopMainDraft.shopName'/>"/>
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">店铺标题： </label>
                                    <div class="controls">
                                        <s:textfield cssClass="span5" data-provide="typeahead"
                                                     name="shopMainDraft.shopTitle" maxlength="150"/>
                                        <p></p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label"><span class="required">*</span>经营品牌：</label>
                                    <div class="controls">
                                        <s:textfield cssClass="span5" data-provide="typeahead"
                                                     name="shopMainDraft.manageBrand" maxlength="150" id="manageBrand"/>
                                        <p class="help-block" id="j_manageBrand_error">经营品牌必须填写，多品牌用逗号隔开</p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label"><span class="required">*</span>在线客服工具： </label>
                                    <div class="controls">
                                        <s:select cssClass="span2 m-wrap" list="#request.shopMainServiceTypeMap"
                                                  name="shopMainDraft.serviceType"></s:select>
                                        <s:textfield cssClass="span2" data-provide="typeahead"
                                                     name="shopMainDraft.serviceQq" maxlength="20" id="serviceQq"/>
                                        <p class="help-block" id="j_serviceQq_error">在线联系方式必须填写</p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label"><span class="required">*</span>店铺负责人姓名：</label>
                                    <div class="controls">
                                        <s:textfield cssClass="span2" data-provide="typeahead"
                                                     name="shopMainDraft.principalName" maxlength="150"
                                                     id="principalName"/>
                                        <p class="help-block" id="j_principalName_error">店铺负责人姓名必须填写</p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label"><span class="required">*</span>店铺负责人手机：</label>
                                    <div class="controls">
                                        <s:textfield cssClass="span2" data-provide="typeahead"
                                                     name="shopMainDraft.linkmanMobile" maxlength="150" id="linkmanMobile"/>
                                        <p class="help-block" id="j_contactInfo_error">
                                        店铺负责人手机必须填写</p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">商家简介：</label>
                                    <div class="controls">
                                        <s:textarea cssClass="input-xlarge textarea span5"
                                                    name="shopMainDraft.introduce" id="introduce" cols="50" rows="4"/>
                                        <p class="help-block" id="j_introduce_error">店铺介绍字数不要超过650字</p>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label ">店铺类型：</label>
                                    <div class="controls">
                                        <s:select cssClass="span2 m-wrap" list="#request.shopMainTypeMap"
                                                  name="shopMainDraft.shopType"></s:select>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">店铺SEO设置：</label>
                                    <div class="controls">
                                        <s:textarea cssClass="input-xlarge textarea span5"
                                                    name="shopMainDraft.shopSeoKey" style="height: 100px" maxlength="300"/>
                                        <p class="help-block">用于店铺搜索引擎的优化，关键字之间请用逗号分隔</p>
                                    </div>
                                </div>
                                <div class="form-actions">
                                    <button class="btn btn-success btn-large j_submit_addShopMain">
                                        <i class="icon-arrow-up icon-white"></i> 提交审核
                                    </button>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
<s:hidden name="reType" id="reType"></s:hidden>
</body>
</html>