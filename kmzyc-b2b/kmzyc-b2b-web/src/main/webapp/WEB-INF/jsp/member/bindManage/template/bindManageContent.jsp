<%@ page language="java" import="java.util.*,com.kmzyc.b2b.third.util.ThirdConstant,com.kmzyc.zkconfig.ConfigurationUtil"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--fn-left end-->

<div class="l-right user-m">
	
	<div class=""><img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/res/images/common/bd-bulletin.jpg"></div>

	<div class="o-mt">
		<h2>第三方平台账号绑定管理</h2>
	</div>
	<div class="binding-text">
		<p>在这里可以进行您的康美会员和其他第三方平台账号之间的绑定和解绑操作</p>
		<p style="color:red;font-size:12px;">
			<% 
				//success_out 20150825 为新加的,用于解绑成功提示
				String bindTip=(String)request.getAttribute("bindTip");
				if("Y_QQ".equals(bindTip)){
					out.println("您刚输入的QQ账号已和其它康美会员绑定,请您重新绑定!");
				}else if("Y_wx".equals(bindTip)){
					out.println("您刚输入的微信账号已和其它康美会员绑定,请您重新绑定!");
				}else if("Y_sina".equals(bindTip)){
					out.println("您刚输入的新浪账号已和其它康美会员绑定,请您重新绑定!");
				}else if("Y_alipay".equals(bindTip)){
					out.println("您刚输入的支付宝账号已和其它康美会员绑定,请您重新绑定!");
				}else if("Y_taobao".equals(bindTip)){
					out.println("您刚输入的淘宝账号已和其它康美会员绑定,请您重新绑定!");
				}else if("success_out".equals(bindTip)){  
				  	out.println("您已成功解绑!");
				}
			%>
		</p>
	</div>
	<div class="safe-content binding-list">

		<!-- 将已绑定的信息输出 -->
		<s:iterator value="#request.resultList" var="bindInfo">
			<ul>
				<li class="ico-logo">
					<s:if test="thirdAccountType=='01'">
						<!-- 换成QQ图标 -->
						<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u637_normal.png" width="80" height="60">
					 </s:if>
					 <s:if test="thirdAccountType=='02'">
						<!-- 换成微信图标 -->
						<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u656_normal.png" width="130" height="60">
					</s:if>
					<s:if test="thirdAccountType=='03'">
						<!-- 换成新浪图标 -->
						<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/sina.png" width="130" height="60">
					</s:if> 
					<s:if test="thirdAccountType=='04'">
						<!-- 换成支付宝图标 -->
						<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u649_normal.png" width="130" height="60">
					</s:if>
				 <s:if test="thirdAccountType=='05'">
						<!-- 换成淘宝图标 -->
					    <img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/taobao_logo.png" width="130" height="60"/>
				 </s:if>
				</li>
				<!-- 头像地址 -->
				<li class="first-text"><img src="<s:if test="null==extends2||extends2.isEmpty()"><%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/default-headpic.png</s:if><s:else><s:property value='extends2'/></s:else>" width="40" height="40" />
				<!-- 昵称 --><s:property value="extends1" /></li>
				<li class="modify"><a class="btn-submit" href="<%=request.getContextPath()%>/third/getOutOfBindForPcAndWap.action?thirdAcctType=<s:property value='thirdAccountType'/>&openId=<s:property value='openId'/>"><span>我要解绑</span></a></li>
			</ul>
		</s:iterator>


	 	<s:if test='#request.isBindQQ=="N"'>
			<ul>
				<li class="ico-logo"><img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u637_normal.png" width="80" height="60"></li>
				<li class="first-text">QQ暂未绑定</li>
				<li class="modify"><a class="btn-submit"
					href="<%=request.getContextPath()%>/third/qqIndex.action?<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y"><span>马上绑定</span></a></li>
			</ul>
		</s:if>
	 <!--  <s:if test='#request.isBindWx=="N"'>
			<ul>
				<li class="ico-logo">
				<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u656_normal.png" width="130" height="60">
				</li>
				<li class="first-text">微信暂未绑定</li>
				<li class="modify"><a class="btn-submit" href="javascript:void(0)"><span>马上绑定</span></a></li>
			</ul>
		</s:if>
		-->
		<s:if test='#request.isBindSina=="N"'>
			<ul>
				<li class="ico-logo">
	             <img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/sina.png" width="130" height="60">
	            </li>
				<li class="first-text">新浪暂未绑定</li>
				<li class="modify"><a class="btn-submit"
					href="<%=request.getContextPath()%>/third/sinaIndex.action?<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y"><span>马上绑定</span></a></li>
			</ul>
		</s:if>
		<s:if test='#request.isBindAlipay=="N"'>
			<ul>
				<li class="ico-logo">
				<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/u649_normal.png" width="130" height="60">
			</li>
				<li class="first-text">支付宝暂未绑定</li>
				<li class="modify"><a class="btn-submit"
					href="<%=request.getContextPath()%>/third/alipayIndex.action?<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y"><span>马上绑定</span></a></li>
			</ul>
		</s:if>
	 <s:if test='#request.isBindTb=="N"'>
			<ul>
				<li class="ico-logo">
				<img src="<%=ConfigurationUtil.getString("cssAndJsPath")%>res/images/taobao_logo.png" width="130" height="60">
				</li>
				<li class="first-text">淘宝暂未绑定</li>
				<li class="modify"><a class="btn-submit" href="<%=request.getContextPath()%>/third/taobaoIndex.action?<%=ThirdConstant.IS_COMEFROMBINDMANAGE%>=Y"><span>马上绑定</span></a></li>
			</ul>
		</s:if>
	</div>
</div>
</div>
