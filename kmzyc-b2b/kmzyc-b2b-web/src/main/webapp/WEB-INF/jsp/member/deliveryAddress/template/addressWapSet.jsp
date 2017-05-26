<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input name="addressId" type="hidden" value="" id="addressId">
<s:form action="editWapAction.action" method="Post" id="addressForm" AUTOCOMPLETE="off">
<s:token/>          
<s:hidden name="address.accountId" value="%{address.accountId}" />
<s:hidden name="address.addressId" id="addId" value="%{address.addressId}" />
    <section class="page-content">
        <header>
            <div class="page-hd"><a href="javascript:void(0)" class="icon-angle-left fn-left"></a><h2>添加收货人信息</h2></div>
        </header>
        <div class="page-box">
            <!--添加收货人信息-->
            <div class="pay-box fn-p10">
                <div class="form-input">
                    <form>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">收货人姓名：</label>
                            <span class="tbl-cell"><input type="text" class="input-text textinput input-block" name="address.name" value="<s:property value="address.name"/>"/></span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">手机号码：</label>
                            <span class="tbl-cell"><s:textfield name="address.cellphone" cssClass="input-text textinput input-block" value="%{address.cellphone}"></s:textfield></span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">省  份：</label>
                            <span class="tbl-cell">
                                <select class="form-control control-block" id="province" data-value="<s:property value="address.province"/>" name="address.province" ></select>
                            </span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">城  市：</label>
                            <span class="tbl-cell">
                                <select class="form-control control-block" id="city" data-value="<s:property value="address.city"/>" name="address.city"></select> 
                            </span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">县  区：</label>
                            <span class="tbl-cell">
                                <select class="form-control control-block" id="area" data-value="<s:property value="address.area"/>" name="address.area" ></select>
                            </span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">详细地址：</label>
                            <span class="tbl-cell"><input type="text" class="textinput control-block" name="address.detailedAddress" value="<s:property value="address.detailedAddress"/>" maxlength="60"/></span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">邮  编：</label>
                            <span class="tbl-cell"><s:textfield name="address.postalcode" cssClass="input-text textinput input-block" value="%{address.postalcode}" maxlength="6"></s:textfield></span>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">&nbsp;</label>
                            <span class="tbl-cell"><span class="cart-checkbox checked"><i class="icon-hook"></i>
                             <s:if test="address.status==0">
								     <s:checkbox name="status" value="true">
									<span>默认为收货地址</span>
								</s:checkbox>
								</s:if>
								<s:else>
									<s:checkbox name="status" value="false">
									<span>默认为收货地址</span>
								</s:checkbox>
								</s:else>
                        </div>
                        <div class="form-group tbl-type">
                            <label class="tbl-cell">&nbsp;</label>
                            <span class="tbl-cell"><a class="btn btn-success" href="javascript:void(0);" id="j_btn_submit">保存地址</a></span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
  </s:form>  