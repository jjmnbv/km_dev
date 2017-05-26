<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/common/wapTemplate.jsp">
        <jsp:param name="titlePrefix" value="填写收货地址"></jsp:param>
    </jsp:include>
</head>

<body class="l-w1000">
	<!--顶部登录条-->
  <%--   <%@ include file="/html/common/wap/portal-head.jsp" %>
 --%>
  		<jsp:include page="/html/common/wap/portal-head.jsp">
	     	<jsp:param name="pageTitle" value="填写收货地址"></jsp:param>
	    </jsp:include> 
	    
	    <!--内容区域  -->
   		<div class="container zjd-zt">
			<div class="col-xs-12 fz-zt">
			恭喜用户：<strong>${userName}</strong>
			<p>您获得康美<strong  class="text-success">${awardsContent}1瓶</strong>！</p></div>
			<div class="col-xs-12 mag-top">
			请仔细核对您填写的收货地址，活动结束后我们会及时将奖品寄送给您！
			<p class=" text-danger">友情提示：请勿随意关闭此页面，否则将视为放弃领取奖品哦！</p></div>
		</div>
	   <!--添加收货人信息-->
	   <div class="container">
		    <form id="addShipAddress" class="form-horizontal" action="/lottery/saveShipAddress.action" method="post">
		    	<input type="hidden" value="${awardsId}" name="awardsId">
		    	<input type="hidden" value="${awardsContent}" name="awardsContent">
		        <div class="form-group ">
		            <input type="text" class="form-control input-lg" id="name" name="name" maxlength="15" placeholder="姓名">
		        </div>
		        <div class="form-group">
		            <input type="text" class="form-control input-lg" id="mobile" name="mobile" maxlength="11" placeholder="电话">
		        </div>
		        <div class="form-group">
		            <select class="form-control input-lg" id="province" name="province">
		            </select>
		        </div>
		        <div class="form-group">
		            <select class="form-control input-lg" id="city" name="city">
		            </select>
		        </div>
		        <div class="form-group">
		            <select class="form-control input-lg" id="area" name="area">
		            </select>
		        </div>
		        <div class="form-group">
		            <textarea rows="5" id="detailedAddress" name="detailedAddress"
							maxlength="60" placeholder="详细地址" class="form-control"></textarea>
		        </div>
		        <div class="form-group">
		            <a href="javascript:void(0)" id="saveShipAddressBtn" class="btn btn-danger btn-block">保存地址</a>
		        </div>
		    </form>
		</div>

	<!--底部-->
   <%@ include file="/html/common/wap/portal-foot.jsp" %>  
</body>
</html>

