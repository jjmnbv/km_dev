<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>产品图片维护</title>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="添加图片"></jsp:param>
    </jsp:include>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">

        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_product.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>商品 <span class="divider">/</span></li>
                            <li>添加新商品</li>
                        </ul>
                    </div>
                    <div class="block-content collapse in">
                        <!--开始-->
                        <div class="com_step">
                            <ul>
                                <li class="gray"><i class="icon-step01 icon-white"></i>
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
                                <li><i class="icon-step04"></i>
                                    <p>STEP.4</p>
                                    <span>添加商品成功</span></li>
                            </ul>
                        </div>
                        <!--添加商品图片开始-->
                        <s:if test="auditStatus == 1">
                            <div class="alert alert-block" style=" clear:both;"><br/>
                                <h4 class="alert-heading"><i class="icon-ok-sign"></i>
                                    添加新商品成功！待审核通过后，即可完成商品上架！</h4>
                                <br/>
                                <br/>
                                <button class="btn btn-primary btn-large" data-id="p_1" id="btnAddProduct"><i class="icon-tags icon-white"></i>继续添加新品
                                </button>
                                <button class="btn btn-large btn-productDraftList" data-id="p_3"
                                        data-href="/productDraft/showProductDraftList.action?auditStatus=1">
                                    <i class="icon-search"></i> 去商品列表查看
                                </button>
                                <br/>
                                <br/>
                                <hr/>
                                <h5 class="alert-heading">提示：</h5>
                                1. 审核中的商品不能进行修改，撤销审核或待审核完成后，才能进行编辑。<br>
                                2. 审核时间大概为1~3个工作日，紧要情况请联系客服人员进行沟通。
                            </div>
                        </s:if>
                        <s:if test="auditStatus == 0">
                            <div class="alert alert-block" style=" clear:both;"><br/>
                                <h4 class="alert-heading"><i class="icon-folder-close"></i>
                                    商品已保存到待售商品中！如需编辑商品或提交审核，请到未审核列表进行操作！</h4>
                                <br/>
                                <br/>
                                <button class="btn btn-primary btn-large" data-id="p_1"
                                        id="btnAddProduct"><i class="icon-tags icon-white"></i>继续添加新品
                                </button>
                                <button class="btn btn-danger btn-large btn-productDraftList"
                                        data-id="p_3"
                                        data-href="/productDraft/showProductDraftList.action?auditStatus=0">
                                    <i class="icon-arrow-up icon-white"></i> 去提交审核
                                </button>
                                <br/>
                                <br/>
                                <hr/>
                                <h5 class="alert-heading">你还可以：</h5>
                                <a href="javascript:;" class="j_update_product_draft"
                                   data-productid="<s:property value="productId" />">1.
                                    重新编辑刚刚添加的新商品。</a>
                            </div>
                        </s:if>
                        <!--添加商品图片结束-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>
