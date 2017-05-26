<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="orderinfo regfree l-w">
	<div class="orderinfo-hd fn-clear">
		<h1 class="fn-f18"><a href="javascript:void(0)">找回密码</a></h1>
	</div>
	<div class="Re-password-pages  fn-t10">
	  	<div class="procedure fn-t10 w306">
	   	 	<ul>
	      		<li class="green">1、填写用户名<b class="background-gl"></b></li>
	      		<li class="greyl">2、发送校验码<b class="background-ll"></b></li>
	      		<li class="greyl">3、根据校验码重设密码<b class="background-ll"></b></li>
                <li class="greyl">4、完成</li>
        	</ul>
      	</div>
      	<input id="msgCodeError" name="msgCodeError" type="hidden" value="${reFalg}" />
	  	<div class="email-con fn-l100">
	    	<form action="#" method="post" id="resetPwdForm">
			    <ul>
			      	<li>
			        	<div class="e-box">手机号： </div>
			        	<input name="txtloginAccount" id="txtloginAccount" type="text" class="i-text err-input" />
			        	<input name="loginAccount" id="hdloginAccount" type="hidden"/>
			        	<div id="accountBox" class="right-box" style="display: none;"><i></i></div>
		          	</li>
			      	<li>
						<div class="e-box">验证码： </div>
			         	<input name="veCode"  id="txtveCode"  type="text" class="i-text code" />
			        	<div class="yzm-wb" style="width: 180px;">
							<a href="javascript:void(0);" id="changeImgLink1">
								<img id="imageCode" style="float: left;padding-left: 2px;"  width="80"	height="30"  src="/codeImage.action?imgKey=regImg"/>
							</a>
							<a class="fn-blue" href="javascript:void(0)" id="changeImgLink2">&nbsp;看不清？换一张</a>
						</div>
						<div id="txtveCodeBox" class="right-box" style="display:none;"><i></i></div>
		          	</li>
			      	<li>
			        	<div class="e-box">&nbsp;</div>
			        	<strong class="fn-green"><a href="javascript:void(0);" id="btnSubInit" class="btn-submit"><span>提交</span></a></strong>
					</li>
				</ul>
        	</form>
      	</div>
  	</div>
</div>