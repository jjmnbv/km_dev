<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="aa" uri="http://ajaxanywhere.sourceforge.net/" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="选择类目"></jsp:param>
    </jsp:include>
    <title>添加商品基本资料</title>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home">
                                </i>
                            <li>商品 <span class="divider">/</span></li>
                            <li>添加新商品</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in"><!--开始-->
                        <div class="com_step">
                            <ul>
                                <li><i class="icon-step01"></i>
                                    <p>STEP.1</p>
                                    <span>选择商品分类</span><i class="icon-stept"></i></li>
                            </ul>
                            <ul>
                                <li class="gray"><i class="icon-step02 icon-white"></i>
                                    <p>STEP.2</p>
                                    <span>填写商品详情</span><i class="icon-stept"></i></li>
                            </ul>
                            <ul>
                                <li class="gray"><i class="icon-step03 icon-white"></i>
                                    <p>STEP.3</p>
                                    <span>上传商品图片</span><i class="icon-stept"></i></li>
                            </ul>
                            <ul>
                                <li class="gray"><i class="icon-step04 icon-white"></i>
                                    <p>STEP.4</p>
                                    <span>添加商品成功</span></li>
                            </ul>
                        </div>
                        <div class="alert">
                            <button class="close" data-dismiss="alert">&times;</button>
                            <strong>提示：</strong> 请选择商品类别
                        </div>
                        <!--三级联动开始-->
                        <s:form action="toProductDraftAdd" method="post" id="frm" name="frm">
                            <div class="threem">
                                <ul>
                                    <li>
                                        <s:select list="#request.categoryList"
                                                  name="product.bCategoryId" id="categoryId1"
                                                  listKey="categoryId" listValue="categoryName"
                                                  data-sourceid="categoryId1" data-targetid="categoryId2" data-subTargetId="categoryId3"
                                                  cssClass="seltitle j_change_bCategory" size="10"></s:select>
                                    </li>
                                    <li>
                                        <s:select list="#request.mCategoryList"
                                                  name="product.mCategoryId" id="categoryId2"
                                                  listKey="categoryId" listValue="categoryName"
                                                  data-sourceid="categoryId2" data-targetid="categoryId3"
                                                  cssClass="seltitle j_change_mCategory" size="10"></s:select>
                                    </li>
                                    <li>
                                        <s:select list="#request.sCategoryList"
                                                  id="categoryId3" name="product.categoryId"
                                                  listKey="categoryId" listValue="categoryName"
                                                  cssClass="seltitle" size="10"></s:select>
                                    </li>
                                </ul>
                            </div>
                        </s:form>

                        <!--三级联动结束-->
                        <div class="form-actions">
                            <a href="javascript:void(0);" id="next"
                               class="btn btn-success btn-large">下一步，编辑商品信息
                                <i class="icon-chevron-right icon-white"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>