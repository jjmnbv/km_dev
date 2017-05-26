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
		      	<li class="black">1、填写买家信息<b class="background-dg"></b></li>
		      	<li class="green">2、验证身份<b class="background-gl"></b></li>
		      	<li class="greyl">3、设置用户名和密码<b class="background-ll"></b></li>
           		<li class="greyl">4、完成</li>
       	 	</ul>
      	</div>
	  	<div class="fn-l100">
	  		<s:if test="#request.ckUser !=null && #request.ckUser.loginId !=null">
         	<div class="email-con">
             	<div>
          			<ul>
                        <li>
                          	<div class="e-box">邮箱地址： </div>
                          	<strong class="fn-green">${ckUser.email}</strong>
                      	</li>
                      	<li>
                          	<div class="e-box">手机号码： </div>
                          	<strong class="fn-green">${ckUser.mobile}</strong>
                        </li>
                        <li>
	              			<div class="e-box">请选择验证身份的方式： </div>
	                  		<select name="sendMethod" class="select" id="sendMethod">
	                          	<s:if test="#request.ckUser.email !=null">
				            	<option value="email">邮箱</option>
				            	</s:if>
				            	<s:if test="#request.ckUser.mobile !=null">
				            	<option value="mobile">手机</option>
				            	</s:if>
	                        </select>
		             		<input id="txtloginAccount" value="${ckUser.loginAccount}" type="hidden"/>
						</li>
						<li id="liMailBox" style="display: none;">
                   		</li>
                        <li id="liMail">
                          	<div class="e-box">&nbsp;</div>
                        	<strong class="fn-green">
                        		<a href="javascript:void(0);" id="linkMail" class="btn-submit"><span>发送验证邮箱</span></a>
                        	</strong>
                   		</li>
               		</ul>
                	<ul id="ulMobile" style="<s:if test="#request.ckUser.email !=null">display:none;</s:if>">
                 		<li>
                 			<div class="p-btn" style="margin-left:150px;">
                              	<a class="p-btn-sub" href="javascript:void(0);" id="linkMoblie" >
                                	<span>点击获取短信校验码</span>
                              	</a>
                           	</div>
                 		</li>
                 		<li id="ulMobileBox" style="margin-bottom: 0px;display: none;">
                          	<div class="e-box">&nbsp;</div>
                        </li>
                        <li>
                          	<div class="e-box">
                          		<label id="lbcode">手机校验码：</label>
                          	 </div>
                          	<input name="msgCode" id="msgCode" type="text" class="i-text code">
                          	<div id="msgCodeBox" class="right-box" style="display:none;"><i></i></div>
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
                          	<strong class="fn-green">
                          		<a href="javascript:void(0)" id="btnSubSendValid" class="btn-submit"><span>下一步</span></a>
                         	</strong>
                      	</li>
               		</ul>
           		</div>
       		</div>
       		</s:if>
       		<s:elseif test="#request.ckUser !=null && #request.ckUser.mobile !=null">
        	<div class="i-modify fn-t10 fn-l200" style="padding-left: 0px;">
			  <div class="tips">此邮箱/手机已经被注册，请登录后再进行结算，如果您忘记了登录密码，请<a href="/member/resetPwd.action">找回密码</a>。</div>
			</div>
       		</s:elseif>
       		<s:else>
       		<div class="i-modify fn-t10 fn-l200" style="padding-left: 0px;">
			  <div class="tips">输入的信息有误，请<a href="/member/resetPwdgoUnRegMember.action">重新填写</a>！</div>
			</div>	
       		</s:else>
      	</div>
  	</div>
</div>
