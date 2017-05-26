<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="logo-box">
		   <div class="l-w i-registration-logo">

				 <a href="<%=ConfigurationUtil.getString("staticPath")%>/index.html"><img height="115" alt="康美中药城" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/res/images/common/logo.png"></a>

		   </div>
		</div> 
		
<div class="reg-bulletin"><img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/res/images/common/reg-bulletin.jpg"></div>
		
<div id="registration-form">
	<div class="mc-avatar">
    	<i class="avatar"><img src="<s:if test="null==#session.userImg||#session.userImg.isEmpty()"><%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/default-headpic.png</s:if><s:else><s:property value="#session.userImg"/></s:else>" width="80" height="80"></i>
        <div class="avatar-box">
       	  <p class="fn-green"><strong>来自
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
       	  的<span class="fn-red"><s:property value="#session.nikeName"/> </span>，您好。</strong></p>
            <p>立即设置一个密码，以后就可以直接登录康美中药城商城了！</p>
        </div>
    </div>
    <div class="regform"> 
    <form action="loginInfoBinding.action" id="setugInfoForm" name="setugInfoForm" method="post" namespace="/third">
    <s:token />
    <div class="reg-box">
        <span class="text">邮箱：</span>
        <div class="reg-text">
            <input type="text" class="err-input" maxlength="50" id="txtregistEmail" name="loginInfo.email" autocomplete="off"> 
            <div id="emailBoxFail" class="right-box-fail" style="display: none;">
		        		<i></i>   
		    </div>
        </div>
    </div>
    <div class="reg-box">
        <span class="text">用户名：</span>
        <div class="reg-text">
            <input type="text" class="err-input" id="txtloginAccount" name="loginInfo.loginAccount"> 
            <div id="accountBoxFail" class="right-box-fail" style="display: none;">
				        	<i></i> 
			</div>
        </div>
    </div>
    <div class="reg-box con-password">
        <span class="text">密码：</span>
        <div class="reg-text">
            <input type="password" class="err-input" id="txtregistPassword" name="loginInfo.loginPassword"> 
            <div id="passwordBoxFail" class="right-box-fail">
			        		<i></i> 
      		</div>
      		<div style="height: 15px;overflow:hidden;width: 100%;float: right;">
	      		<div class="letter fn-clear" id="pwdStrongth" style="display: none;">
		      		<span id="level1" class="current">弱</span>
		      		<span id="level2" >中</span>
		      		<span id="level3" >强</span>
	   			</div>
	   		</div>
        </div>	
    </div>
    <div class="reg-box">
        <span class="text">确认密码：</span>
        <div class="reg-text">
            <input type="password" class="i-text err-input" id="confirmpassword" name="confirmpassword">  
            <div class="right-box-fail" id="confirmPasswordBoxFail" style="display: none;">
                <i></i> 
            </div>
        </div>
    </div>
    <div class="reg-box">
        <div class="code-box">
            <label>
            <input type="checkbox" checked="checked" value="radio" id="ckaccept" name="accept">我已阅读并接受</label>
            <a target="_blank" href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-member-agreement.shtml" class="fn-blue">《用户协议》</a>
        </div>
    </div>
   <div class="reg-btn"> 
        <a id="btnSubReg" href="javascript:void(0);">立即设置</a>
        <a  style="font-weight:normal;color:#005EA7;background:none;margin:0;padding:0;width:200px;display: inline;" href="<%=request.getContextPath()%>/third/returnIndex.action">以后再说，立即去购物</a><br>     
      </div> 
    </form>
    </div>
</div>
