<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="l-right user-m">
        <div class="o-mt">
            <h2>我的个人信息</h2>
        </div>
        <div class="user-m fn-t10">
            <div class="mc fn-clear">
                <div class="i-m">
                    <div class="i-mc">
                        <div class="form">
                            <div class="item">
                            <input type="hidden" id="eraNo" value='<s:property value="era.eraNo"/>'/>
                            <input type="hidden" id="eraInfoId" value='<s:property value="era.eraInfoId"/>'/>
                                <span class="label"  >康美中药城编号：</span>
                                <strong class="username"><s:property value="era.eraNo"/>&nbsp;&nbsp;&nbsp;</strong>
                            </div>
                            <div class="item">
                                <span class="label">手机号码：</span>
                                <strong class="username">
                                <s:if test="era.loginAccount!=null">
                                 <s:if test="era.mobile==null">
                                 <a class="setup" href='/member/goMobileVerifyPassword.action?verifyMobileInfo.modifyStatus=false' target="_blank">点击手机验证?
                                 </a>&nbsp;&nbsp;&nbsp;</s:if>
                                <s:else><s:property value="era.mobile"/><span>[已验证]</span></s:else>
                                </s:if>
                                </strong>
                            </div>
                            <div class="item">
                                <span class="label">昵称：</span>
                                <strong class="username"><s:property value="era.nickname"/></strong>
                            </div>
                            <div class="item">
                                <span class="label">真实姓名：</span>
                                <strong class="username"><s:property value="era.name"/></strong>
                            </div>
                            <div class="item">
                                <span class="label">证件号码：</span>
                                <strong class="username"><s:property value="era.certificateNumber"/></strong>
                            </div>
                            <div class="item">
                                <span class="label">联系电话：</span>
                                <strong class="username"><s:property value="era.contactInformation"/></strong>
                            </div>
                            <div class="item">
                                <span class="label">性别：</span>
                                <strong class="username"><s:property value="era.sex"/></strong>
                            </div>
                            <div class="item">
                                <span class="label">生日：</span>
                                <strong class="username"><s:date name="era.birthday" format="yyyy-MM-dd"/></strong>
                            </div>
                          </div>
                    </div>
                </div>
                <div class="right-extra">
                    <div class="m">
                        <div class="mc">
                            <div class="headpic">
                              
                                
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

