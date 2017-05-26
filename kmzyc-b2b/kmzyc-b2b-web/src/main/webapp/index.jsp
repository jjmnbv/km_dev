<%@ page language="java" import="java.util.*,com.kmzyc.b2b.third.util.ThirdConstant,com.kmzyc.zkconfig.ConfigurationUtil"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String vshopPath=ConfigurationUtil.getString("vshop_path");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
您好！康美中药城B2B首页还在建设当中，您现在可以查看：<br>


一、注册、登录<br>
	1）<a href="/html/regist.htm">注册</a><br/><!-- <a href="/html/registration.jsp">wap注册</a><br/> -->
	2）<a href="/html/loginPage.htm">登录</a><br/><!-- <a href="/html/wapLogin.jsp">wap登录</a><br/> --><a href="/html/popLogin.htm">弹窗登录</a><br/>
	3）<a href="<%=request.getContextPath()%>/member/resetPwd.action">忘记密码</a><br>
	<%-- 4）<a href="<%=request.getContextPath()%>/member/resetPwdgoUnRegMember.action">免注册转会员</a><br> --%>
	<%-- 5）<a href="<%=request.getContextPath()%>/member/showOrderTrail.action">首页订单跟踪</a><br/> --%>
	<%-- 6）<a href="<%=request.getContextPath()%>/ajaxJson/ssoAuth.action?dateTime=20150907150559&number=a000045&sign=9C69BC22E4F0107E2819667C461FCC09">易创网联合登录</a><br/> --%>
二、会员中心：<br>
	<a href="<%=request.getContextPath()%>/member/goHome.action">个人中心</a><br>
	<%-- 1）<a href="<%=request.getContextPath()%>/member/queryOrderList.action">订单管理</a><br>
	2）<a href="<%=request.getContextPath()%>/member/goDeliveryAddress.action">收货地址</a><br>
	3）<a href="<%=request.getContextPath()%>/member/toSiteNotice.action">站内通知</a><br>
	4) <a href="<%=request.getContextPath()%>/member/goUserInfo.action">个人信息</a><br/>
	5) <a href="<%=request.getContextPath()%>/member/queryFavoriteList.action">我的收藏</a><br/>
	6）<a href="<%=request.getContextPath()%>/member/queryCouponList.action">优惠券管理</a><br>
	7) <a href="<%=request.getContextPath()%>/member/initRechargeDetails.action">充值明细</a><br/>
	8) <a href="<%=request.getContextPath()%>/member/queryPointsRecordList.action">等级积分</a><br/>
	8) <a href="<%=request.getContextPath()%>/member/showSecurityCentre.action">安全中心(请先登录)</a><br/>
	9) <a href="<%=request.getContextPath()%>/member/queryNoEvaluateList.action">我的评价</a><br/>
   10) <a href="<%=request.getContextPath()%>/lottery/queryLotteryList.action">我的奖品</a><br/> --%>
三、购物结算<br>
	1）<a href="/cart/listShopCar.action">购物车</a><br>
	2）<a href="gotoSettlement.action">结算</a><br>
	<!-- 2）<a href="/cart/listWapShopCar.action">wap购物车</a><br> -->
五、售后管理<br>
	1）<a href="<%=request.getContextPath()%>/member/gotoComplaint.action">建议投诉</a><br>
	<%-- 2）<a href="<%=request.getContextPath()%>/member/findEmailRrsHisExist.action">是否订阅</a><br> --%>
	
<%-- 六、第三方登录<br>
	1）<a href="<%=request.getContextPath()%>/third/qqIndex.action?clientIp=10.0.1.213">QQ</a><br>
	1-a）<a href="<%=request.getContextPath()%>/third/qqIndex.action?clientIp=10.0.1.213&isWap=Y">wap版QQ登录</a><br>
	2）<a href="<%=request.getContextPath()%>/third/sinaIndex.action?clientIp=10.0.1.213">SINA</a><br>
	3）<a href="<%=request.getContextPath()%>/third/alipayIndex.action?clientIp=10.0.1.213">ALIPAY</a><br>	
	4）<a href="<%=request.getContextPath()%>/third/taobaoIndex.action?clientIp=10.0.1.213">淘宝</a><br>
	5）<a href="https://oauth.taobao.com/authorize?response_type=code&client_id=21800540&redirect_uri=http%3a%2f%2fwww.kmb2b.com%3a8088%2fthird%2ftmallAuth.action&view=tmall&state=item_id_num:39930312391,skuId:0,quantity:1">天猫医药馆授权</a><br>
	6）<a href="http://aabbcc.kmzyw.com.cn/third/wxIndexForWap.action?clientIp=10.0.1.213&comeFrom=authorizeForWap">开发环境wap微信登录</a><br>
	7）<a href="http://maryma.xicp.net/third/wxIndexForWap.action?clientIp=10.0.1.213&comeFrom=authorizeForWap&returnUrl=http://maryma.xicp.net/wap/wapSettlement.action?productSkuID=9845&productVary=1">本地测试wap微信登录</a><br>
	8）<a href="javascript:void(0)" onclick="goBuy('local')">本地测试立即购买</a><br>
	9）<a href="javascript:void(0)" onclick="goBuy('test')">联调立即购买</a><br>
	
七、供应商资讯<br>
    1）<a href="<%=request.getContextPath()%>/member/initSupplierNews.action?shopId=63">资讯列表</a><br>
    
八、Wap结算<br>
	1）<a href="<%=request.getContextPath()%>/wap/wapSettlement.action?productSkuID=9485&productVary=2">wap</a><br>
	
九、微信结算<br>
	1）<a href="<%=request.getContextPath()%>/wechatPay/wxindex.action">微信</a><br>	
十、微信卡券核销<br>
    1）<a href="<%=request.getContextPath()%>/third/toWxConsumeCode.action?card_id=pFXg-t-cfhlMHKsWY0WmRj_RzMXM&encrypt_code=03b81d6ac13c&signature=aa">立即使用-测试礼品券已经预约情况</a><br>
	2）<a href="<%=request.getContextPath()%>/third/toWxConsumeCode.action?card_id=pFXg-t4rQxh2kYxhLBYfQoM969s8&encrypt_code=4545&signature=aa">立即使用--测试礼品券未预约情况</a><br>
	3）<a href="<%=request.getContextPath()%>/third/toWxConsumeCode.action?card_id=pFXg-t4rQxh2kYxhLBYfQoM&encrypt_code=4545&signature=aa">立即使用--测试非礼品券情况</a><br>
	5)<a href="<%=request.getContextPath()%>/third/toUploadImage.action">上传logo</a><br>
<br>	
十一、wap我的优惠券
1）<a href="<%=request.getContextPath()%>/member/goWapCoupon.action">wap我的优惠券</a><br>
十二、wap我的会员中心首页
1）	<a href="<%=request.getContextPath()%>/member/goWapMyHome.action">wap我的会员中心首页</a><br>
十三、wap全部订单
1）	<a href="<%=request.getContextPath()%>/member/queryWapOrderList.action">wap全部订单</a><br>
十四、wap退换货申请
1）	<a href="<%=request.getContextPath()%>/member/applyWapPrepare.action">wap退换货申请</a><br>
2）	<a href="<%=request.getContextPath()%>/member/queryWapReturnShopList.action">wap退换货记录</a><br>
十六、微信收货地址共享<br/>
	1)<a href="<%=request.getContextPath()%>/third/toEditAdress.action">去触发微信地址共享调用jsp页面</a><br>
十七、邀请有奖<br/>
	1）<a href="<%=request.getContextPath()%>/member/queryPrizeInvite.action">邀请有奖</a><br> --%>
</body>
<script language="javascript">
	function goBuy(requestType){
		
		var portalPath="http://maryma.xicp.net/";
		if(requestType=='test'){
			portalPath="http://aabbcc.kmzyw.com.cn/";
		}
		
		var targetUrl=portalPath+"wap/wapSettlement.action?productSkuID=10285&productVary=2";
		//targetUrl="http://maryma.xicp.net/member/gotoComplaint.action";
		//targetUrl="http://maryma.xicp.net/html/wapLogin.jsp";
		targetUrl=encodeURIComponent(targetUrl);
		//targetUrl=encodeURI(encodeURI(targetUrl));
		alert(targetUrl);
		console.log(targetUrl);
		//targetUrl="http%3a%2f%2fmaryma.xicp.net%2fwap%2fwapSettlement.action%3fproductSkuID%3d10285%26productVary%3d1";
		window.location.href=portalPath+"third/wxIndexForWap.action?comeFrom=authorizeForWap&returnUrl="+targetUrl;
		
		return;
		
		targetUrl=encodeURIComponent(targetUrl);
		//获得浏览器内置信息
		 var browserInfo = window.navigator.userAgent.toLowerCase();  
		 console.log("浏览器信息="+browserInfo);
		 var isWxBrowser=false;  //标识是否是微信浏览器
	 
		 if(browserInfo.match(/MicroMessenger/i)=="micromessenger") {  
		      console.log("是微信内置浏览器,详情如下:"+browserInfo);
		      isWxBrowser=true;
		 }	else{
			// alert("不是微信内置浏览器");
			 window.location.href=targetUrl;  
		 }	 
		 //如果是微信内置浏览器
		 if(isWxBrowser){
		    //判断用户是否已经登录
	        $.getJSON(portalPath + "/loginInfoAction.action?callback=?", function (json) {
	            var data = eval(json);
	            //如果用户未登录,则默认其去微信登录
	            if (data.returnObject.mark != 1) {
	            	alert("未登录");
	            	window.location.href=portalPath +"/third/wxIndexForWap.action?comeFrom=authorizeForWap&returnUrl="+targetUrl;
	            } else {
	            	alert("已登录");
	             	window.location.href=targetUrl;  
	            }
	        });
		 }
	}
</script>
</html>
