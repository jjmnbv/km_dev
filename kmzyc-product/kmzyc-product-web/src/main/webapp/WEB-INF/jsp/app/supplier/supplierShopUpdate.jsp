<%@page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>更新供应商</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="/etc/js/supplier/supplierShopUpdate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
    <script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>
    <style type="text/css">
        /**输入框错误提示**/
        label.error {
            background: url(/etc/images/li_err.gif) no-repeat;
            font: 12px/1 verdana, simsun, sans-serif;
            margin: 0;
            padding-left: 20px;
            height: 20px;
            line-height: 20px;
            margin-left: 10px;
        }
        label.checked {
            background: url(/etc/images/li_ok.gif) no-repeat;
            font: 12px/1 verdana, simsun, sans-serif;
            margin: 0;
            padding-left: 20px;
            height: 20px;
            line-height: 20px;
        }
    </style>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'供应商管理'" scope="request"/>
<s:set name="name" value="'供应商店铺列表'" scope="request"/>
<s:set name="son_name" value="'供应商店铺详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="updateSupplierShop.action" namespace="app" name="frm1" method="post"
        enctype="multipart/form-data" id="frm">
    <input name="showShopMain.shopId"
           type="hidden" value="<s:property value='showShopMain.shopId'/>"/>
    <input type="hidden" name="shopId" value="<s:property value='showShopMain.shopId'/>"></input>
    <!-- 数据编辑区域 -->

    <table width="95%" class="edit_table" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px;">
        <!-- error message -->
        <s:if test="rtnMessage != null">
            <tr>
                <td colspan="2" align="center"><font color="red"><s:property
                        value='rtnMessage'/> </font>
                </td>
            </tr>
        </s:if>
        <tr>
            <th colspan="2" align="left" class="edit_title">基本信息</th>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺名称：</th>
            <td><s:property value="showShopMain.shopName"/>
            </td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺标题：</th>
            <td><s:property value="showShopMain.shopTitle"/>
            </td>
        </tr>
        <%--  	<tr>
				<th align="right" class="eidt_rowTitle">店铺LOGO：</th>
				<td>
				<img width="142px" height="50px" src="${imagePaths}${showShopMain.logoPath}">
				</td>
			</tr>
			<tr>
				<th align="right" class="eidt_rowTitle">店铺展示图片：</th>
				<td>
				<img width="300px" height="199px" src="${imagePaths}${showShopMain.filePath}">
				</td>
			</tr>--%>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">经营品牌：</th>
            <td width="80%"><s:property value="showShopMain.manageBrand"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">店铺负责人姓名：</th>
            <td width="80%"><s:property value="showShopMain.principalName"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">店铺负责人电话：</th>
            <td width="80%"><s:property value="showShopMain.contactInfo"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">店铺负责人手机：</th>
            <td width="80%"><s:property value="showShopMain.linkmanMobile"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">商户名称：</th>
            <td width="80%"><s:property value="showShopMain.corporateName"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">客服联系方式：</th>
            <td width="80%"><s:property value="showShopMain.serviceQq"/>
            </td>
        </tr>
        <tr>
            <th width="20%" align="right" class="eidt_rowTitle">店铺类型：</th>
            <td width="80%">
                <s:if test="showShopMain.shopType == 1">旗舰店</s:if>
                <s:if test="showShopMain.shopType == 2">专营店</s:if>
                <s:if test="showShopMain.shopType == 3">专卖店</s:if>
            </td>
        </tr>
        <%--<tr>
				<th align="right" class="eidt_rowTitle"><font color="red">*</font>店铺展示图片/视频：</th>
				<td><input type="file" name="showShopMain.filePath"></iput>
				  <input class="input_style" name="prodSupplier.address" size="60"
					type="text" value="<s:property value="showShopMain.filePath" />" />
				</td>
			</tr>--%>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺SEO：</th>
            <td><s:property value="showShopMain.shopSeoKey"/>
            </td>
        </tr>
        <%--  <tr>
				<th width="20%" align="right" class="eidt_rowTitle">联系方式：</th>
				<td width="80%"><s:property value="showShopMain.contactInfo" /></td>
			</tr>--%>

        <tr>
            <th width="20%" class="eidt_rowTitle" align="right">店铺简介：</th>
            <td width="80%">
                <s:property value="showShopMain.introduce"/>
            </td>
        </tr>
        <%--
        <tr>
            <th align="right" class="eidt_rowTitle">详细描述：</th>
            <td><input class="input_style" name="showShopMain.shopName" type="text"
                value="<s:property value="prodSupplier.companyScale"  />" /></td>
        </tr
        <tr>
            <th align="right" class="eidt_rowTitle">店铺级别：</th>
            <td><s:property value="showShopMain.shopLevel" /></td>
        </tr>
        <tr>
            <th align="right" class="eidt_rowTitle">店铺站点：</th>
            <td><s:property value="showShopMain.shopSite"/></td>
        </tr>
        --%>
    </table>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30"
           border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
        <tr>
            <td align="center">
                <input type="button" class="backBtn" onclick="gotoList()"/>
            <td width="20%" align="center"></td>
        </tr>
    </table>
    <br>
    <br>

    <input type="hidden" name="pageNum" value="<s:property value='pageNum' />"/>
    <input type="hidden" name="searchShopMain.shopCode" value="<s:property value='searchShopMain.shopCode' />"/>
    <input type="hidden" name="searchShopMain.shopName" value="<s:property value='searchShopMain.shopName' />"/>
    <input type="hidden" name="searchShopMain.status" value="<s:property value='searchShopMain.status' />"/>
    <input type="hidden" name="searchShopMain.auditStatus" value="<s:property value='searchShopMain.auditStatus' />"/>
</s:form>

<script type="text/javascript">
    function gotoList() {
        document.forms[0].action = "/app/supplierShopList.action";
        document.forms[0].submit();
    }
</script>
</body>
</html>