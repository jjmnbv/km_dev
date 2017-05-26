<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!--内容区域-->
<div class="container">
    <form  action="<%=basePath%>member/postWapPaySetting.action"class="form-horizontal" method="post" id="form">
        <input value='<s:property value="#request.changePayPasswordInfo.payRange" />' type='hidden' id='payRangeType'>   
        <div class="help-block"><p>请选择以下启用支付密码的条件，提交订单时，将根据您的设置启用支付密码，以保障账户资金安全</p></div>
        <div class="form-group">
                <div class="col-sm-9">
                                        <label class="checkbox-inline">
                                        
                                        <input type="checkbox" value="1" name="payRange" id="payRange1"
		checked="checked" disabled="true">
                                            使用余额支付
                                        </label>
                </div>
                <div class="col-sm-9">
                                        <label class="checkbox-inline">
                                                <input type="checkbox" value="2" name="payRange" id="payRange2">使用优惠券支付
                                        </label>
                </div>
        </div>
        <div class="form-group">
            <a  href="javascript:void(0);" id="submit" class="btn btn-success btn-block">提交</a>
        </div>
    </form>
</div>
<!--内容区域 end-->



























<%-- 

    <section class="page-content">
        <header>
            <div class="page-hd"><a href="javascript:void(0)" class="icon-angle-left fn-left"></a><h2>支付管理</h2></div>
        </header>
        <div class="page-box">
            <!--支付管理-->
            <form class="ch-fm" action="<%=basePath%>member/postWapPaySetting.action"
	method="post" id="form">
            <div class="set-info fn-p10">
                <div class="help-block">
                    选择以下启用支付密码的条件，提交订单时，将根据您的设置启用支付密码，以保障账户资金安全
                </div>
                <div class="form-group">
                <input value='<s:property value="#request.changePayPasswordInfo.payRange" />' type='hidden' id='payRangeType'>   
                    <input type="checkbox" value="1" name="payRange" id="payRange1"
		checked="checked" disabled="true">
                    <label>使用余额时</label>
                </div>
                <div class="form-group">
              <input type="checkbox" value="2" name="payRange" id="payRange2">
                    <label>用优惠券时</label>
                </div>
                <a href="javascript:void(0);" id="submit" class="btn btn-success">提 交</a>
            </div>
            </form>
        </div>
    </section> --%>
   
