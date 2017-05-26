<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="品牌信息"></jsp:param>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
<div class="content" id="content" data-url="${basePath}">
    <div class="row-fluid">
        <!-- block -->
        <div class="block_01">
            <div class="navbar-inner">
                <ul class="breadcrumb">
                    <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
                    <li>商品 <span class="divider">/</span></li>
                    <li>品牌管理 <span class="divider">/</span></li>
                    <s:if test="prodBrandDraft == null">
                        <li>品牌申请</li>
                    </s:if>
                    <s:else>
                        <li>品牌编辑</li>
                    </s:else>
                </ul>
            </div>
            <div class="block-content collapse in">
                <!--内容菜单开始-->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#home" data-toggle="tab">品牌信息</a></li>
                </ul>
                <!--内容菜单结束-->
                <form class="form-horizontal" action="/prodBrandDraft/saveOrUpdateProdBrandDraft.action"
                      name="frm" method="post" enctype="multipart/form-data" id="frm">
                    <s:hidden name="prodBrandDraft.brandId" id="brandId"></s:hidden>
                    <s:hidden name="status"></s:hidden>
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>品牌名称：</label>
                            <div class="controls">
                                <s:textfield name="prodBrandDraft.brandName" id="brandName" cssClass="span5"
                                             maxlength="20" data-provide="typeahead" data-items="4"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>国籍： </label>
                            <div class="controls">
                                <s:select name="prodBrandDraft.nation" cssClass="span2 m-wrap"
                                          list="#{'国内':'国内', '国外':'国外'}"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">英文名称： </label>
                            <div class="controls">
                                <s:textfield name="prodBrandDraft.engName" id="engName" cssClass="span5"
                                             maxlength="60" data-provide="typeahead" data-items="4"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>中文拼音： </label>
                            <div class="controls">
                                <s:textfield name="prodBrandDraft.chnSpell" id="chnSpell" cssClass="span5"
                                             maxlength="60" data-provide="typeahead" data-items="4"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>LOGO图片： </label>
                            <div class="controls">
                                <input type="hidden" name="preLogoPath"
                                       value="<s:property value="prodBrandDraft.logoPath"/>"/>
                                <div class="upfile">
                                    <ul>
                                        <li>
                                            <div class="uploader white">
                                                <input type="text" class="filename logoFileName" name="logoFileName"
                                                       readonly/>
                                                <input type="button" name="file" class="button" value="选择文件..."/>
                                                <input type="file" size="30" class="logoFile" name="logoFile"
                                                       id="logoFileButton"
                                                       multiple accept="image/jpeg,image/png,image/gif"/>
                                            </div>
                                        </li>
                                        <li style="width:auto;">
                                            <span>请选择118*46px，可上传jpg、png、jpeg、gif格式的图片</span>
                                        </li>
                                    </ul>
                                </div>
                                <!--上传资质文件结束 -->
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">已上传图片： </label>
                            <div class="controls logoImgDiv">
                                <s:if test="prodBrandDraft.logoPath != null">
                                    <img alt="已上传品牌图片" id="logoFileImg"
                                         src='<s:property value="imagePath"/><s:property value="prodBrandDraft.logoPath"/>'/>
                                </s:if>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button class="btn btn-large btnBack">返回</button>
                            <button class="btn btn-success btn-large btn_save">
                                <i class="icon-arrow-up icon-white"></i>
                                <s:if test="prodBrandDraft == null">
                                    保存并提交审核
                                </s:if>
                                <s:else>
                                    修改并提交审核
                                </s:else>
                            </button>
                        </div>
                    </fieldset>
                </form>

                <s:form action="/prodBrandDraft/showProdBrandDraftList.action"
                        method="POST" id="backFrm" name='backFrm'>
                    <s:hidden name="status"></s:hidden>
                </s:form>
                <%--结束--%>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>