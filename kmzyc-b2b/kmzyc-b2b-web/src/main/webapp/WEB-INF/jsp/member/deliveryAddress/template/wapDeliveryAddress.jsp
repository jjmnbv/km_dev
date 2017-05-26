<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input name="addressId" type="hidden" value="" id="addressId">

    <section class="page-content">
        <header>
            <div class="page-hd"><a href="javascript:void(0)" class="icon-angle-left fn-left"></a><h2>收货人信息</h2></div>
        </header>
        <div class="page-box">
            <!--收货人信息-->
            <div class="pay-box fn-p10">
            <s:iterator value="#request.addressList" id="Address" status="status">
                <div class="shadow-in">
                    <div class="text">
                        <span class="pitchon icon-check"></span>
                        <span class="fn-left fn-w5">
                           <p>姓名：${Address.name}</p>
                            <p>电话：${Address.cellphone}</p>
                            <p>地址：${Address.province}${Address.city}${Address.area}${Address.detailedAddress}</p>
                        </span>
                        <span class="fn-right fn-p-t30">
                            <a href="javascript:void(0)" class="icon icon-editor fn-w50 fn-t-c"> </a>
                            <s:if test="#Address.status==1">
                            <a href="javascript:void(0)" class="icon  icon-delete fn-w50 fn-t-c"></a>
                            </s:if>
                        </span>
                    </div>
                </div>
                </s:iterator>
                <p class="fn-t-c fn-t5"><a href="javascript:void(0)" id="btn_add_new_address" class="btn btn-success">添加新地址</a></p>
            </div>
        </div>
    </section>
    