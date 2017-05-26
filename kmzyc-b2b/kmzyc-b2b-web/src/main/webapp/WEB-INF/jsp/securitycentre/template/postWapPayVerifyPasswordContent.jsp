<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--内容区域-->
<div class="container">
    <ul class="steps">
        <li>
            <span class="step"><i class="icon-uniE650"></i></span>
            <span class="title">身份验证</span>
        </li>
        <li class="active">
            <span class="step"><i class="icon-uniE64F"></i></span>
            <span class="title">设置支付密码</span>
        </li>
        <li>
            <span class="step">OK</span>
            <span class="title">完成</span>
        </li>
    </ul>
    <div class="form-horizontal">
    <form id="postVerifyEmail" action="postWapModifyPayPassword.action" method="post">
      <input type="hidden" name="safeLevel" id="safeLevel" value=""> 
	  <input type="hidden" name="changePayPasswordInfo.modifyStatus" id="modifyStatus" value="${changePayPasswordInfo.modifyStatus}">
        <div class="form-group">
            <input name="changePayPasswordInfo.payPassword" type="password"  class="form-control input-lg" placeholder="设置支付密码">
            <div class="help-block">
            <p id="newPassworderrdiv" style="display: none;" class="text-danger">密码长度在8-20字符之间，不能用纯数字/英文字母！</p>
			<p id="newPassworderrdiv3" style="display: none;" class="text-danger">密码不能为空或全空格!</p>
			<p id="newPassworderrdiv1" style="display: none;" class="text-danger">支付密码不能和登录密码相同，请重新输入</p>
			<p id="newPassworderrdiv2" style="display: none;" class="text-danger">新密码不能和原密码相同，请重新输入</p>
            </div>
        </div>
        <div class="form-group">
            <input type="password" name="changePayPasswordInfo.verifyPayPassword"  placeholder="确认支付密码" class="form-control input-lg tooltips" data-original-title="" title="">
            <div class="help-block">
               	<p id="verifyNewPassworderrdiv" style="display: none;" class="text-danger">两次输入的密码不一致</p>
            </div>
        </div>
        <div class="form-group">
         <a href="javascript:void(0);" class="btn btn-success btn-block"  id="submit">提交</a>
        </div>
        </form>
    </div>
</div>
<!--内容区域 end-->

   