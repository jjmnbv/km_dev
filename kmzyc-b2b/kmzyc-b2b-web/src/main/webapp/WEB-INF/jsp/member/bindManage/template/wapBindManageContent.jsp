<%@ page language="java" import="java.util.*,com.kmzyc.b2b.third.util.ThirdConstant"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--fn-left end-->


<!-- 20150811 wap改版update -->
<!--内容区域 begin-->
<div class="container">
    <div class="panel-body text-center bg-success">
        <div class="text-muted">
            <i class="fa icon-uniE64E"></i>
        </div>
        <h2 class="text-strong">绑定管理</h2>
         <p style="color:red;font-size:12px;">
			<% 
				//success_out 20150825 为新加的,用于解绑成功提示
				String bindTip=(String)request.getAttribute("bindTip");
				if("Y_QQ".equals(bindTip)){
					out.println("<p id='errorTip' style='color:red;'>您刚输入的QQ账号已和其它康美会员绑定,请您重新绑定!</p>");
				}else if("Y_wx".equals(bindTip)){
					out.println("<p id='errorTip' style='color:red;'>您刚输入的微信账号已和其它康美会员绑定,请您重新绑定!</p>");
				}else if("Y_sina".equals(bindTip)){
					out.println("<p id='errorTip' style='color:red;'>您刚输入的新浪账号已和其它康美会员绑定,请您重新绑定!</p>");
				}else if("Y_alipay".equals(bindTip)){
					out.println("<p id='errorTip' style='color:red;'>您刚输入的支付宝账号已和其它康美会员绑定,请您重新绑定!</p>");
				}else if("Y_taobao".equals(bindTip)){
					out.println("<p id='errorTip' style='color:red;'>您刚输入的淘宝账号已和其它康美会员绑定,请您重新绑定!</p>");
				}else if("success_out".equals(bindTip)){  
				  	out.println("<p id='errorTip' style='color:red;'>您已成功解绑!</p>");
				}
			%>
		</p>
        
    </div>
    <ul class="binding-list">
    	<!-- 将已绑定的信息输出  begin -->
		<s:iterator value="#request.resultList" var="bindInfo">
								<!-- qq解绑 -->
								<s:if test="thirdAccountType=='01'">
								 <li>
						            <span>QQ</span>
						            <span class="fa icon-uniE652"></span>
						            <button class="btn btn-danger" type="button"><a href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?isWap=Y&thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>">我要解绑</a></button>
						        </li>
								</s:if>
								
								<!-- 微信解绑 -->
								<s:if test="thirdAccountType=='02'">
									<li>
							            <span>微信</span>
							            <span class="fa icon-uniE655"></span>
							            <button class="btn btn-danger" type="button"><a href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?isWap=Y&thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>">我要解绑</a></button>
							        </li>
								</s:if>
								
								<!-- 新浪解绑 -->
								<s:if test="thirdAccountType=='03'">
									<li>
							            <span>新浪微博</span>
							            <span class="fa icon-uniE653"></span>
							            <button class="btn btn-danger" type="button"><a href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?isWap=Y&thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>">我要解绑</a></button>
							        </li>
								</s:if> 
								
								<!-- 支付宝解绑 -->
								<s:if test="thirdAccountType=='04'">
								<li>
						            <span>支付宝</span>
						            <span class="fa icon-uniE654"></span>						      
									<button class="btn btn-danger" type="button"><a href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?isWap=Y&thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>">我要解绑</a></button>
								</li>
								</s:if>
								
								
								<!-- 淘宝解绑 -->
								
								<s:if test="thirdAccountType=='05'">
									<li>
							            <span>淘宝</span>
							            <span class="fa icon-uniE651"></span>
										<button class="btn btn-danger" type="button"><a href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?isWap=Y&thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>">我要解绑</a></button>
							    	</li>
							    </s:if>
						</s:iterator>
						<!-- 已绑定信息输出end -->
						
						
		<!-- 未做绑定信息显示 begin -->	
				<!-- QQ未绑定 -->
				<s:if test='#request.isBindQQ=="N"'>
						 <li>
						 <span>QQ</span>
           				 <span class="fa icon-uniE652"></span>
                         <button class="btn btn-success" type="button"><a class="btn-submit" href="<%=request.getContextPath()%>/third/qqIndex.action?isWap=Y&<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y">我要绑定</a></button>
                        </li>
				</s:if>
				<!-- 微信未绑定 
			 	<s:if test='#request.isBindWx=="N"'>
					 <li>
					 	<span>微信</span>
            			<span class="fa icon-uniE655"></span>
                        <button class="btn btn-success" type="button"><a class="btn-submit" href="<%=request.getContextPath()%>/third/wxIndexForWap.action?isWap=Y&<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y">我要绑定</a></button>
                        </li>
				</s:if>
				-->
		
				<!-- 新浪未绑定 -->
			    <!--<s:if test='#request.isBindSina=="N"'>
					 <li>
					 	<span>新浪微博</span>
            		<span class="fa icon-uniE653"></span>
                            <button class="btn btn-success" type="button"><a class="btn-submit" href="<%=request.getContextPath()%>/third/sinaIndex.action?isWap=Y&<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y">我要绑定</a></button>
                        </li>
				</s:if>
				<!-- 支付宝未绑定 -->
			<!--  <s:if test='#request.isBindAlipay=="N"'>
					 <li>
					 <span>支付宝</span>
           			 <span class="fa icon-uniE654"></span>
                    <button class="btn btn-success" type="button"><a class="btn-submit" href="<%=request.getContextPath()%>/third/alipayIndex.action?isWap=Y&<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y">我要绑定</a></button>
                     </li>
				</s:if>				
				<!-- taobao未绑定 -->
				 <s:if test='#request.isBindTb=="N"'>
				 	<li>
				 	<span>淘宝</span>
            		<span class="fa icon-uniE651"></span>
                    <button class="btn btn-success" type="button"><a class="btn-submit" href="<%=request.getContextPath()%>/third/taobaoIndex.action?isWap=Y&<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y">我要绑定</a></button>
                    </li>
				 </s:if>			
				<!-- 未做绑定信息显示 end -->	
        <li>
            <span>...</span>
            <span>其他</span>
        </li>
        
        
        
        
    </ul>
</div>
<!--内容区域 end-->


