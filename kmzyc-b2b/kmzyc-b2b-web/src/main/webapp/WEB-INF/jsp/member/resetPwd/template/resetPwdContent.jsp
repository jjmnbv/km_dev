<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="orderinfo regfree l-w">
	<div class="orderinfo-hd fn-clear">
		<h1 class="fn-f18"><a href="javascript:void(0)">找回密码</a></h1>
	</div>
	<div class="Re-password-pages fn-t10">
	  	<div class="procedure fn-t10 w306">
	    	<ul>
	      		<li class="black">1、填写用户名<b class="background-dd"></b></li>
	      		<li class="black">2、发送校验码<b class="background-dg"></b></li>
	      		<li class="green">3、根据校验码重设密码<b class="background-gl"></b></li>
                <li class="greyl">4、完成</li>
        	</ul>
      	</div>
	  	<div class="fn-l100">
       		<div class="email-con">
       			<div>
       				<form action="#" method="post" id="resetPwdForm" name="resetPwdForm">
       				<s:token />
            		<ul>
                        <li>
                          	<div class="e-box">找回密码用户名： </div>
                          	<strong class="fn-green">${loginAccount}</strong>
                          	<input type="hidden" name="ckUser.loginAccount" value="${loginAccount}" id="txtloginAccount" />
                        </li>
                        <li>
                          	<div class="e-box">设置新密码： </div>
                          	<div class="fn-left">
	                          	<p>
	                        		<input name="ckUser.loginPassword" id="txtPassword" type="password" class="email-p">
	                        	</p>
	                        	<div class="letter fn-left" style="display: none;" id="passBox">
		                            <span id="level1">弱</span>
							      	<span id="level2" >中</span>
							      	<span id="level3" >强</span>
	                         	</div>
	                      		<p style="line-height: 1px; height: 15px;"></p>
	                   			<div class="right-box" id="txtPasswordDiv" style="color:#fd3301;display: none;">
	                        		<p class="fn-gray">密码长度在8-20字符之间，不能用纯数字/英文字母！</p>
	                        	</div>
                          	</div>
                        </li>
                     	<li>
                          	<div class="e-box">确认密码： </div>
                          	<input name="txtRePassword" id="txtRePassword" type="password" class="email-p">
                          	<div id="txtRePasswordDiv" style="color:#fd3301;display: none;"></div>
                        </li>
                        <li>
                          	<div class="e-box">&nbsp;</div>
                          	<strong class="fn-green"><a href="javascript:void(0)" id="btnSubReset" class="btn-submit"><span>确定</span></a></strong></li>
                      </ul>
                      </form>
                  </div>
              </div>
      	</div>
  	</div>
</div>