<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="orderinfo regfree l-w">
	<div class="orderinfo-hd fn-clear">
		<h1 class="fn-f18"><a href="javascript:void(0)">免注册购物转为会员</a></h1>
	</div>
	<div class="Re-password-pages  fn-t10">
		<div class="procedure fn-t10 w306">
	    	<ul>
		      	<li class="black">1、填写买家信息<b class="background-dd"></b></li>
		      	<li class="black">2、验证身份<b class="background-dg"></b></li>
		      	<li class="green">3、设置用户名和密码<b class="background-gl"></b></li>
	          	<li class="greyl">4、完成</li>
	       	</ul>
		</div>
	  	<div class="email-con fn-l100">
	  		<s:if test="#request.ckUser != null && #request.ckUser.email!=null">
	  		<form action="#" id="setugInfoForm" name="setugInfoForm" method="post">
	  		<s:token />
			<ul>
	     		<li>
	           		<div class="e-box">电子邮箱： </div>
	         		<strong class="fn-green">${ckUser.email}</strong>
	        	</li>
	         	<li>
	           		<div class="e-box">手机号码： </div>
	              	<strong class="fn-green">${ckUser.mobile}</strong>
	          	</li>
	         	<li>
	       			<div class="e-box">请设置您的用户名： </div>
	        		<div class=" fn-left">
            			<input name="txtloginAccount" id="txtloginAccount" type="text" class="email-p">
            			<input name="ckUser.loginAccount" id="hdloginAccount" type="hidden">
            			<span id="txtloginAccountBox" style="color:#fd3301;"></span>
	            		<p class="fn-gray">6-20个字符，一个汉字为两个字符，推荐使用中文用户名。</p>
	           		</div>
	 			</li>
	         	<li>
	           		<div class="e-box">设置新密码： </div>
	          		<div class="fn-left">
	             		<input name="ckUser.loginPassword" id="txtloginPassword" type="password" class="email-p">
	               		<div class="letter fn-left" id="passBox" style="display: none;">
	                    	<span id="level1">弱</span>
					      	<span id="level2" >中</span>
					      	<span id="level3" >强</span>
	                	</div>
	                	<p class="fn-gray">6-20个字符，请使用字母加数字或字符的组合密码。</p>
	         		</div>
				</li>
	     		<li>
	           		<div class="e-box">确认密码： </div>
	            	<input name="reloginPassword" id="txtreloginPassword" type="password" class="email-p">
	            	<div id="txtRePasswordDiv" style="color:#fd3301;display: none;"></div>
	    		</li>
	  		</ul>
	  		</form>
	  		</s:if>
	  		<s:else>
			<div class="i-modify fn-t10 fn-l200">
				  <div class="tips">输入的信息有误，请确认您的邮箱地址和手机号码！</div>
			</div>
			</s:else>
	    </div>
	    <s:if test="#request.ckUser != null && #request.ckUser.email!=null">
		<div class="button  fn-pl430">
			<a href="javascript:void(0);" id="btnSubunRegMember" class="btn-submit"><span>下一步</span></a>
		</div>
		</s:if>
	</div>
</div>