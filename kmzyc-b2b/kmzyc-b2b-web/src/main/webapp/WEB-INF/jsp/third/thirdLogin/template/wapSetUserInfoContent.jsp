<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- wap版内容 begin 20150811 update wap改版 -->
<!--内容区域-->
<div class="container">
    <div class="panel-body text-center">
        <div class="m-b-md">
            <img class="img-circle circle-border" width="80px;" height="80px;" src="<s:if test="null==#session.userImg||#session.userImg.isEmpty()"><%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/default-headpic.png</s:if><s:else><s:property value="#session.userImg"/></s:else>" />
        </div>
        <div class="help-block">
            <p>亲爱的
     		<s:if test="#session.thirdLoginType=='01'">
       	 	QQ
       	  </s:if>
       	  <s:elseif test="#session.thirdLoginType=='02'">
       	           微信
       	  </s:elseif>
       	  <s:elseif test="#session.thirdLoginType=='03'">
       	  	新浪微博
       	  </s:elseif>
       	  <s:elseif test="#session.thirdLoginType=='04'">
       	  	支付宝
       	  </s:elseif>
       	   <s:elseif test="#session.thirdLoginType=='05'">
       	  	淘宝
       	  </s:elseif>       
                        用户：<s:property value="#session.nikeName"/></p>
            <p>为了给您更好的服务，立即设置一个密码，以后就可以直接登录康美中药城商城了！</p>
        </div>
        <div class="help-block">
	          <p class="text-danger"  id="errorMessage" style="display: none;" >请输入正确密码！</p>
	          <p style="display: none;" id="valid3" class="text-danger">该时间段内不支持短信发送，请于当时8点至23点内使用！</p>
        	  <p style="display: none;" id="valid2" class="text-danger">您当日累计获取验证码已达上限，请您次日再试！</p>
              <p style="display: none;" id="valid0" class="text-danger">您获取验证码过于频繁，请稍后再试！</p>
        </div>
    </div>
    <form action="wapFullInfo.action" id="setUserInfoFrm" name="setUserInfoFrm" method="post" namespace="/third">
    <s:token/>
    <s:hidden name="isWap" value="Y"/>
    <s:hidden name="isActivity"/>
    <div class="form-body">
        <div class="form-group">
            <input type="hidden" id="registMobile" name="registMobile"/>
        	<input type="text" placeholder="请输入手机号码" class="form-control input-lg tooltips" id="txtMobile" data-original-title="" title=""/>
        </div>
        <div class="form-group">
            <!--<input type="text" placeholder="用户名" id="userName" name="loginInfo.loginAccount" class="form-control input-lg tooltips" data-original-title="" title="">-->
        	 <div class="input-group">
                <input type="text" aria-describedby="请输入短信验证码" placeholder="请输入短信验证码" class="form-control input-lg" id="mobileVerifyCode"/>
                <span class="input-group-btn" id="basic-addon2"><a id="getMail" class="btn btn-success" disabled="disabled">获取验证码</a><!-- <a class="btn btn-danger hidden">重新获取</a> --></span>
            </div>
        </div>
        <div class="form-group input-group">
            <input type="text" class="form-control input-lg" placeholder="验证码" id="imageVerifyCode"/>           
            <span class="captcha-img">
                <a href="javascript:void(0);" id="changeImgCode" class="inputimg"><img id="imageCodeObj" src="/codeImage.action?imgKey=regImg"></a>
            </span>
        </div>       
        <div class="form-group">
            <!--<input type="password" placeholder="密码"  id="pwd" name="loginInfo.loginPassword" class="form-control input-lg tooltips" data-original-title="" title="" />-->
        	 <input type="password" placeholder="请设置登录密码" class="form-control input-lg tooltips" data-original-title="" title="" id="pwd" name="loginInfo.loginPassword"/>
        </div>
        <div class="form-group">
            <!--<input type="password" placeholder="确认密码" id="confirmPwd" name="" class="form-control input-lg tooltips" data-original-title="" title=""/>-->
        	<input type="password" placeholder="请确认登录密码" class="form-control input-lg tooltips" data-original-title="" title="" id="confirmPwd">
        </div>
         <div class="form-group">
            <div class="ckbox ckbox-success">
                <input type="checkbox" checked="checked" id="agreementCk" />
                <label>同意<a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-member-agreement.shtml"  target="_blank" class="text-danger"> 《用户协议》</a></label>
            </div>
        </div>
        <div class="form-group">
            <a class="btn btn-success btn-block" id="btnSubmit">立即设置</a>
            <a href="<%=request.getContextPath()%>/third/returnIndex.action?isWap=Y" class="btn btn-default btn-block">以后再说，立即去购物</a>
        </div>
    </div>
    </form>
</div>
<!--内容区域 end-->

<!-- wap版内容end -->
