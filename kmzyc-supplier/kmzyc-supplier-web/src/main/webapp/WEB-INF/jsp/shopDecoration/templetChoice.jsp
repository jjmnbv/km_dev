<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="店铺装修"></jsp:param>
    </jsp:include>
    <title>店铺装修</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
        <s:if test="shopMain!=null">
            <div class="content">
                <div class="row-fluid">
                    <!-- block -->
                    <div class="block_01">
                        <div class="navbar-inner">
                            <ul class="breadcrumb">
                                <i class="icon-home"></i>
                                <li>店铺 <span class="divider">/</span></li>
                                <li>店铺装修</li>
                            </ul>
                        </div>
                        <div class="block-content collapse in">
                            <!--内容菜单开始-->
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#home" data-toggle="tab">店铺装修</a></li>
                            </ul>
                            <!--内容菜单结束-->
                            <!--提示开始-->
                            <div class="alert alert-block">
                                1、请先选择装修模板，模板样式可点击“预览模板”查看效果。<br>
                                2、点击“装修页面”，根据提示完成装修。<br>
                                3、点击“生成店铺首页”进行提交。
                            </div>
                        </div>
                        <!--提示结束-->
                        <fieldset>
                            <div class="shop_list">
                                <ul>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/default.jpg">
                                            </p>
                                            <div class="wz">
                                                <span>
                                                    <a href="javascript:void(0);" target="_blank" data-type="2"
                                                       data-shopid="<s:property value='shopMain.shopId' />" class="j_view_templet">
                                                        <i class="icon-shop03"></i>简介</a>
                                                </span>
                                                <span>
                                                    <a href="javascript:void(0);" target="_blank" data-type="2" class="j_view_brief"
                                                       data-shopid="<s:property value='shopMain.shopId' />">
                                                        <i class="icon-shop01"></i>预览</a>
                                                </span>
                                                <span>
                                                    <a href="javascript:void(0);" target="_blank" data-type="2" class="j_edit_templet"
                                                       data-shopid="<s:property value='shopMain.shopId' />">
                                                    <i class="icon-shop02"></i>装修</a>
                                                </span>
                                            </div>
                                            <div class="select">
                                                <label class="checkbox-inline">
                                                    <input name="choice" type="radio" value="radio"
                                                           <s:if test="shopMain.templateType==2 || shopMain.templateType==null">checked="checked"</s:if>
                                                           data-shopid="<s:property value='shopMain.shopId' />"
                                                           data-type="2" class="j_choice_templet">
                                                    默认模板</label>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/blue_img.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="3" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="3" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="3" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==3">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="3" class="j_choice_templet">
                                                简易模板</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="7" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="7" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="7" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>

                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==7">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="7" class="j_choice_templet">
                                                药房模板一</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p>
                                                <img src="${staticUrl}${imageBaseUrl}/yaofang_simple.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="8" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="8" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="8" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==8">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="8" class="j_choice_templet">
                                                药房简单模板一</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/foods.jpg"></p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="9" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="9" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="9" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==9">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="9" class="j_choice_templet">
                                                美食模板</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang2.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="10" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="10" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="10" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==10">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="10" class="j_choice_templet">
                                                药房模板二</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang3.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="11" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="11" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="11" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==11">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="11" class="j_choice_templet">
                                                药房模板三</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang4.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="12" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="12" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="12" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==12">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="12" class="j_choice_templet">
                                                轻变个性模板</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p>
                                                <img src="${staticUrl}${imageBaseUrl}/yaofang4_simple.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="13" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="13" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="13" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==13">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="13" class="j_choice_templet">
                                                轻变个性模板-简板</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang5.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="14" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="14" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="14" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==14">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="14" class="j_choice_templet">
                                                多用精装模板</label>
                                        </div>
                                    </li>
                                    <li>
                                        <div class="img_wz">
                                            <p><img src="${staticUrl}${imageBaseUrl}/yaofang6.jpg">
                                            </p>
                                            <div class="wz">
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="15" class="j_view_templet">
                                                    <i class="icon-shop03"></i>简介</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="15" class="j_view_brief">
                                                    <i class="icon-shop01"></i>预览</a></span>
                                                <span><a href="javascript:void(0);" target="_blank"
                                                         data-shopid="<s:property value='shopMain.shopId' />"
                                                         data-type="15" class="j_edit_templet">
                                                    <i class="icon-shop02"></i>装修</a></span>
                                            </div>
                                        </div>
                                        <div class="select">
                                            <label class="checkbox-inline">
                                                <input name="choice" type="radio" value="radio"
                                                       <s:if test="shopMain.templateType==15">checked="checked"</s:if>
                                                       data-shopid="<s:property value='shopMain.shopId' />"
                                                       data-type="15" class="j_choice_templet">
                                                懒人全屏模板</label>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="form-actions">
                                <button data-type="<s:if test='shopMain.templateType!=null'><s:property value='shopMain.templateType'/></s:if><s:else>2</s:else>"
                                        data-shopid="<s:property value='shopMain.shopId' />"
                                        class="btn btn-success btn-large j_publish_templet">
                                    <i class="icon-arrow-right  icon-white"></i> 生成店铺首页
                                </button>
                            </div>
                        </fieldset>
                        <!--结束-->
                    </div>
                </div>
            </div>
        </s:if>
        <s:else>
            <div class="content">
                <div class="row-fluid">
                    <!-- block -->
                    <div class="block_01 shopheight">
                        <div class="navbar-inner">
                            <ul class="breadcrumb">
                                <i class="icon-home"></i>
                                <li>店铺 <span class="divider">/</span></li>
                                <li>店铺装修</li>
                            </ul>
                        </div>
                        <div class="block-content prompt">
                            亲爱的商家，您好！您还没有申请店铺，请先去提交申请哦。
                        </div>
                        <div class="btnnobg">
                            <a data-id="s_1" data-href="/supplier/toAddOrUpdateShopMain.action"
                               href="javascript:void(0);" class="btn btn-success btn-large j_shop_apply">立即申请店铺</a>
                        </div>
                    </div>
                </div>
            </div>
        </s:else>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>