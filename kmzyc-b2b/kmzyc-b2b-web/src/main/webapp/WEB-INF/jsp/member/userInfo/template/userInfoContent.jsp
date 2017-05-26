<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>个人信息</h2>
	</div>
	<div class="user-m fn-t10">
		<div class="mc fn-clear">
			<div class="i-m i-m-w">
				<div class="i-mc">

					<s:form action="updateUserInfo.action" method="Post"
						id="userInfoForm" name="form1">
						<s:hidden name="userInfo.loginId" value="%{userInfo.loginId}" />
						<s:hidden name="userInfo.personalId"
							value="%{userInfo.personalId}" />
						<s:hidden name="userInfo.personalityId"
							value="%{userInfo.personalityId}" />
						<s:hidden name="userInfo.healthYgenericId"
							value="%{userInfo.healthYgenericId}" />
						<div class="form">
							<div class="item">
								<span class="label">会员账号：</span>
								<s:property value="userInfo.accountLogin" />
								&nbsp;&nbsp;&nbsp;
							</div>
							<!-- 返利 -->
							<s:if test='#session.isFanliUser!=true'>
							<%--<div class="item">
								<span class="label">邮箱：</span> <span> <s:property
										value="userInfo.email" /></span>&nbsp;
								<s:if test="loginInfo.emailStatus==0">
									<s:if test='#session.isTempMember=="Y"'>
										<span id="skipSpan_email">
											<a id="email_validate" href="javascript:void(0)" disabled='true'
											class="fn-blue"> 点击邮箱验证?</a>
										</span>
									</s:if>
									<s:else>
										<span><a
											href="goEmailVerifyPassword.action?verifEmailInfo.modifyStatus=false"
											class="fn-blue">点击邮箱验证?</a></span>
									</s:else>
							</s:if>
								<s:elseif test="loginInfo.emailStatus==1">
									<span>[已验证]</span>
								</s:elseif>
							</div>--%>
							<div class="item">
								<span class="label">手机：</span> <span><s:property
										value="userInfo.mobile" /></span>&nbsp;
								<s:if test="loginInfo.mobileStatus==0">
									<s:if test='#session.isTempMember=="Y"'>
										<span id="skipSpan_mobile">
											<a id="email_validate" href="javascript:void(0)" disabled='true'
											class="fn-blue"> 点击手机验证?</a>
										</span>
									</s:if>
									<s:else>
									<span><a
										href="goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=false"
										class="fn-blue">点击手机验证?</a></span>
									</s:else>
								</s:if>
								<s:elseif test="loginInfo.mobileStatus==1">
									<span>[已验证]</span>
								</s:elseif>
							</div>
						</s:if>
						<!--  -->
							<div class="item">
								<span class="label">昵称：</span>
								<s:textfield cssClass="u-text" name="userInfo.nickName"
									value="%{userInfo.nickName}" maxLength="16"></s:textfield>
							</div>
							<div class="item">
								<span class="label">真实姓名：</span>
								<s:textfield cssClass="u-text" name="userInfo.name"
									value="%{userInfo.name}" maxlength="15"></s:textfield>

							</div>
							<div class="item">
								<span class="label">性别：</span>
								<s:radio list="#{'0':'男','1':'女'}" name="userInfo.sex"
									value="userInfo.sex"></s:radio>
							</div>

							<div class="item">
								<span class="label"><em>*</em>生日：</span>
								<div class="fl">
									<input type="text" id="birthday" name="userInfo.birthday"
										value="<s:date name='%{userInfo.birthday}' format='yyyy-MM-dd'/>"
										readonly="readonly" class="u-text-date  j_birthday" /> <span
										class="fn-red"></span>
								</div>
							</div>
							<div class="item">
								<span class="label"><em>*</em>所在地：</span>
								<div class="fl">
									<select class="sele" id="province"
										data-value="<s:property value="userInfo.province"/>"
										name="userInfo.province"></select> <select class="sele"
										id="city" data-value="<s:property value="userInfo.city"/>"
										name="userInfo.city"></select> <select class="sele" id="area"
										data-value="<s:property value="userInfo.area"/>"
										name="userInfo.area"></select>
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label"><em>*</em>你的地址：</span>
								<s:textfield cssClass="Complaints-text"
									name="userInfo.detailedaddress"
									value="%{userInfo.detailedaddress}" maxlength="80"></s:textfield>
							</div>
							<div class="item">
								<span class="label">身份：</span>
								<div class="fn-left">
									<s:radio
										list="#{'学生':'学生','在职':'在职','自由职业':'自由职业','家庭主妇':'家庭主妇','退休':'退休'}"
										name="userInfo.professionType" value="userInfo.professionType"></s:radio>
									<input type="radio" value="0" style="display: none">
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label">婚姻状况：</span>
								<div class="fn-left">
									<s:radio list="#{'0':'未婚','1':'已婚','2':'保密'}"
										name="userInfo.maritalStatus" value="userInfo.maritalStatus"></s:radio>
									<input type="radio" value="0" style="display: none">

								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label">居住状态：</span>
								<div class="fn-left">
									<s:radio list="#{'0':'保密','1':'和伴侣','2':'和室友','3':'和父母'}"
										name="userInfo.liveStatus" value="userInfo.liveStatus"></s:radio>
								</div>
							</div>
						</div>
					</s:form>

				</div>
			</div>
		</div>
		<div class="button">
			<div>
				<a href="javascript:void(0)" class="btn-submit j_btn-submit"> <span>提交</span></a>
			</div>
		</div>
	</div>
</div>
</div>

