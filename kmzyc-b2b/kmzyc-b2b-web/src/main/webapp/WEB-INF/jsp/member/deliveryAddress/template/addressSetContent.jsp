<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="l-right user-m">
	<div class="o-mt">
		<h2>收货地址</h2>
	</div>
	<div class="user-m fn-t10">
		<div class="mc">
			<div class="i-m i-m-w">
				<div class="i-mc">
				
					<s:form action="editAction.action" method="Post" id="addressForm" AUTOCOMPLETE="off">
                        <s:token/>          
						<s:hidden name="address.accountId" value="%{address.accountId}" />
						<s:hidden name="address.addressId" id="addId" value="%{address.addressId}" />
						<div class="form">
							<div class="item">
								<span class="label" style="red"><em>*</em>姓名：</span>
								<input type="text" class="u-text" name="address.name" value="<s:property value="address.name"/>" maxlength="15"/>
							</div> 
							<div class="item">
								<span class="label"><em>*</em>手机号：</span>
								<s:textfield name="address.cellphone" cssClass="u-text"
									value="%{address.cellphone}" maxlength="11"></s:textfield>
							</div>
							<div class="item">
								<span class="label">固定电话：</span>
								<s:textfield name="address.telephone" cssClass="u-text"
									value="%{address.telephone}" maxlength="20"></s:textfield>
							</div>
							<div class="item">
								<span class="label"><em>*</em>地区：</span>
								<div>
								<select class="sele" id="province" data-value="<s:property value="address.province"/>" name="address.province" ></select> 
								<select class="sele" id="city" data-value="<s:property value="address.city"/>" name="address.city"></select> 
								<select class="sele" id="area" data-value="<s:property value="address.area"/>" name="address.area" ></select>		
								</div>
								<div class="clr"></div>
							</div>
							<div class="item">
								<span class="label"><em>*</em>详细收货地址： </span>
								<input type="text" class="Complaints-text" name="address.detailedAddress" value="<s:property value="address.detailedAddress"/>" maxlength="60"/>
							</div>
							<div class="item">
								<span class="label"><em>*</em>邮政编码：</span>
								<s:textfield name="address.postalcode" cssClass="u-text"
									value="%{address.postalcode}" maxlength="6"></s:textfield>
							</div>
							<div class="item">
								<div class="label"></div>
								<s:if test="address.status==0">
								     <s:checkbox name="status" value="true">
									<label>默认为收货地址</label>
								</s:checkbox>
								</s:if>
								<s:else>
									<s:checkbox name="status" value="false">
									<label>默认为收货地址</label>
								</s:checkbox>
								</s:else>
							</div>
							<div class="button">
								<div>
									<a id="j_btn_submit" class="btn-submit" disable=""><span>提交</span></a>&nbsp&nbsp&nbsp&nbsp&nbsp
									<a href="goDeliveryAddress.action" class="btn-cancel"><span>取消</span></a>
								</div>
							</div>
						</div>
					</s:form>

				</div>
			</div>
		</div>
	</div>
   </div>
</div>
