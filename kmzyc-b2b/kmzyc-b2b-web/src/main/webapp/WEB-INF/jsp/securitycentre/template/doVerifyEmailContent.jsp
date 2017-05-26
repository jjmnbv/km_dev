<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="l-right user-m">
<div class="o-mt"><s:if test="#request.isFile == 1">
	<h2>验证邮箱</h2>
</s:if> <s:if test="#request.isFile == 2">
	<h2>修改邮箱</h2>
</s:if> <s:if test="#request.isFile == 0">
	<h2>验证提示</h2>
</s:if> <s:if test="#request.isFile == 3">
	<h2>验证提示</h2>
</s:if></div>
<s:if test="#request.isFile == 1">
	<div class="change-p">
	<div class="i-modify fn-t10 tips-green">
	<ul>
		<li>
		<div class="tips"><i></i>恭喜，邮箱验证成功</div>
		</li>
		<li><i class="point"><img
			src="" id="hotsale_loadingimg"/></i><span>您验证的邮箱为:</span><strong>${verifEmailInfo.emailAddress}</strong></li>
		<li><i class="point"><img
			src="" id="browsingHis_loadingimg"/></i><span>邮箱可用来登录、找回密码、订购产品等</span></li>
		<li class="success-btn"><span
			class="text"><a href="showSecurityCentre.action">返回安全中心</a></span></li>
	</ul>
	</div>
	</div>

</s:if> <s:if test="#request.isFile == 2">
	<div class="change-p">
	<div class="i-modify fn-t10 tips-green">
	<ul>
		<li>
		<div class="tips"><i></i>恭喜，邮箱修改成功</div>
		</li>
		<li><i class="point"><img
			src="" id="hotsale_loadingimg"></i><span>您现在的邮箱为:</span><strong>${verifEmailInfo.emailAddress}</strong></li>
		<li><i class="point"><img
			src="" id="browsingHis_loadingimg"></i><span>邮箱可用来登录、找回密码、订购产品等</span></li>
		<li class="success-btn"><span
			class="text"><a href="showSecurityCentre.action">返回安全中心</a></span></li>
	</ul>
	</div>
	</div>
</s:if> <s:if test="#request.isFile == 0">
	<div class="i-user-tipbox">
	            <div class="ui-tipbox ui-tipbox-warning fn-t20">
				    <div class="ui-tipbox-icon">
				    </div>
				    <div class="ui-tipbox-content">
				        <h3 class="ui-tipbox-title">对不起，邮箱验证失败</h3>
				        <dl>
				        	<dt><strong>可能原因:</strong></dt>
				        	<dd>1、您已经验证了</dd>
				        	<dd>2、验证链接超过了24小时</dd>
				        	<dd>3、不是同一用户登录</dd>
				        	<dd>4、邮箱已被占用</dd>
				        </dl>
				        <p class="ui-tipbox-explain"><a href="showSecurityCentre.action">重新验证</a></p>
				    </div>
				</div>
            </div>
</s:if> <s:if test="#request.isFile == 3">
	<div class="i-user-tipbox">
	            <div class="ui-tipbox ui-tipbox-warning fn-t20">
				    <div class="ui-tipbox-icon">
				    </div>
				    <div class="ui-tipbox-content">
				        <h3 class="ui-tipbox-title">对不起，邮箱验证失败</h3>
				        <dl>
				        	<dt><strong>可能原因:</strong></dt>
				        	<dd>1、您已经验证了</dd>
				        	<dd>2、验证链接超过了24小时</dd>
				        	<dd>3、不是同一用户登录</dd>
				        	<dd>4、邮箱已被占用</dd>
				        </dl>
				        <p class="ui-tipbox-explain"><a href="showSecurityCentre.action">重新验证</a></p>
				    </div>
				</div>
            </div>
</s:if></div>
</div>