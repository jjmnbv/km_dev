<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认订单</title><link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery.dragndrop.js"></script>
<script type="text/javascript" src="/etc/js/showframe.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<Script src="/etc/js/Form.js" type="text/javascript"></Script>
<Script  src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript">
	function show(){
		document.getElementById("Panel").style.display="";
	}
	
	function useCouponFun1(){
		if(confirm("你确定要使用优惠券吗?")){
			var paysum=document.getElementById("payable").value;
			document.getElementById("payable").value=(paysum-50)+".00";
			//alert("cdindex1");
			//var index = $(this).attr("name");
			document.getElementById("cgindex").value=1;
			//alert("cdindex2");
		}
	}
	
</script>
</head>

<body>

<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单生成'" scope="request"/>
<s:set name="son_name" value="'订单提交'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>

<form id="orderForm"  method="post" action="/app/createOrderAction.action">
<div id="main_container" class="padding-left:10px;">


<div id="u0" class="u0"  >
<div id="u0_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:20px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333;">订单信息</span></p></div>
</div>
<input type="hidden" name="cgindex"  id="cdindex"/>
<div id="u1" class="u1"  >
<div id="u1_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:16px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333;">收货人信息</span></p></div>
</div>
<div id="u2" class="u2"  >
<div id="u2_rtf"><input type="hidden" name="order.customerName" value="康美测试" /><input type="hidden" name="order.customerMobile" value="18656457245" /><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">收货人 :</span> <input type="text" name="order.consigneeName" value="康美"/></p></div>
</div>
<div>
<div> <p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">客户账户:</span> <input type="text" name="order.customerAccount" value="orderpay"/></p></div>
<div> <p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">客户ID:</span> <input type="text" name="order.customerId" value="658421"/></p></div>
</div>
<div id="u3" class="u3"  >
<div id="u3_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">地</span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">&nbsp;&nbsp; </span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">址：</span><input type="text" name="order.consigneeAddr" wide="180" value="广东省深圳市福田区泰科路3号康美药业3楼"/></p></div>
</div>

<div id="u4" class="u4"  >
<div id="u4_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">固定电话</span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">：</span></p></div>
</div>
<div id="u5" class="u5"  >
<div id="u5_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">手机号码</span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">：<input type="text" name="order.consigneeMobile" value="18688888888" /></p></div>
</div>
<div id="u7" class="u7"  >
<div id="u7_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:16px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333;">支付及配送方式</span></p></div>
</div>
<div id="u8" class="u8"  >
<div id="u8_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">支付方式：</span>
		<select class="condition" name="order.payMethod"  value="<s:property value='#order.payMethod'/>" style="width:156px">
		   		
		   		<s:iterator id="payMethod" value="typeList">
		   		<option value="<s:property value='#payMethod.orderDictionaryKey'/>" ><s:property value="#payMethod.orderDictionaryValue"/></option>
		   		</s:iterator>
		</select>
</p></div>
</div>
<div id="u51" class="u51"  >
<div id="u51_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">配送</span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">方式：</span>
		<select class="condition" name="order.deliveryDateType"  style="width:156px">
		   		
		   		<s:iterator id="deliveryDateType" value="deliveryDateTypeList">
		   		<option value="<s:property value='#deliveryDateType.orderDictionaryKey'/>" ><s:property value="#deliveryDateType.orderDictionaryValue"/></option>
		   		</s:iterator>
		</select>	
</p></div>
</div>
<div id="u9" class="u9"  >
<div id="u9_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">运&nbsp; &nbsp; 费：</span><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;" name="order.fare">￥</span><input name="order.fare" type="text" value="<s:property value='order.fare'/>"/></p></div>
</div>
<div id="u10" class="u10"  >
<div id="u10_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:16px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333;">发票信息</span></p></div>
</div>
<div id="u11" class="u11"  >
<div id="u11_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:13px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">发票类型：不开发票</span></p></div>
</div>
<div id="u12" class="u12"  >
<div id="u12_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:16px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333;">商品清单</span></p></div>
<table width="1000" border="1" cellspacing="0" cellpadding="0">
  <tr>
  	<td>商品SKU</td>
    <td>商品编号</td>
    <td>仓库ID</td>
    <td>商品名称</td>
    <td>康美中药城价格(￥)</td>
    <td>赠送积分</td>
    <td>商品数量</td>
    <td>库存状态</td>
  </tr>
  <tr>  
  
    <td><input type="text" name="commoditySku" value="S00103001000301" /></td>
    <td><input type="text" name="commodityCode" value="S001030010003"/></td>
    <td><input type="text" name="warehouseId" value="1977"/></td>
    <td><input type="text" name="commodityName" value="康美菊皇茶6.5gx20包"/></td>
    <td><input type="text" name="commodityUnitPrice" value="32.00"/></td>
    <td><input type="text" name="credits" value="10"/></td>
    <td><input type="text" name="commodityNumber" value="1"/><input type="hidden" name="commoditySum" value="32"/></td>
    <td>现货</td>
  </tr>
  <tr> 
    <td><input type="text" name="commoditySku" value="S00102001000101" /></td>
    <td><input type="text" name="commodityCode" value="S001020010001"/></td>
    <td><input type="text" name="warehouseId" value="1982"/></td>
    <td><input type="text" name="commodityName" value="新疆天山 昆仑雪菊6罐*20g精品礼盒装"/></td>
    <td><input type="text" name="commodityUnitPrice" value="300.00"/></td>
    <td><input type="text" name="credits" value="100"/></td>
    <td><input type="text" name="commodityNumber" value="1"/><input type="hidden" name="commoditySum" value="300"/></td>
    <td>现货</td> 
  </tr>
</table>

</div>
<DIV id="u13" class="u13;" >

</DIV>


<DIV id="u13container" style="position:absolute; left:111px; top:507px; width:605px; height:161px; overflow:visible">

</DIV><div id="u52" class="u52" >
<DIV id="u52_line" class="u52_line" ></DIV>
</div>
<p></p>
<table class="table_search" width="70%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">商品总额:￥<input name="order.commoditySum" type="text" value="332.00"></td>
	</tr>
		<tr>
		<td align="right">+运费:&nbsp;&nbsp;￥00.00</td>
	</tr>
		<tr>
		<td align="right">-返现:&nbsp;&nbsp;￥00.00</td>
	</tr>
		<tr>
		<td align="right">应付总额:￥332.00</td>
	</tr>
</table>
<div id="u53" class="u53"  >

<input type="button" onclick="show()" value="使用优惠券" id="coupon">
</div>
<div id="Panel" style="display: none" >
 <h4>你可用的优惠券有:</h4>
 <table width="70%" align="left" cellpadding="0" cellspacing="0">
 	 <tr>
 		<td>优惠券编号</td>
 		<td>优惠券名称</td>
 		<td>优惠金额</td>
 		<td>操作</td>
	 </tr>
	 
	<s:iterator id="cgrant" value="cgrantList" status='st'>
	 <tr>
 		<td><input type="text" name="couponID" value="<s:property value='#cgrant.couponId'/>"/></td>
 		<td><input type="text" name="couponName" value="<s:property value='#cgrant.couponName'/>"/></td>
 		<td>￥<input type="text" name="couponSum" value="<s:property value='#cgrant.couponMoney'/>"/></td>
 		<td><input type="button"  name ="#st.index" onclick="useCouponFun1()" class="cgclass"  value="使用" id="useCoupon"></td>
	 </tr>
	</s:iterator>
 </table>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div style="padding-top:20px;margin-right:100px">
	<label>应付总额:￥</label><input style="color: #FF0000" type="text" name="order.amountPayable" id="payable" value="<s:property value='order.amountPayable'/>"></input>
</div>
<INPUT  type="submit"  value="提交订单"/>
</div></form>
</body>
</html>
