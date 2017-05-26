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
    <input type="hidden" value="<s:property value="rtnMessage" />" id="rtnMessage"/>
    <div class="row-fluid">
        <!-- block -->
        <div class="block_01">
            <div class="navbar-inner">
                <ul class="breadcrumb">
                    <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
                    <li>商品 <span class="divider">/</span></li>
                    <li>品牌管理 <span class="divider">/</span></li>
                    <li>品牌详情</li>
                </ul>
            </div>
            <div class="block-content collapse in">
                <!--内容菜单开始-->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#home" data-toggle="tab">品牌信息</a></li>
                </ul>
                <!--内容菜单结束-->
                <form class="form-horizontal" name="frm" method="post" id="frm">
                    <s:hidden name="status"></s:hidden>
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>品牌名称： </label>
                            <div class="controls">
                                <span><s:property value="prodBrandDraft.brandName"/></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>国籍： </label>
                            <div class="controls">
                                <s:property value="prodBrandDraft.nation"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">英文名称： </label>
                            <div class="controls">
                                <s:property value="prodBrandDraft.engName"/>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"><span class="required">*</span>中文拼音： </label>
                            <div class="controls">
                                <s:property value="prodBrandDraft.chnSpell"/>
                            </div>
                        </div>
                        <%--<div class="control-group">
                            <label class="control-label"><span class="required">*</span>LOGO图片： </label>
                            <div class="controls">
                                <!--上传资质文件开始 -->
                                <input type="hidden" name="preLogoPath"
                                       value="<s:property value="prodBrandDraft.logoPath"/>" />
                                <div class="upfile">
                                    <ul>
                                        <li>
                                            <div class="uploader white">
                                                <input type="text" class="filename logoFileName" name="logoFileName"
                                                       readonly/>
                                                <input type="button" name="file" class="button" value="选择文件..."/>
                                                <input type="file" size="30" class="logoFile" name="logoFile"
                                                       multiple accept="image/jpeg,image/png"/>
                                            </div>
                                        </li>
                                        <li>
                                            <span>请选择118*46px，jpg或png格式的图片</span>
                                        </li>
                                    </ul>
                                </div>
                                <!--上传资质文件结束 -->
                            </div>
                        </div>--%>
                        <div class="control-group">
                            <label class="control-label">已上传图片： </label>
                            <s:if test="prodBrandDraft.logoPath != null">
                                <div class="controls">
                                    <img alt="已上传品牌图片" width="118" height="46"
                                         src='<s:property value="imagePath"/><s:property value="prodBrandDraft.logoPath"/>'/>
                                </div>
                            </s:if>
                        </div>
                        <div class="form-actions">
                            <button class="btn btn-large btnBack">返回</button>
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