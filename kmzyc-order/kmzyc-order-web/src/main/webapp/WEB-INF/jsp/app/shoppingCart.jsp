<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认订单</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery.dragndrop.js"></script>
<script type="text/javascript" src="/etc/js/showframe.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>

</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单生成'" scope="request"/>
<s:set name="son_name" value="'订单确认'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<br/>
<form action="/app/getFareAction.action" method="post">
<div>客户ID:<input type="text" name="customerID" value="2096"/></div>
<div><font size="5" face="宋体">我的购物车</font></div>
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td >商品名称</td>
    <td >价格</td>
    <td >返现/送积分</td>
    <td >库存状态</td>
    <td >数量</td>
    <td>总重量(kg)</td>
    <td width="108">操作</td>
  </tr>
  <tr>
    <td>康美菊皇茶6.5gx20包</td>
    <td>32.00</td>
    <td>10</td>
    <td>有货</td>
    <td>1</td>
    <td><input type="text" value="0.2" name="weight" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td> 昆仑雪菊6罐*20g精品礼盒装</td>
    <td>300</td>
    <td>100</td>
    <td>有货</td>
    <td>1</td>
    <td><input type="text" value="0.3" name="weight" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" /></td>
    <td>&nbsp;</td>
  </tr>
</table>
<p></p>
<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td align="right">2件商品</td>
    <td align="right">总计：</td>
    <td >￥
      332.00    </td>
  </tr>
  <tr>
    <td colspan="2"><div align="right">返现：</div></td>
    <td>-￥0.00</td>
  </tr>
  <tr>
    <td colspan="2" align="right">总计(不含运费)：</td>
    <td >￥<input name="commoditySum" type="text" class="STYLE1" value="332.00"  /></td>
  </tr>
  <tr>
  <td colspan="3" align="center"><input type="submit" name="Submit" value="提交" /></td>
  </tr>
</table>

</form>
</body>
</html>