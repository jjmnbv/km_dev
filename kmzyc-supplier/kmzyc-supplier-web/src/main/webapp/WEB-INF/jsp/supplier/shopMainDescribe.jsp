<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="商家介绍"></jsp:param>
    </jsp:include>
    <title>商家介绍</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid" id="content" data-url="${basePath}">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_shop.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>店铺 <span class="divider">/</span></li>
                            <li>商家介绍</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <!--内容菜单开始-->
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">商家介绍</a></li>
                        </ul>
                        <!--内容菜单结束-->
                        <!--提示开始-->
                        <div class="alert">
                            <strong>提示：</strong>
                            商家介绍是关于商家亮点的详细描述，填写完成后，并在【店铺装修】中点击“生成店铺首页”，商家介绍的内容将会出现在店铺分页。
                        </div>
                        <s:form action="updateShopMainDescribe" namespace="ajaxJson" method="post"
                                enctype="multipart/form-data" name="describeFrm" id="describeFrm">
                            <s:hidden name="shopMain.shopId"></s:hidden>
                            <textarea id="editor_id" name="shopMain.describe" style="height:420px;width:99%;resize:none;">
					            <s:property value="shopMain.describe"/></textarea>
                            <br/>
                            <div class="form-actions">
                                <button id="frmintroButton"
                                        class="btn btn-success btn-large j_submit_shopMainDescribe">
                                    <i class="icon-arrow-up icon-white"></i> 保存
                                </button>
                            </div>

                            <!-- lazy -->
                            <div class="editor_change_describe"
                                 style="visibility:hidden;display: none;"></div>
                            <div style="visibility:hidden">
                            <textarea id="editor_lazy_describe" name="shopMain.describe_lazy">
                                 <s:property value="shopMain.describe_lazy"/>
                            </textarea>
                            </div>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>