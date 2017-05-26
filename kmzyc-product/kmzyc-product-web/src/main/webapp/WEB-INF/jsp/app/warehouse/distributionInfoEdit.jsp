<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>配送单管理</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
    <link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type="text/javascript" src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/warehouse/distributionInfo.js"></script>
</head>
<body>
<script type="text/javascript">
	$().ready(function() {
		$("#distributionInfoEditfrm").validate();
	});
	
	//是否已提交标识
	var checkSubmitFlg = false; 
	function submitForm(){
		if(checkSubmitFlg){
			return false;
		}

		checkSubmitFlg == true;
		if($("#logisticsCompany").val()!=""){
			if($("#logisticsNo").val()==""){
				alert("请填写快递单号!");
				$("#logisticsNo").focus();
				return false;
			}
			
			//快递单号只支持输入英文或数字
			var regForLogisticNo=/^[A-Za-z0-9]+$/;
			if(!regForLogisticNo.test($("#logisticsNo").val())){
			      alert("快递单号只能录入数字或者英文,请您检查数据!");
			      $("#logisticsNo").focus();
			      return false;
			}
		}
		
		var count = 3;
		while(true){
			if($("#batchNo"+count).length<1){
				break;
			}
			
			if(document.getElementById("batchNo"+count).getAttribute("data-value")=="1" 
					|| document.getElementById("batchNo"+count).getAttribute("data-value")=="2"){
				if($("#batchNo"+count).val()==""){
					alert("请输入药品的生产批次号!");
					$("#batchNo"+count).focus();
					return false;
				}
			}
			count++;
		}
		
		$("#distributionInfoEditfrm").submit();
	} 
</script>

<s:set name="parent_name" value="'配送单管理'" scope="request" />
<s:set name="name" value="'配送单修改'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="editDistributionInfo" method="post" namespace='/app'
        id="distributionInfoEditfrm" name='distributionInfoEditfrm' onsubmit="return validateForm();">
	<input id="distributionId3" type="hidden" value="<s:property value='distributionId'/>"
           name="distributionInfo.distributionId" />
	<s:hidden name="distributionInfo.billNo" />
	<input type="hidden" name="isDrugTmp" value="${isDrug}"/>
    <input type="hidden" name="distributionInfo.orderNo" value="<s:property value='distributionInfo.orderNo'/>" />

	<!-- 数据编辑区域 -->
	<table width="98%" class="edit_table" align="center" cellpadding="1"
           cellspacing="0" border="1" bordercolor="#C7D3E2"
           style="border-collapse: collapse; font-size: 12px; margin-bottom: -31px">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="9" align="left"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>

		<tr>
			<th colspan="16" align="center" class="edit_title">配送详情单基本信息</th>
		</tr>
		<tr>
			<th colspan="2" align="right"><font color="red">*</font>物流单位:</th>
			<td>
                <s:select list="#request.logisticsCompanyMap"
                          style="width:125px" name="distributionInfo.logisticsCompany"
                          id="logisticsCompany" headerKey="" headerValue="请选择物流" reg="[^0]" tip="请选择" />
            </td>
			<th align="right"><font color="red">*</font>物流单号</th>
			<td style="width: 149px;">
                <input size="20" type="text" id="logisticsNo" name="distributionInfo.logisticsNo"
                       value="<s:property value='distributionInfo.logisticsNo'/>"
                       reg="^(\S{0,30})$" maxlength="30" /></td>

			<th align="right">联系人电话</th>
			<td colspan="5"><s:property value="distributionInfo.tel" /></td>

		</tr>
		<tr>
			<th colspan="2" align="right">仓库</th>
			<td><s:property value="#request.warehouseInfoMap[distributionInfo.warehouseId]" /></td>
			<th align="right">收货人:</th>
			<td style="width: 149px;"><s:property value="distributionInfo.userName" /></td>
			<th align="right">收货地址:</th>
			<td colspan="3"><s:property value="distributionInfo.deliveryAddress" /></td>
		</tr>
		<tr>
			<th colspan="2" align="right">配送单号</th>
			<td><s:property value="distributionInfo.distributionNo" /></td>
			<th align="right">订单号:</th>
			<td style="width: 149px;"><s:property value="distributionInfo.orderNo" /></td>
			<th align="right">出库单号:</th>
			<td colspan="3"><s:property value="distributionInfo.billNo" /></td>
		</tr>
    </table>
    <table id="dataTable" width="98%" class="edit_table" align="center"
        cellpadding="1" cellspacing="0" border="1" bordercolor="#C7D3E2"
        style="border-collapse: collapse; font-size: 12px;">
        <tr>
            <th align="center" width="10%">产品SKU码</th>
            <th align="center" width="19%">产品标题</th>
            <th align="center" width="8%">产品编号</th>
            <th align="center" width="8%">配送数量</th>
            <th align="center" width="10%">配送单价</th>
            <th align="center" width="10%">小计</th>
            <th align="center" width="10%">生产批次号</th>
            <th align="center" width="10%">备注</th>
        </tr>
        <s:iterator value="distributionDetailInfoList" status="stuts">
            <tr id="distributionInfo<s:property value='#stuts.index+3'/>">

                <td align="center">
                    <input id="productId<s:property value='#stuts.index+3'/>" type="hidden"
                           name="distributionDetailInfoList[<s:property value='#stuts.index'/>].productId"/>
                    <input id="productSkuId<s:property value='#stuts.index+3'/>"
                           value="<s:property value='productSkuId'/>" type="hidden"
                           name="distributionDetailInfoList[<s:property value='#stuts.index'/>].productSkuId"/>
                    <input id="productSkuValue<s:property value='#stuts.index+3'/>"
                           value="<s:property value='productSkuValue'/>" type="hidden"
                           name="distributionDetailInfoList[<s:property value='#stuts.index'/>].productSkuValue"/>
                    <input id="detailId<s:property value='#stuts.index+3'/>"
                           value="<s:property value='detailId'/>" type="hidden"
                           name="distributionDetailInfoList[<s:property value='#stuts.index'/>].detailId"/>
                    <s:property value='productSkuValue'/>
                </td>
                <td align="center">
                    <s:property value='product.productTitle'/>
                    <font color="red">
                        <s:if test="product.productType==1">[药品]</s:if>
                        <s:elseif test="product.productType==2">[医疗器械]</s:elseif>
                    </font>
                </td>
                <td align="center"><s:property value='productNo'/></td>
                <td align="center"><input size="5"
                    value="<s:property value='quantity'/>" disabled="true"
                    id="quantity<s:property value='#stuts.index+3'/>" type="text"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].quantity"
                    onkeyup="value=value.replace(/[^0-9]/g,'')"
                    onblur="checkProductStock(this);" reg="^((?!0)\d{1,7}|1000000)$"
                    tip="请输入1-1000000的整数" maxlength="7" /> <input size="15"
                    value="<s:property value='quantity'/>" type="hidden"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].quantity" />

                </td>
                <td align="center"><input size="5"
                    value="<s:property value='price'/>"
                    id="price<s:property value='#stuts.index+3'/>" type="text"
                    disabled="true"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].price"
                    onchange="totalsum(this);"
                    reg='^\d+(\.\d+)?$'
                    tip="请输入非负数" maxlength="7" /> <input
                    value="<s:property value='price'/>" type="hidden"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].price" />

                </td>
                <td align="center"><input size="8"
                    value="<s:property value='sum'/>" disabled="true"
                    id="sumId<s:property value='#stuts.index+3'/>" type="text"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].sum"
                    reg='^\d+(\.\d+)?$'
                    tip="请输入非负数" readonly maxlength="9" /> <input
                    size="10" value="<s:property value='sum'/>"
                    id="sumHide<s:property value='#stuts.index+3'/>" type="hidden"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].sum" />
                </td>
                <td align="center"><input size="15"
                    value="<s:property value='batchNo'/>" data-value="<s:property value='product.productType'/>"
                    id="batchNo<s:property value='#stuts.index+3'/>" type="text"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].batchNo"
                    reg='^(\S{0,32})$'
                    tip="请输入生产批次号" maxlength="32" />
                </td>
                <td align="center"><textarea
                    id="remark<s:property value='#stuts.index+3'/>"
                    name="distributionDetailInfoList[<s:property value='#stuts.index'/>].remark"
                    style="width: 200px; height: 23px;" cols="10" wrap="physical"
                    reg="^(\S{0,100})$" tip="备注不要超过100个汉字" maxlength="200"><s:property
                    value='remark' /></textarea></td>

            </tr>
        </s:iterator>
    </table>

    <!-- 底部 按钮条 -->
    <table width="98%" align="center" class="edit_bottom" height="30"
        border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
        <tr>
            <td align="center">
                <input class="btn-custom btnStyle" type="button" value="保存" onClick="submitForm();"/>&nbsp;&nbsp;
                <input class="btn-custom btnStyle" type="reset"  value="重置" />&nbsp;&nbsp;
                <input class="btn-custom btnStyle" type="button" value="返回" onClick="javascript:;history.back(-1)"/ >&nbsp;&nbsp;
            </td>
        </tr>
    </table>
    <br />
    <br />
</s:form>
</body>
</html>