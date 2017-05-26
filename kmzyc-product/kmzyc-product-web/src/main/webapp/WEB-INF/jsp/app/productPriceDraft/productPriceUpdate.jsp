<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品发布</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<script src="/etc/js/jquery-1.8.3.js"></script>
<style type="text/css">
	.edit_title{
		padding:0px;
		font-size:12px;
	}
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:set name="parent_name" value="'产品管理'" scope="request" />
<s:set name="name" value="'产品发布'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/updateProductDraftSKUPrice.action" method="POST"
	namespace="/app" id="frm" onsubmit="return checkPrice();" >
    <s:token></s:token>
	<input type="hidden" name="productId" value="<s:property value="productId" />" />
	<input type="hidden" id="sell" value="<s:property value="#request.sell" />" />
	<input type="hidden" id="support" value="<s:property value="#request.support" />" />
<!-- 数据编辑区域 -->
<table width="98%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1"
       bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;margin-left:12px;float:left;">
	<tr> 
		<th align="center" class="edit_title">商品信息</th>
		<th align="center" class="edit_title" width="80px">市场价</th>
		<th align="center" class="edit_title" width="80px">成本价</th>
		<th align="center" class="edit_title" width="80px">销售单价</th>
		<th align="center" class="edit_title" width="110px">重量（单位：克）</th>
		<th align="center" class="edit_title" width="110px">PV值（康美中药城）</th>
		<th align="center" class="edit_title" width="110px">条形码</th>
		<th align="center" class="edit_title">操作</th>
	</tr>
	<s:iterator value="product.productSkuDrafts" >
		<tr> 
			<td align="left">
				<input type="hidden" name="productSkuId" value="<s:property value="productSkuId" />" />
				<input type="hidden" id="skuCostPrice<s:property value="productSkuId" />" name="skuCostPrice" value="<s:property value="costPrice"/>" />
				<input type="hidden" id="skuPrice<s:property value="productSkuId" />" name="skuPrice" value="<s:property value="price"/>" />
				<input type="hidden" id="skuMarkPrice<s:property value="productSkuId" />" name="skuMarkPrice" value="<s:property value="markPrice"/>" />
				<input type="hidden" id="skuWeight<s:property value="productSkuId" />" name="skuWeight" value="<s:property value="unitWeight"/>" />
				<input type="hidden" id="skuPvValue<s:property value="productSkuId" />" name="skuPvValue" value="<s:property value="pvValue"/>" />
				<input type="hidden" id="skuSkuBarCode<s:property value="productSkuId" />" name="skuSkuBarCode" value="<s:property value="skuBarCode"/>" />
				<b>商品名称</b>：<s:property value='product.productName'/>&nbsp;&nbsp;
				<b>商家名称</b>：
				<s:if test="product.merchantName==null">
						康美
				</s:if>
				<s:else>
					<s:property value="product.merchantName" />
				</s:else>&nbsp;&nbsp;
				<s:iterator value="attributeValues">
					<b><s:property value="attribute" /></b>：<s:property value="value" />&nbsp;&nbsp;
				</s:iterator>
			</td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='markPrice != null'><s:property value="%{getText('{0,number,##.##}',{markPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='costPrice != null'><s:property value="%{getText('{0,number,##.##}',{costPrice})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='price != null'><s:property value="%{getText('{0,number,##.##}',{price})}" /></s:if><s:else>暂无价格</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='unitWeight != null'><s:property value="%{getText('{0,number,##.##}',{unitWeight})}" /></s:if><s:else>暂无重量</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:if test='pvValue != null'><s:property value="%{getText('{0,number,##.##}',{pvValue})}" /></s:if><s:else>0.0</s:else></td>
			<td align="right" class="<s:property value="productSkuId" />" ><s:property value="skuBarCode" /></td>
			<td align="center">
				<input type="button" class="btnStyle" value="修 改" onclick="priceChange(this,<s:property value="productSkuId" />)" />
			</td>
		</tr>
	</s:iterator>
</table>
<br />
<!-- 底部 按钮条 -->
<table width="98%" align="left" class="edit_bottom" height="30"
	border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
	<tr>
		<td align="center">
			<input class="saveBtn" TYPE="submit" value="" />
			&nbsp;&nbsp;
			<input type="button" class="backBtn" onclick="gotoList()" />
		</td>
	</tr>
</table>
</s:form>

<s:form action="/app/productDraftAuditShow.action" method="POST" namespace='/app' id="listfrm" name='listfrm'>
	<s:hidden type="hidden" name="checkedId"/>
	<s:hidden name="productForSelectPara.productNo"/>
	<s:hidden name="productForSelectPara.productName"/>
	<s:hidden name="productForSelectPara.bCategoryId"/>
	<s:hidden name="productForSelectPara.mCategoryId"/>
	<s:hidden name="productForSelectPara.categoryId"/>
	<s:hidden name="productForSelectPara.status"/>
	<s:hidden name="productForSelectPara.keyword"/>
	<s:hidden name="productForSelectPara.brandId"/>
	<s:hidden name="page.pageNo"/>
	<s:hidden name="type" />
</s:form>
</body>
<script>
	function gotoList(){
		document.getElementById("listfrm").submit();
	}
	
	function checkPrice(){
		if($("input[class='btnStyle'][value='确 定']").length>0){
			alert("请先点击确定按钮！");
			return false;
		}
		var havePass;
		$.ajax({
			type:'POST',
			url:'/app/findSameSkuBarCodeProductSku.action',
			async:false,
			data:$("#frm").serializeArray(),
			success:function(data){
                havePass = data.isSuccess;
				if(!havePass){
					alert(data.message);
				}
			},
			dataType:'json'
		});
        return havePass;
	}

    $(document).on('blur','.toChange',function(e){
        e.preventDefault();
        //非自营和代销
        if ($("#support").val() != "true") {
            return;
        }

        var _this = $(this);
        //非销售价和成本价输入框
        var index = _this.attr("index-number");
        if (index != "1" && index != "2") {
            return;
        }

        var price, costPrice;
        var skuId = _this.attr("sku-id");
        if (index == "1") {
            costPrice = _this.val();
            price = $(".modify" + skuId + "[index-number='2']").val();
        } else if (index == "2") {
            costPrice = $(".modify" + skuId + "[index-number='1']").val();
            price = _this.val();
        }
        //为空或者不存在
        if (price == null || price == undefined || $.trim(price) == ""
                || costPrice == null || costPrice == undefined || $.trim(costPrice) == "") {
            return;
        }

        computePv(price, costPrice, $(".modify" + skuId + "[index-number='4']"));
    });

    //计算代销和自营的pv
    function computePv(price, costPrice, pvObj) {
        $.ajax({
            type: "POST",
            url: "/app/getDefaultPvValue.action?t=" + new Date().getTime(),
            data: {
                "price"    :$.trim(price),
                "costPrice":$.trim(costPrice)
            },
            traditional:true,
            success: function(data){
                pvObj.val(data);
            },
            error : function(data) {
                alert("计算默认pv失败!");
            }
        });
    }

    function priceChange(btn, skuId) {
        if (btn.value == '修 改') {
            $("." + skuId).each(function (i) {
                var inputValue = $(this).text();
                if (inputValue == '暂无价格' || inputValue == '暂无重量') {
                    inputValue = '';
                }
                if (i == 4 && inputValue == "") {
                    inputValue = "1";
                }
                var html = '<input class="modify' + skuId + ' toChange" sku-id="'
                        +skuId+'" index-number="'+i+'" size="8" type="text" value="'
                        + inputValue + '" maxlength="9"/>';
                //条形码
                if (i == 5) {
                    html = '<input class="modify' + skuId + '" size="13" type="text" value="' + inputValue + '" />';
                }
                $(this).html(html);
            });

            btn.value = '确 定';
            setTimeout(function () {
                $(".modify" + skuId)[0].select();
            }, 10);
        } else {
            var price = "";
            var costPrice = "";
            var markPrice = "";
            var unitWeight = "";
            var pvValue = "";
            var skuBarCode = "";
            var havePass = true;
            var priceTest = /^\d+(\.?\d{1,2})?$/;
            var pvTest = /^\d+(\.?\d{1})?$/;
            var _input;
            var strMsg = "";

            $(".modify" + skuId).each(function (i) {
                var inputValue = $(this).val();
                if (inputValue != '') {
                    if (i == 5) {
                        skuBarCode = inputValue;
                    } else if (i == 4) {
                        havePass = pvTest.test(inputValue);
                        if (!havePass) {
                            _input = $(".modify" + skuId)[i];
                            strMsg = "请输入正确PV值，且请不要超过1位小数！\r\n";
                            return false;
                        } else {
                            pvValue = inputValue;
                        }
                    } else {
                        havePass = priceTest.test(inputValue);
                        if (!havePass) {
                            _input = $(".modify" + skuId)[i];
                            strMsg = "请输入正确的数字，且请不要超过2位小数！\r\n";
                            return false;
                        } else if (i == 0) {
                            markPrice = inputValue;
                        } else if (i == 1) {
                            costPrice = inputValue;
                        } else if (i == 2) {
                            price = inputValue;
                        } else if (i == 3) {
                            unitWeight = inputValue;
                        }
                    }
                }
            });

            if (!havePass) {
                alert(strMsg);
                setTimeout(function () {
                    _input.select();
                }, 10);
                return;
            }

            if (price == '') {
                price = "0";
            }
            if (costPrice == '') {
                costPrice = "0";
            }
            if (pvValue == '' || parseFloat(pvValue) < parseFloat("1")) {
                pvValue = "1";
            }
            $("#skuPrice" + skuId).val(price);
            $("#skuCostPrice" + skuId).val(costPrice);
            $("#skuMarkPrice" + skuId).val(markPrice);
            $("#skuWeight" + skuId).val(unitWeight);
            $("#skuPvValue" + skuId).val(pvValue);
            $("#skuSkuBarCode" + skuId).val(skuBarCode);
            if (markPrice == '') {
                markPrice = "暂无价格";
            }
            if (unitWeight == '') {
                unitWeight = "暂无重量";
            }
            $($("." + skuId)[0]).html(markPrice);
            $($("." + skuId)[1]).html(costPrice);
            $($("." + skuId)[2]).html(price);
            $($("." + skuId)[3]).html(unitWeight);
            $($("." + skuId)[4]).html(pvValue);
            $($("." + skuId)[5]).html(skuBarCode);
            btn.value = '修 改';
        }
    }
</script>
</html>