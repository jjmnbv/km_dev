<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

  <div class="container">
            <!--账户管理-->
            <div class="mian-box">
                <!--未设置支付密码-->
              <ul class="navlist">
              <li><a href="/settlement/consigneeInfo.action?&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE65C"></i>收货地址<span class="icon-uniE61F"></span></a></li>
               <s:if test='#session.isFanliUser!=true'>
               <input type="hidden" id="mobVa" value="${securityCentreInfo.mobileValidate}" />
              <li>       
                     <!-- 只有是已完善信息的会员才可以修改密码 -->                   
                      <s:if test='#session.isTempMember=="Y"'>
                        	<a href="javascript:void(0)"  id="neddFillInfo_changePwd">
	                            <i class="icon-uniE65B"></i>修改登录密码 <span class="icon-uniE61F"></span></i>
                          	</a>
                     </s:if>
                     <s:else>
                     	<a href="/member/goWapChangePassword.action?&_r=<%=System.currentTimeMillis()%>"> <i class="icon-uniE65B"></i>修改登录密码 <span class="icon-uniE61F"></span></a>
                     </s:else>
               </li>                   
               <li><s:if test='#session.isTempMember=="Y"'>
                        	<a href="javascript:void(0)"  id="neddFillInfo_validPhone">
	                            	<i class="icon-uniE65A"></i>手机验证<span class="icon-uniE61F"></span>
                          	</a>
	                     </s:if>
	                     <s:else><s:if test="#request.securityCentreInfo.mobileValidate==true"><a href="/member/goWapMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=true&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE65A"></i></s:if><s:else><a href="/member/goWapMobileVerifyPassword.action?_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE65A"></i></s:else>手机验证(<s:if test="#request.securityCentreInfo.mobileValidate==true">已验证
			                    </s:if><s:else>未验证</s:else>)<span class="icon-uniE61F"></span>
	                        </a>
	                     </s:else>                                                             
                  </li>
                  <li>
           <s:if test="#request.securityCentreInfo.payPasswordInvocation==false">
				<!-- 已经通过手机验证 -->
				<s:if test="#request.securityCentreInfo.mobileValidate==true">
				<%-- <a href="/member/goWapPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=true&_r=<%=System.currentTimeMillis()%>">用户支付密码(立即启用)<i class="crow-arrow icon-angle-right"></i></a> --%>
				<a href="javascript:void(0);" id="j_gotoTelValidate"><i class="icon-uniE659"></i>用户支付密码(立即启用)<span class="icon-uniE61F"></span></a>
				</s:if>
				<!-- 未通过手机验证 -->
				<s:if test="#request.securityCentreInfo.mobileValidate==false">
					<!-- 屏蔽掉第三方账号登录进来的需要完善信息的正式会员(这里的需要完善信息的正式会员时没有密码的) 20140418 add by maliqun-->
					 <s:if test='#session.isTempMember=="Y"'>
                        	<a href="javascript:void(0)"  id="neddFillInfo_payPwd">
	                            	<i class="icon-uniE659"></i>用户支付密码（立即启用）<span class="icon-uniE61F"></span>
                          	</a>
	                 </s:if>
				 	<s:else>
						<a href="javascript:void(0);" id="j_gotoTelValidate"><i class="icon-uniE659"></i>用户支付密码(立即启用)<span class="icon-uniE61F"></span></a>
					</s:else>
				
				</s:if>
		</s:if>
         <s:else>
				<!-- 已经启用支付密码  -->
				<a href="/member/goWapPayVerifyPassword.action?changePayPasswordInfo.modifyStatus=true&_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE659"></i>用户支付密码(修改支付密码)<span class="icon-uniE61F"></a>
	    </s:else>  	
          </li>
       <s:if test="#request.securityCentreInfo.payPasswordInvocation!=false">
         <li>
	                    	 <s:if test='#session.isTempMember=="Y"'>
	                        	<a href="javascript:void(0)"  id="neddFillInfo_payManage">
		                            	<i class="icon-uniE658"></i>支付管理<span class="icon-uniE61F"></span>
	                          	</a>
		                     </s:if>
		                     <s:else>
		                     	 <a href="/member/goWapPaySetting.action?_r=<%=System.currentTimeMillis()%>"><i class="icon-uniE658"></i>支付管理<span class="icon-uniE61F"></span></a>
		                     </s:else>                   	
          </li>
      </s:if>
      <li><!-- 只有是已完善信息的会员才可以进入绑定管理 -->                   
                      <s:if test='#session.isTempMember=="Y"'>
                        	<a href="javascript:void(0)"  id="neddFillInfo_bind">
	                            	<i class="icon-uniE656"></i>绑定管理<span class="icon-uniE61F"></span>
                          	</a>
                     </s:if>
                     <s:else>
                     	 <a href="<%=request.getContextPath()%>/third/toBindManage.action?isWap=Y&_r=<%=System.currentTimeMillis()%>">
	                            	<i class="icon-uniE656"></i>绑定管理<span class="icon-uniE61F"></span>
                          </a>
                     </s:else>
		</li>
		</s:if>
        </ul>

            </div>
        </div>

