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
        <jsp:param name="titlePrefix" value="活动详情"></jsp:param>
    </jsp:include>
    <title>活动详情</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_activity.jsp"></jsp:include>
        <s:form action="" method="post" id="frm" name="frm" enctype="multipart/form-data">
        <div class="content" id="content" data-url="${basePath}">
            <div class="row-fluid"><!-- block -->
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home"></i>
                            <li>活动<span class="divider">/</span></li>
                            <s:if test="entryType=='save'">
                                <li>活动报名</li>
                            </s:if>
                            <s:elseif test="entryType=='update'">
                                <li>编辑活动</li>
                            </s:elseif>
                            <s:elseif test="entryType=='view'">
                                <li>活动详情</li>
                            </s:elseif>
                            <s:else>
                                <li>活动详情</li>
                            </s:else>
                        </ul>
                    </div>
                    <s:hidden id="msg" name="resultMessage.message"></s:hidden>
                    <s:hidden id="isSuccess" name="resultMessage.isSuccess"></s:hidden>
                    <%--操作类型 save/update/view--%>
                    <s:hidden id="entryType" name="entryType"></s:hidden>
                    <s:hidden id="activityId" name="activityId"></s:hidden>
                    <jsp:include page="activityBaseDetail.jsp"></jsp:include>

                    <div class="block-content">
                        <ul class="nav nav-tabs navbottom">
                            <li class="active"><a href="#tab01" data-toggle="tab">活动报名</a></li>
                        </ul>
                        <div class="content-box">
                            <div class="tab-content">
                                <div class="tab-pane fade in active">
                                    <p>
                                        <div class="form-horizontal">
                                            <s:if test="entryType=='save'||entryType=='update'">
                                                <div class="control-group">
                                                    <label class="control-label">
                                                        <span class="required">*</span>活动商品：
                                                    </label>
                                                    <div class="controls">
                                                        <a class="btn btn-mini open_sku">
                                                            选择商品
                                                        </a>
                                                    </div>
                                                </div>
                                            </s:if>
                                        </div>
                                        <!--以下为隐藏DIV如需显加入in即可：<div id="demo" class="collapse in">-->
                                        <div id="demo" class="collapse in">
                                            <table cellpadding="0" cellspacing="0" border="0"
                                                   style="display: <s:if test="existActivitySkuList == null || existActivitySkuList.size()==0">none</s:if>"
                                                   class="table table-bordered skuTable" style="margin-top: 10px">
                                                <tr class="tdbg skuTitle">
                                                    <td>商品标题</td>
                                                    <td width="100px">SUK编码</td>
                                                    <td width="100px">品牌</td>
                                                    <td width="80px">单价</td>
                                                    <td width="70px">库存</td>
                                                    <s:if test="activity.activityType==1">
                                                        <td width="150px">促销活动ID</td>
                                                    </s:if>
                                                    <s:elseif test="activity.activityType==2">
                                                        <td width="150px">上传活动图片</td>
                                                    </s:elseif>
                                                    <s:elseif test="activity.activityType==3">
                                                        <td width="80px">活动价</td>
                                                        <td width="80px">预销数量</td>
                                                        <td width="80px">推广佣金(%) </td>
                                                    </s:elseif>
                                                    <td width="90px">活动费用(元)</td>
                                                    <s:if test="entryType=='save'||entryType=='update'">
                                                        <td width="60px">操作</td>
                                                    </s:if>
                                                </tr>
                                                <s:iterator value="existActivitySkuList" status="item">
                                                    <s:if test="#item.first">
                                                        <s:set name="tempSkuId" value="productSkuId"></s:set>
                                                    </s:if>
                                                    <s:else>
                                                        <s:set name="tempSkuId" value="#tempSkuId + ',' + productSkuId"></s:set>
                                                    </s:else>
                                                    <s:if test="entryType=='update' && auditStatus == 2">
                                                        <tr class="editTr delbg tooltip-show" data-toggle="tooltip" title="审核不通产品">
                                                    </s:if>
                                                    <s:else>
                                                        <tr class="editTr">
                                                    </s:else>
                                                        <input type="hidden" name="activitySkuId" value="<s:property value='activitySkuId'/>"/>
                                                        <input type="hidden" name="activityId" value="<s:property value='activityId'/>"/>
                                                        <input type="hidden" name="productSkuCode" value="<s:property value="productSkuCode"/>"/>
                                                        <input type="hidden" name="brandName" value="<s:property value="brandName"/>"/>
                                                        <input type="hidden" name="price" value="<s:property value="price"/>"/>
                                                        <input type="hidden" name="stock" value="<s:property value="stock"/>"/>
                                                        <input type="hidden" name="productSkuId" value="<s:property value='productSkuId'/>"/>

                                                        <td><s:property value="productTitle"/></td>
                                                        <td><s:property value="productSkuCode"/></td>
                                                        <td><s:property value="brandName"/></td>
                                                        <td><s:property value="price"/></td>
                                                        <td><s:property value="stock"/></td>
                                                        <s:if test="activity.activityType==1">
                                                            <s:if test="entryType=='update'">
                                                                <td>
                                                                    <input type="text" class="promotionId" name="promotionId" id="promotionId" value="<s:property value="promotionId"/>"/>
                                                                </td>
                                                            </s:if>
                                                            <s:elseif test="entryType=='view'">
                                                                <td><s:property value="promotionId"/></td>
                                                            </s:elseif>
                                                        </s:if>
                                                        <s:elseif test="activity.activityType==2">
                                                            <s:if test="entryType=='update'">
                                                                <td>
                                                                    <s:if test="activitySkuImage != null && activitySkuImage!=''">
                                                                        <a target="_blank" href="<s:property value='imagePath'/><s:property value='activitySkuImage'/>" >查看图片</a>
                                                                        <br/>
                                                                    </s:if>
                                                                    <div class="uploader white">
                                                                        <input type="hidden" name="activitySkuImage" value="<s:property value="activitySkuImage"/>"/>
                                                                        <input type="text" name="activitySkuImageFileName" class="filename uwidth"/>
                                                                        <input type="button" name="file" class="button" value="浏览"/>
                                                                        <input type="file" name="activitySkuImageFile" class="imgFile" size="20"/>
                                                                    </div>
                                                                </td>
                                                            </s:if>
                                                            <s:elseif test="entryType=='view'">
                                                                <td>
                                                                    <s:if test="activitySkuImage != null && activitySkuImage!=''">
                                                                        <a target="_blank" href="<s:property value='imagePath'/><s:property value='activitySkuImage'/>" >查看图片</a>
                                                                    </s:if>
                                                                </td>
                                                            </s:elseif>
                                                        </s:elseif>
                                                        <s:elseif test="activity.activityType==3">
                                                            <s:if test="entryType=='update'">
                                                                <td><input type="text" class="span9 activityPrice" placeholder="元"
                                                                           name="activityPrice" value="<s:property value="activityPrice"/>"/></td>
                                                                <td><input type="text" class="span9 preSaleQuantity" placeholder="件"
                                                                           name="preSaleQuantity" value="<s:property value="preSaleQuantity"/>"/></td>
                                                                <td><input type="text" class="span9 commissionRate" placeholder="%"
                                                                           name="commissionRate" value="<s:property value="commissionRate"/>"/></td>
                                                            </s:if>
                                                            <s:elseif test="entryType=='view'">
                                                                <td><s:property value="activityPrice"/></td>
                                                                <td><s:property value="preSaleQuantity"/></td>
                                                                <td><s:property value="commissionRate"/></td>
                                                            </s:elseif>
                                                        </s:elseif>
                                                        <td>
                                                            <span class="money"><s:property value="skuTotalPrice"/></span>
                                                            <input type="hidden" name="skuTotalPrice" value="<s:property value='skuTotalPrice'/>"/>
                                                        </td>
                                                        <s:if test="entryType=='save'||entryType=='update'">
                                                            <td>
                                                                <input type="button" class="btn btn-danger btn-mini delete_tr"
                                                                        deleteId="<s:property value='productSkuId'/>" value="删除"/>
                                                            </td>
                                                        </s:if>
                                                    </tr>
                                                </s:iterator>
                                            </table>
                                            <s:hidden name="deleteSkuId" id="deleteSkuId"></s:hidden>
                                            <input type="hidden" name="haveSkuId" id="haveSkuId"
                                                   value="<s:property value='#tempSkuId'/>"/>
                                            <div class="allmoney"
                                                 style="display: <s:if test="existActivitySkuList == null || existActivitySkuList.size()==0">none</s:if>">
                                                合计：<span class="moneySpan"><s:property value="activityTotalPrice"/></span> 元
                                            </div>
                                            <s:if test="entryType=='save'||entryType=='update'">
                                                <div class="alert">
                                                    <button class="close" data-dismiss="alert">×</button>
                                                    <strong>提示：</strong> 审核不通过产品需删除后才能提交！
                                                </div>
                                            </s:if>
                                        </div>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <s:if test="entryType=='save'||entryType=='update'">
                                <a class="btn btn-success btn-large btn_submit" disabled=true>
                                    提交<s:if test="activity.chargeType != 1">并缴费</s:if>
                                </a>　
                            </s:if>
                            <input type="button" class="btn btn-large back_button" value="返回"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </s:form>
    </div>
</div>
<s:form action="/activity/getActivityList.action" method="post" id="backFrm" name="backFrm">
    <s:hidden name="activitySupplierType" id="activitySupplierType"></s:hidden>
    <s:hidden name="page" id="pageList"/>
</s:form>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>