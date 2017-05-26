<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>提交订单</title>
    <style type="text/css">
<!--
.STYLE2 {font-size: 24px}
.STYLE3 {font-size: 18px}
-->
    </style>
</head>
<body>支付及配送方式

<p class="STYLE2">填写并核对订单信息</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="STYLE3"> 收货人信息</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 收货人：康美</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 收货人地址：康广东省深圳市福田区泰科路3号康美药业3楼</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 收货人手机：15698542324</p>

<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=""> 支付及配送方式</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 支付方式：网银支付</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 配送物流：康美
</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 运费：￥0.00</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=""> 发票信息</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 发票类型：不开发票</p>
<p>商品列表：</p>
  <table width="1266" border="1" cellpadding="0" cellspacing="0" bordercolor="#000000">
    <tr>
      <td width="179">商品名称</td>
      <td width="179">商品编号</td>
      <td width="179">商品数量</td>
      <td width="179">单位</td>
      <td width="179">商品单价</td>
      <td width="179">商品总价</td>
      <td width="176">商品SUK编号</td>
    </tr>
    <tr>
      <td>菊皇茶</td>
      <td>4562555</td>
      <td>5</td>
      <td>瓶</td>
      <td>6.00</td>
      <td>30.00</td>
      <td>654833465</td>
    </tr>
    <tr>
      <td>白兰菊茶</td>
      <td>8545212</td>
      <td>6</td>
      <td>包</td>
      <td>12</td>
      <td>72.00</td>
      <td>954421332</td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <p>2件商品总金额：￥102.00
    &nbsp;</p>
  <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;返现：-￥0.00 </p>
  <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运费：￥0.00 </p>
  <p> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;应付总金额：￥102.00 </p>
  <form action="" method="post">
  		<input name="提交订单" type="submit" value="提交订单">
  </form>
</body>

</html>