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
		      	<li class="black">2、发送校验码<b class="background-dd"></b></li>
		      	<li class="black">3、根据校验码重设密码<b class="background-dg"></b></li>
          		<li class="green">4、完成</li>
        	</ul>
      	</div>
	  	<div class="tips-green fn-t10">
	    	<div class="i-modify fn-t10 fn-l200 ">
        		<s:actionerror id="errorDiv" />
        		<div id="resultDiv">
	        		<s:if test="#request.info == 'success'">
	          		<div class="tips"><i></i>密码重置成功！请到<a href="/html/loginPage.htm">登录</a>页面使用您设置的新密码登录。</div>
	          		</s:if>
	          		<s:elseif test="#request.info == 'fail'"><div class="tips">密码重置失败，请稍后再试！</div></s:elseif>
          		</div>
        	</div>
      	</div>
  	</div>
</div>