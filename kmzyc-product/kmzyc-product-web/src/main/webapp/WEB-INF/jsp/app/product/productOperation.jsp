<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/addproduct.css" type="text/css" rel="stylesheet">
    <link  rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
    <link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script src="/etc/js/dialog.js"></script>
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/sea.js"></script>
    <script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/etc/js/product/product.js"></script>
    <script charset="utf-8"  src="/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
    <script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"  ></script>
    <script charset="utf-8"  src="/kindeditor/kindeditor.js"></script>
    <script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
    <script type="text/javascript" src="/etc/js/product_add.js"></script>
    <script type="text/javascript" src="/etc/js/kindeditor_show.js"></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
    <script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
    <script language='JavaScript' src="/etc/js/dialog-common.js"></script>

    <title>查看产品信息</title>
</head>
<body>
<input value='<s:property value="product.introduce" />' id="productContent" type="hidden" />

<div style="position:absolute;align:center;top:20px;left:30px">
    <div id="content">
        <div>
            <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                <tr>
                    <td colspan="4" align="center">
                        <input type="button" class="backBtn" onClick="gotoListForView()" />
                    </td>
                </tr>
                <tr>
                    <th style="background-color: #BFDFCA;" align="left" class="eidt_rowTitle" colspan="4">操作日志</th>
                </tr>
                <tr>
                    <th align="center" class="eidt_rowTitle">操作类型</th>
                    <th align="center" class="eidt_rowTitle">操作者</th>
                    <th align="center" class="eidt_rowTitle">操作时间</th>
                </tr>
                <s:iterator value="productOperateLogList">
                    <tr>
                        <td align="center" class="eidt_rowTitle"><s:property value="#request.productOperateType[operateType]"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="operateUserName"/></td>
                        <td align="center" class="eidt_rowTitle"><s:date name="operateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </s:iterator>
            </table>
        </div>
        <div style="margin-top: 20px">
            <table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
                <tr>
                    <th style="background-color: #BFDFCA;" align="left" class="eidt_rowTitle" colspan="8">SKU价格变动</th>
                </tr>
                <tr>
                    <th align="center" class="eidt_rowTitle">SKUID</th>
                    <th align="center" class="eidt_rowTitle">SKU编码</th>
                    <th align="center" class="eidt_rowTitle">重量（g）</th>
                    <th align="center" class="eidt_rowTitle">销售价</th>
                    <th align="center" class="eidt_rowTitle">市场价</th>
                    <th align="center" class="eidt_rowTitle">PV</th>
                    <th align="center" class="eidt_rowTitle">操作者</th>
                    <th align="center" class="eidt_rowTitle">操作时间</th>
                </tr>
                <s:iterator value="recordList">
                    <tr>
                        <td align="center" class="eidt_rowTitle"><s:property value="productSkuId"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="productSkuCode"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="unitWeight"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="price"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="markPrice"/></td>
                        <td align="center" class="eidt_rowTitle"><s:property value="pvValue"/></td>
                        <td align="center" class="eidt_rowTitle"><s:hidden name="createUser"/>
                            <s:if test="createUserName != null">
                                <s:property value="createUserName"/>
                            </s:if>
                            <s:else>
                                <s:property value="supplierAccount"/>
                            </s:else>
                        </td>
                        <td align="center" class="eidt_rowTitle"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>
</div>

<s:form action="/app/productShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
    <s:hidden type="hidden" name="checkedId"/>
    <s:hidden name="productForSelectPara.productNo"/>
    <s:hidden name="productForSelectPara.productTitle"/>
    <s:hidden name="productForSelectPara.bCategoryId"/>
    <s:hidden name="productForSelectPara.mCategoryId"/>
    <s:hidden name="productForSelectPara.categoryId"/>
    <s:hidden name="productForSelectPara.status" id="statusChecked"/>
    <s:hidden name="productForSelectPara.keyword"/>
    <s:hidden name="productForSelectPara.brandId"/>
    <s:hidden name="productForSelectPara.searchBrandName"/>
    <s:hidden name="productForSelectPara.bCategoryName"/>
    <s:hidden name="productForSelectPara.mCategoryName"/>
    <s:hidden name="productForSelectPara.searchCategoryName"/>
    <s:hidden name="page.pageNo"/>
    <s:hidden name="type" id="returnType" ></s:hidden>
</s:form>
</body>
</html>