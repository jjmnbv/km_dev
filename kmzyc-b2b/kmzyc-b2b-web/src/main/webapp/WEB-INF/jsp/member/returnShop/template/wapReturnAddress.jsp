<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<s:form id="sform" name="sform" action="applyWapSubmit.action" method="post" theme="simple" autocomplete="off">
	<s:hidden name="page" id="page"/>
	<input type="hidden" value="<%=basePath%>" id="basePath"/>
	<input type="hidden" value="<%=session.getId()%>" id="jsessionId"/>
	<input name="alterType" type="hidden" value="${alterType}">
	<input name="alterNum" type="hidden" value="${alterNum}">
	<input name="evidence" type="hidden" value="${evidence}">
	<input type="hidden" name="batchNo" id="batchNo" value="${batchNo}"/>
	<input type="hidden" name="alterComment" id="alterComment" value="${alterComment}"/>
	<input type="hidden" name="backType" id="backType" value="${backType}"/>
 	<input type="hidden" id="orderCode" name="orderCode" value="${orderItem.orderCode}"/>
	<input type="hidden" id="orderItemId" name="orderItemId" value="${orderItem.orderItemId}"/>
	<div class="container">
    <section class="saleinfo">
        <h4>商品返回方式</h4>
        <button type="button" class="btn btn-success">快递至康美中药城</button>
        <p class="help-block">先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户。</p>
        <h4>换货买家地址</h4>
		<s:if test="null!=addressList && addressList.size() > 0">
        <div class="form-horizontal" id="addressList">
        <s:iterator value="addressList" id="addr" status="status">
       	<div class="radio address" id="lb_add_${addr.addressId}">
     		<label><input class="addressId" id="<s:property value="#status.index"/>" name="addressId" type="radio" value="${addr.addressId}" <s:if test="#addr.status==0||#status.index==0">checked="checked"</s:if>>
     				${addr.province}&nbsp;${addr.city}&nbsp;${addr.area}&nbsp;${addr.detailedAddress}&nbsp;(${addr.name} 收)&nbsp;${addr.cellphone}
     		</label>
     		<a href="javascript:void(0);" class="j_edit_address" data-name="${addr.name}" data-province='${addr.province}'
										data-city='${addr.city}' data-area='${addr.area}' data-detailedAddress='${addr.detailedAddress}' data-postalcode='${addr.postalcode}'
										data-cellphone='${addr.cellphone}' data-naddressId='${addr.addressId}' data-email='${addr.email}' data-telephone='${addr.telephone}'>编辑</a>
     		<s:if test="${addr.status!=0}">&nbsp;<a href="javascript:void(0);" class="j_del_address" data-value="${addr.addressId}">删除</a></s:if>
		</div>
		</s:iterator>
		</div>
		</s:if>
		<s:if test="null!=addressList && addressList.size()< 10">
		<div class="radio address">
     		<label><input type="radio" name="addressId" id="newAddr" value="" />新增收货地址</label>
     	</div>
     	</s:if>	
        <div id="editAddress" class="form-horizontal" <s:if test="null!=addressList && addressList.size()!=0">style="display:none;"</s:if>>
            <div class="form-group ">
                <input type="text" id="name" name="name" maxlength="15" class="form-control input-lg" placeholder="收货人姓名" />
            </div>
            <div class="form-group">
                <input type="text" id="mobile" name="mobile" maxlength="11" class="form-control input-lg" placeholder="手机号码" />
            </div>
            <div class="form-group">
                <input type="text" id="postalcode" name="postalcode" maxlength="6" class="form-control input-lg" placeholder="邮政编码" />
            </div>
            <div class="form-group">
                <select id="province" name="province" class="form-control input-lg"></select>
            </div>
            <div class="form-group">
                <select id="city" name="city" class="form-control input-lg"></select>
            </div>
            <div class="form-group">
                <select id="area" name="area"  class="form-control input-lg"></select>
            </div>
            <div class="form-group">
            	<input type="text" class="form-control" placeholder="详细地址" maxlength="60" id="detailedAddress" name="detailedAddress" />
            </div>
            <div class="form-group">
                <a class="btn btn-success" href="javascript:void(0);" id="j_save_address">保存地址</a>
            </div>
        </div>
        <div class="form-group">
     		<a href="javascript:void(0)" class="btn btn-success btn-block" id="addressSubmit">提 交</a>
		</div>
    </section>
	</div>
</s:form>
