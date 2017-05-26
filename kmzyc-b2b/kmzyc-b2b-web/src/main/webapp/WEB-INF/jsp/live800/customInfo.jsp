<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>无标题文档</title>
	<jsp:include page="/WEB-INF/jsp/common/template.jsp" />
<!-- <link href="css/live800.css" rel="stylesheet" type="text/css"> -->
</head>

<body style="width:640px; margin:0 auto;">
<input id="loginId" type="hidden" value="${customInfo.baseInfo.loginAccount}" />
<div class="live">
  <ul class="tab">
  	<li class="tab-l"></li>
    <li id="infoTab" class="tab-h"><a href="javascript:void(0);">会员信息</a></li>
    <li id="orderTab" class="tab-a"><a href="javascript:void(0);">订单记录</a></li>
    <li id="exchangeTab" class="tab-a"><a href="javascript:void(0);">退换货记录</a></li>
  </ul>
  <div class="tab-div infoTable" style="display:block;">
  	<div>
      <ul>
        <li><b>用户名：</b><span>${customInfo.baseInfo.loginAccount}</span></li>
        <li class="tab-div-l"><b>注册时间：</b><span><fmt:formatDate pattern="yyyy-MM-dd" value="${customInfo.baseInfo.regiestDate}" /></span></li>
        <li><b>会员级别：</b><span>${customInfo.baseInfo.levelName}</span></li>
        <li><b>手机号码：</b><span>${customInfo.baseInfo.mobile}</span></li>
        <li><b>电子邮箱：</b><span>${customInfo.baseInfo.email}</span></li>
<%--         <li><b>优惠券：</b><span>${customInfo.baseInfo.}</span></li> --%>
        <li><b>账户余额：</b><span>${customInfo.baseInfo.amountAvlibal}</span></li>
        <li><b>会员积分：</b><span>${customInfo.baseInfo.availScore}</span></li>
      </ul>
    </div>
    <div class="tab-hr"></div>
    <div>
      <ul>
        <li><b>昵称：</b><span>${customInfo.baseInfo.nickname}</span></li>
        <li><b>职业类型：</b><span>${customInfo.baseInfo.professionType}</span></li>
        <li><b>真实姓名：</b><span>${customInfo.baseInfo.name}</span></li>
         <c:choose>
			<c:when test="${customInfo.baseInfo.sex == 0}">
				 <li><b>性别：</b><span>女</span></li>
			</c:when>
			<c:when test="${customInfo.baseInfo.sex == 1}">
				 <li><b>性别：</b><span>男</span></li>
			</c:when>
		</c:choose>
        <li><b>生日：</b><span>${customInfo.baseInfo.birthday}</span></li>
        <c:choose>
			<c:when test="${customInfo.baseInfo.maritalStatus == 0}">
				<li><b>婚姻状态：</b><span>未婚</span></li>
			</c:when>
			<c:when test="${customInfo.baseInfo.maritalStatus == 1}">
				<li><b>婚姻状态：</b><span>已婚</span></li>
			</c:when>
			<c:otherwise>
				<li><b>婚姻状态：</b><span>保密</span></li>
			</c:otherwise>
		</c:choose>
        <li><b>所在地：</b><span>${customInfo.baseInfo.province}${customInfo.baseInfo.city}${customInfo.baseInfo.area}</span></li>
        <li class="tab-div-l"><b>地址：</b><span>${customInfo.baseInfo.address}</span></li>
      </ul>
    </div>
    <table width="100%"  border="1" cellspacing="0">
      <th colspan="5">会员收货地址簿</th>
      <tr class="th-a">
        <td class="td-1">收货人</td>
        <td class="td-2">手机号码</td>
        <td class="td-3">省市县</td>
        <td class="td-4">详细地址</td>
        <td class="td-5">邮编</td>
      </tr>
      <c:forEach items="${customInfo.goodsReceiptAdd}" var="goods">
      <tr>
        <td class="td-1">${goods.name}</td>
        <td class="td-2">${goods.mobile}</td>
        <td class="td-3">${goods.province}${goods.city}</td>
        <td class="td-4">${goods.detailAdd}</td>
        <td class="td-5">${goods.postalCode}</td>
      </tr>
      </c:forEach>
    </table>
    <div class="tab-all alink"><a href="http://kmuser.kmb2b.com/userInfo/personalBasicInfo_preDetail.action?personalId=${customInfo.baseInfo.personalId }
    " target="_blank">查看详细会员信息>></a></div>
  </div>
  <div class="tab-div orderTable" style="display:none">
  	<div>
      <ul>
        <li><b>订单号：</b><input id="orderCode" name="" type="text"></li>
        <li><b>订单状态：</b><select id="orderStatus" name="">
         	<option value="">请选择</option>
			<option value="-1">已取消</option>
			<option value ="1">未付款</option>
			<option value ="2">已付款</option>
			<option value="3">已结转</option>
			<option value="4">已出库</option>
			<option value ="5">已配送</option>
			<option value ="6">已完成</option>
			<option value="12">送货失败</option>
			<option value="15">已结转未出库</option>
			<option value ="16">已拆分</option>
			<option value ="17">已合并</option>
			<option value="18">已拆分未结转</option>
			<option value="19">已合并未结转</option>
  		</select>
  		</li>
        <li><b>渠道：</b><select id="orderChannel" name="">
        	<option value="">请选择</option>
        	<option value="B2B">B2B</option>
        	<option value="B2B">B2B</option>
 			</select>
  		</li>
        <li><b>收货人：</b><input id="consigneeName" name="" type="text"></li>
        <li><b>收货电话：</b><input id="consigneeMobile" name="" type="text"></li>
        <li><input id="orderQueryBtn" name="" type="button" class="bin" value="查询"></li>
      </ul>
    </div>
    <table id="orderTable" width="100%"  border="1" cellspacing="0">
      <tr>
      <th colspan="8">订单总金额：${orderInfo.orderMoney }    实付总额：${orderInfo.orderActual }</th>
      </tr>
      <tr class="th-b">
        <td class="td-b1">订单号</td>
        <td class="td-b2">渠道</td>
        <td class="td-b3">销售类型</td>
        <td class="td-b4">收货人</td>
        <td class="td-b5">订单状态</td>
        <td class="td-b6">订单金额</td>
        <td class="td-b7">下单时间</td>
        <td class="td-b8">查看</td>
      </tr>
      <c:forEach items="${orderInfo.orderList }" var="order" varStatus="status">
      <tr>
        <td class="td-b1">${order.orderCode }</td>
        <td class="td-b2">${order.orderChannel }</td>
        <c:choose>
			<c:when test="${empty order.commerceId }">
				<td class="td-b3">自营</td>
			</c:when>
			<c:otherwise>
				<td class="td-b3">第三方</td>
			</c:otherwise>
		</c:choose>
        
        <td class="td-b4">${order.consigneeName }</td>
        <td class="td-b5">${order.orderStatusStr }</td>
        <td class="td-b6">${order.amountPayable }</td>
        <td class="td-b7"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${order.createDate }" /></td>
        <td class="td-b8 alink"><a href="http://kmorder.kmb2b.com/app/orderItemdetailAction.action?orderCode=${order.orderCode }" target="_blank">查看</a></td>
      </tr>
      </c:forEach>
    </table>
    <div class="tab-all alink"><a href="http://kmorder.kmb2b.com/app/orderlistByMapAction.action?map[%27customerAccount%27]=${customInfo.baseInfo.loginAccount}" target="_blank">查看所有订单记录>></a></div>
  </div>
  <div class="tab-div exchangeTable" style="display:none">
  	<div>
      <ul class="c-f">
        <li><b>订单号：</b><input id="exOrderCode" name="" type="text"></li>
        <li><b>退换货号：</b><input id="orderAlterCode" name="" type="text"></li>
        <li class="c-w"><b>状态：</b>
        <select id="proposeStatus" name="">
	        <option value="">请选择</option>
	        <option value="-2">已驳回</option>
	        <option value="-1">已取消</option>
	        <option value="1">已提交待审核</option>
	        <option value="2">已通过待退货</option>
	        <option value="3">已退货待取件</option>
	        <option value="4">已取件待质检</option>
	        <option value="51">已通过待退款</option>
	        <option value="52">已通过待发货</option>
	        <option value="53">已驳回待原件返回</option>
	        <option value="54">换货转退货</option>
	        <option value="61">已退款待确认</option>
	        <option value="62">已发货待签收</option>
	        <option value="63">已原件返回待签收</option>
	        <option value="7">已完成</option>
		</select>
		</li>
        <li class="bin-f c-w-60"><input id="exchangeQueryBtn" name="" type="button" class="bin" value="查询"></li>
      </ul>
    </div>
    <table id="exchangeTable" width="100%"  border="1" cellspacing="0">
      <tr class="th-b">
        <td class="td-c1">退换货号</td>
        <td class="td-c2">订单号</td>
        <td class="td-c3">商品标题</td>
        <td class="td-c4">类型</td>
        <td class="td-c5">状态</td>
        <td class="td-c6">查看</td>
      </tr>
      <c:forEach items="${exchangeInfo }" var="exchange" varStatus="status">
      <tr>
        <td class="td-c1">${exchange.orderAlterCode }</td>
        <td class="td-c2">${exchange.orderCode }</td>
        <td class="td-c3">${exchange.commodityName }</td>
        <td class="td-c4">${exchange.alterTypeStr }</td>
        <td class="td-c5">${exchange.proposeStatusStr }</td>
        <td class="td-c6 alink"><a href="http://kmorder.kmb2b.com/app/orderBackdownshowBackdownCKAction.action?returnPage=2&alterCode=${exchange.orderAlterCode }" target="_blank">查看</a></td>
      </tr>
      </c:forEach>
    </table>
    <div class="tab-all alink"><a href="http://kmorder.kmb2b.com/app/orderBackdownlistByMapAction.action?map[%27proposer%27]=${customInfo.baseInfo.loginAccount}" target="_blank">查看所有订单记录>></a></div>
  </div>
</div>
</body>
</html>