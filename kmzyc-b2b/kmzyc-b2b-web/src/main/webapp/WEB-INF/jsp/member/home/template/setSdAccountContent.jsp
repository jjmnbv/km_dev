<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="logo-box">
		   <div class="l-w i-registration-logo">

				<a href="<%=ConfigurationUtil.getString("staticPath")%>/index.html"><img height="115" alt="康美中药城" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/res/images/common/logo.png"></a>

		   </div>
		</div> 
		
		
	<div class="sd-title" style="display:none">
	    <h2>亲爱的时代用户${b2b_sessionUserName}，为了给您更好的服务，请完善康美中药城相关资料，以后您可以直接使用手机号进行登录</h2>
	</div>
	
<div id="registration-form">
  <div class="new-reg">
    <div class="new-box1"> 
    	<!--  <i class="new-box1-left"><img src="http://qzapp.qlogo.cn/qzapp/101037674/8312251A13E03CF1BEA83602C9C523C8/50" width="80" height="80"></i>-->
        <i class="new-box1-left"><img id="userImg" width="100" height="100" src="" alt="" onerror="this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__man_err100_100.jpg';"/>
                <!--  <a id="editUserImg_a" href="javascript:void(0)">编辑头像</a>--></i>
      
      <div class="new-box1-right">
        <p class="fn-green"><strong>亲爱的时代用户<span class="fn-red">${b2b_sessionUserName}</span>，您好。</strong></p>
        <p>为了给您更好的服务，请完善康美中药城相关资料，以后您就可以直接使用手机号进行登录!</p>
      </div>
    </div>
    <form class="regform sd-form" id="sform" action="sdBindMember.action" menthod="post">
  	  <input type="hidden" value="${b2b_seesionUserId}" name="user.loginId" id="loginId" />
      <div class="reg-list-box">
        <div class="reg-info">
          <div class="reg-zh">
          <div class="reg-info-le" id="mobileErrRed"> <span class="reg-name">手机号码：</span>
            <input type="text" class="reg-txt" name="mobile" id="mobile" autocomplete="off" placeholder="请输入手机号">
          </div>
          <div class="reg-info-ri" id="mobileFngo" style="display:none"><i class="fn-go"></i>&nbsp;</div>
          </div>
          <div class="reg-ts" id="mobileFormatErr" style="display:none" ><i class="fn-error"></i>手机格式不正确</div>
          <div class="reg-ts" id="mobileUsedErr" style="display:none" ><i class="fn-error"></i>手机号已注册，如果您曾用此手机号注册过，可通过该手机号<a  href="javascript:void(0);" id="link_forget_pass">找回帐号密码</a></div>
          <div class="reg-ts cr-c-666" id="mobileTip" style="display:none"><i class="fn-sigh"></i>完成验证后，可以用手机登录或找回密码</div>
        </div>
        <div class="reg-info">
        <div class="reg-zh">
          <div class="reg-info-le" id="imgCodeErrRed">
            <span class="reg-name">验证码：</span>
            <input type="text" class="reg-txt w-120" name="autoCode" id="autoCode"   autocomplete="off" placeholder="请输入验证码">
            <a href="javascript:void(0)" class="reg-yz"><img id="imageCode"/></a>
          </div>
          <div class="reg-info-ri" id="imgCodeFngo" style="display:none"><i class="fn-go"></i>&nbsp;</div>
          </div>
          <div class="reg-ts" id="imgCodeErr" style="display:none" ><i class="fn-error"></i>验证码错误</div>
          <div class="reg-ts cr-c-666" id="imgCodeTip" style="display:none" ><i class="fn-sigh"></i>看不清图片？点击图片更换图形验证码</div>
        </div>
        <div class="reg-info">
       <div class="reg-zh">
          <div class="reg-info-le" id="mobileVerifyCodeErrRed">
            <span class="reg-name">短信验证码：</span>
            <input type="text" class="reg-txt w-120" name="mobileVerifyCode" id="mobileVerifyCode" autocomplete="off" placeholder="请输入短信验证码">
            <div class="phone-btn" >
						<div class="p-btn" >
							<a href="javascript:void(0);" class="btn-b btn-b-disabled btn-b-sm" id="getMail"> 
							<span style="color:#333">短信验证码</span>
							</a>
						</div>
			</div>
            </input>
            
          </div>
          <div class="reg-info-ri"><span class="reg-info-ri" id="pRemainTag"></span></div>
          <div class="reg-info-ri" style="display:none"  id="valid2"><span class="reg-info-ri">您当日累计获取验证码已达上限，请您次日再试！</span></div>
          <div class="reg-info-ri" style="display:none"  id="valid0"><span class="reg-info-ri">您获取验证码过于频繁，请稍后再试！</span></div>
          <div class="reg-info-ri" style="display:none"  id="valid3"><span class="reg-info-ri">该时间段内不支持发送，请于当时8点至23点内使用！</span></div>
          <div class="reg-info-ri" id="mobileVerifyCodeFngo" style="display:none"  ><i class="fn-go"></i>&nbsp;</div>
          </div>
          <div class="reg-ts" id="mobileVerifyCodeErr" style="display:none"  ><i class="fn-error"></i>短信验证码错误</div>
          <div class="reg-ts cr-c-666" id="mobileVerifyCodeTip" style="display:none"><i class="fn-sigh"></i>点击短信验证码，获取验证码数字</div>
        </div>
        <div class="reg-info">
          <div class="reg-zh">
	          <div class="reg-info-le" id="passwordErrRed"> 
	            <span class="reg-name">设置密码：</span>
	            <input type="password" class="reg-txt" name="user.loginPassword" id="passWordInp" autocomplete="off" placeholder="设置8-20位登录密码">
	          </div>
	          <div class="reg-info-ri"><i class="fn-sigh"></i>这里是设置的是健康账号的登录密码，不会影响时代账号密码</div>
	          <div class="reg-info-ri" id="Fngo" style="display:none"  ><i class="fn-go"></i>&nbsp;</div>
          </div>
          <div class="reg-ts" id="passwordFormatErr" style="display:none" ><i class="fn-error"></i>长度8-20字符之间</div>
          <div class="reg-ts" id="passwordRiskErr" style="display:none" ><i class="fn-error"></i>有被盗风险，建议使用大小写字母、数字和符号两种及以上组合</div>
          <div class="reg-ts cr-c-666" id="passwordTip" style="display:none"><i class="fn-sigh"></i>建议使用大小写字母，数字和符号两种及以上的组合，8-20字符</div>
        </div>
        <div class="reg-info">
        <div class="reg-zh">
          <div class="reg-info-le" id="samePassInpErrRed"> 
            <span class="reg-name">确认密码：</span>
            <input type="password" class="reg-txt" name="samePassInp" id="samePassInp" autocomplete="off" placeholder="请再次输入登录密码">
          </div>
          <div class="reg-info-ri" id="samePassFngo" style="display:none"  ><i class="fn-go"></i>&nbsp;</div>
          </div>
          <div class="reg-ts" id="samePassInpErr" style="display:none"><i class="fn-error"></i>两次密码设置不一致</div>
        </div>
        <div class="reg-info fz-14">
          <label>
            <input type="checkbox" checked="checked" value="radio"  id="read" name="accept">我已阅读并接受
          </label>
          <a target="_blank" href="<%=ConfigurationUtil.getString("staticPath")%>/helps/help-member-agreement.shtml"  class="fn-blue">《用户协议》</a>
        </div>
        <div class="reg-info">
        <a href="javascript:void(0)" class="reg-new-btn" id="registButon" disable="disabled" >立即设置</a>
        <a style="font-weight:400;color:#005EA7;display: inline; font-size:12px; margin-left:10px;" href="<%=request.getContextPath()%>/third/returnIndex.action">以后再说，立即去购物</a>
        </div>
      </div>
    </form>
  </div>
</div>
