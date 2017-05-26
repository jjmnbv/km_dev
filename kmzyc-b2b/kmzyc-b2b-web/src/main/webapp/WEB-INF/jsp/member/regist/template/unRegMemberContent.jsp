<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="orderinfo regfree l-w">
	<div class="orderinfo-hd fn-clear">
		<h1 class="fn-f18"><a href="javascript:void(0)">免注册购物转为会员</a></h1>
	</div>
	<div class="Re-password-pages fn-t10">
	  	<div class="procedure fn-t10 w306">
	    	<ul>
		      	<li class="green">1、填写买家信息<b class="background-gl"></b></li>
		      	<li class="greyl">2、验证身份<b class="background-ll"></b></li>
		      	<li class="greyl">3、设置用户名和密码<b class="background-ll"></b></li>
                <li class="greyl">4、完成</li>
        	</ul>
     	</div>
	  	<div class="email-con fn-l100">
	  		<form action="#" id="regInfoForm" name="regInfoForm" method="post">
     		<ul>         
        		<li>
                   <div class="e-box">电子邮箱：</div>
                   <div class="fn-left">
                   		<input name="ckUser.email" value="${ckUser.email}" id="txtemail" type="text" class="email-p">
                      	<p>请输入您订单收货信息输入的电子邮箱地址</p>
                   </div>
                   <div id="txtemailBox" class="right-box" style="display:none;color: #fd3301;"></div>
             	</li>
                <li>
                   <div class="e-box">手机号码：</div>
                   <div class="fn-left">
                   		<input name="ckUser.mobile" value="${ckUser.mobile }" id="txtmobile" type="text" class="email-p">
                      	<p>请输入您订单收货信息输入的手机号码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                   </div>
                   <div id="txtmobileBox" class="right-box" style="display:none;color: #fd3301;"></div>
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
                	<div class="reg-text fn-l100">
    					<span class="fn-l60">
    						 <input name="accept" type="checkbox" id="ckaccept" value="radio" checked="checked" />
    						我已阅读并接受
    						<span class="green" style="background:none;">
    							<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>/helps/help-member-agreement.shtml" target="_blank">《用户协议》</a>
    						</span>
    					</span>
    				</div>
    			</li>
        	</ul>
        	</form>
     	</div>
   		<div class="button fn-pl430">
   			<a href="javascript:void(0);" id="btnSubRegInfo" class="btn-submit"><span>下一步</span></a>
   		</div>
  	</div>
</div>
